/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiInput extends BodyTagSupport
/*     */ {
/*     */   private String id;
/*     */   private String name;
/*     */   private String width;
/*     */   private String height;
/*     */   private String maxLength;
/*     */   private String size;
/*     */   private String style;
/*     */   private String onclick;
/*     */   private String onchange;
/*     */   private String onfocus;
/*     */   private String onblur;
/*     */   private String type;
/*     */   private String cols;
/*     */   private String rows;
/*     */   private String css;
/*     */   private String scope;
/*     */   private String maxlength;
/*     */   private String validators;
/*     */   private String displayName;
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/*  38 */     JspWriter out = this.pageContext.getOut();
/*     */ 
/*  40 */     HiETF etf = null;
/*  41 */     String scope = getScope();
/*  42 */     String[] tmp = StringUtils.split(scope, ".");
/*  43 */     String etfname = "";
/*  44 */     if (tmp != null) {
/*  45 */       if (tmp.length == 1) {
/*  46 */         scope = tmp[0];
/*  47 */         etfname = "etf";
/*     */       }
/*  49 */       else if (tmp.length >= 2) {
/*  50 */         scope = tmp[0];
/*  51 */         etfname = tmp[1];
/*     */       }
/*     */       else {
/*  54 */         scope = "request";
/*  55 */         etfname = "etf";
/*     */       }
/*     */     }
/*     */     else {
/*  59 */       scope = "request";
/*  60 */       etfname = "etf";
/*     */     }
/*     */ 
/*  63 */     if (StringUtils.equals("request", scope)) {
/*  64 */       etf = (HiETF)this.pageContext.getRequest().getAttribute(etfname);
/*     */     }
/*  66 */     else if (StringUtils.equals("session", scope)) {
/*  67 */       etf = (HiETF)this.pageContext.getSession().getAttribute(etfname);
/*     */     }
/*     */ 
/*  70 */     if (etf == null)
/*  71 */       etf = HiETFFactory.createETF();
/*     */     try
/*     */     {
/*  74 */       StringBuffer buf = new StringBuffer();
/*  75 */       buf.append("<input name=" + this.name + " type=" + this.type + " id=" + this.id);
/*     */ 
/*  77 */       if (!(StringUtils.isEmpty(this.onclick))) {
/*  78 */         buf.append(" onclick='" + this.onclick + "'");
/*     */       }
/*  80 */       if (!(StringUtils.isEmpty(this.style))) {
/*  81 */         buf.append(" style='" + this.style + "'");
/*     */       }
/*  83 */       if (!(StringUtils.isEmpty(this.validators))) {
/*  84 */         buf.append(" validators='" + this.validators + "'");
/*     */       }
/*  86 */       if (!(StringUtils.isEmpty(this.displayName))) {
/*  87 */         buf.append(" displayName='" + this.displayName + "'");
/*     */       }
/*  89 */       if (!(StringUtils.isEmpty(this.width))) {
/*  90 */         buf.append(" width='" + this.width + "'");
/*     */       }
/*  92 */       if (!(StringUtils.isEmpty(this.height))) {
/*  93 */         buf.append(" height='" + this.height + "'");
/*     */       }
/*  95 */       if (!(StringUtils.isEmpty(this.maxLength))) {
/*  96 */         buf.append(" maxLength" + this.maxLength + "'");
/*     */       }
/*  98 */       if (!(StringUtils.isEmpty(this.size))) {
/*  99 */         buf.append(" size='" + this.size + "'");
/*     */       }
/* 101 */       if (!(StringUtils.isEmpty(this.onchange))) {
/* 102 */         buf.append(" onchange='" + this.onchange + "'");
/*     */       }
/* 104 */       if (!(StringUtils.isEmpty(this.onfocus))) {
/* 105 */         buf.append(" onfocus='" + this.onfocus + "'");
/*     */       }
/* 107 */       if (!(StringUtils.isEmpty(this.onblur))) {
/* 108 */         buf.append(" onblur='" + this.onblur + "'");
/*     */       }
/* 110 */       if (!(StringUtils.isEmpty(this.cols))) {
/* 111 */         buf.append(" cols=" + this.cols);
/*     */       }
/* 113 */       if (!(StringUtils.isEmpty(this.rows))) {
/* 114 */         buf.append(" rows=" + this.rows);
/*     */       }
/* 116 */       if (!(StringUtils.isEmpty(this.css))) {
/* 117 */         buf.append(" class=" + this.css);
/*     */       }
/* 119 */       if (!(StringUtils.isEmpty(this.maxlength))) {
/* 120 */         buf.append(" maxlength=" + this.maxlength);
/*     */       }
/*     */ 
/* 124 */       String value = (etf.getGrandChildValue(this.name) == null) ? "" : etf.getGrandChildValue(this.name);
/* 125 */       buf.append(" value='" + value + "'");
/*     */ 
/* 127 */       buf.append(">");
/* 128 */       out.println(buf.toString());
/* 129 */       out.println("</input>");
/*     */     }
/*     */     catch (IOException e) {
/* 132 */       e.printStackTrace();
/*     */     }
/* 134 */     return super.doStartTag();
/*     */   }
/*     */ 
/*     */   public int doAfterBody() throws JspException
/*     */   {
/* 139 */     return super.doAfterBody();
/*     */   }
/*     */ 
/*     */   public int doEndTag() throws JspException
/*     */   {
/* 144 */     return super.doEndTag();
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 148 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/* 152 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 156 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 160 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getStyle() {
/* 164 */     return this.style;
/*     */   }
/*     */ 
/*     */   public void setStyle(String style) {
/* 168 */     this.style = style;
/*     */   }
/*     */ 
/*     */   public String getOnclick() {
/* 172 */     return this.onclick;
/*     */   }
/*     */ 
/*     */   public void setOnclick(String onclick) {
/* 176 */     this.onclick = onclick;
/*     */   }
/*     */ 
/*     */   public String getValidators() {
/* 180 */     return this.validators;
/*     */   }
/*     */ 
/*     */   public void setValidators(String validators) {
/* 184 */     this.validators = validators;
/*     */   }
/*     */ 
/*     */   public String getDisplayName() {
/* 188 */     return this.displayName;
/*     */   }
/*     */ 
/*     */   public void setDisplayName(String displayName) {
/* 192 */     this.displayName = displayName;
/*     */   }
/*     */ 
/*     */   public String getWidth() {
/* 196 */     return this.width;
/*     */   }
/*     */ 
/*     */   public void setWidth(String width) {
/* 200 */     this.width = width;
/*     */   }
/*     */ 
/*     */   public String getHeight() {
/* 204 */     return this.height;
/*     */   }
/*     */ 
/*     */   public void setHeight(String height) {
/* 208 */     this.height = height;
/*     */   }
/*     */ 
/*     */   public String getMaxLength() {
/* 212 */     return this.maxLength;
/*     */   }
/*     */ 
/*     */   public void setMaxLength(String maxLength) {
/* 216 */     this.maxLength = maxLength;
/*     */   }
/*     */ 
/*     */   public String getSize() {
/* 220 */     return this.size;
/*     */   }
/*     */ 
/*     */   public void setSize(String size) {
/* 224 */     this.size = size;
/*     */   }
/*     */ 
/*     */   public String getOnchange() {
/* 228 */     return this.onchange;
/*     */   }
/*     */ 
/*     */   public void setOnchange(String onchange) {
/* 232 */     this.onchange = onchange;
/*     */   }
/*     */ 
/*     */   public String getOnfocus() {
/* 236 */     return this.onfocus;
/*     */   }
/*     */ 
/*     */   public void setOnfocus(String onfocus) {
/* 240 */     this.onfocus = onfocus;
/*     */   }
/*     */ 
/*     */   public String getOnblur() {
/* 244 */     return this.onblur;
/*     */   }
/*     */ 
/*     */   public void setOnblur(String onblur) {
/* 248 */     this.onblur = onblur;
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 252 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/* 256 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getCols() {
/* 260 */     return this.cols;
/*     */   }
/*     */ 
/*     */   public void setCols(String cols) {
/* 264 */     this.cols = cols;
/*     */   }
/*     */ 
/*     */   public String getRows() {
/* 268 */     return this.rows;
/*     */   }
/*     */ 
/*     */   public void setRows(String rows) {
/* 272 */     this.rows = rows;
/*     */   }
/*     */ 
/*     */   public String getCss() {
/* 276 */     return this.css;
/*     */   }
/*     */ 
/*     */   public void setCss(String css) {
/* 280 */     this.css = css;
/*     */   }
/*     */ 
/*     */   public String getScope() {
/* 284 */     return this.scope;
/*     */   }
/*     */ 
/*     */   public void setScope(String scope) {
/* 288 */     this.scope = scope;
/*     */   }
/*     */ 
/*     */   public String getMaxlength() {
/* 292 */     return this.maxlength;
/*     */   }
/*     */ 
/*     */   public void setMaxlength(String maxlength) {
/* 296 */     this.maxlength = maxlength;
/*     */   }
/*     */ }