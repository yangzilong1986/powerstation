package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.model.HostCommand;
import com.hzjbbis.fas.protocol.zj.ErrorCode;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;

public class C28MessageDecoder extends AbstractMessageDecoder {
    public Object decode(IMessage message) {
        HostCommand hc = null;
        try {
            if (ParseTool.getOrientation(message) == 1) {
                int rtype = ParseTool.getErrCode(message);

                hc = new HostCommand();
                if (rtype == 0) {
                    hc.setStatus("1");
                } else {
                    byte[] data = ParseTool.getData(message);
                    if ((data != null) && (data.length > 0)) hc.setStatus(ErrorCode.toHostCommandStatus(data[0]));
                    else {
                        hc.setStatus("2");
                    }
                }

            }

        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return hc;
    }
}