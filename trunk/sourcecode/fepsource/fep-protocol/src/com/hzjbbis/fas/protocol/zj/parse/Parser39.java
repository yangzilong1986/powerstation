package com.hzjbbis.fas.protocol.zj.parse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser39 {
    private static final Log log = LogFactory.getLog(Parser39.class);

    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                StringBuffer sb = new StringBuffer();
                int num = ((data[1] & 0xFF) << 8) + (data[0] & 0xFF);
                int iloc = loc + 2;
                rt = new ArrayList();
                for (int i = 0; i < num; ++i) ;
                rt = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        int slen = -1;
        try {
            String[] para = value.split(",");
            if ((para != null) && (para.length > 0)) {
                int iloc = loc;

                ParseTool.RtuaToBytesC(frame, para[0], iloc, 4);
                iloc += 4;

                if (!(para[1].equals("null"))) frame[iloc] = (byte) Integer.parseInt(para[1]);
                else {
                    frame[iloc] = -1;
                }
                ++iloc;

                if (!(para[2].equals("null"))) {
                    if (fraction > 0) Parser37.constructor(frame, para[2], iloc, 8, 1);
                    else Parser37.constructor(frame, para[2], iloc, 8, frame[(iloc - 1)] & 0xFF);
                } else {
                    Arrays.fill(frame, iloc, iloc + 8, -1);
                }
                iloc += 8;

                if (!(para[3].equals("null"))) frame[iloc] = (byte) Integer.parseInt(para[3]);
                else {
                    frame[iloc] = -1;
                }
                ++iloc;

                if (!(para[4].equals("null"))) frame[iloc] = para[4].getBytes()[0];
                else {
                    frame[iloc] = -1;
                }
                ++iloc;

                if (!(para[5].equals("null"))) Parser43.constructor(frame, para[5], iloc, 10, 0);
                else {
                    Arrays.fill(frame, iloc, iloc + 10, -1);
                }
                iloc += 10;

                if (!(para[6].equals("null"))) Parser43.constructor(frame, para[6], iloc, 20, 0);
                else {
                    Arrays.fill(frame, iloc, iloc + 20, -1);
                }
                iloc += 20;

                if (!(para[7].equals("null"))) Parser43.constructor(frame, para[7], iloc, 12, 0);
                else {
                    Arrays.fill(frame, iloc, iloc + 12, -1);
                }
                iloc += 12;

                if (!(para[8].equals("null"))) Parser43.constructor(frame, para[8], iloc, 14, 0);
                else {
                    Arrays.fill(frame, iloc, iloc + 14, -1);
                }

                slen = 71;
            }
        } catch (Exception e) {
            log.warn("错误的 终端参数 组帧参数:" + value);
        }
        return slen;
    }
}