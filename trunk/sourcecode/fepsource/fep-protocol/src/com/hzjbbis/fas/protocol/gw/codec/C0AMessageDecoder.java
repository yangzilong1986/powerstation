package com.hzjbbis.fas.protocol.gw.codec;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.model.HostCommand;
import com.hzjbbis.fas.model.HostCommandResult;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fas.protocol.gw.parse.DataItemParser;
import com.hzjbbis.fas.protocol.gw.parse.DataValue;
import com.hzjbbis.fas.protocol.gw.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gw.MessageGw;

public class C0AMessageDecoder extends AbstractMessageDecoder {
    public Object decode(IMessage message) {
        HostCommand hc = new HostCommand();
        try {
            String data = ParseTool.getGwData(message);
            MessageGw msg = (MessageGw) message;
            if (msg.head.seq_tpv == 1) data = data.substring(0, data.length() - 12);
            if (msg.head.c_acd == 1) data = data.substring(0, data.length() - 4);
            DataValue dataValue = new DataValue();
            while (data.length() >= 8) {
                int[] tn = DataItemParser.measuredPointParser(data.substring(0, 4));
                String[] codes = DataItemParser.dataCodeParser(data.substring(4, 8), "04");
                data = data.substring(8);
                for (int i = 0; i < tn.length; ++i) {
                    for (int j = 0; j < codes.length; ++j) {
                        ProtocolDataItemConfig pdc = this.dataConfig.getDataItemConfig(codes[j]);
                        dataValue = DataItemParser.parser(data, pdc.getFormat());
                        data = data.substring(dataValue.getLen());
                        HostCommandResult hcr = new HostCommandResult();
                        hcr.setCode(codes[j]);
                        hcr.setTn("" + tn[i]);
                        hcr.setValue(dataValue.getValue());
                        hc.addResult(hcr);
                    }
                }
            }
            hc.setStatus("1");
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return hc;
    }
}