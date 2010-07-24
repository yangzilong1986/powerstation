/*
 * 基于376国网规范的实时交互代理类
 */
package pep.bp.realinterface;

import java.util.*;
import java.util.Date;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import pep.bp.db.RTTaskService;
import pep.bp.model.RTTaskRecvDAO;
import pep.bp.model.RealTimeTaskDAO;
import pep.bp.realinterface.conf.ProtocolConfig;
import pep.bp.realinterface.conf.ProtocolDataItem;

import pep.bp.realinterface.mto.*;
import pep.bp.utils.AFNType;
import pep.bp.utils.Converter;
import pep.codec.protocol.gb.*;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.protocol.gb.gb376.PmPacket376DA;
import pep.codec.protocol.gb.gb376.PmPacket376DT;

import pep.codec.utils.BcdUtils;
import pep.meter645.Gb645MeterPacket;
import pep.system.SystemConst;

/**
 *
 * @author Thinkpad
 */
public class RealTimeProxy376 implements ICollectInterface {

    private static int ID;
    private final int FAILCODE = -1;
    private final int cmdItemNum = 4;
    private final static Logger log = LoggerFactory.getLogger(RealTimeProxy376.class);
    private RTTaskService taskService;
    private Converter converter;


        public void setTaskService(RTTaskService rtTaskService){
    	this.taskService = rtTaskService;
    }

    public void setConverter(Converter converter){
    	this.converter = converter;
    }


    private int getID() {
        return taskService.getSequnce();
    }

    private List<RealTimeTaskDAO> Encode(MessageTranObject MTO, int sequenceCode, byte AFN) {
        MTO_376 mto = (MTO_376) MTO;
        List<RealTimeTaskDAO> tasks = new ArrayList<RealTimeTaskDAO>();
        StringBuffer gpMark = new StringBuffer();
        StringBuffer commandMark = new StringBuffer();
        for (CollectObject obj : mto.getCollectObjects()) {
            gpMark.delete(0, gpMark.length());
            commandMark.delete(0, commandMark.length());

            List<PmPacket376> packetList = converter.CollectObject2PacketList(obj, AFN, gpMark, commandMark, cmdItemNum);
            for (PmPacket376 packet : packetList) {
                RealTimeTaskDAO task = new RealTimeTaskDAO();
                task.setSendmsg(BcdUtils.binArrayToString(packet.getValue()));
                task.setSequencecode(sequenceCode);
                task.setLogicAddress(obj.getLogicalAddr());
                task.setGpMark(gpMark.toString());
                task.setCommandMark(commandMark.toString());
                tasks.add(task);
            }
        }
        return tasks;
    }

