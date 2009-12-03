/*    */ package com.hisun.web.tag;
/*    */ 
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import java.io.IOException;
/*    */ import java.net.MalformedURLException;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*    */ import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
/*    */ import org.dom4j.DocumentException;
/*    */ 
/*    */ public class HiConvertTag extends BodyTagSupport
/*    */ {
/*    */   private String _value;
/*    */   private String _defaultValue;
/*    */   private String _name;
/*    */   private HiConvert _convert;
/*    */   private Logger _log;
/*    */ 
/*    */   public HiConvertTag()
/*    */   {
/* 24 */     this._value = "";
/* 25 */     this._defaultValue = "";
/*    */ 
/* 27 */     this._convert = HiConvert.getInstance();
/* 28 */     this._log = HiLog.getLogger("convert.trc"); }
/*    */ 
/*    */   public int doEndTag() throws JspException {
/* 31 */     JspWriter out = this.pageContext.getOut();
/*    */ 
/* 33 */     ServletContext context = this.pageContext.getServletContext();
/*    */     try
/*    */     {
/* 36 */       this._convert.init(context.getRealPath("/conf/convert.xml"));
/*    */     } catch (MalformedURLException e) {
/* 38 */       throw new JspException("init Convert failure", e);
/*    */     } catch (DocumentException e) {
/* 40 */       throw new JspException("init Convert failure", e);
/*    */     }
/* 42 */     if (this._log.isDebugEnabled()) {
/* 43 */       this._log.debug("Before Convert:[" + this._value + "]:name[" + this._name + "]");
/*    */     }
/* 45 */     String value = this._convert.convert(this.pageContext, this._name, this._value);
/*    */ 
/* 47 */     if (this._log.isDebugEnabled()) {
/* 48 */       this._log.debug("After Convert:[" + this._value + "]:name[" + this._name + "]");
/*    */     }
/* 50 */     if (value == null)
/* 51 */       value = this._value;
/*    */     try {
/* 53 */       out.print(value);
/*    */     } catch (IOException e) {
/* 55 */       throw new JspException(e);
/*    */     }
/*    */ 
/* 58 */     super.doEndTag();
/* 59 */     return 6;
/*    */   }
/*    */ 
/*    */   public int doStartTag() throws JspException
/*    */   {
/* 64 */     super.doStartTag();
/* 65 */     return 1;
/*    */   }
/*    */ 
/*    */   public String getValue() {
/* 69 */     return this._value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value) throws JspException {
/* 73 */     this._value = ((String)ExpressionEvaluatorManager.evaluate("value", value, Object.class, this, this.pageContext));
/* 74 */     if ((this._value == null) || (this._value.equals("")))
/* 75 */       this._value = this._defaultValue;
/*    */   }
/*    */ 
/*    */   public String getDefaultValue() {
/* 79 */     return this._defaultValue;
/*    */   }
/*    */ 
/*    */   public void setDefaultValue(String defaultValue) throws JspException {
/* 83 */     this._defaultValue = defaultValue;
/* 84 */     if ((this._value == null) || (this._value.equals("")))
/* 85 */       this._value = this._defaultValue;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 89 */     return this._name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 93 */     this._name = name;
/*    */   }
/*    */ }