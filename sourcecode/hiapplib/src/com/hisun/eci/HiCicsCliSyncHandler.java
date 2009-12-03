/*     */ package com.hisun.eci;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiCicsCliSyncHandler
/*     */   implements IHandler, IServerInitListener
/*     */ {
/*  22 */   private static HiStringManager _sm = HiStringManager.getManager();
/*     */   private String systemName;
/*     */   private String userName;
/*     */   private String password;
/*     */   private int preLen;
/*     */   private int timeOut;
/*     */   private String progId;
/*     */   private int bufferLen;
/*     */   CicsCliSyncJni cicsCli;
/*     */   final Logger log;
/*     */   final HiStringManager sm;
/*     */ 
/*     */   public HiCicsCliSyncHandler()
/*     */   {
/*  24 */     this.systemName = "";
/*  25 */     this.userName = "";
/*  26 */     this.password = "";
/*  27 */     this.preLen = 4;
/*     */ 
/*  31 */     this.timeOut = 30;
/*  32 */     this.progId = "";
/*     */ 
/*  37 */     this.bufferLen = 10241;
/*     */ 
/*  39 */     this.cicsCli = new CicsCliSyncJni();
/*     */ 
/*  41 */     this.log = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*     */ 
/*  44 */     this.sm = HiStringManager.getManager(); }
/*     */ 
/*     */   public String getSystemName() {
/*  47 */     return this.systemName;
/*     */   }
/*     */ 
/*     */   public void setSystemName(String systemName) {
/*  51 */     this.systemName = systemName;
/*     */   }
/*     */ 
/*     */   public String getUserName() {
/*  55 */     return this.userName;
/*     */   }
/*     */ 
/*     */   public void setUserName(String userName) {
/*  59 */     this.userName = userName;
/*     */   }
/*     */ 
/*     */   public String getPassword() {
/*  63 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String password) {
/*  67 */     this.password = password;
/*     */   }
/*     */ 
/*     */   public int getPreLen() {
/*  71 */     return this.preLen;
/*     */   }
/*     */ 
/*     */   public void setPreLen(String preLen) {
/*  75 */     this.preLen = Integer.parseInt(preLen);
/*     */   }
/*     */ 
/*     */   public void setBufferLen(String bufferLen) {
/*  79 */     this.preLen = Integer.parseInt(bufferLen);
/*     */   }
/*     */ 
/*     */   public String getProgId() {
/*  83 */     return this.progId;
/*     */   }
/*     */ 
/*     */   public void setProgId(String progId) {
/*  87 */     this.progId = progId;
/*     */   }
/*     */ 
/*     */   public int getTimeOut() {
/*  91 */     return this.timeOut;
/*     */   }
/*     */ 
/*     */   public void setTimeOut(String timeOut) {
/*  95 */     this.timeOut = Integer.parseInt(timeOut);
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/*  99 */     if (this.log.isDebugEnabled()) {
/* 100 */       this.log.debug("HiCicsCliSyncHandler process:start");
/*     */     }
/*     */ 
/* 103 */     HiMessage msg = ctx.getCurrentMsg();
/* 104 */     HiByteBuffer plainBytes = (HiByteBuffer)msg.getBody();
/*     */ 
/* 106 */     String pid = msg.getHeadItemRoot("PID");
/* 107 */     if (StringUtils.isNotBlank(pid))
/*     */     {
/* 109 */       this.cicsCli.setProgId(pid);
/*     */     }
/* 111 */     if (this.log.isInfoEnabled())
/*     */     {
/* 113 */       this.log.info(_sm.getString("ibs.send", msg.getRequestId(), this.cicsCli.getProgId(), String.valueOf(plainBytes.length()), plainBytes.toString()));
/*     */     }
/* 115 */     byte[] recvBuffer = new byte[this.bufferLen];
/* 116 */     int ret = this.cicsCli.clientSyncOut(plainBytes.getBytes(), recvBuffer);
/*     */ 
/* 118 */     switch (ret)
/*     */     {
/*     */     case -1:
/* 120 */       throw new HiException("231207", "ECI");
/*     */     case -2:
/* 122 */       throw new HiException("231205", "ECI");
/*     */     }
/*     */ 
/* 125 */     if (ret > 0)
/*     */     {
/* 127 */       msg.setHeadItem("SSC", "000000");
/* 128 */       HiByteBuffer result = new HiByteBuffer(ret);
/* 129 */       result.append(recvBuffer, 0, ret);
/* 130 */       msg.setHeadItem("SCH", "rp");
/* 131 */       msg.setBody(result);
/*     */ 
/* 133 */       if (this.log.isInfoEnabled()) {
/* 134 */         this.log.info(_sm.getString("ibs.recv", msg.getRequestId(), this.cicsCli.getProgId(), String.valueOf(ret), result.toString()));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 139 */     if (this.log.isDebugEnabled())
/* 140 */       this.log.debug("HiCicsCliSyncHandler process:end");
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/* 146 */     if (this.log.isDebugEnabled()) {
/* 147 */       this.log.debug("HiCicsCliSyncHandler serverInit:start");
/*     */     }
/*     */ 
/* 150 */     if (this.log.isInfoEnabled())
/*     */     {
/* 152 */       this.log.info("SystemName[" + this.systemName + "] userName[" + this.userName + "],password[" + this.password + "]");
/*     */     }
/* 154 */     this.cicsCli.init(this.systemName, this.userName, this.password, this.progId, this.timeOut);
/*     */ 
/* 156 */     if (this.log.isDebugEnabled())
/* 157 */       this.log.debug("HiCicsCliSyncHandler serverInit:end");
/*     */   }
/*     */ }