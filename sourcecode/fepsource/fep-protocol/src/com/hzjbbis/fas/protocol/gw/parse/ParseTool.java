package com.hzjbbis.fas.protocol.gw.parse;

import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.util.HexDump;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.Inet4Address;
import java.nio.ByteBuffer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParseTool {
    public static final String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    public static final double FRACTION_TIMES_10 = 10.0D;
    public static final double FRACTION_TIMES_100 = 100.0D;
    public static final double FRACTION_TIMES_1000 = 1000.0D;
    public static final double FRACTION_TIMES_10000 = 10000.0D;
    public static final double[] fraction = {1.0D, 10.0D, 100.0D, 1000.0D, 10000.0D};
    public static final int[] days = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final String METER_PROTOCOL_BB = "10";
    public static final String METER_PROTOCOL_ZJ = "20";
    public static final String METER_PROTOCOL_SM = "40";
    private static final Log log = LogFactory.getLog(ParseTool.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String BytesToHex(byte[] data, int start, int len) {
        StringBuffer sb = new StringBuffer();
        for (int i = start; i < start + len; ++i) {
            sb.append(hex[((data[i] & 0xF0) >> 4)]);
            sb.append(hex[(data[i] & 0xF)]);
            sb.append(" ");
        }
        return sb.substring(0, sb.length() - 1);
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

    public static String BytesToHexC(byte[] data, int start, int len) {
        StringBuffer sb = new StringBuffer();
        int loc = start + len - 1;
        for (int i = 0; i < len; ++i) {
            sb.append(hex[((data[loc] & 0xF0) >> 4)]);
            sb.append(hex[(data[loc] & 0xF)]);
            --loc;
        }
        return sb.toString();
    }

    public static String BytesToHexC(byte[] data, int start, int len, byte invalid) {
        StringBuffer sb = new StringBuffer();
        int loc = start + len - 1;
        for (int i = 0; i < len; ++i) {
            if (data[loc] != invalid) {
                sb.append(hex[((data[loc] & 0xF0) >> 4)]);
                sb.append(hex[(data[loc] & 0xF)]);
            }
            --loc;
        }
        return sb.toString();
    }

    public static int BCDToDecimal(byte bcd) {
        int high = (bcd & 0xF0) >>> 4;
        int low = bcd & 0xF;
        if ((high > 9) || (low > 9)) {
            return -1;
        }
        return (high * 10 + low);
    }

    public static int nBcdToDecimal(byte[] data, int start, int len) {
        int rt = 0;
        for (int i = 0; i < len; ++i) {
            rt *= 100;

            int bval = BCDToDecimal(data[(start + len - i - 1)]);
            if (bval < 0) {
                rt = -1;
                break;
            }
            rt += bval;
        }
        return rt;
    }

    public static int nBcdToDecimalC(byte[] data, int start, int len) {
        int rt = 0;
        for (int i = start; i < start + len; ++i) {
            rt *= 100;
            int bval = BCDToDecimal(data[i]);
            if (bval < 0) {
                rt = -1;
                break;
            }
            rt += bval;
        }
        return rt;
    }

    public static int nBcdToDecimalS(byte[] data, int start, int len) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        int rt = 0;

        int loc1 = start + len - 1;
        for (int i = 0; i < len; ++i) {
            int bval;
            rt *= 100;

            if (i > 0) bval = BCDToDecimal(data[(loc1 - i)]);
            else {
                bval = BCDToDecimal((byte) (data[(loc1 - i)] & 0xF));
            }
            if (bval < 0) {
                rt = -1;
                break;
            }
            rt += bval;
        }

        return rt;
    }

    public static int nByteToInt(byte[] data, int start, int len) {
        int rt = 0;
        for (int i = 0; i < len; ++i) {
            rt <<= 8;
            rt += (data[(start + len - i - 1)] & 0xFF);
        }
        return rt;
    }

    public static int nByteToIntS(byte[] data, int start, int len) {
        int rt = 0;
        int loc = start + len - 1;
        for (int i = 0; i < len; ++i) {
            rt <<= 8;
            if (i > 0) rt += (data[(loc - i)] & 0xFF);
            else {
                rt += (data[(loc - i)] & 0x7F);
            }
        }
        return rt;
    }

    public static int HexToDecimal(String hex) {
        int rt = 0;
        for (int i = 0; i < hex.length(); ++i) {
            rt <<= 4;
            rt += CharToDecimal(hex.substring(i, i + 1));
        }
        return rt;
    }

    public static String toPhoneCode(byte[] data, int index, int len, int invalid) {
        StringBuffer sb = new StringBuffer();
        int valid = index + len - 1;
        for (int i = index + len - 1; i >= index; --i) {
            if ((data[i] & 0xFF) != invalid) {
                valid = i;
                break;
            }
        }
        if (valid >= index) {
            if ((data[valid] & 0xF0) != 0) {
                sb.append(hex[((data[valid] & 0xF0) >> 4)]);
            }
            sb.append(hex[(data[valid] & 0xF)]);
            --valid;
        }
        for (int j = valid; j >= index; --j) {
            sb.append(hex[((data[j] & 0xF0) >> 4)]);
            sb.append(hex[(data[j] & 0xF)]);
        }
        return sb.toString();
    }

    public static int CharToDecimalB(String c) {
        int rt = 0;
        int head = 0;
        int tail = 15;
        rt = head + tail >> 1;
        while (!(hex[rt].equals(c))) {
            if (head == tail) {
                break;
            }

            int var = c.compareTo(hex[rt]);
            if (var == 0) {
                break;
            }
            if (var > 0) {
                if (rt == head) {
                    rt = tail;
                    break;
                }
                head = rt;
                rt = head + tail >> 1;
            } else {
                tail = rt;
                rt = head + tail >> 1;
            }
        }
        return rt;
    }

    public static int CharToDecimal(String hex) {
        int rt = 0;
        if (hex.equals("0")) {
            return 0;
        }
        if (hex.equals("1")) {
            return 1;
        }
        if (hex.equals("2")) {
            return 2;
        }
        if (hex.equals("3")) {
            return 3;
        }
        if (hex.equals("4")) {
            return 4;
        }
        if (hex.equals("5")) {
            return 5;
        }
        if (hex.equals("6")) {
            return 6;
        }
        if (hex.equals("7")) {
            return 7;
        }
        if (hex.equals("8")) {
            return 8;
        }
        if (hex.equals("9")) {
            return 9;
        }
        if ((hex.equals("A")) || (hex.equals("a"))) {
            return 10;
        }
        if ((hex.equals("B")) || (hex.equals("b"))) {
            return 11;
        }
        if ((hex.equals("C")) || (hex.equals("c"))) {
            return 12;
        }
        if ((hex.equals("D")) || (hex.equals("d"))) {
            return 13;
        }
        if ((hex.equals("E")) || (hex.equals("e"))) {
            return 14;
        }
        if ((hex.equals("F")) || (hex.equals("f"))) {
            return 15;
        }
        return rt;
    }

    public static String IntToHex(int data) {
        StringBuffer sb = new StringBuffer();
        sb.append(hex[((data & 0xF000) >>> 12)]);
        sb.append(hex[((data & 0xF00) >>> 8)]);
        sb.append(hex[((data & 0xF0) >>> 4)]);
        sb.append(hex[(data & 0xF)]);
        return sb.toString();
    }

    public static String IntToHex4(int data) {
        StringBuffer sb = new StringBuffer();
        sb.append(hex[((data & 0xF0000000) >>> 28)]);
        sb.append(hex[((data & 0xF000000) >>> 24)]);
        sb.append(hex[((data & 0xF00000) >>> 20)]);
        sb.append(hex[((data & 0xF0000) >>> 16)]);
        sb.append(hex[((data & 0xF000) >>> 12)]);
        sb.append(hex[((data & 0xF00) >>> 8)]);
        sb.append(hex[((data & 0xF0) >>> 4)]);
        sb.append(hex[(data & 0xF)]);
        return sb.toString();
    }

    public static String ByteBit(byte data) {
        StringBuffer sb = new StringBuffer();
        int bd = data & 0xFF;
        for (int i = 0; i < 8; ++i) {
            if ((bd & 0x80) > 0) sb.append("1");
            else {
                sb.append("0");
            }
            bd <<= 1;
        }
        return sb.toString();
    }

    public static String ByteBitC(byte data) {
        StringBuffer sb = new StringBuffer();
        int bd = data & 0xFF;
        for (int i = 0; i < 8; ++i) {
            if ((bd & 0x1) > 0) sb.append("1");
            else {
                sb.append("0");
            }
            bd >>>= 1;
        }
        return sb.toString();
    }

    public static String BytesBit(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        for (int i = 0; i < len; ++i) {
            sb.append(ByteBit(data[(len - i - 1)]));
        }
        return sb.toString();
    }

    public static String BytesBit(byte[] data, int start, int len) {
        StringBuffer sb = new StringBuffer();
        int loc = start + len - 1;
        for (int i = 0; i < len; ++i) {
            sb.append(ByteBit(data[loc]));
            --loc;
        }
        return sb.toString();
    }

    public static String BytesBitC(byte[] data, int start, int len) {
        StringBuffer sb = new StringBuffer();
        int loc = start;
        for (int i = 0; i < len; ++i) {
            sb.append(ByteBitC(data[loc]));
            ++loc;
        }
        return sb.toString();
    }

    public static int bitToBytes(byte[] frame, String bits, int pos) {
        int rt = -1;
        try {
            int vlen = bits.length();
            boolean valid = true;
            for (int i = 0; i < vlen; ++i) {
                if (bits.substring(i, i + 1).equals("0")) continue;
                if (bits.substring(i, i + 1).equals("1")) {
                    continue;
                }
                valid = false;
                break;
            }

            if ((valid) && ((vlen & 0x7) == 0)) {
                int blen = 0;
                int len = vlen >>> 3;
                int iloc = pos + len - 1;
                while (blen < vlen) {
                    frame[iloc] = bitToByte(bits.substring(blen, blen + 8));
                    blen += 8;
                    --iloc;
                }
                rt = len;
            }
            return rt;
        } catch (Exception e) {
            log.error("bits to bytes", e);
        }
        return rt;
    }

    public static int bitToBytesC(byte[] frame, String bits, int pos) {
        int rt = -1;
        try {
            int vlen = bits.length();
            boolean valid = true;
            for (int i = 0; i < vlen; ++i) {
                if (bits.substring(i, i + 1).equals("0")) continue;
                if (bits.substring(i, i + 1).equals("1")) {
                    continue;
                }
                valid = false;
                break;
            }

            if ((valid) && ((vlen & 0x7) == 0)) {
                int blen = 0;
                int len = vlen >>> 3;
                int iloc = pos;
                while (blen < vlen) {
                    frame[iloc] = bitToByteC(bits.substring(blen, blen + 8));
                    blen += 8;
                    ++iloc;
                }
                rt = len;
            }
            return rt;
        } catch (Exception e) {
            log.error("bits to bytes", e);
        }
        return rt;
    }

    public static byte bitToByte(String value) {
        byte rt = 0;
        byte[] aa = value.getBytes();
        for (int i = 0; i < aa.length; ++i) {
            rt = (byte) (rt << 1);
            rt = (byte) (rt + AsciiToInt(aa[i]));
        }
        return rt;
    }

    public static byte bitToByteC(String value) {
        byte rt = 0;
        byte[] aa = value.getBytes();
        for (int i = aa.length - 1; i >= 0; --i) {
            rt = (byte) (rt << 1);
            rt = (byte) (rt + AsciiToInt(aa[i]));
        }
        return rt;
    }

    public static byte IntToBcd(int data) {
        byte rt = 0;
        int i = data;
        i %= 100;
        rt = (byte) (i % 10 + (i / 10 << 4));
        return rt;
    }

    public static void IntToBcd(byte[] frame, int value, int loc, int len) {
        int val = value;
        int valxx = val % 100;
        for (int i = 0; i < len; ++i) {
            frame[(loc + i)] = (byte) (valxx % 10 + (valxx / 10 << 4));
            val /= 100;
            valxx = val % 100;
        }
    }

    public static void IntToBcdC(byte[] frame, int value, int loc, int len) {
        int val = value;
        int valxx = val % 100;
        int start = loc + len - 1;
        for (int i = 0; i < len; ++i) {
            frame[start] = (byte) (valxx % 10 + (valxx / 10 << 4));
            val /= 100;
            valxx = val % 100;
            --start;
        }
    }

    public static byte StringToBcd(String data) {
        byte rt = 0;
        try {
            int i = Integer.parseInt(data);
            rt = IntToBcd(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int ByteToFlag(byte data) {
        int rt = 0;
        int val = data & 0xFF;
        int flag = 1;
        if (data > 0) {
            rt = 1;
            while ((flag & val) <= 0) {
                ++rt;
                flag <<= 1;
            }
        }
        return rt;
    }

    public static double ByteToPercent(byte data) {
        double rt = data & 0x7F;
        if ((data & 0x80) > 0) {
            rt *= -1.0D;
        }
        return rt;
    }

    public static void StringToBcds(byte[] frame, int loc, String data) {
        String row = data;
        if (row.length() > 0) {
            if (row.length() % 2 > 0) {
                row = "0" + row;
            }
            int len = row.length() / 2;

            for (int i = 0; i < len; ++i) {
                frame[(loc + len - i - 1)] = StringToBcd(row.substring(i << 1, i + 1 << 1));
            }
            row = null;
        }
    }

    public static void StringToBcds1(byte[] frame, int loc, String data) {
        String row = data;
        if (row.length() > 0) {
            if (row.length() % 2 > 0) {
                row = "0" + row;
            }
            int len = row.length() / 2;

            for (int i = 0; i < len; ++i) {
                frame[(loc + len - i - 1)] = StringToBcd(row.substring(i << 1, i + 1 << 1));
            }
            row = null;
        }
    }

    public static byte[] StringToBcdDec(String data) {
        if ((null == data) || (0 == data.length())) return new byte[0];
        if (0 != data.length() % 2) data = "0" + data;
        byte[] ret = new byte[data.length() / 2];
        int j = ret.length - 1;

        for (int i = 0; i < data.length() - 1; i += 2) {
            byte b1 = (byte) (data.charAt(i) - '0');
            byte b2 = (byte) (data.charAt(i + 1) - '0');
            ret[(j--)] = (byte) ((b1 << 4) + b2);
        }
        return ret;
    }

    public static void StringToBcds(byte[] frame, int loc, String data, int len, byte invalid) {
        int slen = (data.length() >>> 1) + (data.length() & 0x1);
        int iloc = slen + loc - 1;
        int head = 0;
        if ((data.length() & 0x1) > 0) {
            frame[iloc] = StringToBcd(data.substring(0, 1));
            head = 1;
        } else {
            frame[iloc] = StringToBcd(data.substring(0, 2));
            head = 2;
        }
        --iloc;
        for (int i = 1; i < slen; ++i) {
            frame[iloc] = StringToBcd(data.substring(head, head + 2));
            head += 2;
            --iloc;
        }
        iloc = slen + loc;
        for (i = slen; i < len; ++i) {
            frame[iloc] = invalid;
            ++iloc;
        }
    }

    public static void HexsToBytesC(byte[] frame, int loc, String data) {
        try {
            int len = (data.length() >>> 1) + (data.length() & 0x1);
            int head = 0;
            if ((data.length() & 0x1) > 0) {
                frame[loc] = HexToByte(data.substring(0, 1));
                head = 1;
            } else {
                frame[loc] = HexToByte(data.substring(0, 2));
                head = 2;
            }
            for (int i = 1; i < len; ++i) {
                frame[(i + loc)] = HexToByte(data.substring(head, head + 2));
                head += 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte HexToByte(String data) {
        int rt = 0;
        if (data.length() <= 2) {
            for (int i = 0; i < data.length(); ++i) {
                rt <<= 4;
                rt += CharToDecimal(data.substring(i, i + 1));
            }
        }
        return (byte) rt;
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

    public static void HexsToBytesCB(byte[] frame, int loc, String hex) {
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

    public static void HexsToBytes(byte[] frame, int loc, String hex) {
        try {
            int len = (hex.length() >>> 1) + (hex.length() & 0x1);
            byte[] bt = hex.getBytes();
            int head = 0;
            if ((hex.length() & 0x1) > 0) {
                frame[(loc + len - 1)] = (byte) AsciiToInt(bt[0]);
                head = 1;
            } else {
                frame[(loc + len - 1)] = (byte) ((AsciiToInt(bt[0]) << 4) + AsciiToInt(bt[1]));
                head = 2;
            }
            int start = loc + len - 2;
            for (int i = 1; i < len; ++i) {
                frame[start] = (byte) ((AsciiToInt(bt[head]) << 4) + AsciiToInt(bt[(head + 1)]));
                head += 2;
                --start;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void HexsToBytesAA(byte[] frame, int loc, String hex, int flen, byte invalid) {
        try {
            int len = (hex.length() >>> 1) + (hex.length() & 0x1);
            byte[] bt = hex.getBytes();
            int head = 0;
            if ((hex.length() & 0x1) > 0) {
                frame[(loc + len - 1)] = (byte) AsciiToInt(bt[0]);
                head = 1;
            } else {
                frame[(loc + len - 1)] = (byte) ((AsciiToInt(bt[0]) << 4) + AsciiToInt(bt[1]));
                head = 2;
            }
            int start = loc + len - 2;
            for (int i = 1; i < len; ++i) {
                frame[start] = (byte) ((AsciiToInt(bt[head]) << 4) + AsciiToInt(bt[(head + 1)]));
                head += 2;
                --start;
            }
            start = len + loc;
            for (i = len; i < flen; ++i) {
                frame[start] = invalid;
                ++start;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DecimalToBytes(byte[] frame, int val, int loc, int len) {
        try {
            int vals = val;
            for (int i = 0; i < len; ++i) {
                frame[(loc + i)] = (byte) (vals & 0xFF);
                vals >>>= 8;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DecimalToBytesC(byte[] frame, int val, int loc, int len) {
        try {
            int vals = val;
            for (int i = 0; i < len; ++i) {
                frame[(loc + len - 1 - i)] = (byte) (vals & 0xFF);
                vals >>>= 8;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RtuaToBytesC(byte[] frame, int val, int loc, int len) {
        try {
            frame[loc] = (byte) ((val & 0xFF000000) >>> 24);
            frame[(loc + 1)] = (byte) ((val & 0xFF0000) >>> 16);
            frame[(loc + 2)] = (byte) (val & 0xFF);
            frame[(loc + 3)] = (byte) ((val & 0xFF00) >>> 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RtuaToBytesC(byte[] frame, String val, int loc, int len) {
        try {
            int ival = Integer.parseInt(val);
            frame[loc] = (byte) ((ival & 0xFF000000) >>> 24);
            frame[(loc + 1)] = (byte) ((ival & 0xFF0000) >>> 16);
            frame[(loc + 2)] = (byte) (ival & 0xFF);
            frame[(loc + 3)] = (byte) ((ival & 0xFF00) >>> 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] DateToBytes(String time, int len, int type) {
        byte[] rt = null;
        try {
            SimpleDateFormat sdf;
            Date date;
            Calendar cd;
            if (type == 0) {
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = sdf.parse(time);
                cd = Calendar.getInstance();
                cd.setTime(date);
                rt = new byte[6];
                rt[0] = IntToBcd(cd.get(13));
                rt[1] = IntToBcd(cd.get(12));
                rt[2] = IntToBcd(cd.get(11));
                rt[3] = IntToBcd(cd.get(5));
                rt[4] = IntToBcd(cd.get(2) + 1);
                rt[5] = IntToBcd(cd.get(1));
            }
            if (type == 1) {
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                date = sdf.parse(time);
                cd = Calendar.getInstance();
                cd.setTime(date);
                rt = new byte[5];
                rt[0] = IntToBcd(cd.get(12));
                rt[1] = IntToBcd(cd.get(11));
                rt[2] = IntToBcd(cd.get(5));
                rt[3] = IntToBcd(cd.get(2) + 1);
                rt[4] = IntToBcd(cd.get(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static byte[] TimeToBytes(String time, int len, int type) {
        byte[] rt = null;
        try {
            String[] cells = time.split(":");
            if (type == 0) {
                rt = new byte[3];
                rt[0] = IntToBcd(Integer.parseInt(cells[2]));
                rt[1] = IntToBcd(Integer.parseInt(cells[1]));
                rt[2] = IntToBcd(Integer.parseInt(cells[0]));
            }
            if (type == 1) {
                rt = new byte[2];
                rt[0] = IntToBcd(Integer.parseInt(cells[1]));
                rt[1] = IntToBcd(Integer.parseInt(cells[0]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int getOrientation(IMessage message) {
        int rt = 0;
        try {
            byte reply = ((MessageZj) message).head.c_dir;
            if ((reply & 0xFF) > 0) rt = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static int getErrCode(IMessage message) {
        int rt = 0;
        try {
            byte err = ((MessageZj) message).head.c_expflag;
            rt = err & 0xFF;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static String getGwData(IMessage message) {
        String rt = "";
        try {
            ByteBuffer data = null;
            if (message instanceof MessageGw) {
                data = ((MessageGw) message).data;
            }
            data.rewind();
            rt = HexDump.hexDumpCompact(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static byte[] getData(IMessage message) {
        byte[] rt = null;
        try {
            ByteBuffer data = null;
            if (message instanceof MessageZj) {
                data = ((MessageZj) message).data;
            }
            data.rewind();
            int len = data.limit();
            if (len > 0) {
                rt = new byte[len];
                data.get(rt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Calendar getTime(byte[] data, int offset) {
        Calendar rt = Calendar.getInstance();
        try {
            int num = BCDToDecimal(data[(4 + offset)]);
            rt.set(1, num + 2000);
            num = BCDToDecimal((byte) (data[(3 + offset)] & 0x1F));
            rt.set(2, num - 1);
            num = BCDToDecimal((byte) (data[(2 + offset)] & 0x3F));
            rt.set(5, num);
            num = BCDToDecimal((byte) (data[(1 + offset)] & 0x3F));
            rt.set(11, num);
            num = BCDToDecimal((byte) (data[(0 + offset)] & 0x7F));
            rt.set(12, num);
            rt.set(13, 0);
            rt.set(14, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Calendar getTimeW(byte[] data, int offset) {
        Calendar rt = Calendar.getInstance();
        try {
            int num = BCDToDecimal(data[(5 + offset)]);
            rt.set(1, num + 2000);
            num = BCDToDecimal((byte) (data[(4 + offset)] & 0x1F));
            rt.set(2, num - 1);
            num = BCDToDecimal((byte) (data[(3 + offset)] & 0x3F));
            rt.set(5, num);
            num = BCDToDecimal((byte) (data[(2 + offset)] & 0x3F));
            rt.set(11, num);
            num = BCDToDecimal((byte) (data[(1 + offset)] & 0x7F));
            rt.set(12, num);
            num = BCDToDecimal((byte) (data[(0 + offset)] & 0x7F));
            rt.set(13, num);
            rt.set(14, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Calendar getTimeM(byte[] data, int offset) {
        Calendar rt = Calendar.getInstance();
        try {
            int num = BCDToDecimal((byte) (data[(3 + offset)] & 0x1F));
            rt.set(2, num - 1);
            num = BCDToDecimal((byte) (data[(2 + offset)] & 0x3F));
            rt.set(5, num);
            num = BCDToDecimal((byte) (data[(1 + offset)] & 0x3F));
            rt.set(11, num);
            num = BCDToDecimal((byte) (data[(0 + offset)] & 0x7F));
            rt.set(12, num);
            rt.set(13, 0);
            rt.set(14, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Calendar getTimeL(byte[] data, int offset) {
        Calendar rt = Calendar.getInstance();
        try {
            int num = BCDToDecimal((byte) (data[(2 + offset)] & 0x3F));
            rt.set(11, num);
            num = BCDToDecimal((byte) (data[(1 + offset)] & 0x5F));
            rt.set(12, num);
            num = BCDToDecimal((byte) (data[(0 + offset)] & 0x5F));
            rt.set(13, 0);
            rt.set(14, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static void IPToBytes(byte[] frame, int loc, String ip) {
        try {
            String[] para = ip.split(":");
            Inet4Address netaddress = (Inet4Address) Inet4Address.getByName(para[0]);
            byte[] bip = netaddress.getAddress();
            for (int i = 0; i < bip.length; ++i) {
                frame[(loc + 2 + i)] = bip[(bip.length - i - 1)];
            }
            int port = Integer.parseInt(para[1]);
            frame[loc] = (byte) (port & 0xFF);
            frame[(loc + 1)] = (byte) ((port & 0xFF00) >> 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isValid(byte[] data, int start, int len) {
        boolean rt = false;
        for (int i = start; i < start + len; ++i) {
            if ((data[i] & 0xFF) != 255) {
                rt = true;
                break;
            }
        }
        return rt;
    }

    public static boolean isValidBCD(byte[] data, int start, int len) {
        boolean rt = true;
        if ((data[start] & 0xFF) == 255) {
            rt = !(isAllFF(data, start, len));
        }
        if ((data[start] & 0xFF) == 238) {
            rt = false;
        }
        return rt;
    }

    public static boolean isHaveValidBCD(byte[] data, int start, int len) {
        boolean rt = true;
        rt = !(isHaveFF(data, start, len));
        if ((data[start] & 0xFF) == 238) {
            rt = false;
        }
        return rt;
    }

    public static boolean isAllEE(byte[] data, int start, int len) {
        boolean rt = true;
        for (int i = start; i < start + len; ++i) {
            if ((data[i] & 0xFF) != 238) {
                rt = false;
                break;
            }
        }
        return rt;
    }

    public static boolean isAllFF(byte[] data, int start, int len) {
        boolean rt = true;
        for (int i = start; i < start + len; ++i) {
            if ((data[i] & 0xFF) != 255) {
                rt = false;
                break;
            }
        }
        return rt;
    }

    public static boolean isHaveFF(byte[] data, int start, int len) {
        boolean rt = false;
        for (int i = start; i < start + len; ++i) {
            if ((data[i] & 0xFF) == 255) {
                rt = true;
                break;
            }
        }
        return rt;
    }

    public static boolean isValidMonth(byte data) {
        boolean rt = false;
        int hi = BCDToDecimal(data);
        if ((hi >= 0) && (hi <= 12)) {
            rt = true;
        }
        return rt;
    }

    public static boolean isValidDay(byte data, int month, int year) {
        boolean rt = false;
        int hi = BCDToDecimal(data);
        if ((hi >= 0) && (hi <= 31)) {
            if (month == 2) {
                if (year < 0) {
                    if (hi < days[month]) {
                        rt = true;
                    }
                } else if (isLeapYear(year)) {
                    if (hi <= 29) {
                        rt = true;
                    }
                } else if (hi <= 28) {
                    rt = true;
                }

            } else if (hi <= days[month]) {
                rt = true;
            }
        }

        return rt;
    }

    public static boolean isValidHHMMSS(byte data) {
        boolean rt = false;
        int hi = BCDToDecimal(data);
        if ((hi >= 0) && (hi <= 60)) {
            rt = true;
        }
        return rt;
    }

    public static boolean isLeapYear(int year) {
        boolean rt = false;
        if (year >= 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    rt = true;
                }
            } else if (year % 4 == 0) {
                rt = true;
            }
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

    public static String getMeterProtocol(String type) {
        if (type != null) {
            if (type.equals("10")) {
                return "BBMeter";
            }
            if (type.equals("20")) {
                return "ZJMeter";
            }
            if (type.equals("40")) {
                return "SMMeter";
            }
        }
        return null;
    }

    public static boolean isTask(int datakey) {
        boolean rt = false;
        if ((datakey >= 33025) && (datakey < 33278)) {
            rt = true;
        }
        return rt;
    }

    public static boolean isValidBCDString(String val) {
        boolean rt = true;
        if (val != null) {
            for (int i = 0; i < val.length(); ++i) {
                char c = val.charAt(i);
                if ((c >= '0') && (c <= '9')) {
                    continue;
                }
                rt = false;
                break;
            }
        } else {
            rt = false;
        }
        return rt;
    }
}