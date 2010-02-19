package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser09 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                StringBuffer sb = new StringBuffer();
                sb.append(ParseTool.ByteToHex(data[(loc + 2)]));
                sb.append(":");
                sb.append(ParseTool.ByteToHex(data[(loc + 1)]));
                sb.append(":");
                sb.append(ParseTool.ByteToHex(data[loc]));
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
            for (int i = 0; i < value.length(); ++i) {
                char c = value.charAt(i);
                if (c == ':') {
                    continue;
                }
                if ((c >= '0') && (c <= '9')) {
                    continue;
                }
                throw new MessageEncodeException("错误的 HH:mm:ss 组帧参数:" + value);
            }
            String[] para = value.split(":");
            frame[loc] = ParseTool.StringToBcd(para[2]);
            frame[(loc + 1)] = ParseTool.StringToBcd(para[1]);
            frame[(loc + 2)] = ParseTool.StringToBcd(para[0]);
            slen = len;
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 hh:mm:ss 组帧参数:" + value);
        }
        return slen;
    }
}