 package com.hisun.protocol.tcp.imp;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.HiDefaultServer;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.framework.imp.HiAbstractListener;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.parse.HiVariableExpander;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiResource;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiThreadPool;
 import edu.emory.mathcs.backport.java.util.concurrent.Executors;
 import edu.emory.mathcs.backport.java.util.concurrent.Future;
 import edu.emory.mathcs.backport.java.util.concurrent.RejectedExecutionException;
 import edu.emory.mathcs.backport.java.util.concurrent.ScheduledExecutorService;
 import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
 import edu.emory.mathcs.backport.java.util.concurrent.locks.Lock;
 import edu.emory.mathcs.backport.java.util.concurrent.locks.ReentrantLock;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InterruptedIOException;
 import java.io.OutputStream;
 import java.net.InetAddress;
 import java.net.InetSocketAddress;
 import java.net.ServerSocket;
 import java.net.Socket;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;
 import javax.net.ServerSocketFactory;
 import org.apache.commons.digester.Digester;
 import org.apache.commons.digester.substitution.VariableSubstitutor;
 import org.apache.commons.lang.StringUtils;
 import org.xml.sax.ErrorHandler;
 import org.xml.sax.SAXException;
 import org.xml.sax.SAXParseException;
 
 public class HiALSConnector2 extends HiAbstractListener
   implements IHandler
 {
   static final HiConnXMLParser parser = new HiConnXMLParser();
   ConnectionInformation _ConnInf;
   ScheduledExecutorService scheduler;
   HiThreadPool tp;
   String cfgfile;
   protected int maxThreads;
   protected int minThreads;
   protected int queueSize;
   protected int timeOut;
   static Class class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionInformation;
   static Class class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionGroup;
   static Class class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionNode;
 
   public HiALSConnector2()
   {
     this.maxThreads = 50; this.minThreads = 5; this.queueSize = -1;
     this.timeOut = 2;
   }
 
   public void setCfgfile(String cfgfile)
   {
     this.cfgfile = cfgfile;
   }
 
   void initChannel(HiConnXMLParser parser, URL url)
     throws Exception
   {
     this._ConnInf = ((ConnectionInformation)parser.parse(url));
   }
 
   void closeChannel()
   {
     ConnectionGroup pGroup;
     this.log.info("close channel...");
 
     for (int i = 0; i < this._ConnInf.number; ++i) {
       pGroup = this._ConnInf.group(i);
       pGroup.manager_tid.cancel(false);
     }
     this.scheduler.shutdownNow();
     this.log.info("shutdown manage thread...");
     try {
       this.scheduler.awaitTermination(5L, TimeUnit.SECONDS);
     } catch (InterruptedException e) {
       this.log.error("can not stop manager thread!");
     }
     this.log.info("shutdown manage thread ok...");
     this._ConnInf.m_lock.lock();
     try {
       this.log.info("close connection...");
       for (i = 0; i < this._ConnInf.number; ++i) {
         pGroup = this._ConnInf.group(i);
         this.log.info("close group:" + pGroup.remote_ip);
 
         for (int j = 0; j < pGroup.number; ++j) {
           ConnectionNode pConnection = pGroup.connection(j);
           this.log.info("close listener:" + pConnection.local_port);
 
           pConnection.closeConnection();
           this.log.info("quit thread...");
           if (pConnection.reader_tid != null) {
             thdCancel(pConnection.reader_tid);
 
             pConnection.reader_tid = null;
           }
 
         }
 
       }
 
       if (this.log.isInfoEnabled())
         this.log.info("close channle success!");
     }
     finally {
       this._ConnInf.m_lock.unlock();
     }
   }
 
   private void thdCancel(Thread thread)
   {
     thread.interrupt();
     try {
       thread.join();
     }
     catch (InterruptedException e)
     {
     }
   }
 
   void sendMessage(HiMessage msg)
     throws HiException
   {
     ConnectionNode pConnection = findConnection();
 
     if (pConnection == null) {
       throw new HiException("231204", "请求编号=[" + msg.getRequestId() + "] 没有可发送的通道!");
     }
 
     send_start(msg, pConnection);
   }
 
   private ConnectionNode findConnection()
   {
     ConnectionNode pConnection = null;
 
     this._ConnInf.m_lock.lock();
     try {
       int iGroupId = this._ConnInf.last_group + 1;
       if (iGroupId >= this._ConnInf.number) {
         iGroupId = 0;
       }
 
       ConnectionGroup pGroup = this._ConnInf.group(iGroupId);
 
       if (pGroup.status() == 1) {
         pConnection = pGroup.nextConn();
 
         if (this.log.isDebugEnabled()) {
           this.log.debug("find connection:" + pConnection.local_port + "," + pConnection.remote_port);
         }
 
       }
       else
       {
         if (this.log.isDebugEnabled()) {
           this.log.debug("group :" + pGroup.remote_ip + " busy, find another group");
         }
 
         iGroupId = -1;
         for (int i = 0; i < this._ConnInf.number; ++i) {
           if (i == iGroupId) {
             continue;
           }
           pGroup = this._ConnInf.group(i);
           if (pGroup.status() == 1) {
             iGroupId = i;
             pConnection = pGroup.nextConn();
             if (!(this.log.isDebugEnabled())) break;
             this.log.debug("find connection:" + pConnection.local_port + "," + pConnection.remote_port);
 
             break;
           }
         }
       }
 
       this._ConnInf.last_group = iGroupId;
     }
     finally {
       this._ConnInf.m_lock.unlock();
     }
 
     return pConnection;
   }
 
   private void send_start(HiMessage msg, ConnectionNode pConnection) throws HiException
   {
     if (this.log.isDebugEnabled()) {
       this.log.debug("选择发送通道:" + pConnection.send_fd);
     }
     HiByteBuffer buf = (HiByteBuffer)msg.getBody();
     byte[] data = buf.getBytes();
 
     int iSendLen = this._ConnInf.pre_len;
     try
     {
       pConnection.sendData(data, iSendLen);
     }
     catch (IOException e)
     {
       hsocketclose(pConnection.send_fd);
       pConnection.send_fd = null;
 
       pConnection.pGroup.setStatus(0);
       throw new HiException("231207", e.toString(), e);
     }
 
     if (this.log.isInfoEnabled()) {
       Socket socket = pConnection.send_fd;
       HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
       String ip = socket.getInetAddress().getHostAddress();
       this.log.info(sm.getString("tcplistener.send", msg.getRequestId(), ip, String.valueOf(byteBuffer.length()), byteBuffer.toString()));
     }
   }
 
   static void hsocketclose(Socket s)
   {
     if (s == null)
       return;
     try {
       s.close();
     } catch (IOException e) {
     }
   }
 
   static void hsocketclose(ServerSocket s) {
     if (s == null)
       return;
     try {
       s.close();
     }
     catch (IOException e)
     {
     }
   }
 
   public void serverInit(ServerEvent event)
     throws HiException
   {
     if (this.queueSize == -1)
       this.tp = HiThreadPool.createThreadPool(this.minThreads, this.maxThreads);
     else {
       this.tp = HiThreadPool.createThreadPool(this.minThreads, this.maxThreads, this.queueSize);
     }
 
     URL url = HiResource.getResource(this.cfgfile);
     try {
       initChannel(parser, url);
     } catch (Exception e) {
       throw new HiException("231252", this.cfgfile, e);
     }
   }
 
   public void serverStart(ServerEvent event) throws HiException {
     this.scheduler = Executors.newScheduledThreadPool(this._ConnInf.number);
 
     for (int i = 0; i < this._ConnInf.number; ++i) {
       ConnectionGroup pGroup = this._ConnInf.group(i);
       Runnable manager = new GroupManager(i);
 
       pGroup.manager_tid = this.scheduler.scheduleWithFixedDelay(manager, 1L, this._ConnInf.error_check_itv, TimeUnit.SECONDS);
     }
   }
 
   public void serverStop(ServerEvent event) throws HiException
   {
     closeChannel();
     if (this.log.isDebugEnabled())
       this.log.debug("server stop ok!");
   }
 
   public void serverDestroy(ServerEvent event)
     throws HiException
   {
   }
 
   public void serverPause(ServerEvent event)
   {
   }
 
   public void serverResume(ServerEvent event)
   {
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
 
   public int getMaxThreads()
   {
     return this.maxThreads;
   }
 
   public int getMinThreads()
   {
     return this.minThreads;
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     sendMessage(ctx.getCurrentMsg());
   }
 
   public int getQueueSize()
   {
     return this.queueSize;
   }
 
   public void setQueueSize(int queueSize) {
     this.queueSize = queueSize;
   }
 
   public int getTimeOut() {
     return this.timeOut;
   }
 
   public void setTimeOut(int timeOut) {
     this.timeOut = timeOut;
   }
 
   static Class class$(String x0)
   {
     try
     {
       return Class.forName(x0); } catch (ClassNotFoundException x1) { throw new NoClassDefFoundError().initCause(x1);
     }
   }
 
   static class HiConnXMLParser
     implements ErrorHandler
   {
     private final Digester parser;
 
     public HiConnXMLParser()
     {
       this.parser = new Digester();
       this.parser.addObjectCreate("Config", (HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionInformation == null) ? (HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionInformation = HiALSConnector2.class$("com.hisun.protocol.tcp.imp.HiALSConnector2$ConnectionInformation")) : HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionInformation);
       this.parser.addBeanPropertySetter("Config/LocalIp", "local_ip");
       this.parser.addBeanPropertySetter("Config/ErrorCheckInterval", "error_check_itv");
 
       this.parser.addBeanPropertySetter("Config/CheckInterval", "check_itv");
       this.parser.addBeanPropertySetter("Config/MaxTimeOut", "max_timeout");
       this.parser.addBeanPropertySetter("Config/PreLen", "pre_len");
 
       this.parser.addObjectCreate("Config/Group", (HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionGroup == null) ? (HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionGroup = HiALSConnector2.class$("com.hisun.protocol.tcp.imp.HiALSConnector2$ConnectionGroup")) : HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionGroup);
       this.parser.addBeanPropertySetter("Config/Group/RemoteIp", "remote_ip");
       this.parser.addSetNext("Config/Group", "addGroup");
 
       this.parser.addObjectCreate("Config/Group/Connection", (HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionNode == null) ? (HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionNode = HiALSConnector2.class$("com.hisun.protocol.tcp.imp.HiALSConnector2$ConnectionNode")) : HiALSConnector2.class$com$hisun$protocol$tcp$imp$HiALSConnector2$ConnectionNode);
 
       this.parser.addBeanPropertySetter("Config/Group/Connection/remote_port", "remote_port");
 
       this.parser.addBeanPropertySetter("Config/Group/Connection/local_port", "local_port");
 
       this.parser.addSetNext("Config/Group/Connection", "addConnection");
 
       this.parser.setErrorHandler(this);
 
       this.parser.setSubstitutor(new VariableSubstitutor(new HiVariableExpander(), null));
     }
 
     public void error(SAXParseException arg0) throws SAXException
     {
       throw arg0;
     }
 
     public void fatalError(SAXParseException arg0)
       throws SAXException
     {
       throw arg0;
     }
 
     public Object parse(URL url) throws Exception {
       this.parser.clear();
       this.parser.setClassLoader(Thread.currentThread().getContextClassLoader());
 
       return this.parser.parse(url.openStream());
     }
 
     public void warning(SAXParseException arg0) throws SAXException
     {
       throw arg0;
     }
   }
 
   class ConnectionReader
     implements Runnable
   {
     HiALSConnector2.ConnectionNode pConnection;
 
     public ConnectionReader(HiALSConnector2.ConnectionNode paramConnectionNode)
     {
       this.pConnection = paramConnectionNode;
     }
 
     public void run() {
       try {
         run2();
       } catch (Throwable e) {
         reader_end();
         HiALSConnector2.this.log.error("ConnectionReader:[" + e + "]", e);
       }
     }
 
     public void run2()
     {
       int iLen = 0;
       int iRecvLen = 0;
 
       if (HiALSConnector2.this.log.isDebugEnabled()) {
         HiALSConnector2.this.log.debug("开始建立监听：" + this.pConnection.local_port);
       }
       try
       {
         this.pConnection.listen_fd = my_listen(HiALSConnector2.this._ConnInf.local_ip, this.pConnection.local_port);
       }
       catch (IOException e1)
       {
         s = "绑定本地监听端口[" + HiALSConnector2.this._ConnInf.local_ip + "][" + this.pConnection.local_port + "]失败!";
 
         HiALSConnector2.this.log.error(s);
         reader_end();
         return;
       }
 
       if (HiALSConnector2.this.log.isDebugEnabled()) {
         HiALSConnector2.this.log.debug("等待连接...");
       }
       try
       {
         this.pConnection.recv_fd = this.pConnection.listen_fd.accept();
         this.pConnection.recv_fd.setTcpNoDelay(true);
       } catch (IOException e) {
         HiALSConnector2.this.log.error(this.pConnection.local_port + " 等待接受对方连接失败!" + e);
         reader_end();
         return;
       }
 
       String aClientIp = this.pConnection.recv_fd.getInetAddress().getHostAddress();
 
       int iRc = checkConnectionIp(aClientIp);
       if (iRc < 0) {
         HiALSConnector2.this.log.error("非法IP连接:" + aClientIp);
         reader_end();
         return;
       }
 
       String s = "接收连接请求：IP=" + aClientIp + ",本地端口=" + this.pConnection.local_port;
 
       HiALSConnector2.this.log.info(s);
 
       byte[] aPreLen = new byte[HiALSConnector2.this._ConnInf.pre_len];
       byte[] aData = new byte[4096];
       while (!(Thread.interrupted()))
       {
         String msg;
         Arrays.fill(aPreLen, 0);
         try {
           iLen = socketRead(this.pConnection.recv_fd, aPreLen, HiALSConnector2.this._ConnInf.pre_len, HiALSConnector2.this._ConnInf.max_timeout);
         }
         catch (InterruptedIOException ie)
         {
           msg = "接收前置长度超时,断开连接，超时时间(秒)：" + HiALSConnector2.this._ConnInf.max_timeout;
 
           HiALSConnector2.this.log.error(msg);
           break label932:
         } catch (IOException e) {
         }
         break;
 
         if (iLen != HiALSConnector2.this._ConnInf.pre_len) {
           msg = "接收前置长度失败!应接收长度[" + HiALSConnector2.this._ConnInf.pre_len + "]已接收长度[" + iLen + "]";
 
           HiALSConnector2.this.log.error(msg);
           HiALSConnector2.this.log.error("对方断开连接！");
           break;
         }
         iRecvLen = Integer.parseInt(new String(aPreLen));
         if (iRecvLen == 0)
         {
           if (HiALSConnector2.this.log.isDebugEnabled());
           HiALSConnector2.this.log.debug("接收到空闲检查报文" + this.pConnection.recv_fd);
         }
 
         Arrays.fill(aData, 0);
         try
         {
           iLen = socketRead(this.pConnection.recv_fd, aData, iRecvLen, HiALSConnector2.this.timeOut);
         }
         catch (InterruptedIOException msg)
         {
           msg = "接收数据超时,断开连接，超时时间(秒)：" + HiALSConnector2.this.timeOut;
           HiALSConnector2.this.log.error(msg);
           break label932:
         } catch (IOException msg) {
         }
         break;
 
         if (iLen != iRecvLen)
         {
           msg = "接收数据失败!应接收长度[" + iRecvLen + "]已接收长度[" + iLen + "]";
 
           HiALSConnector2.this.log.error(msg);
           HiALSConnector2.this.log.error("对方断开连接！");
           break;
         }
         HiMessage pMsg = usfCreateMsg();
 
         HiByteBuffer buf = new HiByteBuffer(iRecvLen);
         buf.append(aData, 0, iRecvLen);
         pMsg.setBody(buf);
 
         if (HiALSConnector2.this.log.isInfoEnabled())
         {
           HiByteBuffer byteBuffer = (HiByteBuffer)pMsg.getBody();
 
           HiALSConnector2.this.log.info(HiAbstractListener.sm.getString("tcplistener.receive", pMsg.getRequestId(), aClientIp, String.valueOf(byteBuffer.length()), byteBuffer.toString()));
         }
 
         pMsg.setHeadItem("SIP", aClientIp);
 
         long curtime = System.currentTimeMillis();
         pMsg.setHeadItem("STM", new Long(curtime));
 
         if (usfSendRequest(pMsg) < 0)
         {
           HiALSConnector2.this.log.error("发送消息失败!");
           break;
         }
       }
 
       label932: reader_end();
     }
 
     private void reader_end()
     {
       HiALSConnector2.hsocketclose(this.pConnection.listen_fd);
       this.pConnection.listen_fd = null;
       HiALSConnector2.hsocketclose(this.pConnection.recv_fd);
       this.pConnection.recv_fd = null;
 
       this.pConnection.pGroup.setStatus(0);
 
       this.pConnection.reader_tid = null;
 
       if (HiALSConnector2.this.log.isDebugEnabled())
         HiALSConnector2.this.log.debug("接收线程退出,remote port:" + this.pConnection.remote_port);
     }
 
     ServerSocket my_listen(String local_ip, int port)
       throws IOException
     {
       ServerSocket sockfd = null;
 
       ServerSocketFactory factory = ServerSocketFactory.getDefault();
       sockfd = factory.createServerSocket(port);
 
       return sockfd;
     }
 
     public int socketRead(Socket socket, byte[] preLen, int pre_len, int max_timeout) throws IOException
     {
       if (max_timeout >= 0) {
         socket.setSoTimeout(max_timeout * 1000);
       }
       int nread = 0;
 
       InputStream in = socket.getInputStream();
       while (nread != pre_len) {
         int n = in.read(preLen, nread, pre_len - nread);
         if (n == -1)
           break;
         nread += n;
       }
       return nread;
     }
 
     public HiMessage usfCreateMsg() {
       HiMessage msg = new HiMessage(HiALSConnector2.this.getServer().getName(), HiALSConnector2.this.getMsgType());
 
       msg.setHeadItem("ECT", "text/plain");
       msg.setHeadItem("SCH", "rp");
 
       return msg;
     }
 
     public int usfSendRequest(HiMessage msg) {
       Runnable worker = new Runnable(msg) {
         private final HiMessage val$msg;
 
         public void run() { try { HiALSConnector2.this.getServer().process(HiALSConnector2.this.getMessageContext(this.val$msg));
           } catch (Throwable e) {
             HiALSConnector2.this.log.error(this.val$msg.getRequestId() + ":" + e, e);
           } finally {
             HiLog.close(this.val$msg);
           }
 
         }
 
       };
       while (true)
       {
         try
         {
           HiALSConnector2.this.tp.execute(worker);
         }
         catch (RejectedExecutionException e) {
           HiALSConnector2.this.log.warn("Please increase maxThreads!");
           Thread.currentThread(); Thread.yield();
           try {
             Thread.currentThread(); Thread.sleep(1000L);
           } catch (InterruptedException e1) {
             break label90:
           }
           if (!(HiALSConnector2.this.tp.isShutdown())) if (!(Thread.currentThread().isInterrupted())) break label87;
         }
         label87: break;
       }
 
       label90: return 0;
     }
 
     int checkConnectionIp(String ip)
     {
       for (int i = 0; i < HiALSConnector2.this._ConnInf.number; ++i) {
         if (HiALSConnector2.this._ConnInf.group(i).remote_ip.equals(ip)) {
           return 0;
         }
       }
       return -1;
     }
   }
 
   class GroupManager
     implements Runnable
   {
     private final int group_id;
     private final HiALSConnector2.ConnectionGroup pGroup;
     private final byte[] bufs;
     int i = 0;
 
     public GroupManager(int paramInt) {
       this.group_id = paramInt;
       this.pGroup = HiALSConnector2.this._ConnInf.group(paramInt);
       this.bufs = new byte[HiALSConnector2.this._ConnInf.pre_len];
       Arrays.fill(this.bufs, 48);
     }
 
     public void run() {
       try {
         run2();
       } catch (Throwable t) {
         HiALSConnector2.this.log.fatal("组管理线程异常退出!", t);
       }
     }
 
     public void run2()
     {
       if ((this.pGroup.status() == 0) || (this.i >= HiALSConnector2.this._ConnInf.check_itv)) {
         if (HiALSConnector2.this.log.isDebugEnabled()) {
           HiALSConnector2.this.log.debug("group manage thread runing, group=" + this.group_id + ",time=" + this.i);
         }
 
         int iRc = checkConnectionGroup();
         if (iRc < 0) {
           deleteConnectionGroup();
           iRc = createConnectionGroup();
         }
         if (HiALSConnector2.this.log.isDebugEnabled()) {
           HiALSConnector2.this.log.debug("run end" + this.i + "," + HiALSConnector2.this.getName());
         }
         if (iRc == 0) {
           this.i = 0;
         }
       }
       this.i += HiALSConnector2.this._ConnInf.error_check_itv;
     }
 
     int createConnectionGroup()
     {
       int iRc = 0;
 
       for (int i = 0; i < this.pGroup.number; ++i) {
         HiALSConnector2.ConnectionNode pConnection = this.pGroup.connection(i);
         if (HiALSConnector2.this.log.isDebugEnabled()) {
           HiALSConnector2.this.log.debug("create connection: " + pConnection.local_port + "," + pConnection.remote_port);
         }
 
         Runnable reader = new HiALSConnector2.ConnectionReader(HiALSConnector2.this, pConnection);
         pConnection.reader_tid = new Thread(reader, "ConnReader(" + pConnection.local_port + "," + pConnection.remote_port + ")");
 
         pConnection.reader_tid.start();
 
         iRc = createSendChannel(i);
         if (iRc < 0) {
           this.pGroup.setStatus(0);
           break;
         }
 
         if (HiALSConnector2.this.log.isDebugEnabled()) {
           HiALSConnector2.this.log.debug("success create connection: " + pConnection.local_port + "," + pConnection.remote_port);
         }
 
       }
 
       if (HiALSConnector2.this.log.isDebugEnabled()) {
         HiALSConnector2.this.log.debug("ret from createConnectionGroup: " + iRc);
       }
 
       if (iRc == 0)
         this.pGroup.setStatus(1);
       return iRc;
     }
 
     int deleteConnectionGroup()
     {
       for (int i = 0; i < this.pGroup.number; ++i) {
         HiALSConnector2.ConnectionNode pConnection = this.pGroup.connection(i);
         if (HiALSConnector2.this.log.isDebugEnabled()) {
           HiALSConnector2.this.log.debug("delete connection: " + pConnection.local_port + "," + pConnection.remote_port);
         }
 
         pConnection.closeConnection();
 
         if (HiALSConnector2.this.log.isDebugEnabled()) {
           HiALSConnector2.this.log.debug("kill reader thread:" + pConnection.reader_tid);
         }
         if (pConnection.reader_tid != null) {
           HiALSConnector2.this.thdCancel(pConnection.reader_tid);
           pConnection.reader_tid = null;
         }
 
         if (HiALSConnector2.this.log.isDebugEnabled()) {
           HiALSConnector2.this.log.debug("success delete connection: " + pConnection.local_port + "," + pConnection.remote_port);
         }
 
       }
 
       return 0;
     }
 
     int createSendChannel(int connection_id)
     {
       int iRc = 0;
 
       HiALSConnector2.ConnectionNode pConnection = this.pGroup.connection(connection_id);
       if (HiALSConnector2.this.log.isDebugEnabled()) {
         HiALSConnector2.this.log.debug("开始连接对方系统:IP=[" + this.pGroup.remote_ip + "] Port=[" + pConnection.remote_port + "]");
       }
 
       for (int i = 0; i < HiALSConnector2.this._ConnInf.error_check_itv; ) {
         if (Thread.currentThread().isInterrupted())
           break;
         try
         {
           pConnection.connect();
 
           String s = "连接对方系统成功!IP=[" + this.pGroup.remote_ip + "] Port=[" + pConnection.remote_port + "]";
 
           HiALSConnector2.this.log.info(s);
           iRc = 0;
         }
         catch (Exception e)
         {
           for (; ; ++i)
           {
             if (HiALSConnector2.this.log.isDebugEnabled()) {
               HiALSConnector2.this.log.debug("连接对方系统失败!IP=[" + this.pGroup.remote_ip + "] Port=[" + pConnection.remote_port + "]; cause by :[" + e + "]");
             }
 
             iRc = -1;
             try {
               Thread.sleep(1000L);
             } catch (InterruptedException e1) {
               break label270:
             }
           }
         }
       }
       label270: return iRc;
     }
 
     int checkConnectionGroup()
     {
       for (int i = 0; i < this.pGroup.number; ++i) {
         HiALSConnector2.ConnectionNode pConnection = this.pGroup.connection(i);
         if (HiALSConnector2.this.log.isDebugEnabled()) {
           HiALSConnector2.this.log.debug("send_fd:" + pConnection.send_fd + "; recv_fd:" + pConnection.recv_fd);
         }
 
         if ((pConnection.send_fd == null) || (pConnection.recv_fd == null)) {
           return -1;
         }
 
         try
         {
           pConnection.sendTest(this.bufs, HiALSConnector2.this._ConnInf.pre_len);
         } catch (IOException e) {
           String s = "发送空闲报文失败！断开连接" + pConnection.send_fd + ";" + e;
           HiALSConnector2.this.log.error(s);
 
           HiALSConnector2.hsocketclose(pConnection.send_fd);
           pConnection.send_fd = null;
 
           pConnection.pGroup.setStatus(0);
 
           return -1;
         }
       }
 
       this.pGroup.setStatus(1);
 
       return 0;
     }
   }
 
   public static class ConnectionInformation
   {
     String local_ip;
     int check_itv;
     int error_check_itv;
     int max_timeout;
     int pre_len;
     int number;
     List group;
     Lock m_lock;
     int last_group;
 
     public ConnectionInformation()
     {
       this.error_check_itv = 5;
 
       this.number = 0;
       this.group = new ArrayList();
       this.m_lock = new ReentrantLock();
       this.last_group = -1; }
 
     public void setLocal_ip(String local_ip) {
       this.local_ip = local_ip;
     }
 
     public void setCheck_itv(int check_itv) {
       this.check_itv = check_itv;
     }
 
     public void setMax_timeout(int max_timeout) {
       this.max_timeout = max_timeout;
     }
 
     public void setError_check_itv(int error_check_itv) {
       this.error_check_itv = error_check_itv;
     }
 
     public void setPre_len(int pre_len) {
       this.pre_len = pre_len;
     }
 
     public void addGroup(HiALSConnector2.ConnectionGroup _group) {
       this.group.add(_group);
       this.number += 1;
     }
 
     public HiALSConnector2.ConnectionGroup group(int index) {
       return ((HiALSConnector2.ConnectionGroup)this.group.get(index));
     }
   }
 
   public static class ConnectionGroup
   {
     String remote_ip;
     int number;
     Future manager_tid;
     private int is_ok;
     private Lock m_lock;
     private int last_connection;
     private List connection;
 
     public ConnectionGroup()
     {
       this.number = 0;
 
       this.is_ok = 0;
       this.m_lock = new ReentrantLock();
       this.last_connection = -1;
       this.connection = new ArrayList(); }
 
     public void setRemote_ip(String remote_ip) {
       this.remote_ip = remote_ip;
     }
 
     public void addConnection(HiALSConnector2.ConnectionNode node) {
       node.pGroup = this;
       this.connection.add(node);
       this.number += 1;
     }
 
     public HiALSConnector2.ConnectionNode connection(int index) {
       return ((HiALSConnector2.ConnectionNode)this.connection.get(index));
     }
 
     int setStatus(int status) {
       if ((status < 0) || (status > 1)) {
         return -1;
       }
       this.m_lock.lock();
       this.is_ok = status;
       this.m_lock.unlock();
 
       return 0;
     }
 
     int nextConnection()
     {
       int iConnectionId = this.last_connection + 1;
       if (iConnectionId >= this.number) {
         iConnectionId = 0;
       }
       this.last_connection = iConnectionId;
       return iConnectionId;
     }
 
     HiALSConnector2.ConnectionNode nextConn()
     {
       int iConnectionId = nextConnection();
       HiALSConnector2.ConnectionNode pConnection = connection(iConnectionId);
       return pConnection;
     }
 
     int status() {
       return this.is_ok;
     }
   }
 
   public static class ConnectionNode
   {
     HiALSConnector2.ConnectionGroup pGroup;
     Thread reader_tid;
     int remote_port;
     int local_port;
     ServerSocket listen_fd;
     Socket send_fd;
     Socket recv_fd;
     private Lock m_lock;
 
     public ConnectionNode()
     {
       this.m_lock = new ReentrantLock(); }
 
     public void setRemote_port(int remote_port) {
       this.remote_port = remote_port;
     }
 
     public void setLocal_port(int local_port) {
       this.local_port = local_port;
     }
 
     void closeConnection() {
       this.m_lock.lock();
       try
       {
         if (this.listen_fd != null) {
           HiALSConnector2.hsocketclose(this.listen_fd);
           this.listen_fd = null;
         }
 
         if (this.send_fd != null) {
           HiALSConnector2.hsocketclose(this.send_fd);
           this.send_fd = null;
         }
         if (this.recv_fd != null) {
           HiALSConnector2.hsocketclose(this.recv_fd);
           this.recv_fd = null;
         }
       }
       finally {
         this.m_lock.unlock();
       }
     }
 
     private int send(byte[] buf, int pre_len) throws IOException {
       OutputStream out = this.send_fd.getOutputStream();
       out.write(buf);
       out.flush();
       return pre_len;
     }
 
     void sendData(byte[] data, int prelen)
       throws IOException
     {
       String prestr = String.valueOf(data.length);
       prestr = StringUtils.leftPad(prestr, prelen, "0");
       this.m_lock.lock();
       try {
         int iLen = send(prestr.getBytes(), prelen);
         iLen = send(data, data.length);
       } finally {
         this.m_lock.unlock();
       }
     }
 
     int sendTest(byte[] buf, int prelen) throws IOException
     {
       this.m_lock.lock();
       try {
         int i = send(buf, prelen);
 
         return i;
       }
       finally
       {
         this.m_lock.unlock();
       }
     }
 
     void connect() throws Exception
     {
       this.m_lock.lock();
       try
       {
         Socket s = new Socket();
         s.connect(new InetSocketAddress(this.pGroup.remote_ip, this.remote_port), 2000);
 
         this.send_fd = s;
         this.send_fd.setTcpNoDelay(true);
       } finally {
         this.m_lock.unlock();
       }
     }
   }
 }