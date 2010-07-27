/*
 * 解码器基类
 */
package pep.bp.utils.decoder;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import pep.bp.model.Dto;
import pep.bp.realinterface.conf.ProtocolConfig;
import pep.bp.realinterface.conf.ProtocolDataItem;
import pep.codec.protocol.gb.PmPacketData;

/**
 *
 * @author Thinkpad
 */
public abstract class Decoder {

    private int groupBinValue = 0;
    private byte bits = 8;
    protected ProtocolConfig config;

    protected void DecodeData2Map(String commandItemCode, Map<String, String> dataItems, PmPacketData dataBuffer) {
        String GroupValue = "";
        if (dataItems == null) {
            dataItems = new TreeMap();
        }
        dataBuffer.rewind();
        List<ProtocolDataItem> DataItemList_Config = config.getDataItemList(commandItemCode);
       
        for(ProtocolDataItem dataItem:DataItemList_Config){
            String DataItemCode = dataItem.getDataItemCode();
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
                dataItems.put(DataItemCode, GroupValue.substring(0, bitnumber));
                GroupValue = GroupValue.substring(bitnumber, GroupValue.length());
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
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA2().getValue()));
            } else if (Format.equals("A3")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA3().getValue()));
            } else if (Format.equals("A4")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA4().getValue()));
            } else if (Format.equals("A5")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA5().getValue()));
            } else if (Format.equals("A6")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA6().getValue()));
            } else if (Format.equals("A7")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA7().getValue()));
            } else if (Format.equals("A8")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA8().getValue()));
            } else if (Format.equals("A9")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA9().getValue()));
            } else if (Format.equals("A10")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA10().getValue()));
            } else if (Format.equals("A11")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA11().getValue()));
            } else if (Format.equals("A12")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA12().getValue()));
            } else if (Format.equals("A13")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA13().getValue()));
            } else if (Format.equals("A14")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA14().getValue()));
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
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA22().getValue()));
            } else if (Format.equals("A23")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA23().getValue()));
            } else if (Format.equals("A24")) {
                dataItems.put(DataItemCode, dataBuffer.getA24().toString());
            } else if (Format.equals("A25")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA25().getValue()));
            } else if (Format.equals("A26")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA26().getValue()));
            } else if (Format.equals("A27")) {
                dataItems.put(DataItemCode, String.valueOf(dataBuffer.getA27().getValue()));
            }
        }
    }


    protected void DecodeData2Dto(String commandItemCode, Dto dto, PmPacketData dataBuffer)
    {       String GroupValue = "";
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
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getBin(Len)));
                } else if (Format.equals("IPPORT")) {
                    dto.AddData(DataItemCode, dataBuffer.getIPPORT());
                } else if (Format.equals("IP")) {
                    dto.AddData(DataItemCode, dataBuffer.getIP());
                } else if (Format.equals("TEL")) {
                    dto.AddData(DataItemCode, dataBuffer.getTEL());
                } else if (Format.equals("BS8")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getBS8()));
                } else if (Format.equals("GROUP_BS8")) {
                    if (GroupValue.length() == 0) {
                        GroupValue = dataBuffer.getBS8();
                    }
                    dto.AddData(DataItemCode, GroupValue.substring(0, bitnumber));
                    GroupValue = GroupValue.substring(bitnumber, GroupValue.length());
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
                    dto.AddData(DataItemCode, String.valueOf(resultValue));
                    if (IsGroupEnd.equals("1")) {
                        groupBinValue = 0;
                        bits = 8;
                    }
                } else if (Format.equals("BS24")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getBS24()));
                } else if (Format.equals("BS64")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getBS64()));
                } else if (Format.equals("ASCII")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getAscii(Len)));
                } else if (Format.equals("A1")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA1()));
                } else if (Format.equals("A2")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA2()));
                } else if (Format.equals("A3")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA3()));
                } else if (Format.equals("A4")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA4()));
                } else if (Format.equals("A5")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA5()));
                } else if (Format.equals("A6")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA6()));
                } else if (Format.equals("A7")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA7()));
                } else if (Format.equals("A8")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA8()));
                } else if (Format.equals("A9")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA9()));
                } else if (Format.equals("A10")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA10()));
                } else if (Format.equals("A11")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA11()));
                } else if (Format.equals("A12")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA12()));
                } else if (Format.equals("A13")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA13()));
                } else if (Format.equals("A14")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA14()));
                } else if (Format.equals("A15")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA15()));
                } else if (Format.equals("A16")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA16()));
                } else if (Format.equals("A17")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA17()));
                } else if (Format.equals("A18")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA18()));
                } else if (Format.equals("A19")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA19()));
                } else if (Format.equals("A20")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA20()));
                } else if (Format.equals("A21")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA21()));
                } else if (Format.equals("A22")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA22()));
                } else if (Format.equals("A23")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA23()));
                } else if (Format.equals("A24")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA24()));
                } else if (Format.equals("A25")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA25()));
                } else if (Format.equals("A26")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA26()));
                } else if (Format.equals("A27")) {
                    dto.AddData(DataItemCode, String.valueOf(dataBuffer.getA27()));
                }
            }
    }


    public abstract void decode(Object pack, Map<String, Map<String, String>> results);

    public abstract void decode(Object pack, Dto postData);

    public abstract void decode_TransMit(Object pack, Map<String, Map<String, String>> results);

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
}