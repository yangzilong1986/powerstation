/*     */ package com.hisun.server.manage.servlet;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.parser.svrlst.HiFrontTabNode;
/*     */ import com.hisun.parser.svrlst.HiGroupNode;
/*     */ import com.hisun.parser.svrlst.HiSVRLSTParser;
/*     */ import com.hisun.parser.svrlst.HiServerNode;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import com.hisun.util.HiServiceLocator;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.SystemUtils;
/*     */ 
/*     */ public class HiServerManager
/*     */ {
/*  23 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*  25 */   private static final String[] _modes = { "start", "restart", "stop", "pause", "resume", "list" };
/*     */ 
/*  28 */   private static final String[][] _statusMsg = { { "0", "stoped" }, { "1", "started" }, { "2", "paused" }, { "3", "failed" }, { "4", "nodeploy" }, { null, "stoped" } };
/*     */ 
/*  32 */   private static final String[][] _flagMsg = { { "0", "open" }, { "1", "open" }, { "debug", "open" }, { "INFO", "open" }, { "ERROR", "close" }, { null, "close" } };
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  37 */     StringBuffer buffer = new StringBuffer();
/*  38 */     invoke(args, buffer);
/*  39 */     System.out.println(buffer);
/*     */   }
/*     */ 
/*     */   public static boolean invoke(String[] args, StringBuffer buffer)
/*     */     throws Exception
/*     */   {
/*     */     int j;
/*  44 */     if (args.length < 3) {
/*  45 */       throw new Exception("config_file {start|restart|stop|pause|resume|list}  {-s|-a|-g} name");
/*     */     }
/*     */ 
/*  49 */     int k = 0;
/*  50 */     for (k = 0; k < _modes.length; ++k) {
/*  51 */       if (_modes.equals(args[1])) {
/*     */         break;
/*     */       }
/*     */     }
/*  55 */     if (k > _modes.length) {
/*  56 */       throw new Exception("invalid mode:[" + args[1] + "]");
/*     */     }
/*     */ 
/*  63 */     HiSVRLSTParser parser = new HiSVRLSTParser();
/*  64 */     HiFrontTabNode frontTab = parser.parser();
/*  65 */     HiServerNode server = null;
/*  66 */     HiGroupNode group = null;
/*  67 */     boolean successed = true;
/*  68 */     if (StringUtils.equalsIgnoreCase(args[1], "list")) {
/*  69 */       buffer.append(sm.getString("HiServerManager.list.head"));
/*  70 */       buffer.append(SystemUtils.LINE_SEPARATOR);
/*     */     }
/*  72 */     int count = 0; int failCount = 0;
/*     */ 
/*  74 */     if (StringUtils.equalsIgnoreCase(args[1], "list"))
/*     */     {
/*     */       int i;
/*  75 */       buffer.append(sm.getString("HiServerManager.list.head"));
/*  76 */       buffer.append(SystemUtils.LINE_SEPARATOR);
/*  77 */       buffer.append("================================================================================");
/*  78 */       buffer.append(SystemUtils.LINE_SEPARATOR);
/*  79 */       ArrayList serverList = new ArrayList();
/*  80 */       if (StringUtils.equalsIgnoreCase(args[2], "-a")) {
/*  81 */         for (i = 0; i < frontTab.size(); ++i) {
/*  82 */           group = frontTab.getGroup(i);
/*  83 */           for (int j = 0; j < group.size(); ++j)
/*  84 */             serverList.add(group.getServer(j));
/*     */         }
/*     */       }
/*  87 */       else if (StringUtils.equalsIgnoreCase(args[2], "-g")) {
/*  88 */         group = frontTab.getGroup(args[3]);
/*  89 */         if (group == null) {
/*  90 */           throw new Exception("group:[" + args[3] + "] not existed!");
/*     */         }
/*  92 */         for (j = 0; j < group.size(); ++j)
/*  93 */           serverList.add(group.getServer(j));
/*     */       }
/*  95 */       else if (StringUtils.equalsIgnoreCase(args[2], "-s")) {
/*  96 */         server = frontTab.getServer(args[3]);
/*  97 */         if (server == null) {
/*  98 */           throw new Exception("server:[" + args[3] + "] not existed!");
/*     */         }
/* 100 */         serverList.add(server);
/*     */       } else {
/* 102 */         throw new Exception("invalid flag:[" + args[2] + "]");
/*     */       }
/* 104 */       for (j = 0; j < serverList.size(); ++j) {
/* 105 */         server = (HiServerNode)serverList.get(j);
/* 106 */         ++count;
/* 107 */         HiServiceObject service = null;
/*     */         try {
/* 109 */           service = HiRegisterService.getService(server.getName());
/*     */         } catch (Exception e) {
/*     */         }
/* 112 */         addMsg(buffer, server.getName(), 10);
/*     */ 
/* 114 */         if (service != null) {
/* 115 */           addMsg(buffer, server.getAppName(), 8);
/* 116 */           addMsg(buffer, server.getGrpNam(), 8);
/* 117 */           addMsg(buffer, getStatusMsg(service.getRunning()), 8);
/* 118 */           addMsg(buffer, service.getTime(), 16);
/*     */         } else {
/* 120 */           ++failCount;
/* 121 */           addMsg(buffer, server.getAppName(), 8);
/* 122 */           addMsg(buffer, server.getGrpNam(), 8);
/* 123 */           addMsg(buffer, "stoped", 8);
/* 124 */           addMsg(buffer, " ", 16);
/*     */         }
/*     */ 
/* 127 */         addMsg(buffer, server.getDesc(), 20);
/* 128 */         buffer.append(SystemUtils.LINE_SEPARATOR);
/*     */       }
/* 130 */       if (StringUtils.equalsIgnoreCase(args[1], "list")) {
/* 131 */         buffer.append("================================================================================");
/* 132 */         buffer.append(SystemUtils.LINE_SEPARATOR);
/* 133 */         buffer.append(sm.getString("HiServerManager.list.tail", Integer.valueOf(count), Integer.valueOf(count - failCount), Integer.valueOf(failCount)));
/* 134 */         buffer.append(SystemUtils.LINE_SEPARATOR);
/*     */       }
/* 136 */       return true;
/*     */     }
/*     */ 
/* 139 */     if (StringUtils.equalsIgnoreCase(args[2], "-a")) {
/* 140 */       for (int i = 0; i < frontTab.size(); ++i) {
/* 141 */         group = frontTab.getGroup(i);
/* 142 */         for (j = 0; j < group.size(); ++j) {
/* 143 */           ++count;
/* 144 */           server = group.getServer(j);
/* 145 */           if (!(invoke(args[1], server, buffer))) {
/* 146 */             ++failCount;
/* 147 */             successed = false;
/*     */           }
/*     */         }
/*     */       }
/* 151 */     } else if (StringUtils.equalsIgnoreCase(args[2], "-g")) {
/* 152 */       group = frontTab.getGroup(args[3]);
/* 153 */       if (group == null) {
/* 154 */         throw new Exception("group:[" + args[3] + "] not existed!");
/*     */       }
/* 156 */       for (int j = 0; j < group.size(); ++j) {
/* 157 */         ++count;
/* 158 */         server = group.getServer(j);
/* 159 */         if (!(invoke(args[1], server, buffer))) {
/* 160 */           ++failCount;
/* 161 */           successed = false;
/*     */         }
/*     */       }
/* 164 */     } else if (StringUtils.equalsIgnoreCase(args[2], "-s")) {
/* 165 */       server = frontTab.getServer(args[3]);
/* 166 */       ++count;
/* 167 */       if (server == null) {
/* 168 */         throw new Exception("server:[" + args[3] + "] not existed!");
/*     */       }
/* 170 */       if (!(invoke(args[1], server, buffer))) {
/* 171 */         successed = false;
/* 172 */         ++failCount;
/*     */       }
/*     */     } else {
/* 175 */       throw new Exception("invalid flag:[" + args[2] + "]");
/*     */     }
/*     */ 
/* 178 */     return successed;
/*     */   }
/*     */ 
/*     */   public static boolean invoke(String mode, HiServerNode server, StringBuffer buffer) throws Exception
/*     */   {
/* 183 */     boolean existed = false;
/* 184 */     HiServiceLocator locator = HiServiceLocator.getInstance();
/* 185 */     if (!("JMS".equalsIgnoreCase(server.getType())))
/*     */       try {
/* 187 */         locator.lookup("ibs/ejb/" + server.getName());
/* 188 */         existed = true;
/*     */       }
/*     */       catch (Exception e2) {
/*     */       }
/*     */     else existed = true;
/*     */ 
/* 195 */     if (StringUtils.equalsIgnoreCase(mode, "list")) {
/* 196 */       HiServiceObject service = null;
/*     */       try {
/* 198 */         service = HiRegisterService.getService(server.getName());
/*     */       } catch (Exception e) {
/*     */       }
/* 201 */       addMsg(buffer, server.getName(), 10);
/*     */ 
/* 203 */       if (service != null) {
/* 204 */         addMsg(buffer, server.getGrpNam(), 8);
/* 205 */         addMsg(buffer, server.getAppName(), 8);
/* 206 */         addMsg(buffer, getStatusMsg(service.getRunning()), 8);
/* 207 */         addMsg(buffer, service.getTime(), 16);
/*     */       } else {
/* 209 */         if (existed) {
/* 210 */           addMsg(buffer, server.getGrpNam(), 8);
/* 211 */           addMsg(buffer, server.getAppName(), 8);
/* 212 */           addMsg(buffer, "stoped", 8);
/*     */         } else {
/* 214 */           addMsg(buffer, " ", 8);
/* 215 */           addMsg(buffer, " ", 8);
/* 216 */           addMsg(buffer, "nodeploy", 8);
/*     */         }
/* 218 */         addMsg(buffer, " ", 16);
/*     */       }
/*     */ 
/* 221 */       addMsg(buffer, server.getDesc(), 20);
/* 222 */       buffer.append(SystemUtils.LINE_SEPARATOR);
/*     */ 
/* 224 */       return true;
/*     */     }
/*     */ 
/* 229 */     if (!(existed)) {
/* 230 */       Logger log = HiLog.getErrorLogger("SYS.log");
/* 231 */       log.error("[" + server.getName() + "] not deployed");
/* 232 */       return false;
/*     */     }
/*     */     try
/*     */     {
/* 236 */       HiClientUtil.invoke(mode, server.getName(), server.getAppName(), server.getConfig_file(), server.getType());
/*     */     }
/*     */     catch (Exception e) {
/* 239 */       Logger log = HiLog.getErrorLogger("SYS.log");
/* 240 */       log.error("invoke server:[" + server.getName() + "] failure", e);
/*     */ 
/* 243 */       return false;
/*     */     }
/* 245 */     return true;
/*     */   }
/*     */ 
/*     */   public static String getStatusMsg(String status) {
/* 249 */     for (int i = 0; i < _statusMsg.length; ++i)
/* 250 */       if ((_statusMsg[i][0] == null) || (_statusMsg[i][0].equalsIgnoreCase(status)))
/*     */       {
/* 252 */         return _statusMsg[i][1];
/*     */       }
/* 254 */     return "";
/*     */   }
/*     */ 
/*     */   public static String getFlagMsg(String status) {
/* 258 */     for (int i = 0; i < _flagMsg.length; ++i)
/* 259 */       if ((_flagMsg[i][0] == null) || (_flagMsg[i][0].equalsIgnoreCase(status)))
/*     */       {
/* 261 */         return _flagMsg[i][1];
/*     */       }
/* 263 */     return "";
/*     */   }
/*     */ 
/*     */   public static StringBuffer addMsg(StringBuffer buffer, String data, int len) {
/* 267 */     buffer.append(StringUtils.rightPad(data, len, ' '));
/* 268 */     return buffer;
/*     */   }
/*     */ }