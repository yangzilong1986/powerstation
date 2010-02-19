package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParserA1 {
    public static String parseValue(String data, int len) {
        String rt = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
            data = DataSwitch.ReverseStringByByte(data.substring(0, len));
            int month = Integer.parseInt(data.substring(2, 3), 16) & 0x1;

            data = data.substring(0, 2) + month + data.substring(3);
            Date dt = df.parse(data);
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            rt = df.format(dt);
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    public static String constructor(String data, int len) {
        String rt = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt = df.parse(data);
            df = new SimpleDateFormat("yyMMddHHmmss");
            rt = df.format(dt);
            Calendar date = Calendar.getInstance();
            date.setTime(dt);
            int week = date.get(7);
            if (week == 1) week = 7;
            else week -= 1;
            int month = Integer.parseInt(rt.substring(2, 3));
            month = week * 2 + month;
            rt = rt.substring(0, 2) + Integer.toString(month, 16) + rt.substring(3);
            rt = DataSwitch.ReverseStringByByte(rt);
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        return rt;
    }
}