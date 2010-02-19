package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser44 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            rt = ParseTool.BytesBit(data, loc, len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        int slen = -1;
        try {
            int vlen = value.length();
            for (int i = 0; i < vlen; ++i) {
                if (value.substring(i, i + 1).equals("0")) continue;
                if (value.substring(i, i + 1).equals("1")) {
                    continue;
                }
                throw new MessageEncodeException("错误的 bit位码 组帧参数:" + value);
            }

            if ((vlen & 0x7) == 0) {
                int blen = 0;
                int iloc = loc + len - 1;
                while (blen < vlen) {
                    frame[iloc] = ParseTool.bitToByte(value.substring(blen, blen + 8));
                    blen += 8;
                    --iloc;
                }
                slen = len;
            }
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 bit位码 组帧参数:" + value);
        }
        return slen;
    }
}