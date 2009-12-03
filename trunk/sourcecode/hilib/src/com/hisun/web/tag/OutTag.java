/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import javax.servlet.jsp.tagext.DynamicAttributes;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.SystemUtils;
/*     */ import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
/*     */ import org.dom4j.DocumentException;
/*     */ 
/*     */ public class OutTag extends BodyTagSupport
/*     */   implements DynamicAttributes
/*     */ {
/*     */   private String name;
/*     */   private String dictName;
/*     */   private String initValue;
/*     */   private String convertName;
/*     */   private String helpName;
/*     */   private String validators;
/*  51 */   private HashMap paramMap = null;
/*     */ 
/*  53 */   private static HiDict _dict = HiDict.getInstance();
/*  54 */   private static HiFieldHlp _help = HiFieldHlp.getInstance();
/*  55 */   private static HiConvert _convert = HiConvert.getInstance();
/*  56 */   private Logger log = HiLog.getLogger("SYS.trc");
/*     */ 
/*     */   public void init()
/*     */     throws DocumentException, MalformedURLException
/*     */   {
/*  62 */     ServletContext context = this.pageContext.getServletContext();
/*     */ 
/*  64 */     _help.init(context.getRealPath("/conf/fieldhlp.xml"));
/*  65 */     _dict.init(context.getRealPath("/conf/dict.xml"));
/*  66 */     _convert.init(context.getRealPath("/conf/convert.xml"));
/*     */   }
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  78 */       init();
/*     */     } catch (Exception e1) {
/*  80 */       throw new JspException("init failure", e1);
/*     */     }
/*  82 */     String tmpName = this.dictName;
/*  83 */     if (StringUtils.isEmpty(this.dictName)) {
/*  84 */       tmpName = this.name;
/*     */     }
/*  86 */     HiDictItem dictItem = _dict.get(tmpName);
/*  87 */     if (dictItem == null) {
/*  88 */       this.log.info("dict name:[" + tmpName + "] not founded");
/*     */     }
/*  90 */     tmpName = this.helpName;
/*  91 */     if (StringUtils.isEmpty(this.helpName)) {
/*  92 */       tmpName = this.name;
/*     */     }
/*  94 */     HiFieldHlpItem helpItem = _help.get(tmpName);
/*  95 */     if ((helpItem == null) && (this.helpName != null)) {
/*  96 */       this.log.info("help name:[" + tmpName + "] not founded");
/*     */     }
/*     */ 
/*  99 */     StringBuffer buf = new StringBuffer();
/*     */ 
/* 101 */     if (helpItem != null)
/* 102 */       doSelectProc(helpItem, buf);
/*     */     else {
/* 104 */       doInputProc(dictItem, buf);
/*     */     }
/*     */     try
/*     */     {
/* 108 */       this.pageContext.getOut().write(buf.toString());
/*     */     } catch (IOException e) {
/* 110 */       throw new JspException(e);
/*     */     }
/* 112 */     return 6;
/*     */   }
/*     */ 
/*     */   public void doSelectProc(HiFieldHlpItem helpItem, StringBuffer buf) throws JspException
/*     */   {
/* 117 */     buf.append("<select ");
/* 118 */     if (StringUtils.isNotEmpty(this.name)) {
/* 119 */       buf.append("name='" + this.name + "' ");
/*     */     }
/* 121 */     if (this.paramMap != null) {
/* 122 */       Iterator iter = this.paramMap.keySet().iterator();
/* 123 */       while (iter.hasNext()) {
/* 124 */         String key = (String)iter.next();
/* 125 */         String value = (String)this.paramMap.get(key);
/* 126 */         buf.append(key + "='" + value + "' ");
/*     */       }
/*     */     }
/* 129 */     buf.append(" >");
/*     */ 
/* 131 */     String bodyStr = null;
/* 132 */     int fillerPos = -1;
/* 133 */     if (this.bodyContent != null) {
/* 134 */       bodyStr = this.bodyContent.getString();
/*     */     }
/*     */ 
/* 137 */     if (bodyStr != null) {
/* 138 */       fillerPos = bodyStr.indexOf("<filler/>");
/* 139 */       if (fillerPos != -1) {
/* 140 */         buf.append(bodyStr.substring(0, fillerPos));
/* 141 */         fillerPos += "<filler/>".length();
/*     */       } else {
/* 143 */         buf.append(bodyStr);
/*     */       }
/*     */     }
/* 146 */     HashMap valueMap = helpItem._map;
/* 147 */     if (valueMap != null) {
/* 148 */       Iterator iter = valueMap.keySet().iterator();
/* 149 */       while (iter.hasNext()) {
/* 150 */         String value = (String)iter.next();
/* 151 */         String name = (String)valueMap.get(value);
/* 152 */         buf.append("<option value='" + value + "'");
/* 153 */         if (StringUtils.equals(this.initValue, value)) {
/* 154 */           buf.append(" selected ");
/*     */         }
/* 156 */         buf.append(" >" + name + "</option>");
/* 157 */         buf.append(SystemUtils.LINE_SEPARATOR);
/*     */       }
/*     */     }
/* 160 */     if ((bodyStr != null) && (fillerPos != -1)) {
/* 161 */       buf.append(bodyStr.substring(fillerPos));
/*     */     }
/* 163 */     buf.append("</select>");
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/* 175 */     return super.doStartTag();
/*     */   }
/*     */ 
/*     */   public void doInputProc(HiDictItem item, StringBuffer buf) {
/* 179 */     buf.append("<input ");
/* 180 */     if (StringUtils.isNotEmpty(this.name)) {
/* 181 */       buf.append("name='" + this.name + "' ");
/*     */     }
/* 183 */     String name = null;
/* 184 */     if (this.paramMap != null) {
/* 185 */       Iterator iter = this.paramMap.keySet().iterator();
/* 186 */       while (iter.hasNext()) {
/* 187 */         String key = (String)iter.next();
/* 188 */         String value = (String)this.paramMap.get(key);
/* 189 */         buf.append(key + "='" + value + "' ");
/*     */       }
/*     */     }
/*     */ 
/* 193 */     if (this.initValue != null) {
/* 194 */       if (StringUtils.isNotEmpty(this.convertName)) {
/* 195 */         buf.append(" value ='" + _convert.convert(this.convertName, this.initValue) + "'");
/*     */       }
/* 197 */       else if (StringUtils.isNotEmpty(this.initValue)) {
/* 198 */         buf.append(" value='" + this.initValue + "'");
/*     */       }
/*     */     }
/*     */ 
/* 202 */     StringBuffer tmp = new StringBuffer();
/* 203 */     if (item != null) {
/* 204 */       if ("9".equals(item.type)) {
/* 205 */         tmp.append("numberTyp");
/* 206 */       } else if ("A".equalsIgnoreCase(item.type)) {
/* 207 */         buf.append(" style='text-align:right' onblur='fmtAmt(this)'");
/* 208 */         tmp.append("amtTyp");
/* 209 */       } else if ("X".equalsIgnoreCase(item.type)) {
/* 210 */         tmp.append("stringTyp");
/* 211 */       } else if ("D".equalsIgnoreCase(item.type)) {
/* 212 */         tmp.append("dateTyp");
/* 213 */         buf.append(" class='Wdate' onclick=\"fPopCalendar(" + name + "," + name + ");return false\"");
/*     */       }
/*     */       else {
/* 216 */         tmp.append("stringTyp");
/*     */       }
/*     */     }
/* 219 */     if (StringUtils.isNotEmpty(this.validators)) {
/* 220 */       if (tmp.length() != 0) {
/* 221 */         buf.append(" validators='" + this.validators + "," + tmp.toString() + "'");
/*     */       }
/*     */       else {
/* 224 */         buf.append(" validators='" + this.validators + "'");
/*     */       }
/*     */     }
/* 227 */     else if (tmp.length() != 0) {
/* 228 */       buf.append(" validators='validators:" + tmp.toString() + "'");
/*     */     }
/*     */ 
/* 232 */     if ((!(containsParam("size"))) && (item != null) && (item.length != 0)) {
/* 233 */       if ("D".equalsIgnoreCase(item.type))
/* 234 */         buf.append(" size='" + (item.length + 5) + "'");
/* 235 */       else if ("A".equalsIgnoreCase(item.type))
/* 236 */         buf.append(" size='" + (item.length + 6) + "'");
/*     */       else {
/* 238 */         buf.append(" size='" + item.length + "'");
/*     */       }
/*     */     }
/* 241 */     buf.append(" />");
/*     */   }
/*     */ 
/*     */   public boolean containsParam(String name) {
/* 245 */     if (this.paramMap == null) {
/* 246 */       return false;
/*     */     }
/* 248 */     return this.paramMap.containsKey(name);
/*     */   }
/*     */ 
/*     */   public void setDynamicAttribute(String uri, String name, Object value) throws JspException
/*     */   {
/* 253 */     if ("name".equalsIgnoreCase(name)) {
/* 254 */       setName((String)value);
/* 255 */       return;
/*     */     }
/*     */ 
/* 258 */     if ("dictName".equalsIgnoreCase(name)) {
/* 259 */       setDictName((String)value);
/* 260 */       return;
/*     */     }
/*     */ 
/* 263 */     if ("convertName".equalsIgnoreCase(name)) {
/* 264 */       setConvertName((String)value);
/* 265 */       return;
/*     */     }
/*     */ 
/* 268 */     if ("helpName".equalsIgnoreCase(name)) {
/* 269 */       setHelpName((String)value);
/* 270 */       return;
/*     */     }
/*     */ 
/* 273 */     if ("value".equalsIgnoreCase(name)) {
/* 274 */       setValue((String)value);
/* 275 */       return;
/*     */     }
/*     */ 
/* 278 */     if ("validators".equalsIgnoreCase(name)) {
/* 279 */       setValidators((String)value);
/* 280 */       return;
/*     */     }
/*     */ 
/* 283 */     if (this.paramMap == null) {
/* 284 */       this.paramMap = new HashMap();
/*     */     }
/* 286 */     this.paramMap.put(name, value);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 293 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 301 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getConvertName()
/*     */   {
/* 308 */     return this.convertName;
/*     */   }
/*     */ 
/*     */   public void setConvertName(String convertName)
/*     */   {
/* 316 */     this.convertName = convertName;
/*     */   }
/*     */ 
/*     */   public String getHelpName()
/*     */   {
/* 323 */     return this.helpName;
/*     */   }
/*     */ 
/*     */   public void setHelpName(String helpName)
/*     */   {
/* 331 */     this.helpName = helpName;
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */     throws JspException
/*     */   {
/* 339 */     if (value == null) {
/* 340 */       this.initValue = "";
/* 341 */       return;
/*     */     }
/* 343 */     this.initValue = ((String)ExpressionEvaluatorManager.evaluate("value", value, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getValidators()
/*     */   {
/* 351 */     return this.validators;
/*     */   }
/*     */ 
/*     */   public void setValidators(String validators)
/*     */   {
/* 359 */     this.validators = validators;
/*     */   }
/*     */ 
/*     */   public String getDictName()
/*     */   {
/* 366 */     return this.dictName;
/*     */   }
/*     */ 
/*     */   public void setDictName(String dictName)
/*     */   {
/* 374 */     this.dictName = dictName;
/*     */   }
/*     */ }