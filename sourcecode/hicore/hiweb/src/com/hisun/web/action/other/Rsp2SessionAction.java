 package com.hisun.web.action.other;

 import com.hisun.message.HiETF;
 import com.hisun.web.action.BaseAction;

 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 
 public class Rsp2SessionAction extends BaseAction
 {
   protected void storeResult(HttpServletRequest request, HiETF rspetf)
   {
     HttpSession session = request.getSession();
     session.setAttribute("etfsrc", this.source);
     session.setAttribute("group", this.group);
     session.setAttribute("etf", rspetf);
     session.setAttribute("msg", rspetf.getChildValue("RSP_MSG"));
   }
 }