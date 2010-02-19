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

public class HeartbeatPersist {
    private static final Logger log = Logger.getLogger(HeartbeatPersist.class);
    private static final TraceLog trace = TraceLog.getTracer(HeartbeatPersist.class);
    private static HeartbeatPersist instance = null;
    private static final int STOPPED = 0;
    private static final int RUNNING = 1;
    private static final int STOPPING = 2;
    private static final int ONE_RTU_CACHE_LEN = 40;
    private static final byte[] EMPTY_HEARTS = new byte[36];
    private static String rootPath;
    private Map<Integer, Integer> mapRtuPosition = new HashMap();

    private int batchSize = 1000;

    private String filePath = null;
    private MappedByteBuffer buffer = null;
    private final LinkedList<HeartbeatInfo> heartList = new LinkedList();

    private int _state = 0;

    static {
        try {
            File file = new File("data");
            file.mkdirs();
            rootPath = file.getAbsolutePath() + File.separatorChar;
            instance = new HeartbeatPersist();
        } catch (Exception exp) {
            trace.trace(exp.getLocalizedMessage(), exp);
        }
    }

    private HeartbeatPersist() {
        new HeartbeatHandleThread();
        for (int i = 0; i < EMPTY_HEARTS.length; ++i)
            EMPTY_HEARTS[i] = 0;
    }

    public static final HeartbeatPersist getInstance() {
        return instance;
    }

    public void handleHeartbeat(int rtua) {
        HeartbeatInfo hi = new HeartbeatInfo();
        hi.rtua = rtua;
        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(hi.rtua);
        if ((this.mapRtuPosition.get(Integer.valueOf(hi.rtua)) != null) && (rtu.getHeartSavePosition() > 0) && (((Integer) this.mapRtuPosition.get(Integer.valueOf(hi.rtua))).intValue() != rtu.getHeartSavePosition()))
            trace.trace("心跳打点时rtu：" + HexDump.toHex(hi.rtua) + "保存位置和map不对应 map上pos:" + this.mapRtuPosition.get(Integer.valueOf(rtu.getRtua())) + "rtu缓存地址：" + rtu.getHeartSavePosition());
        synchronized (this.heartList) {
            this.heartList.addLast(hi);
            if (this.heartList.size() >= this.batchSize) this.heartList.notifyAll();
        }
    }

    public void handleHeartbeat(int rtua, long time) {
        HeartbeatInfo hi = new HeartbeatInfo();
        hi.rtua = rtua;
        hi.time = time;
        synchronized (this.heartList) {
            this.heartList.addLast(hi);
            if (this.heartList.size() >= this.batchSize) this.heartList.notifyAll();
        }
    }

