package com.hzjbbis.fk.message.zj;

import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageLoader;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.util.StringTokenizer;

public class MessageLoader4Zj implements MessageLoader {
    private static final Logger log = Logger.getLogger(MessageLoader4Zj.class);

    public MessageZj loadMessage(String serializedString) {
        StringTokenizer st = new StringTokenizer(serializedString, "|");
        MessageZj msg = new MessageZj();

        boolean stop = false;
        try {
            String token = st.nextToken();
            if (token.equals(MessageZj.class.getName())) {
                token = st.nextToken().substring(12);
                stop = true;
            }
            if (!(msg.read(HexDump.toByteBuffer(token)))) {
                log.info("从缓存加载的信息，非浙江规约消息：" + serializedString);
                return null;
            }
            if (stop) return msg;
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
        if (message.getMessageType() != MessageType.MSG_ZJ) return null;
        MessageZj msg = (MessageZj) message;
        StringBuffer sb = new StringBuffer(512);
        sb.append(msg.getRawPacketString()).append("|iotime=");
        sb.append(msg.getIoTime()).append("|peeraddr=").append(msg.getPeerAddr());
        sb.append("|txfs=").append(msg.getTxfs());
        return sb.toString();
    }
}