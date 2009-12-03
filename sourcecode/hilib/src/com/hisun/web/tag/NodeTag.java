/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.message.HiETF;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class NodeTag extends BodyTagSupport
/*     */ {
/*     */   private String name;
/*     */   private String property;
/*     */   private String convertName;
/*     */   private String scope;
/*  25 */   HiConvert _convert = null;
/*     */ 
/*     */   public NodeTag() {
/*  28 */     this._convert = HiConvert.getInstance();
/*     */   }
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*  34 */     HiETF etfRoot = null;
/*     */ 
/*  36 */     etfRoot = (HiETF)this.pageContext.getAttribute(getName());
/*  37 */     JspWriter out = this.pageContext.getOut();
/*     */ 
/*  40 */     if ((getScope() != null) && (etfRoot == null)) {
/*  41 */       if ("request".equals(getScope())) {
/*  42 */         etfRoot = (HiETF)this.pageContext.getRequest().getAttribute(getName());
/*     */       }
/*  44 */       else if ("session".equals(getScope())) {
/*  45 */         etfRoot = (HiETF)this.pageContext.getSession().getAttribute(getName());
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/*  50 */       if (etfRoot != null) {
/*  51 */         HiETF valNod = null;
/*  52 */         if (this.property != null) {
/*  53 */           valNod = etfRoot.getGrandChildNodeBase(getProperty());
/*     */         }
/*     */         else {
/*  56 */           valNod = etfRoot;
/*     */         }
/*     */ 
/*  59 */         if (valNod != null) {
/*  60 */           if (!(StringUtils.isEmpty(this.convertName))) {
/*  61 */             ServletContext context = this.pageContext.getServletContext();
/*     */ 
/*  64 */             this._convert.init(context.getRealPath("/conf/convert.xml"));
/*  65 */             String val = this._convert.convert(this.convertName, valNod.getValue());
/*  66 */             if (val == null) val = "";
/*  67 */             out.print(val);
/*     */           }
/*     */           else {
/*  70 */             out.print(valNod.getValue());
/*     */           }
/*     */         }
/*     */         else
/*  74 */           out.print("");
/*     */       }
/*     */       else
/*     */       {
/*  78 */         out.println("");
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  82 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  85 */     return 6;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  90 */     return this.name; }
/*     */ 
/*     */   public void setName(String name) {
/*  93 */     this.name = name; }
/*     */ 
/*     */   public String getProperty() {
/*  96 */     return this.property; }
/*     */ 
/*     */   public void setProperty(String property) {
/*  99 */     this.property = property;
/*     */   }
/*     */ 
/*     */   public int doStartTag() throws JspException
/*     */   {
/* 104 */     return 1;
/*     */   }
/*     */ 
/*     */   public String getConvert()
/*     */   {
/* 109 */     return this.convertName;
/*     */   }
/*     */ 
/*     */   public void setConvert(String convert)
/*     */   {
/* 114 */     this.convertName = convert;
/*     */   }
/*     */ 
/*     */   public String getScope()
/*     */   {
/* 119 */     return this.scope;
/*     */   }
/*     */ 
/*     */   public void setScope(String scope)
/*     */   {
/* 124 */     this.scope = scope;
/*     */   }
/*     */ }