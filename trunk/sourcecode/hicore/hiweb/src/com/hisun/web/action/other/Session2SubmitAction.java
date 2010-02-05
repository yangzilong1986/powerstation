 package com.hisun.web.action.other;

 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;

 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 
 public class Session2SubmitAction extends QryAction
 {
   protected HiETF beforeProcess(HttpServletRequest request, Logger _log)
     throws HiException
   {
     HiETF etf = (HiETF)request.getSession().getAttribute("reqetf");
 
     super.beforeProcess(request, _log);
     return etf;
   }
 }