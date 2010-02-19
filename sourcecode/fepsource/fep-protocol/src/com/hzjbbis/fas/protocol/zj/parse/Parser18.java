package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser18 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) rt = ParseTool.BytesToHexL(data, loc, len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        try {
            int nn = Integer.parseInt(value);
            ParseTool.IntToBcdC(frame, nn, loc, len);
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 BCD 组帧参数:" + value);
        }

        return len;
    }
}