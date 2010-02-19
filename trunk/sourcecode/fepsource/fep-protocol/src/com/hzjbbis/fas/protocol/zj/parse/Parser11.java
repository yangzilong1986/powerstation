package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser11 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                StringBuffer sb = new StringBuffer();
                sb.append("20");
                sb.append(ParseTool.ByteToHex(data[(loc + 7)]));
                sb.append("-");
                sb.append(ParseTool.ByteToHex(data[(loc + 6)]));
                sb.append("-");
                sb.append(ParseTool.ByteToHex(data[(loc + 5)]));
                sb.append(" ");
                sb.append(ParseTool.ByteToHex(data[(loc + 4)]));
                sb.append(":");
                sb.append(ParseTool.ByteToHex(data[(loc + 3)]));
                sb.append(",");
                boolean bn = (data[(loc + 3 - 1)] & 0x10) > 0;
                int val = ParseTool.nBcdToDecimalS(data, loc, 3);
                if (bn) {
                    val = -val;
                }
                sb.append(String.valueOf(val / ParseTool.fraction[2]));
                rt = sb.toString();
            }
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
                if (c == '.') {
                    continue;
                }
                if (c == ' ') {
                    continue;
                }
                if ((c >= '0') && (c <= '9')) {
                    continue;
                }
                throw new MessageEncodeException("错误的 YYYY-MM-DD HH:mm SXXX.XX 组帧参数:" + value);
            }
            String[] para = value.split(",");
            String[] dpara = para[0].split(" ");
            String[] date = dpara[0].split("-");
            String[] time = dpara[1].split(":");

            Parser02.constructor(frame, para[1], loc, 3, 2);
            frame[(loc + 7)] = ParseTool.StringToBcd(date[0]);
            frame[(loc + 6)] = ParseTool.StringToBcd(date[1]);
            frame[(loc + 5)] = ParseTool.StringToBcd(date[2]);
            frame[(loc + 4)] = ParseTool.StringToBcd(time[0]);
            frame[(loc + 3)] = ParseTool.StringToBcd(time[1]);
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 YYYY-MM-DD HH:mm SXXX.XX 组帧参数:" + value);
        }

        return len;
    }
}