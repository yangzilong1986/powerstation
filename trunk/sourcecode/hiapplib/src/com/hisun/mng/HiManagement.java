/*     */ package com.hisun.mng;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiManagement extends HiMngUtils
/*     */ {
/*     */   public int MonLogin(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  69 */     return 0;
/*     */   }
/*     */ 
/*     */   private int doServerMonControl(String onoff, String sip, String sport) {
/*  73 */     if (StringUtils.equals(onoff, "1")) {
/*  74 */       if (0 == addServerSendNode(sip, sport))
/*     */         break label44;
/*  76 */       return -1;
/*     */     }
/*  78 */     if (StringUtils.equals(onoff, "0")) {
/*  79 */       if (0 == delServerSendNode(sip, sport))
/*     */         break label44;
/*  81 */       return -1;
/*     */     }
/*     */ 
/*  85 */     return -1;
/*     */ 
/*  89 */     label44: return 0;
/*     */   }
/*     */ 
/*     */   private int delServerSendNode(String sip, String sport) {
/*  93 */     ipTable.remove(new HiMngUtils.IP_PORT(this, sip, sport));
/*  94 */     return 0;
/*     */   }
/*     */ 
/*     */   private int addServerSendNode(String sip, String sport) {
/*  98 */     ipTable.add(new HiMngUtils.IP_PORT(this, sip, sport));
/*  99 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doBootServer(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 110 */     HiMessage msg = ctx.getCurrentMsg();
/* 111 */     HiETF etf = msg.getETFBody();
/*     */ 
/* 113 */     Logger log = HiLog.getLogger(msg);
/* 114 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doHaltServer(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 125 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doOpenServerLog(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
/* 129 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doCloseServerLog(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
/* 133 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getServerInfo(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
/* 137 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doServerReload(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
/* 141 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getSystemConf(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
/* 145 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doSystemConf(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
/* 149 */     return 0;
/*     */   }
/*     */ 
/*     */   private int doServerControl(HiETF etf) {
/* 153 */     List list = etf.getChildNodes("server");
/*     */ 
/* 157 */     return 0;
/*     */   }
/*     */ }