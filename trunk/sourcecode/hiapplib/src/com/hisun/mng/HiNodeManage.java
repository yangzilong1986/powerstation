/*     */ package com.hisun.mng;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.atc.common.HiDbtSqlHelper;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.database.HiResultSet;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.version.HiFileVersionInfo;
/*     */ import com.hisun.version.HiVersion;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.zip.ZipEntry;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiNodeManage
/*     */ {
/*     */   public int nodeQuery(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  74 */     String grpNam = args.get("GrpNam");
/*  75 */     if (StringUtils.isBlank(grpNam)) {
/*  76 */       grpNam = "GRP";
/*     */     }
/*  78 */     String nodNam = args.get("NodNam");
/*  79 */     HiRegionInfo regionInfo = HiRegionInfo.parse();
/*  80 */     HiETF rootETF = ctx.getCurrentMsg().getETFBody();
/*     */ 
/*  82 */     rootETF.setChildValue("RGN_NM", regionInfo.getName());
/*  83 */     Iterator iter = regionInfo.iterator();
/*  84 */     int k = 1;
/*  85 */     while (iter.hasNext()) {
/*  86 */       HiNodeInfo nodeInfo = (HiNodeInfo)iter.next();
/*  87 */       if ((StringUtils.isNotBlank(nodNam)) && (!(StringUtils.equalsIgnoreCase(nodNam, nodeInfo.getId())))) {
/*     */         continue;
/*     */       }
/*     */ 
/*  91 */       HiETF grpNod = rootETF;
/*  92 */       grpNod = rootETF.addNode(grpNam + "_" + k);
/*  93 */       grpNod.setChildValue("NOD_ID", nodeInfo.getId());
/*  94 */       grpNod.setChildValue("NOD_DESC", nodeInfo.getDesc());
/*  95 */       grpNod.setChildValue("URL", nodeInfo.getUrl());
/*  96 */       ++k;
/*     */     }
/*  98 */     rootETF.setChildValue(grpNam + "_NUM", String.valueOf(k - 1));
/*  99 */     return 0;
/*     */   }
/*     */ 
/*     */   public int sysInfoQuery(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 112 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 113 */     root.setChildValue("FREE_MEMORY", String.valueOf(Runtime.getRuntime().freeMemory()));
/*     */ 
/* 115 */     root.setChildValue("MAX_MEMORY", String.valueOf(Runtime.getRuntime().maxMemory()));
/*     */ 
/* 117 */     root.setChildValue("INTEGRATOR_HOME", HiICSProperty.getWorkDir());
/* 118 */     root.setChildValue("INTEGRATOR_VERSION", System.getProperty("VERSION"));
/* 119 */     root.setChildValue("APP_SVR_IP", System.getProperty("APP_SVR_IP"));
/* 120 */     root.setChildValue("APP_SVR_ADM_PORT", System.getProperty("APP_SVR_ADM_PORT"));
/*     */ 
/* 122 */     return 0;
/*     */   }
/*     */ 
/*     */   public int JVM_GC(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 132 */     System.gc();
/* 133 */     return 0;
/*     */   }
/*     */ 
/*     */   public int jarChangeLogQry(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 146 */     String file = HiArgUtils.getStringNotNull(args, "JarFil");
/* 147 */     String grpNam = args.get("GrpNam");
/* 148 */     if (StringUtils.isBlank(grpNam)) {
/* 149 */       grpNam = "GRP";
/*     */     }
/* 151 */     if (!(file.startsWith("/"))) {
/* 152 */       file = HiICSProperty.getWorkDir() + "/" + file;
/*     */     }
/* 154 */     JarFile jarFile = null;
/*     */     try {
/* 156 */       jarFile = new JarFile(file);
/* 157 */       ZipEntry zipEntry = jarFile.getEntry("Change.Log");
/* 158 */       if (zipEntry == null) {
/* 159 */         throw new HiException("213302", "Change.Log");
/*     */       }
/*     */ 
/* 163 */       BufferedReader br = new BufferedReader(new InputStreamReader(jarFile.getInputStream(zipEntry)));
/*     */ 
/* 166 */       int k = 1;
/* 167 */       HiETF root = ctx.getCurrentMsg().getETFBody();
/* 168 */       while ((line = br.readLine()) != null)
/*     */       {
/*     */         String line;
/* 169 */         String[] tmps = StringUtils.split(line, " ");
/* 170 */         if (tmps.length < 4) {
/*     */           continue;
/*     */         }
/* 173 */         HiETF grpNod = root.addNode(grpNam + "_" + k);
/* 174 */         ++k;
/* 175 */         int i = 0;
/* 176 */         grpNod.setChildValue("DATE", tmps[(i++)]);
/* 177 */         grpNod.setChildValue("AUTHOR", tmps[(i++)]);
/* 178 */         if (tmps.length == 4)
/* 179 */           grpNod.setChildValue("BUGID", "");
/*     */         else {
/* 181 */           grpNod.setChildValue("BUGID", tmps[(i++)]);
/*     */         }
/* 183 */         grpNod.setChildValue("FILE", tmps[(i++)]);
/* 184 */         grpNod.setChildValue("DESC", tmps[(i++)]);
/*     */       }
/*     */     } catch (IOException e) {
/*     */     }
/*     */     finally {
/* 189 */       if (jarFile != null) {
/*     */         try {
/* 191 */           jarFile.close();
/*     */         } catch (IOException e) {
/* 193 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 197 */     return 0;
/*     */   }
/*     */ 
/*     */   public int jarQry(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 209 */     String grpNam = args.get("GrpNam");
/* 210 */     if (StringUtils.isBlank(grpNam)) {
/* 211 */       grpNam = "GRP";
/*     */     }
/* 213 */     ArrayList versionFiles = new ArrayList();
/* 214 */     HiVersion.dumpDirVersion(HiICSProperty.getWorkDir() + "/lib", versionFiles);
/*     */ 
/* 216 */     HiVersion.dumpDirVersion(HiICSProperty.getWorkDir() + "/applib", versionFiles);
/*     */ 
/* 218 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 219 */     int i = 0;
/* 220 */     for (i = 0; i < versionFiles.size(); ++i) {
/* 221 */       HiFileVersionInfo info = (HiFileVersionInfo)versionFiles.get(i);
/* 222 */       HiETF grpNod = root.addNode(grpNam + "_" + (i + 1));
/* 223 */       grpNod.setChildValue("FILE", info.getFile());
/* 224 */       grpNod.setChildValue("VERSION", info.getVersion());
/* 225 */       grpNod.setChildValue("COMPILE_TM", info.getCompileTm());
/*     */     }
/* 227 */     return 0;
/*     */   }
/*     */ 
/*     */   public int shellCmd(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 240 */     Process p = null;
/* 241 */     int ret = 0;
/* 242 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 243 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 244 */     String cmd = HiArgUtils.getStringNotNull(args, "cmd");
/* 245 */     boolean waitFlag = args.getBoolean("waitFlag");
/*     */     try {
/* 247 */       ProcessBuilder pb = new ProcessBuilder(new String[] { "/bin/sh", "-c", cmd });
/* 248 */       pb.directory(new File(HiICSProperty.getWorkDir()));
/* 249 */       pb.redirectErrorStream(true);
/* 250 */       p = pb.start();
/* 251 */       InputStream stdIs = p.getInputStream();
/* 252 */       Thread receiverThd = new Thread(new Receiver(root, stdIs));
/* 253 */       receiverThd.start();
/*     */       try {
/* 255 */         if (waitFlag)
/* 256 */           ret = p.waitFor();
/*     */       }
/*     */       catch (InterruptedException e) {
/* 259 */         throw new HiException("220309", cmd, e);
/*     */       }
/* 261 */       receiverThd.join(5000L);
/*     */     } catch (IOException e) {
/*     */     }
/*     */     catch (InterruptedException e) {
/*     */     }
/*     */     finally {
/* 267 */       if (p != null) {
/* 268 */         p.destroy();
/*     */       }
/*     */     }
/* 271 */     return ret;
/*     */   }
/*     */ 
/*     */   public int ExpToPropFile(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 288 */     String sqlCmd = HiArgUtils.getStringNotNull(args, "SqlCmd");
/* 289 */     String encoding = args.get("encoding");
/* 290 */     String file = HiArgUtils.getStringNotNull(args, "file");
/* 291 */     if (!(file.startsWith(File.separator))) {
/* 292 */       file = HiICSProperty.getWorkDir() + File.separator + file;
/*     */     }
/* 294 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 295 */     String sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlCmd, root, true);
/*     */ 
/* 297 */     HiResultSet rs = ctx.getDataBaseUtil().execQuerySQL(sqlSentence);
/*     */ 
/* 299 */     if ((rs == null) || (rs.size() == 0)) {
/* 300 */       return 2;
/*     */     }
/* 302 */     Properties p = new Properties();
/* 303 */     for (int i = 0; i < rs.size(); ++i) {
/* 304 */       HiATLParam record = rs.getRecord(i);
/* 305 */       if (record.size() < 2) {
/*     */         continue;
/*     */       }
/* 308 */       if (StringUtils.isBlank(encoding))
/* 309 */         p.put(record.getValue(0), record.getValue(1));
/*     */       else {
/*     */         try {
/* 312 */           p.put(record.getValue(0), new String(record.getValue(1).getBytes(), encoding));
/*     */         }
/*     */         catch (UnsupportedEncodingException e) {
/* 315 */           throw new HiException("220079", file, e);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 320 */     FileOutputStream fos = null;
/*     */     try {
/* 322 */       fos = new FileOutputStream(file);
/* 323 */       p.store(fos, "auto generate message code");
/*     */     } catch (IOException e) {
/*     */     }
/*     */     finally {
/* 327 */       if (fos != null) {
/*     */         try {
/* 329 */           fos.close();
/*     */         } catch (IOException e) {
/* 331 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 335 */     p.clear();
/* 336 */     return 0;
/*     */   }
/*     */ 
/*     */   public int FileDownLoad(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 348 */     boolean isInline = false;
/* 349 */     String file = HiArgUtils.getStringNotNull(args, "FILNAM");
/* 350 */     File f = new File(file);
/* 351 */     if ((!(f.exists())) || (!(f.canRead()))) {
/* 352 */       return 2;
/*     */     }
/* 354 */     HiMessage msg = ctx.getCurrentMsg();
/* 355 */     HttpServletResponse response = (HttpServletResponse)msg.getObjectHeadItem("_WEB_RESPONSE");
/*     */ 
/* 357 */     HttpServletRequest request = (HttpServletRequest)msg.getObjectHeadItem("_WEB_REQUEST");
/*     */ 
/* 359 */     String mimetype = "application/octet-stream;charset=ISO8859-1";
/* 360 */     response.setContentType(mimetype);
/* 361 */     String ua = request.getHeader("User-Agent");
/* 362 */     if (ua == null) {
/* 363 */       ua = "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0;)";
/*     */     }
/* 365 */     boolean isIE = ua.toLowerCase().indexOf("msie") != -1;
/* 366 */     if ((isIE) && (!(isInline))) {
/* 367 */       mimetype = "application/x-msdownload";
/*     */     }
/* 369 */     String downFileName = null;
/*     */     try {
/* 371 */       downFileName = new String(f.getName().getBytes(), "ISO8859-1");
/*     */     } catch (UnsupportedEncodingException e) {
/* 373 */       e.printStackTrace();
/*     */     }
/* 375 */     String inlineType = (isInline) ? "inline" : "attachment";
/* 376 */     response.setHeader("Content-Disposition", inlineType + ";filename=\"" + downFileName + "\"");
/*     */ 
/* 378 */     response.setContentLength((int)f.length());
/* 379 */     byte[] buffer = new byte[4096];
/* 380 */     BufferedOutputStream output = null;
/* 381 */     BufferedInputStream input = null;
/*     */     try {
/* 383 */       output = new BufferedOutputStream(response.getOutputStream());
/* 384 */       input = new BufferedInputStream(new FileInputStream(f));
/*     */ 
/* 386 */       int n = -1;
/* 387 */       while ((n = input.read(buffer, 0, 4096)) > -1) {
/* 388 */         output.write(buffer, 0, n);
/*     */       }
/* 390 */       response.flushBuffer();
/*     */     } catch (Exception e) {
/*     */     } finally {
/* 393 */       if (input != null) {
/*     */         try {
/* 395 */           input.close();
/*     */         } catch (IOException e) {
/* 397 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 400 */       if (output != null) {
/*     */         try {
/* 402 */           output.close();
/*     */         } catch (IOException e) {
/* 404 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 408 */     return 0;
/*     */   }
/*     */ 
/*     */   public int ParseBatchMsgInf(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 418 */     String batchInfo = HiArgUtils.getStringNotNull(args, "BatInf");
/* 419 */     String msgCod = "";
/* 420 */     String msgInf = "";
/* 421 */     String msgNam = "";
/* 422 */     String msgCls = "";
/* 423 */     BufferedReader br = new BufferedReader(new StringReader(batchInfo));
/*     */ 
/* 425 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 426 */     int k = 1;
/*     */     try {
/* 428 */       while ((line = br.readLine()) != null)
/*     */       {
/*     */         String line;
/*     */         int idx1;
/*     */         String[] tmps;
/* 430 */         if ((idx1 = line.indexOf("package ")) != -1) {
/* 431 */           msgCls = line.substring(idx1 + 8);
/* 432 */           msgCls = msgCls.replace(";", ".");
/*     */         }
/*     */ 
/* 435 */         if ((StringUtils.contains(line, "public ")) && (StringUtils.contains(line, " class "))) {
/* 436 */           tmps = StringUtils.split(line, " ");
/* 437 */           if (tmps.length >= 3) {
/* 438 */             msgCls = msgCls + tmps[2];
/*     */           }
/*     */         }
/*     */ 
/* 442 */         if ((idx1 = line.indexOf("@msg ")) != -1) {
/* 443 */           msgInf = StringUtils.trim(line.substring(idx1 + 4));
/*     */         }
/*     */ 
/* 446 */         if ((StringUtils.contains(line, "public ")) && (StringUtils.contains(line, " static ")) && (StringUtils.contains(line, " final ")) && (StringUtils.contains(line, " String ")))
/*     */         {
/* 448 */           tmps = StringUtils.split(line, " =\"");
/* 449 */           if (tmps.length >= 6) {
/* 450 */             msgNam = tmps[4];
/* 451 */             msgCod = tmps[5];
/*     */           }
/*     */         }
/* 454 */         if ((StringUtils.isNotBlank(msgCod)) && (StringUtils.isNotBlank(msgInf))) {
/* 455 */           HiETF grp = root.addNode("GRP_" + k);
/* 456 */           ++k;
/* 457 */           grp.setChildValue("MSG_CD", msgCod);
/* 458 */           grp.setChildValue("MSG_INF", msgInf);
/* 459 */           grp.setChildValue("MSG_NM", msgNam);
/* 460 */           if (StringUtils.isBlank(msgCls))
/* 461 */             grp.setChildValue("MSG_CLS", "undefined");
/*     */           else {
/* 463 */             grp.setChildValue("MSG_CLS", msgCls);
/*     */           }
/* 465 */           msgCod = "";
/* 466 */           msgInf = "";
/* 467 */           msgNam = "";
/*     */         }
/*     */       }
/* 470 */       root.setChildValue("GRP_NUM", String.valueOf(k - 1));
/*     */     } catch (IOException e) {
/* 472 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 475 */     return 0;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 479 */     String line = "2009.03.04 xu_lw              HiIntegrator                增加AutoCommit标志";
/* 480 */     String[] tmps = StringUtils.split(line, " ");
/* 481 */     for (int i = 0; i < tmps.length; ++i)
/* 482 */       System.out.println(tmps[i]);
/*     */   }
/*     */ }