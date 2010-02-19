package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;

public class ParserA2 {
    public static String parseValue(String data, int len) {
        String rt = "";
        try {
            data = DataSwitch.ReverseStringByByte(data.substring(0, len));
            String tag = data.substring(0, 1);
            int iMB = Integer.parseInt(tag, 16) & 0xE;
            if ((Integer.parseInt(tag, 16) & 0x1) == 1) tag = "-";
            else tag = "";
            float iBCD = Integer.parseInt(data.substring(1, data.length()));
            switch (iMB) {
                case 0:
                    iBCD *= 10000.0F;
                    break;
                case 2:
                    iBCD *= 1000.0F;
                    break;
                case 4:
                    iBCD *= 100.0F;
                    break;
                case 6:
                    iBCD *= 10.0F;
                    break;
                case 8:
                    iBCD *= 1.0F;
                    break;
                case 10:
                    iBCD /= 10.0F;
                    break;
                case 12:
                    iBCD /= 100.0F;
                    break;
                case 14:
                    iBCD /= 1000.0F;
                case 1:
                case 3:
                case 5:
                case 7:
                case 9:
                case 11:
                case 13:
            }

            rt = tag + "" + iBCD;
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    public static String constructor(String data, int len) {
        String rt = "";
        try {
            Double d = new Double(data);
            String sFH = "";
            String sZSSjz = "";
            String sMB = "";
            if (d.doubleValue() >= 0.0D) {
                sFH = "+";
                if (d.doubleValue() < 9990000.0D) break label104;
                return "0999";
            }

            if (d.doubleValue() < 0.0D) {
                sFH = "-";
                if (d.doubleValue() > -0.0001D) return "8001";
                try {
                    data = d.toString().substring(1);
                } catch (Exception ex7) {
                    return "0000";
                }
                d = new Double(data);
            }
            try {
                label104:
                char[] cFloat = d.toString().toCharArray();
                for (int i = 0; i < cFloat.length; ++i) {
                    if ((cFloat[i] >= '0') && (cFloat[i] <= '9')) continue;
                    if (cFloat[i] != '.') {
                        return "0000";
                    }
                }
            } catch (Exception ex6) {
                return "0000";
            }
            int iDotPos = data.indexOf(".");
            if (iDotPos == -1) {
                try {
                    if (data.length() >= 7) {
                        sZSSjz = data.substring(0, 3);
                        sMB = "10000";
                    }
                } catch (Exception ex4) {
                    return "0000";
                }
                if ((data.length() < 7) && (data.length() > 3)) {
                    try {
                        sZSSjz = data.substring(0, 3);
                    } catch (Exception ex5) {
                        return "0000";
                    }
                    switch (data.length() - 3) {
                        case 1:
                            sMB = "10";
                            break;
                        case 2:
                            sMB = "100";
                            break;
                        case 3:
                            sMB = "1000";
                    }

                } else {
                    while (data.length() < 3) {
                        data = "0" + data;
                    }
                    sZSSjz = data;
                    sMB = "1";
                }
            }
            try {
                if ((iDotPos == 1) && (data.substring(0, 1).equals("0"))) {
                    sMB = "0.001";
                    if (data.length() - iDotPos < 4) {
                        sZSSjz = data.substring(iDotPos + 1, data.length());
                        while (true) {
                            if (sZSSjz.length() >= 3) break label440;
                            sZSSjz = sZSSjz + "0";
                        }
                    }

                    sZSSjz = data.substring(iDotPos + 1, iDotPos + 4);
                }
            } catch (Exception e) {
                label440:
                throw new MessageEncodeException(e);
            }
            try {
                if ((iDotPos >= 0) && (iDotPos <= 2) && (!(data.substring(0, 1).equals("0")))) {
                    if (data.length() < 4) {
                        data = data + "0";
                    }
                    data = data.substring(0, 4);
                    sZSSjz = data.substring(0, iDotPos) + data.substring(iDotPos + 1, 4);

                    if (data.length() - iDotPos == 2) {
                        sMB = "0.1";
                    } else sMB = "0.01";
                }
            } catch (Exception e) {
                throw new MessageEncodeException(e);
            }
            if ((iDotPos >= 3) && (iDotPos < 7)) {
                try {
                    data = data.substring(0, 3);
                } catch (Exception e) {
                    throw new MessageEncodeException(e);
                }
                switch (iDotPos + 1 - data.length()) {
                    case 1:
                        sMB = "1";
                        break;
                    case 2:
                        sMB = "10";
                        break;
                    case 3:
                        sMB = "100";
                        break;
                    case 4:
                        sMB = "1000";
                }

                sZSSjz = data;
            }
            if (iDotPos >= 7) {
                try {
                    data = data.substring(0, 3);
                } catch (Exception ex) {
                    return "0000";
                }
                sMB = "10000";
                sZSSjz = data;
            }
            if (sFH.equals("+")) {
                if (sMB.equals("10000")) {
                    sMB = "0";
                } else if (sMB.equals("1000")) {
                    sMB = "2";
                } else if (sMB.equals("100")) {
                    sMB = "4";
                } else if (sMB.equals("10")) {
                    sMB = "6";
                } else if (sMB.equals("1")) {
                    sMB = "8";
                } else if (sMB.equals("0.1")) {
                    sMB = "A";
                } else if (sMB.equals("0.01")) {
                    sMB = "C";
                } else if (sMB.equals("0.001")) {
                    sMB = "E";
                }

            } else if (sMB.equals("10000")) {
                sMB = "1";
            } else if (sMB.equals("1000")) {
                sMB = "3";
            } else if (sMB.equals("100")) {
                sMB = "5";
            } else if (sMB.equals("10")) {
                sMB = "7";
            } else if (sMB.equals("1")) {
                sMB = "9";
            } else if (sMB.equals("0.1")) {
                sMB = "B";
            } else if (sMB.equals("0.01")) {
                sMB = "D";
            } else if (sMB.equals("0.001")) {
                sMB = "F";
            }

            rt = sMB + sZSSjz;
            rt = DataSwitch.ReverseStringByByte(rt);
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        return rt;
    }
}