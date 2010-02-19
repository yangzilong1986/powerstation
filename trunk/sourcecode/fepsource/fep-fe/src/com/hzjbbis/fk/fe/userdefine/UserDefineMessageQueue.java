package com.hzjbbis.fk.fe.userdefine;

import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.fe.ChannelManage;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.model.RtuManage;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class UserDefineMessageQueue {
    private static final Logger log = Logger.getLogger(UserDefineMessageQueue.class);
    private static final UserDefineMessageQueue instance = new UserDefineMessageQueue();
    private Map<Integer, IChannel> userMap = new HashMap();

    public static final UserDefineMessageQueue getInstance() {
        return instance;
    }

    public void offer(IMessage msg) {
        if (msg.getMessageType() == MessageType.MSG_ZJ) {
            MessageZj zjmsg = (MessageZj) msg;

            byte manuCode = zjmsg.head.msta;
            if (((manuCode & 0xFF) == 0) && (zjmsg.head.c_func == 15)) {
                manuCode = (byte) BCDToDecimal(zjmsg.data.get(0));
            }

            log.info("收到厂家编码为" + manuCode + "的自定义上行报文:" + msg);
            IChannel srcChannel = (IChannel) this.userMap.get(Byte.valueOf(manuCode));
            if (srcChannel == null) {
                log.error("收到厂家自定义报文，但厂家解析模块与通信前置机的连接找不到。msg=" + msg.getRawPacketString());
                return;
            }
            srcChannel.send(msg);

            log.info("厂家自定义报文应答：" + msg.getRawPacketString());
        } else if (msg.getMessageType() == MessageType.MSG_GW_10) {
            MessageGw gwmsg = (MessageGw) msg;
            IChannel srcChannel = (IChannel) this.userMap.get(Integer.valueOf(gwmsg.getRtua()));
            if (srcChannel == null) {
                log.error("收到厂家升级报文，但厂家升级模块与通信前置机的连接找不到。msg=" + msg.getRawPacketString());
                return;
            }
            srcChannel.send(msg);

            log.info("厂家自定义报文应答：" + msg.getRawPacketString());
        }
    }

    public boolean sendMessageDown(IMessage msg) {
        IChannel srcChannel;
        if (msg.getMessageType() == MessageType.MSG_ZJ) {
            MessageZj zjmsg = (MessageZj) msg;
            log.info("收到厂家编码为" + zjmsg.head.msta + "的自定义下行报文:" + msg);

            srcChannel = msg.getSource();
            int msta = 0xFF & zjmsg.head.msta;
            this.userMap.put(Integer.valueOf(msta), srcChannel);

            if (RtuManage.getInstance().getRemoteUpateRtuaTag(zjmsg.head.rtua)) {
                IChannel channel = ChannelManage.getInstance().getChannel(zjmsg.head.rtua);
                if (channel == null) {
                    return false;
                }
                channel.send(msg);
                return true;
            }
            return false;
        }
        if (msg.getMessageType() == MessageType.MSG_GW_10) {
            MessageGw gwmsg = (MessageGw) msg;

            srcChannel = msg.getSource();
            this.userMap.put(Integer.valueOf(gwmsg.getRtua()), srcChannel);

            if (RtuManage.getInstance().getRemoteUpateRtuaTag(gwmsg.getRtua())) {
                IChannel channel = ChannelManage.getInstance().getGPRSChannel(gwmsg.getRtua());
                if (channel == null) {
                    return false;
                }
                channel.send(msg);
                return true;
            }
            return false;
        }
        return false;
    }

    public static int BCDToDecimal(byte bcd) {
        int high = (bcd & 0xF0) >>> 4;
        int low = bcd & 0xF;
        if ((high > 9) || (low > 9)) {
            return -1;
        }
        return (high * 10 + low);
    }
}