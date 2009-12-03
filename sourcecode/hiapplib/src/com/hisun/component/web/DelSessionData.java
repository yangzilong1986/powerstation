/*    */ package com.hisun.component.web;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class DelSessionData
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 29 */     HiMessage msg = ctx.getCurrentMsg();
/* 30 */     HttpSession session = (HttpSession)msg.getObjectHeadItem("_WEB_SESSION");
/*    */ 
/* 32 */     HiETF root = msg.getETFBody();
/* 33 */     String objNam = args.get("ObjNam");
/* 34 */     if (StringUtils.isNotBlank(objNam)) {
/* 35 */       session.removeAttribute(objNam.toUpperCase());
/* 36 */       root.removeChildNode(objNam);
/* 37 */       return 0;
/*    */     }
/*    */ 
/* 40 */     for (int i = 0; i < args.size(); ++i) {
/* 41 */       String name = args.getName(i).toUpperCase();
/*    */ 
/* 43 */       String value = args.getValue(i);
/* 44 */       session.removeAttribute(name);
/* 45 */       HiETF sessionRoot = root.getChildNode("SESSION");
/* 46 */       if (sessionRoot != null) {
/* 47 */         sessionRoot.removeChildNode(name);
/*    */       }
/*    */     }
/* 50 */     return 0;
/*    */   }
/*    */ }