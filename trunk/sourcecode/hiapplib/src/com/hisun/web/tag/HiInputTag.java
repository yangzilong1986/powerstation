 package com.hisun.web.tag;
 
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.web.taglib.util.FormatUtil;
 import java.io.IOException;
 import java.net.MalformedURLException;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Set;
 import javax.servlet.ServletContext;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyContent;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 import javax.servlet.jsp.tagext.DynamicAttributes;
 import org.apache.commons.lang.BooleanUtils;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.SystemUtils;
 import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
 import org.dom4j.DocumentException;
 
 public class HiInputTag extends BodyTagSupport
   implements DynamicAttributes
 {
   private String name;
   private String dictName;
   private String initValue;
   private String convertName;
   private String helpName;
   private String validateAttr;
   private HashMap paramMap = null;
 
   private static HiDict _dict = HiDict.getInstance();
   private static HiFieldHlp _help = HiFieldHlp.getInstance();
   private static HiConvert _convert = HiConvert.getInstance();
   private Logger log = HiLog.getLogger("SYS.trc");
 
   public void init()
     throws DocumentException, MalformedURLException
   {
     ServletContext context = this.pageContext.getServletContext();
 
     _help.init(context.getRealPath("/conf/fieldhlp.xml"));
     _dict.init(context.getRealPath("/conf/dict.xml"));
     _convert.init(context.getRealPath("/conf/convert.xml"));
   }
 
   public int doEndTag()
     throws JspException
   {
     try
     {
       init();
     } catch (Exception e1) {
       throw new JspException("init failure", e1);
     }
     String tmpName = this.dictName;
     if (StringUtils.isEmpty(this.dictName)) {
       tmpName = this.name;
     }
     HiDictItem dictItem = _dict.get(tmpName);
     if (dictItem == null) {
       this.log.info("dict name:[" + tmpName + "] not founded");
     }
     tmpName = this.helpName;
     if (StringUtils.isEmpty(this.helpName)) {
       tmpName = this.name;
     }
     HiFieldHlpItem helpItem = _help.get(tmpName);
     if ((helpItem == null) && (this.helpName != null)) {
       this.log.info("help name:[" + tmpName + "] not founded");
     }
 
     StringBuffer buf = new StringBuffer();
 
     if (helpItem != null)
       doSelectProc(helpItem, buf);
     else {
       doInputProc(dictItem, buf);
     }
     try
     {
       this.pageContext.getOut().write(buf.toString());
     } catch (IOException e) {
       throw new JspException(e);
     }
     this.name = null;
     this.dictName = null;
     this.initValue = null;
     this.convertName = null;
     this.helpName = null;
     this.validateAttr = null;
     if (this.paramMap != null) {
       this.paramMap.clear();
     }
     return 6;
   }
 
   public void doSelectProc(HiFieldHlpItem helpItem, StringBuffer buf) throws JspException
   {
     buf.append("<select ");
     if (StringUtils.isNotEmpty(this.name)) {
       buf.append("name='" + this.name + "' ");
     }
     buf.append("id='" + getId() + "' ");
 
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
     HashMap valueMap = helpItem._map;
     if (valueMap != null) {
       Iterator iter = valueMap.keySet().iterator();
       while (iter.hasNext()) {
         String value = (String)iter.next();
         String name = (String)valueMap.get(value);
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
   }
 
   public int doStartTag()
     throws JspException
   {
     return super.doStartTag();
   }
 
   public void doInputProc(HiDictItem item, StringBuffer buf) throws JspException {
     if ((item != null) && ("D".equalsIgnoreCase(item.type))) {
       toDateHtml(buf);
       return;
     }
 
     buf.append("<input ");
     if (StringUtils.isNotEmpty(this.name)) {
       if ((item != null) && ("A".equalsIgnoreCase(item.type)))
         buf.append("name='" + this.name + "__A' ");
       else {
         buf.append("name='" + this.name + "' ");
       }
     }
 
     buf.append("id='" + getId() + "' ");
     String name = null;
     if (this.paramMap != null) {
       Iterator iter = this.paramMap.keySet().iterator();
       while (iter.hasNext()) {
         String key = (String)iter.next();
         if ("id".equals(key)) {
           continue;
         }
         String value = (String)this.paramMap.get(key);
         buf.append(key + "='" + value + "' ");
       }
     }
 
     if (this.initValue != null) {
       if (StringUtils.isNotEmpty(this.convertName)) {
         buf.append(" value ='" + _convert.convert(this.pageContext, this.convertName, this.initValue) + "'");
       }
       else if (StringUtils.isNotEmpty(this.initValue)) {
         buf.append(" value='" + this.initValue + "'");
       }
     }
 
     StringBuffer tmp = new StringBuffer();
     if (item != null) {
       if ("9".equals(item.type))
       {
         tmp.append("type=number");
         buf.append(" onchange='checkInput(this);'");
       } else if ("A".equalsIgnoreCase(item.type))
       {
         buf.append(" style='text-align:right' onblur='fmtAmt1(this)' onfocus='fmtAmt2(this)' ");
       } else if ("X".equalsIgnoreCase(item.type))
       {
         buf.append(" onchange='checkInput(this);'");
       } else if ("E".equalsIgnoreCase(item.type))
       {
         tmp.append("type=email");
         buf.append(" onchange='checkInput(this);'");
       } else if ("D".equalsIgnoreCase(item.type))
       {
         tmp.append("type=date");
       }
 
     }
 
     if (StringUtils.isNotEmpty(tmp.toString()))
       tmp.append(";" + this.validateAttr);
     else {
       tmp.append(this.validateAttr);
     }
 
     if (tmp.length() != 0) {
       buf.append(" validateAttr='" + tmp.toString() + "'");
     }
 
     if ((!(containsParam("size"))) && (item != null) && (item.length != 0)) {
       if ("D".equalsIgnoreCase(item.type))
         buf.append(" size='" + (item.length + 5) + "'");
       else if ("A".equalsIgnoreCase(item.type))
         buf.append(" size='" + (item.length + 6) + "'");
       else {
         buf.append(" size='" + item.length + "'");
       }
     }
     if ((!(containsParam("maxlength"))) && (item != null)) {
       buf.append(" maxlength='" + item.length + "'");
     }
     buf.append(" />");
     if ((!(StringUtils.contains(this.validateAttr, "allowNull=false"))) || 
       ("hidden".equalsIgnoreCase(getAttribute("type")))) return;
     buf.insert(0, "<font color='red'>*</font>");
   }
 
   public boolean containsParam(String name)
   {
     if (this.paramMap == null) {
       return false;
     }
     return this.paramMap.containsKey(name);
   }
 
   public void setDynamicAttribute(String uri, String name, Object value) throws JspException
   {
     if ("name".equalsIgnoreCase(name)) {
       setName((String)value);
       return;
     }
 
     if ("dictName".equalsIgnoreCase(name)) {
       setDictName((String)value);
       return;
     }
 
     if ("convertName".equalsIgnoreCase(name)) {
       setConvertName((String)value);
       return;
     }
 
     if ("helpName".equalsIgnoreCase(name)) {
       setHelpName((String)value);
       return;
     }
 
     if ("value".equalsIgnoreCase(name)) {
       setValue((String)value);
       return;
     }
 
     if ("validateAttr".equalsIgnoreCase(name)) {
       setValidateAttr((String)value);
       return;
     }
 
     if (this.paramMap == null) {
       this.paramMap = new HashMap();
     }
     this.paramMap.put(name, value);
   }
 
   public String getName()
   {
     return this.name;
   }
 
   public void setName(String name)
   {
     this.name = name;
   }
 
   public String getConvertName()
   {
     return this.convertName;
   }
 
   public void setConvertName(String convertName)
   {
     this.convertName = convertName;
   }
 
   public String getHelpName()
   {
     return this.helpName;
   }
 
   public void setHelpName(String helpName)
   {
     this.helpName = helpName;
   }
 
   public void setValue(String value)
     throws JspException
   {
     if (value == null) {
       this.initValue = "";
       return;
     }
     this.initValue = ((String)ExpressionEvaluatorManager.evaluate("value", value, Object.class, this, this.pageContext));
   }
 
   public String getValidateAttr()
   {
     return this.validateAttr;
   }
 
   public void setValidateAttr(String validateAttr)
   {
     this.validateAttr = validateAttr;
   }
 
   public String getDictName()
   {
     return this.dictName;
   }
 
   public void setDictName(String dictName)
   {
     this.dictName = dictName;
   }
 
   public void toDateHtml(StringBuffer buf) throws JspException {
     String format = getAttribute("format");
     if (format == null)
       format = getAttribute("tagformat");
     if (format == null) {
       format = "yyyy-mm-dd";
     }
     String maxValue = getAttribute("maxValue");
     String minValue = getAttribute("minValue");
     if ((maxValue != null) && (minValue != null))
     {
       Date max;
       Date min;
       SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
       try
       {
         max = simpleFormat.parse(maxValue);
       } catch (ParseException e) {
         throw new JspException("error format of maxValue", e);
       }
       try
       {
         min = simpleFormat.parse(minValue);
       } catch (ParseException e) {
         throw new JspException("error format of minValue", e);
       }
       if (min.after(max)) {
         throw new JspException(new StringBuilder().append("maxValue ").append(maxValue).append(" should later than minValue ").toString() + minValue);
       }
 
     }
 
     String actFormat = "yyyy-MM-dd";
     if (format == null)
       format = "yyyy-MM-dd";
     else if ((format.indexOf("yyyy") != -1) && (format.indexOf("ss") != -1))
       actFormat = "yyyy-MM-dd HH:mm:ss";
     String srcFormat = getAttribute("srcFormat");
     String value = getValue();
     String actValue = null;
     if (value != null) {
       try {
         actValue = FormatUtil.format(getValue(), srcFormat, actFormat);
       } catch (ParseException e) {
         throw new JspException(e);
       }
     }
     String defaultNull = getAttribute("defaultNull");
     if ((defaultNull != null) && (defaultNull.equals("false"))) {
       value = new Date().toString();
       try {
         actValue = FormatUtil.format(value, actFormat);
       } catch (ParseException e) {
         throw new JspException(e);
       }
 
     }
 
     String id = getId();
     String hiddenId = id + "_hidden";
     String inputId = id + "_input";
     buf.append("<div id='" + id + "_container' class='ics-ic-container'>");
     if ("false".equals(getAttribute("allowNull"))) {
       buf.append("<font color='red'>*</font>");
     }
     buf.append("<input class='ics-calendar-editor-text' type='text'");
     buf.append(" id='" + inputId + "' ");
     if (this.paramMap != null) {
       Iterator iter = this.paramMap.keySet().iterator();
       while (iter.hasNext()) {
         String key = (String)iter.next();
         String value1 = (String)this.paramMap.get(key);
         buf.append(key + "='" + value1 + "' ");
       }
     }
     if (getAttribute("allowNull") != null) {
       buf.append(" validateAttr='type=calendar;allowNull=" + getAttribute("allowNull") + "'");
     }
 
     if (getAttribute("extAttr") != null) {
       buf.append(" " + getAttribute("extAttr").replace(';', ' '));
     }
     buf.append("/><img id='" + id + "_button' class='ics-ic-button'/>");
 
     buf.append("<input type='hidden'");
     if (getName() != null) {
       prepareAttribute(buf, "name", getName() + "__D");
     }
 
     prepareAttribute(buf, "id", hiddenId);
     buf.append("/></div>");
     buf.append("<script language='javascript'>\n");
     buf.append("var _date =new Calendar('" + id + "','").append(format + "');\n");
 
     if (maxValue != null)
       buf.append("_date.maxValue = '" + maxValue + "';\n");
     if (actValue != null)
       buf.append("_date.value = '" + actValue + "';\n");
     if (minValue != null)
       buf.append("_date.minValue = '" + minValue + "';\n");
     String submitFormat = getAttribute("submitFormat");
     if (submitFormat != null) {
       buf.append("_date.submitFormat = '" + submitFormat + "';\n");
     }
     String width = getAttribute("width");
     if (width != null)
       buf.append("_date.width = '" + width + "';\n");
     String readOnly = getAttribute("readOnly");
     if (readOnly != null)
       buf.append("_date.readOnly = " + readOnly + ";\n");
     String allowInput = getAttribute("readonly");
     if (allowInput != null)
       buf.append("_date.allowInput = !" + allowInput + ";\n");
     String disabled = getAttribute("disabled");
     if (disabled != null)
       buf.append("_date.isDisabled = " + disabled + ";\n");
     String onSelectFunc = getAttribute("onSelectFunc");
     if (onSelectFunc != null) {
       buf.append("_date.onSelectFunc = '" + onSelectFunc + "';\n");
     }
     buf.append("_date.init();\n</script>");
     buf.append("");
   }
 
   public String getAttribute(String name) {
     if (this.paramMap == null) {
       return null;
     }
     if (!(this.paramMap.containsKey(name))) {
       return null;
     }
     return ((String)this.paramMap.get(name));
   }
 
   public void prepareAttribute(StringBuffer buf, String name, String value)
   {
     if ((name.equals("readonly")) && (!(getBooleanAttribute(name))))
       return;
     if ((name.equals("disabled")) && (!(getBooleanAttribute(name))))
       return;
     if ((name.equals("multiple")) && (!(getBooleanAttribute(name))))
       return;
     buf.append(" ");
     buf.append(name);
     buf.append("='");
     if (value != null)
       buf.append(value);
     buf.append("'");
   }
 
   public void prepareId(StringBuffer buf) {
     if (this.id != null)
       prepareAttribute(buf, "id", this.id);
   }
 
   public void prepareName(StringBuffer buf) {
     if (getName() != null)
       prepareAttribute(buf, "name", getName());
   }
 
   public String getValue() {
     return getAttribute("value");
   }
 
   public String getId() {
     String id = super.getId();
     if (id == null) {
       id = this.name;
     }
 
     if (id == null) {
       id = "input_" + Math.random();
     }
     return id;
   }
 
   public boolean getBooleanAttribute(String name) {
     return BooleanUtils.toBoolean(getAttribute(name));
   }
 }