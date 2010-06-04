/*
 * 基于376国网规范的实时交互代理类
 */
package pep.bp.realinterface;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import pep.bp.db.RTTaskService;
import pep.bp.model.RealTimeTask;
import pep.bp.realinterface.conf.ProtocolConfig;
import pep.bp.realinterface.mto.*;
import pep.codec.protocol.gb.Authorize;
import pep.codec.protocol.gb.DataTypeA1;
import pep.codec.protocol.gb.DataTypeA2;
import pep.codec.protocol.gb.TimeProtectValue;
import pep.codec.protocol.gb.gb376.*;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author Thinkpad
 */
public class RealTimeProxy376 implements ICollectInterface {

    private static int ID;
    private final int FAILCODE = -1;
    private final byte AFN_RESET = 0x01; //AFN：复位
    private final byte AFN_SETPARA = 0x04; //AFN：设置参数
    private final byte AFN_GETPARA = 0x0A; //AFN：读取参数
    private final byte AFN_TRANSMIT = 0x10; //AFN：数据转发
    private final byte FUNCODE_DOWM_1 = 1;//PRM =1 功能码：1 （发送/确认）
    private final static Logger log = LoggerFactory.getLogger(RealTimeProxy376.class);
    private RTTaskService taskService;

    private int getID() {
        ID++;
        return ID;
    }

    private RealTimeTask Encode(MessageTranObject MTO, int sequenceCode, byte AFN) {
        MTO_376 mto = (MTO_376) MTO;
        RealTimeTask task = new RealTimeTask();
        for (CollectObject obj : mto.getCollectObjects()) {
            PmPacket376 packet = new PmPacket376();
            packet.setAfn(AFN);//AFN
            packet.getAddress().setRtua(obj.getLogicalAddr()); //逻辑地址
            packet.getControlCode().setIsUpDirect(false);
            packet.getControlCode().setIsOrgniger(true);
            packet.getControlCode().setFunctionKey(FUNCODE_DOWM_1);
            packet.getControlCode().setIsDownDirectFrameCountAvaliable(true);
            packet.getControlCode().setDownDirectFrameCount((byte) 0);
            int[] MpSn = obj.getMpSn();
            StringBuffer DataBuffer = new StringBuffer();
            PmPacket376DT dt = null;
            for (int i = 0; i <= MpSn.length - 1; i++) {
                PmPacket376DA da = new PmPacket376DA();
                da.setPn(MpSn[i]);
                List<CommandItem> CommandItems = obj.getCommandItems();
                for (CommandItem commandItem : CommandItems) {
                    dt = new PmPacket376DT();
                    int fn = Integer.parseInt(commandItem.getIdentifier().substring(4, 8));//10+03+0002(protocolcode+afn+fn)
                    dt.setFn(fn);
                    packet.getDataBuffer().putDA(da);
                    packet.getDataBuffer().putDT(dt);
                    //针对参数设置注入设置的值
                    if (AFN == AFN_SETPARA) {
                        InjectDataIteam(packet, commandItem, AFN);
                    }
                }
                if(AFN == AFN_RESET || AFN == AFN_SETPARA ||AFN == AFN_TRANSMIT)//消息认证码字段PW
                    packet.setAuthorize(new Authorize());

                packet.setTpv(new TimeProtectValue());//时间标签
                task.setSendmsg(BcdUtils.binArrayToString(packet.getValue()));
                task.setSequencecode(sequenceCode);
            }
        }
        return task;
    }

    private void InjectDataIteam(PmPacket376 packet, CommandItem commandItem, byte AFN) {

        ProtocolConfig config = ProtocolConfig.getInstance();
        Map<String, String> datacellParam = commandItem.getDatacellParam();
        Iterator iterator = datacellParam.keySet().iterator();
        while (iterator.hasNext()) {
            String DataItemCode = (String) iterator.next();
            String DataItemValue = datacellParam.get(DataItemCode);
            String Format = config.getFormat(DataItemCode);
            if (Format == "BIN") {
                packet.getDataBuffer().put(DataItemValue.getBytes());
            } else if (Format == "BS8") {
                packet.getDataBuffer().putBS8(DataItemValue);
            } else if (Format == "BS24") {
                packet.getDataBuffer().putBS24(DataItemValue);
            } else if (Format == "BS64") {
                packet.getDataBuffer().putBS64(DataItemValue);
            } else if (Format == "ASCII"); else if (Format == "A1") {
                packet.getDataBuffer().putA1(new DataTypeA1( DataItemValue));
            } else if (Format == "A2") {
                packet.getDataBuffer().putA2(new DataTypeA2(DataItemValue.getBytes()));
            } else if (Format == "A3"); else if (Format == "A4"); else if (Format == "A5"); else if (Format == "A6"); else if (Format == "A7"); else if (Format == "A8"); else if (Format == "A9"); else if (Format == "A10"); else if (Format == "A11"); else if (Format == "A12"); else if (Format == "A13"); else if (Format == "A14"); else if (Format == "A15"); else if (Format == "A16"); else if (Format == "A17"); else if (Format == "A18"); else if (Format == "A19"); else if (Format == "A20"); else if (Format == "A21"); else if (Format == "A22"); else if (Format == "A23"); else if (Format == "A24"); else if (Format == "A25"); else if (Format == "A26"); else if (Format == "A27");

        }
    }

