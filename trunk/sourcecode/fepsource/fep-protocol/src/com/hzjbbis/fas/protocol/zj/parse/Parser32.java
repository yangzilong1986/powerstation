package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

import java.text.NumberFormat;

public class Parser32 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                StringBuffer sb = new StringBuffer();
                sb.append(String.valueOf(ParseTool.BytesToHexC(data, loc + 9, 2)));
                sb.append(",");
                sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(loc + 8)])));
                sb.append(",");
                sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(loc + 7)])));
                sb.append(",");
                sb.append(String.valueOf(ParseTool.nBcdToDecimal(data, loc + 4, 3) / ParseTool.fraction[2]));
                sb.append(",");
                sb.append(String.valueOf(ParseTool.nBcdToDecimal(data, loc + 2, 2) / ParseTool.fraction[2]));
                sb.append(",");
                sb.append(String.valueOf(ParseTool.nBcdToDecimal(data, loc, 2) / ParseTool.fraction[2]));
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
                if ((c >= 'A') && (c <= 'F')) {
                    continue;
                }
                if ((c >= 'a') && (c <= 'f')) {
                    continue;
                }
                throw new MessageEncodeException("错误的 DI1DI0 NN1 NN2 MMMM.MM RR.RR SS.SS 组帧参数:" + value);
            }

            String[] para = value.split(",");

            ParseTool.HexsToBytes(frame, loc + 9, para[0]);
            frame[(loc + 8)] = ParseTool.IntToBcd(Integer.parseInt(para[1]));
            frame[(loc + 7)] = ParseTool.IntToBcd(Integer.parseInt(para[2]));
            double val = nf.parse(para[3]).doubleValue();
            val *= ParseTool.fraction[2];
            ParseTool.IntToBcd(frame, (int) val, loc + 4, 3);
            val = nf.parse(para[4]).doubleValue();
            val *= ParseTool.fraction[2];
            ParseTool.IntToBcd(frame, (int) val, loc + 2, 2);
            val = nf.parse(para[5]).doubleValue();
            val *= ParseTool.fraction[2];
            ParseTool.IntToBcd(frame, (int) val, loc, 2);
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 DI1DI0 NN1 NN2 MMMM.MM RR.RR SS.SS 组帧参数:" + value);
        }

        return len;
    }
}