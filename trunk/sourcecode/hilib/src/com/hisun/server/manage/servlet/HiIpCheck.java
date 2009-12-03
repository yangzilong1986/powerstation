/*    */ package com.hisun.server.manage.servlet;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ import java.net.Socket;
/*    */ import java.util.ArrayList;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiIpCheck
/*    */ {
/* 17 */   private String[] _ipLst = null;
/*    */ 
/*    */   public HiIpCheck()
/*    */   {
/*    */   }
/*    */ 
/*    */   public HiIpCheck(String ipLst) {
/* 24 */     setIpCheck(ipLst);
/*    */   }
/*    */ 
/*    */   public HiIpCheck(String[] ipLst) {
/* 28 */     setIpCheck(ipLst);
/*    */   }
/*    */ 
/*    */   public HiIpCheck(ArrayList ipLst) {
/* 32 */     setIpCheck(ipLst);
/*    */   }
/*    */ 
/*    */   public void setIpCheck(String ipLst) {
/* 36 */     if (ipLst == null) {
/* 37 */       return;
/*    */     }
/* 39 */     this._ipLst = ipLst.split("\\|");
/*    */   }
/*    */ 
/*    */   public void setIpCheck(String[] ipLst) {
/* 43 */     if (ipLst == null) {
/* 44 */       return;
/*    */     }
/* 46 */     this._ipLst = ipLst;
/*    */   }
/*    */ 
/*    */   public void setIpCheck(ArrayList ipLst) {
/* 50 */     if (ipLst == null) {
/* 51 */       return;
/*    */     }
/* 53 */     this._ipLst = ((String[])(String[])ipLst.toArray());
/*    */   }
/*    */ 
/*    */   public boolean check(String ip) {
/* 57 */     return containsIp(ip);
/*    */   }
/*    */ 
/*    */   public boolean check(HttpServletRequest request) {
/* 61 */     return containsIp(getIpAddr(request));
/*    */   }
/*    */ 
/*    */   public boolean check(Socket socket) {
/* 65 */     return containsIp(socket.getInetAddress().getHostAddress());
/*    */   }
/*    */ 
/*    */   public String getIpAddr(HttpServletRequest request) {
/* 69 */     String ip = request.getHeader("x-forwarded-for");
/* 70 */     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 71 */       ip = request.getHeader("Proxy-Client-IP");
/*    */     }
/* 73 */     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 74 */       ip = request.getHeader("WL-Proxy-Client-IP");
/*    */     }
/* 76 */     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 77 */       ip = request.getRemoteAddr();
/*    */     }
/* 79 */     return ip;
/*    */   }
/*    */ 
/*    */   private boolean containsIp(String ip) {
/* 83 */     if (this._ipLst == null) {
/* 84 */       return true;
/*    */     }
/*    */ 
/* 87 */     for (int i = 0; i < this._ipLst.length; ++i) {
/* 88 */       if (StringUtils.equals(this._ipLst[i], ip)) {
/* 89 */         return true;
/*    */       }
/*    */     }
/* 92 */     return false;
/*    */   }
/*    */ }