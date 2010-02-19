package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.model.HostCommand;
import com.hzjbbis.fas.model.HostCommandResult;
import com.hzjbbis.fas.protocol.zj.ErrorCode;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;

public class C07MessageDecoder extends AbstractMessageDecoder {
    public Object decode(IMessage message) {
        HostCommand hc = null;
        try {
            if (ParseTool.getOrientation(message) == 1) {
                byte[] data;
                int rtype = ParseTool.getErrCode(message);
                hc = new HostCommand();
                if (0 == rtype) {
                    hc.setStatus("1");
                    data = ParseTool.getData(message);
                    int point = data[0];
                    int loc = 1;
                    if (data.length > 3) {
                        toResult(data, loc, point, hc);
                    } else throw new MessageDecodeException("数据长度不对");
                } else {
                    data = ParseTool.getData(message);
                    if ((data != null) && (data.length > 0)) {
                        if (data.length == 1) hc.setStatus(ErrorCode.toHostCommandStatus(data[0]));
                        else toResult(data, 1, data[0], hc);
                    } else {
                        hc.setStatus("2");
                    }
                }
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return hc;
    }

    private void toResult(byte[] data, int loc, int point, HostCommand hc) {
        try {
            int iloc = loc;
            while (iloc < data.length) {
                int datakey = ((data[(iloc + 1)] & 0xFF) << 8) + (data[iloc] & 0xFF);
                iloc += 2;
                String result = ParseTool.ByteToHex(data[iloc]);
                setItemResult(hc, point, ParseTool.IntToHex(datakey), result);
                ++iloc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setItemResult(HostCommand hc, int point, String code, String result) {
        HostCommandResult hcr = new HostCommandResult();
        hcr.setTn("" + point);
        hcr.setCode(code);
        hcr.setValue(result);
        hc.addResult(hcr);
    }
}