    private List<RealTimeTaskDAO> Encode_TransMit(MessageTranObject MTO, int sequenceCode) {
        MTO_376 mto = (MTO_376) MTO;
        List<PmPacket376> packetList = new ArrayList<PmPacket376>();
        
        List<RealTimeTaskDAO> tasks = new ArrayList<RealTimeTaskDAO>();
        ProtocolConfig config = ProtocolConfig.getInstance();//获取配置文件对象
        StringBuffer gpMark = new StringBuffer();
        StringBuffer commandMark = new StringBuffer();
        for (CollectObject_TransMit obj : mto.getCollectObjects_Transmit()) {
            gpMark.delete(0, gpMark.length());
            gpMark.append(obj.getMeterAddr());
            commandMark.delete(0, commandMark.length());
            List<CommandItem> CommandItems = obj.getCommandItems();
            for (CommandItem commandItem : CommandItems) {

                PmPacket376 packet = new PmPacket376();
                packet.setAfn(AFNType.AFN_TRANSMIT);//AFN
                packet.getAddress().setRtua(obj.getTerminalAddr()); //逻辑地址
                packet.getControlCode().setIsUpDirect(false);
                packet.getControlCode().setIsOrgniger(true);
                packet.getControlCode().setFunctionKey((byte)1);
                packet.getControlCode().setIsDownDirectFrameCountAvaliable(true);
                packet.getControlCode().setDownDirectFrameCount((byte) 0);
                packet.getSeq().setIsTpvAvalibe(true);

                commandMark.append(commandItem.getIdentifier() + "#");
                PmPacket376DA da = new PmPacket376DA(0);
                PmPacket376DT dt = new PmPacket376DT(1);
          
                //376规约组帧
                packet.getDataBuffer().putDA(da);
                packet.getDataBuffer().putDT(dt);
                packet.getDataBuffer().putBin(obj.getPort(), 1);//终端通信端口号
                packet.getDataBuffer().putBS8(obj.getSerialPortPara().toString());//透明转发通信控制字
                packet.getDataBuffer().put(obj.getWaitforPacket());//透明转发接收等待报文超时时间
                packet.getDataBuffer().putBin(obj.getWaitforByte(), 1);//透明转发接收等待字节超时时间             

                //645规约组帧
                Gb645MeterPacket pack = new Gb645MeterPacket(obj.getMeterAddr());
                pack.setControlCode(true, false, false, obj.getFuncode());
                Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItem.getIdentifier());
                Map<String, String> dataItemMap = commandItem.getDatacellParam();
                Iterator iterator = DataItemMap_Config.keySet().iterator();
                while (iterator.hasNext()) {
                    String DataItemCode = (String) iterator.next();
                    ProtocolDataItem dataItem = DataItemMap_Config.get(DataItemCode);
                    String DataItemValue = dataItem.getDefaultValue();
                    if (dataItemMap.containsKey(DataItemCode)) {
                        DataItemValue = dataItemMap.get(DataItemCode);
                    }
                    String Format =  dataItem.getFormat();
                    String IsGroupEnd = dataItem.getIsGroupEnd();
                    int Length = dataItem.getLength();
                    int bitnumber = dataItem.getBitNumber();
                    converter.FillDataBuffer(pack.getDataAsPmPacketData(), Format, DataItemValue, IsGroupEnd, Length, bitnumber);
                }

                packet.getDataBuffer().putBin(pack.getValue().length, 2);//透明转发内容字节数k
                packet.getDataBuffer().put(pack.getValue());

                packet.setAuthorize(new Authorize());
                packet.setTpv(new TimeProtectValue());//时间标签
                packetList.add(packet);
            }
            for (PmPacket376 packet : packetList) {
                RealTimeTaskDAO task = new RealTimeTaskDAO();
                task.setSendmsg(BcdUtils.binArrayToString(packet.getValue()));
                task.setSequencecode(sequenceCode);
                task.setLogicAddress(obj.getTerminalAddr());
                task.setGpMark(gpMark.toString());
                task.setCommandMark(commandMark.toString());
                tasks.add(task);
            }
        }
        return tasks;
    }

