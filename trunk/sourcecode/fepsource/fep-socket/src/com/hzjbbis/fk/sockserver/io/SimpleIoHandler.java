package com.hzjbbis.fk.sockserver.io;

import com.hzjbbis.fk.common.events.GlobalEventHandler;
import com.hzjbbis.fk.common.spi.socket.IClientIO;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.exception.MessageParseException;
import com.hzjbbis.fk.exception.SocketClientCloseException;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.sockserver.event.ReceiveMessageEvent;
import com.hzjbbis.fk.sockserver.event.SendMessageEvent;
import com.hzjbbis.fk.tracelog.TraceLog;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SimpleIoHandler implements IClientIO {
    private static final Logger log = Logger.getLogger(SimpleIoHandler.class);
    private static final TraceLog trace = TraceLog.getTracer(SimpleIoHandler.class);

    public boolean onReceive(IServerSideChannel client) throws SocketClientCloseException {
        int msgCount = 0;
        ByteBuffer readBuf = client.getBufRead();
        int bytesRead = 0;
        int n = 0;
        if (readBuf.remaining() == 0) {
            log.info("SimpleIoHandler.onReceive error. readBuf empty:pos=" + readBuf.position() + ",limit=" + readBuf.limit() + ",capacity=" + readBuf.capacity());
            readBuf.clear();
        }
        do {
            do {
                try {
                    n = client.getChannel().read(readBuf);
                } catch (IOException e) {
                    log.warn("client.getChannel().read(readBuf)异常:" + e.getLocalizedMessage());
                    throw new SocketClientCloseException(e);
                }
                if (n < 0) {
                    String info = "client close socket:" + client.toString();
                    log.info(info);
                    throw new SocketClientCloseException(info);
                }
                bytesRead += n;
            } while (n != 0);
            if (readBuf.hasRemaining()) 
            readBuf.flip();

            msgCount = processBuffer(readBuf, client, msgCount);
        } while (msgCount >= 0);

        if (log.isDebugEnabled()) log.debug("过度读取。暂时放弃读取数据");
        return false;

        if (bytesRead == 0) {
            return true;
        }
        readBuf.flip();

        msgCount = processBuffer(readBuf, client, msgCount);
        return (msgCount < 0);
    }

    private int processBuffer(ByteBuffer buf, IServerSideChannel client, int count) throws SocketClientCloseException {
        while (buf.hasRemaining()) {
            IMessage msg = client.getCurReadingMsg();
            if (msg == null) {
                int rem1 = buf.remaining();
                msg = client.getServer().createMessage(buf);
                int rem2 = buf.remaining();
                if (msg == null) {
                    if ((rem1 > 13) && (rem1 == rem2)) {
                        String info = "消息对象类型配置错误,server port=" + client.getServer().getPort();
                        log.fatal(info);
                        buf.clear();
                        throw new SocketClientCloseException(info);
                    }

                    if (buf.hasRemaining()) buf.compact();
                    else buf.clear();
                    return 0;
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
                log.warn("读消息异常：" + expInfo, mpe);

                client.setCurReadingMsg(null);
                if (buf.hasRemaining()) buf.compact();
                else buf.clear();
                return 0;
            }
            if (!(down)) break;
            ++count;
            client.setCurReadingMsg(null);
            ReceiveMessageEvent ev = new ReceiveMessageEvent(msg, client);
            msg.setIoTime(System.currentTimeMillis());
            msg.setPeerAddr(client.getPeerAddr());
            msg.setTxfs(client.getServer().getTxfs());
            GlobalEventHandler.postEvent(ev);

            int maxCanRead = client.getServer().getMaxContinueRead();
            int sendReqCount = client.sendQueueSize();
            if ((maxCanRead <= 0) || (sendReqCount <= 0) || (count < maxCanRead)) continue;
            if (buf.hasRemaining()) buf.compact();
            else buf.clear();
            return -1;
        }

        if (buf.hasRemaining()) buf.compact();
        else buf.clear();
        return count;
    }

    public boolean onSend(IServerSideChannel client) throws SocketClientCloseException {
        ByteBuffer writeBuf = client.getBufWrite();
        IMessage msg = client.getCurWritingMsg();
        boolean sent = false;

        if (client.bufferHasRemaining()) {
            sent = flush(client.getChannel(), writeBuf);
            if (!(sent)) {
                log.debug("flush(client.getChannel(),writeBuf),缓冲区数据没有发送完毕:msg=" + msg.getRawPacketString());
                return false;
            }

        }

        if (msg != null) {
            sent = sendMessage(msg, client);
            if (!(sent)) {
                log.debug("sendMessage(msg,client),消息没有发送完，剩余数据在缓冲区：msg=" + msg.getRawPacketString());
                return false;
            }
        }
        do {
            client.setCurWritingMsg(msg);
            sent = sendMessage(msg, client);
            if (!(sent)) {
                log.debug("sendMessage(msg,client),消息没有发送完，剩余数据在缓冲区:msg=" + msg.getRawPacketString());
                return false;
            }
        } while ((msg = client.getNewSendMessage()) != null);

        return true;
    }

    private boolean sendMessage(IMessage msg, IServerSideChannel client) throws SocketClientCloseException {
        ByteBuffer writeBuf = client.getBufWrite();
        boolean done = false;
        boolean sent = false;

        int deadloop = 0;
        while (!(done)) {
            done = msg.write(writeBuf);
            writeBuf.flip();
            sent = flush(client.getChannel(), writeBuf);

            if (done) {
                client.setCurWritingMsg(null);
                msg.setIoTime(System.currentTimeMillis());
                msg.setPeerAddr(client.getPeerAddr());
                msg.setSource(client);
                msg.setTxfs(client.getServer().getTxfs());

                GlobalEventHandler.postEvent(new SendMessageEvent(msg, client));
                if (client.getServer().getClientSize() <= 100) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("server port=" + client.getServer().getPort()).append(",clients=");
                    for (IServerSideChannel c : client.getServer().getClients()) {
                        sb.append(c.toString()).append(",");
                    }
                    trace.trace(sb.toString());
                }

            }

            if (!(sent)) {
                client.setBufferHasRemaining(true);
                return false;
            }
            client.setBufferHasRemaining(false);
            if (++deadloop > 1000) {
                log.fatal("Message.write方法死循环错误：" + msg.getClass().getName());
                return true;
            }
        }
        return true;
    }

    private boolean flush(SocketChannel channel, ByteBuffer buf) throws SocketClientCloseException {
        int bytesWritten = 0;
        while (buf.hasRemaining()) {
            try {
                bytesWritten = channel.write(buf);
            } catch (IOException exp) {
                String s = "channel.write()异常，原因" + exp.getLocalizedMessage();
                log.warn(s, exp);
                throw new SocketClientCloseException(exp);
            }
            if (bytesWritten == 0) {
                return false;
            }
        }
        buf.clear();
        return true;
    }
}