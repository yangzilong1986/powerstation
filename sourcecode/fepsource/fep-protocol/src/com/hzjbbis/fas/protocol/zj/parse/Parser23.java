package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

import java.text.NumberFormat;

public class Parser23 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                StringBuffer sb = new StringBuffer();
                sb.append(ParseTool.ByteToHex(data[(loc + 6)]));
                sb.append(":");
                sb.append(ParseTool.ByteToHex(data[(loc + 5)]));
                sb.append(",");
                sb.append(ParseTool.ByteToHex(data[(loc + 4)]));
                sb.append(",");
                int val = ParseTool.nBcdToDecimal(data, loc, 4);
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
                if (c == '.') {
                    continue;
                }
                if ((c >= '0') && (c <= '9')) {
                    continue;
                }
                throw new MessageEncodeException("错误的 HH:mm NN XXXXXX.XX 组帧参数:" + value);
            }

            String[] para = value.split(",");
            String[] time = para[0].split(":");

            frame[(loc + 6)] = ParseTool.StringToBcd(time[0]);
            frame[(loc + 5)] = ParseTool.StringToBcd(time[1]);
            frame[(loc + 4)] = ParseTool.StringToBcd(para[1]);

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(4);
            double val = nf.parse(para[2]).doubleValue();
            val *= ParseTool.fraction[2];
            ParseTool.IntToBcd(frame, (int) val, loc, 4);
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 HH:mm NN XXXXXX.XX 组帧参数:" + value);
        }

        return len;
    }
}