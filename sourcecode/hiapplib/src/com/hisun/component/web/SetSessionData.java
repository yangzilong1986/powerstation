/*    */ package com.hisun.component.web;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.util.Enumeration;
/*    */ import java.util.HashMap;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class SetSessionData
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 30 */     HiMessage msg = ctx.getCurrentMsg();
/* 31 */     HttpSession session = (HttpSession)msg.getObjectHeadItem("_WEB_SESSION");
/*    */ 
/* 34 */     HiETF root = msg.getETFBody();
/* 35 */     String objNam = args.get("ObjNam");
/*    */ 
/* 37 */     for (int i = 0; i < args.size(); ++i) {
/* 38 */       String name = args.getName(i).toUpperCase();
/* 39 */       if ("ObjNam".equalsIgnoreCase(name)) {
/*    */         continue;
/*    */       }
/*    */ 
/* 43 */       String value = args.getValue(i);
/* 44 */       if (StringUtils.isBlank(value)) {
/*    */         continue;
/*    */       }
/* 47 */       if (StringUtils.isNotBlank(objNam)) {
/* 48 */         HashMap map = (HashMap)session.getAttribute(objNam.toUpperCase());
/*    */ 
/* 50 */         if (map == null) {
/* 51 */           map = new HashMap();
/* 52 */           session.setAttribute(objNam.toUpperCase(), map);
/*    */         }
/* 54 */         map.put(name, value);
/*    */ 
/* 56 */         HiETF group = root.getChildNode(objNam);
/* 57 */         if (group == null) {
/* 58 */           group = root.addNode(objNam);
/*    */         }
/* 60 */         group.setChildValue(name, value);
/*    */       } else {
/* 62 */         session.setAttribute(name, value);
/*    */ 
/* 64 */         HiETF sessionRoot = root.getChildNode("SESSION");
/* 65 */         if (sessionRoot == null) {
/* 66 */           sessionRoot = root.addNode("SESSION");
/*    */         }
/* 68 */         sessionRoot.setChildValue(name, value);
/*    */       }
/*    */     }
/* 71 */     Logger log = HiLog.getLogger(msg);
/* 72 */     Enumeration en = session.getAttributeNames();
/* 73 */     while (en.hasMoreElements()) {
/* 74 */       String name = (String)en.nextElement();
/* 75 */       log.info("[" + name + "][" + session.getAttribute(name) + "]");
/*    */     }
/*    */ 
/* 78 */     return 0;
/*    */   }
/*    */ }