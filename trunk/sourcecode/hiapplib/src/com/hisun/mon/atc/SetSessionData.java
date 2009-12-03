/*    */ package com.hisun.mon.atc;
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
/* 23 */     HiMessage msg = ctx.getCurrentMsg();
/* 24 */     HttpSession session = (HttpSession)msg
/* 25 */       .getObjectHeadItem("_WEB_SESSION");
/*    */ 
/* 27 */     HiETF root = msg.getETFBody();
/* 28 */     String objNam = args.get("ObjNam");
/*    */ 
/* 30 */     for (int i = 0; i < args.size(); ++i) {
/* 31 */       String name = args.getName(i).toUpperCase();
/* 32 */       if ("ObjNam".equalsIgnoreCase(name)) {
/*    */         continue;
/*    */       }
/*    */ 
/* 36 */       String value = args.getValue(i);
/* 37 */       if (value == null) {
/* 38 */         value = "";
/*    */       }
/* 40 */       if (StringUtils.isNotBlank(objNam)) {
/* 41 */         HashMap map = (HashMap)session.getAttribute(
/* 42 */           objNam.toUpperCase());
/* 43 */         if (map == null) {
/* 44 */           map = new HashMap();
/* 45 */           session.setAttribute(objNam.toUpperCase(), map);
/*    */         }
/* 47 */         map.put(name, value);
/*    */ 
/* 49 */         HiETF group = root.getChildNode(objNam);
/* 50 */         if (group == null) {
/* 51 */           group = root.addNode(objNam);
/*    */         }
/* 53 */         group.setChildValue(name, value);
/*    */       } else {
/* 55 */         session.setAttribute(name, value);
/*    */ 
/* 57 */         HiETF sessionRoot = root.getChildNode("SESSION");
/* 58 */         if (sessionRoot == null) {
/* 59 */           sessionRoot = root.addNode("SESSION");
/*    */         }
/* 61 */         sessionRoot.setChildValue(name, value);
/*    */       }
/*    */     }
/* 64 */     Logger log = HiLog.getLogger(msg);
/* 65 */     Enumeration en = session.getAttributeNames();
/* 66 */     while (en.hasMoreElements()) {
/* 67 */       String name = (String)en.nextElement();
/* 68 */       log.info("[" + name + "][" + session.getAttribute(name) + "]");
/*    */     }
/*    */ 
/* 72 */     return 0;
/*    */   }
/*    */ }