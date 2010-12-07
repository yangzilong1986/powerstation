package org.pssframework.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * Provides helper methods for JSP tags.
 * <p>
 * Create by Zhangyu.
 * </p>
 * 
 * @author
 * @version $1.0 $Date: 2010-05-07 08:03:44
 * @since 1.0
 */
public class TagUtils {
    /**
     * The Singleton instance.
     */
    private static TagUtils instance = new TagUtils();

    /**
     * Commons logger instance.
     */
    //private static final Logger logger = LoggerFactory.getLog(TagUtils.class);

    /**
     * Maps lowercase JSP scope names to their PageContext integer constant values.
     */
    private static final Map<String, Integer> scopes = new HashMap<String, Integer>();

    /**
     * Initialize the scope names map and the encode variable with the Java 1.4 method if available.
     */
    static {
        scopes.put("page", new Integer(PageContext.PAGE_SCOPE));
        scopes.put("request", new Integer(PageContext.REQUEST_SCOPE));
        scopes.put("session", new Integer(PageContext.SESSION_SCOPE));
        scopes.put("application", new Integer(PageContext.APPLICATION_SCOPE));
    }

    /**
     * Constructor for TagUtils.
     */
    protected TagUtils() {
        super();
    }

    /**
     * Returns the Singleton instance of TagUtils.
     */
    public static TagUtils getInstance() {
        return instance;
    }

    /**
     * Set the instance.
     * This blatently violates the Singleton pattern, but then some say Singletons are an anti-pattern.
     * @param instance The instance to set.
     */
    public static void setInstance(TagUtils instance){
      TagUtils.instance = instance;
    }

    /**
     * Write the specified text as the response to the writer associated with
     * this page.  <strong>WARNING</strong> - If you are writing body content
     * from the <code>doAfterBody()</code> method of a custom tag class that
     * implements <code>BodyTag</code>, you should be calling
     * <code>writePrevious()</code> instead.
     *
     * @param pageContext The PageContext object for this page
     * @param text        The text to be written
     * @throws JspException if an input/output error occurs (already saved)
     */
    public void write(PageContext pageContext, String text) throws JspException {
        JspWriter writer = pageContext.getOut();

        try {
            writer.print(text);
        }
        catch(IOException e) {
            //saveException(pageContext, e);
            //throw new JspException(messages.getMessage("write.io", e.toString()));
        }
    }
}
