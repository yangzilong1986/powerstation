/*     */ package com.hisun.sw.big5;
/*     */ 
/*     */ import com.hisun.sw.HiChar;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class HiB52Host
/*     */ {
/*     */   public static final byte IMPOSSIBLECHARVALUE = -1;
/*  24 */   private static ArrayList<SYMNODE> _symboltable = new ArrayList();
/*     */ 
/*  35 */   private static ArrayList<HOSTNODE> _hosttable = new ArrayList();
/*     */ 
/*  46 */   private static ArrayList<UDCNODE> _udctable = new ArrayList();
/*     */ 
/*  57 */   private static ArrayList<HOSTCHAR> _hostchartable = new ArrayList();
/*     */ 
/*  59 */   private static int _loadudc = 0;
/*     */ 
/*     */   private static boolean _loadudcMappingTable()
/*     */   {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   private static void ConvertBig5To5550(HiChar from, HiChar to)
/*     */   {
/*     */     int i;
/*     */     int hbn;
/*  79 */     short ibmhighbyte = (short)(from.highByte & 0xFF);
/*  80 */     short ibmlowbyte = (short)(from.lowByte & 0xFF);
/*     */ 
/*  88 */     if (((161 == ibmhighbyte) && ((((64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((161 <= ibmlowbyte) && (ibmlowbyte <= 254))))) || ((162 == ibmhighbyte) && ((((64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((161 <= ibmlowbyte) && (ibmlowbyte <= 254))))) || ((163 == ibmhighbyte) && ((((64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((161 <= ibmlowbyte) && (ibmlowbyte <= 224))))) || ((198 == ibmhighbyte) && (161 <= ibmlowbyte) && (ibmlowbyte <= 254)) || ((199 == ibmhighbyte) && ((((64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((161 <= ibmlowbyte) && (ibmlowbyte <= 254))))) || ((200 == ibmhighbyte) && ((((64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((161 <= ibmlowbyte) && (ibmlowbyte <= 211))))) || ((242 == ibmhighbyte) && (134 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((243 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((244 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((245 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((246 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((247 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((248 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((249 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((200 == ibmhighbyte) && (ibmlowbyte == 254)))
/*     */     {
/* 103 */       i = 0;
/* 104 */       for (; (i < _symboltable.size()) && (((SYMNODE)_symboltable.get(i)).big5code[0] != -1); ++i) {
/* 105 */         if ((((SYMNODE)_symboltable.get(i)).big5code[0] != ibmhighbyte) || (((SYMNODE)_symboltable.get(i)).big5code[1] != ibmlowbyte))
/*     */           continue;
/* 107 */         to.highByte = ((SYMNODE)_symboltable.get(i)).ibm5550code[0];
/* 108 */         to.lowByte = ((SYMNODE)_symboltable.get(i)).ibm5550code[1];
/* 109 */         return;
/*     */       }
/*     */ 
/* 112 */       LogMsg("*** ERROR : can't find the symbol ", "");
/* 113 */       return;
/*     */     }
/*     */ 
/* 120 */     if (((250 <= ibmhighbyte) && (ibmhighbyte <= 254) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((250 <= ibmhighbyte) && (ibmhighbyte <= 254) && (161 <= ibmlowbyte) && (ibmlowbyte <= 254)) || ((142 <= ibmhighbyte) && (ibmhighbyte <= 160) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((142 <= ibmhighbyte) && (ibmhighbyte <= 160) && (161 <= ibmlowbyte) && (ibmlowbyte <= 254)) || ((129 <= ibmhighbyte) && (ibmhighbyte <= 141) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((129 <= ibmhighbyte) && (ibmhighbyte <= 141) && (161 <= ibmlowbyte) && (ibmlowbyte <= 254)) || ((129 <= ibmhighbyte) && (ibmhighbyte <= 139) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((140 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 130)) || ((249 == ibmhighbyte) && (214 <= ibmlowbyte) && (ibmlowbyte <= 254)))
/*     */     {
/* 132 */       if (_loadudc == 0) {
/* 133 */         if (!(_loadudcMappingTable())) {
/* 134 */           LogMsg(" ERROR : loading udc mapping table failed ", "");
/* 135 */           return;
/*     */         }
/* 137 */         _loadudc = 1;
/*     */       }
/*     */ 
/* 140 */       i = 0;
/* 141 */       for (; (i < _udctable.size()) && (((UDCNODE)_udctable.get(i)).big5code[0] != -1); ++i) {
/* 142 */         if ((((UDCNODE)_udctable.get(i)).big5code[0] != ibmhighbyte) || (((UDCNODE)_udctable.get(i)).big5code[1] != ibmlowbyte))
/*     */           continue;
/* 144 */         to.highByte = ((UDCNODE)_udctable.get(i)).ibm5550code[0];
/* 145 */         to.lowByte = ((UDCNODE)_udctable.get(i)).ibm5550code[1];
/* 146 */         return;
/*     */       }
/*     */ 
/* 150 */       LogMsg("*** ERROR : can't find the udc symbol ", "");
/* 151 */       return;
/*     */     }
/*     */ 
/* 157 */     if ((254 < ibmhighbyte) || (ibmhighbyte < 129) || (254 < ibmlowbyte) || (ibmlowbyte < 64) || ((126 < ibmlowbyte) && (ibmlowbyte < 129)))
/*     */     {
/* 163 */       to.highByte = -4;
/* 164 */       to.lowByte = -4;
/* 165 */       return;
/*     */     }
/*     */ 
/* 171 */     if (((141 <= ibmhighbyte) && (ibmhighbyte <= 241) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((250 <= ibmhighbyte) && (ibmhighbyte <= 254) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((140 == ibmhighbyte) && (131 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((163 == ibmhighbyte) && (225 <= ibmlowbyte) && (ibmlowbyte <= 254)) || ((200 == ibmhighbyte) && (212 <= ibmlowbyte) && (ibmlowbyte < 254)) || ((242 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 133)))
/*     */     {
/* 180 */       to.highByte = -4;
/* 181 */       to.lowByte = -5;
/* 182 */       return;
/*     */     }
/* 184 */     if (ibmhighbyte < 201)
/* 185 */       hbn = (ibmhighbyte - 164) * 157;
/*     */     else {
/* 187 */       hbn = (ibmhighbyte - 201) * 157;
/*     */     }
/* 189 */     int lbn = ibmlowbyte - 64;
/* 190 */     if (lbn > 63) {
/* 191 */       lbn -= 34;
/*     */     }
/* 193 */     int sn = hbn + lbn;
/* 194 */     if (ibmhighbyte >= 201) {
/* 195 */       if (sn == 138) {
/* 196 */         sn -= 9;
/*     */       }
/* 198 */       else if ((sn >= 129) && (sn < 138)) {
/* 199 */         ++sn;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 204 */     int b5h = sn / 188;
/* 205 */     int b5l = sn % 188;
/* 206 */     if (b5l >= 63) {
/* 207 */       b5l += 1;
/*     */     }
/* 209 */     if (ibmhighbyte < 201)
/* 210 */       to.highByte = (byte)(b5h + 140);
/*     */     else {
/* 212 */       to.highByte = (byte)(b5h + 169);
/*     */     }
/* 214 */     to.lowByte = (byte)(b5l + 64);
/*     */   }
/*     */ 
/*     */   private static void Convert5550ToHOST(HiChar big5)
/*     */   {
/* 227 */     short big5highbyte = (short)(big5.highByte & 0xFF);
/* 228 */     short big5lowbyte = (short)(big5.lowByte & 0xFF);
/*     */ 
/* 232 */     if (big5highbyte < 140) {
/* 233 */       int i = 0;
/* 234 */       for (; (i < _hosttable.size()) && (((HOSTNODE)_hosttable.get(i)).ibm5550code[0] != -1); ++i) {
/* 235 */         if ((((HOSTNODE)_hosttable.get(i)).ibm5550code[0] != big5highbyte) || (((HOSTNODE)_hosttable.get(i)).ibm5550code[1] != big5lowbyte))
/*     */           continue;
/* 237 */         big5.highByte = ((HOSTNODE)_hosttable.get(i)).hostcode[0];
/* 238 */         big5.lowByte = ((HOSTNODE)_hosttable.get(i)).hostcode[1];
/* 239 */         return;
/*     */       }
/*     */ 
/* 242 */       LogMsg("*** ERROR : can't find the host symbol ", "");
/* 243 */       return;
/*     */     }
/*     */ 
/* 246 */     if ((252 == big5highbyte) && (((big5lowbyte == 252) || (big5lowbyte == 251))))
/*     */     {
/* 251 */       if (big5lowbyte == 252)
/* 252 */         big5.lowByte = -2;
/*     */       else {
/* 254 */         big5.lowByte = -3;
/*     */       }
/* 256 */       big5.highByte = -2;
/* 257 */       return;
/*     */     }
/* 259 */     if (big5highbyte > 209) {
/* 260 */       big5.highByte = (byte)(big5.highByte - 25);
/* 261 */       big5.lowByte = (byte)(big5.lowByte + 1);
/*     */     } else {
/* 263 */       big5.highByte = (byte)(big5.highByte - 64);
/* 264 */       big5.lowByte = (byte)(big5.lowByte + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void b52host(HiChar from, HiChar to)
/*     */   {
/* 274 */     ConvertBig5To5550(from, to);
/* 275 */     Convert5550ToHOST(to);
/* 276 */     if (to.highByte == 0)
/* 277 */       to.highByte = 32;
/* 278 */     if (to.lowByte == 0)
/* 279 */       to.lowByte = 32;
/*     */   }
/*     */ 
/*     */   private static void LogMsg(String p1, String p2)
/*     */   {
/*     */   }
/*     */ 
/*     */   class HOSTCHAR
/*     */   {
/*     */     byte big5code;
/*     */     byte hostcharcode;
/*     */   }
/*     */ 
/*     */   class UDCNODE
/*     */   {
/*     */     byte[] big5code;
/*     */     byte[] ibm5550code;
/*     */ 
/*     */     UDCNODE()
/*     */     {
/*  42 */       this.big5code = new byte[2];
/*  43 */       this.ibm5550code = new byte[2];
/*     */     }
/*     */   }
/*     */ 
/*     */   class HOSTNODE
/*     */   {
/*     */     byte[] ibm5550code;
/*     */     byte[] hostcode;
/*     */ 
/*     */     HOSTNODE()
/*     */     {
/*  31 */       this.ibm5550code = new byte[2];
/*  32 */       this.hostcode = new byte[2];
/*     */     }
/*     */   }
/*     */ 
/*     */   class SYMNODE
/*     */   {
/*     */     byte[] big5code;
/*     */     byte[] ibm5550code;
/*     */ 
/*     */     SYMNODE()
/*     */     {
/*  20 */       this.big5code = new byte[2];
/*  21 */       this.ibm5550code = new byte[2];
/*     */     }
/*     */   }
/*     */ }