package org.pssframework.controller.mobile;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.archive.PsInfo;
import org.pssframework.model.mobile.LeakageProtectorDataObject;
import org.pssframework.service.archive.PsInfoManger;
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
@RequestMapping("/mobile/lpm")
public class LeakageProtectorManageController extends BaseSpringController {
    private static final String VIEW_NAME = "/mobile/lpm";

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
        logger.info("mapRequest : " + mapRequest.toString());
        String sPsId = (String) mapRequest.get("psId");
        if(sPsId != null) {
            Long psId = Long.parseLong(sPsId);
            PsInfo psInfo = psInfoManger.getById(psId);
            mav.addObject("psInfo", psInfo);
            mav.addObject("psModel", codeInfoManager.getCodeInfo("PS_MODEL", psInfo.getModelCode()));
            mav.addObject("psType", codeInfoManager.getCodeInfo("PS_TYPE", psInfo.getPsType()));
            mav.addObject("result", new LeakageProtectorDataObject());
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
    @RequestMapping(value = "/rlpd")
    public ModelAndView _readLeakageProtectorData(ModelAndView mav, HttpServletRequest request,
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
            sb_dto.append("\"identifier\":").append("\"8000B66F\"");
            sb_dto.append("}").append("]");
            sb_dto.append("}]");
            sb_dto.append("}");
            String dtoJSONString = sb_dto.toString();
            String mtoType = psInfo.getTerminalInfo().getProtocolNo();
            MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(mtoType, dtoJSONString);
            long collectId = realTimeProxy376.transmitMsg(mto);

            Map<String, Map<String, String>> resultMap = null;
            int i = 3;
            while(i > 0
                    && (resultMap == null || (resultMap != null && !resultMap.containsKey(psInfo.getTerminalInfo()
                            .getLogicalAddr()
                            + "#"
                            + fillTopsMeterAddr(psInfo.getGpInfo().getGpAddr())
                            + "#"
                            + "8000B66F")))) {
                Thread.sleep(10 * 1000);
                resultMap = realTimeProxy376.readTransmitPara(collectId);
                i--;
            }

            LeakageProtectorDataObject lpdo = new LeakageProtectorDataObject();
            Map result = resultMap.get(psInfo.getTerminalInfo().getLogicalAddr() + "#"
                    + fillTopsMeterAddr(psInfo.getGpInfo().getGpAddr()) + "#" + "8000B66F");
            lpdo.setVoltA((String) result.get("B611"));
            lpdo.setVoltB((String) result.get("B612"));
            lpdo.setVoltC((String) result.get("B613"));
            lpdo.setEcurA((String) result.get("B621"));
            lpdo.setEcurB((String) result.get("B622"));
            lpdo.setEcurC((String) result.get("B623"));
            lpdo.setEcurR((String) result.get("B660"));

            sb_dto = new StringBuffer();
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
            dtoJSONString = sb_dto.toString();
            mtoType = psInfo.getTerminalInfo().getProtocolNo();
            mto = ConverterUtils.jsonString2MessageTranObject(mtoType, dtoJSONString);
            collectId = realTimeProxy376.transmitMsg(mto);

            resultMap = null;
            i = 3;
            while(i > 0
                    && (resultMap == null || (resultMap != null && !resultMap.containsKey(psInfo.getTerminalInfo()
                            .getLogicalAddr()
                            + "#"
                            + fillTopsMeterAddr(psInfo.getGpInfo().getGpAddr())
                            + "#"
                            + "8000C04F")))) {
                Thread.sleep(10 * 1000);
                resultMap = realTimeProxy376.readTransmitPara(collectId);
                i--;
            }

            result = resultMap.get(psInfo.getTerminalInfo().getLogicalAddr() + "#"
                    + fillTopsMeterAddr(psInfo.getGpInfo().getGpAddr()) + "#" + "8000C04F");
            lpdo.setErWorkingValue((String) result.get("8000C04F07"));
            lpdo.setTimeBlock((String) result.get("8000C04F09"));
            lpdo.setEcurRating((String) result.get("8000C04F05"));
            String msgStatus = "";
            if("0".equals((String) result.get("8000C04F01"))) {
                msgStatus += "合闸；\r";
            }
            else if("1".equals((String) result.get("8000C04F01"))) {
                msgStatus += "分闸；\r";
                if("0".equals((String) result.get("8000C04F02"))) {
                    msgStatus += "未闭锁；\r";
                }
                else if("1".equals((String) result.get("8000C04F02"))) {
                    msgStatus += "闭锁；\r";
                }

                if("00".equals((String) result.get("8000C04F03"))) {
                    msgStatus += "相位：无效；\r";
                }
                else if("01".equals((String) result.get("8000C04F03"))) {
                    msgStatus += "相位：A相；\r";
                }
                else if("10".equals((String) result.get("8000C04F03"))) {
                    msgStatus += "相位：B相；\r";
                }
                else if("11".equals((String) result.get("8000C04F03"))) {
                    msgStatus += "相位：C相；\r";
                }

                if("0000".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "漏电跳闸";
                }
                else if("0001".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "突变跳闸";
                }
                else if("0010".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "特波跳闸";
                }
                else if("0011".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "过载跳闸";
                }
                else if("0100".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "过压跳闸";
                }
                else if("0101".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "欠压跳闸";
                }
                else if("0110".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "短路跳闸";
                }
                else if("0111".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "手动跳闸";
                }
                else if("1000".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "停电跳闸";
                }
                else if("1001".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "互感器故障跳闸";
                }
                else if("1010".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "远程跳闸";
                }
                else if("1011".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "其它原因跳闸";
                }
                else if("1100".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "合闸过程中";
                }
                else if("1101".equals((String) result.get("8000C04F04"))) {
                    msgStatus += "合闸失败";
                }
            }
            lpdo.setEstatus(msgStatus);

            mav.addObject("result", lpdo);
        }
        return mav;
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
