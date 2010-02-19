package com.hzjbbis.fas.protocol.gw.codec;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.model.HostCommand;
import com.hzjbbis.fas.model.HostCommandResult;
import com.hzjbbis.fas.protocol.gw.parse.DataItemParser;
import com.hzjbbis.fas.protocol.gw.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gw.MessageGw;

public class C00MessageDecoder extends AbstractMessageDecoder {
    public Object decode(IMessage message) {
        HostCommand hc = new HostCommand();
        try {
            String data = ParseTool.getGwData(message);
            MessageGw msg = (MessageGw) message;
            if (msg.head.seq_tpv == 1) data = data.substring(0, data.length() - 12);
            if (msg.head.c_acd == 1) {
                data = data.substring(0, data.length() - 4);
            }
            String[] codes = DataItemParser.dataCodeParser(data.substring(4, 8), "00");
            data = data.substring(8);
            if (codes.length == 1) {
                if ((codes[0].equals("00F001")) || (codes[0].equals("00F002"))) {
                    HostCommandResult hcr = new HostCommandResult();
                    hcr.setCode(codes[0]);
                    hcr.setTn("0");
                    hcr.setValue("00");
                    hc.addResult(hcr);
                    if (codes[0].equals("00F001")) hc.setStatus("1");
                    else hc.setStatus("2");
                } else {
                    String sAFN = data.substring(0, 2);
                    data = data.substring(2);
                    while (data.length() >= 10) {
                        int[] tn = DataItemParser.measuredPointParser(data.substring(0, 4));
                        codes = DataItemParser.dataCodeParser(data.substring(4, 8), sAFN);
                        data = data.substring(8);
                        String sValue = data.substring(0, 2);
                        for (int i = 0; i < tn.length; ++i) {
                            for (int j = 0; j < codes.length; ++j) {
                                HostCommandResult hcr = new HostCommandResult();
                                hcr.setCode(codes[j]);
                                hcr.setTn("" + tn[i]);
                                hcr.setValue(sValue);
                                hc.addResult(hcr);
                            }
                        }
                    }
                    hc.setStatus("1");
                }
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return hc;
    }
}