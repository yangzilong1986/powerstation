/*    */ package com.hisun.atc;
/*    */ 
/*    */ import com.hisun.crypt.Encryptor;
/*    */ import com.hisun.crypt.des.DESCryptorFactory;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiConvHelper;
/*    */ 
/*    */ public class HiEncrypt
/*    */ {
/* 12 */   private Encryptor encryptor = null;
/*    */ 
/*    */   public HiEncrypt() {
/* 15 */     DESCryptorFactory factory = new DESCryptorFactory();
/* 16 */     this.encryptor = factory.getEncryptor();
/* 17 */     this.encryptor.setKey(factory.getDefaultEncryptKey());
/*    */   }
/*    */ 
/*    */   public int DESEncrypt(HiATLParam argsMap, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 30 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 31 */     String node = argsMap.get("node");
/*    */ 
/* 33 */     String data = root.getChildValue(node);
/* 34 */     if ((data != null) && (data.length() > 0)) {
/*    */       try {
/* 36 */         byte[] ret = this.encryptor.encrypt(data.getBytes());
/* 37 */         root.setChildValue(node, HiConvHelper.binToAscStr(ret));
/*    */       } catch (Exception e) {
/* 39 */         throw new HiException(e);
/*    */       }
/*    */     }
/*    */ 
/* 43 */     return 0;
/*    */   }
/*    */ }