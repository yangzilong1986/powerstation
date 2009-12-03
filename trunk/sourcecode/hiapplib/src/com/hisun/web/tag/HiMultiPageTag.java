/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.DynamicAttributes;
/*     */ import javax.servlet.jsp.tagext.TagSupport;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
/*     */ 
/*     */ public class HiMultiPageTag extends TagSupport
/*     */   implements DynamicAttributes
/*     */ {
/*     */   private String _output;
/*     */   private String _url;
/*     */   private HashMap paramMap;
/*     */   private String _pagNum;
/*     */   private static final String PAG_NO = "PAG_NO";
/*     */   private static final String PAG_NUM = "PAG_NUM";
/*     */   private static final String PAG_IDX = "PAG_IDX";
/*     */   private static final String PAG_KEY = "PAG_KEY";
/*     */   private static final String PAG_CNT = "PAG_CNT";
/*     */ 
/*     */   public HiMultiPageTag()
/*     */   {
/*  25 */     this._output = "";
/*     */ 
/*  27 */     this.paramMap = null;
/*  28 */     this._pagNum = "20";
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/*  55 */     return super.doStartTag();
/*     */   }
/*     */ 
/*     */   public int doEndTag() throws JspException {
/*  59 */     ServletRequest request = this.pageContext.getRequest();
/*  60 */     HashMap etf = (HashMap)request.getAttribute("ETF");
/*  61 */     if (etf == null)
/*  62 */       return 6;
/*  63 */     JspWriter out = this.pageContext.getOut();
/*     */     try {
/*  65 */       out.print(getPageData(etf));
/*     */     } catch (IOException e) {
/*  67 */       throw new JspException(e);
/*     */     }
/*  69 */     return 6;
/*     */   }
/*     */ 
/*     */   private String getPageData(HashMap etf) {
/*  73 */     StringBuffer paramBuf = new StringBuffer();
/*  74 */     if (this.paramMap != null) {
/*  75 */       Iterator iter = this.paramMap.keySet().iterator();
/*  76 */       while (iter.hasNext()) {
/*  77 */         String tmp = (String)iter.next();
/*  78 */         String value = (String)this.paramMap.get(tmp);
/*  79 */         if (StringUtils.isBlank(value)) {
/*     */           continue;
/*     */         }
/*     */ 
/*  83 */         paramBuf.append("&" + tmp + "=" + value);
/*     */       }
/*     */     }
/*     */ 
/*  87 */     int pagNo = NumberUtils.toInt((String)etf.get("PAG_NO"));
/*  88 */     int pagCnt = NumberUtils.toInt((String)etf.get("PAG_CNT"));
/*  89 */     String pagKey = (String)etf.get("PAG_KEY");
/*  90 */     StringBuffer buf = new StringBuffer();
/*     */ 
/*  92 */     StringBuffer buf1 = new StringBuffer();
/*  93 */     buf1.append("<a href='");
/*  94 */     buf1.append(this._url);
/*  95 */     buf1.append("?");
/*  96 */     buf1.append("REQ_TYP=P");
/*  97 */     buf1.append("&");
/*     */ 
/*  99 */     StringBuffer buf2 = new StringBuffer();
/* 100 */     buf2.append("&");
/* 101 */     buf2.append("PAG_KEY=" + pagKey);
/* 102 */     buf2.append("&");
/* 103 */     buf2.append("PAG_NUM=" + this._pagNum);
/*     */ 
/* 105 */     if (this._output != null) {
/* 106 */       buf2.append("&");
/* 107 */       buf2.append("output=" + this._output);
/*     */     }
/*     */ 
/* 110 */     if (paramBuf.length() != 0) {
/* 111 */       buf2.append(paramBuf);
/*     */     }
/*     */ 
/* 114 */     buf2.append("'>");
/*     */ 
/* 116 */     if (pagNo > 1) {
/* 117 */       buf.append(buf1);
/* 118 */       buf.append("PAG_IDX=00" + (pagNo - 1));
/* 119 */       buf.append(buf2);
/* 120 */       buf.append("<  前页");
/* 121 */       buf.append("</a>&nbsp");
/*     */     }
/*     */ 
/* 125 */     int k = pagNo / 10 * 10;
/* 126 */     int l = (pagNo / 10 + 1) * 10;
/*     */ 
/* 131 */     if (l > pagCnt) {
/* 132 */       l = pagCnt;
/*     */     }
/* 134 */     for (int i = k + 1; i <= l; ++i) {
/* 135 */       if (pagNo == i) {
/* 136 */         buf.append("<b>" + i + "</b>&nbsp");
/*     */       }
/*     */       else {
/* 139 */         buf.append(buf1);
/* 140 */         buf.append("PAG_IDX=00" + i);
/* 141 */         buf.append(buf2);
/* 142 */         buf.append(i);
/* 143 */         buf.append("</a>&nbsp");
/*     */       }
/*     */     }
/* 146 */     if (pagNo < pagCnt) {
/* 147 */       buf.append(buf1);
/* 148 */       buf.append("PAG_IDX=00" + (pagNo + 1));
/* 149 */       buf.append(buf2);
/* 150 */       buf.append("后页 >");
/* 151 */       buf.append("</a>");
/*     */     }
/* 153 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String getOutput() {
/* 157 */     return this._output;
/*     */   }
/*     */ 
/*     */   public void setOutput(String output) throws JspException {
/* 161 */     if (output == null) {
/* 162 */       this._output = "";
/* 163 */       return;
/*     */     }
/* 165 */     this._output = ((String)ExpressionEvaluatorManager.evaluate("output", output, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/* 169 */     return this._url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url) throws JspException {
/* 173 */     if (url == null) {
/* 174 */       this._url = "";
/* 175 */       return;
/*     */     }
/* 177 */     this._url = ((String)ExpressionEvaluatorManager.evaluate("url", url, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws JspException {
/* 181 */     HiMultiPageTag multiPage = new HiMultiPageTag();
/* 182 */     HashMap etf = new HashMap();
/* 183 */     multiPage.setUrl("021001.dow");
/* 184 */     multiPage.setOutput("book/book_index.jsp");
/* 185 */     multiPage.setPagnum("6");
/*     */ 
/* 187 */     etf.put("CAT_ID", "1");
/* 188 */     etf.put("TST_ID", "2");
/* 189 */     etf.put("PAG_KEY", "KEY123");
/* 190 */     etf.put("PAG_NUM", "6");
/*     */ 
/* 192 */     etf.put("PAG_NO", "3");
/* 193 */     etf.put("PAG_CNT", "3");
/* 194 */     System.out.println(multiPage.getPageData(etf));
/*     */   }
/*     */ 
/*     */   public String getPagnum() {
/* 198 */     return this._pagNum;
/*     */   }
/*     */ 
/*     */   public void setPagnum(String pagNum) throws JspException {
/* 202 */     this._pagNum = ((String)ExpressionEvaluatorManager.evaluate("pagnum", pagNum, Object.class, this, this.pageContext));
/* 203 */     if (StringUtils.isBlank(this._pagNum))
/* 204 */       this._pagNum = "20";
/*     */   }
/*     */ 
/*     */   public void setDynamicAttribute(String uri, String name, Object value) throws JspException
/*     */   {
/* 209 */     if ("url".equalsIgnoreCase(name)) {
/* 210 */       setUrl((String)value);
/* 211 */       return;
/*     */     }
/* 213 */     if ("output".equalsIgnoreCase(name)) {
/* 214 */       setOutput((String)value);
/* 215 */       return;
/*     */     }
/* 217 */     if ("pagnum".equalsIgnoreCase(name)) {
/* 218 */       setPagnum((String)value);
/* 219 */       return;
/*     */     }
/* 221 */     if (this.paramMap == null) {
/* 222 */       this.paramMap = new HashMap();
/*     */     }
/* 224 */     this.paramMap.put(name, value);
/*     */   }
/*     */ }