package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;

public class ParserA3 {
    public static String parseValue(String data, int len) {
        String rt = "";
        try {
            data = DataSwitch.ReverseStringByByte(data.substring(0, len));
            String tag = data.substring(0, 1);
            int unit = (Integer.parseInt(tag, 16) & 0x4) >> 2;
            if ((Integer.parseInt(tag, 16) & 0x1) == 1) tag = "-";
            else tag = "+";
            rt = "" + unit + tag + data.substring(1, 8);
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    public static String constructor(String data, int len) {
        String rt = "";
        try {
            int unit = Integer.parseInt(data.substring(0, 1)) << 2;
            if (data.substring(1, 2).equals("-")) unit += 1;
            data = data.substring(2);
            rt = unit + DataSwitch.StrStuff("0", len - 1, data, "left");
            rt = DataSwitch.ReverseStringByByte(rt);
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        return rt;
    }
}