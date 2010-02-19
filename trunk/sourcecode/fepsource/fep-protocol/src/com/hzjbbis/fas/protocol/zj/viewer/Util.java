package com.hzjbbis.fas.protocol.zj.viewer;

public class Util {
    public static String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    public static String BytesToHex(byte[] data, int start, int len) {
        StringBuffer sb = new StringBuffer();
        for (int i = start; i < start + len; ++i) {
            sb.append(hex[((data[i] & 0xF0) >> 4)]);
            sb.append(hex[(data[i] & 0xF)]);
            sb.append(" ");
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return "";
    }

    public static String ByteToHex(byte data) {
        String bt = "";
        bt = hex[((data & 0xF0) >> 4)] + hex[(data & 0xF)];
        return bt;
    }

    public static String BytesToHexL(byte[] data, int start, int len) {
        StringBuffer sb = new StringBuffer();
        for (int i = start; i < start + len; ++i) {
            sb.append(hex[((data[i] & 0xF0) >> 4)]);
            sb.append(hex[(data[i] & 0xF)]);
        }
        return sb.toString();
    }

    public static void HexsToBytes(byte[] frame, int loc, String hex) {
        try {
            int len = (hex.length() >>> 1) + (hex.length() & 0x1);

            byte[] bt = hex.getBytes();
            int head = 0;
            if ((hex.length() & 0x1) > 0) {
                frame[loc] = (byte) AsciiToInt(bt[0]);
                head = 1;
            } else {
                frame[loc] = (byte) ((AsciiToInt(bt[0]) << 4) + AsciiToInt(bt[1]));
                head = 2;
            }
            for (int i = 1; i < len; ++i) {
                frame[(loc + i)] = (byte) ((AsciiToInt(bt[head]) << 4) + AsciiToInt(bt[(head + 1)]));
                head += 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int AsciiToInt(byte val) {
        int rt = val & 0xFF;
        if (val < 58) rt -= 48;
        else if (rt < 71) rt -= 55;
        else {
            rt -= 87;
        }
        return rt;
    }

    public static boolean validHex(String data) {
        boolean rt = true;
        for (int i = 0; i < data.length(); ++i) {
            String c = data.substring(i, i + 1);
            if ((c.compareTo("0") >= 0) && (c.compareToIgnoreCase("9") <= 0)) {
                continue;
            }
            if ((c.compareToIgnoreCase("A") >= 0) && (c.compareToIgnoreCase("F") <= 0)) {
                continue;
            }
            rt = false;
            break;
        }
        return rt;
    }

    public static byte calculateCS(byte[] data, int start, int len) {
        int cs = 0;
        for (int i = start; i < start + len; ++i) {
            cs += (data[i] & 0xFF);
            cs &= 255;
        }
        return (byte) (cs & 0xFF);
    }

    public static int maxArrow(String arrow) {
        int len = 0;
        int flag = 0;
        int style = -1;
        int lastc = 0;
        String arrow01 = ">";
        String arrow02 = "<";
        String handle01 = "-";
        String handle02 = "=";

        if (arrow != null) {
            String strm = arrow.trim();
            if (strm.length() > 0) {
                flag = 0;
                style = -1;
                int alen = 0;

                for (int i = 0; i < strm.length(); ++i) {
                    String strc = strm.substring(i, i + 1);

                    if (strc.equals(arrow01)) {
                        if (style < 0) {
                            if (len <= 0) len = 1;
                        } else if (style == 0) {
                            ++alen;
                            if (alen > len) {
                                len = alen;
                            }
                            alen = 0;
                            flag = 0;
                            style = -1;
                        } else {
                            if (alen > len) {
                                len = alen;
                            }
                            alen = 0;
                            flag = 0;
                            style = -1;
                        }
                        lastc = 0;
                    } else if (strc.equals(arrow02)) {
                        if (style < 0) {
                            style = 1;
                            alen = 1;
                            flag = 0;
                        } else if (style == 0) {
                            style = 1;
                            alen = 1;
                            flag = 0;
                        } else {
                            if ((len <= 0) || (alen > len)) {
                                len = alen;
                            }
                            style = 1;
                            alen = 1;
                            flag = 0;
                        }
                        lastc = 1;
                    } else if (strc.equals(handle01)) {
                        if (style < 0) {
                            style = 0;
                            alen = 1;
                            flag = 0;
                        } else if (style == 0) {
                            if (lastc == 2) {
                                ++alen;
                            } else alen = 1;

                        } else if ((lastc == 2) || (lastc == 1)) {
                            ++alen;
                        } else {
                            if (alen > len) {
                                len = alen;
                            }
                            alen = 1;
                            flag = 0;
                            style = 0;
                        }

                        lastc = 2;
                    } else if (strc.equals(handle02)) {
                        if (style < 0) {
                            style = 0;
                            alen = 1;
                            flag = 0;
                        } else if (style == 0) {
                            if (lastc == 3) {
                                ++alen;
                            } else alen = 1;

                        } else if ((lastc == 3) || (lastc == 1)) {
                            ++alen;
                        } else {
                            if (alen > len) {
                                len = alen;
                            }
                            alen = 1;
                            flag = 0;
                            style = 0;
                        }

                        lastc = 3;
                    } else {
                        if ((alen > 0) && (alen > len)) {
                            len = alen;
                        }
                        alen = 0;
                        flag = 0;
                        style = -1;
                    }
                }
                if ((style == 1) && (alen > len)) {
                    len = alen;
                }
            }
        }
        return len;
    }
}