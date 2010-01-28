package com.hisun.web.tag;


import com.hisun.message.HiETF;
import com.hisun.message.HiETFFactory;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;


public class HiInput extends BodyTagSupport {
    private String id;
    private String name;
    private String width;
    private String height;
    private String maxLength;
    private String size;
    private String style;
    private String onclick;
    private String onchange;
    private String onfocus;
    private String onblur;
    private String type;
    private String cols;
    private String rows;
    private String css;
    private String scope;
    private String maxlength;
    private String validators;
    private String displayName;


    public int doStartTag() throws JspException {

        JspWriter out = this.pageContext.getOut();


        HiETF etf = null;

        String scope = getScope();

        String[] tmp = StringUtils.split(scope, ".");

        String etfname = "";

        if (tmp != null) {

            if (tmp.length == 1) {

                scope = tmp[0];

                etfname = "etf";

            } else if (tmp.length >= 2) {

                scope = tmp[0];

                etfname = tmp[1];

            } else {

                scope = "request";

                etfname = "etf";

            }

        } else {

            scope = "request";

            etfname = "etf";

        }


        if (StringUtils.equals("request", scope)) {

            etf = (HiETF) this.pageContext.getRequest().getAttribute(etfname);

        } else if (StringUtils.equals("session", scope)) {

            etf = (HiETF) this.pageContext.getSession().getAttribute(etfname);

        }


        if (etf == null) etf = HiETFFactory.createETF();

        try {

            StringBuffer buf = new StringBuffer();

            buf.append("<input name=" + this.name + " type=" + this.type + " id=" + this.id);


            if (!(StringUtils.isEmpty(this.onclick))) {

                buf.append(" onclick='" + this.onclick + "'");

            }

            if (!(StringUtils.isEmpty(this.style))) {

                buf.append(" style='" + this.style + "'");

            }

            if (!(StringUtils.isEmpty(this.validators))) {

                buf.append(" validators='" + this.validators + "'");

            }

            if (!(StringUtils.isEmpty(this.displayName))) {

                buf.append(" displayName='" + this.displayName + "'");

            }

            if (!(StringUtils.isEmpty(this.width))) {

                buf.append(" width='" + this.width + "'");

            }

            if (!(StringUtils.isEmpty(this.height))) {

                buf.append(" height='" + this.height + "'");

            }

            if (!(StringUtils.isEmpty(this.maxLength))) {

                buf.append(" maxLength" + this.maxLength + "'");

            }

            if (!(StringUtils.isEmpty(this.size))) {

                buf.append(" size='" + this.size + "'");

            }

            if (!(StringUtils.isEmpty(this.onchange))) {

                buf.append(" onchange='" + this.onchange + "'");

            }

            if (!(StringUtils.isEmpty(this.onfocus))) {

                buf.append(" onfocus='" + this.onfocus + "'");

            }

            if (!(StringUtils.isEmpty(this.onblur))) {

                buf.append(" onblur='" + this.onblur + "'");

            }

            if (!(StringUtils.isEmpty(this.cols))) {

                buf.append(" cols=" + this.cols);

            }

            if (!(StringUtils.isEmpty(this.rows))) {

                buf.append(" rows=" + this.rows);

            }

            if (!(StringUtils.isEmpty(this.css))) {

                buf.append(" class=" + this.css);

            }

            if (!(StringUtils.isEmpty(this.maxlength))) {

                buf.append(" maxlength=" + this.maxlength);

            }


            String value = (etf.getGrandChildValue(this.name) == null) ? "" : etf.getGrandChildValue(this.name);

            buf.append(" value='" + value + "'");


            buf.append(">");

            out.println(buf.toString());

            out.println("</input>");

        } catch (IOException e) {

            e.printStackTrace();

        }

        return super.doStartTag();

    }


    public int doAfterBody() throws JspException {

        return super.doAfterBody();

    }


    public int doEndTag() throws JspException {

        return super.doEndTag();

    }


    public String getId() {

        return this.id;

    }


    public void setId(String id) {

        this.id = id;

    }


    public String getName() {

        return this.name;

    }


    public void setName(String name) {

        this.name = name;

    }


    public String getStyle() {

        return this.style;

    }


    public void setStyle(String style) {

        this.style = style;

    }


    public String getOnclick() {

        return this.onclick;

    }


    public void setOnclick(String onclick) {

        this.onclick = onclick;

    }


    public String getValidators() {

        return this.validators;

    }


    public void setValidators(String validators) {

        this.validators = validators;

    }


    public String getDisplayName() {

        return this.displayName;

    }


    public void setDisplayName(String displayName) {

        this.displayName = displayName;

    }


    public String getWidth() {

        return this.width;

    }


    public void setWidth(String width) {

        this.width = width;

    }


    public String getHeight() {

        return this.height;

    }


    public void setHeight(String height) {

        this.height = height;

    }


    public String getMaxLength() {

        return this.maxLength;

    }


    public void setMaxLength(String maxLength) {

        this.maxLength = maxLength;

    }


    public String getSize() {

        return this.size;

    }


    public void setSize(String size) {

        this.size = size;

    }


    public String getOnchange() {

        return this.onchange;

    }


    public void setOnchange(String onchange) {

        this.onchange = onchange;

    }


    public String getOnfocus() {

        return this.onfocus;

    }


    public void setOnfocus(String onfocus) {

        this.onfocus = onfocus;

    }


    public String getOnblur() {

        return this.onblur;

    }


    public void setOnblur(String onblur) {

        this.onblur = onblur;

    }


    public String getType() {

        return this.type;

    }


    public void setType(String type) {

        this.type = type;

    }


    public String getCols() {

        return this.cols;

    }


    public void setCols(String cols) {

        this.cols = cols;

    }


    public String getRows() {

        return this.rows;

    }


    public void setRows(String rows) {

        this.rows = rows;

    }


    public String getCss() {

        return this.css;

    }


    public void setCss(String css) {

        this.css = css;

    }


    public String getScope() {

        return this.scope;

    }


    public void setScope(String scope) {

        this.scope = scope;

    }


    public String getMaxlength() {

        return this.maxlength;

    }


    public void setMaxlength(String maxlength) {

        this.maxlength = maxlength;

    }

}