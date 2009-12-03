/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.lang.reflect.Method;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiItemHelper
/*     */ {
/*     */   public static void addEtfItem(HiMessage etfMsg, String name, String item)
/*     */     throws HiException
/*     */   {
/*  38 */     if (name == null) {
/*  39 */       return;
/*     */     }
/*  41 */     HiETF etfBody = (HiETF)etfMsg.getBody();
/*     */     try {
/*  43 */       if (name.startsWith("ROOT.")) {
/*  44 */         etfBody.setGrandChildNode(name, item);
/*     */       }
/*     */       else
/*     */       {
/*  48 */         String curLevel = etfMsg.getHeadItem("ETF_LEVEL");
/*  49 */         if (curLevel == null)
/*  50 */           etfBody.setGrandChildNode(name, item);
/*     */         else
/*  52 */           etfBody.setGrandChildNode(curLevel + name, item);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  56 */       throw new HiException("213139", new String[] { name, item }, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getEtfItem(HiMessage etfMsg, String name)
/*     */   {
/*  62 */     if (name == null) {
/*  63 */       return null;
/*     */     }
/*  65 */     HiETF etfBody = (HiETF)etfMsg.getBody();
/*     */ 
/*  67 */     if (name.startsWith("ROOT.")) {
/*  68 */       return etfBody.getGrandChildValue(name);
/*     */     }
/*  70 */     return etfBody.getGrandChildValue(getCurEtfLevel(etfMsg) + name);
/*     */   }
/*     */ 
/*     */   public static HiETF getEtfItemNode(HiMessage etfMsg, String name)
/*     */   {
/*  75 */     if (name == null) {
/*  76 */       return null;
/*     */     }
/*  78 */     HiETF etfBody = (HiETF)etfMsg.getBody();
/*     */ 
/*  80 */     if (name.startsWith("ROOT.")) {
/*  81 */       return etfBody.getGrandChildNode(name);
/*     */     }
/*  83 */     return etfBody.getGrandChildNode(getCurEtfLevel(etfMsg) + name);
/*     */   }
/*     */ 
/*     */   public static HiByteBuffer getPlainText(HiMessage msg)
/*     */   {
/*  95 */     return ((HiByteBuffer)msg.getObjectHeadItem("PlainText"));
/*     */   }
/*     */ 
/*     */   public static Object getPlainObject(HiMessage msg) {
/*  99 */     return msg.getObjectHeadItem("PlainText");
/*     */   }
/*     */ 
/*     */   public static int getPlainOffset(HiMessage msg)
/*     */   {
/* 110 */     String strIdx = msg.getHeadItem("PlainOffset");
/* 111 */     if (strIdx != null) {
/* 112 */       return Integer.parseInt(strIdx.trim());
/*     */     }
/*     */ 
/* 119 */     return 0;
/*     */   }
/*     */ 
/*     */   public static void setPlainOffset(HiMessage msg, int offset)
/*     */   {
/* 131 */     msg.setHeadItem("PlainOffset", String.valueOf(offset));
/*     */   }
/*     */ 
/*     */   public static void addPlainOffset(HiMessage msg, int offset)
/*     */   {
/* 143 */     msg.setHeadItem("PlainOffset", String.valueOf(offset + getPlainOffset(msg)));
/*     */   }
/*     */ 
/*     */   public static int getPlainByteLen(String plainText)
/*     */   {
/* 154 */     return plainText.getBytes().length;
/*     */   }
/*     */ 
/*     */   public static int getPlainByteLen(StringBuffer plainText)
/*     */   {
/* 164 */     return plainText.toString().getBytes().length;
/*     */   }
/*     */ 
/*     */   public static int getPlainByteLen(HiByteBuffer plainText)
/*     */   {
/* 174 */     return plainText.length();
/*     */   }
/*     */ 
/*     */   public static String subPlainString(String plainText, int beginIdx, int endIdx)
/*     */   {
/* 187 */     byte[] plainBytes = plainText.getBytes();
/*     */ 
/* 189 */     return new String(plainBytes, beginIdx, endIdx - beginIdx);
/*     */   }
/*     */ 
/*     */   public static String subPlainString(StringBuffer plainText, int beginIdx, int endIdx)
/*     */   {
/* 202 */     byte[] plainBytes = plainText.toString().getBytes();
/*     */ 
/* 204 */     return new String(plainBytes, beginIdx, endIdx - beginIdx);
/*     */   }
/*     */ 
/*     */   public static String subPlainString(HiByteBuffer plainText, int beginIdx, int length)
/*     */   {
/* 220 */     return plainText.substr(beginIdx, length);
/*     */   }
/*     */ 
/*     */   public static String subPlainString(byte[] plainText, int beginIdx, int length)
/*     */   {
/* 236 */     return new String(plainText, beginIdx, length);
/*     */   }
/*     */ 
/*     */   public static byte[] subPlainByte(HiByteBuffer plainText, int beginIdx, int length)
/*     */   {
/* 252 */     return plainText.subbyte(beginIdx, length);
/*     */   }
/*     */ 
/*     */   public static byte[] subPlainByte(byte[] plainBytes, int beginIdx, int length)
/*     */   {
/* 268 */     return ArrayUtils.subarray(plainBytes, beginIdx, beginIdx + length);
/*     */   }
/*     */ 
/*     */   public static int indexOfBytePlain(StringBuffer plainText, byte deli, int beginIdx)
/*     */   {
/* 282 */     byte[] plainBytes = plainText.toString().getBytes();
/*     */ 
/* 284 */     for (int i = beginIdx; i < plainBytes.length; ++i) {
/* 285 */       if (plainBytes[i] == deli) {
/* 286 */         return i;
/*     */       }
/*     */     }
/* 289 */     return -1;
/*     */   }
/*     */ 
/*     */   public static int indexOfBytePlain(HiByteBuffer plainText, byte deli, int beginIdx)
/*     */   {
/* 302 */     return plainText.indexOf(deli, beginIdx);
/*     */   }
/*     */ 
/*     */   public static int indexOfBytePlain(StringBuffer plainText, String deli, int beginIdx)
/*     */   {
/* 315 */     byte[] plainBytes = plainText.toString().getBytes();
/*     */ 
/* 317 */     byte[] deliBytes = deli.getBytes();
/* 318 */     int deliLen = deliBytes.length;
/*     */ 
/* 320 */     int endIdx = plainBytes.length - deliLen;
/*     */ 
/* 323 */     for (int i = beginIdx; i <= endIdx; ++i) {
/* 324 */       String sub = new String(plainBytes, i, deliLen);
/* 325 */       if (StringUtils.equals(deli, sub)) {
/* 326 */         return i;
/*     */       }
/*     */     }
/*     */ 
/* 330 */     return -1;
/*     */   }
/*     */ 
/*     */   public static int indexOfBytePlain(HiByteBuffer plainText, String deli, int beginIdx)
/*     */   {
/* 343 */     return plainText.indexOf(deli.getBytes(), beginIdx);
/*     */   }
/*     */ 
/*     */   public static String getCurEtfLevel(HiMessage msg)
/*     */   {
/* 352 */     String curLevel = msg.getHeadItem("ETF_LEVEL");
/*     */ 
/* 354 */     if (curLevel == null) {
/* 355 */       return "";
/*     */     }
/*     */ 
/* 358 */     return curLevel;
/*     */   }
/*     */ 
/*     */   public static void setCurEtfLevel(HiMessage msg, String curLevel) {
/* 362 */     msg.setHeadItem("ETF_LEVEL", curLevel);
/*     */   }
/*     */ 
/*     */   public static String getCurXmlLevel(HiMessage msg)
/*     */   {
/* 371 */     String curLevel = msg.getHeadItem("XML_LEVEL");
/*     */ 
/* 373 */     if (curLevel == null) {
/* 374 */       return "";
/*     */     }
/*     */ 
/* 377 */     return curLevel;
/*     */   }
/*     */ 
/*     */   public static void setCurXmlLevel(HiMessage msg, String curLevel) {
/* 381 */     msg.setHeadItem("XML_LEVEL", curLevel);
/*     */   }
/*     */ 
/*     */   public static HiETF getCurXmlRoot(HiMessage msg)
/*     */   {
/* 390 */     return ((HiETF)msg.getObjectHeadItem("XML_ROOT"));
/*     */   }
/*     */ 
/*     */   public static void setCurXmlRoot(HiMessage msg, HiETF curRoot) {
/* 394 */     msg.setHeadItem("XML_ROOT", curRoot);
/*     */   }
/*     */ 
/*     */   public static void execExpression(HiMessage mess, String expression, String name)
/*     */     throws HiException
/*     */   {
/* 409 */     HiExpression exp = HiExpFactory.createExp(expression);
/* 410 */     execExpression(mess, exp, name);
/*     */   }
/*     */ 
/*     */   public static void execExpression(HiMessage mess, HiExpression exp, String name)
/*     */     throws HiException
/*     */   {
/* 425 */     String val = exp.getValue(HiMessageContext.getCurrentMessageContext());
/*     */ 
/* 427 */     addEtfItem(mess, name, val);
/*     */ 
/* 429 */     Logger log = HiLog.getLogger(mess);
/* 430 */     if (log.isInfoEnabled())
/* 431 */       log.info("执行表达式 [" + exp.getExp() + "] 更新域 " + name + "=[" + val + "]");
/*     */   }
/*     */ 
/*     */   public static String execExpression(HiMessage mess, String expression)
/*     */     throws HiException
/*     */   {
/* 445 */     if (StringUtils.isEmpty(expression)) {
/* 446 */       return expression;
/*     */     }
/*     */ 
/* 449 */     HiExpression exp = HiExpFactory.createExp(expression);
/* 450 */     String val = exp.getValue(HiMessageContext.getCurrentMessageContext());
/*     */ 
/* 452 */     Logger log = HiLog.getLogger(mess);
/* 453 */     if (log.isInfoEnabled())
/* 454 */       log.info("执行表达式 [" + expression + "] 值 [" + val + "]");
/* 455 */     return val;
/*     */   }
/*     */ 
/*     */   public static String execExpression(HiMessage mess, HiExpression exp)
/*     */     throws HiException
/*     */   {
/* 468 */     String val = exp.getValue(HiMessageContext.getCurrentMessageContext());
/*     */ 
/* 470 */     Logger log = HiLog.getLogger(mess);
/* 471 */     if (log.isInfoEnabled())
/* 472 */       log.info("执行表达式 [" + exp.getExp() + "] 值 [" + val + "]");
/* 473 */     return val;
/*     */   }
/*     */ 
/*     */   public static byte[] execMethod(HiMethodItem method, byte[] args, Logger log)
/*     */     throws HiException
/*     */   {
/* 491 */     if (method != null) {
/* 492 */       HiByteBuffer bb = method.invoke(new HiByteBuffer(args), HiMessageContext.getCurrentMessageContext());
/*     */ 
/* 494 */       args = bb.getBytes();
/*     */ 
/* 496 */       if (log.isInfoEnabled()) {
/* 497 */         log.info("执行特殊处理方法 [" + method.method.getName() + "] 处理参数 [" + new String(args) + "],结果返回[" + bb.toString() + "]");
/*     */       }
/*     */     }
/* 500 */     return args;
/*     */   }
/*     */ 
/*     */   public static byte[] execMethod(HiMethodItem method, String args, Logger log) throws HiException
/*     */   {
/* 505 */     byte[] ret = null;
/*     */ 
/* 507 */     if (method != null) {
/* 508 */       HiByteBuffer bb = method.invoke(args, HiMessageContext.getCurrentMessageContext());
/*     */ 
/* 510 */       ret = bb.getBytes();
/*     */ 
/* 512 */       if (log.isInfoEnabled()) {
/* 513 */         log.info("执行特殊处理方法 [" + method.method.getName() + "] 处理参数 [" + new String(args) + "],结果返回[" + bb.toString() + "]");
/*     */       }
/*     */     }
/* 516 */     return ret;
/*     */   }
/*     */ 
/*     */   public static HiMethodItem getMethod(String className, String methodName)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 532 */       Class methodCls = HiResource.loadClassPrefix(className);
/* 533 */       Object obj = methodCls.newInstance();
/* 534 */       Class[] argTypes = { HiByteBuffer.class, HiMessageContext.class };
/* 535 */       Method method = methodCls.getMethod(methodName, argTypes);
/*     */ 
/* 537 */       HiMethodItem methodItem = new HiMethodItem();
/* 538 */       methodItem.method = method;
/* 539 */       methodItem.object = obj;
/*     */ 
/* 541 */       return methodItem;
/*     */     } catch (Exception e) {
/* 543 */       throw new HiException("213138", new String[] { className, methodName }, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static byte[] repeat(byte[] array, byte b, int num)
/*     */   {
/* 560 */     for (int i = 0; i < num; ++i) {
/* 561 */       array = ArrayUtils.add(array, b);
/*     */     }
/* 563 */     return array;
/*     */   }
/*     */ }