package com.hisun.web.tag;


import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.dom4j.DocumentException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;


public class OutTag extends BodyTagSupport implements DynamicAttributes {
    private String name;
    private String dictName;
    private String initValue;
    private String convertName;
    private String helpName;
    private String validators;
    private HashMap paramMap = null;

    private static HiDict _dict = HiDict.getInstance();
    private static HiFieldHlp _help = HiFieldHlp.getInstance();
    private static HiConvert _convert = HiConvert.getInstance();
    private Logger log = HiLog.getLogger("SYS.trc");


    public void init() throws DocumentException, MalformedURLException {

        ServletContext context = this.pageContext.getServletContext();


        _help.init(context.getRealPath("/conf/fieldhlp.xml"));

        _dict.init(context.getRealPath("/conf/dict.xml"));

        _convert.init(context.getRealPath("/conf/convert.xml"));

    }


    public int doEndTag() throws JspException {

        try {

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


        if (helpItem != null) doSelectProc(helpItem, buf);

        else {

            doInputProc(dictItem, buf);

        }

        try {

            this.pageContext.getOut().write(buf.toString());

        } catch (IOException e) {

            throw new JspException(e);

        }

        return 6;

    }


    public void doSelectProc(HiFieldHlpItem helpItem, StringBuffer buf) throws JspException {

        buf.append("<select ");

        if (StringUtils.isNotEmpty(this.name)) {

            buf.append("name='" + this.name + "' ");

        }

        if (this.paramMap != null) {

            Iterator iter = this.paramMap.keySet().iterator();

            while (iter.hasNext()) {

                String key = (String) iter.next();

                String value = (String) this.paramMap.get(key);

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

                String value = (String) iter.next();

                String name = (String) valueMap.get(value);

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


    public int doStartTag() throws JspException {

        return super.doStartTag();

    }


    public void doInputProc(HiDictItem item, StringBuffer buf) {

        buf.append("<input ");

        if (StringUtils.isNotEmpty(this.name)) {

            buf.append("name='" + this.name + "' ");

        }

        String name = null;

        if (this.paramMap != null) {

            Iterator iter = this.paramMap.keySet().iterator();

            while (iter.hasNext()) {

                String key = (String) iter.next();

                String value = (String) this.paramMap.get(key);

                buf.append(key + "='" + value + "' ");

            }

        }


        if (this.initValue != null) {

            if (StringUtils.isNotEmpty(this.convertName)) {

                buf.append(" value ='" + _convert.convert(this.convertName, this.initValue) + "'");

            } else if (StringUtils.isNotEmpty(this.initValue)) {

                buf.append(" value='" + this.initValue + "'");

            }

        }


        StringBuffer tmp = new StringBuffer();

        if (item != null) {

            if ("9".equals(item.type)) {

                tmp.append("numberTyp");

            } else if ("A".equalsIgnoreCase(item.type)) {

                buf.append(" style='text-align:right' onblur='fmtAmt(this)'");

                tmp.append("amtTyp");

            } else if ("X".equalsIgnoreCase(item.type)) {

                tmp.append("stringTyp");

            } else if ("D".equalsIgnoreCase(item.type)) {

                tmp.append("dateTyp");

                buf.append(" class='Wdate' onclick=\"fPopCalendar(" + name + "," + name + ");return false\"");

            } else {

                tmp.append("stringTyp");

            }

        }

        if (StringUtils.isNotEmpty(this.validators)) {

            if (tmp.length() != 0) {

                buf.append(" validators='" + this.validators + "," + tmp.toString() + "'");

            } else {

                buf.append(" validators='" + this.validators + "'");

            }

        } else if (tmp.length() != 0) {

            buf.append(" validators='validators:" + tmp.toString() + "'");

        }


        if ((!(containsParam("size"))) && (item != null) && (item.length != 0)) {

            if ("D".equalsIgnoreCase(item.type)) buf.append(" size='" + (item.length + 5) + "'");

            else if ("A".equalsIgnoreCase(item.type)) buf.append(" size='" + (item.length + 6) + "'");

            else {

                buf.append(" size='" + item.length + "'");

            }

        }

        buf.append(" />");

    }


    public boolean containsParam(String name) {

        if (this.paramMap == null) {

            return false;

        }

        return this.paramMap.containsKey(name);

    }


    public void setDynamicAttribute(String uri, String name, Object value) throws JspException {

        if ("name".equalsIgnoreCase(name)) {

            setName((String) value);

            return;

        }


        if ("dictName".equalsIgnoreCase(name)) {

            setDictName((String) value);

            return;

        }


        if ("convertName".equalsIgnoreCase(name)) {

            setConvertName((String) value);

            return;

        }


        if ("helpName".equalsIgnoreCase(name)) {

            setHelpName((String) value);

            return;

        }


        if ("value".equalsIgnoreCase(name)) {

            setValue((String) value);

            return;

        }


        if ("validators".equalsIgnoreCase(name)) {

            setValidators((String) value);

            return;

        }


        if (this.paramMap == null) {

            this.paramMap = new HashMap();

        }

        this.paramMap.put(name, value);

    }


    public String getName() {

        return this.name;

    }


    public void setName(String name) {

        this.name = name;

    }


    public String getConvertName() {

        return this.convertName;

    }


    public void setConvertName(String convertName) {

        this.convertName = convertName;

    }


    public String getHelpName() {

        return this.helpName;

    }


    public void setHelpName(String helpName) {

        this.helpName = helpName;

    }


    public void setValue(String value) throws JspException {

        if (value == null) {

            this.initValue = "";

            return;

        }

        this.initValue = ((String) ExpressionEvaluatorManager.evaluate("value", value, Object.class, this, this.pageContext));

    }


    public String getValidators() {

        return this.validators;

    }


    public void setValidators(String validators) {

        this.validators = validators;

    }


    public String getDictName() {

        return this.dictName;

    }


    public void setDictName(String dictName) {

        this.dictName = dictName;

    }

}