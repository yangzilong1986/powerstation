/*     */ package com.hisun.common.util;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import java.io.PrintStream;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ 
/*     */ public final class HiByteUtil
/*     */ {
/*     */   public static String toBin(int in)
/*     */   {
/*  30 */     return "0b" + toBinGeneric(in);
/*     */   }
/*     */ 
/*     */   public static String toBin(byte[] in)
/*     */   {
/*  42 */     StringBuffer tmp = new StringBuffer();
/*     */ 
/*  44 */     for (int i = 0; i < in.length; ++i) {
/*  45 */       tmp.append(toBin(in[i]) + " ");
/*     */     }
/*     */ 
/*  48 */     return "0b " + tmp.toString();
/*     */   }
/*     */ 
/*     */   private static String toBinGeneric(int in)
/*     */   {
/*  60 */     StringBuffer tmp = new StringBuffer();
/*     */ 
/*  62 */     for (int i = 0; i < 8; ++i) {
/*  63 */       tmp.insert(0, in >> i & 0x1);
/*     */     }
/*     */ 
/*  66 */     return tmp.toString();
/*     */   }
/*     */ 
/*     */   public static String toHex(byte in)
/*     */   {
/*  78 */     return "0x" + toHexGeneric(in);
/*     */   }
/*     */ 
/*     */   public static String toHex(byte[] in)
/*     */   {
/*  90 */     StringBuffer tmp = new StringBuffer("0x");
/*     */ 
/*  92 */     for (int i = 0; i < in.length; ++i) {
/*  93 */       tmp.append(toHexGeneric(in[i]));
/*     */     }
/*     */ 
/*  96 */     return tmp.toString();
/*     */   }
/*     */ 
/*     */   private static String toHexGeneric(byte in)
/*     */   {
/* 108 */     String tmp = "";
/*     */ 
/* 110 */     byte b = (byte)(in & 0xF0);
/*     */ 
/* 112 */     switch (b)
/*     */     {
/*     */     case 0:
/* 115 */       tmp = tmp + "0";
/* 116 */       break;
/*     */     case 16:
/* 119 */       tmp = tmp + "1";
/* 120 */       break;
/*     */     case 32:
/* 123 */       tmp = tmp + "2";
/* 124 */       break;
/*     */     case 48:
/* 127 */       tmp = tmp + "3";
/* 128 */       break;
/*     */     case 64:
/* 131 */       tmp = tmp + "4";
/* 132 */       break;
/*     */     case 80:
/* 135 */       tmp = tmp + "5";
/* 136 */       break;
/*     */     case 96:
/* 139 */       tmp = tmp + "6";
/* 140 */       break;
/*     */     case 112:
/* 143 */       tmp = tmp + "7";
/* 144 */       break;
/*     */     case -128:
/* 147 */       tmp = tmp + "8";
/* 148 */       break;
/*     */     case -112:
/* 151 */       tmp = tmp + "9";
/* 152 */       break;
/*     */     case -96:
/* 155 */       tmp = tmp + "a";
/* 156 */       break;
/*     */     case -80:
/* 159 */       tmp = tmp + "b";
/* 160 */       break;
/*     */     case -64:
/* 163 */       tmp = tmp + "c";
/* 164 */       break;
/*     */     case -48:
/* 167 */       tmp = tmp + "d";
/* 168 */       break;
/*     */     case -32:
/* 171 */       tmp = tmp + "e";
/* 172 */       break;
/*     */     case -16:
/* 175 */       tmp = tmp + "f";
/*     */     }
/*     */ 
/* 180 */     b = (byte)(in & 0xF);
/*     */ 
/* 182 */     switch (b)
/*     */     {
/*     */     case 0:
/* 185 */       tmp = tmp + "0";
/* 186 */       break;
/*     */     case 1:
/* 189 */       tmp = tmp + "1";
/* 190 */       break;
/*     */     case 2:
/* 193 */       tmp = tmp + "2";
/* 194 */       break;
/*     */     case 3:
/* 197 */       tmp = tmp + "3";
/* 198 */       break;
/*     */     case 4:
/* 201 */       tmp = tmp + "4";
/* 202 */       break;
/*     */     case 5:
/* 205 */       tmp = tmp + "5";
/* 206 */       break;
/*     */     case 6:
/* 209 */       tmp = tmp + "6";
/* 210 */       break;
/*     */     case 7:
/* 213 */       tmp = tmp + "7";
/* 214 */       break;
/*     */     case 8:
/* 217 */       tmp = tmp + "8";
/* 218 */       break;
/*     */     case 9:
/* 221 */       tmp = tmp + "9";
/* 222 */       break;
/*     */     case 10:
/* 225 */       tmp = tmp + "a";
/* 226 */       break;
/*     */     case 11:
/* 229 */       tmp = tmp + "b";
/* 230 */       break;
/*     */     case 12:
/* 233 */       tmp = tmp + "c";
/* 234 */       break;
/*     */     case 13:
/* 237 */       tmp = tmp + "d";
/* 238 */       break;
/*     */     case 14:
/* 241 */       tmp = tmp + "e";
/* 242 */       break;
/*     */     case 15:
/* 245 */       tmp = tmp + "f";
/*     */     }
/*     */ 
/* 250 */     return tmp;
/*     */   }
/*     */ 
/*     */   public static long toLong(byte[] input, int inputOffset)
/*     */     throws IllegalBlockSizeException
/*     */   {
/* 266 */     if (input.length - inputOffset < 8) {
/* 267 */       throw new IllegalBlockSizeException("Usable byte range is " + (input.length - inputOffset) + " bytes large, but it should be 8 bytes or larger.");
/*     */     }
/*     */ 
/* 272 */     long returnValue = 0L;
/*     */ 
/* 274 */     for (int i = inputOffset; i - inputOffset < 8; ++i) {
/* 275 */       returnValue |= (input[i] & 0xFF) << 56 - (8 * (i - inputOffset));
/*     */     }
/*     */ 
/* 278 */     return returnValue;
/*     */   }
/*     */ 
/*     */   public static int byteArrayToInt(byte[] bytes)
/*     */   {
/* 290 */     int result = 0;
/* 291 */     result |= 0xFF000000 & bytes[0] << 24;
/* 292 */     result |= 0xFF0000 & bytes[1] << 16;
/* 293 */     result |= 0xFF00 & bytes[2] << 8;
/* 294 */     result |= 0xFF & bytes[3];
/* 295 */     return result;
/*     */   }
/*     */ 
/*     */   public static long byteArrayToLong(byte[] bytes)
/*     */   {
/* 306 */     long result = 0L;
/* 307 */     result |= 0x0 & bytes[0] << 56;
/* 308 */     result |= 0x0 & bytes[1] << 48;
/* 309 */     result |= 0x0 & bytes[2] << 40;
/* 310 */     result |= 0x0 & bytes[3] << 32;
/* 311 */     result |= 0xFF000000 & bytes[4] << 24;
/* 312 */     result |= 0xFF0000 & bytes[5] << 16;
/* 313 */     result |= 0xFF00 & bytes[6] << 8;
/* 314 */     result |= 0xFF & bytes[7];
/* 315 */     return result;
/*     */   }
/*     */ 
/*     */   public static short byteArrayToShort(byte[] bytes)
/*     */   {
/* 326 */     int result = 0;
/* 327 */     result |= 0xFF00 & bytes[0] << 8;
/* 328 */     result |= 0xFF & bytes[1];
/* 329 */     return (short)result;
/*     */   }
/*     */ 
/*     */   public static byte[] intToByteArray(int valor)
/*     */   {
/* 340 */     byte[] result = new byte[4];
/* 341 */     for (int i = 0; i < result.length; ++i) {
/* 342 */       result[(3 - i)] = (byte)(valor & 0xFF);
/* 343 */       valor >>= 8;
/*     */     }
/* 345 */     return result;
/*     */   }
/*     */ 
/*     */   public static byte[] longToByteArray(long valor)
/*     */   {
/* 356 */     byte[] result = new byte[8];
/* 357 */     for (int i = 0; i < result.length; ++i) {
/* 358 */       result[(7 - i)] = (byte)(int)(valor & 0xFF);
/* 359 */       valor >>= 8;
/*     */     }
/* 361 */     return result;
/*     */   }
/*     */ 
/*     */   public static byte[] shortToByteArray(int valor)
/*     */   {
/* 372 */     byte[] result = new byte[2];
/* 373 */     for (int i = 0; i < result.length; ++i) {
/* 374 */       result[(1 - i)] = (byte)(valor & 0xFF);
/* 375 */       valor >>= 8;
/*     */     }
/* 377 */     return result;
/*     */   }
/*     */ 
/*     */   public static byte[] uudecode(String b)
/*     */   {
/* 390 */     int i = 0;
/* 391 */     StringBuffer sb = new StringBuffer();
/* 392 */     while (b.charAt(i) != ';') {
/* 393 */       sb.append(b.charAt(i));
/* 394 */       ++i;
/*     */     }
/* 396 */     ++i;
/* 397 */     int tam = Integer.parseInt(sb.toString());
/* 398 */     byte[] result = new byte[tam];
/* 399 */     int[] in = new int[4];
/* 400 */     byte[] out = new byte[3];
/* 401 */     int[] aux = new int[3];
/* 402 */     int j = 0;
/* 403 */     for (; i < b.length(); i += 4) {
/* 404 */       in[0] = (b.charAt(i) - '2');
/* 405 */       in[1] = (b.charAt(i + 1) - '2');
/* 406 */       in[2] = (b.charAt(i + 2) - '2');
/* 407 */       in[3] = (b.charAt(i + 3) - '2');
/* 408 */       aux[0] = (in[0] << 2 | (in[1] & 0x30) >> 4);
/* 409 */       aux[1] = ((in[1] & 0xF) << 4 | (in[2] & 0x3C) >> 2);
/* 410 */       aux[2] = ((in[2] & 0x3) << 6 | in[3]);
/* 411 */       for (int k = 0; k < 3; ++k) {
/* 412 */         if (j < tam) {
/* 413 */           result[(j++)] = (byte)aux[k];
/*     */         }
/*     */       }
/*     */     }
/* 417 */     return result;
/*     */   }
/*     */ 
/*     */   public static String uuencode(byte[] b)
/*     */   {
/* 432 */     StringBuffer sb = new StringBuffer(b.length * 4 / 3);
/* 433 */     sb.append(b.length);
/* 434 */     sb.append(';');
/* 435 */     byte[] in = new byte[3];
/* 436 */     char[] out = new char[4];
/* 437 */     int[] aux = new int[4];
/* 438 */     for (int i = 0; i < b.length; i += 3) {
/* 439 */       in[0] = b[i];
/* 440 */       if (i + 1 < b.length)
/* 441 */         in[1] = b[(i + 1)];
/*     */       else {
/* 443 */         in[1] = 32;
/*     */       }
/* 445 */       if (i + 2 < b.length)
/* 446 */         in[2] = b[(i + 2)];
/*     */       else {
/* 448 */         in[2] = 32;
/*     */       }
/* 450 */       aux[0] = ((in[0] & 0xFC) >> 2);
/* 451 */       aux[1] = ((in[0] & 0x3) << 4 | (in[1] & 0xF0) >> 4);
/* 452 */       aux[2] = ((in[1] & 0xF) << 2 | (in[2] & 0xC0) >> 6);
/* 453 */       aux[3] = (in[2] & 0x3F);
/* 454 */       for (int j = 0; j < 4; ++j) {
/* 455 */         out[j] = (char)(50 + aux[j]);
/*     */       }
/* 457 */       sb.append(out);
/*     */     }
/* 459 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static boolean getBit(byte[] bitMap, int index)
/*     */     throws ArrayIndexOutOfBoundsException
/*     */   {
/* 482 */     return ((bitMap[(index / 8)] & 1 << 7 - (index % 8)) != 0);
/*     */   }
/*     */ 
/*     */   public static void setBit(byte[] bitMap, int index, boolean value)
/*     */     throws ArrayIndexOutOfBoundsException
/*     */   {
/* 505 */     int i = index / 8;
/* 506 */     int mask = 1 << 7 - (index % 8);
/*     */ 
/* 508 */     if (value)
/*     */     {
/*     */       int tmp22_21 = i;
/*     */       byte[] tmp22_20 = bitMap; tmp22_20[tmp22_21] = (byte)(tmp22_20[tmp22_21] | mask);
/*     */     }
/*     */     else
/*     */     {
/*     */       int tmp34_33 = i;
/*     */       byte[] tmp34_32 = bitMap; tmp34_32[tmp34_33] = (byte)(tmp34_32[tmp34_33] & (mask ^ 0xFFFFFFFF));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static int hexDigitValue(char c)
/*     */     throws HiException
/*     */   {
/* 528 */     int retorno = 0;
/* 529 */     if ((c >= '0') && (c <= '9'))
/* 530 */       retorno = (byte)c - 48;
/* 531 */     else if ((c >= 'A') && (c <= 'F'))
/* 532 */       retorno = (byte)c - 55;
/* 533 */     else if ((c >= 'a') && (c <= 'f'))
/* 534 */       retorno = (byte)c - 87;
/*     */     else {
/* 536 */       throw new HiException();
/*     */     }
/* 538 */     return retorno;
/*     */   }
/*     */ 
/*     */   public static byte hexToByte(String hexa)
/*     */     throws HiException
/*     */   {
/* 559 */     if (hexa == null) {
/* 560 */       throw new HiException();
/*     */     }
/* 562 */     if (hexa.length() != 2) {
/* 563 */       throw new HiException();
/*     */     }
/* 565 */     byte[] b = hexa.getBytes();
/* 566 */     byte valor = (byte)(hexDigitValue((char)b[0]) * 16 + hexDigitValue((char)b[1]));
/* 567 */     return valor;
/*     */   }
/*     */ 
/*     */   public static byte[] hexToByteArray(String hexa)
/*     */     throws HiException
/*     */   {
/* 588 */     if (hexa == null) {
/* 589 */       throw new HiException();
/*     */     }
/* 591 */     if (hexa.length() % 2 != 0) {
/* 592 */       throw new HiException();
/*     */     }
/* 594 */     int tamArray = hexa.length() / 2;
/* 595 */     byte[] retorno = new byte[tamArray];
/* 596 */     for (int i = 0; i < tamArray; ++i) {
/* 597 */       retorno[i] = hexToByte(hexa.substring(i * 2, i * 2 + 2));
/*     */     }
/* 599 */     return retorno;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 609 */     byte[] bytes = { 3, 64, 65, -1 };
/*     */ 
/* 611 */     System.out.println("String = " + byteArrayToHex(bytes));
/*     */   }
/*     */ 
/*     */   public static String byteArrayToHex(byte[] bytes)
/*     */   {
/* 628 */     String retorno = "";
/* 629 */     if ((bytes == null) || (bytes.length == 0)) {
/* 630 */       return retorno;
/*     */     }
/* 632 */     for (int i = 0; i < bytes.length; ++i) {
/* 633 */       byte valor = bytes[i];
/* 634 */       int d1 = valor & 0xF;
/* 635 */       d1 += ((d1 < 10) ? 48 : 55);
/* 636 */       int d2 = (valor & 0xF0) >> 4;
/* 637 */       d2 += ((d2 < 10) ? 48 : 55);
/* 638 */       retorno = retorno + (char)d2 + (char)d1;
/*     */     }
/* 640 */     return retorno;
/*     */   }
/*     */ 
/*     */   public static String byteToHex(byte valor)
/*     */   {
/* 654 */     int d1 = valor & 0xF;
/* 655 */     d1 += ((d1 < 10) ? 48 : 55);
/* 656 */     int d2 = (valor & 0xF0) >> 4;
/* 657 */     d2 += ((d2 < 10) ? 48 : 55);
/* 658 */     String ret = "" + (char)d2 + (char)d1;
/* 659 */     return ret;
/*     */   }
/*     */ }