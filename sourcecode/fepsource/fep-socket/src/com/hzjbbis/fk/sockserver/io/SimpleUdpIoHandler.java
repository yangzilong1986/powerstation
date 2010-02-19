package com.hzjbbis.fk.sockserver.io;

import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.common.events.GlobalEventHandler;
import com.hzjbbis.fk.common.spi.socket.IClientIO;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.exception.MessageParseException;
import com.hzjbbis.fk.exception.SocketClientCloseException;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.sockserver.event.ReceiveMessageEvent;
import org.apache.log4j.Logger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class SimpleUdpIoHandler implements IClientIO {
    private static final Logger log = Logger.getLogger(SimpleUdpIoHandler.class);

    public boolean onSend(IServerSideChannel client) throws SocketClientCloseException {
        return false;
    }

    public boolean onReceive(IServerSideChannel client) throws SocketClientCloseException {
        ByteBuffer buf = client.getBufRead();
        while (buf.hasRemaining()) {
            IMessage msg = client.getCurReadingMsg();
            if (msg == null) {
                int rem1 = buf.remaining();
                msg = client.getServer().createMessage(buf);
                int rem2 = buf.remaining();
                if (msg == null) {
                    if ((rem1 > 13) && (rem1 == rem2)) {
                        String info = "消息对象类型配置错误,UDP server port=" + client.getServer().getPort();
                        log.fatal(info);
                        buf.clear();
                        throw new SocketClientCloseException(info);
                    }

                    if (buf.hasRemaining()) buf.compact();
                    else buf.clear();
                    return true;
                }

                client.setCurReadingMsg(msg);
                msg.setSource(client);
                msg.setServerAddress(client.getServer().getServerAddress());
            }
            boolean down = false;
            try {
                down = msg.read(buf);
            } catch (MessageParseException mpe) {
                String expInfo = mpe.getLocalizedMessage();

                if (FasSystem.getFasSystem().isTestMode()) {
                    SocketAddress sa = client.getSocketAddress();
                    if (sa == null) return false;
                    byte[] expBytes = expInfo.getBytes();
                    try {
                        DatagramSocket ds = new DatagramSocket();
                        DatagramPacket dp = new DatagramPacket(expBytes, expBytes.length, sa);
                        ds.send(dp);
                    } catch (Exception e) {
                        log.warn("测试模式下UDP应答异常:" + e.getLocalizedMessage(), e);
                    }
                }

                client.setCurReadingMsg(null);
                return false;
            }
            if (!(down)) break;
            client.setCurReadingMsg(null);
            msg.setIoTime(System.currentTimeMillis());
            msg.setPeerAddr(client.getPeerAddr());
            msg.setTxfs(client.getServer().getTxfs());
            ReceiveMessageEvent ev = new ReceiveMessageEvent(msg, client);
            GlobalEventHandler.postEvent(ev);
        }

        if (buf.hasRemaining()) buf.compact();
        else buf.clear();
        return true;
    }
}