/*     */ package com.hisun.dispatcher;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.register.HiBind;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiRouterOut
/*     */ {
/*  19 */   static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public void init()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void destroy()
/*     */   {
/*     */   }
/*     */ 
/*     */   public static HiMessage syncProcess(HiMessage message)
/*     */     throws HiException
/*     */   {
/*  37 */     HiMessage msg = null;
/*     */     try {
/*  39 */       msg = innerSyncProcess(message);
/*  40 */       HiMessage localHiMessage1 = msg;
/*     */ 
/*  45 */       return localHiMessage1;
/*     */     }
/*     */     finally
/*     */     {
/*  42 */       if (msg != null) {
/*  43 */         HiLog.close(msg);
/*     */       }
/*  45 */       HiLog.close(message);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static HiMessage innerSyncProcess(HiMessage message) throws HiException {
/*  50 */     Logger log = HiLog.getLogger(message);
/*     */ 
/*  52 */     if (log.isDebugEnabled()) {
/*  53 */       log.debug("HiRouterOut.syncProcess() - start");
/*     */     }
/*     */ 
/*  56 */     if (log.isDebugEnabled()) {
/*  57 */       log.debug(sm.getString("HiRouterOut.syncProcess", message));
/*     */     }
/*  59 */     timeoutCheck(message);
/*  60 */     deadloopCheck(message);
/*  61 */     HiMessage msg1 = doSyncProcess(message);
/*  62 */     timeoutCheck(msg1);
/*  63 */     if (log.isDebugEnabled()) {
/*  64 */       log.debug("HiRouterOut.syncProcess() - end");
/*     */     }
/*  66 */     return msg1;
/*     */   }
/*     */ 
/*     */   public static HiMessage asyncProcess(HiMessage message)
/*     */     throws HiException
/*     */   {
/*  77 */     HiMessage msg = null;
/*     */     try {
/*  79 */       msg = innerAsyncProcess(message);
/*  80 */       HiMessage localHiMessage1 = msg;
/*     */ 
/*  85 */       return localHiMessage1;
/*     */     }
/*     */     finally
/*     */     {
/*  82 */       if (msg != null) {
/*  83 */         HiLog.close(msg);
/*     */       }
/*  85 */       HiLog.close(message);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static HiMessage innerAsyncProcess(HiMessage message) throws HiException {
/*  90 */     Logger log = HiLog.getLogger(message);
/*     */ 
/*  92 */     if (log.isDebugEnabled()) {
/*  93 */       log.debug("HiRouterOut.asyncProcess() - start");
/*     */     }
/*     */ 
/*  96 */     if (log.isDebugEnabled()) {
/*  97 */       log.debug(sm.getString("HiRouterOut.asyncProcess", message));
/*     */     }
/*  99 */     timeoutCheck(message);
/* 100 */     deadloopCheck(message);
/* 101 */     HiMessage msg1 = doAsyncProcess(message);
/*     */ 
/* 103 */     if (log.isDebugEnabled()) {
/* 104 */       log.debug("HiRouterOut.asyncProcess() - end");
/*     */     }
/* 106 */     return msg1;
/*     */   }
/*     */ 
/*     */   public static void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 118 */       innerProcess(ctx);
/*     */     } finally {
/* 120 */       if (ctx.getCurrentMsg() != null)
/* 121 */         HiLog.close(ctx.getCurrentMsg());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void innerProcess(HiMessageContext ctx) throws HiException
/*     */   {
/* 127 */     HiMessage message = ctx.getCurrentMsg();
/* 128 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/* 130 */     if (log.isDebugEnabled()) {
/* 131 */       log.debug("HiRouterOut.process() - start");
/*     */     }
/*     */ 
/* 134 */     if (log.isDebugEnabled())
/* 135 */       log.debug(sm.getString("HiRouterOut.process", message));
/* 136 */     HiMessage msg1 = null;
/* 137 */     if (isSyncMessage(message))
/* 138 */       msg1 = innerSyncProcess(message);
/*     */     else {
/* 140 */       msg1 = innerAsyncProcess(message);
/*     */     }
/* 142 */     if (StringUtils.equals(msg1.getStatus(), "E")) {
/* 143 */       throw new HiException(msg1.getHeadItem("SSC"));
/*     */     }
/* 145 */     ctx.setCurrentMsg(msg1);
/* 146 */     if (log.isDebugEnabled())
/* 147 */       log.debug("HiRouterOut.process() - end");
/*     */   }
/*     */ 
/*     */   private static boolean isSyncMessage(HiMessage message)
/*     */   {
/* 159 */     return (!(StringUtils.equalsIgnoreCase(message.getHeadItem("SYN"), "N")));
/*     */   }
/*     */ 
/*     */   private static void timeoutCheck(HiMessage message)
/*     */     throws HiException
/*     */   {
/* 169 */     Logger log = HiLog.getLogger(message);
/* 170 */     if (log.isDebugEnabled()) {
/* 171 */       log.debug("HiRouterOut.timeoutCheck() - start");
/*     */     }
/*     */ 
/* 174 */     Object o = message.getObjectHeadItem("ETM");
/* 175 */     if (!(o instanceof Long)) {
/* 176 */       if (log.isDebugEnabled()) {
/* 177 */         log.debug("HiRouterOut.timeoutCheck() - end");
/*     */       }
/* 179 */       return;
/*     */     }
/* 181 */     long etm = ((Long)o).longValue();
/* 182 */     if ((etm > 0L) && (System.currentTimeMillis() > etm)) {
/* 183 */       throw new HiException("212001");
/*     */     }
/*     */ 
/* 186 */     if (log.isDebugEnabled())
/* 187 */       log.debug("HiRouterOut.timeoutCheck() - end");
/*     */   }
/*     */ 
/*     */   private static void deadloopCheck(HiMessage message)
/*     */     throws HiException
/*     */   {
/* 198 */     if (message.getHeadItemValSize("STC") > 5000)
/* 199 */       throw new HiException("212002");
/*     */   }
/*     */ 
/*     */   private static String getService(HiMessage message)
/*     */     throws HiException
/*     */   {
/* 211 */     Logger log = HiLog.getLogger(message);
/* 212 */     if (log.isDebugEnabled()) {
/* 213 */       log.debug("HiRouterOut.getService() - start");
/*     */     }
/*     */ 
/* 216 */     String service = null;
/*     */ 
/* 218 */     service = message.getHeadItem("SRN");
/* 219 */     String rqType = message.getHeadItem("SCH");
/* 220 */     if (StringUtils.equals(rqType, "rq"))
/*     */     {
/* 224 */       HiContext ctx = HiContext.getCurrentContext();
/* 225 */       String regionName = null;
/* 226 */       if (ctx != null) {
/* 227 */         regionName = ctx.getStrProp("@PARA", "_REGION_NAME");
/*     */       }
/*     */ 
/* 231 */       service = message.getHeadItem("SRN");
/* 232 */       if ((StringUtils.isNotBlank(service)) && (!(StringUtils.equals(service, regionName))))
/*     */       {
/* 234 */         service = "S.CONSVR";
/* 235 */         return service;
/*     */       }
/*     */ 
/* 238 */       service = message.getHeadItem("SDT");
/* 239 */       if (StringUtils.isNotBlank(service)) {
/* 240 */         return service;
/*     */       }
/*     */ 
/* 243 */       service = message.getHeadItem("STC");
/* 244 */       if (StringUtils.isNotBlank(service))
/* 245 */         return service;
/*     */     }
/*     */     else {
/* 248 */       service = message.getHeadItem("SRT");
/* 249 */       if (StringUtils.isNotBlank(service)) {
/* 250 */         return service;
/*     */       }
/*     */     }
/* 253 */     throw new HiException("212003");
/*     */   }
/*     */ 
/*     */   private static HiMessage doSyncProcess(HiMessage message)
/*     */     throws HiException
/*     */   {
/* 264 */     Logger log = HiLog.getLogger(message);
/* 265 */     if (log.isDebugEnabled()) {
/* 266 */       log.debug("HiRouterOut.syncProcess() - start");
/*     */     }
/*     */ 
/* 269 */     String name = getService(message);
/* 270 */     HiServiceObject serviceObject = HiRegisterService.getService(name);
/* 271 */     if (!(serviceObject.isRunning())) {
/* 272 */       throw new HiException("212103", name);
/*     */     }
/*     */ 
/* 275 */     if (log.isDebugEnabled())
/* 276 */       log.debug(sm.getString("HiRouterOut.syncProcess", serviceObject));
/* 277 */     HiBind bind = serviceObject.getBind();
/* 278 */     HiMessage msg1 = null;
/* 279 */     HiMessageContext ctx = HiMessageContext.getCurrentMessageContext();
/*     */ 
/* 281 */     String rqType = message.getHeadItem("SCH");
/* 282 */     if (StringUtils.equals(rqType, "rq")) {
/* 283 */       HiContext parent = ctx.getServerContext();
/* 284 */       if ((parent != null) && 
/* 285 */         (parent.containsProperty("SVR.name"))) {
/* 286 */         message.addHeadItem("SRT", parent.getStrProp("SVR.name"));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 293 */       msg1 = bind.process(message);
/*     */     } finally {
/* 295 */       if (msg1 != null) {
/* 296 */         rqType = msg1.getHeadItem("SCH");
/* 297 */         if (StringUtils.equals(rqType, "rp")) {
/* 298 */           msg1.delHeadItemVal("SRT");
/*     */         }
/*     */       }
/* 301 */       HiMessageContext.setCurrentMessageContext(ctx);
/*     */     }
/*     */ 
/* 304 */     if (log.isDebugEnabled()) {
/* 305 */       log.debug("HiRouterOut.syncProcess() - end");
/*     */     }
/* 307 */     return msg1;
/*     */   }
/*     */ 
/*     */   private static HiMessage doAsyncProcess(HiMessage message)
/*     */     throws HiException
/*     */   {
/* 318 */     Logger log = HiLog.getLogger(message);
/* 319 */     if (log.isDebugEnabled()) {
/* 320 */       log.debug("HiRouterOut.asyncProcess() - start");
/*     */     }
/*     */ 
/* 323 */     if (log.isDebugEnabled())
/* 324 */       log.debug(sm.getString("HiRouterOut.asyncProcess"));
/* 325 */     message.delHeadItem("SYN");
/* 326 */     HiJMSProcess.sendMessage(message);
/*     */ 
/* 328 */     if (log.isDebugEnabled()) {
/* 329 */       log.debug("HiRouterOut.asyncProcess() - end");
/*     */     }
/* 331 */     return message;
/*     */   }
/*     */ }