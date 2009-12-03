/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.engine.HiITFEngineModel;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSysException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiGroup extends HiITFEngineModel
/*     */ {
/*     */   protected final Logger logger;
/*     */   private String name;
/*     */   private int offset;
/*     */   private int repeat_num;
/*     */   private String repeat_name;
/*     */   private int length;
/*     */   private HiExpression lengthExp;
/*     */   private String record_name;
/*     */   private DeliParser deliParser;
/*     */ 
/*     */   public HiGroup()
/*     */   {
/*  29 */     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*     */ 
/*  41 */     this.offset = 0;
/*     */ 
/*  45 */     this.repeat_num = -1;
/*     */ 
/*  50 */     this.length = -1;
/*  51 */     this.lengthExp = null;
/*     */ 
/*  56 */     this.deliParser = null;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  81 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public void setOffset(String offset)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  93 */       this.offset = Integer.parseInt(offset.trim());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  98 */       throw new HiException("213152", new String[] { this.name, offset }, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setRepeat_name(String repeat_name)
/*     */   {
/* 105 */     this.repeat_name = repeat_name;
/*     */   }
/*     */ 
/*     */   public void setRecord_name(String record_name)
/*     */   {
/* 110 */     this.record_name = record_name;
/*     */   }
/*     */ 
/*     */   public void setLength(String length) throws HiException {
/* 114 */     if (this.logger.isDebugEnabled()) {
/* 115 */       this.logger.debug("setLength(String) - start:input[" + length + "]");
/*     */     }
/*     */ 
/* 118 */     if (NumberUtils.isNumber(length))
/*     */     {
/* 120 */       this.length = Integer.parseInt(length.trim());
/*     */     }
/*     */     else
/*     */     {
/* 124 */       if (length == null)
/*     */       {
/* 126 */         throw new HiSysException("213104", new String[] { this.name, length });
/*     */       }
/*     */ 
/* 129 */       if (this.logger.isInfoEnabled())
/*     */       {
/* 131 */         this.logger.info(getNodeName() + ":setLength(String)->lengthExpression[" + length + "]");
/*     */       }
/* 133 */       this.length = 0;
/* 134 */       this.lengthExp = HiExpFactory.createExp(length);
/*     */     }
/* 136 */     this._type = "fixed";
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/* 142 */     return "Group";
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 148 */     return super.toString() + ":name[" + this.name + "]";
/*     */   }
/*     */ 
/*     */   public void setRepeat_num(String repeat_num) throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 155 */       this.repeat_num = Integer.parseInt(repeat_num.trim());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 160 */       throw new HiException("213153", new String[] { this.name, repeat_num }, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void loadAfter()
/*     */     throws HiException
/*     */   {
/* 179 */     if (StringUtils.isBlank(this.name))
/*     */     {
/* 181 */       throw new HiException("213151");
/*     */     }
/*     */ 
/* 184 */     if (StringUtils.equals(this._type, "deli"))
/*     */     {
/* 186 */       this.deliParser = new DeliAscParser(null);
/*     */     } else {
/* 188 */       if (!(StringUtils.equals(this._type, "deli_str")))
/*     */         return;
/* 190 */       this.deliParser = new DeliStrParser(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException
/*     */   {
/* 196 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 197 */     if (log.isInfoEnabled()) {
/* 198 */       log.info(sm.getString("HiGroup.process00", HiEngineUtilities.getCurFlowStep(), this.name));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 203 */       HiMessage mess = ctx.getCurrentMsg();
/* 204 */       doProcess(mess, ctx);
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/* 208 */       throw e;
/*     */     }
/*     */     catch (Throwable te)
/*     */     {
/* 212 */       throw new HiSysException("213159", this.name, te);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doProcess(HiMessage mess, HiMessageContext tranData)
/*     */     throws HiException
/*     */   {
/* 219 */     String ect = mess.getHeadItem("ECT");
/*     */ 
/* 222 */     if (StringUtils.equals(ect, "text/etf"))
/*     */     {
/* 224 */       formatEtf(mess, tranData);
/*     */     }
/* 226 */     else if (StringUtils.equals(ect, "text/plain"))
/*     */     {
/* 228 */       formatPlain(mess, tranData);
/*     */     }
/*     */     else
/*     */     {
/* 232 */       throw new HiException("213154", ect);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void formatEtf(HiMessage etfMsg, HiMessageContext tranData)
/*     */     throws HiException
/*     */   {
/*     */     int repeatNum;
/*     */     HiETF groupNode;
/*     */     int i;
/*     */     Iterator iter;
/* 239 */     Logger log = HiLog.getLogger(etfMsg);
/* 240 */     String curEtfLevel = HiItemHelper.getCurEtfLevel(etfMsg);
/* 241 */     if (log.isDebugEnabled())
/*     */     {
/* 243 */       log.debug(sm.getString("HiGroup.formatEtf0", curEtfLevel));
/*     */     }
/*     */ 
/* 246 */     HiETF etfBody = etfMsg.getETFBody();
/*     */ 
/* 250 */     if (this.offset > 0)
/*     */     {
/* 252 */       fitGroupPlain(etfMsg, this.offset);
/*     */     }
/*     */ 
/* 255 */     if (StringUtils.isNotBlank(this.repeat_name))
/*     */     {
/* 257 */       repeatNum = getRepeat_num(etfBody);
/*     */     }
/*     */     else
/*     */     {
/* 261 */       repeatNum = this.repeat_num;
/*     */     }
/*     */ 
/* 264 */     if (repeatNum >= 0)
/*     */     {
/* 266 */       if (log.isInfoEnabled()) {
/* 267 */         log.info(sm.getString("HiGroup.showRepeatNum", String.valueOf(repeatNum)));
/*     */       }
/* 269 */       for (int groupIdx = 1; groupIdx <= repeatNum; ++groupIdx)
/*     */       {
/* 271 */         if (log.isInfoEnabled())
/* 272 */           log.info(sm.getString("HiGroup.formatEtfRun", this.name + "_" + groupIdx));
/* 273 */         HiEngineUtilities.setCurFlowStep(groupIdx);
/* 274 */         doGroupItem(etfMsg, this.name + "_" + groupIdx, curEtfLevel, tranData);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 280 */       if (log.isInfoEnabled()) {
/* 281 */         log.info(sm.getString("HiGroup.formatEtf1", this.name));
/*     */       }
/* 283 */       groupNode = null;
/* 284 */       i = 0;
/* 285 */       for (iter = getGroups(etfBody).iterator(); iter.hasNext(); )
/*     */       {
/* 287 */         groupNode = (HiETF)iter.next();
/*     */ 
/* 289 */         HiEngineUtilities.setCurFlowStep(i);
/* 290 */         ++i;
/* 291 */         if (log.isInfoEnabled())
/* 292 */           log.info(sm.getString("HiGroup.formatEtfRun", groupNode.getName()));
/* 293 */         doGroupItem(etfMsg, groupNode.getName(), curEtfLevel, tranData);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 303 */     if (this.deliParser == null)
/*     */       return;
/* 305 */     this.deliParser.fitGroupPlain(etfMsg);
/*     */   }
/*     */ 
/*     */   private void formatPlain(HiMessage plainMsg, HiMessageContext tranData)
/*     */     throws HiException
/*     */   {
/*     */     int repeatNum;
/*     */     HiByteBuffer plainBuf;
/*     */     int plainLen;
/* 312 */     Logger log = HiLog.getLogger(plainMsg);
/* 313 */     String curEtfLevel = HiItemHelper.getCurEtfLevel(plainMsg);
/* 314 */     if (log.isDebugEnabled()) {
/* 315 */       log.debug(sm.getString("HiGroup.formatPlain0", curEtfLevel));
/*     */     }
/* 317 */     HiETF etfBody = plainMsg.getETFBody();
/*     */ 
/* 320 */     int groupIdx = 1;
/*     */ 
/* 325 */     int plain_offset = HiItemHelper.getPlainOffset(plainMsg) + this.offset;
/*     */ 
/* 327 */     if (this.offset > 0)
/*     */     {
/* 329 */       HiItemHelper.setPlainOffset(plainMsg, plain_offset);
/*     */     }
/*     */ 
/* 332 */     if (StringUtils.isNotBlank(this.repeat_name))
/*     */     {
/* 334 */       repeatNum = getRepeat_num(etfBody);
/*     */     }
/*     */     else
/*     */     {
/* 338 */       repeatNum = this.repeat_num;
/*     */     }
/*     */ 
/* 341 */     if (repeatNum >= 0)
/*     */     {
/* 343 */       if (log.isInfoEnabled()) {
/* 344 */         log.info(sm.getString("HiGroup.showRepeatNum", String.valueOf(repeatNum)));
/*     */       }
/* 346 */       for (groupIdx = 1; ; ++groupIdx) { if (groupIdx > repeatNum)
/*     */           return;
/* 348 */         addGroupEtfItem(etfBody, groupIdx, curEtfLevel);
/*     */ 
/* 350 */         if (log.isInfoEnabled()) {
/* 351 */           log.info(sm.getString("HiGroup.formatPlainRun", this.name + "_" + groupIdx));
/*     */         }
/* 353 */         doGroupItem(plainMsg, this.name + "_" + groupIdx, curEtfLevel, tranData);
/*     */       }
/*     */     }
/*     */ 
/* 357 */     if (this.deliParser != null)
/*     */     {
/* 359 */       if (log.isDebugEnabled()) {
/* 360 */         log.debug(sm.getString("HiGroup.formatPlain1", this.deliParser.getDeli()));
/*     */       }
/*     */ 
/* 363 */       plainBuf = HiItemHelper.getPlainText(plainMsg);
/* 364 */       plainLen = HiItemHelper.getPlainByteLen(plainBuf);
/*     */ 
/* 366 */       while (this.deliParser.isNotGroupEnd(plainMsg, plainBuf, plainLen))
/*     */       {
/* 368 */         addGroupEtfItem(etfBody, groupIdx, curEtfLevel);
/*     */ 
/* 370 */         if (log.isInfoEnabled())
/* 371 */           log.info(sm.getString("HiGroup.formatPlainRun", this.name + "_" + groupIdx));
/* 372 */         doGroupItem(plainMsg, this.name + "_" + groupIdx, curEtfLevel, tranData);
/*     */ 
/* 375 */         ++groupIdx;
/*     */       }
/*     */ 
/* 379 */       if (StringUtils.isNotBlank(this.record_name))
/*     */       {
/* 381 */         etfBody.setGrandChildNode(curEtfLevel + this.record_name, String.valueOf(groupIdx - 1));
/*     */       }
/*     */ 
/* 384 */       HiItemHelper.addPlainOffset(plainMsg, this.deliParser.getDeliLen());
/*     */     }
/* 387 */     else if (this.length >= 0)
/*     */     {
/* 392 */       int curPos = HiItemHelper.getPlainOffset(plainMsg);
/* 393 */       int endPos = getLengthExpValue() + curPos;
/* 394 */       while (curPos < endPos)
/*     */       {
/* 396 */         addGroupEtfItem(etfBody, groupIdx, curEtfLevel);
/*     */ 
/* 398 */         if (log.isInfoEnabled())
/* 399 */           log.info(sm.getString("HiGroup.formatPlainRun", this.name + "_" + groupIdx));
/* 400 */         doGroupItem(plainMsg, this.name + "_" + groupIdx, curEtfLevel, tranData);
/*     */ 
/* 403 */         ++groupIdx;
/* 404 */         curPos = HiItemHelper.getPlainOffset(plainMsg);
/*     */       }
/*     */ 
/* 407 */       if (StringUtils.isNotBlank(this.record_name))
/*     */       {
/* 409 */         etfBody.setGrandChildNode(curEtfLevel + this.record_name, String.valueOf(groupIdx - 1));
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 417 */       plainBuf = HiItemHelper.getPlainText(plainMsg);
/* 418 */       plainLen = HiItemHelper.getPlainByteLen(plainBuf);
/*     */ 
/* 420 */       int curPos = HiItemHelper.getPlainOffset(plainMsg);
/* 421 */       while (curPos < plainLen)
/*     */       {
/* 423 */         addGroupEtfItem(etfBody, groupIdx, curEtfLevel);
/*     */ 
/* 425 */         if (log.isInfoEnabled())
/* 426 */           log.info(sm.getString("HiGroup.formatPlainRun", this.name + "_" + groupIdx));
/* 427 */         doGroupItem(plainMsg, this.name + "_" + groupIdx, curEtfLevel, tranData);
/*     */ 
/* 430 */         ++groupIdx;
/* 431 */         curPos = HiItemHelper.getPlainOffset(plainMsg);
/*     */       }
/*     */ 
/* 434 */       if (!(StringUtils.isNotBlank(this.record_name)))
/*     */         return;
/* 436 */       etfBody.setGrandChildNode(curEtfLevel + this.record_name, String.valueOf(groupIdx - 1));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doGroupItem(HiMessage msg, String curGroup, String curEtfLevel, HiMessageContext tranData)
/*     */     throws HiException
/*     */   {
/* 444 */     addEtfLevel(msg, curGroup, curEtfLevel);
/*     */ 
/* 446 */     Logger log = HiLog.getLogger(msg);
/* 447 */     if (log.isDebugEnabled()) {
/* 448 */       log.debug(sm.getString("HiGroup.showEtfLevel", curEtfLevel + curGroup));
/*     */     }
/*     */     try
/*     */     {
/* 452 */       super.process(tranData);
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/* 458 */       throw e;
/*     */     }
/*     */     finally
/*     */     {
/* 462 */       removeEtfLevel(msg, curEtfLevel);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addEtfLevel(HiMessage msg, String curGroup, String curEtfLevel)
/*     */   {
/* 470 */     msg.setHeadItem("ETF_LEVEL", curEtfLevel + curGroup + ".");
/*     */   }
/*     */ 
/*     */   private void removeEtfLevel(HiMessage msg, String curEtfLevel)
/*     */   {
/* 479 */     msg.setHeadItem("ETF_LEVEL", curEtfLevel);
/*     */   }
/*     */ 
/*     */   private void addGroupEtfItem(HiETF etfBody, int groupIdx, String curEtfLevel)
/*     */   {
/* 485 */     etfBody.setGrandChildNode(curEtfLevel + this.name + "_" + groupIdx, "");
/*     */   }
/*     */ 
/*     */   private void fitGroupPlain(HiMessage msg, int offset)
/*     */   {
/* 490 */     HiByteBuffer plainBody = HiItemHelper.getPlainText(msg);
/*     */ 
/* 492 */     plainBody.append(StringUtils.repeat(" ", offset));
/*     */   }
/*     */ 
/*     */   private List getGroups(HiETF etfBody)
/*     */   {
/* 497 */     return etfBody.getGrandChildFuzzyEnd(this.name + "_");
/*     */   }
/*     */ 
/*     */   private int getRepeat_num(HiETF etfBody)
/*     */   {
/* 504 */     String strNum = etfBody.getGrandChildValue(this.repeat_name);
/*     */ 
/* 506 */     if (strNum == null)
/*     */     {
/* 508 */       return 0;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 513 */       return Integer.parseInt(strNum.trim());
/*     */     }
/*     */     catch (NumberFormatException ne)
/*     */     {
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */ 
/* 523 */     return 0;
/*     */   }
/*     */ 
/*     */   private int getLengthExpValue() throws HiException
/*     */   {
/* 528 */     if (this.lengthExp != null)
/*     */     {
/* 530 */       String expVal = null;
/*     */       try {
/* 532 */         expVal = this.lengthExp.getValue(HiMessageContext.getCurrentMessageContext());
/* 533 */         return Integer.parseInt(expVal.trim());
/*     */       } catch (NumberFormatException e) {
/* 535 */         throw new HiException("213124", new String[] { this.name, expVal }, e);
/*     */       }
/*     */       catch (Throwable te)
/*     */       {
/* 539 */         throw HiSysException.makeException("213124", new String[] { this.name, expVal, "Sys Exception" }, te);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 545 */     return this.length;
/*     */   }
/*     */ 
/*     */   private int getGroupEndPos(HiMessage msg, int groupStart)
/*     */   {
/* 551 */     HiByteBuffer plainBuf = HiItemHelper.getPlainText(msg);
/*     */ 
/* 553 */     return HiItemHelper.indexOfBytePlain(plainBuf, this._deli_str, groupStart);
/*     */   }
/*     */ 
/*     */   private boolean isNotGroupEnd(HiMessage msg, int endPos)
/*     */     throws HiException
/*     */   {
/* 559 */     int curPos = HiItemHelper.getPlainOffset(msg);
/*     */ 
/* 561 */     if (curPos < endPos)
/*     */     {
/* 563 */       return true;
/*     */     }
/* 565 */     if (curPos == endPos)
/*     */     {
/* 567 */       return false;
/*     */     }
/*     */ 
/* 572 */     throw new HiException("213157", this.name, this._deli_str);
/*     */   }
/*     */ 
/*     */   private class DeliStrParser extends HiGroup.DeliParser
/*     */   {
/*     */     private int deliStrLen;
/*     */     private final HiGroup this$0;
/*     */ 
/*     */     private DeliStrParser()
/*     */     {
/* 646 */       super(???, null);
/*     */ 
/* 648 */       this.deliStrLen = HiItemHelper.getPlainByteLen(this.this$0._deli_str); }
/*     */ 
/*     */     public boolean isNotGroupEnd(HiMessage msg, HiByteBuffer plainBuf, int plainBufLen) {
/* 651 */       int curPos = HiItemHelper.getPlainOffset(msg);
/* 652 */       if (curPos >= plainBufLen)
/*     */       {
/* 654 */         return false;
/*     */       }
/*     */ 
/* 658 */       return (this.this$0._deli_str.equals(plainBuf.substr(curPos, this.this$0._deli_str.length())));
/*     */     }
/*     */ 
/*     */     public void fitGroupPlain(HiMessage msg)
/*     */     {
/* 666 */       HiByteBuffer plainBody = HiItemHelper.getPlainText(msg);
/* 667 */       plainBody.append(this.this$0._deli_str);
/*     */     }
/*     */ 
/*     */     public int getDeliLen()
/*     */     {
/* 673 */       return this.deliStrLen;
/*     */     }
/*     */ 
/*     */     public String getDeli()
/*     */     {
/* 678 */       return "deli_str->" + this.this$0._deli_str;
/*     */     }
/*     */ 
/*     */     DeliStrParser(HiGroup.1 x1)
/*     */     {
/* 646 */       this(x0);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class DeliAscParser extends HiGroup.DeliParser
/*     */   {
/*     */     private final HiGroup this$0;
/*     */ 
/*     */     private DeliAscParser()
/*     */     {
/* 609 */       super(???, null);
/*     */     }
/*     */ 
/*     */     public boolean isNotGroupEnd(HiMessage msg, HiByteBuffer plainBuf, int plainBufLen)
/*     */     {
/* 614 */       int curPos = HiItemHelper.getPlainOffset(msg);
/* 615 */       if (curPos >= plainBufLen)
/*     */       {
/* 617 */         return false;
/*     */       }
/*     */ 
/* 621 */       return (this.this$0._deli_asc == plainBuf.charAt(curPos));
/*     */     }
/*     */ 
/*     */     public void fitGroupPlain(HiMessage msg)
/*     */     {
/* 629 */       HiByteBuffer plainBody = HiItemHelper.getPlainText(msg);
/* 630 */       plainBody.append(this.this$0._deli_asc);
/*     */     }
/*     */ 
/*     */     public int getDeliLen()
/*     */     {
/* 636 */       return 1;
/*     */     }
/*     */ 
/*     */     public String getDeli()
/*     */     {
/* 641 */       return "deli_asc->" + Byte.toString(this.this$0._deli_asc);
/*     */     }
/*     */ 
/*     */     DeliAscParser(HiGroup.1 x1)
/*     */     {
/* 609 */       this(x0);
/*     */     }
/*     */   }
/*     */ 
/*     */   private abstract class DeliParser
/*     */   {
/*     */     private final HiGroup this$0;
/*     */ 
/*     */     private DeliParser()
/*     */     {
/*     */     }
/*     */ 
/*     */     public abstract boolean isNotGroupEnd(HiMessage paramHiMessage, HiByteBuffer paramHiByteBuffer, int paramInt);
/*     */ 
/*     */     public abstract void fitGroupPlain(HiMessage paramHiMessage);
/*     */ 
/*     */     public abstract int getDeliLen();
/*     */ 
/*     */     public abstract String getDeli();
/*     */ 
/*     */     DeliParser(HiGroup.1 x1)
/*     */     {
/* 581 */       this(x0);
/*     */     }
/*     */   }
/*     */ }