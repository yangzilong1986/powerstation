 package com.hisun.mon.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 
 public class HiSetOutETFBody
   implements IHandler
 {
   final Logger log = (Logger)HiContext.getCurrentContext()
     .getProperty("SVR.log");
 
   private String name = "ETFOUTLIST";
 
   public void setName(String name) {
     this.name = name;
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
 
     Object body = msg.getObjectHeadItem(this.name);
     if (body == null) {
       return;
     }
 
     msg.setBody(body);
   }
 }