package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;

public class ParserASC {
    public static String parseValue(String data, int len) {
        String rt = "";
        try {
            data = data.substring(0, len);
            if (data.length() % 2 == 0) {
                int byteLen = data.length() / 2;
                char[] chrList = new char[byteLen];
                for (int i = 0; i < byteLen; ++i) {
                    chrList[i] = (char) Integer.parseInt(ParserHTB.parseValue(data.substring(2 * i, 2 * i + 2), 2));
                }
                rt = new String(chrList).trim();
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    public static String constructor(String data, int len) {
        String rt = "";
        try {
            byte[] bt = data.getBytes();
            for (int i = 0; i < bt.length; ++i) {
                rt = rt + Integer.toHexString(bt[i]);
            }
            rt = DataSwitch.StrStuff("0", len, rt, "right");
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        return rt;
    }
}