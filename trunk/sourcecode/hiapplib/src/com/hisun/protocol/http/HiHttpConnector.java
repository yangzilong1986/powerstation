 package com.hisun.protocol.http;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerDestroyListener;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiStringManager;
 import java.io.IOException;
 import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
 import org.apache.commons.httpclient.HttpClient;
 import org.apache.commons.httpclient.HttpConnectionManager;
 import org.apache.commons.httpclient.HttpException;
 import org.apache.commons.httpclient.HttpMethod;
 import org.apache.commons.httpclient.NameValuePair;
 import org.apache.commons.httpclient.methods.GetMethod;
 import org.apache.commons.httpclient.methods.PostMethod;
 import org.apache.commons.httpclient.params.HttpClientParams;
 import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
 import org.apache.commons.httpclient.params.HttpMethodParams;
 import org.apache.commons.lang.StringUtils;
 
 public abstract class HiHttpConnector
   implements IHandler, IServerInitListener, IServerDestroyListener
 {
   protected static HiStringManager sm = HiStringManager.getManager();
   protected String _url;
   protected String _hostFile;
   protected String _method;
   protected Logger _log;
   protected int _tmOut;
   protected String _charSet;
   protected String _contentType;
   protected boolean _ignoreCase;
 
   public HiHttpConnector()
   {
     this._method = "post";
 
     this._tmOut = 30000;
 
     this._contentType = "text/plain";
     this._ignoreCase = false; }
 
   public void setLog(Logger log) {
     this._log = log;
   }
 
   public String getUrl() {
     return this._url;
   }
 
   public void setUrl(String url) {
     this._url = url;
   }
 
   public String getMethod() {
     return this._method;
   }
 
   public void setMethod(String method) {
     this._method = method; }
 
   public String getContentType() {
     return this._contentType;
   }
 
   public void setContentType(String contentType) {
     this._contentType = contentType;
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     HttpClient client = new HttpClient();
     client.getParams().setSoTimeout(this._tmOut);
     client.getHttpConnectionManager().getParams().setConnectionTimeout(this._tmOut);
 
     HiMessage msg = ctx.getCurrentMsg();
     String url = getHostURL(msg);
 
     HttpMethod method = getMethod(url);
     setParams(method, msg.getBody());
     method.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler(0, false));
     try
     {
       if (this._log.isInfoEnabled()) {
         StringBuffer tmp = new StringBuffer();
         if (this._method.equalsIgnoreCase("get")) {
           tmp.append(method.getQueryString());
         } else {
           PostMethod method1 = (PostMethod)method;
           NameValuePair[] pairs = method1.getParameters();
           for (int i = 0; i < pairs.length; ++i) {
             tmp.append(pairs[i].getName());
             tmp.append('=');
             tmp.append(pairs[i].getValue());
             if (i < pairs.length - 1) {
               tmp.append('&');
             }
           }
         }
         this._log.info(sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), url, method.getName(), String.valueOf(tmp.length()), tmp));
       }
 
       if (!(StringUtils.isEmpty(this._contentType))) {
         method.setRequestHeader("Content-type", this._contentType);
       }
 
       if (!(StringUtils.isEmpty(this._charSet))) {
         client.getParams().setContentCharset(this._charSet);
       }
       client.executeMethod(method);
       if (method.getStatusCode() == 200) {
         byte[] responseBody = method.getResponseBody();
         if (this._log.isInfoEnabled()) {
           this._log.info(sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), url, method.getName(), String.valueOf(responseBody.length), new String(responseBody)));
         }
 
         msg.setBody(getResponseObject(responseBody));
         msg.setHeadItem("SCH", "rp");
 
         ctx.setCurrentMsg(msg);
       } else {
         String errstr = sm.getString("httpclient.err.failure", method.getStatusText(), url);
 
         this._log.error(errstr);
         throw new HiException("231205", errstr);
       }
     } catch (HttpException e) {
     }
     catch (IOException e) {
     }
     finally {
       method.releaseConnection();
     }
   }
 
   private String getHostURL(HiMessage msg) {
     String url = msg.getHeadItem("OIP");
     if (StringUtils.isEmpty(url)) {
       url = this._url;
     }
     return url;
   }
 
   private String getURLByHostName(String hostName) {
     return null;
   }
 
   public void serverInit(ServerEvent arg0) throws HiException {
     setLog(arg0.getLog());
   }
 
   public void serverDestroy(ServerEvent arg0) throws HiException
   {
   }
 
   public String getHostFile() {
     return this._hostFile;
   }
 
   public void setHostFile(String hostFile) {
     this._hostFile = hostFile;
   }
 
   public int getTmOut() {
     return this._tmOut;
   }
 
   public void setTmOut(int tmOut) {
     this._tmOut = (tmOut * 1000);
   }
 
   public String getCharSet() {
     return this._charSet;
   }
 
   public void setCharSet(String charSet) {
     this._charSet = charSet;
   }
 
   private HttpMethod getMethod(String url) {
     if (this._method.equalsIgnoreCase("post"))
       return new PostMethod(url);
     if (this._method.equalsIgnoreCase("get")) {
       return new GetMethod(url);
     }
     return new PostMethod(url);
   }
 
   private void setParams(HttpMethod method, Object request)
     throws HiException
   {
     if (this._method.equalsIgnoreCase("post"))
       setPostParams((PostMethod)method, request);
     else if (this._method.equalsIgnoreCase("get"))
       setGetParams((GetMethod)method, request);
     else
       setPostParams((PostMethod)method, request);
   }
 
   protected abstract void setGetParams(GetMethod paramGetMethod, Object paramObject)
     throws HiException;
 
   protected abstract void setPostParams(PostMethod paramPostMethod, Object paramObject)
     throws HiException;
 
   protected abstract Object getResponseObject(byte[] paramArrayOfByte)
     throws HiException;
 
   public boolean isIgnoreCase()
   {
     return this._ignoreCase;
   }
 
   public void setIgnoreCase(boolean case1) {
     this._ignoreCase = case1;
   }
 }