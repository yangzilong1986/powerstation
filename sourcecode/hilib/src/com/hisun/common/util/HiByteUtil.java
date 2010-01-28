package com.hisun.common.util;


import com.hisun.exception.HiException;

import javax.crypto.IllegalBlockSizeException;


public final class HiByteUtil {

    public static String toBin(int in) {

        return "0b" + toBinGeneric(in);

    }


    public static String toBin(byte[] in) {

        StringBuffer tmp = new StringBuffer();


        for (int i = 0; i < in.length; ++i) {

            tmp.append(toBin(in[i]) + " ");

        }


        return "0b " + tmp.toString();

    }


    private static String toBinGeneric(int in) {

        StringBuffer tmp = new StringBuffer();


        for (int i = 0; i < 8; ++i) {

            tmp.insert(0, in >> i & 0x1);

        }


        return tmp.toString();

    }


    public static String toHex(byte in) {

        return "0x" + toHexGeneric(in);

    }


    public static String toHex(byte[] in) {

        StringBuffer tmp = new StringBuffer("0x");


        for (int i = 0; i < in.length; ++i) {

            tmp.append(toHexGeneric(in[i]));

        }


        return tmp.toString();

    }


    private static String toHexGeneric(byte in) {

        String tmp = "";


        byte b = (byte) (in & 0xF0);


        switch (b) {

            case 0:

                tmp = tmp + "0";

                break;

            case 16:

                tmp = tmp + "1";

                break;

            case 32:

                tmp = tmp + "2";

                break;

            case 48:

                tmp = tmp + "3";

                break;

            case 64:

                tmp = tmp + "4";

                break;

            case 80:

                tmp = tmp + "5";

                break;

            case 96:

                tmp = tmp + "6";

                break;

            case 112:

                tmp = tmp + "7";

                break;

            case -128:

                tmp = tmp + "8";

                break;

            case -112:

                tmp = tmp + "9";

                break;

            case -96:

                tmp = tmp + "a";

                break;

            case -80:

                tmp = tmp + "b";

                break;

            case -64:

                tmp = tmp + "c";

                break;

            case -48:

                tmp = tmp + "d";

                break;

            case -32:

                tmp = tmp + "e";

                break;

            case -16:

                tmp = tmp + "f";

        }


        b = (byte) (in & 0xF);


        switch (b) {

            case 0:

                tmp = tmp + "0";

                break;

            case 1:

                tmp = tmp + "1";

                break;

            case 2:

                tmp = tmp + "2";

                break;

            case 3:

                tmp = tmp + "3";

                break;

            case 4:

                tmp = tmp + "4";

                break;

            case 5:

                tmp = tmp + "5";

                break;

            case 6:

                tmp = tmp + "6";

                break;

            case 7:

                tmp = tmp + "7";

                break;

            case 8:

                tmp = tmp + "8";

                break;

            case 9:

                tmp = tmp + "9";

                break;

            case 10:

                tmp = tmp + "a";

                break;

            case 11:

                tmp = tmp + "b";

                break;

            case 12:

                tmp = tmp + "c";

                break;

            case 13:

                tmp = tmp + "d";

                break;

            case 14:

                tmp = tmp + "e";

                break;

            case 15:

                tmp = tmp + "f";

        }


        return tmp;

    }


    public static long toLong(byte[] input, int inputOffset) throws IllegalBlockSizeException {

        if (input.length - inputOffset < 8) {

            throw new IllegalBlockSizeException("Usable byte range is " + (input.length - inputOffset) + " bytes large, but it should be 8 bytes or larger.");

        }


        long returnValue = 0L;


        for (int i = inputOffset; i - inputOffset < 8; ++i) {

            returnValue |= (input[i] & 0xFF) << 56 - (8 * (i - inputOffset));

        }


        return returnValue;

    }


    public static int byteArrayToInt(byte[] bytes) {

        int result = 0;

        result |= 0xFF000000 & bytes[0] << 24;

        result |= 0xFF0000 & bytes[1] << 16;

        result |= 0xFF00 & bytes[2] << 8;

        result |= 0xFF & bytes[3];

        return result;

    }


    public static long byteArrayToLong(byte[] bytes) {

        long result = 0L;

        result |= 0x0 & bytes[0] << 56;

        result |= 0x0 & bytes[1] << 48;

        result |= 0x0 & bytes[2] << 40;

        result |= 0x0 & bytes[3] << 32;

        result |= 0xFF000000 & bytes[4] << 24;

        result |= 0xFF0000 & bytes[5] << 16;

        result |= 0xFF00 & bytes[6] << 8;

        result |= 0xFF & bytes[7];

        return result;

    }


    public static short byteArrayToShort(byte[] bytes) {

        int result = 0;

        result |= 0xFF00 & bytes[0] << 8;

        result |= 0xFF & bytes[1];

        return (short) result;

    }


    public static byte[] intToByteArray(int valor) {

        byte[] result = new byte[4];

        for (int i = 0; i < result.length; ++i) {

            result[(3 - i)] = (byte) (valor & 0xFF);

            valor >>= 8;

        }

        return result;

    }


    public static byte[] longToByteArray(long valor) {

        byte[] result = new byte[8];

        for (int i = 0; i < result.length; ++i) {

            result[(7 - i)] = (byte) (int) (valor & 0xFF);

            valor >>= 8;

        }

        return result;

    }


