/*
 * 组帧/解帧的门面类
 */
package pep.bp.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.bp.model.Dto;
import pep.bp.realinterface.conf.ProtocolConfig;
import pep.bp.realinterface.conf.ProtocolDataItem;
import pep.bp.realinterface.mto.CircleDataItems;
import pep.bp.realinterface.mto.CollectObject;
import pep.bp.realinterface.mto.CollectObject_TransMit;
import pep.bp.realinterface.mto.CommandItem;
import pep.bp.realinterface.mto.DataItem;
import pep.bp.realinterface.mto.DataItemGroup;
import pep.bp.utils.decoder.Decoder;
import pep.codec.protocol.gb.Authorize;
import pep.codec.protocol.gb.DataTypeA1;
import pep.codec.protocol.gb.DataTypeA10;
import pep.codec.protocol.gb.DataTypeA11;
import pep.codec.protocol.gb.DataTypeA12;
import pep.codec.protocol.gb.DataTypeA13;
import pep.codec.protocol.gb.DataTypeA14;
import pep.codec.protocol.gb.DataTypeA15;
import pep.codec.protocol.gb.DataTypeA16;
import pep.codec.protocol.gb.DataTypeA17;
import pep.codec.protocol.gb.DataTypeA18;
import pep.codec.protocol.gb.DataTypeA19;
import pep.codec.protocol.gb.DataTypeA2;
import pep.codec.protocol.gb.DataTypeA20;
import pep.codec.protocol.gb.DataTypeA21;
import pep.codec.protocol.gb.DataTypeA22;
import pep.codec.protocol.gb.DataTypeA23;
import pep.codec.protocol.gb.DataTypeA24;
import pep.codec.protocol.gb.DataTypeA25;
import pep.codec.protocol.gb.DataTypeA26;
import pep.codec.protocol.gb.DataTypeA27;
import pep.codec.protocol.gb.DataTypeA3;
import pep.codec.protocol.gb.DataTypeA4;
import pep.codec.protocol.gb.DataTypeA5;
import pep.codec.protocol.gb.DataTypeA6;
import pep.codec.protocol.gb.DataTypeA7;
import pep.codec.protocol.gb.DataTypeA8;
import pep.codec.protocol.gb.DataTypeA9;
import pep.codec.protocol.gb.PmPacketData;
import pep.codec.protocol.gb.TimeProtectValue;
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
public class Converter {

    private final static Logger log = LoggerFactory.getLogger(Converter.class);
    private static final byte FUNCODE_DOWM_1 = 1;//PRM =1 功能码：1 （发送/确认）
    private String groupValue = "";
    private int groupBinValue = 0;
    byte bits = 8;
    private ProtocolConfig config;
    private Decoder decoder;

    public void CollectObject2Packet(CollectObject obj, PmPacket376 packet, byte AFN, StringBuffer gpMark, StringBuffer commandMark) {
        try {
            preSetPacket(packet, AFN, obj.getLogicalAddr());
            int[] MpSn = obj.getMpSn();

            for (int i = 0; i <= MpSn.length - 1; i++) {
                gpMark.append(MpSn[i] + "#");
                List<CommandItem> CommandItems = obj.getCommandItems();
                for (CommandItem commandItem : CommandItems) {
                    commandMark.append(commandItem.getIdentifier() + "#");
                    PmPacket376DA da = new PmPacket376DA(MpSn[i]);
                    PmPacket376DT dt = new PmPacket376DT();
                    int fn = Integer.parseInt(commandItem.getIdentifier().substring(4, 8));//10+03+0002(protocolcode+afn+fn)
                    dt.setFn(fn);
                    packet.getDataBuffer().putDA(da);
                    packet.getDataBuffer().putDT(dt);
                    if (AFN == AFNType.AFN_READDATA2) {
                        putDataBuf_readWithConfig(packet, commandItem);
                    }
                    if ((AFN == AFNType.AFN_SETPARA)) {
                        putDataBuf_withValue(packet, commandItem);
                    }
                }
                if (AFN == AFNType.AFN_RESET || AFN == AFNType.AFN_SETPARA || AFN == AFNType.AFN_TRANSMIT)//消息认证码字段PW
                {
                    packet.setAuthorize(new Authorize());
                }
            }
            packet.setTpv(new TimeProtectValue());//时间标签
        } catch (NumberFormatException numberFormatException) {
            log.error(numberFormatException.getMessage());
        }
    }

