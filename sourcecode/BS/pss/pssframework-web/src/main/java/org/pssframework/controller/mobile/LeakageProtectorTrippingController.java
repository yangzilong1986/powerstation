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
@RequestMapping("/mobile/lpt")
public class LeakageProtectorTrippingController extends BaseSpringController {
    private static final String VIEW_NAME = "/mobile/lpt";

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
    @RequestMapping(value = "/rb")
    public ModelAndView _remoteBreaking(ModelAndView mav, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
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
            sb_dto.append("\"identifier\":").append("\"8000C036\"").append(",");
            sb_dto.append("\"datacellParam\":").append("{");
            sb_dto.append("\"8000C03601\": \"50\"");
            sb_dto.append("}");
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
                            + "8000C036")))) {
                try {
                    Thread.sleep(3 * 1000);
                }
                catch(InterruptedException _ie) {
                }
                resultMap = realTimeProxy376.readTransmitWriteBack(collectId);
                i--;
            }

            String resultMsg = null;
            if(resultMap != null) {
                Map result = resultMap.get(psInfo.getTerminalInfo().getLogicalAddr() + "#"
                        + fillTopsMeterAddr(psInfo.getGpInfo().getGpAddr()) + "#" + "8000C036");
                if(result != null) {
                    if("1".equals((String) result.get("C04001"))) {
                        resultMsg = "开关分闸成功";
                    }
                    else {
                        resultMsg = "开关分闸失败";
                    }
                }
                else {
                    resultMsg = "下发开关分闸命令超时";
                }
            }
            else {
                resultMsg = "下发开关分闸命令超时";
            }

            mav.addObject("resultMsg", resultMsg);
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
    @RequestMapping(value = "/rs")
    public ModelAndView _remoteSwitching(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
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
            sb_dto.append("\"identifier\":").append("\"8000C036\"").append(",");
            sb_dto.append("\"datacellParam\":").append("{");
            sb_dto.append("\"8000C03601\": \"5F\"");
            sb_dto.append("}");
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
                            + "8000C036")))) {
                try {
                    Thread.sleep(3 * 1000);
                }
                catch(InterruptedException _ie) {
                }
                resultMap = realTimeProxy376.readTransmitWriteBack(collectId);
                i--;
            }

            String resultMsg = null;
            if(resultMap != null) {
                Map result = resultMap.get(psInfo.getTerminalInfo().getLogicalAddr() + "#"
                        + fillTopsMeterAddr(psInfo.getGpInfo().getGpAddr()) + "#" + "8000C036");
                if(result != null) {
                    if("0".equals((String) result.get("C04001"))) {
                        resultMsg = "开关合闸成功";
                    }
                    else {
                        if("1100".equals((String) result.get("C04004"))) {
                            resultMsg = "下发开关合闸命令成功";
                            mav.addObject("goRlpd", true);
                        }
                        else {
                            resultMsg = "开关合闸失败";
                        }
                    }
                }
                else {
                    resultMsg = "下发开关合闸命令超时";
                }
            }
            else {
                resultMsg = "下发开关合闸命令超时";
            }

            mav.addObject("resultMsg", resultMsg);
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
    @RequestMapping(value = "/rt")
    public ModelAndView _remoteTesting(ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
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
            sb_dto.append("\"identifier\":").append("\"8000C037\"");
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
                            + "8000C037")))) {
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
                        + fillTopsMeterAddr(psInfo.getGpInfo().getGpAddr()) + "#" + "8000C037");
                if(result != null) {
                    if("1".equals(result)) {
                        resultMsg = "开关试验跳成功";
                    }
                    else {
                        resultMsg = "开关试验跳失败";
                    }
                }
                else {
                    resultMsg = "下发开关试验跳命令超时";
                }
            }
            else {
                resultMsg = "下发开关试验跳命令超时";
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
