package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

import java.util.Arrays;

public class Parser43 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            int begin = loc;
            for (int i = 0; i < len; ++i) {
                if (((data[(loc + i)] & 0xFF) != 0) && ((data[(loc + i)] & 0xFF) < 128)) {
                    break;
                }
                ++begin;
            }
            int rlen = 0;
            for (int i = begin; (i < loc + len) && ((data[i] & 0xFF) != 0); ++i) {
                if ((data[i] & 0xFF) >= 128) {
                    break;
                }
                ++rlen;
            }
            if (rlen > 0) {
                byte[] apn = new byte[rlen];
                int iloc = begin + rlen - 1;
                for (int i = 0; i < rlen; ++i) {
                    apn[i] = data[iloc];
                    --iloc;
                }
                rt = new String(apn, "GBK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        int slen = -1;
        try {
            int src;
            int dest;
            int i;
            Arrays.fill(frame, loc, loc + len - 1, 0);
            byte[] str = value.getBytes();
            int rlen = str.length;
            if (rlen > len) {
                rlen = len;
            }
            if (fraction == 0) {
                src = str.length - 1;
                dest = loc;
                for (i = 0; i < rlen; ++i) {
                    frame[dest] = str[src];
                    --src;
                    ++dest;
                }
            } else {
                src = 0;
                dest = loc + len - 1;
                for (i = 0; i < rlen; ++i) {
                    frame[dest] = str[src];
                    ++src;
                    --dest;
                }
            }

            slen = len;
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 ascii字符码 组帧参数:" + value);
        }
        return slen;
    }
}