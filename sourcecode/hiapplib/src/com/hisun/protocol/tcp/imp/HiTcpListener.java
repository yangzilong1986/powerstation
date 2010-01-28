 package com.hisun.protocol.tcp.imp;
 
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiSysException;
 import com.hisun.framework.HiDefaultServer;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.framework.imp.HiAbstractListener;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.HiMessageInOut;
 import com.hisun.protocol.tcp.HiSSLHandler;
 import com.hisun.protocol.tcp.HiTcpEndpoint;
 import com.hisun.protocol.tcp.ISocketHandler;
 import com.hisun.protocol.tcp.ISocketHandlerFilter;
 import com.hisun.protocol.tcp.filters.IpCheckFilter;
 import com.hisun.protocol.tcp.filters.SetOptionFilter;
 import com.hisun.protocol.tcp.filters.SocketHandlers;
 import com.hisun.protocol.tcp.parser.HiIPXMLParser;
 import com.hisun.util.HiResource;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiThreadPool;
 import java.net.Socket;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Set;
 import java.util.concurrent.TimeUnit;
 import org.apache.commons.lang.StringUtils;
 
 public class HiTcpListener extends HiAbstractListener
 {
   protected HiTcpEndpoint ep = new HiTcpEndpoint();
   public ISocketHandler handler;
   IpCheckFilter ipcheck;
   Set ipset;
   protected HiMessageInOut msginout = new HiMessageInOut();
   protected HiSSLHandler sslHandler = HiSSLHandler.getInstance();
   SetOptionFilter opthandler = new SetOptionFilter();
   ISocketHandlerFilter poolhandler;
   String _ipLstFil = null;
   protected HiThreadPool threadpool;
   protected int maxThreads = 50; protected int minThreads = 5; protected int queueSize = -1;
 
   public HiTcpListener()
   {
     setSoLinger(-1);
 
     setServerSoTimeout(1000);
     setTcpNoDelay(true);
   }
 
   public ISocketHandler buildSocketHandler()
     throws HiException
   {
     return null;
   }
 
   public int getBacklog() {
     return this.ep.getBacklog();
   }
 
   public int getLocalPort() {
     return this.ep.getLocalPort();
   }
 
   public int getMaxThreads()
   {
     return this.maxThreads;
   }
 
   public int getMinThreads() {
     return this.minThreads;
   }
 
   public int getPreLen() {
     return this.msginout.getPreLen();
   }
 
   public int getServerSoTimeout() {
     return this.ep.getServerSoTimeout();
   }
 
   public void serverDestroy(ServerEvent arg0)
     throws HiException
   {
     if (this.log.isInfoEnabled()) {
       this.log.info(sm.getString("tcplistener.stop", String.valueOf(getLocalPort())));
     }
     this.ep.stopEndpoint();
   }
 
   public void serverInit(ServerEvent event)
     throws HiException
   {
     if (this.queueSize != -1) {
       this.threadpool = HiThreadPool.createThreadPool(this.minThreads, this.maxThreads, this.queueSize);
     }
     else {
       this.threadpool = HiThreadPool.createThreadPool(this.minThreads, this.maxThreads);
     }
     this.opthandler.setLog(this.log);
 
     this.poolhandler = SocketHandlers.poolFilter(this.log, this.threadpool);
 
     if (this.handler == null)
       this.handler = buildSocketHandler();
     this.ep.setHandler(this.handler);
 
     this.ep.setSslHandler(this.sslHandler);
     try {
       this.ep.initEndpoint();
     }
     catch (Exception ex)
     {
       throw HiException.makeException(ex);
     }
     if (!(this.log.isInfoEnabled()))
       return;
     this.log.info(sm.getString("tcplistener.init", "listener"));
   }
 
   public void serverPause(ServerEvent event)
   {
     HiException he;
     try
     {
       this.ep.pauseEndpoint();
     }
     catch (Exception ex)
     {
       he = new HiSysException("231201", sm.getString("TcpListener.pause01", String.valueOf(getLocalPort())), ex);
     }
 
     if (this.log.isInfoEnabled())
       this.log.info(sm.getString("TcpListener.pause02", String.valueOf(getLocalPort())));
   }
 
   public void serverResume(ServerEvent event)
   {
     try {
       this.ep.resumeEndpoint();
     } catch (Exception ex) {
       this.log.error(sm.getString("tcplistener.endpoint.resumeerror"), ex);
     }
 
     if (this.log.isInfoEnabled())
       this.log.info(sm.getString("tcplistener.resume", String.valueOf(getLocalPort())));
   }
 
   public void serverStart(ServerEvent event) throws HiException
   {
     try {
       this.ep.startEndpoint();
     } catch (Exception ex) {
       HiException he = new HiSysException("231201", sm.getString("TcpListener.start01", String.valueOf(getLocalPort())), ex);
 
       throw he;
     }
     if (this.log.isInfoEnabled())
       this.log.info(sm.getString("TcpListener.start02", String.valueOf(getLocalPort())));
   }
 
   public void serverStop(ServerEvent arg0) throws HiException
   {
     if (this.log.isInfoEnabled()) {
       this.log.info(sm.getString("tcplistener.stop", String.valueOf(getLocalPort())));
     }
     this.ep.stopEndpoint();
 
     shutdown(this.threadpool);
   }
 
   public void setBacklog(int i) {
     this.ep.setBacklog(i);
   }
 
   public void setIpLstFil(String ipLstFil)
     throws Exception
   {
     if (this.ipset == null)
       this.ipset = new HashSet();
     this._ipLstFil = ipLstFil;
     if (!(StringUtils.isEmpty(this._ipLstFil))) {
       HiIPXMLParser ipParser = new HiIPXMLParser();
       URL url = HiResource.getResource(this._ipLstFil);
       if (url == null) {
         throw new HiException("212004", this._ipLstFil);
       }
 
       this.ipset.addAll((ArrayList)ipParser.parse(url));
     }
   }
 
   public void setLocalPort(int port)
   {
     this.ep.setLocalPort(port);
   }
 
   public void setMaxThreads(int maxThreads)
   {
     this.maxThreads = maxThreads;
   }
 
   public void setMinThreads(int minThreads) {
     if (minThreads <= 0)
       return;
     this.minThreads = minThreads;
   }
 
   public void setPreLen(int preLen) {
     this.msginout.setPreLen(preLen);
   }
 
   public void setPreLenType(String type) {
     this.msginout.setPreLenType(type);
   }
 
   public String getPreLenType() {
     return this.msginout.getPreLenType();
   }
 
   public void setServer(HiDefaultServer service)
   {
     super.setServer(service);
     this.ep.setLog(this.log);
   }
 
   public void setServerSoTimeout(int i) {
     this.ep.setServerSoTimeout(i);
   }
 
   public void setSoLinger(int i) {
     this.opthandler.setSoLinger(i);
   }
 
   public void setTimeOut(int i)
   {
     this.opthandler.setSoTimeout(i);
   }
 
   public void setTcpNoDelay(boolean b) {
     this.opthandler.setTcpNoDelay(b);
   }
 
   public void shutdown(HiThreadPool tp) {
     tp.shutdown();
     try
     {
       tp.awaitTermination(10L, TimeUnit.SECONDS);
     }
     catch (InterruptedException e)
     {
       this.log.error("shut down thread pool err!", e);
     }
   }
 
   public IpCheckFilter getIpcheck() {
     return this.ipcheck;
   }
 
   public void setIpcheck(IpCheckFilter ipcheck) {
     this.ipcheck = ipcheck;
   }
 
   public Set getIpset() {
     return this.ipset;
   }
 
   public void setIpset(Set ipset) {
     this.ipset = ipset;
   }
 
   public SetOptionFilter getOpthandler() {
     return this.opthandler;
   }
 
   public void setOpthandler(SetOptionFilter opthandler) {
     this.opthandler = opthandler;
   }
 
   public ISocketHandlerFilter getPoolhandler() {
     return this.poolhandler;
   }
 
   protected ISocketHandler buildSocketHandler(List filters, ISocketHandler handler)
   {
     return filter(filters, handler);
   }
 
   public static ISocketHandler filter(List filters, ISocketHandler handler)
   {
     ISocketHandler ret = handler;
     for (int i = filters.size() - 1; i >= 0; --i) {
       ret = filter((ISocketHandlerFilter)filters.get(i), ret);
     }
     return ret;
   }
 
   public static ISocketHandler filter(ISocketHandlerFilter filter, ISocketHandler handler)
   {
     return new ISocketHandler(filter, handler) { private final ISocketHandlerFilter val$filter;
       private final ISocketHandler val$handler;
 
       public void process(Socket socket, HiMessageContext ctx) { try { this.val$filter.process(socket, ctx, this.val$handler);
         }
         catch (Exception e) {
           throw new RuntimeException(e);
         }
       }
     };
   }
 
   public int getQueueSize() {
     return this.queueSize;
   }
 
   public void setQueueSize(int queueSize) {
     this.queueSize = queueSize;
   }
 
   public String getIpLstFil() {
     return this._ipLstFil;
   }
 
   public void setSslMode(String mode)
     throws HiException
   {
     this.sslHandler.setSslMode(Integer.parseInt(mode));
   }
 
   public void setIdentityKS(String identityKS)
     throws HiException
   {
     this.sslHandler.setIdentityKS(identityKS);
   }
 
   public void setKeyPsw(String keyPsw)
     throws HiException
   {
     this.sslHandler.setKeyPsw(keyPsw);
   }
 
   public void setAlg(String alg)
     throws HiException
   {
     this.sslHandler.setAlg(alg);
   }
 
   public void setTrustKS(String trustKS)
     throws HiException
   {
     this.sslHandler.setTrustKS(trustKS);
   }
 
   public void setAuthFlag(String authFlag)
     throws HiException
   {
     this.sslHandler.setAuthFlag(Integer.parseInt(authFlag));
   }
 }