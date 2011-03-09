package org.pssframework.controller.mobile;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.archive.GpInfo;
import org.pssframework.model.archive.PsInfo;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.model.archive.TranInfo;
import org.pssframework.model.mobile.LeakageProtectorParameterObject;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.service.archive.PsInfoManger;
import org.pssframework.service.archive.TgInfoManager;
import org.pssframework.service.system.CodeInfoManager;
import org.pssframework.util.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import pep.bp.realinterface.ICollectInterface;
import pep.bp.realinterface.mto.MessageTranObject;

/**
 * 
 * @author Nick
 * 
 */
@Controller
@RequestMapping("/mobile/lps")
public class LeakageProtectorParameterSettingController extends BaseSpringController {
    private static final String VIEW_NAME = "/mobile/lps";

    @Autowired
    private TgInfoManager tgInfoManager;

    @Autowired
    private PsInfoManger psInfoManger;

    @Autowired
    private CodeInfoManager codeInfoManager;

    @Autowired
    private ICollectInterface realTimeProxy376;

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping
    public ModelAndView index(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME);
        Map mapRequest = WebUtils.getParametersStartingWith(request, "");

        try {
            Long psId = Long.valueOf((String) mapRequest.get("psId"));

            PsInfo psInfo = psInfoManger.getById(psId);
            mav.addObject("psInfo", psInfo);

            GpInfo gpInfo = psInfo.getGpInfo();
            TranInfo tranInfo = null;
            if(gpInfo != null) {
                Long tgId = gpInfo.getObjectId();
                if(tgId != null) {
                    tranInfo = getTranInfo(tgId);
                }
            }
            if(tranInfo == null) {
                tranInfo = new TranInfo();
            }
            mav.addObject("tranInfo", tranInfo);

            mav.addObject("psModel", codeInfoManager.getCodeInfo("PS_MODEL", psInfo.getModelCode()));
            mav.addObject("commModeGm", codeInfoManager.getCodeInfo("COMM_MODE_GM", psInfo.getCommModeGm()));
            mav.addObject("psType", codeInfoManager.getCodeInfo("PS_TYPE", psInfo.getPsType()));
            logger.info("mapRequest : " + mapRequest.toString());
        }
        catch(Exception _e) {
            logger.error("", _e.fillInStackTrace());
        }
        return mav;
    }

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/r")
    public ModelAndView _reading(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME);
        Map mapRequest = WebUtils.getParametersStartingWith(request, "");
        logger.info("mapRequest : " + mapRequest.toString());
        String sPsId = (String) mapRequest.get("psId");
        if(sPsId != null) {
            Long psId = Long.parseLong(sPsId);
            PsInfo psInfo = psInfoManger.getById(psId);
            mav.addObject("psInfo", psInfo);
            mav.addObject("psModel", codeInfoManager.getCodeInfo("PS_MODEL", psInfo.getModelCode()));
            mav.addObject("psType", codeInfoManager.getCodeInfo("PS_TYPE", psInfo.getPsType()));

            StringBuffer sb_dto = new StringBuffer();
            sb_dto.append("{");
            sb_dto.append("\"collectObjects_Transmit\":").append("[{");
            sb_dto.append("\"terminalAddr\":\"" + psInfo.getTerminalInfo().getLogicalAddr() + "\"").append(",");
            sb_dto.append("\"equipProtocol\":\"" + psInfo.getTerminalInfo().getProtocolNo() + "\"").append(",");
            sb_dto.append("\"meterAddr\":\"" + psInfo.getGpInfo().getGpAddr() + "\"").append(",");
            sb_dto.append("\"meterType\":\"" + "100" + "\"").append(",");
            sb_dto.append("\"funcode\":\"1\"").append(",");
            sb_dto.append("\"port\":\"" + "1" + "\"").append(",");
            sb_dto.append("\"serialPortPara\":").append("{");
            sb_dto.append("\"baudrate\":\"" + "110" + "\"").append(",");
            sb_dto.append("\"stopbit\":\"" + "1" + "\"").append(",");
            sb_dto.append("\"checkbit\":\"" + "0" + "\"").append(",");
            sb_dto.append("\"odd_even_bit\":\"" + "1" + "\"").append(",");
            sb_dto.append("\"databit\":\"" + "8" + "\"");
            sb_dto.append("}").append(",");
            sb_dto.append("\"waitforPacket\":\"" + "10" + "\"").append(",");
            sb_dto.append("\"waitforByte\":\"" + "5" + "\"").append(",");
            sb_dto.append("\"commandItems\":").append("[").append("{");
            sb_dto.append("\"identifier\":").append("\"8000C04F\"");
            sb_dto.append("}").append("]");
            sb_dto.append("}]");
            sb_dto.append("}");
            String dtoJSONString = sb_dto.toString();
            String mtoType = psInfo.getTerminalInfo().getProtocolNo();
            MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(mtoType, dtoJSONString);
            long collectId = realTimeProxy376.transmitMsg(mto);

            Map<String, Map<String, String>> resultMap = null;
            int i = 5;
            while(i > 0
                    && (resultMap == null || (resultMap != null && !resultMap.containsKey(psInfo.getTerminalInfo()
                            .getLogicalAddr()
                            + "#"
                            + fillTopsMeterAddr(psInfo.getGpInfo().getGpAddr())
                            + "#"
                            + "8000C04F")))) {
                try {
                    Thread.sleep(3 * 1000);
                }
                catch(InterruptedException _ie) {
                }
                resultMap = realTimeProxy376.readTransmitPara(collectId);
                i--;
            }

            LeakageProtectorParameterObject lppo = new LeakageProtectorParameterObject();
            if(resultMap != null) {
                Map result = resultMap.get(psInfo.getTerminalInfo().getLogicalAddr() + "#"
                        + fillTopsMeterAddr(psInfo.getGpInfo().getGpAddr()) + "#" + "8000C04F");
                if(result != null) {
                    lppo.setTsStatus((String) result.get("8000C04F01"));            // 分合状态
                    lppo.setIsAtresia((String) result.get("8000C04F02"));           // 是否闭锁
                    lppo.setPhase((String) result.get("8000C04F03"));               // 相位
                    lppo.setActionType((String) result.get("8000C04F04"));          // 动作类型
                    lppo.setRlcGearValue((String) result.get("8000C04F05"));        // 额定负载电流档位值
                    lppo.setRcGear((String) result.get("8000C04F06"));              // 剩余电流档位
                    lppo.setRcGearValue((String) result.get("8000C04F07"));         // 剩余电流当前档位值
                    lppo.setCbdGear((String) result.get("8000C04F08"));             // 漏电分断延迟档位
                    // lppo.setCbdGearValue((String) result.get("8000C04F09"));     // 漏电分断延迟时间值
                    lppo.setFuncSetupBytes((String) result.get("8000C04F10"));      // 开关功能设定字
                    lppo.setLpModelId((String) result.get("8000C04F11"));           // 保护器型号ID

                    if("1".equals(lppo.getLpModelId()) || "3".equals(lppo.getLpModelId())
                            || "5".equals(lppo.getLpModelId()) || "7".equals(lppo.getLpModelId())) {
                        if("1".equals(lppo.getCbdGear())) {
                            lppo.setCbdGearValue("200");
                        }
                        else {
                            lppo.setCbdGearValue("300");
                        }
                    }
                    else if("2".equals(lppo.getLpModelId()) || "4".equals(lppo.getLpModelId())
                            || "6".equals(lppo.getLpModelId()) || "8".equals(lppo.getLpModelId())) {
                        if("1".equals(lppo.getCbdGear())) {
                            lppo.setCbdGearValue("300");
                        }
                        else {
                            lppo.setCbdGearValue("500");
                        }
                    }
                    else if("101".equals(lppo.getLpModelId())) {
                        if("1".equals(lppo.getCbdGear())) {
                            lppo.setCbdGearValue("200");
                        }
                        else {
                            lppo.setCbdGearValue("500");
                        }
                    }

                    if(lppo.getFuncSetupBytes() != null && lppo.getFuncSetupBytes().length() == 8) {
                        for(int n = 1; n <= 8; n++) {
                            if(lppo.getFuncSetupBytes().charAt(n - 1) == '1') {
                                if(n == 1) {
                                    lppo.setFuncSetupByte1("1");
                                }
                                else if(n == 2) {
                                    lppo.setFuncSetupByte2("1");
                                }
                                else if(n == 3) {
                                    lppo.setFuncSetupByte3("1");
                                }
                                else if(n == 4) {
                                    lppo.setFuncSetupByte4("1");
                                }
                                else if(n == 5) {
                                    lppo.setFuncSetupByte5("1");
                                }
                                else if(n == 6) {
                                    lppo.setFuncSetupByte6("1");
                                }
                                else if(n == 7) {
                                    lppo.setFuncSetupByte7("1");
                                }
                                else if(n == 8) {
                                    lppo.setFuncSetupByte8("1");
                                }
                            }
                            else {
                                if(n == 1) {
                                    lppo.setFuncSetupByte1("0");
                                }
                                else if(n == 2) {
                                    lppo.setFuncSetupByte2("0");
                                }
                                else if(n == 3) {
                                    lppo.setFuncSetupByte3("0");
                                }
                                else if(n == 4) {
                                    lppo.setFuncSetupByte4("0");
                                }
                                else if(n == 5) {
                                    lppo.setFuncSetupByte5("0");
                                }
                                else if(n == 6) {
                                    lppo.setFuncSetupByte6("0");
                                }
                                else if(n == 7) {
                                    lppo.setFuncSetupByte7("0");
                                }
                                else if(n == 8) {
                                    lppo.setFuncSetupByte8("0");
                                }
                            }
                        }
                    }

                    if("1".equals(lppo.getLpModelId()) || "2".equals(lppo.getLpModelId())) {
                        mav.addObject("hintRLCGearValue", "范围：60A~250A");
                    }
                    else if("3".equals(lppo.getLpModelId()) || "4".equals(lppo.getLpModelId())) {
                        mav.addObject("hintRLCGearValue", "范围：20A~100A");
                    }
                    else if("5".equals(lppo.getLpModelId()) || "6".equals(lppo.getLpModelId())) {
                        mav.addObject("hintRLCGearValue", "范围：200A~400A");
                    }
                    else if("7".equals(lppo.getLpModelId()) || "8".equals(lppo.getLpModelId())) {
                        mav.addObject("hintRLCGearValue", "范围：200A~600A");
                    }
                }
            }

            mav.addObject("result", lppo);
        }
        return mav;
    }

    /**
     * 
     * @param mav
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/s")
    public ModelAndView _setting(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        mav.setViewName(VIEW_NAME);
        Map mapRequest = WebUtils.getParametersStartingWith(request, "");
        logger.info("mapRequest : " + mapRequest.toString());
        String sPsId = (String) mapRequest.get("psId");
        String sModelId = (String) mapRequest.get("modelId");

        String rlcGearValue = (String) mapRequest.get("S_8001C04F03");
        int v = 0;
        try {
            v = Integer.parseInt(rlcGearValue);
        }
        catch(Exception _e) {
            mav.addObject("resultMsg", "输入值非法");
            return mav;
        }
        if("1".equals(sModelId) || "2".equals(sModelId)) {
            if(v < 60 || v > 250) {
                mav.addObject("resultMsg", "额定过载保护电流值超出范围60A~250A");
                return mav;
            }
        }
        else if("3".equals(sModelId) || "4".equals(sModelId)) {
            if(v < 20 || v > 100) {
                mav.addObject("resultMsg", "额定过载保护电流值超出范围20A~100A");
                return mav;
            }
        }
        else if("5".equals(sModelId) || "6".equals(sModelId)) {
            if(v < 200 || v > 400) {
                mav.addObject("resultMsg", "额定过载保护电流值超出范围200A~400A");
                return mav;
            }
        }
        else if("7".equals(sModelId) || "8".equals(sModelId)) {
            if(v < 200 || v > 600) {
                mav.addObject("resultMsg", "额定过载保护电流值超出范围200A~600A");
                return mav;
            }
        }

        if(sPsId != null) {
            Long psId = Long.parseLong(sPsId);
            PsInfo psInfo = psInfoManger.getById(psId);
            mav.addObject("psInfo", psInfo);
            CodeInfo psModel = codeInfoManager.getCodeInfo("PS_MODEL", psInfo.getModelCode());
            mav.addObject("psModel", psModel);
            mav.addObject("psType", codeInfoManager.getCodeInfo("PS_TYPE", psInfo.getPsType()));
            
            StringBuffer sb_dto = new StringBuffer();
            sb_dto.append("{");
            sb_dto.append("\"collectObjects_Transmit\":").append("[{");
            sb_dto.append("\"terminalAddr\":\"" + psInfo.getTerminalInfo().getLogicalAddr() + "\"").append(",");
            sb_dto.append("\"equipProtocol\":\"" + psInfo.getTerminalInfo().getProtocolNo() + "\"").append(",");
            sb_dto.append("\"meterAddr\":\"" + psInfo.getGpInfo().getGpAddr() + "\"").append(",");
            sb_dto.append("\"meterType\":\"" + "100" + "\"").append(",");
            sb_dto.append("\"funcode\":\"4\"").append(",");
            sb_dto.append("\"port\":\"" + "1" + "\"").append(",");
            sb_dto.append("\"serialPortPara\":").append("{");
            sb_dto.append("\"baudrate\":\"" + "110" + "\"").append(",");
            sb_dto.append("\"stopbit\":\"" + "1" + "\"").append(",");
            sb_dto.append("\"checkbit\":\"" + "0" + "\"").append(",");
            sb_dto.append("\"odd_even_bit\":\"" + "1" + "\"").append(",");
            sb_dto.append("\"databit\":\"" + "8" + "\"");
            sb_dto.append("}").append(",");
            sb_dto.append("\"waitforPacket\":\"" + "10" + "\"").append(",");
            sb_dto.append("\"waitforByte\":\"" + "5" + "\"").append(",");
            sb_dto.append("\"commandItems\":").append("[").append("{");
            sb_dto.append("\"identifier\":").append("\"8001C04F\"").append(",");
            sb_dto.append("\"datacellParam\":").append("{");
            sb_dto.append("\"8001C04F01\": \"" + psModel.getCode() + "\"").append(",");
            sb_dto.append("\"8001C04F02\": \"11100001\"").append(",");
            sb_dto.append("\"8001C04F03\": \"" + (String) mapRequest.get("S_8001C04F03") + "\"").append(",");
            sb_dto.append("\"8001C04F04\": \"" + (String) mapRequest.get("S_8001C04F04") + "\"").append(",");
            sb_dto.append("\"8001C04F05\": \"" + (String) mapRequest.get("S_8001C04F05") + "\"").append(",");
            sb_dto.append("\"8001C04F06\": \"" + "111111" + (String) mapRequest.get("S_8000C04F10_07") + (String) mapRequest.get("S_8000C04F10_08") + "\"");
            sb_dto.append("}");
            sb_dto.append("}").append("]");
            sb_dto.append("}]");
            sb_dto.append("}");
            String dtoJSONString = sb_dto.toString();
            String mtoType = psInfo.getTerminalInfo().getProtocolNo();
            MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(mtoType, dtoJSONString);
            long collectId = realTimeProxy376.transmitMsg(mto);

            Map<String, String> resultMap = null;
            int i = 5;
            while(i > 0
                    && (resultMap == null || (resultMap != null && !resultMap.containsKey(psInfo.getTerminalInfo()
                            .getLogicalAddr()
                            + "#"
                            + fillTopsMeterAddr(psInfo.getGpInfo().getGpAddr())
                            + "#"
                            + "8001C04F")))) {
                try {
                    Thread.sleep(3 * 1000);
                }
                catch(InterruptedException _ie) {
                }
                resultMap = realTimeProxy376.getReturnByWriteParameter_TransMit(collectId);
                i--;
            }

            String resultMsg = null;
            if(resultMap != null) {
                String result = resultMap.get(psInfo.getTerminalInfo().getLogicalAddr() + "#"
                        + fillTopsMeterAddr(psInfo.getGpInfo().getGpAddr()) + "#" + "8001C04F");
                if(result != null) {
                    if("1".equals(result)) {
                        resultMsg = "设置开关参数成功";
                    }
                    else {
                        resultMsg = "设置开关参数失败";
                    }
                }
                else {
                    resultMsg = "下发设置开关参数命令超时";
                }
            }
            else {
                resultMsg = "下发设置开关参数命令超时";
            }

            mav.addObject("resultMsg", resultMsg);
        }
        return mav;
    }

    private TranInfo getTranInfo(Long tgId) {
        TgInfo tgInfo = tgInfoManager.getById(tgId);
        List<TranInfo> tranInfoList = tgInfo.getTranInfos();
        if(tranInfoList.size() > 0)
            return tranInfoList.get(0);
        else
            return new TranInfo();
    }

    private String fillTopsMeterAddr(String meterAddr) {
        if(meterAddr != null) {
            String result = meterAddr.trim();
            int lens = result.length();
            if(lens < 12) {
                for(int i = 0; i < (12 - lens); i++) {
                    result = '0' + result;
                }
            }
            else {
                result = result.substring(0, 12);
            }

            return result;
        }
        else
            return "000000000000";
    }
}
