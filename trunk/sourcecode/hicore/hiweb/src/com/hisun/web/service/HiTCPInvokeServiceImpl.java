 package com.hisun.web.service;

 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import org.apache.commons.lang.StringUtils;

 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.net.Socket;
 
 public class HiTCPInvokeServiceImpl
   implements IInvokeService
 {
   private String ip;
   private int port;
   private int tmOut;
   private boolean logSwitch;
 
   public HiTCPInvokeServiceImpl()
   {
     this.tmOut = 30;
     this.logSwitch = false;
   }
 
   public String getIp()
   {
     return this.ip;
   }
 
   public void setIp(String ip)
   {
     this.ip = ip;
   }
 
   public int getPort()
   {
     return this.port;
   }
 
   public void setPort(int port)
   {
     this.port = port;
   }
 
   public HiETF invoke(HiETF reqETF) throws HiException
   {
     return reqETF;
   }
 
   public HiMessage invoke(HiMessage msg) throws HiException {
     String code = msg.getHeadItem("STC");
     Socket socket = null;
     if (this.logSwitch)
       msg.setHeadItem("STF", "1");
     try
     {
       socket = new Socket(this.ip, this.port);
       socket.setSoTimeout(this.tmOut * 1000);
       InputStream is = socket.getInputStream();
       OutputStream os = socket.getOutputStream();
       write(os, msg.toString().getBytes(), 8);
 
       ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
       if (read(is, buf, 8) == 0) {
         throw new HiException("241148", code);
       }
       HiMessage localHiMessage = new HiMessage(buf.toString());
 
       return localHiMessage;
     }
     catch (HiException e)
     {
     }
     catch (Exception e)
     {
     }
     finally
     {
       if (socket != null)
         try {
           socket.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
     }
   }
 
   public static int read(InputStream in, OutputStream out, int prelen)
     throws IOException
   {
     int len = readlen(in, prelen);
     if (len <= 0) {
       return 0;
     }
     int count = 0;
     byte[] buf = new byte[1024];
     while (count < len) {
       int readlen = in.read(buf, 0, Math.min(len - count, 1024));
       if (readlen == -1)
         break;
       out.write(buf, 0, readlen);
       count += readlen;
     }
 
     if (count != len) {
       throw new IOException("data is not receive completed:(" + len + "," + count + ")");
     }
 
     return len;
   }
 
   public static int readlen(InputStream in, int prelen) throws IOException {
     if (prelen <= 0) {
       return 0;
     }
     byte[] lendata = new byte[prelen];
     int count = 0; int off = 0;
     do
     {
       int c;
       if ((c = in.read()) == -1) break;
       lendata[(off++)] = (byte)c;
       ++count; }
     while (count != prelen);
 
     if (count != prelen) {
       return 0;
     }
     int len = Integer.parseInt(new String(lendata));
     return len;
   }
 
   public static void write(OutputStream out, byte[] data, int prelen) throws IOException
   {
     int len = data.length;
     String prestr = String.valueOf(len);
     prestr = StringUtils.leftPad(prestr, prelen, "0");
 
     out.write(prestr.getBytes());
     out.write(data);
     out.flush();
   }
 
   public int getTmOut()
   {
     return this.tmOut;
   }
 
   public void setTmOut(int tmOut)
   {
     this.tmOut = tmOut;
   }
 
   public boolean isLogSwitch()
   {
     return this.logSwitch;
   }
 
   public void setLogSwitch(boolean logSwitch)
   {
     this.logSwitch = logSwitch;
   }
 }