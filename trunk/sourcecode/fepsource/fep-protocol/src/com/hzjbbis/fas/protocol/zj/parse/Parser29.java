package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser29 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                StringBuffer sb = new StringBuffer();
                sb.append(ParseTool.ByteToHex(data[(loc + 2)]));
                sb.append(",");
                sb.append(ParseTool.ByteToHex(data[(loc + 1)]));
                sb.append(",");
                sb.append(ParseTool.ByteToHex(data[loc]));
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
                if ((c >= '0') && (c <= '9')) {
                    continue;
                }
                if ((c >= 'A') && (c <= 'F')) {
                    continue;
                }
                if ((c >= 'a') && (c <= 'f')) {
                    continue;
                }
                throw new MessageEncodeException("错误的 MM YP 组帧参数:" + value);
            }
            String[] para = value.split(",");
            frame[loc] = ParseTool.IntToBcd(Integer.parseInt(para[2]));
            frame[(loc + 1)] = ParseTool.IntToBcd(Integer.parseInt(para[1]));
            frame[(loc + 2)] = ParseTool.IntToBcd(Integer.parseInt(para[0]));
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 MM YP 组帧参数:" + value);
        }

        return len;
    }
}