    public void initOnStartup() {
        int i;
        trace.trace("心跳初始化initOnStartup");
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());
        int dayOfMonth = cl.get(5);
        this.filePath = rootPath + "heartbeat-" + dayOfMonth + ".data";
        File f = new File(this.filePath);
        if ((!(f.exists())) || (f.length() == 0L)) {
            trace.trace("心跳" + this.filePath + "文件不存在,初始化结束");
            return;
        }
        synchronized (instance) {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(f, "rw");
                this.buffer = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0L, f.length());
            } catch (Exception e) {
                trace.trace("heartbeat file exception:" + e.getLocalizedMessage(), e);
            } finally {
                if (raf != null) try {
                    raf.close();
                    raf = null;
                } catch (Exception localException2) {
                }
            }
        }
        if (this.buffer == null) return;
        int count = this.buffer.capacity() / 40;
        int pos = 0;
        int rtua = -1;
        for (int i = 0; i < count; ++i) {
            rtua = this.buffer.getInt(pos);
            ComRtu rtuObj = RtuManage.getInstance().getComRtuInCache(rtua);
            if (rtuObj != null) rtuObj.setHeartSavePosition(pos);
            pos += 40;
        }
        trace.trace("心跳初始化initOnStartup 跑完结束");
    }

    public void dispose() {
        if (this._state != 1) return;
        this._state = 2;
        synchronized (this.heartList) {
            if (this.heartList.size() > 0) {
                this.heartList.notifyAll();
                int cnt = 10;
                break label54:
                do {
                    try {
                        Thread.sleep(100L);
                    } catch (Exception localException) {
                    }
                    label54:
                    if (cnt-- <= 0) break;
                } while (this._state != 0);
            }
        }
    }

    public void initPerDay() {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());
        int dayOfMonth = cl.get(5);

        trace.trace(dayOfMonth + "号初始化");

        this.filePath = rootPath + "heartbeat-" + dayOfMonth + ".data";
        File f = new File(this.filePath);

        if (f.exists()) {
            trace.trace(this.filePath + "已存在");
            f.delete();
            this.buffer = null;
        } else {
            trace.trace(this.filePath + "不存在");
        }
        synchronized (instance) {
            RandomAccessFile raf = null;
            try {
                ComRtu rtu;
                raf = new RandomAccessFile(f, "rw");
                List rtus = new ArrayList(RtuManage.getInstance().getAllComRtu());
                trace.trace("心跳每天初始化rtu队列大小：" + rtus.size());
                int maxPos = 0;
                for (Iterator localIterator = rtus.iterator(); localIterator.hasNext();) {
                    rtu = (ComRtu) localIterator.next();
                    if (rtu.getHeartSavePosition() > maxPos) maxPos = rtu.getHeartSavePosition();
                }
                raf.setLength(maxPos + 40);
                if (this.buffer != null) {
                    this.buffer.force();
                    this.buffer = null;
                }
                this.buffer = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0L, raf.length());
                for (localIterator = rtus.iterator(); localIterator.hasNext();) {
                    rtu = (ComRtu) localIterator.next();
                    if ((this.mapRtuPosition.get(Integer.valueOf(rtu.getRtua())) != null) && (rtu.getHeartSavePosition() > 0) && (((Integer) this.mapRtuPosition.get(Integer.valueOf(rtu.getRtua()))).intValue() != rtu.getHeartSavePosition()))
                        trace.trace("心跳每天初始化rtu:" + HexDump.toHex(rtu.getRtua()) + "位置发生错误 map上pos:" + this.mapRtuPosition.get(Integer.valueOf(rtu.getRtua())) + "rtu缓存地址：" + rtu.getHeartSavePosition());
                    if (rtu.getHeartSavePosition() >= 0) {
                        this.buffer.position(rtu.getHeartSavePosition());
                        this.buffer.putInt(rtu.getRtua());
                        this.buffer.put(EMPTY_HEARTS);
                    }
                }
                this.buffer.force();
            } catch (Exception e) {
                trace.trace("heartbeat file exception:" + e.getLocalizedMessage(), e);
            } finally {
                if (raf != null) try {
                    raf.close();
                    raf = null;
                } catch (Exception localException2) {
                }
            }
        }
    }

    private String heartbeatInfo2String(byte[] heartBits) {
        StringBuilder sb = new StringBuilder();
        try {
            int minute = 0;
            int hour = 0;
            int totalMinutes = 0;
            for (int i = 0; i < heartBits.length; ++i) {
                byte b = heartBits[i];
                int sum = 0;
                for (int j = 7; j >= 0; --j) {
                    int result = (j != 0) ? 1 << j & b : b & 0x1;
                    if (result != 0) {
                        sum = totalMinutes + 5 * (7 - j);
                        hour = sum / 60;
                        minute = sum % 60;
                        sb.append(hour).append(":").append(minute).append("; ");
                    }
                }
                totalMinutes += 40;
            }
        } catch (Exception e) {
            trace.trace(e.getLocalizedMessage(), e);
            sb.append("exception:").append(e.getLocalizedMessage());
        }
        return sb.toString();
    }

    public String queryHeartbeatInfo(int rtua) {
        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(rtua);
        if ((rtu == null) || (rtu.getHeartSavePosition() < 0)) return "no rtu";
        int pos = rtu.getHeartSavePosition() + 4;
        byte[] heartBits = new byte[36];
        this.buffer.position(pos);
        this.buffer.get(heartBits);
        return heartbeatInfo2String(heartBits);
    }

    public String queryHeartbeatInfo(int rtua, int dayOfMonth) {
        this.filePath = rootPath + "heartbeat-" + dayOfMonth + ".data";
        File f = new File(this.filePath);
        if ((!(f.exists())) || (f.length() == 0L)) {
            return "file not exist:" + this.filePath;
        }
        String result = "rtu not found in that day";
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(f, "rw");
            MappedByteBuffer buf = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0L, raf.length());

            int end = buf.capacity() - 40;
            for (int pos = 0; pos < end; pos += 40) {
                if (buf.getInt(pos) == rtua) {
                    buf.position(pos + 4);
                    byte[] heartBits = new byte[36];
                    buf.get(heartBits);
                    result = heartbeatInfo2String(heartBits);
                    break;
                }
            }

            buf = null;
        } catch (Exception e) {
            result = "heartbeat file exception:" + e.getLocalizedMessage();
            trace.trace(result, e);
        } finally {
            if (raf != null) try {
                raf.close();
                raf = null;
            } catch (Exception localException2) {
            }
        }
        return result;
    }

    private void addNewRtu2File(Collection<ComRtu> rtus) {
        if ((rtus == null) || (rtus.size() == 0)) {
            return;
        }
        trace.trace("有新的rtu:" + rtus.size());
        int count = rtus.size();
        int expandLength = count * 40;

        File f = new File(this.filePath);
        synchronized (instance) {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(f, "rw");
                int pos0 = (int) raf.length();
                raf.setLength(pos0 + expandLength);
                for (ComRtu rtu : rtus) {
                    raf.seek(pos0);
                    rtu.setHeartSavePosition(pos0);
                    raf.writeInt(rtu.getRtua());
                    raf.write(EMPTY_HEARTS);
                    this.mapRtuPosition.put(Integer.valueOf(rtu.getRtua()), Integer.valueOf(rtu.getHeartSavePosition()));
                    trace.trace("rtu:" + HexDump.toHex(rtu.getRtua()) + " pos:" + pos0);
                    pos0 += 40;
                }
                this.buffer = null;
                this.buffer = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0L, raf.length());
            } catch (Exception e) {
                trace.trace("addMoreRtu2File exception:" + e.getLocalizedMessage(), e);
            } finally {
                if (raf != null) try {
                    raf.close();
                    raf = null;
                } catch (Exception localException2) {
                }
            }
        }
    }

    private void processHeartInfoList() {
        Map uniqRtus = new HashMap();
        ArrayList list = null;
        if (this.heartList.size() == 0) return;
        synchronized (this.heartList) {
            list = new ArrayList(this.heartList);
            this.heartList.clear();
        }
        trace.trace("处理心跳，队列长度size：" + list.size());
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            HeartbeatInfo hi = (HeartbeatInfo) iter.next();
            ComRtu rtu = RtuManage.getInstance().getComRtuInCache(hi.rtua);
            if (rtu == null) {
                iter.remove();
            } else if (rtu.getHeartSavePosition() < 0) {
                uniqRtus.put(Integer.valueOf(rtu.getRtua()), rtu);
            }
        }
        addNewRtu2File(uniqRtus.values());
        uniqRtus = null;

        iter = list.iterator();
        synchronized (instance) {
            while (iter.hasNext()) {
                HeartbeatInfo hi = (HeartbeatInfo) iter.next();
                ComRtu rtu = RtuManage.getInstance().getComRtuInCache(hi.rtua);
                int rtua = this.buffer.getInt(rtu.getHeartSavePosition());
                if (rtua != rtu.getRtua()) {
                    trace.trace("终端RTUA定位不一致：" + HexDump.toHex(rtua));
                } else {
                    Calendar cl = Calendar.getInstance();
                    cl.setTimeInMillis(hi.time);
                    int hour = cl.get(11);
                    int minute = cl.get(12);
                    int quotient = minute / 5;
                    int bitPos = hour * 12 + quotient;
                    int delta = minute % 5;

                    quotient = bitPos / 8;
                    delta = 7 - (bitPos % 8);
                    byte b = 1;
                    if (delta > 0) b = (byte) (b << delta);
                    int pos = rtu.getHeartSavePosition() + 4 + quotient;
                    this.buffer.put(pos, (byte) (this.buffer.get(pos) | b));
                }
            }
            this.buffer.force();
        }
        list = null;
    }

    public final void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    class HeartbeatHandleThread extends Thread {
        public HeartbeatHandleThread() {
            super("HeartbeatHandle");
            setDaemon(true);
            start();
        }

        public void run() {
            HeartbeatPersist.this._state = 1;
            while (true) try {
                do {
                    synchronized (HeartbeatPersist.this.heartList) {
                        while (true) {
                            HeartbeatPersist.this.heartList.wait(60000L);
                            if (HeartbeatPersist.this.heartList.size() != 0) break;
                        }
                    }
                    long t1 = System.currentTimeMillis();
                    HeartbeatPersist.this.processHeartInfoList();
                    if (HeartbeatPersist.log.isDebugEnabled()) {
                        long t2 = System.currentTimeMillis();
                        HeartbeatPersist.trace.trace("save heartbeat takes " + (t2 - t1) + " milliseconds");
                    }
                } while (HeartbeatPersist.this._state != 2);
            } catch (Exception e) {
                while (true) HeartbeatPersist.trace.trace(e.getLocalizedMessage(), e);
            }

            HeartbeatPersist.this._state = 0;
        }
    }

    class HeartbeatInfo {
        public int rtua;
        public long time;

        HeartbeatInfo() {
            this.rtua = 0;
            this.time = System.currentTimeMillis();
        }
    }
}