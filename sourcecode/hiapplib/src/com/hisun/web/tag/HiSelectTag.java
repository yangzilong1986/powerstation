 package com.hisun.web.tag;
 
 import com.hisun.client.HiInvokeService;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.LinkedHashMap;
 import java.util.List;
 import java.util.Set;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyContent;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 import javax.servlet.jsp.tagext.DynamicAttributes;
 import net.sf.json.JSONObject;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.SystemUtils;
 import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
 
 public class HiSelectTag extends BodyTagSupport
   implements DynamicAttributes
 {
   private String code;
   private String sql;
   private String colName;
   private String colValue;
   private String param;
   private HashMap paramMap;
   private String initValue;
   private String grpName;
   private Logger log;
 
   public HiSelectTag()
   {
     this.colName = "name";
 
     this.colValue = "value";
 
     this.grpName = "GRP";
     this.log = HiLog.getLogger("SYS.trc"); }
 
   public int doAfterBody() throws JspException {
     super.doAfterBody();
     return 0;
   }
 
   public int doEndTag() throws JspException {
     JspWriter out = this.pageContext.getOut();
     HashMap valueMap = null;
     if (!(StringUtils.isBlank(this.code)))
       try {
         valueMap = processByCode(this.code, this.param);
       } catch (Exception e) {
         throw new JspException(e);
       }
     if (!(StringUtils.isBlank(this.sql))) {
       try {
         valueMap = processBySql(this.sql, this.param);
       } catch (HiException e) {
         throw new JspException(e);
       }
     }
     throw new JspException("invalid args");
 
     StringBuffer buf = new StringBuffer();
     buf.append("<select ");
     if (this.paramMap != null) {
       Iterator iter = this.paramMap.keySet().iterator();
       while (iter.hasNext()) {
         String key = (String)iter.next();
         String value = (String)this.paramMap.get(key);
         buf.append(key + "='" + value + "' ");
       }
     }
     buf.append(" >");
     String bodyStr = null;
     int fillerPos = -1;
     if (this.bodyContent != null) {
       bodyStr = this.bodyContent.getString();
     }
 
     if (bodyStr != null) {
       fillerPos = bodyStr.indexOf("<filler/>");
       if (fillerPos != -1) {
         buf.append(bodyStr.substring(0, fillerPos));
         fillerPos += "<filler/>".length();
       } else {
         buf.append(bodyStr);
       }
     }
 
     if (valueMap != null) {
       Iterator iter = valueMap.keySet().iterator();
       while (iter.hasNext()) {
         String name = (String)iter.next();
         String value = (String)valueMap.get(name);
         buf.append("<option value='" + value + "'");
         if (StringUtils.equals(this.initValue, value)) {
           buf.append(" selected ");
         }
         buf.append(" >" + name + "</option>");
         buf.append(SystemUtils.LINE_SEPARATOR);
       }
     }
     if ((bodyStr != null) && (fillerPos != -1)) {
       buf.append(bodyStr.substring(fillerPos));
     }
     buf.append("</select>");
     try {
       out.print(buf);
     } catch (IOException e) {
       throw new JspException(e);
     }
     super.doEndTag();
     return 6;
   }
 
   public LinkedHashMap processByCode(String code, String param) throws Exception
   {
     HiInvokeService service = HiInvokeService.createService();
     LinkedHashMap valueMap = new LinkedHashMap();
     if (StringUtils.isNotBlank(param)) {
       root = HiETFUtils.fromJSON(JSONObject.fromObject(param));
 
       service.setETF(root);
     }
     if (this.log.isDebugEnabled()) {
       this.log.debug("[" + this.colName + "][" + this.colValue + "]");
     }
     if (this.log.isDebugEnabled()) {
       this.log.debug("send data:[" + service.toString() + "]");
     }
     HiETF root = service.invokeRetETF(code);
     if (this.log.isDebugEnabled()) {
       this.log.debug("recv data:[" + root + "]");
     }
     for (int i = 0; ; i++) {
       HiETF grpNod = root.getChildNode(this.grpName + "_" + (i + 1));
       if (grpNod == null) break; if (grpNod.isEndNode()) {
         break;
       }
       valueMap.put(grpNod.getChildValue(this.colName), grpNod.getChildValue(this.colValue));
     }
 
     return valueMap;
   }
 
   public LinkedHashMap processBySql(String sql, String param) throws HiException
   {
     HiDataBaseUtil dbUtil = new HiDataBaseUtil();
     try
     {
       List list;
       String[] tmps;
       if (param != null) {
         tmps = param.split("\\|");
         list = dbUtil.execQuery(sql, tmps);
       } else {
         list = dbUtil.execQuery(sql);
       }
       if (list == null) {
         tmps = null;
         return tmps;
       }
       LinkedHashMap map = new LinkedHashMap();
       for (int i = 0; i < list.size(); ++i) {
         HashMap rec = (HashMap)list.get(i);
         map.put(rec.get(this.colName), rec.get(this.colValue));
       }
       i = map;
 
       return i; } finally { dbUtil.close();
     }
   }
 
   public String getCode() {
     return this.code;
   }
 
   public void setCode(String code) throws JspException {
     if (code == null) {
       this.code = "";
       return;
     }
     this.code = ((String)ExpressionEvaluatorManager.evaluate("code", code, Object.class, this, this.pageContext));
   }
 
   public String getSql()
   {
     return this.sql;
   }
 
   public void setSql(String sql) throws JspException {
     if (sql == null) {
       this.sql = "";
       return;
     }
     this.sql = ((String)ExpressionEvaluatorManager.evaluate("sql", sql, Object.class, this, this.pageContext));
   }
 
   public String getParam()
   {
     return this.param;
   }
 
   public void setParam(String param) throws JspException
   {
     if (param == null) {
       this.param = "";
       return;
     }
     this.param = ((String)ExpressionEvaluatorManager.evaluate("param", param, Object.class, this, this.pageContext));
 
     if (StringUtils.isBlank(this.param)) {
       return;
     }
     if ((!(this.param.startsWith("{"))) && (!(this.param.endsWith("}"))))
       this.param = "{" + this.param + "}";
   }
 
   public void setInitValue(String initValue) throws JspException
   {
     if (initValue == null) {
       this.initValue = "";
       return;
     }
     this.initValue = ((String)ExpressionEvaluatorManager.evaluate("initValue", initValue, Object.class, this, this.pageContext));
   }
 
   public void setCols(String cols) throws JspException
   {
     int idx = cols.indexOf(124);
     if (idx == -1) {
       throw new JspException("names invalid");
     }
     this.colValue = cols.substring(0, idx);
     this.colName = cols.substring(idx + 1);
   }
 
   public void setDynamicAttribute(String uri, String name, Object value) throws JspException
   {
     if ("sql".equalsIgnoreCase(name)) {
       setSql((String)value);
       return;
     }
 
     if ("code".equalsIgnoreCase(name)) {
       setCode((String)value);
       return;
     }
 
     if ("param".equalsIgnoreCase(name)) {
       setParam((String)value);
       return;
     }
 
     if ("cols".equalsIgnoreCase(name)) {
       setCols((String)value);
       return;
     }
 
     if ("initValue".equalsIgnoreCase(name)) {
       setInitValue((String)value);
       return;
     }
 
     if ("grpName".equalsIgnoreCase(name)) {
       setGrpName((String)value);
       return;
     }
 
     if (this.paramMap == null) {
       this.paramMap = new HashMap();
     }
     this.paramMap.put(name, value);
   }
 
   public static void main(String[] args) {
     String s = "{hello1='world1',hello2:[{s1='t1', s2='t2'},{s1='t11', s2='t22'}]}";
     System.out.println(HiETFUtils.fromJSON(JSONObject.fromObject(s)));
   }
 
   public String getGrpName()
   {
     return this.grpName;
   }
 
   public void setGrpName(String grpName)
   {
     this.grpName = grpName;
   }
 }