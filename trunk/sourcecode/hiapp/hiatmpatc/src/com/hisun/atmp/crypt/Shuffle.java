package com.hisun.atmp.crypt;

public class Shuffle {
    public static final int TRANKEY_LEN = 8;
    public static final char[] mask = "19F2C8276AD0839B".toCharArray();
    public static final char[] step = "35827629".toCharArray();
    public static final char[] keymask = "BA987D40".toCharArray();

    static int t_xtoi(int x) {
        return ((x >= 65) ? x - 65 + 10 : x - 48);
    }

    public static int shuffle(char[] inbuf, char[] key, char[] outbuf, int len) {
        int c = 0;

        for (int i = 0; i < len; ++i) {
            int j;
            int k;
            if (i < 8) {
                j = inbuf[(len - i - 1)] - '0' + key[(7 - i)] - '0' + c;
                c = j / 10;
            } else if (i == 8) {
                j = inbuf[(len - i - 1)] - '0' + c;
                c = j / 10;
            } else if (i == 9) {
                j = inbuf[(len - i - 1)] - '0' + c;
            } else {
                j = inbuf[(len - i - 1)] - '0';
            }
            j %= 10;
            if (i < 16) k = t_xtoi(mask[(15 - i)]);
            else k = 0;
            outbuf[(len - i - 1)] = (char) (j ^ k + 48);
        }

        return 0;
    }

    public static int unshuffle(char[] inbuf, char[] key, char[] outbuf, int len) {
        int j;
        int c = 0;

        for (int i = 0; i < len; ++i) {
            int k;
            j = inbuf[(len - i - 1)] - '0';
            if (i < 16) k = t_xtoi(mask[(15 - i)]);
            else k = 0;
            inbuf[(len - i - 1)] = (char) (j ^ k + 48);
        }
        for (i = 0; i < len; ++i) {
            if (i < 8) {
                j = inbuf[(len - i - 1)] - '0' - (key[(7 - i)] - '0') + c;
                if (j < 0) {
                    j += 10;
                    c = -1;
                } else {
                    c = 0;
                }
            } else if (i == 8) {
                j = inbuf[(len - i - 1)] - '0' + c;
                if (j < 0) {
                    j += 10;
                    c = -1;
                } else {
                    c = 0;
                }
            } else if (i == 9) {
                j = inbuf[(len - i - 1)] - '0' + c;
                if (j < 0) j += 10;
            } else {
                j = inbuf[(len - i - 1)] - '0';
            }
            outbuf[(len - i - 1)] = (char) (j + 48);
        }
        return 0;
    }

    public static void updatetrm(char[] key) {
        int c = 0;

        for (int i = 0; i < 8; ++i) {
            int j = key[(8 - i - 1)] - '0' + step[(7 - i)] - '0' + c;
            c = j / 10;
            j %= 10;

            key[(8 - i - 1)] = (char) (j + 48);
        }
    }

    public static void shufkey(char[] pkey) {
        for (int i = 0; i < 8; ++i)
            pkey[i] = (char) ((pkey[i] - '0' ^ t_xtoi(keymask[i])) + 48);
    }

    public static void strToBin(char[] Outpuf, char[] Input, int num) {
        for (int i = 0; i < num; ++i)
            Outpuf[i] = (char) ((Input[(i << 1)] - '0' << 4) + Input[((i << 1) + 1)] - '0');
    }

    public static void binToStr(char[] Output, char[] Input, int num) {
        for (int i = 0; i < num; ++i) {
            Output[(i << 1)] = (char) (((Input[i] & 0xF0) >> '\4') + 48);
            Output[((i << 1) + 1)] = (char) ((Input[i] & 0xF) + '0');
        }
    }

    public static void strToBCD(char[] Outpuf, char[] Input, int num) {
        for (int i = 0; i < num; ++i)
            Outpuf[i] = (char) ((t_xtoi(Input[(i << 1)]) << 4) + t_xtoi(Input[((i << 1) + 1)]));
    }

    public static void bcdToStr(char[] Output, char[] Input, int num) {
        for (int i = 0; i < num; ++i) {
            Output[(i << 1)] = bin2char((Input[i] & 0xF0) >> '\4');
            Output[((i << 1) + 1)] = bin2char(Input[i] & 0xF);
        }
    }

    private static char bin2char(int bin) {
        return (char) ((bin < 10) ? bin + 48 : bin - 10 + 65);
    }
}