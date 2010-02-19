package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;

public class ParserHTB {
    public static String parseValue(String data, int len) {
        String rt = "";
        try {
            data = DataSwitch.ReverseStringByByte(data.substring(0, len));
            rt = "" + Integer.parseInt(data, 16);
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    public static String constructor(String data, int len) {
        String rt = "";
        try {
            rt = Integer.toString(Integer.parseInt(data), 16).toUpperCase();
            rt = DataSwitch.StrStuff("0", len, rt, "left");
            rt = DataSwitch.ReverseStringByByte(rt);
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        return rt;
    }
}