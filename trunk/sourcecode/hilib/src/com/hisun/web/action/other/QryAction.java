/*    */ package com.hisun.web.action.other;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.web.action.BaseAction;
/*    */ import com.hisun.web.tag.Page;
/*    */ import com.hisun.web.tag.PageData;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ public class QryAction extends BaseAction
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Page page;
/*    */ 
/*    */   public QryAction()
/*    */   {
/* 23 */     this.page = null;
/*    */   }
/*    */ 
/*    */   protected HiETF beforeProcess(HttpServletRequest request, Logger _log) throws HiException {
/* 27 */     if (_log.isDebugEnabled())
/* 28 */       _log.debug("QryAction constuctETF doing");
/* 29 */     HiETF etf = super.beforeProcess(request, _log);
/* 30 */     this.page = new Page();
/* 31 */     int i = 1;
/*    */     try {
/* 33 */       i = Integer.parseInt(request.getParameter("page"));
/*    */     } catch (NumberFormatException e) {
/* 35 */       i = 1;
/*    */     }
/* 37 */     this.page.setPage(i);
/* 38 */     this.page.setPageSize(15);
/* 39 */     etf.setChildValue("PAG_INX", String.valueOf((i - 1) * this.page.getPageSize() + 1));
/* 40 */     etf.setChildValue("PAG_END", String.valueOf(i * 15));
/* 41 */     etf.setChildValue("PAG_SIZ", String.valueOf(this.page.getPageSize()));
/* 42 */     return etf;
/*    */   }
/*    */ 
/*    */   protected boolean endProcess(HttpServletRequest request, HiETF rspetf, Logger _log)
/*    */   {
/* 47 */     if (_log.isDebugEnabled())
/* 48 */       _log.debug("QryAction processEnd doing");
/* 49 */     int count = NumberUtils.toInt(rspetf.getChildValue("TOT_NUM"));
/* 50 */     this.page.setRowCount(count);
/* 51 */     List list = new ArrayList();
/* 52 */     PageData pageData = null;
/* 53 */     if (count == 0) {
/* 54 */       pageData = new PageData(this.page, list);
/*    */     } else {
/* 56 */       list = rspetf.getChildNodes("GROUP_");
/* 57 */       pageData = new PageData(this.page, list);
/*    */     }
/* 59 */     request.setAttribute("pageData", pageData);
/* 60 */     return super.endProcess(request, rspetf, _log);
/*    */   }
/*    */ }