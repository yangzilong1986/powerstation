package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser33 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) rt = ParseTool.BytesToHexC(data, loc, len, -86);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        try {
            for (int i = 0; i < value.length(); ++i) {
                char c = value.charAt(i);
                if (c == ',') {
                    continue;
                }
                if (c == ':') {
                    continue;
                }
                if (c == '-') {
                    continue;
                }
                if ((c >= '0') && (c <= '9')) {
                    continue;
                }
                throw new MessageEncodeException("错误的 BCD 组帧参数:" + value);
            }
            ParseTool.StringToBcds(frame, loc, value, len, -86);
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 BCD 组帧参数:" + value);
        }

        return len;
    }
}