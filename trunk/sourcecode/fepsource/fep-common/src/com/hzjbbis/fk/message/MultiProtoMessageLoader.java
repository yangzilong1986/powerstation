package com.hzjbbis.fk.message;

import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.StringTokenizer;

public class MultiProtoMessageLoader implements MessageLoader {
    private static final Logger log = Logger.getLogger(MultiProtoMessageLoader.class);

    public IMessage loadMessage(String serializedString) {
        StringTokenizer st = new StringTokenizer(serializedString, "|");
        IMessage msg = null;
        try {
            String token = st.nextToken();
            ByteBuffer buf = HexDump.toByteBuffer(token);
            msg = MultiProtoRecognizer.recognize(buf);
            if ((msg == null) || (!(msg.read(buf)))) {
                log.info("从缓存加载的信息，非终端规约消息：" + serializedString);
                return null;
            }
            do {
                String item = st.nextToken();
                if ("ioti".equalsIgnoreCase(item.substring(0, 4))) {
                    token = item.substring(7);
                    msg.setIoTime(Long.parseLong(token));
                } else if ("peer".equalsIgnoreCase(item.substring(0, 4))) {
                    token = item.substring(9);
                    msg.setPeerAddr(token);
                } else if ("txfs".equalsIgnoreCase(item.substring(0, 4))) {
                    token = item.substring(5);
                    msg.setTxfs(token);
                }
            } while (st.hasMoreTokens());

            msg.setPriority(0);
            return msg;
        } catch (Exception exp) {
            log.warn("缓存加载错误：buf=" + serializedString + ",exp=" + exp.getLocalizedMessage());
        }
        return null;
    }

    public String serializeMessage(IMessage message) {
        StringBuffer sb = new StringBuffer(512);
        sb.append(message.getRawPacketString()).append("|iotime=");
        sb.append(message.getIoTime()).append("|peeraddr=").append(message.getPeerAddr());
        sb.append("|txfs=").append(message.getTxfs());
        return sb.toString();
    }
}