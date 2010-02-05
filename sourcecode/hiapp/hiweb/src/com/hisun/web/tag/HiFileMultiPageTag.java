 package com.hisun.web.tag;
 
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.util.HiResource;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.net.URISyntaxException;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.HashMap;
 import javax.servlet.ServletContext;
 import javax.servlet.ServletRequest;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
 
 public class HiFileMultiPageTag extends BodyTagSupport
 {
   private Logger _log;
   private String _file;
   private String[] _cols;
   private String _separator;
   private int _pageNo;
   private int _recNum;
   private String _charSet;
   private String _groupName;
 
   public HiFileMultiPageTag()
   {
     this._log = HiLog.getLogger("SYS.trc");
 
     this._separator = " \t";
     this._pageNo = 1;
     this._recNum = 19;
 
     this._groupName = "GRP";
   }
 
   public int doEndTag() throws JspException {
     ArrayList multiPageList = loadFile();
 
     ArrayList tmpList = new ArrayList();
 
     int totalPage = multiPageList.size() / this._recNum + ((multiPageList.size() % this._recNum == 0) ? 0 : 1);
 
     if (this._pageNo <= 0) {
       this._pageNo = 1;
     }
 
     if (this._pageNo >= multiPageList.size() / this._recNum) {
       this._pageNo = totalPage;
     }
 
     int i = (this._pageNo - 1) * this._recNum;
     for (; i < (this._pageNo - 1) * this._recNum + this._recNum; ++i) {
       if (i >= multiPageList.size()) {
         break;
       }
 
       String[] values = (String[])(String[])multiPageList.get(i);
       HashMap tmpMap = new HashMap();
       for (int j = 0; j < this._cols.length; ++j) {
         if (j >= values.length)
           tmpMap.put(this._cols[j], "");
         else {
           tmpMap.put(this._cols[j], values[j]);
         }
       }
 
       tmpList.add(tmpMap);
     }
     HashMap root = (HashMap)this.pageContext.getRequest().getAttribute("ETF");
 
     if (root == null) {
       root = new HashMap();
       this.pageContext.getRequest().setAttribute("ETF", root);
     }
     root.put(this._groupName, tmpList);
     root.put("PAG_CNT", Integer.valueOf(totalPage));
     root.put("REC_NUM", Integer.valueOf(this._recNum));
     root.put("PAG_NO", Integer.valueOf(this._pageNo));
     root.put("TOT_REC_NUM", Integer.valueOf(multiPageList.size()));
     super.doEndTag();
     return 6;
   }
 
   public String getFile() {
     return this._file;
   }
 
   public void setFile(String file) throws JspException {
     this._file = ((String)ExpressionEvaluatorManager.evaluate("file", file, Object.class, this, this.pageContext));
 
     if ((this._file == null) || (this._file.equals("")))
       throw new JspException("file property is empty");
   }
 
   public String getCols()
   {
     return "";
   }
 
   public void setCols(String cols) throws JspException {
     String tmp = (String)ExpressionEvaluatorManager.evaluate("cols", cols, Object.class, this, this.pageContext);
 
     this._cols = tmp.split("\\|");
     if ((this._cols == null) || (this._cols.length == 0))
       throw new JspException("cols property is empty");
   }
 
   public String getSeparator()
   {
     return this._separator;
   }
 
   public void setSeparator(String separator) throws JspException {
     this._separator = ((String)ExpressionEvaluatorManager.evaluate("separator", separator, Object.class, this, this.pageContext));
 
     if (StringUtils.isEmpty(this._separator))
       throw new JspException("separator property is empty");
   }
 
   public String getRecNum()
   {
     return String.valueOf(this._recNum);
   }
 
   public void setRecNum(String recNum) throws JspException {
     String tmp = (String)ExpressionEvaluatorManager.evaluate("recNum", recNum, Object.class, this, this.pageContext);
 
     this._recNum = NumberUtils.toInt(tmp);
     if (this._recNum <= 0)
       this._recNum = 19;
   }
 
   public String getPageNo()
   {
     return String.valueOf(this._pageNo);
   }
 
   public void setPageNo(String pageNo) throws JspException {
     String tmp = (String)ExpressionEvaluatorManager.evaluate("pageNo", pageNo, Object.class, this, this.pageContext);
 
     this._pageNo = NumberUtils.toInt(pageNo);
     if (this._pageNo <= 0)
       this._pageNo = 1;
   }
 
   public String getGroupName()
   {
     return this._separator;
   }
 
   public void setGroupName(String groupName) throws JspException {
     this._groupName = ((String)ExpressionEvaluatorManager.evaluate("groupName", groupName, Object.class, this, this.pageContext));
 
     if (StringUtils.isEmpty(this._groupName))
       throw new JspException("groupName property is empty");
   }
 
   public String getCharSet()
   {
     return this._charSet;
   }
 
   public void setCharSet(String charSet) throws JspException {
     this._charSet = ((String)ExpressionEvaluatorManager.evaluate("charSet", charSet, Object.class, this, this.pageContext));
 
     if (StringUtils.isEmpty(this._charSet))
       throw new JspException("charSet property is empty");
   }
 
   private synchronized ArrayList loadFile() throws JspException {
     ArrayList multiPageList = (ArrayList)this.pageContext.getServletContext().getAttribute("__FILE_MULTI_PAGE");
 
     if (multiPageList != null)
     {
       File f;
       URL url = HiResource.getResource(this._file);
       if (url == null) {
         throw new JspException("[" + this._file + "] not existed");
       }
       try
       {
         f = new File(url.toURI());
       } catch (URISyntaxException e) {
         throw new JspException("open [" + this._file + "] failure", e);
       }
       Long lastModified = (Long)this.pageContext.getServletContext().getAttribute("__FILE_MULTI_PAGE_LAST_MODIFIED");
 
       if ((lastModified != null) && (lastModified.longValue() == f.lastModified())) {
         return multiPageList;
       }
       if (this._log.isInfoEnabled()) {
         this._log.info("[" + f.getAbsoluteFile() + "] reloaded");
       }
       multiPageList.clear();
     } else {
       multiPageList = new ArrayList();
     }
 
     BufferedReader br = null;
     try {
       URL url = HiResource.getResource(this._file);
       if (url == null) {
         throw new JspException("[" + this._file + "] not existed");
       }
       File f = new File(url.toURI());
       if (this._charSet != null)
         br = new BufferedReader(new InputStreamReader(new FileInputStream(f), this._charSet));
       else {
         br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
       }
       this.pageContext.getServletContext().setAttribute("__FILE_MULTI_PAGE_LAST_MODIFIED", Long.valueOf(f.lastModified()));
 
       int i = 1;
       while ((line = br.readLine()) != null)
       {
         String line;
         if (line.startsWith("#")) {
           continue;
         }
         tmps = StringUtils.split(line, this._separator);
         if (tmps.length == 0) {
           continue;
         }
         multiPageList.add(tmps);
       }
       br.close();
       br = null;
       this.pageContext.getServletContext().setAttribute("__FILE_MULTI_PAGE", multiPageList);
 
       String[] tmps = multiPageList;
 
       return tmps;
     }
     catch (IOException e)
     {
     }
     catch (URISyntaxException e)
     {
     }
     finally
     {
       if (br != null)
         try {
           br.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
     }
   }
 
   public int doStartTag()
     throws JspException
   {
     super.doStartTag();
     return 1;
   }
 }