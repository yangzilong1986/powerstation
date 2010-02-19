package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;

public class ParserBS {
    public static String parseValue(String data, int len) {
        String rt = "";
        try {
            data = DataSwitch.ReverseStringByByte(data.substring(0, len));
            for (int i = 0; i < data.length() / 2; ++i)
                rt = rt + DataSwitch.Fun2HexTo8Bin(data.substring(i * 2, i * 2 + 2));
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    public static String constructor(String data, int len) {
        String rt = "";
        try {
            data = DataSwitch.StrStuff("0", len * 4, data, "left");
            for (int i = 0; i < data.length() / 8; ++i)
                rt = rt + DataSwitch.Fun8BinTo2Hex(data.substring(i * 8, i * 8 + 8));
            rt = DataSwitch.ReverseStringByByte(rt);
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        return rt;
    }
}