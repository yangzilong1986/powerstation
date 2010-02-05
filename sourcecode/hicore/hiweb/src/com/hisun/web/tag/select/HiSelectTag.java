 package com.hisun.web.tag.select;

 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import org.apache.commons.lang.StringUtils;

 import javax.servlet.ServletRequest;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 import java.io.IOException;
 
 public class HiSelectTag extends BodyTagSupport
 {
   private String id;
   private String name;
   private String options;
   private String defaultSelected;
   private String style;
   private String onclick;
   private String onchange;
 
   public int doStartTag()
     throws JspException
   {
     JspWriter out = this.pageContext.getOut();
 
     HiETF etf = (HiETF)this.pageContext.getRequest().getAttribute("etf");
     if (etf == null)
       etf = HiETFFactory.createETF();
     try
     {
       StringBuffer buf = new StringBuffer();
       buf.append("<select name=" + this.name);
 
       if (!(StringUtils.isEmpty(this.onclick))) {
         buf.append(" onclick='" + this.onclick + "'");
       }
       if (!(StringUtils.isEmpty(this.style))) {
         buf.append(" style='" + this.style + "'");
       }
       if (!(StringUtils.isEmpty(this.onchange))) {
         buf.append(" onchange=\"" + this.onchange + "\"");
       }
 
       buf.append(">");
 
       out.print(buf.toString());
 
       String[] aOpt = StringUtils.split(this.options, ",");
       for (int i = 0; i < aOpt.length; ++i) {
         String opt = aOpt[i];
         String[] tmp = StringUtils.split(opt, "=");
         if ((tmp == null) || (tmp.length != 2)) throw new JspException("select param error!");
         String sel = "";
         if (etf != null) {
           sel = etf.getChildValue(this.name);
         }
 
         if (StringUtils.equals(sel, tmp[1])) {
           out.println("<option value='" + tmp[1] + "' selected>" + tmp[0] + "</option>");
         }
         else if (StringUtils.equals(this.defaultSelected, tmp[1])) {
           out.println("<option value='" + tmp[1] + "' selected>" + tmp[0] + "</option>");
         }
         else {
           out.println("<option value='" + tmp[1] + "'>" + tmp[0] + "</option>");
         }
       }
 
       out.println("</select>");
     }
     catch (IOException e) {
       e.printStackTrace();
     }
     return super.doStartTag();
   }
 
   public int doAfterBody() throws JspException
   {
     return super.doAfterBody();
   }
 
   public int doEndTag() throws JspException
   {
     return super.doEndTag();
   }
 
   public String getId() {
     return this.id; }
 
   public void setId(String id) {
     this.id = id; }
 
   public String getName() {
     return this.name; }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String getOptions() {
     return this.options;
   }
 
   public void setOptions(String options) {
     this.options = options;
   }
 
   public String getDefaultSelected() {
     return this.defaultSelected;
   }
 
   public void setDefaultSelected(String defaultSelected) {
     this.defaultSelected = defaultSelected;
   }
 
   public String getStyle() {
     return this.style;
   }
 
   public void setStyle(String style) {
     this.style = style;
   }
 
   public String getOnclick() {
     return this.onclick;
   }
 
   public void setOnclick(String onclick) {
     this.onclick = onclick;
   }
 
   public String getOnchange() {
     return this.onchange;
   }
 
   public void setOnchange(String onchange) {
     this.onchange = onchange;
   }
 }