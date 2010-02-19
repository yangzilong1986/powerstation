package com.hzjbbis.fas.protocol.gw.parse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataItemParser {
    private static Log log = LogFactory.getLog(DataItemCoder.class);

    public static DataValue parseValue(String input, String format) {
        DataValue dataValue = new DataValue();
        try {
            String output = "";
            int len = 0;
            try {
                if (format.startsWith("HTB")) {
                    len = Integer.parseInt(format.substring(3));
                    output = ParserHTB.parseValue(input, len * 2);
                } else if (format.startsWith("HEX")) {
                    len = Integer.parseInt(format.substring(3));
                    output = ParserHEX.parseValue(input, len * 2);
                } else if (format.startsWith("STS")) {
                    len = Integer.parseInt(format.substring(3));
                    output = ParserString.parseValue(input, len * 2);
                } else if (format.startsWith("ASC")) {
                    len = Integer.parseInt(format.substring(3));
                    output = ParserASC.parseValue(input, len * 2);
                } else if (format.startsWith("SIM")) {
                    len = Integer.parseInt(format.substring(3));
                    output = ParserSIM.parseValue(input, len * 2);
                } else if (format.startsWith("BS")) {
                    len = Integer.parseInt(format.substring(2));
                    output = ParserBS.parseValue(input, len * 2);
                } else if (format.startsWith("IP")) {
                    len = Integer.parseInt(format.substring(2));
                    output = ParserIP.parseValue(input, len * 2);
                } else if (format.startsWith("N")) {
                    len = Integer.parseInt(format.substring(1));
                    output = ParserHTB.parseValue(input, len * 2);
                } else if (format.equals("A1")) {
                    len = 6;
                    output = ParserA1.parseValue(input, len * 2);
                } else if (format.equals("A2")) {
                    len = 2;
                    output = ParserA2.parseValue(input, len * 2);
                } else if (format.equals("A3")) {
                    len = 4;
                    output = ParserA3.parseValue(input, len * 2);
                } else if (format.equals("A4")) {
                    len = 1;
                    output = ParserFTB.parseValue(input, "CC", len * 2);
                } else if (format.equals("A5")) {
                    len = 2;
                    output = ParserFTB.parseValue(input, "CCC.C", len * 2);
                } else if (format.equals("A6")) {
                    len = 2;
                    output = ParserFTB.parseValue(input, "CC.CC", len * 2);
                } else if (format.equals("A7")) {
                    len = 2;
                    output = ParserFTB.parseValue(input, "000.0", len * 2);
                } else if (format.equals("A8")) {
                    len = 2;
                    output = ParserFTB.parseValue(input, "0000", len * 2);
                } else if (format.equals("A9")) {
                    len = 3;
                    output = ParserFTB.parseValue(input, "CC.CCCC", len * 2);
                } else if (format.equals("A10")) {
                    len = 3;
                    output = ParserFTB.parseValue(input, "000000", len * 2);
                } else if (format.equals("A11")) {
                    len = 4;
                    output = ParserFTB.parseValue(input, "000000.00", len * 2);
                } else if (format.equals("A12")) {
                    len = 6;
                    output = ParserFTB.parseValue(input, "000000000000", len * 2);
                } else if (format.equals("A13")) {
                    len = 4;
                    output = ParserFTB.parseValue(input, "0000.0000", len * 2);
                } else if (format.equals("A14")) {
                    len = 5;
                    output = ParserFTB.parseValue(input, "000000.0000", len * 2);
                } else if (format.equals("A15")) {
                    len = 5;
                    output = ParserDATE.parseValue(input, "yyyy-MM-dd HH:mm", "yyMMddHHmm", len * 2);
                } else if (format.equals("A16")) {
                    len = 4;
                    output = ParserDATE.parseValue(input, "dd HH:mm:ss", "ddHHmmss", len * 2);
                } else if (format.equals("A17")) {
                    len = 4;
                    output = ParserDATE.parseValue(input, "MM-dd HH:mm", "MMddHHmm", len * 2);
                } else if (format.equals("A18")) {
                    len = 3;
                    output = ParserDATE.parseValue(input, "dd HH:mm", "ddHHmm", len * 2);
                } else if (format.equals("A19")) {
                    len = 2;
                    output = ParserDATE.parseValue(input, "HH:mm", "HHmm", len * 2);
                } else if (format.equals("A20")) {
                    len = 3;
                    output = ParserDATE.parseValue(input, "yyyy-MM-dd", "yyMMdd", len * 2);
                } else if (format.equals("A21")) {
                    len = 2;
                    output = ParserDATE.parseValue(input, "yyyy-MM", "yyMM", len * 2);
                } else if (format.equals("A22")) {
                    len = 1;
                    output = ParserFTB.parseValue(input, "0.0", len * 2);
                } else if (format.equals("A23")) {
                    len = 3;
                    output = ParserFTB.parseValue(input, "00.0000", len * 2);
                } else if (format.equals("A24")) {
                    len = 2;
                    output = ParserDATE.parseValue(input, "dd HH", "ddHH", len * 2);
                } else if (format.equals("A25")) {
                    len = 3;
                    output = ParserFTB.parseValue(input, "CCC.CCC", len * 2);
                } else if (format.equals("A26")) {
                    len = 2;
                    output = ParserFTB.parseValue(input, "0.000", len * 2);
                } else if (format.equals("A27")) {
                    len = 4;
                    output = ParserFTB.parseValue(input, "00000000", len * 2);
                }
            } catch (Exception e) {
                log.error("constructor error:" + e.toString());
            }
            dataValue.setValue(output);
            dataValue.setLen(len * 2);
        } catch (Exception e) {
            log.equals("parsevalue error:" + e.toString());
        }
        return dataValue;
    }

    public static int[] measuredPointParser(String sDA) {
        int iCount = 0;
        int[] measuredListTemp = new int[8];
        try {
            String sDA1 = sDA.substring(0, 2);
            String sDA2 = sDA.substring(2, 4);
            if (sDA.equals("0000")) {
                iCount = 1;
                measuredListTemp[0] = 0;
            } else {
                int iDA2 = Integer.parseInt(sDA2, 16);
                char[] cDA1 = DataSwitch.Fun2HexTo8Bin(sDA1).toCharArray();
                for (int i = 7; i >= 0; --i)
                    if (cDA1[i] == '1') {
                        measuredListTemp[iCount] = ((iDA2 - 1) * 8 + 8 - i);

                        iCount += 1;
                    }
            }
        } catch (Exception e) {
            log.error("MeasuredPointParser error:" + e.toString());
        }
        int[] measuredList = new int[iCount];
        for (int i = 0; i < iCount; ++i) {
            measuredList[i] = measuredListTemp[i];
        }
        return measuredList;
    }

    public static String[] dataCodeParser(String sDT, String sAFN) {
        int iCount = 0;
        int[] codeListTemp = new int[8];
        try {
            String sDT1 = sDT.substring(0, 2);
            String sDT2 = sDT.substring(2, 4);

            int iDT2 = Integer.parseInt(sDT2, 16);
            char[] cDT1 = DataSwitch.Fun2HexTo8Bin(sDT1).toCharArray();
            for (int i = 7; i >= 0; --i) {
                if (cDT1[i] == '1') {
                    codeListTemp[iCount] = (iDT2 * 8 + 8 - i);

                    iCount += 1;
                }
            }
        } catch (Exception e) {
            log.error("dataCodeParser error:" + e.toString());
        }
        String[] codeList = new String[iCount];
        for (int i = 0; i < iCount; ++i) {
            codeList[i] = sAFN + "F" + DataSwitch.StrStuff("0", 3, new StringBuilder().append("").append(codeListTemp[i]).toString(), "left");
        }
        return codeList;
    }

    public static DataTimeTag getTaskDateTimeInfo(String sDateTimeLabel, int DateType) {
        DataTimeTag dataTimeTag = new DataTimeTag();
        try {
            String sDateTime = "";
            String sNowDateTime = "";
            int iDataDensity = 0;
            int iDataCount = 0;
            if (DateType == 1) {
                sDateTime = "" + (Integer.parseInt(sDateTimeLabel.substring(0, 1)) & 0x3) + sDateTimeLabel.substring(1, 2);

                iDataDensity = Integer.parseInt(sDateTimeLabel.substring(2, 4), 16);

                Calendar cLogTime = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
                sNowDateTime = formatter.format(cLogTime.getTime());
            } else if (DateType == 2) {
                sDateTime = "20" + DataSwitch.ReverseStringByByte(sDateTimeLabel.substring(0, 10));

                iDataDensity = Integer.parseInt(sDateTimeLabel.substring(10, 12), 16);

                iDataCount = Integer.parseInt(sDateTimeLabel.substring(12, 14), 16);
            } else if (DateType == 3) {
                sDateTime = parseValue(sDateTimeLabel.substring(0, 6), "A20").getValue();
            } else if (DateType == 4) {
                sDateTime = parseValue(sDateTimeLabel.substring(0, 4), "A21").getValue();
            }
            switch (iDataDensity) {
                case 1:
                    iDataDensity = 15;
                    if (DateType == 1) {
                        sDateTime = sNowDateTime.substring(0, 8) + sDateTime + "15";
                        iDataCount = 4;
                    }
                    break;
                case 2:
                    iDataDensity = 30;
                    if (DateType == 1) {
                        sDateTime = sNowDateTime.substring(0, 8) + sDateTime + "30";
                        iDataCount = 2;
                    }
                    break;
                case 3:
                    iDataDensity = 60;
                    if (DateType == 1) {
                        sDateTime = sNowDateTime.substring(0, 8) + sDateTime + "00";
                        iDataCount = 1;
                    }
                    break;
                case 254:
                    iDataDensity = 5;
                    if (DateType == 1) {
                        sDateTime = sNowDateTime.substring(0, 8) + sDateTime + "05";
                        iDataCount = 12;
                    }
                    break;
                case 255:
                    iDataDensity = 1;
                    if (DateType == 1) {
                        sDateTime = sNowDateTime.substring(0, 8) + sDateTime + "01";
                        iDataCount = 60;
                    }
                    break;
                default:
                    iDataDensity = 0;
                    iDataCount = 1;
            }

            dataTimeTag.setDataTime(sDateTime);
            dataTimeTag.setDataDensity(iDataDensity);
            dataTimeTag.setDataCount(iDataCount);
        } catch (Exception e) {
            log.error("getTaskDateTimeInfo error：" + e.toString());
        }
        return dataTimeTag;
    }

    public static DataValue parser(String input, String format) {
        DataValue dataItem = new DataValue();
        try {
            String[] formatItems = format.split("#");
            String value = "";
            int len = 0;
            DataValue dataItemTemp = new DataValue();
            if (formatItems.length > 0) {
                for (int i = 0; i < formatItems.length; ++i) {
                    if (formatItems[i].startsWith("N")) {
                        DataValue nValue = new DataValue();
                        nValue = parseValue(input, formatItems[i]);
                        input = input.substring(nValue.getLen());
                        format = format.substring(format.indexOf("#") + 1);
                        value = value + nValue.getValue() + "#";
                        len += nValue.getLen();
                        for (int j = 0; j < Integer.parseInt(nValue.getValue()); ++j) {
                            dataItemTemp = parser(input, format);
                            input = input.substring(dataItemTemp.getLen());
                            if (format.indexOf("N") >= 0) {
                                value = value + dataItemTemp.getValue() + ";";
                            } else {
                                value = value + dataItemTemp.getValue() + ",";
                            }
                            len += dataItemTemp.getLen();
                        }
                        if ((!(value.endsWith(","))) && (!(value.endsWith(";")))) break;
                        value = value.substring(0, value.length() - 1);
                        break;
                    }

                    dataItemTemp = parseValue(input, formatItems[i]);
                    input = input.substring(dataItemTemp.getLen());
                    format = format.substring(format.indexOf("#") + 1);
                    value = value + dataItemTemp.getValue() + "#";
                    len += dataItemTemp.getLen();
                }

                if (value.endsWith("#")) value = value.substring(0, value.length() - 1);
                dataItem.setValue(value);
                dataItem.setLen(len);
            }
        } catch (Exception e) {
            log.error("coder error:" + e.toString());
        }
        return dataItem;
    }
}