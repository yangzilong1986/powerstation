/*     */ package com.hisun.web.tag.select;
/*     */ 
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiSelectTag extends BodyTagSupport
/*     */ {
/*     */   private String id;
/*     */   private String name;
/*     */   private String options;
/*     */   private String defaultSelected;
/*     */   private String style;
/*     */   private String onclick;
/*     */   private String onchange;
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/*  26 */     JspWriter out = this.pageContext.getOut();
/*     */ 
/*  28 */     HiETF etf = (HiETF)this.pageContext.getRequest().getAttribute("etf");
/*  29 */     if (etf == null)
/*  30 */       etf = HiETFFactory.createETF();
/*     */     try
/*     */     {
/*  33 */       StringBuffer buf = new StringBuffer();
/*  34 */       buf.append("<select name=" + this.name);
/*     */ 
/*  36 */       if (!(StringUtils.isEmpty(this.onclick))) {
/*  37 */         buf.append(" onclick='" + this.onclick + "'");
/*     */       }
/*  39 */       if (!(StringUtils.isEmpty(this.style))) {
/*  40 */         buf.append(" style='" + this.style + "'");
/*     */       }
/*  42 */       if (!(StringUtils.isEmpty(this.onchange))) {
/*  43 */         buf.append(" onchange=\"" + this.onchange + "\"");
/*     */       }
/*     */ 
/*  46 */       buf.append(">");
/*     */ 
/*  48 */       out.print(buf.toString());
/*     */ 
/*  50 */       String[] aOpt = StringUtils.split(this.options, ",");
/*  51 */       for (int i = 0; i < aOpt.length; ++i) {
/*  52 */         String opt = aOpt[i];
/*  53 */         String[] tmp = StringUtils.split(opt, "=");
/*  54 */         if ((tmp == null) || (tmp.length != 2)) throw new JspException("select param error!");
/*  55 */         String sel = "";
/*  56 */         if (etf != null) {
/*  57 */           sel = etf.getChildValue(this.name);
/*     */         }
/*     */ 
/*  60 */         if (StringUtils.equals(sel, tmp[1])) {
/*  61 */           out.println("<option value='" + tmp[1] + "' selected>" + tmp[0] + "</option>");
/*     */         }
/*  63 */         else if (StringUtils.equals(this.defaultSelected, tmp[1])) {
/*  64 */           out.println("<option value='" + tmp[1] + "' selected>" + tmp[0] + "</option>");
/*     */         }
/*     */         else {
/*  67 */           out.println("<option value='" + tmp[1] + "'>" + tmp[0] + "</option>");
/*     */         }
/*     */       }
/*     */ 
/*  71 */       out.println("</select>");
/*     */     }
/*     */     catch (IOException e) {
/*  74 */       e.printStackTrace();
/*     */     }
/*  76 */     return super.doStartTag();
/*     */   }
/*     */ 
/*     */   public int doAfterBody() throws JspException
/*     */   {
/*  81 */     return super.doAfterBody();
/*     */   }
/*     */ 
/*     */   public int doEndTag() throws JspException
/*     */   {
/*  86 */     return super.doEndTag();
/*     */   }
/*     */ 
/*     */   public String getId() {
/*  90 */     return this.id; }
/*     */ 
/*     */   public void setId(String id) {
/*  93 */     this.id = id; }
/*     */ 
/*     */   public String getName() {
/*  96 */     return this.name; }
/*     */ 
/*     */   public void setName(String name) {
/*  99 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getOptions() {
/* 103 */     return this.options;
/*     */   }
/*     */ 
/*     */   public void setOptions(String options) {
/* 107 */     this.options = options;
/*     */   }
/*     */ 
/*     */   public String getDefaultSelected() {
/* 111 */     return this.defaultSelected;
/*     */   }
/*     */ 
/*     */   public void setDefaultSelected(String defaultSelected) {
/* 115 */     this.defaultSelected = defaultSelected;
/*     */   }
/*     */ 
/*     */   public String getStyle() {
/* 119 */     return this.style;
/*     */   }
/*     */ 
/*     */   public void setStyle(String style) {
/* 123 */     this.style = style;
/*     */   }
/*     */ 
/*     */   public String getOnclick() {
/* 127 */     return this.onclick;
/*     */   }
/*     */ 
/*     */   public void setOnclick(String onclick) {
/* 131 */     this.onclick = onclick;
/*     */   }
/*     */ 
/*     */   public String getOnchange() {
/* 135 */     return this.onchange;
/*     */   }
/*     */ 
/*     */   public void setOnchange(String onchange) {
/* 139 */     this.onchange = onchange;
/*     */   }
/*     */ }