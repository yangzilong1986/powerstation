/*    */ package com.hisun.protocol.http;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import org.apache.commons.httpclient.methods.GetMethod;
/*    */ import org.apache.commons.httpclient.methods.PostMethod;
/*    */ import org.apache.commons.httpclient.methods.StringRequestEntity;
/*    */ 
/*    */ public class HiHttpConnectorETFImpl extends HiHttpConnector
/*    */ {
/*    */   protected Object getResponseObject(byte[] responseBody)
/*    */     throws HiException
/*    */   {
/* 13 */     return HiETFFactory.createETF(new String(responseBody));
/*    */   }
/*    */ 
/*    */   protected void setGetParams(GetMethod method, Object request) throws HiException {
/* 17 */     method.setQueryString(request.toString());
/*    */   }
/*    */ 
/*    */   protected void setPostParams(PostMethod method, Object request) throws HiException {
/* 21 */     method.setRequestEntity(new StringRequestEntity(request.toString()));
/*    */   }
/*    */ }