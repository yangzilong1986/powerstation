package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

import java.text.NumberFormat;

public class Parser54 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                StringBuffer sb = new StringBuffer();
                sb.append(ParseTool.ByteToHex(data[(loc + 48)]));
                sb.append(",");
                for (int i = 15; i >= 0; --i) {
                    sb.append(ParseTool.ByteToHex(data[(loc + i * 3 + 2)]));
                    sb.append(",");
                    sb.append(String.valueOf(ParseTool.nBcdToDecimal(data, loc + i * 3, 2) / ParseTool.fraction[2]));
                    sb.append(",");
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
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(4);

            for (int i = 0; i < value.length(); ++i) {
                char c = value.charAt(i);
                if (c == ',') {
                    continue;
                }
                if (c == '.') {
                    continue;
                }
                if ((c >= '0') && (c <= '9')) {
                    continue;
                }
                throw new MessageEncodeException("错误的 NN,SS1,PP.PP1……SS16,PP.PP16 组帧参数:" + value);
            }
            String[] para = value.split(",");
            frame[(loc + 48)] = ParseTool.StringToBcd(para[0]);
            for (int i = 0; i < 16; ++i) {
                double xx = nf.parse(para[(32 - (i * 2))]).doubleValue() * ParseTool.fraction[2];
                ParseTool.IntToBcd(frame, (int) xx, loc + i * 3, 2);
                frame[(loc + i * 3 + 2)] = ParseTool.IntToBcd(Integer.parseInt(para[(32 - (i * 2) - 1)]));
            }
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 NN,SS1,PP.PP1……SS16,PP.PP16 组帧参数:" + value);
        }

        return len;
    }
}