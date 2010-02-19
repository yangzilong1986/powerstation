package com.hzjbbis.fk.fe.filecache;

import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

public class RtuParamsCache {
    private static final Logger log = Logger.getLogger(RtuParamsCache.class);
    private static final int STOPPED = 0;
    private static final int RUNNING = 1;
    private static final int STOPPING = 2;
    private static RtuParamsCache instance = null;
    private static String filePath;
    private static final int ONE_RTU_CACHE_LEN = 45;
    private int batchSize = 1000;

    private Map<Integer, ComRtu> rtuMap = new HashMap(1024);
    private int _state = 0;

    private MappedByteBuffer buffer = null;

    static {
        try {
            File file = new File("data");
            file.mkdirs();
            filePath = file.getAbsolutePath() + File.separatorChar + "rtu-params.data";
            instance = new RtuParamsCache();
        } catch (Exception exp) {
            log.error(exp.getLocalizedMessage(), exp);
        }
    }

    private RtuParamsCache() {
        new RtuParamCacheThread();
    }

    public static final RtuParamsCache getInstance() {
        return instance;
    }

    public void initOnStartup(boolean create) {
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
        int count = this.buffer.capacity() / 45;
        int pos = 0;
        int rtua = -1;
        int i = 0;
        int successCount = 0;

        byte[] activeGprsBytes = new byte[21];
        byte[] subAppIdBytes = new byte[3];
        for (int i = 0; i < count; ++i) {
            this.buffer.position(pos);
            rtua = this.buffer.getInt();
            ComRtu rtuObj = null;
            if (create) {
                rtuObj = new ComRtu();
                rtuObj.setRtua(rtua);
                rtuObj.setLogicAddress(HexDump.toHex(rtua));
                RtuManage.getInstance().putComRtuToCache(rtuObj);
            } else {
                rtuObj = RtuManage.getInstance().getComRtuInCache(rtua);
            }
            if (rtuObj != null) {
                rtuObj.setRtuSavePosition(pos);

                byte b = this.buffer.get();
                if (b == 0) rtuObj.setManufacturer(null);
                else {
                    rtuObj.setManufacturer(HexDump.toHex(b));
                }
                long mobile = this.buffer.getLong();
                if (0L != mobile) {
                    if ((rtuObj.getSimNum() == null) || (rtuObj.getSimNum().length() == 0))
                        rtuObj.setSimNum(String.valueOf(mobile));
                } else {
                    rtuObj.setSimNum(null);
                }
                this.buffer.get(activeGprsBytes);
                int gprsLen = activeGprsBytes.length;
                for (int j = 0; j < activeGprsBytes.length; ++j) {
                    if (activeGprsBytes[j] == 0) {
                        gprsLen = j;
                        break;
                    }
                }
                if (gprsLen > 0) rtuObj.setActiveGprs(new String(activeGprsBytes, 0, gprsLen));
                else {
                    rtuObj.setActiveGprs(null);
                }
                mobile = this.buffer.getLong();
                if (0L != mobile) rtuObj.setActiveUms(String.valueOf(mobile));
                else {
                    rtuObj.setActiveUms(null);
                }
                this.buffer.get(subAppIdBytes);
                int subAppIdLen = subAppIdBytes.length;
                for (int j = 0; j < subAppIdBytes.length; ++j) {
                    if (subAppIdBytes[j] == 0) {
                        subAppIdLen = j;
                        break;
                    }
                }
                if (subAppIdLen > 0) rtuObj.setActiveSubAppId(new String(subAppIdBytes, 0, subAppIdLen));
                else rtuObj.setActiveSubAppId("");
                ++successCount;
            } else {
                ++i;
            }
            pos += 45;
        }
        if (i + successCount > 0) {
            double rate = i * 1.0D / (i + successCount);
            if (rate > 0.99D)
                log.warn("loading rtu params, but failedCount/total > 99%. RTU save format may be error.");
        }
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
            log.info("RtuParamsCache disposed, state=" + this._state);
        }
    }

    private void addNewRtu(Collection<ComRtu> rtus) {
        if ((rtus == null) || (rtus.size() == 0)) {
            return;
        }
        int count = rtus.size();
        int expandLength = count * 45;

        File f = new File(filePath);
        synchronized (instance) {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(f, "rw");
                int pos0 = (int) raf.length();
                raf.setLength(pos0 + expandLength);
                for (ComRtu rtu : rtus) {
                    raf.seek(pos0);
                    rtu.setRtuSavePosition(pos0);
                    raf.writeInt(rtu.getRtua());
                    pos0 += 45;
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
            if (rtu.getRtuSavePosition() < 0) newRtuList.add(rtu);
        }
        addNewRtu(newRtuList);
        newRtuList = null;

        synchronized (instance) {
            iter = list.iterator();
            while (iter.hasNext()) {
                ComRtu rtu = (ComRtu) iter.next();
                this.buffer.position(rtu.getRtuSavePosition());
                int rtua = this.buffer.getInt();
                if (rtua != rtu.getRtua()) {
                    log.warn("终端RTUA定位不一致：" + HexDump.toHex(rtua));
                } else {
                    String str = null;

                    byte manuf = 0;
                    this.buffer.put(manuf);

                    str = rtu.getSimNum();
                    if ((str == null) || (str.length() == 0)) str = "0";
                    long longVal = 0L;
                    try {
                        longVal = Long.parseLong(str);
                    } catch (Exception e) {
                        log.warn("simNum error:" + str, e);
                    }
                    this.buffer.putLong(longVal);

                    byte[] activeGprsBytes = new byte[21];
                    int endPos = 0;
                    str = rtu.getActiveGprs();
                    if ((str != null) && (str.length() > 0)) {
                        byte[] srcObj = str.getBytes();
                        endPos = srcObj.length;
                        if (endPos > 21) {
                            log.error("activeGprs>21 bytes. which is:" + str);
                            endPos = 21;
                        }
                        for (j = 0; j < endPos; ++j)
                            activeGprsBytes[j] = srcObj[j];
                    }
                    for (int j = endPos; j < 21; ++j)
                        activeGprsBytes[j] = 0;
                    this.buffer.put(activeGprsBytes);

                    longVal = 0L;
                    str = rtu.getActiveUms();
                    if ((str == null) || (str.length() == 0)) str = "0";
                    try {
                        longVal = Long.parseLong(str);
                    } catch (Exception e) {
                        log.warn("activeUms error:" + str, e);
                    }
                    this.buffer.putLong(longVal);

                    byte[] subAppIdBytes = new byte[3];
                    str = rtu.getActiveSubAppId();
                    endPos = 0;
                    if ((str != null) && (str.length() > 0)) {
                        byte[] srcObj = str.getBytes();
                        endPos = srcObj.length;
                        if (endPos > 3) {
                            log.error("activeSubAppId>3 bytes. which is:" + str);
                            endPos = 3;
                        }
                        for (int j = 0; j < endPos; ++j)
                            subAppIdBytes[j] = srcObj[j];
                    }
                    for (int j = endPos; j < subAppIdBytes.length; ++j)
                        subAppIdBytes[j] = 0;
                    this.buffer.put(subAppIdBytes);
                }
            }
            this.buffer.force();
        }
        list = null;
    }

    class RtuParamCacheThread extends Thread {
        public RtuParamCacheThread() {
            super("RtuParamCacheThread");
            setDaemon(true);
            start();
        }

        public void run() {
            RtuParamsCache.this._state = 1;
            while (true) try {
                do {
                    synchronized (RtuParamsCache.this.rtuMap) {
                        while (true) {
                            RtuParamsCache.this.rtuMap.wait(2000L);
                            if (RtuParamsCache.this.rtuMap.size() != 0) break;
                        }
                    }
                    long t1 = System.currentTimeMillis();
                    RtuParamsCache.this.doSave2Cache();
                    if (RtuParamsCache.log.isInfoEnabled()) {
                        long t2 = System.currentTimeMillis();
                        RtuParamsCache.log.info("save rtu params takes " + (t2 - t1) + " milliseconds");
                    }
                } while (RtuParamsCache.this._state != 2);
            } catch (Exception e) {
                while (true) RtuParamsCache.log.warn(e.getLocalizedMessage(), e);
            }

            RtuParamsCache.this._state = 0;
        }
    }
}