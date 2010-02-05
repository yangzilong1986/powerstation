 package com.hisun.web.servlet;
 
 import com.hisun.data.cache.HiDBUtil;
 import com.hisun.data.cache.HiDataCacheConfig;
 import com.hisun.exception.HiException;
 import com.hisun.server.manage.servlet.HiIpCheck;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.net.URL;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Set;
 import javax.servlet.ServletConfig;
 import javax.servlet.ServletContext;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.lang.StringUtils;
 
 public class HiDataCacheServlet extends HttpServlet
 {
   private String file = "/conf/data_cache.xml";
   private HiDataCacheConfig dataCacheConfig;
   public static boolean isLoaded = false;
   private HiIpCheck ipCheck = new HiIpCheck();
 
   public void destroy()
   {
     super.destroy();
   }
 
   public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     doPost(request, response);
   }
 
   public void doPost(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     if (!(this.ipCheck.check(request))) {
       response.getWriter().write("client ip:[" + this.ipCheck.getIpAddr(request) + "] deny");
       return;
     }
 
     String id = request.getParameter("ID");
 
     if (StringUtils.equalsIgnoreCase(request.getParameter("CMD"), "reloadall"))
     {
       if (this.dataCacheConfig != null) {
         this.dataCacheConfig.clear();
       }
       load();
       return;
     }
 
     if (StringUtils.isBlank(id)) {
       throw new ServletException("id is empty");
     }
 
     if (!(StringUtils.equalsIgnoreCase(request.getParameter("CMD"), "reloadid"))) {
       return;
     }
     if (this.dataCacheConfig == null) {
       load();
     }
     else {
       HiDBUtil dbUtil = new HiDBUtil();
       try {
         this.dataCacheConfig.validate(id, dbUtil);
       } catch (HiException e) {
       }
       finally {
         dbUtil.close();
       }
     }
   }
 
   public void init(ServletConfig config)
     throws ServletException
   {
     if (isLoaded) {
       return;
     }
     super.init();
     String tmp = config.getInitParameter("ipLst");
     if (tmp == null) {
       tmp = getServletContext().getInitParameter("ipLst");
     }
     this.ipCheck.setIpCheck(tmp);
     load();
   }
 
   private void load() throws ServletException
   {
     String tmpFile = getServletConfig().getInitParameter("FILE");
     if (StringUtils.isNotBlank(tmpFile)) {
       this.file = tmpFile;
     }
 
     HiDBUtil dbUtil = new HiDBUtil();
     try {
       this.dataCacheConfig = HiDataCacheConfig.loadStream(getServletConfig().getServletContext().getResource(this.file).openStream());
 
       this.dataCacheConfig.process(dbUtil);
       HiDataCacheConfig.setInstance(this.dataCacheConfig);
     } catch (Exception e) {
     }
     finally {
       dbUtil.close();
     }
     HashMap map = this.dataCacheConfig.getDataMap();
     Iterator iter = map.keySet().iterator();
     while (iter.hasNext()) {
       String key = (String)iter.next();
       getServletConfig().getServletContext().setAttribute(key, map.get(key));
     }
     isLoaded = true;
   }
 }