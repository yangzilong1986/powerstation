package com.hisun.web.tag;


import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.dom4j.DocumentException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.net.MalformedURLException;

public class HiConvertTag extends BodyTagSupport {
    private String _value;
    private String _defaultValue;
    private String _name;
    private HiConvert _convert;
    private Logger _log;

    public HiConvertTag() {

        this._value = "";

        this._defaultValue = "";


        this._convert = HiConvert.getInstance();

        this._log = HiLog.getLogger("convert.trc");
    }

    public int doEndTag() throws JspException {

        JspWriter out = this.pageContext.getOut();


        ServletContext context = this.pageContext.getServletContext();
        try {

            this._convert.init(context.getRealPath("/conf/convert.xml"));
        } catch (MalformedURLException e) {

            throw new JspException("init Convert failure", e);
        } catch (DocumentException e) {

            throw new JspException("init Convert failure", e);
        }

        if (this._log.isDebugEnabled()) {

            this._log.debug("Before Convert:[" + this._value + "]:name[" + this._name + "]");
        }

        String value = this._convert.convert(this._name, this._value);

        if (this._log.isDebugEnabled()) {

            this._log.debug("After Convert:[" + this._value + "]:name[" + this._name + "]");
        }

        if (value == null) value = this._value;
        try {

            out.print(value);
        } catch (IOException e) {

            throw new JspException(e);
        }


        super.doEndTag();

        return 6;
    }

    public int doStartTag() throws JspException {

        super.doStartTag();

        return 1;
    }

    public String getValue() {

        return this._value;
    }

    public void setValue(String value) throws JspException {

        this._value = ((String) ExpressionEvaluatorManager.evaluate("value", value, Object.class, this, this.pageContext));

        if ((this._value == null) || (this._value.equals(""))) this._value = this._defaultValue;
    }

    public String getDefaultValue() {

        return this._defaultValue;
    }

    public void setDefaultValue(String defaultValue) throws JspException {

        this._defaultValue = defaultValue;

        if ((this._value == null) || (this._value.equals(""))) this._value = this._defaultValue;
    }

    public String getName() {

        return this._name;
    }

    public void setName(String name) {

        this._name = name;
    }
}