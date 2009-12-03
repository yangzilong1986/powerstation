/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import freemarker.template.utility.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.TagSupport;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiPage extends TagSupport
/*     */ {
/*     */   public String _output;
/*     */   public String _txncod;
/*     */   public String _fields;
/*     */ 
/*     */   public HiPage()
/*     */   {
/*  18 */     this._fields = null; }
/*     */ 
/*     */   public String getOutput() {
/*  21 */     return this._output;
/*     */   }
/*     */ 
/*     */   public void setOutput(String output) {
/*  25 */     this._output = output;
/*     */   }
/*     */ 
/*     */   public String getTxncod() {
/*  29 */     return this._txncod;
/*     */   }
/*     */ 
/*     */   public void setTxncod(String txncod) {
/*  33 */     this._txncod = txncod;
/*     */   }
/*     */ 
/*     */   public String getfields() {
/*  37 */     return this._fields;
/*     */   }
/*     */ 
/*     */   public void setfields(String _fields) {
/*  41 */     this._fields = _fields;
/*     */   }
/*     */ 
/*     */   public int doEndTag() throws JspException {
/*  45 */     ServletRequest request = this.pageContext.getRequest();
/*  46 */     HashMap map = (HashMap)request.getAttribute("ETF");
/*  47 */     if (map == null)
/*  48 */       return 6;
/*  49 */     JspWriter out = this.pageContext.getOut();
/*  50 */     StringBuffer buf = new StringBuffer();
/*  51 */     if (this._fields != null) {
/*  52 */       String[] field = StringUtil.split(this._fields, '|');
/*  53 */       for (String a : field) {
/*  54 */         String value = (String)map.get(a);
/*  55 */         if (value == null) {
/*     */           continue;
/*     */         }
/*  58 */         buf.append("&" + a + "=" + value);
/*     */       }
/*     */     }
/*     */     try {
/*  62 */       int recNum = NumberUtils.toInt((String)map.get("REC_NUM"));
/*  63 */       int pageNo = NumberUtils.toInt((String)map.get("PAG_NO"));
/*  64 */       int pageCnt = NumberUtils.toInt((String)map.get("PAG_CNT"));
/*  65 */       String pagKey = (String)map.get("PAG_KEY");
/*  66 */       if ((pageNo != 1) && (pageNo != 0)) {
/*  67 */         out.println("<a href='" + this._txncod + "?REQ_TYP=P&PAG_IDX=050000&PAG_KEY=" + pagKey + buf + "&output=" + this._output + "'" + " onmouseover='setStatusBar('转到首页'); return true'" + ">");
/*     */       }
/*  69 */       out.println("<img src='img/first-02.gif' alt='首页' border='0' style='margin-top:5px' align='absmiddle'/>");
/*  70 */       if ((pageNo != 1) && (pageNo != 0)) {
/*  71 */         out.println("</a>");
/*     */       }
/*     */ 
/*  74 */       if ((pageNo != 1) && (pageNo != 0)) {
/*  75 */         out.println("<a href='" + this._txncod + "?REQ_TYP=P&PAG_IDX=070000&PAG_KEY=" + pagKey + buf + "&output=" + this._output + "'" + " onmouseover='setStatusBar('转到上一页'); return true'" + ">");
/*     */       }
/*  77 */       out.println("<img src='img/prev-02.gif' alt='上一页' border='0' style='margin-top:5px' align='absmiddle'/>");
/*  78 */       if ((pageNo != 1) && (pageNo != 0)) {
/*  79 */         out.println("</a>");
/*     */       }
/*     */ 
/*  82 */       if (pageNo != pageCnt) {
/*  83 */         out.println("<a href='" + this._txncod + "?REQ_TYP=P&PAG_IDX=080000&PAG_KEY=" + pagKey + buf + "&output=" + this._output + "'" + " onmouseover='setStatusBar('转到下一页'); return true'" + ">");
/*     */       }
/*     */ 
/*  86 */       out.println("<img src='img/next-02.gif' alt='下一页' border='0' style='margin-top:5px' align='absmiddle'/>");
/*  87 */       if (pageNo != pageCnt) {
/*  88 */         out.println("</a>");
/*     */       }
/*     */ 
/*  91 */       if (pageNo != pageCnt) {
/*  92 */         out.println("<a href='" + this._txncod + "?REQ_TYP=P&PAG_IDX=060000&PAG_KEY=" + pagKey + buf + "&output=" + this._output + "'" + " onmouseover='setStatusBar('转到尾页'); return true'" + ">");
/*     */       }
/*     */ 
/*  95 */       out.println("<img src='img/last-02.gif' alt='尾页' border='0' style='margin-top:5px' align='absmiddle'/>");
/*  96 */       if (pageNo != pageCnt) {
/*  97 */         out.println("</a>");
/*     */       }
/*  99 */       out.println("转到&nbsp;");
/* 100 */       out.println("<input name='pageno' id='pageno' class='INPUT_TEXT' type='text' style='width:24px'/>");
/*     */ 
/* 105 */       out.println("<span onclick='onQryEndPag()' style='cursor:hand'>");
/* 106 */       out.println(" <img name='goPage' src='img/go.gif' boder='0' align='absmiddle'/>");
/* 107 */       out.println("</span>");
/*     */ 
/* 109 */       out.println("<script language='javascript'>");
/* 110 */       out.println("function onQryEndPag() {");
/* 111 */       out.println("    alert(onQryEndPag);");
/* 112 */       out.println("    location.href='" + this._txncod + "?REQ_TYP=P&PAG_IDX=00+document.getElementById('pageno').value+&REQ_TYP=P" + buf + "&output=" + this._output + "';");
/* 113 */       out.println("    return false;");
/* 114 */       out.println("}");
/* 115 */       out.println("</script>");
/*     */     } catch (IOException e) {
/* 117 */       throw new JspException(e);
/*     */     }
/* 119 */     return 6;
/*     */   }
/*     */ 
/*     */   public int doStartTag() throws JspException
/*     */   {
/* 124 */     return super.doStartTag();
/*     */   }
/*     */ }