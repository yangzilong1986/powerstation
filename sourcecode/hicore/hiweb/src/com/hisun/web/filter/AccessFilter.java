 package com.hisun.web.filter;

 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;

 import javax.servlet.*;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import java.io.File;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 
 public class AccessFilter
   implements Filter
 {
   protected FilterConfig filterConfig;
   private String redirectURL;
   private ArrayList excludeURLList;
   private ArrayList includeURLList;
   private String file;
   private String sessionKey;
   private long lastModified;
   private Logger log;
 
   public AccessFilter()
   {
     this.filterConfig = null;
     this.redirectURL = null;
     this.excludeURLList = new ArrayList();
     this.includeURLList = new ArrayList();
     this.file = "/conf/checkurl.xml";
     this.sessionKey = null;
     this.lastModified = -1L;
     this.log = HiLog.getLogger("SYS.trc");
   }
 
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
     HttpServletRequest request = (HttpServletRequest)servletRequest;
     HttpServletResponse response = (HttpServletResponse)servletResponse;
 
     HttpSession session = request.getSession();
     if (this.sessionKey == null) {
       filterChain.doFilter(request, response);
       return;
     }
 
     if (isCanLoaded(this.file)) {
       this.excludeURLList.clear();
       this.includeURLList.clear();
       loadFilterURL(this.file, this.excludeURLList, this.includeURLList);
     }
 
     if ((checkRequestURIIntFilterList(request)) && 
       (!(checkSession(session, this.sessionKey)))) {
       response.sendRedirect(request.getContextPath() + this.redirectURL);
       return;
     }
 
     filterChain.doFilter(servletRequest, servletResponse);
   }
 
   public void destroy() {
     this.excludeURLList.clear();
   }
 
   private boolean checkSession(HttpSession session, String key) {
     int idx = key.indexOf(46);
     String objNam = null;
     if (idx != -1) {
       objNam = key.substring(idx - 1);
       key = key.substring(idx);
     }
     if (StringUtils.isNotBlank(objNam)) {
       HashMap map = (HashMap)session.getAttribute(objNam);
       if (map == null) {
         return false;
       }
       return map.containsKey(key);
     }
     return (session.getAttribute(key) != null);
   }
 
   private boolean checkRequestURIIntFilterList(HttpServletRequest request)
   {
     String tmp;
     String uri = request.getServletPath() + ((request.getPathInfo() == null) ? "" : request.getPathInfo());
 
     for (int i = 0; i < this.includeURLList.size(); ++i) {
       tmp = (String)this.includeURLList.get(i);
       this.log.info("include: [" + uri + "][" + tmp + "]");
       if (uri.matches(tmp)) {
         return true;
       }
     }
 
     for (i = 0; i < this.excludeURLList.size(); ++i) {
       tmp = (String)this.excludeURLList.get(i);
       this.log.info("exclude: [" + uri + "][" + tmp + "]");
       if (uri.matches(tmp)) {
         return false;
       }
     }
 
     return false;
   }
 
   public void init(FilterConfig filterConfig) throws ServletException {
     this.filterConfig = filterConfig;
     this.redirectURL = filterConfig.getInitParameter("redirectURL");
     this.sessionKey = filterConfig.getInitParameter("checkSessionKey");
     this.file = filterConfig.getInitParameter("file");
     if (StringUtils.isBlank(this.file)) {
       this.file = "/conf/checkurl.xml";
     }
     else if (!(this.file.startsWith("/")))
       this.file = "/" + this.file;
   }
 
   public boolean isCanLoaded(String file)
     throws ServletException
   {
     File f = new File(this.filterConfig.getServletContext().getRealPath(file));
     if (f.lastModified() != this.lastModified) {
       this.lastModified = f.lastModified();
       return true;
     }
     return false;
   }
 
   public void loadFilterURL(String file, ArrayList excludeList, ArrayList includeList)
     throws ServletException
   {
     Element element;
     String url;
     SAXReader reader = new SAXReader();
     Document doc = null;
     try {
       doc = reader.read(this.filterConfig.getServletContext().getResource(file));
     }
     catch (Exception e) {
       throw new ServletException(file, e);
     }
     Element root = doc.getRootElement();
     Element include = root.element("include");
     Element exclude = root.element("exclude");
     Iterator iter = null;
     if (include != null) {
       iter = include.elementIterator();
       while (iter.hasNext()) {
         element = (Element)iter.next();
 
         url = element.attributeValue("url");
         if (StringUtils.isBlank(url)) {
           continue;
         }
         includeList.add(url);
       }
     }
 
     if (exclude != null) {
       iter = exclude.elementIterator();
       while (iter.hasNext()) {
         element = (Element)iter.next();
 
         url = element.attributeValue("url");
         if (StringUtils.isBlank(url)) {
           continue;
         }
         excludeList.add(url);
       }
     }
   }
 }