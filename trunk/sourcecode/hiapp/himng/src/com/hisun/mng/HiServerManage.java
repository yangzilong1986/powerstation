 package com.hisun.mng;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.parser.svrlst.HiFrontTabNode;
 import com.hisun.parser.svrlst.HiGroupNode;
 import com.hisun.parser.svrlst.HiSVRLSTParser;
 import com.hisun.parser.svrlst.HiServerNode;
 import com.hisun.register.HiRegisterService;
 import com.hisun.register.HiServiceObject;
 import com.hisun.server.manage.servlet.HiClientUtil;
 import com.hisun.server.manage.servlet.HiClientUtilPOJO;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiServiceLocator;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import org.apache.commons.lang.StringUtils;
 
 public class HiServerManage
 {
   private String SVRLSTCfg;
 
   public HiServerManage()
   {
     this.SVRLSTCfg = HiICSProperty.getProperty("svrlst.config");
   }
 
   public int serverGrpQuery(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiSVRLSTParser parser = new HiSVRLSTParser();
     String grpNam = args.get("grpNam");
     if (StringUtils.isBlank(grpNam)) {
       grpNam = "GRP";
     }
     HiETF root = ctx.getCurrentMsg().getETFBody();
     HiFrontTabNode frontTab = null;
     try {
       frontTab = parser.parser();
     } catch (Exception e) {
       throw new HiException("213319", this.SVRLSTCfg, e);
     }
 
     int k = 1;
     for (int i = 0; i < frontTab.size(); ++i) {
       HiGroupNode group = frontTab.getGroup(i);
       HiETF grp = root.addNode(grpNam + "_" + k);
       grp.setChildValue("NAME", group.getName());
       grp.setChildValue("DESC", group.getDesc());
       ++k;
     }
     root.setChildValue(grpNam + "_NUM", String.valueOf(k - 1));
     return 0;
   }
 
   public int serverMonSwitch(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String tmp = HiArgUtils.getStringNotNull(args, "flag");
     boolean flag = false;
     if ("1".equals(tmp)) {
       flag = true;
     }
 
     ArrayList list = getServerList(args, ctx);
     for (int i = 0; i < list.size(); ++i) {
       HiServerNode node = (HiServerNode)list.get(i);
       HiRegisterService.setMonSwitch(node.getName(), flag);
     }
     return 0;
   }
 
   public int serverLogSwitch(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String logLvl = HiArgUtils.getStringNotNull(args, "flag");
 
     ArrayList list = getServerList(args, ctx);
     for (int i = 0; i < list.size(); ++i) {
       HiServerNode node = (HiServerNode)list.get(i);
       HiRegisterService.setLogSwitch(node.getName(), logLvl);
     }
 
     return 0;
   }
 
   public int serverQuery(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String grpNam = args.get("grpNam");
     if (StringUtils.isBlank(grpNam)) {
       grpNam = "GRP";
     }
 
     HiETF root = ctx.getCurrentMsg().getETFBody();
 
     ArrayList list = getServerList(args, ctx);
     int i = 0;
     for (i = 0; i < list.size(); ++i) {
       HiServerNode node = (HiServerNode)list.get(i);
       HiETF grp = root.getChildNode(grpNam + "_" + (i + 1));
       if (grp == null) {
         grp = root.addNode(grpNam + "_" + (i + 1));
       }
       oneServerQuery(node, grp);
     }
     root.setChildValue(grpNam + "_NUM", String.valueOf(i));
     return 0;
   }
 
   public void oneServerQuery(HiServerNode server, HiETF node)
     throws HiException
   {
     boolean existed = true;
     if ("EJB".equalsIgnoreCase(HiICSProperty.getProperty("framework")))
     {
       HiServiceLocator locator = HiServiceLocator.getInstance();
       if (!("JMS".equalsIgnoreCase(server.getType()))) {
         try {
           locator.lookup("ibs/ejb/" + server.getName());
         } catch (Exception e2) {
           existed = false;
         }
       }
     }
     HiServiceObject service = null;
     try {
       service = HiRegisterService.getService(server.getName());
     }
     catch (Exception e) {
     }
     if (service != null) {
       node.setChildValue("SVR_NM", server.getName());
       node.setChildValue("GRP_NM", server.getGrpNam());
       node.setChildValue("SVR_TYP", service.getServerType());
       node.setChildValue("RUN_FLG", service.getRunning());
       node.setChildValue("LOG_LVL", service.getLogLevel());
       node.setChildValue("MON_SW", service.getMonSwitch());
       node.setChildValue("SVR_TM", service.getTime());
       node.setChildValue("SVR_DESC", server.getDesc());
     } else {
       node.setChildValue("SVR_NM", server.getName());
       node.setChildValue("GRP_NM", server.getGrpNam());
       node.setChildValue("SVR_TYP", "");
       node.setChildValue("RUN_FLG", "0");
       node.setChildValue("LOG_LVL", "0");
       node.setChildValue("MON_SW", "0");
       node.setChildValue("SVR_TM", "");
       node.setChildValue("SVR_DESC", server.getDesc());
     }
   }
 
   public int serverManage(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiSVRLSTParser parser = new HiSVRLSTParser();
     String type = HiArgUtils.getStringNotNull(args, "type");
     String mode = HiArgUtils.getStringNotNull(args, "mode");
     HiFrontTabNode frontTab = null;
     try {
       frontTab = parser.parser();
     } catch (Exception e) {
       throw new HiException("213319", this.SVRLSTCfg, e);
     }
     try
     {
       HiGroupNode group;
       int j;
       if (StringUtils.equals(type, "a")) {
         for (int i = 0; i < frontTab.size(); ++i) {
           group = frontTab.getGroup(i);
           for (j = 0; j < group.size(); ++j)
             oneServerManage(mode, group.getServer(j));
         }
       }
       else
       {
         String name;
         if (StringUtils.equals(type, "g")) {
           name = HiArgUtils.getStringNotNull(args, "name");
           group = frontTab.getGroup(name);
           for (j = 0; j < group.size(); ++j)
             oneServerManage(mode, group.getServer(j));
         }
         else if (StringUtils.equals(type, "s")) {
           name = HiArgUtils.getStringNotNull(args, "name");
           String[] tmps = name.split("\\|");
           for (int i = 0; i < tmps.length; ++i)
             oneServerManage(mode, frontTab.getServer(tmps[i]));
         }
       }
     } finally {
       HiMessageContext.setCurrentMessageContext(ctx);
     }
     return 0;
   }
 
   private void oneServerManage(String mode, HiServerNode server)
     throws HiException
   {
     if ("EJB".equalsIgnoreCase(HiICSProperty.getProperty("framework")))
       doOneServerManageForEJB(mode, server);
     else
       doOneServerManageForPOJO(mode, server);
   }
 
   private void doOneServerManageForEJB(String mode, HiServerNode server)
     throws HiException
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
       HiClientUtil.invoke(mode, server.getName(), server.getAppName(), server.getConfig_file(), server.getType());
     }
     catch (Exception e) {
       Logger log = HiLog.getErrorLogger("SYS.log");
       log.error("boot server:[" + server.getName() + "] failure", e);
       System.out.println("boot server:[" + server.getName() + "] failure");
 
       e.printStackTrace();
     }
   }
 
   private void doOneServerManageForPOJO(String mode, HiServerNode server) throws HiException
   {
     try {
       HiClientUtilPOJO.invoke(mode, server.getName(), server.getAppName(), server.getConfig_file(), server.getType());
     }
     catch (Exception e) {
       Logger log = HiLog.getErrorLogger("SYS.log");
       log.error("boot server:[" + server.getName() + "] failure", e);
       System.out.println("boot server:[" + server.getName() + "] failure");
 
       e.printStackTrace();
     }
   }
 
   private ArrayList getServerList(HiATLParam args, HiMessageContext ctx) throws HiException
   {
     String type = HiArgUtils.getStringNotNull(args, "type");
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     HiSVRLSTParser parser = new HiSVRLSTParser();
     HiFrontTabNode frontTab = null;
     try {
       frontTab = parser.parser();
     } catch (Exception e) {
       throw new HiException("213319", this.SVRLSTCfg, e);
     }
 
     ArrayList list = new ArrayList();
 
     if (StringUtils.equals(type, "a")) {
       for (int i = 0; i < frontTab.size(); ++i) {
         HiGroupNode group = frontTab.getGroup(i);
         for (int j = 0; j < group.size(); ++j)
           list.add(group.getServer(j));
       }
     }
     else
     {
       String name;
       String[] tmps;
       int i;
       if (StringUtils.equals(type, "g")) {
         name = HiArgUtils.getStringNotNull(args, "name");
         tmps = name.split("\\|");
         for (i = 0; i < tmps.length; ++i) {
           HiGroupNode group = frontTab.getGroup(tmps[i]);
           if (group == null) {
             log.info("group:[" + tmps[i] + "] not existed!");
           }
           else
             for (int j = 0; j < group.size(); ++j)
               list.add(group.getServer(j));
         }
       }
       else if (StringUtils.equals(type, "s")) {
         name = HiArgUtils.getStringNotNull(args, "name");
         tmps = name.split("\\|");
         for (i = 0; i < tmps.length; ++i) {
           HiServerNode server = frontTab.getServer(tmps[i]);
           if (server == null) {
             log.info("group:[" + tmps[i] + "] not existed!");
           }
           else
             list.add(server); 
         }
       }
     }
     return list;
   }
 }