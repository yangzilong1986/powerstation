/*    */ package com.hisun.web.action.other;
/*    */ 
/*    */ import com.hisun.message.HiETF;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class Req2SessionAction extends QryAction
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   protected void storeReqMsg(HttpServletRequest request, HiETF reqetf)
/*    */   {
/* 17 */     HttpSession session = request.getSession();
/* 18 */     session.setAttribute("reqetf", reqetf);
/*    */   }
/*    */ }