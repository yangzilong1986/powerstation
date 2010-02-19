package com.hzjbbis.fas.protocol.meter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SmMeterFrame extends AbstractMeterFrame {
    private final Log log;
    public static final int SIEMENS_FRAME_HEAD = 47;

    public SmMeterFrame() {
        this.log = LogFactory.getLog(SmMeterFrame.class);
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
                if (head < rbound) {
                    this.start = 0;
                    this.len = (rbound - head);
                    this.data = new byte[this.len];
                    System.arraycopy(data, head, this.data, this.start, this.len);
                }
            }

        } catch (Exception e) {
            this.log.error("西门子表规约解析", e);
        }
    }
}