/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.client.HiInvokeService;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import java.io.IOException;
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
/*     */   private Logger log;
/*     */ 
/*     */   public HiSelectTag()
/*     */   {
/*  54 */     this.colName = "name";
/*     */ 
/*  58 */     this.colValue = "value";
/*     */ 
/*  71 */     this.log = HiLog.getLogger("SYS.trc"); }
/*     */ 
/*     */   public int doAfterBody() throws JspException {
/*  74 */     super.doAfterBody();
/*  75 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doEndTag() throws JspException {
/*  79 */     JspWriter out = this.pageContext.getOut();
/*  80 */     HashMap valueMap = null;
/*  81 */     if (!(StringUtils.isBlank(this.code)))
/*     */       try {
/*  83 */         valueMap = processByCode(this.code, this.param);
/*     */       } catch (Exception e) {
/*  85 */         throw new JspException(e);
/*     */       }
/*  87 */     if (!(StringUtils.isBlank(this.sql))) {
/*     */       try {
/*  89 */         valueMap = processBySql(this.sql, this.param);
/*     */       } catch (HiException e) {
/*  91 */         throw new JspException(e);
/*     */       }
/*     */     }
/*  94 */     throw new JspException("invalid args");
/*     */ 
/*  97 */     StringBuffer buf = new StringBuffer();
/*  98 */     buf.append("<select ");
/*  99 */     if (this.paramMap != null) {
/* 100 */       Iterator iter = this.paramMap.keySet().iterator();
/* 101 */       while (iter.hasNext()) {
/* 102 */         String key = (String)iter.next();
/* 103 */         String value = (String)this.paramMap.get(key);
/* 104 */         buf.append(key + "='" + value + "' ");
/*     */       }
/*     */     }
/* 107 */     buf.append(" >");
/* 108 */     String bodyStr = null;
/* 109 */     int fillerPos = -1;
/* 110 */     if (this.bodyContent != null) {
/* 111 */       bodyStr = this.bodyContent.getString();
/*     */     }
/*     */ 
/* 114 */     if (bodyStr != null) {
/* 115 */       fillerPos = bodyStr.indexOf("<filler/>");
/* 116 */       if (fillerPos != -1) {
/* 117 */         buf.append(bodyStr.substring(0, fillerPos));
/* 118 */         fillerPos += "<filler/>".length();
/*     */       } else {
/* 120 */         buf.append(bodyStr);
/*     */       }
/*     */     }
/*     */ 
/* 124 */     if (valueMap != null) {
/* 125 */       Iterator iter = valueMap.keySet().iterator();
/* 126 */       while (iter.hasNext()) {
/* 127 */         String name = (String)iter.next();
/* 128 */         String value = (String)valueMap.get(name);
/* 129 */         buf.append("<option value='" + value + "'");
/* 130 */         if (StringUtils.equals(this.initValue, value)) {
/* 131 */           buf.append(" selected ");
/*     */         }
/* 133 */         buf.append(" >" + name + "</option>");
/* 134 */         buf.append(SystemUtils.LINE_SEPARATOR);
/*     */       }
/*     */     }
/* 137 */     if ((bodyStr != null) && (fillerPos != -1)) {
/* 138 */       buf.append(bodyStr.substring(fillerPos));
/*     */     }
/* 140 */     buf.append("</select>");
/*     */     try {
/* 142 */       out.print(buf);
/*     */     } catch (IOException e) {
/* 144 */       throw new JspException(e);
/*     */     }
/* 146 */     super.doEndTag();
/* 147 */     return 6;
/*     */   }
/*     */ 
/*     */   public LinkedHashMap processByCode(String code, String param) throws Exception
/*     */   {
/* 152 */     HiInvokeService service = HiInvokeService.createService();
/* 153 */     LinkedHashMap valueMap = new LinkedHashMap();
/* 154 */     if (param != null) {
/* 155 */       root = HiETFFactory.createETF(param);
/* 156 */       service.setETF(root);
/*     */     }
/* 158 */     if (this.log.isDebugEnabled()) {
/* 159 */       this.log.debug("[" + this.colName + "][" + this.colValue + "]");
/*     */     }
/* 161 */     if (this.log.isDebugEnabled()) {
/* 162 */       this.log.debug("send data:[" + service.toString() + "]");
/*     */     }
/* 164 */     HiETF root = service.invokeRetETF(code);
/* 165 */     if (this.log.isDebugEnabled()) {
/* 166 */       this.log.debug("recv data:[" + root + "]");
/*     */     }
/*     */ 
/* 169 */     for (int i = 0; ; ++i) {
/* 170 */       HiETF grpNod = root.getChildNode("GRP_" + (i + 1));
/* 171 */       if (grpNod == null) break; if (grpNod.isEndNode()) {
/*     */         break;
/*     */       }
/* 174 */       valueMap.put(grpNod.getChildValue(this.colName), grpNod.getChildValue(this.colValue));
/*     */     }
/*     */ 
/* 177 */     return valueMap;
/*     */   }
/*     */ 
/*     */   public LinkedHashMap processBySql(String sql, String param) throws HiException
/*     */   {
/* 182 */     HiDataBaseUtil dbUtil = new HiDataBaseUtil();
/*     */     try
/*     */     {
/*     */       List list;
/*     */       String[] tmps;
/* 185 */       if (param != null) {
/* 186 */         tmps = param.split("\\|");
/* 187 */         list = dbUtil.execQuery(sql, tmps);
/*     */       } else {
/* 189 */         list = dbUtil.execQuery(sql);
/*     */       }
/* 191 */       if (list == null) {
/* 192 */         tmps = null;
/*     */         return tmps;
/*     */       }
/* 194 */       LinkedHashMap map = new LinkedHashMap();
/* 195 */       for (int i = 0; i < list.size(); ++i) {
/* 196 */         HashMap rec = (HashMap)list.get(i);
/* 197 */         map.put(rec.get(this.colName), rec.get(this.colValue));
/*     */       }
/* 199 */       i = map;
/*     */ 
/* 202 */       return i;
/*     */     }
/*     */     finally
/*     */     {
/* 201 */       dbUtil.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getCode() {
/* 206 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code) throws JspException {
/* 210 */     if (code == null) {
/* 211 */       this.code = "";
/* 212 */       return;
/*     */     }
/* 214 */     this.code = ((String)ExpressionEvaluatorManager.evaluate("code", code, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getSql()
/*     */   {
/* 219 */     return this.sql;
/*     */   }
/*     */ 
/*     */   public void setSql(String sql) throws JspException {
/* 223 */     if (sql == null) {
/* 224 */       this.sql = "";
/* 225 */       return;
/*     */     }
/* 227 */     this.sql = ((String)ExpressionEvaluatorManager.evaluate("sql", sql, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getParam()
/*     */   {
/* 232 */     return this.param;
/*     */   }
/*     */ 
/*     */   public void setParam(String param) throws JspException {
/* 236 */     if (param == null) {
/* 237 */       this.param = "";
/* 238 */       return;
/*     */     }
/* 240 */     this.param = ((String)ExpressionEvaluatorManager.evaluate("param", param, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public void setInitValue(String initValue) throws JspException
/*     */   {
/* 245 */     if (initValue == null) {
/* 246 */       this.initValue = "";
/* 247 */       return;
/*     */     }
/* 249 */     this.initValue = ((String)ExpressionEvaluatorManager.evaluate("initValue", initValue, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public void setCols(String cols) throws JspException
/*     */   {
/* 254 */     int idx = cols.indexOf(124);
/* 255 */     if (idx == -1) {
/* 256 */       throw new JspException("names invalid");
/*     */     }
/* 258 */     this.colValue = cols.substring(0, idx);
/* 259 */     this.colName = cols.substring(idx + 1);
/*     */   }
/*     */ 
/*     */   public void setDynamicAttribute(String uri, String name, Object value) throws JspException
/*     */   {
/* 264 */     if ("sql".equalsIgnoreCase(name)) {
/* 265 */       setSql((String)value);
/* 266 */       return;
/*     */     }
/*     */ 
/* 269 */     if ("code".equalsIgnoreCase(name)) {
/* 270 */       setCode((String)value);
/* 271 */       return;
/*     */     }
/*     */ 
/* 274 */     if ("param".equalsIgnoreCase(name)) {
/* 275 */       setParam((String)value);
/* 276 */       return;
/*     */     }
/*     */ 
/* 279 */     if ("cols".equalsIgnoreCase(name)) {
/* 280 */       setCols((String)value);
/* 281 */       return;
/*     */     }
/*     */ 
/* 284 */     if ("initValue".equalsIgnoreCase(name)) {
/* 285 */       setInitValue((String)value);
/* 286 */       return;
/*     */     }
/*     */ 
/* 289 */     if (this.paramMap == null) {
/* 290 */       this.paramMap = new HashMap();
/*     */     }
/* 292 */     this.paramMap.put(name, value);
/*     */   }
/*     */ }