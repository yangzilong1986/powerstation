package com.hisun.atmp.tcp;

import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.protocol.tcp.HiMessageInOut;
import com.hisun.protocol.tcp.HiSocketUtil;
import com.hisun.util.HiByteBuffer;
import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HiPOSMessageInOut extends HiMessageInOut {
    private Logger log;

    public void setLog(Logger log) {
        this.log = log;
    }

    public HiPOSMessageInOut(Logger log) {
        this.log = log;
    }

    public HiPOSMessageInOut() {
    }

    public int read(InputStream in, HiMessage msg) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream(getSocketBuffer());

        if (getPreLen() == 0) HiSocketUtil.read(in, buf);
        else HiSocketUtil.read(in, buf, getPreLen(), getPreLenType());
        if (buf.size() == 0) {
            return 0;
        }
        byte[] data = buf.toByteArray();
        byte[] tudu = new byte[4];

        System.arraycopy(data, 1, tudu, 0, 4);
        msg.setHeadItem("POS_TPDU", tudu);
        byte[] d8583 = new byte[data.length - 5];
        System.arraycopy(data, 5, d8583, 0, data.length - 5);
        msg.setBody(new HiByteBuffer(d8583));

        byte[] data1 = getMACData(d8583);
        msg.setHeadItem("MACDATA", new String(Hex.encodeHex(data1)));
        msg.setHeadItem("DATALEN", String.valueOf(data1.length));

        msg.setHeadItem("ECT", "text/plain");

        return data.length;
    }

    private byte[] getMACData(byte[] d8583) {
        int len = d8583.length - 8;
        len = (len > 512) ? 512 : len;
        byte[] ret = new byte[len];
        System.arraycopy(d8583, 0, ret, 0, len);
        return ret;
    }

    public void write(OutputStream out, HiMessage msg) throws IOException {
        byte[] data = ((HiByteBuffer) msg.getBody()).getBytes();
        byte[] buf = new byte[data.length + 5];
        byte[] tpdu = (byte[]) (byte[]) msg.getObjectHeadItem("POS_TPDU");
        buf[0] = 96;
        buf[1] = tpdu[2];
        buf[2] = tpdu[3];
        buf[3] = tpdu[0];
        buf[4] = tpdu[1];
        System.arraycopy(data, 0, buf, 5, data.length);
        if (getPreLen() == 0) HiSocketUtil.write(out, buf);
        else HiSocketUtil.write(out, buf, getPreLen(), getPreLenType());
    }
}