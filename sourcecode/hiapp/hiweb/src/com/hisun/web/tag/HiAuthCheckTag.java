 package com.hisun.web.tag;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import javax.servlet.ServletRequest;
 import javax.servlet.http.HttpSession;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 import org.apache.commons.lang.StringUtils;
 import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
 
 public class HiAuthCheckTag extends BodyTagSupport
 {
   private String pageId;
 
   public int doStartTag()
     throws JspException
   {
     ArrayList authList = (ArrayList)this.pageContext.getSession().getAttribute("__AUTH_LIST");
     if (authList == null) {
       HashMap authMap = (HashMap)this.pageContext.getRequest().getAttribute("ETF");
       if (authMap == null) {
         return 1;
       }
       ArrayList tmpList = (ArrayList)authMap.get("GRP");
       if (tmpList == null) {
         return 1;
       }
       authList = new ArrayList();
       for (int i = 0; (tmpList != null) && (i < tmpList.size()); ++i) {
         HashMap tmpMap = (HashMap)tmpList.get(i);
         authList.add(tmpMap.get("PAG_ID"));
       }
       this.pageContext.getSession().setAttribute("__AUTH_LIST", authList);
     }
 
     if ((authList == null) || (StringUtils.isEmpty(this.pageId))) {
       return 1;
     }
 
     return ((authList.contains(this.pageId)) ? 1 : 0);
   }
 
   public String getPageId()
   {
     return this.pageId;
   }
 
   public void setPageId(String pageId)
     throws JspException
   {
     this.pageId = ((String)ExpressionEvaluatorManager.evaluate("pageId", pageId, Object.class, this, this.pageContext));
   }
 }