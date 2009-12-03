/*     */ package com.hisun.ccb.atc;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class FunctionMsg
/*     */ {
/*     */   public int addMsg(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  29 */     String appMmo = argsMap.get("AP_MMO");
/*  30 */     String msgCode = argsMap.get("MSG_CD");
/*  31 */     String msgInfo = argsMap.get("MSG_INF");
/*  32 */     String errorFieldName = argsMap.get("ERR_FLD");
/*  33 */     String auth_level1 = argsMap.get("ATH_LVL1");
/*  34 */     String auth_level2 = argsMap.get("ATH_LVL2");
/*  35 */     String values = argsMap.get("VALUES");
/*     */ 
/*  37 */     HiMessage mess = ctx.getCurrentMsg();
/*  38 */     HiETF etfRoot = (HiETF)mess.getBody();
/*     */ 
/*  40 */     MsgAuth msgAuth = null;
/*  41 */     MsgError msgError = null;
/*  42 */     MsgNormal msgNormal = null;
/*  43 */     MsgSet msgSet = null;
/*  44 */     Object obj = ctx.getBaseSource("_msgSet");
/*  45 */     if (obj != null)
/*     */     {
/*  47 */       msgSet = (MsgSet)obj;
/*     */     }
/*     */     else {
/*  50 */       msgSet = new MsgSet();
/*  51 */       ctx.setBaseSource("_msgSet", msgSet);
/*     */     }
/*  53 */     if ((appMmo == null) || (appMmo.equals("")))
/*     */     {
/*  55 */       setResult(ctx, "E", "6014");
/*  56 */       throw new HiException("220207", "AP_MMO");
/*     */     }
/*  58 */     if ((msgCode == null) || (msgCode.equals("")))
/*     */     {
/*  60 */       setResult(ctx, "E", "6014");
/*  61 */       throw new HiException("220207", "MSG_CD");
/*     */     }
/*     */ 
/*  66 */     if ((((auth_level1 == null) || (auth_level1.equals("")) || (auth_level1.equals("0")))) && (auth_level2 != null) && (!(auth_level2.equals(""))) && (!(auth_level2.equals("0"))))
/*     */     {
/*  69 */       setResult(ctx, "E", "6014");
/*  70 */       throw new HiException("213309");
/*     */     }
/*     */ 
/*  75 */     if ((auth_level1 != null) && (!(auth_level1.equals(""))))
/*     */     {
/*  77 */       msgAuth = new MsgAuth(appMmo, msgCode, msgInfo);
/*     */       try
/*     */       {
/*  80 */         msgAuth.setAuth_level1(Integer.parseInt(auth_level1));
/*  81 */         if ((auth_level2 != null) && (!(auth_level2.equals(""))));
/*  94 */         return 0;
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/*     */       }
/*     */       finally
/*     */       {
/*  92 */         setResult(ctx, "A", msgCode);
/*  93 */         msgSet.addMsg(msgAuth);
/*  94 */         return 0; }
/*     */     }
/*  96 */     if ((errorFieldName != null) && (!(errorFieldName.equals(""))))
/*     */     {
/*  98 */       msgError = new MsgError(appMmo, msgCode, msgInfo);
/*  99 */       msgError.setErrorFieldName(errorFieldName);
/* 100 */       setResult(ctx, "E", msgCode);
/* 101 */       msgSet.addMsg(msgError);
/* 102 */       return 0;
/*     */     }
/*     */ 
/* 105 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/* 106 */     String sql = "select MSG_TYP,MSG_INF,ATH_LVL1,ATH_LVL2,MSG_INF from PUBTMSG where AP_MMO='" + appMmo + "' and " + "MSG_CD" + "='" + msgCode + "'";
/*     */ 
/* 110 */     List list = dbUtil.execQuery(sql);
/* 111 */     if ((list == null) || (list.size() == 0))
/*     */     {
/* 113 */       dbUtil.close();
/* 114 */       setResult(ctx, "E", "6014");
/* 115 */       throw new HiAppException(-1, "record not found");
/*     */     }
/*     */ 
/* 125 */     dbUtil.close();
/* 126 */     Map map = (Map)list.iterator().next();
/* 127 */     auth_level1 = (String)map.get("ATH_LVL1");
/* 128 */     auth_level2 = (String)map.get("ATH_LVL2");
/* 129 */     String msgType = (String)map.get("MSG_TYP");
/*     */ 
/* 134 */     if ((msgInfo == null) || (msgInfo.equals("")))
/* 135 */       msgInfo = (String)map.get("MSG_INF");
/* 136 */     if ((msgInfo == null) || (msgInfo.equals(""))) {
/* 137 */       msgInfo = "message info was not defined in database 'PUBTMSG'";
/*     */     }
/* 139 */     if ((auth_level1 != null) && (!(auth_level1.equals(""))) && (!(auth_level1.equals("0"))))
/*     */     {
/* 141 */       msgAuth = new MsgAuth(appMmo, msgCode, msgInfo);
/*     */       try
/*     */       {
/* 144 */         msgAuth.setAuth_level1(Integer.parseInt(auth_level1));
/* 145 */         if ((auth_level2 != null) && (!(auth_level2.equals(""))));
/* 162 */         return 0;
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/*     */       }
/*     */       finally
/*     */       {
/* 156 */         msgSet.addMsg(msgAuth);
/*     */ 
/* 161 */         setResult(ctx, "A", msgCode);
/* 162 */         return (((msgInfo == null) || (msgInfo.equals(""))) ? 1 : 0);
/*     */       }
/*     */     }
/* 165 */     if ((msgType == null) || ((!(msgType.equals("E"))) && (!(msgType.equals("N")))))
/*     */     {
/* 171 */       etfRoot.setChildValue("MSG_TYP", "E");
/* 172 */       msgError = new MsgError(appMmo, msgCode, msgInfo);
/* 173 */       msgSet.addMsg(msgError);
/* 174 */       return -1;
/*     */     }
/*     */ 
/* 177 */     etfRoot.setChildValue("MSG_TYP", msgType);
/* 178 */     etfRoot.setChildValue("MSG_CD", msgCode);
/*     */ 
/* 180 */     if (msgType.equals("N"))
/*     */     {
/* 182 */       return 0;
/*     */     }
/* 184 */     msgError = new MsgError(appMmo, msgCode, msgInfo);
/*     */ 
/* 192 */     setResult(ctx, "E", msgCode);
/* 193 */     msgSet.addMsg(msgError);
/* 194 */     return 0;
/*     */   }
/*     */ 
/*     */   public int outputErrorMsg(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 217 */     HiMessage mess = ctx.getCurrentMsg();
/* 218 */     HiETF etfRoot = (HiETF)mess.getBody();
/* 219 */     String msgType = etfRoot.getChildValue("MSG_TYP");
/* 220 */     MsgSet msgSet = null;
/* 221 */     if (msgType.equalsIgnoreCase("E"))
/*     */     {
/* 223 */       Object obj = ctx.getBaseSource("_msgSet");
/* 224 */       if (obj != null)
/*     */       {
/* 226 */         msgSet = (MsgSet)obj;
/*     */ 
/* 230 */         etfRoot.setChildValue("OUTP_DAT", msgSet.getMsgError());
/*     */       }
/*     */     }
/*     */ 
/* 234 */     return 0;
/*     */   }
/*     */ 
/*     */   private void setResult(HiMessageContext ctx, String msgType, String msgCode)
/*     */     throws HiException
/*     */   {
/* 247 */     HiMessage mess = ctx.getCurrentMsg();
/* 248 */     HiETF etfRoot = (HiETF)mess.getBody();
/*     */ 
/* 250 */     etfRoot.setChildValue("MSG_TYP", msgType);
/* 251 */     if (msgCode != null) {
/* 252 */       etfRoot.setChildValue("MSG_CD", msgCode);
/*     */     }
/* 254 */     HiETF etfGWA = (HiETF)ctx.getBaseSource("GWA");
/* 255 */     if (etfGWA == null)
/*     */       return;
/* 257 */     etfGWA.setChildValue("MSG_TYP", msgType);
/* 258 */     if (msgCode != null)
/* 259 */       etfGWA.setChildValue("MSG_CD", msgCode);
/*     */   }
/*     */ }