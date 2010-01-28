 package com.hisun.protocol.tcp.imp;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.HiMessageInOut;
 import com.hisun.protocol.tcp.HiSSLHandler;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiStringManager;
 import java.io.IOException;
 import java.net.InetSocketAddress;
 import java.net.Socket;
 import java.net.UnknownHostException;
 import javax.net.SocketFactory;
 
 public class HiTcpSynConnector01
   implements IHandler, IServerInitListener
 {
   protected static HiStringManager g_sm = HiStringManager.getManager();
   protected int _port;
   protected String _host;
   protected int _preLen;
   protected Logger _log;
   protected String _preLenType;
   protected int _timeOut;
   protected HiSSLHandler sslHandler;
   protected final HiMessageInOut msginout;
 
   public HiTcpSynConnector01()
   {
     this._log = null;
     this._preLenType = null;
     this._timeOut = 30;
     this.sslHandler = HiSSLHandler.getInstance();
 
     this.msginout = new HiMessageInOut(); }
 
   public void setPort(int port) {
     this._port = port;
   }
 
   public void setHost(String host) {
     this._host = host;
   }
 
   public void setPreLen(int preLen) {
     this._preLen = preLen;
     this.msginout.setPreLen(this._preLen);
   }
 
   public void setPreLenType(String type) {
     this._preLenType = type;
     this.msginout.setPreLenType(type);
   }
 
   public void setTimeOut(int timeOut) {
     this._timeOut = timeOut;
   }
 
   public void setLog(Logger log)
   {
     this._log = log;
   }
 
   public void setSslMode(int sslMode)
   {
     this.sslHandler.setSslMode(sslMode);
   }
 
   public void setIdentityKS(String identityKS)
   {
     this.sslHandler.setIdentityKS(identityKS);
   }
 
   public void setTrustKS(String trustKS)
   {
     this.sslHandler.setTrustKS(trustKS);
   }
 
   public void setKeyPsw(String keyPsw)
   {
     this.sslHandler.setKeyPsw(keyPsw);
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     try {
       HiMessage msg = ctx.getCurrentMsg();
       String ip = msg.getHeadItem("OIP");
       String port = msg.getHeadItem("OPT");
       if (ip == null) {
         sendReceive(msg);
       } else {
         int nport = Integer.parseInt(port);
         sendReceive(msg, ip, nport);
       }
     } catch (HiException e) {
       this._log.error(e);
       throw e;
     }
   }
 
   public HiMessage sendReceive(HiMessage msg) throws HiException {
     return sendReceive(msg, this._host, this._port);
   }
 
   public HiMessage sendReceive(HiMessage msg, String ip, int port) throws HiException
   {
     Socket socket = null;
     try {
       int rdlen;
       try {
         if (!(this.sslHandler.isSSLMode())) {
           socket = createConnection(ip, port);
         }
         else
           socket = createSSLConnection(ip, port);
       }
       catch (IOException e) {
         throw new HiException("231204", "connector error", e);
       }
 
       if (this._log.isInfoEnabled())
       {
         HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
         this._log.info(g_sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
       }
 
       HiByteBuffer tmp1 = (HiByteBuffer)msg.getBody();
       HiByteBuffer tmp2 = new HiByteBuffer(1024);
       tmp2.append(49);
       tmp2.append(tmp1.getBytes());
       msg.setBody(tmp2);
       try
       {
         this.msginout.write(socket.getOutputStream(), msg);
       } catch (IOException e) {
         closeConnection(socket);
         throw new HiException("231207", "connector send error", e);
       }
 
       try
       {
         rdlen = this.msginout.read(socket.getInputStream(), msg);
       } catch (IOException rdlen) {
         closeConnection(socket);
         throw new HiException("231205", "connector receive error", e);
       }
 
       if (this._log.isInfoEnabled())
       {
         HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
         this._log.info(g_sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
       }
 
       msg.setHeadItem("SCH", "rp");
     }
     finally
     {
       closeConnection(socket);
     }
     if (this._log.isDebugEnabled())
       this._log.debug("[connector] return");
     return msg;
   }
 
   private Socket createConnection(String host, int port) throws UnknownHostException, IOException {
     Socket socket = new Socket();
     if (this._timeOut > 0)
       socket.connect(new InetSocketAddress(host, port), this._timeOut * 1000);
     else
       socket.connect(new InetSocketAddress(host, port));
     if (this._timeOut > 0)
       socket.setSoTimeout(this._timeOut * 1000);
     socket.setTcpNoDelay(true);
     return socket;
   }
 
   private Socket createSSLConnection(String host, int port) throws UnknownHostException, IOException, HiException {
     this.sslHandler.init();
     SocketFactory socketFactory = this.sslHandler.getSocketFactory();
 
     Socket socket = socketFactory.createSocket();
     if (this._timeOut > 0)
       socket.connect(new InetSocketAddress(host, port), this._timeOut * 1000);
     else {
       socket.connect(new InetSocketAddress(host, port));
     }
     if (this._timeOut > 0) {
       socket.setSoTimeout(this._timeOut * 1000);
     }
     return socket;
   }
 
   private void closeConnection(Socket socket) {
     if (socket == null)
       return;
     try {
       socket.close();
     } catch (IOException e) {
     }
   }
 
   public void serverInit(ServerEvent arg0) throws HiException {
     this._log = arg0.getLog();
   }
 }