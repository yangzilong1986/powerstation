/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.TagSupport;
/*     */ 
/*     */ public class HtmlPageTag extends TagSupport
/*     */ {
/*     */   private static final String IMG_PTH = "image/";
/*     */   protected String txncode;
/*     */   protected String output;
/*     */   protected String action;
/*     */   protected PageData pageData;
/*     */   protected int width;
/*     */   protected int startPosition;
/*     */   protected int endPosition;
/*     */   protected int pageSize;
/*     */   protected int rowCount;
/*     */   protected int pageCount;
/*     */   protected int currPage;
/*     */   protected int currPlace;
/*     */ 
/*     */   public HtmlPageTag()
/*     */   {
/* 153 */     this.txncode = "";
/*     */ 
/* 155 */     this.output = "";
/*     */ 
/* 157 */     this.action = "";
/*     */ 
/* 159 */     this.pageData = null;
/*     */ 
/* 161 */     this.width = 0;
/*     */ 
/* 163 */     this.startPosition = 0;
/*     */ 
/* 165 */     this.endPosition = 0;
/*     */ 
/* 167 */     this.pageSize = 0;
/*     */ 
/* 169 */     this.rowCount = 0;
/*     */ 
/* 171 */     this.pageCount = 0;
/*     */ 
/* 173 */     this.currPage = 1;
/*     */ 
/* 175 */     this.currPlace = 3;
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/*  13 */     JspWriter writer = this.pageContext.getOut();
/*     */     try {
/*  15 */       init();
/*  16 */       drawList(writer);
/*     */     } catch (IOException ie) {
/*  18 */       throw new JspException(ie.toString());
/*     */     }
/*  20 */     return 0; }
/*     */ 
/*     */   public void init() {
/*  23 */     if (this.pageData != null) {
/*  24 */       this.pageSize = this.pageData.getPageSize();
/*  25 */       this.rowCount = this.pageData.getRowCount();
/*  26 */       this.pageCount = this.pageData.getPageCount();
/*  27 */       this.currPage = this.pageData.getCurrPage();
/*  28 */       this.startPosition = this.pageData.getStartPosition();
/*  29 */       this.endPosition = this.pageData.getEndPosition(); }
/*     */   }
/*     */ 
/*     */   public void drawList(JspWriter writer) throws JspException, IOException {
/*  33 */     int startPage = 0;
/*  34 */     int endPage = 0;
/*  35 */     Page page = new Page();
/*     */ 
/*  37 */     if ((this.pageCount <= Page.getGroupPages()) || (this.currPage <= this.currPlace)) {
/*  38 */       startPage = 1;
/*  39 */       endPage = (this.pageCount <= Page.getGroupPages()) ? this.pageCount : Page.getGroupPages();
/*  40 */     } else if (this.pageCount - this.currPage < Page.getGroupPages() - this.currPlace + 1)
/*     */     {
/*  42 */       startPage = this.pageCount - Page.getGroupPages() + 1;
/*  43 */       endPage = this.pageCount;
/*     */     } else {
/*  45 */       startPage = this.currPage - this.currPlace + 1;
/*  46 */       endPage = (this.pageCount <= startPage + Page.getGroupPages() - 1) ? this.pageCount : startPage + Page.getGroupPages() - 1;
/*     */     }
/*     */ 
/*  49 */     writer.println("<script> MM_preloadImages('image/go.gif','image/go_gray.gif');</script>");
/*  50 */     writer.println("  <table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
/*  51 */     writer.println("    <tr>");
/*  52 */     writer.println("      <td width=\"6%\">&nbsp;</td>");
/*  53 */     writer.println("      <td width=\"94%\" align=\"center\">共" + this.rowCount + "条 第" + this.currPage + "/" + this.pageCount + "页&nbsp;&nbsp;每页显示" + this.pageSize + "条&nbsp;&nbsp;");
/*  54 */     if (this.rowCount > 0)
/*     */     {
/*  57 */       if (this.currPage == 1)
/*     */       {
/*  59 */         writer.println("      <img src=\"image/first-02.gif\" alt=\"首页\" name=\"首页\" border=0 style=\"margin-top:5px\" align=absmiddle>");
/*  60 */         writer.println("      <img src=\"image/prev-02.gif\" alt=\"上一页\" name=\"上一页\" border=0 style=\"margin-top:5px\" align=absmiddle>");
/*     */       }
/*     */       else {
/*  63 */         writer.println("      <a href=\"#\" onmouseover=\"setStatusBar('转到首页');return true;\" onclick=\"javascript:doPageSubmit('" + this.action + "','" + this.txncode + "','" + this.output + "',1);return false\"><img src=\"" + "image/" + "first-01.gif\" alt=\"首页\" name=\"首页\" border=0 style=\"margin-top:5px\" align=absmiddle></a>");
/*  64 */         writer.println("      <a href=\"#\" onmouseover=\"setStatusBar('转到上一页');return true;\" onclick=\"javascript:doPageSubmit('" + this.action + "','" + this.txncode + "','" + this.output + "'," + (this.currPage - 1) + ");return false\"><img src=\"" + "image/" + "prev-01.gif\" alt=\"上一页\" name=\"上一页\" border=0 style=\"margin-top:5px\" align=absmiddle></a>");
/*     */       }
/*  66 */       for (int i = startPage; i <= endPage; ++i) {
/*  67 */         if (this.currPage == i)
/*     */         {
/*  69 */           writer.println("<b>[" + i + "]</b>&nbsp;");
/*     */         } else {
/*  71 */           writer.print("<a href=\"#\" onmouseover=\"setStatusBar('转到第 " + i + " 页');return true;\" onclick=\"javascript:doPageSubmit('" + this.action + "','" + this.txncode + "','" + this.output + "'," + i + ");return false\">");
/*  72 */           writer.println("[" + i + "]</a>");
/*  73 */           writer.print("&nbsp;");
/*     */         }
/*     */       }
/*     */ 
/*  77 */       if (this.currPage == this.pageCount)
/*     */       {
/*  79 */         writer.println("      <img src=\"image/next-02.gif\" alt=\"下一页\" name=\"下一页\" border=0 style=\"margin-top:5px\" align=absmiddle>");
/*  80 */         writer.println("      <img src=\"image/last-02.gif\" alt=\"尾页\" name=\"尾页\" border=0 style=\"margin-top:5px\" align=absmiddle>");
/*     */       } else {
/*  82 */         writer.println("      <a href=\"#\" onmouseover=\"setStatusBar('转到下一页');return true;\" onclick=\"javascript:doPageSubmit('" + this.action + "','" + this.txncode + "','" + this.output + "'," + (this.currPage + 1) + ");return false\"><img src=\"" + "image/" + "next-01.gif\" alt=\"下一页\" name=\"下一页\" border=0 style=\"margin-top:5px\" align=absmiddle></a>");
/*  83 */         writer.println("      <a href=\"#\" onmouseover=\"setStatusBar('转到尾页');return true;\" onclick=\"javascript:doPageSubmit('" + this.action + "','" + this.txncode + "','" + this.output + "'," + this.pageCount + ");return false\"> <img src=\"" + "image/" + "last-01.gif\" alt=\"尾页\" name=\"尾页\" border=0 style=\"margin-top:5px\" align=absmiddle></a>");
/*     */       }
/*  85 */       writer.println("       转到&nbsp;");
/*  86 */       writer.println("        <input onkeydown=\"return doPageKeyHadler(this);\" maxlength=5 style=\"ime-mode:disabled;\" class=\"INPUT_TEXT\" name=\"page\" type=\"text\" style=\"width:48px\">");
/*  87 */       writer.println("      &nbsp;页");
/*  88 */       writer.println("      <a onmouseover=\"setStatusBar('转到指定页');return true;\" href=\"javascript:doGoPageSubmit('" + this.action + "','" + this.txncode + "','" + this.output + "')\"><img name=\"goPage\" src=\"" + "image/" + "go.gif\" border=0 align=absmiddle onMouseOver=\"MM_swapImage('goPage','','" + "image/" + "go_gray.gif', 1)\" style=\"margin-top:3px\" onMouseOut=\"MM_swapImgRestore()\"></a>");
/*  89 */       writer.println("      &nbsp;</td>");
/*     */     }
/*     */     else
/*     */     {
/*  93 */       writer.println("      <img src=\"image/first-02.gif\" alt=\"首页\" name=\"首页\" border=0 style=\"margin-top:5px\" align=absmiddle>");
/*  94 */       writer.println("      <img src=\"image/prev-02.gif\" alt=\"上一页\" name=\"上一页\" border=0 style=\"margin-top:5px\" align=absmiddle>");
/*  95 */       writer.println("      <img src=\"image/next-02.gif\" alt=\"下一页\" name=\"下一页\" border=0 style=\"margin-top:5px\" align=absmiddle>");
/*  96 */       writer.println("      <img src=\"image/last-02.gif\" alt=\"尾页\" name=\"尾页\" border=0 style=\"margin-top:5px\" align=absmiddle>");
/*  97 */       writer.println("       转到&nbsp;");
/*  98 */       writer.println("        <input name=\"page\" class=\"INPUT_TEXT\" type=\"text\" style=\"width:48px\" disabled>");
/*  99 */       writer.println("      &nbsp;页");
/* 100 */       writer.println("      <img name=\"goPage\" src=\"image/go.gif\" border=0 align=absmiddle onMouseOver=\"MM_swapImage('goPage','','image/go_gray.gif', 1)\" onMouseOut=\"MM_swapImgRestore()\">");
/* 101 */       writer.println("      &nbsp;</td>");
/*     */     }
/* 103 */     writer.println("    </tr>");
/* 104 */     writer.println("  </table>");
/*     */ 
/* 107 */     writer.print("<input name=\"actions\" type=\"hidden\" value=\"" + this.action + "\">");
/* 108 */     writer.print("<input name=\"oldPage\" type=\"hidden\" value=\"" + this.currPage + "\">");
/* 109 */     writer.print("<input name=\"pageCount\" type=\"hidden\" value=\"" + this.pageCount + "\">"); }
/*     */ 
/*     */   public void release() {
/* 112 */     super.release(); }
/*     */ 
/*     */   public int getWidth() {
/* 115 */     return this.width; }
/*     */ 
/*     */   public void setWidth(int width) {
/* 118 */     this.width = width; }
/*     */ 
/*     */   public PageData getPageData() {
/* 121 */     return this.pageData;
/*     */   }
/*     */ 
/*     */   public void setPageData(PageData pageData) {
/* 125 */     this.pageData = pageData;
/*     */   }
/*     */ 
/*     */   public String getAction() {
/* 129 */     return this.action;
/*     */   }
/*     */ 
/*     */   public void setAction(String action) {
/* 133 */     this.action = action;
/*     */   }
/*     */ 
/*     */   public String getTxncode() {
/* 137 */     return this.txncode;
/*     */   }
/*     */ 
/*     */   public void setTxncode(String txncode) {
/* 141 */     this.txncode = txncode;
/*     */   }
/*     */ 
/*     */   public String getOutput() {
/* 145 */     return this.output;
/*     */   }
/*     */ 
/*     */   public void setOutput(String output) {
/* 149 */     this.output = output;
/*     */   }
/*     */ }