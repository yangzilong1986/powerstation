/*     */ package com.hisun.atmp.handler;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiGetTxnCode
/*     */   implements IHandler, IServerInitListener
/*     */ {
/*     */   public static final int NCR_MESSAGE = 0;
/*     */   public static final int DIBAO_MESSAGE = 1;
/*     */   public static final int OTHER_MESSAGE = 2;
/*     */   private char _deli;
/*     */ 
/*     */   public HiGetTxnCode()
/*     */   {
/*  23 */     this._deli = '\28'; }
/*     */ 
/*     */   public char getDeli() {
/*  26 */     return this._deli;
/*     */   }
/*     */ 
/*     */   public void setDeli(char deli) {
/*  30 */     this._deli = deli;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext arg0) throws HiException {
/*  34 */     getTxnCode(arg0);
/*     */   }
/*     */ 
/*     */   public void getTxnCode(HiMessageContext arg0) throws HiException {
/*  38 */     HiMessage msg = arg0.getCurrentMsg();
/*  39 */     Logger log = HiLog.getLogger(arg0.getCurrentMsg());
/*     */ 
/*  41 */     HiByteBuffer buffer = (HiByteBuffer)msg.getBody();
/*     */ 
/*  43 */     String txnCode = null;
/*  44 */     switch (getType(arg0, this._deli))
/*     */     {
/*     */     case 1:
/*  47 */       String[] strs = StringUtils.splitPreserveAllTokens(buffer.toString(), '\28');
/*     */ 
/*  49 */       if (strs.length < 3) {
/*  50 */         throw new HiException("310007", "dibao");
/*     */       }
/*     */ 
/*  54 */       log.info("迪宝第二域:" + strs[1]);
/*  55 */       if ("RESP".equalsIgnoreCase(StringUtils.substring(strs[1], 0, 4))) {
/*  56 */         txnCode = strs[5];
/*     */       }
/*     */       else {
/*  59 */         txnCode = strs[2] + "D";
/*     */       }
/*  61 */       break;
/*     */     case 0:
/*  63 */       if (buffer.length() < 13) {
/*  64 */         throw new HiException("310007", "NCR");
/*     */       }
/*     */ 
/*  67 */       txnCode = buffer.substr(10, 3) + "N";
/*  68 */       break;
/*     */     default:
/*  70 */       throw new HiException("310007", "ATM");
/*     */     }
/*  72 */     msg.setHeadItem("STC", txnCode);
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException
/*     */   {
/*     */   }
/*     */ 
/*     */   public static int getType(HiMessageContext ctx, char deli) {
/*  80 */     HiMessage msg = ctx.getCurrentMsg();
/*  81 */     HiByteBuffer buffer = (HiByteBuffer)msg.getBody();
/*  82 */     if (buffer.charAt(0) == deli) {
/*  83 */       return 1;
/*     */     }
/*  85 */     if (buffer.length() < 5) {
/*  86 */       return 2;
/*     */     }
/*  88 */     String s1 = buffer.substr(0, 5);
/*  89 */     if (StringUtils.equalsIgnoreCase(s1, "ABCDE")) {
/*  90 */       return 0;
/*     */     }
/*  92 */     return 2;
/*     */   }
/*     */ 
/*     */   public static int getType(HiMessageContext ctx)
/*     */   {
/*  98 */     HiMessage msg = ctx.getCurrentMsg();
/*  99 */     HiByteBuffer buffer = (HiByteBuffer)msg.getBody();
/* 100 */     if (buffer.charAt(0) == 28) {
/* 101 */       return 1;
/*     */     }
/* 103 */     if (buffer.length() < 5) {
/* 104 */       return 2;
/*     */     }
/* 106 */     String s1 = buffer.substr(0, 5);
/* 107 */     if (StringUtils.equalsIgnoreCase(s1, "ABCDE")) {
/* 108 */       return 0;
/*     */     }
/* 110 */     return 2;
/*     */   }
/*     */ }