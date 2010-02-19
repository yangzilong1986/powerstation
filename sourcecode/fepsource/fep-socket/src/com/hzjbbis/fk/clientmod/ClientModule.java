package com.hzjbbis.fk.clientmod;

import com.hzjbbis.fk.common.simpletimer.ITimerFunctor;
import com.hzjbbis.fk.common.simpletimer.TimerData;
import com.hzjbbis.fk.common.simpletimer.TimerScheduler;
import com.hzjbbis.fk.common.spi.IClientModule;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.IMessageCreator;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.message.gate.MessageGate;
import com.hzjbbis.fk.message.gate.MessageGateCreator;
import com.hzjbbis.fk.sockclient.JSocket;
import com.hzjbbis.fk.sockclient.JSocketListener;
import com.hzjbbis.fk.sockserver.event.ReceiveMessageEvent;
import com.hzjbbis.fk.sockserver.event.SendMessageEvent;
import com.hzjbbis.fk.tracelog.TraceLog;
import com.hzjbbis.fk.utils.CalendarUtil;
import org.apache.log4j.Logger;

public class ClientModule implements JSocketListener, IClientModule, ITimerFunctor {
    private static final Logger log = Logger.getLogger(ClientModule.class);

    private String name = "GPRS网关客户端";
    private String moduleType = "socketClient";
    private String hostIp = "127.0.0.1";
    private int hostPort = 10001;
    private int bufLength = 256;
    private IMessageCreator messageCreator = new MessageGateCreator();
    private int timeout = 2;
    private String txfs = "02";
    private IEventHandler eventHandler;
    private JSocket socket = null;

    private int heartInterval = 0;
    private int requestNum = 200;
    private long lastHeartbeat = System.currentTimeMillis();

    private long lastReceiveTime = System.currentTimeMillis();
    private long lastSendTime = 0L;
    private long totalRecvMessages = 0L;
    private long totalSendMessages = 0L;
    private int msgRecvPerMinute = 0;
    private int msgSendPerMinute = 0;
    private Object statisticsRecv = new Object();
    private Object statisticsSend = new Object();

    private boolean active = false;
    private IMessage heartMsg = null;
    private int curRecv = 200;

    public boolean sendMessage(IMessage msg) {
        if (!(this.active)) return false;
        boolean result = this.socket.sendMessage(msg);
        if ((result) && (msg.isHeartbeat())) {
            TraceLog _tracer = TraceLog.getTracer(this.socket.getClass());
            if (_tracer.isEnabled()) {
                _tracer.trace("send heart-beat ok.");
            }
        }
        return result;
    }

    public void onClose(JSocket client) {
        this.active = false;
    }

    public void onConnected(JSocket client) {
        this.active = true;
        if ((this.heartInterval > 0) && (this.requestNum > 0)) {
            sendMessage(this.heartMsg);
            if (log.isDebugEnabled()) {
                log.debug("连接时，请求报文数量=" + this.requestNum);
            }
            this.curRecv = this.requestNum;
        }
    }

    public void onReceive(JSocket client, IMessage msg) {
        synchronized (this.statisticsRecv) {
            this.msgRecvPerMinute += 1;
            this.totalRecvMessages += 1L;
            if (this.requestNum > 0) {
                if (msg.isHeartbeat()) {
                    this.lastHeartbeat = System.currentTimeMillis();
                    TraceLog _tracer = TraceLog.getTracer(this.socket.getClass());
                    if (_tracer.isEnabled()) {
                        _tracer.trace("receive heart-beat,lastHeartbeat=" + this.lastHeartbeat + ",client=" + this.socket.getPeerAddr());
                    }
                }
                if (--this.curRecv == 0) {
                    sendMessage(this.heartMsg);
                    if (log.isDebugEnabled())
                        log.debug("onReceive时，server传输数量达到requestNum，重新请求报文数量=" + this.requestNum);
                    this.curRecv = this.requestNum;
                } else if (log.isDebugEnabled()) {
                    IMessage amsg = msg;
                    if (msg.getMessageType() == MessageType.MSG_GATE) {
                        amsg = ((MessageGate) msg).getInnerMessage();
                        if (amsg != null) log.debug("剩余报文数量=" + this.curRecv + ",msg=" + amsg);
                    } else {
                        log.debug("剩余报文数量=" + this.curRecv + ",msg=" + amsg);
                    }
                }
            }
        }
        this.lastReceiveTime = System.currentTimeMillis();
        try {
            this.eventHandler.handleEvent(new ReceiveMessageEvent(msg, client));
        } catch (Exception localException) {
        }
        long timeSpand = System.currentTimeMillis() - this.lastReceiveTime;
        if (timeSpand > 100L) {
            TraceLog _tracer = TraceLog.getTracer(this.socket.getClass());
            if (_tracer.isEnabled())
                _tracer.trace("ClientModule fire onReceive event, it takes " + timeSpand + " milliseconds.");
        }
    }

    public void onSend(JSocket client, IMessage msg) {
        synchronized (this.statisticsSend) {
            this.msgSendPerMinute += 1;
            this.totalSendMessages += 1L;
        }
        this.lastSendTime = System.currentTimeMillis();
        try {
            this.eventHandler.handleEvent(new SendMessageEvent(msg, client));
        } catch (Exception localException) {
        }
        long timeSpand = System.currentTimeMillis() - this.lastSendTime;
        if (timeSpand > 100L) {
            TraceLog _tracer = TraceLog.getTracer(this.socket.getClass());
            if (_tracer.isEnabled())
                _tracer.trace("ClientModule fire onSend event, it takes " + timeSpand + " milliseconds.");
        }
    }

