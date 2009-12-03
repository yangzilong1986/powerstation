/*    */ package com.hisun.atc.bat;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class HiPriBatchInfo
/*    */   implements Cloneable
/*    */ {
/*    */   private static final String KEY = "_BatchInfo";
/*    */   public HiPubBatchInfo pubBatchInfo;
/*    */   public String applyLogNoFlag;
/*    */   public String fileCurr;
/*    */   public String filIn;
/*    */   public String filOut;
/*    */   public String formatName;
/*    */   public String tableName;
/*    */   public String txnCode;
/*    */ 
/*    */   public HiPriBatchInfo()
/*    */   {
/* 18 */     this.pubBatchInfo = new HiPubBatchInfo();
/*    */   }
/*    */ 
/*    */   public static HiPriBatchInfo getPriBatchInfo(HiMessageContext ctx)
/*    */   {
/* 15 */     return ((HiPriBatchInfo)ctx.getBaseSource("_BatchInfo"));
/*    */   }
/*    */ 
/*    */   public HiPriBatchInfo cloneProperty()
/*    */   {
/*    */     try
/*    */     {
/* 57 */       return ((HiPriBatchInfo)super.clone());
/*    */     } catch (CloneNotSupportedException e) {
/*    */     }
/* 60 */     return null;
/*    */   }
/*    */ 
/*    */   public void getValuesFromETF(HiETF root) throws HiException
/*    */   {
/* 65 */     this.filIn = root.getChildValue("filIn");
/* 66 */     this.filOut = root.getChildValue("filOut");
/* 67 */     this.formatName = root.getChildValue("formatName");
/* 68 */     this.tableName = root.getChildValue("tableName");
/* 69 */     this.txnCode = root.getChildValue("txnCode");
/* 70 */     this.applyLogNoFlag = root.getChildValue("applyLogNoFlag");
/* 71 */     this.pubBatchInfo.setValuesFromETF(root);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 76 */     return "PriBatchInfo.applyLogNoFlag=[" + this.applyLogNoFlag + "]\n" + "PriBatchInfo.fileCurr=[" + this.fileCurr + "]\n" + "PriBatchInfo.filIn=[" + this.filIn + "]\n" + "PriBatchInfo.filOut=[" + this.filOut + "]\n" + "PriBatchInfo.formatName=[" + this.formatName + "]\n" + "PriBatchInfo.tableName=[" + this.tableName + "]\n" + "PriBatchInfo.txnCode=[" + this.txnCode + "]\n" + this.pubBatchInfo;
/*    */   }
/*    */ 
/*    */   public void setETFValues(HiETF root)
/*    */     throws HiException
/*    */   {
/* 87 */     root.setChildValue("filIn", this.filIn);
/* 88 */     root.setChildValue("filOut", this.filOut);
/* 89 */     root.setChildValue("formatName", this.formatName);
/* 90 */     root.setChildValue("tableName", this.tableName);
/* 91 */     root.setChildValue("txnCode", this.txnCode);
/* 92 */     root.setChildValue("applyLogNoFlag", this.applyLogNoFlag);
/* 93 */     this.pubBatchInfo.setETFValues(root);
/*    */   }
/*    */ 
/*    */   public void setPriBatchInfo(HiMessageContext ctx)
/*    */   {
/* 98 */     ctx.setBaseSource("_BatchInfo", this);
/*    */   }
/*    */ 
/*    */   public void setProperty(String name, String value)
/*    */   {
/*    */   }
/*    */ }