/*     */ package com.hisun.protocol.tcp;
/*     */ 
/*     */ import com.hisun.common.util.HiByteUtil;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.util.HiConvHelper;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiSocketUtil
/*     */ {
/* 299 */   static int MAX_SHUTDOWN_TRIES = 20;
/*     */ 
/*     */   public static void read(InputStream in, OutputStream out)
/*     */     throws IOException
/*     */   {
/*  23 */     byte[] buf = new byte[10240];
/*  24 */     int len = in.read(buf);
/*  25 */     if (len != -1)
/*  26 */       out.write(buf, 0, len);
/*     */   }
/*     */ 
/*     */   public static int read(InputStream in, byte[] out) throws IOException
/*     */   {
/*  31 */     int len = out.length;
/*  32 */     int count = 0;
/*     */ 
/*  34 */     int readlen = 0;
/*  35 */     while (count < len)
/*     */     {
/*  37 */       readlen = in.read(out, count, Math.min(len - count, 1024));
/*  38 */       if (readlen == -1) {
/*     */         break;
/*     */       }
/*  41 */       count += readlen;
/*     */     }
/*     */ 
/*  44 */     if (count != len) {
/*  45 */       throw new IOException("data is not receive completed:(" + len + "," + count + ")");
/*     */     }
/*     */ 
/*  48 */     return len;
/*     */   }
/*     */ 
/*     */   public static void write(OutputStream out, byte[] data) throws IOException {
/*  52 */     out.write(data);
/*  53 */     out.flush();
/*     */   }
/*     */ 
/*     */   public static int read(InputStream in, OutputStream out, int prelen)
/*     */     throws IOException
/*     */   {
/*  59 */     int len = readlen(in, prelen);
/*     */ 
/*  61 */     if (len <= 0) {
/*  62 */       return 0;
/*     */     }
/*  64 */     int count = 0;
/*     */ 
/*  74 */     byte[] buf = new byte[1024];
/*  75 */     while (count < len)
/*     */     {
/*  78 */       int readlen = in.read(buf, 0, Math.min(len - count, 1024));
/*     */ 
/*  80 */       if (readlen == -1) {
/*     */         break;
/*     */       }
/*  83 */       out.write(buf, 0, readlen);
/*  84 */       count += readlen;
/*     */     }
/*     */ 
/*  89 */     if (count != len) {
/*  90 */       throw new IOException("data is not receive completed:(" + len + "," + count + ")");
/*     */     }
/*     */ 
/*  93 */     return len;
/*     */   }
/*     */ 
/*     */   public static int read(InputStream in, OutputStream out, int prelen, String type) throws IOException
/*     */   {
/*  98 */     int len = readlen(in, prelen, type);
/*  99 */     if (len <= 0) {
/* 100 */       return 0;
/*     */     }
/* 102 */     int count = 0;
/* 103 */     byte[] buf = new byte[1024];
/* 104 */     while (count < len)
/*     */     {
/* 107 */       int readlen = in.read(buf, 0, Math.min(len - count, 1024));
/*     */ 
/* 109 */       if (readlen == -1)
/*     */         break;
/* 111 */       out.write(buf, 0, readlen);
/* 112 */       count += readlen;
/*     */     }
/*     */ 
/* 115 */     if (count != len) {
/* 116 */       throw new IOException("data is not receive completed:(" + len + "," + count + ")");
/*     */     }
/*     */ 
/* 119 */     return len;
/*     */   }
/*     */ 
/*     */   public static void write(OutputStream out, byte[] data, int prelen) throws IOException
/*     */   {
/* 124 */     int len = data.length;
/* 125 */     String prestr = String.valueOf(len);
/*     */ 
/* 128 */     prestr = StringUtils.leftPad(prestr, prelen, "0");
/*     */ 
/* 130 */     out.write(prestr.getBytes());
/* 131 */     out.write(data);
/* 132 */     out.flush();
/*     */   }
/*     */ 
/*     */   public static void write(OutputStream out, byte[] data, int prelen, String type)
/*     */     throws IOException
/*     */   {
/*     */     int i;
/* 137 */     int len = data.length;
/* 138 */     if ("bcd".equalsIgnoreCase(type))
/*     */     {
/* 140 */       byte[] bs = HiConvHelper.ascStr2Bcd(String.valueOf(len));
/* 141 */       int k = prelen - bs.length;
/* 142 */       for (i = 0; i < prelen - bs.length; ++i) {
/* 143 */         out.write(0);
/*     */       }
/* 145 */       out.write(bs); } else {
/* 146 */       if ("bin".equalsIgnoreCase(type));
/* 147 */       switch (prelen)
/*     */       {
/*     */       case 2:
/* 149 */         out.write(HiByteUtil.shortToByteArray(len));
/* 150 */         break;
/*     */       case 4:
/* 152 */         out.write(HiByteUtil.shortToByteArray(len));
/* 153 */         break;
/*     */       case 8:
/* 155 */         out.write(HiByteUtil.shortToByteArray(len));
/* 156 */         break;
/*     */       default:
/*     */         String prestr;
/* 158 */         out.write(HiByteUtil.shortToByteArray(len));
/* 159 */         break label298:
/*     */ 
/* 161 */         if ("asc".equalsIgnoreCase(type)) {
/* 162 */           prestr = String.valueOf(len);
/* 163 */           prestr = StringUtils.leftPad(prestr, prelen, "0");
/* 164 */           out.write(prestr.getBytes());
/* 165 */         } else if ("ebcd".equalsIgnoreCase(type)) {
/* 166 */           prestr = String.valueOf(len);
/* 167 */           prestr = StringUtils.leftPad(prestr, prelen, "0");
/* 168 */           byte[] bs = prestr.getBytes();
/* 169 */           for (i = 0; i < bs.length; ++i) {
/* 170 */             bs[i] = (byte)((short)bs[i] - 48 + 240);
/*     */           }
/* 172 */           out.write(bs);
/*     */         } else {
/* 174 */           prestr = String.valueOf(len);
/* 175 */           prestr = StringUtils.leftPad(prestr, prelen, "0");
/* 176 */           out.write(prestr.getBytes()); }
/*     */       }
/*     */     }
/* 179 */     label298: out.write(data);
/* 180 */     out.flush();
/*     */   }
/*     */ 
/*     */   public static int readlen(InputStream in, int prelen) throws IOException {
/* 184 */     if (prelen <= 0) {
/* 185 */       return 0;
/*     */     }
/* 187 */     byte[] lendata = new byte[prelen];
/* 188 */     int count = 0; int off = 0;
/*     */     do
/*     */     {
/*     */       int c;
/* 190 */       if ((c = in.read()) == -1) break;
/* 191 */       lendata[(off++)] = (byte)c;
/* 192 */       ++count; }
/* 193 */     while (count != prelen);
/*     */ 
/* 198 */     if (count != prelen) {
/* 199 */       return 0;
/*     */     }
/*     */ 
/* 204 */     int len = Integer.parseInt(new String(lendata));
/* 205 */     return len;
/*     */   }
/*     */ 
/*     */   public static int readlen(InputStream in, int prelen, String type)
/*     */     throws IOException
/*     */   {
/*     */     int len;
/* 210 */     if (prelen <= 0) {
/* 211 */       return 0;
/*     */     }
/* 213 */     byte[] lendata = new byte[prelen];
/* 214 */     int count = 0; int off = 0;
/*     */     do
/*     */     {
/*     */       int c;
/* 216 */       if ((c = in.read()) == -1) break;
/* 217 */       lendata[(off++)] = (byte)c;
/* 218 */       ++count; }
/* 219 */     while (count != prelen);
/*     */ 
/* 224 */     if (count != prelen) {
/* 225 */       return 0;
/*     */     }
/*     */ 
/* 230 */     if ("bcd".equalsIgnoreCase(type))
/*     */     {
/* 232 */       len = NumberUtils.toInt(HiConvHelper.bcd2AscStr(lendata)); } else {
/* 233 */       if ("bin".equalsIgnoreCase(type));
/* 234 */       switch (lendata.length)
/*     */       {
/*     */       case 2:
/* 236 */         len = HiByteUtil.byteArrayToShort(lendata);
/* 237 */         break;
/*     */       case 4:
/* 239 */         len = HiByteUtil.byteArrayToInt(lendata);
/* 240 */         break;
/*     */       case 8:
/* 242 */         len = (int)HiByteUtil.byteArrayToLong(lendata);
/* 243 */         break;
/*     */       default:
/* 245 */         len = HiByteUtil.byteArrayToInt(lendata);
/* 246 */         break label257:
/*     */ 
/* 248 */         if ("asc".equalsIgnoreCase(type)) {
/* 249 */           len = Integer.parseInt(new String(lendata));
/* 250 */           break label257: } if ("ebcd".equalsIgnoreCase(type)) {
/* 251 */           for (int i = 0; i < lendata.length; ++i) {
/* 252 */             lendata[i] = (byte)((short)lendata[i] - 240 + 48);
/*     */           }
/* 254 */           len = Integer.parseInt(new String(lendata));
/* 255 */           break label257: }
/* 256 */         len = Integer.parseInt(new String(lendata)); }
/*     */     }
/* 258 */     label257: return len;
/*     */   }
/*     */ 
/*     */   public static boolean isSameAddress(InetAddress server, InetAddress client)
/*     */   {
/* 274 */     byte[] serverAddr = server.getAddress();
/* 275 */     byte[] clientAddr = client.getAddress();
/* 276 */     if (serverAddr.length != clientAddr.length)
/* 277 */       return false;
/* 278 */     boolean match = true;
/* 279 */     for (int i = 0; i < serverAddr.length; ++i) {
/* 280 */       if (serverAddr[i] != clientAddr[i]) {
/* 281 */         match = false;
/* 282 */         break;
/*     */       }
/*     */     }
/* 285 */     if (match) {
/* 286 */       return true;
/*     */     }
/*     */ 
/* 289 */     for (i = 0; i < serverAddr.length; ++i) {
/* 290 */       if (serverAddr[i] != clientAddr[(serverAddr.length - 1 - i)])
/* 291 */         return false;
/*     */     }
/* 293 */     return true;
/*     */   }
/*     */ 
/*     */   public static void setMaxShutdownTries(int mst)
/*     */   {
/* 302 */     MAX_SHUTDOWN_TRIES = mst;
/*     */   }
/*     */ 
/*     */   public static void shutdownInput(Socket socket) throws IOException
/*     */   {
/*     */     try {
/* 308 */       InputStream is = socket.getInputStream();
/* 309 */       int available = is.available();
/* 310 */       int count = 0;
/*     */ 
/* 316 */       while ((available > 0) && (count++ < MAX_SHUTDOWN_TRIES)) {
/* 317 */         is.skip(available);
/* 318 */         available = is.available();
/*     */       }
/*     */     }
/*     */     catch (NullPointerException npe)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int readLine(InputStream in, byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 330 */     if (len <= 0) {
/* 331 */       return 0;
/*     */     }
/* 333 */     int count = 0;
/*     */     do
/*     */     {
/*     */       int c;
/* 335 */       if ((c = in.read()) == -1) break;
/* 336 */       b[(off++)] = (byte)c;
/* 337 */       ++count;
/* 338 */       if (c == 10) break;  }
/* 338 */     while (count != len);
/*     */ 
/* 342 */     return ((count > 0) ? count : -1);
/*     */   }
/*     */ 
/*     */   public static void printSocketStatus(Socket socket, Logger log) {
/* 346 */     if (log.isDebugEnabled()) {
/* 347 */       StringBuffer buf = new StringBuffer();
/* 348 */       buf.append(socket.toString()).append("status:");
/* 349 */       if (socket.isConnected())
/* 350 */         buf.append("connected");
/* 351 */       if (socket.isOutputShutdown()) {
/* 352 */         buf.append(",output shutdown");
/*     */       }
/* 354 */       if (socket.isInputShutdown()) {
/* 355 */         buf.append(",input shutdown");
/*     */       }
/* 357 */       log.debug(buf.toString());
/*     */     }
/*     */   }
/*     */ }