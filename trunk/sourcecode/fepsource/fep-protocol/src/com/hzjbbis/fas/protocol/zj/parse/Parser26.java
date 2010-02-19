package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser26 {
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
                sb.append(String.valueOf(ParseTool.nBcdToDecimal(data, loc + 11, 2)));
                sb.append(",");
                sb.append(String.valueOf(ParseTool.nBcdToDecimal(data, loc + 13, 2)));
                sb.append(",");
                int din = ParseTool.BCDToDecimal(data[(loc + 15)]);
                sb.append(String.valueOf(din));
                if (din <= 32) {
                    int iloc = loc + 16;
                    for (int i = 0; i < din; ++i) {
                        sb.append(",");
                        sb.append(ParseTool.BytesToHexC(data, iloc, 2));
                        iloc += 2;
                    }
                }
                rt = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        int iloc = loc;
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

            int nums = Integer.parseInt(para[11]);
            ParseTool.IntToBcd(frame, nums, loc + 11, 2);

            nums = Integer.parseInt(para[12]);
            ParseTool.IntToBcd(frame, nums, loc + 13, 2);

            nums = Integer.parseInt(para[13]);
            frame[(loc + 15)] = ParseTool.IntToBcd(nums);

            if (nums != para.length - 14) {
                System.out.println("task para is error");
            }
            iloc = loc + 16;
            for (int i = 14; i < para.length; ++i) {
                ParseTool.HexsToBytes(frame, iloc, para[i]);
                iloc += 2;
            }
            slen = iloc - loc;
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 普通任务设置 组帧参数:" + value);
        }

        return slen;
    }
}