/*     */ package com.hisun.server.manage.servlet;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.parser.svrlst.HiFrontTabNode;
/*     */ import com.hisun.parser.svrlst.HiGroupNode;
/*     */ import com.hisun.parser.svrlst.HiSVRLSTParser;
/*     */ import com.hisun.parser.svrlst.HiServerNode;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiServiceLocator;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiAutoBootServerServlet extends HttpServlet
/*     */ {
/*     */   private ArrayList serverList;
/*     */   private String SVRLSTCfg;
/*     */ 
/*     */   public HiAutoBootServerServlet()
/*     */   {
/*  30 */     this.serverList = new ArrayList();
/*  31 */     this.SVRLSTCfg = "etc/SVRLST_PUB.XML"; }
/*     */ 
/*     */   public void init(ServletConfig config) throws ServletException {
/*     */     try {
/*  35 */       initConfig(config);
/*  36 */       StartAllServer();
/*     */     } catch (Throwable e) {
/*  38 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initConfig(ServletConfig config)
/*     */     throws Exception
/*     */   {
/*     */     String[] tmps;
/*     */     int i;
/*  44 */     super.init(config);
/*  45 */     String tmp = config.getInitParameter("config");
/*  46 */     if (StringUtils.isNotBlank(tmp)) {
/*  47 */       this.SVRLSTCfg = tmp;
/*     */     }
/*     */ 
/*  50 */     HiSVRLSTParser parser = new HiSVRLSTParser();
/*  51 */     HiFrontTabNode frontTab = parser.parser();
/*     */ 
/*  53 */     tmp = config.getInitParameter("a");
/*  54 */     if (StringUtils.isNotBlank(tmp)) {
/*  55 */       for (int i = 0; i < frontTab.size(); ++i) {
/*  56 */         HiGroupNode group = frontTab.getGroup(i);
/*  57 */         for (int j = 0; j < group.size(); ++j) {
/*  58 */           this.serverList.add(group.getServer(i));
/*     */         }
/*     */       }
/*  61 */       return;
/*     */     }
/*     */ 
/*  64 */     tmp = config.getInitParameter("g");
/*  65 */     if (StringUtils.isNotBlank(tmp)) {
/*  66 */       tmps = tmp.split("\\|");
/*  67 */       for (i = 0; i < tmps.length; ++i) {
/*  68 */         if (StringUtils.isBlank(tmps[i])) {
/*     */           continue;
/*     */         }
/*  71 */         HiGroupNode group = frontTab.getGroup(tmps[i]);
/*  72 */         for (int j = 0; j < group.size(); ++j) {
/*  73 */           this.serverList.add(group.getServer(i));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  78 */     tmp = config.getInitParameter("s");
/*  79 */     if (StringUtils.isNotBlank(tmp)) {
/*  80 */       tmps = tmp.split("\\|");
/*  81 */       for (i = 0; i < tmps.length; ++i) {
/*  82 */         if (StringUtils.isBlank(tmps[i])) {
/*     */           continue;
/*     */         }
/*  85 */         HiServerNode server = frontTab.getServer(tmps[i]);
/*  86 */         if (server == null) {
/*  87 */           System.out.println("Server:[" + tmps[i] + "] not existed");
/*     */         }
/*  90 */         else if (!(this.serverList.contains(server)))
/*  91 */           this.serverList.add(server);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void StartAllServer() throws Exception
/*     */   {
/*  98 */     for (int i = 0; i < this.serverList.size(); ++i) {
/*  99 */       HiServerNode server = (HiServerNode)this.serverList.get(i);
/* 100 */       System.out.println("Starting server:[" + server.getName() + "]");
/* 101 */       startOneServer(server);
/* 102 */       System.out.println("Started server:[" + server.getName() + "]");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void startOneServer(HiServerNode server) throws Exception {
/* 107 */     boolean existed = true;
/* 108 */     if ("EJB".equalsIgnoreCase(HiICSProperty.getProperty("framework")))
/* 109 */       doStartOneServerForEJB(server);
/*     */     else
/* 111 */       doStartOneServerForPOJO(server);
/*     */   }
/*     */ 
/*     */   private void doStartOneServerForEJB(HiServerNode server)
/*     */     throws Exception
/*     */   {
/* 118 */     boolean existed = true;
/* 119 */     HiServiceLocator locator = HiServiceLocator.getInstance();
/* 120 */     if (!("JMS".equalsIgnoreCase(server.getType()))) {
/*     */       try {
/* 122 */         locator.lookup("ibs/ejb/" + server.getName());
/*     */       } catch (Exception e2) {
/* 124 */         existed = false;
/*     */       }
/*     */     }
/*     */ 
/* 128 */     if (!(existed)) {
/* 129 */       Logger log = HiLog.getErrorLogger("SYS.log");
/* 130 */       log.error("[" + server.getName() + "] not deployed");
/* 131 */       System.out.println("[" + server.getName() + "] not deployed");
/* 132 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 136 */       HiClientUtil.invoke("start", server.getName(), server.getGrpNam(), server.getConfig_file(), server.getType());
/*     */     }
/*     */     catch (Exception e) {
/* 139 */       Logger log = HiLog.getErrorLogger("SYS.log");
/* 140 */       log.error("boot server:[" + server.getName() + "] failure", e);
/* 141 */       System.out.println("boot server:[" + server.getName() + "] failure");
/*     */ 
/* 143 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doStartOneServerForPOJO(HiServerNode server) throws Exception {
/*     */     try {
/* 149 */       HiClientUtilPOJO.invoke("start", server.getName(), server.getGrpNam(), server.getConfig_file(), server.getType());
/*     */     }
/*     */     catch (Exception e) {
/* 152 */       Logger log = HiLog.getErrorLogger("SYS.log");
/* 153 */       log.error("boot server:[" + server.getName() + "] failure", e);
/* 154 */       System.out.println("boot server:[" + server.getName() + "] failure");
/*     */ 
/* 156 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }