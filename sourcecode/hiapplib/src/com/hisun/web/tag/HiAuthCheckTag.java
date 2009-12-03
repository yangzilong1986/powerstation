/*    */ package com.hisun.web.tag;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
/*    */ 
/*    */ public class HiAuthCheckTag extends BodyTagSupport
/*    */ {
/*    */   private String pageId;
/*    */ 
/*    */   public int doStartTag()
/*    */     throws JspException
/*    */   {
/* 19 */     ArrayList authList = (ArrayList)this.pageContext.getSession().getAttribute("__AUTH_LIST");
/* 20 */     if (authList == null) {
/* 21 */       HashMap authMap = (HashMap)this.pageContext.getRequest().getAttribute("ETF");
/* 22 */       if (authMap == null) {
/* 23 */         return 1;
/*    */       }
/* 25 */       ArrayList tmpList = (ArrayList)authMap.get("GRP");
/* 26 */       if (tmpList == null) {
/* 27 */         return 1;
/*    */       }
/* 29 */       authList = new ArrayList();
/* 30 */       for (int i = 0; (tmpList != null) && (i < tmpList.size()); ++i) {
/* 31 */         HashMap tmpMap = (HashMap)tmpList.get(i);
/* 32 */         authList.add(tmpMap.get("PAG_ID"));
/*    */       }
/* 34 */       this.pageContext.getSession().setAttribute("__AUTH_LIST", authList);
/*    */     }
/*    */ 
/* 37 */     if ((authList == null) || (StringUtils.isEmpty(this.pageId))) {
/* 38 */       return 1;
/*    */     }
/*    */ 
/* 41 */     return ((authList.contains(this.pageId)) ? 1 : 0);
/*    */   }
/*    */ 
/*    */   public String getPageId()
/*    */   {
/* 48 */     return this.pageId;
/*    */   }
/*    */ 
/*    */   public void setPageId(String pageId)
/*    */     throws JspException
/*    */   {
/* 55 */     this.pageId = ((String)ExpressionEvaluatorManager.evaluate("pageId", pageId, Object.class, this, this.pageContext));
/*    */   }
/*    */ }