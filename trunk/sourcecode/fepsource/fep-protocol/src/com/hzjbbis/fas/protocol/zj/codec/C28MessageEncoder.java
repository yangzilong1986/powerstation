package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.exception.MessageEncodeException;
import com.hzjbbis.fas.model.FaalSendSmsRequest;
import com.hzjbbis.fas.protocol.zj.parse.Parser43;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.message.zj.MessageZjHead;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class C28MessageEncoder extends AbstractMessageEncoder {
    private static Log log = LogFactory.getLog(C28MessageEncoder.class);

    public IMessage[] encode(Object obj) {
        List rt = null;
        try {
            if (obj instanceof FaalSendSmsRequest) {
                FaalSendSmsRequest para = (FaalSendSmsRequest) obj;
                String content = para.getContent();
                if ((content == null) || (content.length() == 0)) {
                    throw new MessageEncodeException("请指定短信内容");
                }

                byte[] bcont = content.getBytes("GB2312");
                exchangeBytes(bcont, 1);
                String[] phones = para.getMobiles();
                if ((phones != null) && (phones.length > 0)) {
                    rt = new ArrayList();
                    int len = 14 + bcont.length;
                    if (bcont.length > content.length() * 2) {
                        len -= 2;
                    }
                    for (int i = 0; i < phones.length; ++i) {
                        MessageZjHead head = new MessageZjHead();
                        head.c_dir = 0;
                        head.c_expflag = 0;
                        head.c_func = 40;

                        head.rtua_a1 = -110;
                        head.rtua_a2 = 0;
                        head.rtua_b1b2 = 7680;

                        head.iseq = 0;

                        head.dlen = (short) len;

                        byte[] frame = new byte[len];
                        int dlen = Parser43.constructor(frame, phones[i], 0, 14, 0);
                        if (dlen <= 0) {
                            continue;
                        }

                        if (bcont.length > content.length() * 2)
                            System.arraycopy(bcont, 2, frame, 14, bcont.length - 2);
                        else {
                            System.arraycopy(bcont, 0, frame, 14, bcont.length);
                        }

                        MessageZj msg = new MessageZj();
                        msg.data = ByteBuffer.wrap(frame);
                        msg.head = head;
                        rt.add(msg);
                        if (log.isDebugEnabled()) log.debug(content + " to " + phones[i]);
                    }
                } else {
                    throw new MessageEncodeException("请指定短信发送对象的手机号码");
                }
            } else {
                throw new MessageEncodeException("错误的参数对象，请使用：FaalSendSmsRequest");
            }
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }

        if ((rt != null) && (rt.size() > 0)) {
            IMessage[] msgs = new IMessage[rt.size()];
            rt.toArray(msgs);
            return msgs;
        }
        return null;
    }

    private void exchangeBytes(byte[] data) {
        int i = 0;
        while (i < data.length) {
            byte cc = data[i];
            data[i] = data[(i + 1)];
            data[(i + 1)] = cc;
            i += 2;
        }
    }

    private void exchangeBytes(byte[] data, int type) {
        if (type == 0) {
            exchangeBytes(data);
        } else {
            int i = 0;
            int j = data.length - 1;
            while (i < data.length / 2) {
                byte cc = data[i];
                data[i] = data[j];
                data[j] = cc;
                ++i;
                --j;
            }
        }
    }
}