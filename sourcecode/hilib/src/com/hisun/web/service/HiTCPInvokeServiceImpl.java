/*     */ package com.hisun.web.service;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Socket;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiTCPInvokeServiceImpl
/*     */   implements IInvokeService
/*     */ {
/*     */   private String ip;
/*     */   private int port;
/*     */   private int tmOut;
/*     */   private boolean logSwitch;
/*     */ 
/*     */   public HiTCPInvokeServiceImpl()
/*     */   {
/*  24 */     this.tmOut = 30;
/*  25 */     this.logSwitch = false;
/*     */   }
/*     */ 
/*     */   public String getIp()
/*     */   {
/*  31 */     return this.ip;
/*     */   }
/*     */ 
/*     */   public void setIp(String ip)
/*     */   {
/*  39 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */   public int getPort()
/*     */   {
/*  46 */     return this.port;
/*     */   }
/*     */ 
/*     */   public void setPort(int port)
/*     */   {
/*  54 */     this.port = port;
/*     */   }
/*     */ 
/*     */   public HiETF invoke(HiETF reqETF) throws HiException
/*     */   {
/*  59 */     return reqETF;
/*     */   }
/*     */ 
/*     */   public HiMessage invoke(HiMessage msg) throws HiException {
/*  63 */     String code = msg.getHeadItem("STC");
/*  64 */     Socket socket = null;
/*  65 */     if (this.logSwitch)
/*  66 */       msg.setHeadItem("STF", "1");
/*     */     try
/*     */     {
/*  69 */       socket = new Socket(this.ip, this.port);
/*  70 */       socket.setSoTimeout(this.tmOut * 1000);
/*  71 */       InputStream is = socket.getInputStream();
/*  72 */       OutputStream os = socket.getOutputStream();
/*  73 */       write(os, msg.toString().getBytes(), 8);
/*     */ 
/*  75 */       ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
/*  76 */       if (read(is, buf, 8) == 0) {
/*  77 */         throw new HiException("241148", code);
/*     */       }
/*  79 */       HiMessage localHiMessage = new HiMessage(buf.toString());
/*     */ 
/*  92 */       return localHiMessage;
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/*  85 */       if (socket != null)
/*     */         try {
/*  87 */           socket.close();
/*     */         } catch (IOException e) {
/*  89 */           e.printStackTrace();
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int read(InputStream in, OutputStream out, int prelen)
/*     */     throws IOException
/*     */   {
/*  97 */     int len = readlen(in, prelen);
/*  98 */     if (len <= 0) {
/*  99 */       return 0;
/*     */     }
/* 101 */     int count = 0;
/* 102 */     byte[] buf = new byte[1024];
/* 103 */     while (count < len) {
/* 104 */       int readlen = in.read(buf, 0, Math.min(len - count, 1024));
/* 105 */       if (readlen == -1)
/*     */         break;
/* 107 */       out.write(buf, 0, readlen);
/* 108 */       count += readlen;
/*     */     }
/*     */ 
/* 111 */     if (count != len) {
/* 112 */       throw new IOException("data is not receive completed:(" + len + "," + count + ")");
/*     */     }
/*     */ 
/* 115 */     return len;
/*     */   }
/*     */ 
/*     */   public static int readlen(InputStream in, int prelen) throws IOException {
/* 119 */     if (prelen <= 0) {
/* 120 */       return 0;
/*     */     }
/* 122 */     byte[] lendata = new byte[prelen];
/* 123 */     int count = 0; int off = 0;
/*     */     do
/*     */     {
/*     */       int c;
/* 125 */       if ((c = in.read()) == -1) break;
/* 126 */       lendata[(off++)] = (byte)c;
/* 127 */       ++count; }
/* 128 */     while (count != prelen);
/*     */ 
/* 133 */     if (count != prelen) {
/* 134 */       return 0;
/*     */     }
/* 136 */     int len = Integer.parseInt(new String(lendata));
/* 137 */     return len;
/*     */   }
/*     */ 
/*     */   public static void write(OutputStream out, byte[] data, int prelen) throws IOException
/*     */   {
/* 142 */     int len = data.length;
/* 143 */     String prestr = String.valueOf(len);
/* 144 */     prestr = StringUtils.leftPad(prestr, prelen, "0");
/*     */ 
/* 146 */     out.write(prestr.getBytes());
/* 147 */     out.write(data);
/* 148 */     out.flush();
/*     */   }
/*     */ 
/*     */   public int getTmOut()
/*     */   {
/* 155 */     return this.tmOut;
/*     */   }
/*     */ 
/*     */   public void setTmOut(int tmOut)
/*     */   {
/* 162 */     this.tmOut = tmOut;
/*     */   }
/*     */ 
/*     */   public boolean isLogSwitch()
/*     */   {
/* 169 */     return this.logSwitch;
/*     */   }
/*     */ 
/*     */   public void setLogSwitch(boolean logSwitch)
/*     */   {
/* 176 */     this.logSwitch = logSwitch;
/*     */   }
/*     */ }