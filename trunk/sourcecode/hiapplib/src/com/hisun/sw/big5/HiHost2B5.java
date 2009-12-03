/*     */ package com.hisun.sw.big5;
/*     */ 
/*     */ import com.hisun.sw.HiChar;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class HiHost2B5
/*     */ {
/*     */   private static final byte IMPOSSIBLECHARVALUE = -1;
/*  16 */   private static ArrayList<HOSTNODE> hosttable = new ArrayList();
/*     */ 
/*  26 */   private static ArrayList<SYMNODE> symboltable = new ArrayList();
/*     */ 
/*  36 */   private static ArrayList<UDCNODE> udctable = new ArrayList();
/*     */ 
/*  46 */   private static ArrayList<HOSTCHAR> hostchartable = new ArrayList();
/*     */ 
/*  48 */   private static int loadudc = 0;
/*     */ 
/*     */   private static boolean LoadUDCMappingTable()
/*     */   {
/*  57 */     return false;
/*     */   }
/*     */ 
/*     */   private static void Convert5550ToBig5(HiChar ibm, HiChar big5)
/*     */   {
/*     */     int i;
/*     */     int hbn;
/*  69 */     short ibmhighbyte = (short)(ibm.highByte & 0xFF);
/*  70 */     short ibmlowbyte = (short)(ibm.lowByte & 0xFF);
/*     */ 
/*  74 */     if (((210 <= ibmhighbyte) && (ibmhighbyte <= 218) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((210 <= ibmhighbyte) && (ibmhighbyte <= 218) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((132 == ibmhighbyte) && (191 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((133 == ibmhighbyte) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((133 == ibmhighbyte) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((134 == ibmhighbyte) && (161 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((136 == ibmhighbyte) && (89 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((136 == ibmhighbyte) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((137 == ibmhighbyte) && (122 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((137 == ibmhighbyte) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((139 == ibmhighbyte) && (98 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((139 == ibmhighbyte) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((168 == ibmhighbyte) && (203 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((209 == ibmhighbyte) && (199 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((252 == ibmhighbyte) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((252 == ibmhighbyte) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)))
/*     */     {
/*  93 */       big5.highByte = -56;
/*  94 */       big5.lowByte = -2;
/*  95 */       return;
/*     */     }
/*     */ 
/* 101 */     if (((129 <= ibmhighbyte) && (ibmhighbyte <= 138) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((129 <= ibmhighbyte) && (ibmhighbyte <= 138) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((139 == ibmhighbyte) && (64 <= ibmlowbyte) && (ibmlowbyte <= 97)) || ((209 == ibmhighbyte) && (((ibmlowbyte == 197) || (ibmlowbyte == 198)))) || ((168 == ibmhighbyte) && (ibmlowbyte == 202)) || ((252 == ibmhighbyte) && (((ibmlowbyte == 252) || (ibmlowbyte == 251)))))
/*     */     {
/* 107 */       i = 0;
/* 108 */       for (; (i < symboltable.size()) && (((SYMNODE)symboltable.get(i)).ibm5550code[0] != -1); ++i) {
/* 109 */         if ((((SYMNODE)symboltable.get(i)).ibm5550code[0] != ibmhighbyte) || (((SYMNODE)symboltable.get(i)).ibm5550code[1] != ibmlowbyte))
/*     */           continue;
/* 111 */         big5.highByte = ((SYMNODE)symboltable.get(i)).big5code[0];
/* 112 */         big5.lowByte = ((SYMNODE)symboltable.get(i)).big5code[1];
/* 113 */         return;
/*     */       }
/*     */ 
/* 120 */       LogMsg("", "*** ERROR : can't find the symbol  ");
/* 121 */       return;
/*     */     }
/*     */ 
/* 127 */     if (((219 <= ibmhighbyte) && (ibmhighbyte <= 251) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((219 <= ibmhighbyte) && (ibmhighbyte <= 251) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)))
/*     */     {
/* 132 */       if (loadudc == 0) {
/* 133 */         if (!(LoadUDCMappingTable())) {
/* 134 */           LogMsg("", " ERROR : loading udc mapping table failed  ");
/* 135 */           return;
/*     */         }
/* 137 */         loadudc = 1;
/*     */       }
/*     */ 
/* 140 */       i = 0;
/* 141 */       for (; (i < udctable.size()) && (((UDCNODE)udctable.get(i)).ibm5550code[0] != -1); ++i) {
/* 142 */         if ((((UDCNODE)udctable.get(i)).ibm5550code[0] != ibmhighbyte) || (((UDCNODE)udctable.get(i)).ibm5550code[1] != ibmlowbyte))
/*     */           continue;
/* 144 */         big5.highByte = ((UDCNODE)udctable.get(i)).big5code[0];
/* 145 */         big5.lowByte = ((UDCNODE)udctable.get(i)).big5code[1];
/* 146 */         return;
/*     */       }
/*     */ 
/* 149 */       LogMsg("", "*** ERROR : can't find the udc symbol  ");
/* 150 */       return;
/*     */     }
/*     */ 
/* 156 */     if ((252 < ibmhighbyte) || (ibmhighbyte < 129) || (252 < ibmlowbyte) || (ibmlowbyte < 64) || ((126 < ibmlowbyte) && (ibmlowbyte < 128)))
/*     */     {
/* 162 */       big5.highByte = -56;
/* 163 */       big5.lowByte = -2;
/* 164 */       return;
/*     */     }
/* 166 */     if (ibmhighbyte < 169)
/* 167 */       hbn = (ibmhighbyte - 140) * 188;
/*     */     else {
/* 169 */       hbn = (ibmhighbyte - 169) * 188;
/*     */     }
/* 171 */     int lbn = ibmlowbyte - 64;
/* 172 */     if (lbn > 63) {
/* 173 */       lbn -= 1;
/*     */     }
/* 175 */     int sn = hbn + lbn;
/* 176 */     if (ibmhighbyte >= 169) {
/* 177 */       if (sn == 129) {
/* 178 */         sn += 9;
/*     */       }
/* 180 */       else if ((sn > 129) && (sn < 139)) {
/* 181 */         --sn;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 186 */     int b5h = sn / 157;
/* 187 */     int b5l = sn % 157;
/* 188 */     if (b5l >= 63) {
/* 189 */       b5l += 34;
/*     */     }
/* 191 */     if (ibmhighbyte < 169)
/* 192 */       big5.highByte = (byte)(b5h + 164);
/*     */     else {
/* 194 */       big5.highByte = (byte)(b5h + 201);
/*     */     }
/* 196 */     big5.lowByte = (byte)(b5l + 64);
/*     */   }
/*     */ 
/*     */   private static void ConvertHOSTTo5550(HiChar big5)
/*     */   {
/* 210 */     short big5highbyte = (short)(big5.highByte & 0xFF);
/* 211 */     short big5lowbyte = (short)(big5.lowByte & 0xFF);
/* 212 */     if (big5highbyte < 76) {
/* 213 */       int i = 0;
/* 214 */       for (; (i < hosttable.size()) && (((HOSTNODE)hosttable.get(i)).ibm5550code[0] != -1); ++i) {
/* 215 */         if ((((HOSTNODE)hosttable.get(i)).hostcode[0] != big5highbyte) || (((HOSTNODE)hosttable.get(i)).hostcode[1] != big5lowbyte))
/*     */           continue;
/* 217 */         big5.highByte = ((HOSTNODE)hosttable.get(i)).ibm5550code[0];
/* 218 */         big5.lowByte = ((HOSTNODE)hosttable.get(i)).ibm5550code[1];
/* 219 */         return;
/*     */       }
/*     */ 
/* 222 */       LogMsg("", "*** ERROR : can't find the symbol  ");
/* 223 */       return;
/*     */     }
/*     */ 
/* 226 */     if ((254 == big5highbyte) && (((big5lowbyte == 254) || (big5lowbyte == 253))))
/*     */     {
/* 231 */       if (big5lowbyte == 254)
/* 232 */         big5.lowByte = -4;
/*     */       else {
/* 234 */         big5.lowByte = -5;
/*     */       }
/* 236 */       big5.highByte = -4;
/* 237 */       return;
/*     */     }
/* 239 */     if (big5highbyte > 145)
/*     */     {
/* 241 */       big5.highByte = (byte)(big5.highByte + 25);
/* 242 */       big5.lowByte = (byte)(big5.lowByte - 1);
/*     */     } else {
/* 244 */       big5.highByte = (byte)(big5.highByte + 64);
/* 245 */       big5.lowByte = (byte)(big5.lowByte - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void host2b5(HiChar from, HiChar to)
/*     */   {
/* 252 */     ConvertHOSTTo5550(from);
/* 253 */     Convert5550ToBig5(from, to);
/* 254 */     if (to.highByte == 0)
/* 255 */       to.highByte = 32;
/* 256 */     if (to.lowByte == 0)
/* 257 */       to.lowByte = 32;
/*     */   }
/*     */ 
/*     */   private static void LogMsg(String msg1, String msg2)
/*     */   {
/*     */   }
/*     */ 
/*     */   class HOSTCHAR
/*     */   {
/*     */     byte hostcharcode;
/*     */     byte big5code;
/*     */   }
/*     */ 
/*     */   class UDCNODE
/*     */   {
/*     */     byte[] ibm5550code;
/*     */     byte[] big5code;
/*     */ 
/*     */     UDCNODE()
/*     */     {
/*  32 */       this.ibm5550code = new byte[2];
/*  33 */       this.big5code = new byte[2];
/*     */     }
/*     */   }
/*     */ 
/*     */   class SYMNODE
/*     */   {
/*     */     byte[] ibm5550code;
/*     */     byte[] big5code;
/*     */ 
/*     */     SYMNODE()
/*     */     {
/*  22 */       this.ibm5550code = new byte[2];
/*  23 */       this.big5code = new byte[2];
/*     */     }
/*     */   }
/*     */ 
/*     */   class HOSTNODE
/*     */   {
/*     */     byte[] hostcode;
/*     */     byte[] ibm5550code;
/*     */ 
/*     */     HOSTNODE()
/*     */     {
/*  12 */       this.hostcode = new byte[2];
/*  13 */       this.ibm5550code = new byte[2];
/*     */     }
/*     */   }
/*     */ }