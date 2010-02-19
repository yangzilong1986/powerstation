package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser28 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                StringBuffer sb = new StringBuffer();
                sb.append(ParseTool.ByteToHex(data[loc]));
                sb.append(",");
                sb.append(ParseTool.BytesToHexC(data, loc + 1, 2));
                sb.append(",");
                sb.append(String.valueOf(data[(loc + 4)] & 0xFF));
                sb.append(",");
                sb.append(ParseTool.ByteToHex(data[(loc + 3)]));
                sb.append(",");
                sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(loc + 5)])));
                sb.append(",");
                int din = ParseTool.BCDToDecimal(data[(loc + 6)]);
                sb.append(String.valueOf(din));
                if (din <= 32) {
                    int iloc = loc + 7;
                    for (int i = 0; i < din; ++i) {
                        sb.append(",");
                        sb.append(ParseTool.ByteToHex(data[(iloc + 2)]));
                        sb.append(" ");
                        sb.append(ParseTool.BytesToHexC(data, iloc, 2));
                        iloc += 3;
                    }
                    sb.append(",");
                    sb.append(String.valueOf(ParseTool.BCDToDecimal(data[iloc])));
                }
                rt = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        int slen = -1;
        try {
            String[] para = value.split(",");
            frame[loc] = ParseTool.IntToBcd(Integer.parseInt(para[0]));

            ParseTool.HexsToBytes(frame, loc + 1, para[1]);

            frame[(loc + 3)] = ParseTool.StringToBcd(para[3]);
            frame[(loc + 4)] = (byte) (Integer.parseInt(para[2]) & 0xFF);

            frame[(loc + 5)] = ParseTool.IntToBcd(Integer.parseInt(para[4]));

            int nums = Integer.parseInt(para[5]);
            frame[(loc + 6)] = ParseTool.IntToBcd(nums);

            if (2 * nums != para.length - 7) {
                System.out.println("task para is error");
            }
            int iloc = loc + 7;
            int ipara = 6;
            for (int i = 0; i < nums; ++i) {
                ParseTool.HexsToBytes(frame, iloc, para[(ipara + 1)]);
                frame[(iloc + 2)] = ParseTool.IntToBcd(Integer.parseInt(para[ipara]));
                ipara += 2;
                iloc += 3;
            }
            frame[iloc] = ParseTool.IntToBcd(Integer.parseInt(para[ipara]));
            slen = iloc - loc + 1;
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 异常任务 组帧参数:" + value);
        }
        return slen;
    }
}