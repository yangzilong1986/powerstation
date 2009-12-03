/*     */ package com.hisun.mng;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.parser.svrlst.HiFrontTabNode;
/*     */ import com.hisun.parser.svrlst.HiGroupNode;
/*     */ import com.hisun.parser.svrlst.HiSVRLSTParser;
/*     */ import com.hisun.parser.svrlst.HiServerNode;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import com.hisun.server.manage.servlet.HiClientUtil;
/*     */ import com.hisun.server.manage.servlet.HiClientUtilPOJO;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiServiceLocator;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiServerManage
/*     */ {
/*     */   private String SVRLSTCfg;
/*     */ 
/*     */   public HiServerManage()
/*     */   {
/*  35 */     this.SVRLSTCfg = HiICSProperty.getProperty("svrlst.config");
/*     */   }
/*     */ 
/*     */   public int serverGrpQuery(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  47 */     HiSVRLSTParser parser = new HiSVRLSTParser();
/*  48 */     String grpNam = args.get("grpNam");
/*  49 */     if (StringUtils.isBlank(grpNam)) {
/*  50 */       grpNam = "GRP";
/*     */     }
/*  52 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/*  53 */     HiFrontTabNode frontTab = null;
/*     */     try {
/*  55 */       frontTab = parser.parser();
/*     */     } catch (Exception e) {
/*  57 */       throw new HiException("213319", this.SVRLSTCfg, e);
/*     */     }
/*     */ 
/*  60 */     int k = 1;
/*  61 */     for (int i = 0; i < frontTab.size(); ++i) {
/*  62 */       HiGroupNode group = frontTab.getGroup(i);
/*  63 */       HiETF grp = root.addNode(grpNam + "_" + k);
/*  64 */       grp.setChildValue("NAME", group.getName());
/*  65 */       grp.setChildValue("DESC", group.getDesc());
/*  66 */       ++k;
/*     */     }
/*  68 */     root.setChildValue(grpNam + "_NUM", String.valueOf(k - 1));
/*  69 */     return 0;
/*     */   }
/*     */ 
/*     */   public int serverMonSwitch(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  85 */     String tmp = HiArgUtils.getStringNotNull(args, "flag");
/*  86 */     boolean flag = false;
/*  87 */     if ("1".equals(tmp)) {
/*  88 */       flag = true;
/*     */     }
/*     */ 
/*  91 */     ArrayList list = getServerList(args, ctx);
/*  92 */     for (int i = 0; i < list.size(); ++i) {
/*  93 */       HiServerNode node = (HiServerNode)list.get(i);
/*  94 */       HiRegisterService.setMonSwitch(node.getName(), flag);
/*     */     }
/*  96 */     return 0;
/*     */   }
/*     */ 
/*     */   public int serverLogSwitch(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 112 */     String logLvl = HiArgUtils.getStringNotNull(args, "flag");
/*     */ 
/* 114 */     ArrayList list = getServerList(args, ctx);
/* 115 */     for (int i = 0; i < list.size(); ++i) {
/* 116 */       HiServerNode node = (HiServerNode)list.get(i);
/* 117 */       HiRegisterService.setLogSwitch(node.getName(), logLvl);
/*     */     }
/*     */ 
/* 120 */     return 0;
/*     */   }
/*     */ 
/*     */   public int serverQuery(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 136 */     String grpNam = args.get("grpNam");
/* 137 */     if (StringUtils.isBlank(grpNam)) {
/* 138 */       grpNam = "GRP";
/*     */     }
/*     */ 
/* 141 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/*     */ 
/* 143 */     ArrayList list = getServerList(args, ctx);
/* 144 */     int i = 0;
/* 145 */     for (i = 0; i < list.size(); ++i) {
/* 146 */       HiServerNode node = (HiServerNode)list.get(i);
/* 147 */       HiETF grp = root.getChildNode(grpNam + "_" + (i + 1));
/* 148 */       if (grp == null) {
/* 149 */         grp = root.addNode(grpNam + "_" + (i + 1));
/*     */       }
/* 151 */       oneServerQuery(node, grp);
/*     */     }
/* 153 */     root.setChildValue(grpNam + "_NUM", String.valueOf(i));
/* 154 */     return 0;
/*     */   }
/*     */ 
/*     */   public void oneServerQuery(HiServerNode server, HiETF node)
/*     */     throws HiException
/*     */   {
/* 160 */     boolean existed = true;
/* 161 */     if ("EJB".equalsIgnoreCase(HiICSProperty.getProperty("framework")))
/*     */     {
/* 163 */       HiServiceLocator locator = HiServiceLocator.getInstance();
/* 164 */       if (!("JMS".equalsIgnoreCase(server.getType()))) {
/*     */         try {
/* 166 */           locator.lookup("ibs/ejb/" + server.getName());
/*     */         } catch (Exception e2) {
/* 168 */           existed = false;
/*     */         }
/*     */       }
/*     */     }
/* 172 */     HiServiceObject service = null;
/*     */     try {
/* 174 */       service = HiRegisterService.getService(server.getName());
/*     */     }
/*     */     catch (Exception e) {
/*     */     }
/* 178 */     if (service != null) {
/* 179 */       node.setChildValue("SVR_NM", server.getName());
/* 180 */       node.setChildValue("GRP_NM", server.getGrpNam());
/* 181 */       node.setChildValue("SVR_TYP", service.getServerType());
/* 182 */       node.setChildValue("RUN_FLG", service.getRunning());
/* 183 */       node.setChildValue("LOG_LVL", service.getLogLevel());
/* 184 */       node.setChildValue("MON_SW", service.getMonSwitch());
/* 185 */       node.setChildValue("SVR_TM", service.getTime());
/* 186 */       node.setChildValue("SVR_DESC", server.getDesc());
/*     */     } else {
/* 188 */       node.setChildValue("SVR_NM", server.getName());
/* 189 */       node.setChildValue("GRP_NM", server.getGrpNam());
/* 190 */       node.setChildValue("SVR_TYP", "");
/* 191 */       node.setChildValue("RUN_FLG", "0");
/* 192 */       node.setChildValue("LOG_LVL", "0");
/* 193 */       node.setChildValue("MON_SW", "0");
/* 194 */       node.setChildValue("SVR_TM", "");
/* 195 */       node.setChildValue("SVR_DESC", server.getDesc());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int serverManage(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 212 */     HiSVRLSTParser parser = new HiSVRLSTParser();
/* 213 */     String type = HiArgUtils.getStringNotNull(args, "type");
/* 214 */     String mode = HiArgUtils.getStringNotNull(args, "mode");
/* 215 */     HiFrontTabNode frontTab = null;
/*     */     try {
/* 217 */       frontTab = parser.parser();
/*     */     } catch (Exception e) {
/* 219 */       throw new HiException("213319", this.SVRLSTCfg, e);
/*     */     }
/*     */     try
/*     */     {
/*     */       HiGroupNode group;
/*     */       int j;
/* 222 */       if (StringUtils.equals(type, "a")) {
/* 223 */         for (int i = 0; i < frontTab.size(); ++i) {
/* 224 */           group = frontTab.getGroup(i);
/* 225 */           for (j = 0; j < group.size(); ++j)
/* 226 */             oneServerManage(mode, group.getServer(j));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*     */         String name;
/* 229 */         if (StringUtils.equals(type, "g")) {
/* 230 */           name = HiArgUtils.getStringNotNull(args, "name");
/* 231 */           group = frontTab.getGroup(name);
/* 232 */           for (j = 0; j < group.size(); ++j)
/* 233 */             oneServerManage(mode, group.getServer(j));
/*     */         }
/* 235 */         else if (StringUtils.equals(type, "s")) {
/* 236 */           name = HiArgUtils.getStringNotNull(args, "name");
/* 237 */           String[] tmps = name.split("\\|");
/* 238 */           for (int i = 0; i < tmps.length; ++i)
/* 239 */             oneServerManage(mode, frontTab.getServer(tmps[i]));
/*     */         }
/*     */       }
/*     */     } finally {
/* 243 */       HiMessageContext.setCurrentMessageContext(ctx);
/*     */     }
/* 245 */     return 0;
/*     */   }
/*     */ 
/*     */   private void oneServerManage(String mode, HiServerNode server)
/*     */     throws HiException
/*     */   {
/* 251 */     if ("EJB".equalsIgnoreCase(HiICSProperty.getProperty("framework")))
/* 252 */       doOneServerManageForEJB(mode, server);
/*     */     else
/* 254 */       doOneServerManageForPOJO(mode, server);
/*     */   }
/*     */ 
/*     */   private void doOneServerManageForEJB(String mode, HiServerNode server)
/*     */     throws HiException
/*     */   {
/* 261 */     boolean existed = true;
/* 262 */     HiServiceLocator locator = HiServiceLocator.getInstance();
/* 263 */     if (!("JMS".equalsIgnoreCase(server.getType()))) {
/*     */       try {
/* 265 */         locator.lookup("ibs/ejb/" + server.getName());
/*     */       } catch (Exception e2) {
/* 267 */         existed = false;
/*     */       }
/*     */     }
/*     */ 
/* 271 */     if (!(existed)) {
/* 272 */       Logger log = HiLog.getErrorLogger("SYS.log");
/* 273 */       log.error("[" + server.getName() + "] not deployed");
/* 274 */       System.out.println("[" + server.getName() + "] not deployed");
/* 275 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 279 */       HiClientUtil.invoke(mode, server.getName(), server.getAppName(), server.getConfig_file(), server.getType());
/*     */     }
/*     */     catch (Exception e) {
/* 282 */       Logger log = HiLog.getErrorLogger("SYS.log");
/* 283 */       log.error("boot server:[" + server.getName() + "] failure", e);
/* 284 */       System.out.println("boot server:[" + server.getName() + "] failure");
/*     */ 
/* 286 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doOneServerManageForPOJO(String mode, HiServerNode server) throws HiException
/*     */   {
/*     */     try {
/* 293 */       HiClientUtilPOJO.invoke(mode, server.getName(), server.getAppName(), server.getConfig_file(), server.getType());
/*     */     }
/*     */     catch (Exception e) {
/* 296 */       Logger log = HiLog.getErrorLogger("SYS.log");
/* 297 */       log.error("boot server:[" + server.getName() + "] failure", e);
/* 298 */       System.out.println("boot server:[" + server.getName() + "] failure");
/*     */ 
/* 300 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private ArrayList getServerList(HiATLParam args, HiMessageContext ctx) throws HiException
/*     */   {
/* 306 */     String type = HiArgUtils.getStringNotNull(args, "type");
/* 307 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/* 309 */     HiSVRLSTParser parser = new HiSVRLSTParser();
/* 310 */     HiFrontTabNode frontTab = null;
/*     */     try {
/* 312 */       frontTab = parser.parser();
/*     */     } catch (Exception e) {
/* 314 */       throw new HiException("213319", this.SVRLSTCfg, e);
/*     */     }
/*     */ 
/* 317 */     ArrayList list = new ArrayList();
/*     */ 
/* 319 */     if (StringUtils.equals(type, "a")) {
/* 320 */       for (int i = 0; i < frontTab.size(); ++i) {
/* 321 */         HiGroupNode group = frontTab.getGroup(i);
/* 322 */         for (int j = 0; j < group.size(); ++j)
/* 323 */           list.add(group.getServer(j));
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*     */       String name;
/*     */       String[] tmps;
/*     */       int i;
/* 326 */       if (StringUtils.equals(type, "g")) {
/* 327 */         name = HiArgUtils.getStringNotNull(args, "name");
/* 328 */         tmps = name.split("\\|");
/* 329 */         for (i = 0; i < tmps.length; ++i) {
/* 330 */           HiGroupNode group = frontTab.getGroup(tmps[i]);
/* 331 */           if (group == null) {
/* 332 */             log.info("group:[" + tmps[i] + "] not existed!");
/*     */           }
/*     */           else
/* 335 */             for (int j = 0; j < group.size(); ++j)
/* 336 */               list.add(group.getServer(j));
/*     */         }
/*     */       }
/* 339 */       else if (StringUtils.equals(type, "s")) {
/* 340 */         name = HiArgUtils.getStringNotNull(args, "name");
/* 341 */         tmps = name.split("\\|");
/* 342 */         for (i = 0; i < tmps.length; ++i) {
/* 343 */           HiServerNode server = frontTab.getServer(tmps[i]);
/* 344 */           if (server == null) {
/* 345 */             log.info("group:[" + tmps[i] + "] not existed!");
/*     */           }
/*     */           else
/* 348 */             list.add(server); 
/*     */         }
/*     */       }
/*     */     }
/* 351 */     return list;
/*     */   }
/*     */ }