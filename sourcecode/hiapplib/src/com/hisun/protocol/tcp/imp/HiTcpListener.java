/*     */ package com.hisun.protocol.tcp.imp;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSysException;
/*     */ import com.hisun.framework.HiDefaultServer;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.framework.imp.HiAbstractListener;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.protocol.tcp.HiMessageInOut;
/*     */ import com.hisun.protocol.tcp.HiSSLHandler;
/*     */ import com.hisun.protocol.tcp.HiTcpEndpoint;
/*     */ import com.hisun.protocol.tcp.ISocketHandler;
/*     */ import com.hisun.protocol.tcp.ISocketHandlerFilter;
/*     */ import com.hisun.protocol.tcp.filters.IpCheckFilter;
/*     */ import com.hisun.protocol.tcp.filters.SetOptionFilter;
/*     */ import com.hisun.protocol.tcp.filters.SocketHandlers;
/*     */ import com.hisun.protocol.tcp.parser.HiIPXMLParser;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import com.hisun.util.HiThreadPool;
/*     */ import java.net.Socket;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiTcpListener extends HiAbstractListener
/*     */ {
/*  39 */   protected HiTcpEndpoint ep = new HiTcpEndpoint();
/*     */   public ISocketHandler handler;
/*     */   IpCheckFilter ipcheck;
/*     */   Set ipset;
/*  47 */   protected HiMessageInOut msginout = new HiMessageInOut();
/*  48 */   protected HiSSLHandler sslHandler = HiSSLHandler.getInstance();
/*  49 */   SetOptionFilter opthandler = new SetOptionFilter();
/*     */   ISocketHandlerFilter poolhandler;
/*  52 */   String _ipLstFil = null;
/*     */   protected HiThreadPool threadpool;
/*  56 */   protected int maxThreads = 50; protected int minThreads = 5; protected int queueSize = -1;
/*     */ 
/*     */   public HiTcpListener()
/*     */   {
/*  60 */     setSoLinger(-1);
/*     */ 
/*  63 */     setServerSoTimeout(1000);
/*  64 */     setTcpNoDelay(true);
/*     */   }
/*     */ 
/*     */   public ISocketHandler buildSocketHandler()
/*     */     throws HiException
/*     */   {
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */   public int getBacklog() {
/*  81 */     return this.ep.getBacklog();
/*     */   }
/*     */ 
/*     */   public int getLocalPort() {
/*  85 */     return this.ep.getLocalPort();
/*     */   }
/*     */ 
/*     */   public int getMaxThreads()
/*     */   {
/*  91 */     return this.maxThreads;
/*     */   }
/*     */ 
/*     */   public int getMinThreads() {
/*  95 */     return this.minThreads;
/*     */   }
/*     */ 
/*     */   public int getPreLen() {
/*  99 */     return this.msginout.getPreLen();
/*     */   }
/*     */ 
/*     */   public int getServerSoTimeout() {
/* 103 */     return this.ep.getServerSoTimeout();
/*     */   }
/*     */ 
/*     */   public void serverDestroy(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/* 111 */     if (this.log.isInfoEnabled()) {
/* 112 */       this.log.info(sm.getString("tcplistener.stop", String.valueOf(getLocalPort())));
/*     */     }
/* 114 */     this.ep.stopEndpoint();
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent event)
/*     */     throws HiException
/*     */   {
/* 127 */     if (this.queueSize != -1) {
/* 128 */       this.threadpool = HiThreadPool.createThreadPool(this.minThreads, this.maxThreads, this.queueSize);
/*     */     }
/*     */     else {
/* 131 */       this.threadpool = HiThreadPool.createThreadPool(this.minThreads, this.maxThreads);
/*     */     }
/* 133 */     this.opthandler.setLog(this.log);
/*     */ 
/* 136 */     this.poolhandler = SocketHandlers.poolFilter(this.log, this.threadpool);
/*     */ 
/* 138 */     if (this.handler == null)
/* 139 */       this.handler = buildSocketHandler();
/* 140 */     this.ep.setHandler(this.handler);
/*     */ 
/* 142 */     this.ep.setSslHandler(this.sslHandler);
/*     */     try {
/* 144 */       this.ep.initEndpoint();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 148 */       throw HiException.makeException(ex);
/*     */     }
/* 150 */     if (!(this.log.isInfoEnabled()))
/*     */       return;
/* 152 */     this.log.info(sm.getString("tcplistener.init", "listener"));
/*     */   }
/*     */ 
/*     */   public void serverPause(ServerEvent event)
/*     */   {
/*     */     HiException he;
/*     */     try
/*     */     {
/* 171 */       this.ep.pauseEndpoint();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 175 */       he = new HiSysException("231201", sm.getString("TcpListener.pause01", String.valueOf(getLocalPort())), ex);
/*     */     }
/*     */ 
/* 181 */     if (this.log.isInfoEnabled())
/* 182 */       this.log.info(sm.getString("TcpListener.pause02", String.valueOf(getLocalPort())));
/*     */   }
/*     */ 
/*     */   public void serverResume(ServerEvent event)
/*     */   {
/*     */     try {
/* 188 */       this.ep.resumeEndpoint();
/*     */     } catch (Exception ex) {
/* 190 */       this.log.error(sm.getString("tcplistener.endpoint.resumeerror"), ex);
/*     */     }
/*     */ 
/* 193 */     if (this.log.isInfoEnabled())
/* 194 */       this.log.info(sm.getString("tcplistener.resume", String.valueOf(getLocalPort())));
/*     */   }
/*     */ 
/*     */   public void serverStart(ServerEvent event) throws HiException
/*     */   {
/*     */     try {
/* 200 */       this.ep.startEndpoint();
/*     */     } catch (Exception ex) {
/* 202 */       HiException he = new HiSysException("231201", sm.getString("TcpListener.start01", String.valueOf(getLocalPort())), ex);
/*     */ 
/* 206 */       throw he;
/*     */     }
/* 208 */     if (this.log.isInfoEnabled())
/* 209 */       this.log.info(sm.getString("TcpListener.start02", String.valueOf(getLocalPort())));
/*     */   }
/*     */ 
/*     */   public void serverStop(ServerEvent arg0) throws HiException
/*     */   {
/* 214 */     if (this.log.isInfoEnabled()) {
/* 215 */       this.log.info(sm.getString("tcplistener.stop", String.valueOf(getLocalPort())));
/*     */     }
/* 217 */     this.ep.stopEndpoint();
/*     */ 
/* 220 */     shutdown(this.threadpool);
/*     */   }
/*     */ 
/*     */   public void setBacklog(int i) {
/* 224 */     this.ep.setBacklog(i);
/*     */   }
/*     */ 
/*     */   public void setIpLstFil(String ipLstFil)
/*     */     throws Exception
/*     */   {
/* 233 */     if (this.ipset == null)
/* 234 */       this.ipset = new HashSet();
/* 235 */     this._ipLstFil = ipLstFil;
/* 236 */     if (!(StringUtils.isEmpty(this._ipLstFil))) {
/* 237 */       HiIPXMLParser ipParser = new HiIPXMLParser();
/* 238 */       URL url = HiResource.getResource(this._ipLstFil);
/* 239 */       if (url == null) {
/* 240 */         throw new HiException("212004", this._ipLstFil);
/*     */       }
/*     */ 
/* 243 */       this.ipset.addAll((ArrayList)ipParser.parse(url));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setLocalPort(int port)
/*     */   {
/* 249 */     this.ep.setLocalPort(port);
/*     */   }
/*     */ 
/*     */   public void setMaxThreads(int maxThreads)
/*     */   {
/* 254 */     this.maxThreads = maxThreads;
/*     */   }
/*     */ 
/*     */   public void setMinThreads(int minThreads) {
/* 258 */     if (minThreads <= 0)
/*     */       return;
/* 260 */     this.minThreads = minThreads;
/*     */   }
/*     */ 
/*     */   public void setPreLen(int preLen) {
/* 264 */     this.msginout.setPreLen(preLen);
/*     */   }
/*     */ 
/*     */   public void setPreLenType(String type) {
/* 268 */     this.msginout.setPreLenType(type);
/*     */   }
/*     */ 
/*     */   public String getPreLenType() {
/* 272 */     return this.msginout.getPreLenType();
/*     */   }
/*     */ 
/*     */   public void setServer(HiDefaultServer service)
/*     */   {
/* 277 */     super.setServer(service);
/* 278 */     this.ep.setLog(this.log);
/*     */   }
/*     */ 
/*     */   public void setServerSoTimeout(int i) {
/* 282 */     this.ep.setServerSoTimeout(i);
/*     */   }
/*     */ 
/*     */   public void setSoLinger(int i) {
/* 286 */     this.opthandler.setSoLinger(i);
/*     */   }
/*     */ 
/*     */   public void setTimeOut(int i)
/*     */   {
/* 291 */     this.opthandler.setSoTimeout(i);
/*     */   }
/*     */ 
/*     */   public void setTcpNoDelay(boolean b) {
/* 295 */     this.opthandler.setTcpNoDelay(b);
/*     */   }
/*     */ 
/*     */   public void shutdown(HiThreadPool tp) {
/* 299 */     tp.shutdown();
/*     */     try
/*     */     {
/* 302 */       tp.awaitTermination(10L, TimeUnit.SECONDS);
/*     */     }
/*     */     catch (InterruptedException e)
/*     */     {
/* 306 */       this.log.error("shut down thread pool err!", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public IpCheckFilter getIpcheck() {
/* 311 */     return this.ipcheck;
/*     */   }
/*     */ 
/*     */   public void setIpcheck(IpCheckFilter ipcheck) {
/* 315 */     this.ipcheck = ipcheck;
/*     */   }
/*     */ 
/*     */   public Set getIpset() {
/* 319 */     return this.ipset;
/*     */   }
/*     */ 
/*     */   public void setIpset(Set ipset) {
/* 323 */     this.ipset = ipset;
/*     */   }
/*     */ 
/*     */   public SetOptionFilter getOpthandler() {
/* 327 */     return this.opthandler;
/*     */   }
/*     */ 
/*     */   public void setOpthandler(SetOptionFilter opthandler) {
/* 331 */     this.opthandler = opthandler;
/*     */   }
/*     */ 
/*     */   public ISocketHandlerFilter getPoolhandler() {
/* 335 */     return this.poolhandler;
/*     */   }
/*     */ 
/*     */   protected ISocketHandler buildSocketHandler(List filters, ISocketHandler handler)
/*     */   {
/* 342 */     return filter(filters, handler);
/*     */   }
/*     */ 
/*     */   public static ISocketHandler filter(List filters, ISocketHandler handler)
/*     */   {
/* 347 */     ISocketHandler ret = handler;
/* 348 */     for (int i = filters.size() - 1; i >= 0; --i) {
/* 349 */       ret = filter((ISocketHandlerFilter)filters.get(i), ret);
/*     */     }
/* 351 */     return ret;
/*     */   }
/*     */ 
/*     */   public static ISocketHandler filter(ISocketHandlerFilter filter, ISocketHandler handler)
/*     */   {
/* 356 */     return new ISocketHandler(filter, handler) { private final ISocketHandlerFilter val$filter;
/*     */       private final ISocketHandler val$handler;
/*     */ 
/*     */       public void process(Socket socket, HiMessageContext ctx) { try { this.val$filter.process(socket, ctx, this.val$handler);
/*     */         }
/*     */         catch (Exception e) {
/* 362 */           throw new RuntimeException(e);
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public int getQueueSize() {
/* 369 */     return this.queueSize;
/*     */   }
/*     */ 
/*     */   public void setQueueSize(int queueSize) {
/* 373 */     this.queueSize = queueSize;
/*     */   }
/*     */ 
/*     */   public String getIpLstFil() {
/* 377 */     return this._ipLstFil;
/*     */   }
/*     */ 
/*     */   public void setSslMode(String mode)
/*     */     throws HiException
/*     */   {
/* 386 */     this.sslHandler.setSslMode(Integer.parseInt(mode));
/*     */   }
/*     */ 
/*     */   public void setIdentityKS(String identityKS)
/*     */     throws HiException
/*     */   {
/* 395 */     this.sslHandler.setIdentityKS(identityKS);
/*     */   }
/*     */ 
/*     */   public void setKeyPsw(String keyPsw)
/*     */     throws HiException
/*     */   {
/* 404 */     this.sslHandler.setKeyPsw(keyPsw);
/*     */   }
/*     */ 
/*     */   public void setAlg(String alg)
/*     */     throws HiException
/*     */   {
/* 412 */     this.sslHandler.setAlg(alg);
/*     */   }
/*     */ 
/*     */   public void setTrustKS(String trustKS)
/*     */     throws HiException
/*     */   {
/* 420 */     this.sslHandler.setTrustKS(trustKS);
/*     */   }
/*     */ 
/*     */   public void setAuthFlag(String authFlag)
/*     */     throws HiException
/*     */   {
/* 428 */     this.sslHandler.setAuthFlag(Integer.parseInt(authFlag));
/*     */   }
/*     */ }