    public static byte[] shortToByteArray(int valor) {

        byte[] result = new byte[2];

        for (int i = 0; i < result.length; ++i) {

            result[(1 - i)] = (byte) (valor & 0xFF);

            valor >>= 8;

        }

        return result;

    }


    public static byte[] uudecode(String b) {

        int i = 0;

        StringBuffer sb = new StringBuffer();

        while (b.charAt(i) != ';') {

            sb.append(b.charAt(i));

            ++i;

        }

        ++i;

        int tam = Integer.parseInt(sb.toString());

        byte[] result = new byte[tam];

        int[] in = new int[4];

        byte[] out = new byte[3];

        int[] aux = new int[3];

        int j = 0;

        for (; i < b.length(); i += 4) {

            in[0] = (b.charAt(i) - '2');

            in[1] = (b.charAt(i + 1) - '2');

            in[2] = (b.charAt(i + 2) - '2');

            in[3] = (b.charAt(i + 3) - '2');

            aux[0] = (in[0] << 2 | (in[1] & 0x30) >> 4);

            aux[1] = ((in[1] & 0xF) << 4 | (in[2] & 0x3C) >> 2);

            aux[2] = ((in[2] & 0x3) << 6 | in[3]);

            for (int k = 0; k < 3; ++k) {

                if (j < tam) {

                    result[(j++)] = (byte) aux[k];

                }

            }

        }

        return result;

    }


    public static String uuencode(byte[] b) {

        StringBuffer sb = new StringBuffer(b.length * 4 / 3);

        sb.append(b.length);

        sb.append(';');

        byte[] in = new byte[3];

        char[] out = new char[4];

        int[] aux = new int[4];

        for (int i = 0; i < b.length; i += 3) {

            in[0] = b[i];

            if (i + 1 < b.length) in[1] = b[(i + 1)];

            else {

                in[1] = 32;

            }

            if (i + 2 < b.length) in[2] = b[(i + 2)];

            else {

                in[2] = 32;

            }

            aux[0] = ((in[0] & 0xFC) >> 2);

            aux[1] = ((in[0] & 0x3) << 4 | (in[1] & 0xF0) >> 4);

            aux[2] = ((in[1] & 0xF) << 2 | (in[2] & 0xC0) >> 6);

            aux[3] = (in[2] & 0x3F);

            for (int j = 0; j < 4; ++j) {

                out[j] = (char) (50 + aux[j]);

            }

            sb.append(out);

        }

        return sb.toString();

    }


    public static boolean getBit(byte[] bitMap, int index) throws ArrayIndexOutOfBoundsException {

        return ((bitMap[(index / 8)] & 1 << 7 - (index % 8)) != 0);

    }


    public static void setBit(byte[] bitMap, int index, boolean value) throws ArrayIndexOutOfBoundsException {

        int i = index / 8;

        int mask = 1 << 7 - (index % 8);


        if (value) {

            int tmp22_21 = i;

            byte[] tmp22_20 = bitMap;
            tmp22_20[tmp22_21] = (byte) (tmp22_20[tmp22_21] | mask);

        } else {

            int tmp34_33 = i;

            byte[] tmp34_32 = bitMap;
            tmp34_32[tmp34_33] = (byte) (tmp34_32[tmp34_33] & (mask ^ 0xFFFFFFFF));

        }

    }


    private static int hexDigitValue(char c) throws HiException {

        int retorno = 0;

        if ((c >= '0') && (c <= '9')) retorno = (byte) c - 48;

        else if ((c >= 'A') && (c <= 'F')) retorno = (byte) c - 55;

        else if ((c >= 'a') && (c <= 'f')) retorno = (byte) c - 87;

        else {

            throw new HiException();

        }

        return retorno;

    }


    public static byte hexToByte(String hexa) throws HiException {

        if (hexa == null) {

            throw new HiException();

        }

        if (hexa.length() != 2) {

            throw new HiException();

        }

        byte[] b = hexa.getBytes();

        byte valor = (byte) (hexDigitValue((char) b[0]) * 16 + hexDigitValue((char) b[1]));

        return valor;

    }


    public static byte[] hexToByteArray(String hexa) throws HiException {

        if (hexa == null) {

            throw new HiException();

        }

        if (hexa.length() % 2 != 0) {

            throw new HiException();

        }

        int tamArray = hexa.length() / 2;

        byte[] retorno = new byte[tamArray];

        for (int i = 0; i < tamArray; ++i) {

            retorno[i] = hexToByte(hexa.substring(i * 2, i * 2 + 2));

        }

        return retorno;

    }


    public static void main(String[] args) {

        byte[] bytes = {3, 64, 65, -1};


        System.out.println("String = " + byteArrayToHex(bytes));

    }


    public static String byteArrayToHex(byte[] bytes) {

        String retorno = "";

        if ((bytes == null) || (bytes.length == 0)) {

            return retorno;

        }

        for (int i = 0; i < bytes.length; ++i) {

            byte valor = bytes[i];

            int d1 = valor & 0xF;

            d1 += ((d1 < 10) ? 48 : 55);

            int d2 = (valor & 0xF0) >> 4;

            d2 += ((d2 < 10) ? 48 : 55);

            retorno = retorno + (char) d2 + (char) d1;

        }

        return retorno;

    }


    public static String byteToHex(byte valor) {

        int d1 = valor & 0xF;

        d1 += ((d1 < 10) ? 48 : 55);

        int d2 = (valor & 0xF0) >> 4;

        d2 += ((d2 < 10) ? 48 : 55);

        String ret = "" + (char) d2 + (char) d1;

        return ret;

    }

}