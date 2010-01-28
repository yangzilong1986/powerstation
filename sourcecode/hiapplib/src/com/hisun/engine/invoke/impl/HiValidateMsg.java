 package com.hisun.engine.invoke.impl;
 
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import java.util.ArrayList;
 
 public class HiValidateMsg
 {
   private ArrayList _errMsg;
   private static final String VALIDATE_MSG_KEY = "_VALIDATE_MSG_KEY";
 
   public HiValidateMsg()
   {
     this._errMsg = new ArrayList();
   }
 
   public static HiValidateMsg get(HiContext ctx)
   {
     if (ctx.containsProperty("_VALIDATE_MSG_KEY")) {
       return ((HiValidateMsg)ctx.getProperty("_VALIDATE_MSG_KEY"));
     }
     HiValidateMsg msg = new HiValidateMsg();
     ctx.setProperty("_VALIDATE_MSG_KEY", msg);
     return msg;
   }
 
   public boolean isEmpty()
   {
     return (this._errMsg.size() == 0);
   }
 
   public void add(String code, String msg) {
     this._errMsg.add(new HiValidateMsgItem(code, msg));
   }
 
   public void dump(HiETF root) {
     int i = 0;
 
     for (i = 0; i < this._errMsg.size(); ++i) {
       HiValidateMsgItem item = (HiValidateMsgItem)this._errMsg.get(i);
       HiETF errRoot = root.addNode("ERR_MSG_" + (i + 1));
       errRoot.setChildValue("CODE", item.code);
       errRoot.setChildValue("MSG", item.msg);
     }
 
     if (i != 0)
       root.setChildValue("ERR_MSG_NUM", String.valueOf(i));
   }
 }