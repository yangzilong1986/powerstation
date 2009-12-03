/*     */ package com.hisun.sqn;
/*     */ 
/*     */ import com.hisun.dispatcher.HiRouterOut;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiSqnMng
/*     */ {
/*     */   private static final String MSGTYPE = "SQNMNG";
/*     */ 
/*     */   public static void updActDat(String oldDat, String newDat)
/*     */     throws HiException
/*     */   {
/*  21 */     HiMessage msg = new HiMessage("S.SQNSVR", "SQNMNG");
/*  22 */     msg.setType("SQNMNG");
/*  23 */     msg.setHeadItem("SDT", "S.SQNSVR");
/*  24 */     msg.setHeadItem("CMD", "UPDDAT");
/*  25 */     msg.setHeadItem("OLD", oldDat);
/*  26 */     msg.setHeadItem("NEW", newDat);
/*  27 */     msg.setHeadItem("SCH", "rq");
/*  28 */     msg = HiRouterOut.innerSyncProcess(msg);
/*  29 */     if (!(StringUtils.isEmpty(msg.getHeadItem("SSC"))))
/*  30 */       throw new HiException(msg.getHeadItem("SSC"));
/*     */   }
/*     */ 
/*     */   public static String getDumTlr(String brNo, String aplCod, String aplSub)
/*     */     throws HiException
/*     */   {
/*  45 */     HiMessage msg = new HiMessage("S.SQNSVR", "SQNMNG");
/*  46 */     msg.setType("SQNMNG");
/*  47 */     msg.setHeadItem("SDT", "S.SQNSVR");
/*  48 */     msg.setHeadItem("CMD", "GETTLR");
/*  49 */     msg.setHeadItem("BRNO", brNo);
/*  50 */     msg.setHeadItem("APP", aplCod);
/*  51 */     if (!(StringUtils.isEmpty(aplSub))) {
/*  52 */       msg.setHeadItem("SUB", aplSub);
/*     */     }
/*  54 */     msg.setHeadItem("SCH", "rq");
/*  55 */     msg = HiRouterOut.innerSyncProcess(msg);
/*  56 */     if (!(StringUtils.isEmpty(msg.getHeadItem("SSC")))) {
/*  57 */       throw new HiException(msg.getHeadItem("SSC"));
/*     */     }
/*  59 */     return msg.getHeadItem("TLR");
/*     */   }
/*     */ 
/*     */   public static String getLogNo(int num)
/*     */     throws HiException
/*     */   {
/*  71 */     HiMessage msg = new HiMessage("S.SQNSVR", "SQNMNG");
/*  72 */     msg.setHeadItem("SDT", "S.SQNSVR");
/*  73 */     msg.setHeadItem("CMD", "GETTRC");
/*  74 */     msg.setHeadItem("NUM", String.valueOf(num));
/*  75 */     msg.setHeadItem("SCH", "rq");
/*  76 */     msg = HiRouterOut.innerSyncProcess(msg);
/*  77 */     if (!(StringUtils.isEmpty(msg.getHeadItem("SSC")))) {
/*  78 */       throw new HiException(msg.getHeadItem("SSC"));
/*     */     }
/*  80 */     return msg.getHeadItem("LSH");
/*     */   }
/*     */ 
/*     */   public static String getLogNo(HiMessage oriMsg, int num)
/*     */     throws HiException
/*     */   {
/*  92 */     HiMessage newMsg = new HiMessage("S.SQNSVR", "SQNMNG");
/*  93 */     newMsg.setHeadItem("SDT", "S.SQNSVR");
/*  94 */     newMsg.setHeadItem("CMD", "GETTRC");
/*  95 */     newMsg.setHeadItem("NUM", String.valueOf(num));
/*  96 */     newMsg.setHeadItem("SCH", "rq");
/*  97 */     newMsg = HiRouterOut.innerSyncProcess(newMsg);
/*  98 */     if (oriMsg.getBody() instanceof HiETF) {
/*  99 */       HiETF root = oriMsg.getETFBody();
/* 100 */       String accDt = root.getChildValue("ACC_DT");
/*     */ 
/* 102 */       String actDt = root.getChildValue("ACT_DT");
/* 103 */       if (!(StringUtils.isEmpty(accDt)))
/* 104 */         newMsg.setHeadItem("ACC_DT", accDt);
/* 105 */       else if (!(StringUtils.isEmpty(actDt))) {
/* 106 */         newMsg.setHeadItem("ACC_DT", actDt);
/*     */       }
/*     */     }
/*     */ 
/* 110 */     if (!(StringUtils.isEmpty(newMsg.getHeadItem("SSC")))) {
/* 111 */       throw new HiException(newMsg.getHeadItem("SSC"));
/*     */     }
/* 113 */     return newMsg.getHeadItem("LSH");
/*     */   }
/*     */ }