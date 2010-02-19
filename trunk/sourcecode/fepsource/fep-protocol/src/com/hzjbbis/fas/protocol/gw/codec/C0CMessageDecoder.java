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

public class C0CMessageDecoder extends AbstractMessageDecoder {
    public Object decode(IMessage message) {
        List datas = new ArrayList();
        HostCommand hc = new HostCommand();

        int taskTag = 0;
        try {
            String sTaskDateTime = "";
            String sTaskNum = null;
            String data = ParseTool.getGwData(message);
            MessageGw msg = (MessageGw) message;
            if (msg.head.seq_tpv == 1) data = data.substring(0, data.length() - 12);
            if (msg.head.c_acd == 1) data = data.substring(0, data.length() - 4);
            DataValue dataValue = new DataValue();
            while (data.length() >= 8) {
                int[] tn = DataItemParser.measuredPointParser(data.substring(0, 4));
                String[] codes = DataItemParser.dataCodeParser(data.substring(4, 8), "0C");
                data = data.substring(8);
                for (int i = 0; i < tn.length; ++i)
                    for (int j = 0; j < codes.length; ++j) {
                        HostCommandResult hcr;
                        ProtocolDataItemConfig pdc = this.dataConfig.getDataItemConfig(codes[j]);
                        List childItems = pdc.getChildItems();

                        if ((Integer.parseInt(codes[j].substring(3, 6)) >= 81) && (Integer.parseInt(codes[j].substring(3, 6)) <= 121)) {
                            DataTimeTag dataTimeTag = DataItemParser.getTaskDateTimeInfo(data.substring(0, 4), 1);
                            data = data.substring(4);
                            for (int k = 0; k < dataTimeTag.getDataCount(); ++k)
                                if (childItems.size() > 0) {
                                    dataValue = DataItemParser.parseValue(data.substring(0, ((ProtocolDataItemConfig) childItems.get(0)).getLength() * 2), ((ProtocolDataItemConfig) childItems.get(0)).getFormat());
                                    data = data.substring(((ProtocolDataItemConfig) childItems.get(0)).getLength() * 2);
                                    sTaskDateTime = DataSwitch.IncreaseDateTime(dataTimeTag.getDataTime(), dataTimeTag.getDataDensity() * k, 2);
                                    if (taskTag == 1) {
                                        BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(((MessageGw) message).head.rtua);
                                        RtuData rd = new RtuData();
                                        rd.setLogicAddress(rtu.getLogicAddress());
                                        rd.setTn("" + tn[i]);

                                        if (sTaskNum == null) {
                                            List codesTemp = new ArrayList();
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
                                        hcr = new HostCommandResult();
                                        hcr.setCode(((ProtocolDataItemConfig) childItems.get(0)).getCode());
                                        hcr.setTn("" + tn[i]);
                                        hcr.setValue(sTaskDateTime + "#" + dataValue.getValue());
                                        hc.addResult(hcr);
                                        hc.setStatus("1");
                                    }
                                }
                        } else {
                            for (int k = 0; k < childItems.size(); ++k) {
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
                                        }
                                    }
                                    break;
                                }

                                if (pc.getCode().indexOf("XX") > 0) {
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
                                        }
                                    }
                                    break;
                                }

                                dataValue = DataItemParser.parseValue(data.substring(0, pc.getLength() * 2), pc.getFormat());
                                data = data.substring(pc.getLength() * 2);
                                hcr = new HostCommandResult();
                                hcr.setCode(pc.getCode());
                                hcr.setTn("" + tn[i]);
                                hcr.setValue(dataValue.getValue());
                                hc.addResult(hcr);
                            }

                            hc.setStatus("1");
                        }
                    }
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        if (taskTag == 1) {
            return datas;
        }
        return hc;
    }
}