/*     */ package com.hisun.message;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiMessageHelper
/*     */ {
/*     */   public static String getCurEtfLevel(HiMessage msg)
/*     */   {
/*  17 */     String curLevel = msg.getHeadItem("ETF_LEVEL");
/*     */ 
/*  19 */     if (curLevel == null) {
/*  20 */       return "";
/*     */     }
/*     */ 
/*  23 */     return curLevel;
/*     */   }
/*     */ 
/*     */   public static void setCurEtfLevel(HiMessage msg, String curLevel)
/*     */   {
/*  32 */     msg.setHeadItem("ETF_LEVEL", curLevel);
/*     */   }
/*     */ 
/*     */   public static void addEtfItem(HiMessage etfMsg, String name, String item)
/*     */     throws HiException
/*     */   {
/*  48 */     HiETF etfBody = (HiETF)etfMsg.getBody();
/*     */     try {
/*  50 */       if (name.startsWith("ROOT."))
/*  51 */         etfBody.setGrandChildNode(name, item);
/*     */       else
/*  53 */         etfBody.setGrandChildNode(getCurEtfLevel(etfMsg) + name, item);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  57 */       throw new HiException("213139", new String[] { name, item }, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getEtfItem(HiMessage etfMsg, String name)
/*     */   {
/*  72 */     HiETF etfBody = (HiETF)etfMsg.getBody();
/*     */ 
/*  74 */     if (name.startsWith("ROOT.")) {
/*  75 */       return etfBody.getGrandChildValue(name);
/*     */     }
/*  77 */     return etfBody.getGrandChildValue(getCurEtfLevel(etfMsg) + name);
/*     */   }
/*     */ 
/*     */   public static boolean isInnerMessage(HiMessage msg)
/*     */   {
/*  87 */     return StringUtils.equalsIgnoreCase(msg.getHeadItem("ECT"), "text/etf");
/*     */   }
/*     */ 
/*     */   public static boolean isOutterMessage(HiMessage msg)
/*     */   {
/*  97 */     return (!(isInnerMessage(msg)));
/*     */   }
/*     */ 
/*     */   public static boolean isRequestMessage(HiMessage msg)
/*     */   {
/* 106 */     return StringUtils.equalsIgnoreCase(msg.getHeadItem("SCH"), "rq");
/*     */   }
/*     */ 
/*     */   public static boolean isResponseMessage(HiMessage msg)
/*     */   {
/* 116 */     return StringUtils.equalsIgnoreCase(msg.getHeadItem("SCH"), "rp");
/*     */   }
/*     */ 
/*     */   public static void setRequestMessage(HiMessage msg)
/*     */   {
/* 125 */     msg.setHeadItem("SCH", "rq");
/*     */   }
/*     */ 
/*     */   public static void setResponseMessage(HiMessage msg)
/*     */   {
/* 133 */     msg.setHeadItem("SCH", "rp");
/*     */   }
/*     */ 
/*     */   public static void setMessageTypePlain(HiMessage msg)
/*     */   {
/* 141 */     msg.setHeadItem("ECT", "text/plain");
/*     */   }
/*     */ 
/*     */   public static void setMessageTypeETF(HiMessage msg)
/*     */   {
/* 149 */     msg.setHeadItem("ECT", "text/etf");
/*     */   }
/*     */ 
/*     */   public static void setMessageTypeXML(HiMessage msg)
/*     */   {
/* 157 */     msg.setHeadItem("ECT", "text/xml");
/*     */   }
/*     */ 
/*     */   public static void setUnpackMessage(HiMessage msg, HiByteBuffer byteBuffer) {
/* 161 */     msg.setHeadItem("PlainText", byteBuffer);
/* 162 */     msg.setHeadItem("ECT", "text/plain");
/* 163 */     msg.setHeadItem("SCH", "rq");
/*     */   }
/*     */ 
/*     */   public static void setPlainOffset(HiMessage msg, int offset)
/*     */   {
/* 173 */     msg.setHeadItem("PlainOffset", String.valueOf(offset));
/*     */   }
/*     */ 
/*     */   public static HiByteBuffer getUnpackMessageBuffer(HiMessage msg) {
/* 177 */     HiByteBuffer byteBuffer = (HiByteBuffer)msg.getObjectHeadItem("PlainText");
/*     */ 
/* 179 */     msg.delHeadItem("PlainText");
/* 180 */     return byteBuffer;
/*     */   }
/*     */ 
/*     */   public static void setPackMessage(HiMessage msg, HiByteBuffer byteBuffer) {
/* 184 */     msg.setHeadItem("PlainText", byteBuffer);
/* 185 */     msg.setHeadItem("ECT", "text/etf");
/* 186 */     msg.setHeadItem("SCH", "rp");
/*     */   }
/*     */ 
/*     */   public static void setInnerMessage(HiMessage msg)
/*     */   {
/* 194 */     msg.setHeadItem("ECT", "text/etf");
/*     */   }
/*     */ 
/*     */   public static void setOutterMessage(HiMessage msg)
/*     */   {
/* 202 */     msg.setHeadItem("ECT", "text/plain");
/*     */   }
/*     */ 
/*     */   public static String getMessageTextType(HiMessage msg)
/*     */   {
/* 211 */     return msg.getHeadItem("ECT");
/*     */   }
/*     */ 
/*     */   public static HiMessage setMessageTmOut(HiMessage msg, int tmOut)
/*     */   {
/* 220 */     long curtime = System.currentTimeMillis();
/* 221 */     msg.setHeadItem("STM", new Long(curtime));
/*     */ 
/* 223 */     if (tmOut > 0) {
/* 224 */       long exptime = curtime + tmOut * 1000;
/* 225 */       msg.setHeadItem("ETM", new Long(exptime));
/*     */     }
/* 227 */     return msg;
/*     */   }
/*     */ 
/*     */   public static HiMessage setMessageTmOut(HiMessage msg)
/*     */   {
/* 236 */     long curtime = System.currentTimeMillis();
/* 237 */     msg.setHeadItem("ETM", new Long(curtime - 100L));
/* 238 */     return msg;
/*     */   }
/*     */ }