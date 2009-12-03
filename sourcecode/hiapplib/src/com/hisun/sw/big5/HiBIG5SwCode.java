/*     */ package com.hisun.sw.big5;
/*     */ 
/*     */ import com.hisun.sw.HiChar;
/*     */ 
/*     */ public class HiBIG5SwCode
/*     */ {
/*     */   public static int HostToClient(byte[] buffer, byte[] temp)
/*     */   {
/*  25 */     return HostToClient(buffer, buffer.length, temp);
/*     */   }
/*     */ 
/*     */   public static int HostToClient(byte[] buffer, int len, byte[] temp)
/*     */   {
/*  38 */     int ilen = len;
/*  39 */     int olen = 0;
/*     */ 
/*  41 */     int ipos = 0;
/*  42 */     int i = 0; for (int j = 0; i < ilen; )
/*     */     {
/*  44 */       if ((buffer[i] == 1) || (buffer[i] == 2)) {
/*  45 */         temp[(j++)] = buffer[(i++)];
/*     */       }
/*     */ 
/*  49 */       if (buffer[i] == 14)
/*     */       {
/*  51 */         int icount = 1; int iend = 0; ipos = i + 1; for (int templen = 1; ipos < ilen; ) {
/*  52 */           if (buffer[ipos] == 14) {
/*  53 */             ++icount;
/*  54 */             if (icount > 1) {
/*     */               break;
/*     */             }
/*     */           }
/*  58 */           if (buffer[ipos] == 15) {
/*  59 */             --icount;
/*     */           }
/*     */ 
/*  62 */           if (buffer[ipos] == 2) {
/*  63 */             iend = 1;
/*  64 */             break;
/*     */           }
/*  66 */           ++ipos;
/*  67 */           ++templen;
/*     */         }
/*     */ 
/*  70 */         if (icount % 2 == 1) {
/*  71 */           iend = 0;
/*     */         }
/*  73 */         if (iend == 1)
/*  74 */           toGB(buffer, i, temp, j, templen);
/*     */         else {
/*  76 */           toGB2(buffer, i, temp, j, templen);
/*     */         }
/*  78 */         i += templen;
/*  79 */         j += templen;
/*     */       }
/*     */ 
/*  83 */       if ((i + 1 < ilen) && (buffer[(i + 1)] == 1)) {
/*  84 */         temp[j] = signedConvert((short)buffer[i]);
/*  85 */         ++i;
/*  86 */         ++j;
/*     */       }
/*  88 */       temp[(j++)] = bEbcdToAscii(buffer[(i++)]);
/*     */     }
/*     */ 
/*  91 */     return j;
/*     */   }
/*     */ 
/*     */   public static int ClientToHost(byte[] buffer, byte[] temp)
/*     */   {
/* 100 */     return ClientToHost(buffer, buffer.length, temp);
/*     */   }
/*     */ 
/*     */   public static int ClientToHost(byte[] buffer, int len, byte[] temp)
/*     */   {
/* 112 */     int ilen = len;
/* 113 */     int olen = 0;
/*     */ 
/* 115 */     int i = 0; for (int j = 0; i < ilen; )
/*     */     {
/* 117 */       short s = (short)(buffer[i] & 0xFF);
/* 118 */       if ((buffer[i] == 1) || (buffer[i] == 2)) {
/* 119 */         ++i;
/*     */       }
/*     */ 
/* 123 */       if (s >= 128)
/*     */       {
/* 125 */         int ipos = i + 1; for (int templen = 1; ipos < ilen; ) {
/* 126 */           if (buffer[ipos] == 2) {
/*     */             break;
/*     */           }
/*     */ 
/* 130 */           ++ipos;
/* 131 */           ++templen;
/*     */         }
/*     */ 
/* 134 */         toDBCS(buffer, i, temp, j, templen);
/* 135 */         i += templen;
/* 136 */         j += templen;
/*     */       }
/*     */ 
/* 140 */       if ((i + 1 < ilen) && (buffer[(i + 1)] == 1))
/*     */       {
/* 142 */         if (buffer[i] >= 112)
/* 143 */           temp[j] = (byte)(buffer[i] + 96);
/*     */         else {
/* 145 */           temp[j] = (byte)(buffer[i] + 144);
/*     */         }
/* 147 */         ++i;
/* 148 */         ++j;
/*     */       }
/*     */       else {
/* 151 */         temp[(j++)] = bAsciiToEbcd(buffer[(i++)]);
/*     */       }
/*     */     }
/* 154 */     return j;
/*     */   }
/*     */ 
/*     */   public static void toGB(byte[] from, int off1, byte[] to, int off2, int len)
/*     */   {
/* 159 */     int iPos = off2;
/* 160 */     int i = off1;
/* 161 */     int iLen = len + off1;
/*     */     while (true) { if (i >= iLen) break label239;
/* 163 */       if (from[i] == 15) {
/* 164 */         ++i;
/*     */       }
/*     */ 
/* 168 */       if ((from[i] == 14) && (((from[i] != 14) || (!(Unmatch0f(i + 1, iLen, from)))))) {
/*     */         break;
/*     */       }
/* 171 */       to[(iPos++)] = bEbcdToAscii(from[(i++)]);
/*     */     }
/*     */ 
/* 175 */     ++i;
/*     */     while (true) { while (true) { do do while (true) { if (i < iLen - 1);
/* 177 */               if (from[i] != 64) break;
/* 178 */               to[(iPos++)] = 32;
/* 179 */               ++i;
/*     */             }
/*     */ 
/* 182 */           while (from[i] == 15); while (from[i] == 14);
/*     */ 
/* 184 */         if ((from[(i + 1)] != 15) && (from[(i + 1)] != 14))
/*     */           break;
/* 186 */         to[(iPos++)] = 63;
/* 187 */         ++i;
/*     */       }
/*     */ 
/* 191 */       HiChar tmpChar = new HiChar();
/* 192 */       HiHost2B5.host2b5(new HiChar(from[i], from[(i + 1)]), tmpChar);
/* 193 */       to[iPos] = tmpChar.highByte;
/* 194 */       to[(iPos + 1)] = tmpChar.lowByte;
/* 195 */       i += 2;
/* 196 */       iPos += 2;
/*     */     }
/*     */ 
/* 201 */     while (iPos < iLen)
/* 202 */       label239: to[(iPos++)] = 32;
/*     */   }
/*     */ 
/*     */   public static void toGB2(byte[] from, int off1, byte[] to, int off2, int len)
/*     */   {
/* 211 */     int iPos = off2;
/* 212 */     int iLen = len;
/* 213 */     int i = off1;
/* 214 */     while (i < iLen + off1) {
/* 215 */       if (from[i] == 15) {
/* 216 */         to[(iPos++)] = 32;
/* 217 */         ++i;
/*     */       }
/*     */ 
/* 220 */       if ((from[i] != 14) || ((from[i] == 14) && (Unmatch0f(i + 1, iLen, from))))
/*     */       {
/* 223 */         to[iPos] = bEbcdToAscii(from[i]);
/* 224 */         ++i;
/* 225 */         ++iPos;
/*     */       }
/*     */ 
/* 229 */       to[(iPos++)] = 32;
/* 230 */       ++i;
/* 231 */       while (i < iLen - 1) {
/* 232 */         if (from[i] == 64) {
/* 233 */           to[(iPos++)] = 32;
/* 234 */           ++i;
/*     */         }
/*     */ 
/* 237 */         if (from[i] == 15) break; if (from[i] == 14)
/*     */           break;
/* 239 */         if ((from[(i + 1)] == 15) || (from[(i + 1)] == 14))
/*     */         {
/* 241 */           to[(iPos++)] = 63;
/* 242 */           ++i;
/* 243 */           break;
/*     */         }
/*     */ 
/* 246 */         HiChar tmpChar = new HiChar();
/* 247 */         HiHost2B5.host2b5(new HiChar(from[i], from[(i + 1)]), tmpChar);
/* 248 */         to[iPos] = tmpChar.highByte;
/* 249 */         to[(iPos + 1)] = tmpChar.lowByte;
/*     */ 
/* 251 */         i += 2;
/* 252 */         iPos += 2;
/*     */       }
/* 254 */       to[(iPos++)] = 32;
/* 255 */       ++i;
/*     */     }
/*     */ 
/* 259 */     while (i < iLen) {
/* 260 */       to[(iPos++)] = 32;
/* 261 */       ++i;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void toDBCS(byte[] from, int off1, byte[] to, int off2, int len)
/*     */   {
/* 271 */     int iLen = len;
/* 272 */     int iPos = off2;
/* 273 */     int i = off1;
/* 274 */     while (i < iLen + off1) {
/* 275 */       short s = (short)(from[i] & 0xFF);
/* 276 */       if (s < 128) {
/* 277 */         to[(iPos++)] = bAsciiToEbcd(from[(i++)]);
/*     */       }
/*     */ 
/* 284 */       if (i - off1 > iLen - 4) {
/*     */         break;
/*     */       }
/* 287 */       to[(iPos++)] = 14;
/* 288 */       while (s >= 128) {
/* 289 */         HiChar tmpChar = new HiChar();
/* 290 */         HiB52Host.b52host(new HiChar(from[i], from[(i + 1)]), tmpChar);
/*     */ 
/* 292 */         to[iPos] = tmpChar.highByte;
/* 293 */         to[(iPos + 1)] = tmpChar.lowByte;
/* 294 */         i += 2;
/* 295 */         iPos += 2;
/*     */ 
/* 297 */         s = (short)(from[i] & 0xFF);
/* 298 */         if (iPos - off1 > iLen - 3) {
/*     */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 304 */       to[(iPos++)] = 15;
/*     */     }
/*     */ 
/* 309 */     while (iPos < iLen)
/* 310 */       to[(iPos++)] = 64;
/*     */   }
/*     */ 
/*     */   public static byte bEbcdToAscii(byte bEbcd)
/*     */   {
/* 323 */     byte[] ebc_to_asc = { 32, 1, 2, 3, 0, 9, 0, 0, 0, 0, 0, 11, 12, 13, 32, 32, 16, 17, 18, 19, 0, 21, 8, 0, 24, 25, 0, 0, 28, 29, 30, 31, 0, 0, 0, 0, 0, 10, 23, 27, 0, 0, 0, 0, 0, 5, 6, 7, 0, 0, 22, 0, 0, 0, 0, 4, 0, 0, 0, 0, 20, 21, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 91, 46, 60, 40, 43, 124, 38, 32, 32, 32, 32, 32, 32, 32, 32, 32, 33, 36, 42, 41, 59, 94, 45, 47, 32, 32, 32, 32, 32, 32, 32, 32, 93, 44, 37, 95, 62, 63, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 58, 35, 64, 39, 61, 34, 32, 97, 98, 99, 100, 101, 102, 103, 104, 105, 32, 32, 32, 32, 32, 32, 32, 106, 107, 108, 109, 110, 111, 112, 113, 114, 32, 32, 32, 32, 32, 32, 32, 126, 115, 116, 117, 118, 119, 120, 121, 122, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 123, 65, 66, 67, 68, 69, 70, 71, 72, 73, 32, 32, 32, 32, 32, 32, 125, 74, 75, 76, 77, 78, 79, 80, 81, 82, 32, 32, 32, 32, 32, 32, 92, 32, 83, 84, 85, 86, 87, 88, 89, 90, 32, 32, 32, 32, 32, 32, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 32, 32, 32, 32, 32, 32 };
/*     */ 
/* 350 */     return ebc_to_asc[(bEbcd & 0xFF)];
/*     */   }
/*     */ 
/*     */   public static byte bAsciiToEbcd(byte bAscii)
/*     */   {
/* 361 */     byte[] asc_to_ebc = { 0, 1, 2, 3, 55, 45, 46, 47, 22, 5, 37, 11, 12, 13, 63, 63, 16, 17, 18, 19, 60, 61, 50, 38, 24, 25, 63, 39, 28, 29, 30, 31, 64, 90, 127, 123, 91, 108, 80, 125, 77, 93, 92, 78, 107, 96, 75, 97, -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, 122, 94, 76, 126, 110, 111, 124, -63, -62, -61, -60, -59, -58, -57, -56, -55, -47, -46, -45, -44, -43, -42, -41, -40, -39, -30, -29, -28, -27, -26, -25, -24, -23, 74, -32, 106, 95, 109, 121, -127, -126, -125, -124, -123, -122, -121, -120, -119, -111, -110, -109, -108, -107, -106, -105, -104, -103, -94, -93, -92, -91, -90, -89, -88, -87, -64, 79, -48, -95, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 74, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64 };
/*     */ 
/* 400 */     return asc_to_ebc[(bAscii & 0xFF)];
/*     */   }
/*     */ 
/*     */   private static boolean Unmatch0f(int iBeginPos, int iBufLen, byte[] buf)
/*     */   {
/* 406 */     int i = iBeginPos;
/* 407 */     while (i < iBufLen) {
/* 408 */       if (buf[i] == 14)
/* 409 */         return true;
/* 410 */       if (buf[i] == 15)
/* 411 */         return false;
/* 412 */       ++i;
/*     */     }
/*     */ 
/* 415 */     return true;
/*     */   }
/*     */ 
/*     */   public static byte signedConvert(short bFrom)
/*     */   {
/*     */     short bTo;
/* 422 */     if ((bFrom >= 240) && (bFrom <= 249))
/* 423 */       bTo = (short)(byte)(bFrom - 192);
/* 424 */     else if ((bFrom >= 192) && (bFrom <= 201))
/* 425 */       bTo = (short)(byte)(bFrom - 144);
/* 426 */     else if ((bFrom >= 208) && (bFrom <= 217))
/* 427 */       bTo = (short)(byte)(bFrom - 96);
/*     */     else {
/* 429 */       bTo = bFrom;
/*     */     }
/* 431 */     return (byte)bTo;
/*     */   }
/*     */ }