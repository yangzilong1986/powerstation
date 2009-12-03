/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.atc.common.HiFTP;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiOthOnlAtl
/*     */ {
/*  27 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public int FtpPut(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  53 */     HashMap ftpInfo = null;
/*  54 */     if (argsMap.size() == 1)
/*     */     {
/*  56 */       if (argsMap.contains("FtpId"))
/*     */       {
/*  58 */         String strFtpID = argsMap.get("FtpId");
/*  59 */         ftpInfo = GetFtpConfigFromDB(ctx, strFtpID);
/*     */       }
/*     */       else
/*     */       {
/*  63 */         String strFtpCfg = argsMap.get("FtpCfg");
/*  64 */         ftpInfo = GetFtpConfigFromFile(ctx, strFtpCfg);
/*     */       }
/*     */ 
/*     */     }
/*     */     else {
/*  69 */       ftpInfo = argsMap.toMap();
/*     */     }
/*     */ 
/*  72 */     HiFTP ftp = new HiFTP();
/*     */ 
/*  74 */     ftp.setServer((String)ftpInfo.get("IPADR"));
/*  75 */     if (StringUtils.isEmpty(ftp.getServer()))
/*     */     {
/*  77 */       throw new HiException("220005", "IPADR");
/*     */     }
/*  79 */     ftp.setRemoteDir((String)ftpInfo.get("OBJDIR"));
/*  80 */     if (StringUtils.isEmpty(ftp.getRemoteDir()))
/*     */     {
/*  82 */       throw new HiException("220005", "OBJDIR");
/*     */     }
/*  84 */     String strLclDir = (String)ftpInfo.get("LCLDIR");
/*  85 */     if (StringUtils.isEmpty(strLclDir))
/*     */     {
/*  87 */       throw new HiException("220005", "LCLDIR");
/*     */     }
/*  89 */     if (!(strLclDir.startsWith(File.separator)))
/*  90 */       ftp.setLocalDir(HiICSProperty.getWorkDir() + File.separator + strLclDir);
/*     */     else {
/*  92 */       ftp.setLocalDir(strLclDir);
/*     */     }
/*  94 */     ftp.setUsername((String)ftpInfo.get("USRNAM"));
/*  95 */     if (StringUtils.isEmpty(ftp.getUsername()))
/*     */     {
/*  97 */       throw new HiException("220005", "USRNAM");
/*     */     }
/*     */ 
/* 100 */     ftp.setPassword((String)ftpInfo.get("USRPWD"));
/* 101 */     if (StringUtils.isEmpty(ftp.getPassword()))
/*     */     {
/* 103 */       throw new HiException("220005", "USRPWD");
/*     */     }
/* 105 */     ftp.setLocalFile((String)ftpInfo.get("LCLFIL"));
/* 106 */     if (StringUtils.isEmpty(ftp.getLocalFile()))
/*     */     {
/* 108 */       throw new HiException("220005", "LCLFIL");
/*     */     }
/*     */ 
/* 111 */     ftp.setRemoteFile((String)ftpInfo.get("OBJFIL"));
/* 112 */     ftp.setParam((String)ftpInfo.get("PARAM"));
/* 113 */     ftp.setBinaryTransfer(true);
/*     */ 
/* 116 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 117 */     if (log.isInfoEnabled()) {
/* 118 */       log.info(sm.getString("HiOthOnlAtl.FtpPut.Param", new String[] { ftp.getServer(), ftp.getUsername(), ftp.getPassword(), ftp.getRemoteDir(), ftp.getLocalDir(), ftp.getLocalFile(), ftp.getRemoteFile() }));
/*     */     }
/*     */ 
/* 125 */     ftp.put();
/* 126 */     return 0;
/*     */   }
/*     */ 
/*     */   public int HiFtpPut(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 157 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int HiFtpOp(HiATLParam argsMap, HiMessageContext ctx, String op)
/*     */     throws HiException
/*     */   {
/* 163 */     HashMap ftpInfo = null;
/* 164 */     if (argsMap.size() == 1)
/*     */     {
/* 166 */       if (argsMap.contains("FtpId"))
/*     */       {
/* 168 */         String strFtpID = argsMap.get("FtpId");
/* 169 */         ftpInfo = GetFtpConfigFromDB(ctx, strFtpID);
/*     */       }
/*     */       else
/*     */       {
/* 173 */         String strFtpCfg = argsMap.get("FtpCfg");
/* 174 */         ftpInfo = GetFtpConfigFromFile(ctx, strFtpCfg);
/*     */       }
/*     */ 
/*     */     }
/*     */     else {
/* 179 */       ftpInfo = argsMap.toMap();
/*     */     }
/* 181 */     String strIpAdr = (String)ftpInfo.get("IPADR");
/* 182 */     if (StringUtils.isEmpty(strIpAdr))
/*     */     {
/* 184 */       throw new HiException("220005", "IPADR", strIpAdr);
/*     */     }
/*     */ 
/* 187 */     String strObjDir = (String)ftpInfo.get("OBJDIR");
/* 188 */     if (StringUtils.isEmpty(strObjDir))
/*     */     {
/* 190 */       throw new HiException("220005", "OBJDIR", strObjDir);
/*     */     }
/*     */ 
/* 193 */     String strLclDir = (String)ftpInfo.get("LCLDIR");
/* 194 */     if (StringUtils.isEmpty(strLclDir))
/*     */     {
/* 196 */       throw new HiException("220005", "LCLDIR", strLclDir);
/*     */     }
/*     */ 
/* 199 */     String Port = (String)ftpInfo.get("PROT");
/* 200 */     if (StringUtils.isEmpty(Port))
/*     */     {
/* 202 */       throw new HiException("220005", "Port", Port);
/*     */     }
/* 204 */     String Mode = "bin";
/* 205 */     if (ftpInfo.containsKey("MODE")) {
/* 206 */       Mode = (String)ftpInfo.get("MODE");
/*     */     }
/* 208 */     String strObjFile = (String)ftpInfo.get("OBJFIL");
/* 209 */     if (StringUtils.isEmpty(strObjFile))
/*     */     {
/* 211 */       throw new HiException("220005", "OBJFIL", strObjFile);
/*     */     }
/*     */ 
/* 214 */     String strLclFile = (String)ftpInfo.get("LCLFIL");
/* 215 */     if (StringUtils.isEmpty(strLclFile))
/*     */     {
/* 217 */       throw new HiException("220005", "LCLFIL", strLclFile);
/*     */     }
/*     */ 
/* 221 */     String fullFile = HiArgUtils.absoutePath(strLclDir + File.separator + strLclFile);
/*     */ 
/* 224 */     return 0;
/*     */   }
/*     */ 
/*     */   public static HashMap GetFtpConfigFromArgs(HiMessageContext ctx, String strFtpID)
/*     */     throws HiException
/*     */   {
/* 230 */     HashMap ftpInfo = null;
/*     */ 
/* 232 */     return ftpInfo;
/*     */   }
/*     */ 
/*     */   private static HashMap GetFtpConfigFromDB(HiMessageContext ctx, String strFtpID)
/*     */     throws HiException
/*     */   {
/* 238 */     HashMap ftpInfo = null;
/* 239 */     String strBrNo = ctx.getCurrentMsg().getETFBody().getChildValue("BR_NO");
/*     */ 
/* 241 */     String strSQL = "SELECT IP_ADR,USR_NAM,USR_PWD,OBJ_DIR,OBJ_FIL, LCL_DIR,LCL_FIL,FTP_MOD,Port FROM \tpubftpcfg WHERE BR_NO='" + strBrNo + "' AND FTP_ID='" + strFtpID + "'";
/*     */     try
/*     */     {
/* 246 */       List list = ctx.getDataBaseUtil().execQuery(strSQL);
/*     */ 
/* 248 */       if ((list == null) || (list.size() == 0))
/*     */       {
/* 250 */         throw new HiException("220001", strBrNo, strFtpID);
/*     */       }
/*     */ 
/* 253 */       ftpInfo = (HashMap)list.get(0);
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/* 257 */       e.addMsgStack("220001", strBrNo, strFtpID);
/* 258 */       throw e;
/*     */     }
/*     */ 
/* 261 */     return ftpInfo;
/*     */   }
/*     */ 
/*     */   private static HashMap GetFtpConfigFromFile(HiMessageContext ctx, String strFtpCfg)
/*     */     throws HiException
/*     */   {
/* 267 */     HashMap ftpInfo = new HashMap();
/*     */ 
/* 269 */     Element rootNode = (Element)ctx.getProperty("CONFIGDECLARE", strFtpCfg);
/*     */ 
/* 272 */     Iterator list = rootNode.elementIterator();
/* 273 */     while (list.hasNext())
/*     */     {
/* 275 */       Element item = (Element)list.next();
/* 276 */       String strNodeName = item.getName();
/* 277 */       String strValue = item.getText();
/* 278 */       if ((strValue != null) && (strValue.startsWith("$")) && (strValue.length() > 1))
/*     */       {
/* 280 */         strValue = strValue.substring(1);
/* 281 */         strValue = ctx.getETFValue(ctx.getCurrentMsg().getETFBody(), strValue);
/*     */       }
/* 283 */       ftpInfo.put(strNodeName.toUpperCase(), strValue);
/*     */     }
/*     */ 
/* 286 */     return ftpInfo;
/*     */   }
/*     */ 
/*     */   public int FtpGet(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 313 */     HashMap ftpInfo = null;
/* 314 */     if (argsMap.size() == 1)
/*     */     {
/* 316 */       if (argsMap.contains("FtpId"))
/*     */       {
/* 318 */         String strFtpID = argsMap.get("FtpId");
/* 319 */         ftpInfo = GetFtpConfigFromDB(ctx, strFtpID);
/*     */       }
/*     */       else
/*     */       {
/* 323 */         String strFtpCfg = argsMap.get("FtpCfg");
/* 324 */         ftpInfo = GetFtpConfigFromFile(ctx, strFtpCfg);
/*     */       }
/*     */ 
/*     */     }
/*     */     else {
/* 329 */       ftpInfo = argsMap.toMap();
/*     */     }
/* 331 */     HiFTP ftp = new HiFTP();
/*     */ 
/* 333 */     ftp.setServer((String)ftpInfo.get("IPADR"));
/* 334 */     if (StringUtils.isEmpty(ftp.getServer()))
/*     */     {
/* 336 */       throw new HiException("220005", "IPADR");
/*     */     }
/* 338 */     ftp.setRemoteDir((String)ftpInfo.get("OBJDIR"));
/* 339 */     if (StringUtils.isEmpty(ftp.getRemoteDir()))
/*     */     {
/* 341 */       throw new HiException("220005", "OBJDIR");
/*     */     }
/* 343 */     String strLclDir = (String)ftpInfo.get("LCLDIR");
/* 344 */     if (StringUtils.isEmpty(strLclDir))
/*     */     {
/* 346 */       throw new HiException("220005", "LCLDIR");
/*     */     }
/* 348 */     if (!(strLclDir.startsWith(File.separator)))
/* 349 */       ftp.setLocalDir(HiICSProperty.getWorkDir() + File.separator + strLclDir);
/*     */     else {
/* 351 */       ftp.setLocalDir(strLclDir);
/*     */     }
/*     */ 
/* 354 */     ftp.setUsername((String)ftpInfo.get("USRNAM"));
/* 355 */     if (StringUtils.isEmpty(ftp.getUsername()))
/*     */     {
/* 357 */       throw new HiException("220005", "USRNAM");
/*     */     }
/* 359 */     ftp.setPassword((String)ftpInfo.get("USRPWD"));
/* 360 */     if (StringUtils.isEmpty(ftp.getPassword()))
/*     */     {
/* 362 */       throw new HiException("220005", "USRPWD");
/*     */     }
/* 364 */     ftp.setRemoteFile((String)ftpInfo.get("OBJFIL"));
/*     */ 
/* 366 */     if (StringUtils.isEmpty(ftp.getRemoteFile()))
/*     */     {
/* 368 */       throw new HiException("220005", "OBJFIL");
/*     */     }
/* 370 */     ftp.setLocalFile((String)ftpInfo.get("LCLFIL"));
/*     */ 
/* 374 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 375 */     if (log.isInfoEnabled()) {
/* 376 */       log.info(sm.getString("HiOthOnlAtl.FtpPut.Param", new String[] { ftp.getServer(), ftp.getUsername(), ftp.getPassword(), ftp.getRemoteDir(), ftp.getLocalDir(), ftp.getLocalFile(), ftp.getRemoteFile() }));
/*     */     }
/*     */ 
/* 379 */     ftp.setParam((String)ftpInfo.get("PARAM"));
/* 380 */     ftp.setBinaryTransfer(true);
/*     */ 
/* 383 */     ftp.get();
/* 384 */     return 0;
/*     */   }
/*     */ 
/*     */   public int HiFtpGet(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 390 */     throw new HiException("not implement");
/*     */   }
/*     */ }