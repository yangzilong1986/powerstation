/*     */ package com.hisun.atmp;
/*     */ 
/*     */ import com.hisun.crypt.Encryptor;
/*     */ import com.hisun.crypt.des.DESCryptorFactory;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiConvHelper;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.FileChannel.MapMode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import org.apache.commons.codec.binary.Hex;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiAtmpFit
/*     */ {
/*     */   private static final Encryptor encryptor;
/*     */ 
/*     */   public int GetCrdFlg(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  56 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/*  57 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/*  60 */     String trk2 = argsMap.get("Trk2");
/*  61 */     String trk3 = argsMap.get("Trk3");
/*  62 */     boolean isHandInputCard = false;
/*  63 */     String crdNo = argsMap.get("CrdNo");
/*     */ 
/*  66 */     if ((StringUtils.isBlank(trk2)) && (StringUtils.isBlank(trk3))) {
/*  67 */       if (log.isDebugEnabled()) {
/*  68 */         log.debug("hand input card:" + crdNo);
/*     */       }
/*  70 */       isHandInputCard = true;
/*     */     }
/*  72 */     if ((isHandInputCard) && (StringUtils.isEmpty(crdNo))) {
/*  73 */       throw new HiAppException(-1, "310008");
/*     */     }
/*  75 */     int i = 0;
/*     */ 
/*  77 */     HiFit.loadFit(ctx);
/*  78 */     ArrayList fitTable = HiFit.getFitTable();
/*  79 */     for (; i < fitTable.size(); ++i) {
/*  80 */       HiFitItem item = new HiFitItem();
/*  81 */       item.toFitItem((HashMap)fitTable.get(i));
/*  82 */       if (isHandInputCard) {
/*  83 */         String fitValue = StringUtils.substring(crdNo, 0, item.fitLen);
/*     */ 
/*  85 */         String fit2 = item.fitCtt.substring(0, item.fitLen);
/*  86 */         if (!(StringUtils.equals(fitValue, fit2)))
/*     */           continue;
/*     */       }
/*     */       else
/*     */       {
/*     */         int idx;
/*  90 */         String trk = null;
/*     */ 
/*  92 */         if (item.fitTrk == 2)
/*  93 */           trk = trk2;
/*     */         else {
/*  95 */           trk = trk3;
/*     */         }
/*     */ 
/*  98 */         if (StringUtils.isEmpty(trk))
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/* 103 */         if (item.fitOfs + item.fitLen > trk.length()) {
/*     */           continue;
/*     */         }
/* 106 */         if (item.fitLen > item.fitCtt.length()) {
/* 107 */           throw new HiAppException(-1, "310003");
/*     */         }
/*     */ 
/* 111 */         String fit1 = trk.substring(item.fitOfs, item.fitOfs + item.fitLen);
/* 112 */         String fit2 = item.fitCtt.substring(0, item.fitLen);
/* 113 */         if (log.isDebugEnabled()) {
/* 114 */           log.debug("fit:[" + fit1 + "]:[" + fit2 + "]");
/*     */         }
/*     */ 
/* 117 */         if (!(StringUtils.equals(fit1, fit2))) {
/*     */           continue;
/*     */         }
/*     */ 
/* 121 */         if ((item.crdTrk == 2) && (StringUtils.isEmpty(trk2))) {
/* 122 */           throw new HiAppException(-1, "310001");
/*     */         }
/*     */ 
/* 126 */         if ((item.crdTrk == 3) && (StringUtils.isEmpty(trk3))) {
/* 127 */           throw new HiAppException(-1, "310002");
/*     */         }
/*     */ 
/* 131 */         if (item.crdTrk == 2)
/* 132 */           trk = trk2;
/*     */         else {
/* 134 */           trk = trk3;
/*     */         }
/*     */ 
/* 137 */         if (item.crdLen > 0) {
/* 138 */           if (item.crdOfs + item.crdLen > trk.length()) {
/* 139 */             throw new HiAppException(-1, "310004");
/*     */           }
/*     */ 
/* 142 */           crdNo = trk.substring(item.crdOfs, item.crdOfs + item.crdLen);
/*     */         }
/*     */         else {
/* 145 */           idx = trk.indexOf("=", item.crdOfs);
/* 146 */           if (idx == -1) {
/* 147 */             throw new HiAppException(-1, "310006");
/*     */           }
/*     */ 
/* 150 */           crdNo = trk.substring(item.crdOfs, idx);
/*     */         }
/*     */ 
/* 154 */         if (item.vaDtOf >= 0) {
/* 155 */           if (item.vaDtOf + 4 > trk.length()) {
/* 156 */             throw new HiAppException(-1, "310010", String.valueOf(item.vaDtOf));
/*     */           }
/*     */ 
/* 159 */           String expDat = trk.substring(item.vaDtOf, item.vaDtOf + 4);
/* 160 */           if (!(StringUtils.isNumeric(expDat))) {
/* 161 */             throw new HiException("310011", expDat);
/*     */           }
/*     */ 
/* 164 */           if (NumberUtils.toInt(expDat.substring(2, 2)) > 12) {
/* 165 */             throw new HiException("310011", expDat);
/*     */           }
/* 167 */           root.setChildValue("ExpDat", expDat);
/*     */         } else {
/* 169 */           idx = trk2.indexOf("=");
/* 170 */           if ((idx == -1) || (idx == 0)) {
/* 171 */             throw new HiAppException(-1, "310006");
/*     */           }
/*     */ 
/* 174 */           String expDat = trk2.substring(idx + 1, idx + 1 + 4);
/* 175 */           if (!(StringUtils.isNumeric(expDat))) {
/* 176 */             throw new HiException("310011", expDat);
/*     */           }
/*     */ 
/* 179 */           if (NumberUtils.toInt(expDat.substring(2, 2)) > 12) {
/* 180 */             throw new HiException("310011", expDat);
/*     */           }
/* 182 */           root.setChildValue("ExpDat", expDat);
/*     */         }
/*     */       }
/*     */ 
/* 186 */       root.setChildValue("Crd_No", crdNo);
/* 187 */       root.setChildValue("CrdFlg", item.crdFlg);
/* 188 */       root.setChildValue("BnkTyp", item.bnkTyp);
/* 189 */       if ((!(StringUtils.equals(item.crdFlg, "01"))) && (!(StringUtils.equals(item.crdFlg, "02"))) && (!(StringUtils.equals(item.crdFlg, "03"))) && (!(StringUtils.equals(item.crdFlg, "04"))))
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/* 196 */       if (item.cdCdOf + 3 >= crdNo.length()) {
/* 197 */         throw new HiAppException(-1, "310005");
/*     */       }
/*     */ 
/* 201 */       String orgCd = crdNo.substring(item.cdCdOf, item.cdCdOf + 3);
/* 202 */       root.setChildValue("OrgCd", orgCd);
/* 203 */       break;
/*     */     }
/*     */ 
/* 206 */     if (i >= fitTable.size())
/*     */     {
/* 208 */       if (log.isInfoEnabled()) {
/* 209 */         log.info("no match record!default process...");
/*     */       }
/* 211 */       if (isHandInputCard) {
/* 212 */         root.setChildValue("Crd_No", crdNo);
/*     */       } else {
/* 214 */         if (StringUtils.isEmpty(trk2)) {
/* 215 */           throw new HiAppException(-1, "310001");
/*     */         }
/*     */ 
/* 218 */         int idx = trk2.indexOf("=");
/* 219 */         if ((idx == -1) || (idx == 0)) {
/* 220 */           throw new HiAppException(-1, "310006");
/*     */         }
/*     */ 
/* 223 */         crdNo = trk2.substring(0, idx);
/* 224 */         root.setChildValue("Crd_No", crdNo);
/*     */ 
/* 226 */         String expDat = trk2.substring(idx + 1, idx + 1 + 4);
/* 227 */         if (!(StringUtils.isNumeric(expDat))) {
/* 228 */           throw new HiException("310011", expDat);
/*     */         }
/*     */ 
/* 231 */         if (NumberUtils.toInt(expDat.substring(2, 2)) > 12) {
/* 232 */           throw new HiException("310011", expDat);
/*     */         }
/* 234 */         root.setChildValue("ExpDat", expDat);
/*     */       }
/* 236 */       root.setChildValue("CrdFlg", "50");
/* 237 */       root.setChildValue("BnkTyp", "9999");
/*     */     }
/*     */ 
/* 240 */     return 0;
/*     */   }
/*     */ 
/*     */   public int DESEncrypt(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 254 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 255 */     String node = argsMap.get("node");
/*     */ 
/* 257 */     String data = root.getChildValue(node);
/* 258 */     if ((data != null) && (data.length() > 0)) {
/*     */       try {
/* 260 */         byte[] ret = encryptor.encrypt(data.getBytes());
/* 261 */         root.setChildValue(node, HiConvHelper.binToAscStr(ret));
/*     */       } catch (Exception e) {
/* 263 */         throw new HiException(e);
/*     */       }
/*     */     }
/*     */ 
/* 267 */     return 0;
/*     */   }
/*     */ 
/*     */   public int XOREncrypt(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 281 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 282 */     String node = argsMap.get("node");
/*     */ 
/* 284 */     String data = root.getChildValue(node);
/* 285 */     StringBuffer result = new StringBuffer();
/* 286 */     String key = "hisun";
/* 287 */     int j = 0;
/* 288 */     for (int i = 0; i < data.length(); ++i) {
/* 289 */       if (j == key.length())
/* 290 */         j = 0;
/* 291 */       result.append(StringUtils.leftPad(Integer.toHexString(data.charAt(i) ^ key.charAt(j)), 2, '0'));
/*     */ 
/* 294 */       ++j;
/*     */     }
/* 296 */     root.setChildValue(node, result.toString());
/* 297 */     return 0;
/*     */   }
/*     */ 
/*     */   public int XORDecrypt(HiATLParam argsMap, HiMessageContext ctx) throws HiException
/*     */   {
/* 302 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 303 */     String node = argsMap.get("node");
/*     */ 
/* 305 */     String data = root.getChildValue(node);
/* 306 */     StringBuffer result = new StringBuffer();
/* 307 */     String key = "hisun";
/* 308 */     int j = 0;
/* 309 */     for (int i = 0; i < data.length(); i += 2) {
/* 310 */       if (j == key.length())
/* 311 */         j = 0;
/* 312 */       int c = Integer.parseInt(data.substring(i, i + 2), 16);
/* 313 */       result.append((char)(c ^ key.charAt(j)));
/* 314 */       ++j;
/*     */     }
/* 316 */     root.setChildValue(node, result.toString());
/* 317 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int ParseFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 330 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 331 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/* 333 */     String file = HiICSProperty.getWorkDir() + "/" + argsMap.get("file");
/* 334 */     String lennode = argsMap.get("len");
/* 335 */     String datanode = argsMap.get("data");
/*     */ 
/* 337 */     if (log.isInfoEnabled()) {
/* 338 */       log.info("ParseFile parameters: file = [" + file + "]");
/*     */     }
/* 340 */     FileChannel channel = null;
/*     */     try {
/* 342 */       channel = new FileInputStream(file).getChannel();
/* 343 */       ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
/* 344 */       byte[] lenbyte = new byte[3];
/* 345 */       buf.get(lenbyte);
/*     */ 
/* 347 */       String len = new String(lenbyte);
/*     */ 
/* 349 */       byte[] databyte = new byte[(int)(channel.size() - 3L)];
/* 350 */       buf.get(databyte);
/* 351 */       char[] hex = Hex.encodeHex(databyte);
/* 352 */       String data = new String(hex);
/*     */ 
/* 354 */       if (log.isInfoEnabled()) {
/* 355 */         log.info("ParseFile output: len = [" + len + "], data = [" + data + "]");
/*     */       }
/*     */ 
/* 359 */       root.setChildValue(lennode, len);
/* 360 */       root.setChildValue(datanode, data);
/*     */     } catch (Exception e) {
/*     */     }
/*     */     finally {
/*     */       try {
/* 365 */         if (channel != null)
/* 366 */           channel.close();
/*     */       }
/*     */       catch (IOException e) {
/*     */       }
/*     */     }
/* 371 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int Random(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 377 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 378 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/* 380 */     String freq = argsMap.get("freq");
/* 381 */     int n = Integer.parseInt(freq);
/* 382 */     Random r = new Random();
/* 383 */     if (n == 1) {
/* 384 */       root.setChildValue("result", "1");
/* 385 */       return 0;
/*     */     }
/*     */ 
/* 388 */     int ret = r.nextInt(n);
/* 389 */     if (ret == 1)
/* 390 */       root.setChildValue("result", "1");
/*     */     else
/* 392 */       root.setChildValue("result", "0");
/* 393 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int PasswdCheck(HiATLParam argsMap, HiMessageContext ctx) throws HiException
/*     */   {
/* 398 */     String passwd = argsMap.get("PassWd");
/* 399 */     for (int i = 0; i < passwd.length(); ++i) {
/* 400 */       if (!(Character.isDigit(passwd.charAt(i)))) {
/* 401 */         return 1;
/*     */       }
/*     */     }
/* 404 */     return 0;
/*     */   }
/*     */ 
/*     */   public int ReloadCrdFlg(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 415 */     HiFit.reLoadFit(ctx);
/* 416 */     return 0;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 322 */     DESCryptorFactory factory = new DESCryptorFactory();
/* 323 */     encryptor = factory.getEncryptor();
/* 324 */     encryptor.setKey(factory.getDefaultEncryptKey());
/*     */   }
/*     */ }