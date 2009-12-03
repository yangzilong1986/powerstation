/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.IServerInitListener;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.util.HiComponentParaParser;
/*    */ import com.hisun.util.HiXmlHelper;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class HiInitComponentParamHandler
/*    */   implements IServerInitListener
/*    */ {
/*    */   public void serverInit(ServerEvent arg0)
/*    */     throws HiException
/*    */   {
/* 22 */     Element element = null;
/*    */ 
/* 24 */     String componentConfig = HiContext.getRootContext().getStrProp("@SYS", "component.config");
/*    */ 
/* 26 */     if (componentConfig == null) {
/* 27 */       return;
/*    */     }
/*    */     try
/*    */     {
/* 31 */       element = HiXmlHelper.getRootElement(componentConfig);
/*    */     } catch (Exception e) {
/* 33 */       arg0.getLog().error("load component config file:[ " + componentConfig + "] failure:[" + e + "]", e);
/*    */ 
/* 36 */       return;
/*    */     }
/*    */ 
/* 39 */     if (element == null) {
/* 40 */       return;
/*    */     }
/*    */ 
/* 43 */     HiContext ctx1 = null;
/* 44 */     ctx1 = arg0.getServerContext().getFirstChild();
/*    */ 
/* 46 */     for (; ctx1 != null; ctx1 = ctx1.getNextBrother()) {
/* 47 */       String strAppName = ctx1.getStrProp("app_name");
/* 48 */       HiComponentParaParser.setAppParam(ctx1, strAppName, element);
/* 49 */       HiContext ctx2 = ctx1.getFirstChild();
/* 50 */       for (; ctx2 != null; ctx2 = ctx2.getNextBrother()) {
/* 51 */         String code = ctx2.getStrProp("trans_code");
/* 52 */         HiComponentParaParser.setTrnParam(ctx2, strAppName, code, element);
/*    */ 
/* 54 */         if (arg0.getLog().isDebugEnabled()) {
/* 55 */           arg0.getLog().debug("交易:[" + code + "] " + ctx2);
/*    */         }
/*    */       }
/* 58 */       if (arg0.getLog().isDebugEnabled())
/* 59 */         arg0.getLog().debug("应用:[" + strAppName + "] " + ctx1);
/*    */     }
/*    */   }
/*    */ }