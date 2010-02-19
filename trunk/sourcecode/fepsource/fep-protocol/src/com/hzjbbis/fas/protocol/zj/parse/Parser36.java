package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser36 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (len != 4) {
                ok = false;
            }
            if (ok) {
                StringBuffer sb = new StringBuffer();
                sb.append(ParseTool.BytesToHexL(data, loc, 2));
                sb.append(ParseTool.BytesToHexL(data, loc + 2, 2));
                rt = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        try {
            ParseTool.HexsToBytesCB(frame, loc, value.substring(0, 4));
            ParseTool.HexsToBytesCB(frame, loc + 2, value.substring(4, 8));
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 A1A2B2B1 组帧参数:" + value);
        }
        return len;
    }
}