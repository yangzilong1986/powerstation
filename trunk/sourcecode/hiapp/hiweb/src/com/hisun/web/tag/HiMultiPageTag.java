 package com.hisun.web.tag;
 
 import java.io.IOException;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Set;
 import javax.servlet.ServletRequest;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.DynamicAttributes;
 import javax.servlet.jsp.tagext.TagSupport;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
 
 public class HiMultiPageTag extends TagSupport
   implements DynamicAttributes
 {
   private String _output;
   private String _url;
   private HashMap paramMap;
   private String _pagNum;
   private static final String PAG_NO = "PAG_NO";
   private static final String PAG_NUM = "PAG_NUM";
   private static final String PAG_IDX = "PAG_IDX";
   private static final String PAG_KEY = "PAG_KEY";
   private static final String PAG_CNT = "PAG_CNT";
 
   public HiMultiPageTag()
   {
     this._output = "";
 
     this.paramMap = null;
     this._pagNum = "20";
   }
 
   public int doStartTag()
     throws JspException
   {
     return super.doStartTag();
   }
 
   public int doEndTag() throws JspException {
     ServletRequest request = this.pageContext.getRequest();
     HashMap etf = (HashMap)request.getAttribute("ETF");
     if (etf == null)
       return 6;
     JspWriter out = this.pageContext.getOut();
     try {
       out.print(getPageData(etf));
     } catch (IOException e) {
       throw new JspException(e);
     }
     return 6;
   }
 
   private String getPageData(HashMap etf) {
     StringBuffer paramBuf = new StringBuffer();
     if (this.paramMap != null) {
       Iterator iter = this.paramMap.keySet().iterator();
       while (iter.hasNext()) {
         String tmp = (String)iter.next();
         String value = (String)this.paramMap.get(tmp);
         if (StringUtils.isBlank(value)) {
           continue;
         }
 
         paramBuf.append("&" + tmp + "=" + value);
       }
     }
 
     int pagNo = NumberUtils.toInt((String)etf.get("PAG_NO"));
     int pagCnt = NumberUtils.toInt((String)etf.get("PAG_CNT"));
     String pagKey = (String)etf.get("PAG_KEY");
     StringBuffer buf = new StringBuffer();
 
     StringBuffer buf1 = new StringBuffer();
     buf1.append("<a href='");
     buf1.append(this._url);
     buf1.append("?");
     buf1.append("REQ_TYP=P");
     buf1.append("&");
 
     StringBuffer buf2 = new StringBuffer();
     buf2.append("&");
     buf2.append("PAG_KEY=" + pagKey);
     buf2.append("&");
     buf2.append("PAG_NUM=" + this._pagNum);
 
     if (this._output != null) {
       buf2.append("&");
       buf2.append("output=" + this._output);
     }
 
     if (paramBuf.length() != 0) {
       buf2.append(paramBuf);
     }
 
     buf2.append("'>");
 
     if (pagNo > 1) {
       buf.append(buf1);
       buf.append("PAG_IDX=00" + (pagNo - 1));
       buf.append(buf2);
       buf.append("<  前页");
       buf.append("</a>&nbsp");
     }
 
     int k = pagNo / 10 * 10;
     int l = (pagNo / 10 + 1) * 10;
 
     if (l > pagCnt) {
       l = pagCnt;
     }
     for (int i = k + 1; i <= l; ++i) {
       if (pagNo == i) {
         buf.append("<b>" + i + "</b>&nbsp");
       }
       else {
         buf.append(buf1);
         buf.append("PAG_IDX=00" + i);
         buf.append(buf2);
         buf.append(i);
         buf.append("</a>&nbsp");
       }
     }
     if (pagNo < pagCnt) {
       buf.append(buf1);
       buf.append("PAG_IDX=00" + (pagNo + 1));
       buf.append(buf2);
       buf.append("后页 >");
       buf.append("</a>");
     }
     return buf.toString();
   }
 
   public String getOutput() {
     return this._output;
   }
 
   public void setOutput(String output) throws JspException {
     if (output == null) {
       this._output = "";
       return;
     }
     this._output = ((String)ExpressionEvaluatorManager.evaluate("output", output, Object.class, this, this.pageContext));
   }
 
   public String getUrl() {
     return this._url;
   }
 
   public void setUrl(String url) throws JspException {
     if (url == null) {
       this._url = "";
       return;
     }
     this._url = ((String)ExpressionEvaluatorManager.evaluate("url", url, Object.class, this, this.pageContext));
   }
 
   public static void main(String[] args) throws JspException {
     HiMultiPageTag multiPage = new HiMultiPageTag();
     HashMap etf = new HashMap();
     multiPage.setUrl("021001.dow");
     multiPage.setOutput("book/book_index.jsp");
     multiPage.setPagnum("6");
 
     etf.put("CAT_ID", "1");
     etf.put("TST_ID", "2");
     etf.put("PAG_KEY", "KEY123");
     etf.put("PAG_NUM", "6");
 
     etf.put("PAG_NO", "3");
     etf.put("PAG_CNT", "3");
     System.out.println(multiPage.getPageData(etf));
   }
 
   public String getPagnum() {
     return this._pagNum;
   }
 
   public void setPagnum(String pagNum) throws JspException {
     this._pagNum = ((String)ExpressionEvaluatorManager.evaluate("pagnum", pagNum, Object.class, this, this.pageContext));
     if (StringUtils.isBlank(this._pagNum))
       this._pagNum = "20";
   }
 
   public void setDynamicAttribute(String uri, String name, Object value) throws JspException
   {
     if ("url".equalsIgnoreCase(name)) {
       setUrl((String)value);
       return;
     }
     if ("output".equalsIgnoreCase(name)) {
       setOutput((String)value);
       return;
     }
     if ("pagnum".equalsIgnoreCase(name)) {
       setPagnum((String)value);
       return;
     }
     if (this.paramMap == null) {
       this.paramMap = new HashMap();
     }
     this.paramMap.put(name, value);
   }
 }