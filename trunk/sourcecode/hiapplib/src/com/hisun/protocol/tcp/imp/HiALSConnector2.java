/*      */ package com.hisun.protocol.tcp.imp;
/*      */ 
/*      */ import com.hisun.exception.HiException;
/*      */ import com.hisun.framework.HiDefaultServer;
/*      */ import com.hisun.framework.event.ServerEvent;
/*      */ import com.hisun.framework.imp.HiAbstractListener;
/*      */ import com.hisun.hilog4j.HiLog;
/*      */ import com.hisun.hilog4j.Logger;
/*      */ import com.hisun.message.HiMessage;
/*      */ import com.hisun.message.HiMessageContext;
/*      */ import com.hisun.parse.HiVariableExpander;
/*      */ import com.hisun.pubinterface.IHandler;
/*      */ import com.hisun.util.HiByteBuffer;
/*      */ import com.hisun.util.HiResource;
/*      */ import com.hisun.util.HiStringManager;
/*      */ import com.hisun.util.HiThreadPool;
/*      */ import edu.emory.mathcs.backport.java.util.concurrent.Executors;
/*      */ import edu.emory.mathcs.backport.java.util.concurrent.Future;
/*      */ import edu.emory.mathcs.backport.java.util.concurrent.RejectedExecutionException;
/*      */ import edu.emory.mathcs.backport.java.util.concurrent.ScheduledExecutorService;
/*      */ import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
/*      */ import edu.emory.mathcs.backport.java.util.concurrent.locks.Lock;
/*      */ import edu.emory.mathcs.backport.java.util.concurrent.locks.ReentrantLock;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InterruptedIOException;
/*      */ import java.io.OutputStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.ServerSocket;
/*      */ import java.net.Socket;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import javax.net.ServerSocketFactory;
/*      */ import org.apache.commons.digester.Digester;
/*      */ import org.apache.commons.digester.substitution.VariableSubstitutor;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ 
/*      */ public class HiALSConnector2 extends HiAbstractListener
/*      */   implements IHandler
/*      */ {
/*  258 */   static final HiConnXMLParser parser = new HiConnXMLParser();
/*      */   ConnectionInformation _ConnInf;
/*      */   ScheduledExecutorService scheduler;
/*      */   HiThreadPool tp;
/*      */   String cfgfile;
/*      */   protected int maxThreads;
/*      */   protected int minThreads;
/*      */   protected int queueSize;
/*      */   protected int timeOut;
/*      */   static Class class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionInformation;
/*      */   static Class class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionGroup;
/*      */   static Class class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionNode;
/*      */ 
/*      */   public HiALSConnector2()
/*      */   {
/* 1100 */     this.maxThreads = 50; this.minThreads = 5; this.queueSize = -1;
/* 1101 */     this.timeOut = 2;
/*      */   }
/*      */ 
/*      */   public void setCfgfile(String cfgfile)
/*      */   {
/*  273 */     this.cfgfile = cfgfile;
/*      */   }
/*      */ 
/*      */   void initChannel(HiConnXMLParser parser, URL url)
/*      */     throws Exception
/*      */   {
/*  280 */     this._ConnInf = ((ConnectionInformation)parser.parse(url));
/*      */   }
/*      */ 
/*      */   void closeChannel()
/*      */   {
/*      */     ConnectionGroup pGroup;
/*  291 */     this.log.info("close channel...");
/*      */ 
/*  293 */     for (int i = 0; i < this._ConnInf.number; ++i) {
/*  294 */       pGroup = this._ConnInf.group(i);
/*  295 */       pGroup.manager_tid.cancel(false);
/*      */     }
/*  297 */     this.scheduler.shutdownNow();
/*  298 */     this.log.info("shutdown manage thread...");
/*      */     try {
/*  300 */       this.scheduler.awaitTermination(5L, TimeUnit.SECONDS);
/*      */     } catch (InterruptedException e) {
/*  302 */       this.log.error("can not stop manager thread!");
/*      */     }
/*  304 */     this.log.info("shutdown manage thread ok...");
/*  305 */     this._ConnInf.m_lock.lock();
/*      */     try {
/*  307 */       this.log.info("close connection...");
/*  308 */       for (i = 0; i < this._ConnInf.number; ++i) {
/*  309 */         pGroup = this._ConnInf.group(i);
/*  310 */         this.log.info("close group:" + pGroup.remote_ip);
/*      */ 
/*  312 */         for (int j = 0; j < pGroup.number; ++j) {
/*  313 */           ConnectionNode pConnection = pGroup.connection(j);
/*  314 */           this.log.info("close listener:" + pConnection.local_port);
/*      */ 
/*  317 */           pConnection.closeConnection();
/*  318 */           this.log.info("quit thread...");
/*  319 */           if (pConnection.reader_tid != null) {
/*  320 */             thdCancel(pConnection.reader_tid);
/*      */ 
/*  323 */             pConnection.reader_tid = null;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  331 */       if (this.log.isInfoEnabled())
/*  332 */         this.log.info("close channle success!");
/*      */     }
/*      */     finally {
/*  335 */       this._ConnInf.m_lock.unlock();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void thdCancel(Thread thread)
/*      */   {
/*  341 */     thread.interrupt();
/*      */     try {
/*  343 */       thread.join();
/*      */     }
/*      */     catch (InterruptedException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void sendMessage(HiMessage msg)
/*      */     throws HiException
/*      */   {
/*  353 */     ConnectionNode pConnection = findConnection();
/*      */ 
/*  355 */     if (pConnection == null) {
/*  356 */       throw new HiException("231204", "请求编号=[" + msg.getRequestId() + "] 没有可发送的通道!");
/*      */     }
/*      */ 
/*  359 */     send_start(msg, pConnection);
/*      */   }
/*      */ 
/*      */   private ConnectionNode findConnection()
/*      */   {
/*  364 */     ConnectionNode pConnection = null;
/*      */ 
/*  370 */     this._ConnInf.m_lock.lock();
/*      */     try {
/*  372 */       int iGroupId = this._ConnInf.last_group + 1;
/*  373 */       if (iGroupId >= this._ConnInf.number) {
/*  374 */         iGroupId = 0;
/*      */       }
/*      */ 
/*  377 */       ConnectionGroup pGroup = this._ConnInf.group(iGroupId);
/*      */ 
/*  379 */       if (pGroup.status() == 1) {
/*  380 */         pConnection = pGroup.nextConn();
/*      */ 
/*  382 */         if (this.log.isDebugEnabled()) {
/*  383 */           this.log.debug("find connection:" + pConnection.local_port + "," + pConnection.remote_port);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  390 */         if (this.log.isDebugEnabled()) {
/*  391 */           this.log.debug("group :" + pGroup.remote_ip + " busy, find another group");
/*      */         }
/*      */ 
/*  395 */         iGroupId = -1;
/*  396 */         for (int i = 0; i < this._ConnInf.number; ++i) {
/*  397 */           if (i == iGroupId) {
/*      */             continue;
/*      */           }
/*  400 */           pGroup = this._ConnInf.group(i);
/*  401 */           if (pGroup.status() == 1) {
/*  402 */             iGroupId = i;
/*  403 */             pConnection = pGroup.nextConn();
/*  404 */             if (!(this.log.isDebugEnabled())) break;
/*  405 */             this.log.debug("find connection:" + pConnection.local_port + "," + pConnection.remote_port);
/*      */ 
/*  408 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  413 */       this._ConnInf.last_group = iGroupId;
/*      */     }
/*      */     finally {
/*  416 */       this._ConnInf.m_lock.unlock();
/*      */     }
/*      */ 
/*  419 */     return pConnection;
/*      */   }
/*      */ 
/*      */   private void send_start(HiMessage msg, ConnectionNode pConnection) throws HiException
/*      */   {
/*  424 */     if (this.log.isDebugEnabled()) {
/*  425 */       this.log.debug("选择发送通道:" + pConnection.send_fd);
/*      */     }
/*  427 */     HiByteBuffer buf = (HiByteBuffer)msg.getBody();
/*  428 */     byte[] data = buf.getBytes();
/*      */ 
/*  430 */     int iSendLen = this._ConnInf.pre_len;
/*      */     try
/*      */     {
/*  433 */       pConnection.sendData(data, iSendLen);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  439 */       hsocketclose(pConnection.send_fd);
/*  440 */       pConnection.send_fd = null;
/*      */ 
/*  443 */       pConnection.pGroup.setStatus(0);
/*  444 */       throw new HiException("231207", e.toString(), e);
/*      */     }
/*      */ 
/*  447 */     if (this.log.isInfoEnabled()) {
/*  448 */       Socket socket = pConnection.send_fd;
/*  449 */       HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
/*  450 */       String ip = socket.getInetAddress().getHostAddress();
/*  451 */       this.log.info(sm.getString("tcplistener.send", msg.getRequestId(), ip, String.valueOf(byteBuffer.length()), byteBuffer.toString()));
/*      */     }
/*      */   }
/*      */ 
/*      */   static void hsocketclose(Socket s)
/*      */   {
/*  461 */     if (s == null)
/*  462 */       return;
/*      */     try {
/*  464 */       s.close();
/*      */     } catch (IOException e) {
/*      */     }
/*      */   }
/*      */ 
/*      */   static void hsocketclose(ServerSocket s) {
/*  470 */     if (s == null)
/*  471 */       return;
/*      */     try {
/*  473 */       s.close();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void serverInit(ServerEvent event)
/*      */     throws HiException
/*      */   {
/* 1024 */     if (this.queueSize == -1)
/* 1025 */       this.tp = HiThreadPool.createThreadPool(this.minThreads, this.maxThreads);
/*      */     else {
/* 1027 */       this.tp = HiThreadPool.createThreadPool(this.minThreads, this.maxThreads, this.queueSize);
/*      */     }
/*      */ 
/* 1030 */     URL url = HiResource.getResource(this.cfgfile);
/*      */     try {
/* 1032 */       initChannel(parser, url);
/*      */     } catch (Exception e) {
/* 1034 */       throw new HiException("231252", this.cfgfile, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void serverStart(ServerEvent event) throws HiException {
/* 1039 */     this.scheduler = Executors.newScheduledThreadPool(this._ConnInf.number);
/*      */ 
/* 1043 */     for (int i = 0; i < this._ConnInf.number; ++i) {
/* 1044 */       ConnectionGroup pGroup = this._ConnInf.group(i);
/* 1045 */       Runnable manager = new GroupManager(i);
/*      */ 
/* 1047 */       pGroup.manager_tid = this.scheduler.scheduleWithFixedDelay(manager, 1L, this._ConnInf.error_check_itv, TimeUnit.SECONDS);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void serverStop(ServerEvent event) throws HiException
/*      */   {
/* 1053 */     closeChannel();
/* 1054 */     if (this.log.isDebugEnabled())
/* 1055 */       this.log.debug("server stop ok!");
/*      */   }
/*      */ 
/*      */   public void serverDestroy(ServerEvent event)
/*      */     throws HiException
/*      */   {
/*      */   }
/*      */ 
/*      */   public void serverPause(ServerEvent event)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void serverResume(ServerEvent event)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void setMaxThreads(int maxThreads)
/*      */   {
/* 1077 */     this.maxThreads = maxThreads;
/*      */   }
/*      */ 
/*      */   public void setMinThreads(int minThreads) {
/* 1081 */     if (minThreads <= 0)
/*      */       return;
/* 1083 */     this.minThreads = minThreads;
/*      */   }
/*      */ 
/*      */   public int getMaxThreads()
/*      */   {
/* 1088 */     return this.maxThreads;
/*      */   }
/*      */ 
/*      */   public int getMinThreads()
/*      */   {
/* 1093 */     return this.minThreads;
/*      */   }
/*      */ 
/*      */   public void process(HiMessageContext ctx) throws HiException {
/* 1097 */     sendMessage(ctx.getCurrentMsg());
/*      */   }
/*      */ 
/*      */   public int getQueueSize()
/*      */   {
/* 1104 */     return this.queueSize;
/*      */   }
/*      */ 
/*      */   public void setQueueSize(int queueSize) {
/* 1108 */     this.queueSize = queueSize;
/*      */   }
/*      */ 
/*      */   public int getTimeOut() {
/* 1112 */     return this.timeOut;
/*      */   }
/*      */ 
/*      */   public void setTimeOut(int timeOut) {
/* 1116 */     this.timeOut = timeOut;
/*      */   }
/*      */ 
/*      */   static Class class$(String x0)
/*      */   {
/*      */     try
/*      */     {
/*  973 */       return Class.forName(x0); } catch (ClassNotFoundException x1) { throw new NoClassDefFoundError().initCause(x1);
/*      */     }
/*      */   }
/*      */ 
/*      */   static class HiConnXMLParser
/*      */     implements ErrorHandler
/*      */   {
/*      */     private final Digester parser;
/*      */ 
/*      */     public HiConnXMLParser()
/*      */     {
/*  972 */       this.parser = new Digester();
/*  973 */       this.parser.addObjectCreate("Config", (HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionInformation == null) ? (HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionInformation = HiALSConnector2.class$("com.hisun.protocol.tcp.imp.HiALSConnector2$ConnectionInformation")) : HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionInformation);
/*  974 */       this.parser.addBeanPropertySetter("Config/LocalIp", "local_ip");
/*  975 */       this.parser.addBeanPropertySetter("Config/ErrorCheckInterval", "error_check_itv");
/*      */ 
/*  977 */       this.parser.addBeanPropertySetter("Config/CheckInterval", "check_itv");
/*  978 */       this.parser.addBeanPropertySetter("Config/MaxTimeOut", "max_timeout");
/*  979 */       this.parser.addBeanPropertySetter("Config/PreLen", "pre_len");
/*      */ 
/*  981 */       this.parser.addObjectCreate("Config/Group", (HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionGroup == null) ? (HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionGroup = HiALSConnector2.class$("com.hisun.protocol.tcp.imp.HiALSConnector2$ConnectionGroup")) : HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionGroup);
/*  982 */       this.parser.addBeanPropertySetter("Config/Group/RemoteIp", "remote_ip");
/*  983 */       this.parser.addSetNext("Config/Group", "addGroup");
/*      */ 
/*  985 */       this.parser.addObjectCreate("Config/Group/Connection", (HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionNode == null) ? (HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionNode = HiALSConnector2.class$("com.hisun.protocol.tcp.imp.HiALSConnector2$ConnectionNode")) : HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionNode);
/*      */ 
/*  987 */       this.parser.addBeanPropertySetter("Config/Group/Connection/remote_port", "remote_port");
/*      */ 
/*  989 */       this.parser.addBeanPropertySetter("Config/Group/Connection/local_port", "local_port");
/*      */ 
/*  991 */       this.parser.addSetNext("Config/Group/Connection", "addConnection");
/*      */ 
/*  993 */       this.parser.setErrorHandler(this);
/*      */ 
/*  996 */       this.parser.setSubstitutor(new VariableSubstitutor(new HiVariableExpander(), null));
/*      */     }
/*      */ 
/*      */     public void error(SAXParseException arg0) throws SAXException
/*      */     {
/* 1001 */       throw arg0;
/*      */     }
/*      */ 
/*      */     public void fatalError(SAXParseException arg0)
/*      */       throws SAXException
/*      */     {
/* 1007 */       throw arg0;
/*      */     }
/*      */ 
/*      */     public Object parse(URL url) throws Exception {
/* 1011 */       this.parser.clear();
/* 1012 */       this.parser.setClassLoader(Thread.currentThread().getContextClassLoader());
/*      */ 
/* 1014 */       return this.parser.parse(url.openStream());
/*      */     }
/*      */ 
/*      */     public void warning(SAXParseException arg0) throws SAXException
/*      */     {
/* 1019 */       throw arg0;
/*      */     }
/*      */   }
/*      */ 
/*      */   class ConnectionReader
/*      */     implements Runnable
/*      */   {
/*      */     HiALSConnector2.ConnectionNode pConnection;
/*      */ 
/*      */     public ConnectionReader(HiALSConnector2.ConnectionNode paramConnectionNode)
/*      */     {
/*  691 */       this.pConnection = paramConnectionNode;
/*      */     }
/*      */ 
/*      */     public void run() {
/*      */       try {
/*  696 */         run2();
/*      */       } catch (Throwable e) {
/*  698 */         reader_end();
/*  699 */         HiALSConnector2.this.log.error("ConnectionReader:[" + e + "]", e);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void run2()
/*      */     {
/*  705 */       int iLen = 0;
/*  706 */       int iRecvLen = 0;
/*      */ 
/*  708 */       if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  709 */         HiALSConnector2.this.log.debug("开始建立监听：" + this.pConnection.local_port);
/*      */       }
/*      */       try
/*      */       {
/*  713 */         this.pConnection.listen_fd = my_listen(HiALSConnector2.this._ConnInf.local_ip, this.pConnection.local_port);
/*      */       }
/*      */       catch (IOException e1)
/*      */       {
/*  718 */         s = "绑定本地监听端口[" + HiALSConnector2.this._ConnInf.local_ip + "][" + this.pConnection.local_port + "]失败!";
/*      */ 
/*  720 */         HiALSConnector2.this.log.error(s);
/*  721 */         reader_end();
/*  722 */         return;
/*      */       }
/*      */ 
/*  725 */       if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  726 */         HiALSConnector2.this.log.debug("等待连接...");
/*      */       }
/*      */       try
/*      */       {
/*  730 */         this.pConnection.recv_fd = this.pConnection.listen_fd.accept();
/*  731 */         this.pConnection.recv_fd.setTcpNoDelay(true);
/*      */       } catch (IOException e) {
/*  733 */         HiALSConnector2.this.log.error(this.pConnection.local_port + " 等待接受对方连接失败!" + e);
/*  734 */         reader_end();
/*  735 */         return;
/*      */       }
/*      */ 
/*  740 */       String aClientIp = this.pConnection.recv_fd.getInetAddress().getHostAddress();
/*      */ 
/*  742 */       int iRc = checkConnectionIp(aClientIp);
/*  743 */       if (iRc < 0) {
/*  744 */         HiALSConnector2.this.log.error("非法IP连接:" + aClientIp);
/*  745 */         reader_end();
/*  746 */         return;
/*      */       }
/*      */ 
/*  750 */       String s = "接收连接请求：IP=" + aClientIp + ",本地端口=" + this.pConnection.local_port;
/*      */ 
/*  752 */       HiALSConnector2.this.log.info(s);
/*      */ 
/*  754 */       byte[] aPreLen = new byte[HiALSConnector2.this._ConnInf.pre_len];
/*  755 */       byte[] aData = new byte[4096];
/*  756 */       while (!(Thread.interrupted()))
/*      */       {
/*      */         String msg;
/*  758 */         Arrays.fill(aPreLen, 0);
/*      */         try {
/*  760 */           iLen = socketRead(this.pConnection.recv_fd, aPreLen, HiALSConnector2.this._ConnInf.pre_len, HiALSConnector2.this._ConnInf.max_timeout);
/*      */         }
/*      */         catch (InterruptedIOException ie)
/*      */         {
/*  764 */           msg = "接收前置长度超时,断开连接，超时时间(秒)：" + HiALSConnector2.this._ConnInf.max_timeout;
/*      */ 
/*  766 */           HiALSConnector2.this.log.error(msg);
/*  767 */           break label932:
/*      */         } catch (IOException e) {
/*      */         }
/*  770 */         break;
/*      */ 
/*  772 */         if (iLen != HiALSConnector2.this._ConnInf.pre_len) {
/*  773 */           msg = "接收前置长度失败!应接收长度[" + HiALSConnector2.this._ConnInf.pre_len + "]已接收长度[" + iLen + "]";
/*      */ 
/*  775 */           HiALSConnector2.this.log.error(msg);
/*  776 */           HiALSConnector2.this.log.error("对方断开连接！");
/*  777 */           break;
/*      */         }
/*  779 */         iRecvLen = Integer.parseInt(new String(aPreLen));
/*  780 */         if (iRecvLen == 0)
/*      */         {
/*  782 */           if (HiALSConnector2.this.log.isDebugEnabled());
/*  783 */           HiALSConnector2.this.log.debug("接收到空闲检查报文" + this.pConnection.recv_fd);
/*      */         }
/*      */ 
/*  789 */         Arrays.fill(aData, 0);
/*      */         try
/*      */         {
/*  793 */           iLen = socketRead(this.pConnection.recv_fd, aData, iRecvLen, HiALSConnector2.this.timeOut);
/*      */         }
/*      */         catch (InterruptedIOException msg)
/*      */         {
/*  797 */           msg = "接收数据超时,断开连接，超时时间(秒)：" + HiALSConnector2.this.timeOut;
/*  798 */           HiALSConnector2.this.log.error(msg);
/*  799 */           break label932:
/*      */         } catch (IOException msg) {
/*      */         }
/*  802 */         break;
/*      */ 
/*  804 */         if (iLen != iRecvLen)
/*      */         {
/*  807 */           msg = "接收数据失败!应接收长度[" + iRecvLen + "]已接收长度[" + iLen + "]";
/*      */ 
/*  809 */           HiALSConnector2.this.log.error(msg);
/*  810 */           HiALSConnector2.this.log.error("对方断开连接！");
/*  811 */           break;
/*      */         }
/*  813 */         HiMessage pMsg = usfCreateMsg();
/*      */ 
/*  815 */         HiByteBuffer buf = new HiByteBuffer(iRecvLen);
/*  816 */         buf.append(aData, 0, iRecvLen);
/*  817 */         pMsg.setBody(buf);
/*      */ 
/*  819 */         if (HiALSConnector2.this.log.isInfoEnabled())
/*      */         {
/*  823 */           HiByteBuffer byteBuffer = (HiByteBuffer)pMsg.getBody();
/*      */ 
/*  825 */           HiALSConnector2.this.log.info(HiAbstractListener.sm.getString("tcplistener.receive", pMsg.getRequestId(), aClientIp, String.valueOf(byteBuffer.length()), byteBuffer.toString()));
/*      */         }
/*      */ 
/*  832 */         pMsg.setHeadItem("SIP", aClientIp);
/*      */ 
/*  836 */         long curtime = System.currentTimeMillis();
/*  837 */         pMsg.setHeadItem("STM", new Long(curtime));
/*      */ 
/*  840 */         if (usfSendRequest(pMsg) < 0)
/*      */         {
/*  842 */           HiALSConnector2.this.log.error("发送消息失败!");
/*  843 */           break;
/*      */         }
/*      */       }
/*      */ 
/*  847 */       label932: reader_end();
/*      */     }
/*      */ 
/*      */     private void reader_end()
/*      */     {
/*  854 */       HiALSConnector2.hsocketclose(this.pConnection.listen_fd);
/*  855 */       this.pConnection.listen_fd = null;
/*  856 */       HiALSConnector2.hsocketclose(this.pConnection.recv_fd);
/*  857 */       this.pConnection.recv_fd = null;
/*      */ 
/*  861 */       this.pConnection.pGroup.setStatus(0);
/*      */ 
/*  863 */       this.pConnection.reader_tid = null;
/*      */ 
/*  865 */       if (HiALSConnector2.this.log.isDebugEnabled())
/*  866 */         HiALSConnector2.this.log.debug("接收线程退出,remote port:" + this.pConnection.remote_port);
/*      */     }
/*      */ 
/*      */     ServerSocket my_listen(String local_ip, int port)
/*      */       throws IOException
/*      */     {
/*  874 */       ServerSocket sockfd = null;
/*      */ 
/*  877 */       ServerSocketFactory factory = ServerSocketFactory.getDefault();
/*  878 */       sockfd = factory.createServerSocket(port);
/*      */ 
/*  880 */       return sockfd;
/*      */     }
/*      */ 
/*      */     public int socketRead(Socket socket, byte[] preLen, int pre_len, int max_timeout) throws IOException
/*      */     {
/*  885 */       if (max_timeout >= 0) {
/*  886 */         socket.setSoTimeout(max_timeout * 1000);
/*      */       }
/*  888 */       int nread = 0;
/*      */ 
/*  890 */       InputStream in = socket.getInputStream();
/*  891 */       while (nread != pre_len) {
/*  892 */         int n = in.read(preLen, nread, pre_len - nread);
/*  893 */         if (n == -1)
/*      */           break;
/*  895 */         nread += n;
/*      */       }
/*  897 */       return nread;
/*      */     }
/*      */ 
/*      */     public HiMessage usfCreateMsg() {
/*  901 */       HiMessage msg = new HiMessage(HiALSConnector2.this.getServer().getName(), HiALSConnector2.this.getMsgType());
/*      */ 
/*  904 */       msg.setHeadItem("ECT", "text/plain");
/*  905 */       msg.setHeadItem("SCH", "rp");
/*      */ 
/*  908 */       return msg;
/*      */     }
/*      */ 
/*      */     public int usfSendRequest(HiMessage msg) {
/*  912 */       Runnable worker = new Runnable(msg) {
/*      */         private final HiMessage val$msg;
/*      */ 
/*      */         public void run() { try { HiALSConnector2.this.getServer().process(HiALSConnector2.this.getMessageContext(this.val$msg));
/*      */           } catch (Throwable e) {
/*  917 */             HiALSConnector2.this.log.error(this.val$msg.getRequestId() + ":" + e, e);
/*      */           } finally {
/*  919 */             HiLog.close(this.val$msg);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       };
/*      */       while (true)
/*      */       {
/*      */         try
/*      */         {
/*  931 */           HiALSConnector2.this.tp.execute(worker);
/*      */         }
/*      */         catch (RejectedExecutionException e) {
/*  934 */           HiALSConnector2.this.log.warn("Please increase maxThreads!");
/*  935 */           Thread.currentThread(); Thread.yield();
/*      */           try {
/*  937 */             Thread.currentThread(); Thread.sleep(1000L);
/*      */           } catch (InterruptedException e1) {
/*  939 */             break label90:
/*      */           }
/*  941 */           if (!(HiALSConnector2.this.tp.isShutdown())) if (!(Thread.currentThread().isInterrupted())) break label87;
/*      */         }
/*  943 */         label87: break;
/*      */       }
/*      */ 
/*  948 */       label90: return 0;
/*      */     }
/*      */ 
/*      */     int checkConnectionIp(String ip)
/*      */     {
/*  957 */       for (int i = 0; i < HiALSConnector2.this._ConnInf.number; ++i) {
/*  958 */         if (HiALSConnector2.this._ConnInf.group(i).remote_ip.equals(ip)) {
/*  959 */           return 0;
/*      */         }
/*      */       }
/*  962 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */   class GroupManager
/*      */     implements Runnable
/*      */   {
/*      */     private final int group_id;
/*      */     private final HiALSConnector2.ConnectionGroup pGroup;
/*      */     private final byte[] bufs;
/*  486 */     int i = 0;
/*      */ 
/*      */     public GroupManager(int paramInt) {
/*  489 */       this.group_id = paramInt;
/*  490 */       this.pGroup = HiALSConnector2.this._ConnInf.group(paramInt);
/*  491 */       this.bufs = new byte[HiALSConnector2.this._ConnInf.pre_len];
/*  492 */       Arrays.fill(this.bufs, 48);
/*      */     }
/*      */ 
/*      */     public void run() {
/*      */       try {
/*  497 */         run2();
/*      */       } catch (Throwable t) {
/*  499 */         HiALSConnector2.this.log.fatal("组管理线程异常退出!", t);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void run2()
/*      */     {
/*  505 */       if ((this.pGroup.status() == 0) || (this.i >= HiALSConnector2.this._ConnInf.check_itv)) {
/*  506 */         if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  507 */           HiALSConnector2.this.log.debug("group manage thread runing, group=" + this.group_id + ",time=" + this.i);
/*      */         }
/*      */ 
/*  510 */         int iRc = checkConnectionGroup();
/*  511 */         if (iRc < 0) {
/*  512 */           deleteConnectionGroup();
/*  513 */           iRc = createConnectionGroup();
/*      */         }
/*  515 */         if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  516 */           HiALSConnector2.this.log.debug("run end" + this.i + "," + HiALSConnector2.this.getName());
/*      */         }
/*  518 */         if (iRc == 0) {
/*  519 */           this.i = 0;
/*      */         }
/*      */       }
/*  522 */       this.i += HiALSConnector2.this._ConnInf.error_check_itv;
/*      */     }
/*      */ 
/*      */     int createConnectionGroup()
/*      */     {
/*  530 */       int iRc = 0;
/*      */ 
/*  533 */       for (int i = 0; i < this.pGroup.number; ++i) {
/*  534 */         HiALSConnector2.ConnectionNode pConnection = this.pGroup.connection(i);
/*  535 */         if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  536 */           HiALSConnector2.this.log.debug("create connection: " + pConnection.local_port + "," + pConnection.remote_port);
/*      */         }
/*      */ 
/*  541 */         Runnable reader = new HiALSConnector2.ConnectionReader(HiALSConnector2.this, pConnection);
/*  542 */         pConnection.reader_tid = new Thread(reader, "ConnReader(" + pConnection.local_port + "," + pConnection.remote_port + ")");
/*      */ 
/*  545 */         pConnection.reader_tid.start();
/*      */ 
/*  548 */         iRc = createSendChannel(i);
/*  549 */         if (iRc < 0) {
/*  550 */           this.pGroup.setStatus(0);
/*  551 */           break;
/*      */         }
/*      */ 
/*  554 */         if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  555 */           HiALSConnector2.this.log.debug("success create connection: " + pConnection.local_port + "," + pConnection.remote_port);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  560 */       if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  561 */         HiALSConnector2.this.log.debug("ret from createConnectionGroup: " + iRc);
/*      */       }
/*      */ 
/*  564 */       if (iRc == 0)
/*  565 */         this.pGroup.setStatus(1);
/*  566 */       return iRc;
/*      */     }
/*      */ 
/*      */     int deleteConnectionGroup()
/*      */     {
/*  576 */       for (int i = 0; i < this.pGroup.number; ++i) {
/*  577 */         HiALSConnector2.ConnectionNode pConnection = this.pGroup.connection(i);
/*  578 */         if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  579 */           HiALSConnector2.this.log.debug("delete connection: " + pConnection.local_port + "," + pConnection.remote_port);
/*      */         }
/*      */ 
/*  583 */         pConnection.closeConnection();
/*      */ 
/*  585 */         if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  586 */           HiALSConnector2.this.log.debug("kill reader thread:" + pConnection.reader_tid);
/*      */         }
/*  588 */         if (pConnection.reader_tid != null) {
/*  589 */           HiALSConnector2.this.thdCancel(pConnection.reader_tid);
/*  590 */           pConnection.reader_tid = null;
/*      */         }
/*      */ 
/*  593 */         if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  594 */           HiALSConnector2.this.log.debug("success delete connection: " + pConnection.local_port + "," + pConnection.remote_port);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  599 */       return 0;
/*      */     }
/*      */ 
/*      */     int createSendChannel(int connection_id)
/*      */     {
/*  607 */       int iRc = 0;
/*      */ 
/*  609 */       HiALSConnector2.ConnectionNode pConnection = this.pGroup.connection(connection_id);
/*  610 */       if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  611 */         HiALSConnector2.this.log.debug("开始连接对方系统:IP=[" + this.pGroup.remote_ip + "] Port=[" + pConnection.remote_port + "]");
/*      */       }
/*      */ 
/*  614 */       for (int i = 0; i < HiALSConnector2.this._ConnInf.error_check_itv; ) {
/*  615 */         if (Thread.currentThread().isInterrupted())
/*      */           break;
/*      */         try
/*      */         {
/*  619 */           pConnection.connect();
/*      */ 
/*  621 */           String s = "连接对方系统成功!IP=[" + this.pGroup.remote_ip + "] Port=[" + pConnection.remote_port + "]";
/*      */ 
/*  623 */           HiALSConnector2.this.log.info(s);
/*  624 */           iRc = 0;
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  614 */           for (; ; ++i)
/*      */           {
/*  628 */             if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  629 */               HiALSConnector2.this.log.debug("连接对方系统失败!IP=[" + this.pGroup.remote_ip + "] Port=[" + pConnection.remote_port + "]; cause by :[" + e + "]");
/*      */             }
/*      */ 
/*  633 */             iRc = -1;
/*      */             try {
/*  635 */               Thread.sleep(1000L);
/*      */             } catch (InterruptedException e1) {
/*  637 */               break label270:
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  642 */       label270: return iRc;
/*      */     }
/*      */ 
/*      */     int checkConnectionGroup()
/*      */     {
/*  652 */       for (int i = 0; i < this.pGroup.number; ++i) {
/*  653 */         HiALSConnector2.ConnectionNode pConnection = this.pGroup.connection(i);
/*  654 */         if (HiALSConnector2.this.log.isDebugEnabled()) {
/*  655 */           HiALSConnector2.this.log.debug("send_fd:" + pConnection.send_fd + "; recv_fd:" + pConnection.recv_fd);
/*      */         }
/*      */ 
/*  658 */         if ((pConnection.send_fd == null) || (pConnection.recv_fd == null)) {
/*  659 */           return -1;
/*      */         }
/*      */ 
/*      */         try
/*      */         {
/*  664 */           pConnection.sendTest(this.bufs, HiALSConnector2.this._ConnInf.pre_len);
/*      */         } catch (IOException e) {
/*  666 */           String s = "发送空闲报文失败！断开连接" + pConnection.send_fd + ";" + e;
/*  667 */           HiALSConnector2.this.log.error(s);
/*      */ 
/*  669 */           HiALSConnector2.hsocketclose(pConnection.send_fd);
/*  670 */           pConnection.send_fd = null;
/*      */ 
/*  672 */           pConnection.pGroup.setStatus(0);
/*      */ 
/*  674 */           return -1;
/*      */         }
/*      */       }
/*      */ 
/*  678 */       this.pGroup.setStatus(1);
/*      */ 
/*  680 */       return 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class ConnectionInformation
/*      */   {
/*      */     String local_ip;
/*      */     int check_itv;
/*      */     int error_check_itv;
/*      */     int max_timeout;
/*      */     int pre_len;
/*      */     int number;
/*      */     List group;
/*      */     Lock m_lock;
/*      */     int last_group;
/*      */ 
/*      */     public ConnectionInformation()
/*      */     {
/*  220 */       this.error_check_itv = 5;
/*      */ 
/*  223 */       this.number = 0;
/*  224 */       this.group = new ArrayList();
/*  225 */       this.m_lock = new ReentrantLock();
/*  226 */       this.last_group = -1; }
/*      */ 
/*      */     public void setLocal_ip(String local_ip) {
/*  229 */       this.local_ip = local_ip;
/*      */     }
/*      */ 
/*      */     public void setCheck_itv(int check_itv) {
/*  233 */       this.check_itv = check_itv;
/*      */     }
/*      */ 
/*      */     public void setMax_timeout(int max_timeout) {
/*  237 */       this.max_timeout = max_timeout;
/*      */     }
/*      */ 
/*      */     public void setError_check_itv(int error_check_itv) {
/*  241 */       this.error_check_itv = error_check_itv;
/*      */     }
/*      */ 
/*      */     public void setPre_len(int pre_len) {
/*  245 */       this.pre_len = pre_len;
/*      */     }
/*      */ 
/*      */     public void addGroup(HiALSConnector2.ConnectionGroup _group) {
/*  249 */       this.group.add(_group);
/*  250 */       this.number += 1;
/*      */     }
/*      */ 
/*      */     public HiALSConnector2.ConnectionGroup group(int index) {
/*  254 */       return ((HiALSConnector2.ConnectionGroup)this.group.get(index));
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class ConnectionGroup
/*      */   {
/*      */     String remote_ip;
/*      */     int number;
/*      */     Future manager_tid;
/*      */     private int is_ok;
/*      */     private Lock m_lock;
/*      */     private int last_connection;
/*      */     private List connection;
/*      */ 
/*      */     public ConnectionGroup()
/*      */     {
/*  157 */       this.number = 0;
/*      */ 
/*  160 */       this.is_ok = 0;
/*  161 */       this.m_lock = new ReentrantLock();
/*  162 */       this.last_connection = -1;
/*  163 */       this.connection = new ArrayList(); }
/*      */ 
/*      */     public void setRemote_ip(String remote_ip) {
/*  166 */       this.remote_ip = remote_ip;
/*      */     }
/*      */ 
/*      */     public void addConnection(HiALSConnector2.ConnectionNode node) {
/*  170 */       node.pGroup = this;
/*  171 */       this.connection.add(node);
/*  172 */       this.number += 1;
/*      */     }
/*      */ 
/*      */     public HiALSConnector2.ConnectionNode connection(int index) {
/*  176 */       return ((HiALSConnector2.ConnectionNode)this.connection.get(index));
/*      */     }
/*      */ 
/*      */     int setStatus(int status) {
/*  180 */       if ((status < 0) || (status > 1)) {
/*  181 */         return -1;
/*      */       }
/*  183 */       this.m_lock.lock();
/*  184 */       this.is_ok = status;
/*  185 */       this.m_lock.unlock();
/*      */ 
/*  187 */       return 0;
/*      */     }
/*      */ 
/*      */     int nextConnection()
/*      */     {
/*  192 */       int iConnectionId = this.last_connection + 1;
/*  193 */       if (iConnectionId >= this.number) {
/*  194 */         iConnectionId = 0;
/*      */       }
/*  196 */       this.last_connection = iConnectionId;
/*  197 */       return iConnectionId;
/*      */     }
/*      */ 
/*      */     HiALSConnector2.ConnectionNode nextConn()
/*      */     {
/*  203 */       int iConnectionId = nextConnection();
/*  204 */       HiALSConnector2.ConnectionNode pConnection = connection(iConnectionId);
/*  205 */       return pConnection;
/*      */     }
/*      */ 
/*      */     int status() {
/*  209 */       return this.is_ok;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class ConnectionNode
/*      */   {
/*      */     HiALSConnector2.ConnectionGroup pGroup;
/*      */     Thread reader_tid;
/*      */     int remote_port;
/*      */     int local_port;
/*      */     ServerSocket listen_fd;
/*      */     Socket send_fd;
/*      */     Socket recv_fd;
/*      */     private Lock m_lock;
/*      */ 
/*      */     public ConnectionNode()
/*      */     {
/*   69 */       this.m_lock = new ReentrantLock(); }
/*      */ 
/*      */     public void setRemote_port(int remote_port) {
/*   72 */       this.remote_port = remote_port;
/*      */     }
/*      */ 
/*      */     public void setLocal_port(int local_port) {
/*   76 */       this.local_port = local_port;
/*      */     }
/*      */ 
/*      */     void closeConnection() {
/*   80 */       this.m_lock.lock();
/*      */       try
/*      */       {
/*   83 */         if (this.listen_fd != null) {
/*   84 */           HiALSConnector2.hsocketclose(this.listen_fd);
/*   85 */           this.listen_fd = null;
/*      */         }
/*      */ 
/*   88 */         if (this.send_fd != null) {
/*   89 */           HiALSConnector2.hsocketclose(this.send_fd);
/*   90 */           this.send_fd = null;
/*      */         }
/*   92 */         if (this.recv_fd != null) {
/*   93 */           HiALSConnector2.hsocketclose(this.recv_fd);
/*   94 */           this.recv_fd = null;
/*      */         }
/*      */       }
/*      */       finally {
/*   98 */         this.m_lock.unlock();
/*      */       }
/*      */     }
/*      */ 
/*      */     private int send(byte[] buf, int pre_len) throws IOException {
/*  103 */       OutputStream out = this.send_fd.getOutputStream();
/*  104 */       out.write(buf);
/*  105 */       out.flush();
/*  106 */       return pre_len;
/*      */     }
/*      */ 
/*      */     void sendData(byte[] data, int prelen)
/*      */       throws IOException
/*      */     {
/*  112 */       String prestr = String.valueOf(data.length);
/*  113 */       prestr = StringUtils.leftPad(prestr, prelen, "0");
/*  114 */       this.m_lock.lock();
/*      */       try {
/*  116 */         int iLen = send(prestr.getBytes(), prelen);
/*  117 */         iLen = send(data, data.length);
/*      */       } finally {
/*  119 */         this.m_lock.unlock();
/*      */       }
/*      */     }
/*      */ 
/*      */     int sendTest(byte[] buf, int prelen) throws IOException
/*      */     {
/*  125 */       this.m_lock.lock();
/*      */       try {
/*  127 */         int i = send(buf, prelen);
/*      */ 
/*  130 */         return i;
/*      */       }
/*      */       finally
/*      */       {
/*  129 */         this.m_lock.unlock();
/*      */       }
/*      */     }
/*      */ 
/*      */     void connect() throws Exception
/*      */     {
/*  135 */       this.m_lock.lock();
/*      */       try
/*      */       {
/*  139 */         Socket s = new Socket();
/*  140 */         s.connect(new InetSocketAddress(this.pGroup.remote_ip, this.remote_port), 2000);
/*      */ 
/*  142 */         this.send_fd = s;
/*  143 */         this.send_fd.setTcpNoDelay(true);
/*      */       } finally {
/*  145 */         this.m_lock.unlock();
/*      */       }
/*      */     }
/*      */   }
/*      */ }