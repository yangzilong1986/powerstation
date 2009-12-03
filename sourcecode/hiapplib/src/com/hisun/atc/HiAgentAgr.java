/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiAtcLib;
/*     */ import com.hisun.atc.common.HiDbtSqlHelper;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringUtils;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiAgentAgr
/*     */ {
/*     */   public int InsertAgentAgreement(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  43 */     HiAtcLib.insertTable("PUBCRPAGT", ctx);
/*     */ 
/*  45 */     return 0;
/*     */   }
/*     */ 
/*     */   public int GetAgentAgreement(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  63 */     HiMessage mess = ctx.getCurrentMsg();
/*  64 */     Logger log = HiLog.getLogger(mess);
/*  65 */     if ((mess == null) || (ctx == null)) {
/*  66 */       throw new HiException("220026", "[HiMessage][HiMessageContext] IS NULL");
/*     */     }
/*  68 */     HiETF etfRoot = (HiETF)mess.getBody();
/*  69 */     StringBuffer sqlcmd = new StringBuffer();
/*     */ 
/*  71 */     String argVal = argsMap.get("CndSts");
/*     */ 
/*  73 */     if (!(StringUtils.isEmpty(argVal)))
/*     */     {
/*     */       try
/*     */       {
/*  78 */         String CndSts = HiDbtSqlHelper.getDynSentence(ctx, argVal);
/*     */ 
/*  83 */         sqlcmd = new StringBuffer("SELECT * FROM PUBCRPAGT WHERE " + CndSts);
/*     */       }
/*     */       catch (HiException e) {
/*  86 */         throw e;
/*     */       }
/*     */     }
/*     */ 
/*  90 */     String busTyp = etfRoot.getChildValue("BUSTYP");
/*  91 */     String crpCod = etfRoot.getChildValue("CRPCOD");
/*  92 */     String brNo = etfRoot.getChildValue("BRNO");
/*  93 */     String crpSub = etfRoot.getChildValue("CRPSUB");
/*     */ 
/*  95 */     if ((busTyp == null) || (crpCod == null) || (brNo == null)) {
/*  96 */       log.error("没有键值![BUSTYP][CRPCOD][BRNO]");
/*  97 */       throw new HiException("220058", "[BUSTYP][CRPCOD][BRNO]");
/*     */     }
/*  99 */     if (crpSub == null) {
/* 100 */       crpSub = "";
/*     */     }
/*     */ 
/* 103 */     sqlcmd = new StringBuffer(HiStringUtils.format("SELECT * FROM PUBCRPAGT WHERE BRNO='%s' AND BUSTYP='%s' AND CRPCOD='%s' AND (CRPSUB='%s' OR CRPSUB=' ')", brNo, busTyp, crpCod, crpSub));
/*     */     try
/*     */     {
/* 109 */       List list = ctx.getDataBaseUtil().execQuery(sqlcmd.toString());
/* 110 */       if ((list != null) && (list.size() == 1)) {
/* 111 */         HashMap resMap = (HashMap)list.get(0);
/* 112 */         Iterator iter = resMap.keySet().iterator();
/* 113 */         while (iter.hasNext()) {
/* 114 */           String key = (String)iter.next();
/* 115 */           String val = (String)resMap.get(key);
/* 116 */           if (!(StringUtils.isEmpty(val)))
/* 117 */             etfRoot.setChildValue(key, val);
/*     */         }
/*     */       }
/*     */       else {
/* 121 */         if (list.size() == 0) {
/* 122 */           throw new HiAppException(-100, "220040", "GetAgentAgreement,数据库无此记录");
/*     */         }
/*     */ 
/* 125 */         throw new HiException("220039", "GetAgentAgreement,数据库操作错误");
/*     */       }
/*     */     }
/*     */     catch (HiException e) {
/* 129 */       throw e;
/*     */     }
/*     */ 
/* 132 */     return 0;
/*     */   }
/*     */ 
/*     */   public int UpdateAgentAgreement(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 149 */     HiMessage mess = ctx.getCurrentMsg();
/* 150 */     if ((mess == null) || (ctx == null)) {
/* 151 */       throw new HiException("220026", "[HiMessage][HiMessageContext]IS NULL");
/*     */     }
/*     */ 
/* 154 */     StringBuffer sqlcmd = new StringBuffer();
/* 155 */     HiETF etfRoot = (HiETF)mess.getBody();
/* 156 */     if (etfRoot == null) {
/* 157 */       throw new HiException("220026", "ETF is NULL");
/*     */     }
/* 159 */     String argVal = argsMap.get("CndSts");
/*     */ 
/* 161 */     if (StringUtils.isEmpty(argVal)) {
/* 162 */       String busTyp = etfRoot.getChildValue("BUSTYP");
/* 163 */       String crpCod = etfRoot.getChildValue("CRPCOD");
/* 164 */       String brNo = etfRoot.getChildValue("BRNO");
/* 165 */       String crpSub = etfRoot.getChildValue("CRPSUB");
/*     */ 
/* 167 */       if ((busTyp == null) || (crpCod == null) || (brNo == null)) {
/* 168 */         throw new HiException("220026", "没有键值![BUSTYP][CRPCOD][BRNO]");
/*     */       }
/* 170 */       if (crpSub == null) {
/* 171 */         crpSub = "";
/*     */       }
/*     */ 
/* 174 */       sqlcmd = new StringBuffer(HiStringUtils.format("BRNO='%s' AND BUSTYP='%s' AND CRPCOD='%s' AND (CRPSUB='%s' OR CRPSUB=' ')", brNo, busTyp, crpCod, crpSub));
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 183 */         String CndSts = HiDbtSqlHelper.getDynSentence(ctx, argVal);
/*     */ 
/* 187 */         sqlcmd = new StringBuffer(CndSts);
/*     */       }
/*     */       catch (HiException e) {
/* 190 */         throw e;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 196 */     String sql = HiAtcLib.updateFormat("PUBCRPAGT", sqlcmd.toString(), etfRoot, ctx);
/*     */     try {
/* 198 */       int row = ctx.getDataBaseUtil().execUpdate(sql);
/* 199 */       if (row == 1) {
/* 200 */         return 0;
/*     */       }
/*     */ 
/* 203 */       throw new HiException("220042", "UpdateAgentAgreement: 更新数据库pubcrpact失败");
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/* 207 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int CheckAgentAgreement(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 225 */     HiMessage mess = ctx.getCurrentMsg();
/* 226 */     if ((mess == null) || (ctx == null)) {
/* 227 */       throw new HiException("220026", "[HiMessage][HiMessageContext]Is NULL");
/*     */     }
/*     */ 
/* 232 */     HiETF etfRoot = (HiETF)mess.getBody();
/*     */ 
/* 234 */     String busTyp = etfRoot.getChildValue("BUSTYP");
/* 235 */     String crpCod = etfRoot.getChildValue("CRPCOD");
/* 236 */     String brNo = etfRoot.getChildValue("BRNO");
/* 237 */     String crpSub = etfRoot.getChildValue("CRPSUB");
/*     */ 
/* 239 */     if ((busTyp == null) || (crpCod == null) || (brNo == null))
/*     */     {
/* 241 */       throw new HiException("220058", "ETF Has Not [BUSTYP][CRPCOD][BRNO]");
/*     */     }
/* 243 */     if (crpSub == null) {
/* 244 */       crpSub = " ";
/*     */     }
/*     */ 
/* 247 */     String sqlcmd = HiStringUtils.format("SELECT SVRSTS FROM PUBCRPAGT WHERE BRNO='%s' And BUSTYP='%s' And CRPCOD='%s' And CRPSUB='%s'", brNo, busTyp, crpCod, crpSub);
/*     */ 
/* 251 */     List list = null;
/*     */     try {
/* 253 */       list = ctx.getDataBaseUtil().execQuery(sqlcmd);
/* 254 */       if ((list != null) && (list.size() > 0)) {
/* 255 */         HashMap svrsts = (HashMap)list.get(0);
/* 256 */         if (!(svrsts.get("SVRSTS").equals("1")))
/* 257 */           throw new HiAppException(-3, "215027", "代理服务已经关闭");
/*     */       }
/*     */       else {
/* 260 */         if (list.size() == 0) {
/* 261 */           throw new HiAppException(-2, "215027", "代理服务未找到");
/*     */         }
/*     */ 
/* 264 */         throw new HiException("215027", "前置系统错误");
/*     */       }
/*     */     }
/*     */     catch (HiException e) {
/* 268 */       throw e;
/*     */     }
/*     */ 
/* 271 */     return 0;
/*     */   }
/*     */ }