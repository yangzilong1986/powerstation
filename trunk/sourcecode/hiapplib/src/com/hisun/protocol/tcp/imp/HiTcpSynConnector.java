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
 
 public class HiTcpSynConnector
   implements IHandler, IServerInitListener
 {
   protected static HiStringManager g_sm = HiStringManager.getManager();
   protected int _port;
   protected String _host;
   protected int _preLen;
   protected Logger _log;
   protected String _preLenType;
   protected int _timeOut;
   protected final HiMessageInOut msginout;
   protected HiSSLHandler sslHandler;
   protected SocketFactory socketFactory;
 
   public HiTcpSynConnector()
   {
     this._log = null;
     this._preLenType = null;
     this._timeOut = 30;
     this.msginout = new HiMessageInOut();
     this.sslHandler = HiSSLHandler.getInstance();
     this.socketFactory = null; }
 
   public void setPort(int port) { this._port = port;
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
 
   public void process(HiMessageContext ctx) throws HiException
   {
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
       try { socket = createConnection(ip, port);
       } catch (IOException e) {
         throw new HiException("231204", "connector error", e);
       }
 
       if (this._log.isInfoEnabled())
       {
         byteBuffer = (HiByteBuffer)msg.getBody();
         this._log.info(g_sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
       }
 
       try
       {
         this.msginout.write(socket.getOutputStream(), msg);
       } catch (IOException byteBuffer) {
         closeConnection(socket);
         throw new HiException("231207", "connector send error", byteBuffer);
       }
 
       try
       {
         rdlen = this.msginout.read(socket.getInputStream(), msg);
       } catch (IOException rdlen) {
         closeConnection(socket);
         throw new HiException("231205", "connector receive error", byteBuffer);
       }
 
       if (this._log.isInfoEnabled())
       {
         byteBuffer = (HiByteBuffer)msg.getBody();
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
     Socket socket = null;
 
     socket = this.socketFactory.createSocket();
 
     if (this._timeOut > 0)
       socket.connect(new InetSocketAddress(host, port), this._timeOut * 1000);
     else
       socket.connect(new InetSocketAddress(host, port));
     if (this._timeOut > 0) {
       socket.setSoTimeout(this._timeOut * 1000);
     }
     socket.setTcpNoDelay(true);
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
     if (this.sslHandler.isSSLMode())
     {
       this.sslHandler.init();
       this.socketFactory = this.sslHandler.getSocketFactory();
     }
     else
     {
       this.socketFactory = SocketFactory.getDefault();
     }
   }
 }