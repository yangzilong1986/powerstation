package org.pssframework.tag;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.pssframework.util.TagUtils;

/**
 * <p> Layout path. </p>
 * <p> Create by Zhangyu. </p>
 * @author Electricity Service Center, Hisun Technology
 * @version $1.0 $Date: 2010-05-06 11:33:00 
 * @since 1.0
 */
public class PathTag extends TagSupport {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6471498551434795282L;

    //protected static PropertiesConfig properties = PropertiesConfig.getConfigFile("ewebui-layout");
    protected static final String CONFIG_FOLDER = "/WEB-INF/conf/";
    protected static final String CONFIG_FILENAME = "pss-layout.properties";

    protected static final String TYPE_WEBAPP_PATH = "webapp";
    protected static final String TYPE_STYLE_PATH = "style";
    protected static final String TYPE_BGCOLOR_PATH = "bgcolor";
    protected static final String TYPE_CUSTOM_PATH = "custom";

    protected String type = null;
    protected String property = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * 
     */
    @Override
    public int doStartTag() throws JspException {
        String contextRealPath = pageContext.getServletContext().getRealPath("/");
        String contextPath = pageContext.getServletContext().getContextPath();
        Properties props = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(contextRealPath + CONFIG_FOLDER + CONFIG_FILENAME));
            props.load(inputStream);

            String pathText = null;

            if(this.type == null) {
                pathText = contextPath;
            }
            else if(TYPE_WEBAPP_PATH.equalsIgnoreCase(this.type)) {
                pathText = contextPath;
            }
            else if(TYPE_STYLE_PATH.equalsIgnoreCase(this.type)) {
                pathText = contextPath + "/style" + "/" + props.getProperty("layout.style");
            }
            else if(TYPE_BGCOLOR_PATH.equalsIgnoreCase(this.type)) {
                pathText = contextPath + "/style" + "/" + props.getProperty("layout.style") + "/bgcolor" + "/"
                        + props.getProperty("layout.bgcolor");
            }
            else if(TYPE_CUSTOM_PATH.equalsIgnoreCase(this.type)) {
                pathText = contextPath + props.getProperty(this.property);
            }

            TagUtils.getInstance().write(pageContext, pathText);
        }
        catch(Exception _e) {
            _e.printStackTrace();
        }
        finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                }
                catch(IOException _ioe) {
                    _ioe.printStackTrace();
                }
            }
            if(props != null) {
                props.clear();
                props = null;
            }
        }
        return SKIP_BODY;
    }

    /**
     * 
     */
    @Override
    public void release() {
        super.release();
        this.type = null;
        this.property = null;
    }
}
