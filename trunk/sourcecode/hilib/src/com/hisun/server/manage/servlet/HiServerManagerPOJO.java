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
/*     */ public class HiServerManagerPOJO
/*     */ {
/*  21 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*  23 */   private static Logger _log = HiLog.getLogger("SYS.trc");
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
/*  48 */     if (_log.isDebugEnabled()) {
/*  49 */       _log.debug(String.format("invoke:[%s][%s][%s]", new Object[] { args[0], args[1], args[2] }));
/*     */     }
/*     */ 
/*  53 */     int k = 0;
/*  54 */     for (k = 0; k < _modes.length; ++k) {
/*  55 */       if (_modes.equals(args[1])) {
/*     */         break;
/*     */       }
/*     */     }
/*  59 */     if (k > _modes.length) {
/*  60 */       throw new Exception("invalid mode:[" + args[1] + "]");
/*     */     }
/*     */ 
/*  67 */     HiSVRLSTParser parser = new HiSVRLSTParser();
/*  68 */     HiFrontTabNode frontTab = parser.parser();
/*  69 */     HiServerNode server = null;
/*  70 */     HiGroupNode group = null;
/*  71 */     boolean successed = true;
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
/* 171 */         ++failCount;
/* 172 */         successed = false;
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
/* 183 */     if (_log.isDebugEnabled()) {
/* 184 */       _log.debug(String.format("invoke server:[%s][%s]", new Object[] { mode, server.getName() }));
/*     */     }
/*     */ 
/* 187 */     boolean existed = false;
/* 188 */     HiServiceLocator locator = HiServiceLocator.getInstance();
/*     */ 
/* 190 */     if (StringUtils.equalsIgnoreCase(mode, "list")) {
/* 191 */       HiServiceObject service = null;
/*     */       try {
/* 193 */         service = HiRegisterService.getService(server.getName());
/*     */       } catch (Exception e) {
/*     */       }
/* 196 */       addMsg(buffer, server.getName(), 10);
/*     */ 
/* 198 */       if (service != null) {
/* 199 */         addMsg(buffer, server.getAppName(), 8);
/* 200 */         addMsg(buffer, server.getGrpNam(), 8);
/* 201 */         addMsg(buffer, getStatusMsg(service.getRunning()), 8);
/* 202 */         addMsg(buffer, service.getTime(), 16);
/*     */       } else {
/* 204 */         addMsg(buffer, server.getAppName(), 8);
/* 205 */         addMsg(buffer, server.getGrpNam(), 8);
/* 206 */         addMsg(buffer, "stoped", 8);
/* 207 */         addMsg(buffer, " ", 16);
/*     */       }
/*     */ 
/* 210 */       addMsg(buffer, server.getDesc(), 20);
/* 211 */       buffer.append(SystemUtils.LINE_SEPARATOR);
/*     */ 
/* 213 */       return true;
/*     */     }
/*     */     try
/*     */     {
/* 217 */       HiClientUtilPOJO.invoke(mode, server.getName(), server.getAppName(), server.getConfig_file(), server.getType());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 221 */       Logger log = HiLog.getErrorLogger("SYS.log");
/* 222 */       log.error("invoke server:[" + server.getName() + "] failure", e);
/* 223 */       return false;
/*     */     }
/*     */ 
/* 227 */     return true;
/*     */   }
/*     */ 
/*     */   public static String getStatusMsg(String status) {
/* 231 */     for (int i = 0; i < _statusMsg.length; ++i)
/* 232 */       if ((_statusMsg[i][0] == null) || (_statusMsg[i][0].equalsIgnoreCase(status)))
/*     */       {
/* 234 */         return _statusMsg[i][1];
/*     */       }
/* 236 */     return "";
/*     */   }
/*     */ 
/*     */   public static String getFlagMsg(String status) {
/* 240 */     for (int i = 0; i < _flagMsg.length; ++i)
/* 241 */       if ((_flagMsg[i][0] == null) || (_flagMsg[i][0].equalsIgnoreCase(status)))
/*     */       {
/* 243 */         return _flagMsg[i][1];
/*     */       }
/* 245 */     return "";
/*     */   }
/*     */ 
/*     */   public static StringBuffer addMsg(StringBuffer buffer, String data, int len) {
/* 249 */     buffer.append(StringUtils.rightPad(data, len, ' '));
/* 250 */     return buffer;
/*     */   }
/*     */ }