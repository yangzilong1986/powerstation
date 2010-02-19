package com.hzjbbis.fk.message;

public class MessageType {
    public static final MessageType MSG_INVAL = new MessageType("非法类型报文");
    public static final MessageType MSG_ZJ = new MessageType("浙江规约");
    public static final MessageType MSG_GW_10 = new MessageType("国网电能采集规约2010版本");
    public static final MessageType MSG_GW = MSG_GW_10;
    public static final MessageType MSG_GW_04 = new MessageType("国网04规约");
    public static final MessageType MSG_GW_96 = new MessageType("国网96规约");
    public static final MessageType MSG_WEB = new MessageType("主站WEB接口规约");
    public static final MessageType MSG_HR = new MessageType("华隆厂商规约");
    public static final MessageType MSG_HG = new MessageType("华冠厂商规约");
    public static final MessageType MSG_MONITOR = new MessageType("监控");
    public static final MessageType MSG_GATE = new MessageType("网关");
    public static final MessageType MSG_UMS = new MessageType("新世纪短信报文");
    public static final MessageType MSG_GATE_230 = new MessageType("230信道接口规约");
    public static final MessageType MSG_ZJPB = new MessageType("浙江配变规约");
    public static final MessageType MSG_SAMPLE = new MessageType("simple message sample");
    public static final MessageType MSG_KILLTHREAD = new MessageType("kill thread message");
    public static final MessageType MSG_CALLUP = new MessageType("漏点补召通知报文");
    private final String desc;

    private MessageType(String msgType) {
        this.desc = msgType;
    }

    public String toString() {
        return this.desc;
    }
}