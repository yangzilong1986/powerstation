 package com.hisun.protocol.http;
 
 import com.hisun.exception.HiException;
 import com.hisun.util.HiByteBuffer;
 import org.apache.commons.httpclient.methods.GetMethod;
 import org.apache.commons.httpclient.methods.PostMethod;
 import org.apache.commons.httpclient.methods.StringRequestEntity;
 
 public class HiHttpConnectorImpl extends HiHttpConnector
 {
   protected Object getResponseObject(byte[] responseBody)
     throws HiException
   {
     return new HiByteBuffer(responseBody);
   }
 
   protected void setGetParams(GetMethod method, Object request) throws HiException
   {
     method.setQueryString(request.toString());
   }
 
   protected void setPostParams(PostMethod method, Object request) throws HiException
   {
     method.setRequestEntity(new StringRequestEntity(request.toString()));
   }
 }