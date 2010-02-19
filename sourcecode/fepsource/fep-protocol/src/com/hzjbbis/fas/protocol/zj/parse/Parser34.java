package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageEncodeException;

public class Parser34 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                int tasktype = ParseTool.BCDToDecimal(data[loc]);
                switch (tasktype) {
                    case 1:
                        rt = Parser26.parsevalue(data, loc, len, fraction);
                        break;
                    case 2:
                        rt = Parser27.parsevalue(data, loc, len, fraction);
                        break;
                    case 4:
                        rt = Parser28.parsevalue(data, loc, len, fraction);
                    case 3:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        int slen = 0;
        try {
            int index = value.indexOf(",");
            if (index > 0) {
                int tasktype = Integer.parseInt(value.substring(0, index));
                switch (tasktype) {
                    case 1:
                        slen = Parser26.constructor(frame, value, loc, len, fraction);
                        break;
                    case 2:
                        slen = Parser27.constructor(frame, value, loc, len, fraction);
                        break;
                    case 4:
                        slen = Parser28.constructor(frame, value, loc, len, fraction);
                    case 3:
                }
            }
        } catch (Exception e) {
            throw new MessageEncodeException("错误的 任务 组帧参数:" + value);
        }
        return slen;
    }
}