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
/*    */ import java.util.HashMap;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ public class GetOnLineFriends
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 32 */     HiMessage msg = ctx.getCurrentMsg();
/* 33 */     Logger log = HiLog.getLogger(msg);
/* 34 */     ServletContext webContext = (ServletContext)msg.getObjectHeadItem("_WEB_APPLICATION");
/* 35 */     ArrayList list = (ArrayList)webContext.getAttribute("_SESSION_LIST");
/* 36 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 37 */     HiETF group = null;
/* 38 */     String groupName = args.get("GROUP");
/* 39 */     if (StringUtils.isEmpty(groupName)) {
/* 40 */       groupName = "GROUP";
/*    */     }
/* 42 */     log.info("[" + list.size() + "][" + groupName + "]");
/* 43 */     ArrayList usrList = new ArrayList();
/* 44 */     for (int i = 0; i < list.size(); ++i) {
/* 45 */       HttpSession session = (HttpSession)list.get(i);
/* 46 */       Enumeration en = null;
/*    */       try {
/* 48 */         en = session.getAttributeNames();
/*    */       } catch (IllegalStateException e) {
/* 50 */         break label248:
/*    */       }
/* 52 */       if (!(en.hasMoreElements())) {
/* 53 */         log.info("[" + session.getId() + "][not elements]");
/*    */       }
/*    */       else {
/* 56 */         HashMap usrInf = (HashMap)session.getAttribute("USR_INF");
/* 57 */         if (usrInf == null) {
/*    */           continue;
/*    */         }
/* 60 */         label248: usrList.add(usrInf.get("USR_ID"));
/*    */       }
/*    */     }
/* 63 */     int num = NumberUtils.toInt(root.getChildValue("REC_NUM"));
/*    */ 
/* 65 */     for (int i = 0; i < num; ++i) {
/* 66 */       group = root.getChildNode(groupName + "_" + (i + 1));
/* 67 */       String usrId = group.getChildValue("FRD_ID");
/* 68 */       if (usrList.contains(usrId))
/* 69 */         group.setChildValue("FLG", "1");
/*    */       else {
/* 71 */         group.setChildValue("FLG", "0");
/*    */       }
/*    */     }
/* 74 */     return 0;
/*    */   }
/*    */ }