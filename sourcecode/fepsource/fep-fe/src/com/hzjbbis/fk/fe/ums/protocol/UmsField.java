package com.hzjbbis.fk.fe.ums.protocol;

public class UmsField {
    public static final String FIELD_LENGTH = "Length";
    public static final String FIELD_TRANSTYPE = "TransType";
    public static final String FIELD_SUBTYPE = "SubType";
    public static final String FIELD_APPID = "AppId";
    public static final String FIELD_PWD = "Passwd";
    public static final String FIELD_RETCODE = "RetCode";
    public static final String FIELD_RETMSG = "RetMsg";
    public static final String FIELD_APPSERIALNO = "AppSerialNo";
    public static final String FIELD_MSGTYPE = "MessageType";
    public static final String FIELD_MOBILES = "RecvId";
    public static final String FIELD_ACK = "Ack";
    public static final String FIELD_REPLY = "Reply";
    public static final String FIELD_PRIORITY = "Priority";
    public static final String FIELD_REPEAT = "Rep";
    public static final String FIELD_SUBAPP = "SubApp";
    public static final String FIELD_CHECKFLAG = "CheckFlag";
    public static final String FIELD_CONTENT = "Content";
    public static final String FIELD_RTUCONTENT = "RtuContent";
    public static final String FIELD_BATCHNO = "BatchNO";
    public static final String FIELD_SERIALNO = "SerialNO";
    public static final String FIELD_RETTYPE = "InfoType";
    public static final String FIELD_MSGID = "MsgID";
    public static final String FIELD_RECEIVER = "Receive";
    public static final String FIELD_FROM = "From";
    public static final String FIELD_RECVDATE = "ReceiveDate";
    public static final String FIELD_RECVTIME = "ReceiveTime";
    private int index;
    private String name;
    private int length;
    private String defValue = "";
    private String value;

    public UmsField() {
    }

    public UmsField(String n, String v) {
        this.name = n;
        this.value = v;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDefValue() {
        return this.defValue;
    }

    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}