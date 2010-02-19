package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser27 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                StringBuffer sb = new StringBuffer();
                sb.append(ParseTool.ByteToHex(data[loc]));
                sb.append(",");
                sb.append(String.valueOf(data[(loc + 2)] & 0xFF));
                sb.append(",");
                sb.append(ParseTool.ByteToHex(data[(loc + 1)]));
                sb.append(",");
                sb.append(String.valueOf(data[(loc + 4)] & 0xFF));
                sb.append(",");
                sb.append(ParseTool.ByteToHex(data[(loc + 3)]));
                sb.append(",");
                sb.append(String.valueOf(data[(loc + 6)] & 0xFF));
                sb.append(",");
                sb.append(ParseTool.ByteToHex(data[(loc + 5)]));
                sb.append(",");
                sb.append(String.valueOf(data[(loc + 8)] & 0xFF));
                sb.append(",");
                sb.append(ParseTool.ByteToHex(data[(loc + 7)]));
                sb.append(",");
                sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(loc + 9)])));
                sb.append(",");
                sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(loc + 10)])));
                sb.append(",");
                sb.append(ParseTool.ByteToHex(data[(loc + 11)]));
                sb.append(",");
                sb.append(String.valueOf(ParseTool.nBcdToDecimal(data, loc + 12, 2)));
                sb.append(",");
                sb.append(String.valueOf(data[(loc + 14)] & 0xFF));
                sb.append(",");
                sb.append(ParseTool.ByteToHex(data[(loc + 15)]));
                sb.append(",");
                sb.append(String.valueOf(ParseTool.nByteToInt(data, loc + 16, 2)));
                sb.append(",");
                sb.append(String.valueOf(ParseTool.nByteToInt(data, loc + 18, 2)));
                sb.append(",");
                int cl = ParseTool.BCDToDecimal(data[(loc + 20)]);
                sb.append(String.valueOf(cl));
                sb.append(",");
                if (cl <= 32) {
                    sb.append(ParseTool.BytesToHexL(data, loc + 21, cl));
                }
                rt = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        int nums = 0;
        int slen = -1;
        try {
            String[] para = value.split(",");
            frame[loc] = ParseTool.IntToBcd(Integer.parseInt(para[0]));

            frame[(loc + 1)] = ParseTool.StringToBcd(para[2]);
            frame[(loc + 2)] = (byte) (Integer.parseInt(para[1]) & 0xFF);

            frame[(loc + 3)] = ParseTool.StringToBcd(para[4]);
            frame[(loc + 4)] = (byte) (Integer.parseInt(para[3]) & 0xFF);

            frame[(loc + 5)] = ParseTool.StringToBcd(para[6]);
            frame[(loc + 6)] = (byte) (Integer.parseInt(para[5]) & 0xFF);

            frame[(loc + 7)] = ParseTool.StringToBcd(para[8]);
            frame[(loc + 8)] = (byte) (Integer.parseInt(para[7]) & 0xFF);

            frame[(loc + 9)] = ParseTool.IntToBcd(Integer.parseInt(para[9]));

            frame[(loc + 10)] = ParseTool.IntToBcd(Integer.parseInt(para[10]));

            frame[(loc + 11)] = (byte) Integer.parseInt(para[11]);

            nums = Integer.parseInt(para[12]);
            ParseTool.IntToBcd(frame, nums, loc + 12, 2);

            frame[(loc + 14)] = ParseTool.IntToBcd(Integer.parseInt(para[13]));

            frame[(loc + 15)] = ParseTool.HexToByte(para[14]);

            ParseTool.DecimalToBytes(frame, Integer.parseInt(para[15]), loc + 16, 2);
            ParseTool.DecimalToBytes(frame, Integer.parseInt(para[16]), loc + 18, 2);

            nums = Integer.parseInt(para[17]);
            frame[(loc + 20)] = ParseTool.IntToBcd(nums);

            if (nums != para[18].length() / 2) {
                System.out.println("task para is error");
            }
            ParseTool.HexsToBytesCB(frame, loc + 21, para[18]);
            slen = nums + 21;
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 中继任务 组帧参数:" + value);
        }

        return slen;
    }
}