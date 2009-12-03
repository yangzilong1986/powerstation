/*    */ package com.hisun.protocol.http;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import org.apache.commons.httpclient.methods.GetMethod;
/*    */ import org.apache.commons.httpclient.methods.PostMethod;
/*    */ import org.apache.commons.httpclient.methods.StringRequestEntity;
/*    */ 
/*    */ public class HiHttpConnectorImpl extends HiHttpConnector
/*    */ {
/*    */   protected Object getResponseObject(byte[] responseBody)
/*    */     throws HiException
/*    */   {
/* 14 */     return new HiByteBuffer(responseBody);
/*    */   }
/*    */ 
/*    */   protected void setGetParams(GetMethod method, Object request) throws HiException
/*    */   {
/* 19 */     method.setQueryString(request.toString());
/*    */   }
/*    */ 
/*    */   protected void setPostParams(PostMethod method, Object request) throws HiException
/*    */   {
/* 24 */     method.setRequestEntity(new StringRequestEntity(request.toString()));
/*    */   }
/*    */ }