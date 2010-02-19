package com.hzjbbis.fk.fe.bpserver;

import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.events.BasicEventHook;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.fe.msgqueue.FEMessageQueue;
import com.hzjbbis.fk.fe.msgqueue.MessageDispatch2Bp;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.message.gate.MessageGate;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.sockserver.event.AcceptEvent;
import com.hzjbbis.fk.sockserver.event.ClientCloseEvent;
import com.hzjbbis.fk.sockserver.event.ReceiveMessageEvent;
import com.hzjbbis.fk.sockserver.event.SendMessageEvent;
import com.hzjbbis.fk.tracelog.TraceLog;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BPServerEventHandler extends BasicEventHook {
    private static final Logger log = Logger.getLogger(BPServerEventHandler.class);
    private static final TraceLog trace = TraceLog.getTracer(BPServerEventHandler.class);
    private FEMessageQueue msgQueue;
    private boolean noConvert = false;
    private boolean dispatchRandom = true;

    private List<IServerSideChannel> bpClients = Collections.synchronizedList(new ArrayList());

    public boolean start() {
        return super.start();
    }

    public void setMsgQueue(FEMessageQueue queue) {
        this.msgQueue = queue;
        this.msgQueue.setDispatchRandom(this.dispatchRandom);
        this.msgQueue.setNoConvert(this.noConvert);
    }

    public FEMessageQueue getMsgQueue() {
        return this.msgQueue;
    }

    public void handleEvent(IEvent e) {
        if (e.getType() == EventType.MSG_RECV) {
            onRecvMessage((ReceiveMessageEvent) e);
        } else if (e.getType() == EventType.MSG_SENT) {
            onSendMessage((SendMessageEvent) e);
        } else if (e.getType() == EventType.ACCEPTCLIENT) {
            for (int i = 0; i < this.bpClients.size(); ++i) {
                try {
                    IServerSideChannel client = (IServerSideChannel) this.bpClients.get(i);
                    if (System.currentTimeMillis() - client.getLastIoTime() <= 1800000L) break label143;
                    this.bpClients.remove(i);
                    if (trace.isEnabled()) trace.trace("garbage client removed:" + client);
                } catch (Exception exp) {
                }
                label143:
                break;
            }

            AcceptEvent ae = (AcceptEvent) e;

            if (ae.getClient().getChannel().isConnected()) {
                this.bpClients.add(ae.getClient());
            }
            MessageDispatch2Bp.getInstance().clearTimeoutChannel();
            if (trace.isEnabled()) {
                trace.trace("MessageDispatch2Bp.getInstance().clearTimeoutChannel()");
            }
        } else if (e.getType() == EventType.CLIENTCLOSE) {
            ClientCloseEvent ce = (ClientCloseEvent) e;
            this.bpClients.remove(ce.getClient());
            this.msgQueue.onBpClientClosed(ce.getClient());
        } else if (e.getType() == EventType.MSG_SEND_FAIL) {
            this.msgQueue.pushBack(e.getMessage());
        } else {
            super.handleEvent(e);
        }
    }

    private void onRecvMessage(ReceiveMessageEvent e) {
        IMessage msg = e.getMessage();
        if (msg.getMessageType() == MessageType.MSG_GATE) {
            IMessage rtuMsg;
            boolean success;
            MessageGate mgate = (MessageGate) msg;

            if (mgate.getHead().getCommand() == 17) {
                IServerSideChannel client = (IServerSideChannel) msg.getSource();

                if (this.bpClients.remove(client)) {
                    this.msgQueue.onBpClientConnected(client);
                }

                ByteBuffer data = mgate.getData();
                int numPackets = (data.remaining() < 4) ? -1 : data.getInt();
                synchronized (client) {
                    client.setRequestNum(numPackets);
                }

                MessageGate hreply = MessageGate.createHReply();
                client.send(hreply);
                return;
            }
            if (mgate.getHead().getCommand() == 33) {
                rtuMsg = mgate.getInnerMessage();

                rtuMsg.setPeerAddr(mgate.getSource().getPeerAddr());
                success = this.msgQueue.sendMessage(mgate);
                if ((success) && (log.isDebugEnabled())) log.debug("业务处理器下行命令:" + rtuMsg);
            } else {
                if (mgate.getHead().getCommand() == 50) {
                    String bpProfile = new String(mgate.getData().array());
                    FasSystem.getFasSystem().addBizProcessorProfile(e.getClient().getPeerAddr(), bpProfile);
                    return;
                }
                if (mgate.getHead().getCommand() != 0) return;
                rtuMsg = mgate.getInnerMessage();
                if (rtuMsg == null) {
                    return;
                }
                rtuMsg.setPeerAddr(mgate.getSource().getPeerAddr());
                success = this.msgQueue.sendMessage(rtuMsg);
                if ((success) && (log.isDebugEnabled())) log.info("终端下行命令:" + rtuMsg);
            }
        } else if (msg.getMessageType() == MessageType.MSG_ZJ) {
            MessageZj zjmsg = (MessageZj) msg;
            boolean success = this.msgQueue.sendMessage(zjmsg);
            if ((success) && (log.isDebugEnabled())) log.debug("业务处理器下行命令:" + zjmsg);
        }
    }

    private void onSendMessage(SendMessageEvent e) {
        IMessage msg = e.getMessage();

        if (msg instanceof MessageZj) {
            MessageZj zjmsg = (MessageZj) msg;
            if (zjmsg.head.c_func == 15) {
                if (log.isDebugEnabled()) log.debug("往厂家解析模块发送报文成功:" + zjmsg.getRawPacketString());
                return;
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("往业务处理器发送报文成功:" + msg);
        }

        if (this.dispatchRandom) {
            IServerSideChannel client = (IServerSideChannel) e.getClient();
            trySendNextPacket(client);
        } else {
            trySendNextPacketByA1();
        }
    }

    private void trySendNextPacketByA1() {
        MessageZj msg = (MessageZj) this.msgQueue.poll();
        if (msg == null) return;
        IServerSideChannel client = MessageDispatch2Bp.getInstance().getBpChannel(msg.head.rtua_a1);
        if (client == null) {
            this.msgQueue.pushBack(msg);
            return;
        }

        boolean success = false;
        if (this.noConvert) {
            success = client.send(msg);
        } else {
            MessageGate gateMsg = new MessageGate();
            gateMsg.setUpInnerMessage(msg);
            success = client.send(gateMsg);
        }
        if (!(success)) this.msgQueue.pushBack(msg);
    }

    private void trySendNextPacket(IServerSideChannel client) {
        if (client.getRequestNum() <= 0) {
            return;
        }
        IMessage msg = this.msgQueue.poll();
        if (msg != null) {
            boolean success = false;
            if (this.noConvert) {
                success = client.send(msg);
            } else {
                MessageGate gateMsg = new MessageGate();
                gateMsg.setUpInnerMessage(msg);
                success = client.send(gateMsg);
            }
            if (!(success)) this.msgQueue.pushBack(msg);
        }
    }

    public boolean isNoConvert() {
        return this.noConvert;
    }

    public void setNoConvert(boolean noConvert) {
        this.noConvert = noConvert;
        if (this.msgQueue != null) this.msgQueue.setNoConvert(noConvert);
    }

    public void setDispatchRandom(boolean dispRandom) {
        this.dispatchRandom = dispRandom;
        if (this.msgQueue != null) this.msgQueue.setDispatchRandom(this.dispatchRandom);
    }
}