package com.hzjbbis.fk.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RtuManage {
    private static final Log log = LogFactory.getLog(RtuManage.class);
    private static RtuManage instance;
    private static String defaultRtuProtocol = "01";
    private static SysConfig sysConfig;
    private static Map<Integer, ComRtu> comRtuMap = new HashMap(102400);

    private static Map<String, Integer> bizRtuaIdMap = new HashMap(102400);

    private static Map<Integer, BizRtu> bizRtuMap = new HashMap(102400);

    private static Map<Integer, TaskTemplate> taskPlateMap = new HashMap();

    private static Map<Integer, RtuAlertCode> alertCodeMap = new HashMap();

    private static Map<String, TaskDbConfig> taskDbConfigMap = new HashMap();

    private static Map<Integer, Boolean> rtuRemoteUpdateMap = new HashMap();

    public static RtuManage getInstance() {
        if (instance == null) {
            synchronized (RtuManage.class) {
                if (instance == null) {
                    instance = new RtuManage();
                }
            }
        }
        return instance;
    }

    private static void checkProtocol(BizRtu rtu) {
        if (rtu.getRtuProtocol() == null) rtu.setRtuProtocol(defaultRtuProtocol);
    }

    public ComRtu getComRtuInCache(int rtua) {
        return ((ComRtu) comRtuMap.get(new Integer(rtua)));
    }

    public Collection<ComRtu> getAllComRtu() {
        return comRtuMap.values();
    }

    public Map<Integer, ComRtu> getComRtuMap() {
        return comRtuMap;
    }

    public BizRtu getBizRtuInCache(int rtua) {
        return ((BizRtu) bizRtuMap.get(new Integer(rtua)));
    }

    public BizRtu getBizRtuInCache(String rtuId) {
        return ((BizRtu) bizRtuMap.get(bizRtuaIdMap.get(rtuId)));
    }

    public synchronized void putBizRtuToCache(BizRtu bizRtu) {
        try {
            checkProtocol(bizRtu);
            bizRtu.setRtua((int) Long.parseLong(bizRtu.getLogicAddress(), 16));
            bizRtuaIdMap.put(bizRtu.getRtuId(), new Integer(bizRtu.getRtua()));
            bizRtuMap.put(new Integer(bizRtu.getRtua()), bizRtu);
        } catch (Exception ex) {
            log.debug("Error to put BizRtu: " + bizRtu.toString(), ex);
        }
    }

    public synchronized void putComRtuToCache(ComRtu comRtu) {
        try {
            comRtu.setRtua((int) Long.parseLong(comRtu.getLogicAddress(), 16));
            comRtuMap.put(new Integer(comRtu.getRtua()), comRtu);
        } catch (Exception ex) {
            log.debug("Error to put ComRtu: " + comRtu.toString(), ex);
        }
    }

    public synchronized void putMeasuredPointToCache(MeasuredPoint mp) {
        try {
            BizRtu bizRtu = getBizRtuInCache(mp.getRtuId());
            if (bizRtu == null) {
                log.debug("Can't find busRtu when loading MeasuredPoint: " + mp.toString());
                return;
            }
            bizRtu.addMeasuredPoint(mp);
        } catch (Exception ex) {
            log.debug("Error to put MeasuredPoint: " + mp.toString(), ex);
        }
    }

    public synchronized void putRtuTaskToCache(RtuTask rt) {
        try {
            BizRtu bizRtu = getBizRtuInCache(rt.getRtuId());
            if (bizRtu == null) {
                log.debug("Can't find busRtu when loading RtuTask: " + rt.toString());
                return;
            }
            bizRtu.addRtuTask(rt);
        } catch (Exception ex) {
            log.debug("Error to put RtuTask: " + rt.toString(), ex);
        }
    }

    public TaskTemplate getTaskPlateInCache(String taskPlateID) {
        return ((TaskTemplate) taskPlateMap.get(new Integer(taskPlateID)));
    }

    public RtuAlertCode getRtuAlertCode(int code) {
        return ((RtuAlertCode) alertCodeMap.get(new Integer(code)));
    }

    public synchronized void putTaskTemplateToCache(TaskTemplate tp) {
        try {
            taskPlateMap.put(new Integer(tp.getTaskTemplateID()), tp);
        } catch (Exception ex) {
            log.debug("Error to put TaskPlate: " + tp.toString(), ex);
        }
    }

    public synchronized void putTaskTemplateItemToCache(TaskTemplateItem tpi) {
        try {
            TaskTemplate tp = (TaskTemplate) taskPlateMap.get(new Integer(tpi.getTaskTemplateID()));
            if (tp == null) {
                log.debug("Can't find TaskPlate when loading TaskPlateItem: " + tpi.toString());
                return;
            }
            tp.addDataCode(tpi.getCode());
        } catch (Exception ex) {
            log.debug("Error to put TaskPlateItem: " + tpi.toString(), ex);
        }
    }

    public TaskDbConfig getTaskDbConfigInCache(String key) {
        return ((TaskDbConfig) taskDbConfigMap.get(key));
    }

    public synchronized void putTaskDbConfigToCache(TaskDbConfig tsi) {
        try {
            tsi.setDbConfigStr(tsi.getDbConfigStr());
            taskDbConfigMap.put(tsi.getCode(), tsi);
        } catch (Exception ex) {
            log.debug("Error to put TaskDbConfig: " + tsi.toString(), ex);
        }
    }

    public synchronized void putAlertCodeToCache(RtuAlertCode rac) {
        try {
            alertCodeMap.put(new Integer(Integer.parseInt(rac.getCode(), 16)), rac);
        } catch (Exception ex) {
            log.debug("Error to put RtuAlertCode: " + rac.toString(), ex);
        }
    }

    public synchronized void putRemoteUpateRtuaToCache(String rtua) {
        try {
            rtuRemoteUpdateMap.put(new Integer((int) Long.parseLong(rtua, 16)), Boolean.valueOf(true));
        } catch (Exception ex) {
            log.debug("Error to put RemoteUpateRtua: " + rtua, ex);
        }
    }

    public boolean getRemoteUpateRtuaTag(String rtua) {
        if (rtuRemoteUpdateMap.get(new Integer((int) Long.parseLong(rtua, 16))) != null) {
            return ((Boolean) rtuRemoteUpdateMap.get(new Integer((int) Long.parseLong(rtua, 16)))).booleanValue();
        }
        return false;
    }

    public boolean getRemoteUpateRtuaTag(int rtua) {
        if (rtuRemoteUpdateMap.get(new Integer(rtua)) != null) {
            return ((Boolean) rtuRemoteUpdateMap.get(new Integer(rtua))).booleanValue();
        }
        return false;
    }

    public synchronized void clearRtuRemoteUpdateMap() {
        rtuRemoteUpdateMap.clear();
    }

    public SysConfig getSysConfig() {
        return sysConfig;
    }

    public void setSysConfig(SysConfig sysConfig) {
        sysConfig = sysConfig;
    }
}