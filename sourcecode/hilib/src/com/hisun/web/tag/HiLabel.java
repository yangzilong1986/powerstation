/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.message.HiETF;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.DocumentException;
/*     */ 
/*     */ public class HiLabel extends BodyTagSupport
/*     */ {
/*     */   private String _value;
/*     */   private String _name;
/*     */   private String _convertName;
/*     */   private HiConvert _convert;
/*     */ 
/*     */   public HiLabel()
/*     */   {
/*  24 */     this._value = "";
/*     */ 
/*  27 */     this._convert = HiConvert.getInstance();
/*     */   }
/*     */ 
/*     */   public int doEndTag() throws JspException {
/*  31 */     JspWriter out = this.pageContext.getOut();
/*     */ 
/*  33 */     ServletContext context = this.pageContext.getServletContext();
/*     */     try
/*     */     {
/*  36 */       this._convert.init(context.getRealPath("/conf/convert.xml"));
/*     */     }
/*     */     catch (MalformedURLException e) {
/*  39 */       throw new JspException("init Convert failure", e);
/*     */     } catch (DocumentException e) {
/*  41 */       throw new JspException("init Convert failure", e);
/*     */     }
/*  43 */     String convertName = this._convertName;
/*  44 */     if (StringUtils.isEmpty(this._convertName)) {
/*  45 */       convertName = this._name;
/*     */     }
/*  47 */     String value = null;
/*  48 */     HiETF nodeEtf = (HiETF)this.pageContext.getAttribute("node");
/*  49 */     if (nodeEtf != null) {
/*  50 */       value = nodeEtf.getChildValue(this._name);
/*     */     }
/*     */ 
/*  53 */     if (StringUtils.isEmpty(value)) {
/*  54 */       ServletRequest request = this.pageContext.getRequest();
/*  55 */       HiETF etf = (HiETF)request.getAttribute("etf");
/*  56 */       if (etf != null) {
/*  57 */         value = etf.getChildValue(this._name);
/*     */       }
/*     */     }
/*     */ 
/*  61 */     if (value == null) {
/*  62 */       value = this._value;
/*     */     }
/*     */ 
/*  65 */     System.out.println("Convert02:[" + value + "]:name[" + convertName + "]");
/*  66 */     value = this._convert.convert(convertName, value);
/*  67 */     System.out.println("Convert03:[" + value + "]:name[" + convertName + "]");
/*  68 */     if (value == null) {
/*  69 */       value = this._value;
/*     */     }
/*     */     try
/*     */     {
/*  73 */       out.println(value);
/*     */     } catch (IOException e) {
/*  75 */       throw new JspException(e);
/*     */     }
/*     */ 
/*  78 */     super.doEndTag();
/*  79 */     return 6;
/*     */   }
/*     */ 
/*     */   public int doStartTag() throws JspException
/*     */   {
/*  84 */     super.doStartTag();
/*  85 */     return 1;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  89 */     return this._name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  93 */     this._name = name;
/*     */   }
/*     */ 
/*     */   public String getConvert() {
/*  97 */     return this._convertName;
/*     */   }
/*     */ 
/*     */   public void setConvert(String convertName) {
/* 101 */     this._convertName = convertName;
/*     */   }
/*     */ 
/*     */   public String getValue() {
/* 105 */     return this._value;
/*     */   }
/*     */ 
/*     */   public void setValue(String value) {
/* 109 */     this._value = value;
/*     */   }
/*     */ }