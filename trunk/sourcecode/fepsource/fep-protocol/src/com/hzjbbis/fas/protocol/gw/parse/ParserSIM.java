package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;

public class ParserSIM {
    public static String parseValue(String data, int len) {
        String rt = "";
        try {
            data = data.substring(0, len);
            for (int i = 0; i < data.length(); ++i)
                if (data.substring(i, i + 1).equals("A")) rt = rt + ",";
                else if (data.substring(i, i + 1).equals("B")) rt = rt + "#";
                else if (data.substring(i, i + 1).equals("F")) rt = rt + "";
                else rt = rt + data.substring(i, i + 1);
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    public static String constructor(String data, int len) {
        String rt = "";
        try {
            rt = DataSwitch.StrStuff("F", len, data, "right");
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        return rt;
    }
}