package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParserDATE {
    public static String parseValue(String data, String outputFormat, String inputFormat, int len) {
        String rt = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat(inputFormat);
            data = DataSwitch.ReverseStringByByte(data.substring(0, len));
            Date dt = df.parse(data);
            df = new SimpleDateFormat(outputFormat);
            rt = df.format(dt);
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    public static String constructor(String data, String inputFormat, String outputFormat, int len) {
        String rt = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat(inputFormat);
            Date dt = df.parse(data);
            df = new SimpleDateFormat(outputFormat);
            rt = df.format(dt);
            rt = DataSwitch.ReverseStringByByte(rt);
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        return rt;
    }
}