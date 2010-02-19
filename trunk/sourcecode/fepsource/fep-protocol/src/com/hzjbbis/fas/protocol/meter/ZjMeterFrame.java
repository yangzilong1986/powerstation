package com.hzjbbis.fas.protocol.meter;

import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ZjMeterFrame extends AbstractMeterFrame {
    private final Log log = LogFactory.getLog(ZjMeterFrame.class);
    public static final int CHARACTER_HEAD_FLAG = 104;
    public static final int CHARACTER_TAIL_FLAG = 13;
    public static final int MINIMUM_FRAME_LENGTH = 7;
    public static final int FLAG_REPLY_ERROR = 240;
    public static final int FLAG_REPLY_OK = 250;
    public static final int FLAG_BLOCK_DATA = 237;
    public static final int FLAG_NO_DATA = 186;
    public static final int FLAG_ADDRESS_POSITION = 4;
    private int datalen;
    private int pos;
    private String meteraddr;

    public ZjMeterFrame() {
        this.pos = 4;
    }

    public ZjMeterFrame(byte[] data, int loc, int len) {
        parse(data, loc, len);
        this.pos = 4;
    }

    public void parse(byte[] data, int loc, int len) {
        int head = loc;
        int rbound = 0;

        super.clear();
        try {
            if (data != null) {
                if (data.length > loc + len) rbound = loc + len;
                else {
                    rbound = data.length;
                }
                while ((rbound - loc >= 7) && (head <= rbound - 7)) {
                    if ((104 == (data[head] & 0xFF)) && (104 == data[(head + 3)]) && (data[(head + 1)] == data[(head + 2)])) {
                        int flen = data[(head + 1)] & 0xFF;
                        if ((head + flen + 4 + 2 <= rbound) && (13 == (data[(head + 5 + flen)] & 0xFF)) && (calculateCS(data, head + 4, flen) == data[(head + 4 + flen)])) {
                            this.start = 0;
                            this.len = (flen + 6);
                            this.data = new byte[this.len];
                            this.datalen = flen;
                            System.arraycopy(data, head, this.data, this.start, this.len);
                            this.meteraddr = ParseTool.ByteToHex(this.data[this.pos]);
                            this.pos = 4;
                            break;
                        }

                    }

                    ++head;
                }
            }
        } catch (Exception e) {
            this.log.error("浙江表规约解析", e);
        }
    }

    private byte calculateCS(byte[] data, int start, int len) {
        int cs = 0;
        for (int i = start; i < start + len; ++i) {
            cs += (data[i] & 0xFF);
            cs &= 255;
        }
        return (byte) (cs & 0xFF);
    }

    public int getDatalen() {
        return this.datalen;
    }

    public String getMeteraddr() {
        return this.meteraddr;
    }

    public void setMeteraddr(String meteraddr) {
        this.meteraddr = meteraddr;
    }

    public int getPos() {
        return this.pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}