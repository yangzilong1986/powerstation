package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

import java.text.NumberFormat;

public class Parser25 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                StringBuffer sb = new StringBuffer();
                boolean bn = (data[(loc + len - 1)] & 0x10) > 0;
                int val = ParseTool.nBcdToDecimalS(data, loc + 1, 4);
                if (bn) {
                    val = -val;
                }
                sb.append(String.valueOf(val));
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
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(4);

            for (int i = 0; i < value.length(); ++i) {
                char c = value.charAt(i);
                if (c == ',') {
                    continue;
                }
                if (c == '-') {
                    continue;
                }
                if (c == '.') {
                    continue;
                }
                if ((c >= '0') && (c <= '9')) {
                    continue;
                }
                throw new MessageEncodeException("错误的 SNNNNNNN XX 组帧参数:" + value);
            }
            String[] para = value.split(",");

            int val = nf.parse(para[0]).intValue();
            boolean bn = val < 0;
            if (bn) {
                val = -val;
            }
            ParseTool.IntToBcd(frame, val, loc + 1, 4);
            if (bn) {
                frame[(loc + 4)] = (byte) (frame[(loc + 4)] & 0xF | 0x10);
            }

            frame[loc] = ParseTool.StringToBcd(para[1]);
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 SNNNNNNN XX 组帧参数:" + value);
        }

        return len;
    }
}