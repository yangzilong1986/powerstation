/*     */ package com.hisun.specproc;
/*     */ 
/*     */ import com.hisun.common.util.HiByteUtil;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiConvHelper;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class Conv
/*     */ {
/*  15 */   private static Logger log = HiLog.getLogger("conv.trc");
/*     */ 
/*     */   public HiByteBuffer genUnionMac(HiByteBuffer buf, HiMessageContext ctx)
/*     */   {
/*  25 */     HiByteBuffer buf1 = new HiByteBuffer(buf.length());
/*  26 */     boolean isBlank = false;
/*  27 */     int first = 0; int tail = buf.length();
/*     */ 
/*  29 */     for (first = 0; first < buf.length(); ++first) {
/*  30 */       if (buf.charAt(first) != 32) {
/*     */         break;
/*     */       }
/*     */     }
/*     */ 
/*  35 */     for (tail = buf.length() - 1; tail >= first; --tail) {
/*  36 */       if (buf.charAt(tail) != 32) {
/*  37 */         ++tail;
/*  38 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  43 */     for (int i = first; i < tail; ++i) {
/*  44 */       byte b = buf.charAt(i);
/*  45 */       if ((b != 44) && (b != 46) && (!(Character.isLetterOrDigit(b))) && (b != 32))
/*     */         continue;
/*  47 */       if (b != 32) {
/*  48 */         isBlank = false;
/*     */       }
/*  50 */       if (!(isBlank)) {
/*  51 */         buf1.append(Character.toUpperCase(b));
/*     */       }
/*  53 */       if (b == 32) {
/*  54 */         isBlank = true;
/*     */       }
/*     */     }
/*     */ 
/*  58 */     return buf1;
/*     */   }
/*     */ 
/*     */   public HiByteBuffer hex2str(HiByteBuffer bb, HiMessageContext mc) {
/*  62 */     if (bb.length() == 0)
/*  63 */       return bb;
/*  64 */     byte[] bs = HiConvHelper.ascStr2Bcd(bb.toString());
/*  65 */     return new HiByteBuffer(bs);
/*     */   }
/*     */ 
/*     */   public HiByteBuffer str2hex(HiByteBuffer bb, HiMessageContext mc) {
/*  69 */     if (bb.length() == 0)
/*  70 */       return bb;
/*  71 */     HiByteBuffer buf = new HiByteBuffer(bb.length());
/*  72 */     for (int i = 0; i < bb.length(); ++i) {
/*  73 */       byte b = bb.charAt(i);
/*  74 */       buf.append(bin2char((b & 0xF0) >> 4));
/*  75 */       buf.append(bin2char(b & 0xF));
/*     */     }
/*  77 */     return buf;
/*     */   }
/*     */ 
/*     */   private static char bin2char(int bin) {
/*  81 */     return (char)((bin < 10) ? bin + 48 : bin - 10 + 65);
/*     */   }
/*     */ 
/*     */   public HiByteBuffer Str2Short(HiByteBuffer bb, HiMessageContext mc)
/*     */   {
/*     */     try {
/*  87 */       if (bb.length() == 0) {
/*  88 */         return bb;
/*     */       }
/*  90 */       int i = NumberUtils.toInt(bb.toString());
/*  91 */       byte[] b = HiByteUtil.shortToByteArray(i);
/*  92 */       if (log.isInfoEnabled()) {
/*  93 */         log.info("Str2Short:[" + String.valueOf(i) + "]", b);
/*     */       }
/*  95 */       return new HiByteBuffer(b);
/*     */     } catch (RuntimeException e) {
/*  97 */       Logger log = HiLog.getLogger(mc.getCurrentMsg());
/*  98 */       log.error(e);
/*  99 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiByteBuffer Short2Str(HiByteBuffer bb, HiMessageContext mc)
/*     */   {
/*     */     try {
/* 106 */       if (bb.length() == 0)
/* 107 */         return bb;
/* 108 */       int i = HiByteUtil.byteArrayToShort(bb.getBytes());
/* 109 */       if (log.isInfoEnabled()) {
/* 110 */         log.info("Int2Str:[" + String.valueOf(i) + "]");
/*     */       }
/* 112 */       return new HiByteBuffer(String.valueOf(i).getBytes());
/*     */     } catch (RuntimeException e) {
/* 114 */       Logger log = HiLog.getLogger(mc.getCurrentMsg());
/* 115 */       log.error(e);
/* 116 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiByteBuffer Int2Str(HiByteBuffer bb, HiMessageContext mc)
/*     */   {
/*     */     try
/*     */     {
/* 127 */       if (bb.length() == 0)
/* 128 */         return bb;
/* 129 */       int i = byteArrayToInt(bb.getBytes());
/* 130 */       if (log.isInfoEnabled()) {
/* 131 */         log.info("Int2Str:[" + String.valueOf(i) + "]");
/*     */       }
/* 133 */       return new HiByteBuffer(String.valueOf(i).getBytes());
/*     */     } catch (RuntimeException e) {
/* 135 */       Logger log = HiLog.getLogger(mc.getCurrentMsg());
/* 136 */       log.error(e);
/* 137 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiByteBuffer Str2Int(HiByteBuffer bb, HiMessageContext mc)
/*     */   {
/*     */     try {
/* 144 */       if (bb.length() == 0) {
/* 145 */         return bb;
/*     */       }
/* 147 */       int i = NumberUtils.toInt(bb.toString());
/* 148 */       if (log.isInfoEnabled()) {
/* 149 */         log.info("Str2Int:[" + String.valueOf(i) + "]");
/*     */       }
/* 151 */       return new HiByteBuffer(intToByteArray(i));
/*     */     } catch (RuntimeException e) {
/* 153 */       Logger log = HiLog.getLogger(mc.getCurrentMsg());
/* 154 */       log.error(e);
/* 155 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static byte[] intToByteArray(int valor)
/*     */   {
/* 167 */     byte[] result = new byte[4];
/* 168 */     for (int i = 0; i < result.length; ++i)
/*     */     {
/* 170 */       result[i] = (byte)(valor & 0xFF);
/* 171 */       valor >>= 8;
/*     */     }
/* 173 */     return result;
/*     */   }
/*     */ 
/*     */   public static int byteArrayToInt(byte[] bytes)
/*     */   {
/* 184 */     int result = 0;
/*     */ 
/* 189 */     result |= 0xFF000000 & bytes[3] << 24;
/* 190 */     result |= 0xFF0000 & bytes[2] << 16;
/* 191 */     result |= 0xFF00 & bytes[1] << 8;
/* 192 */     result |= 0xFF & bytes[0];
/* 193 */     return result;
/*     */   }
/*     */ 
/*     */   public static byte[] shortToByteArray(int valor)
/*     */   {
/* 204 */     byte[] result = new byte[2];
/* 205 */     for (int i = 0; i < result.length; ++i)
/*     */     {
/* 208 */       result[i] = (byte)(valor & 0xFF);
/* 209 */       valor >>= 8;
/*     */     }
/* 211 */     return result;
/*     */   }
/*     */ 
/*     */   public static short byteArrayToShort(byte[] bytes)
/*     */   {
/* 222 */     int result = 0;
/*     */ 
/* 225 */     result |= 0xFF00 & bytes[1] << 8;
/* 226 */     result |= 0xFF & bytes[0];
/* 227 */     return (short)result;
/*     */   }
/*     */ }