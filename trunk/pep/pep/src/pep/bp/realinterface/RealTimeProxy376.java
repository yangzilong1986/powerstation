/*
 * 基于376国网规范的实时交互代理类
 */
package pep.bp.realinterface;

import java.text.DecimalFormat;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import pep.bp.db.RTTaskService;
import pep.bp.model.RTTaskRecv;
import pep.bp.model.RealTimeTask;
import pep.bp.realinterface.conf.ProtocolConfig;
import pep.bp.realinterface.conf.ProtocolDataItem;
import pep.bp.realinterface.mto.*;
import pep.codec.protocol.gb.*;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.protocol.gb.gb376.PmPacket376DA;
import pep.codec.protocol.gb.gb376.PmPacket376DT;

import pep.codec.utils.BcdUtils;

/**
 *
 * @author Thinkpad
 */
public class RealTimeProxy376 implements ICollectInterface {

    private static int ID;
    private final int FAILCODE = -1;
    private final byte AFN_CONFIRM = 0x00; //AFN：确认/否认
    private final byte AFN_RESET = 0x01; //AFN：复位
    private final byte AFN_SETPARA = 0x04; //AFN：设置参数
    private final byte AFN_GETPARA = 0x0A; //AFN：读取参数
    private final byte AFN_TRANSMIT = 0x10; //AFN：数据转发
    private final byte FUNCODE_DOWM_1 = 1;//PRM =1 功能码：1 （发送/确认）
    private final static Logger log = LoggerFactory.getLogger(RealTimeProxy376.class);
    private RTTaskService taskService;

    private String groupValue = "";
    private int groupBinValue = 0;
    byte bits = 8;

    private int getID() {
        return taskService.getSequnce();
    }

    private List<RealTimeTask> Encode(MessageTranObject MTO, int sequenceCode, byte AFN) {
        MTO_376 mto = (MTO_376) MTO;
        List<RealTimeTask> tasks = new ArrayList<RealTimeTask>();
        String gpMark, commandMark;
        for (CollectObject obj : mto.getCollectObjects()) {
            gpMark = "";
            commandMark = "";
            RealTimeTask task = new RealTimeTask();
            PmPacket376 packet = new PmPacket376();
            packet.setAfn(AFN);//AFN
            packet.getAddress().setRtua(obj.getLogicalAddr()); //逻辑地址
            packet.getControlCode().setIsUpDirect(false);
            packet.getControlCode().setIsOrgniger(true);
            packet.getControlCode().setFunctionKey(FUNCODE_DOWM_1);
            packet.getControlCode().setIsDownDirectFrameCountAvaliable(true);
            packet.getControlCode().setDownDirectFrameCount((byte) 0);
            packet.getSeq().setIsTpvAvalibe(true);
            int[] MpSn = obj.getMpSn();
            for (int i = 0; i <= MpSn.length - 1; i++) {
                gpMark += String.valueOf(MpSn[i]) + "#";
                List<CommandItem> CommandItems = obj.getCommandItems();
                for (CommandItem commandItem : CommandItems) {
                    commandMark += commandItem.getIdentifier() + "#";
                    PmPacket376DA da = new PmPacket376DA(MpSn[i]);
                    PmPacket376DT dt = new PmPacket376DT();
                    int fn = Integer.parseInt(commandItem.getIdentifier().substring(4, 8));//10+03+0002(protocolcode+afn+fn)
                    dt.setFn(fn);
                    packet.getDataBuffer().putDA(da);
                    packet.getDataBuffer().putDT(dt);
                    if (AFN == AFN_SETPARA) {
                        if (commandItem.getCircleLevel() == 0) 
                            InjectDataIteam(packet, commandItem);
                        else
                            InjectDataIteamCircle1(packet, commandItem);
                    }
                }
                if (AFN == AFN_RESET || AFN == AFN_SETPARA || AFN == AFN_TRANSMIT)//消息认证码字段PW
                {
                    packet.setAuthorize(new Authorize());
                }
            }
            packet.setTpv(new TimeProtectValue());//时间标签
            task.setSendmsg(BcdUtils.binArrayToString(packet.getValue()));
            task.setSequencecode(sequenceCode);
            task.setLogicAddress(obj.getLogicalAddr());
            task.setGpMark(gpMark);
            task.setCommandMark(commandMark);
            tasks.add(task);
        }
        return tasks;
    }


