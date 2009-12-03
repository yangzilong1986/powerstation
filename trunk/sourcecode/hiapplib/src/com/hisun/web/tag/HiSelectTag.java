/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.client.HiInvokeService;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import javax.servlet.jsp.tagext.DynamicAttributes;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.SystemUtils;
/*     */ import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
/*     */ 
/*     */ public class HiSelectTag extends BodyTagSupport
/*     */   implements DynamicAttributes
/*     */ {
/*     */   private String code;
/*     */   private String sql;
/*     */   private String colName;
/*     */   private String colValue;
/*     */   private String param;
/*     */   private HashMap paramMap;
/*     */   private String initValue;
/*     */   private String grpName;
/*     */   private Logger log;
/*     */ 
/*     */   public HiSelectTag()
/*     */   {
/*  54 */     this.colName = "name";
/*     */ 
/*  58 */     this.colValue = "value";
/*     */ 
/*  74 */     this.grpName = "GRP";
/*  75 */     this.log = HiLog.getLogger("SYS.trc"); }
/*     */ 
/*     */   public int doAfterBody() throws JspException {
/*  78 */     super.doAfterBody();
/*  79 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doEndTag() throws JspException {
/*  83 */     JspWriter out = this.pageContext.getOut();
/*  84 */     HashMap valueMap = null;
/*  85 */     if (!(StringUtils.isBlank(this.code)))
/*     */       try {
/*  87 */         valueMap = processByCode(this.code, this.param);
/*     */       } catch (Exception e) {
/*  89 */         throw new JspException(e);
/*     */       }
/*  91 */     if (!(StringUtils.isBlank(this.sql))) {
/*     */       try {
/*  93 */         valueMap = processBySql(this.sql, this.param);
/*     */       } catch (HiException e) {
/*  95 */         throw new JspException(e);
/*     */       }
/*     */     }
/*  98 */     throw new JspException("invalid args");
/*     */ 
/* 101 */     StringBuffer buf = new StringBuffer();
/* 102 */     buf.append("<select ");
/* 103 */     if (this.paramMap != null) {
/* 104 */       Iterator iter = this.paramMap.keySet().iterator();
/* 105 */       while (iter.hasNext()) {
/* 106 */         String key = (String)iter.next();
/* 107 */         String value = (String)this.paramMap.get(key);
/* 108 */         buf.append(key + "='" + value + "' ");
/*     */       }
/*     */     }
/* 111 */     buf.append(" >");
/* 112 */     String bodyStr = null;
/* 113 */     int fillerPos = -1;
/* 114 */     if (this.bodyContent != null) {
/* 115 */       bodyStr = this.bodyContent.getString();
/*     */     }
/*     */ 
/* 118 */     if (bodyStr != null) {
/* 119 */       fillerPos = bodyStr.indexOf("<filler/>");
/* 120 */       if (fillerPos != -1) {
/* 121 */         buf.append(bodyStr.substring(0, fillerPos));
/* 122 */         fillerPos += "<filler/>".length();
/*     */       } else {
/* 124 */         buf.append(bodyStr);
/*     */       }
/*     */     }
/*     */ 
/* 128 */     if (valueMap != null) {
/* 129 */       Iterator iter = valueMap.keySet().iterator();
/* 130 */       while (iter.hasNext()) {
/* 131 */         String name = (String)iter.next();
/* 132 */         String value = (String)valueMap.get(name);
/* 133 */         buf.append("<option value='" + value + "'");
/* 134 */         if (StringUtils.equals(this.initValue, value)) {
/* 135 */           buf.append(" selected ");
/*     */         }
/* 137 */         buf.append(" >" + name + "</option>");
/* 138 */         buf.append(SystemUtils.LINE_SEPARATOR);
/*     */       }
/*     */     }
/* 141 */     if ((bodyStr != null) && (fillerPos != -1)) {
/* 142 */       buf.append(bodyStr.substring(fillerPos));
/*     */     }
/* 144 */     buf.append("</select>");
/*     */     try {
/* 146 */       out.print(buf);
/*     */     } catch (IOException e) {
/* 148 */       throw new JspException(e);
/*     */     }
/* 150 */     super.doEndTag();
/* 151 */     return 6;
/*     */   }
/*     */ 
/*     */   public LinkedHashMap processByCode(String code, String param) throws Exception
/*     */   {
/* 156 */     HiInvokeService service = HiInvokeService.createService();
/* 157 */     LinkedHashMap valueMap = new LinkedHashMap();
/* 158 */     if (StringUtils.isNotBlank(param)) {
/* 159 */       root = HiETFUtils.fromJSON(JSONObject.fromObject(param));
/*     */ 
/* 161 */       service.setETF(root);
/*     */     }
/* 163 */     if (this.log.isDebugEnabled()) {
/* 164 */       this.log.debug("[" + this.colName + "][" + this.colValue + "]");
/*     */     }
/* 166 */     if (this.log.isDebugEnabled()) {
/* 167 */       this.log.debug("send data:[" + service.toString() + "]");
/*     */     }
/* 169 */     HiETF root = service.invokeRetETF(code);
/* 170 */     if (this.log.isDebugEnabled()) {
/* 171 */       this.log.debug("recv data:[" + root + "]");
/*     */     }
/* 173 */     for (int i = 0; ; ++i) {
/* 174 */       HiETF grpNod = root.getChildNode(this.grpName + "_" + (i + 1));
/* 175 */       if (grpNod == null) break; if (grpNod.isEndNode()) {
/*     */         break;
/*     */       }
/* 178 */       valueMap.put(grpNod.getChildValue(this.colName), grpNod.getChildValue(this.colValue));
/*     */     }
/*     */ 
/* 181 */     return valueMap;
/*     */   }
/*     */ 
/*     */   public LinkedHashMap processBySql(String sql, String param) throws HiException
/*     */   {
/* 186 */     HiDataBaseUtil dbUtil = new HiDataBaseUtil();
/*     */     try
/*     */     {
/*     */       List list;
/*     */       String[] tmps;
/* 189 */       if (param != null) {
/* 190 */         tmps = param.split("\\|");
/* 191 */         list = dbUtil.execQuery(sql, tmps);
/*     */       } else {
/* 193 */         list = dbUtil.execQuery(sql);
/*     */       }
/* 195 */       if (list == null) {
/* 196 */         tmps = null;
/*     */         return tmps;
/*     */       }
/* 198 */       LinkedHashMap map = new LinkedHashMap();
/* 199 */       for (int i = 0; i < list.size(); ++i) {
/* 200 */         HashMap rec = (HashMap)list.get(i);
/* 201 */         map.put(rec.get(this.colName), rec.get(this.colValue));
/*     */       }
/* 203 */       i = map;
/*     */ 
/* 205 */       return i; } finally { dbUtil.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getCode() {
/* 210 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code) throws JspException {
/* 214 */     if (code == null) {
/* 215 */       this.code = "";
/* 216 */       return;
/*     */     }
/* 218 */     this.code = ((String)ExpressionEvaluatorManager.evaluate("code", code, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getSql()
/*     */   {
/* 223 */     return this.sql;
/*     */   }
/*     */ 
/*     */   public void setSql(String sql) throws JspException {
/* 227 */     if (sql == null) {
/* 228 */       this.sql = "";
/* 229 */       return;
/*     */     }
/* 231 */     this.sql = ((String)ExpressionEvaluatorManager.evaluate("sql", sql, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getParam()
/*     */   {
/* 236 */     return this.param;
/*     */   }
/*     */ 
/*     */   public void setParam(String param) throws JspException
/*     */   {
/* 241 */     if (param == null) {
/* 242 */       this.param = "";
/* 243 */       return;
/*     */     }
/* 245 */     this.param = ((String)ExpressionEvaluatorManager.evaluate("param", param, Object.class, this, this.pageContext));
/*     */ 
/* 247 */     if (StringUtils.isBlank(this.param)) {
/* 248 */       return;
/*     */     }
/* 250 */     if ((!(this.param.startsWith("{"))) && (!(this.param.endsWith("}"))))
/* 251 */       this.param = "{" + this.param + "}";
/*     */   }
/*     */ 
/*     */   public void setInitValue(String initValue) throws JspException
/*     */   {
/* 256 */     if (initValue == null) {
/* 257 */       this.initValue = "";
/* 258 */       return;
/*     */     }
/* 260 */     this.initValue = ((String)ExpressionEvaluatorManager.evaluate("initValue", initValue, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public void setCols(String cols) throws JspException
/*     */   {
/* 265 */     int idx = cols.indexOf(124);
/* 266 */     if (idx == -1) {
/* 267 */       throw new JspException("names invalid");
/*     */     }
/* 269 */     this.colValue = cols.substring(0, idx);
/* 270 */     this.colName = cols.substring(idx + 1);
/*     */   }
/*     */ 
/*     */   public void setDynamicAttribute(String uri, String name, Object value) throws JspException
/*     */   {
/* 275 */     if ("sql".equalsIgnoreCase(name)) {
/* 276 */       setSql((String)value);
/* 277 */       return;
/*     */     }
/*     */ 
/* 280 */     if ("code".equalsIgnoreCase(name)) {
/* 281 */       setCode((String)value);
/* 282 */       return;
/*     */     }
/*     */ 
/* 285 */     if ("param".equalsIgnoreCase(name)) {
/* 286 */       setParam((String)value);
/* 287 */       return;
/*     */     }
/*     */ 
/* 290 */     if ("cols".equalsIgnoreCase(name)) {
/* 291 */       setCols((String)value);
/* 292 */       return;
/*     */     }
/*     */ 
/* 295 */     if ("initValue".equalsIgnoreCase(name)) {
/* 296 */       setInitValue((String)value);
/* 297 */       return;
/*     */     }
/*     */ 
/* 300 */     if ("grpName".equalsIgnoreCase(name)) {
/* 301 */       setGrpName((String)value);
/* 302 */       return;
/*     */     }
/*     */ 
/* 305 */     if (this.paramMap == null) {
/* 306 */       this.paramMap = new HashMap();
/*     */     }
/* 308 */     this.paramMap.put(name, value);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 312 */     String s = "{hello1='world1',hello2:[{s1='t1', s2='t2'},{s1='t11', s2='t22'}]}";
/* 313 */     System.out.println(HiETFUtils.fromJSON(JSONObject.fromObject(s)));
/*     */   }
/*     */ 
/*     */   public String getGrpName()
/*     */   {
/* 320 */     return this.grpName;
/*     */   }
/*     */ 
/*     */   public void setGrpName(String grpName)
/*     */   {
/* 327 */     this.grpName = grpName;
/*     */   }
/*     */ }