/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.web.taglib.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
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
/*     */ import org.apache.commons.lang.BooleanUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.SystemUtils;
/*     */ import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
/*     */ import org.dom4j.DocumentException;
/*     */ 
/*     */ public class HiInputTag extends BodyTagSupport
/*     */   implements DynamicAttributes
/*     */ {
/*     */   private String name;
/*     */   private String dictName;
/*     */   private String initValue;
/*     */   private String convertName;
/*     */   private String helpName;
/*     */   private String validateAttr;
/*  62 */   private HashMap paramMap = null;
/*     */ 
/*  64 */   private static HiDict _dict = HiDict.getInstance();
/*  65 */   private static HiFieldHlp _help = HiFieldHlp.getInstance();
/*  66 */   private static HiConvert _convert = HiConvert.getInstance();
/*  67 */   private Logger log = HiLog.getLogger("SYS.trc");
/*     */ 
/*     */   public void init()
/*     */     throws DocumentException, MalformedURLException
/*     */   {
/*  73 */     ServletContext context = this.pageContext.getServletContext();
/*     */ 
/*  75 */     _help.init(context.getRealPath("/conf/fieldhlp.xml"));
/*  76 */     _dict.init(context.getRealPath("/conf/dict.xml"));
/*  77 */     _convert.init(context.getRealPath("/conf/convert.xml"));
/*     */   }
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  89 */       init();
/*     */     } catch (Exception e1) {
/*  91 */       throw new JspException("init failure", e1);
/*     */     }
/*  93 */     String tmpName = this.dictName;
/*  94 */     if (StringUtils.isEmpty(this.dictName)) {
/*  95 */       tmpName = this.name;
/*     */     }
/*  97 */     HiDictItem dictItem = _dict.get(tmpName);
/*  98 */     if (dictItem == null) {
/*  99 */       this.log.info("dict name:[" + tmpName + "] not founded");
/*     */     }
/* 101 */     tmpName = this.helpName;
/* 102 */     if (StringUtils.isEmpty(this.helpName)) {
/* 103 */       tmpName = this.name;
/*     */     }
/* 105 */     HiFieldHlpItem helpItem = _help.get(tmpName);
/* 106 */     if ((helpItem == null) && (this.helpName != null)) {
/* 107 */       this.log.info("help name:[" + tmpName + "] not founded");
/*     */     }
/*     */ 
/* 110 */     StringBuffer buf = new StringBuffer();
/*     */ 
/* 112 */     if (helpItem != null)
/* 113 */       doSelectProc(helpItem, buf);
/*     */     else {
/* 115 */       doInputProc(dictItem, buf);
/*     */     }
/*     */     try
/*     */     {
/* 119 */       this.pageContext.getOut().write(buf.toString());
/*     */     } catch (IOException e) {
/* 121 */       throw new JspException(e);
/*     */     }
/* 123 */     this.name = null;
/* 124 */     this.dictName = null;
/* 125 */     this.initValue = null;
/* 126 */     this.convertName = null;
/* 127 */     this.helpName = null;
/* 128 */     this.validateAttr = null;
/* 129 */     if (this.paramMap != null) {
/* 130 */       this.paramMap.clear();
/*     */     }
/* 132 */     return 6;
/*     */   }
/*     */ 
/*     */   public void doSelectProc(HiFieldHlpItem helpItem, StringBuffer buf) throws JspException
/*     */   {
/* 137 */     buf.append("<select ");
/* 138 */     if (StringUtils.isNotEmpty(this.name)) {
/* 139 */       buf.append("name='" + this.name + "' ");
/*     */     }
/* 141 */     buf.append("id='" + getId() + "' ");
/*     */ 
/* 143 */     if (this.paramMap != null) {
/* 144 */       Iterator iter = this.paramMap.keySet().iterator();
/* 145 */       while (iter.hasNext()) {
/* 146 */         String key = (String)iter.next();
/* 147 */         String value = (String)this.paramMap.get(key);
/* 148 */         buf.append(key + "='" + value + "' ");
/*     */       }
/*     */     }
/* 151 */     buf.append(" >");
/*     */ 
/* 153 */     String bodyStr = null;
/* 154 */     int fillerPos = -1;
/* 155 */     if (this.bodyContent != null) {
/* 156 */       bodyStr = this.bodyContent.getString();
/*     */     }
/*     */ 
/* 159 */     if (bodyStr != null) {
/* 160 */       fillerPos = bodyStr.indexOf("<filler/>");
/* 161 */       if (fillerPos != -1) {
/* 162 */         buf.append(bodyStr.substring(0, fillerPos));
/* 163 */         fillerPos += "<filler/>".length();
/*     */       } else {
/* 165 */         buf.append(bodyStr);
/*     */       }
/*     */     }
/* 168 */     HashMap valueMap = helpItem._map;
/* 169 */     if (valueMap != null) {
/* 170 */       Iterator iter = valueMap.keySet().iterator();
/* 171 */       while (iter.hasNext()) {
/* 172 */         String value = (String)iter.next();
/* 173 */         String name = (String)valueMap.get(value);
/* 174 */         buf.append("<option value='" + value + "'");
/* 175 */         if (StringUtils.equals(this.initValue, value)) {
/* 176 */           buf.append(" selected ");
/*     */         }
/* 178 */         buf.append(" >" + name + "</option>");
/* 179 */         buf.append(SystemUtils.LINE_SEPARATOR);
/*     */       }
/*     */     }
/* 182 */     if ((bodyStr != null) && (fillerPos != -1)) {
/* 183 */       buf.append(bodyStr.substring(fillerPos));
/*     */     }
/* 185 */     buf.append("</select>");
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/* 196 */     return super.doStartTag();
/*     */   }
/*     */ 
/*     */   public void doInputProc(HiDictItem item, StringBuffer buf) throws JspException {
/* 200 */     if ((item != null) && ("D".equalsIgnoreCase(item.type))) {
/* 201 */       toDateHtml(buf);
/* 202 */       return;
/*     */     }
/*     */ 
/* 205 */     buf.append("<input ");
/* 206 */     if (StringUtils.isNotEmpty(this.name)) {
/* 207 */       if ((item != null) && ("A".equalsIgnoreCase(item.type)))
/* 208 */         buf.append("name='" + this.name + "__A' ");
/*     */       else {
/* 210 */         buf.append("name='" + this.name + "' ");
/*     */       }
/*     */     }
/*     */ 
/* 214 */     buf.append("id='" + getId() + "' ");
/* 215 */     String name = null;
/* 216 */     if (this.paramMap != null) {
/* 217 */       Iterator iter = this.paramMap.keySet().iterator();
/* 218 */       while (iter.hasNext()) {
/* 219 */         String key = (String)iter.next();
/* 220 */         if ("id".equals(key)) {
/*     */           continue;
/*     */         }
/* 223 */         String value = (String)this.paramMap.get(key);
/* 224 */         buf.append(key + "='" + value + "' ");
/*     */       }
/*     */     }
/*     */ 
/* 228 */     if (this.initValue != null) {
/* 229 */       if (StringUtils.isNotEmpty(this.convertName)) {
/* 230 */         buf.append(" value ='" + _convert.convert(this.pageContext, this.convertName, this.initValue) + "'");
/*     */       }
/* 233 */       else if (StringUtils.isNotEmpty(this.initValue)) {
/* 234 */         buf.append(" value='" + this.initValue + "'");
/*     */       }
/*     */     }
/*     */ 
/* 238 */     StringBuffer tmp = new StringBuffer();
/* 239 */     if (item != null) {
/* 240 */       if ("9".equals(item.type))
/*     */       {
/* 242 */         tmp.append("type=number");
/* 243 */         buf.append(" onchange='checkInput(this);'");
/* 244 */       } else if ("A".equalsIgnoreCase(item.type))
/*     */       {
/* 246 */         buf.append(" style='text-align:right' onblur='fmtAmt1(this)' onfocus='fmtAmt2(this)' ");
/* 247 */       } else if ("X".equalsIgnoreCase(item.type))
/*     */       {
/* 249 */         buf.append(" onchange='checkInput(this);'");
/* 250 */       } else if ("E".equalsIgnoreCase(item.type))
/*     */       {
/* 252 */         tmp.append("type=email");
/* 253 */         buf.append(" onchange='checkInput(this);'");
/* 254 */       } else if ("D".equalsIgnoreCase(item.type))
/*     */       {
/* 256 */         tmp.append("type=date");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 261 */     if (StringUtils.isNotEmpty(tmp.toString()))
/* 262 */       tmp.append(";" + this.validateAttr);
/*     */     else {
/* 264 */       tmp.append(this.validateAttr);
/*     */     }
/*     */ 
/* 267 */     if (tmp.length() != 0) {
/* 268 */       buf.append(" validateAttr='" + tmp.toString() + "'");
/*     */     }
/*     */ 
/* 271 */     if ((!(containsParam("size"))) && (item != null) && (item.length != 0)) {
/* 272 */       if ("D".equalsIgnoreCase(item.type))
/* 273 */         buf.append(" size='" + (item.length + 5) + "'");
/* 274 */       else if ("A".equalsIgnoreCase(item.type))
/* 275 */         buf.append(" size='" + (item.length + 6) + "'");
/*     */       else {
/* 277 */         buf.append(" size='" + item.length + "'");
/*     */       }
/*     */     }
/* 280 */     if ((!(containsParam("maxlength"))) && (item != null)) {
/* 281 */       buf.append(" maxlength='" + item.length + "'");
/*     */     }
/* 283 */     buf.append(" />");
/* 284 */     if ((!(StringUtils.contains(this.validateAttr, "allowNull=false"))) || 
/* 285 */       ("hidden".equalsIgnoreCase(getAttribute("type")))) return;
/* 286 */     buf.insert(0, "<font color='red'>*</font>");
/*     */   }
/*     */ 
/*     */   public boolean containsParam(String name)
/*     */   {
/* 293 */     if (this.paramMap == null) {
/* 294 */       return false;
/*     */     }
/* 296 */     return this.paramMap.containsKey(name);
/*     */   }
/*     */ 
/*     */   public void setDynamicAttribute(String uri, String name, Object value) throws JspException
/*     */   {
/* 301 */     if ("name".equalsIgnoreCase(name)) {
/* 302 */       setName((String)value);
/* 303 */       return;
/*     */     }
/*     */ 
/* 306 */     if ("dictName".equalsIgnoreCase(name)) {
/* 307 */       setDictName((String)value);
/* 308 */       return;
/*     */     }
/*     */ 
/* 311 */     if ("convertName".equalsIgnoreCase(name)) {
/* 312 */       setConvertName((String)value);
/* 313 */       return;
/*     */     }
/*     */ 
/* 316 */     if ("helpName".equalsIgnoreCase(name)) {
/* 317 */       setHelpName((String)value);
/* 318 */       return;
/*     */     }
/*     */ 
/* 321 */     if ("value".equalsIgnoreCase(name)) {
/* 322 */       setValue((String)value);
/* 323 */       return;
/*     */     }
/*     */ 
/* 326 */     if ("validateAttr".equalsIgnoreCase(name)) {
/* 327 */       setValidateAttr((String)value);
/* 328 */       return;
/*     */     }
/*     */ 
/* 331 */     if (this.paramMap == null) {
/* 332 */       this.paramMap = new HashMap();
/*     */     }
/* 334 */     this.paramMap.put(name, value);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 341 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 349 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getConvertName()
/*     */   {
/* 356 */     return this.convertName;
/*     */   }
/*     */ 
/*     */   public void setConvertName(String convertName)
/*     */   {
/* 364 */     this.convertName = convertName;
/*     */   }
/*     */ 
/*     */   public String getHelpName()
/*     */   {
/* 371 */     return this.helpName;
/*     */   }
/*     */ 
/*     */   public void setHelpName(String helpName)
/*     */   {
/* 379 */     this.helpName = helpName;
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */     throws JspException
/*     */   {
/* 387 */     if (value == null) {
/* 388 */       this.initValue = "";
/* 389 */       return;
/*     */     }
/* 391 */     this.initValue = ((String)ExpressionEvaluatorManager.evaluate("value", value, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getValidateAttr()
/*     */   {
/* 399 */     return this.validateAttr;
/*     */   }
/*     */ 
/*     */   public void setValidateAttr(String validateAttr)
/*     */   {
/* 407 */     this.validateAttr = validateAttr;
/*     */   }
/*     */ 
/*     */   public String getDictName()
/*     */   {
/* 414 */     return this.dictName;
/*     */   }
/*     */ 
/*     */   public void setDictName(String dictName)
/*     */   {
/* 422 */     this.dictName = dictName;
/*     */   }
/*     */ 
/*     */   public void toDateHtml(StringBuffer buf) throws JspException {
/* 426 */     String format = getAttribute("format");
/* 427 */     if (format == null)
/* 428 */       format = getAttribute("tagformat");
/* 429 */     if (format == null) {
/* 430 */       format = "yyyy-mm-dd";
/*     */     }
/* 432 */     String maxValue = getAttribute("maxValue");
/* 433 */     String minValue = getAttribute("minValue");
/* 434 */     if ((maxValue != null) && (minValue != null))
/*     */     {
/*     */       Date max;
/*     */       Date min;
/* 435 */       SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
/*     */       try
/*     */       {
/* 438 */         max = simpleFormat.parse(maxValue);
/*     */       } catch (ParseException e) {
/* 440 */         throw new JspException("error format of maxValue", e);
/*     */       }
/*     */       try
/*     */       {
/* 444 */         min = simpleFormat.parse(minValue);
/*     */       } catch (ParseException e) {
/* 446 */         throw new JspException("error format of minValue", e);
/*     */       }
/* 448 */       if (min.after(max)) {
/* 449 */         throw new JspException(new StringBuilder().append("maxValue ").append(maxValue).append(" should later than minValue ").toString() + minValue);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 454 */     String actFormat = "yyyy-MM-dd";
/* 455 */     if (format == null)
/* 456 */       format = "yyyy-MM-dd";
/* 457 */     else if ((format.indexOf("yyyy") != -1) && (format.indexOf("ss") != -1))
/* 458 */       actFormat = "yyyy-MM-dd HH:mm:ss";
/* 459 */     String srcFormat = getAttribute("srcFormat");
/* 460 */     String value = getValue();
/* 461 */     String actValue = null;
/* 462 */     if (value != null) {
/*     */       try {
/* 464 */         actValue = FormatUtil.format(getValue(), srcFormat, actFormat);
/*     */       } catch (ParseException e) {
/* 466 */         throw new JspException(e);
/*     */       }
/*     */     }
/* 469 */     String defaultNull = getAttribute("defaultNull");
/* 470 */     if ((defaultNull != null) && (defaultNull.equals("false"))) {
/* 471 */       value = new Date().toString();
/*     */       try {
/* 473 */         actValue = FormatUtil.format(value, actFormat);
/*     */       } catch (ParseException e) {
/* 475 */         throw new JspException(e);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 480 */     String id = getId();
/* 481 */     String hiddenId = id + "_hidden";
/* 482 */     String inputId = id + "_input";
/* 483 */     buf.append("<div id='" + id + "_container' class='ics-ic-container'>");
/* 484 */     if ("false".equals(getAttribute("allowNull"))) {
/* 485 */       buf.append("<font color='red'>*</font>");
/*     */     }
/* 487 */     buf.append("<input class='ics-calendar-editor-text' type='text'");
/* 488 */     buf.append(" id='" + inputId + "' ");
/* 489 */     if (this.paramMap != null) {
/* 490 */       Iterator iter = this.paramMap.keySet().iterator();
/* 491 */       while (iter.hasNext()) {
/* 492 */         String key = (String)iter.next();
/* 493 */         String value1 = (String)this.paramMap.get(key);
/* 494 */         buf.append(key + "='" + value1 + "' ");
/*     */       }
/*     */     }
/* 497 */     if (getAttribute("allowNull") != null) {
/* 498 */       buf.append(" validateAttr='type=calendar;allowNull=" + getAttribute("allowNull") + "'");
/*     */     }
/*     */ 
/* 502 */     if (getAttribute("extAttr") != null) {
/* 503 */       buf.append(" " + getAttribute("extAttr").replace(';', ' '));
/*     */     }
/* 505 */     buf.append("/><img id='" + id + "_button' class='ics-ic-button'/>");
/*     */ 
/* 508 */     buf.append("<input type='hidden'");
/* 509 */     if (getName() != null) {
/* 510 */       prepareAttribute(buf, "name", getName() + "__D");
/*     */     }
/*     */ 
/* 513 */     prepareAttribute(buf, "id", hiddenId);
/* 514 */     buf.append("/></div>");
/* 515 */     buf.append("<script language='javascript'>\n");
/* 516 */     buf.append("var _date =new Calendar('" + id + "','").append(format + "');\n");
/*     */ 
/* 518 */     if (maxValue != null)
/* 519 */       buf.append("_date.maxValue = '" + maxValue + "';\n");
/* 520 */     if (actValue != null)
/* 521 */       buf.append("_date.value = '" + actValue + "';\n");
/* 522 */     if (minValue != null)
/* 523 */       buf.append("_date.minValue = '" + minValue + "';\n");
/* 524 */     String submitFormat = getAttribute("submitFormat");
/* 525 */     if (submitFormat != null) {
/* 526 */       buf.append("_date.submitFormat = '" + submitFormat + "';\n");
/*     */     }
/* 528 */     String width = getAttribute("width");
/* 529 */     if (width != null)
/* 530 */       buf.append("_date.width = '" + width + "';\n");
/* 531 */     String readOnly = getAttribute("readOnly");
/* 532 */     if (readOnly != null)
/* 533 */       buf.append("_date.readOnly = " + readOnly + ";\n");
/* 534 */     String allowInput = getAttribute("readonly");
/* 535 */     if (allowInput != null)
/* 536 */       buf.append("_date.allowInput = !" + allowInput + ";\n");
/* 537 */     String disabled = getAttribute("disabled");
/* 538 */     if (disabled != null)
/* 539 */       buf.append("_date.isDisabled = " + disabled + ";\n");
/* 540 */     String onSelectFunc = getAttribute("onSelectFunc");
/* 541 */     if (onSelectFunc != null) {
/* 542 */       buf.append("_date.onSelectFunc = '" + onSelectFunc + "';\n");
/*     */     }
/* 544 */     buf.append("_date.init();\n</script>");
/* 545 */     buf.append("");
/*     */   }
/*     */ 
/*     */   public String getAttribute(String name) {
/* 549 */     if (this.paramMap == null) {
/* 550 */       return null;
/*     */     }
/* 552 */     if (!(this.paramMap.containsKey(name))) {
/* 553 */       return null;
/*     */     }
/* 555 */     return ((String)this.paramMap.get(name));
/*     */   }
/*     */ 
/*     */   public void prepareAttribute(StringBuffer buf, String name, String value)
/*     */   {
/* 560 */     if ((name.equals("readonly")) && (!(getBooleanAttribute(name))))
/* 561 */       return;
/* 562 */     if ((name.equals("disabled")) && (!(getBooleanAttribute(name))))
/* 563 */       return;
/* 564 */     if ((name.equals("multiple")) && (!(getBooleanAttribute(name))))
/* 565 */       return;
/* 566 */     buf.append(" ");
/* 567 */     buf.append(name);
/* 568 */     buf.append("='");
/* 569 */     if (value != null)
/* 570 */       buf.append(value);
/* 571 */     buf.append("'");
/*     */   }
/*     */ 
/*     */   public void prepareId(StringBuffer buf) {
/* 575 */     if (this.id != null)
/* 576 */       prepareAttribute(buf, "id", this.id);
/*     */   }
/*     */ 
/*     */   public void prepareName(StringBuffer buf) {
/* 580 */     if (getName() != null)
/* 581 */       prepareAttribute(buf, "name", getName());
/*     */   }
/*     */ 
/*     */   public String getValue() {
/* 585 */     return getAttribute("value");
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 589 */     String id = super.getId();
/* 590 */     if (id == null) {
/* 591 */       id = this.name;
/*     */     }
/*     */ 
/* 594 */     if (id == null) {
/* 595 */       id = "input_" + Math.random();
/*     */     }
/* 597 */     return id;
/*     */   }
/*     */ 
/*     */   public boolean getBooleanAttribute(String name) {
/* 601 */     return BooleanUtils.toBoolean(getAttribute(name));
/*     */   }
/*     */ }