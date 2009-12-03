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
/* 23 */     this._value = "";
/* 24 */     this._defaultValue = "";
/*    */ 
/* 26 */     this._convert = HiConvert.getInstance();
/* 27 */     this._log = HiLog.getLogger("convert.trc"); }
/*    */ 
/*    */   public int doEndTag() throws JspException {
/* 30 */     JspWriter out = this.pageContext.getOut();
/*    */ 
/* 32 */     ServletContext context = this.pageContext.getServletContext();
/*    */     try
/*    */     {
/* 35 */       this._convert.init(context.getRealPath("/conf/convert.xml"));
/*    */     } catch (MalformedURLException e) {
/* 37 */       throw new JspException("init Convert failure", e);
/*    */     } catch (DocumentException e) {
/* 39 */       throw new JspException("init Convert failure", e);
/*    */     }
/* 41 */     if (this._log.isDebugEnabled()) {
/* 42 */       this._log.debug("Before Convert:[" + this._value + "]:name[" + this._name + "]");
/*    */     }
/* 44 */     String value = this._convert.convert(this._name, this._value);
/* 45 */     if (this._log.isDebugEnabled()) {
/* 46 */       this._log.debug("After Convert:[" + this._value + "]:name[" + this._name + "]");
/*    */     }
/* 48 */     if (value == null)
/* 49 */       value = this._value;
/*    */     try {
/* 51 */       out.print(value);
/*    */     } catch (IOException e) {
/* 53 */       throw new JspException(e);
/*    */     }
/*    */ 
/* 56 */     super.doEndTag();
/* 57 */     return 6;
/*    */   }
/*    */ 
/*    */   public int doStartTag() throws JspException
/*    */   {
/* 62 */     super.doStartTag();
/* 63 */     return 1;
/*    */   }
/*    */ 
/*    */   public String getValue() {
/* 67 */     return this._value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value) throws JspException {
/* 71 */     this._value = ((String)ExpressionEvaluatorManager.evaluate("value", value, Object.class, this, this.pageContext));
/* 72 */     if ((this._value == null) || (this._value.equals("")))
/* 73 */       this._value = this._defaultValue;
/*    */   }
/*    */ 
/*    */   public String getDefaultValue() {
/* 77 */     return this._defaultValue;
/*    */   }
/*    */ 
/*    */   public void setDefaultValue(String defaultValue) throws JspException {
/* 81 */     this._defaultValue = defaultValue;
/* 82 */     if ((this._value == null) || (this._value.equals("")))
/* 83 */       this._value = this._defaultValue;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 87 */     return this._name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 91 */     this._name = name;
/*    */   }
/*    */ }