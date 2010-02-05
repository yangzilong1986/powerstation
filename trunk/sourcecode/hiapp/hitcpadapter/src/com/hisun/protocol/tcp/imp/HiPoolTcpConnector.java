 package com.hisun.protocol.tcp.imp;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.protocol.tcp.HiConnection;
 import com.hisun.protocol.tcp.HiConnectionPool;
 import com.hisun.protocol.tcp.HiMessageInOut;
 import com.hisun.protocol.tcp.HiSocketUtil;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiStringManager;
 import java.io.IOException;
 import java.net.Socket;
 
 public class HiPoolTcpConnector extends HiConnectionPool
 {
   private final HiMessageInOut msginout;
   private static final HiStringManager sm = HiStringManager.getManager();
 
   public HiPoolTcpConnector() {
     this.msginout = new HiMessageInOut();
   }
 
   public HiPoolTcpConnector(HiMessageInOut inout) {
     this.msginout = inout;
   }
 
   public int getSocketBuffer() {
     return this.msginout.getSocketBuffer();
   }
 
   public void setSocketBuffer(int valueI) {
     this.msginout.setSocketBuffer(valueI);
   }
 
   public int getPreLen() {
     return this.msginout.getPreLen();
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
 
   public HiMessage send(HiMessage msg) throws HiException {
     HiConnection conn = null;
     try {
       if ((this.log.isInfoEnabled()) && 
         (this.log.isInfoEnabled())) {
         HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
         this.log.info(sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), this.host, String.valueOf(this.port), String.valueOf(byteBuffer.length()), byteBuffer));
       }
 
       conn = getConnection();
     }
     catch (IOException e) {
       if (conn != null) {
         conn.close();
       }
       throw new HiException("231204", "connector error", e);
     }
     catch (InterruptedException e) {
       if (conn != null) {
         conn.close();
       }
       throw new HiException("231204", "connector interrupt error", e);
     }
 
     Socket socket = conn.getSocket();
     HiSocketUtil.printSocketStatus(socket, this.log);
     try {
       this.msginout.write(socket.getOutputStream(), msg);
     } catch (IOException e) {
       if (conn != null)
         conn.close();
       throw new HiException("231207", "connector send error", e);
     }
 
     conn.returnToPool();
     return msg;
   }
 
   public HiMessage send(HiMessage msg, String ip, int port)
     throws HiException
   {
     HiConnection conn = null;
     try {
       if ((this.log.isInfoEnabled()) && 
         (this.log.isInfoEnabled())) {
         HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
         this.log.info(sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
       }
 
       conn = getConnection(ip, port);
     } catch (IOException e) {
       if (conn != null) {
         conn.close();
       }
       throw new HiException("231204", "connector error", e);
     }
     catch (InterruptedException e) {
       if (conn != null) {
         conn.close();
       }
       throw new HiException("231204", "connector interrupt error", e);
     }
 
     Socket socket = conn.getSocket();
     try {
       this.msginout.write(socket.getOutputStream(), msg);
     } catch (IOException e) {
       if (conn != null)
         conn.close();
       throw new HiException("231207", "connector send error", e);
     }
 
     conn.returnToPool();
     return msg;
   }
 
   public HiMessage sendReceive(HiMessage msg, String ip, int port) throws HiException
   {
     HiConnection conn = null;
     try
     {
       conn = getConnection(ip, port);
     } catch (IOException e) {
       if (conn != null) {
         conn.close();
       }
       throw new HiException("231204", "connector error", e);
     }
     catch (InterruptedException rdlen)
     {
       int rdlen;
       if (conn != null) {
         conn.close();
       }
       throw new HiException("231204", "connector interrupt error", e);
 
       Socket socket = conn.getSocket();
       HiSocketUtil.printSocketStatus(socket, this.log);
 
       if ((this.log.isInfoEnabled()) && 
         (this.log.isInfoEnabled())) {
         byteBuffer = (HiByteBuffer)msg.getBody();
         this.log.info(sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), this.host, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
       }
 
       try
       {
         this.msginout.write(socket.getOutputStream(), msg);
       } catch (IOException byteBuffer) {
         if (conn != null)
           conn.close();
         throw new HiException("231207", "connector send error", byteBuffer);
       }
 
       try
       {
         rdlen = this.msginout.read(socket.getInputStream(), msg);
       } catch (IOException rdlen) {
         if (conn != null)
           conn.close();
         throw new HiException("231205", "connector receive error", byteBuffer);
       }
 
       if (this.log.isInfoEnabled())
       {
         byteBuffer = (HiByteBuffer)msg.getBody();
         this.log.info(sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), this.host, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
       }
 
       msg.setHeadItem("SCH", "rp");
 
       if (conn == null) break label309;
       label309: conn.close();
     }
     finally
     {
       if (conn != null) {
         conn.close();
       }
     }
 
     return msg;
   }
 
   public HiMessage sendReceive(HiMessage msg) throws HiException
   {
     HiConnection conn = null;
     try
     {
       conn = getConnection();
     } catch (IOException e) {
       if (conn != null) {
         conn.close();
       }
       throw new HiException("231204", "connector error", e);
     }
     catch (InterruptedException rdlen)
     {
       int rdlen;
       if (conn != null) {
         conn.close();
       }
       throw new HiException("231204", "connector interrupt error", e);
 
       Socket socket = conn.getSocket();
       HiSocketUtil.printSocketStatus(socket, this.log);
 
       if ((this.log.isInfoEnabled()) && 
         (this.log.isInfoEnabled())) {
         byteBuffer = (HiByteBuffer)msg.getBody();
         this.log.info(sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), this.host, String.valueOf(this.port), String.valueOf(byteBuffer.length()), byteBuffer));
       }
 
       try
       {
         this.msginout.write(socket.getOutputStream(), msg);
       } catch (IOException byteBuffer) {
         if (conn != null)
           conn.close();
         throw new HiException("231207", "connector send error", byteBuffer);
       }
 
       try
       {
         rdlen = this.msginout.read(socket.getInputStream(), msg);
       } catch (IOException rdlen) {
         if (conn != null)
           conn.close();
         throw new HiException("231205", "connector receive error", byteBuffer);
       }
 
       if (this.log.isInfoEnabled())
       {
         byteBuffer = (HiByteBuffer)msg.getBody();
         this.log.info(sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), this.host, String.valueOf(this.port), String.valueOf(byteBuffer.length()), byteBuffer));
       }
 
       msg.setHeadItem("SCH", "rp");
 
       if (conn == null) break label296;
       label296: conn.close();
     }
     finally
     {
       if (conn != null) {
         conn.close();
       }
     }
 
     return msg;
   }
 }