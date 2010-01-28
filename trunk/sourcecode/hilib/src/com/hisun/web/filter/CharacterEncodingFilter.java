package com.hisun.web.filter;


import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class CharacterEncodingFilter extends TemplateFilter {
    private String encoding;
    private boolean ignore;

    public CharacterEncodingFilter() {

        this.encoding = null;


        this.ignore = true;
    }

    public boolean doPreProcessing(ServletRequest request, ServletResponse response) throws IOException, ServletException {

        if ((((this.ignore) || (request.getCharacterEncoding() == null))) && (this.encoding != null)) {

            request.setCharacterEncoding(this.encoding);
        }


        return true;
    }

    public void doPostProcessing(ServletRequest request, ServletResponse response) throws IOException, ServletException {
    }

    public void initFilter(FilterConfig filterConfig) throws ServletException {

        this.encoding = filterConfig.getInitParameter("encoding");

        if ("false".equals(filterConfig.getInitParameter("ignore"))) this.ignore = false;
    }

    public void destroy() {

        this.encoding = null;

        super.destroy();
    }
}