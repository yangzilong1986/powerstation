package com.hzjbbis.db.managertu;

import com.hzjbbis.db.batch.BatchDaoParameterUtils;
import com.hzjbbis.db.initrtu.dao.BizRtuDao;
import com.hzjbbis.db.initrtu.dao.ComRtuDao;
import com.hzjbbis.db.rtu.RtuRefreshDao;
import com.hzjbbis.fk.model.*;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;

public class ManageRtu {
    private static final Logger log = Logger.getLogger(ManageRtu.class);
    private ComRtuDao comRtuDao;
    private BizRtuDao bizRtuDao;
    private RtuRefreshDao rtuRefreshDao;
    public RtuManage rtuManage = RtuManage.getInstance();

    public void loadComRtu() {
        log.info("start initializeComRtu");
        try {
            List comRtus = this.comRtuDao.loadComRtu();
            log.info("ComRtus size:" + comRtus.size());
            for (ComRtu rtu : comRtus)
                this.rtuManage.putComRtuToCache(rtu);
        } catch (Exception ex) {
            log.error("loadComRtus" + ex);
        }
        log.info("end initializeComRtu");
    }

    public void loadBizRtu() {
        int size = 0;
        long startTime = System.currentTimeMillis();
        log.info("start initializeBizRtu");
        size = initializeBizRtu();
        log.info("end initializeBizRtu");
        long endTime = System.currentTimeMillis();
        long timeConsume = endTime - startTime;
        long speed = size * 1000 / timeConsume;
        log.info(size + "个终端加载时间=" + timeConsume + "ms;效率=" + speed + "/s");

        startTime = System.currentTimeMillis();
        log.info("start initializeTaskTemplate");
        size = initializeTaskTemplate();
        log.info("end initializeTaskTemplate");
        endTime = System.currentTimeMillis();
        timeConsume = endTime - startTime;
        speed = size * 1000 / timeConsume;
        log.info(size + "个任务模版加载时间=" + timeConsume + "ms;效率=" + speed + "/s");

        startTime = System.currentTimeMillis();
        log.info("start initializeTaskDbConfig");
        size = initializeTaskDbConfig();
        log.info("end initializeTaskDbConfig");
        endTime = System.currentTimeMillis();
        timeConsume = endTime - startTime;
        speed = size * 1000 / timeConsume;
        log.info(size + "个任务数据项数据库表映射关系加载时间=" + timeConsume + "ms;效率=" + speed + "/s");

        startTime = System.currentTimeMillis();
        log.info("start initializeAlertCode");
        size = initializeAlertCode();
        log.info("end initializeAlertCode");
        endTime = System.currentTimeMillis();
        timeConsume = endTime - startTime;
        speed = size * 1000 / timeConsume;
        log.info(size + "个异常数据项加载时间=" + timeConsume + "ms;效率=" + speed + "/s");

        log.info("start initializeSysConfig");
        size = initializeSysConfig();
        log.info("end initializeSysConfig");
    }

    public boolean refreshBizRtu(String zdjh) {
        try {
            BizRtu bizRtu = this.rtuRefreshDao.getRtu(zdjh);
            if (bizRtu != null) {
                this.rtuManage.putBizRtuToCache(bizRtu);
                return true;
            }
            return false;
        } catch (Exception ex) {
            log.error("find not rtuId:" + zdjh);
        }
        return false;
    }

    public boolean refreshBizRtu(int rtua) {
        try {
            BizRtu bizRtu = this.rtuRefreshDao.getRtu(rtua);
            if (bizRtu != null) {
                this.rtuManage.putBizRtuToCache(bizRtu);
                return true;
            }

            return false;
        } catch (Exception ex) {
            log.error("find not rtuAdd:" + HexDump.toHex(rtua));
        }
        return false;
    }

    public boolean refreshComRtu(int rtua) {
        try {
            ComRtu comRtu = this.rtuRefreshDao.getComRtu(rtua);
            if (comRtu != null) {
                this.rtuManage.putComRtuToCache(comRtu);
                return true;
            }

            return false;
        } catch (Exception ex) {
            log.error("find not comRtuAdd:" + HexDump.toHex(rtua));
        }
        return false;
    }

    public void refreshMeasurePoints(String zdjh) {
        List mps = this.rtuRefreshDao.getMeasurePoints(zdjh);
        for (MeasuredPoint mp : mps)
            this.rtuManage.putMeasuredPointToCache(mp);
    }

    public void refreshGwMeasurePoints(String zdjh) {
        List mps = this.rtuRefreshDao.getGwMeasurePoints(zdjh);
        for (MeasuredPoint mp : mps)
            this.rtuManage.putMeasuredPointToCache(mp);
    }

    public void refreshRtuTasks(String zdjh) {
        List rts = this.rtuRefreshDao.getRtuTasks(zdjh);
        if (rts == null) rts = this.rtuRefreshDao.getGwRtuTasks(zdjh);
        for (RtuTask rt : rts)
            this.rtuManage.putRtuTaskToCache(rt);
    }

    public void refreshTaskTemplate(String templID) {
        try {
            TaskTemplate tt = this.rtuRefreshDao.getTaskTemplate(templID);
            this.rtuManage.putTaskTemplateToCache(tt);
            List ttis = this.rtuRefreshDao.getTaskTemplateItems(templID);
            for (TaskTemplateItem tti : ttis)
                this.rtuManage.putTaskTemplateItemToCache(tti);
        } catch (Exception ex) {
            log.error("find not templID:" + templID);
        }
    }

