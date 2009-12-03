/*     */ package com.hisun.component.web;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.atc.common.HiDbtSqlHelper;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class UpdateRssSource
/*     */ {
/*     */   private Logger log;
/*  29 */   private static Map RssItemSpec = new HashMap();
/*     */ 
/*     */   public UpdateRssSource()
/*     */   {
/*  28 */     this.log = HiLog.getLogger("updatersssource.trc");
/*     */   }
/*     */ 
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  41 */     HiMessage msg = ctx.getCurrentMsg();
/*  42 */     HiETF etfBody = msg.getETFBody();
/*  43 */     String rssSql = HiArgUtils.getStringNotNull(args, "rssSql");
/*  44 */     String rssFile = HiArgUtils.getStringNotNull(args, "rssFile");
/*  45 */     String rssTit = args.get("title");
/*  46 */     String rssDesc = args.get("description");
/*  47 */     if (rssTit == null)
/*     */     {
/*  49 */       rssTit = "RSS 订阅";
/*     */     }
/*  51 */     if (rssDesc == null)
/*     */     {
/*  53 */       rssDesc = "RSS 订阅";
/*     */     }
/*     */ 
/*  56 */     String sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, rssSql, etfBody);
/*  57 */     if (this.log.isDebugEnabled()) {
/*  58 */       this.log.debug("UpdateRssSource: [" + sqlSentence + "]");
/*     */     }
/*     */ 
/*  61 */     List articleList = ctx.getDataBaseUtil().execQuery(sqlSentence);
/*     */ 
/*  63 */     StringBuffer sb = new StringBuffer(1024);
/*  64 */     sb.append("<?xml version='1.0' encoding='GBK'?>");
/*  65 */     sb.append("\n");
/*  66 */     sb.append("<rss version='2.0'>");
/*  67 */     sb.append("<channel>");
/*  68 */     sb.append("<title>");
/*  69 */     sb.append(rssTit);
/*  70 */     sb.append("</title>");
/*  71 */     sb.append("<description>");
/*  72 */     sb.append(rssDesc);
/*  73 */     sb.append("</description>");
/*     */ 
/*  75 */     if ((articleList != null) && (articleList.size() != 0))
/*     */     {
/*  77 */       Map queryRec = null;
/*  78 */       Map.Entry recEntry = null;
/*  79 */       Iterator recIt = null;
/*     */ 
/*  81 */       int i = 0;
/*     */ 
/*  83 */       for (i = 0; i < articleList.size(); ++i) {
/*  84 */         queryRec = (Map)articleList.get(i);
/*  85 */         recIt = queryRec.entrySet().iterator();
/*  86 */         sb.append("\n");
/*  87 */         sb.append("<item>");
/*     */ 
/*  89 */         while (recIt.hasNext())
/*     */         {
/*  91 */           recEntry = (Map.Entry)recIt.next();
/*  92 */           String col = getSpecName((String)recEntry.getKey());
/*  93 */           if (col == null)
/*     */             continue;
/*  95 */           sb.append("\n");
/*  96 */           sb.append("<");
/*  97 */           sb.append(col);
/*  98 */           sb.append(">");
/*     */ 
/* 100 */           sb.append(recEntry.getValue());
/*     */ 
/* 102 */           sb.append("</");
/* 103 */           sb.append(col);
/* 104 */           sb.append(">");
/*     */         }
/*     */ 
/* 108 */         sb.append("</item>");
/*     */       }
/*     */     }
/* 111 */     sb.append("\n");
/* 112 */     sb.append("</channel>");
/* 113 */     sb.append("</rss>");
/*     */     try
/*     */     {
/* 118 */       DataOutputStream dout = new DataOutputStream(new FileOutputStream(rssFile));
/* 119 */       dout.write(sb.toString().getBytes());
/*     */     } catch (Exception e) {
/* 121 */       throw new HiException(e);
/*     */     }
/* 123 */     return 0;
/*     */   }
/*     */ 
/*     */   private String getSpecName(String key)
/*     */   {
/* 128 */     return ((String)RssItemSpec.get(key));
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  31 */     RssItemSpec.put("TITLE", "title");
/*  32 */     RssItemSpec.put("DESCRIPTION", "description");
/*  33 */     RssItemSpec.put("PUBDATE", "pubDate");
/*  34 */     RssItemSpec.put("AUTHOR", "author");
/*  35 */     RssItemSpec.put("CATEGORY", "category");
/*  36 */     RssItemSpec.put("LINK", "link");
/*     */   }
/*     */ }