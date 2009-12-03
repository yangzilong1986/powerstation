/*     */ package com.hisun.ccb.atc.multi;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.atc.common.HiDbtSqlHelper;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiMulti
/*     */ {
/*     */   public int GenDataSet(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     Iterator i$;
/*  65 */     HiMessage mess = ctx.getCurrentMsg();
/*  66 */     Logger log = HiLog.getLogger(mess);
/*  67 */     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
/*     */ 
/*  69 */     String type = argsMap.get("Type");
/*  70 */     if ((StringUtils.isEmpty(recKey)) || (StringUtils.isEmpty(type)))
/*     */     {
/*  72 */       throw new HiException("220049");
/*     */     }
/*  74 */     recKey = recKey.trim();
/*  75 */     String flagStr = argsMap.get("Flag");
/*  76 */     boolean flag = (flagStr != null) && (flagStr.equalsIgnoreCase("ADD"));
/*     */ 
/*  78 */     String fileDir = HiICSProperty.getWorkDir() + File.separator + "tmp/Multi_";
/*     */ 
/*  80 */     String fileName = fileDir + recKey + ".dat";
/*  81 */     if (log.isDebugEnabled())
/*     */     {
/*  83 */       log.debug("FILE NAME IS:[" + fileName + "]");
/*     */     }
/*     */ 
/*  86 */     HiETF root = mess.getETFBody();
/*  87 */     if (type.equalsIgnoreCase("SQL"))
/*     */     {
/*  89 */       if (log.isDebugEnabled())
/*     */       {
/*  91 */         log.debug("PREPARE TO QUERY DATA FROM DB...");
/*     */       }
/*  93 */       String sqlCmd = HiArgUtils.getStringNotNull(argsMap, "SqlCmd");
/*     */ 
/*  96 */       sqlCmd = HiDbtSqlHelper.getDynSentence(ctx, sqlCmd, root);
/*  97 */       HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*     */ 
/*  99 */       List list = null;
/* 100 */       list = dbUtil.execQuery(sqlCmd);
/*     */ 
/* 105 */       if (list == null)
/* 106 */         throw new HiException("220038");
/* 107 */       if (list.size() == 0) {
/* 108 */         return 2;
/*     */       }
/* 110 */       if (log.isDebugEnabled())
/*     */       {
/* 112 */         log.debug("COUNTS OF RESULT IS:[" + list.size() + "]");
/*     */       }
/*     */ 
/* 115 */       for (i$ = list.iterator(); i$.hasNext(); ) { Object obj = i$.next();
/*     */ 
/* 117 */         Map map = (Map)obj;
/* 118 */         Collection names = map.keySet();
/* 119 */         HiETF nodeEtf = HiETFFactory.createETF();
/* 120 */         nodeEtf.setName("REC");
/* 121 */         for (String name : names)
/*     */         {
/* 123 */           HiETF etf = HiETFFactory.createETF();
/* 124 */           etf.setName(name);
/* 125 */           etf.setValue((String)map.get(name));
/* 126 */           nodeEtf.appendNode(etf);
/*     */         }
/*     */ 
/* 129 */         writeToFile(fileName, nodeEtf.toString(), flag);
/* 130 */         if (log.isDebugEnabled()) {
/* 131 */           log.debug("WRITE TO FILE:[" + fileName + "], content:[" + nodeEtf.toString() + "] flag:[" + flag + "]");
/*     */         }
/*     */ 
/* 134 */         flag = true;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*     */       HiDataBaseUtil dbUtil;
/*     */       Iterator i$;
/* 138 */       if (type.equalsIgnoreCase("SQLExtend"))
/*     */       {
/* 140 */         String table = HiArgUtils.getStringNotNull(argsMap, "Table");
/*     */ 
/* 142 */         String cndSts = HiArgUtils.getStringNotNull(argsMap, "CndSts");
/*     */ 
/* 145 */         if ((StringUtils.isEmpty(table)) || (StringUtils.isEmpty(cndSts)))
/* 146 */           throw new HiException("213307");
/* 147 */         cndSts = HiDbtSqlHelper.getDynSentence(ctx, cndSts, root);
/*     */ 
/* 149 */         String sqlQueryMaintable = "SELECT * FROM " + table + " WHERE " + cndSts;
/*     */ 
/* 152 */         dbUtil = ctx.getDataBaseUtil();
/*     */ 
/* 154 */         List list = null;
/* 155 */         list = dbUtil.execQuery(sqlQueryMaintable);
/*     */ 
/* 157 */         if ((list == null) || (list.size() == 0))
/* 158 */           throw new HiException("220040");
/* 159 */         for (i$ = list.iterator(); i$.hasNext(); ) { Object obj = i$.next();
/*     */ 
/* 161 */           Map map = (Map)obj;
/* 162 */           String extTableName = (String)map.get("CTBLNam");
/* 163 */           String extTableKey = (String)map.get("extkey");
/*     */ 
/* 165 */           StringBuffer queryExtTableBf = new StringBuffer();
/* 166 */           queryExtTableBf.append("SELECT * FROM ");
/* 167 */           queryExtTableBf.append(extTableName);
/* 168 */           queryExtTableBf.append("WHERE");
/* 169 */           queryExtTableBf.append("extkey");
/* 170 */           queryExtTableBf.append("=");
/* 171 */           queryExtTableBf.append(extTableKey);
/*     */ 
/* 173 */           List extList = dbUtil.execQuery(queryExtTableBf.toString());
/* 174 */           Map extMap = (Map)extList.iterator();
/*     */ 
/* 177 */           HiETF nodeEtf = HiETFFactory.createETF();
/* 178 */           nodeEtf.setName("REC");
/*     */ 
/* 180 */           if (log.isDebugEnabled())
/*     */           {
/* 182 */             log.debug("ADD DATA FROM MAIN TABLE");
/*     */           }
/* 184 */           Collection names = map.keySet();
/* 185 */           for (String name : names)
/*     */           {
/* 187 */             HiETF etf = HiETFFactory.createETF();
/* 188 */             etf.setName(name);
/* 189 */             etf.setValue((String)map.get(name));
/* 190 */             nodeEtf.appendNode(etf);
/*     */           }
/* 192 */           if (log.isDebugEnabled())
/*     */           {
/* 194 */             log.debug("ADD DATA FROM EXTEND TABLE");
/*     */           }
/* 196 */           Collection extNames = extMap.keySet();
/* 197 */           for (String name : extNames)
/*     */           {
/* 199 */             HiETF etf = HiETFFactory.createETF();
/* 200 */             etf.setName(name);
/* 201 */             etf.setValue((String)extMap.get(name));
/* 202 */             nodeEtf.appendNode(etf);
/*     */           }
/* 204 */           writeToFile(fileName, nodeEtf.toString(), flag);
/* 205 */           flag = true;
/*     */         }
/*     */       }
/* 208 */       else if (type.equalsIgnoreCase("ETF"))
/*     */       {
/* 214 */         String grpName = argsMap.get("GrpName");
/* 215 */         String fieldsStr = argsMap.get("Fields");
/*     */ 
/* 231 */         HiETF etfRoot = mess.getETFBody();
/* 232 */         HiETF nodeEtf = null;
/*     */ 
/* 234 */         if (StringUtils.isEmpty(grpName))
/*     */         {
/* 236 */           if (StringUtils.isEmpty(fieldsStr))
/*     */           {
/* 238 */             nodeEtf = etfRoot.cloneNode();
/* 239 */             nodeEtf.setName("REC");
/*     */           }
/*     */           else
/*     */           {
/* 243 */             nodeEtf = HiETFFactory.createETF();
/* 244 */             nodeEtf.setName("REC");
/*     */ 
/* 246 */             Collection fields = getRegContent(fieldsStr, "|");
/*     */ 
/* 248 */             for (String field : fields)
/*     */             {
/* 250 */               nodeEtf.setChildValue(field, etfRoot.getChildValue(field));
/*     */             }
/*     */           }
/* 253 */           writeToFile(fileName, nodeEtf.toString(), flag);
/*     */         }
/*     */         else
/*     */         {
/* 257 */           int count = 0;
/*     */ 
/* 259 */           HiETF etfGroup = null;
/* 260 */           for (; ; ++count)
/*     */           {
/* 262 */             String tmpName = grpName + "_" + Integer.toString(count + 1);
/* 263 */             etfGroup = etfRoot.getChildNode(tmpName);
/* 264 */             if (etfGroup == null)
/*     */             {
/*     */               break;
/*     */             }
/*     */ 
/* 269 */             if (StringUtils.isEmpty(fieldsStr))
/*     */             {
/* 271 */               nodeEtf = etfGroup.cloneNode();
/* 272 */               nodeEtf.setName("REC");
/*     */             }
/*     */             else
/*     */             {
/* 276 */               nodeEtf = HiETFFactory.createETF();
/* 277 */               nodeEtf.setName("REC");
/*     */ 
/* 279 */               Collection fields = getRegContent(fieldsStr, "|");
/*     */ 
/* 281 */               for (String field : fields)
/*     */               {
/* 283 */                 nodeEtf.setChildValue(field, etfGroup.getChildValue(field));
/*     */               }
/*     */             }
/* 286 */             writeToFile(fileName, nodeEtf.toString(), flag);
/* 287 */             flag = true;
/*     */           }
/*     */         }
/* 290 */       } else if (type.equalsIgnoreCase("FILE"))
/*     */       {
/* 292 */         String inFile = argsMap.get("InFile");
/* 293 */         String cfgFile = argsMap.get("CfgFil");
/* 294 */         if ((StringUtils.isEmpty(inFile)) || (StringUtils.isEmpty(cfgFile)))
/* 295 */           throw new HiException("213307"); 
/*     */       }
/*     */     }
/* 297 */     return 0;
/*     */   }
/*     */ 
/*     */   public int QueryPage(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 314 */     HiMessage mess = ctx.getCurrentMsg();
/* 315 */     Logger log = HiLog.getLogger(mess);
/* 316 */     HiMultiDTO md = null;
/* 317 */     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
/*     */ 
/* 319 */     String queryCmd = HiArgUtils.getStringNotNull(argsMap, "QryCmd");
/*     */ 
/* 321 */     int re = 0;
/* 322 */     if ((StringUtils.isEmpty(recKey)) || (StringUtils.isEmpty(queryCmd)))
/* 323 */       throw new HiException("213307");
/* 324 */     recKey = recKey.trim();
/*     */ 
/* 326 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*     */ 
/* 329 */     int pageNum = 1;
/* 330 */     int pageRecCounts = 1;
/* 331 */     String pageNumStr = null;
/* 332 */     String pageRecCounts_str = null;
/* 333 */     String sql_query = "SELECT NUM_PERPAGE, CURR_PAGE  FROM PUBMULQRY WHERE REC_KEY='" + recKey + "'";
/*     */ 
/* 335 */     List list = dbUtil.execQuery(sql_query);
/*     */ 
/* 340 */     if (list == null)
/* 341 */       throw new HiException("220038");
/* 342 */     if (list.size() == 0) {
/* 343 */       return 2;
/*     */     }
/* 345 */     Map map = (Map)list.iterator().next();
/* 346 */     pageNumStr = (String)map.get("CURR_PAGE");
/* 347 */     pageRecCounts_str = (String)map.get("NUM_PERPAGE");
/*     */     try
/*     */     {
/* 351 */       pageRecCounts = Integer.parseInt(pageRecCounts_str);
/* 352 */       pageNum = Integer.parseInt(pageNumStr);
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/* 356 */       throw new HiException("220049");
/*     */     }
/* 358 */     if (log.isDebugEnabled())
/*     */     {
/* 360 */       log.debug("NUM PERPAGE IS:[" + pageRecCounts + "]");
/* 361 */       log.debug("CURRENT PAGE IS:[" + pageNum + "]");
/*     */     }
/* 363 */     md = new HiMultiDTO(recKey, pageRecCounts);
/* 364 */     String fileDir = HiICSProperty.getWorkDir() + File.separator + "tmp/Multi_";
/*     */ 
/* 366 */     md.setFileDir(fileDir);
/*     */ 
/* 368 */     md.setMultiQuery(HiQueryFactory.getProcessor(md));
/*     */ 
/* 370 */     if (log.isDebugEnabled())
/*     */     {
/* 372 */       log.debug("DIR OF DATA SOURCE FILE IS:[" + fileDir + "]");
/*     */     }
/*     */ 
/* 375 */     if (queryCmd.equalsIgnoreCase("050000"))
/*     */     {
/* 378 */       if (log.isDebugEnabled())
/*     */       {
/* 380 */         log.debug("QUERY FIRST PAGE...");
/*     */       }
/* 382 */       pageNum = 1;
/* 383 */       md.setPageNum(pageNum);
/*     */     }
/* 385 */     else if (queryCmd.equalsIgnoreCase("060000"))
/*     */     {
/* 388 */       if (log.isDebugEnabled())
/*     */       {
/* 390 */         log.debug("QUERY END PAGE...");
/*     */       }
/* 392 */       pageNum = HiMultiService.getTotalPage(md);
/* 393 */       md.setEndPage(true);
/* 394 */       md.setPageNum(pageNum);
/*     */     }
/* 396 */     else if (queryCmd.equalsIgnoreCase("070000"))
/*     */     {
/* 398 */       if (log.isDebugEnabled())
/*     */       {
/* 400 */         log.debug("PAGEUP, AND CURRENT PAGE IS:[" + pageNum + "]");
/*     */       }
/* 402 */       --pageNum;
/* 403 */       pageNum = (pageNum < 1) ? 1 : pageNum;
/* 404 */       md.setPageNum(pageNum);
/*     */     }
/* 406 */     else if (queryCmd.equalsIgnoreCase("080000"))
/*     */     {
/* 409 */       if (log.isDebugEnabled())
/*     */       {
/* 411 */         log.debug("PAGEDOWN, AND CURRENT PAGE IS:[" + pageNum + "]");
/*     */       }
/* 413 */       ++pageNum;
/* 414 */       int totalPage = HiMultiService.getTotalPage(md);
/* 415 */       pageNum = (pageNum > totalPage) ? totalPage : pageNum;
/* 416 */       md.setPageNum(pageNum);
/* 417 */     } else if (queryCmd.indexOf("00") == 0)
/*     */     {
/* 420 */       String pageStr = queryCmd.substring(2);
/*     */       try
/*     */       {
/* 423 */         pageNum = Integer.parseInt(pageStr);
/*     */       }
/*     */       catch (NumberFormatException e) {
/* 426 */         throw new HiException("220049", "Page num is not int type");
/*     */       }
/*     */ 
/* 429 */       int totalPage = HiMultiService.getTotalPage(md);
/* 430 */       pageNum = (pageNum > totalPage) ? totalPage : pageNum;
/* 431 */       md.setPageNum(pageNum);
/* 432 */       if (log.isDebugEnabled()) {
/* 433 */         log.debug("QUERY CERTAIN PAGE, pageNum=" + pageNum);
/*     */       }
/*     */     }
/* 436 */     getCertainPage(md, mess, ctx);
/*     */ 
/* 438 */     String time = getSysTime();
/* 439 */     String date = getSysDate();
/*     */ 
/* 441 */     StringBuffer updateSqlBf = new StringBuffer();
/* 442 */     updateSqlBf.append("UPDATE  PUBMULQRY SET CURR_PAGE=");
/* 443 */     updateSqlBf.append(pageNum);
/* 444 */     updateSqlBf.append(" ,SYS_DATE='");
/* 445 */     updateSqlBf.append(time);
/* 446 */     updateSqlBf.append("' WHERE REC_KEY='");
/* 447 */     updateSqlBf.append(recKey);
/* 448 */     updateSqlBf.append("'");
/*     */ 
/* 450 */     String sql_update = "update PUBMULQRY set CURR_PAGE=" + pageNum + " ,SYS_DATE='" + date + "' ,SYS_TIME='" + time + "' where REC_KEY='" + recKey + "'";
/*     */ 
/* 453 */     log.debug("prepare to update PUBMULQRY and sql_update=" + sql_update);
/* 454 */     dbUtil.execUpdate(updateSqlBf.toString());
/* 455 */     return re;
/*     */   }
/*     */ 
/*     */   public int QueryFirstPage(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 472 */     HiMessage mess = ctx.getCurrentMsg();
/* 473 */     Logger log = HiLog.getLogger(mess);
/* 474 */     log.debug("begin to query firstPage");
/* 475 */     HiMultiDTO md = null;
/* 476 */     String recKey = argsMap.get("RecKey");
/* 477 */     String numPerPage_str = argsMap.get("RecNumPerPage");
/* 478 */     log.debug("recKey=" + recKey + " RecNumPerPage=" + numPerPage_str);
/* 479 */     if ((StringUtils.isEmpty(recKey)) || (StringUtils.isEmpty(numPerPage_str))) {
/* 480 */       throw new HiException("213307");
/*     */     }
/* 482 */     int numPerPage = 1;
/*     */     try
/*     */     {
/* 485 */       numPerPage = Integer.parseInt(numPerPage_str);
/*     */     }
/*     */     catch (NumberFormatException e) {
/* 488 */       throw new HiException("220049", "Page num is not int type");
/*     */     }
/*     */ 
/* 491 */     String fileDir = HiICSProperty.getWorkDir() + File.separator + "tmp/Multi_";
/*     */ 
/* 493 */     md = new HiMultiDTO(recKey.trim(), numPerPage);
/* 494 */     md.setFirstPage(true);
/* 495 */     md.setPageNum(1);
/* 496 */     md.setFileDir(fileDir);
/* 497 */     md.setPageRecCounts(numPerPage);
/* 498 */     log.debug("MultiDTO has been created");
/* 499 */     md.setMultiQuery(HiQueryFactory.getProcessor(md));
/* 500 */     int totalPage = HiMultiService.getTotalPage(md);
/* 501 */     int total = HiMultiService.getTotalRec(md);
/*     */ 
/* 503 */     log.debug("totalpage=" + totalPage + " toal=" + total);
/*     */ 
/* 505 */     String time = getSysTime();
/* 506 */     String date = getSysDate();
/* 507 */     log.debug("time=[" + time + "] date=[" + date + "]");
/* 508 */     log.debug("md.getRecKey() =" + md.getRecKey());
/* 509 */     log.debug("md.getPageRecCounts()=" + md.getPageRecCounts());
/*     */ 
/* 511 */     StringBuffer delSqlBf = new StringBuffer();
/* 512 */     delSqlBf.append("delete from PUBMULQRY where REC_KEY='");
/* 513 */     delSqlBf.append(md.getRecKey());
/* 514 */     delSqlBf.append("'");
/*     */ 
/* 516 */     StringBuffer insertSqlBf = new StringBuffer();
/* 517 */     insertSqlBf.append("INSERT INTO PUBMULQRY(REC_KEY,NUM_PERPAGE,CURR_PAGE,TOTAL,TOTAL_PAGE,SYS_DATE,SYS_TIME) values('");
/*     */ 
/* 519 */     insertSqlBf.append(md.getRecKey());
/* 520 */     insertSqlBf.append("',");
/* 521 */     insertSqlBf.append(md.getPageRecCounts());
/* 522 */     insertSqlBf.append(",1,");
/* 523 */     insertSqlBf.append(total);
/* 524 */     insertSqlBf.append(",");
/* 525 */     insertSqlBf.append(totalPage);
/* 526 */     insertSqlBf.append(",'");
/* 527 */     insertSqlBf.append(date);
/* 528 */     insertSqlBf.append("','");
/* 529 */     insertSqlBf.append(time);
/* 530 */     insertSqlBf.append("')");
/*     */ 
/* 532 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/* 533 */     dbUtil.execUpdate(delSqlBf.toString());
/* 534 */     dbUtil.execUpdate(insertSqlBf.toString());
/*     */ 
/* 536 */     getCertainPage(md, mess, ctx);
/*     */ 
/* 538 */     return 0;
/*     */   }
/*     */ 
/*     */   private void getCertainPage(HiMultiDTO md, HiMessage mess, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 554 */     Logger log = HiLog.getLogger(mess);
/* 555 */     HiETF rootEtf = mess.getETFBody();
/* 556 */     int totalPage = HiMultiService.getTotalPage(md);
/* 557 */     int recNum = HiMultiService.getCurrPageRecCounts(md);
/* 558 */     int totalRecNum = HiMultiService.getTotalRec(md);
/* 559 */     Collection etfChildren = HiMultiService.query(md);
/* 560 */     int i = 0;
/* 561 */     for (HiETF etf : etfChildren)
/*     */     {
/* 563 */       rootEtf.appendNode(etf);
/*     */     }
/* 565 */     rootEtf.setChildValue("REC_NUM", String.valueOf(recNum));
/* 566 */     rootEtf.setChildValue("PAG_CNT", String.valueOf(totalPage));
/* 567 */     rootEtf.setChildValue("PAG_NO", String.valueOf(md.getPageNum()));
/* 568 */     rootEtf.setChildValue("TOT_REC_NUM", String.valueOf(totalRecNum));
/*     */   }
/*     */ 
/*     */   private String getSysTime()
/*     */   {
/* 579 */     Calendar cd = Calendar.getInstance();
/* 580 */     long timeValue = cd.getTimeInMillis();
/* 581 */     String time = new SimpleDateFormat("hhmmss").format(Long.valueOf(timeValue));
/* 582 */     return time;
/*     */   }
/*     */ 
/*     */   private String getSysDate()
/*     */   {
/* 592 */     Calendar cd = Calendar.getInstance();
/* 593 */     long timeValue = cd.getTimeInMillis();
/* 594 */     String date = new SimpleDateFormat("yyyyMMdd").format(Long.valueOf(timeValue));
/* 595 */     return date;
/*     */   }
/*     */ 
/*     */   private Collection<String> getRegContent(String content, String reg)
/*     */   {
/* 609 */     if (content == null)
/* 610 */       return null;
/* 611 */     Collection reStrs = new ArrayList();
/* 612 */     int start = 0;
/* 613 */     int end = 0;
/* 614 */     while (content.length() > 0)
/*     */     {
/* 616 */       end = content.indexOf(reg);
/* 617 */       String part = content.substring(start, end);
/* 618 */       reStrs.add(part);
/* 619 */       content = content.substring(end + 1);
/*     */     }
/* 621 */     return reStrs;
/*     */   }
/*     */ 
/*     */   private void writeToFile(String fileName, String content, boolean isAdd)
/*     */     throws HiException
/*     */   {
/* 639 */     File file = new File(fileName);
/* 640 */     FileOutputStream fos = null;
/*     */     try
/*     */     {
/* 643 */       fos = new FileOutputStream(file, isAdd);
/* 644 */       byte[] buf = content.getBytes();
/* 645 */       fos.write(String.format("%08d", new Object[] { Integer.valueOf(content.length()) }).getBytes());
/* 646 */       fos.write(buf);
/*     */       try
/*     */       {
/* 656 */         fos.close();
/*     */       }
/*     */       catch (IOException e) {
/* 659 */         throw new HiException("220079", fileName, e);
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 656 */         fos.close();
/*     */       }
/*     */       catch (IOException e) {
/* 659 */         throw new HiException("220079", fileName, e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }