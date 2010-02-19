package com.hzjbbis.fk.fe.msgqueue;

import com.hzjbbis.db.managertu.ManageRtu;
import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.common.queue.CacheQueue;
import com.hzjbbis.fk.common.simpletimer.ITimerFunctor;
import com.hzjbbis.fk.common.simpletimer.TimerData;
import com.hzjbbis.fk.common.simpletimer.TimerScheduler;
import com.hzjbbis.fk.common.spi.IMessageQueue;
import com.hzjbbis.fk.common.spi.IProfile;
import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.fe.ChannelManage;
import com.hzjbbis.fk.fe.userdefine.UserDefineMessageQueue;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.message.gate.MessageGate;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.message.zj.MessageZjCreator;
import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

public class FEMessageQueue implements IMessageQueue, ITimerFunctor, IProfile {
    private static final Logger log = Logger.getLogger(FEMessageQueue.class);
    private CacheQueue cacheQueue;
    private ManageRtu manageRtu;
    private int rtuHeartbeatInterval = 900;
    private CacheQueue taskCacheQueue;
    private final int heartbeatTimer = 0;
    private TimerData td = null;

    private long hbInterval =
    ???.rtuHeartbeatInterval*1000;
    private MessageZjCreator messageCreator = new MessageZjCreator();
    private boolean dispatchRandom = true;
    private boolean noConvert = false;

    private Runnable shutdownHook = new Runnable() {
        public void run() {
            FEMessageQueue.this.dispose();
        }
    };

    public void setCacheQueue(CacheQueue queue) {
        this.cacheQueue = queue;
        if (this.td == null) initialize();
        FasSystem.getFasSystem().addShutdownHook(this.shutdownHook);
    }

    public void setRtuHeartbeatInterval(int interval) {
        this.rtuHeartbeatInterval = interval;
        this.hbInterval = (this.rtuHeartbeatInterval * 1000);
        initialize();
    }

    public void initialize() {
        if (this.td != null) {
            TimerScheduler.getScheduler().removeTimer(this, 0);
            this.td = null;
        }
        if (this.rtuHeartbeatInterval > 10) {
            this.td = new TimerData(this, 0, this.rtuHeartbeatInterval);
            TimerScheduler.getScheduler().addTimer(this.td);
        }
    }

    public void onTimer(int timerID) {
        if (timerID == 0) for (ComRtu rtu : RtuManage.getInstance().getAllComRtu()) {
            if (rtu.getActiveGprs() == null) continue;
            long distance = Math.abs(System.currentTimeMillis() - rtu.getLastIoTime());
            if (distance <= this.hbInterval) {
                continue;
            }
            MessageZj heartbeat = this.messageCreator.createHeartBeat(rtu.getRtua());
            rtu.setLastIoTime(System.currentTimeMillis());
            sendMessage(heartbeat);
        }
    }

    public boolean sendMessage(IMessage msg) {
        if (msg.getMessageType() == MessageType.MSG_ZJ) {
            MessageZj zjmsg = (MessageZj) msg;
            IChannel channel = null;
            boolean result = false;
            if (zjmsg.head.c_func == 36) {
                channel = ChannelManage.getInstance().getGPRSChannel(zjmsg.head.rtua);
                if (channel != null) result = channel.send(zjmsg);
            } else if (zjmsg.head.c_func == 15) {
                result = UserDefineMessageQueue.getInstance().sendMessageDown(zjmsg);
            } else {
                channel = ChannelManage.getInstance().getChannel(zjmsg.head.rtua);
                if (channel == null) {
                    log.warn("该终端无可用通道下行,RTUA=" + HexDump.toHex(zjmsg.head.rtua));
                    return false;
                }
                result = channel.send(zjmsg);
            }
            return result;
        }
        if (msg.getMessageType() == MessageType.MSG_GATE) {
            MessageGate gatemsg = (MessageGate) msg;

            String appstring = gatemsg.getHead().getAttributeAsString(9);
            if ((appstring != null) && (appstring.length() >= 9)) {
                String appid = appstring.substring(5, 9);
                IChannel channel = ChannelManage.getInstance().getChannel(appid);
                if (channel == null) {
                    log.warn("指定短信应用号无对应通道：appid=" + appid);
                    handleSendFail(gatemsg.getInnerMessage());
                    return false;
                }
                IMessage rtuMsg = gatemsg.getInnerMessage();
                rtuMsg.setPeerAddr(appstring);
                return channel.send(rtuMsg);
            }

            IMessage rtuMsg = gatemsg.getInnerMessage();
            if (rtuMsg == null) {
                log.error("下行的网关消息没有包含浙江或国网规约帧。gatemsg=" + gatemsg.getRawPacketString());
                return false;
            }

            if (rtuMsg.getMessageType() == MessageType.MSG_ZJ) {
                MessageZj zjmsg = (MessageZj) rtuMsg;
                if (zjmsg.head.c_func == 40) {
                    IChannel umsChannel = ChannelManage.getInstance().getActiveUmsChannel();
                    if (umsChannel == null) {
                        log.warn("当前没有在线的UMS短信通道，请求发送短信失败。");
                        return false;
                    }
                    return umsChannel.send(zjmsg);
                }

            }

            ComRtu rtu = RtuManage.getInstance().getComRtuInCache(rtuMsg.getRtua());
            if (rtu == null) {
                boolean refreshTag = this.manageRtu.refreshComRtu(rtuMsg.getRtua());
                if (!(refreshTag)) {
                    log.warn("rtu send failed,not find comRtu in db:" + HexDump.toHex(rtuMsg.getRtua()));
                    return false;
                }

                rtu = RtuManage.getInstance().getComRtuInCache(rtuMsg.getRtua());
            }
            IChannel channel = ChannelManage.getInstance().getChannel(rtuMsg.getRtua());
            if (channel == null) {
                log.warn("该终端无可用通道下行,RTUA=" + HexDump.toHex(rtuMsg.getRtua()));
                handleSendFail(rtuMsg);
                return false;
            }

            if (rtuMsg.getMessageType() == MessageType.MSG_ZJ) {
                if (((MessageZj) rtuMsg).head.msta != 0) rtu.setLastReqTime(System.currentTimeMillis());
            } else if (rtuMsg.getMessageType() == MessageType.MSG_GW_10) {
                MessageGw gwmsg = (MessageGw) rtuMsg;
                if ((gwmsg.head.c_prm == 1) && (gwmsg.head.c_dir == 0)) {
                    rtu.setLastReqTime(System.currentTimeMillis());
                }
                if (gwmsg.afn() == 15) {
                    channel = ChannelManage.getInstance().getGPRSChannel(gwmsg.getRtua());
                    boolean result = false;
                    if (channel != null) result = UserDefineMessageQueue.getInstance().sendMessageDown(gwmsg);
                    return result;
                }

            }

            rtuMsg.setStatus(rtu.getActiveGprs());
            return channel.send(msg);
        }
        log.error("FEMessageQueue只支持MessageGate,MessageZj消息下行。程序错啦！");
        return false;
    }

    private void handleSendFail(IMessage msg) {
        try {
            IChannel channel = msg.getSource();
            if (msg.getMessageType() == MessageType.MSG_ZJ) {
                MessageZj zjmsg = (MessageZj) msg;
                MessageZj repSendFail = zjmsg.createSendFailReply();
                MessageGate gatemsg = new MessageGate();
                gatemsg.setUpInnerMessage(repSendFail);
                channel.send(gatemsg);
            }
        } catch (Exception exp) {
            log.error(exp.getLocalizedMessage(), exp);
        }
    }

    public boolean sendMessageByUms(IMessage message) {
        IChannel channel = null;
        IMessage msg = message;
        if (message.getMessageType() == MessageType.MSG_ZJ) {
            MessageZj zjmsg = (MessageZj) message;
            channel = ChannelManage.getInstance().getUmsChannel(null, zjmsg.head.rtua);
        } else if (message.getMessageType() == MessageType.MSG_GATE) {
            MessageGate gatemsg = (MessageGate) message;
            msg = gatemsg.getInnerMessage();
            if (msg == null) {
                log.error("下行的网关消息没有包含规约帧。gatemsg=" + gatemsg.getRawPacketString());
                return false;
            }

            String appid = msg.getPeerAddr();
            channel = ChannelManage.getInstance().getUmsChannel(appid, msg.getRtua());
        }
        if (channel == null) {
            log.warn("该终端无可用UMS通道下行,RTUA=" + HexDump.toHex(msg.getRtua()));
            return false;
        }
        channel.send(msg);
        return true;
    }

    public IMessage take() {
        return this.cacheQueue.take();
    }

    public IMessage poll() {
        return this.cacheQueue.poll();
    }

    public void offer(IMessage msg0) {
        if (msg0.getMessageType() == MessageType.MSG_GATE) {
            RuntimeException re = new RuntimeException();
            log.warn("出现插入gate 消息", re);
            return;
        }
        if (msg0 instanceof MessageZj) {
            MessageZj zjmsg = (MessageZj) msg0;
            if (this.taskCacheQueue != null) {
                try {
                    if (zjmsg.head.c_func == 2) this.taskCacheQueue.offer(zjmsg);
                } catch (Exception localException) {
                }
            }
        }
        this.cacheQueue.offer(msg0);

        msg0 = this.cacheQueue.poll();

        if (msg0 == null) {
            return;
        }
        IServerSideChannel bpChannel = null;

        if (this.dispatchRandom) {
            bpChannel = MessageDispatch2Bp.getInstance().getIdleChannel();
        }

        if (bpChannel == null) {
            pushBack(msg0);
            return;
        }
        boolean success = false;
        if (this.noConvert) {
            success = bpChannel.send(msg0);
        } else {
            MessageGate gateMsg = new MessageGate();
            gateMsg.setUpInnerMessage(msg0);
            success = bpChannel.send(gateMsg);
        }
        if (!(success)) pushBack(msg0);
    }

    public void pushBack(IMessage msg) {
        this.cacheQueue.offer(msg);
    }

    public int size() {
        return this.cacheQueue.size();
    }

    public String toString() {
        return "FEMessageQueue";
    }

    public CacheQueue getTaskCacheQueue() {
        return this.taskCacheQueue;
    }

    public void setTaskCacheQueue(CacheQueue taskCacheQueue) {
        this.taskCacheQueue = taskCacheQueue;
    }

    public void onBpClientConnected(IServerSideChannel bpClient) {
        MessageDispatch2Bp.getInstance().onBpClientConnected(bpClient);
    }

    public void onBpClientClosed(IServerSideChannel bpClient) {
        MessageDispatch2Bp.getInstance().onBpClientClosed(bpClient);
    }

    public void setDispatchRandom(boolean dispatchRandom) {
        this.dispatchRandom = dispatchRandom;
    }

    public void setNoConvert(boolean noConvert) {
        this.noConvert = noConvert;
    }

    public String profile() {
        StringBuffer sb = new StringBuffer(256);
        sb.append("\r\n    <message-queue type=\"fe\">");
        sb.append("\r\n        <size>").append(size()).append("</size>");
        sb.append("\r\n    </message-queue>");
        return sb.toString();
    }

    public void dispose() {
        this.cacheQueue.dispose();
    }

    public void setManageRtu(ManageRtu manageRtu) {
        this.manageRtu = manageRtu;
    }
}