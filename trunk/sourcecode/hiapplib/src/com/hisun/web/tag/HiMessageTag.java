/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.util.MessageResources;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
/*     */ 
/*     */ public class HiMessageTag extends BodyTagSupport
/*     */ {
/*     */   private String arg0;
/*     */   private String arg1;
/*     */   private String arg2;
/*     */   private String arg3;
/*     */   private String arg4;
/*     */   private String key;
/*     */   private String bundle;
/*     */   private String locale;
/*     */ 
/*     */   public HiMessageTag()
/*     */   {
/*  25 */     this.bundle = "application";
/*     */   }
/*     */ 
/*     */   public int doEndTag() throws JspException {
/*  29 */     JspWriter out = this.pageContext.getOut();
/*  30 */     MessageResources messageResources = MessageResources.getMessageResources("resource." + this.bundle);
/*  31 */     String message = messageResources.getMessage(this.key, new Object[] { this.arg0, this.arg1, this.arg2, this.arg3, this.arg4 });
/*     */     try {
/*  33 */       out.print(message);
/*     */     } catch (IOException e) {
/*  35 */       throw new JspException(e);
/*     */     }
/*  37 */     super.doEndTag();
/*  38 */     return 6;
/*     */   }
/*     */ 
/*     */   public int doStartTag() throws JspException
/*     */   {
/*  43 */     super.doStartTag();
/*  44 */     return 1;
/*     */   }
/*     */ 
/*     */   public String getArg0() {
/*  48 */     return this.arg0;
/*     */   }
/*     */ 
/*     */   public void setArg0(String arg0) throws JspException {
/*  52 */     if (arg0 == null) {
/*  53 */       this.arg0 = "";
/*  54 */       return;
/*     */     }
/*  56 */     this.arg0 = ((String)ExpressionEvaluatorManager.evaluate("arg0", arg0, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getArg1() {
/*  60 */     return this.arg1;
/*     */   }
/*     */ 
/*     */   public void setArg1(String arg1) throws JspException {
/*  64 */     if (arg1 == null) {
/*  65 */       this.arg1 = "";
/*  66 */       return;
/*     */     }
/*  68 */     this.arg1 = ((String)ExpressionEvaluatorManager.evaluate("arg1", arg1, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getArg2() {
/*  72 */     return this.arg2;
/*     */   }
/*     */ 
/*     */   public void setArg2(String arg2) throws JspException {
/*  76 */     if (arg2 == null) {
/*  77 */       this.arg2 = "";
/*  78 */       return;
/*     */     }
/*  80 */     this.arg2 = ((String)ExpressionEvaluatorManager.evaluate("arg2", arg2, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getArg3() {
/*  84 */     return this.arg3;
/*     */   }
/*     */ 
/*     */   public void setArg3(String arg3) throws JspException {
/*  88 */     if (arg3 == null) {
/*  89 */       this.arg3 = "";
/*  90 */       return;
/*     */     }
/*     */ 
/*  93 */     this.arg3 = ((String)ExpressionEvaluatorManager.evaluate("arg3", arg3, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getArg4() {
/*  97 */     return this.arg4;
/*     */   }
/*     */ 
/*     */   public void setArg4(String arg4) throws JspException {
/* 101 */     if (arg4 == null) {
/* 102 */       this.arg4 = "";
/* 103 */       return;
/*     */     }
/* 105 */     this.arg4 = ((String)ExpressionEvaluatorManager.evaluate("arg4", arg4, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getKey() {
/* 109 */     return this.key;
/*     */   }
/*     */ 
/*     */   public void setKey(String key) throws JspException {
/* 113 */     if (key == null) {
/* 114 */       this.key = "";
/* 115 */       return;
/*     */     }
/* 117 */     this.key = ((String)ExpressionEvaluatorManager.evaluate("key", key, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getBundle() {
/* 121 */     return this.bundle;
/*     */   }
/*     */ 
/*     */   public void setBundle(String bundle) {
/* 125 */     this.bundle = bundle;
/*     */   }
/*     */ 
/*     */   public String getLocale() {
/* 129 */     return this.locale;
/*     */   }
/*     */ 
/*     */   public void setLocale(String locale) {
/* 133 */     this.locale = locale;
/*     */   }
/*     */ }