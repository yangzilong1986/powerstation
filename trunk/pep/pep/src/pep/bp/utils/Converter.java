/*
 * 组帧/解帧的门面类
 */
package pep.bp.utils;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import pep.bp.model.Dto;
import pep.bp.realinterface.conf.ProtocolConfig;
import pep.bp.realinterface.conf.ProtocolDataItem;
import pep.bp.realinterface.mto.CircleDataItems;
import pep.bp.realinterface.mto.CollectObject;
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
import pep.meter645.Gb645MeterPacket;
import pep.system.SystemConst;

/**
 *
 * @author Thinkpad
 */
public class Converter {

    private static final byte FUNCODE_DOWM_1 = 1;//PRM =1 功能码：1 （发送/确认）
    private String groupValue = "";
    private int groupBinValue = 0;
    byte bits = 8;
    private ProtocolConfig config;
    private Decoder decoder;

    public void CollectObject2Packet(CollectObject obj, PmPacket376 packet, byte AFN, StringBuffer gpMark, StringBuffer commandMark) {

        preSetPacket(packet, AFN, obj.getLogicalAddr());
        int[] MpSn = obj.getMpSn();

        for (int i = 0; i <= MpSn.length - 1; i++) {
            gpMark.append(String.valueOf(MpSn[i]) + "#");
            List<CommandItem> CommandItems = obj.getCommandItems();
            for (CommandItem commandItem : CommandItems) {
                commandMark.append(commandItem.getIdentifier() + "#");
                PmPacket376DA da = new PmPacket376DA(MpSn[i]);
                PmPacket376DT dt = new PmPacket376DT();
                int fn = Integer.parseInt(commandItem.getIdentifier().substring(4, 8));//10+03+0002(protocolcode+afn+fn)
                dt.setFn(fn);
                packet.getDataBuffer().putDA(da);
                packet.getDataBuffer().putDT(dt);
                if ((AFN == AFNType.AFN_GETPARA) || (AFN == AFNType.AFN_SETPARA) || (AFN == AFNType.AFN_READDATA1)) {
                    putDataBuf(packet, commandItem);
                }
            }
            if (AFN == AFNType.AFN_RESET || AFN == AFNType.AFN_SETPARA || AFN == AFNType.AFN_TRANSMIT)//消息认证码字段PW
            {
                packet.setAuthorize(new Authorize());
            }
        }
        packet.setTpv(new TimeProtectValue());//时间标签
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
                    if ((Index-1) % CmdItemNum == 0) {
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
                    if ((AFN == AFNType.AFN_GETPARA) || (AFN == AFNType.AFN_SETPARA) || (AFN == AFNType.AFN_READDATA1)) {
                        putDataBuf(packet, commandItem);
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

    public List<PmPacket376> CollectObject_TransMit2PacketList(CollectObject obj, StringBuffer commandMark) {
        List<PmPacket376> results = new ArrayList<PmPacket376>();
        PmPacket376 packet = new PmPacket376();
        int Index = 0;
        int DataBuffLen = SystemConst.MAX_PACKET_LEN - 16 - 22;//[68+L+L+68+C+A+AFN+SEQ+TP+PW+CS+16]
        List<CommandItem> CommandItems = obj.getCommandItems();
        for (CommandItem commandItem : CommandItems) {
            preSetPacket(packet, AFNType.AFN_TRANSMIT, obj.getLogicalAddr());
            commandMark.append(commandItem.getIdentifier() + "#");
            PmPacket376DA da = new PmPacket376DA(0);
            PmPacket376DT dt = new PmPacket376DT();
            int fn = Integer.parseInt(commandItem.getIdentifier().substring(4, 8));//10+03+0002(protocolcode+afn+fn)
            dt.setFn(fn);
            packet.getDataBuffer().putDA(da);
            packet.getDataBuffer().putDT(dt);
            putDataBuf(packet, commandItem);

            packet.setAuthorize(new Authorize());
            packet.setTpv(new TimeProtectValue());//时间标签
            results.add(packet);

            Index++;
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
                if ((Index % ActualgroupNumber == 0) && (Index > 1) && (CanPacket)) {
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

    public void putDataBuf(PmPacket376 packet, CommandItem commandItem) {
        String DataItemValue, Format, IsGroupEnd = "";
        int Length, bitnumber = 0;
        long TempCode = 0;
        Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItem.getIdentifier());
        Map<String, String> dataItemMap = commandItem.getDatacellParam();
        if (dataItemMap != null) {

            Iterator iterator = DataItemMap_Config.keySet().iterator();
            while (iterator.hasNext()) {
                ProtocolDataItem dataItem;
                String DataItemCode = (String) iterator.next();
                dataItem = DataItemMap_Config.get(DataItemCode);
                DataItemValue = dataItem.getDefaultValue();
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
        if (Format.equals("BIN")) {
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
            packetdata.putA25(new DataTypeA25(Long.parseLong(DataItemValue)));
        } else if (Format.equals("A26")) {
            packetdata.putA26(new DataTypeA26(Float.parseFloat(DataItemValue)));
        } else if (Format.equals("A27")) {
            packetdata.putA27(new DataTypeA27(Long.parseLong(DataItemValue)));
        }
    }


    public void decodeData(PmPacket376 packet, Map<String, Map<String, String>> results){
        this.decoder.decode(packet, results);
    }

//    public void decodeData(PmPacket376 packet, Map<String, Map<String, String>> results) {
//        String key = "";
//        String GroupValue = "";
//        String logicAddress = packet.getAddress().getRtua();
//        PmPacketData dataBuffer = packet.getDataBuffer();
//        dataBuffer.rewind();
//        while (dataBuffer.HaveDate()) {
//            PmPacket376DA da = new PmPacket376DA();
//            PmPacket376DT dt = new PmPacket376DT();
//            dataBuffer.getDA(da);
//            dataBuffer.getDT(dt);
//            byte afn = packet.getAfn();
//            String commandItemCode = "10" + String.format("%02d", afn) + String.format("%04d", dt.getFn());
//            if (afn == 0x0A) {
//                afn = 0x04;
//            }
//            key = logicAddress + "#" + String.valueOf(da.getPn()) + "#" + commandItemCode;
//            Map<String, String> dataItems = results.get(commandItemCode);
//            if (dataItems == null) {
//                dataItems = new TreeMap();
//            }
//            Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItemCode);
//            Iterator iterator = DataItemMap_Config.keySet().iterator();
//
//            while (iterator.hasNext()) {
//                String DataItemCode = (String) iterator.next();
//                ProtocolDataItem dataItem = DataItemMap_Config.get(DataItemCode);
//                int Len = dataItem.getLength();
//                String Format = dataItem.getFormat();
//                int bitnumber = dataItem.getBitNumber();
//                String IsGroupEnd = dataItem.getIsGroupEnd();
//                if (Format.equals("BIN")) {
//                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getBin(Len)));
//                } else if (Format.equals("IPPORT")) {
//                    dataItems.put(DataItemCode, dataBuffer.getIPPORT());
//                } else if (Format.equals("IP")) {
//                    dataItems.put(DataItemCode, dataBuffer.getIP());
//                } else if (Format.equals("TEL")) {
//                    dataItems.put(DataItemCode, dataBuffer.getTEL());
//                } else if (Format.equals("BS8")) {
//                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getBS8()));
//                } else if (Format.equals("GROUP_BS8")) {
//                    if (GroupValue.length() == 0) {
//                        GroupValue = dataBuffer.getBS8();
//                    }
//                    dataItems.put(DataItemCode, GroupValue.substring(0, bitnumber));
//                    GroupValue = GroupValue.substring(bitnumber, GroupValue.length());
//                    if (IsGroupEnd.equals("1")) {
//                        GroupValue = "";
//                    }
//                } else if (Format.equals("GROUP_BIN")) {
//                    if (groupBinValue == 0) {
//                        groupBinValue = dataBuffer.get();
//                    }
//                    if (groupBinValue < 0) {
//                        groupBinValue = groupBinValue + 256;
//                    }
//                    byte resultValue = (byte) (groupBinValue >> (bits - bitnumber));
//                    groupBinValue = groupBinValue - (resultValue << (bits - bitnumber));
//                    bits -= bitnumber;
//                    dataItems.put(DataItemCode, String.valueOf(resultValue));
//                    if (IsGroupEnd.equals("1")) {
//                        groupBinValue = 0;
//                        bits = 8;
//                    }
//                } else if (Format.equals("BS24")) {
//                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getBS24()));
//                } else if (Format.equals("BS64")) {
//                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getBS64()));
//                } else if (Format.equals("ASCII")) {
//                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getAscii(Len)));
//                } else if (Format.equals("A1")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA1().toString());
//                } else if (Format.equals("A2")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA2().toString());
//                } else if (Format.equals("A3")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA3().toString());
//                } else if (Format.equals("A4")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA4().toString());
//                } else if (Format.equals("A5")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA5().toString());
//                } else if (Format.equals("A6")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA6().toString());
//                } else if (Format.equals("A7")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA7().toString());
//                } else if (Format.equals("A8")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA8().toString());
//                } else if (Format.equals("A9")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA9().toString());
//                } else if (Format.equals("A10")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA10().toString());
//                } else if (Format.equals("A11")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA11().toString());
//                } else if (Format.equals("A12")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA12().toString());
//                } else if (Format.equals("A13")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA13().toString());
//                } else if (Format.equals("A14")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA14().toString());
//                } else if (Format.equals("A15")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA15().toString());
//                } else if (Format.equals("A16")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA16().toString());
//                } else if (Format.equals("A17")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA17().toString());
//                } else if (Format.equals("A18")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA18().toString());
//                } else if (Format.equals("A19")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA19().toString());
//                } else if (Format.equals("A20")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA20().toString());
//                } else if (Format.equals("A21")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA21().toString());
//                } else if (Format.equals("A22")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA22().toString());
//                } else if (Format.equals("A23")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA23().toString());
//                } else if (Format.equals("A24")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA24().toString());
//                } else if (Format.equals("A25")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA25().toString());
//                } else if (Format.equals("A26")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA26().toString());
//                } else if (Format.equals("A27")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA27().toString());
//                }
//            }
//            if (!results.containsKey(key)) {
//                results.put(key, dataItems);
//            }
//        }
//    }


    public void decodeData_TransMit(PmPacket376 packet, Map<String, Map<String, String>> results){
        this.decoder.decode_TransMit(packet, results);
    }
//    public void decodeData_TransMit(PmPacket376 packet, Map<String, Map<String, String>> results) {
//        String key = "";
//        String GroupValue = "";
//
//        String logicAddress = packet.getAddress().getRtua();
//        PmPacketData dataBuffer = packet.getDataBuffer();
//        dataBuffer.rewind();
//        while (dataBuffer.HaveDate()) {
//            PmPacket376DA da = new PmPacket376DA();
//            PmPacket376DT dt = new PmPacket376DT();
//            dataBuffer.getDA(da);
//            dataBuffer.getDT(dt);
//            byte afn = packet.getAfn();
//            String commandItemCode = "10" + String.format("%02d", afn) + String.format("%04d", dt.getFn());
//            if (afn == 0x0A) {
//                afn = 0x04;
//            }
//            if(afn == 0X10){  //透明转发，特殊处理
//                dataBuffer.getBin(1);//终端通信端口号
//                dataBuffer.getBS8();//透明转发通信控制字
//                dataBuffer.getBS8();//透明转发接收等待报文超时时间
//                dataBuffer.getBin(1);//透明转发接收等待字节超时时间
//                long len = dataBuffer.getBin(2);//透明转发内容字节数k
//                byte[] databuff = dataBuffer.getValue();
//                int head = Gb645MeterPacket.getMsgHeadOffset(databuff, 0);
//                Gb645MeterPacket pack = Gb645MeterPacket.getPacket(databuff, head);
//                dataBuffer = pack.getDataAsPmPacketData();
//            }
//
//            key = logicAddress + "#" + String.valueOf(da.getPn()) + "#" + commandItemCode;
//
//            Map<String, String> dataItems = results.get(commandItemCode);
//            if (dataItems == null) {
//                dataItems = new TreeMap();
//            }
//            Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItemCode);
//            Iterator iterator = DataItemMap_Config.keySet().iterator();
//
//            while (iterator.hasNext()) {
//                String DataItemCode = (String) iterator.next();
//                ProtocolDataItem dataItem = DataItemMap_Config.get(DataItemCode);
//                int Len = dataItem.getLength();
//                String Format = dataItem.getFormat();
//                int bitnumber = dataItem.getBitNumber();
//                String IsGroupEnd = dataItem.getIsGroupEnd();
//                if (Format.equals("BIN")) {
//                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getBin(Len)));
//                } else if (Format.equals("IPPORT")) {
//                    dataItems.put(DataItemCode, dataBuffer.getIPPORT());
//                } else if (Format.equals("IP")) {
//                    dataItems.put(DataItemCode, dataBuffer.getIP());
//                } else if (Format.equals("TEL")) {
//                    dataItems.put(DataItemCode, dataBuffer.getTEL());
//                } else if (Format.equals("BS8")) {
//                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getBS8()));
//                } else if (Format.equals("GROUP_BS8")) {
//                    if (GroupValue.length() == 0) {
//                        GroupValue = dataBuffer.getBS8();
//                    }
//                    dataItems.put(DataItemCode, GroupValue.substring(0, bitnumber));
//                    GroupValue = GroupValue.substring(bitnumber, GroupValue.length());
//                    if (IsGroupEnd.equals("1")) {
//                        GroupValue = "";
//                    }
//                } else if (Format.equals("GROUP_BIN")) {
//                    if (groupBinValue == 0) {
//                        groupBinValue = dataBuffer.get();
//                    }
//                    if (groupBinValue < 0) {
//                        groupBinValue = groupBinValue + 256;
//                    }
//                    byte resultValue = (byte) (groupBinValue >> (bits - bitnumber));
//                    groupBinValue = groupBinValue - (resultValue << (bits - bitnumber));
//                    bits -= bitnumber;
//                    dataItems.put(DataItemCode, String.valueOf(resultValue));
//                    if (IsGroupEnd.equals("1")) {
//                        groupBinValue = 0;
//                        bits = 8;
//                    }
//                } else if (Format.equals("BS24")) {
//                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getBS24()));
//                } else if (Format.equals("BS64")) {
//                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getBS64()));
//                } else if (Format.equals("ASCII")) {
//                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getAscii(Len)));
//                } else if (Format.equals("A1")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA1().toString());
//                } else if (Format.equals("A2")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA2().toString());
//                } else if (Format.equals("A3")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA3().toString());
//                } else if (Format.equals("A4")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA4().toString());
//                } else if (Format.equals("A5")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA5().toString());
//                } else if (Format.equals("A6")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA6().toString());
//                } else if (Format.equals("A7")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA7().toString());
//                } else if (Format.equals("A8")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA8().toString());
//                } else if (Format.equals("A9")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA9().toString());
//                } else if (Format.equals("A10")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA10().toString());
//                } else if (Format.equals("A11")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA11().toString());
//                } else if (Format.equals("A12")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA12().toString());
//                } else if (Format.equals("A13")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA13().toString());
//                } else if (Format.equals("A14")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA14().toString());
//                } else if (Format.equals("A15")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA15().toString());
//                } else if (Format.equals("A16")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA16().toString());
//                } else if (Format.equals("A17")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA17().toString());
//                } else if (Format.equals("A18")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA18().toString());
//                } else if (Format.equals("A19")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA19().toString());
//                } else if (Format.equals("A20")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA20().toString());
//                } else if (Format.equals("A21")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA21().toString());
//                } else if (Format.equals("A22")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA22().toString());
//                } else if (Format.equals("A23")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA23().toString());
//                } else if (Format.equals("A24")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA24().toString());
//                } else if (Format.equals("A25")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA25().toString());
//                } else if (Format.equals("A26")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA26().toString());
//                } else if (Format.equals("A27")) {
//                    dataItems.put(DataItemCode, dataBuffer.getA27().toString());
//                }
//            }
//            if (!results.containsKey(key)) {
//                results.put(key, dataItems);
//            }
//        }
//
//    }

    public void decodeDataDB(PmPacket376 packet, Dto postData){
        this.decoder.decode(packet, postData);
    }

//    public void decodeDataDB(PmPacket376 packet, Dto postData) {
//
//        String key = "";
//        String GroupValue = "";
//
//        String logicAddress = packet.getAddress().getRtua();
//        postData.setLogicAddress(logicAddress) ;
//        PmPacketData dataBuffer = packet.getDataBuffer();
//        dataBuffer.rewind();
//        while (dataBuffer.HaveDate()) {
//            PmPacket376DA da = new PmPacket376DA();
//            PmPacket376DT dt = new PmPacket376DT();
//            dataBuffer.getDA(da);
//            dataBuffer.getDT(dt);
//            byte afn = packet.getAfn();
//            postData.setAfn(afn);
//            postData.AddGP(da.getPn());
//            String commandItemCode = "10" + String.format("%02d", afn) + String.format("%04d", dt.getFn());
//            Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItemCode);
//            Iterator iterator = DataItemMap_Config.keySet().iterator();
//
//            while (iterator.hasNext()) {
//                String DataItemCode = (String) iterator.next();
//                ProtocolDataItem dataItem = DataItemMap_Config.get(DataItemCode);
//                int Len = dataItem.getLength();
//                String Format = dataItem.getFormat();
//                int bitnumber = dataItem.getBitNumber();
//                String IsGroupEnd = dataItem.getIsGroupEnd();
//                if (Format.equals("BIN")) {
//                    postData.AddData(DataItemCode, String.valueOf(dataBuffer.getBin(Len)));
//                } else if (Format.equals("IPPORT")) {
//                    postData.AddData(DataItemCode, dataBuffer.getIPPORT());
//                } else if (Format.equals("IP")) {
//                    postData.AddData(DataItemCode, dataBuffer.getIP());
//                } else if (Format.equals("TEL")) {
//                    postData.AddData(DataItemCode, dataBuffer.getTEL());
//                } else if (Format.equals("BS8")) {
//                    postData.AddData(DataItemCode, String.valueOf(dataBuffer.getBS8()));
//                } else if (Format.equals("GROUP_BS8")) {
//                    if (GroupValue.length() == 0) {
//                        GroupValue = dataBuffer.getBS8();
//                    }
//                    postData.AddData(DataItemCode, GroupValue.substring(0, bitnumber));
//                    GroupValue = GroupValue.substring(bitnumber, GroupValue.length());
//                    if (IsGroupEnd.equals("1")) {
//                        GroupValue = "";
//                    }
//                } else if (Format.equals("GROUP_BIN")) {
//                    if (groupBinValue == 0) {
//                        groupBinValue = dataBuffer.get();
//                    }
//                    if (groupBinValue < 0) {
//                        groupBinValue = groupBinValue + 256;
//                    }
//                    byte resultValue = (byte) (groupBinValue >> (bits - bitnumber));
//                    groupBinValue = groupBinValue - (resultValue << (bits - bitnumber));
//                    bits -= bitnumber;
//                    postData.AddData(DataItemCode, String.valueOf(resultValue));
//                    if (IsGroupEnd.equals("1")) {
//                        groupBinValue = 0;
//                        bits = 8;
//                    }
//                } else if (Format.equals("BS24")) {
//                    postData.AddData(DataItemCode, String.valueOf(dataBuffer.getBS24()));
//                } else if (Format.equals("BS64")) {
//                    postData.AddData(DataItemCode, String.valueOf(dataBuffer.getBS64()));
//                } else if (Format.equals("ASCII")) {
//                    postData.AddData(DataItemCode, String.valueOf(dataBuffer.getAscii(Len)));
//                } else if (Format.equals("A1")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA1().toString());
//                } else if (Format.equals("A2")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA2().toString());
//                } else if (Format.equals("A3")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA3().toString());
//                } else if (Format.equals("A4")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA4().toString());
//                } else if (Format.equals("A5")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA5().toString());
//                } else if (Format.equals("A6")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA6().toString());
//                } else if (Format.equals("A7")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA7().toString());
//                } else if (Format.equals("A8")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA8().toString());
//                } else if (Format.equals("A9")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA9().toString());
//                } else if (Format.equals("A10")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA10().toString());
//                } else if (Format.equals("A11")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA11().toString());
//                } else if (Format.equals("A12")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA12().toString());
//                } else if (Format.equals("A13")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA13().toString());
//                } else if (Format.equals("A14")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA14().toString());
//                } else if (Format.equals("A15")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA15().toString());
//                } else if (Format.equals("A16")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA16().toString());
//                } else if (Format.equals("A17")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA17().toString());
//                } else if (Format.equals("A18")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA18().toString());
//                } else if (Format.equals("A19")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA19().toString());
//                } else if (Format.equals("A20")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA20().toString());
//                } else if (Format.equals("A21")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA21().toString());
//                } else if (Format.equals("A22")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA22().toString());
//                } else if (Format.equals("A23")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA23().toString());
//                } else if (Format.equals("A24")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA24().toString());
//                } else if (Format.equals("A25")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA25().toString());
//                } else if (Format.equals("A26")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA26().toString());
//                } else if (Format.equals("A27")) {
//                    postData.AddData(DataItemCode, dataBuffer.getA27().toString());
//                }
//            }
//        }
//    }

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
