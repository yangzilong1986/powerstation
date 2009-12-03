/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
/*     */ 
/*     */ public class HiFileMultiPageTag extends BodyTagSupport
/*     */ {
/*     */   private Logger _log;
/*     */   private String _file;
/*     */   private String[] _cols;
/*     */   private String _separator;
/*     */   private int _pageNo;
/*     */   private int _recNum;
/*     */   private String _charSet;
/*     */   private String _groupName;
/*     */ 
/*     */   public HiFileMultiPageTag()
/*     */   {
/*  30 */     this._log = HiLog.getLogger("SYS.trc");
/*     */ 
/*  33 */     this._separator = " \t";
/*  34 */     this._pageNo = 1;
/*  35 */     this._recNum = 19;
/*     */ 
/*  37 */     this._groupName = "GRP";
/*     */   }
/*     */ 
/*     */   public int doEndTag() throws JspException {
/*  41 */     ArrayList multiPageList = loadFile();
/*     */ 
/*  43 */     ArrayList tmpList = new ArrayList();
/*     */ 
/*  45 */     int totalPage = multiPageList.size() / this._recNum + ((multiPageList.size() % this._recNum == 0) ? 0 : 1);
/*     */ 
/*  47 */     if (this._pageNo <= 0) {
/*  48 */       this._pageNo = 1;
/*     */     }
/*     */ 
/*  51 */     if (this._pageNo >= multiPageList.size() / this._recNum) {
/*  52 */       this._pageNo = totalPage;
/*     */     }
/*     */ 
/*  55 */     int i = (this._pageNo - 1) * this._recNum;
/*  56 */     for (; i < (this._pageNo - 1) * this._recNum + this._recNum; ++i) {
/*  57 */       if (i >= multiPageList.size()) {
/*     */         break;
/*     */       }
/*     */ 
/*  61 */       String[] values = (String[])(String[])multiPageList.get(i);
/*  62 */       HashMap tmpMap = new HashMap();
/*  63 */       for (int j = 0; j < this._cols.length; ++j) {
/*  64 */         if (j >= values.length)
/*  65 */           tmpMap.put(this._cols[j], "");
/*     */         else {
/*  67 */           tmpMap.put(this._cols[j], values[j]);
/*     */         }
/*     */       }
/*     */ 
/*  71 */       tmpList.add(tmpMap);
/*     */     }
/*  73 */     HashMap root = (HashMap)this.pageContext.getRequest().getAttribute("ETF");
/*     */ 
/*  75 */     if (root == null) {
/*  76 */       root = new HashMap();
/*  77 */       this.pageContext.getRequest().setAttribute("ETF", root);
/*     */     }
/*  79 */     root.put(this._groupName, tmpList);
/*  80 */     root.put("PAG_CNT", Integer.valueOf(totalPage));
/*  81 */     root.put("REC_NUM", Integer.valueOf(this._recNum));
/*  82 */     root.put("PAG_NO", Integer.valueOf(this._pageNo));
/*  83 */     root.put("TOT_REC_NUM", Integer.valueOf(multiPageList.size()));
/*  84 */     super.doEndTag();
/*  85 */     return 6;
/*     */   }
/*     */ 
/*     */   public String getFile() {
/*  89 */     return this._file;
/*     */   }
/*     */ 
/*     */   public void setFile(String file) throws JspException {
/*  93 */     this._file = ((String)ExpressionEvaluatorManager.evaluate("file", file, Object.class, this, this.pageContext));
/*     */ 
/*  95 */     if ((this._file == null) || (this._file.equals("")))
/*  96 */       throw new JspException("file property is empty");
/*     */   }
/*     */ 
/*     */   public String getCols()
/*     */   {
/* 102 */     return "";
/*     */   }
/*     */ 
/*     */   public void setCols(String cols) throws JspException {
/* 106 */     String tmp = (String)ExpressionEvaluatorManager.evaluate("cols", cols, Object.class, this, this.pageContext);
/*     */ 
/* 108 */     this._cols = tmp.split("\\|");
/* 109 */     if ((this._cols == null) || (this._cols.length == 0))
/* 110 */       throw new JspException("cols property is empty");
/*     */   }
/*     */ 
/*     */   public String getSeparator()
/*     */   {
/* 116 */     return this._separator;
/*     */   }
/*     */ 
/*     */   public void setSeparator(String separator) throws JspException {
/* 120 */     this._separator = ((String)ExpressionEvaluatorManager.evaluate("separator", separator, Object.class, this, this.pageContext));
/*     */ 
/* 122 */     if (StringUtils.isEmpty(this._separator))
/* 123 */       throw new JspException("separator property is empty");
/*     */   }
/*     */ 
/*     */   public String getRecNum()
/*     */   {
/* 129 */     return String.valueOf(this._recNum);
/*     */   }
/*     */ 
/*     */   public void setRecNum(String recNum) throws JspException {
/* 133 */     String tmp = (String)ExpressionEvaluatorManager.evaluate("recNum", recNum, Object.class, this, this.pageContext);
/*     */ 
/* 135 */     this._recNum = NumberUtils.toInt(tmp);
/* 136 */     if (this._recNum <= 0)
/* 137 */       this._recNum = 19;
/*     */   }
/*     */ 
/*     */   public String getPageNo()
/*     */   {
/* 142 */     return String.valueOf(this._pageNo);
/*     */   }
/*     */ 
/*     */   public void setPageNo(String pageNo) throws JspException {
/* 146 */     String tmp = (String)ExpressionEvaluatorManager.evaluate("pageNo", pageNo, Object.class, this, this.pageContext);
/*     */ 
/* 148 */     this._pageNo = NumberUtils.toInt(pageNo);
/* 149 */     if (this._pageNo <= 0)
/* 150 */       this._pageNo = 1;
/*     */   }
/*     */ 
/*     */   public String getGroupName()
/*     */   {
/* 155 */     return this._separator;
/*     */   }
/*     */ 
/*     */   public void setGroupName(String groupName) throws JspException {
/* 159 */     this._groupName = ((String)ExpressionEvaluatorManager.evaluate("groupName", groupName, Object.class, this, this.pageContext));
/*     */ 
/* 161 */     if (StringUtils.isEmpty(this._groupName))
/* 162 */       throw new JspException("groupName property is empty");
/*     */   }
/*     */ 
/*     */   public String getCharSet()
/*     */   {
/* 167 */     return this._charSet;
/*     */   }
/*     */ 
/*     */   public void setCharSet(String charSet) throws JspException {
/* 171 */     this._charSet = ((String)ExpressionEvaluatorManager.evaluate("charSet", charSet, Object.class, this, this.pageContext));
/*     */ 
/* 173 */     if (StringUtils.isEmpty(this._charSet))
/* 174 */       throw new JspException("charSet property is empty");
/*     */   }
/*     */ 
/*     */   private synchronized ArrayList loadFile() throws JspException {
/* 178 */     ArrayList multiPageList = (ArrayList)this.pageContext.getServletContext().getAttribute("__FILE_MULTI_PAGE");
/*     */ 
/* 180 */     if (multiPageList != null)
/*     */     {
/*     */       File f;
/* 181 */       URL url = HiResource.getResource(this._file);
/* 182 */       if (url == null) {
/* 183 */         throw new JspException("[" + this._file + "] not existed");
/*     */       }
/*     */       try
/*     */       {
/* 187 */         f = new File(url.toURI());
/*     */       } catch (URISyntaxException e) {
/* 189 */         throw new JspException("open [" + this._file + "] failure", e);
/*     */       }
/* 191 */       Long lastModified = (Long)this.pageContext.getServletContext().getAttribute("__FILE_MULTI_PAGE_LAST_MODIFIED");
/*     */ 
/* 194 */       if ((lastModified != null) && (lastModified.longValue() == f.lastModified())) {
/* 195 */         return multiPageList;
/*     */       }
/* 197 */       if (this._log.isInfoEnabled()) {
/* 198 */         this._log.info("[" + f.getAbsoluteFile() + "] reloaded");
/*     */       }
/* 200 */       multiPageList.clear();
/*     */     } else {
/* 202 */       multiPageList = new ArrayList();
/*     */     }
/*     */ 
/* 205 */     BufferedReader br = null;
/*     */     try {
/* 207 */       URL url = HiResource.getResource(this._file);
/* 208 */       if (url == null) {
/* 209 */         throw new JspException("[" + this._file + "] not existed");
/*     */       }
/* 211 */       File f = new File(url.toURI());
/* 212 */       if (this._charSet != null)
/* 213 */         br = new BufferedReader(new InputStreamReader(new FileInputStream(f), this._charSet));
/*     */       else {
/* 215 */         br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
/*     */       }
/* 217 */       this.pageContext.getServletContext().setAttribute("__FILE_MULTI_PAGE_LAST_MODIFIED", Long.valueOf(f.lastModified()));
/*     */ 
/* 221 */       int i = 1;
/* 222 */       while ((line = br.readLine()) != null)
/*     */       {
/*     */         String line;
/* 223 */         if (line.startsWith("#")) {
/*     */           continue;
/*     */         }
/* 226 */         tmps = StringUtils.split(line, this._separator);
/* 227 */         if (tmps.length == 0) {
/*     */           continue;
/*     */         }
/* 230 */         multiPageList.add(tmps);
/*     */       }
/* 232 */       br.close();
/* 233 */       br = null;
/* 234 */       this.pageContext.getServletContext().setAttribute("__FILE_MULTI_PAGE", multiPageList);
/*     */ 
/* 237 */       String[] tmps = multiPageList;
/*     */ 
/* 248 */       return tmps;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */     catch (URISyntaxException e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/* 243 */       if (br != null)
/*     */         try {
/* 245 */           br.close();
/*     */         } catch (IOException e) {
/* 247 */           e.printStackTrace();
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/* 255 */     super.doStartTag();
/* 256 */     return 1;
/*     */   }
/*     */ }