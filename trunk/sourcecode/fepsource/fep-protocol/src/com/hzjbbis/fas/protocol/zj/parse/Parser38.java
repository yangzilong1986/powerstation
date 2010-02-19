package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser38 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            rt = Parser43.parsevalue(data, loc, len, fraction);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        int slen = -1;
        try {
            slen = Parser43.constructor(frame, value, loc, len, fraction);
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 ascii码字符 组帧参数:" + value);
        }
        return slen;
    }
}