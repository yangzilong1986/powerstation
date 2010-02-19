package com.hzjbbis.fas.protocol.gw.codec;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.model.HostCommand;
import com.hzjbbis.fas.model.HostCommandResult;
import com.hzjbbis.fas.model.RtuData;
import com.hzjbbis.fas.model.RtuDataItem;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fas.protocol.gw.parse.*;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.RtuManage;

import java.util.ArrayList;
import java.util.List;

public class C0DMessageDecoder extends AbstractMessageDecoder {
    public Object decode(IMessage message) {
        List datas = new ArrayList();
        HostCommand hc = new HostCommand();

        int dataType = 0;
        try {
            String sTaskDateTime = "";
            String sTaskNum = null;
            String data = ParseTool.getGwData(message);
            MessageGw msg = (MessageGw) message;
            if (msg.head.seq_tpv == 1) data = data.substring(0, data.length() - 12);
            if (msg.head.c_acd == 1) data = data.substring(0, data.length() - 4);
            BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(((MessageGw) message).head.rtua);
            DataValue dataValue = new DataValue();
            while (data.length() >= 12) {
                int[] tn = DataItemParser.measuredPointParser(data.substring(0, 4));
                String[] codes = DataItemParser.dataCodeParser(data.substring(4, 8), "0D");
                data = data.substring(8);
                DataTimeTag dataTimeTag = new DataTimeTag();
                List codesTemp = new ArrayList();
                for (int i = 0; i < tn.length; ++i) {
                    for (int j = 0; j < codes.length; ++j) {
                        int k;
                        ProtocolDataItemConfig pdc = this.dataConfig.getDataItemConfig(codes[j]);
                        List childItems = pdc.getChildItems();
                        dataType = getGw0DDataType(Integer.parseInt(codes[j].substring(3, 6)));

                        if (dataType == 0) {
                            dataTimeTag = DataItemParser.getTaskDateTimeInfo(data.substring(0, 14), 2);
                            data = data.substring(14);
                            for (k = 0; k < dataTimeTag.getDataCount(); ++k)
                                if (childItems.size() > 0) {
                                    dataValue = DataItemParser.parseValue(data.substring(0, ((ProtocolDataItemConfig) childItems.get(0)).getLength() * 2), ((ProtocolDataItemConfig) childItems.get(0)).getFormat());
                                    data = data.substring(((ProtocolDataItemConfig) childItems.get(0)).getLength() * 2);
                                    sTaskDateTime = DataSwitch.IncreaseDateTime(dataTimeTag.getDataTime(), dataTimeTag.getDataDensity() * k, 2);
                                    if (message.isTask()) {
                                        RtuData rd = new RtuData();
                                        rd.setLogicAddress(rtu.getLogicAddress());
                                        rd.setTn("" + tn[i]);
                                        if (sTaskNum == null) {
                                            codesTemp.add(((ProtocolDataItemConfig) childItems.get(0)).getCode());
                                            sTaskNum = rtu.getTaskNum(codesTemp);
                                            codesTemp.clear();
                                        }
                                        if (sTaskNum == null) {
                                            String error = "终端：" + rtu.getLogicAddress() + "任务报文无法找到匹配的任务模版," + message.getRawPacketString();
                                            throw new MessageDecodeException(error);
                                        }

                                        rd.setTaskNum(sTaskNum);
                                        rd.setTime(sTaskDateTime);
                                        RtuDataItem rdItem = new RtuDataItem();
                                        rdItem.setCode(((ProtocolDataItemConfig) childItems.get(0)).getCode());
                                        rdItem.setValue(dataValue.getValue());
                                        rd.addDataList(rdItem);
                                        datas.add(rd);
                                    } else {
                                        HostCommandResult hcr = new HostCommandResult();
                                        hcr.setCode(((ProtocolDataItemConfig) childItems.get(0)).getCode());
                                        hcr.setTn("" + tn[i]);
                                        hcr.setValue(sTaskDateTime + "#" + dataValue.getValue());
                                        hc.addResult(hcr);
                                        hc.setStatus("1");
                                    }
                                }
                        } else {
                            if (dataType == 1) {
                                dataTimeTag = DataItemParser.getTaskDateTimeInfo(data.substring(0, 6), 3);
                                data = data.substring(6);
                            } else {
                                dataTimeTag = DataItemParser.getTaskDateTimeInfo(data.substring(0, 4), 4);
                                data = data.substring(4);
                            }
                            for (k = 0; k < childItems.size(); ++k) {
                                DataValue nValue;
                                int n;
                                int m;
                                HostCommandResult hcr;
                                ProtocolDataItemConfig pc = (ProtocolDataItemConfig) childItems.get(k);

                                if (pc.getCode().equals("0000000000")) {
                                    nValue = new DataValue();
                                    nValue = DataItemParser.parseValue(data.substring(0, pc.getLength() * 2), pc.getFormat());
                                    data = data.substring(pc.getLength() * 2);
                                    for (int m = k + 1; m < childItems.size(); ++m) {
                                        pc = (ProtocolDataItemConfig) childItems.get(m);
                                        for (int n = 0; n < Integer.parseInt(nValue.getValue()) + 1; ++n) {
                                            dataValue = DataItemParser.parseValue(data.substring(0, pc.getLength() * 2), pc.getFormat());
                                            data = data.substring(pc.getLength() * 2);
                                            hcr = new HostCommandResult();
                                            hcr.setCode(DataSwitch.StrStuff("0", 10, "" + (Long.parseLong(new StringBuilder().append(pc.getCode().substring(0, 8)).append("00").toString()) + n), "left"));
                                            hcr.setTn("" + tn[i]);
                                            hcr.setValue(dataValue.getValue());
                                            hc.addResult(hcr);
                                            codesTemp.add(hcr.getCode());
                                        }
                                    }
                                    break;
                                }

                                if (pc.getCode().equals("0000000001")) {
                                    nValue = new DataValue();
                                    nValue = DataItemParser.parseValue(data.substring(0, pc.getLength() * 2), pc.getFormat());
                                    data = data.substring(pc.getLength() * 2);
                                    for (n = 0; n < Integer.parseInt(nValue.getValue()) + 1; ++n) {
                                        for (m = k + 1; m < childItems.size(); ++m) {
                                            pc = (ProtocolDataItemConfig) childItems.get(m);
                                            dataValue = DataItemParser.parseValue(data.substring(0, pc.getLength() * 2), pc.getFormat());
                                            data = data.substring(pc.getLength() * 2);
                                            hcr = new HostCommandResult();
                                            hcr.setCode(DataSwitch.StrStuff("0", 10, "" + (Long.parseLong(new StringBuilder().append(pc.getCode().substring(0, 8)).append("00").toString()) + n), "left"));
                                            hcr.setTn("" + tn[i]);
                                            hcr.setValue(dataValue.getValue());
                                            hc.addResult(hcr);
                                            codesTemp.add(hcr.getCode());
                                        }
                                    }
                                    break;
                                }

                                if (pc.getCode().indexOf("X") > 0) {
                                    nValue = new DataValue();
                                    nValue = DataItemParser.parseValue(data.substring(0, pc.getLength() * 2), pc.getFormat());
                                    data = data.substring(pc.getLength() * 2);
                                    for (n = 0; n < Integer.parseInt(nValue.getValue()); ++n) {
                                        for (m = k + 1; m < childItems.size(); ++m) {
                                            pc = (ProtocolDataItemConfig) childItems.get(m);
                                            dataValue = DataItemParser.parseValue(data.substring(0, pc.getLength() * 2), pc.getFormat());
                                            data = data.substring(pc.getLength() * 2);
                                            hcr = new HostCommandResult();
                                            hcr.setCode(DataSwitch.StrStuff("0", 10, "" + (Long.parseLong(pc.getCode()) + n * 10), "left"));
                                            hcr.setTn("" + tn[i]);
                                            hcr.setValue(dataValue.getValue());
                                            hc.addResult(hcr);
                                            codesTemp.add(hcr.getCode());
                                        }
                                    }
                                    break;
                                }

                                dataValue = DataItemParser.parseValue(data.substring(0, pc.getLength() * 2), pc.getFormat());
                                data = data.substring(pc.getLength() * 2);
                                HostCommandResult hcr = new HostCommandResult();
                                hcr.setCode(pc.getCode());
                                hcr.setTn("" + tn[i]);
                                hcr.setValue(dataValue.getValue());
                                hc.addResult(hcr);
                                codesTemp.add(hcr.getCode());
                            }
                        }

                    }

                    if ((dataType == 0) || (codesTemp.size() <= 0)) continue;
                    if (message.isTask()) {
                        RtuData rd = new RtuData();
                        rd.setLogicAddress(rtu.getLogicAddress());
                        rd.setTn("" + tn[i]);

                        sTaskNum = rtu.getTaskNum(codesTemp);
                        codesTemp.clear();
                        if (sTaskNum == null) {
                            String error = "终端：" + rtu.getLogicAddress() + "任务报文无法找到匹配的任务模版," + message.getRawPacketString();
                            throw new MessageDecodeException(error);
                        }

                        rd.setTaskNum(sTaskNum);
                        rd.setTime(dataTimeTag.getDataTime());
                        for (HostCommandResult hcr : hc.getResults()) {
                            RtuDataItem rdItem = new RtuDataItem();
                            rdItem.setCode(hcr.getCode());
                            rdItem.setValue(hcr.getValue());
                            rd.addDataList(rdItem);
                        }
                        datas.add(rd);
                    } else {
                        for (HostCommandResult hcr : hc.getResults()) {
                            hcr.setValue(dataTimeTag.getDataTime() + "#" + hcr.getValue());
                        }
                        hc.setStatus("1");
                    }
                }
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        if (message.isTask()) {
            return datas;
        }
        return hc;
    }

    public int getGw0DDataType(int fn) {
        int type = 2;

        if (((fn >= 1) && (fn <= 12)) || ((fn >= 25) && (fn <= 32)) || ((fn >= 41) && (fn <= 43)) || (fn == 45) || ((fn >= 49) && (fn <= 50)) || (fn == 53) || ((fn >= 57) && (fn <= 59)) || ((fn >= 113) && (fn <= 129)) || ((fn >= 153) && (fn <= 156)) || ((fn >= 161) && (fn <= 176)) || ((fn >= 185) && (fn <= 192)) || (fn == 209)) {
            type = 1;
        } else if (((fn >= 73) && (fn <= 110)) || ((fn >= 138) && (fn <= 148)) || ((fn >= 217) && (fn <= 218))) {
            type = 0;
        }
        return type;
    }
}