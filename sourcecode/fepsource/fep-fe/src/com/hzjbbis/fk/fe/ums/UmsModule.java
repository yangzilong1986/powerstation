package com.hzjbbis.fk.fe.ums;

import com.hzjbbis.fk.common.simpletimer.Speedometer;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.exception.SendMessageException;
import com.hzjbbis.fk.fe.fiber.IFiber;
import com.hzjbbis.fk.fe.ums.protocol.UmsCommands;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.message.MultiProtoMessageLoader;
import com.hzjbbis.fk.message.gate.MessageGate;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.fk.sockclient.SimpleSocket;
import com.hzjbbis.fk.sockserver.event.MessageSendFailEvent;
import com.hzjbbis.fk.sockserver.event.ReceiveMessageEvent;
import com.hzjbbis.fk.sockserver.event.SendMessageEvent;
import com.hzjbbis.fk.tracelog.TraceLog;
import com.hzjbbis.fk.utils.CalendarUtil;
import com.hzjbbis.fk.utils.Counter;
import com.hzjbbis.fk.utils.HexDump;
import com.hzjbbis.fk.utils.State;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class UmsModule extends BaseClientChannelModule implements IFiber {
    private static final Logger log = Logger.getLogger(UmsModule.class);
    private static final TraceLog tracer = TraceLog.getTracer(UmsModule.class);
    public static final String SMS_TYPE_CH = "0";
    public static final String SMS_TYPE_PDU = "21";
    private String appid;
    private String apppwd;
    private String reply;
    private IEventHandler eventHandler;
    private UmsCommands umsProtocol;
    private boolean fiber = false;

    private State state = State.STOPPED;
    private SimpleSocket client;
    private List<IMessage> rtuReqList = new LinkedList();
    private List<MessageZj> genReqList = new LinkedList();
    private final MultiProtoMessageLoader messageLoad = new MultiProtoMessageLoader();
    private UmsSocketThread thread;
    private int umsSendSpeed = 100;
    private int sendUserLimit = 2;
    private int sendRtuLimit = 10;
    private int retrieveMsgLimit = 10;
    private Speedometer speedom = new Speedometer();
    private Counter socketIOSpeed;
    private long noUpLogAlertTime;
    private List<String> simNoList;
    private String alertContent;
    private long sleepInterval = 10000L;
    private long noUpLogTime = System.currentTimeMillis() - ?
    ??.sleepInterval;

    public String getPeerAddr() {
        return this.appid;
    }

    public void close() {
    }

    public boolean send(IMessage msg) {
        if (!(sendMessage(msg))) throw new SendMessageException("发送消息异常");
        return true;
    }

    public boolean sendMessage(IMessage message) {
        IMessage rtuMsg = null;
        if (message.getMessageType() == MessageType.MSG_GATE) {
            rtuMsg = ((MessageGate) message).getInnerMessage();
        }
        if (rtuMsg == null) {
            return false;
        }
        rtuMsg.setTxfs(this.txfs);
        rtuMsg.setSource(this);
        if (rtuMsg.getMessageType() == MessageType.MSG_ZJ) {
            MessageZj zjmsg = (MessageZj) rtuMsg;
            if (zjmsg.head.c_func == 40) {
                synchronized (this.genReqList) {
                    this.genReqList.add(zjmsg);
                }
            }

        }

        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(rtuMsg.getRtua());
        String simNo = rtu.getSimNum();
        if ((simNo != null) && (simNo.length() >= 11) && (isNumeric(simNo.trim()))) {
            synchronized (this.rtuReqList) {
                this.rtuReqList.add(rtuMsg);
            }
        }

        log.warn("rtu=" + HexDump.toHex(rtuMsg.getRtua()) + " simNo is error:" + simNo);
        if (rtuMsg.getMessageType() == MessageType.MSG_ZJ) {
            MessageZj zjmsg = (MessageZj) rtuMsg;
            zjmsg = zjmsg.createSendFailReply();
            this.eventHandler.handleEvent(new MessageSendFailEvent(zjmsg, this));
        }

        return true;
    }

    public boolean isNumeric(String str) {
        return Pattern.matches("[0-9]*", str);
    }

    public boolean isActive() {
        return ((this.client == null) || (!(this.client.isAlive())));
    }

    public boolean start() {
        this.lastReceiveTime = System.currentTimeMillis();
        if (!(this.state.isStopped())) return false;
        this.state = State.STARTING;
        log.debug("ums-" + this.appid + "启动...");

        if (this.client == null) {
            this.client = new SimpleSocket(this.peerIp, this.peerPort);
        }
        if (!(this.fiber)) {
            this.thread = new UmsSocketThread();
            this.thread.start();
        }
        this.socketIOSpeed = new Counter(1000L, "ums-" + this.appid);
        return true;
    }

    public void stop() {
        if (!(this.state.isRunning())) return;
        this.state = State.STOPPING;
        this.client.close();
        if (!(this.fiber)) {
            this.thread.interrupt();
            Thread.yield();
        }
        try {
            Thread.sleep(100L);
        } catch (Exception localException) {
        }
        this.thread = null;
        this.client = null;
    }

    protected boolean doSendGenReq(MessageZj msg) {
        if (msg.data.position() == msg.data.limit()) msg.data.position(0);
        msg.head.dlen = (short) msg.data.remaining();
        byte[] mn = new byte[14];
        msg.data.get(mn);
        int pos = 0;
        while ((mn[pos] == 0) && (pos < 14)) ++pos;
        if (pos >= 14) {
            log.warn("用户自定义短信发送失败：目标号码全0！无法发送");
            this.eventHandler.handleEvent(new MessageSendFailEvent(msg, this));
            return false;
        }
        String mobile = new String(mn, pos, 14 - pos);
        byte[] ct = new byte[msg.head.dlen - 14];
        msg.data.get(ct);

        int j = ct.length - 1;
        pos = 0;

        while (pos < j) {
            byte cc = ct[pos];
            ct[pos] = ct[j];
            ct[j] = cc;
            ++pos;
            --j;
        }

        String contents = new String(ct);

        if ((mobile == null) || (!(isNumeric(mobile.trim())))) {
            if (log.isDebugEnabled()) log.debug("用户自定义短信[" + contents + "]发送失败,simNo is error:" + mobile);
            this.eventHandler.handleEvent(new MessageSendFailEvent(msg, this));
            return false;
        }

        int ret = 0;
        StringBuffer msb = new StringBuffer(mobile);
        msb.reverse();

        String umsAddr = msg.getPeerAddr();
        String subappid = "";
        if ((umsAddr != null) && (umsAddr.length() > 0)) {
            int subIndex = umsAddr.indexOf(this.appid);
            if (subIndex >= 0) {
                subappid = umsAddr.substring(subIndex + this.appid.length());
            }
        }

        String sendCont = null;
        int maxlen = 600;

        int num = 1;
        int nums = contents.length() / maxlen;
        if (contents.length() % maxlen > 0) {
            ++nums;
        }
        String tag = "";
        while (contents.length() > 0) {
            if (nums > 1) tag = "[" + num + "/" + nums + "]";
            if (contents.length() >= maxlen) {
                sendCont = tag + contents.substring(0, maxlen);
                ret = this.umsProtocol.sendUserMessage(this.client, msb.toString(), sendCont, this.appid, subappid, this.reply);
                if (ret != 0) {
                    break;
                }
                contents = contents.substring(maxlen, contents.length());
            } else {
                if (ret == 0) {
                    sendCont = tag + contents.substring(0, contents.length());
                    ret = this.umsProtocol.sendUserMessage(this.client, msb.toString(), sendCont, this.appid, subappid, this.reply);
                }
                contents = "";
            }
            ++num;
        }

        if (-1 == ret) {
            this.client.close();
        }
        String info = null;
        if (log.isDebugEnabled()) {
            StringBuffer sb = new StringBuffer();
            sb.append(getPeerAddr());
            if (ret == 0) sb.append("成功");
            else sb.append("失败");
            sb.append(" ->短信通道下行报文:[mobile=");
            sb.append(mobile);
            sb.append(",contents=").append(contents).append("],subappid=" + subappid);
            info = sb.toString();
            log.debug(info);
        }
        msg.setPeerAddr(getPeerAddr() + subappid);
        msg.setIoTime(System.currentTimeMillis());

        msg.setServerAddress(msg.getPeerAddr() + "," + mobile);
        if (ret == 0) {
            this.eventHandler.handleEvent(new SendMessageEvent(msg, this));
        } else {
            this.eventHandler.handleEvent(new MessageSendFailEvent(msg, this));
        }
        return (ret != 0);
    }

    protected boolean doSendRtuReq(IMessage rtuMsg) {
        rtuMsg.setIoTime(System.currentTimeMillis());

        String downReqString = null;

        downReqString = new String(rtuMsg.getRawPacketString());

        int ret = -1;

        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(rtuMsg.getRtua());
        if (rtu == null) {
            log.warn("终端不存在，无法发送召测命令----" + HexDump.toHex(rtuMsg.getRtua()));
            return false;
        }
        String umsAddr = rtuMsg.getPeerAddr();
        String subappid = "";
        if ((umsAddr != null) && (umsAddr.length() > 0)) {
            int subIndex = umsAddr.indexOf(this.appid);
            if (subIndex >= 0) {
                subappid = umsAddr.substring(subIndex + this.appid.length());
            } else if (rtu.getActiveSubAppId() != null) {
                subappid = rtu.getActiveSubAppId();
            }

        } else if (rtu.getActiveSubAppId() != null) {
            subappid = rtu.getActiveSubAppId();
        }
        String mobilePhone = rtu.getSimNum();
        if ((mobilePhone == null) || (mobilePhone.length() <= 0)) {
            log.warn("终端SIM卡资料缺失,短信无法发送--" + HexDump.toHex(rtuMsg.getRtua()));
            this.eventHandler.handleEvent(new MessageSendFailEvent(rtuMsg, this));
            return false;
        }
        ret = this.umsProtocol.sendRtuMessage(this.client, mobilePhone, downReqString, this.appid, subappid, this.reply);
        if (ret != 0) {
            this.client.close();
        }

        String info = null;
        if (log.isDebugEnabled()) {
            StringBuffer sb = new StringBuffer();
            sb.append(getPeerAddr());
            if (ret == 0) sb.append("成功");
            else sb.append("失败");
            sb.append(" ->短信通道下行报文:");
            sb.append(downReqString).append(",subappid=").append(subappid);
            info = sb.toString();
            log.debug(info);
        }

        if (ret == 0) {
            rtuMsg.setPeerAddr(getPeerAddr() + subappid);
            rtuMsg.setIoTime(System.currentTimeMillis());

            rtuMsg.setServerAddress("95598" + rtuMsg.getPeerAddr() + "," + mobilePhone);
            this.eventHandler.handleEvent(new SendMessageEvent(rtuMsg, this));
        } else {
            if (rtuMsg.getMessageType() == MessageType.MSG_ZJ) {
                MessageZj msgzj = (MessageZj) rtuMsg;
                byte msta = msgzj.head.msta;

                if (((msta >= 10) && (msta <= 29)) || (msgzj.head.rtua == 0)) {
                    return (ret != 0);
                }
            }
            rtuMsg.setPeerAddr(getPeerAddr() + subappid);
            rtuMsg.setIoTime(System.currentTimeMillis());

            rtuMsg.setServerAddress("95598" + rtuMsg.getPeerAddr() + "," + mobilePhone);
            this.eventHandler.handleEvent(new MessageSendFailEvent(rtuMsg, this));
        }
        return (ret != 0);
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setApppwd(String apppwd) {
        this.apppwd = apppwd;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public void setEventHandler(IEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void runOnce() {
        label157:
        String strTime;
        if ((this.state == State.STOPPING) || (this.state == State.STOPPED)) {
            this.state = State.STOPPED;
            return;
        }
        if (this.state == State.STARTING) this.state = State.RUNNING;
        long time0 = System.currentTimeMillis();

        if (!(isActive())) {
            long delta = System.currentTimeMillis() - this.client.getLastConnectTime();
            if (delta > 60000L) {
                boolean ret = this.client.reConnect();
                log.info("ums-" + this.appid + " reConnetct...");
                if ((!(ret)) || (this.umsProtocol.login(this.client, this.appid, this.apppwd))) break label157;
                return;
            }

            try {
                Thread.sleep(50L);
            } catch (Exception localException1) {
            }
            return;
        }

        int msgCount = 0;
        while ((this.rtuReqList.size() > 0) && (msgCount++ < this.sendRtuLimit) && (this.client.isAlive())) {
            IMessage rtuMsg;
            if (log.isDebugEnabled()) {
                log.debug("发送终端短信" + msgCount + ";rtuReqList=" + this.rtuReqList.size());
            }
            synchronized (this.rtuReqList) {
                rtuMsg = (IMessage) this.rtuReqList.remove(0);
            }
            doSendRtuReq(rtuMsg);
            this.totalSendMessages += 1L;
            this.lastSendTime = System.currentTimeMillis();
            this.speedom.add(1);
            this.socketIOSpeed.add();
            if (this.speedom.getSpeed1() <= this.umsSendSpeed) continue;
            try {
                log.info("ums-" + this.appid + " send speed > limit speed:" + this.speedom.getSpeed1());
                Thread.sleep(50L);
            } catch (Exception localException2) {
            }
            break;
        }

        msgCount = 0;
        if (System.currentTimeMillis() - this.noUpLogTime <= this.sleepInterval)
            if ((this.rtuReqList.size() <= 0) && (this.genReqList.size() <= 0)) try {
                Thread.sleep(50L);
            } catch (Exception localException3) {
            }
            else {
                do {
                    if (log.isDebugEnabled()) log.debug("巡测终端上行短信:" + msgCount);
                    Map repMap = this.umsProtocol.retrieveSMS(this.client, this.appid);
                    if (repMap == null) {
                        this.noUpLogTime = System.currentTimeMillis();

                        break;
                    }
                    ++msgCount;

                    String rawMessage = (String) repMap.get("Content");
                    String strDate = (String) repMap.get("ReceiveDate");
                    strTime = (String) repMap.get("ReceiveTime");
                    String from = (String) repMap.get("From");
                    if (from == null) from = " ";
                    String receiver = (String) repMap.get("Receive");
                    if (receiver == null) {
                        receiver = " ";
                    }
                    this.lastReceiveTime = System.currentTimeMillis();
                    this.totalRecvMessages += 1L;
                    try {
                        IMessage msg = this.messageLoad.loadMessage(rawMessage);
                        if (log.isDebugEnabled()) log.debug(msgCount + "--retrieveSMS msg:" + msg);
                        if (msg != null) {
                            msg.setPeerAddr(getPeerAddr());
                            msg.setIoTime(System.currentTimeMillis());
                            msg.setSource(this);
                            msg.setTxfs(this.txfs);
                            msg.setServerAddress(from + "," + receiver);
                            this.eventHandler.handleEvent(new ReceiveMessageEvent(msg, this));
                            break label880:
                        }

                        log.info("ums-" + this.appid + " 非终端规约短信上行：" + rawMessage + ",from=" + from + ",datetime=" + strDate + strTime);
                    } catch (Exception exp) {
                        log.error("MultiProtoMessageLoader.loadMessage 异常,原因=" + exp.getLocalizedMessage() + ",rawpacket=" + rawMessage);
                    }
                    label880:
                    this.speedom.add(1);
                    this.socketIOSpeed.add();
                } while ((msgCount < this.retrieveMsgLimit) && (this.client.isAlive()));
            }

        msgCount = 0;
        while ((this.genReqList.size() > 0) && (msgCount++ < this.sendUserLimit) && (this.client.isAlive())) {
            MessageZj msg;
            if (log.isDebugEnabled()) {
                log.debug("给普通用户发送短信" + msgCount + ";genReqList=" + this.genReqList.size());
            }
            synchronized (this.genReqList) {
                msg = (MessageZj) this.genReqList.remove(0);
            }
            doSendGenReq(msg);
            this.lastSendTime = System.currentTimeMillis();
            this.totalSendMessages += 1L;
            this.speedom.add(1);
            this.socketIOSpeed.add();
            if (this.speedom.getSpeed1() <= this.umsSendSpeed) continue;
            try {
                Thread.sleep(50L);
            } catch (Exception localException4) {
            }
            break;
        }

        if ((this.client.isAlive()) && (this.noUpLogAlertTime > 0L) && (this.alertContent != null)) {
            long delt = System.currentTimeMillis() - this.lastReceiveTime;
            if (delt > this.noUpLogAlertTime) {
                if (this.simNoList != null) {
                    for (String mobileNo : this.simNoList) {
                        this.umsProtocol.sendUserMessage(this.client, mobileNo, this.alertContent, this.appid, null, this.reply);
                    }
                }
                this.client.close();
                this.lastReceiveTime = System.currentTimeMillis();
                log.info("UMS通讯应用ID" + this.appid + "的通道在指定时间范围内无消息上报,重启链路");
            }
        }
        long timeTake = System.currentTimeMillis() - time0;
        if (timeTake > 1000L) tracer.trace("ums-" + this.appid + "socketIO takes(milliseconds):" + timeTake);
    }

    public void setNoUpLogAlertTime(long noUpLogAlertTime) {
        this.noUpLogAlertTime = noUpLogAlertTime;
    }

    public void setSimNoList(List<String> simNoList) {
        this.simNoList = simNoList;
    }

    public void setAlertContent(String alertContent) {
        this.alertContent = alertContent;
    }

    public final void setUmsProtocol(UmsCommands umsProtocol) {
        this.umsProtocol = umsProtocol;
    }

    public final void setUmsSendSpeed(int umsSendSpeed) {
        this.umsSendSpeed = umsSendSpeed;
    }

    public final void setSendUserLimit(int sendUserLimit) {
        this.sendUserLimit = sendUserLimit;
    }

    public final void setSendRtuLimit(int sendRtuLimit) {
        this.sendRtuLimit = sendRtuLimit;
    }

    public void setFiber(boolean isFiber) {
        this.fiber = isFiber;
    }

    public boolean isFiber() {
        return this.fiber;
    }

    public final void setRetrieveMsgLimit(int retrieveMsgLimit) {
        this.retrieveMsgLimit = retrieveMsgLimit;
    }

    public String profile() {
        StringBuffer sb = new StringBuffer(1024);
        sb.append("\r\n<sockclient-profile type=\"").append(getModuleType()).append("\">");
        sb.append("\r\n    ").append("<name>").append(getName()).append("</name>");
        sb.append("\r\n    ").append("<ip>").append(getPeerIp()).append("</ip>");
        sb.append("\r\n    ").append("<port>").append(getPeerPort()).append("</port>");
        sb.append("\r\n    ").append("<state>").append(isActive()).append("</state>");

        sb.append("\r\n    ").append("<txfs>").append(this.txfs).append("</txfs>");
        sb.append("\r\n    ").append("<totalRecv>").append(this.totalRecvMessages).append("</totalRecv>");
        sb.append("\r\n    ").append("<totalSend>").append(this.totalSendMessages).append("</totalSend>");
        sb.append("\r\n    ").append("<speed>").append(this.speedom.getSpeed1()).append("</speed>");

        String stime = CalendarUtil.getTimeString(this.lastReceiveTime);
        sb.append("\r\n    ").append("<lastRecv>").append(stime).append("</lastRecv>");
        stime = CalendarUtil.getTimeString(this.lastSendTime);
        sb.append("\r\n    ").append("<lastSend>").append(stime).append("</lastSend>");
        sb.append("\r\n</sockclient-profile>");
        return sb.toString();
    }

    public String getModuleType() {
        return "umsClient";
    }

    public void setNoUpLogTime(long noUpLogTime) {
        this.noUpLogTime = noUpLogTime;
    }

    public void setSleepInterval(long sleepInterval) {
        this.sleepInterval = sleepInterval;
    }

    private class UmsSocketThread extends Thread {
        public UmsSocketThread() {
            super("ums.thread." + UmsModule.this.appid);
        }

        public void run() {
            while (UmsModule.this.state != State.STOPPED) try {
                UmsModule.this.runOnce();
            } catch (Exception exp) {
                UmsModule.log.error("UMS通信处理异常：" + exp.getLocalizedMessage(), exp);
            }
        }
    }
}