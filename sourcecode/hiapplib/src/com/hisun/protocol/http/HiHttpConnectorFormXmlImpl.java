/*    */ package com.hisun.protocol.http;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.apache.commons.httpclient.NameValuePair;
/*    */ import org.apache.commons.httpclient.methods.GetMethod;
/*    */ import org.apache.commons.httpclient.methods.PostMethod;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.DocumentHelper;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class HiHttpConnectorFormXmlImpl extends HiHttpConnector
/*    */ {
/*    */   protected void setGetParams(GetMethod method, Object request)
/*    */     throws HiException
/*    */   {
/*    */     Element root;
/*    */     try
/*    */     {
/* 22 */       root = DocumentHelper.parseText(((HiByteBuffer)request).toString()).getRootElement();
/*    */     }
/*    */     catch (DocumentException e)
/*    */     {
/* 26 */       throw new HiException(e);
/*    */     }
/*    */ 
/* 29 */     NameValuePair[] pairs = new NameValuePair[root.elements().size()];
/* 30 */     int i = 0;
/* 31 */     Iterator iter = root.elementIterator();
/* 32 */     while (iter.hasNext()) {
/* 33 */       Element node = (Element)iter.next();
/* 34 */       pairs[(i++)] = new NameValuePair(node.getName(), node.getText());
/*    */     }
/* 36 */     method.setQueryString(pairs);
/*    */   }
/*    */ 
/*    */   protected void setPostParams(PostMethod method, Object request) throws HiException
/*    */   {
/*    */     Element root;
/*    */     try {
/* 43 */       root = DocumentHelper.parseText(((HiByteBuffer)request).toString()).getRootElement();
/*    */     }
/*    */     catch (DocumentException e)
/*    */     {
/* 47 */       throw new HiException(e);
/*    */     }
/* 49 */     Iterator iter = root.elementIterator();
/* 50 */     while (iter.hasNext()) {
/* 51 */       Element node = (Element)iter.next();
/* 52 */       method.setParameter(node.getName(), node.getText());
/*    */     }
/*    */   }
/*    */ 
/*    */   protected Object getResponseObject(byte[] responseBody)
/*    */     throws HiException
/*    */   {
/* 67 */     return new HiByteBuffer(responseBody);
/*    */   }
/*    */ }