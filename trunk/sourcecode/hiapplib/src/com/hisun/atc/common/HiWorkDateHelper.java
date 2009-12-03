/*     */ package com.hisun.atc.common;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiDateUtils;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiWorkDateHelper
/*     */ {
/*     */   public static final int HLDFLGLEN = 372;
/*     */ 
/*     */   public static String[] getHoliday(HiMessageContext ctx, String areCod)
/*     */     throws HiException
/*     */   {
/*  32 */     HiETF recRoot = HiETFFactory.createETF("REC", "");
/*     */ 
/*  34 */     String sqlCmd = "SELECT Year,HldFlg FROM cimhldtbl WHERE AreCod='" + areCod + "' ORDER BY Year";
/*     */ 
/*  37 */     int recNum = HiDbtUtils.dbtsqlquery(sqlCmd, recRoot, ctx);
/*  38 */     if (recNum == 0)
/*     */     {
/*  40 */       return null;
/*     */     }
/*     */ 
/*  44 */     String lastYear = null;
/*     */ 
/*  46 */     String[] hldFlgInfo = new String[2];
/*  47 */     StringBuffer bb = new StringBuffer(372 * recNum + 3);
/*  48 */     for (int i = 1; i <= recNum; ++i)
/*     */     {
/*  50 */       HiETF recNode = recRoot.getChildNode("REC_" + i);
/*  51 */       if (recNode == null)
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/*  56 */       String curYear = recNode.getChildValue("Year");
/*  57 */       if (i == 1)
/*     */       {
/*  59 */         hldFlgInfo[0] = curYear;
/*     */       }
/*     */ 
/*  62 */       if ((lastYear != null) && (NumberUtils.toInt(curYear) - NumberUtils.toInt(lastYear) != 1))
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/*  68 */       String hldFlg = recNode.getChildValue("HldFlg");
/*     */ 
/*  71 */       hldFlg = StringUtils.rightPad(hldFlg, 372);
/*     */ 
/*  73 */       bb.append(hldFlg);
/*  74 */       lastYear = curYear;
/*     */     }
/*  76 */     hldFlgInfo[1] = bb.toString();
/*  77 */     recRoot = null;
/*     */ 
/*  79 */     return hldFlgInfo;
/*     */   }
/*     */ 
/*     */   public static int date2pos(String dateStr, String refYear, int numYear)
/*     */   {
/*     */     try
/*     */     {
/* 100 */       int curYear = NumberUtils.toInt(dateStr.substring(0, 4));
/* 101 */       int difYear = curYear - NumberUtils.toInt(refYear);
/* 102 */       if ((difYear >= numYear) || (difYear < 0))
/*     */       {
/* 104 */         return -1;
/*     */       }
/*     */ 
/* 108 */       Date refDate = HiDateUtils.parseDate(curYear + "0101");
/* 109 */       Date curDate = HiDateUtils.parseDate(dateStr);
/*     */ 
/* 111 */       int seq = HiDateUtils.diffDate(curDate, refDate);
/* 112 */       if (seq < 0)
/*     */       {
/* 114 */         return -1;
/*     */       }
/*     */ 
/* 119 */       return (difYear * 372 + seq);
/*     */     }
/*     */     catch (HiException e) {
/*     */     }
/* 123 */     return -1;
/*     */   }
/*     */ 
/*     */   public static String pos2date(String refYear, int pos)
/*     */   {
/* 139 */     int curYear = NumberUtils.toInt(refYear) + pos / 372;
/*     */     try
/*     */     {
/* 142 */       Date curDate = HiDateUtils.addDate(HiDateUtils.parseDate(curYear + "0101"), pos % 372);
/* 143 */       return HiDateUtils.formatDateByFormat(curDate, "yyyyMMdd");
/*     */     } catch (HiException e) {
/*     */     }
/* 146 */     return null;
/*     */   }
/*     */ }