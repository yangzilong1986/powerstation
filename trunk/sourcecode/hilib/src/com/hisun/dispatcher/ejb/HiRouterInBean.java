/*     */ package com.hisun.dispatcher.ejb;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.startup.HiStartup;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.PrintStream;
/*     */ import java.rmi.RemoteException;
/*     */ import javax.ejb.CreateException;
/*     */ import javax.ejb.EJBException;
/*     */ import javax.ejb.SessionBean;
/*     */ import javax.ejb.SessionContext;
/*     */ import javax.naming.InitialContext;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiRouterInBean
/*     */   implements SessionBean
/*     */ {
/*     */   private static final long serialVersionUID = 9008942480923232951L;
/*  41 */   private HiStartup startup = null;
/*  42 */   private static Logger log = HiLog.getErrorLogger("SYS.log");
/*  43 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public void ejbCreate()
/*     */     throws CreateException
/*     */   {
/*  49 */     initialize();
/*     */   }
/*     */ 
/*     */   public void ejbActivate()
/*     */     throws EJBException, RemoteException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void ejbPassivate()
/*     */     throws EJBException, RemoteException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void ejbRemove() throws EJBException, RemoteException
/*     */   {
/*  64 */     if (this.startup == null)
/*  65 */       return;
/*     */     try {
/*  67 */       this.startup.setFailured(false);
/*  68 */       this.startup.destory();
/*     */     } catch (HiException e) {
/*  70 */       System.out.println(sm.getString("HiRouterInBean.remove00", this.startup.getServerName()));
/*     */     }
/*  72 */     this.startup = null;
/*     */   }
/*     */ 
/*     */   public void setSessionContext(SessionContext newContext)
/*     */     throws EJBException
/*     */   {
/*     */   }
/*     */ 
/*     */   public HiMessage process(HiMessage message)
/*     */   {
/* 100 */     if (this.startup == null) {
/* 101 */       message.setStatus("E");
/* 102 */       message.setHeadItem("SSC", "212010");
/* 103 */       return message;
/*     */     }
/*     */     try
/*     */     {
/* 107 */       return this.startup.process(message);
/*     */     } catch (Throwable e) {
/* 109 */       message.setStatus("E");
/* 110 */       HiLog.logServiceError(message, HiException.makeException(e));
/* 111 */       if (e instanceof HiException)
/* 112 */         message.setHeadItem("SSC", ((HiException)e).getCode());
/*     */       else
/* 114 */         message.setHeadItem("SSC", "212012"); 
/*     */     }
/* 115 */     return message;
/*     */   }
/*     */ 
/*     */   public HiMessage manage(HiMessage message)
/*     */     throws EJBException
/*     */   {
/* 127 */     if (this.startup == null) {
/* 128 */       message.setStatus("E");
/* 129 */       message.setHeadItem("SSC", "211007");
/* 130 */       message.setHeadItem("SEM", sm.getString("211007"));
/* 131 */       return message;
/*     */     }
/*     */     try
/*     */     {
/* 135 */       return this.startup.manage(message);
/*     */     } catch (Throwable e) {
/* 137 */       message.setStatus("E");
/* 138 */       if (e instanceof HiException) {
/* 139 */         message.setHeadItem("SSC", ((HiException)e).getCode());
/* 140 */         message.setHeadItem("SEM", ((HiException)e).getAppMessage());
/*     */       }
/*     */       else {
/* 143 */         message.setHeadItem("SSC", "211007");
/* 144 */         message.setHeadItem("SEM", sm.getString("211007"));
/*     */       }
/* 146 */       HiLog.logSysError("initializ00", e); }
/* 147 */     return message;
/*     */   }
/*     */ 
/*     */   private void initialize()
/*     */   {
/* 152 */     String serverName = null;
/*     */     try {
/* 154 */       InitialContext ic = new InitialContext();
/* 155 */       serverName = (String)ic.lookup("java:comp/env/ServerName");
/*     */ 
/* 157 */       if (StringUtils.isEmpty(serverName)) {
/* 158 */         System.out.println(sm.getString("HiRouterInBean.initialize00"));
/* 159 */         log.error(sm.getString("HiRouterInBean.initialize00"));
/* 160 */         return;
/*     */       }
/* 162 */       this.startup = HiStartup.getInstance(serverName);
/* 163 */       HiStartup.initialize(serverName);
/*     */     } catch (Throwable e) {
/* 165 */       System.out.println(sm.getString("HiRouterInBean.initialize01", serverName));
/* 166 */       HiLog.logSysError(sm.getString("HiRouterInBean.initialize01", serverName), e);
/*     */     }
/*     */   }
/*     */ }