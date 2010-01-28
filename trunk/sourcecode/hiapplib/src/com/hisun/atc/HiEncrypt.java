 package com.hisun.atc;
 
 import com.hisun.crypt.Encryptor;
 import com.hisun.crypt.des.DESCryptorFactory;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiConvHelper;
 
 public class HiEncrypt
 {
   private Encryptor encryptor = null;
 
   public HiEncrypt() {
     DESCryptorFactory factory = new DESCryptorFactory();
     this.encryptor = factory.getEncryptor();
     this.encryptor.setKey(factory.getDefaultEncryptKey());
   }
 
   public int DESEncrypt(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF root = ctx.getCurrentMsg().getETFBody();
     String node = argsMap.get("node");
 
     String data = root.getChildValue(node);
     if ((data != null) && (data.length() > 0)) {
       try {
         byte[] ret = this.encryptor.encrypt(data.getBytes());
         root.setChildValue(node, HiConvHelper.binToAscStr(ret));
       } catch (Exception e) {
         throw new HiException(e);
       }
     }
 
     return 0;
   }
 }