/*     */ package com.hisun.protocol.http;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerDestroyListener;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
/*     */ import org.apache.commons.httpclient.HttpClient;
/*     */ import org.apache.commons.httpclient.HttpConnectionManager;
/*     */ import org.apache.commons.httpclient.HttpException;
/*     */ import org.apache.commons.httpclient.HttpMethod;
/*     */ import org.apache.commons.httpclient.NameValuePair;
/*     */ import org.apache.commons.httpclient.methods.GetMethod;
/*     */ import org.apache.commons.httpclient.methods.PostMethod;
/*     */ import org.apache.commons.httpclient.params.HttpClientParams;
/*     */ import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
/*     */ import org.apache.commons.httpclient.params.HttpMethodParams;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public abstract class HiHttpConnector
/*     */   implements IHandler, IServerInitListener, IServerDestroyListener
/*     */ {
/*  42 */   protected static HiStringManager sm = HiStringManager.getManager();
/*     */   protected String _url;
/*     */   protected String _hostFile;
/*     */   protected String _method;
/*     */   protected Logger _log;
/*     */   protected int _tmOut;
/*     */   protected String _charSet;
/*     */   protected String _contentType;
/*     */   protected boolean _ignoreCase;
/*     */ 
/*     */   public HiHttpConnector()
/*     */   {
/*  45 */     this._method = "post";
/*     */ 
/*  47 */     this._tmOut = 30000;
/*     */ 
/*  49 */     this._contentType = "text/plain";
/*  50 */     this._ignoreCase = false; }
/*     */ 
/*     */   public void setLog(Logger log) {
/*  53 */     this._log = log;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  57 */     return this._url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url) {
/*  61 */     this._url = url;
/*     */   }
/*     */ 
/*     */   public String getMethod() {
/*  65 */     return this._method;
/*     */   }
/*     */ 
/*     */   public void setMethod(String method) {
/*  69 */     this._method = method; }
/*     */ 
/*     */   public String getContentType() {
/*  72 */     return this._contentType;
/*     */   }
/*     */ 
/*     */   public void setContentType(String contentType) {
/*  76 */     this._contentType = contentType;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/*  80 */     HttpClient client = new HttpClient();
/*  81 */     client.getParams().setSoTimeout(this._tmOut);
/*  82 */     client.getHttpConnectionManager().getParams().setConnectionTimeout(this._tmOut);
/*     */ 
/*  84 */     HiMessage msg = ctx.getCurrentMsg();
/*  85 */     String url = getHostURL(msg);
/*     */ 
/*  87 */     HttpMethod method = getMethod(url);
/*  88 */     setParams(method, msg.getBody());
/*  89 */     method.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler(0, false));
/*     */     try
/*     */     {
/*  92 */       if (this._log.isInfoEnabled()) {
/*  93 */         StringBuffer tmp = new StringBuffer();
/*  94 */         if (this._method.equalsIgnoreCase("get")) {
/*  95 */           tmp.append(method.getQueryString());
/*     */         } else {
/*  97 */           PostMethod method1 = (PostMethod)method;
/*  98 */           NameValuePair[] pairs = method1.getParameters();
/*  99 */           for (int i = 0; i < pairs.length; ++i) {
/* 100 */             tmp.append(pairs[i].getName());
/* 101 */             tmp.append('=');
/* 102 */             tmp.append(pairs[i].getValue());
/* 103 */             if (i < pairs.length - 1) {
/* 104 */               tmp.append('&');
/*     */             }
/*     */           }
/*     */         }
/* 108 */         this._log.info(sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), url, method.getName(), String.valueOf(tmp.length()), tmp));
/*     */       }
/*     */ 
/* 113 */       if (!(StringUtils.isEmpty(this._contentType))) {
/* 114 */         method.setRequestHeader("Content-type", this._contentType);
/*     */       }
/*     */ 
/* 117 */       if (!(StringUtils.isEmpty(this._charSet))) {
/* 118 */         client.getParams().setContentCharset(this._charSet);
/*     */       }
/* 120 */       client.executeMethod(method);
/* 121 */       if (method.getStatusCode() == 200) {
/* 122 */         byte[] responseBody = method.getResponseBody();
/* 123 */         if (this._log.isInfoEnabled()) {
/* 124 */           this._log.info(sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), url, method.getName(), String.valueOf(responseBody.length), new String(responseBody)));
/*     */         }
/*     */ 
/* 129 */         msg.setBody(getResponseObject(responseBody));
/* 130 */         msg.setHeadItem("SCH", "rp");
/*     */ 
/* 132 */         ctx.setCurrentMsg(msg);
/*     */       } else {
/* 134 */         String errstr = sm.getString("httpclient.err.failure", method.getStatusText(), url);
/*     */ 
/* 138 */         this._log.error(errstr);
/* 139 */         throw new HiException("231205", errstr);
/*     */       }
/*     */     } catch (HttpException e) {
/*     */     }
/*     */     catch (IOException e) {
/*     */     }
/*     */     finally {
/* 146 */       method.releaseConnection();
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getHostURL(HiMessage msg) {
/* 151 */     String url = msg.getHeadItem("OIP");
/* 152 */     if (StringUtils.isEmpty(url)) {
/* 153 */       url = this._url;
/*     */     }
/* 155 */     return url;
/*     */   }
/*     */ 
/*     */   private String getURLByHostName(String hostName) {
/* 159 */     return null;
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/* 163 */     setLog(arg0.getLog());
/*     */   }
/*     */ 
/*     */   public void serverDestroy(ServerEvent arg0) throws HiException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getHostFile() {
/* 171 */     return this._hostFile;
/*     */   }
/*     */ 
/*     */   public void setHostFile(String hostFile) {
/* 175 */     this._hostFile = hostFile;
/*     */   }
/*     */ 
/*     */   public int getTmOut() {
/* 179 */     return this._tmOut;
/*     */   }
/*     */ 
/*     */   public void setTmOut(int tmOut) {
/* 183 */     this._tmOut = (tmOut * 1000);
/*     */   }
/*     */ 
/*     */   public String getCharSet() {
/* 187 */     return this._charSet;
/*     */   }
/*     */ 
/*     */   public void setCharSet(String charSet) {
/* 191 */     this._charSet = charSet;
/*     */   }
/*     */ 
/*     */   private HttpMethod getMethod(String url) {
/* 195 */     if (this._method.equalsIgnoreCase("post"))
/* 196 */       return new PostMethod(url);
/* 197 */     if (this._method.equalsIgnoreCase("get")) {
/* 198 */       return new GetMethod(url);
/*     */     }
/* 200 */     return new PostMethod(url);
/*     */   }
/*     */ 
/*     */   private void setParams(HttpMethod method, Object request)
/*     */     throws HiException
/*     */   {
/* 206 */     if (this._method.equalsIgnoreCase("post"))
/* 207 */       setPostParams((PostMethod)method, request);
/* 208 */     else if (this._method.equalsIgnoreCase("get"))
/* 209 */       setGetParams((GetMethod)method, request);
/*     */     else
/* 211 */       setPostParams((PostMethod)method, request);
/*     */   }
/*     */ 
/*     */   protected abstract void setGetParams(GetMethod paramGetMethod, Object paramObject)
/*     */     throws HiException;
/*     */ 
/*     */   protected abstract void setPostParams(PostMethod paramPostMethod, Object paramObject)
/*     */     throws HiException;
/*     */ 
/*     */   protected abstract Object getResponseObject(byte[] paramArrayOfByte)
/*     */     throws HiException;
/*     */ 
/*     */   public boolean isIgnoreCase()
/*     */   {
/* 225 */     return this._ignoreCase;
/*     */   }
/*     */ 
/*     */   public void setIgnoreCase(boolean case1) {
/* 229 */     this._ignoreCase = case1;
/*     */   }
/*     */ }