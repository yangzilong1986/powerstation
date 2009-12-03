/*     */ package com.hisun.dispatcher.ejb;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.startup.HiStartup;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.PrintStream;
/*     */ import javax.ejb.EJBException;
/*     */ import javax.ejb.MessageDrivenBean;
/*     */ import javax.ejb.MessageDrivenContext;
/*     */ import javax.jms.Message;
/*     */ import javax.jms.MessageListener;
/*     */ import javax.jms.ObjectMessage;
/*     */ import javax.naming.InitialContext;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiASyncProcess
/*     */   implements MessageDrivenBean, MessageListener
/*     */ {
/*  36 */   private static boolean started = false;
/*  37 */   private static Object lock = new Object();
/*     */   private MessageDrivenContext context;
/*  41 */   private static Logger log = HiLog.getErrorLogger("SYS.log");
/*  42 */   private HiStartup startup = null;
/*     */ 
/*  44 */   private String serverName = null;
/*     */ 
/*  46 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public void ejbRemove()
/*     */     throws EJBException
/*     */   {
/*  55 */     if (this.startup == null)
/*  56 */       return;
/*     */     try {
/*  58 */       this.startup.setFailured(false);
/*  59 */       this.startup.destory();
/*     */     } catch (HiException e) {
/*  61 */       System.out.println(sm.getString("HiRouterInBean.remove00", this.startup.getServerName()));
/*     */     }
/*     */ 
/*  64 */     this.startup = null;
/*     */   }
/*     */ 
/*     */   public void setMessageDrivenContext(MessageDrivenContext newContext)
/*     */     throws EJBException
/*     */   {
/*  85 */     this.context = newContext;
/*     */   }
/*     */ 
/*     */   public void onMessage(Message arg0)
/*     */   {
/* 127 */     Logger msgLog = null;
/*     */     try {
/* 129 */       HiMessage message = (HiMessage)((ObjectMessage)arg0).getObject();
/* 130 */       if (message == null) {
/* 131 */         System.out.println("throw out invalid message");
/* 132 */         return;
/*     */       }
/*     */ 
/* 135 */       msgLog = HiLog.getLogger(message);
/* 136 */       if (msgLog.isDebugEnabled()) {
/* 137 */         msgLog.debug("HiASyncProcess.onMessage start");
/*     */       }
/* 139 */       if (msgLog.isDebugEnabled()) {
/* 140 */         msgLog.debug(sm.getString("HiASyncProcess.onMessage", message));
/*     */       }
/*     */ 
/* 143 */       String cmd = message.getHeadItem("CMD");
/* 144 */       if (!(StringUtils.isEmpty(cmd)))
/* 145 */         manage(message);
/*     */       else
/* 147 */         process(message);
/*     */     }
/*     */     catch (Throwable e) {
/* 150 */       if (e instanceof HiException) {
/* 151 */         String msgCode = ((HiException)e).getCode();
/* 152 */         if ((StringUtils.equals(msgCode, "212010")) || (StringUtils.equals(msgCode, "212101")))
/*     */         {
/* 157 */           log.error(e, e);
/*     */ 
/* 159 */           Thread.yield();
/*     */           try {
/* 161 */             Thread.currentThread(); Thread.sleep(1000L);
/*     */           } catch (InterruptedException e1) {
/* 163 */             log.error(e1, e1);
/*     */           }
/* 165 */           throw new RuntimeException("Server not start", e);
/*     */         }
/*     */       }
/* 168 */       log.error(e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiMessage process(HiMessage message)
/*     */     throws HiException
/*     */   {
/* 184 */     if (this.startup == null) {
/* 185 */       message.setStatus("E");
/* 186 */       message.setHeadItem("SSC", "212010");
/*     */ 
/* 188 */       return message;
/*     */     }
/*     */ 
/* 192 */     return this.startup.process(message);
/*     */   }
/*     */ 
/*     */   public HiMessage manage(HiMessage message)
/*     */     throws EJBException
/*     */   {
/* 214 */     if (this.startup == null) {
/* 215 */       message.setStatus("E");
/* 216 */       message.setHeadItem("SSC", "211007");
/* 217 */       message.setHeadItem("SEM", sm.getString("211007"));
/* 218 */       return message;
/*     */     }
/*     */     try
/*     */     {
/* 222 */       return this.startup.manage(message);
/*     */     } catch (Throwable e) {
/* 224 */       message.setStatus("E");
/* 225 */       if (e instanceof HiException) {
/* 226 */         message.setHeadItem("SSC", ((HiException)e).getCode());
/* 227 */         message.setHeadItem("SEM", ((HiException)e).getAppMessage());
/*     */       } else {
/* 229 */         message.setHeadItem("SSC", "211007");
/* 230 */         message.setHeadItem("SEM", sm.getString("211007"));
/*     */       }
/*     */ 
/* 233 */       HiLog.logSysError("initializ00", e); }
/* 234 */     return message;
/*     */   }
/*     */ 
/*     */   public void ejbCreate()
/*     */   {
/* 247 */     initialize();
/*     */   }
/*     */ 
/*     */   private synchronized void initialize() {
/*     */     try {
/* 252 */       InitialContext ic = new InitialContext();
/* 253 */       this.serverName = ((String)ic.lookup("java:comp/env/ServerName"));
/*     */ 
/* 255 */       if (StringUtils.isEmpty(this.serverName)) {
/* 256 */         System.out.println(sm.getString("HiASyncProcess.initialize00"));
/*     */ 
/* 258 */         log.error(sm.getString("HiASyncProcess.initialize00"));
/* 259 */         return;
/*     */       }
/* 261 */       this.startup = HiStartup.getInstance(this.serverName);
/* 262 */       synchronized (lock) {
/* 263 */         if (!(started)) {
/* 264 */           System.out.println("ejbCreate");
/* 265 */           this.startup = HiStartup.initialize(this.serverName, true);
/* 266 */           started = true;
/*     */         } else {
/* 268 */           this.startup = HiStartup.initialize(this.serverName);
/*     */         }
/*     */       }
/*     */     } catch (Throwable t) {
/* 272 */       HiLog.logSysError(sm.getString("HiASyncProcess.initialize01", this.serverName), t);
/*     */ 
/* 276 */       System.out.println(sm.getString("HiASyncProcess.initialize01", this.serverName));
/*     */     }
/*     */   }
/*     */ }