 package com.hisun.engine.invoke.impl;
 
 import com.hisun.exception.HiException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiResource;
 import java.lang.reflect.Method;
 import org.apache.commons.lang.ArrayUtils;
 import org.apache.commons.lang.StringUtils;
 
 public class HiItemHelper
 {
   public static void addEtfItem(HiMessage etfMsg, String name, String item)
     throws HiException
   {
     if (name == null) {
       return;
     }
     HiETF etfBody = (HiETF)etfMsg.getBody();
     try {
       if (name.startsWith("ROOT.")) {
         etfBody.setGrandChildNode(name, item);
       }
       else
       {
         String curLevel = etfMsg.getHeadItem("ETF_LEVEL");
         if (curLevel == null)
           etfBody.setGrandChildNode(name, item);
         else
           etfBody.setGrandChildNode(curLevel + name, item);
       }
     }
     catch (Exception e) {
       throw new HiException("213139", new String[] { name, item }, e);
     }
   }
 
   public static String getEtfItem(HiMessage etfMsg, String name)
   {
     if (name == null) {
       return null;
     }
     HiETF etfBody = (HiETF)etfMsg.getBody();
 
     if (name.startsWith("ROOT.")) {
       return etfBody.getGrandChildValue(name);
     }
     return etfBody.getGrandChildValue(getCurEtfLevel(etfMsg) + name);
   }
 
   public static HiETF getEtfItemNode(HiMessage etfMsg, String name)
   {
     if (name == null) {
       return null;
     }
     HiETF etfBody = (HiETF)etfMsg.getBody();
 
     if (name.startsWith("ROOT.")) {
       return etfBody.getGrandChildNode(name);
     }
     return etfBody.getGrandChildNode(getCurEtfLevel(etfMsg) + name);
   }
 
   public static HiByteBuffer getPlainText(HiMessage msg)
   {
     return ((HiByteBuffer)msg.getObjectHeadItem("PlainText"));
   }
 
   public static Object getPlainObject(HiMessage msg) {
     return msg.getObjectHeadItem("PlainText");
   }
 
   public static int getPlainOffset(HiMessage msg)
   {
     String strIdx = msg.getHeadItem("PlainOffset");
     if (strIdx != null) {
       return Integer.parseInt(strIdx.trim());
     }
 
     return 0;
   }
 
   public static void setPlainOffset(HiMessage msg, int offset)
   {
     msg.setHeadItem("PlainOffset", String.valueOf(offset));
   }
 
   public static void addPlainOffset(HiMessage msg, int offset)
   {
     msg.setHeadItem("PlainOffset", String.valueOf(offset + getPlainOffset(msg)));
   }
 
   public static int getPlainByteLen(String plainText)
   {
     return plainText.getBytes().length;
   }
 
   public static int getPlainByteLen(StringBuffer plainText)
   {
     return plainText.toString().getBytes().length;
   }
 
   public static int getPlainByteLen(HiByteBuffer plainText)
   {
     return plainText.length();
   }
 
   public static String subPlainString(String plainText, int beginIdx, int endIdx)
   {
     byte[] plainBytes = plainText.getBytes();
 
     return new String(plainBytes, beginIdx, endIdx - beginIdx);
   }
 
   public static String subPlainString(StringBuffer plainText, int beginIdx, int endIdx)
   {
     byte[] plainBytes = plainText.toString().getBytes();
 
     return new String(plainBytes, beginIdx, endIdx - beginIdx);
   }
 
   public static String subPlainString(HiByteBuffer plainText, int beginIdx, int length)
   {
     return plainText.substr(beginIdx, length);
   }
 
   public static String subPlainString(byte[] plainText, int beginIdx, int length)
   {
     return new String(plainText, beginIdx, length);
   }
 
   public static byte[] subPlainByte(HiByteBuffer plainText, int beginIdx, int length)
   {
     return plainText.subbyte(beginIdx, length);
   }
 
   public static byte[] subPlainByte(byte[] plainBytes, int beginIdx, int length)
   {
     return ArrayUtils.subarray(plainBytes, beginIdx, beginIdx + length);
   }
 
   public static int indexOfBytePlain(StringBuffer plainText, byte deli, int beginIdx)
   {
     byte[] plainBytes = plainText.toString().getBytes();
 
     for (int i = beginIdx; i < plainBytes.length; ++i) {
       if (plainBytes[i] == deli) {
         return i;
       }
     }
     return -1;
   }
 
   public static int indexOfBytePlain(HiByteBuffer plainText, byte deli, int beginIdx)
   {
     return plainText.indexOf(deli, beginIdx);
   }
 
   public static int indexOfBytePlain(StringBuffer plainText, String deli, int beginIdx)
   {
     byte[] plainBytes = plainText.toString().getBytes();
 
     byte[] deliBytes = deli.getBytes();
     int deliLen = deliBytes.length;
 
     int endIdx = plainBytes.length - deliLen;
 
     for (int i = beginIdx; i <= endIdx; ++i) {
       String sub = new String(plainBytes, i, deliLen);
       if (StringUtils.equals(deli, sub)) {
         return i;
       }
     }
 
     return -1;
   }
 
   public static int indexOfBytePlain(HiByteBuffer plainText, String deli, int beginIdx)
   {
     return plainText.indexOf(deli.getBytes(), beginIdx);
   }
 
   public static String getCurEtfLevel(HiMessage msg)
   {
     String curLevel = msg.getHeadItem("ETF_LEVEL");
 
     if (curLevel == null) {
       return "";
     }
 
     return curLevel;
   }
 
   public static void setCurEtfLevel(HiMessage msg, String curLevel) {
     msg.setHeadItem("ETF_LEVEL", curLevel);
   }
 
   public static String getCurXmlLevel(HiMessage msg)
   {
     String curLevel = msg.getHeadItem("XML_LEVEL");
 
     if (curLevel == null) {
       return "";
     }
 
     return curLevel;
   }
 
   public static void setCurXmlLevel(HiMessage msg, String curLevel) {
     msg.setHeadItem("XML_LEVEL", curLevel);
   }
 
   public static HiETF getCurXmlRoot(HiMessage msg)
   {
     return ((HiETF)msg.getObjectHeadItem("XML_ROOT"));
   }
 
   public static void setCurXmlRoot(HiMessage msg, HiETF curRoot) {
     msg.setHeadItem("XML_ROOT", curRoot);
   }
 
   public static void execExpression(HiMessage mess, String expression, String name)
     throws HiException
   {
     HiExpression exp = HiExpFactory.createExp(expression);
     execExpression(mess, exp, name);
   }
 
   public static void execExpression(HiMessage mess, HiExpression exp, String name)
     throws HiException
   {
     String val = exp.getValue(HiMessageContext.getCurrentMessageContext());
 
     addEtfItem(mess, name, val);
 
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled())
       log.info("执行表达式 [" + exp.getExp() + "] 更新域 " + name + "=[" + val + "]");
   }
 
   public static String execExpression(HiMessage mess, String expression)
     throws HiException
   {
     if (StringUtils.isEmpty(expression)) {
       return expression;
     }
 
     HiExpression exp = HiExpFactory.createExp(expression);
     String val = exp.getValue(HiMessageContext.getCurrentMessageContext());
 
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled())
       log.info("执行表达式 [" + expression + "] 值 [" + val + "]");
     return val;
   }
 
   public static String execExpression(HiMessage mess, HiExpression exp)
     throws HiException
   {
     String val = exp.getValue(HiMessageContext.getCurrentMessageContext());
 
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled())
       log.info("执行表达式 [" + exp.getExp() + "] 值 [" + val + "]");
     return val;
   }
 
   public static byte[] execMethod(HiMethodItem method, byte[] args, Logger log)
     throws HiException
   {
     if (method != null) {
       HiByteBuffer bb = method.invoke(new HiByteBuffer(args), HiMessageContext.getCurrentMessageContext());
 
       args = bb.getBytes();
 
       if (log.isInfoEnabled()) {
         log.info("执行特殊处理方法 [" + method.method.getName() + "] 处理参数 [" + new String(args) + "],结果返回[" + bb.toString() + "]");
       }
     }
     return args;
   }
 
   public static byte[] execMethod(HiMethodItem method, String args, Logger log) throws HiException
   {
     byte[] ret = null;
 
     if (method != null) {
       HiByteBuffer bb = method.invoke(args, HiMessageContext.getCurrentMessageContext());
 
       ret = bb.getBytes();
 
       if (log.isInfoEnabled()) {
         log.info("执行特殊处理方法 [" + method.method.getName() + "] 处理参数 [" + new String(args) + "],结果返回[" + bb.toString() + "]");
       }
     }
     return ret;
   }
 
   public static HiMethodItem getMethod(String className, String methodName)
     throws HiException
   {
     try
     {
       Class methodCls = HiResource.loadClassPrefix(className);
       Object obj = methodCls.newInstance();
       Class[] argTypes = { HiByteBuffer.class, HiMessageContext.class };
       Method method = methodCls.getMethod(methodName, argTypes);
 
       HiMethodItem methodItem = new HiMethodItem();
       methodItem.method = method;
       methodItem.object = obj;
 
       return methodItem;
     } catch (Exception e) {
       throw new HiException("213138", new String[] { className, methodName }, e);
     }
   }
 
   public static byte[] repeat(byte[] array, byte b, int num)
   {
     for (int i = 0; i < num; ++i) {
       array = ArrayUtils.add(array, b);
     }
     return array;
   }
 }