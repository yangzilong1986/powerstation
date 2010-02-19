package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser37 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                int port;
                String ip;
                int type = fraction;

                switch (type) {
                    case 1:
                        rt = ParseTool.toPhoneCode(data, loc, 8, 170);
                        break;
                    case 2:
                        port = ParseTool.nByteToInt(data, loc, 2);
                        ip = (data[(loc + 5)] & 0xFF) + "." + (data[(loc + 4)] & 0xFF) + "." + (data[(loc + 3)] & 0xFF) + "." + (data[(loc + 2)] & 0xFF);

                        rt = ip + ":" + port;
                        break;
                    case 3:
                        rt = ParseTool.toPhoneCode(data, loc, 8, 170);
                        break;
                    case 4:
                        port = ParseTool.nByteToInt(data, loc, 2);
                        ip = (data[(loc + 5)] & 0xFF) + "." + (data[(loc + 4)] & 0xFF) + "." + (data[(loc + 3)] & 0xFF) + "." + (data[(loc + 2)] & 0xFF);

                        rt = ip + ":" + port;
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        rt = ParseTool.toPhoneCode(data, loc, 8, 170);
                        label352:
                        break label352:
                    case 8:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        try {
            int flen;
            int i;
            int type = fraction;
            switch (type) {
                case 1:
                    ParseTool.StringToBcds(frame, loc, value);
                    flen = (value.length() >>> 1) + (value.length() & 0x1);
                    for (i = loc + flen; i < loc + len; ++i) {
                        frame[i] = -86;
                    }
                    break;
                case 2:
                    ParseTool.IPToBytes(frame, loc, value);
                    frame[(loc + 6)] = -86;
                    frame[(loc + 7)] = -86;
                    break;
                case 3:
                    ParseTool.StringToBcds(frame, loc, value);
                    flen = (value.length() >>> 1) + (value.length() & 0x1);
                    for (i = loc + flen; i < loc + len; ++i) {
                        frame[i] = -86;
                    }
                    break;
                case 4:
                    ParseTool.IPToBytes(frame, loc, value);
                    frame[(loc + 6)] = -86;
                    frame[(loc + 7)] = -86;
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    ParseTool.StringToBcds(frame, loc, value);
                    flen = (value.length() >>> 1) + (value.length() & 0x1);
                    for (i = loc + flen; i < loc + len; ++i) {
                        frame[i] = -86;
                    }
                    label261:
                    break label261:
                case 8:
            }

        } catch (Exception e) {
            throw new MessageEncodeException("错误的 主站通讯地址 组帧参数:" + value);
        }

        return len;
    }
}