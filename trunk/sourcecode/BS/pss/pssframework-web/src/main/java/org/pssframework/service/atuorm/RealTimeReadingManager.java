package org.pssframework.service.atuorm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.autorm.RealTimeReadingDao;
import org.pssframework.model.autorm.RealTimeReadingInfo;
import org.pssframework.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.bp.realinterface.ICollectInterface;
import pep.bp.realinterface.mto.AssistParam;
import pep.bp.realinterface.mto.CollectObject;
import pep.bp.realinterface.mto.CommandItem;
import pep.bp.realinterface.mto.MessageTranObject;
import cn.org.rapid_framework.generator.util.StringHelper;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Zhangyu
 * 
 */
@Service
public class RealTimeReadingManager extends BaseManager<RealTimeReadingInfo, Long> {
    @Autowired
    private RealTimeReadingDao realTimeReadingDao;

    @Autowired
    private ICollectInterface realTimeProxy376;

    @Override
    public EntityDao<?, ?> getEntityDao() {
        return this.realTimeReadingDao;
    }

    public Page<?> findByPageRequest(PageRequest<?> pagePara) {
        Page<?> page = realTimeReadingDao.findByPageRequest(pagePara);
        return page;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> readRealTimeData(String gpIds, Map<String, Object> commandItemMap, String dataDensity,
            String dataPoints, String dataTime, int fetchCount) throws ServiceException {
        // 设置超时时间
        // collectInterface.setTmOut(fetchCount * 3 * 2);
        Map<String, Object> map = new HashMap<String, Object>();
        if(commandItemMap != null) {
            Iterator<String> commandItemIterator = commandItemMap.keySet().iterator();
            String key = null;
            String value = null;
            String[] keys = null;
            String[] temps = null;
            String[] values = null;
            String protocolNo = null;
            String gpChar = null;
            String sAppId = null;
            String commanditemCodes = null;
            StringBuffer sbValidAppIds = new StringBuffer();
            while(commandItemIterator.hasNext()) {
                key = (String) commandItemIterator.next();
                value = (String) commandItemMap.get(key);
                keys = key.split("#");
                temps = value.split(";");
                if(keys.length == 2) {
                    protocolNo = keys[0];
                    gpChar = keys[1];
                }
                for(int i = 0; i < temps.length; i++) {
                    values = temps[i].split("#");
                    if(values.length == 2) {
                        sAppId = values[0];
                        commanditemCodes = values[1];
                    }
                    else {
                        sAppId = null;
                    }

                    boolean bSend = false;
                    List collectObjects = getCollectObjects(gpIds, protocolNo, gpChar);
                    // 添加辅助项
                    List<AssistParam> assistParamList = null; // 辅助项
                    if(isAddAssistParam(dataDensity, dataPoints, dataTime)) {
                        if(isStateGrid(protocolNo) && isSecClassData(commanditemCodes.split(","))) {
                            assistParamList = new ArrayList<AssistParam>();
                            setAssistParam(assistParamList, dataDensity, dataPoints, dataTime, protocolNo,
                                           commanditemCodes.split(","));
                        }
                    }
                    if(collectObjects != null && collectObjects.size() > 0 && sAppId != null) {
                        if(isStateGrid(protocolNo)) {
                            logger.info("collectInterface.readRealtimeData [ protocolNo : " + protocolNo
                                    + " ; appid : " + Long.parseLong(sAppId) + " ; commanditem : " + commanditemCodes
                                    + " ; object size : " + collectObjects.size() + "]");
                            try {
                                // bSend = collectInterface.readRealtimeData(collectObjects,
                                // commanditemCodes.split(","), assistParamList, Long.parseLong(sAppId));
                                // bSend = true;
                                if(bSend) {
                                    sbValidAppIds.append("," + sAppId);
                                }
                            }
                            catch(Exception _e) {
                                logger.info("错误信息：" + _e);
                                bSend = false;
                            }
                        }
                        else if(isZheGrid(protocolNo)) {
                            logger.info("collectInterface.readRealtimeDataZJ [ protocolNo : " + protocolNo
                                    + " ; appid : " + Long.parseLong(sAppId) + " ; commanditem : " + commanditemCodes
                                    + " ; object size : " + collectObjects.size() + "]");
                            try {
                                // bSend = collectInterface.readRealtimeDataZJ(collectObjects,
                                // commanditemCodes.split(","), assistParamList, Long.parseLong(sAppId));
                                // bSend = true;
                                if(bSend) {
                                    sbValidAppIds.append("," + sAppId);
                                }
                            }
                            catch(Exception _e) {
                                logger.info("错误信息：" + _e);
                                bSend = false;
                            }
                        }
                        else if("126".equals(protocolNo)) { // 集中器抄收测量点其他数据 - 广东集抄规约[126]
                            logger.info("collectInterface.gdConcRGOD [ protocolNo : " + protocolNo + " ; appid : "
                                    + Long.parseLong(sAppId) + " ; commanditem : " + commanditemCodes
                                    + " ; object size : " + collectObjects.size() + "]");
                            List<AssistParam> assistParams = new ArrayList();
                            AssistParam assistParam = new AssistParam();
                            assistParam.setKey("F01B");
                            assistParam.setValue(String.valueOf(commanditemCodes.split(",").length));
                            assistParams.add(assistParam);
                            logger.info("  length : " + String.valueOf(commanditemCodes.split(",").length));
                            try {
                                if(isIntPointData(commanditemCodes.split(","))) {
                                    // bSend =collectInterface.gdConcRFHD(collectObjects, commanditemCodes.split(","),
                                    // assistParams, Long.parseLong(sAppId));
                                }
                                else {
                                    // bSend =collectInterface.gdConcRGOD(collectObjects, commanditemCodes.split(","),
                                    // assistParams, Long.parseLong(sAppId));
                                }
                                // bSend = true;
                                if(bSend) {
                                    sbValidAppIds.append("," + sAppId);
                                }
                            }
                            catch(Exception _e) {
                                logger.info("错误信息：" + _e);
                                bSend = false;
                            }
                        }
                        else if("146".equals(protocolNo)) { // 广东变电站规约[146]
                            logger.info("collectInterface.readRealtimeData102 [ protocolNo : " + protocolNo
                                    + " ; appid : " + Long.parseLong(sAppId) + " ; commanditem : " + commanditemCodes
                                    + " ; object size : " + collectObjects.size() + "]");
                            List<CommandItem> commandItems = new ArrayList();
                            String[] commanditems = commanditemCodes.split(",");
                            for(int j = 0; j < commanditems.length; j++) {
                                CommandItem commandItem = new CommandItem();
                                commandItem.setIdentifier(commanditems[j]);
                                commandItems.add(commandItem);
                            }
                            List<AssistParam> assistParams = new ArrayList();
                            AssistParam assistParam = new AssistParam();
                            assistParam.setKey("RAD");
                            assistParam.setValue("40");
                            assistParams.add(assistParam);
                            try {
                                // bSend = collectInterface.readRealtimeData102(collectObjects, commandItems,
                                // assistParams, Long.parseLong(sAppId));
                                // bSend = true;
                                if(bSend) {
                                    sbValidAppIds.append("," + sAppId);
                                }
                            }
                            catch(Exception _e) {
                                logger.info("错误信息：" + _e);
                                bSend = false;
                            }
                        }
                    }
                }
                if(sbValidAppIds.length() > 0) {
                    map.put("VALID_APPIDS", sbValidAppIds.toString().substring(1));
                }
            }
            // if(bSend) { //发送成功
            // return getReadMessages(collectObjects, "正在召测...");
            // }
            // else { //发送失败
            // return getReadMessages(collectObjects, "下发失败...");
            // }
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public Map gdConcRTMR(String gpIds, Map commandItemMap, int fetchCount) throws ServiceException {
        // TODO Auto-generated method stub
        // 设置超时时间
        // collectInterface.setTmOut(fetchCount * 3 * 2);
        if(commandItemMap != null) {
            Iterator commandItemIterator = commandItemMap.keySet().iterator();
            String key = null;
            String value = null;
            String[] keys = null;
            String[] values = null;
            String protocolNo = null;
            //String gpChar = null;
            String sAppId = null;
            String commanditemCodes = null;
            while(commandItemIterator.hasNext()) {
                key = (String) commandItemIterator.next();
                value = (String) commandItemMap.get(key);
                keys = key.split("#");
                values = value.split("#");
                if(keys.length == 2) {
                    protocolNo = keys[0];
                    //gpChar = keys[1];
                }
                if(values.length == 2) {
                    sAppId = values[0];
                    commanditemCodes = values[1];
                }

                //boolean bSend = false;
                List concentratorObjects = null;// getConcentratorObjects(gpIds, protocolNo, gpChar);
                if(concentratorObjects != null && concentratorObjects.size() > 0) {
                    if("126".equals(protocolNo)) { // 广东集抄规约[126]
                        logger.info("collectInterface.gdConcRTMR [ protocolNo : " + protocolNo + " ; appid : "
                                + Long.parseLong(sAppId) + " ; commanditem : " + commanditemCodes + " ; object size : "
                                + concentratorObjects.size() + "]");
                        try {
                            // bSend = collectInterface.gdConcRTMR(concentratorObjects, commanditemCodes.split(","),
                            // null, Long.parseLong(sAppId));
                            // bSend = true;
                        }
                        catch(Exception _e) {
                            //bSend = false;
                        }
                    }
                }

            }
            // if(bSend) { //发送成功
            // return getReadMessages(collectObjects, "正在召测...");
            // }
            // else { //发送失败
            // return getReadMessages(collectObjects, "下发失败...");
            // }
        }
        return null;
    }

    /**
     * 
     * @param gpIds
     * @param protocolNo
     * @param gpChar
     * @return
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    public List getCollectObjects(String gpIds, String protocolNo, String gpChar) throws ServiceException {
        logger.info("protocolNo   : " + protocolNo);
        logger.info("gpChar       : " + gpChar);
        logger.info("gpIds       : " + (gpIds != null && !"".equals(gpIds.trim()) ? gpIds.trim() : "-1"));
        //String sqlCode = "AL_AUTORM_1002";
        Map paramsPage = new HashMap();
        paramsPage.put("protocolNo", protocolNo);
        paramsPage.put("gpChar", gpChar);
        paramsPage.put("gpIds", (gpIds != null && !"".equals(gpIds.trim()) ? gpIds.trim() : "-1"));
        List listCollectObject = null;// = sqlManager.getObjectsList(sqlCode, paramsPage, 0, Integer.MAX_VALUE);
        if(listCollectObject != null) {
            logger.info("listCollectObject size       : " + listCollectObject.size());
            List collectObjects = new ArrayList();
            Iterator iterator = listCollectObject.iterator();
            Map map = new TreeMap();
            Object[] os = null;
            Object oLogicalAddr = null;
            Object oGpSn = null;
            Object oPwAlgorigth = null;
            Object oPwContent = null;
            String gpSns = null;
            while(iterator.hasNext()) {
                os = (Object[]) iterator.next();
                oLogicalAddr = os[0];
                oGpSn = os[1];
                oPwAlgorigth = os[2];
                oPwContent = os[3];
                if(oLogicalAddr != null && oGpSn != null) {
                    gpSns = (String) map.get(oLogicalAddr.toString());
                    if(gpSns == null) {
                        gpSns = oGpSn.toString();
                    }
                    else {
                        gpSns = gpSns.split("#")[0] + "," + oGpSn.toString();
                    }
                    map.put(oLogicalAddr.toString(), gpSns + "#"
                            + (oPwAlgorigth != null ? oPwAlgorigth.toString() : "") + "#"
                            + (oPwContent != null ? oPwContent.toString() : ""));
                }
            }

            Iterator itMapKeySet = map.keySet().iterator();
            String sLogicalAddr = null;
            String sTemp = null;
            String[] sTemps = null;
            String sGpSns = null;
            String sPwAlgorigth = null;
            String sPwContent = null;
            while(itMapKeySet.hasNext()) {
                if("126".equals(protocolNo) || "146".equals(protocolNo)) {
                    sLogicalAddr = (String) itMapKeySet.next();
                    sTemp = (String) map.get(sLogicalAddr);
                    sTemps = sTemp.split("#");
                    if(sTemps.length >= 3) {
                        sGpSns = sTemps[0];
                        sPwAlgorigth = sTemps[1];
                        sPwContent = sTemps[2];
                    }
                    else if(sTemps.length == 2) {
                        sGpSns = sTemps[0];
                        sPwAlgorigth = sTemps[1];
                        sPwContent = "";
                    }
                    else if(sTemps.length == 1) {
                        sGpSns = sTemps[0];
                        sPwAlgorigth = "";
                        sPwContent = "";
                    }
                    /*
                     * int[] mpSn = new int[sGpSns.split(",").length]; for(int i = 0; i < mpSn.length; i++) { mpSn[i] =
                     * Integer.parseInt(sGpSns.split(",")[i]); logger.info("collectObject.setLogicalAddr   : " +
                     * sLogicalAddr); logger.info("collectObject.setEquipProtocol : " + protocolNo);
                     * logger.info("collectObject.setMpExpressMode : " + Constant.MP_EXPRESSMODE_BLOCK);
                     * logger.info("collectObject.setPwAlgorith    : " + sPwAlgorigth);
                     * logger.info("collectObject.setPwContent     : " + sPwContent);
                     * logger.info("collectObject.setMpSn          : " + mpSn[i]); CollectObject collectObject = new
                     * CollectObject(); collectObject.setLogicalAddr(sLogicalAddr);
                     * collectObject.setEquipProtocol(protocolNo); collectObject.setPwAlgorith(sPwAlgorigth);
                     * collectObject.setPwContent(sPwContent);
                     * collectObject.setMpExpressMode(Constant.MP_EXPRESSMODE_BLOCK); collectObject.setMpSn(new
                     * int[]{mpSn[i],1}); collectObjects.add(collectObject); }
                     */
                    int[] mpSn = new int[sGpSns.split(",").length];
                    for(int i = 0; i < mpSn.length; i++) {
                        mpSn[i] = Integer.parseInt(sGpSns.split(",")[i]);
                    }

                    CollectObject collectObject = new CollectObject();
                    collectObject.setLogicalAddr(sLogicalAddr);
                    collectObject.setEquipProtocol(protocolNo);
                    collectObject.setPwAlgorith(sPwAlgorigth);
                    collectObject.setPwContent(sPwContent);
                    // collectObject.setMpExpressMode(Constant.MP_EXPRESSMODE_BLOCK);
                    collectObject.setMpSn(new int[] { mpSn[0], mpSn[mpSn.length - 1] });
                    collectObjects.add(collectObject);
                }
                else {
                    sLogicalAddr = (String) itMapKeySet.next();
                    sTemp = (String) map.get(sLogicalAddr);
                    sTemps = sTemp.split("#");
                    if(sTemps.length >= 3) {
                        sGpSns = sTemps[0];
                        sPwAlgorigth = sTemps[1];
                        sPwContent = sTemps[2];
                    }
                    else if(sTemps.length == 2) {
                        sGpSns = sTemps[0];
                        sPwAlgorigth = sTemps[1];
                        sPwContent = "";
                    }
                    else if(sTemps.length == 1) {
                        sGpSns = sTemps[0];
                        sPwAlgorigth = "";
                        sPwContent = "";
                    }
                    int[] mpSn = new int[sGpSns.split(",").length];
                    for(int i = 0; i < mpSn.length; i++) {
                        mpSn[i] = Integer.parseInt(sGpSns.split(",")[i]);
                    }
                    logger.info("collectObject.setLogicalAddr   : " + sLogicalAddr);
                    logger.info("collectObject.setEquipProtocol : " + protocolNo);
                    // logger.info("collectObject.setMpExpressMode : " + Constant.MP_EXPRESSMODE_BLOCK);
                    logger.info("collectObject.setPwAlgorith    : " + sPwAlgorigth);
                    logger.info("collectObject.setPwContent     : " + sPwContent);
                    logger.info("collectObject.setMpSn          : " + sGpSns);
                    CollectObject collectObject = new CollectObject();
                    collectObject.setLogicalAddr(sLogicalAddr);
                    collectObject.setEquipProtocol(protocolNo);
                    collectObject.setPwAlgorith(sPwAlgorigth);
                    collectObject.setPwContent(sPwContent);
                    // collectObject.setMpExpressMode(Constant.MP_EXPRESSMODE_LIST);
                    collectObject.setMpSn(mpSn);
                    collectObjects.add(collectObject);
                }
            }
            return collectObjects;
        }
        return null;
    }

    /*
     * public List getConcentratorObjects(String gpIds, String protocolNo, String gpChar) throws ServiceException {
     * logger.info("protocolNo   : " + protocolNo); logger.info("gpChar       : " + gpChar);
     * logger.info("gpIds       : " + (gpIds != null && !"".equals(gpIds.trim()) ? gpIds.trim() : "-1")); String sqlCode
     * = "AL_AUTORM_2002"; Map paramsPage = new HashMap(); paramsPage.put("protocolNo", protocolNo);
     * paramsPage.put("gpChar", gpChar); paramsPage.put("gpIds", (gpIds != null && !"".equals(gpIds.trim()) ?
     * gpIds.trim() : "-1")); List listConcentratorObjects = null;//sqlManager.getObjectsList(sqlCode, paramsPage, 0,
     * Integer.MAX_VALUE); if (listConcentratorObjects != null) { logger.info("listConcentratorObjects size       : " +
     * listConcentratorObjects.size()); List concentratorObjects = new ArrayList(); Iterator iterator =
     * listConcentratorObjects.iterator(); Map map = new TreeMap(); Object[] os = null; Object oLogicalAddr = null;
     * Object oGpSn = null; Object oGpAddr = null; Object oRelayType = null; Object oRelayAddr1 = null; Object
     * oRelayAddr2 = null; Object oRelayAddr3 = null; Object oRelayAddr4 = null; //String gpSns = null; List<ConcMeter>
     * meterObjects = null; while (iterator.hasNext()) { os = (Object[]) iterator.next(); oLogicalAddr = os[0]; oGpSn =
     * os[1]; oGpAddr = os[2]; oRelayType = os[3]; oRelayAddr1 = os[4]; oRelayAddr2 = os[5]; oRelayAddr3 = os[6];
     * oRelayAddr4 = os[7]; if (oLogicalAddr != null && oGpSn != null) { //gpSns = (String)
     * map.get(oLogicalAddr.toString()); meterObjects = (List<ConcMeter>) map.get(oLogicalAddr.toString()); if
     * (meterObjects == null) { //gpSns = oGpSn.toString(); meterObjects = new ArrayList(); } if (oGpAddr != null &&
     * oGpSn != null) { ConcMeter concMeter = new ConcMeter(); concMeter.setMeterAddr(oGpAddr.toString());
     * concMeter.setMeterSn(Integer.parseInt(oGpSn.toString())); concMeter.setForwardCnt(Integer.parseInt(oRelayType !=
     * null && oRelayType.toString().trim().length() > 0 ? oRelayType.toString() : "255"));
     * concMeter.setForwardMeter1(oRelayAddr1 != null && oRelayAddr1.toString().trim().length() > 0 ?
     * DataConverter.fillTops(oRelayAddr1.toString(), 12, 'F') : "FFFFFFFFFFFF"); concMeter.setForwardMeter2(oRelayAddr2
     * != null && oRelayAddr2.toString().trim().length() > 0 ? DataConverter.fillTops(oRelayAddr2.toString(), 12, 'F') :
     * "FFFFFFFFFFFF"); concMeter.setForwardMeter3(oRelayAddr3 != null && oRelayAddr3.toString().trim().length() > 0 ?
     * DataConverter.fillTops(oRelayAddr3.toString(), 12, 'F') : "FFFFFFFFFFFF"); concMeter.setForwardMeter4(oRelayAddr4
     * != null && oRelayAddr4.toString().trim().length() > 0 ? DataConverter.fillTops(oRelayAddr4.toString(), 12, 'F') :
     * "FFFFFFFFFFFF"); meterObjects.add(concMeter); } map.put(oLogicalAddr.toString(), meterObjects); } }
     * 
     * Iterator itMapKeySet = map.keySet().iterator(); String sLogicalAddr = null; meterObjects = null; while
     * (itMapKeySet.hasNext()) { if ("126".equals(protocolNo)) { sLogicalAddr = (String) itMapKeySet.next();
     * meterObjects = (List<ConcMeter>) map.get(sLogicalAddr); Concentrator concentratorObject = new Concentrator();
     * concentratorObject.setLogicalAddr(sLogicalAddr); concentratorObject.setEquipProtocol(protocolNo);
     * concentratorObject.setMeterObjects(meterObjects); concentratorObjects.add(concentratorObject); } } return
     * concentratorObjects; } return null; }
     */

    @SuppressWarnings("unchecked")
    public Map getReturnByRRTD(Long[] appIds) throws ServiceException {
        return getReturnByRRTD(appIds, null);
    }

    @SuppressWarnings("unchecked")
    public Map getReturnByRRTD(Long[] appIdArray, String fetchCount) throws ServiceException {
        Map result = new HashMap();
        if(appIdArray != null) {
            for(int i = 0; i < appIdArray.length; i++) {
                try {
                    Map mapReturn = null;
                    logger.debug("------ collectInterface.getReturnByRRTD ------");
                    try {
                        mapReturn = realTimeProxy376.getReturnByReadData(appIdArray[i]);
                    }
                    catch(Exception _e) {
                        logger.info("错误信息：" + _e);
                        mapReturn = null;
                    }

                    //
                    // Map testValueMap1 = new HashMap();
                    // testValueMap1.put("20100309000000", "0.0001");
                    // testValueMap1.put("20100309001500", "0.0002");
                    // testValueMap1.put("20100309003000", "0.0003");
                    // testValueMap1.put("20100309010000", "0.0004");
                    // testValueMap1.put("20100309011500", "0.0005");
                    // testValueMap1.put("20100309013000", "0.0006");
                    // testValueMap1.put("20100309014500", "0.0007");
                    // testValueMap1.put("20100309020000", "0.0008");
                    // testValueMap1.put("20100309021500", "0.0009");
                    // testValueMap1.put("20100309023000", "0.0010");
                    // mapReturn.put("96123456#5#100C0025#2101", testValueMap1);
                    // mapReturn.put("96123456#5#100C0025#2102", testValueMap1);
                    // mapReturn.put("96123456#5#100C0025#2201", testValueMap1);
                    // mapReturn.put("96123456#5#100C0025#2202", testValueMap1);

                    if(mapReturn != null) {
                        Iterator iterator = mapReturn.keySet().iterator();
                        while(iterator.hasNext()) {
                            String key = null;
                            // HashMap value = null;
                            TreeMap value = null;
                            key = (String) iterator.next();
                            value = (TreeMap) mapReturn.get(key);
                            Iterator valueiterator = value.keySet().iterator();
                            logger.info("  [key, valuemap] : " + "[" + key + " , " + value + "]");
                            String[] keys = key.split("#");
                            if(keys.length == 4) {
                                key = keys[0] + "_" + keys[1] + "_" + keys[3];
                                while(valueiterator.hasNext()) {
                                    String sresult = null;
                                    String timekey = null;
                                    timekey = (String) valueiterator.next();
                                    sresult = (String) value.get(timekey);
                                    result.put(key, sresult);
                                    logger.info("    [[key, timekey, value]] : " + "[" + key + " , " + timekey + " , "
                                            + sresult + "]");
                                }
                            }
                        }
                        // 释放MAP
                        mapReturn = null;
                    }
                }
                catch(NumberFormatException e) {
                    logger.info("数据格式错误信息：" + e);
                }
                catch(Exception e) {
                    logger.info("错误信息：" + e);
                }
            }
        }
        return result;
    }

    /*
     * public int getFetchCount(String sysObject) throws ServiceException { int fetchCount = 0; if
     * ("1".equals(sysObject)) { // 专变用户 SParameterId id = new SParameterId(); id.setParamName("FETCH_NUM_PG");
     * id.setOrgNo("0"); try { SParameter parameter = parameterDao.load(id); fetchCount =
     * Integer.parseInt(parameter.getParamValue()); } catch (Exception _de) {
     * logger.error("getFetchCount FETCH_NUM_PG error! " + _de.getMessage()); } } else if ("2".equals(sysObject)) { //
     * 配变台区 SParameterId id = new SParameterId(); id.setParamName("FETCH_NUM_TG"); id.setOrgNo("0"); try { SParameter
     * parameter = parameterDao.load(id); fetchCount = Integer.parseInt(parameter.getParamValue()); } catch (Exception
     * _de) { logger.error("getFetchCount FETCH_NUM_TG error! " + _de.getMessage()); } } else if ("3".equals(sysObject))
     * { // 低压用户 SParameterId id = new SParameterId(); id.setParamName("FETCH_NUM_CUST"); id.setOrgNo("0"); try {
     * SParameter parameter = parameterDao.load(id); fetchCount = Integer.parseInt(parameter.getParamValue()); } catch
     * (Exception _de) { logger.error("getFetchCount FETCH_NUM_CUST error! " + _de.getMessage()); } } else if
     * ("4".equals(sysObject)) { // 变电站 SParameterId id = new SParameterId(); id.setParamName("FETCH_NUM_SUBS");
     * id.setOrgNo("0"); try { SParameter parameter = parameterDao.load(id); fetchCount =
     * Integer.parseInt(parameter.getParamValue()); } catch (Exception _de) {
     * logger.error("getFetchCount FETCH_NUM_SUBS error! " + _de.getMessage()); } } else { SParameterId id = new
     * SParameterId(); id.setParamName("FETCH_NUM_PG"); id.setOrgNo("0"); try { SParameter parameter =
     * parameterDao.load(id); fetchCount = Integer.parseInt(parameter.getParamValue()); } catch (Exception _de) {
     * logger.error("getFetchCount FETCH_NUM_PG error! " + _de.getMessage()); } } return fetchCount; }
     */

    /**
     * @param collectObjects
     *            : List<CollectObject>
     * @param message
     * @return
     */
    @SuppressWarnings( { "unchecked", "unused" })
    private Map getReadMessages(List collectObjects, String message) {
        if(collectObjects != null) {
            Map map = new HashMap();
            Iterator iterator = collectObjects.iterator();
            CollectObject collectObject = null;
            String logicalAddr = null;
            int[] mpSn = null;
            while(iterator.hasNext()) {
                collectObject = (CollectObject) iterator.next();
                logicalAddr = collectObject.getLogicalAddr();
                mpSn = collectObject.getMpSn();
            }
            return null;
        }
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    public Map getCurveReturnByRRTD(String appIds, String fetchCount, String timeData, String dataGap, String points,
            String proNo) throws ServiceException {
        Map result = new HashMap();
        if(appIds != null) {
            String[] appIdArray = appIds.split(",");
            for(int i = 0; i < appIdArray.length; i++) {
                try {
                    Map mapReturn = null;
                    logger.info("------ realTimeProxy376.getCurveReturnByRRTD ------");
                    try {
                        mapReturn = realTimeProxy376.getReturnByReadData(Long.parseLong(appIdArray[i]));
                        // 测试数据
                        // Map testValueMap1 = new HashMap();
                        // testValueMap1.put("20100309000000", "0.0001");
                        // testValueMap1.put("20100309001500", "0.0002");
                        // testValueMap1.put("20100309003000", "0.0003");
                        // testValueMap1.put("20100309010000", "0.0004");
                        // testValueMap1.put("20100309011500", "0.0005");
                        // testValueMap1.put("20100309013000", "0.0006");
                        // testValueMap1.put("20100309014500", "0.0007");
                        // testValueMap1.put("20100309020000", "0.0008");
                        // testValueMap1.put("20100309021500", "0.0009");
                        // testValueMap1.put("20100309023000", "0.0010");
                        // mapReturn.put("8D013099#0#2000A01F#DI_1101", testValueMap1);
                        // mapReturn.put("8D013099#0#2000A01F#DI_1100", testValueMap1);
                        // mapReturn.put("8D013099#1#2000A01F#DI_1101", testValueMap1);
                        // mapReturn.put("8D013099#1#2000A01F#DI_1100", testValueMap1);
                    }
                    catch(Exception _e) {
                        logger.info("错误信息 ：" + _e);
                        mapReturn = null;
                    }
                    if(mapReturn != null) {
                        Iterator iterator = mapReturn.keySet().iterator();
                        while(iterator.hasNext()) {
                            String key = null;
                            HashMap value = null;
                            key = (String) iterator.next();
                            value = (LinkedHashMap) resultMapSort((HashMap) mapReturn.get(key));
                            logger.info("-----------------接口返回的结果---------------");
                            logger
                                    .info("  [key, valuemap] : " + "[" + key + " , " + (HashMap) mapReturn.get(key)
                                            + "]");
                            String[] keys = key.split("#");
                            if(keys.length == 4) {
                                key = keys[0] + "_" + keys[1] + "_" + keys[2] + "_" + keys[3];
                                result.put(key, valueConver(value, keys[2], proNo));
                            }
                        }
                        // 释放MAP
                        mapReturn = null;
                    }
                }
                catch(NumberFormatException e) {
                    logger.info("错误信息：" + e);
                }
                catch(Exception e) {
                    logger.info("错误信息：" + e);
                }
            }
        }
        return result;
    }

    /**
     * 设置辅助项
     * 
     * @param assistParams
     * @param dataDensity
     * @param dataPoints
     * @param dataTime
     */
    private void setAssistParam(List<AssistParam> assistParams, String dataDensity, String dataPoints, String dataTime,
            String protocolNo, String[] commanditemCodes) {
        AssistParam assistParam1 = new AssistParam();// 数据密度
        assistParam1.setKey("F002");
        assistParam1.setValue(dataDensity);
        AssistParam assistParam2 = new AssistParam();// 数据点数
        assistParam2.setKey("F003");
        assistParam2.setValue(dataPoints);
        AssistParam assistParam3 = new AssistParam();// 数据时间
        assistParam3.setKey(getDataTimeType(commanditemCodes, protocolNo));
        assistParam3.setValue(dataTime);
        assistParams.add(assistParam1);
        assistParams.add(assistParam2);
        assistParams.add(assistParam3);
    }

    /**
     * 给接口数据按时间排序
     * 
     * @param valueMap
     * @return
     */
    private LinkedHashMap<String, String> resultMapSort(HashMap<String, String> valueMap) {
        LinkedHashMap<String, String> resultMap = new LinkedHashMap<String, String>();
        Object[] keys = (Object[]) valueMap.keySet().toArray();
        Arrays.sort(keys); // KEY值排序
        for(int i = 0; i < keys.length; i++) {
            resultMap.put((String) keys[i], valueMap.get(keys[i]));
        }
        return resultMap;
    }

    /**
     * 把返回的MAP数据转成String
     * 
     * @param valueMap
     * @return
     */
    private String valueConver(Map<String, String> valueMap, String commd, String proNo) {
        StringBuffer value = new StringBuffer();
        if(valueMap != null) {
            //Iterator iterator = valueMap.keySet().iterator();
            //while(iterator.hasNext()) {
            //String key = (String) iterator.next();
                //String val = (String) valueMap.get(key);
                //String groupRm = getGroupRmByCommdAndProNo(commd, proNo);
                /*
                 * if (DATAGROUP_RM_CURVE.equals(groupRm) || DATAGROUP_RM_HOUR.equals(groupRm)) { //曲线数据或小时数据
                 * value.append(ToolHelper.stringToDateFormat(key, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss") + " = " +
                 * (("-1".equals(val)) ? "无效值" : val) + " <br/>"); } else { value.append((("-1".equals(val)) ? "无效值" :
                 * val) + " <br/>"); }
                 */
            //}
        }
        return value.toString();
    }

    /**
     * 是否需要添加辅助项
     * 
     * @param dataDensity
     * @param dataPoints
     * @param dataTime
     * @return
     */
    private boolean isAddAssistParam(String dataDensity, String dataPoints, String dataTime) {
        boolean result = false;
        if(!StringHelper.isBlank(dataPoints) && !StringHelper.isBlank(dataDensity) && !StringHelper.isBlank(dataTime)) {
            result = true;
        }
        return result;
    }

    /**
     * 判断是否是国网
     * 
     * @param protocolNo
     * @return
     */
    private boolean isStateGrid(String protocolNo) {
        boolean result = false;
        if("100".equals(protocolNo) || "101".equals(protocolNo) || "102".equals(protocolNo) || "106".equals(protocolNo)) {
            result = true;
        }
        return result;
    }

    /**
     * 判断是否是浙网
     * 
     * @param protocolNo
     * @return
     */
    private boolean isZheGrid(String protocolNo) {
        boolean result = false;
        if("120".equals(protocolNo) || "121".equals(protocolNo) || "122".equals(protocolNo) || "123".equals(protocolNo)
                || "124".equals(protocolNo) || "125".equals(protocolNo) || "127".equals(protocolNo)
                || "129".equals(protocolNo)) {
            result = true;
        }
        return result;
    }

    /**
     * 判断是否2类数据(只有国网有)
     * 
     * @param commanditemCodes
     * @return
     */
    private boolean isSecClassData(String[] commanditemCodes) {
        boolean result = false;
        if(commanditemCodes != null) {
            String commd = commanditemCodes[0];
            logger.info("命令项：" + commd);
            if(commd.contains("D")) {
                result = true;
                logger.info("是2类数据");
            }
        }
        return result;
    }

    /**
     * 得到命令项对应的时间标识
     * 
     * @param commanditemCodes
     * @param proNo
     * @return
     */
    @SuppressWarnings("unchecked")
    private String getDataTimeType(String[] commanditemCodes, String proNo) {
        String result = "";
        try {
            if(commanditemCodes != null) {
                String commd = commanditemCodes[0];
                Map paramsPage = new HashMap();
                paramsPage.put("commCode", commd);
                paramsPage.put("proNo", proNo);
                //String sqlCode = "AL_AUTORM_3001";
                //List resultList = null;// sqlManager.getObjectsList(sqlCode, paramsPage, 0, 1);
                //for(Iterator iterator = resultList.iterator(); iterator.hasNext();) {
                //    Object[] object = (Object[]) iterator.next();
                //    String dataRm = (String) object[0];
                    /*
                     * if (DATAGROUP_RM_DAY.equals(dataRm)) { //日冻结 result = "F012"; } else if
                     * (DATAGROUP_RM_MONTH.equals(dataRm)) { //月冻结 result = "F013"; } else { result = "F011"; }
                     */
                //}
            }
        }
        catch(Exception e) {
            logger.info("错误信息：" + e);
        }
        logger.info("命令项对应的时间标识：" + result);
        return result;
    }

    /**
     * 根据命令项得到分组号
     * 
     * @param commd
     * @param proNo
     * @return
     */
    @SuppressWarnings( { "unused", "unchecked" })
    private String getGroupRmByCommdAndProNo(String commd, String proNo) {
        String result = "";
        try {
            if(commd != null) {
                Map paramsPage = new HashMap();
                paramsPage.put("commCode", commd);
                paramsPage.put("proNo", proNo);
                String sqlCode = "AL_AUTORM_3001";
                //List resultList = null; // sqlManager.getObjectsList(sqlCode, paramsPage, 0, 1);
                //for(Iterator iterator = resultList.iterator(); iterator.hasNext();) {
                //    Object[] object = (Object[]) iterator.next();
                //    String dataRm = (String) object[0];
                //    result = dataRm;
                //}
            }
        }
        catch(Exception e) {
            logger.info("错误信息：" + e);
        }
        logger.info("命令项对应的分组号：" + result);
        return result;
    }

    /**
     * 根据命令项判断是否是用户整点数据(只用在低压集抄)
     * 
     * @param commanditemCodes
     * @return
     */
    private boolean isIntPointData(String[] commanditemCodes) {
        boolean result = false;
        if(commanditemCodes != null) {
            String commd = commanditemCodes[0];
            String commdTemp = commd.substring(2, 4);
            if("12".equals(commdTemp)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 实时召测下发
     * 
     * @param mto_376
     * @return
     * @throws Exception
     */
    public long send(MessageTranObject mto_376) throws Exception {
        return this.realTimeProxy376.readData(mto_376);
    }
}
