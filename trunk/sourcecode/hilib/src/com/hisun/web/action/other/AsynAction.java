/*    */ package com.hisun.web.action.other;
/*    */ 
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.web.action.BaseAction;
/*    */ import com.hisun.web.service.HiCallHostService;
/*    */ import com.hisun.web.service.HiLogFactory;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.RequestDispatcher;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.struts2.ServletActionContext;
/*    */ 
/*    */ public class AsynAction extends BaseAction
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public String execute()
/*    */     throws Exception
/*    */   {
/* 20 */     Logger _log = this.logFactory.getLogger();
/* 21 */     if (_log.isDebugEnabled()) _log.debug("AsynAction execute starting");
/* 22 */     HttpServletRequest request = ServletActionContext.getRequest();
/* 23 */     HttpServletResponse response = ServletActionContext.getResponse();
/* 24 */     ServletContext context = ServletActionContext.getServletContext();
/*    */ 
/* 27 */     HiETF etf = beforeProcess(request, _log);
/*    */ 
/* 30 */     HiETF rspetf = null;
/* 31 */     if (!(StringUtils.isEmpty(this.txncode))) {
/* 32 */       rspetf = getCallHostService().callhost(this.txncode, etf, context);
/*    */     }
/*    */     else {
/* 35 */       rspetf = etf;
/* 36 */       rspetf.setChildValue("RSP_CD", "000000");
/*    */     }
/*    */ 
/* 40 */     boolean flag = endProcess(request, rspetf, _log);
/* 41 */     request.setAttribute("etfsrc", this.source);
/* 42 */     request.setAttribute("group", this.group);
/* 43 */     request.setAttribute("etf", rspetf);
/* 44 */     if (flag) {
/* 45 */       response.setContentType("text/xml");
/* 46 */       response.setCharacterEncoding("gb2312");
/* 47 */       response.getWriter().write("<?xml version=\"1.0\" encoding=\"gb2312\" ?>" + rspetf.toString());
/* 48 */       response.getWriter().flush();
/*    */ 
/* 50 */       if (this.output != null) {
/* 51 */         int index = StringUtils.indexOf(this.output, "action");
/* 52 */         if (index >= 0) {
/* 53 */           response.sendRedirect(this.output);
/*    */         }
/*    */         else {
/* 56 */           ServletActionContext.getServletContext().getRequestDispatcher(this.output).forward(request, response);
/*    */         }
/* 58 */         return "";
/*    */       }
/* 60 */       return "success";
/*    */     }
/*    */ 
/* 63 */     return "";
/*    */   }
/*    */ }