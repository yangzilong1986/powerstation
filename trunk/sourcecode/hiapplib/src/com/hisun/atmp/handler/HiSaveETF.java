 package com.hisun.atmp.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 
 public class HiSaveETF
   implements IHandler
 {
   private String key;
 
   public HiSaveETF()
   {
     this.key = "POS_EXTRA"; }
 
   public void process(HiMessageContext arg0) throws HiException {
     HiMessage msg = arg0.getCurrentMsg();
     HiETF root = msg.getETFBody();
 
     arg0.setProperty(this.key, root);
   }
 
   public String getKey() {
     return this.key;
   }
 
   public void setKey(String key) {
     this.key = key;
   }
 }