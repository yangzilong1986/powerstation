 package com.hisun.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiETFUtils;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.message.HiXmlETF;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import java.io.PrintStream;
 import org.apache.commons.lang.StringUtils;
 import org.w3c.dom.Document;
 
 public class HiBodyTypeConvert
   implements IHandler
 {
   public void etf2bb(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Object o = msg.getBody();
     if (o instanceof HiETF) {
       HiETF root = (HiETF)o;
       msg.setBody(new HiByteBuffer(root.toString().getBytes()));
     }
   }
 
   public void bb2etf(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Object o = msg.getBody();
     if (o instanceof HiByteBuffer) {
       HiByteBuffer buf = (HiByteBuffer)o;
       msg.setBody(HiETFFactory.createETF(buf.toString()));
     }
   }
 
   public void etf2dom(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     if (msg.getBody() instanceof HiETF) {
       HiXmlETF etfBody = (HiXmlETF)msg.getETFBody();
       Document doc = HiETFUtils.convertToDOM(etfBody);
       msg.setBody(doc);
       System.out.println("etf01" + etfBody);
       System.out.println("doc01:" + doc);
     }
   }
 
   public void dom2etf(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     if (msg.getBody() instanceof Document) {
       Document doc = (Document)msg.getBody();
       msg.setBody(HiETFUtils.convertToXmlETF(doc));
     }
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     String sch = msg.getHeadItem("SCH");
     if (StringUtils.equals(sch, "rq"))
       etf2dom(ctx);
     else
       dom2etf(ctx);
   }
 }