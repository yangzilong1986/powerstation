package com.hzjbbis.fk.fe.init;

import com.hzjbbis.db.DbMonitor;
import com.hzjbbis.db.initrtu.dao.ComRtuDao;
import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.fe.filecache.HeartbeatPersist;
import com.hzjbbis.fk.fe.filecache.RtuCommFlowCache;
import com.hzjbbis.fk.fe.filecache.RtuParamsCache;
import com.hzjbbis.fk.fe.msgqueue.BpBalanceFactor;
import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.fk.tracelog.TraceLog;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.util.*;

public class Initialize {
    private static final Logger log = Logger.getLogger(Initialize.class);
    private ComRtuDao rtuDao;
    private boolean bpClusterTest = false;

    public void setRtuDao(ComRtuDao rtuDao) {
        this.rtuDao = rtuDao;
    }

    public void initRtus() {
        ComRtu rtu;
        Iterator localIterator;
        TraceLog.getTracer().trace("initRtus called");
        List rtus = null;
        boolean loadDbSuccess = false;
        if (!(this.bpClusterTest)) {
            try {
                if (DbMonitor.getMasterMonitor().isAvailable()) {
                    rtus = this.rtuDao.loadComRtu();
                    loadDbSuccess = true;
                    if (rtus != null) for (localIterator = rtus.iterator(); localIterator.hasNext();) {
                        rtu = (ComRtu) localIterator.next();
                        RtuManage.getInstance().putComRtuToCache(rtu);
                    }
                    rtus = this.rtuDao.loadComGwRtu();
                    if (rtus != null) for (localIterator = rtus.iterator(); localIterator.hasNext();) {
                        rtu = (ComRtu) localIterator.next();
                        RtuManage.getInstance().putComRtuToCache(rtu);
                    }
                }
            } catch (Exception e) {
                log.warn("通信前置机RTU数据库初始化失败：" + e.getLocalizedMessage(), e);
            }
        } else {
            loadDbSuccess = true;
            rtus = testCase4BpCluster();
            for (localIterator = rtus.iterator(); localIterator.hasNext();) {
                rtu = (ComRtu) localIterator.next();
                RtuManage.getInstance().putComRtuToCache(rtu);
            }
        }

        RtuParamsCache.getInstance().initOnStartup(!(loadDbSuccess));

        BpBalanceFactor.getInstance().travelRtus(RtuManage.getInstance().getAllComRtu());

        HeartbeatPersist.getInstance().initOnStartup();

        RtuCommFlowCache.getInstance().initOnStartup();

        FasSystem.getFasSystem().addShutdownHook(new Runnable() {
            public void run() {
                Initialize.this.shutdownWork();
            }
        });
    }

    private void shutdownWork() {
        RtuParamsCache.getInstance().dispose();
        RtuCommFlowCache.getInstance().dispose();
        HeartbeatPersist.getInstance().dispose();
    }

    private List<ComRtu> testCase4BpCluster() {
        List list = new LinkedList();
        Map rtuMap = new HashMap();
        rtuMap.put(Integer.valueOf(-1862205439), Integer.valueOf(2));
        rtuMap.put(Integer.valueOf(-1845428223), Integer.valueOf(3));
        rtuMap.put(Integer.valueOf(-1828651007), Integer.valueOf(5));
        rtuMap.put(Integer.valueOf(-1811873791), Integer.valueOf(6));
        rtuMap.put(Integer.valueOf(-1795096575), Integer.valueOf(7));
        rtuMap.put(Integer.valueOf(-1778319359), Integer.valueOf(8));
        rtuMap.put(Integer.valueOf(-1761542143), Integer.valueOf(11));
        rtuMap.put(Integer.valueOf(-1744764927), Integer.valueOf(12));
        rtuMap.put(Integer.valueOf(-1727987711), Integer.valueOf(13));
        Iterator iter = rtuMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            for (int i = 0; i < ((Integer) entry.getValue()).intValue(); ++i) {
                ComRtu rtu = new ComRtu();
                int rtua = ((Integer) entry.getKey()).intValue() + i;
                rtu.setRtua(rtua);
                rtu.setLogicAddress(HexDump.toHex(rtua));
                list.add(rtu);
            }
        }
        return list;
    }

    public final void setBpClusterTest(boolean bpClusterTest) {
        this.bpClusterTest = bpClusterTest;
    }
}