    public String getModuleType() {
        return this.moduleType;
    }

    public void setModuleType(String modType) {
        this.moduleType = modType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String nm) {
        this.name = nm;
    }

    public String getTxfs() {
        return this.txfs;
    }

    public boolean isActive() {
        return this.active;
    }

    public void init() {
        if (this.socket != null) return;
        this.socket = new JSocket();
        this.socket.setHostIp(this.hostIp);
        this.socket.setHostPort(this.hostPort);
    }

    public boolean start() {
        if ((this.heartInterval > 0) && (this.heartMsg == null)) {
            this.heartMsg = this.messageCreator.createHeartBeat(this.requestNum);
            this.curRecv = this.requestNum;
        }
        init();
        this.socket.setBufLength(this.bufLength);
        this.socket.setMessageCreator(this.messageCreator);
        this.socket.setTimeout(this.timeout);
        this.socket.setListener(this);
        this.socket.setTxfs(this.txfs);
        this.socket.init();

        TimerScheduler.getScheduler().addTimer(new TimerData(this, 0, 60L));

        if (this.heartInterval > 0) {
            TimerScheduler.getScheduler().addTimer(new TimerData(this, 1, 2L));
        }
        this.lastHeartbeat = System.currentTimeMillis();
        return true;
    }

    public void stop() {
        TimerScheduler.getScheduler().removeTimer(this, 0);
        if (this.socket != null) this.socket.close();
    }

    public String profile() {
        StringBuffer sb = new StringBuffer(1024);
        sb.append("\r\n    <sockclient-profile type=\"").append(getModuleType()).append("\">");
        sb.append("\r\n        ").append("<name>").append(getName()).append("</name>");
        sb.append("\r\n        ").append("<port>").append(this.hostIp + ":" + this.hostPort).append("</port>");
        sb.append("\r\n        ").append("<state>").append(isActive()).append("</state>");
        sb.append("\r\n        ").append("<timeout>").append(this.timeout).append("</timeout>");

        sb.append("\r\n        ").append("<txfs>").append(this.txfs).append("</txfs>");
        sb.append("\r\n        ").append("<totalRecv>").append(this.totalRecvMessages).append("</totalRecv>");
        sb.append("\r\n        ").append("<totalSend>").append(this.totalSendMessages).append("</totalSend>");
        sb.append("\r\n        ").append("<perMinuteRecv>").append(this.msgRecvPerMinute).append("</perMinuteRecv>");
        sb.append("\r\n        ").append("<perMinuteSend>").append(this.msgSendPerMinute).append("</perMinuteSend>");

        String stime = CalendarUtil.getTimeString(this.lastReceiveTime);
        sb.append("\r\n        ").append("<lastRecv>").append(stime).append("</lastRecv>");
        stime = CalendarUtil.getTimeString(this.lastSendTime);
        sb.append("\r\n        ").append("<lastSend>").append(stime).append("</lastSend>");
        sb.append("\r\n    </sockclient-profile>");
        return sb.toString();
    }

    public void onTimer(int id) {
        if ((1 == id) && (this.heartInterval > 0)) {
            long interval = System.currentTimeMillis() - this.lastHeartbeat;
            if (interval >= this.heartInterval) {
                this.curRecv = this.requestNum;
                sendMessage(this.heartMsg);
            }

            interval = System.currentTimeMillis() - this.lastReceiveTime;
            if (interval > this.heartInterval << 4) {
                TraceLog _trace = TraceLog.getTracer(this.socket.getClass());
                if (_trace.isEnabled())
                    _trace.trace("no up frame within 2 heartbeat intervals，connection will be reset. client=" + this.socket.getPeerAddr() + ",heartInterval=" + this.heartInterval + ",interval=" + interval + ",lastReceiveTime=" + this.lastReceiveTime);
                this.socket.reConnect();

                this.lastReceiveTime = System.currentTimeMillis();
            }
        } else {
            if (id != 0) {
                return;
            }
            synchronized (this.statisticsRecv) {
                this.msgRecvPerMinute = 0;
            }
            synchronized (this.statisticsSend) {
                this.msgSendPerMinute = 0;
            }
        }
    }

    public long getLastReceiveTime() {
        return this.lastReceiveTime;
    }

    public long getLastSendTime() {
        return this.lastSendTime;
    }

    public int getMsgRecvPerMinute() {
        return this.msgRecvPerMinute;
    }

    public int getMsgSendPerMinute() {
        return this.msgSendPerMinute;
    }

    public long getTotalRecvMessages() {
        return this.totalRecvMessages;
    }

    public long getTotalSendMessages() {
        return this.totalSendMessages;
    }

    public String getHostIp() {
        return this.hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public int getHostPort() {
        return this.hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    public int getBufLength() {
        return this.bufLength;
    }

    public void setBufLength(int bufLength) {
        this.bufLength = bufLength;
    }

    public IMessageCreator getMessageCreator() {
        return this.messageCreator;
    }

    public void setMessageCreator(IMessageCreator messageCreator) {
        this.messageCreator = messageCreator;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public JSocket getSocket() {
        return this.socket;
    }

    public void setSocket(JSocket socket) {
        this.socket = socket;
    }

    public void setTxfs(String txfs) {
        this.txfs = txfs;
    }

    public void setEventHandler(IEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void setHeartInterval(int heartInterval) {
        this.heartInterval = (heartInterval * 1000);
    }

    public int getRequestNum() {
        return this.requestNum;
    }

    public void setRequestNum(int requestNum) {
        this.requestNum = requestNum;
    }

    public String toString() {
        return profile();
    }

    public void setHeartMsg(IMessage heartMsg) {
        this.heartMsg = heartMsg;
    }
}