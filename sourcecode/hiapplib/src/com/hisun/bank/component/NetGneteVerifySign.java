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
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class NetGneteVerifySign
/*     */ {
/*     */   private Logger log1;
/*     */ 
/*     */   public NetGneteVerifySign()
/*     */   {
/*  17 */     this.log1 = HiLog.getLogger("callproc.trc");
/*     */   }
/*     */ 
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  37 */     String EncodeMsg = args.get("EncodeMsg");
/*  38 */     String SignMsg = args.get("SignMsg");
/*  39 */     String SendCertFile = args.get("SendCertFile");
/*  40 */     String RcvCertFile = args.get("RcvCertFile");
/*  41 */     String Password = args.get("Password");
/*     */ 
/*  43 */     String DecryptedMsg = "";
/*     */ 
/*  45 */     String OrderNo = args.get("OrderNo");
/*  46 */     String PayNo = args.get("PayNo");
/*  47 */     String PayAmount = args.get("PayAmount");
/*  48 */     String CurrCode = args.get("CurrCode");
/*  49 */     String SystemSSN = args.get("SystemSSN");
/*  50 */     String RespCode = args.get("RespCode");
/*  51 */     String SettDate = args.get("SettDate");
/*     */ 
/*  53 */     String OrderNoVar = "";
/*  54 */     String PayNoVar = "";
/*  55 */     String PayAmountVar = "";
/*  56 */     String CurrCodeVar = "";
/*  57 */     String SystemSSNVar = "";
/*  58 */     String RespCodeVar = "";
/*  59 */     String SettDateVar = "";
/*     */ 
/*  61 */     HiMessage msg = ctx.getCurrentMsg();
/*  62 */     HiETF root = msg.getETFBody();
/*     */ 
/*  64 */     NetTran obj = new NetTran();
/*  65 */     boolean ret = obj.DecryptMsg(EncodeMsg, SendCertFile, Password);
/*  66 */     if (ret == true)
/*     */     {
/*  68 */       DecryptedMsg = obj.getLastResult();
/*     */     }
/*     */     else
/*     */     {
/*  72 */       this.log1.info("DecryptMsg failed,errorMsg=" + obj.getLastErrMsg());
/*  73 */       return -1;
/*     */     }
/*     */ 
/*  76 */     ret = obj.VerifyMsg(SignMsg, DecryptedMsg, RcvCertFile);
/*  77 */     if (ret != true)
/*     */     {
/*  82 */       this.log1.info("VerifyMsg failed,errorMsg=" + obj.getLastErrMsg());
/*  83 */       return -1;
/*     */     }
/*  85 */     OrderNoVar = getContent(DecryptedMsg, "OrderNo");
/*  86 */     this.log1.info("商户订单号不超过20位: " + OrderNoVar + "<br>");
/*  87 */     PayNoVar = getContent(DecryptedMsg, "PayNo");
/*  88 */     this.log1.info("支付单号: " + PayNoVar + "<br>");
/*  89 */     PayAmountVar = getContent(DecryptedMsg, "PayAmount");
/*  90 */     this.log1.info("支付金额: " + PayAmountVar + "<br>");
/*  91 */     CurrCodeVar = getContent(DecryptedMsg, "CurrCode");
/*  92 */     this.log1.info("货币代码: " + CurrCodeVar + "<br>");
/*  93 */     SystemSSNVar = getContent(DecryptedMsg, "SystemSSN");
/*  94 */     this.log1.info("系统参考号: " + SystemSSNVar + "<br>");
/*  95 */     RespCodeVar = getContent(DecryptedMsg, "RespCode");
/*  96 */     this.log1.info("响应码: " + RespCodeVar + "<br>");
/*  97 */     SettDateVar = getContent(DecryptedMsg, "SettDate");
/*  98 */     this.log1.info("清算日期: " + SettDateVar + "<br>");
/*  99 */     String Reserved01 = getContent(DecryptedMsg, "Reserved01");
/* 100 */     this.log1.info("保留域1: " + Reserved01 + "<br>");
/* 101 */     String Reserved02 = getContent(DecryptedMsg, "Reserved02");
/* 102 */     this.log1.info("保留域2: " + Reserved02 + "<br>");
/*     */ 
/* 104 */     root.setChildValue(OrderNo, OrderNoVar);
/* 105 */     root.setChildValue(PayNo, PayNoVar);
/* 106 */     root.setChildValue(PayAmount, PayAmountVar);
/* 107 */     root.setChildValue(CurrCode, CurrCodeVar);
/* 108 */     root.setChildValue(SystemSSN, SystemSSNVar);
/* 109 */     root.setChildValue(RespCode, RespCodeVar);
/* 110 */     root.setChildValue(SettDate, SettDateVar);
/*     */ 
/* 112 */     return 0;
/*     */   }
/*     */ 
/*     */   public String getContent(String input, String para)
/*     */   {
/* 117 */     if ((input.equals("")) || (para.equals("")))
/*     */     {
/* 119 */       return "";
/*     */     }
/* 121 */     String vv = "";
/* 122 */     StringTokenizer st = new StringTokenizer(input, "&");
/*     */     do { if (!(st.hasMoreElements()))
/*     */         break label92;
/* 125 */       vv = st.nextToken(); }
/* 126 */     while ((vv.indexOf(para) == -1) || (!(vv.substring(0, vv.indexOf("=")).equals(para))));
/*     */ 
/* 128 */     vv = vv.substring(vv.indexOf("=") + 1);
/* 129 */     return vv;
/*     */ 
/* 132 */     label92: return "";
/*     */   }
/*     */ }