/*     */ package com.hisun.bank.component;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.security.MessageDigest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class Sigature
/*     */ {
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  35 */     HiMessage msg = ctx.getCurrentMsg();
/*  36 */     Logger log = HiLog.getLogger(msg);
/*  37 */     HiETF root = msg.getETFBody();
/*     */ 
/*  39 */     String alg = HiArgUtils.getStringNotNull(args, "alg");
/*  40 */     String flds = HiArgUtils.getStringNotNull(args, "flds");
/*  41 */     String dstFld = args.get("dstFld");
/*  42 */     if (StringUtils.isBlank(dstFld)) {
/*  43 */       dstFld = "SgnVal";
/*     */     }
/*  45 */     String[] tmps = flds.split("\\|");
/*  46 */     StringBuffer buf = new StringBuffer();
/*  47 */     for (int i = 0; i < tmps.length; ++i) {
/*  48 */       String tmpVal = root.getChildValue(tmps[i]);
/*  49 */       if (!(StringUtils.isEmpty(tmpVal))) {
/*  50 */         buf.append(tmps[i] + "=" + tmpVal);
/*  51 */         if (i < tmps.length - 1) {
/*  52 */           buf.append("&");
/*     */         }
/*     */       }
/*     */     }
/*  56 */     root.setChildValue(dstFld, MD5(buf.toString().getBytes()));
/*  57 */     return 0;
/*     */   }
/*     */ 
/*     */   public static String MD5(byte[] source)
/*     */   {
/*  73 */     String s = null;
/*  74 */     char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */     try
/*     */     {
/*  78 */       MessageDigest md = MessageDigest.getInstance("MD5");
/*     */ 
/*  80 */       md.update(source);
/*  81 */       byte[] tmp = md.digest();
/*     */ 
/*  83 */       char[] str = new char[32];
/*     */ 
/*  86 */       int k = 0;
/*  87 */       for (int i = 0; i < 16; ++i)
/*     */       {
/*  89 */         byte byte0 = tmp[i];
/*  90 */         str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
/*     */ 
/*  93 */         str[(k++)] = hexDigits[(byte0 & 0xF)];
/*     */       }
/*  95 */       s = new String(str);
/*     */     }
/*     */     catch (Exception e) {
/*  98 */       e.printStackTrace();
/*     */     }
/* 100 */     return s;
/*     */   }
/*     */ }