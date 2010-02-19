package com.hzjbbis.fas.protocol.meter;

import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BbMeterFrame extends AbstractMeterFrame {
    private final Log log = LogFactory.getLog(BbMeterFrame.class);
    public static final int CHARACTER_HEAD_FLAG = 104;
    public static final int CHARACTER_TAIL_FLAG = 22;
    public static final int MINIMUM_FRAME_LENGTH = 12;
    public static final int FLAG_ADDRESS_POSITION = 1;
    public static final int FLAG_DATA_POSITION = 10;
    public static final int FLAG_CTRL_POSITION = 8;
    public static final int FLAG_BLOCK_DATA = 170;
    private int datalen;
    private int pos;
    private String meteraddr;
    private int ctrl;

    public BbMeterFrame() {
        this.datalen = 0;
        this.pos = 10;
    }

    public BbMeterFrame(byte[] data, int loc, int len) {
        parse(data, loc, len);
        this.pos = 10;
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
                while ((rbound - loc >= 12) && (head <= rbound - 12)) {
                    if ((104 == (data[head] & 0xFF)) && (104 == (data[(head + 7)] & 0xFF))) {
                        int flen = data[(head + 9)] & 0xFF;
                        if ((head + flen + 10 + 1 <= rbound) && (22 == (data[(head + 10 + flen + 1)] & 0xFF)) && (ParseTool.calculateCS(data, head, flen + 10) == data[(head + 10 + flen)])) {
                            this.start = 0;
                            this.len = (flen + 12);
                            this.data = new byte[this.len];
                            this.datalen = flen;
                            System.arraycopy(data, head, this.data, this.start, this.len);
                            this.meteraddr = ParseTool.BytesToHexC(this.data, 1, 6, -86);
                            this.pos = 10;
                            this.ctrl = this.data[8];

                            adjustData(this.data, this.pos, this.datalen, 51);
                            break;
                        }

                    }

                    ++head;
                }
            }
        } catch (Exception e) {
            this.log.error("部颁帧识别", e);
        }
    }

    private void adjustData(byte[] data, int start, int len, int adjust) {
        if ((data != null) && (data.length >= start + len)) for (int i = start; i < start + len; ++i) {
            int tmp26_24 = i;
            byte[] tmp26_23 = data;
            tmp26_23[tmp26_24] = (byte) (tmp26_23[tmp26_24] - adjust);
        }
    }

    public int getDatalen() {
        return this.datalen;
    }

    public void setDatalen(int datalen) {
        this.datalen = datalen;
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

    public int getCtrl() {
        return this.ctrl;
    }
}