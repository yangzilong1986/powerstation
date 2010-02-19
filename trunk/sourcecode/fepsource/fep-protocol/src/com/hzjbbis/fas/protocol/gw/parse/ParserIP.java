package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;

public class ParserIP {
    public static String parseValue(String data, int len) {
        String rt = "";
        try {
            data = data.substring(0, len);
            for (int i = 0; i < 4; ++i)
                rt = rt + ParserHTB.parseValue(data.substring(i * 2, i * 2 + 2), 2) + ".";
            data = data.substring(8);
            rt = rt.substring(0, rt.length() - 1) + ":" + ParserHTB.parseValue(data.substring(0, 4), 4);
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    public static String constructor(String data, int len) {
        String rt = "";
        try {
            String[] strs = data.split(":");
            if (strs.length == 2) {
                String[] ips = strs[0].trim().split("\\.");
                for (int i = 0; i < ips.length; ++i)
                    rt = rt + ParserHTB.constructor(ips[i].trim(), 2);
                rt = rt + ParserHTB.constructor(strs[1].trim(), 4);
            } else {
                throw new MessageEncodeException("IP is illegal:" + data);
            }
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        return rt;
    }
}