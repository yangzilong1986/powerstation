/*    */ package com.hisun.component.web;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Enumeration;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class GetAllLoginUser
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 30 */     HiMessage msg = ctx.getCurrentMsg();
/* 31 */     Logger log = HiLog.getLogger(msg);
/* 32 */     ServletContext webContext = (ServletContext)msg.getObjectHeadItem("_WEB_APPLICATION");
/* 33 */     ArrayList list = (ArrayList)webContext.getAttribute("_SESSION_LIST");
/* 34 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 35 */     HiETF group = null;
/* 36 */     String groupName = args.get("GROUP");
/* 37 */     if (StringUtils.isEmpty(groupName)) {
/* 38 */       groupName = "GROUP";
/*    */     }
/* 40 */     log.info("[" + list.size() + "][" + groupName + "]");
/* 41 */     int j = 1;
/* 42 */     for (int i = 0; i < list.size(); ++i) {
/* 43 */       HttpSession session = (HttpSession)list.get(i);
/* 44 */       Enumeration en = session.getAttributeNames();
/* 45 */       if (!(en.hasMoreElements())) {
/* 46 */         log.info("[" + session.getId() + "][not elements]");
/*    */       }
/*    */       else {
/* 49 */         group = root.addNode(groupName + "_" + j);
/* 50 */         ++j;
/* 51 */         while (en.hasMoreElements()) {
/* 52 */           String name = (String)en.nextElement();
/* 53 */           String value = (String)session.getAttribute(name);
/* 54 */           log.info("[" + name + "][" + value + "]");
/* 55 */           if (value == null) {
/*    */             continue;
/*    */           }
/* 58 */           group.setChildValue(name, value); }
/*    */       }
/*    */     }
/* 61 */     return 0;
/*    */   }
/*    */ }