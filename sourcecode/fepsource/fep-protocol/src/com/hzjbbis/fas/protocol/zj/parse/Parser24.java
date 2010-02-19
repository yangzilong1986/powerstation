package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

import java.util.Arrays;

public class Parser24 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc + 4, len - 4);
            if (ok) {
                StringBuffer sb = new StringBuffer();
                sb.append(ParseTool.ByteToHex(data[(loc + 8)]));
                sb.append("-");
                sb.append(ParseTool.ByteToHex(data[(loc + 7)]));
                sb.append(",");
                sb.append(ParseTool.ByteToHex(data[(loc + 6)]));
                sb.append("-");
                sb.append(ParseTool.ByteToHex(data[(loc + 5)]));
                sb.append(",");
                int ti = data[(loc + 4)] & 0xFF;
                sb.append(ParseTool.ByteToHex(data[(loc + 4)]));
                sb.append(",");
                switch (ti) {
                    case 4:
                        sb.append(ParseTool.ByteToHex(data[loc]));
                        break;
                    case 5:
                        sb.append(ParseTool.BytesBitC(data, loc, 4));
                        break;
                    case 6:
                        sb.append(ParseTool.ByteBitC(data[loc]));
                        break;
                    default:
                        sb.append("0");
                }

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
                if (c == '-') {
                    continue;
                }
                if ((c >= '0') && (c <= '9')) {
                    continue;
                }
                throw new MessageEncodeException("错误的 MS-DS ME-DE TI N3N2N1N0 组帧参数:" + value);
            }

            String[] para = value.split(",");
            String[] sdate = para[0].split("-");
            String[] edate = para[1].split("-");

            frame[(loc + 8)] = ParseTool.StringToBcd(sdate[0]);
            frame[(loc + 7)] = ParseTool.StringToBcd(sdate[1]);
            frame[(loc + 6)] = ParseTool.StringToBcd(edate[0]);
            frame[(loc + 5)] = ParseTool.StringToBcd(edate[1]);

            int ti = Integer.parseInt(para[2]);
            frame[(loc + 4)] = (byte) (ti % 10);
            Arrays.fill(frame, loc, loc + 3, 0);
            switch (ti) {
                case 4:
                    frame[loc] = ParseTool.IntToBcd(Integer.parseInt(para[3]));
                    break;
                case 5:
                    ParseTool.bitToBytesC(frame, para[3], loc);
                    break;
                case 6:
                    ParseTool.bitToBytesC(frame, para[3], loc);
            }

        } catch (Exception e) {
            throw new MessageEncodeException("错误的 MS-DS ME-DE TI N3N2N1N0 组帧参数:" + value);
        }

        return len;
    }
}