package com.hzjbbis.fk.fe.ums.protocol;

import com.hzjbbis.fk.sockclient.SimpleSocket;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UmsCommands {
    private static final Logger log;
    private List<UmsField> checkPasswordFields;
    private List<UmsField> heartBeatFields;
    private List<UmsField> sendSMSFields;
    private List<UmsField> sendRtuSMSFields;
    private List<UmsField> retrieveSMSFields;
    private List<UmsField> genReplyFields;
    private List<UmsField> smsReplyFields;
    private List<UmsField> smsConfirmFields;
    private static final Map<String, UmsField> emptyParam;
    private final byte[] buffer = new byte[4096];

    static {
        log = Logger.getLogger(UmsCommands.class);

        emptyParam = new HashMap();
    }

    private String readReply(SimpleSocket socket) {
        int offset = 0;
        int len = this.buffer.length - offset;
        int n = -1;

        n = socket.read(this.buffer, offset, len);
        if (n <= 0) return null;
        offset += n;
        len -= n;
        int f1Len = ((UmsField) this.genReplyFields.get(0)).getLength();
        int toRead = Integer.parseInt(new String(this.buffer, 0, f1Len).trim());
        while ((offset < toRead + f1Len) && (n > 0)) {
            n = socket.read(this.buffer, offset, len);
            offset += n;
            len -= n;
        }
        return new String(this.buffer, 0, offset);
    }

    public boolean login(SimpleSocket socket, String appid, String pwd) {
        Map param = new HashMap();
        UmsField field = new UmsField();
        field.setName("AppId");
        field.setValue(appid);
        param.put(field.getName(), field);
        field = new UmsField();
        field.setName("Passwd");
        field.setValue(pwd);
        param.put(field.getName(), field);

        String strCommand = createCommand(this.checkPasswordFields, param);
        if (socket.write(strCommand) <= 0) return false;
        String strReply = readReply(socket);
        int f1Len = ((UmsField) this.genReplyFields.get(0)).getLength();
        int f2Len = ((UmsField) this.genReplyFields.get(1)).getLength();
        String retCode = strReply.substring(f1Len, f1Len + f2Len);
        if ("0000".equals(retCode)) {
            log.info("ums-" + appid + "login success. reply=" + strReply);
            return true;
        }
        log.warn("ums-" + appid + "登录失败，原因＝" + strReply.substring(f1Len + f2Len));
        socket.close();
        return false;
    }

    public boolean heartBeat(SimpleSocket socket) {
        String strCommand = createCommand(this.heartBeatFields, emptyParam);
        if (socket.write(strCommand) <= 0) return false;
        String strReply = readReply(socket);
        int f1Len = ((UmsField) this.genReplyFields.get(0)).getLength();
        int f2Len = ((UmsField) this.genReplyFields.get(1)).getLength();
        String retCode = strReply.substring(f1Len, f1Len + f2Len);
        if ("0000".equals(retCode)) return true;
        log.warn("发送心跳失败，原因＝" + strReply.substring(f1Len + f2Len));
        return false;
    }

    public Map<String, String> retrieveSMS(SimpleSocket socket, String appid) {
        Map param = new HashMap();
        UmsField field = new UmsField();
        field.setName("SubType");
        field.setValue("  ");
        param.put(field.getName(), field);
        field = new UmsField();
        field.setName("AppId");
        field.setValue(appid);
        param.put(field.getName(), field);

        String strCommand = createCommand(this.retrieveSMSFields, param);
        if (log.isDebugEnabled()) {
            log.debug("retrieveSMS strCommand=" + strCommand);
        }
        if (socket.write(strCommand) <= 0) return null;
        String strReply = readReply(socket);
        if (strReply == null) return null;
        if (log.isDebugEnabled()) log.debug("retrieveSMS strReply=" + strReply);
        int f1Len = ((UmsField) this.genReplyFields.get(0)).getLength();
        int f2Len = ((UmsField) this.genReplyFields.get(1)).getLength();
        String retCode = strReply.substring(f1Len, f1Len + f2Len);
        if ("1162".equals(retCode)) {
            return null;
        }
        if ("1041".equals(retCode)) {
            log.warn("ums-" + appid + " 短信接收异常,短信网关回应未登录");
            return null;
        }
        if ("2001".equals(retCode)) {
            Map smsRep = parseReply(strReply);
            if (log.isDebugEnabled()) {
                log.debug("retrieveSMS smsRep=" + smsRep);
            }
            Map para = new HashMap();
            field = new UmsField();
            field.setName("SerialNO");
            field.setValue((String) smsRep.get("SerialNO"));
            para.put(field.getName(), field);
            field = new UmsField();
            field.setName("BatchNO");
            field.setValue((String) smsRep.get("BatchNO"));
            para.put(field.getName(), field);
            String confirm = createCommand(this.smsConfirmFields, para);
            socket.write(confirm);
            return smsRep;
        }
        return null;
    }

    public int sendUserMessage(SimpleSocket socket, String mobilePhone, String content, String appid, String subAppId, String replyAddr) {
        return sendMessage(socket, mobilePhone, content, "0", appid, subAppId, replyAddr);
    }

    public int sendRtuMessage(SimpleSocket socket, String mobilePhone, String content, String appid, String subAppId, String replyAddr) {
        return sendMessage(socket, mobilePhone, content, "21", appid, subAppId, replyAddr);
    }

    public int sendMessage(SimpleSocket socket, String mobilePhone, String content, String msgType, String appid, String subAppId, String replyAddr) {
        Map param = new HashMap();
        UmsField field = new UmsField();
        field.setName("RecvId");
        field.setValue(mobilePhone);
        param.put(field.getName(), field);

        field = new UmsField();
        field.setName("AppId");
        field.setValue(appid);
        param.put(field.getName(), field);

        field = new UmsField();
        field.setName("Reply");
        if (replyAddr == null) replyAddr = "";
        field.setValue(replyAddr);
        param.put(field.getName(), field);

        if ((msgType == null) || (msgType.length() == 0)) msgType = "21";
        field = new UmsField();
        field.setName("MessageType");
        field.setValue(msgType);
        param.put(field.getName(), field);

        if ((subAppId != null) && (subAppId.length() > 0)) {
            field = new UmsField();
            field.setName("SubApp");
            field.setValue(subAppId);
            param.put(field.getName(), field);
        }
        String strCommand = null;
        if (msgType.equals("0")) {
            field = new UmsField();
            field.setName("Content");
            field.setValue(content);
            param.put(field.getName(), field);
            strCommand = createCommand(this.sendSMSFields, param);
        } else {
            field = new UmsField();
            field.setName("RtuContent");
            field.setValue(content);
            param.put(field.getName(), field);
            strCommand = createCommand(this.sendRtuSMSFields, param);
        }
        if (log.isDebugEnabled()) log.debug("sendMessage strCommand=" + strCommand);
        if (socket.write(strCommand) <= 0) return -1;
        String strReply = readReply(socket);
        if ((strReply == null) || (strReply.length() < 4)) {
            return -1;
        }
        int f1Len = ((UmsField) this.genReplyFields.get(0)).getLength();
        int f2Len = ((UmsField) this.genReplyFields.get(1)).getLength();
        String retCode = strReply.substring(f1Len, f1Len + f2Len);

        if ("0000".equals(retCode)) {
            log.debug("send UMS success. reply=" + strReply);
            return 0;
        }
        if ("1088".equals(retCode)) {
            log.info("ums-" + appid + " send UMS failed. reply=" + strReply);
            return 0;
        }
        log.warn("ums-" + appid + " 发送失败，返回码＝" + retCode + ";strReply=" + strReply);
        return -2;
    }

    public String createCommand(List<UmsField> define, Map<String, UmsField> param) {
        int len = 0;

        StringBuffer sb = new StringBuffer(1024);
        for (int i = 1; i < define.size(); ++i) {
            UmsField field = (UmsField) define.get(i);

            UmsField p = (UmsField) param.get(field.getName());

            if (field.getLength() > 0) {
                len += field.getLength();
                if (p != null) sb.append(String.format("%-" + field.getLength() + "s", new Object[]{p.getValue()}));
                else sb.append(String.format("%-" + field.getLength() + "s", new Object[]{field.getDefValue()}));
            } else {
                if ((!($assertionsDisabled)) && (p == null)) throw new AssertionError();
                try {
                    len += p.getValue().getBytes("GBK").length;
                } catch (Exception e) {
                    log.warn("getValue().getBytes(\"GBK\") exception:", e);
                }
                sb.append(p.getValue());
            }
        }
        sb.insert(0, String.format("%-" + ((UmsField) define.get(0)).getLength() + "s", new Object[]{Integer.valueOf(len)}));
        return sb.toString();
    }

    private Map<String, String> parseReply(String rep) {
        Map map = new HashMap();
        int offset = 0;
        for (UmsField f : this.smsReplyFields) {
            if (f.getLength() > 0) {
                map.put(f.getName(), rep.substring(offset, offset + f.getLength()).trim());
                offset += f.getLength();
            } else {
                map.put(f.getName(), rep.substring(offset).trim());
                break;
            }
        }
        return map;
    }

    public void setCheckPasswordFields(List<UmsField> checkPassword1001) {
        this.checkPasswordFields = checkPassword1001;
    }

    public void setHeartBeatFields(List<UmsField> heart1003) {
        this.heartBeatFields = heart1003;
    }

    public void setSendSMSFields(List<UmsField> sendSMS3011) {
        this.sendSMSFields = sendSMS3011;
    }

    public void setRetrieveSMSFields(List<UmsField> retrieveSMS3012) {
        this.retrieveSMSFields = retrieveSMS3012;
    }

    public void setGenReplyFields(List<UmsField> genReplyFields) {
        this.genReplyFields = genReplyFields;
    }

    public void setSmsReplyFields(List<UmsField> smsReplyFields) {
        this.smsReplyFields = smsReplyFields;
    }

    public void setSmsConfirmFields(List<UmsField> smsConfirmFields) {
        this.smsConfirmFields = smsConfirmFields;
    }

    public void setSendRtuSMSFields(List<UmsField> sendRtuSMS3002) {
        this.sendRtuSMSFields = sendRtuSMS3002;
    }
}