    public List<PmPacket376> CollectObject2PacketList(CollectObject obj, byte AFN, StringBuffer gpMark, StringBuffer commandMark, int CmdItemNum) {
        List<PmPacket376> results = new ArrayList<PmPacket376>();
        PmPacket376 packet = new PmPacket376();
        int Index = 1;
        int[] MpSn = obj.getMpSn();
        int DataBuffLen = SystemConst.MAX_PACKET_LEN - 16 - 22;//[68+L+L+68+C+A+AFN+SEQ+TP+PW+CS+16]
        for (int i = 0; i <= MpSn.length - 1; i++) {
            gpMark.delete(0, gpMark.length());
            gpMark.append(String.valueOf(MpSn[i]) + "#");
            List<CommandItem> CommandItems = obj.getCommandItems();
            for (CommandItem commandItem : CommandItems) {

                if (IsSeqValid(commandItem)) //针对类似F10的参数，按每帧最大长度进行自动分包处理
                {
                    commandItem2PacketList(commandItem, AFN, obj.getLogicalAddr(), i, DataBuffLen, results);
                } else {
                    if ((Index - 1) % CmdItemNum == 0) {
                        packet = new PmPacket376();
                        preSetPacket(packet, AFN, obj.getLogicalAddr());
                    }
                    commandMark.append(commandItem.getIdentifier() + "#");
                    PmPacket376DA da = new PmPacket376DA(MpSn[i]);
                    PmPacket376DT dt = new PmPacket376DT();
                    int fn = Integer.parseInt(commandItem.getIdentifier().substring(4, 8));//10+03+0002(protocolcode+afn+fn)
                    dt.setFn(fn);
                    packet.getDataBuffer().putDA(da);
                    packet.getDataBuffer().putDT(dt);
                    if ((AFN == AFNType.AFN_SETPARA)) {
                        putDataBuf_withValue(packet, commandItem);
                    }
                    if ((AFN == AFNType.AFN_READDATA2)) {
                        putDataBuf_readWithConfig(packet, commandItem);
                    }
                    if (Index % CmdItemNum == 0) {
                        if (AFN == AFNType.AFN_RESET || AFN == AFNType.AFN_SETPARA || AFN == AFNType.AFN_TRANSMIT)//消息认证码字段PW
                        {
                            packet.setAuthorize(new Authorize());
                        }
                        packet.setTpv(new TimeProtectValue());//时间标签
                        packet.setRemark1(commandMark.toString());
                        packet.setRemark2(gpMark.toString());
                        results.add(packet);
                        commandMark.delete(0, commandMark.length());
                    }
                }
                Index++;
            }
        }
        return results;
    }

