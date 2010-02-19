package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser06 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            if (ok) rt = ParseTool.BytesToHexL(data, loc, len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        try {
            ParseTool.HexsToBytesCB(frame, loc, value);
        } catch (Exception e) {
            throw new MessageEncodeException("错误的HEX码组帧参数:" + value);
        }
        return len;
    }
}