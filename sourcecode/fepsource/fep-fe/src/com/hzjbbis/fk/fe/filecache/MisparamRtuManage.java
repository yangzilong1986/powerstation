package com.hzjbbis.fk.fe.filecache;

import com.hzjbbis.db.batch.dao.jdbc.JdbcBatchDao;
import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;
import org.apache.log4j.Logger;

import java.util.*;

public class MisparamRtuManage {
    private static final Logger log = Logger.getLogger(MisparamRtuManage.class);
    private JdbcBatchDao batchDao;
    private Object lock = new Object();
    private static MisparamRtuManage instance = null;

    private Map<Integer, MisparamRtu> disorders = new HashMap();

    public static MisparamRtuManage getInstance() {
        if (instance == null) instance = new MisparamRtuManage();
        return instance;
    }

    public void addRtuByGprs(ComRtu rtu, String actGprsAddr) {
        synchronized (this.lock) {
            MisparamRtu param = (MisparamRtu) this.disorders.get(Integer.valueOf(rtu.getRtua()));
            if (param == null) {
                param = new MisparamRtu();
                param.setRtua(rtu.getRtua());
                this.disorders.put(Integer.valueOf(param.getRtua()), param);
            }
            param.setGprsActiveCommAddr(actGprsAddr);
        }
    }

    public Collection<MisparamRtu> getAll() {
        return this.disorders.values();
    }

    public MisparamRtu get(int rtua) {
        return ((MisparamRtu) this.disorders.get(Integer.valueOf(rtua)));
    }

    public void remove(int rtua) {
        synchronized (this.disorders) {
            this.disorders.remove(Integer.valueOf(rtua));
        }
    }

    public void addRtuBySms(ComRtu rtu, String actSmsAddr) {
        synchronized (this.lock) {
            MisparamRtu param = (MisparamRtu) this.disorders.get(Integer.valueOf(rtu.getRtua()));
            if (param == null) {
                param = new MisparamRtu();
                param.setRtua(rtu.getRtua());
                this.disorders.put(Integer.valueOf(param.getRtua()), param);
            }
            param.setSmsActiveCommAddr(actSmsAddr);
        }
    }

    private boolean dirty() {
        return (this.disorders.size() <= 0);
    }

    public void save() {
        if (!(dirty())) {
            return;
        }
        synchronized (this.lock) {
            ArrayList arr = new ArrayList();
            for (MisparamRtu param : this.disorders.values()) {
                if (param.getLastUpdate() != 0L) {
                    continue;
                }

                param.setLastUpdate();
                arr.add(param);
            }
        }
    }

    public void merge2RtuCache() {
        synchronized (this.lock) {
            for (ComRtu rtu : RtuManage.getInstance().getAllComRtu()) {
                MisparamRtu p = (MisparamRtu) this.disorders.get(Integer.valueOf(rtu.getRtua()));
                String actGprs = null;
                String actUms = null;
                if (p != null) {
                    actGprs = p.getGprsActiveCommAddr();
                    actUms = p.getSmsActiveCommAddr();
                    p.setLastUpdate(0L);
                }

                rtu.setMisGprsAddress(actGprs);
                rtu.setMisSmsAddress(actUms);
            }
        }
    }

    public void merge2RtuCache(Collection<ComRtu> list) {
        synchronized (this.lock) {
            for (ComRtu rtu : list) {
                MisparamRtu p = (MisparamRtu) this.disorders.get(Integer.valueOf(rtu.getRtua()));
                String actGprs = null;
                String actUms = null;
                if (p != null) {
                    actGprs = p.getGprsActiveCommAddr();
                    actUms = p.getSmsActiveCommAddr();
                    p.setLastUpdate(0L);
                }

                rtu.setMisGprsAddress(actGprs);
                rtu.setMisSmsAddress(actUms);
            }
        }
    }

    public void saveRtuStatus2Db() {
        log.info("saveRtuStatus2Db start...");
        synchronized (this.lock) {
            merge2RtuCache();

            RtuManage rm = RtuManage.getInstance();
            List list = null;
            synchronized (rm) {
                list = new ArrayList(rm.getAllComRtu());
            }
            for (ComRtu rtu : list)
                this.batchDao.add(rtu);
            this.batchDao.batchUpdate();
        }
        log.info("saveRtuStatus2Db end...");
    }

    public void saveRtuStatus2Db(Collection<ComRtu> list) {
        log.info("saveRtuStatus2Db start...");
        synchronized (this.lock) {
            merge2RtuCache(list);

            for (ComRtu rtu : list)
                this.batchDao.add(rtu);
            this.batchDao.batchUpdate();
        }
        log.info("saveRtuStatus2Db end...");
    }

    public void saveRtuStatus2DbPerDay() {
        log.info("saveRtuStatus2DbPerDay start...");
        RtuManage rm = RtuManage.getInstance();
        Collection rtus = null;
        synchronized (rm) {
            rtus = new ArrayList(rm.getAllComRtu());
        }
        saveRtuStatus2Db(rtus);
        for (ComRtu rtu : rtus) {
            rtu.clearStatus();

            RtuCommFlowCache.getInstance().addRtu(rtu);
        }

        log.info("saveRtuStatus2DbPerDay end...");
    }

    public void setBatchDao(JdbcBatchDao batchDao) {
        this.batchDao = batchDao;
    }
}