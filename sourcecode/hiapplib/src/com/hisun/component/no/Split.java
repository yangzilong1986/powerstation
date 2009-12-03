/*     */ package com.hisun.component.no;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class Split
/*     */ {
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  35 */     HiMessage msg = ctx.getCurrentMsg();
/*  36 */     HiETF etfBody = msg.getETFBody();
/*  37 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/*  39 */     String sValue = HiArgUtils.getStringNotNull(args, "Value");
/*  40 */     String grpNam = args.get("GrpNam");
/*  41 */     String paraNam = args.get("ParaNam");
/*  42 */     String sepChar = args.get("SepChar");
/*     */ 
/*  44 */     if (StringUtils.isEmpty(grpNam)) {
/*  45 */       grpNam = "PARA";
/*     */     }
/*     */ 
/*  48 */     if (StringUtils.isEmpty(paraNam)) {
/*  49 */       paraNam = "NAM";
/*     */     }
/*     */ 
/*  52 */     if (StringUtils.isEmpty(sepChar)) {
/*  53 */       sepChar = " ";
/*     */     }
/*     */ 
/*  57 */     String tmp = args.get("Type");
/*  58 */     int sepTyp = 0;
/*  59 */     sepTyp = NumberUtils.toInt(tmp);
/*  60 */     if ((sepTyp != 0) || (sepTyp != 1)) {
/*  61 */       sepTyp = 0;
/*     */     }
/*     */ 
/*  65 */     if (log.isInfoEnabled()) {
/*  66 */       log.info(String.format("Value[%s]GrpNam[%s]SepChar[%s]Type[%d]", new Object[] { sValue, grpNam, sepChar, Integer.valueOf(sepTyp) }));
/*     */     }
/*     */ 
/*  69 */     String[] paras = null;
/*     */ 
/*  72 */     if (StringUtils.isEmpty(sValue)) {
/*  73 */       etfBody.setChildValue(grpNam + "_NUM", "0");
/*     */     } else {
/*  75 */       if (sepTyp == 1)
/*  76 */         paras = StringUtils.split(sValue, sepChar);
/*  77 */       else if (sepTyp == 0) {
/*  78 */         paras = StringUtils.splitByWholeSeparator(sValue, sepChar);
/*     */       }
/*  80 */       for (int i = 0; i < paras.length; ++i) {
/*  81 */         HiETF grp = etfBody.getChildNode(grpNam + "_" + (i + 1));
/*  82 */         if (grp == null) {
/*  83 */           grp = etfBody.addNode(grpNam + "_" + (i + 1));
/*     */         }
/*     */ 
/*  86 */         if ((paras[i].indexOf(38) != -1) || (paras[i].indexOf(61) != -1)) {
/*  87 */           String[] tmp1s = paras[i].split("&");
/*  88 */           for (int j = 0; j < tmp1s.length; ++j) {
/*  89 */             String[] tmp2s = tmp1s[j].split("=");
/*  90 */             grp.setChildValue(tmp2s[0], tmp2s[1]);
/*     */           }
/*     */         } else {
/*  93 */           grp.setChildValue(paraNam, paras[i]);
/*     */         }
/*     */       }
/*  96 */       etfBody.setChildValue(grpNam + "_NUM", String.valueOf(paras.length));
/*     */     }
/*     */ 
/* 100 */     return 0;
/*     */   }
/*     */ }