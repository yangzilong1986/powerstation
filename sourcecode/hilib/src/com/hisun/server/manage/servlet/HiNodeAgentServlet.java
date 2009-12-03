/*    */ package com.hisun.server.manage.servlet;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.mng.HiRegionInfo;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiNodeAgentServlet extends HttpServlet
/*    */ {
/*    */   private HiIpCheck _ipCheck;
/*    */   private HiRegionInfo regionInfo;
/*    */ 
/*    */   public HiNodeAgentServlet()
/*    */   {
/* 27 */     this._ipCheck = new HiIpCheck();
/*    */   }
/*    */ 
/*    */   public void init(ServletConfig config) throws ServletException {
/*    */     try {
/* 32 */       this.regionInfo = HiRegionInfo.parse();
/*    */     } catch (HiException e) {
/* 34 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*    */   {
/* 40 */     doGet(request, response);
/*    */   }
/*    */ 
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*    */   {
/* 45 */     String nodId = request.getParameter("GRP.NOD_ID");
/* 46 */     if (!(StringUtils.isNotBlank(nodId)))
/*    */       return;
/*    */   }
/*    */ }