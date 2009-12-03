/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.IServerInitListener;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.util.HiParaParser;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class HiInitTransParamHandler
/*    */   implements IServerInitListener
/*    */ {
/*    */   public void serverInit(ServerEvent arg0)
/*    */     throws HiException
/*    */   {
/* 23 */     HiContext ctx1 = null;
/* 24 */     ctx1 = arg0.getServerContext().getFirstChild();
/*    */ 
/* 26 */     for (; ctx1 != null; ctx1 = ctx1.getNextBrother()) {
/* 27 */       Element element = (Element)ctx1.getProperty("CONFIGDECLARE", "PARAFILE");
/*    */ 
/* 29 */       if (element != null) {
/* 30 */         String strAppName = ctx1.getStrProp("app_name");
/* 31 */         HiParaParser.setAppParam(ctx1, strAppName, element);
/* 32 */         HiContext ctx2 = ctx1.getFirstChild();
/* 33 */         for (; ctx2 != null; ctx2 = ctx2.getNextBrother()) {
/* 34 */           String code = ctx2.getStrProp("trans_code");
/* 35 */           HiParaParser.setTrnParam(ctx2, strAppName, code, element);
/* 36 */           if (arg0.getLog().isDebugEnabled()) {
/* 37 */             arg0.getLog().debug("交易:[" + code + "] " + ctx2);
/*    */           }
/*    */         }
/* 40 */         if (arg0.getLog().isDebugEnabled())
/* 41 */           arg0.getLog().debug("应用:[" + strAppName + "] " + ctx1);
/*    */       }
/*    */     }
/*    */   }
/*    */ }