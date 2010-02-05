 package com.hisun.web.tag;

 import com.hisun.message.HiETF;
 import org.apache.commons.lang.StringUtils;

 import javax.servlet.ServletContext;
 import javax.servlet.ServletRequest;
 import javax.servlet.http.HttpSession;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 
 public class NodeTag extends BodyTagSupport
 {
   private String name;
   private String property;
   private String convertName;
   private String scope;
   HiConvert _convert = null;
 
   public NodeTag() {
     this._convert = HiConvert.getInstance();
   }
 
   public int doEndTag()
     throws JspException
   {
     HiETF etfRoot = null;
 
     etfRoot = (HiETF)this.pageContext.getAttribute(getName());
     JspWriter out = this.pageContext.getOut();
 
     if ((getScope() != null) && (etfRoot == null)) {
       if ("request".equals(getScope())) {
         etfRoot = (HiETF)this.pageContext.getRequest().getAttribute(getName());
       }
       else if ("session".equals(getScope())) {
         etfRoot = (HiETF)this.pageContext.getSession().getAttribute(getName());
       }
     }
     try
     {
       if (etfRoot != null) {
         HiETF valNod = null;
         if (this.property != null) {
           valNod = etfRoot.getGrandChildNodeBase(getProperty());
         }
         else {
           valNod = etfRoot;
         }
 
         if (valNod != null) {
           if (!(StringUtils.isEmpty(this.convertName))) {
             ServletContext context = this.pageContext.getServletContext();
 
             this._convert.init(context.getRealPath("/conf/convert.xml"));
             String val = this._convert.convert(this.convertName, valNod.getValue());
             if (val == null) val = "";
             out.print(val);
           }
           else {
             out.print(valNod.getValue());
           }
         }
         else
           out.print("");
       }
       else
       {
         out.println("");
       }
     }
     catch (Exception e) {
       e.printStackTrace();
     }
 
     return 6;
   }
 
   public String getName()
   {
     return this.name; }
 
   public void setName(String name) {
     this.name = name; }
 
   public String getProperty() {
     return this.property; }
 
   public void setProperty(String property) {
     this.property = property;
   }
 
   public int doStartTag() throws JspException
   {
     return 1;
   }
 
   public String getConvert()
   {
     return this.convertName;
   }
 
   public void setConvert(String convert)
   {
     this.convertName = convert;
   }
 
   public String getScope()
   {
     return this.scope;
   }
 
   public void setScope(String scope)
   {
     this.scope = scope;
   }
 }