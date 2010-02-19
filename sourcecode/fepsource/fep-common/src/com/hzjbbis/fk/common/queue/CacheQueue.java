package com.hzjbbis.fk.common.queue;

import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageLoader;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class CacheQueue {
    private static String s_cachePath;
    private int maxSize = 10000;
    private int minSize = 2000;
    private int maxFileSize = 20;
    private int fileCount = 20;
    private String key = "undefine";
    private MessageLoader messageLoader;
    private String cachePath;
    private static final Logger log;
    private final ArrayList<LinkedList<IMessage>> queue = new ArrayList(6);
    private final ArrayList<IMessage> tempQueue = new ArrayList(this.maxSize);
    private final Object fileLock = new Object();
    private long minInterval = 10000L;
    private long lastCacheRead = System.currentTimeMillis() - this.minInterval;
    private long lastCacheWrite = System.currentTimeMillis() - this.minInterval;

    static {
        String workDir = System.getProperty("user.dir");
        s_cachePath = workDir + File.separator + "cache";
        File f = new File(s_cachePath);
        if (!(f.exists())) {
            f.mkdir();
        }

        log = Logger.getLogger(CacheQueue.class);
    }

    public CacheQueue() {
        for (int i = 0; i < 6; ++i) {
            LinkedList list = new LinkedList();
            this.queue.add(list);
        }
        try {
            Class clz = Class.forName(MessageLoader.class.getPackage().getName() + ".MultiProtoMessageLoader");
            this.messageLoader = ((MessageLoader) clz.newInstance());
        } catch (ClassNotFoundException notFoundExp) {
            log.error(notFoundExp.getLocalizedMessage(), notFoundExp);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        this.cachePath = s_cachePath;
    }

    public void offer(IMessage message) {
        if ((message.getPriority() < 0) || (message.getPriority() > 5)) message.setPriority(1);
        synchronized (this.queue) {
            if (size() >= this.maxSize) {
                asyncSaveQueue();
            }
            ((LinkedList) this.queue.get(message.getPriority())).add(message);
            this.queue.notifyAll();
        }
    }

    public IMessage poll() {
        synchronized (this.queue) {
            do {
                for (int i = 5; i >= 0; --i) {
                    LinkedList list = (LinkedList) this.queue.get(i);
                    if (list.size() > 0) return ((IMessage) list.remove());
                }
            } while (_loadFromFile());

            return null;
        }
    }

    public IMessage peek() {
        synchronized (this.queue) {
            do {
                for (int i = 5; i >= 0; --i) {
                    LinkedList list = (LinkedList) this.queue.get(i);
                    if (list.size() > 0) return ((IMessage) list.getFirst());
                }
            } while (_loadFromFile());

            return null;
        }
    }

    public IMessage take() {
        synchronized (this.queue) {
            while (true) {
                for (int i = 5; i >= 0; --i) {
                    LinkedList list = (LinkedList) this.queue.get(i);
                    if (list.size() > 0) {
                        return ((IMessage) list.remove());
                    }
                }
                if (_loadFromFile()) continue;
                try {
                    this.queue.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
        }
    }

    public int size() {
        int size = 0;
        for (int i = 5; i >= 0; --i) {
            LinkedList list = (LinkedList) this.queue.get(i);
            size += list.size();
        }
        return size;
    }

    public void dispose() {
        log.info("cacheQueue disposed. key=" + this.key);
        synchronized (this.tempQueue) {
            for (int i = 0; i <= 5; ++i) {
                LinkedList ar = (LinkedList) this.queue.get(i);
                this.tempQueue.addAll(ar);
                ar.clear();
            }
            this.lastCacheWrite = System.currentTimeMillis();
            __saveToFile();
        }
    }

    public void asyncSaveQueue() {
        int count = this.maxSize - this.minSize;
        synchronized (this.tempQueue) {
            long now = System.currentTimeMillis();
            if (now - this.lastCacheWrite < this.minInterval) {
                return;
            }
            for (int i = 0; i <= 5; ++i) {
                LinkedList ar = (LinkedList) this.queue.get(i);
                this.tempQueue.addAll(ar);
                count -= ar.size();
                ar.clear();
                if (count <= 0) {
                    break;
                }
            }
            this.lastCacheWrite = System.currentTimeMillis();
            CacheFileWriteThread t = new CacheFileWriteThread();
            t.start();
        }
    }

    private void __saveToFile() {
        synchronized (this.fileLock) {
            String filename = _findWriteCacheFileName();
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
                _saveMessages(out);
                out.close();
                out = null;
            } catch (Exception exp) {
                StringBuffer sb = new StringBuffer();
                sb.append("消息队列保存异常,filename=").append(filename);
                sb.append(",异常原因：").append(exp.getLocalizedMessage());
                log.error(sb.toString(), exp);
                return;
            }
        }
    }

    private boolean _loadFromFile() {
        RandomAccessFile raf;
        String filename;
        long now = System.currentTimeMillis();
        if (now - this.lastCacheRead < this.minInterval) return false;
        this.lastCacheRead = System.currentTimeMillis();

        synchronized (this.fileLock) {
            raf = null;
            filename = _findReadCacheFileName();
            if (filename != null) break label52;
            return false;
            label52:
            if (!(log.isDebugEnabled())) break label85;
            label85:
            log.debug("begin read cache file(开始加载缓存文件):" + filename);
        }

        label467:
        return false;
    }

    private void _saveMessages(PrintWriter out) {
        synchronized (this.tempQueue) {
            String strMsg = null;
            for (IMessage msg : this.tempQueue) {
                strMsg = this.messageLoader.serializeMessage(msg);
                if ((strMsg != null) && (strMsg.length() > 0)) out.println(strMsg);
            }
            this.tempQueue.clear();
        }
    }

    private String _findWriteCacheFileName() {
        String fname0 = "cache-" + this.key + "-";
        File f = new File(this.cachePath);
        File[] list = f.listFiles();
        if (list == null) {
            list = new File[0];
        }
        File oldestFile = null;
        Date oldDate = new Date(0L);
        Date curDate = new Date(0L);

        for (int i = 0; i < this.fileCount; ++i) {
            String cname = fname0 + i + ".txt";
            boolean found = false;
            for (int j = 0; j < list.length; ++j)
                if (list[j].isFile()) {
                    if (list[j].getName().indexOf(fname0) < 0) {
                        continue;
                    }
                    if (oldestFile == null) {
                        oldestFile = list[j];
                        oldDate.setTime(oldestFile.lastModified());
                    } else {
                        curDate.setTime(list[j].lastModified());
                        if (curDate.before(oldDate)) {
                            oldDate.setTime(curDate.getTime());
                            oldestFile = list[j];
                        }
                    }
                    if (cname.equals(list[j].getName())) {
                        found = true;
                        File file = new File(this.cachePath + File.separator + cname);
                        if (file.length() < this.maxFileSize * 1024 * 1024) {
                            return file.getPath();
                        }
                    }
                }
            if (!(found)) {
                return this.cachePath + File.separator + cname;
            }
        }
        if (oldestFile != null) {
            String opath = oldestFile.getPath();
            oldestFile.delete();
            return opath;
        }
        return this.cachePath + File.separator + fname0 + "exp.txt";
    }

    public String _findReadCacheFileName() {
        String fname0 = "cache-" + this.key + "-";
        File f = new File(this.cachePath);
        File[] list = f.listFiles();
        if (list == null) {
            log.warn(f.getPath() + ":列表错误。null==list");
            return null;
        }

        for (int j = 0; j < list.length; ++j) {
            File file = list[j];
            if (!(file.isFile())) continue;
            if (file.length() <= 0L) continue;
            String s = file.getName();
            if (s.indexOf(fname0) == 0) return file.getPath();
        }
        if (log.isDebugEnabled()) log.debug(f.getPath() + ":目录下无缓存文件。");
        return null;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(int maxSize) {
        if (maxSize <= 100) maxSize = 100;
        else if (maxSize > 200000) maxSize = 200000;
        this.maxSize = maxSize;
        _adjustMinSize();
    }

    private void _adjustMinSize() {
        if ((this.minSize <= this.maxSize >> 4) || (this.minSize > this.maxSize >> 2))
            this.minSize = (this.maxSize >> 4);
    }

    public int getMinSize() {
        return this.minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
        _adjustMinSize();
    }

    public int getMaxFileSize() {
        return this.maxFileSize;
    }

    public void setMaxFileSize(int maxFileSize) {
        if ((maxFileSize <= 0) || (maxFileSize > 500)) maxFileSize = 500;
        this.maxFileSize = maxFileSize;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MessageLoader getMessageLoader() {
        return this.messageLoader;
    }

    public void setMessageLoader(MessageLoader messageLoader) {
        this.messageLoader = messageLoader;
    }

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    class CacheFileWriteThread extends Thread {
        public CacheFileWriteThread() {
            super("cacheWrite-" + CacheQueue.this.key);
        }

        public void run() {
            CacheQueue.this.__saveToFile();
        }
    }
}