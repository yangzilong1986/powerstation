package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser15 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) rt = ParseTool.toPhoneCode(data, loc, len, 170);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        try {
            for (int i = 0; i < value.length(); ++i) {
                char c = value.charAt(i);
                if ((c >= '0') && (c <= '9')) {
                    continue;
                }
                throw new MessageEncodeException("错误的 短信中心号码 组帧参数:" + value);
            }
            ParseTool.StringToBcds(frame, loc, value);
            int flen = (value.length() >>> 1) + (value.length() & 0x1);
            for (int i = loc + flen; i < loc + len; ++i)
                frame[i] = -86;
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 短信中心号码 组帧参数:" + value);
        }
        return len;
    }
}