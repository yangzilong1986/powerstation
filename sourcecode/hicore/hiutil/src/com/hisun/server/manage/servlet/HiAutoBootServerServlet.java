 package com.hisun.server.manage.servlet;
 
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.parser.svrlst.HiFrontTabNode;
 import com.hisun.parser.svrlst.HiGroupNode;
 import com.hisun.parser.svrlst.HiSVRLSTParser;
 import com.hisun.parser.svrlst.HiServerNode;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiServiceLocator;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import javax.servlet.ServletConfig;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
 import org.apache.commons.lang.StringUtils;
 
 public class HiAutoBootServerServlet extends HttpServlet
 {
   private ArrayList serverList;
   private String SVRLSTCfg;
 
   public HiAutoBootServerServlet()
   {
     this.serverList = new ArrayList();
     this.SVRLSTCfg = "etc/SVRLST_PUB.XML"; }
 
   public void init(ServletConfig config) throws ServletException {
     try {
       initConfig(config);
       StartAllServer();
     } catch (Throwable e) {
       e.printStackTrace();
     }
   }
 
   private void initConfig(ServletConfig config)
     throws Exception
   {
     String[] tmps;
     int i;
     super.init(config);
     String tmp = config.getInitParameter("config");
     if (StringUtils.isNotBlank(tmp)) {
       this.SVRLSTCfg = tmp;
     }
 
     HiSVRLSTParser parser = new HiSVRLSTParser();
     HiFrontTabNode frontTab = parser.parser();
 
     tmp = config.getInitParameter("a");
     if (StringUtils.isNotBlank(tmp)) {
       for (int i = 0; i < frontTab.size(); ++i) {
         HiGroupNode group = frontTab.getGroup(i);
         for (int j = 0; j < group.size(); ++j) {
           this.serverList.add(group.getServer(i));
         }
       }
       return;
     }
 
     tmp = config.getInitParameter("g");
     if (StringUtils.isNotBlank(tmp)) {
       tmps = tmp.split("\\|");
       for (i = 0; i < tmps.length; ++i) {
         if (StringUtils.isBlank(tmps[i])) {
           continue;
         }
         HiGroupNode group = frontTab.getGroup(tmps[i]);
         for (int j = 0; j < group.size(); ++j) {
           this.serverList.add(group.getServer(i));
         }
       }
     }
 
     tmp = config.getInitParameter("s");
     if (StringUtils.isNotBlank(tmp)) {
       tmps = tmp.split("\\|");
       for (i = 0; i < tmps.length; ++i) {
         if (StringUtils.isBlank(tmps[i])) {
           continue;
         }
         HiServerNode server = frontTab.getServer(tmps[i]);
         if (server == null) {
           System.out.println("Server:[" + tmps[i] + "] not existed");
         }
         else if (!(this.serverList.contains(server)))
           this.serverList.add(server);
       }
     }
   }
 
   private void StartAllServer() throws Exception
   {
     for (int i = 0; i < this.serverList.size(); ++i) {
       HiServerNode server = (HiServerNode)this.serverList.get(i);
       System.out.println("Starting server:[" + server.getName() + "]");
       startOneServer(server);
       System.out.println("Started server:[" + server.getName() + "]");
     }
   }
 
   private void startOneServer(HiServerNode server) throws Exception {
     boolean existed = true;
     if ("EJB".equalsIgnoreCase(HiICSProperty.getProperty("framework")))
       doStartOneServerForEJB(server);
     else
       doStartOneServerForPOJO(server);
   }
 
   private void doStartOneServerForEJB(HiServerNode server)
     throws Exception
   {
     boolean existed = true;
     HiServiceLocator locator = HiServiceLocator.getInstance();
     if (!("JMS".equalsIgnoreCase(server.getType()))) {
       try {
         locator.lookup("ibs/ejb/" + server.getName());
       } catch (Exception e2) {
         existed = false;
       }
     }
 
     if (!(existed)) {
       Logger log = HiLog.getErrorLogger("SYS.log");
       log.error("[" + server.getName() + "] not deployed");
       System.out.println("[" + server.getName() + "] not deployed");
       return;
     }
     try
     {
       HiClientUtil.invoke("start", server.getName(), server.getGrpNam(), server.getConfig_file(), server.getType());
     }
     catch (Exception e) {
       Logger log = HiLog.getErrorLogger("SYS.log");
       log.error("boot server:[" + server.getName() + "] failure", e);
       System.out.println("boot server:[" + server.getName() + "] failure");
 
       e.printStackTrace();
     }
   }
 
   private void doStartOneServerForPOJO(HiServerNode server) throws Exception {
     try {
       HiClientUtilPOJO.invoke("start", server.getName(), server.getGrpNam(), server.getConfig_file(), server.getType());
     }
     catch (Exception e) {
       Logger log = HiLog.getErrorLogger("SYS.log");
       log.error("boot server:[" + server.getName() + "] failure", e);
       System.out.println("boot server:[" + server.getName() + "] failure");
 
       e.printStackTrace();
     }
   }
 }