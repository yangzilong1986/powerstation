 package com.hisun.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.util.HiParaParser;
 import org.dom4j.Element;
 
 public class HiInitTransParamHandler
   implements IServerInitListener
 {
   public void serverInit(ServerEvent arg0)
     throws HiException
   {
     HiContext ctx1 = null;
     ctx1 = arg0.getServerContext().getFirstChild();
 
     for (; ctx1 != null; ctx1 = ctx1.getNextBrother()) {
       Element element = (Element)ctx1.getProperty("CONFIGDECLARE", "PARAFILE");
 
       if (element != null) {
         String strAppName = ctx1.getStrProp("app_name");
         HiParaParser.setAppParam(ctx1, strAppName, element);
         HiContext ctx2 = ctx1.getFirstChild();
         for (; ctx2 != null; ctx2 = ctx2.getNextBrother()) {
           String code = ctx2.getStrProp("trans_code");
           HiParaParser.setTrnParam(ctx2, strAppName, code, element);
           if (arg0.getLog().isDebugEnabled()) {
             arg0.getLog().debug("交易:[" + code + "] " + ctx2);
           }
         }
         if (arg0.getLog().isDebugEnabled())
           arg0.getLog().debug("应用:[" + strAppName + "] " + ctx1);
       }
     }
   }
 }