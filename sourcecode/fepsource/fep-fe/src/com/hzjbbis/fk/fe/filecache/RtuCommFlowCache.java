package com.hzjbbis.fk.fe.filecache;

import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.fk.tracelog.TraceLog;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

public class RtuCommFlowCache {
    private static final Logger log = Logger.getLogger(RtuCommFlowCache.class);
    private static final TraceLog tracer = TraceLog.getTracer(RtuCommFlowCache.class);
    private static final int STOPPED = 0;
    private static final int RUNNING = 1;
    private static final int STOPPING = 2;
    private static RtuCommFlowCache instance;
    private static String filePath;
    private static final int ONE_RTU_CACHE_LEN = 48;
    private int batchSize = 1000;

    private Map<Integer, ComRtu> rtuMap = new HashMap(1024);
    private int _state = 0;

    private MappedByteBuffer buffer = null;

    static {
        try {
            File file = new File("data");
            file.mkdirs();
            filePath = file.getAbsolutePath() + File.separatorChar + "rtu-flow.data";
            instance = new RtuCommFlowCache();
        } catch (Exception exp) {
            log.error(exp.getLocalizedMessage(), exp);
        }
    }

    private RtuCommFlowCache() {
        new RtuCommFlowCacheThread();
    }

    public static final RtuCommFlowCache getInstance() {
        return instance;
    }

