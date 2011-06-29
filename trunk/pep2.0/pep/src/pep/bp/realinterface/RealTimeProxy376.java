/*
 * 基于376国网规范的实时交互代理类
 */
package pep.bp.realinterface;

import java.io.IOException;
import java.util.*;
import java.util.Date;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 *
 * @author Thinkpad
 */
public class RealTimeProxy376 implements CollectInterface {

    private static int ID;
    private final int FAILCODE = -1;
    private final int cmdItemNum = 1;
    private final static Logger log = LoggerFactory.getLogger(RealTimeProxy376.class);
    private RTTaskService taskService;
    private Converter converter;

    public void setTaskService(RTTaskService rtTaskService) {
        this.taskService = rtTaskService;
    }

    public void setConverter(Converter converter) {
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

        try {
            for (CollectObject obj : mto.getCollectObjects()) {
                gpMark.delete(0, gpMark.length());
                commandMark.delete(0, commandMark.length());

                List<PmPacket376> packetList = converter.CollectObject2PacketList(obj, AFN, gpMark, commandMark, cmdItemNum);
                for (PmPacket376 packet : packetList) {
                    RealTimeTaskDAO task = new RealTimeTaskDAO();
                    task.setSendmsg(BcdUtils.binArrayToString(packet.getValue()));
                    task.setSequencecode(sequenceCode);
                    task.setLogicAddress(obj.getLogicalAddr());
                    task.setGpMark(packet.getRemark2());
                    task.setCommandMark(packet.getRemark1());
                    tasks.add(task);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return tasks;
    }

    private List<RealTimeTaskDAO> Encode_TransMit(MessageTranObject MTO, int sequenceCode) throws IOException {
        MTO_376 mto = (MTO_376) MTO;
        List<PmPacket376> packetList = new ArrayList<PmPacket376>();

        List<RealTimeTaskDAO> tasks = new ArrayList<RealTimeTaskDAO>();
        ProtocolConfig config = ProtocolConfig.getInstance();//获取配置文件对象
        StringBuilder gpMark = new StringBuilder();
        StringBuilder commandMark = new StringBuilder();
        try {
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
                    packet.getControlCode().setFunctionKey((byte) 1);
                    packet.getControlCode().setIsDownDirectFrameCountAvaliable(true);
                    packet.getControlCode().setDownDirectFrameCount((byte) 0);
                    packet.getSeq().setIsTpvAvalibe(true);

                    commandMark.append(commandItem.getIdentifier()).append("#");
                    PmPacket376DA da = new PmPacket376DA(0);
                    PmPacket376DT dt = new PmPacket376DT(1);

                    //376规约组帧
                    packet.getDataBuffer().putDA(da);
                    packet.getDataBuffer().putDT(dt);
                    packet.getDataBuffer().putBin(obj.getPort(), 1);//终端通信端口号
                    packet.getDataBuffer().putBS8(obj.getSerialPortPara().toString());//透明转发通信控制字
                    packet.getDataBuffer().put((byte) obj.getWaitforPacket());//透明转发接收等待报文超时时间
                    packet.getDataBuffer().putBin(obj.getWaitforByte(), 1);//透明转发接收等待字节超时时间

                    //645规约组帧
                    Gb645MeterPacket pack = new Gb645MeterPacket(obj.getMeterAddr());
                    pack.setControlCode(true, false, false, (byte) obj.getFuncode());
                    byte[] DI = BcdUtils.reverseBytes(BcdUtils.stringToByteArray(commandItem.getIdentifier().substring(4, 8)));
                    pack.getDataAsPmPacketData().put(DI);
                    Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItem.getIdentifier());
                    Map<String, String> dataItemMap = commandItem.getDatacellParam();
                    if (dataItemMap != null) {
                        Iterator iterator = DataItemMap_Config.keySet().iterator();
                        while (iterator.hasNext()) {
                            String DataItemCode = (String) iterator.next();
                            ProtocolDataItem dataItem = DataItemMap_Config.get(DataItemCode);
                            String DataItemValue = dataItem.getDefaultValue();
                            if ((dataItemMap != null) && (dataItemMap.containsKey(DataItemCode))) {
                                DataItemValue = dataItemMap.get(DataItemCode);
                            }
                            String Format = dataItem.getFormat();
                            String IsGroupEnd = dataItem.getIsGroupEnd();
                            int Length = dataItem.getLength();
                            int bitnumber = dataItem.getBitNumber();
                            converter.FillDataBuffer(pack.getDataAsPmPacketData(), Format, DataItemValue, IsGroupEnd, Length, bitnumber);
                        }
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
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return tasks;
    }

    /**
     * 参数设置
     * @param MTO 消息传输对象
     * @return 回执码(-1:表示失败)
     * @throws Exception
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    public Map<String, String> getReturnByWriteParameter(long appId) throws Exception {
        List<RealTimeTaskDAO> tasks = this.taskService.getTasks(appId);
        StringBuilder sb = new StringBuilder();
        Map<String, String> results = new HashMap<String, String>();
        try {
            for (RealTimeTaskDAO task : tasks) {
                String logicAddress = task.getLogicAddress();
                String gpMark = task.getGpMark();
                String[] GpArray = null;
                if (null != gpMark) {
                    GpArray = task.getGpMark().split("#");
                }

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
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return results;
    }

    @Override
    public Map<String, String> getReturnByWriteParameter_TransMit(long appId) throws Exception {
        List<RealTimeTaskDAO> tasks = this.taskService.getTasks(appId);
        StringBuilder sb = new StringBuilder();
        Map<String, String> results = new HashMap<String, String>();
        for (RealTimeTaskDAO task : tasks) {
            String logicAddress = task.getLogicAddress();
            String gpMark = task.getGpMark();
            String[] GpArray = null;
            if (null != gpMark) {
                GpArray = task.getGpMark().split("#");
            }

            String[] CommandArray = task.getCommandMark().split("#");
            List<RTTaskRecvDAO> recvs = task.getRecvMsgs();
            PmPacket376 packet = new PmPacket376();
            for (RTTaskRecvDAO recv : recvs) {
                byte[] msg = BcdUtils.stringToByteArray(recv.getRecvMsg());
                packet.setValue(msg, 0);
                PmPacketData dataBuf = packet.getDataBuffer();
                dataBuf.rewind();
                dataBuf.getDA(new PmPacket376DA());
                dataBuf.getDT(new PmPacket376DT());
                byte afn = packet.getAfn();
                if (afn == 0X10) {  //透明转发，特殊处理
                    long port = dataBuf.getBin(1);//终端通信端口号
                    long len = dataBuf.getBin(2);//透明转发内容字节数k
                    byte[] databuff = new byte[(int) len];
                    dataBuf.getRowIoBuffer().get(databuff);
                    int head = Gb645MeterPacket.getMsgHeadOffset(databuff, 0);
                    Gb645MeterPacket packet645 = Gb645MeterPacket.getPacket(databuff, head);
                    for (int i = 0; i < GpArray.length; i++) {
                        for (int j = 0; j < CommandArray.length; j++) {
                            String key = logicAddress + "#" + String.valueOf(GpArray[i]) + "#" + String.valueOf(CommandArray[i]);
                            String value = "2";
                            if (BcdUtils.byteToUnsigned(packet645.getControlCode().getValue())  == 0x84) {
                                value = String.valueOf(1);//确认
                            } else if (BcdUtils.byteToUnsigned(packet645.getControlCode().getValue()) == 0xC1) {
                                value = String.valueOf(2);//否认
                            }
                            results.put(key, value);
                        }
                    }
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
    @Override
    public String getReturnByWriteParameter_Json(long appId) throws Exception {
        return "";
    }

    /**
     * 获取参数读取结果
     * @param appId 回执码
     * @return 返回结果<"zdljdz#cldxh#commanditem", <"dataitem", "datavalue">>
     * @throws Exception
     */
    @Override
    public Map<String, Map<String, String>> getReturnByReadParameter(long appId) throws Exception {
        List<RealTimeTaskDAO> tasks = this.taskService.getTasks(appId);
        StringBuilder sb = new StringBuilder();
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
    @Override
    public String getReturnByReadParameter_Json(long appId) throws Exception {
        return "";
    }

    /**
     * 获取复位操作结果
     * @param appId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> getReturnByWriteResetCommand(long appId) throws Exception {
        return null;
    }

    /**
     * 获取实时召测返回结果
     * @param appId:任务序列号，调用readData时返回，是前台交互的唯一认证
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Map<String, String>> getReturnByReadData(long appId) throws Exception {
        List<RealTimeTaskDAO> tasks = this.taskService.getTasks(appId);
        StringBuilder sb = new StringBuilder();
        Map<String, Map<String, String>> tempMap = new HashMap<String, Map<String, String>>();
        for (RealTimeTaskDAO task : tasks) {
            String logicAddress = task.getLogicAddress();
            String[] GpArray = task.getGpMark().split("#");
            String[] CommandArray = task.getCommandMark().split("#");
            List<RTTaskRecvDAO> recvs = task.getRecvMsgs();
            PmPacket376 packet = new PmPacket376();
            for (RTTaskRecvDAO recv : recvs) {
                byte[] msg = BcdUtils.stringToByteArray(recv.getRecvMsg());
                packet.setValue(msg, 0);
                converter.decodeData(packet, tempMap);
            }
        }
        return Deal2DataMap(tempMap);
    }

    /**
     * 透明转发读参数，如：读漏保参数
     * @param appId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Map<String, String>> readTransmitPara(long appId) throws Exception {
        List<RealTimeTaskDAO> tasks = this.taskService.getTasks(appId);
        StringBuilder sb = new StringBuilder();
        Map<String, Map<String, String>> tempMap = new HashMap<String, Map<String, String>>();
        for (RealTimeTaskDAO task : tasks) {
            String logicAddress = task.getLogicAddress();
            String[] GpArray = task.getGpMark().split("#");
            String[] CommandArray = task.getCommandMark().split("#");
            List<RTTaskRecvDAO> recvs = task.getRecvMsgs();
            PmPacket376 packet = new PmPacket376();
            for (RTTaskRecvDAO recv : recvs) {
                byte[] msg = BcdUtils.stringToByteArray(recv.getRecvMsg());
                packet.setValue(msg, 0);
                converter.decodeData_TransMit(packet, tempMap);
            }
        }
        return tempMap;
    }

    @Override
    public Map<String, Map<String, String>> readTransmitWriteBack(long appId) throws Exception {
        List<RealTimeTaskDAO> tasks = this.taskService.getTasks(appId);
        StringBuilder sb = new StringBuilder();
        Map<String, Map<String, String>> tempMap = new HashMap<String, Map<String, String>>();
        for (RealTimeTaskDAO task : tasks) {
            String logicAddress = task.getLogicAddress();
            String[] GpArray = task.getGpMark().split("#");
            String[] CommandArray = task.getCommandMark().split("#");
            List<RTTaskRecvDAO> recvs = task.getRecvMsgs();
            PmPacket376 packet = new PmPacket376();
            for (RTTaskRecvDAO recv : recvs) {
                byte[] msg = BcdUtils.stringToByteArray(recv.getRecvMsg());
                packet.setValue(msg, 0);
                converter.decodeData_TransMit_WriteBack(packet, tempMap);
            }
        }
        return tempMap;
    }



    @Override
    public Map<String, Map<String, String>> readTransmitData(long appId) throws Exception {
        List<RealTimeTaskDAO> tasks = this.taskService.getTasks(appId);
        StringBuilder sb = new StringBuilder();
        Map<String, Map<String, String>> tempMap = new HashMap<String, Map<String, String>>();
        for (RealTimeTaskDAO task : tasks) {
            String logicAddress = task.getLogicAddress();
            String[] GpArray = task.getGpMark().split("#");
            String[] CommandArray = task.getCommandMark().split("#");
            List<RTTaskRecvDAO> recvs = task.getRecvMsgs();
            PmPacket376 packet = new PmPacket376();
            for (RTTaskRecvDAO recv : recvs) {
                byte[] msg = BcdUtils.stringToByteArray(recv.getRecvMsg());
                packet.setValue(msg, 0);
                converter.decodeData(packet, tempMap);
            }
        }
        return Deal2DataMap(tempMap);
    }

    private Map<String, Map<String, String>> Deal2DataMap(Map<String, Map<String, String>> sourceMap) throws IOException {
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
            
            while (iterator2.hasNext()) {
                Map<String, String> resultMap = new TreeMap<String, String>();
                dataItemCode = (String) iterator2.next();
                String dataValue = DataMap.get(dataItemCode);
                ProtocolDataItem dataItem = config.getDataItemMap(CommandItemCode).get(dataItemCode);
                String IsTd = dataItem.getIsTd();
                if (IsTd.equals("1")) {
                    keyInner = dataValue;
                } else {
                    keyInner = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
                }
                resultMap.put(keyInner, dataValue);
                results.put(key + "#" + dataItemCode, resultMap);
            }

        }
        return results;
    }
}