//    private void InjectDataItem_REP(PmPacket376 packet, CollectObject cob, int[] MpSn) {
//        List<CommandItem> cmditems = cob.getCommandItems();
//        for (int i = 0; i <= MpSn.length - 1; i++) {
//            for (CommandItem commandItem : cmditems) {
//                PmPacket376DA da = new PmPacket376DA(MpSn[i]);
//                PmPacket376DT dt = new PmPacket376DT();
//                int fn = Integer.parseInt(commandItem.getIdentifier().substring(4, 8));//10+03+0002(protocolcode+afn+fn)
//                dt.setFn(fn);
//                packet.getDataBuffer().putDA(da);
//                packet.getDataBuffer().putDT(dt);
//                this.putDataBuf(packet, commandItem);
//            }
//        }
//    }
//    private void putDataBuf(PmPacket376 packet, CommandItem commandItem) {
//        String DataItemValue, Format, IsGroupEnd = "";
//        int Length, bitnumber = 0;
//        long TempCode = 0;
//        ProtocolConfig config = ProtocolConfig.getInstance();
//        Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItem.getIdentifier());
//        Map<String, String> dataItemMap = commandItem.getDatacellParam();
//        if (dataItemMap != null) {
//
//
//            Iterator iterator = dataItemMap.keySet().iterator();
//            while (iterator.hasNext()) {
//                ProtocolDataItem dataItem;
//                String DataItemCode = (String) iterator.next();
//                dataItem = DataItemMap_Config.get(DataItemCode);
//                DataItemValue = dataItem.getDefaultValue();
//                if (dataItemMap.containsKey(DataItemCode)) {
//                    DataItemValue = dataItemMap.get(DataItemCode);
//                }
//                Format = dataItem.getFormat();
//                Length = dataItem.getLength();
//                IsGroupEnd = dataItem.getIsGroupEnd();
//                bitnumber = dataItem.getBitNumber();
//                FillDataBuffer(packet, Format, DataItemValue, IsGroupEnd, Length, bitnumber);
//            }
//        }
//        if (commandItem.getCircleLevel() == 1) {
//            CircleDataItems circleDIs = commandItem.getCircleDataItems();
//            if (circleDIs != null) {
//                List<DataItemGroup> groupList = circleDIs.getDataItemGroups();
//                for (DataItemGroup group : groupList) {
//                    List<DataItem> dataItemList = group.getDataItemList();
//                    for (DataItem dataItem : dataItemList) {
//                        String DataItemCode = dataItem.getDataItemCode();
//
//                        if ((Long.valueOf(DataItemCode) - TempCode > 10000) && (TempCode != 0)) {
//                            ProtocolDataItem TempdataItem = DataItemMap_Config.get(String.valueOf(TempCode + 10000).substring(0, 10));
//                            DataItemValue = TempdataItem.getDefaultValue();
//                            Format = TempdataItem.getFormat();
//                            Length = TempdataItem.getLength();
//                            IsGroupEnd = TempdataItem.getIsGroupEnd();
//                            bitnumber = TempdataItem.getBitNumber();
//                            FillDataBuffer(packet, Format, DataItemValue, IsGroupEnd, Length, bitnumber);
//                        }
//                        ProtocolDataItem protocoldataItem = DataItemMap_Config.get(DataItemCode.substring(0, 10));
//                        DataItemValue = dataItem.getDataItemValue();
//                        Format = protocoldataItem.getFormat();
//                        Length = protocoldataItem.getLength();
//                        IsGroupEnd = protocoldataItem.getIsGroupEnd();
//                        bitnumber = protocoldataItem.getBitNumber();
//                        FillDataBuffer(packet, Format, DataItemValue, IsGroupEnd, Length, bitnumber);
//                        TempCode = Long.valueOf(DataItemCode);
//                    }
//                }
//            }
//        }
//    }
    /**
     * 针对一层循环的命令项的参数值注入
     * @param packet
     * @param commandItem
     */
