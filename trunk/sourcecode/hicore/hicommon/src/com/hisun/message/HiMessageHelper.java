package com.hisun.message;

import com.hisun.exception.HiException;
import com.hisun.util.HiByteBuffer;
import org.apache.commons.lang.StringUtils;

public class HiMessageHelper {
    public static String getCurEtfLevel(HiMessage msg) {
        String curLevel = msg.getHeadItem("ETF_LEVEL");

        if (curLevel == null) {
            return "";
        }

        return curLevel;
    }

    public static void setCurEtfLevel(HiMessage msg, String curLevel) {
        msg.setHeadItem("ETF_LEVEL", curLevel);
    }

    public static void addEtfItem(HiMessage etfMsg, String name, String item) throws HiException {
        HiETF etfBody = (HiETF) etfMsg.getBody();
        try {
            if (name.startsWith("ROOT.")) etfBody.setGrandChildNode(name, item);
            else etfBody.setGrandChildNode(getCurEtfLevel(etfMsg) + name, item);
        } catch (Exception e) {
            throw new HiException("213139", new String[]{name, item}, e);
        }
    }

    public static String getEtfItem(HiMessage etfMsg, String name) {
        HiETF etfBody = (HiETF) etfMsg.getBody();

        if (name.startsWith("ROOT.")) {
            return etfBody.getGrandChildValue(name);
        }
        return etfBody.getGrandChildValue(getCurEtfLevel(etfMsg) + name);
    }

    public static boolean isInnerMessage(HiMessage msg) {
        return StringUtils.equalsIgnoreCase(msg.getHeadItem("ECT"), "text/etf");
    }

    public static boolean isOutterMessage(HiMessage msg) {
        return (!(isInnerMessage(msg)));
    }

    public static boolean isRequestMessage(HiMessage msg) {
        return StringUtils.equalsIgnoreCase(msg.getHeadItem("SCH"), "rq");
    }

    public static boolean isResponseMessage(HiMessage msg) {
        return StringUtils.equalsIgnoreCase(msg.getHeadItem("SCH"), "rp");
    }

    public static void setRequestMessage(HiMessage msg) {
        msg.setHeadItem("SCH", "rq");
    }

    public static void setResponseMessage(HiMessage msg) {
        msg.setHeadItem("SCH", "rp");
    }

    public static void setMessageTypePlain(HiMessage msg) {
        msg.setHeadItem("ECT", "text/plain");
    }

    public static void setMessageTypeETF(HiMessage msg) {
        msg.setHeadItem("ECT", "text/etf");
    }

    public static void setMessageTypeXML(HiMessage msg) {
        msg.setHeadItem("ECT", "text/xml");
    }

    public static void setUnpackMessage(HiMessage msg, HiByteBuffer byteBuffer) {
        msg.setHeadItem("PlainText", byteBuffer);
        msg.setHeadItem("ECT", "text/plain");
        msg.setHeadItem("SCH", "rq");
    }

    public static void setPlainOffset(HiMessage msg, int offset) {
        msg.setHeadItem("PlainOffset", String.valueOf(offset));
    }

    public static HiByteBuffer getUnpackMessageBuffer(HiMessage msg) {
        HiByteBuffer byteBuffer = (HiByteBuffer) msg.getObjectHeadItem("PlainText");

        msg.delHeadItem("PlainText");
        return byteBuffer;
    }

    public static void setPackMessage(HiMessage msg, HiByteBuffer byteBuffer) {
        msg.setHeadItem("PlainText", byteBuffer);
        msg.setHeadItem("ECT", "text/etf");
        msg.setHeadItem("SCH", "rp");
    }

    public static void setInnerMessage(HiMessage msg) {
        msg.setHeadItem("ECT", "text/etf");
    }

    public static void setOutterMessage(HiMessage msg) {
        msg.setHeadItem("ECT", "text/plain");
    }

    public static String getMessageTextType(HiMessage msg) {
        return msg.getHeadItem("ECT");
    }

    public static HiMessage setMessageTmOut(HiMessage msg, int tmOut) {
        long curtime = System.currentTimeMillis();
        msg.setHeadItem("STM", new Long(curtime));

        if (tmOut > 0) {
            long exptime = curtime + tmOut * 1000;
            msg.setHeadItem("ETM", new Long(exptime));
        }
        return msg;
    }

    public static HiMessage setMessageTmOut(HiMessage msg) {
        long curtime = System.currentTimeMillis();
        msg.setHeadItem("ETM", new Long(curtime - 100L));
        return msg;
    }
}