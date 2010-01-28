package com.hisun.web.tag;


import com.hisun.util.MessageResources;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;


public class HiMessageTag extends BodyTagSupport {
    private String arg0;
    private String arg1;
    private String arg2;
    private String arg3;
    private String arg4;
    private String key;
    private String bundle;
    private String locale;


    public HiMessageTag() {

        this.bundle = "application";

    }


    public int doEndTag() throws JspException {

        JspWriter out = this.pageContext.getOut();

        MessageResources messageResources = MessageResources.getMessageResources("resource." + this.bundle);

        String message = messageResources.getMessage(this.key, new Object[]{this.arg0, this.arg1, this.arg2, this.arg3, this.arg4});

        try {

            out.print(message);

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


    public String getArg0() {

        return this.arg0;

    }


    public void setArg0(String arg0) throws JspException {

        if (arg0 == null) {

            this.arg0 = "";

            return;

        }

        this.arg0 = ((String) ExpressionEvaluatorManager.evaluate("arg0", arg0, Object.class, this, this.pageContext));

    }


    public String getArg1() {

        return this.arg1;

    }


    public void setArg1(String arg1) throws JspException {

        if (arg1 == null) {

            this.arg1 = "";

            return;

        }

        this.arg1 = ((String) ExpressionEvaluatorManager.evaluate("arg1", arg1, Object.class, this, this.pageContext));

    }


    public String getArg2() {

        return this.arg2;

    }


    public void setArg2(String arg2) throws JspException {

        if (arg2 == null) {

            this.arg2 = "";

            return;

        }

        this.arg2 = ((String) ExpressionEvaluatorManager.evaluate("arg2", arg2, Object.class, this, this.pageContext));

    }


    public String getArg3() {

        return this.arg3;

    }


    public void setArg3(String arg3) throws JspException {

        if (arg3 == null) {

            this.arg3 = "";

            return;

        }


        this.arg3 = ((String) ExpressionEvaluatorManager.evaluate("arg3", arg3, Object.class, this, this.pageContext));

    }


    public String getArg4() {

        return this.arg4;

    }


    public void setArg4(String arg4) throws JspException {

        if (arg4 == null) {

            this.arg4 = "";

            return;

        }

        this.arg4 = ((String) ExpressionEvaluatorManager.evaluate("arg4", arg4, Object.class, this, this.pageContext));

    }


    public String getKey() {

        return this.key;

    }


    public void setKey(String key) throws JspException {

        if (key == null) {

            this.key = "";

            return;

        }

        this.key = ((String) ExpressionEvaluatorManager.evaluate("key", key, Object.class, this, this.pageContext));

    }


    public String getBundle() {

        return this.bundle;

    }


    public void setBundle(String bundle) {

        this.bundle = bundle;

    }


    public String getLocale() {

        return this.locale;

    }


    public void setLocale(String locale) {

        this.locale = locale;

    }

}