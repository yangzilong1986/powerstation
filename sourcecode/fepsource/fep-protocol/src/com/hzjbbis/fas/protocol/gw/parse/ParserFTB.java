package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;

public class ParserFTB {
    public static String parseValue(String data, String format, int len) {
        String rt = "";
        try {
            int iLenSJGS;
            String tag = "";
            String sZS = "";
            String sXS = "";
            data = DataSwitch.ReverseStringByByte(data.substring(0, len));
            if (format.substring(0, 1).equals("C")) {
                tag = data.substring(0, 1);
                if ((Integer.parseInt(tag, 16) & 0x8) == 8) {
                    tag = Integer.toString(Integer.parseInt(tag, 16) & 0x7);
                    data = tag + data.substring(1, data.length());
                    tag = "-";
                }
            }

            int iPos = format.indexOf(46, 0);
            int iLenBCD = data.length();
            if (iPos != -1) {
                iLenSJGS = format.length() - 1;
                if ((iLenBCD == iLenSJGS) && (iLenBCD % 2 == 0)) {
                    sZS = data.substring(0, iPos);
                    if (iPos == 0) {
                        sZS = "0";
                    }
                    sXS = data.substring(iPos, iLenBCD);
                    rt = sZS + "." + sXS;
                } else {
                    rt = "";
                }
            } else {
                iLenSJGS = format.length();
                if ((iLenBCD == iLenSJGS) && (iLenBCD % 2 == 0)) {
                    rt = data;
                } else {
                    rt = "";
                }
            }
            if ((tag.equals("-")) && (!(rt.equals("")))) rt = tag + rt;
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    public static String constructor(String data, String format, int len) {
        String rt = "";
        try {
            String tag = "";
            String sZS = "";
            String sXS = "";
            int iLenZS = 0;
            int iLenXS = 0;
            if (format.substring(0, 1).equals("C")) {
                if (data.substring(0, 1).equals("-")) {
                    data = data.substring(1, data.length());
                    tag = "-";
                } else {
                    tag = "+";
                }
            }
            int iPos = format.indexOf(46, 0);
            if (iPos != -1) {
                iLenZS = format.substring(0, iPos).length();
                iLenXS = format.substring(iPos + 1, format.length()).length();
                iPos = data.indexOf(46, 0);
                if (iPos != -1) {
                    sZS = DataSwitch.StrStuff("0", iLenZS, data.substring(0, iPos), "left");
                    sXS = DataSwitch.StrStuff("0", iLenXS, data.substring(iPos + 1, data.length()), "right");
                } else {
                    sZS = DataSwitch.StrStuff("0", iLenZS, data, "left");
                    sXS = DataSwitch.StrStuff("0", iLenXS, sXS, "right");
                }
                rt = sZS + sXS;
            } else {
                iPos = data.indexOf(46, 0);
                iLenZS = format.length();
                if (iPos != -1) {
                    sZS = DataSwitch.StrStuff("0", iLenZS, data.substring(0, iPos), "left");
                } else {
                    sZS = DataSwitch.StrStuff("0", iLenZS, data, "left");
                }
                rt = sZS;
            }
            if (tag.equals("-")) {
                tag = Integer.toString(Integer.parseInt(rt.substring(0, 1), 16) | 0x8);
                rt = tag + rt.substring(1, rt.length());
            } else if (tag.equals("+")) {
                tag = Integer.toString(Integer.parseInt(rt.substring(0, 1), 16) & 0x7);
                rt = tag + rt.substring(1, rt.length());
            }
            rt = DataSwitch.ReverseStringByByte(rt);
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        return rt;
    }
}