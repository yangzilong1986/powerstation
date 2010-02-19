package com.hzjbbis.fas.protocol.gw.codec;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.model.RtuAlert;
import com.hzjbbis.fas.protocol.gw.parse.DataItemParser;
import com.hzjbbis.fas.protocol.gw.parse.DataSwitch;
import com.hzjbbis.fas.protocol.gw.parse.DataValue;
import com.hzjbbis.fas.protocol.gw.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.RtuManage;

import java.util.ArrayList;
import java.util.List;

public class C0EMessageDecoder extends AbstractMessageDecoder {
    public Object decode(IMessage message) {
        List rt = new ArrayList();
        try {
            String data = ParseTool.getGwData(message);
            MessageGw msg = (MessageGw) message;
            if (msg.head.seq_tpv == 1) data = data.substring(0, data.length() - 12);
            if (msg.head.c_acd == 1) data = data.substring(0, data.length() - 4);
            String[] codes = DataItemParser.dataCodeParser(data.substring(4, 8), "0E");
            data = data.substring(8);
            if ((codes.length > 0) && (((codes[0].equals("0EF001")) || (codes[0].equals("0EF002"))))) {
                data = data.substring(4);
                int pm = Integer.parseInt(data.substring(0, 2), 16);
                int pn = Integer.parseInt(data.substring(2, 4), 16);
                int count = 0;
                if (pm < pn) count = pn - pm;
                else if (pm > pn) count = 256 + pn - pm;
                data = data.substring(4);
                int rtua = ((MessageGw) message).head.rtua;
                BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(rtua);
                for (int i = 0; i < count; ++i) {
                    int alertCode = Integer.parseInt(data.substring(0, 2), 16);
                    int len = Integer.parseInt(data.substring(2, 4), 16);
                    data = data.substring(4);
                    RtuAlert ra = alertParse(data.substring(0, len * 2), alertCode);
                    ra.setRtuId(rtu.getRtuId());
                    ra.setCorpNo(rtu.getDeptCode());
                    if (rtu.getMeasuredPoint(ra.getTn()) != null) {
                        ra.setDataSaveID(rtu.getMeasuredPoint(ra.getTn()).getDataSaveID());
                        ra.setCustomerNo(rtu.getMeasuredPoint(ra.getTn()).getCustomerNo());
                        ra.setStationNo(rtu.getMeasuredPoint(ra.getTn()).getCustomerNo());
                    }
                    rt.add(ra);
                }
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    private RtuAlert alertParse(String data, int alertCode) {
        String tag;
        String value;
        RtuAlert ra = new RtuAlert();
        String alertCodeHex = "";
        String alertTime = data.substring(0, 10);
        data = data.substring(10);
        int tn = 0;
        switch (alertCode) {
            case 1:
                tag = data.substring(0, 2);
                DataValue param1 = DataItemParser.parseValue(data.substring(2, 10), "ASC4");
                DataValue param2 = DataItemParser.parseValue(data.substring(10, 18), "ASC4");
                ra.setSbcs(alertCodeHex + "01=" + param1.getValue() + "," + alertCodeHex + "02=" + param2.getValue() + ",");
                if ((Integer.parseInt(tag) & 0x1) == 1) {
                    alertCodeHex = "C011";
                    ra.setAlertCode(Integer.parseInt(alertCodeHex, 16));
                } else if ((Integer.parseInt(tag) & 0x2) == 2) {
                    alertCodeHex = "C012";
                    ra.setAlertCode(Integer.parseInt(alertCodeHex, 16));
                }
                break;
            case 2:
                tag = data.substring(0, 2);
                if ((Integer.parseInt(tag) & 0x1) == 1) {
                    alertCodeHex = "C021";
                    ra.setAlertCode(Integer.parseInt(alertCodeHex, 16));
                } else if ((Integer.parseInt(tag) & 0x2) == 2) {
                    alertCodeHex = "C022";
                    ra.setAlertCode(Integer.parseInt(alertCodeHex, 16));
                }
                break;
            case 3:
                alertCodeHex = "C030";
                ra.setAlertCode(Integer.parseInt(alertCodeHex, 16));
                value = alertCodeHex + "01=" + data.substring(0, 2) + ",";
                data = data.substring(2);
                for (int i = 0; i < data.length() / 8; ++i) {
                    value = value + "C030" + DataSwitch.IntToHex(new StringBuilder().append("").append(i).append(2).toString(), 2) + "=" + data.substring(i * 8, i * 8 + 8) + ",";
                }
                value = value.substring(0, value.length() - 1);
                ra.setSbcs(value);
                break;
            case 14:
                String alertTime1 = data.substring(0, 10);

                if (Integer.parseInt(DataSwitch.ReverseStringByByte(alertTime)) > Integer.parseInt(DataSwitch.ReverseStringByByte(alertTime1))) {
                    alertCodeHex = "C141";
                } else {
                    alertCodeHex = "C142";
                    alertTime = alertTime1;
                }
                ra.setAlertCode(Integer.parseInt(alertCodeHex, 16));
                break;
            case 21:
                int tag = Integer.parseInt(data.substring(0, 2), 16);
                alertCodeHex = "C21" + tag;
                ra.setAlertCode(Integer.parseInt(alertCodeHex, 16));
                break;
            case 31:
                value = data.substring(3, 4);
                if ((Integer.parseInt(value) & 0x8) == 8) {
                    alertCodeHex = "C310";
                    tn = Integer.parseInt(data.substring(2, 3) + data.substring(0, 2), 16);
                    data = data.substring(4);
                    String value = "C31001" + DataItemParser.parseValue(data.substring(0, 10), "A15").getValue() + ",";
                    value = value + "C31002" + DataItemParser.parseValue(data.substring(10, 20), "A14").getValue() + ",";
                    value = value + "C31003" + DataItemParser.parseValue(data.substring(20, 28), "A11").getValue();
                    ra.setSbcs(value);
                }
                break;
            case 32:
                alertCodeHex = "C320";
                value = "C32001" + DataItemParser.parseValue(data.substring(0, 8), "HTB4").getValue() + ",";
                value = value + "C32002" + DataItemParser.parseValue(data.substring(8, 16), "HTB4").getValue();
                ra.setSbcs(value);
                break;
            case 60:
                alertCodeHex = "C600";
                value = "C60001" + DataItemParser.parseValue(data.substring(0, 8), "BS4").getValue();
                ra.setSbcs(value);
        }

        ra.setTn("" + tn);
        ra.setAlertTime(DataItemParser.parseValue(alertTime, "A15").getValue());
        return ra;
    }
}