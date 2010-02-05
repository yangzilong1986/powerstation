 package com.hisun.atc.bat;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessageContext;
 
 public class HiPriBatchInfo
   implements Cloneable
 {
   private static final String KEY = "_BatchInfo";
   public HiPubBatchInfo pubBatchInfo;
   public String applyLogNoFlag;
   public String fileCurr;
   public String filIn;
   public String filOut;
   public String formatName;
   public String tableName;
   public String txnCode;
 
   public HiPriBatchInfo()
   {
     this.pubBatchInfo = new HiPubBatchInfo();
   }
 
   public static HiPriBatchInfo getPriBatchInfo(HiMessageContext ctx)
   {
     return ((HiPriBatchInfo)ctx.getBaseSource("_BatchInfo"));
   }
 
   public HiPriBatchInfo cloneProperty()
   {
     try
     {
       return ((HiPriBatchInfo)super.clone());
     } catch (CloneNotSupportedException e) {
     }
     return null;
   }
 
   public void getValuesFromETF(HiETF root) throws HiException
   {
     this.filIn = root.getChildValue("filIn");
     this.filOut = root.getChildValue("filOut");
     this.formatName = root.getChildValue("formatName");
     this.tableName = root.getChildValue("tableName");
     this.txnCode = root.getChildValue("txnCode");
     this.applyLogNoFlag = root.getChildValue("applyLogNoFlag");
     this.pubBatchInfo.setValuesFromETF(root);
   }
 
   public String toString()
   {
     return "PriBatchInfo.applyLogNoFlag=[" + this.applyLogNoFlag + "]\n" + "PriBatchInfo.fileCurr=[" + this.fileCurr + "]\n" + "PriBatchInfo.filIn=[" + this.filIn + "]\n" + "PriBatchInfo.filOut=[" + this.filOut + "]\n" + "PriBatchInfo.formatName=[" + this.formatName + "]\n" + "PriBatchInfo.tableName=[" + this.tableName + "]\n" + "PriBatchInfo.txnCode=[" + this.txnCode + "]\n" + this.pubBatchInfo;
   }
 
   public void setETFValues(HiETF root)
     throws HiException
   {
     root.setChildValue("filIn", this.filIn);
     root.setChildValue("filOut", this.filOut);
     root.setChildValue("formatName", this.formatName);
     root.setChildValue("tableName", this.tableName);
     root.setChildValue("txnCode", this.txnCode);
     root.setChildValue("applyLogNoFlag", this.applyLogNoFlag);
     this.pubBatchInfo.setETFValues(root);
   }
 
   public void setPriBatchInfo(HiMessageContext ctx)
   {
     ctx.setBaseSource("_BatchInfo", this);
   }
 
   public void setProperty(String name, String value)
   {
   }
 }