//    private void InjectDataItem(PmPacket376 packet, CommandItem commandItem, int circlelevle) {
//        long TempCode = 0;
//        String DataItemValue, Format, IsGroupEnd = "";
//        int Length, bitnumber = 0;
//        putDataBuf(packet, commandItem);
//    }
//    private void FillDataBuffer(PmPacket376 packet, String Format, String DataItemValue, String IsGroupEnd, int Length, int bitnumber) {
//        if (Format.equals("BIN")) {
//            packet.getDataBuffer().putBin(Integer.parseInt(DataItemValue), Length);
//        } else if (Format.equals("IPPORT")) {
//            packet.getDataBuffer().putIPPORT(DataItemValue);
//        } else if (Format.equals("IP")) {
//            packet.getDataBuffer().putIP(DataItemValue);
//        } else if (Format.equals("TEL")) {
//            packet.getDataBuffer().putTEL(DataItemValue);
//        } else if (Format.equals("BS8")) {
//            packet.getDataBuffer().putBS8(DataItemValue);
//        } else if (Format.equals("GROUP_BS8")) {
//            groupValue += DataItemValue;
//            // String IsGroupEnd = dataItem.getIsGroupEnd();
//            if (IsGroupEnd.equals("1")) {
//                packet.getDataBuffer().putBS8(groupValue);
//                groupValue = "";
//            }
//        } else if (Format.equals("GROUP_BIN")) {
//            groupBinValue += Integer.parseInt(DataItemValue) << (bits - bitnumber);
//            bits -= bitnumber;
//            if (IsGroupEnd.equals("1")) {
//                packet.getDataBuffer().put((byte) groupBinValue);
//                bits = 8;
//                groupBinValue = 0;
//            }
//        } else if (Format.equals("BS24")) {
//            packet.getDataBuffer().putBS24(DataItemValue);
//        } else if (Format.equals("BS64")) {
//            packet.getDataBuffer().putBS64(DataItemValue);
//        } else if (Format.equals("ASCII")) {
//            packet.getDataBuffer().putAscii(DataItemValue, Length);
//        } else if (Format.equals("A1")) {
//            packet.getDataBuffer().putA1(new DataTypeA1(DataItemValue));
//        } else if (Format.equals("A2")) {
//            packet.getDataBuffer().putA2(new DataTypeA2(Double.parseDouble(DataItemValue)));
//        } else if (Format.equals("A3")) {
//            packet.getDataBuffer().putA3(new DataTypeA3(Long.parseLong(DataItemValue)));
//        } else if (Format.equals("A4")) {
//            packet.getDataBuffer().putA4(new DataTypeA4(Byte.parseByte(DataItemValue)));
//        } else if (Format.equals("A5")) {
//            packet.getDataBuffer().putA5(new DataTypeA5(Float.parseFloat(DataItemValue)));
//        } else if (Format.equals("A6")) {
//            packet.getDataBuffer().putA6(new DataTypeA6(Float.parseFloat(DataItemValue)));
//        } else if (Format.equals("A7")) {
//            packet.getDataBuffer().putA7(new DataTypeA7(Float.parseFloat(DataItemValue)));
//        } else if (Format.equals("A8")) {
//            packet.getDataBuffer().putA8(new DataTypeA8(Integer.parseInt(DataItemValue)));
//        } else if (Format.equals("A9")) {
//            packet.getDataBuffer().putA9(new DataTypeA9(Double.parseDouble(DataItemValue)));
//        } else if (Format.equals("A10")) {
//            packet.getDataBuffer().putA10(new DataTypeA10(Long.parseLong(DataItemValue)));
//        } else if (Format.equals("A11")) {
//            packet.getDataBuffer().putA11(new DataTypeA11(Double.parseDouble(DataItemValue)));
//        } else if (Format.equals("A12")) {
//            packet.getDataBuffer().putA12(new DataTypeA12(Long.parseLong(DataItemValue)));
//        } else if (Format.equals("A13")) {
//            packet.getDataBuffer().putA13(new DataTypeA13(Double.parseDouble(DataItemValue)));
//        } else if (Format.equals("A14")) {
//            packet.getDataBuffer().putA14(new DataTypeA14(Double.parseDouble(DataItemValue)));
//        } else if (Format.equals("A15")) {
//            packet.getDataBuffer().putA15(new DataTypeA15(DataItemValue, "yyyy-MM-dd HH:mm:ss"));
//        } else if (Format.equals("A16")) {
//            packet.getDataBuffer().putA16(new DataTypeA16(DataItemValue, "dd HH:mm:ss"));
//        } else if (Format.equals("A17")) {
//            packet.getDataBuffer().putA17(new DataTypeA17(DataItemValue, "MM-dd HH:mm"));
//        } else if (Format.equals("A18")) {
//            packet.getDataBuffer().putA18(new DataTypeA18(DataItemValue, "dd HH:mm"));
//        } else if (Format.equals("A19")) {
//            packet.getDataBuffer().putA19(new DataTypeA19(DataItemValue, "HH:mm"));
//        } else if (Format.equals("A20")) {
//            packet.getDataBuffer().putA20(new DataTypeA20(DataItemValue, "yyyy-MM-dd"));
//        } else if (Format.equals("A21")) {
//            packet.getDataBuffer().putA21(new DataTypeA21(DataItemValue, "yyyy-mm"));
//        } else if (Format.equals("A22")) {
//            packet.getDataBuffer().putA22(new DataTypeA22(Float.parseFloat(DataItemValue)));
//        } else if (Format.equals("A23")) {
//            packet.getDataBuffer().putA23(new DataTypeA23(Float.parseFloat(DataItemValue)));
//        } else if (Format.equals("A24")) {
//            packet.getDataBuffer().putA24(new DataTypeA24(DataItemValue, "dd HH"));
//        } else if (Format.equals("A25")) {
//            packet.getDataBuffer().putA25(new DataTypeA25(Long.parseLong(DataItemValue)));
//        } else if (Format.equals("A26")) {
//            packet.getDataBuffer().putA26(new DataTypeA26(Float.parseFloat(DataItemValue)));
//        } else if (Format.equals("A27")) {
//            packet.getDataBuffer().putA27(new DataTypeA27(Long.parseLong(DataItemValue)));
//        }
//    }
//    public RealTimeProxy376() {
//        ApplicationContext cxt = new ClassPathXmlApplicationContext(SystemConst.SPRING_BEANS);
//        taskService = (RTTaskService) cxt.getBean(SystemConst.REALTIMETASK_BEAN);
//        converter = new Converter();
//    }

    /**
     * 参数设置
     * @param MTO 消息传输对象
     * @return 回执码(-1:表示失败)
     * @throws Exception
     */
    public long writeParameters(MessageTranObject MTO) throws Exception {
        try {
            if ((null == MTO) || (MTO.getType() != MTOType.GW_376)) {
                return FAILCODE;

            } else {
                int sequenceCode = getID();
                List<RealTimeTaskDAO> tasks = this.Encode(MTO, sequenceCode, AFNType.AFN_SETPARA);
                for (RealTimeTaskDAO task : tasks) {
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
    public long readParameters(MessageTranObject MTO) throws Exception {
        try {
            if ((null == MTO) || (MTO.getType() != MTOType.GW_376)) {
                return FAILCODE;
            } else {
                int sequenceCode = getID();
                List<RealTimeTaskDAO> tasks = this.Encode(MTO, sequenceCode, AFNType.AFN_GETPARA);
                for (RealTimeTaskDAO task : tasks) {
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
                List<RealTimeTaskDAO> tasks = this.Encode(MTO, sequenceCode, AFNType.AFN_RESET);
                for (RealTimeTaskDAO task : tasks) {
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
     * 实时召测
     * @param MTO
     * @return
     * @throws Exception
     */
    public long readData(MessageTranObject MTO) throws Exception {

        try {
            if ((null == MTO) || (MTO.getType() != MTOType.GW_376)) {
                return FAILCODE;
            } else {
                int sequenceCode = getID();
                List<RealTimeTaskDAO> tasks = this.Encode(MTO, sequenceCode, AFNType.AFN_READDATA1);
                for (RealTimeTaskDAO task : tasks) {
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
     * 透明转发
     * @param MTO
     * @return
     * @throws Exception
     */
    @Override
    public long transmitMsg(MessageTranObject MTO) throws Exception {
        try {
            if ((null == MTO) || (MTO.getType() != MTOType.GW_376)) {
                return FAILCODE;
            } else {
                int sequenceCode = getID();

                List<RealTimeTaskDAO> tasks = this.Encode_TransMit(MTO, sequenceCode);
                for (RealTimeTaskDAO task : tasks) {
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
    public Map<String, String> getReturnByWriteParameter(long appId) throws Exception {
        List<RealTimeTaskDAO> tasks = this.taskService.getTasks(appId);
        StringBuffer sb = new StringBuffer();
        Map<String, String> results = new HashMap<String, String>();
        for (RealTimeTaskDAO task : tasks) {
            String logicAddress = task.getLogicAddress();
            String[] GpArray = task.getGpMark().split("#");
            String[] CommandArray = task.getCommandMark().split("#");
            List<RTTaskRecvDAO> recvs = task.getRecvMsgs();
            PmPacket376 packet = new PmPacket376();
            for (RTTaskRecvDAO recv : recvs) {
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
    public String getReturnByWriteParameter_Json(long appId) throws Exception {
        return "";
    }

    /**
     * 获取参数读取结果
     * @param appId 回执码
     * @return 返回结果<"zdljdz#cldxh#commanditem", <"dataitem", "datavalue">>
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByReadParameter(long appId) throws Exception {
        List<RealTimeTaskDAO> tasks = this.taskService.getTasks(appId);
        StringBuffer sb = new StringBuffer();
        Map<String, Map<String, String>> results = new HashMap<String, Map<String, String>>();
        for (RealTimeTaskDAO task : tasks) {
            String logicAddress = task.getLogicAddress();
            String[] GpArray = task.getGpMark().split("#");
            String[] CommandArray = task.getCommandMark().split("#");
            List<RTTaskRecvDAO> recvs = task.getRecvMsgs();
            PmPacket376 packet = new PmPacket376();
            for (RTTaskRecvDAO recv : recvs) {
                byte[] msg = BcdUtils.stringToByteArray(recv.getRecvMsg());
                packet.setValue(msg, 0);
                converter.decodeData(packet, results);
            }
        }
        return results;
    }

    /**
     * 获取参数读取结果
     * @param appId
     * @return 返回JSon格式结果
     * @throws Exception
     */
    public String getReturnByReadParameter_Json(long appId) throws Exception {
        return "";
    }

    /**
     * 获取复位操作结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, String> getReturnByWriteResetCommand(long appId) throws Exception {
        return null;
    }

    /**
     * 获取实时召测返回结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByReadData(long appId) throws Exception {
        List<RealTimeTaskDAO> tasks = this.taskService.getTasks(appId);
        StringBuffer sb = new StringBuffer();
        Map<String, Map<String, String>> results = new HashMap<String, Map<String, String>>();
        for (RealTimeTaskDAO task : tasks) {
            String logicAddress = task.getLogicAddress();
            String[] GpArray = task.getGpMark().split("#");
            String[] CommandArray = task.getCommandMark().split("#");
            List<RTTaskRecvDAO> recvs = task.getRecvMsgs();
            PmPacket376 packet = new PmPacket376();
            for (RTTaskRecvDAO recv : recvs) {
                byte[] msg = BcdUtils.stringToByteArray(recv.getRecvMsg());
                packet.setValue(msg, 0);
                converter.decodeData(packet, results);
            }
        }
        return results;
    }

    private Map<String, Map<String, String>> Deal2DataMap(Map<String, Map<String, String>> sourceMap)
    {
        String dataItemCode = "";
        Map<String, Map<String, String>> results = new TreeMap<String, Map<String, String>>();
        ProtocolConfig config = ProtocolConfig.getInstance();//获取配置文件对象
        Iterator iterator = sourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String keyInner = "";
            String CommandItemCode = key.split("#")[2];
            Map<String, String> DataMap = sourceMap.get(key);
            Iterator iterator2 = DataMap.keySet().iterator();
            Map<String, String> resultMap = new TreeMap<String, String>();
            while (iterator2.hasNext()) {
                dataItemCode = (String) iterator2.next();
                String dataValue = DataMap.get(dataItemCode);
                ProtocolDataItem dataItem = config.getDataItemMap(CommandItemCode).get(dataItemCode);
                String IsTd = dataItem.getIsTd();
                if(IsTd.equals("1"))
                    keyInner = dataValue;
                else
                    keyInner = DateFormatUtils.format(new Date(),"YYYY-MM-DD HH:MI:SS");
                resultMap.put(keyInner, dataValue);               
            }
            results.put(key+"#"+dataItemCode, resultMap);
        }
        return results;
    }
}
