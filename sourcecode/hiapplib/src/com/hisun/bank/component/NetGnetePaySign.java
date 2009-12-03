/*     */ package com.hisun.bank.component;
/*     */ 
/*     */ import com.EasyLink.OpenVendorV34.NetTran;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ 
/*     */ public class NetGnetePaySign
/*     */ {
/*     */   private Logger log1;
/*     */ 
/*     */   public NetGnetePaySign()
/*     */   {
/*  15 */     this.log1 = HiLog.getLogger("callproc.trc");
/*     */   }
/*     */ 
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  34 */       String MerId = args.get("MerId");
/*  35 */       String OrderNo = args.get("OrderNo");
/*  36 */       String OrderAmount = args.get("OrderAmount");
/*  37 */       String CallBackUrl = args.get("CallBackUrl");
/*  38 */       String BankCode = args.get("BankCode");
/*  39 */       String SendCertFile = args.get("SendCertFile");
/*  40 */       String RcvCertFile = args.get("RcvCertFile");
/*  41 */       String Password = args.get("Password");
/*     */ 
/*  43 */       String EncryptedMsg = args.get("EncryptedMsg");
/*  44 */       String SignedMsg = args.get("SignedMsg");
/*     */ 
/*  46 */       this.log1.info("MerId=" + MerId);
/*  47 */       this.log1.info("OrderNo=" + OrderNo);
/*  48 */       this.log1.info("OrderAmount=" + OrderAmount);
/*  49 */       this.log1.info("CallBackUrl=" + CallBackUrl);
/*  50 */       this.log1.info("BankCode=" + BankCode);
/*  51 */       this.log1.info("SendCertFile=" + SendCertFile);
/*  52 */       this.log1.info("RcvCertFile=" + RcvCertFile);
/*  53 */       this.log1.info("Password=" + Password);
/*  54 */       this.log1.info("EncryptedMsg=" + EncryptedMsg);
/*  55 */       this.log1.info("SignedMsg=" + SignedMsg);
/*     */ 
/*  57 */       String EncryptedMsgVar = "";
/*  58 */       String SignedMsgVar = "";
/*     */ 
/*  60 */       HiMessage msg = ctx.getCurrentMsg();
/*  61 */       HiETF root = msg.getETFBody();
/*     */ 
/*  63 */       String SourceText = "MerId=" + MerId + "&" + "OrderNo=" + OrderNo + "&" + "OrderAmount=" + OrderAmount + "&" + "CurrCode=CNY&" + "CallBackUrl=" + CallBackUrl + "&" + "ResultMode=0&" + "Reserved01=&" + "Reserved02=";
/*     */ 
/*  72 */       NetTran obj = new NetTran();
/*  73 */       this.log1.info("SourceText=" + SourceText);
/*     */ 
/*  76 */       boolean ret = obj.EncryptMsg(SourceText, RcvCertFile);
/*  77 */       if (ret == true)
/*     */       {
/*  79 */         EncryptedMsgVar = obj.getLastResult();
/*  80 */         this.log1.info("EncryptedMsgVar=" + EncryptedMsgVar);
/*     */       }
/*     */       else
/*     */       {
/*  84 */         this.log1.info("EncryptMsg() Return:" + obj.getLastErrMsg() + "<br>");
/*  85 */         return -1;
/*     */       }
/*     */ 
/*  89 */       ret = obj.SignMsg(SourceText, SendCertFile, Password);
/*  90 */       if (ret == true)
/*     */       {
/*  92 */         SignedMsgVar = obj.getLastResult();
/*  93 */         this.log1.info("SignedMsgVar=" + SignedMsgVar);
/*     */       }
/*     */       else
/*     */       {
/*  97 */         this.log1.info("SignMsg() Return:" + obj.getLastErrMsg() + "<br>");
/*  98 */         return -1;
/*     */       }
/*     */ 
/* 101 */       root.setChildValue(EncryptedMsg, EncryptedMsgVar);
/* 102 */       root.setChildValue(SignedMsg, SignedMsgVar);
/*     */ 
/* 104 */       return 0;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 108 */       this.log1.error(e.getMessage());
/*     */     }
/* 110 */     return -1;
/*     */   }
/*     */ }