    public List<PmPacket376> CollectObject_TransMit2PacketList(CollectObject_TransMit obj, StringBuffer commandMark) {
        List<PmPacket376> results = new ArrayList<PmPacket376>();
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

            commandMark.append(commandItem.getIdentifier() + "#");
            PmPacket376DA da = new PmPacket376DA(obj.getMpSn());
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
                    this.FillDataBuffer(pack.getDataAsPmPacketData(), Format, DataItemValue, IsGroupEnd, Length, bitnumber);
                }
            }
            // pack.getDataAsPmPacketData().rewind();
            packet.getDataBuffer().putBin(pack.getValue().length, 2);//透明转发内容字节数k
            packet.getDataBuffer().put(pack.getValue());

            packet.setAuthorize(new Authorize());
            packet.setTpv(new TimeProtectValue());//时间标签
            results.add(packet);
        }
        return results;
    }

    private Boolean IsSeqValid(CommandItem commandItem) {
        Boolean result = (commandItem != null);
        result = result && (commandItem.getDatacellParam() != null);
        result = result && (commandItem.getCircleDataItems() != null);
        result = result && (commandItem.getCircleDataItems().getDataItemGroups() != null);
        result = result && ((commandItem.getDatacellParam().size() > 0)
                && (commandItem.getCircleDataItems().getDataItemGroups().size() > 0));
        return result;
    }

    private int getDataItemGroupLength(CommandItem commandItem) {
        long TempCode = 0;
        int Len = 0;
        List<DataItemGroup> groups = commandItem.getCircleDataItems().getDataItemGroups();
        if (groups.size() > 0) {
            DataItemGroup group = groups.get(0);
            Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItem.getIdentifier());

            List<DataItem> dataItemList = group.getDataItemList();
            for (DataItem dataItem : dataItemList) {
                String DataItemCode = dataItem.getDataItemCode();
                if ((Long.valueOf(DataItemCode) - TempCode > 10000) && (TempCode != 0)) {
                    ProtocolDataItem TempdataItem = DataItemMap_Config.get(String.valueOf(TempCode + 10000).substring(0, 10));
                    Len += TempdataItem.getLength();
                }
                ProtocolDataItem protocoldataItem = DataItemMap_Config.get(DataItemCode.substring(0, 10));

                Len += protocoldataItem.getLength();
                TempCode = Long.valueOf(DataItemCode);
            }
            return Len + 2;//个数（2字节）
        } else {
            return 0;
        }
    }

    private List<PmPacket376> commandItem2PacketList(CommandItem commandItem, byte AFN, String Rtua, int MpSn, int PacketLen, List<PmPacket376> results) {
        int Index = 1;
        int packetNo = 1;
        long TempCode = 0;
        boolean CanPacket = false;
        int groupNumber = PacketLen / getDataItemGroupLength(commandItem);  //理论每一帧下发的参数组数
        int ActualgroupNumber = 0;//每次实际下发的参数组数
        String DataItemValue = "";
        String Format = "";
        String IsGroupEnd = "";
        int Length = 0;
        int bitnumber = 0;
        ProtocolDataItem dataItem = null;

        Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItem.getIdentifier());
        Map<String, String> dataItemMap = commandItem.getDatacellParam();

        PmPacket376 packet = null;
        CircleDataItems circleDIs = commandItem.getCircleDataItems();
        if (circleDIs != null) {
            List<DataItemGroup> groups = circleDIs.getDataItemGroups();
            int WaitForSendPacketNum = groups.size();
            int fn = Integer.parseInt(commandItem.getIdentifier().substring(4, 8));//10+03+0002(protocolcode+afn+fn)
            for (DataItemGroup group : groups) {
                if (WaitForSendPacketNum >= groupNumber) {
                    ActualgroupNumber = groupNumber;
                } else {
                    ActualgroupNumber = WaitForSendPacketNum;
                }

                //生成一个报文
                if (((Index - 1) % ActualgroupNumber == 0) && (!CanPacket)) {
                    packet = new PmPacket376();
                    packet.setRemark1(commandItem.getIdentifier());//设置命令项标志
                    packet.setRemark2(String.valueOf(MpSn));//设置测量点标志
                    preSetPacket(packet, AFN, Rtua);
                    PmPacket376DA da = new PmPacket376DA(MpSn);
                    PmPacket376DT dt = new PmPacket376DT(fn);
                    packet.getDataBuffer().putDA(da);
                    packet.getDataBuffer().putDT(dt);
                    Iterator iterator = dataItemMap.keySet().iterator();
                    while (iterator.hasNext()) {
                        String DataItemCode = (String) iterator.next();
                        dataItem = DataItemMap_Config.get(DataItemCode);
                    }
                    FillDataBuffer(packet.getDataBuffer(), dataItem.getFormat(), String.valueOf(ActualgroupNumber), dataItem.getIsGroupEnd(), dataItem.getLength(), dataItem.getBitNumber());
                }

                List<DataItem> dataItemList = group.getDataItemList();
                for (DataItem dataItemTemp : dataItemList) {
                    CanPacket = true;
                    String DataItemCode = dataItemTemp.getDataItemCode();
                    if ((Long.valueOf(DataItemCode) - TempCode > 10000) && (TempCode != 0)) {
                        ProtocolDataItem TempdataItem = DataItemMap_Config.get(String.valueOf(TempCode + 10000).substring(0, 10));
                        DataItemValue = TempdataItem.getDefaultValue();
                        Format = TempdataItem.getFormat();
                        Length = TempdataItem.getLength();
                        IsGroupEnd = TempdataItem.getIsGroupEnd();
                        bitnumber = TempdataItem.getBitNumber();
                        FillDataBuffer(packet.getDataBuffer(), Format, DataItemValue, IsGroupEnd, Length, bitnumber);
                    }
                    ProtocolDataItem protocoldataItem = DataItemMap_Config.get(DataItemCode.substring(0, 10));
                    DataItemValue = dataItemTemp.getDataItemValue();
                    Format = protocoldataItem.getFormat();
                    Length = protocoldataItem.getLength();
                    IsGroupEnd = protocoldataItem.getIsGroupEnd();
                    bitnumber = protocoldataItem.getBitNumber();
                    FillDataBuffer(packet.getDataBuffer(), Format, DataItemValue, IsGroupEnd, Length, bitnumber);
                    TempCode = Long.valueOf(DataItemCode);
                }
                if ((Index % ActualgroupNumber == 0) && (CanPacket)) {
                    if (AFN == AFNType.AFN_RESET || AFN == AFNType.AFN_SETPARA || AFN == AFNType.AFN_TRANSMIT)//消息认证码字段PW
                    {
                        packet.setAuthorize(new Authorize());
                    }
                    packet.setTpv(new TimeProtectValue());//时间标签
                    if (groups.size() == 1) {//单帧
                        packet.getSeq().setIsTpvAvalibe(true);
                        packet.getSeq().setIsFinishFrame(true);
                        packet.getSeq().setIsFirstFrame(true);
                        packet.getSeq().setSeq((byte) 0);
                    } else if (packetNo == 1)//起始帧
                    {
                        packet.getSeq().setIsTpvAvalibe(true);
                        packet.getSeq().setIsFinishFrame(false);
                        packet.getSeq().setIsFirstFrame(true);
                        packet.getSeq().setSeq((byte) packetNo);
                    } else if ((packetNo > 1) && (Index < groups.size()))//中间帧
                    {
                        packet.getSeq().setIsTpvAvalibe(true);
                        packet.getSeq().setIsFinishFrame(false);
                        packet.getSeq().setIsFirstFrame(false);
                        packet.getSeq().setSeq((byte) packetNo);
                    } else if (Index == groups.size())//结束帧
                    {
                        packet.getSeq().setIsTpvAvalibe(true);
                        packet.getSeq().setIsFinishFrame(true);
                        packet.getSeq().setIsFirstFrame(false);
                        packet.getSeq().setSeq((byte) packetNo);
                    }
                    results.add(packet);
                    CanPacket = false;
                    WaitForSendPacketNum -= ActualgroupNumber;
                    packetNo++;
                }
                Index++;
            }
        }

        return results;
    }

    private void preSetPacket(PmPacket376 packet, byte AFN, String Rtua) {
        if (null != packet) {
            packet.setAfn(AFN);//AFN
            packet.getAddress().setRtua(Rtua); //逻辑地址
            packet.getControlCode().setIsUpDirect(false);
            packet.getControlCode().setIsOrgniger(true);
            packet.getControlCode().setFunctionKey(FUNCODE_DOWM_1);
            packet.getControlCode().setIsDownDirectFrameCountAvaliable(true);
            packet.getControlCode().setDownDirectFrameCount((byte) 0);
            packet.getSeq().setIsTpvAvalibe(true);
        }

    }

    public void putDataBuf_readWithConfig(PmPacket376 packet, CommandItem commandItem) {
        String DataItemValue, Format, IsGroupEnd = "";
        int Length, bitnumber = 0;
        long TempCode = 0;
        List<ProtocolDataItem> DataItemList_Config = config.getDataItemList(commandItem.getIdentifier());
        Map<String, String> dataItemMap = commandItem.getDatacellParam();
        for (ProtocolDataItem dataItem : DataItemList_Config) {
            String DataItemCode = dataItem.getDataItemCode();
            DataItemValue = dataItem.getDefaultValue();
            if (DataItemValue.equals("YESTERDAY")) //抄上一天
                DataItemValue = UtilsBp.getYeasterday();

            if (dataItemMap != null) {
                if (dataItemMap.containsKey(DataItemCode)) {
                    DataItemValue = dataItemMap.get(DataItemCode);
                }
            }
            Format = dataItem.getFormat();
            Length = dataItem.getLength();
            IsGroupEnd = dataItem.getIsGroupEnd();
            bitnumber = dataItem.getBitNumber();
            FillDataBuffer(packet.getDataBuffer(), Format, DataItemValue, IsGroupEnd, Length, bitnumber);
        }
    }

    public void putDataBuf_withValue(PmPacket376 packet, CommandItem commandItem) {
        String DataItemValue, Format, IsGroupEnd = "";
        int Length, bitnumber = 0;
        long TempCode = 0;
        List<ProtocolDataItem> DataItemList_Config = config.getDataItemList(commandItem.getIdentifier());
        Map<String, String> dataItemMap = commandItem.getDatacellParam();
        if (dataItemMap != null) {
            for (ProtocolDataItem dataItem : DataItemList_Config) {
                String DataItemCode = dataItem.getDataItemCode();
                DataItemValue = dataItem.getDefaultValue();
                if (DataItemValue.equals("YESTERDAY")) //抄上一天
                {
                    DataItemValue = UtilsBp.getYeasterday();
                }

                if (dataItemMap.containsKey(DataItemCode)) {
                    DataItemValue = dataItemMap.get(DataItemCode);
                }
                Format = dataItem.getFormat();
                Length = dataItem.getLength();
                IsGroupEnd = dataItem.getIsGroupEnd();
                bitnumber = dataItem.getBitNumber();
                FillDataBuffer(packet.getDataBuffer(), Format, DataItemValue, IsGroupEnd, Length, bitnumber);
            }
        }
    }

    private void putDataBuf_seq(PmPacket376 packet, CommandItem commandItem, int DataBufLenth) {
        String DataItemValue, Format, IsGroupEnd = "";
        int dataLenth = 0;
        int Length = 0;
        int bitnumber = 0;
        long TempCode = 0;
        Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItem.getIdentifier());
        Map<String, String> dataItemMap = commandItem.getDatacellParam();

        if (commandItem.getCircleLevel() == 1) {
            CircleDataItems circleDIs = commandItem.getCircleDataItems();
            if (circleDIs != null) {
                List<DataItemGroup> groupList = circleDIs.getDataItemGroups();
                for (DataItemGroup group : groupList) {
                    List<DataItem> dataItemList = group.getDataItemList();
                    for (DataItem dataItem : dataItemList) {
                        String DataItemCode = dataItem.getDataItemCode();

                        if ((Long.valueOf(DataItemCode) - TempCode > 10000) && (TempCode != 0)) {
                            ProtocolDataItem TempdataItem = DataItemMap_Config.get(String.valueOf(TempCode + 10000).substring(0, 10));
                            DataItemValue = TempdataItem.getDefaultValue();
                            Format = TempdataItem.getFormat();
                            Length = TempdataItem.getLength();
                            IsGroupEnd = TempdataItem.getIsGroupEnd();
                            bitnumber = TempdataItem.getBitNumber();
                            FillDataBuffer(packet.getDataBuffer(), Format, DataItemValue, IsGroupEnd, Length, bitnumber);
                        }
                        ProtocolDataItem protocoldataItem = DataItemMap_Config.get(DataItemCode.substring(0, 10));
                        DataItemValue = dataItem.getDataItemValue();
                        Format = protocoldataItem.getFormat();
                        Length = protocoldataItem.getLength();
                        IsGroupEnd = protocoldataItem.getIsGroupEnd();
                        bitnumber = protocoldataItem.getBitNumber();
                        FillDataBuffer(packet.getDataBuffer(), Format, DataItemValue, IsGroupEnd, Length, bitnumber);
                        TempCode = Long.valueOf(DataItemCode);
                    }
                }
            }
        }
    }

    public void FillDataBuffer(PmPacketData packetdata, String Format, String DataItemValue, String IsGroupEnd, int Length, int bitnumber) {
        if (Format.equals("HEX")) {
            packetdata.putBin(BcdUtils.stringToByte(UtilsBp.lPad(DataItemValue, "0", 2)), Length);
        } else if (Format.equals("BIN")) {
            packetdata.putBin(Integer.parseInt(DataItemValue), Length);
        } else if (Format.equals("IPPORT")) {
            packetdata.putIPPORT(DataItemValue);
        } else if (Format.equals("IP")) {
            packetdata.putIP(DataItemValue);
        } else if (Format.equals("TEL")) {
            packetdata.putTEL(DataItemValue);
        } else if (Format.equals("BS8")) {
            packetdata.putBS8(DataItemValue);
        } else if (Format.equals("GROUP_BS8")) {
            groupValue += DataItemValue;
            // String IsGroupEnd = dataItem.getIsGroupEnd();
            if (IsGroupEnd.equals("1")) {
                packetdata.putBS8(groupValue);
                groupValue = "";
            }
        } else if (Format.equals("GROUP_BIN")) {
            groupBinValue += Integer.parseInt(DataItemValue) << (bits - bitnumber);
            bits -= bitnumber;
            if (IsGroupEnd.equals("1")) {
                packetdata.put((byte) groupBinValue);
                bits = 8;
                groupBinValue = 0;
            }
        } else if (Format.equals("BS24")) {
            packetdata.putBS24(DataItemValue);
        } else if (Format.equals("BS64")) {
            packetdata.putBS64(DataItemValue);
        } else if (Format.equals("ASCII")) {
            packetdata.putAscii(DataItemValue, Length);
        } else if (Format.equals("A1")) {
            packetdata.putA1(new DataTypeA1(DataItemValue));
        } else if (Format.equals("A2")) {
            packetdata.putA2(new DataTypeA2(Double.parseDouble(DataItemValue)));
        } else if (Format.equals("A3")) {
            packetdata.putA3(new DataTypeA3(Long.parseLong(DataItemValue)));
        } else if (Format.equals("A4")) {
            packetdata.putA4(new DataTypeA4(Byte.parseByte(DataItemValue)));
        } else if (Format.equals("A5")) {
            packetdata.putA5(new DataTypeA5(Float.parseFloat(DataItemValue)));
        } else if (Format.equals("A6")) {
            packetdata.putA6(new DataTypeA6(Float.parseFloat(DataItemValue)));
        } else if (Format.equals("A7")) {
            packetdata.putA7(new DataTypeA7(Float.parseFloat(DataItemValue)));
        } else if (Format.equals("A8")) {
            packetdata.putA8(new DataTypeA8(Integer.parseInt(DataItemValue)));
        } else if (Format.equals("A9")) {
            packetdata.putA9(new DataTypeA9(Double.parseDouble(DataItemValue)));
        } else if (Format.equals("A10")) {
            packetdata.putA10(new DataTypeA10(Long.parseLong(DataItemValue)));
        } else if (Format.equals("A11")) {
            packetdata.putA11(new DataTypeA11(Double.parseDouble(DataItemValue)));
        } else if (Format.equals("A12")) {
            packetdata.putA12(new DataTypeA12(Long.parseLong(DataItemValue)));
        } else if (Format.equals("A13")) {
            packetdata.putA13(new DataTypeA13(Double.parseDouble(DataItemValue)));
        } else if (Format.equals("A14")) {
            packetdata.putA14(new DataTypeA14(Double.parseDouble(DataItemValue)));
        } else if (Format.equals("A15")) {
            packetdata.putA15(new DataTypeA15(DataItemValue, "yyyy-MM-dd HH:mm:ss"));
        } else if (Format.equals("A16")) {
            packetdata.putA16(new DataTypeA16(DataItemValue, "dd HH:mm:ss"));
        } else if (Format.equals("A17")) {
            packetdata.putA17(new DataTypeA17(DataItemValue, "MM-dd HH:mm"));
        } else if (Format.equals("A18")) {
            packetdata.putA18(new DataTypeA18(DataItemValue, "dd HH:mm"));
        } else if (Format.equals("A19")) {
            packetdata.putA19(new DataTypeA19(DataItemValue, "HH:mm"));
        } else if (Format.equals("A20")) {
            packetdata.putA20(new DataTypeA20(DataItemValue, "yyyy-MM-dd"));
        } else if (Format.equals("A21")) {
            packetdata.putA21(new DataTypeA21(DataItemValue, "yyyy-mm"));
        } else if (Format.equals("A22")) {
            packetdata.putA22(new DataTypeA22(Float.parseFloat(DataItemValue)));
        } else if (Format.equals("A23")) {
            packetdata.putA23(new DataTypeA23(Float.parseFloat(DataItemValue)));
        } else if (Format.equals("A24")) {
            packetdata.putA24(new DataTypeA24(DataItemValue, "dd HH"));
        } else if (Format.equals("A25")) {
            packetdata.putA25(new DataTypeA25(Double.parseDouble(DataItemValue)));
        } else if (Format.equals("A26")) {
            packetdata.putA26(new DataTypeA26(Float.parseFloat(DataItemValue)));
        } else if (Format.equals("A27")) {
            packetdata.putA27(new DataTypeA27(Long.parseLong(DataItemValue)));
        } else if (Format.equals("A29")) {
            packetdata.putBcdInt(Long.parseLong(DataItemValue), 1);
        } else if (Format.equals("DATE_LOUBAO")) {
            try {
                packetdata.put(UtilsBp.String2DateArray(DataItemValue, "yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException ex) {
                log.error(ex.getMessage());
            }
        }

    }

    public void decodeData(PmPacket376 packet, Map<String, Map<String, String>> results) {
        this.decoder.decode(packet, results);
    }

    public void decodeData_TransMit(PmPacket376 packet, Map<String, Map<String, String>> results,boolean IsWriteBack) {
        this.decoder.decode_TransMit(packet, results,IsWriteBack);
    }

    public void decodeData_TransMit(PmPacket376 packet, Dto postData) {
        this.decoder.decode_TransMit(packet, postData);
    }

    public void decodeDataDB(PmPacket376 packet, Dto postData) {
        this.decoder.decode(packet, postData);
    }

    /**
     * @return the config
     */
    public ProtocolConfig getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(ProtocolConfig config) {
        this.config = config;
    }

    /**
     * @return the decoder
     */
    public Decoder getDecoder() {
        return decoder;
    }

    /**
     * @param decoder the decoder to set
     */
    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }
}
