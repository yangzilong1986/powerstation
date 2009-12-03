/*     */ package com.hisun.sw.gbk;
/*     */ 
/*     */ import com.hisun.sw.HiChar;
/*     */ 
/*     */ public class HiGBSwCode
/*     */ {
/* 351 */   static final int[] table = { 17220, 41378, 17217, 41379, 17221, 41380, 17733, 41381, 17734, 41382, 17760, 41383, 17499, 41384, 17501, 41385, 17482, 41386, 17313, 41387, 17532, 41388, 17535, 41389, 17505, 41390, 17521, 41391, 17506, 41392, 17522, 41393, 17507, 41394, 17523, 41395, 17508, 41396, 17524, 41397, 17509, 41398, 17525, 41399, 17218, 41400, 17218, 41400, 17219, 41401, 17474, 41402, 17475, 41403, 17755, 41404, 17756, 41405, 17510, 41406, 17526, 41407, 17483, 41408, 17530, 41409, 17531, 41410, 17762, 41411, 17763, 41412, 17764, 41413, 17765, 41414, 17766, 41415, 17767, 41416, 17768, 41417, 17769, 41418, 17770, 41419, 17771, 41420, 17772, 41421, 17773, 41422, 17774, 41423, 17775, 41424, 17776, 41425, 17777, 41426, 17778, 41427, 17779, 41428, 17780, 41429, 17781, 41430, 17782, 41431, 17783, 41432, 17484, 41433, 17785, 41434, 17786, 41435, 17511, 41436, 17527, 41437, 17485, 41438, 17528, 41439, 17512, 41440, 17513, 41441, 17529, 41442, 17645, 41443, 17646, 41444, 17647, 41445, 17486, 41446, 17120, 41447, 17800, 41448, 17226, 41449, 16970, 41450, 17803, 41451, 17514, 41452, 17518, 41453, 17637, 41454, 17638, 41455, 17632, 41456, 17633, 41457, 17636, 41458, 17639, 41459, 17640, 41460, 17641, 41461, 17642, 41462, 17634, 41463, 17635, 41464, 17515, 41465, 17648, 41466, 17649, 41467, 17650, 41468, 17651, 41469, 17533, 41470, 16986, 41889, 17023, 41890, 17019, 41891, 16987, 41892, 17004, 41893, 16976, 41894, 17488, 41895, 16973, 41896, 16989, 41897, 16988, 41898, 16974, 41899, 17003, 41900, 16992, 41901, 16971, 41902, 16993, 41903, 17018, 41914, 16990, 41915, 16972, 41916, 17022, 41917, 17006, 41918, 17007, 41919, 17020, 41920, 17476, 41947, 17389, 41948, 17477, 41949, 17520, 41950, 17005, 41951, 17017, 41952, 17088, 41979, 16975, 41980, 17104, 41981, 17057, 41982, 17479, 42145, 17537, 42146, 17480, 42147, 17538, 42148, 17481, 42149, 17539, 42150, 17489, 42151, 17540, 42152, 17490, 42153, 17541, 42154, 17542, 42155, 17600, 42156, 17543, 42157, 17601, 42158, 17544, 42159, 17602, 42160, 17545, 42161, 17603, 42162, 17546, 42163, 17604, 42164, 17548, 42165, 17605, 42166, 17549, 42167, 17606, 42168, 17550, 42169, 17607, 42170, 17551, 42171, 17608, 42172, 17552, 42173, 17609, 42174, 17553, 42175, 17610, 42176, 17554, 42177, 17611, 42178, 17494, 42179, 17555, 42180, 17612, 42181, 17556, 42182, 17613, 42183, 17557, 42184, 17614, 42185, 17558, 42186, 17559, 42187, 17560, 42188, 17561, 42189, 17562, 42190, 17565, 42191, 17615, 42192, 17621, 42193, 17566, 42194, 17616, 42195, 17622, 42196, 17567, 42197, 17617, 42198, 17623, 42199, 17570, 42200, 17618, 42201, 17624, 42202, 17571, 42203, 17619, 42204, 17625, 42205, 17572, 42206, 17573, 42207, 17574, 42208, 17575, 42209, 17576, 42210, 17491, 42211, 17577, 42212, 17492, 42213, 17578, 42214, 17493, 42215, 17580, 42216, 17581, 42217, 17582, 42218, 17583, 42219, 17594, 42220, 17595, 42221, 17495, 42222, 17596, 42223, 17626, 42224, 17627, 42225, 17478, 42226, 17597, 42227, 17223, 42401, 17281, 42402, 17224, 42403, 17282, 42404, 17225, 42405, 17283, 42406, 17233, 42407, 17284, 42408, 17234, 42409, 17285, 42410, 17286, 42411, 17344, 42412, 17287, 42413, 17345, 42414, 17288, 42415, 17346, 42416, 17289, 42417, 17347, 42418, 17290, 42419, 17348, 42420, 17292, 42421, 17349, 42422, 17293, 42423, 17350, 42424, 17294, 42425, 17351, 42426, 17295, 42427, 17352, 42428, 17296, 42429, 17353, 42430, 17297, 42431, 17354, 42432, 17298, 42433, 17355, 42434, 17238, 42435, 17299, 42436, 17356, 42437, 17300, 42438, 17357, 42439, 17301, 42440, 17358, 42441, 17302, 42442, 17303, 42443, 17304, 42444, 17305, 42445, 17306, 42446, 17309, 42447, 17359, 42448, 17365, 42449, 17310, 42450, 17360, 42451, 17366, 42452, 17311, 42453, 17361, 42454, 17367, 42455, 17314, 42456, 17362, 42457, 17368, 42458, 17315, 42459, 17363, 42460, 17369, 42461, 17316, 42462, 17317, 42463, 17318, 42464, 17319, 42465, 17320, 42466, 17235, 42467, 17321, 42468, 17236, 42469, 17322, 42470, 17237, 42471, 17324, 42472, 17325, 42473, 17326, 42474, 17327, 42475, 17338, 42476, 17339, 42477, 17239, 42478, 17340, 42479, 17370, 42480, 17371, 42481, 17222, 42482, 17341, 42483, 17364, 42484, 17241, 42485, 17242, 42486 };
/*     */ 
/*     */   public static int HostToClient(byte[] buffer, byte[] temp)
/*     */   {
/*  29 */     return HostToClient(buffer, buffer.length, temp);
/*     */   }
/*     */ 
/*     */   public static int HostToClient(byte[] buffer, int len, byte[] temp)
/*     */   {
/*  47 */     int ilen = len;
/*  48 */     int olen = 0;
/*     */ 
/*  50 */     int ipos = 0;
/*  51 */     int i = 0; for (int j = 0; i < ilen; )
/*     */     {
/*  53 */       if ((buffer[i] == 1) || (buffer[i] == 2)) {
/*  54 */         temp[(j++)] = buffer[(i++)];
/*     */       }
/*     */ 
/*  58 */       if (buffer[i] == 14)
/*     */       {
/*  61 */         int icount = 1; int iend = 0; ipos = i + 1; for (int templen = 1; ipos < ilen; ) {
/*  62 */           if (buffer[ipos] == 14) {
/*  63 */             ++icount;
/*  64 */             if (icount > 1) {
/*     */               break;
/*     */             }
/*     */           }
/*  68 */           if (buffer[ipos] == 15) {
/*  69 */             --icount;
/*     */           }
/*     */ 
/*  72 */           if (buffer[ipos] == 2) {
/*  73 */             iend = 1;
/*  74 */             break;
/*     */           }
/*  76 */           ++ipos;
/*  77 */           ++templen;
/*     */         }
/*     */ 
/*  80 */         if (icount % 2 == 1) {
/*  81 */           iend = 0;
/*     */         }
/*  83 */         if (iend == 1)
/*     */         {
/*  85 */           ToGb(buffer, i, temp, j, templen);
/*     */         }
/*     */         else {
/*  88 */           ToGb2(buffer, i, temp, j, templen);
/*     */         }
/*  90 */         i += templen;
/*  91 */         j += templen;
/*     */       }
/*     */ 
/*  95 */       if ((i + 1 < ilen) && (buffer[(i + 1)] == 1)) {
/*  96 */         temp[j] = signedConvert((short)buffer[i]);
/*  97 */         ++i;
/*  98 */         ++j;
/*     */       }
/*     */ 
/* 101 */       temp[(j++)] = bEbcdToAscii(buffer[(i++)]);
/*     */     }
/*     */ 
/* 104 */     return j;
/*     */   }
/*     */ 
/*     */   public static int ClientToHost(byte[] buffer, byte[] temp)
/*     */   {
/* 117 */     return ClientToHost(buffer, buffer.length, temp);
/*     */   }
/*     */ 
/*     */   public static int ClientToHost(byte[] buffer, int len, byte[] temp)
/*     */   {
/* 133 */     int ilen = len;
/* 134 */     int olen = 0;
/*     */ 
/* 136 */     int i = 0; for (int j = 0; i < ilen; )
/*     */     {
/* 138 */       if ((buffer[i] == 1) || (buffer[i] == 2)) {
/* 139 */         ++i;
/*     */       }
/*     */ 
/* 143 */       if ((buffer[i] & 0xFF) >= 128)
/*     */       {
/* 146 */         int ipos = i + 1; for (int templen = 1; ipos < ilen; ) {
/* 147 */           if (buffer[ipos] == 2) {
/*     */             break;
/*     */           }
/*     */ 
/* 151 */           ++ipos;
/* 152 */           ++templen;
/*     */         }
/*     */ 
/* 155 */         ToDbcs(buffer, i, temp, j, templen);
/* 156 */         i += templen;
/* 157 */         j += templen;
/*     */       }
/*     */ 
/* 161 */       if ((i + 1 < ilen) && (buffer[(i + 1)] == 1))
/*     */       {
/* 163 */         if (buffer[i] >= 112)
/* 164 */           temp[j] = (byte)((buffer[i] & 0xFF) + 96);
/*     */         else {
/* 166 */           temp[j] = (byte)((buffer[i] & 0xFF) + 144);
/*     */         }
/* 168 */         ++i;
/* 169 */         ++j;
/*     */       }
/*     */ 
/* 172 */       temp[(j++)] = bAsciiToEbcd(buffer[(i++)]);
/*     */     }
/*     */ 
/* 175 */     return j;
/*     */   }
/*     */ 
/*     */   public static void ToGb(byte[] pchFrom, int off1, byte[] pchTo, int off2, int len)
/*     */   {
/* 183 */     int iPos = off2;
/* 184 */     int i = off1;
/* 185 */     int iLen = len + off1;
/*     */     while (true) {
/* 187 */       if (i >= iLen) break label279;
/* 188 */       if (pchFrom[i] == 15) {
/* 189 */         ++i;
/*     */       }
/*     */ 
/* 192 */       if ((pchFrom[i] == 14) && (((pchFrom[i] != 14) || (!(Unmatch0f(i + 1, iLen, pchFrom))))))
/*     */         break;
/* 194 */       if ((i + 1 < iLen) && (pchFrom[(i + 1)] == 1)) {
/* 195 */         pchTo[(iPos++)] = signedConvert((short)pchFrom[(i++)]);
/*     */       }
/*     */ 
/* 198 */       pchTo[(iPos++)] = bEbcdToAscii(pchFrom[(i++)]);
/*     */     }
/*     */ 
/* 203 */     ++i;
/*     */     while (true) { while (true) { do do while (true) { if (i < iLen - 1);
/* 205 */               if (pchFrom[i] != 64) break;
/* 206 */               pchTo[(iPos++)] = 32;
/* 207 */               ++i;
/*     */             }
/*     */ 
/* 210 */           while (pchFrom[i] == 15); while (pchFrom[i] == 14);
/*     */ 
/* 212 */         if ((pchFrom[(i + 1)] != 15) && (pchFrom[(i + 1)] != 14)) break;
/* 213 */         pchTo[(iPos++)] = 63;
/* 214 */         ++i;
/*     */       }
/*     */ 
/* 217 */       HiChar tmpChar = new HiChar();
/* 218 */       DbcsToGb(new HiChar(pchFrom[i], pchFrom[(i + 1)]), tmpChar);
/* 219 */       pchTo[iPos] = tmpChar.highByte;
/* 220 */       pchTo[(iPos + 1)] = tmpChar.lowByte;
/*     */ 
/* 222 */       i += 2;
/* 223 */       iPos += 2;
/*     */     }
/*     */ 
/* 228 */     while (iPos < iLen)
/* 229 */       label279: pchTo[(iPos++)] = 32;
/*     */   }
/*     */ 
/*     */   public static void ToGb2(byte[] pchFrom, int off1, byte[] pchTo, int off2, int len)
/*     */   {
/* 238 */     int iPos = off2;
/* 239 */     int i = off1;
/* 240 */     int iLen = len + off1;
/* 241 */     while (i < iLen) {
/* 242 */       if (pchFrom[i] == 15) {
/* 243 */         pchTo[(iPos++)] = 32;
/* 244 */         ++i;
/*     */       }
/*     */ 
/* 248 */       if ((pchFrom[i] != 14) || ((pchFrom[i] == 14) && (Unmatch0f(i + 1, iLen, pchFrom))))
/*     */       {
/* 250 */         if ((i + 1 < iLen) && (pchFrom[(i + 1)] == 1)) {
/* 251 */           pchTo[iPos] = signedConvert((short)pchFrom[i]);
/*     */         }
/*     */         else {
/* 254 */           pchTo[iPos] = bEbcdToAscii(pchFrom[i]);
/*     */         }
/* 256 */         ++i;
/* 257 */         ++iPos;
/*     */       }
/*     */ 
/* 261 */       pchTo[(iPos++)] = 32;
/* 262 */       ++i;
/* 263 */       while (i < iLen - 1) {
/* 264 */         if (pchFrom[i] == 64) {
/* 265 */           pchTo[(iPos++)] = 32;
/* 266 */           ++i;
/*     */         }
/*     */ 
/* 269 */         if (pchFrom[i] == 15) break; if (pchFrom[i] == 14)
/*     */           break;
/* 271 */         if ((pchFrom[(i + 1)] == 15) || (pchFrom[(i + 1)] == 14))
/*     */         {
/* 275 */           pchTo[(iPos++)] = 63;
/* 276 */           ++i;
/* 277 */           break;
/*     */         }
/*     */ 
/* 280 */         HiChar tmpChar = new HiChar();
/* 281 */         DbcsToGb(new HiChar(pchFrom[i], pchFrom[(i + 1)]), tmpChar);
/* 282 */         pchTo[iPos] = tmpChar.highByte;
/* 283 */         pchTo[(iPos + 1)] = tmpChar.lowByte;
/* 284 */         iPos += 2;
/* 285 */         i += 2;
/*     */       }
/* 287 */       pchTo[(iPos++)] = 32;
/* 288 */       ++i;
/*     */     }
/*     */ 
/* 292 */     while (i < iLen)
/* 293 */       pchTo[(i++)] = 32;
/*     */   }
/*     */ 
/*     */   public static void ToDbcs(byte[] pchFrom, int off1, byte[] pchTo, int off2, int iLen)
/*     */   {
/* 303 */     int iPos = off2;
/* 304 */     int i = off1;
/* 305 */     while ((i < iLen + off1) && (iPos < iLen + off2)) {
/* 306 */       if ((pchFrom[i] & 0xFF) < 128) {
/* 307 */         pchTo[(iPos++)] = bAsciiToEbcd(pchFrom[(i++)]);
/*     */       }
/*     */ 
/* 317 */       if (i - off1 > iLen - 4) {
/*     */         break;
/*     */       }
/* 320 */       pchTo[(iPos++)] = 14;
/* 321 */       while ((pchFrom[i] & 0xFF) >= 128) {
/* 322 */         HiChar tmpChar = new HiChar();
/* 323 */         GbToDbcs(new HiChar(pchFrom[i], pchFrom[(i + 1)]), tmpChar);
/*     */ 
/* 329 */         pchTo[iPos] = tmpChar.highByte;
/* 330 */         pchTo[(iPos + 1)] = tmpChar.lowByte;
/* 331 */         i += 2;
/* 332 */         iPos += 2;
/*     */ 
/* 334 */         if (i - off1 > iLen - 3) {
/*     */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 340 */       pchTo[(iPos++)] = 15;
/*     */     }
/*     */ 
/* 345 */     while (iPos < iLen)
/* 346 */       pchTo[(iPos++)] = 64;
/*     */   }
/*     */ 
/*     */   public static void DbcsToGb(HiChar pbFrom, HiChar pbTo)
/*     */   {
/* 439 */     if (pbFrom.high() >= 129) {
/* 440 */       HiIBMSwGB.EBCDICtoGBK(pbFrom, pbTo);
/* 441 */       return;
/*     */     }
/*     */ 
/* 444 */     pbTo.setHigh(pbFrom.high());
/* 445 */     pbTo.setLow(pbFrom.low());
/*     */ 
/* 447 */     IBMToGB(pbTo);
/*     */   }
/*     */ 
/*     */   public static void GbToDbcs(HiChar pbFrom, HiChar pbTo)
/*     */   {
/* 464 */     if ((pbFrom.high() <= 160) || (pbFrom.high() >= 248) || (pbFrom.low() <= 160))
/*     */     {
/* 466 */       HiIBMSwGB.GBKtoEBCDIC(pbFrom, pbTo);
/* 467 */       return;
/*     */     }
/*     */ 
/* 470 */     pbTo.setHigh(pbFrom.high());
/* 471 */     pbTo.setLow(pbFrom.low());
/*     */ 
/* 473 */     if ((pbFrom.high() < 128) || (pbFrom.low() < 128)) {
/* 474 */       return;
/*     */     }
/*     */ 
/* 477 */     if ((pbFrom.high() > 160) && (pbFrom.high() < 176)) {
/* 478 */       GbSpecial(pbFrom, pbTo);
/* 479 */       return;
/*     */     }
/*     */ 
/* 482 */     int Byte0Offset = 104 + (pbFrom.high() - 176) / 2;
/*     */ 
/* 484 */     if (pbFrom.high() % 2 == 0) {
/* 485 */       pbTo.setHigh(pbTo.high() - Byte0Offset);
/* 486 */       pbTo.setLow(pbTo.low() - 1);
/*     */     }
/* 488 */     else if (pbFrom.low() < 224) {
/* 489 */       pbTo.setHigh(pbTo.high() - Byte0Offset);
/* 490 */       pbTo.setLow(pbTo.low() - 96);
/*     */     } else {
/* 492 */       pbTo.setHigh(pbTo.high() - Byte0Offset);
/* 493 */       pbTo.setLow(pbTo.low() - 95);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void IBMToGB(HiChar Code)
/*     */   {
/* 501 */     int gbcode = 0;
/*     */ 
/* 505 */     int highbyte = Code.high();
/* 506 */     int lowerbyte = Code.low();
/*     */ 
/* 508 */     int ibmcode = lowerbyte | highbyte << 8;
/*     */ 
/* 510 */     if ((highbyte >= 72) && (highbyte <= 108))
/*     */     {
/* 514 */       int x = 72;
/* 515 */       int n = 0;
/* 516 */       while (x != highbyte) {
/* 517 */         ++x;
/* 518 */         ++n;
/*     */       }
/* 520 */       if ((lowerbyte >= 65) && (lowerbyte <= 127)) {
/* 521 */         gbcode = ibmcode + 26464 + n * 256;
/*     */       }
/* 523 */       else if ((lowerbyte >= 129) && (lowerbyte <= 159)) {
/* 524 */         gbcode = ibmcode + 26463 + n * 256;
/*     */       }
/* 526 */       else if ((lowerbyte >= 160) && (lowerbyte <= 253))
/* 527 */         gbcode = ibmcode + 26625 + n * 256;
/*     */       else {
/* 529 */         gbcode = 8224;
/*     */       }
/*     */ 
/*     */     }
/* 533 */     else if (highbyte >= 64) {
/* 534 */       if (ibmcode == 16448) {
/* 535 */         gbcode = 41377;
/*     */       }
/* 539 */       else if ((ibmcode >= 18084) && (ibmcode <= 18159)) {
/* 540 */         gbcode = ibmcode + 24576;
/*     */       }
/* 544 */       else if ((ibmcode >= 16768) && (ibmcode <= 16800)) {
/* 545 */         gbcode = ibmcode + 26193;
/* 546 */       } else if ((ibmcode >= 16832) && (ibmcode <= 16864)) {
/* 547 */         gbcode = ibmcode + 26081;
/* 548 */       } else if ((ibmcode >= 17025) && (ibmcode <= 17033)) {
/* 549 */         gbcode = ibmcode + 24928;
/* 550 */       } else if ((ibmcode >= 17041) && (ibmcode <= 17049)) {
/* 551 */         gbcode = ibmcode + 24921;
/* 552 */       } else if ((ibmcode >= 17058) && (ibmcode <= 17065)) {
/* 553 */         gbcode = ibmcode + 24913;
/* 554 */       } else if ((ibmcode >= 17136) && (ibmcode <= 17145)) {
/* 555 */         gbcode = ibmcode + 24768;
/* 556 */       } else if ((ibmcode >= 17089) && (ibmcode <= 17097)) {
/* 557 */         gbcode = ibmcode + 24832;
/* 558 */       } else if ((ibmcode >= 17105) && (ibmcode <= 17113)) {
/* 559 */         gbcode = ibmcode + 24825;
/* 560 */       } else if ((ibmcode >= 17122) && (ibmcode <= 17129)) {
/* 561 */         gbcode = ibmcode + 24817;
/* 562 */       } else if ((ibmcode >= 17889) && (ibmcode <= 17898)) {
/* 563 */         gbcode = ibmcode + 23780;
/* 564 */       } else if ((ibmcode >= 17905) && (ibmcode <= 17914)) {
/* 565 */         gbcode = ibmcode + 23796;
/* 566 */       } else if ((ibmcode >= 16881) && (ibmcode <= 16892)) {
/* 567 */         gbcode = ibmcode + 24832;
/* 568 */       } else if ((ibmcode >= 17841) && (ibmcode <= 17880)) {
/* 569 */         gbcode = ibmcode + 23808;
/*     */       } else {
/* 571 */         int i = 0;
/* 572 */         while (i < table.length - 1) {
/* 573 */           if (table[i] == ibmcode) {
/* 574 */             gbcode = table[(i + 1)];
/* 575 */             break;
/*     */           }
/* 577 */           i += 2;
/*     */         }
/* 579 */         if (i >= table.length) {
/* 580 */           gbcode = 8224;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 585 */     Code.setHigh(gbcode / 256);
/* 586 */     Code.setLow(gbcode % 256);
/*     */   }
/*     */ 
/*     */   public static byte bEbcdToAscii(byte bEbcd)
/*     */   {
/* 599 */     byte[] ebc_to_asc = { 32, 1, 2, 3, 0, 9, 0, 0, 0, 0, 0, 11, 12, 13, 32, 32, 16, 17, 18, 19, 0, 21, 8, 0, 24, 25, 0, 0, 28, 29, 30, 31, 0, 0, 0, 0, 0, 10, 23, 27, 0, 0, 0, 0, 0, 5, 6, 7, 0, 0, 22, 0, 0, 0, 0, 4, 0, 0, 0, 0, 20, 21, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 91, 46, 60, 40, 43, 124, 38, 32, 32, 32, 32, 32, 32, 32, 32, 32, 33, 36, 42, 41, 59, 94, 45, 47, 32, 32, 32, 32, 32, 32, 32, 32, 93, 44, 37, 95, 62, 63, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 58, 35, 64, 39, 61, 34, 32, 97, 98, 99, 100, 101, 102, 103, 104, 105, 32, 32, 32, 32, 32, 32, 32, 106, 107, 108, 109, 110, 111, 112, 113, 114, 32, 32, 32, 32, 32, 32, 32, 126, 115, 116, 117, 118, 119, 120, 121, 122, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 123, 65, 66, 67, 68, 69, 70, 71, 72, 73, 32, 32, 32, 32, 32, 32, 125, 74, 75, 76, 77, 78, 79, 80, 81, 82, 32, 32, 32, 32, 32, 32, 92, 32, 83, 84, 85, 86, 87, 88, 89, 90, 32, 32, 32, 32, 32, 32, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 32, 32, 32, 32, 32, 32 };
/*     */ 
/* 625 */     return ebc_to_asc[(bEbcd & 0xFF)];
/*     */   }
/*     */ 
/*     */   public static byte bAsciiToEbcd(byte bAscii)
/*     */   {
/* 636 */     short[] asc_to_ebc = { 0, 1, 2, 3, 55, 45, 46, 47, 22, 5, 37, 11, 12, 13, 63, 63, 16, 17, 18, 19, 60, 61, 50, 38, 24, 25, 63, 39, 28, 29, 30, 31, 64, 90, 127, 123, 91, 108, 80, 125, 77, 93, 92, 78, 107, 96, 75, 97, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 122, 94, 76, 126, 110, 111, 124, 193, 194, 195, 196, 197, 198, 199, 200, 201, 209, 210, 211, 212, 213, 214, 215, 216, 217, 226, 227, 228, 229, 230, 231, 232, 233, 74, 224, 106, 95, 109, 121, 129, 130, 131, 132, 133, 134, 135, 136, 137, 145, 146, 147, 148, 149, 150, 151, 152, 153, 162, 163, 164, 165, 166, 167, 168, 169, 192, 79, 208, 161, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 74, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64 };
/*     */ 
/* 663 */     return (byte)asc_to_ebc[(bAscii & 0xFF)];
/*     */   }
/*     */ 
/*     */   public static int GbSpecial(HiChar pbFrom, HiChar pbTo) {
/* 667 */     int ibmcode = 0;
/*     */ 
/* 669 */     int gbcode = pbFrom.high() * 256 + pbFrom.low();
/*     */ 
/* 671 */     if (gbcode == 41377) {
/* 672 */       ibmcode = 16448;
/* 673 */     } else if ((gbcode >= 42660) && (gbcode <= 42735)) {
/* 674 */       ibmcode = gbcode - 24576;
/* 675 */     } else if ((gbcode >= 42961) && (gbcode <= 42992)) {
/* 676 */       ibmcode = gbcode - 26193;
/* 677 */     } else if ((gbcode >= 42913) && (gbcode <= 42945)) {
/* 678 */       ibmcode = gbcode - 26081;
/* 679 */     } else if ((gbcode >= 41953) && (gbcode <= 41961)) {
/* 680 */       ibmcode = gbcode - 24928;
/* 681 */     } else if ((gbcode >= 41962) && (gbcode <= 41970)) {
/* 682 */       ibmcode = gbcode - 24921;
/* 683 */     } else if ((gbcode >= 41971) && (gbcode <= 41978)) {
/* 684 */       ibmcode = gbcode - 24913;
/* 685 */     } else if ((gbcode >= 41904) && (gbcode <= 41913)) {
/* 686 */       ibmcode = gbcode - 24768;
/* 687 */     } else if ((gbcode >= 41921) && (gbcode <= 41929)) {
/* 688 */       ibmcode = gbcode - 24832;
/* 689 */     } else if ((gbcode >= 41930) && (gbcode <= 41938)) {
/* 690 */       ibmcode = gbcode - 24825;
/* 691 */     } else if ((gbcode >= 41939) && (gbcode <= 41946)) {
/* 692 */       ibmcode = gbcode - 24817;
/* 693 */     } else if ((gbcode >= 41669) && (gbcode <= 41678)) {
/* 694 */       ibmcode = gbcode - 23780;
/* 695 */     } else if ((gbcode >= 41701) && (gbcode <= 41710)) {
/* 696 */       ibmcode = gbcode - 23796;
/* 697 */     } else if ((gbcode >= 41713) && (gbcode <= 41724)) {
/* 698 */       ibmcode = gbcode - 24832;
/* 699 */     } else if ((gbcode >= 41649) && (gbcode <= 41688)) {
/* 700 */       ibmcode = gbcode - 23808;
/*     */     }
/*     */     else {
/* 703 */       int i = 0;
/* 704 */       while (i < table.length - 1) {
/* 705 */         if (table[(i + 1)] == gbcode) {
/* 706 */           ibmcode = table[i];
/* 707 */           break;
/*     */         }
/* 709 */         i += 2;
/*     */       }
/* 711 */       if (i >= table.length) {
/* 712 */         ibmcode = 16448;
/*     */       }
/*     */     }
/* 715 */     pbTo.setHigh(ibmcode / 256);
/* 716 */     pbTo.setLow(ibmcode % 256);
/*     */ 
/* 718 */     return 1;
/*     */   }
/*     */ 
/*     */   private static boolean Unmatch0f(int iBeginPos, int iBufLen, byte[] buf)
/*     */   {
/* 724 */     int i = iBeginPos;
/* 725 */     while (i < iBufLen) {
/* 726 */       if (buf[i] == 14)
/* 727 */         return true;
/* 728 */       if (buf[i] == 15)
/* 729 */         return false;
/* 730 */       ++i;
/*     */     }
/*     */ 
/* 733 */     return true;
/*     */   }
/*     */ 
/*     */   public static byte signedConvert(short from)
/*     */   {
/*     */     short bTo;
/* 739 */     short bFrom = (short)(from & 0xFF);
/*     */ 
/* 741 */     if ((bFrom >= 240) && (bFrom <= 249))
/* 742 */       bTo = (short)(byte)(bFrom - 192);
/* 743 */     else if ((bFrom >= 192) && (bFrom <= 201))
/* 744 */       bTo = (short)(byte)(bFrom - 144);
/* 745 */     else if ((bFrom >= 208) && (bFrom <= 217))
/* 746 */       bTo = (short)(byte)(bFrom - 96);
/*     */     else {
/* 748 */       bTo = bFrom;
/*     */     }
/* 750 */     return (byte)bTo;
/*     */   }
/*     */ }