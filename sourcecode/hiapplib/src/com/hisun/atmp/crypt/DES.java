/*     */ package com.hisun.atmp.crypt;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class DES
/*     */ {
/*   4 */   private static final int[] IP = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };
/*     */ 
/*  10 */   private static final int[] IP_1 = { 40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25 };
/*     */ 
/*  16 */   private static final int[] PC_1 = { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4 };
/*     */ 
/*  22 */   private static final int[] PC_2 = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32 };
/*     */ 
/*  27 */   private static final int[] E = { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1 };
/*     */ 
/*  32 */   private static final int[] P = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25 };
/*     */ 
/*  36 */   private static final int[][][] S_Box = { { { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 }, { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 }, { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 }, { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } }, { { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 }, { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 }, { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 }, { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } }, { { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 }, { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 }, { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 }, { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } }, { { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 }, { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 }, { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 }, { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } }, { { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 }, { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 }, { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 }, { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } }, { { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 }, { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 }, { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 }, { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } }, { { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 }, { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 }, { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 }, { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } }, { { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 }, { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 }, { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 }, { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } } };
/*     */ 
/*  86 */   private static final int[] LeftMove = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
/*     */ 
/*     */   private byte[] UnitDes(byte[] des_key, byte[] des_data, int flag)
/*     */   {
/*  92 */     if ((des_key.length != 8) || (des_data.length != 8) || ((flag != 1) && (flag != 0)))
/*     */     {
/*  94 */       throw new RuntimeException("Data Format Error !");
/*     */     }
/*  96 */     int flags = flag;
/*     */ 
/*  98 */     int[] keydata = new int[64];
/*     */ 
/* 100 */     int[] encryptdata = new int[64];
/*     */ 
/* 102 */     byte[] EncryptCode = new byte[8];
/*     */ 
/* 104 */     int[][] KeyArray = new int[16][48];
/*     */ 
/* 106 */     keydata = ReadDataToBirnaryIntArray(des_key);
/*     */ 
/* 108 */     encryptdata = ReadDataToBirnaryIntArray(des_data);
/*     */ 
/* 110 */     KeyInitialize(keydata, KeyArray);
/*     */ 
/* 112 */     EncryptCode = Encrypt(encryptdata, flags, KeyArray);
/* 113 */     return EncryptCode;
/*     */   }
/*     */ 
/*     */   private void KeyInitialize(int[] key, int[][] keyarray)
/*     */   {
/* 120 */     int[] K0 = new int[56];
/*     */ 
/* 122 */     for (int i = 0; i < 56; ++i) {
/* 123 */       K0[i] = key[(PC_1[i] - 1)];
/*     */     }
/* 125 */     for (i = 0; i < 16; ++i) {
/* 126 */       LeftBitMove(K0, LeftMove[i]);
/*     */ 
/* 128 */       for (int j = 0; j < 48; ++j)
/* 129 */         keyarray[i][j] = K0[(PC_2[j] - 1)];
/*     */     }
/*     */   }
/*     */ 
/*     */   private byte[] Encrypt(int[] timeData, int flag, int[][] keyarray)
/*     */   {
/* 137 */     byte[] encrypt = new byte[8];
/* 138 */     int flags = flag;
/* 139 */     int[] M = new int[64];
/* 140 */     int[] MIP_1 = new int[64];
/*     */ 
/* 142 */     for (int i = 0; i < 64; ++i) {
/* 143 */       M[i] = timeData[(IP[i] - 1)];
/*     */     }
/* 145 */     if (flags == 1)
/* 146 */       for (i = 0; ; ++i) { if (i >= 16) break label117;
/* 147 */         LoopF(M, i, flags, keyarray);
/*     */       }
/* 149 */     if (flags == 0) {
/* 150 */       for (i = 15; i > -1; --i) {
/* 151 */         LoopF(M, i, flags, keyarray);
/*     */       }
/*     */     }
/* 154 */     for (i = 0; i < 64; ++i) {
/* 155 */       label117: MIP_1[i] = M[(IP_1[i] - 1)];
/*     */     }
/* 157 */     GetEncryptResultOfByteArray(MIP_1, encrypt);
/*     */ 
/* 159 */     return encrypt;
/*     */   }
/*     */ 
/*     */   private int[] ReadDataToBirnaryIntArray(byte[] intdata)
/*     */   {
/* 166 */     int[] IntDa = new int[8];
/* 167 */     for (int i = 0; i < 8; ++i) {
/* 168 */       IntDa[i] = intdata[i];
/* 169 */       if (IntDa[i] < 0) {
/* 170 */         IntDa[i] += 256;
/* 171 */         IntDa[i] %= 256;
/*     */       }
/*     */     }
/* 174 */     int[] IntVa = new int[64];
/* 175 */     for (i = 0; i < 8; ++i) {
/* 176 */       for (int j = 0; j < 8; ++j) {
/* 177 */         IntVa[(i * 8 + 7 - j)] = (IntDa[i] % 2);
/* 178 */         IntDa[i] /= 2;
/*     */       }
/*     */     }
/* 181 */     return IntVa;
/*     */   }
/*     */ 
/*     */   private void LeftBitMove(int[] k, int offset)
/*     */   {
/* 187 */     int[] c0 = new int[28];
/* 188 */     int[] d0 = new int[28];
/* 189 */     int[] c1 = new int[28];
/* 190 */     int[] d1 = new int[28];
/* 191 */     for (int i = 0; i < 28; ++i) {
/* 192 */       c0[i] = k[i];
/* 193 */       d0[i] = k[(i + 28)];
/*     */     }
/* 195 */     if (offset == 1) {
/* 196 */       for (i = 0; i < 27; ++i) {
/* 197 */         c1[i] = c0[(i + 1)];
/* 198 */         d1[i] = d0[(i + 1)];
/*     */       }
/* 200 */       c1[27] = c0[0];
/* 201 */       d1[27] = d0[0];
/* 202 */     } else if (offset == 2) {
/* 203 */       for (i = 0; i < 26; ++i) {
/* 204 */         c1[i] = c0[(i + 2)];
/* 205 */         d1[i] = d0[(i + 2)];
/*     */       }
/* 207 */       c1[26] = c0[0];
/* 208 */       d1[26] = d0[0];
/* 209 */       c1[27] = c0[1];
/* 210 */       d1[27] = d0[1];
/*     */     }
/* 212 */     for (i = 0; i < 28; ++i) {
/* 213 */       k[i] = c1[i];
/* 214 */       k[(i + 28)] = d1[i];
/*     */     }
/*     */   }
/*     */ 
/*     */   private void LoopF(int[] M, int times, int flag, int[][] keyarray)
/*     */   {
/* 221 */     int[] L0 = new int[32];
/* 222 */     int[] R0 = new int[32];
/* 223 */     int[] L1 = new int[32];
/* 224 */     int[] R1 = new int[32];
/* 225 */     int[] RE = new int[48];
/* 226 */     int[][] S = new int[8][6];
/* 227 */     int[] sBoxData = new int[8];
/* 228 */     int[] sValue = new int[32];
/* 229 */     int[] RP = new int[32];
/* 230 */     for (int i = 0; i < 32; ++i) {
/* 231 */       L0[i] = M[i];
/* 232 */       R0[i] = M[(i + 32)];
/*     */     }
/* 234 */     for (i = 0; i < 48; ++i) {
/* 235 */       RE[i] = R0[(E[i] - 1)];
/* 236 */       RE[i] += keyarray[times][i];
/* 237 */       if (RE[i] == 2) {
/* 238 */         RE[i] = 0;
/*     */       }
/*     */     }
/* 241 */     for (i = 0; i < 8; ++i) {
/* 242 */       for (int j = 0; j < 6; ++j) {
/* 243 */         S[i][j] = RE[(i * 6 + j)];
/*     */       }
/*     */ 
/* 246 */       sBoxData[i] = S_Box[i][((S[i][0] << 1) + S[i][5])][((S[i][1] << 3) + (S[i][2] << 2) + (S[i][3] << 1) + S[i][4])];
/*     */ 
/* 249 */       for (j = 0; j < 4; ++j) {
/* 250 */         sValue[(i * 4 + 3 - j)] = (sBoxData[i] % 2);
/* 251 */         sBoxData[i] /= 2;
/*     */       }
/*     */     }
/* 254 */     for (i = 0; i < 32; ++i) {
/* 255 */       RP[i] = sValue[(P[i] - 1)];
/* 256 */       L1[i] = R0[i];
/* 257 */       R1[i] = (L0[i] + RP[i]);
/* 258 */       if (R1[i] == 2) {
/* 259 */         R1[i] = 0;
/*     */       }
/*     */ 
/* 263 */       if (((flag == 0) && (times == 0)) || ((flag == 1) && (times == 15)))
/*     */       {
/* 265 */         M[i] = R1[i];
/* 266 */         M[(i + 32)] = L1[i];
/*     */       } else {
/* 268 */         M[i] = L1[i];
/* 269 */         M[(i + 32)] = R1[i];
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void GetEncryptResultOfByteArray(int[] data, byte[] value)
/*     */   {
/* 277 */     for (int i = 0; i < 8; ++i) {
/* 278 */       for (int j = 0; j < 8; ++j)
/*     */       {
/*     */         int tmp20_19 = i;
/*     */         byte[] tmp20_18 = value; tmp20_18[tmp20_19] = (byte)(tmp20_18[tmp20_19] + (data[((i << 3) + j)] << 7 - j));
/*     */       }
/*     */     }
/* 282 */     for (i = 0; i < 8; ++i)
/*     */     {
/*     */       int tmp61_60 = i;
/*     */       byte[] tmp61_59 = value; tmp61_59[tmp61_60] = (byte)(tmp61_59[tmp61_60] % 256);
/* 284 */       if (value[i] > 128)
/*     */       {
/*     */         int tmp80_79 = i;
/*     */         byte[] tmp80_78 = value; tmp80_78[tmp80_79] = (byte)(tmp80_78[tmp80_79] - 255);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private byte[] ByteDataFormat(byte[] data) {
/* 291 */     int len = data.length;
/* 292 */     int padlen = 8 - (len % 8);
/* 293 */     int newlen = len + padlen;
/* 294 */     byte[] newdata = new byte[newlen];
/* 295 */     System.arraycopy(data, 0, newdata, 0, len);
/* 296 */     for (int i = len; i < newlen; ++i)
/* 297 */       newdata[i] = (byte)padlen;
/* 298 */     return newdata;
/*     */   }
/*     */ 
/*     */   public byte[] DesEncrypt(byte[] des_key, byte[] des_data, int flag) {
/* 302 */     byte[] format_key = ByteDataFormat(des_key);
/* 303 */     byte[] format_data = ByteDataFormat(des_data);
/* 304 */     int datalen = format_data.length;
/* 305 */     int unitcount = datalen / 8;
/* 306 */     byte[] result_data = new byte[datalen];
/* 307 */     for (int i = 0; i < unitcount; ++i) {
/* 308 */       byte[] tmpkey = new byte[8];
/* 309 */       byte[] tmpdata = new byte[8];
/* 310 */       System.arraycopy(format_key, 0, tmpkey, 0, 8);
/* 311 */       System.arraycopy(format_data, i * 8, tmpdata, 0, 8);
/* 312 */       byte[] tmpresult = UnitDes(tmpkey, tmpdata, flag);
/* 313 */       System.arraycopy(tmpresult, 0, result_data, i * 8, 8);
/*     */     }
/* 315 */     return result_data; }
/*     */ 
/*     */   public static void main(String[] args) {
/* 318 */     String key = "00000000";
/* 319 */     String data = "11111111";
/* 320 */     int bytelen = data.getBytes().length;
/* 321 */     byte[] result = new byte[bytelen + 8 - (bytelen % 8)];
/* 322 */     byte[] bytekey = key.getBytes();
/* 323 */     byte[] bytedata = data.getBytes();
/* 324 */     for (int i = 0; i < bytedata.length; ++i) {
/* 325 */       System.out.print(" " + bytedata[i] + " ");
/*     */     }
/* 327 */     System.out.println();
/* 328 */     DES des = new DES();
/* 329 */     result = des.DesEncrypt(bytekey, bytedata, 1);
/* 330 */     for (int i = 0; i < result.length; ++i) {
/* 331 */       System.out.print(" " + result[i] + " ");
/*     */     }
/* 333 */     System.out.println();
/* 334 */     result = des.DesEncrypt(bytekey, result, 0);
/* 335 */     for (i = 0; i < result.length; ++i) {
/* 336 */       System.out.print(" " + result[i] + " ");
/*     */     }
/* 338 */     System.out.println();
/*     */   }
/*     */ }