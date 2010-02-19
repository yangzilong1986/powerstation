package com.hzjbbis.fas.protocol.zj.parse;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataItemParser {
    public static final int COMM_TYPE_SMS = 1;
    public static final int COMM_TYPE_GPRS = 2;
    public static final int COMM_TYPE_DTMF = 3;
    public static final int COMM_TYPE_ETHERNET = 4;
    public static final int COMM_TYPE_INFRA = 5;
    public static final int COMM_TYPE_RS232 = 6;
    public static final int COMM_TYPE_CSD = 7;
    public static final int COMM_TYPE_RADIO = 8;
    public static final int COMM_TYPE_INVALID = 255;
    public static final int TASK_TYPE_NORMAL = 1;
    public static final int TASK_TYPE_RELAY = 2;
    public static final int TASK_TYPE_EXCEPTION = 4;

    public static Object parsevalue(byte[] data, int loc, int len, int fraction, int parserno) {
        Object rt = null;
        try {
            if (data != null) {
                switch (parserno) {
                    case 1:
                        rt = Parser01.parsevalue(data, loc, len, fraction);
                        break;
                    case 2:
                        rt = Parser02.parsevalue(data, loc, len, fraction);
                        break;
                    case 3:
                        rt = Parser03.parsevalue(data, loc, len, fraction);
                        break;
                    case 4:
                        rt = Parser04.parsevalue(data, loc, len, fraction);
                        break;
                    case 5:
                        rt = Parser05.parsevalue(data, loc, len, fraction);
                        break;
                    case 6:
                        rt = Parser06.parsevalue(data, loc, len, fraction);
                        break;
                    case 7:
                        rt = Parser07.parsevalue(data, loc, len, fraction);
                        break;
                    case 8:
                        rt = Parser08.parsevalue(data, loc, len, fraction);
                        break;
                    case 9:
                        rt = Parser09.parsevalue(data, loc, len, fraction);
                        break;
                    case 10:
                        rt = Parser10.parsevalue(data, loc, len, fraction);
                        break;
                    case 11:
                        rt = Parser11.parsevalue(data, loc, len, fraction);
                        break;
                    case 12:
                        rt = Parser12.parsevalue(data, loc, len, fraction);
                        break;
                    case 13:
                        rt = Parser13.parsevalue(data, loc, len, fraction);
                        break;
                    case 14:
                        rt = Parser14.parsevalue(data, loc, len, fraction);
                        break;
                    case 15:
                        rt = Parser15.parsevalue(data, loc, len, fraction);
                        break;
                    case 16:
                        rt = Parser16.parsevalue(data, loc, len, fraction);
                        break;
                    case 17:
                        rt = Parser17.parsevalue(data, loc, len, fraction);
                        break;
                    case 18:
                        rt = Parser18.parsevalue(data, loc, len, fraction);
                        break;
                    case 19:
                        rt = Parser19.parsevalue(data, loc, len, fraction);
                        break;
                    case 20:
                        rt = Parser20.parsevalue(data, loc, len, fraction);
                        break;
                    case 21:
                        rt = Parser21.parsevalue(data, loc, len, fraction);
                        break;
                    case 22:
                        rt = Parser22.parsevalue(data, loc, len, fraction);
                        break;
                    case 23:
                        rt = Parser23.parsevalue(data, loc, len, fraction);
                        break;
                    case 24:
                        rt = Parser24.parsevalue(data, loc, len, fraction);
                        break;
                    case 25:
                        rt = Parser25.parsevalue(data, loc, len, fraction);
                        break;
                    case 26:
                        rt = Parser26.parsevalue(data, loc, len, fraction);
                        break;
                    case 27:
                        rt = Parser27.parsevalue(data, loc, len, fraction);
                        break;
                    case 28:
                        rt = Parser28.parsevalue(data, loc, len, fraction);
                        break;
                    case 29:
                        rt = Parser29.parsevalue(data, loc, len, fraction);
                        break;
                    case 30:
                        rt = Parser30.parsevalue(data, loc, len, fraction);
                        break;
                    case 31:
                        rt = Parser31.parsevalue(data, loc, len, fraction);
                        break;
                    case 32:
                        rt = Parser32.parsevalue(data, loc, len, fraction);
                        break;
                    case 33:
                        rt = Parser33.parsevalue(data, loc, len, fraction);
                        break;
                    case 34:
                        rt = Parser34.parsevalue(data, loc, len, fraction);
                        break;
                    case 35:
                        rt = Parser35.parsevalue(data, loc, len, fraction);
                        break;
                    case 36:
                        rt = Parser36.parsevalue(data, loc, len, fraction);
                        break;
                    case 37:
                        rt = Parser37.parsevalue(data, loc, len, fraction);
                        break;
                    case 38:
                        rt = Parser38.parsevalue(data, loc, len, fraction);
                        break;
                    case 39:
                        rt = Parser39.parsevalue(data, loc, len, fraction);
                        break;
                    case 40:
                        rt = Parser40.parsevalue(data, loc, len, fraction);
                        break;
                    case 41:
                        rt = Parser41.parsevalue(data, loc, len, fraction);
                        break;
                    case 42:
                        rt = Parser42.parsevalue(data, loc, len, fraction);
                        break;
                    case 43:
                        rt = Parser43.parsevalue(data, loc, len, fraction);
                        break;
                    case 44:
                        rt = Parser44.parsevalue(data, loc, len, fraction);
                        break;
                    case 45:
                        rt = Parser45.parsevalue(data, loc, len, fraction);
                        break;
                    case 46:
                        rt = Parser46.parsevalue(data, loc, len, fraction);
                        break;
                    case 47:
                        rt = Parser47.parsevalue(data, loc, len, fraction);
                        break;
                    case 48:
                        rt = Parser48.parsevalue(data, loc, len, fraction);
                        break;
                    case 49:
                        rt = Parser49.parsevalue(data, loc, len, fraction);
                        break;
                    case 50:
                        rt = Parser50.parsevalue(data, loc, len, fraction);
                        break;
                    case 51:
                        rt = Parser51.parsevalue(data, loc, len, fraction);
                        break;
                    case 52:
                        rt = Parser52.parsevalue(data, loc, len, fraction);
                        break;
                    case 53:
                        rt = Parser53.parsevalue(data, loc, len, fraction);
                        break;
                    case 54:
                        rt = Parser54.parsevalue(data, loc, len, fraction);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Object parsevalue(byte[] data, int index, int datakey) {
        Object di = null;
        try {
            switch (datakey & 0xFF00) {
                case 32768:
                    di = parseVC8000(data, index, 0, datakey);
                    break;
                case 33024:
                    di = parseVC81XX(data, index, 0, datakey);
                    break;
                case 33280:
                    di = parseVC82XX(data, index, 0, datakey);
                    break;
                case 33536:
                    di = parseVC83XX(data, index, 0, datakey);
                    break;
                case 33792:
                    di = parseVC84XX(data, index, 0, datakey);
                    break;
                case 34048:
                    di = parseVC85XX(data, index, 0, datakey);
                    break;
                case 34304:
                    di = parseVC86XX(data, index, 0, datakey);
                    break;
                case 34560:
                    di = parseVC87XX(data, index, 0, datakey);
                    break;
                case 34816:
                    di = parseVC88XX(data, index, 0, datakey);
                    break;
                case 35072:
                    di = parseVC89XX(data, index, 0, datakey);
                    break;
                case 36352:
                    di = parseVC8EXX(data, index, 0, datakey);
                    break;
                case 36864:
                case 37120:
                    di = parseVC9XXX(data, index, 4, datakey);
                    break;
                case 40960:
                case 41984:
                    di = parseVCAXXX(data, index, 3, datakey);
                    break;
                case 45056:
                    di = parseVCB0XX(data, index, 4, datakey);
                    break;
                case 45568:
                    di = parseVCB2XX(data, index, 2, datakey);
                    break;
                case 45824:
                    di = parseVCB3XX(data, index, 3, datakey);
                    break;
                case 46080:
                    di = parseVCB0XX(data, index, 4, datakey);
                    break;
                case 46592:
                    di = parseVCB6XX(data, index, 4, datakey);
            }

        } catch (Exception e) {
        }

        return di;
    }

    public static Object parseC8010(byte[] data, int index, int len) {
        int port;
        String ip;
        String des = "";
        int type = data[(index + 8)] & 0xFF;
        switch (type) {
            case 1:
                des = ParseTool.toPhoneCode(data, index, 8, 170);
                break;
            case 2:
                port = ParseTool.nBcdToDecimal(data, index, 2);
                ip = (data[(index + 5)] & 0xFF) + "." + (data[(index + 4)] & 0xFF) + "." + (data[(index + 3)] & 0xFF) + "." + (data[(index + 2)] & 0xFF);
                des = ip + ":" + port;
                break;
            case 3:
                des = ParseTool.toPhoneCode(data, index, 8, 170);
                break;
            case 4:
                port = ParseTool.nBcdToDecimal(data, index, 2);
                ip = (data[(index + 5)] & 0xFF) + "." + (data[(index + 4)] & 0xFF) + "." + (data[(index + 3)] & 0xFF) + "." + (data[(index + 2)] & 0xFF);
                des = ip + ":" + port;
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                des = ParseTool.toPhoneCode(data, index, 8, 170);
                break;
            case 8:
        }

        return des;
    }

    public static Object parseVC8013(byte[] data, int index, int len) {
        String rt = "";
        try {
            rt = ParseTool.toPhoneCode(data, index, len, 170);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Object parseVC8014(byte[] data, int index, int len) {
        String rt = "";
        try {
            int port = ParseTool.nBcdToDecimal(data, index, 2);
            String ip = (data[(index + 5)] & 0xFF) + "." + (data[(index + 4)] & 0xFF) + "." + (data[(index + 3)] & 0xFF) + "." + (data[(index + 2)] & 0xFF);
            rt = ip + ":" + port;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Object parseVC8015(byte[] data, int index, int len) {
        String rt = "";
        try {
            rt = new String(data, index, len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Object parseC8016(byte[] data, int index, int len) {
        int code = ((data[index] & 0xFF) << 8) + (data[(index + 1)] & 0xFF);

        return String.valueOf(code);
    }

    public static Object parseC8017(byte[] data, int index, int len) {
        int code = ((data[index] & 0xFF) << 8) + (data[(index + 1)] & 0xFF);

        return String.valueOf(code);
    }

    public static Object parseVC8020(byte[] data, int index, int len) {
        String rt = "";
        try {
            rt = (data[index] & 0xF) + "," + ParseTool.BytesToHexC(data, index + 1, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Object parseVC8021(byte[] data, int index, int len) {
        String rt = "";
        try {
            rt = ParseTool.BytesToHexL(data, index, len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Object parseVC8030(byte[] data, int index, int len) {
        String rt = "";
        try {
            Calendar time = ParseTool.getTimeW(data, index);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            rt = sf.format(time.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Object parseVC8031(byte[] data, int index, int len) {
        String rt = "";
        try {
            rt = rt + ParseTool.ByteToHex(data[(index + 3)]);
            rt = rt + "," + (data[(index + 2)] & 0xFF);
            rt = rt + "," + ParseTool.ByteToHex(data[(index + 1)]) + ":" + ParseTool.ByteToHex(data[index]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Object parseVC8032(byte[] data, int index, int len) {
        return new Integer(data[index] & 0xF);
    }

    public static Object parseVC8000(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey & 0xFFF0) {
            case 32784:
                rt = parseVC801X(data, index, len, datakey);
                break;
            case 32800:
                rt = parseVC802X(data, index, len, datakey);
                break;
            case 32816:
                rt = parseVC803X(data, index, len, datakey);
                break;
            case 32832:
                rt = parseVC804X(data, index, len, datakey);
                break;
            case 32848:
                rt = parseVC805X(data, index, len, datakey);
                break;
            case 32864:
                rt = parseVC806X(data, index, len, datakey);
        }

        return rt;
    }

    public static Object parseVC801X(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 32784:
            case 32785:
            case 32786:
                rt = parseC8010(data, index, len);
                break;
            case 32787:
                rt = parseVC8013(data, index, len);
                break;
            case 32788:
                rt = parseVC8014(data, index, len);
                break;
            case 32789:
                rt = parseVC8015(data, index, len);
                break;
            case 32790:
                rt = parseC8016(data, index, len);
                break;
            case 32791:
                rt = parseC8017(data, index, len);
        }

        return rt;
    }

    public static Object parseVC802X(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 32800:
                rt = parseVC8020(data, index, 3);
                break;
            case 32801:
            case 32802:
                rt = parseVC8021(data, index, 3);
                break;
            case 32803:
                rt = parseVC8021(data, index, 1);
        }

        return rt;
    }

    public static Object parseVC803X(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 32816:
                rt = parseVC8030(data, index, 6);
                break;
            case 32817:
                rt = parseVC8031(data, index, 4);
            case 32818:
                rt = parseVC8032(data, index, 1);
                break;
            case 32819:
                rt = ParseTool.BytesToHexL(data, index, 16);
                break;
            case 32820:
            case 32821:
            case 32822:
                rt = new Integer(data[index] & 0xFF);
                break;
            case 32823:
                rt = ParseTool.ByteToHex(data[index]);
        }

        return rt;
    }

    public static Object parseVC8048(byte[] data, int index, int len) {
        StringBuffer sb = new StringBuffer();
        sb.append(ParseTool.ByteToHex(data[(index + 8)]));
        for (int i = 0; i < 8; ++i) {
            sb.append(",");
            sb.append(String.valueOf(data[(index + 7 - i)] & 0xFF));
        }
        return sb.toString();
    }

    public static Object parseVC804C(byte[] data, int index, int len) {
        StringBuffer sb = new StringBuffer();
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);
        sb.append(String.valueOf(data[(index + 3)] & 0xF));
        sb.append(",");
        sb.append(nf.format((data[(index + 2)] & 0xFF) / 100.0D));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 1)]));
        sb.append(":");
        sb.append(ParseTool.ByteToHex(data[index]));
        return sb.toString();
    }

    public static Object parseVC804X(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 32832:
            case 32833:
            case 32834:
            case 32835:
            case 32836:
                rt = ParseTool.ByteToHex(data[index]);
                break;
            case 32837:
            case 32838:
                rt = ParseTool.ByteToHex(data[(index + 2)]) + ":" + ParseTool.ByteToHex(data[(index + 1)]) + ":" + ParseTool.ByteToHex(data[index]);
                break;
            case 32839:
                rt = new Integer(data[index] & 0xFF);
                break;
            case 32840:
            case 32841:
                rt = parseVC8048(data, index, 9);
                break;
            case 32842:
            case 32843:
                rt = new Double(ParseTool.ByteToPercent(data[index]));
                break;
            case 32844:
                rt = parseVC804C(data, index, 4);
                break;
            case 32845:
                rt = new Integer(data[index] & 0xFF);
                break;
            case 32846:
                rt = new Double(ParseTool.nBcdToDecimal(data, index, 4) / 100.0D);
        }

        return rt;
    }

    public static Object parseVC8051(byte[] data, int index, int len) {
        StringBuffer sb = new StringBuffer();
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);
        sb.append(ParseTool.ByteToHex(data[(index + 6)]));
        sb.append(":");
        sb.append(ParseTool.ByteToHex(data[(index + 5)]));
        sb.append(",");
        sb.append(String.valueOf(data[(index + 4)] & 0xF));
        sb.append(",");
        sb.append(nf.format(ParseTool.nBcdToDecimal(data, index, 4) / 100.0D));

        return sb.toString();
    }

    public static Object parseVC8059(byte[] data, int index, int len) {
        StringBuffer sb = new StringBuffer();
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);
        sb.append(ParseTool.ByteToHex(data[(index + 8)]));
        sb.append("-");
        sb.append(ParseTool.ByteToHex(data[(index + 7)]));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 6)]));
        sb.append("-");
        sb.append(ParseTool.ByteToHex(data[(index + 5)]));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 4)]));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 3)]));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 2)]));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 1)]));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 0)]));
        return sb.toString();
    }

    public static Object parseVC805X(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 32848:
                rt = new Integer(data[index] & 0xF);
            case 32849:
            case 32850:
            case 32851:
            case 32852:
            case 32853:
            case 32854:
            case 32855:
            case 32856:
                rt = parseVC8051(data, index, 7);
                break;
            case 32857:
                rt = parseVC8059(data, index, 9);
        }

        return rt;
    }

    public static Object parseVC806X(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 32864:
            case 32865:
                rt = new Integer(ParseTool.nBcdToDecimal(data, index, 4));
            case 32866:
                rt = String.valueOf(ParseTool.nBcdToDecimalS(data, index + 1, 4)) + "," + (data[index] & 0xFF);
                break;
            case 32867:
                rt = new Integer(ParseTool.nBcdToDecimalS(data, index, 5));
                break;
            case 32868:
                rt = ParseTool.ByteToHex(data[(index + 1)]) + ":" + ParseTool.ByteToHex(data[index]);
                break;
            case 32869:
                rt = new Integer(ParseTool.nBcdToDecimal(data, index, 4));
        }

        return rt;
    }

    public static Object parseVC807X(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 32880:
                rt = new Integer(data[index] & 0xF);
            case 32881:
            case 32882:
            case 32883:
            case 32884:
            case 32885:
            case 32886:
            case 32887:
            case 32888:
                rt = parseVC8051(data, index, 7);
                break;
            case 32889:
                rt = parseVC8059(data, index, 9);
        }

        return rt;
    }

    public static Object parseVC808X(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 32896:
                rt = new Integer(data[index] & 0xF);
            case 32897:
            case 32898:
            case 32899:
            case 32900:
            case 32901:
            case 32902:
            case 32903:
            case 32904:
                rt = parseVC8051(data, index, 7);
                break;
            case 32905:
                rt = parseVC8059(data, index, 9);
        }

        return rt;
    }

    public static Object parseVC809X(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 32912:
                rt = new Integer(data[index] & 0xF);
            case 32913:
            case 32914:
            case 32915:
            case 32916:
            case 32917:
            case 32918:
            case 32919:
            case 32920:
                rt = parseVC8051(data, index, 7);
                break;
            case 32921:
                rt = parseVC8059(data, index, 9);
        }

        return rt;
    }

    public static Object parseVC80AX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 32928:
                rt = new Integer(data[index] & 0xF);
            case 32929:
            case 32930:
            case 32931:
            case 32932:
            case 32933:
            case 32934:
            case 32935:
            case 32936:
                rt = parseVC8051(data, index, 7);
                break;
            case 32937:
                rt = parseVC8059(data, index, 9);
        }

        return rt;
    }

    public static Object parseVC80BX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 32944:
                rt = new Integer(data[index] & 0xF);
            case 32945:
            case 32946:
            case 32947:
            case 32948:
            case 32949:
            case 32950:
            case 32951:
            case 32952:
                rt = parseVC8051(data, index, 7);
                break;
            case 32953:
                rt = parseVC8059(data, index, 9);
        }

        return rt;
    }

    public static Object parseVC8101(byte[] data, int index, int len) {
        StringBuffer sb = new StringBuffer();
        sb.append(ParseTool.ByteToHex(data[index]));
        sb.append(",");
        sb.append(String.valueOf(data[(index + 2)] & 0xFF));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 1)]));
        sb.append(",");
        sb.append(String.valueOf(data[(index + 4)] & 0xFF));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 3)]));
        sb.append(",");
        sb.append(String.valueOf(data[(index + 6)] & 0xFF));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 5)]));
        sb.append(",");
        sb.append(String.valueOf(data[(index + 8)] & 0xFF));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 7)]));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(index + 9)])));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(index + 10)])));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.nBcdToDecimal(data, index + 11, 2)));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.nBcdToDecimal(data, index + 13, 2)));
        sb.append(",");
        int din = ParseTool.BCDToDecimal(data[(index + 15)]);
        sb.append(String.valueOf(din));
        if (din <= 32) {
            int loc = index + 16;
            for (int i = 0; i < din; ++i) {
                sb.append(",");
                sb.append(ParseTool.BytesToHexC(data, loc, 2));
                loc += 2;
            }
        }
        return sb.toString();
    }

    public static Object parseVC8102(byte[] data, int index, int len) {
        StringBuffer sb = new StringBuffer();
        sb.append(ParseTool.ByteToHex(data[index]));
        sb.append(",");
        sb.append(String.valueOf(data[(index + 2)] & 0xFF));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 1)]));
        sb.append(",");
        sb.append(String.valueOf(data[(index + 4)] & 0xFF));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 3)]));
        sb.append(",");
        sb.append(String.valueOf(data[(index + 6)] & 0xFF));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 5)]));
        sb.append(",");
        sb.append(String.valueOf(data[(index + 8)] & 0xFF));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 7)]));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(index + 9)])));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(index + 10)])));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 11)]));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.nBcdToDecimal(data, index + 12, 2)));
        sb.append(",");
        sb.append(String.valueOf(data[(index + 14)] & 0xFF));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 15)]));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.nByteToInt(data, index + 16, 2)));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.nByteToInt(data, index + 18, 2)));
        sb.append(",");
        int cl = ParseTool.BCDToDecimal(data[(index + 20)]);
        sb.append(String.valueOf(cl));
        sb.append(",");
        if (cl <= 32) {
            sb.append(ParseTool.BytesToHexL(data, index + 21, cl));
        }
        return sb.toString();
    }

    public static Object parseVC8104(byte[] data, int index, int len) {
        StringBuffer sb = new StringBuffer();
        sb.append(ParseTool.ByteToHex(data[index]));
        sb.append(",");
        sb.append(ParseTool.BytesToHexC(data, index + 1, 2));
        sb.append(",");
        sb.append(String.valueOf(data[(index + 4)] & 0xFF));
        sb.append(",");
        sb.append(ParseTool.ByteToHex(data[(index + 3)]));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(index + 5)])));
        sb.append(",");
        int din = ParseTool.BCDToDecimal(data[(index + 6)]);
        sb.append(String.valueOf(din));
        if (din <= 32) {
            int loc = index + 7;
            for (int i = 0; i < din; ++i) {
                sb.append(",");
                sb.append(ParseTool.ByteToHex(data[(loc + 2)]));
                sb.append(" ");
                sb.append(ParseTool.BytesToHexC(data, loc, 2));
                loc += 3;
            }
            sb.append(",");
            sb.append(String.valueOf(ParseTool.BCDToDecimal(data[loc])));
        }
        return sb.toString();
    }

    public static Object parseVC81XX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        if (datakey == 33024) {
            rt = new Integer(data[index] & 0xFF);
        } else if ((datakey > 33024) && (datakey < 33278)) {
            int type = data[index] & 0xFF;
            switch (type) {
                case 1:
                    parseVC8101(data, index, 16);
                    break;
                case 2:
                    parseVC8102(data, index, 21);
                    break;
                case 4:
                    parseVC8104(data, index, 7);
                case 3:
            }
        } else if (datakey == 33278) {
            rt = ParseTool.BytesToHexC(data, index, 32);
        }
        return rt;
    }

    public static Object parseVC82XX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        rt = new Integer(data[index] & 0xFF);
        return rt;
    }

    public static Object parseVC83XX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        if (datakey == 33536) rt = new Integer(data[index] & 0xFF);
        else if ((datakey > 33536) && (datakey < 33790)) {
            rt = String.valueOf(ParseTool.BCDToDecimal(data[(index + 1)])) + "," + String.valueOf((data[index] & 0xFF) >> 4) + "," + String.valueOf(data[index] & 0xF);
        } else if (datakey == 33790) {
            rt = ParseTool.BytesToHexC(data, index, 32);
        }
        return rt;
    }

    public static Object parseVC84XX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        if (datakey == 33792) rt = new Integer(data[index] & 0xFF);
        else if ((datakey > 33792) && (datakey < 34046)) {
            rt = String.valueOf(ParseTool.BCDToDecimal(data[(index + 4)])) + "," + ParseTool.ByteToHex(data[(index + 3)]) + "," + String.valueOf(ParseTool.nBcdToDecimal(data, index, 3));
        } else if (datakey == 34046) {
            rt = ParseTool.BytesToHexC(data, index, 32);
        }
        return rt;
    }

    public static Object parseVC8501(byte[] data, int index, int len) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(index + 18)])));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.BytesToHexC(data, index + 16, 2)));
        int loc = index + 14;
        for (int i = 0; i < 8; ++i) {
            sb.append(",");
            sb.append(ParseTool.ByteToHex(data[(loc + 1)]));
            sb.append(",");
            sb.append(String.valueOf(ParseTool.BCDToDecimal(data[loc])));
            loc -= 2;
        }
        return sb.toString();
    }

    public static Object parseVC85XX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        if (datakey == 34048) rt = new Integer(data[index] & 0xFF);
        else if ((datakey > 34048) && (datakey < 34302)) {
            rt = parseVC8501(data, index, 19);
        } else if (datakey == 34302) {
            rt = ParseTool.BytesToHexC(data, index, 32);
        }
        return rt;
    }

    public static Object parseVC8601(byte[] data, int index, int len) {
        StringBuffer sb = new StringBuffer();
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);
        sb.append(String.valueOf(ParseTool.BytesToHexC(data, index + 9, 2)));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(index + 8)])));
        sb.append(",");
        sb.append(String.valueOf(ParseTool.BCDToDecimal(data[(index + 7)])));
        sb.append(",");
        sb.append(nf.format(ParseTool.nBcdToDecimal(data, index + 4, 3) / 100.0D));
        sb.append(",");
        sb.append(nf.format(ParseTool.nBcdToDecimal(data, index + 2, 2) / 100.0D));
        sb.append(",");
        sb.append(nf.format(ParseTool.nBcdToDecimal(data, index, 2) / 100.0D));
        return sb.toString();
    }

    public static Object parseVC86XX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        if (datakey == 34304) rt = new Integer(data[index] & 0xFF);
        else if ((datakey > 34304) && (datakey < 34558)) {
            rt = parseVC8601(data, index, 19);
        } else if (datakey == 34558) {
            rt = ParseTool.BytesToHexC(data, index, 32);
        }
        return rt;
    }

    public static Object parseVC87XX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey & 0xFF0F) {
            case 34560:
                rt = new Integer((data[index] & 0xFF) * 300);
                break;
            case 34561:
                rt = new Integer(ParseTool.BCDToDecimal(data[index]));
                break;
            case 34562:
                rt = new Integer(ParseTool.BCDToDecimal(data[index]));
                break;
            case 34563:
                rt = new Integer(ParseTool.BCDToDecimal(data[index]));
        }

        return rt;
    }

    public static Object parseVC88XX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 34816:
                rt = ParseTool.BytesToHexC(data, index, 2);
                break;
            case 34817:
                rt = new Double(ParseTool.nBcdToDecimal(data, index, 2) / 10.0D);
                break;
            case 34818:
            case 34819:
            case 34820:
            case 34821:
            case 34822:
                rt = new Integer(data[index] & 0xFF);
                break;
            case 34823:
                rt = ParseTool.BytesToHexC(data, index, 2);
                break;
            case 34824:
                rt = ParseTool.BytesToHexC(data, index, 1);
                break;
            case 34825:
                rt = ParseTool.BytesToHexC(data, index, 8);
                break;
            case 34826:
                rt = ParseTool.BytesToHexC(data, index, 2);
        }

        return rt;
    }

    public static Object parseVC89XX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 35072:
                rt = new Integer(data[index] & 0xFF);
                break;
            case 35073:
                rt = ParseTool.ByteToHex(data[index]);
                break;
            case 35074:
                rt = ParseTool.BytesToHexC(data, index, 6);
                break;
            case 35075:
                rt = ParseTool.ByteToHex(data[index]);
                break;
            case 35076:
                rt = new Integer(ParseTool.BCDToDecimal(data[index]));
                break;
            case 35077:
                rt = new Integer(ParseTool.BCDToDecimal(data[index]) * 300);
                break;
            case 34822:
                rt = new Integer(data[index] & 0xFF);
                break;
            case 35088:
                rt = ParseTool.ByteToHex(data[index]);
                break;
            case 35089:
            case 35090:
            case 35091:
                rt = new Integer(ParseTool.nBcdToDecimal(data, index, 2));
                break;
            case 35092:
            case 35093:
            case 35094:
            case 35105:
            case 35106:
            case 35107:
            case 35108:
            case 35109:
                rt = new Double(ParseTool.nBcdToDecimal(data, index, 2) / 100.0D);
                break;
            case 35110:
            case 35111:
            case 35112:
            case 35113:
                rt = new Double(ParseTool.nBcdToDecimal(data, index, 1) / 100.0D);
        }

        return rt;
    }

    public static Object parseVC9XXX(byte[] data, int index, int len, int datakey) {
        Double rt = null;
        int v = ParseTool.nBcdToDecimal(data, index, len);
        rt = new Double(v / 100.0D);
        return rt;
    }

    public static Object parseVCAXXX(byte[] data, int index, int len, int datakey) {
        Double rt = null;
        int v = ParseTool.nBcdToDecimal(data, index, len);
        rt = new Double(v / 10000.0D);
        return rt;
    }

    public static Object parseVCB0XX(byte[] data, int index, int len, int datakey) {
        return ParseTool.getTimeM(data, index);
    }

    public static Object parseVCB2XX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 45584:
            case 45585:
                rt = parseVCB0XX(data, index, len, datakey);
                break;
            case 45586:
            case 45587:
                rt = new Integer(ParseTool.nBcdToDecimal(data, index, len));
        }

        return rt;
    }

    public static Object parseVCB3XX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey & 0xFFF0) {
            case 45840:
                rt = new Integer(ParseTool.nBcdToDecimal(data, index, 2));
                break;
            case 45856:
                rt = new Integer(ParseTool.nBcdToDecimal(data, index, 3));
        }

        return rt;
    }

    public static Object parseVCB6XX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey & 0xFFF0) {
            case 46608:
                rt = new Integer(ParseTool.nBcdToDecimal(data, index, 2));
                break;
            case 46624:
                rt = new Double(ParseTool.nBcdToDecimal(data, index, 2) / 100.0D);
                break;
            case 46640:
                rt = new Double(ParseTool.nBcdToDecimal(data, index, 3) / 10000.0D);
                break;
            case 46656:
                rt = new Double(ParseTool.nBcdToDecimal(data, index, 2) / 100.0D);
                break;
            case 46672:
                rt = new Double(ParseTool.nBcdToDecimal(data, index, 2) / 1000.0D);
        }

        return rt;
    }

    public static Object parseVCCXXX(byte[] data, int index, int len, int datakey) {
        Object rt = null;
        switch (datakey) {
            case 49168:
                rt = "20" + ParseTool.ByteToHex(data[(index + 3)]) + "-" + ParseTool.ByteToHex(data[(index + 2)]) + "-" + ParseTool.ByteToHex(data[(index + 1)]) + "," + ParseTool.ByteToHex(data[index]);

                break;
            case 49169:
                rt = ParseTool.ByteToHex(data[(index + 2)]) + ":" + ParseTool.ByteToHex(data[(index + 1)]) + ":" + ParseTool.ByteToHex(data[index]);

                break;
            case 49184:
                rt = new Integer(data[index] & 0xF);
                break;
            case 49200:
            case 49201:
                rt = new Integer(ParseTool.nBcdToDecimal(data, index, 3));
                break;
            case 49433:
            case 49434:
                rt = new Double(ParseTool.nBcdToDecimal(data, index, 4) / 100.0D);
                break;
            case 49969:
            case 49970:
            case 49971:
            case 49972:
            case 49973:
            case 49974:
            case 49975:
            case 49976:
                rt = ParseTool.ByteToHex(data[(index + 2)]) + ":" + ParseTool.ByteToHex(data[(index + 1)]) + "," + ParseTool.ByteToHex(data[index]);
        }

        return rt;
    }

    public static Object parseVC8EXX(byte[] data, int index, int len, int datakey) {
        SimpleDateFormat sf;
        Object rt = null;
        switch (datakey & 0xFFF0) {
            case 36368:
            case 36384:
                rt = new Double(ParseTool.nBcdToDecimal(data, index, 4) / 100.0D);
                break;
            case 36400:
            case 36416:
                rt = new Double(ParseTool.nBcdToDecimalS(data, index, 4) / 10.0D);
                break;
            case 36448:
                if (datakey < 36450) {
                    rt = new Double(ParseTool.nBcdToDecimalS(data, index, 4) / 10.0D);
                    break label512:
                }
                rt = new Double(ParseTool.nBcdToDecimalS(data, index, 3) / 100.0D);

                break;
            case 36464:
                rt = new Double(ParseTool.nBcdToDecimal(data, index, 2) / 100.0D);
                break;
            case 36480:
                if (datakey < 36486) {
                    rt = new Integer(ParseTool.BCDToDecimal(data[index]));
                    break label512:
                }
                sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Calendar time = ParseTool.getTime(data, index + 2);
                int val = ParseTool.nBcdToDecimal(data, index, 2);
                rt = sf.format(time.getTime()) + "," + val;

                break;
            case 36496:
                if (datakey == 36496) {
                    rt = new Double(ParseTool.nBcdToDecimal(data, index, 2) / 10.0D);
                    break label512:}
                if (datakey < 36499) {
                    sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    nf = NumberFormat.getInstance();
                    nf.setMaximumFractionDigits(2);
                    nf.setGroupingUsed(false);
                    time = ParseTool.getTime(data, index + 3);
                    val = ParseTool.nBcdToDecimalS(data, index, 3);
                    rt = sf.format(time.getTime()) + "," + nf.format(val / 100.0D);
                    break label512:}
                sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                nf.setGroupingUsed(false);
                Calendar time = ParseTool.getTime(data, index + 2);
                int val = ParseTool.nBcdToDecimal(data, index, 2);
                rt = sf.format(time.getTime()) + "," + nf.format(val / 100.0D);
        }

        label512:
        return rt;
    }

    public static Object parseVError(byte err) {
        String rt = "";
        switch (err & 0xFF) {
            case 0:
                rt = "正确";
                break;
            case 1:
                rt = "中继命令没有返回";
                break;
            case 2:
                rt = "设置内容非法";
                break;
            case 3:
                rt = "密码权限不足";
                break;
            case 4:
                rt = "无此数据项";
                break;
            case 5:
                rt = "命令时间失效";
                break;
            case 17:
                rt = "目标地址不存在";
                break;
            case 18:
                rt = "发送失败";
                break;
            case 19:
                rt = "短信息帧太长";
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
        }
        return rt;
    }
}