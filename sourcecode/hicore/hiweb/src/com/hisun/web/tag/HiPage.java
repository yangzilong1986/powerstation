 package com.hisun.web.tag;

 import freemarker.template.utility.StringUtil;
 import org.apache.commons.lang.math.NumberUtils;

 import javax.servlet.ServletRequest;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.TagSupport;
 import java.io.IOException;
 import java.util.HashMap;
 
 public class HiPage extends TagSupport
 {
   public String _output;
   public String _txncod;
   public String _fields;
 
   public HiPage()
   {
     this._fields = null; }
 
   public String getOutput() {
     return this._output;
   }
 
   public void setOutput(String output) {
     this._output = output;
   }
 
   public String getTxncod() {
     return this._txncod;
   }
 
   public void setTxncod(String txncod) {
     this._txncod = txncod;
   }
 
   public String getfields() {
     return this._fields;
   }
 
   public void setfields(String _fields) {
     this._fields = _fields;
   }
 
   public int doEndTag() throws JspException {
     ServletRequest request = this.pageContext.getRequest();
     HashMap map = (HashMap)request.getAttribute("ETF");
     if (map == null)
       return 6;
     JspWriter out = this.pageContext.getOut();
     StringBuffer buf = new StringBuffer();
     if (this._fields != null) {
       String[] field = StringUtil.split(this._fields, '|');
       for (String a : field) {
         String value = (String)map.get(a);
         if (value == null) {
           continue;
         }
         buf.append("&" + a + "=" + value);
       }
     }
     try {
       int recNum = NumberUtils.toInt((String)map.get("REC_NUM"));
       int pageNo = NumberUtils.toInt((String)map.get("PAG_NO"));
       int pageCnt = NumberUtils.toInt((String)map.get("PAG_CNT"));
       String pagKey = (String)map.get("PAG_KEY");
       if ((pageNo != 1) && (pageNo != 0)) {
         out.println("<a href='" + this._txncod + "?REQ_TYP=P&PAG_IDX=050000&PAG_KEY=" + pagKey + buf + "&output=" + this._output + "'" + " onmouseover='setStatusBar('转到首页'); return true'" + ">");
       }
       out.println("<img src='img/first-02.gif' alt='首页' border='0' style='margin-top:5px' align='absmiddle'/>");
       if ((pageNo != 1) && (pageNo != 0)) {
         out.println("</a>");
       }
 
       if ((pageNo != 1) && (pageNo != 0)) {
         out.println("<a href='" + this._txncod + "?REQ_TYP=P&PAG_IDX=070000&PAG_KEY=" + pagKey + buf + "&output=" + this._output + "'" + " onmouseover='setStatusBar('转到上一页'); return true'" + ">");
       }
       out.println("<img src='img/prev-02.gif' alt='上一页' border='0' style='margin-top:5px' align='absmiddle'/>");
       if ((pageNo != 1) && (pageNo != 0)) {
         out.println("</a>");
       }
 
       if (pageNo != pageCnt) {
         out.println("<a href='" + this._txncod + "?REQ_TYP=P&PAG_IDX=080000&PAG_KEY=" + pagKey + buf + "&output=" + this._output + "'" + " onmouseover='setStatusBar('转到下一页'); return true'" + ">");
       }
 
       out.println("<img src='img/next-02.gif' alt='下一页' border='0' style='margin-top:5px' align='absmiddle'/>");
       if (pageNo != pageCnt) {
         out.println("</a>");
       }
 
       if (pageNo != pageCnt) {
         out.println("<a href='" + this._txncod + "?REQ_TYP=P&PAG_IDX=060000&PAG_KEY=" + pagKey + buf + "&output=" + this._output + "'" + " onmouseover='setStatusBar('转到尾页'); return true'" + ">");
       }
 
       out.println("<img src='img/last-02.gif' alt='尾页' border='0' style='margin-top:5px' align='absmiddle'/>");
       if (pageNo != pageCnt) {
         out.println("</a>");
       }
       out.println("转到&nbsp;");
       out.println("<input name='pageno' id='pageno' class='INPUT_TEXT' type='text' style='width:24px'/>");
 
       out.println("<span onclick='onQryEndPag()' style='cursor:hand'>");
       out.println(" <img name='goPage' src='img/go.gif' boder='0' align='absmiddle'/>");
       out.println("</span>");
 
       out.println("<script language='javascript'>");
       out.println("function onQryEndPag() {");
       out.println("    alert(onQryEndPag);");
       out.println("    location.href='" + this._txncod + "?REQ_TYP=P&PAG_IDX=00+document.getElementById('pageno').value+&REQ_TYP=P" + buf + "&output=" + this._output + "';");
       out.println("    return false;");
       out.println("}");
       out.println("</script>");
     } catch (IOException e) {
       throw new JspException(e);
     }
     return 6;
   }
 
   public int doStartTag() throws JspException
   {
     return super.doStartTag();
   }
 }