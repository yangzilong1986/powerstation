package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser16 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                int port = ParseTool.nByteToInt(data, loc, 2);
                String ip = (data[(loc + 5)] & 0xFF) + "." + (data[(loc + 4)] & 0xFF) + "." + (data[(loc + 3)] & 0xFF) + "." + (data[(loc + 2)] & 0xFF);

                rt = ip + ":" + port;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        try {
            ParseTool.IPToBytes(frame, loc, value);
            frame[(loc + 6)] = -86;
            frame[(loc + 7)] = -86;
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 网关 组帧参数:" + value);
        }
        return len;
    }
}