    public RealTimeProxy376() {
        ApplicationContext cxt = new ClassPathXmlApplicationContext("beans.xml");
        taskService = (RTTaskService) cxt.getBean("taskService");
    }

    /**
     * 参数设置
     * @param MTO 消息传输对象
     * @return 回执码(-1:表示失败)
     * @throws Exception
     */
    public long writeEquipmentParameters(MessageTranObject MTO) throws Exception {
        try {
            if ((null == MTO) || (MTO.getType() != MTOType.GW_376)) {
                return FAILCODE;

            } else {
                int sequenceCode = getID();
                RealTimeTask task = this.Encode(MTO, sequenceCode, AFN_SETPARA);
                this.taskService.insertTask(task);
                return sequenceCode;
            }
        } catch (NumberFormatException numberFormatException) {
            log.error(numberFormatException.getMessage());
            return FAILCODE;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return FAILCODE;
        }
    }

    /**
     * 参数读取
     * @param MTO 消息传输对象
     * @return 回执码（-1:表示失败）
     * @throws Exception
     */
    public long readEquipmentParameters(MessageTranObject MTO) throws Exception {
        try {
            if ((null == MTO) || (MTO.getType() != MTOType.GW_376)) {
                return FAILCODE;
            } else {
                int sequenceCode = getID();
                RealTimeTask task = this.Encode(MTO, sequenceCode, AFN_GETPARA);
                this.taskService.insertTask(task);
                return sequenceCode;
            }

        } catch (NumberFormatException numberFormatException) {
            log.error(numberFormatException.getMessage());
            return FAILCODE;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return FAILCODE;
        }
    }

    /**
     * 下发复位命令
     * @param MTO
     * @return 回执码（-1:表示失败）
     * @throws Exception
     */
    public long writeResetCommands(MessageTranObject MTO) throws Exception {
        try {
            if ((null == MTO) || (MTO.getType() != MTOType.GW_376)) {
                return FAILCODE;
            } else {
                int sequenceCode = getID();
                RealTimeTask task = this.Encode(MTO, sequenceCode, AFN_RESET);
                this.taskService.insertTask(task);
                return sequenceCode;
            }

        } catch (NumberFormatException numberFormatException) {
            log.error(numberFormatException.getMessage());
            return FAILCODE;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return FAILCODE;
        }
    }

    /**
     * 下发控制命令
     * @param MTO
     * @return 回执码（-1:表示失败）
     * @throws Exception
     */
    public long writeControlCommands(MessageTranObject MTO) throws Exception {
        return getID();
    }

    /**
     * 实时召测
     * @param MTO
     * @return
     * @throws Exception
     */
    public long readRealtimeData(MessageTranObject MTO) throws Exception {
        return getID();
    }

    /**
     * 透明转发
     * @param MTO
     * @return
     * @throws Exception
     */
    public long transmitMsg(MessageTranObject MTO) throws Exception {
        try {
            if ((null == MTO) || (MTO.getType() != MTOType.GW_376)) {
                return FAILCODE;
            } else {
                int sequenceCode = getID();
                RealTimeTask task = this.Encode(MTO, sequenceCode, AFN_TRANSMIT);
                this.taskService.insertTask(task);
                return sequenceCode;
            }
        } catch (NumberFormatException numberFormatException) {
            log.error(numberFormatException.getMessage());
            return FAILCODE;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return FAILCODE;
        }
    }

    /**
     * 获取参数设置结果
     * @param appId 回执码
     * @return 返回结果<"zdljdz#cldxh#commanditem", "result">
     * @throws Exception
     */
    public Map<String, String> getReturnByWEP(long appId) throws Exception {
        return null;
    }

    /**
     * 获取参数设置结果
     * @param appId
     * @return 返回JSon格式结果
     * @throws Exception
     */
    public String getReturnByWEP_Json(long appId) throws Exception {
        return "";
    }

    /**
     * 获取参数读取结果
     * @param appId 回执码
     * @return 返回结果<"zdljdz#cldxh#commanditem", <"dataitem", "datavalue">>
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByREP(long appId) throws Exception {
        return null;
    }

    /**
     * 获取参数读取结果
     * @param appId
     * @return 返回JSon格式结果
     * @throws Exception
     */
    public String getReturnByREP_Json(long appId) throws Exception {
        return "";
    }

    /**
     * 获取复位操作结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, String> getReturnByWRC(long appId) throws Exception {
        return null;
    }

    /**
     * 获取下发控制命令返回结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, String> getReturnByWCC(long appId) throws Exception {
        return null;
    }

    /**
     * 获取实时召测返回结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByRRD(long appId) throws Exception {
        return null;
    }
}
