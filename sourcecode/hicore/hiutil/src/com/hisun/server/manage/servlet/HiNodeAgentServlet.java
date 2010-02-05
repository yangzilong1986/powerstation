 package com.hisun.server.manage.servlet;
 
 import com.hisun.exception.HiException;
 import com.hisun.mng.HiRegionInfo;
 import java.io.IOException;
 import javax.servlet.ServletConfig;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.lang.StringUtils;
 
 public class HiNodeAgentServlet extends HttpServlet
 {
   private HiIpCheck _ipCheck;
   private HiRegionInfo regionInfo;
 
   public HiNodeAgentServlet()
   {
     this._ipCheck = new HiIpCheck();
   }
 
   public void init(ServletConfig config) throws ServletException {
     try {
       this.regionInfo = HiRegionInfo.parse();
     } catch (HiException e) {
       e.printStackTrace();
     }
   }
 
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
     doGet(request, response);
   }
 
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
     String nodId = request.getParameter("GRP.NOD_ID");
     if (!(StringUtils.isNotBlank(nodId)))
       return;
   }
 }