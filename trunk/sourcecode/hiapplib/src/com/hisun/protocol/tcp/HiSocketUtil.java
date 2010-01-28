 package com.hisun.protocol.tcp;
 
 import com.hisun.common.util.HiByteUtil;
 import com.hisun.hilog4j.Logger;
 import com.hisun.util.HiConvHelper;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.net.InetAddress;
 import java.net.Socket;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiSocketUtil
 {
   static int MAX_SHUTDOWN_TRIES = 20;
 
   public static void read(InputStream in, OutputStream out)
     throws IOException
   {
     byte[] buf = new byte[10240];
     int len = in.read(buf);
     if (len != -1)
       out.write(buf, 0, len);
   }
 
   public static int read(InputStream in, byte[] out) throws IOException
   {
     int len = out.length;
     int count = 0;
 
     int readlen = 0;
     while (count < len)
     {
       readlen = in.read(out, count, Math.min(len - count, 1024));
       if (readlen == -1) {
         break;
       }
       count += readlen;
     }
 
     if (count != len) {
       throw new IOException("data is not receive completed:(" + len + "," + count + ")");
     }
 
     return len;
   }
 
   public static void write(OutputStream out, byte[] data) throws IOException {
     out.write(data);
     out.flush();
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
     while (count < len)
     {
       int readlen = in.read(buf, 0, Math.min(len - count, 1024));
 
       if (readlen == -1) {
         break;
       }
       out.write(buf, 0, readlen);
       count += readlen;
     }
 
     if (count != len) {
       throw new IOException("data is not receive completed:(" + len + "," + count + ")");
     }
 
     return len;
   }
 
   public static int read(InputStream in, OutputStream out, int prelen, String type) throws IOException
   {
     int len = readlen(in, prelen, type);
     if (len <= 0) {
       return 0;
     }
     int count = 0;
     byte[] buf = new byte[1024];
     while (count < len)
     {
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
 
   public static void write(OutputStream out, byte[] data, int prelen) throws IOException
   {
     int len = data.length;
     String prestr = String.valueOf(len);
 
     prestr = StringUtils.leftPad(prestr, prelen, "0");
 
     out.write(prestr.getBytes());
     out.write(data);
     out.flush();
   }
 
   public static void write(OutputStream out, byte[] data, int prelen, String type)
     throws IOException
   {
     int i;
     int len = data.length;
     if ("bcd".equalsIgnoreCase(type))
     {
       byte[] bs = HiConvHelper.ascStr2Bcd(String.valueOf(len));
       int k = prelen - bs.length;
       for (i = 0; i < prelen - bs.length; ++i) {
         out.write(0);
       }
       out.write(bs); } else {
       if ("bin".equalsIgnoreCase(type));
       switch (prelen)
       {
       case 2:
         out.write(HiByteUtil.shortToByteArray(len));
         break;
       case 4:
         out.write(HiByteUtil.shortToByteArray(len));
         break;
       case 8:
         out.write(HiByteUtil.shortToByteArray(len));
         break;
       default:
         String prestr;
         out.write(HiByteUtil.shortToByteArray(len));
         break label298:
 
         if ("asc".equalsIgnoreCase(type)) {
           prestr = String.valueOf(len);
           prestr = StringUtils.leftPad(prestr, prelen, "0");
           out.write(prestr.getBytes());
         } else if ("ebcd".equalsIgnoreCase(type)) {
           prestr = String.valueOf(len);
           prestr = StringUtils.leftPad(prestr, prelen, "0");
           byte[] bs = prestr.getBytes();
           for (i = 0; i < bs.length; ++i) {
             bs[i] = (byte)((short)bs[i] - 48 + 240);
           }
           out.write(bs);
         } else {
           prestr = String.valueOf(len);
           prestr = StringUtils.leftPad(prestr, prelen, "0");
           out.write(prestr.getBytes()); }
       }
     }
     label298: out.write(data);
     out.flush();
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
 
   public static int readlen(InputStream in, int prelen, String type)
     throws IOException
   {
     int len;
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
 
     if ("bcd".equalsIgnoreCase(type))
     {
       len = NumberUtils.toInt(HiConvHelper.bcd2AscStr(lendata)); } else {
       if ("bin".equalsIgnoreCase(type));
       switch (lendata.length)
       {
       case 2:
         len = HiByteUtil.byteArrayToShort(lendata);
         break;
       case 4:
         len = HiByteUtil.byteArrayToInt(lendata);
         break;
       case 8:
         len = (int)HiByteUtil.byteArrayToLong(lendata);
         break;
       default:
         len = HiByteUtil.byteArrayToInt(lendata);
         break label257:
 
         if ("asc".equalsIgnoreCase(type)) {
           len = Integer.parseInt(new String(lendata));
           break label257: } if ("ebcd".equalsIgnoreCase(type)) {
           for (int i = 0; i < lendata.length; ++i) {
             lendata[i] = (byte)((short)lendata[i] - 240 + 48);
           }
           len = Integer.parseInt(new String(lendata));
           break label257: }
         len = Integer.parseInt(new String(lendata)); }
     }
     label257: return len;
   }
 
   public static boolean isSameAddress(InetAddress server, InetAddress client)
   {
     byte[] serverAddr = server.getAddress();
     byte[] clientAddr = client.getAddress();
     if (serverAddr.length != clientAddr.length)
       return false;
     boolean match = true;
     for (int i = 0; i < serverAddr.length; ++i) {
       if (serverAddr[i] != clientAddr[i]) {
         match = false;
         break;
       }
     }
     if (match) {
       return true;
     }
 
     for (i = 0; i < serverAddr.length; ++i) {
       if (serverAddr[i] != clientAddr[(serverAddr.length - 1 - i)])
         return false;
     }
     return true;
   }
 
   public static void setMaxShutdownTries(int mst)
   {
     MAX_SHUTDOWN_TRIES = mst;
   }
 
   public static void shutdownInput(Socket socket) throws IOException
   {
     try {
       InputStream is = socket.getInputStream();
       int available = is.available();
       int count = 0;
 
       while ((available > 0) && (count++ < MAX_SHUTDOWN_TRIES)) {
         is.skip(available);
         available = is.available();
       }
     }
     catch (NullPointerException npe)
     {
     }
   }
 
   public static int readLine(InputStream in, byte[] b, int off, int len)
     throws IOException
   {
     if (len <= 0) {
       return 0;
     }
     int count = 0;
     do
     {
       int c;
       if ((c = in.read()) == -1) break;
       b[(off++)] = (byte)c;
       ++count;
       if (c == 10) break;  }
     while (count != len);
 
     return ((count > 0) ? count : -1);
   }
 
   public static void printSocketStatus(Socket socket, Logger log) {
     if (log.isDebugEnabled()) {
       StringBuffer buf = new StringBuffer();
       buf.append(socket.toString()).append("status:");
       if (socket.isConnected())
         buf.append("connected");
       if (socket.isOutputShutdown()) {
         buf.append(",output shutdown");
       }
       if (socket.isInputShutdown()) {
         buf.append(",input shutdown");
       }
       log.debug(buf.toString());
     }
   }
 }