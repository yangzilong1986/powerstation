/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import pep.bp.realinterface.conf.ProtocolConfig;
import pep.bp.realinterface.conf.ProtocolDataItem;
import pep.bp.realinterface.mto.CircleDataItems;
import pep.bp.realinterface.mto.CollectObject;
import pep.bp.realinterface.mto.CommandItem;
import pep.bp.realinterface.mto.DataItem;
import pep.bp.realinterface.mto.DataItemGroup;
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

/**
 *
 * @author Thinkpad
 */
public class Converter {

    private static final byte FUNCODE_DOWM_1 = 1;//PRM =1 功能码：1 （发送/确认）
    private String groupValue = "";
    private int groupBinValue = 0;
    byte bits = 8;


    
    public void CollectObject2Packet(CollectObject obj, PmPacket376 packet, byte AFN, StringBuffer gpMark, StringBuffer commandMark) {

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
                    InjectDataItem(packet, commandItem, commandItem.getCircleLevel());
                }
            }
            if (AFN == AFNType.AFN_RESET || AFN == AFNType.AFN_SETPARA || AFN == AFNType.AFN_TRANSMIT)//消息认证码字段PW
            {
                packet.setAuthorize(new Authorize());
            }
        }
        packet.setTpv(new TimeProtectValue());//时间标签
    }

    public List<PmPacket376> CollectObject2PacketList(CollectObject obj, byte AFN, StringBuffer gpMark, StringBuffer commandMark,int CmdItemNum) {
        List<PmPacket376> results = new ArrayList<PmPacket376>();
        PmPacket376 packet =null;
        int Index = 0;
        int[] MpSn = obj.getMpSn();

        for (int i = 0; i <= MpSn.length - 1; i++) {
            gpMark.append(String.valueOf(MpSn[i]) + "#");
            List<CommandItem> CommandItems = obj.getCommandItems();
            for (CommandItem commandItem : CommandItems) {
                if(Index % CmdItemNum == 0)
                {
                    packet = new PmPacket376();
                    packet.setAfn(AFN);//AFN
                    packet.getAddress().setRtua(obj.getLogicalAddr()); //逻辑地址
                    packet.getControlCode().setIsUpDirect(false);
                    packet.getControlCode().setIsOrgniger(true);
                    packet.getControlCode().setFunctionKey(FUNCODE_DOWM_1);
                    packet.getControlCode().setIsDownDirectFrameCountAvaliable(true);
                    packet.getControlCode().setDownDirectFrameCount((byte) 0);
                    packet.getSeq().setIsTpvAvalibe(true);
                }
                commandMark.append(commandItem.getIdentifier() + "#");
                PmPacket376DA da = new PmPacket376DA(MpSn[i]);
                PmPacket376DT dt = new PmPacket376DT();
                int fn = Integer.parseInt(commandItem.getIdentifier().substring(4, 8));//10+03+0002(protocolcode+afn+fn)
                dt.setFn(fn);
                packet.getDataBuffer().putDA(da);
                packet.getDataBuffer().putDT(dt);
                if ((AFN == AFNType.AFN_GETPARA) || (AFN == AFNType.AFN_SETPARA) || (AFN == AFNType.AFN_READDATA1)) 
                    InjectDataItem(packet, commandItem, commandItem.getCircleLevel());
                if(CmdItemNum % CmdItemNum == 0)
                {
                    if (AFN == AFNType.AFN_RESET || AFN == AFNType.AFN_SETPARA || AFN == AFNType.AFN_TRANSMIT)//消息认证码字段PW
                        packet.setAuthorize(new Authorize());
                    packet.setTpv(new TimeProtectValue());//时间标签
                    results.add(packet);
                }                
                Index++;
            }
        }
        return results;
    }

    

    private void putDataBuf(PmPacket376 packet, CommandItem commandItem) {
        String DataItemValue, Format, IsGroupEnd = "";
        int Length, bitnumber = 0;
        long TempCode = 0;
        ProtocolConfig config = ProtocolConfig.getInstance();
        Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItem.getIdentifier());
        Map<String, String> dataItemMap = commandItem.getDatacellParam();
        if (dataItemMap != null) {

            Iterator iterator = dataItemMap.keySet().iterator();
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
                FillDataBuffer(packet, Format, DataItemValue, IsGroupEnd, Length, bitnumber);
            }
        }
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
                            FillDataBuffer(packet, Format, DataItemValue, IsGroupEnd, Length, bitnumber);
                        }
                        ProtocolDataItem protocoldataItem = DataItemMap_Config.get(DataItemCode.substring(0, 10));
                        DataItemValue = dataItem.getDataItemValue();
                        Format = protocoldataItem.getFormat();
                        Length = protocoldataItem.getLength();
                        IsGroupEnd = protocoldataItem.getIsGroupEnd();
                        bitnumber = protocoldataItem.getBitNumber();
                        FillDataBuffer(packet, Format, DataItemValue, IsGroupEnd, Length, bitnumber);
                        TempCode = Long.valueOf(DataItemCode);
                    }
                }
            }
        }
    }

    private void InjectDataItem(PmPacket376 packet, CommandItem commandItem, int circlelevle) {
        long TempCode = 0;
        String DataItemValue, Format, IsGroupEnd = "";
        int Length, bitnumber = 0;
        putDataBuf(packet, commandItem);
    }

    private void FillDataBuffer(PmPacket376 packet, String Format, String DataItemValue, String IsGroupEnd, int Length, int bitnumber) {
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
                packet.getDataBuffer().put((byte) groupBinValue);
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

    public Map<String, Map<String, String>> decodeData(PmPacket376 packet, Map<String, Map<String, String>> results) {
        String key = "";
        String GroupValue = "";

        ProtocolConfig config = ProtocolConfig.getInstance();//获取配置文件对象
        String logicAddress = packet.getAddress().getRtua();
        PmPacketData dataBuffer = packet.getDataBuffer();
        dataBuffer.rewind();
        while (dataBuffer.HaveDate()) {
            PmPacket376DA da = new PmPacket376DA();
            PmPacket376DT dt = new PmPacket376DT();
            dataBuffer.getDA(da);
            dataBuffer.getDT(dt);
            byte afn = packet.getAfn();
            if (afn == 0x0A) {
                afn = 0x04;
            }
            String commandItemCode = "10" + String.format("%02d", afn) + String.format("%04d", dt.getFn());
            key = logicAddress + "#" + String.valueOf(da.getPn()) + "#" + commandItemCode;

            Map<String, String> dataItems = results.get(commandItemCode);
            if (dataItems == null) {
                dataItems = new TreeMap();
            }
            Map<String, ProtocolDataItem> DataItemMap_Config = config.getDataItemMap(commandItemCode);
            Iterator iterator = DataItemMap_Config.keySet().iterator();

            while (iterator.hasNext()) {
                String DataItemCode = (String) iterator.next();
                ProtocolDataItem dataItem = DataItemMap_Config.get(DataItemCode);
                int Len = dataItem.getLength();
                String Format = dataItem.getFormat();
                int bitnumber = dataItem.getBitNumber();
                String IsGroupEnd = dataItem.getIsGroupEnd();
                if (Format.equals("BIN")) {
                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getBin(Len)));
                } else if (Format.equals("IPPORT")) {
                    dataItems.put(DataItemCode, dataBuffer.getIPPORT());
                } else if (Format.equals("IP")) {
                    dataItems.put(DataItemCode, dataBuffer.getIP());
                } else if (Format.equals("TEL")) {
                    dataItems.put(DataItemCode, dataBuffer.getTEL());
                } else if (Format.equals("BS8")) {
                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getBS8()));
                } else if (Format.equals("GROUP_BS8")) {
                    if (GroupValue.length() == 0) {
                        GroupValue = dataBuffer.getBS8();
                    }
                    dataItems.put(DataItemCode, GroupValue.substring(0, Len));
                    GroupValue = GroupValue.substring(Len, GroupValue.length());
                    if (IsGroupEnd.equals("1")) {
                        GroupValue = "";
                    }
                } else if (Format.equals("GROUP_BIN")) {
                    if (groupBinValue == 0) {
                        groupBinValue = dataBuffer.get();
                    }
                    if (groupBinValue < 0) {
                        groupBinValue = groupBinValue + 256;
                    }
                    byte resultValue = (byte) (groupBinValue >> (bits - bitnumber));
                    groupBinValue = groupBinValue - (resultValue << (bits - bitnumber));
                    bits -= bitnumber;
                    dataItems.put(DataItemCode, String.valueOf(resultValue));
                    if (IsGroupEnd.equals("1")) {
                        groupBinValue = 0;
                        bits = 8;
                    }
                } else if (Format.equals("BS24")) {
                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getBS24()));
                } else if (Format.equals("BS64")) {
                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getBS64()));
                } else if (Format.equals("ASCII")) {
                    dataItems.put(DataItemCode, String.valueOf(dataBuffer.getAscii(Len)));
                } else if (Format.equals("A1")) {
                    dataItems.put(DataItemCode, dataBuffer.getA1().toString());
                } else if (Format.equals("A2")) {
                    dataItems.put(DataItemCode, dataBuffer.getA2().toString());
                } else if (Format.equals("A3")) {
                    dataItems.put(DataItemCode, dataBuffer.getA3().toString());
                } else if (Format.equals("A4")) {
                    dataItems.put(DataItemCode, dataBuffer.getA4().toString());
                } else if (Format.equals("A5")) {
                    dataItems.put(DataItemCode, dataBuffer.getA5().toString());
                } else if (Format.equals("A6")) {
                    dataItems.put(DataItemCode, dataBuffer.getA6().toString());
                } else if (Format.equals("A7")) {
                    dataItems.put(DataItemCode, dataBuffer.getA7().toString());
                } else if (Format.equals("A8")) {
                    dataItems.put(DataItemCode, dataBuffer.getA8().toString());
                } else if (Format.equals("A9")) {
                    dataItems.put(DataItemCode, dataBuffer.getA9().toString());
                } else if (Format.equals("A10")) {
                    dataItems.put(DataItemCode, dataBuffer.getA10().toString());
                } else if (Format.equals("A11")) {
                    dataItems.put(DataItemCode, dataBuffer.getA11().toString());
                } else if (Format.equals("A12")) {
                    dataItems.put(DataItemCode, dataBuffer.getA12().toString());
                } else if (Format.equals("A13")) {
                    dataItems.put(DataItemCode, dataBuffer.getA13().toString());
                } else if (Format.equals("A14")) {
                    dataItems.put(DataItemCode, dataBuffer.getA14().toString());
                } else if (Format.equals("A15")) {
                    dataItems.put(DataItemCode, dataBuffer.getA15().toString());
                } else if (Format.equals("A16")) {
                    dataItems.put(DataItemCode, dataBuffer.getA16().toString());
                } else if (Format.equals("A17")) {
                    dataItems.put(DataItemCode, dataBuffer.getA17().toString());
                } else if (Format.equals("A18")) {
                    dataItems.put(DataItemCode, dataBuffer.getA18().toString());
                } else if (Format.equals("A19")) {
                    dataItems.put(DataItemCode, dataBuffer.getA19().toString());
                } else if (Format.equals("A20")) {
                    dataItems.put(DataItemCode, dataBuffer.getA20().toString());
                } else if (Format.equals("A21")) {
                    dataItems.put(DataItemCode, dataBuffer.getA21().toString());
                } else if (Format.equals("A22")) {
                    dataItems.put(DataItemCode, dataBuffer.getA22().toString());
                } else if (Format.equals("A23")) {
                    dataItems.put(DataItemCode, dataBuffer.getA23().toString());
                } else if (Format.equals("A24")) {
                    dataItems.put(DataItemCode, dataBuffer.getA24().toString());
                } else if (Format.equals("A25")) {
                    dataItems.put(DataItemCode, dataBuffer.getA25().toString());
                } else if (Format.equals("A26")) {
                    dataItems.put(DataItemCode, dataBuffer.getA26().toString());
                } else if (Format.equals("A27")) {
                    dataItems.put(DataItemCode, dataBuffer.getA27().toString());
                }
            }
            if (!results.containsKey(key)) {
                results.put(key, dataItems);
            }
        }
        return results;
    }
}
