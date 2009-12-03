/*    */ package com.hisun.message;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import org.dom4j.io.DOMReader;
/*    */ 
/*    */ public class HiETFFactory
/*    */ {
/*    */   public static HiETF createETF()
/*    */   {
/* 30 */     HiETF etf = new HiXmlETF();
/* 31 */     return etf;
/*    */   }
/*    */ 
/*    */   public static HiETF createXmlETF()
/*    */   {
/* 40 */     HiETF etf = new HiXmlETF();
/* 41 */     return etf;
/*    */   }
/*    */ 
/*    */   public static HiETF createXmlETF(org.w3c.dom.Document domDoc)
/*    */   {
/* 50 */     DOMReader reader = new DOMReader();
/* 51 */     org.dom4j.Document doc = reader.read(domDoc);
/* 52 */     if (doc != null)
/*    */     {
/* 54 */       HiETF etf = new HiXmlETF(doc.getRootElement());
/* 55 */       return etf;
/*    */     }
/*    */ 
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   public static HiETF createETF(String name, String value)
/*    */   {
/* 69 */     HiETF etf = new HiXmlETF(name, value);
/* 70 */     return etf;
/*    */   }
/*    */ 
/*    */   public static HiETF createETF(String text)
/*    */     throws HiException
/*    */   {
/* 81 */     HiETF etf = null;
/*    */ 
/* 83 */     etf = new HiXmlETF(text);
/* 84 */     return etf;
/*    */   }
/*    */ }