 package com.hisun.server.manage.servlet;
 
 import com.hisun.exception.HiException;
 import com.hisun.jms.bean.HiASyncProcess;
 import com.hisun.util.HiServiceLocator;
 import java.io.IOException;
 import javax.servlet.ServletConfig;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.lang.StringUtils;
 
 public class HiMDBManagerServlet extends HttpServlet
 {
   String[] mdbArr;
 
   public HiMDBManagerServlet()
   {
     this.mdbArr = null; }
 
   public void init(ServletConfig config) throws ServletException {
     String mdb_list = config.getInitParameter("list");
     this.mdbArr = StringUtils.split(mdb_list, "|");
     try
     {
       start();
     }
     catch (HiException e)
     {
       throw new ServletException(e);
     }
   }
 
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
     doGet(request, response);
   }
 
   public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     try
     {
       if (StringUtils.equalsIgnoreCase(request.getParameter("CMD"), "start"))
       {
         start();
       }
       else if (StringUtils.equalsIgnoreCase(request.getParameter("CMD"), "stop"))
       {
         stop();
       }
     }
     catch (HiException e) {
       throw new ServletException(e);
     }
   }
 
   private void start()
     throws HiException
   {
     if (this.mdbArr == null) {
       return;
     }
 
     for (int i = 0; i < this.mdbArr.length; ++i)
     {
       String serverName = this.mdbArr[i];
       String jndi = "ibs/jms/" + serverName;
 
       HiServiceLocator locator = HiServiceLocator.getInstance();
 
       Object o = locator.lookup(jndi);
       if (o == null) {
         HiASyncProcess asynProcess = HiASyncProcess.getInstance(serverName);
         locator.bind(jndi, asynProcess);
       }
     }
   }
 
   private void stop()
     throws HiException
   {
     if ((this.mdbArr == null) || 
       (this.mdbArr == null)) {
       return;
     }
 
     for (int i = 0; i < this.mdbArr.length; ++i)
     {
       String serverName = this.mdbArr[i];
       String jndi = "ibs/jms/" + serverName;
 
       HiServiceLocator locator = HiServiceLocator.getInstance();
 
       Object o = locator.lookup(jndi);
       if (o != null) {
         HiASyncProcess asynProcess = (HiASyncProcess)o;
         asynProcess.destory();
         locator.unbind(jndi);
       }
     }
   }
 }