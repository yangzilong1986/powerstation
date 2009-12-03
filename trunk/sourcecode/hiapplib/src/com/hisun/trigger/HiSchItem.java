/*     */ package com.hisun.trigger;
/*     */ 
/*     */ import com.hisun.dispatcher.HiRouterOut;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.util.Date;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class HiSchItem
/*     */ {
/*  37 */   private HiCronExpression cron = null;
/*     */ 
/*  42 */   private String strRepeat_send = "0";
/*     */ 
/*  47 */   private String strIstxn = null;
/*     */ 
/*  49 */   private Date lastDate = null;
/*     */ 
/*  54 */   private String strFuncName = null;
/*     */ 
/*  59 */   private String strFuncpath = null;
/*     */ 
/*  64 */   private String strHour = "*";
/*     */ 
/*  69 */   private String strID = null;
/*     */ 
/*  80 */   private String strMinute = "*";
/*     */ 
/*  85 */   private String strMonitor = "0";
/*     */ 
/*  90 */   private String strMonth = "*";
/*     */ 
/*  95 */   private String strMonthday = "*";
/*     */ 
/* 100 */   private String strMonthweek = "*";
/*     */ 
/* 105 */   private String strMsgType = "PLTIN0";
/*     */ 
/* 110 */   private String strObjSvr = null;
/*     */ 
/* 115 */   private String strSndFlag = null;
/*     */ 
/* 120 */   private String strTrace = "0";
/*     */ 
/* 125 */   private String strTxncod = null;
/*     */ 
/* 130 */   private String strTxnDat = null;
/*     */ 
/* 132 */   private int tmOut = 30;
/*     */ 
/* 139 */   private String strTxnDataType = "text/plain";
/*     */ 
/* 144 */   private String strWeekday = "*";
/*     */   private String logId;
/* 265 */   private Node dataNode = null;
/*     */ 
/* 267 */   private String dataText = null;
/*     */ 
/*     */   public HiCronExpression getCronExpression()
/*     */   {
/* 156 */     return this.cron;
/*     */   }
/*     */ 
/*     */   public Date getLastDate()
/*     */   {
/* 161 */     return this.lastDate;
/*     */   }
/*     */ 
/*     */   public void init()
/*     */     throws HiException
/*     */   {
/* 167 */     this.cron = new HiCronExpression(this.strMinute, this.strHour, this.strMonthday, this.strMonth, this.strWeekday, this.strMonthweek);
/*     */ 
/* 171 */     Logger log = HiTimeTrigger.log;
/* 172 */     if (log.isDebugEnabled())
/* 173 */       log.debug("SchItem info:" + this.cron);
/*     */   }
/*     */ 
/*     */   public void setRepeat_send(String strRepeat_send)
/*     */   {
/* 178 */     if (StringUtils.isNotEmpty(strRepeat_send))
/* 179 */       this.strRepeat_send = strRepeat_send;
/*     */   }
/*     */ 
/*     */   public boolean isRepeatSend()
/*     */   {
/* 185 */     return (!(this.strRepeat_send.equals("1")));
/*     */   }
/*     */ 
/*     */   public void setFuncname(String strFuncName)
/*     */   {
/* 192 */     if (StringUtils.isNotEmpty(strFuncName))
/* 193 */       this.strFuncName = strFuncName;
/*     */   }
/*     */ 
/*     */   public void setFuncpath(String strFuncpath)
/*     */   {
/* 198 */     if (StringUtils.isNotEmpty(strFuncpath))
/* 199 */       this.strFuncpath = strFuncpath;
/*     */   }
/*     */ 
/*     */   public void setHour(String strHour)
/*     */   {
/* 204 */     if (StringUtils.isNotEmpty(strHour))
/* 205 */       this.strHour = strHour;
/*     */   }
/*     */ 
/*     */   public void setId(String strID)
/*     */   {
/* 210 */     this.strID = strID;
/*     */   }
/*     */ 
/*     */   public void setIstxn(String strIstxn)
/*     */   {
/* 215 */     this.strIstxn = strIstxn;
/*     */   }
/*     */ 
/*     */   public void setLastDate(Date lastDate)
/*     */   {
/* 220 */     this.lastDate = lastDate;
/*     */   }
/*     */ 
/*     */   public void setMinute(String strMinute)
/*     */   {
/* 225 */     if (StringUtils.isNotEmpty(strMinute))
/* 226 */       this.strMinute = strMinute;
/*     */   }
/*     */ 
/*     */   public void setMonitor(String strMonitor)
/*     */   {
/* 231 */     this.strMonitor = strMonitor;
/*     */   }
/*     */ 
/*     */   public void setMonth(String strMonth)
/*     */   {
/* 236 */     if (!(StringUtils.isNotEmpty(strMonth)))
/*     */       return;
/* 238 */     this.strMonth = strMonth;
/*     */   }
/*     */ 
/*     */   public void setMonthday(String strMonthday)
/*     */   {
/* 244 */     if (StringUtils.isNotEmpty(strMonthday))
/* 245 */       this.strMonthday = strMonthday;
/*     */   }
/*     */ 
/*     */   public void setMonthweek(String strMonthweek)
/*     */   {
/* 250 */     if (StringUtils.isNotEmpty(strMonthweek))
/* 251 */       this.strMonthweek = strMonthweek;
/*     */   }
/*     */ 
/*     */   public void setMsgtype(String strMsgType)
/*     */   {
/* 257 */     this.strMsgType = strMsgType;
/*     */   }
/*     */ 
/*     */   public void setObjsvr(String strObjSvr)
/*     */   {
/* 262 */     this.strObjSvr = strObjSvr;
/*     */   }
/*     */ 
/*     */   public void setProperty(Object value)
/*     */     throws HiException
/*     */   {
/* 271 */     this.dataNode = ((Node)value);
/* 272 */     Node node = this.dataNode.getFirstChild();
/* 273 */     this.dataText = documentToString(node);
/*     */ 
/* 275 */     if (HiTimeTrigger.log.isDebugEnabled())
/* 276 */       HiTimeTrigger.log.debug("DATE NODE[" + this.dataText + "]");
/*     */   }
/*     */ 
/*     */   public String documentToString(Node doc)
/*     */   {
/*     */     try
/*     */     {
/* 283 */       Transformer transformer = newTransformer();
/* 284 */       DOMSource source = new DOMSource(doc);
/* 285 */       ByteArrayOutputStream sw = new ByteArrayOutputStream();
/* 286 */       StreamResult result = new StreamResult(sw);
/* 287 */       transformer.transform(source, result);
/* 288 */       sw.close();
/* 289 */       String xml = sw.toString();
/* 290 */       return xml;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 294 */       e.printStackTrace();
/*     */     }
/* 296 */     return null;
/*     */   }
/*     */ 
/*     */   private Transformer newTransformer()
/*     */   {
/*     */     try
/*     */     {
/* 303 */       Transformer transformer = TransformerFactory.newInstance().newTransformer();
/*     */ 
/* 311 */       return transformer;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 315 */       e.printStackTrace(); }
/* 316 */     return null;
/*     */   }
/*     */ 
/*     */   public void setSndflag(String strSndFlag)
/*     */   {
/* 322 */     this.strSndFlag = strSndFlag;
/*     */   }
/*     */ 
/*     */   public void setTrace(String strTrace)
/*     */   {
/* 327 */     this.strTrace = strTrace;
/*     */   }
/*     */ 
/*     */   public void setTxncod(String strTxncod)
/*     */   {
/* 332 */     this.strTxncod = strTxncod;
/*     */   }
/*     */ 
/*     */   public void setTxndat(String strTxnDat)
/*     */   {
/* 337 */     this.strTxnDat = strTxnDat;
/*     */   }
/*     */ 
/*     */   public void setTxndatatype(String strTxnDataType)
/*     */   {
/* 342 */     this.strTxnDataType = strTxnDataType;
/*     */   }
/*     */ 
/*     */   public void setWeekday(String strWeekday)
/*     */   {
/* 347 */     if (StringUtils.isNotEmpty(strWeekday))
/* 348 */       this.strWeekday = strWeekday;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx, Date currentDate)
/*     */     throws HiException
/*     */   {
/* 359 */     if ("1".equals(this.strSndFlag))
/* 360 */       return;
/* 361 */     Logger log = HiTimeTrigger.log;
/* 362 */     HiMessage mess = ctx.getCurrentMsg();
/* 363 */     if (StringUtils.isNotEmpty(this.logId)) {
/* 364 */       mess.setHeadItem("FID", this.logId);
/*     */     }
/* 366 */     mess.setHeadItem("SCH", "rq");
/* 367 */     mess.setHeadItem("ECT", this.strTxnDataType);
/* 368 */     mess.setHeadItem("STC", this.strTxncod);
/* 369 */     mess.setHeadItem("STF", this.strTrace);
/* 370 */     long l = currentDate.getTime();
/* 371 */     mess.setHeadItem("STM", new Long(l));
/* 372 */     mess.setHeadItem("ETM", new Long(l + this.tmOut * 1000));
/*     */ 
/* 374 */     if (log.isDebugEnabled()) {
/* 375 */       log.debug("MsgType[" + this.strMsgType + "]");
/*     */     }
/* 377 */     if (StringUtils.isNotEmpty(this.strMsgType)) {
/* 378 */       mess.setType(this.strMsgType);
/*     */     }
/* 380 */     if (log.isDebugEnabled())
/*     */     {
/* 382 */       log.debug("istxn[" + this.strIstxn + "]");
/* 383 */       log.debug("setLastDate[" + currentDate + "]");
/*     */     }
/*     */ 
/* 386 */     setLastDate(currentDate);
/* 387 */     if (this.strIstxn.equalsIgnoreCase("0"))
/*     */     {
/*     */       HiByteBuffer byteBuffer;
/* 389 */       mess.setHeadItem("SYN", "N");
/*     */ 
/* 391 */       if (StringUtils.isNotEmpty(this.strObjSvr)) {
/* 392 */         mess.setHeadItem("SDT", this.strObjSvr);
/*     */       }
/* 394 */       if (log.isDebugEnabled()) {
/* 395 */         log.debug("txndatatype[" + this.strTxnDataType + "]");
/*     */       }
/* 397 */       if (this.strTxnDataType.equalsIgnoreCase("text/xml"))
/*     */       {
/* 400 */         if (this.dataText != null)
/*     */         {
/* 402 */           byteBuffer = new HiByteBuffer(this.dataText.getBytes());
/*     */ 
/* 404 */           mess.setBody(byteBuffer);
/*     */         }
/*     */       }
/* 407 */       else if (this.strTxnDataType.equalsIgnoreCase("text/etf"))
/*     */       {
/* 409 */         if (this.dataText != null)
/*     */         {
/* 411 */           HiETF etf = HiETFFactory.createETF(this.dataText);
/* 412 */           mess.setBody(etf);
/*     */         }
/*     */ 
/*     */       }
/* 418 */       else if (this.strTxnDat != null)
/*     */       {
/* 420 */         byteBuffer = new HiByteBuffer(this.strTxnDat.getBytes());
/*     */ 
/* 422 */         mess.setBody(byteBuffer);
/*     */       }
/*     */ 
/* 426 */       HiRouterOut.process(ctx);
/*     */ 
/* 428 */       if (!(this.strMonitor.equalsIgnoreCase("1"))) {
/*     */         return;
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 435 */       HiSchUtilites.execFunction(this.strFuncpath, this.strFuncName);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getTmout()
/*     */   {
/* 441 */     return this.tmOut;
/*     */   }
/*     */ 
/*     */   public void setTmout(int tmOut)
/*     */   {
/* 446 */     this.tmOut = tmOut;
/*     */   }
/*     */ 
/*     */   public String getLogId() {
/* 450 */     return this.logId;
/*     */   }
/*     */ 
/*     */   public void setLogId(String logId)
/*     */   {
/* 455 */     this.logId = logId;
/*     */   }
/*     */ }