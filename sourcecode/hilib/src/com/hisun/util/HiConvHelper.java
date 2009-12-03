/*     */ package com.hisun.util;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.InvalidParameterException;
/*     */ import org.apache.commons.codec.DecoderException;
/*     */ import org.apache.commons.codec.binary.Hex;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiConvHelper
/*     */ {
/* 312 */   static final char[] ascii = "0123456789ABCDEF".toCharArray();
/*     */ 
/*     */   public static int binary2Int(String bin)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  37 */       return Integer.valueOf(bin, 2).intValue();
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/*  42 */       throw new HiException("", "错误提示信息: 待转换的bin-[" + bin + "], 包含非法二进制形式.", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String int2Binary(int val)
/*     */   {
/*  53 */     return Integer.toBinaryString(val);
/*     */   }
/*     */ 
/*     */   public static String hex2Binary(String val)
/*     */     throws HiException
/*     */   {
/*  63 */     StringBuffer ret = new StringBuffer();
/*     */ 
/*  65 */     for (int i = 0; i < val.length(); ++i)
/*     */     {
/*  67 */       ret.append(hex2binary(val.charAt(i)));
/*     */     }
/*  69 */     return ret.toString();
/*     */   }
/*     */ 
/*     */   public static String hex2binary(char hex) throws HiException
/*     */   {
/*  74 */     switch (hex) { case '0':
/*  77 */       return "0000";
/*     */     case '1':
/*  79 */       return "0001";
/*     */     case '2':
/*  81 */       return "0010";
/*     */     case '3':
/*  83 */       return "0011";
/*     */     case '4':
/*  85 */       return "0100";
/*     */     case '5':
/*  87 */       return "0101";
/*     */     case '6':
/*  89 */       return "0110";
/*     */     case '7':
/*  91 */       return "0111";
/*     */     case '8':
/*  93 */       return "1000";
/*     */     case '9':
/*  95 */       return "1001";
/*     */     case 'A':
/*     */     case 'a':
/*  98 */       return "1010";
/*     */     case 'B':
/*     */     case 'b':
/* 101 */       return "1011";
/*     */     case 'C':
/*     */     case 'c':
/* 104 */       return "1100";
/*     */     case 'D':
/*     */     case 'd':
/* 107 */       return "1101";
/*     */     case 'E':
/*     */     case 'e':
/* 110 */       return "1110";
/*     */     case 'F':
/*     */     case 'f':
/* 113 */       return "1111";
/*     */     case ':':
/*     */     case ';':
/*     */     case '<':
/*     */     case '=':
/*     */     case '>':
/*     */     case '?':
/*     */     case '@':
/*     */     case 'G':
/*     */     case 'H':
/*     */     case 'I':
/*     */     case 'J':
/*     */     case 'K':
/*     */     case 'L':
/*     */     case 'M':
/*     */     case 'N':
/*     */     case 'O':
/*     */     case 'P':
/*     */     case 'Q':
/*     */     case 'R':
/*     */     case 'S':
/*     */     case 'T':
/*     */     case 'U':
/*     */     case 'V':
/*     */     case 'W':
/*     */     case 'X':
/*     */     case 'Y':
/*     */     case 'Z':
/*     */     case '[':
/*     */     case '\\':
/*     */     case ']':
/*     */     case '^':
/*     */     case '_':
/*     */     case '`': } throw new HiException("", "十六进制转为二进制时, 有非法十六制字符:" + hex);
/*     */   }
/*     */ 
/*     */   public static String binary2hex(String binary)
/*     */   {
/* 126 */     String hexString = "";
/* 127 */     int binLen = binary.length();
/* 128 */     if (binLen % 4 != 0)
/*     */     {
/* 130 */       binary = StringUtils.repeat("0", 4 - (binLen % 4)) + binary;
/* 131 */       binLen = binary.length();
/*     */     }
/* 133 */     for (int i = 0; i < binLen; i += 4)
/*     */     {
/* 135 */       hexString = hexString + Integer.toHexString(Integer.valueOf(binary.substring(i, i + 4), 2).intValue());
/*     */     }
/* 137 */     return hexString;
/*     */   }
/*     */ 
/*     */   public static String byte2String(byte val) throws HiException
/*     */   {
/* 142 */     byte[] arrVal = { val };
/* 143 */     return byte2String(arrVal, "ISO-8859-1");
/*     */   }
/*     */ 
/*     */   public static String byte2String(byte[] val, String charset) throws HiException
/*     */   {
/*     */     try {
/* 149 */       return new String(val, charset);
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/* 153 */       throw new HiException("", "不支持指定的编码转换", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String binToAscStr(String binStr)
/*     */     throws HiException
/*     */   {
/* 170 */     return binToAscStr(binStr.getBytes());
/*     */   }
/*     */ 
/*     */   public static String binToAscStr(byte[] binBuf)
/*     */   {
/* 182 */     long ascVal = 0L;
/* 183 */     for (int i = 0; i < binBuf.length; ++i)
/*     */     {
/* 185 */       ascVal = (ascVal << 8) + (binBuf[i] & 0xFF);
/*     */     }
/*     */ 
/* 188 */     return String.valueOf(ascVal);
/*     */   }
/*     */ 
/*     */   public static String asc2bin(String strAsc)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 201 */       Integer deliInt = Integer.valueOf(strAsc);
/* 202 */       if ((deliInt.intValue() > 255) || (deliInt.intValue() < -128))
/*     */       {
/* 204 */         throw new HiException("EN0010", "转换为bin有误, asc=[" + strAsc + "]");
/*     */       }
/*     */ 
/* 207 */       byte[] asc = { deliInt.byteValue() };
/* 208 */       return new String(asc, "ISO-8859-1");
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/* 215 */       throw new HiException("", "asc2bin执行出错, 一个ASCII值 + [" + strAsc + "],转为对应的字符时失败.", e);
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/* 220 */       throw new HiException("", "asc2bin执行出错, 不支持相应编码集.", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String asc2bin(int intAsc)
/*     */     throws HiException
/*     */   {
/* 239 */     byte[] aryAsc = new byte[4];
/*     */ 
/* 241 */     int2byte(aryAsc, 0, intAsc);
/*     */ 
/* 256 */     return byte2String(aryAsc, "ISO-8859-1");
/*     */   }
/*     */ 
/*     */   public static String asc2bin(int intAsc, int binLen)
/*     */     throws HiException
/*     */   {
/* 269 */     byte[] aryAsc = new byte[4];
/*     */ 
/* 271 */     int2byte(aryAsc, 0, intAsc);
/* 272 */     String retStr = "";
/*     */ 
/* 274 */     boolean start = true;
/* 275 */     for (int i = 0; i < 4; ++i)
/*     */     {
/* 277 */       if ((aryAsc[i] == 0) && (start)) {
/*     */         continue;
/*     */       }
/*     */ 
/* 281 */       start = false;
/* 282 */       retStr = retStr + (char)aryAsc[i];
/*     */     }
/*     */ 
/* 285 */     if (retStr.length() < binLen)
/*     */     {
/* 287 */       char fill_asc = '\0';
/* 288 */       String fill_str = String.valueOf(fill_asc);
/*     */ 
/* 290 */       retStr = StringUtils.repeat(fill_str, binLen - retStr.length()) + retStr;
/*     */     }
/* 292 */     else if (retStr.length() > binLen)
/*     */     {
/* 294 */       throw new HiException("CO0010", "ascii2bin, 超过指定的宽度 " + binLen);
/*     */     }
/*     */ 
/* 297 */     return retStr;
/*     */   }
/*     */ 
/*     */   public static String bcd2AscStr(byte[] bytes)
/*     */   {
/* 304 */     return ascii2Str(bcd2Ascii(bytes));
/*     */   }
/*     */ 
/*     */   public static byte[] ascStr2Bcd(String s)
/*     */   {
/* 309 */     return ascii2Bcd(str2Ascii(s));
/*     */   }
/*     */ 
/*     */   public static byte[] bcd2Ascii(byte[] bytes)
/*     */   {
/* 315 */     byte[] temp = new byte[bytes.length * 2];
/*     */ 
/* 317 */     for (int i = 0; i < bytes.length; ++i)
/*     */     {
/* 319 */       temp[(i * 2)] = (byte)(bytes[i] >> 4 & 0xF);
/* 320 */       temp[(i * 2 + 1)] = (byte)(bytes[i] & 0xF);
/*     */     }
/*     */ 
/* 323 */     return temp;
/*     */   }
/*     */ 
/*     */   public static byte[] str2Ascii(String s)
/*     */   {
/* 328 */     byte[] str = s.toUpperCase().getBytes();
/* 329 */     byte[] ascii = new byte[str.length];
/* 330 */     for (int i = 0; i < ascii.length; ++i)
/*     */     {
/* 332 */       ascii[i] = (byte)asciiValue(str[i]);
/*     */     }
/* 334 */     return ascii;
/*     */   }
/*     */ 
/*     */   public static String ascii2Str(byte[] ascii)
/*     */   {
/* 339 */     StringBuffer res = new StringBuffer();
/* 340 */     for (int i = 0; i < ascii.length; ++i)
/*     */     {
/* 342 */       res.append(strValue(ascii[i]));
/*     */     }
/* 344 */     return res.toString();
/*     */   }
/*     */ 
/*     */   private static char strValue(byte asc)
/*     */   {
/* 349 */     if ((asc < 0) || (asc > 15))
/* 350 */       throw new InvalidParameterException();
/* 351 */     return ascii[asc];
/*     */   }
/*     */ 
/*     */   public static byte[] ascii2Bcd(byte[] asc)
/*     */   {
/* 356 */     int len = asc.length / 2;
/* 357 */     byte[] bcd = new byte[len];
/* 358 */     for (int i = 0; i < len; ++i)
/*     */     {
/* 360 */       bcd[i] = (byte)(asc[(2 * i)] << 4 | asc[(2 * i + 1)]);
/*     */     }
/* 362 */     return bcd;
/*     */   }
/*     */ 
/*     */   private static int asciiValue(byte b)
/*     */   {
/* 367 */     if ((b >= 48) && (b <= 57))
/*     */     {
/* 369 */       return (b - 48);
/*     */     }
/* 371 */     if ((b >= 97) && (b <= 102))
/*     */     {
/* 373 */       return (b - 97 + 10);
/*     */     }
/* 375 */     if ((b >= 65) && (b <= 70))
/*     */     {
/* 377 */       return (b - 65 + 10);
/*     */     }
/*     */ 
/* 380 */     throw new InvalidParameterException();
/*     */   }
/*     */ 
/*     */   public static void printByte(byte[] b)
/*     */   {
/* 385 */     for (int i = 0; i < b.length; ++i)
/*     */     {
/* 387 */       System.out.print(b[i] + " ");
/*     */     }
/* 389 */     System.out.println();
/*     */   }
/*     */ 
/*     */   public static short byte2short(byte[] bp, int index)
/*     */   {
/* 408 */     return (short)(((bp[index] & 0xFF) << 8) + (bp[(index + 1)] & 0xFF));
/*     */   }
/*     */ 
/*     */   public static int byte2int(byte[] bp, int index)
/*     */   {
/* 418 */     return (((bp[index] & 0xFF) << 24) + ((bp[(index + 1)] & 0xFF) << 16) + ((bp[(index + 2)] & 0xFF) << 8) + (bp[(index + 3)] & 0xFF));
/*     */   }
/*     */ 
/*     */   public static void short2byte(byte[] bp, int index, short value)
/*     */   {
/* 429 */     bp[index] = (byte)(value >> 8 & 0xFF);
/* 430 */     bp[(index + 1)] = (byte)(value & 0xFF);
/*     */   }
/*     */ 
/*     */   public static void int2byte(byte[] bp, int index, int value)
/*     */   {
/* 438 */     bp[index] = (byte)(value >> 24 & 0xFF);
/* 439 */     bp[(index + 1)] = (byte)(value >> 16 & 0xFF);
/* 440 */     bp[(index + 2)] = (byte)(value >> 8 & 0xFF);
/* 441 */     bp[(index + 3)] = (byte)(value & 0xFF);
/*     */   }
/*     */ 
/*     */   public static long int2uint(int x)
/*     */   {
/* 450 */     return (x << 32 >>> 32);
/*     */   }
/*     */ 
/*     */   public static long byte2uint(byte[] x, int offs)
/*     */   {
/* 458 */     long z = 0L;
/* 459 */     for (int i = 0; i < 4; ++i)
/*     */     {
/* 461 */       z = (z << 8) + (x[(offs + i)] & 0xFF);
/*     */     }
/* 463 */     return z;
/*     */   }
/*     */ 
/*     */   public static byte[] uint2byte(long[] x)
/*     */   {
/* 471 */     byte[] res = new byte[8];
/* 472 */     int2byte(res, 0, (int)x[0]);
/* 473 */     int2byte(res, 4, (int)x[1]);
/* 474 */     return res;
/*     */   }
/*     */ 
/*     */   public static byte[] long2byte(long x)
/*     */   {
/* 482 */     byte[] res = new byte[8];
/* 483 */     int2byte(res, 0, (int)(x >> 32 & 0xFFFFFFFF));
/* 484 */     int2byte(res, 4, (int)(x & 0xFFFFFFFF));
/* 485 */     return res;
/*     */   }
/*     */ 
/*     */   public static long byte2long(byte[] msg, int offs)
/*     */   {
/* 490 */     long high = byte2uint(msg, offs);
/* 491 */     offs += 4;
/* 492 */     long low = byte2uint(msg, offs);
/* 493 */     offs += 4;
/* 494 */     long ans = (high << 32) + low;
/* 495 */     return ans;
/*     */   }
/*     */ 
/*     */   public static String boolean2String(boolean[] ba)
/*     */   {
/* 502 */     StringBuffer strb = new StringBuffer();
/* 503 */     int cnt = 0;
/*     */ 
/* 505 */     if ((ba == null) || (ba.length == 0))
/*     */     {
/* 507 */       return "(none)";
/*     */     }
/*     */ 
/* 510 */     for (int i = 0; i < ba.length; ++i)
/*     */     {
/* 512 */       if (ba[i] == 0)
/*     */         continue;
/* 514 */       if (cnt++ != 0)
/*     */       {
/* 516 */         strb.append("+");
/*     */       }
/* 518 */       strb.append(i);
/*     */     }
/*     */ 
/* 521 */     return strb.toString();
/*     */   }
/*     */ 
/*     */   public static String convFlags(String equiv, byte flags)
/*     */   {
/* 526 */     char[] chs = new char[8];
/* 527 */     StringBuffer strb = new StringBuffer(" ");
/*     */ 
/* 530 */     if (equiv.length() > 8)
/*     */     {
/* 532 */       return ">8?";
/*     */     }
/* 534 */     equiv.getChars(0, equiv.length(), chs, 0);
/*     */ 
/* 536 */     int bit = 128; for (int i = 0; bit != 0; ++i)
/*     */     {
/* 538 */       if ((flags & bit) != 0)
/*     */       {
/* 540 */         strb.setCharAt(0, '*');
/* 541 */         strb.append(chs[i]);
/*     */       }
/* 536 */       bit >>= 1;
/*     */     }
/*     */ 
/* 544 */     return strb.toString();
/*     */   }
/*     */ 
/*     */   public static String timer2string(long time)
/*     */   {
/* 549 */     String timeString = null;
/*     */ 
/* 551 */     long msec = time % 1000L;
/* 552 */     String ms = String.valueOf(msec);
/* 553 */     ms = fill(ms, 3, "0");
/*     */ 
/* 555 */     long rem = time / 1000L;
/* 556 */     int xsec = (int)(rem % 60L);
/* 557 */     rem = (int)((rem - xsec) / 60L);
/* 558 */     int xmin = (int)(rem % 60L);
/* 559 */     rem = (int)((rem - xmin) / 60L);
/* 560 */     int xhour = (int)(rem % 24L);
/* 561 */     int xday = (int)((rem - xhour) / 24L);
/*     */ 
/* 563 */     String sday = String.valueOf(xday);
/* 564 */     String shour = String.valueOf(xhour);
/* 565 */     shour = fill(shour, 2, "0");
/* 566 */     String smin = String.valueOf(xmin);
/* 567 */     smin = fill(smin, 2, "0");
/* 568 */     String ssec = String.valueOf(xsec);
/* 569 */     ssec = fill(ssec, 2, "0");
/*     */ 
/* 571 */     timeString = sday + " days, " + shour + ":" + smin + ":" + ssec + "." + ms;
/* 572 */     return timeString;
/*     */   }
/*     */ 
/*     */   private static String fill(String str, int sz, String cfill)
/*     */   {
/* 577 */     while (str.length() < sz)
/*     */     {
/* 579 */       str = cfill + str;
/*     */     }
/* 581 */     return str;
/*     */   }
/*     */ 
/*     */   public static byte[] ascByte2Bcd(byte[] bytes)
/*     */     throws HiException
/*     */   {
/* 593 */     Hex hex = new Hex();
/*     */     try {
/* 595 */       bytes = hex.decode(bytes);
/* 596 */       hex = null;
/*     */     }
/*     */     catch (DecoderException e) {
/* 599 */       throw new HiException(e);
/*     */     }
/*     */ 
/* 602 */     return bytes;
/*     */   }
/*     */ 
/*     */   public static byte[] bcd2AscByte(byte[] bytes)
/*     */   {
/* 611 */     Hex hex = new Hex();
/* 612 */     bytes = hex.encode(bytes);
/* 613 */     hex = null;
/* 614 */     return bytes;
/*     */   }
/*     */ }