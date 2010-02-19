package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser05 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            if (ok) rt = ParseTool.BytesToHexC(data, loc, len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        try {
            for (int i = 0; i < value.length(); ++i) {
                char c = value.charAt(i);
                if ((c >= 'a') && (c <= 'f')) {
                    continue;
                }
                if ((c >= 'A') && (c <= 'F')) {
                    continue;
                }
                if ((c >= '0') && (c <= '9')) {
                    continue;
                }
                throw new MessageEncodeException("错误的 Hex 组帧参数:" + value);
            }
            ParseTool.HexsToBytes(frame, loc, value);
        } catch (Exception e) {
            throw new MessageEncodeException("错误的HEX码组帧参数:" + value);
        }
        return len;
    }
}