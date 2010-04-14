/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.codec.utils;

import java.nio.ByteBuffer;

/**
 *
 * @author Thinkpad
 */
public class HexDump {

    private static final byte[] highDigits;
    private static final byte[] lowDigits;

    // initialize lookup tables
    static {
        final byte[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        int i;
        byte[] high = new byte[256];
        byte[] low = new byte[256];

        for (i = 0; i < 256; i++) {
            high[i] = digits[i >>> 4];
            low[i] = digits[i & 0x0F];
        }

        highDigits = high;
        lowDigits = low;
    }

    public static String hexDump(ByteBuffer in) {
        if (null == in) {
            return "null";
        }
        int size = in.remaining();

        if (size == 0) {
            return "empty";
        }

        StringBuffer out = new StringBuffer((in.remaining() * 3) - 1);

        //in.mark();
        int i = in.position();

        // fill the first
        int byteValue = in.get(i) & 0xFF;
        out.append((char) highDigits[byteValue]);
        out.append((char) lowDigits[byteValue]);
        size--;
        i++;
        // and the others, too
        for (; size > 0; size--) {
            out.append(' ');
            byteValue = in.get(i) & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);
            i++;
        }

        //in.reset();

        return out.toString();
    }

    public static String hexDumpCompact(ByteBuffer in) {
        if (null == in) {
            return "";
        }
        int size = in.remaining();

        if (size == 0) {
            return "";
        }

        StringBuffer out = new StringBuffer(in.remaining() * 2);

        int i = in.position();

        //in.mark();		

        int byteValue;

        for (; size > 0; size--) {
            byteValue = in.get(i) & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);
            i++;
        }

        //in.reset();

        return out.toString();
    }

    public static String hexDumpCompactSilent(ByteBuffer in) {
        if (null == in) {
            return "";
        }
        int size = in.remaining();

        if (size == 0) {
            return "";
        }

        StringBuffer out = new StringBuffer(in.remaining() * 2);

        int byteValue;

        int i = in.position();
        for (; size > 0; size--) {
            byteValue = in.get(i) & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);
            i++;
        }

        return out.toString();
    }

    public static String hexDump(byte[] in, int offset, int length) {
        int size = in.length;
        if (size == 0 || length < 0 || length > size || offset < 0 || offset > size) {
            return "empty";
        }

        if (offset + length > size) {
            return "empty";
        }

        StringBuffer out = new StringBuffer(length * 3 - 1);

        // fill the first
        int byteValue = in[offset++] & 0xFF;
        out.append((char) highDigits[byteValue]);
        out.append((char) lowDigits[byteValue]);
        length--;

        // and the others, too
        for (; length > 0; length--) {
            out.append(' ');
            byteValue = in[offset++] & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);
        }

        return out.toString();
    }

    public static String hexDumpCompact(byte[] in, int offset, int length) {
        if (null == in) {
            return "";
        }
        int size = in.length;
        if (size == 0 || length < 0 || length > size || offset < 0 || offset > size) {
            return "";
        }

        if (offset + length > size) {
            return "";
        }

        StringBuffer out = new StringBuffer(length * 2);

        int byteValue;

        // and the others, too
        for (; length > 0; length--) {
            byteValue = in[offset++] & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);
        }

        return out.toString();
    }

    public static String toHex(byte byteValue) {
        StringBuffer out = new StringBuffer(2);
        int index = byteValue & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        return out.toString();
    }

    public static String toHex(short shortValue) {
        StringBuffer out = new StringBuffer(5);
        int index = (shortValue >> 8) & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        index = shortValue & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        return out.toString();
    }

    public static String toHex(int i) {
//		  return Integer.toHexString(i);
        StringBuffer out = new StringBuffer(8);

        int index;
        index = (i >> 24) & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        index = (i >> 16) & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        index = (i >> 8) & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        index = i & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        return out.toString();
    }

    public static ByteBuffer toByteBuffer(String str) {
        ByteBuffer buff = ByteBuffer.wrap(new byte[str.length() / 2]);
        byte b1;
        char c1, c2;
        for (int i = 0; i < str.length() - 1; i += 2) {
            c1 = str.charAt(i);
            c2 = str.charAt(i + 1);
            b1 = (byte) ((char2byte(c1) << 4) | (0x0f & char2byte(c2)));
            buff.put(b1);
        }
        buff.flip();
        return buff;
    }

    public static ByteBuffer toByteBuffer(final ByteBuffer dest, String str) {
        if (null == str || str.length() == 0) {
            return dest;
        }
        byte b1;
        char c1, c2;
        for (int i = 0; i < str.length() - 1; i += 2) {
            c1 = str.charAt(i);
            c2 = str.charAt(i + 1);
            b1 = (byte) ((char2byte(c1) << 4) | (0x0f & char2byte(c2)));
            dest.put(b1);
        }
        return dest;
    }

    private static byte char2byte(char c) {
        if (c >= '0' && c <= '9') {
            return (byte) (c - '0');
        }
        if (c >= 'A' && c <= 'F') {
            return (byte) (c - 'A' + 10);
        }
        if (c >= 'a' && c <= 'f') {
            return (byte) (c - 'a' + 10);
        }
        return 0;
    }
}
