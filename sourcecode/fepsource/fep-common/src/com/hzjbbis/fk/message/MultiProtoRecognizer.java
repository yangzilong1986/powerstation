package com.hzjbbis.fk.message;

import com.hzjbbis.fk.message.gate.MessageGate;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;

public class MultiProtoRecognizer implements IMessageCreator {
    private static final Logger log = Logger.getLogger(MultiProtoRecognizer.class);
    private static final byte[] gateFlag = "JBBS".getBytes();

    public static IMessage recognize(ByteBuffer buf) {
        if (buf.remaining() < 13) {
            return null;
        }
        int flen = gateFlag.length;
        boolean matched = true;
        for (int pos = buf.position(); pos + flen < buf.limit(); ++pos) {
            matched = true;
            for (int i = 0; i < flen; ++i) {
                if (buf.get(pos + i) != gateFlag[i]) {
                    matched = false;
                    break;
                }
            }
            if (matched) break;
        }
        if (matched) {
            return new MessageGate();
        }
        int last68 = -1;
        for (int pos = buf.position(); pos + 13 < buf.limit(); ++pos) {
            if (104 != buf.get(pos)) continue;
            last68 = pos;

            if (104 == buf.get(pos + 5)) {
                short len1 = buf.getShort(pos + 1);
                short len2 = buf.getShort(pos + 3);
                if (len1 == len2) {
                    return new MessageGw();
                }
            }
            if (104 == buf.get(pos + 7)) {
                return new MessageZj();
            }
        }

        byte[] dump = (byte[]) null;
        if (last68 == -1) {
            dump = new byte[(buf.remaining() > 200) ? 200 : buf.remaining()];
            for (int i = 0; i < dump.length; ++i) {
                dump[i] = buf.get(buf.position() + i);
            }
            buf.position(0);
            buf.limit(0);
        } else {
            int len = buf.limit() - last68;
            if (len == 0) {
                dump = new byte[(buf.remaining() > 200) ? 200 : buf.remaining()];
                for (int i = 0; i < dump.length; ++i) {
                    dump[i] = buf.get(buf.position() + i);
                }
                buf.position(0);
                buf.limit(0);
            } else {
                int rem = last68 - buf.position();
                dump = new byte[(rem > 200) ? 200 : rem];
                for (int i = 0; i < dump.length; ++i)
                    dump[i] = buf.get(buf.position() + i);
                for (i = 0; i < len; ++i)
                    buf.put(i, buf.get(last68 + i));
                buf.position(0);
                buf.limit(len);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("多规约识别器丢弃无效数据：" + HexDump.hexDumpCompact(dump, 0, dump.length));
        }
        return null;
    }

    public IMessage create() {
        return null;
    }

    public IMessage createHeartBeat(int reqNum) {
        return null;
    }
}