package com.hzjbbis.fas.protocol.gw.parse;

import C;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataItemCoder {
    private static Log log = LogFactory.getLog(DataItemCoder.class);

    public static String getCodeFrom1To1(int mt, String code) {
        String sDADT = "";
        String sDA = "";
        String sDT = "";
        try {
            char[] chr1 = {'0', '0', '0', '0', '0', '0', '0', '0'};
            if (mt == 0) {
                sDA = "0000";
            } else if ((mt > 0) && (mt <= 2040)) {
                if (mt % 8 == 0) chr1[0] = '1';
                else chr1[(8 - (mt % 8))] = '1';
                sDA = DataSwitch.Fun8BinTo2Hex(new String(chr1).trim()) + DataSwitch.IntToHex(new StringBuilder().append("").append((int) Math.floor((mt - 1) / 8)).append(1).toString(), 2);
            }

            char[] chr2 = {'0', '0', '0', '0', '0', '0', '0', '0'};
            int fn = Integer.parseInt(code.substring(3, 6));
            if ((fn > 0) && (fn <= 2040)) {
                if (fn % 8 == 0) chr2[0] = '1';
                else {
                    chr2[(8 - (fn % 8))] = '1';
                }
                sDT = DataSwitch.Fun8BinTo2Hex(new String(chr2).trim()) + DataSwitch.IntToHex(new StringBuilder().append("").append((int) Math.floor((fn - 1) / 8)).toString(), 2);
            }
            sDADT = sDA + sDT;
        } catch (Exception e) {
            log.error("getCodeFrom1To1 error:" + e.toString());
        }
        return sDADT;
    }

    public static String[] getCodeFromNToN(int[] mts, String[] codes) {
        String[] sDADTList = null;
        String[] sDAList = null;
        String[] sDTList = null;
        try {
            char[] chr;
            Map.Entry entry;
            Map chrMap = new HashMap();
            char[] chr0 = {'0', '0', '0', '0', '0', '0', '0', '0'};

            for (int i = 0; i < mts.length; ++i) {
                if (mts[i] == 0) {
                    chr = chr0;
                    chrMap.put(Integer.valueOf(0), chr);
                } else if ((mts[i] > 0) && (mts[i] <= 2040)) {
                    int iDA2 = (int) Math.floor((mts[i] - 1) / 8) + 1;
                    chr = (char[]) chrMap.get(new Integer(iDA2));
                    if (chr == null) {
                        chr = (char[]) chr0.clone();
                    }
                    if (mts[i] % 8 == 0) chr[0] = '1';
                    else chr[(8 - (mts[i] % 8))] = '1';
                    chrMap.put(new Integer(iDA2), chr);
                }
            }
            sDAList = new String[chrMap.size()];
            Iterator it = chrMap.entrySet().iterator();
            int icount = 0;
            while (it.hasNext()) {
                entry = (Map.Entry) it.next();
                sDAList[icount] = DataSwitch.Fun8BinTo2Hex(new String((char[]) (char[]) entry.getValue()).trim()) + DataSwitch.IntToHex(new StringBuilder().append("").append(entry.getKey()).toString(), 2);
                ++icount;
            }

            chrMap.clear();

            for (int i = 0; i < codes.length; ++i) {
                int fn = Integer.parseInt(codes[i].substring(3, 6));
                if ((fn > 0) && (fn <= 2040)) {
                    int iDT2 = (int) Math.floor((fn - 1) / 8);
                    chr = (char[]) chrMap.get(new Integer(iDT2));
                    if (chr == null) {
                        chr = (char[]) chr0.clone();
                    }
                    if (fn % 8 == 0) chr[0] = '1';
                    else chr[(8 - (fn % 8))] = '1';
                    chrMap.put(new Integer(iDT2), chr);
                }
            }
            sDTList = new String[chrMap.size()];
            it = chrMap.entrySet().iterator();
            icount = 0;
            while (it.hasNext()) {
                i = (Map.Entry) it.next();
                sDTList[icount] = DataSwitch.Fun8BinTo2Hex(new String((char[]) (char[]) i.getValue()).trim()) + DataSwitch.IntToHex(new StringBuilder().append("").append(i.getKey()).toString(), 2);
                ++icount;
            }
            sDADTList = new String[sDAList.length * sDTList.length];
            icount = 0;
            for (i = 0; i < sDAList.length; ++i) {
                for (int j = 0; j < sDTList.length; ++j) {
                    sDADTList[icount] = sDAList[i] + sDTList[j];
                    ++icount;
                }
            }
        } catch (Exception e) {
            log.error("getCodeFromNToN error:" + e.toString());
        }
        return sDADTList;
    }

    public static String coder(String input, String format) {
        String output = "";
        try {
            String[] formatItems = format.split("#");
            String[] inputItems = input.split("#");
            if ((formatItems.length > 0) && (inputItems.length >= formatItems.length)) {
                for (int i = 0; i < formatItems.length; ++i)
                    if (formatItems[i].startsWith("N")) {
                        int num = Integer.parseInt(input.substring(0, input.indexOf("#")));
                        output = output + constructor(input.substring(0, input.indexOf("#")), formatItems[i]);
                        format = format.substring(format.indexOf("#") + 1);
                        input = input.substring(input.indexOf("#") + 1);
                        if (num > 1) {
                            inputItems = input.split(";");
                            if (num != inputItems.length) {
                                inputItems = input.split(",");
                            }
                            for (int j = 0; j < inputItems.length; ++j) {
                                output = output + coder(inputItems[j], format);
                            }
                            break;
                        }

                    } else {
                        if (i == formatItems.length - 1) output = output + constructor(input, formatItems[i]);
                        else output = output + constructor(input.substring(0, input.indexOf("#")), formatItems[i]);
                        format = format.substring(format.indexOf("#") + 1);
                        input = input.substring(input.indexOf("#") + 1);
                    }
            }
        } catch (Exception e) {
            log.error("coder error:" + e.toString());
        }
        return output;
    }

    public static String constructor(String input, String format) {
        String output = "";
        try {
            int len = 0;
            if (format.startsWith("HTB")) {
                len = Integer.parseInt(format.substring(3));
                output = ParserHTB.constructor(input, len * 2);
            } else if (format.startsWith("HEX")) {
                len = Integer.parseInt(format.substring(3));
                output = ParserHEX.constructor(input, len * 2);
            } else if (format.startsWith("STS")) {
                len = Integer.parseInt(format.substring(3));
                output = ParserString.constructor(input, len * 2);
            } else if (format.startsWith("ASC")) {
                len = Integer.parseInt(format.substring(3));
                output = ParserASC.constructor(input, len * 2);
            } else if (format.startsWith("SIM")) {
                len = Integer.parseInt(format.substring(3));
                output = ParserSIM.constructor(input, len * 2);
            } else if (format.startsWith("BS")) {
                len = Integer.parseInt(format.substring(2));
                output = ParserBS.constructor(input, len * 2);
            } else if (format.startsWith("IP")) {
                len = Integer.parseInt(format.substring(2));
                output = ParserIP.constructor(input, len * 2);
            } else if (format.startsWith("N")) {
                len = Integer.parseInt(format.substring(1));
                output = ParserHTB.constructor(input, len * 2);
            } else if (format.equals("A1")) {
                output = ParserA1.constructor(input, 12);
            } else if (format.equals("A2")) {
                output = ParserA2.constructor(input, 4);
            } else if (format.equals("A3")) {
                output = ParserA3.constructor(input, 8);
            } else if (format.equals("A4")) {
                output = ParserFTB.constructor(input, "CC", 2);
            } else if (format.equals("A5")) {
                output = ParserFTB.constructor(input, "CCC.C", 4);
            } else if (format.equals("A6")) {
                output = ParserFTB.constructor(input, "CC.CC", 4);
            } else if (format.equals("A7")) {
                output = ParserFTB.constructor(input, "000.0", 4);
            } else if (format.equals("A8")) {
                output = ParserFTB.constructor(input, "0000", 4);
            } else if (format.equals("A9")) {
                output = ParserFTB.constructor(input, "CC.CCCC", 6);
            } else if (format.equals("A10")) {
                output = ParserFTB.constructor(input, "000000", 6);
            } else if (format.equals("A11")) {
                output = ParserFTB.constructor(input, "000000.00", 8);
            } else if (format.equals("A12")) {
                output = ParserFTB.constructor(input, "000000000000", 12);
            } else if (format.equals("A13")) {
                output = ParserFTB.constructor(input, "0000.0000", 8);
            } else if (format.equals("A14")) {
                output = ParserFTB.constructor(input, "000000.0000", 10);
            } else if (format.equals("A15")) {
                output = ParserDATE.constructor(input, "yyyy-MM-dd HH:mm", "yyMMddHHmm", 10);
            } else if (format.equals("A16")) {
                output = ParserDATE.constructor(input, "dd HH:mm:ss", "ddHHmmss", 8);
            } else if (format.equals("A17")) {
                output = ParserDATE.constructor(input, "MM-dd HH:mm", "MMddHHmm", 8);
            } else if (format.equals("A18")) {
                output = ParserDATE.constructor(input, "dd HH:mm", "ddHHmm", 6);
            } else if (format.equals("A19")) {
                output = ParserDATE.constructor(input, "HH:mm", "HHmm", 4);
            } else if (format.equals("A20")) {
                output = ParserDATE.constructor(input, "yyyy-MM-dd", "yyMMdd", 6);
            } else if (format.equals("A21")) {
                output = ParserDATE.constructor(input, "yyyy-MM", "yyMM", 4);
            } else if (format.equals("A22")) {
                output = ParserFTB.constructor(input, "0.0", 2);
            } else if (format.equals("A23")) {
                output = ParserFTB.constructor(input, "00.0000", 6);
            } else if (format.equals("A24")) {
                output = ParserDATE.constructor(input, "dd HH", "ddHH", 4);
            } else if (format.equals("A25")) {
                output = ParserFTB.constructor(input, "CCC.CCC", 6);
            } else if (format.equals("A26")) {
                output = ParserFTB.constructor(input, "0.000", 4);
            } else if (format.equals("A27")) {
                output = ParserFTB.constructor(input, "00000000", 8);
            }
        } catch (Exception e) {
            log.error("constructor error:" + e.toString());
        }
        return output;
    }
}