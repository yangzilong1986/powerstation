package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.exception.MessageDecodeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataSwitch {
    private static final Log log = LogFactory.getLog(DataSwitch.class);

    public static String IntToHex(String sInt, int len) {
        String sDataContent = "";
        try {
            sInt = Integer.toString(Integer.parseInt(sInt), 16).toUpperCase();
            sDataContent = StrStuff("0", len, sInt, "left");
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return sDataContent;
    }

    public static String ReverseStringByByte(String str) {
        String sOutput = "";
        try {
            if (str.length() % 2 == 0) {
                for (int i = 0; i < str.length() / 2; ++i) {
                    sOutput = sOutput + str.substring(str.length() - ((i + 1) * 2), str.length() - (i * 2));
                }
            } else throw new MessageDecodeException("ReverseStringByByte() error,input:" + str);
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return sOutput;
    }

    public static String StrStuff(String str, int iLen, String sInput, String sSign) {
        String sOutput = "";
        try {
            int iLenStr = sInput.length();
            if (iLen > iLenStr) {
                for (int i = 0; i < iLen - iLenStr; ++i) {
                    if (sSign.equals("left")) {
                        sInput = str + sInput;
                    } else {
                        sInput = sInput + str;
                    }
                }
            } else if (iLen < iLenStr) {
                if (sSign.equals("left")) {
                    sInput = sInput.substring(iLenStr - iLen, iLenStr);
                } else {
                    sInput = sInput.substring(0, iLen);
                }
            }
            sOutput = sInput;
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return sOutput;
    }

    public static String Fun8BinTo2Hex(String sBit8) {
        String sResult = "";
        String sTemp = "";
        try {
            try {
                for (int i = 0; i < 2; ++i) {
                    sTemp = sBit8.substring(i * 4, i * 4 + 4);
                    if (sTemp.equals("0000")) sTemp = "0";
                    if (sTemp.equals("0001")) sTemp = "1";
                    if (sTemp.equals("0010")) sTemp = "2";
                    if (sTemp.equals("0011")) sTemp = "3";
                    if (sTemp.equals("0100")) sTemp = "4";
                    if (sTemp.equals("0101")) sTemp = "5";
                    if (sTemp.equals("0110")) sTemp = "6";
                    if (sTemp.equals("0111")) sTemp = "7";
                    if (sTemp.equals("1000")) sTemp = "8";
                    if (sTemp.equals("1001")) sTemp = "9";
                    if (sTemp.equals("1010")) sTemp = "A";
                    if (sTemp.equals("1011")) sTemp = "B";
                    if (sTemp.equals("1100")) sTemp = "C";
                    if (sTemp.equals("1101")) sTemp = "D";
                    if (sTemp.equals("1110")) sTemp = "E";
                    if (sTemp.equals("1111")) sTemp = "F";
                    sResult = sResult + sTemp;
                }
            } catch (Exception e) {
                System.out.println("数据区解析出错Fun8BinTo2Hex:" + e.toString());
            }

            return sResult;
        } finally {
        }
        return sResult;
    }

    public static String Fun2HexTo8Bin(String sBit8) {
        String sResult = "";
        String sTemp = "";
        try {
            try {
                for (int i = 0; i < 2; ++i) {
                    sTemp = sBit8.substring(i, 1 + i);
                    if (sTemp.toUpperCase().equals("0")) sTemp = "0000";
                    if (sTemp.toUpperCase().equals("1")) sTemp = "0001";
                    if (sTemp.toUpperCase().equals("2")) sTemp = "0010";
                    if (sTemp.toUpperCase().equals("3")) sTemp = "0011";
                    if (sTemp.toUpperCase().equals("4")) sTemp = "0100";
                    if (sTemp.toUpperCase().equals("5")) sTemp = "0101";
                    if (sTemp.toUpperCase().equals("6")) sTemp = "0110";
                    if (sTemp.toUpperCase().equals("7")) sTemp = "0111";
                    if (sTemp.toUpperCase().equals("8")) sTemp = "1000";
                    if (sTemp.toUpperCase().equals("9")) sTemp = "1001";
                    if (sTemp.toUpperCase().equals("A")) sTemp = "1010";
                    if (sTemp.toUpperCase().equals("B")) sTemp = "1011";
                    if (sTemp.toUpperCase().equals("C")) sTemp = "1100";
                    if (sTemp.toUpperCase().equals("D")) sTemp = "1101";
                    if (sTemp.toUpperCase().equals("E")) sTemp = "1110";
                    if (sTemp.toUpperCase().equals("F")) sTemp = "1111";
                    sResult = sResult + sTemp;
                }
            } catch (Exception e) {
                System.out.println("数据区解析出错Fun2HexTo8Bin:" + e.toString());
            }

            return sResult;
        } finally {
        }
        return sResult;
    }

    public static String IncreaseDateTime(String sDateTime, int iIncreaseNo, int iIncreaseType) {
        String sResult = "";
        try {
            if (iIncreaseNo >= 0) {
                Calendar DateTime = Calendar.getInstance();

                DateTime.set(Integer.parseInt(sDateTime.substring(0, 4)), Integer.parseInt(sDateTime.substring(4, 6)) - 1, Integer.parseInt(sDateTime.substring(6, 8)), Integer.parseInt(sDateTime.substring(8, 10)), Integer.parseInt(sDateTime.substring(10, 12)), 0);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    switch (iIncreaseType) {
                        case 2:
                            DateTime.add(12, iIncreaseNo);
                            break;
                        case 3:
                            DateTime.add(10, iIncreaseNo);
                            break;
                        case 4:
                            DateTime.add(5, iIncreaseNo);
                            break;
                        case 5:
                            DateTime.add(2, iIncreaseNo);
                    }

                    sResult = formatter.format(DateTime.getTime());
                } catch (Exception e) {
                    log.error("数据区解析IncreaseDateTime出错:" + e.toString());
                }
            } else {
                sResult = sDateTime;
            }

            return sResult;
        } finally {
        }
        return sResult;
    }
}