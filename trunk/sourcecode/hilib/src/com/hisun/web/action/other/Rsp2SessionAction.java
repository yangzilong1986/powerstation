/*    */ package com.hisun.web.action.other;
/*    */ 
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.web.action.BaseAction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class Rsp2SessionAction extends BaseAction
/*    */ {
/*    */   protected void storeResult(HttpServletRequest request, HiETF rspetf)
/*    */   {
/* 13 */     HttpSession session = request.getSession();
/* 14 */     session.setAttribute("etfsrc", this.source);
/* 15 */     session.setAttribute("group", this.group);
/* 16 */     session.setAttribute("etf", rspetf);
/* 17 */     session.setAttribute("msg", rspetf.getChildValue("RSP_MSG"));
/*    */   }
/*    */ }