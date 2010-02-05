 package com.hisun.web.tag;

 import com.hisun.message.HiETF;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.DocumentException;

 import javax.servlet.ServletContext;
 import javax.servlet.ServletRequest;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 import java.io.IOException;
 import java.net.MalformedURLException;
 
 public class HiLabel extends BodyTagSupport
 {
   private String _value;
   private String _name;
   private String _convertName;
   private HiConvert _convert;
 
   public HiLabel()
   {
     this._value = "";
 
     this._convert = HiConvert.getInstance();
   }
 
   public int doEndTag() throws JspException {
     JspWriter out = this.pageContext.getOut();
 
     ServletContext context = this.pageContext.getServletContext();
     try
     {
       this._convert.init(context.getRealPath("/conf/convert.xml"));
     }
     catch (MalformedURLException e) {
       throw new JspException("init Convert failure", e);
     } catch (DocumentException e) {
       throw new JspException("init Convert failure", e);
     }
     String convertName = this._convertName;
     if (StringUtils.isEmpty(this._convertName)) {
       convertName = this._name;
     }
     String value = null;
     HiETF nodeEtf = (HiETF)this.pageContext.getAttribute("node");
     if (nodeEtf != null) {
       value = nodeEtf.getChildValue(this._name);
     }
 
     if (StringUtils.isEmpty(value)) {
       ServletRequest request = this.pageContext.getRequest();
       HiETF etf = (HiETF)request.getAttribute("etf");
       if (etf != null) {
         value = etf.getChildValue(this._name);
       }
     }
 
     if (value == null) {
       value = this._value;
     }
 
     System.out.println("Convert02:[" + value + "]:name[" + convertName + "]");
     value = this._convert.convert(convertName, value);
     System.out.println("Convert03:[" + value + "]:name[" + convertName + "]");
     if (value == null) {
       value = this._value;
     }
     try
     {
       out.println(value);
     } catch (IOException e) {
       throw new JspException(e);
     }
 
     super.doEndTag();
     return 6;
   }
 
   public int doStartTag() throws JspException
   {
     super.doStartTag();
     return 1;
   }
 
   public String getName() {
     return this._name;
   }
 
   public void setName(String name) {
     this._name = name;
   }
 
   public String getConvert() {
     return this._convertName;
   }
 
   public void setConvert(String convertName) {
     this._convertName = convertName;
   }
 
   public String getValue() {
     return this._value;
   }
 
   public void setValue(String value) {
     this._value = value;
   }
 }