    /**
     * 针对一层循环的命令项的参数值注入
     * @param packet
     * @param commandItem
     */
    private void InjectDataIteamCircle1(PmPacket376 packet, CommandItem commandItem) {
        long TempCode = 0;
        byte bits = 8;
        ProtocolConfig config = ProtocolConfig.getInstance();
        Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItem.getIdentifier());
        CircleDataItems circleDIs = commandItem.getCircleDataItems();
        String DataItemCode =  circleDIs.getNumberItem().getDataItemCode();
        String DataItemValue = circleDIs.getNumberItem().getDataItemValue();
        ProtocolDataItem protocoldataItem = DataItemMap_Config.get(DataItemCode);
        String Format = protocoldataItem.getFormat();
        int Length = protocoldataItem.getLength();
        String IsGroupEnd = protocoldataItem.getIsGroupEnd();
        int bitnumber = protocoldataItem.getBitNumber();
        FillDataBuffer(packet,Format,DataItemValue,IsGroupEnd,Length,bitnumber);

        List<DataItemGroup> groupList = circleDIs.getDataItemGroups();
        for(DataItemGroup group :groupList){
            List<DataItem> dataItemList = group.getDataItemList();
            for(DataItem dataItem : dataItemList){
                DataItemCode = dataItem.getDataItemCode();
                
                if((Long.valueOf(DataItemCode) -TempCode > 10000)&&(TempCode!=0)){
                    ProtocolDataItem TempdataItem = DataItemMap_Config.get(String.valueOf(TempCode+10000).substring(0, 10));
                    DataItemValue = TempdataItem.getDefaultValue();
                    Format = TempdataItem.getFormat();
                    Length = TempdataItem.getLength();
                    IsGroupEnd = TempdataItem.getIsGroupEnd();
                    bitnumber = TempdataItem.getBitNumber();
                    FillDataBuffer(packet,Format,DataItemValue,IsGroupEnd,Length,bitnumber);
                }
                protocoldataItem = DataItemMap_Config.get(DataItemCode.substring(0, 10));
                DataItemValue = dataItem.getDataItemValue();
                Format = protocoldataItem.getFormat();
                Length = protocoldataItem.getLength();
                IsGroupEnd = protocoldataItem.getIsGroupEnd();
                bitnumber = protocoldataItem.getBitNumber();
                FillDataBuffer(packet,Format,DataItemValue,IsGroupEnd,Length,bitnumber);
                TempCode = Long.valueOf(DataItemCode);
            }
        }
    }

    private void InjectDataIteam(PmPacket376 packet, CommandItem commandItem) {  
        byte bits = 8;
        ProtocolConfig config = ProtocolConfig.getInstance();
        Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItem.getIdentifier());
        Map<String, String> dataItemMap = commandItem.getDatacellParam();
        Iterator iterator = DataItemMap_Config.keySet().iterator();
        while (iterator.hasNext()) {
            //针对参数设置注入设置的值
            ProtocolDataItem dataItem;
            String DataItemCode = (String) iterator.next();
            dataItem = DataItemMap_Config.get(DataItemCode);
            String DataItemValue = dataItem.getDefaultValue();
            if (dataItemMap.containsKey(DataItemCode)) {
                DataItemValue = dataItemMap.get(DataItemCode);
            }
            String Format = dataItem.getFormat();
            int Length = dataItem.getLength();
            String IsGroupEnd = dataItem.getIsGroupEnd();
            int bitnumber = dataItem.getBitNumber();
            FillDataBuffer(packet,Format,DataItemValue,IsGroupEnd,Length,bitnumber);
            
        }
    }

    private void FillDataBuffer(PmPacket376 packet,String Format,String DataItemValue,String IsGroupEnd, int Length,int bitnumber){
        if (Format.equals("BIN")) {
                packet.getDataBuffer().putBin(Integer.parseInt(DataItemValue), Length);
            } else if (Format.equals("IPPORT")) {
                packet.getDataBuffer().putIPPORT(DataItemValue);
            } else if (Format.equals("IP")) {
                packet.getDataBuffer().putIP(DataItemValue);
            } else if (Format.equals("TEL")) {
                packet.getDataBuffer().putTEL(DataItemValue);
            } else if (Format.equals("BS8")) {
                packet.getDataBuffer().putBS8(DataItemValue);
            } else if (Format.equals("GROUP_BS8")) {
                groupValue += DataItemValue;
               // String IsGroupEnd = dataItem.getIsGroupEnd();
                if (IsGroupEnd.equals("1")) {
                    packet.getDataBuffer().putBS8(groupValue);
                    groupValue = "";

                }
            } else if (Format.equals("GROUP_BIN")) {
                groupBinValue += Integer.parseInt(DataItemValue) << (bits - bitnumber);
                bits -= bitnumber;
                if (IsGroupEnd.equals("1")) {
                    packet.getDataBuffer().put((byte)groupBinValue);
                    bits = 8;
                    groupBinValue = 0;
                }
            } else if (Format.equals("BS24")) {
                packet.getDataBuffer().putBS24(DataItemValue);
            } else if (Format.equals("BS64")) {
                packet.getDataBuffer().putBS64(DataItemValue);
            } else if (Format.equals("ASCII")) {
                packet.getDataBuffer().putAscii(DataItemValue, Length);
            } else if (Format.equals("A1")) {
                packet.getDataBuffer().putA1(new DataTypeA1(DataItemValue));
            } else if (Format.equals("A2")) {
                packet.getDataBuffer().putA2(new DataTypeA2(Double.parseDouble(DataItemValue)));
            } else if (Format.equals("A3")) {
                packet.getDataBuffer().putA3(new DataTypeA3(Long.parseLong(DataItemValue)));
            } else if (Format.equals("A4")) {
                packet.getDataBuffer().putA4(new DataTypeA4(Byte.parseByte(DataItemValue)));
            } else if (Format.equals("A5")) {
                packet.getDataBuffer().putA5(new DataTypeA5(Float.parseFloat(DataItemValue)));
            } else if (Format.equals("A6")) {
                packet.getDataBuffer().putA6(new DataTypeA6(Float.parseFloat(DataItemValue)));
            } else if (Format.equals("A7")) {
                packet.getDataBuffer().putA7(new DataTypeA7(Float.parseFloat(DataItemValue)));
            } else if (Format.equals("A8")) {
                packet.getDataBuffer().putA8(new DataTypeA8(Integer.parseInt(DataItemValue)));
            } else if (Format.equals("A9")) {
                packet.getDataBuffer().putA9(new DataTypeA9(Double.parseDouble(DataItemValue)));
            } else if (Format.equals("A10")) {
                packet.getDataBuffer().putA10(new DataTypeA10(Long.parseLong(DataItemValue)));
            } else if (Format.equals("A11")) {
                packet.getDataBuffer().putA11(new DataTypeA11(Double.parseDouble(DataItemValue)));
            } else if (Format.equals("A12")) {
                packet.getDataBuffer().putA12(new DataTypeA12(Long.parseLong(DataItemValue)));
            } else if (Format.equals("A13")) {
                packet.getDataBuffer().putA13(new DataTypeA13(Double.parseDouble(DataItemValue)));
            } else if (Format.equals("A14")) {
                packet.getDataBuffer().putA14(new DataTypeA14(Double.parseDouble(DataItemValue)));
            } else if (Format.equals("A15")) {
                packet.getDataBuffer().putA15(new DataTypeA15(DataItemValue, "yyyy-MM-dd HH:mm:ss"));
            } else if (Format.equals("A16")) {
                packet.getDataBuffer().putA16(new DataTypeA16(DataItemValue, "dd HH:mm:ss"));
            } else if (Format.equals("A17")) {
                packet.getDataBuffer().putA17(new DataTypeA17(DataItemValue, "MM-dd HH:mm"));
            } else if (Format.equals("A18")) {
                packet.getDataBuffer().putA18(new DataTypeA18(DataItemValue, "dd HH:mm"));
            } else if (Format.equals("A19")) {
                packet.getDataBuffer().putA19(new DataTypeA19(DataItemValue, "HH:mm"));
            } else if (Format.equals("A20")) {
                packet.getDataBuffer().putA20(new DataTypeA20(DataItemValue, "yyyy-MM-dd"));
            } else if (Format.equals("A21")) {
                packet.getDataBuffer().putA21(new DataTypeA21(DataItemValue, "yyyy-mm"));
            } else if (Format.equals("A22")) {
                packet.getDataBuffer().putA22(new DataTypeA22(Float.parseFloat(DataItemValue)));
            } else if (Format.equals("A23")) {
                packet.getDataBuffer().putA23(new DataTypeA23(Float.parseFloat(DataItemValue)));
            } else if (Format.equals("A24")) {
                packet.getDataBuffer().putA24(new DataTypeA24(DataItemValue, "dd HH"));
            } else if (Format.equals("A25")) {
                packet.getDataBuffer().putA25(new DataTypeA25(Long.parseLong(DataItemValue)));
            } else if (Format.equals("A26")) {
                packet.getDataBuffer().putA26(new DataTypeA26(Float.parseFloat(DataItemValue)));
            } else if (Format.equals("A27")) {
                packet.getDataBuffer().putA27(new DataTypeA27(Long.parseLong(DataItemValue)));
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
                List<RealTimeTask> tasks = this.Encode(MTO, sequenceCode, AFN_SETPARA);
                for (RealTimeTask task : tasks) {
                    this.taskService.insertTask(task);
                }
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
                List<RealTimeTask> tasks = this.Encode(MTO, sequenceCode, AFN_GETPARA);
                for (RealTimeTask task : tasks) {
                    this.taskService.insertTask(task);
                }
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
                List<RealTimeTask> tasks = this.Encode(MTO, sequenceCode, AFN_RESET);
                for (RealTimeTask task : tasks) {
                    this.taskService.insertTask(task);
                }
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

                List<RealTimeTask> tasks = this.Encode(MTO, sequenceCode, AFN_TRANSMIT);
                for (RealTimeTask task : tasks) {
                    this.taskService.insertTask(task);
                }
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
        List<RealTimeTask> tasks = this.taskService.getTasks(appId);
        StringBuffer sb = new StringBuffer();
        Map<String, String> results = new HashMap<String, String>();
        for (RealTimeTask task : tasks) {
            String logicAddress = task.getLogicAddress();
            String[] GpArray = task.getGpMark().split("#");
            String[] CommandArray = task.getCommandMark().split("#");
            List<RTTaskRecv> recvs = task.getRecvMsgs();
            PmPacket376 packet = new PmPacket376();
            for (RTTaskRecv recv : recvs) {
                byte[] msg = BcdUtils.stringToByteArray(recv.getRecvMsg());
                packet.setValue(msg, 0);
                PmPacketData dataBuf = packet.getDataBuffer();
                dataBuf.rewind();
                dataBuf.getDA(new PmPacket376DA());
                PmPacket376DT dt = new PmPacket376DT();
                dataBuf.getDT(dt);
                int result = dt.getFn();
                //全部确认或否认
                if (result < 3) {
                    for (int i = 0; i < GpArray.length; i++) {
                        for (int j = 0; j < CommandArray.length; j++) {
                            String key = logicAddress + "#" + String.valueOf(GpArray[i]) + "#" + String.valueOf(CommandArray[i]);
                            String value = String.valueOf(result);
                            results.put(key, value);
                        }
                    }
                }
                //逐项确认
                if (result == 3) {
                }
            }
        }
        return results;
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

    public Map<String, String> decodeData(PmPacketData dataBuffer, String CommandItemCode) {
        String GroupValue = "";
        int groupBinValue =0;
        Map<String, String> results = new TreeMap<String, String>();
        ProtocolConfig config = ProtocolConfig.getInstance();
        Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(CommandItemCode);
        Iterator iterator = DataItemMap_Config.keySet().iterator();
        dataBuffer.rewind();
        dataBuffer.getWord();//DA
        dataBuffer.getWord();//DT
        while (iterator.hasNext()) {
            String DataItemCode = (String) iterator.next();
            ProtocolDataItem dataItem = DataItemMap_Config.get(DataItemCode);
            int Len = dataItem.getLength();
            String Format = dataItem.getFormat();
            int bitnumber = dataItem.getBitNumber();
            String IsGroupEnd = dataItem.getIsGroupEnd();
            if (Format.equals("BIN")) {
                results.put(DataItemCode, String.valueOf(dataBuffer.getBin(Len)));
                /*
                if (Len == 1) {
                    results.put(DataItemCode, String.valueOf(dataBuffer.get()));
                } else {
                    results.put(DataItemCode, String.valueOf(dataBuffer.getWord()));
                }
                 */
            } else if (Format.equals("IPPORT")) {
                results.put(DataItemCode, dataBuffer.getIPPORT());
            } else if (Format.equals("IP")) {
                results.put(DataItemCode, dataBuffer.getIP());
            } else if (Format.equals("TEL")) {
                results.put(DataItemCode, dataBuffer.getTEL());
            } else if (Format.equals("BS8")) {
                results.put(DataItemCode, String.valueOf(dataBuffer.getBS8()));
            } else if (Format.equals("GROUP_BS8")) {
                if (GroupValue.length() == 0) {
                    GroupValue = dataBuffer.getBS8();
                }
                results.put(DataItemCode, GroupValue.substring(0, Len));
                GroupValue = GroupValue.substring(Len, GroupValue.length());
                if (IsGroupEnd.equals("1"))
                    GroupValue = "";
            } else if (Format.equals("GROUP_BIN")) {
                if(groupBinValue == 0)
                    groupBinValue = dataBuffer.get();
                if(groupBinValue<0) groupBinValue = groupBinValue + 256;
                byte resultValue = (byte) (groupBinValue >> (bits - bitnumber));
                groupBinValue = groupBinValue-(resultValue<<(bits - bitnumber));
                bits-= bitnumber;               
                results.put(DataItemCode, String.valueOf(resultValue));
                if (IsGroupEnd.equals("1")){
                    groupBinValue = 0;
                    bits = 8;
                }
            } else if (Format.equals("BS24")) {
                results.put(DataItemCode, String.valueOf(dataBuffer.getBS24()));
            } else if (Format.equals("BS64")) {
                results.put(DataItemCode, String.valueOf(dataBuffer.getBS64()));
            } else if (Format.equals("ASCII")) {
                results.put(DataItemCode, String.valueOf(dataBuffer.getAscii(Len)));
            } else if (Format.equals("A1")) {
                results.put(DataItemCode, dataBuffer.getA1().toString());
            } else if (Format.equals("A2")) {
                results.put(DataItemCode, dataBuffer.getA2().toString());
            } else if (Format.equals("A3")) {
                results.put(DataItemCode, dataBuffer.getA3().toString());
            } else if (Format.equals("A4")) {
                results.put(DataItemCode, dataBuffer.getA4().toString());
            } else if (Format.equals("A5")) {
                results.put(DataItemCode, dataBuffer.getA5().toString());
            } else if (Format.equals("A6")) {
                results.put(DataItemCode, dataBuffer.getA6().toString());
            } else if (Format.equals("A7")) {
                results.put(DataItemCode, dataBuffer.getA7().toString());
            } else if (Format.equals("A8")) {
                results.put(DataItemCode, dataBuffer.getA8().toString());
            } else if (Format.equals("A9")) {
                results.put(DataItemCode, dataBuffer.getA9().toString());
            } else if (Format.equals("A10")) {
                results.put(DataItemCode, dataBuffer.getA10().toString());
            } else if (Format.equals("A11")) {
                results.put(DataItemCode, dataBuffer.getA11().toString());
            } else if (Format.equals("A12")) {
                results.put(DataItemCode, dataBuffer.getA12().toString());
            } else if (Format.equals("A13")) {
                results.put(DataItemCode, dataBuffer.getA13().toString());
            } else if (Format.equals("A14")) {
                results.put(DataItemCode, dataBuffer.getA14().toString());
            } else if (Format.equals("A15")) {
                results.put(DataItemCode, dataBuffer.getA15().toString());
            } else if (Format.equals("A16")) {
                results.put(DataItemCode, dataBuffer.getA16().toString());
            } else if (Format.equals("A17")) {
                results.put(DataItemCode, dataBuffer.getA17().toString());
            } else if (Format.equals("A18")) {
                results.put(DataItemCode, dataBuffer.getA18().toString());
            } else if (Format.equals("A19")) {
                results.put(DataItemCode, dataBuffer.getA19().toString());
            } else if (Format.equals("A20")) {
                results.put(DataItemCode, dataBuffer.getA20().toString());
            } else if (Format.equals("A21")) {
                results.put(DataItemCode, dataBuffer.getA21().toString());
            } else if (Format.equals("A22")) {
                results.put(DataItemCode, dataBuffer.getA22().toString());
            } else if (Format.equals("A23")) {
                results.put(DataItemCode, dataBuffer.getA23().toString());
            } else if (Format.equals("A24")) {
                results.put(DataItemCode, dataBuffer.getA24().toString());
            } else if (Format.equals("A25")) {
                results.put(DataItemCode, dataBuffer.getA25().toString());
            } else if (Format.equals("A26")) {
                results.put(DataItemCode, dataBuffer.getA26().toString());
            } else if (Format.equals("A27")) {
                results.put(DataItemCode, dataBuffer.getA27().toString());
            }
        }
        return results;
    }
}