    private int initializeBizRtu() {
        Iterator localIterator;
        int size = 0;
        try {
            BizRtu rtu;
            List bizRtus = this.bizRtuDao.loadBizRtu();
            size = bizRtus.size();
            log.info("BizRtuList size:" + bizRtus.size());
            for (localIterator = bizRtus.iterator(); localIterator.hasNext();) {
                rtu = (BizRtu) localIterator.next();
                rtu.setRtuType("01");
                if (rtu.getRtuProtocol() == null) rtu.setRtuProtocol("01");
                this.rtuManage.putBizRtuToCache(rtu);
            }

            bizRtus = this.bizRtuDao.loadBizGwRtu();
            size = bizRtus.size();
            log.info("BizGwRtuList size:" + bizRtus.size());
            for (localIterator = bizRtus.iterator(); localIterator.hasNext();) {
                rtu = (BizRtu) localIterator.next();
                rtu.setRtuType("02");
                if (rtu.getRtuProtocol() == null) rtu.setRtuProtocol("02");
                this.rtuManage.putBizRtuToCache(rtu);
            }
        } catch (Exception ex) {
            log.error("loadBizRtus" + ex);
            return size;
        }
        try {
            MeasuredPoint mp;
            List mps = this.bizRtuDao.loadMeasuredPoints();
            log.info("MeasuredPointList size:" + mps.size());
            for (localIterator = mps.iterator(); localIterator.hasNext();) {
                mp = (MeasuredPoint) localIterator.next();
                this.rtuManage.putMeasuredPointToCache(mp);
            }
            mps = this.bizRtuDao.loadGwMeasuredPoints();
            log.info("GwMeasuredPointList size:" + mps.size());
            for (localIterator = mps.iterator(); localIterator.hasNext();) {
                mp = (MeasuredPoint) localIterator.next();
                this.rtuManage.putMeasuredPointToCache(mp);
            }
        } catch (Exception mps) {
            log.error("loadMeasuredPoints" + ex);
        }
        try {
            RtuTask rt;
            List rts = this.bizRtuDao.loadRtuTasks();
            log.info("RtuTaskList size:" + rts.size());
            for (localIterator = rts.iterator(); localIterator.hasNext();) {
                rt = (RtuTask) localIterator.next();
                this.rtuManage.putRtuTaskToCache(rt);
            }
            rts = this.bizRtuDao.loadGwRtuTasks();
            log.info("GwRtuTaskList size:" + rts.size());
            for (localIterator = rts.iterator(); localIterator.hasNext();) {
                rt = (RtuTask) localIterator.next();
                this.rtuManage.putRtuTaskToCache(rt);
            }
        } catch (Exception rts) {
            log.error("loadRtuTasks" + ex);
        }
        return size;
    }

    private int initializeTaskTemplate() {
        int size = 0;
        try {
            List tts = this.bizRtuDao.loadTaskTemplate();
            size = tts.size();
            log.info("TaskTemplateList size:" + tts.size());
            for (TaskTemplate tt : tts)
                this.rtuManage.putTaskTemplateToCache(tt);
        } catch (Exception ex) {
            log.error("loadTaskTemplate" + ex);
            return size;
        }
        return size;
    }

    public int initializeSysConfig() {
        int size = 0;
        try {
            List sc = this.bizRtuDao.loadSysConfig();
            size = sc.size();
            log.info("SysConfig size:" + sc.size());
            if (size == 2) {
                SysConfig sysConfig = new SysConfig();
                sysConfig.setBj10(((SysConfig) sc.get(0)).getPzz());
                sysConfig.setBj11(((SysConfig) sc.get(1)).getPzz());
                this.rtuManage.setSysConfig(sysConfig);
                BatchDaoParameterUtils.getInstance().setAdditiveParameter(RtuManage.getInstance().getSysConfig());
            }
        } catch (Exception ex) {
            log.error("loadSysConfig" + ex);
            return size;
        }
        return size;
    }

    public int initializeTaskDbConfig() {
        int size = 0;
        try {
            List rdcs = this.bizRtuDao.loadTaskDbConfig();
            size = rdcs.size();
            log.info("TaskDbConfigList size:" + rdcs.size());
            for (TaskDbConfig rdc : rdcs)
                this.rtuManage.putTaskDbConfigToCache(rdc);
        } catch (Exception ex) {
            log.error("loadTaskDbConfig" + ex);
        }
        return size;
    }

    private int initializeAlertCode() {
        int size = 0;
        try {
            List racs = this.bizRtuDao.loadRtuAlertCodes();
            size = racs.size();
            log.info("RtuAlertCodeList size:" + racs.size());
            for (RtuAlertCode rac : racs)
                this.rtuManage.putAlertCodeToCache(rac);
        } catch (Exception ex) {
            log.error("loadRtuAlertCodes" + ex);
        }
        return size;
    }

    public void setComRtuDao(ComRtuDao comRtuDao) {
        this.comRtuDao = comRtuDao;
    }

    public void setBizRtuDao(BizRtuDao bizRtuDao) {
        this.bizRtuDao = bizRtuDao;
    }

    public void setRtuRefreshDao(RtuRefreshDao rtuRefreshDao) {
        this.rtuRefreshDao = rtuRefreshDao;
    }
}