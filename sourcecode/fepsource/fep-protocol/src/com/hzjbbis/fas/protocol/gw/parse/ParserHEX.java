package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;

public class ParserHEX {
    public static String parseValue(String data, int len) {
        String rt = "";
        try {
            rt = DataSwitch.ReverseStringByByte(data.substring(0, len));
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    public static String constructor(String data, int len) {
        String rt = "";
        try {
            rt = DataSwitch.StrStuff("0", len, data, "left");
            rt = DataSwitch.ReverseStringByByte(rt);
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        return rt;
    }
}