    public void initOnStartup() {
        int i;
        File f = new File(filePath);
        if ((!(f.exists())) || (f.length() == 0L)) return;
        synchronized (instance) {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(f, "rw");
                this.buffer = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0L, f.length());
            } catch (Exception e) {
                log.error("heartbeat file exception:" + e.getLocalizedMessage(), e);
            } finally {
                if (raf != null) try {
                    raf.close();
                    raf = null;
                } catch (Exception localException2) {
                }
            }
        }
        int count = this.buffer.capacity() / 48;

        boolean checkDataLength = false;
        int pos = 0;
        int i = -1;
        int failedCount = 0;
        int successCount = 0;

        for (int i = 0; i < count; ++i) {
            this.buffer.position(pos);
            i = this.buffer.getInt();
            ComRtu rtuObj = RtuManage.getInstance().getComRtuInCache(i);
            if (rtuObj != null) {
                rtuObj.setFlowSavePosition(pos);
                loadRtuFlowData(rtuObj);
                ++successCount;
            } else {
                ++failedCount;
                tracer.trace("can not find rtua in db when Rtu Flow init :" + HexDump.toHex(i));

                rtuObj = new ComRtu();
                rtuObj.setLogicAddress(HexDump.toHex(i));
                rtuObj.setRtua(i);
                rtuObj.setFlowSavePosition(pos);
                loadRtuFlowData(rtuObj);
                RtuManage.getInstance().putComRtuToCache(rtuObj);
            }
            if (!(checkDataLength)) {
                int dataLen = this.buffer.position() - pos;
                if (dataLen != 48) {
                    log.fatal("终端对象读取数据长度与每终端缓存长度不一致. ONE_RTU_CACHE_LEN＝48, readLen=" + dataLen);
                }
            }
            pos += 48;
        }
    }

    private void loadRtuFlowData(ComRtu rtuObj) {
        rtuObj.setUpGprsFlowmeter(this.buffer.getInt());
        rtuObj.setUpSmsCount(this.buffer.getInt());
        rtuObj.setDownGprsFlowmeter(this.buffer.getInt());
        rtuObj.setDownSmsCount(this.buffer.getInt());
        rtuObj.setUpGprsCount(this.buffer.getInt());
        rtuObj.setDownGprsCount(this.buffer.getInt());

        rtuObj.setLastGprsTime(this.buffer.getLong());
        rtuObj.setLastSmsTime(this.buffer.getLong());
        rtuObj.setTaskCount(this.buffer.getShort());
        rtuObj.setHeartbeatCount(this.buffer.getShort());
    }

    private void storeRtuFlowData(ComRtu rtu) {
        this.buffer.putInt(rtu.getUpGprsFlowmeter());
        this.buffer.putInt(rtu.getUpSmsCount());
        this.buffer.putInt(rtu.getDownGprsFlowmeter());
        this.buffer.putInt(rtu.getDownSmsCount());

        this.buffer.putInt(rtu.getUpGprsCount());
        this.buffer.putInt(rtu.getDownGprsCount());

        this.buffer.putLong(rtu.getLastGprsTime());
        this.buffer.putLong(rtu.getLastSmsTime());
        this.buffer.putShort((short) rtu.getTaskCount());
        this.buffer.putShort((short) rtu.getHeartbeatCount());
    }

    public void addRtu(ComRtu rtu) {
        synchronized (this.rtuMap) {
            this.rtuMap.put(Integer.valueOf(rtu.getRtua()), rtu);
            if (this.rtuMap.size() >= this.batchSize) this.rtuMap.notifyAll();
        }
    }

    public void dispose() {
        if (this._state != 1) return;
        this._state = 2;
        if (this.rtuMap.size() > 0) {
            this.rtuMap.notifyAll();
            int cnt = 20;

            while ((cnt-- > 0) && (this._state != 0)) try {
                Thread.sleep(100L);
            } catch (Exception localException) {
            }
        }
        log.info("RtuCommFlowCache disposed, state=" + this._state);
    }

    private void addNewRtu(Collection<ComRtu> rtus) {
        if ((rtus == null) || (rtus.size() == 0)) {
            return;
        }
        int count = rtus.size();
        int expandLength = count * 48;

        File f = new File(filePath);
        synchronized (instance) {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(f, "rw");
                int pos0 = (int) raf.length();
                raf.setLength(pos0 + expandLength);
                for (ComRtu rtu : rtus) {
                    raf.seek(pos0);
                    rtu.setFlowSavePosition(pos0);
                    raf.writeInt(rtu.getRtua());
                    pos0 += 48;
                }
                this.buffer = null;
                this.buffer = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0L, raf.length());
            } catch (Exception e) {
                log.warn("addNewRtu exception:" + e.getLocalizedMessage(), e);
            } finally {
                if (raf != null) try {
                    raf.close();
                    raf = null;
                } catch (Exception localException2) {
                }
            }
        }
    }

    private void doSave2Cache() {
        ArrayList list = null;

        if (this.rtuMap.size() == 0) return;
        synchronized (this.rtuMap) {
            list = new ArrayList(this.rtuMap.values());
            this.rtuMap.clear();
        }

        ArrayList newRtuList = new ArrayList();
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            ComRtu rtu = (ComRtu) iter.next();
            if (rtu.getFlowSavePosition() < 0) newRtuList.add(rtu);
        }
        addNewRtu(newRtuList);
        newRtuList = null;

        synchronized (instance) {
            iter = list.iterator();
            while (iter.hasNext()) {
                ComRtu rtu = (ComRtu) iter.next();
                this.buffer.position(rtu.getFlowSavePosition());
                int rtua = this.buffer.getInt();
                if (rtua != rtu.getRtua()) {
                    log.warn("终端RTUA定位不一致：" + HexDump.toHex(rtua));
                } else storeRtuFlowData(rtu);
            }
            this.buffer.force();
        }
        list = null;
    }

    class RtuCommFlowCacheThread extends Thread {
        public RtuCommFlowCacheThread() {
            super("rtuCommFlowCacheThread");
            setDaemon(true);
            start();
        }

        public void run() {
            RtuCommFlowCache.this._state = 1;
            while (true) try {
                do {
                    synchronized (RtuCommFlowCache.this.rtuMap) {
                        while (true) {
                            RtuCommFlowCache.this.rtuMap.wait(60000L);
                            if (RtuCommFlowCache.this.rtuMap.size() != 0) break;
                        }
                    }
                    long t1 = System.currentTimeMillis();
                    RtuCommFlowCache.this.doSave2Cache();
                    if (RtuCommFlowCache.log.isInfoEnabled()) {
                        long t2 = System.currentTimeMillis();
                        RtuCommFlowCache.log.info("save rtu params takes " + (t2 - t1) + " milliseconds");
                    }
                } while (RtuCommFlowCache.this._state != 2);
            } catch (Exception e) {
                while (true) RtuCommFlowCache.log.warn(e.getLocalizedMessage(), e);
            }

            RtuCommFlowCache.this._state = 0;
        }
    }
}