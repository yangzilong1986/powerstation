package com.hzjbbis.fk.fe.filecache;

import com.hzjbbis.fk.message.zj.MessageZj;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

public class UndocRtuMsgCache {
    private static final Logger log = Logger.getLogger(UndocRtuMsgCache.class);
    private static String path;
    private static int maxCount = 10;
    private static int maxSizeM = 100;
    private static final String fileName = "undocRtu.msg";
    private static final Object fLock = new Object();
    private static final int maxMessageSize = 100;
    private static final ArrayList<MessageZj> msgPool = new ArrayList(100);

    static {
        try {
            File file = new File("data");
            file.mkdirs();
            path = file.getAbsolutePath();
            System.out.println("undocumented rtu message file path= " + path);
        } catch (Exception exp) {
            log.error(exp.getLocalizedMessage(), exp);
        }
    }

    public static void addMessage(MessageZj msg) {
        synchronized (fLock) {
            if (msgPool.size() == 100) flush();
            msgPool.add(msg);
        }
    }

    public static void flush() {
        if (msgPool.size() == 0) return;
        synchronized (fLock) {
            try {
                _save2File();
                msgPool.clear();
            } catch (Exception e) {
                log.error("Undocument RTU message save to cache exception:" + e.getLocalizedMessage(), e);
            }
        }
    }

    private static void _save2File() throws IOException {
        String nextPath = getNextFilePath();
        PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(nextPath, true), 1048576));
        for (int i = 0; i < msgPool.size(); ++i) {
            MessageZj msg = (MessageZj) msgPool.get(i);
            if (msg == null) continue;
            printer.println(msg.getRawPacketString());
        }
        printer.flush();
        printer.close();
    }

    private static String getNextFilePath() {
        String npath = path + File.separatorChar + "undocRtu.msg";
        int stdNameLen = "undocRtu.msg".length();
        File f = new File(npath);
        if (!(f.exists())) return npath;
        if (f.length() >= maxSizeM << 20) {
            f = new File(path);
            File[] allFiles = new File[maxCount + 1];
            int maxIndex = -1;
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; ++i) {
                if (!(files[i].isFile())) continue;
                String fn = files[i].getName();
                if (!(fn.startsWith("undocRtu.msg"))) continue;
                String pfix = fn.substring(stdNameLen + 1);
                if (pfix.length() <= 0) continue;
                int appendInt = Integer.parseInt(pfix);
                if (appendInt >= allFiles.length) continue;
                allFiles[appendInt] = files[i];
                maxIndex = i;
            }
            for (i = maxIndex; i >= 0; --i)
                if (i >= maxCount) {
                    allFiles[i].delete();
                } else {
                    npath = path + File.separatorChar + "undocRtu.msg" + "." + (i + 1);
                    allFiles[i].renameTo(new File(npath));
                }
        }
        return path + File.separatorChar + "undocRtu.msg";
    }

    public static void setPath(String pstr) {
        try {
            File file = new File(pstr);
            if (!(file.isDirectory())) {
                log.error("未归档终端上行消息保存目录不存在：" + pstr);
                return;
            }
            path = file.getAbsolutePath();
            System.out.println("undocumented rtu message file path= " + path);
        } catch (Exception exp) {
            log.error(exp.getLocalizedMessage(), exp);
        }
    }

    public static void setMaxCount(int mc) {
        if (mc > 0) maxCount = mc;
    }

    public static void setMaxSizeM(int sizeM) {
        if (sizeM > 0) maxSizeM = sizeM;
    }
}