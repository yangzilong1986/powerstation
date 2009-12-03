/*     */ package com.hisun.client;
/*     */ 
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.protocol.tcp.HiSocketUtil;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Socket;
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.security.cert.CertificateException;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class HiTCPInvokeService extends HiInvokeService
/*     */ {
/*  37 */   private HiETF etf = HiETFFactory.createETF();
/*  38 */   private Logger logger = Logger.getLogger("invokeservice");
/*     */ 
/*     */   public String toString() {
/*  41 */     return this.etf.toString();
/*     */   }
/*     */ 
/*     */   public HiTCPInvokeService()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HiTCPInvokeService(String code) {
/*  49 */     super(code);
/*     */   }
/*     */ 
/*     */   protected HiETF doInvoke(String code, HiETF root) throws Exception {
/*  53 */     IpPortPair ipPortPair = info.getIpPortPair();
/*  54 */     log("send host [" + ipPortPair.ip + "]:[" + ipPortPair.port + "]:[" + root.toString() + "]");
/*     */ 
/*  56 */     Socket socket = null;
/*     */     try {
/*  58 */       if (ipPortPair.sslMode == 0)
/*  59 */         socket = new Socket(ipPortPair.ip, ipPortPair.port);
/*     */       else {
/*  61 */         socket = createSSLSocket(ipPortPair.ip, ipPortPair.port, ipPortPair.identityFile, ipPortPair.trustFile, ipPortPair.keyPsw, ipPortPair.trustPsw);
/*     */       }
/*     */ 
/*  65 */       socket.setSoTimeout(ipPortPair.tmOut * 1000);
/*  66 */       if (ipPortPair.isSRNConn())
/*  67 */         root = doSRNInvoke(socket, code, root);
/*     */       else {
/*  69 */         root = doTCPInvoke(socket, code, root);
/*     */       }
/*  71 */       log("recv host [" + ipPortPair.ip + "]:[" + ipPortPair.port + "]:[" + root.toString() + "]");
/*     */ 
/*  73 */       HiETF localHiETF = root;
/*     */ 
/*  78 */       return localHiETF;
/*     */     }
/*     */     finally
/*     */     {
/*  75 */       if (socket != null)
/*  76 */         socket.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected HiETF doSRNInvoke(Socket socket, String code, HiETF root)
/*     */     throws Exception
/*     */   {
/*  83 */     HiMessage msg = new HiMessage(info.getServerName(), info.getMsgType());
/*  84 */     IpPortPair ipPortPair = info.getIpPortPair();
/*  85 */     if (ipPortPair.logSwitch) {
/*  86 */       msg.setHeadItem("STF", "1");
/*     */     }
/*  88 */     msg.setHeadItem("STC", code);
/*  89 */     msg.setHeadItem("SCH", "rq");
/*  90 */     msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
/*  91 */     msg.setHeadItem("ECT", "text/etf");
/*  92 */     msg.setBody(root);
/*  93 */     InputStream is = socket.getInputStream();
/*  94 */     OutputStream os = socket.getOutputStream();
/*     */ 
/*  96 */     HiSocketUtil.write(os, msg.toString().getBytes(), 8);
/*     */ 
/*  98 */     ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
/*  99 */     if (HiSocketUtil.read(is, buf, 8) == 0) {
/* 100 */       throw new Exception("invoke service:[" + root.getChildValue("TXN_CD") + "] error");
/*     */     }
/*     */ 
/* 104 */     msg = new HiMessage(buf.toString());
/*     */ 
/* 106 */     return msg.getETFBody();
/*     */   }
/*     */ 
/*     */   protected HiETF doTCPInvoke(Socket socket, String code, HiETF root) throws Exception
/*     */   {
/* 111 */     root.setChildValue("TXN_CD", code);
/* 112 */     InputStream is = socket.getInputStream();
/* 113 */     OutputStream os = socket.getOutputStream();
/*     */ 
/* 115 */     HiSocketUtil.write(os, root.toString().getBytes(), 8);
/*     */ 
/* 117 */     ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
/* 118 */     if (HiSocketUtil.read(is, buf, 8) == 0) {
/* 119 */       throw new Exception("invoke service:[" + root.getChildValue("TXN_CD") + "] error");
/*     */     }
/*     */ 
/* 122 */     return HiETFFactory.createETF(buf.toString());
/*     */   }
/*     */ 
/*     */   private Socket createSSLSocket(String host, int port, String identityFile, String trustFile, String keyPsw, String trustPsw)
/*     */   {
/* 127 */     Socket socket = null;
/* 128 */     if (StringUtils.isEmpty(host)) {
/* 129 */       log("host is null");
/* 130 */       return null;
/*     */     }
/*     */ 
/* 133 */     if (StringUtils.isEmpty(identityFile)) {
/* 134 */       log("identityFile is null");
/* 135 */       return null;
/*     */     }
/*     */ 
/* 138 */     if (StringUtils.isEmpty(trustFile)) {
/* 139 */       log("trustFile is null");
/* 140 */       return null;
/*     */     }
/*     */ 
/* 143 */     if (StringUtils.isEmpty(keyPsw)) {
/* 144 */       log("keyPsw is null");
/* 145 */       return null;
/*     */     }
/*     */ 
/* 148 */     if (StringUtils.isEmpty(trustPsw)) {
/* 149 */       log("trustPsw is null");
/* 150 */       return null;
/*     */     }
/*     */     try
/*     */     {
/* 154 */       SSLContext context = SSLContext.getInstance("SSLv3");
/*     */ 
/* 156 */       KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
/*     */ 
/* 158 */       TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/*     */ 
/* 161 */       KeyStore ks = KeyStore.getInstance("JKS");
/* 162 */       KeyStore tks = KeyStore.getInstance("JKS");
/*     */ 
/* 164 */       InputStream identityIs = Thread.currentThread().getContextClassLoader().getResourceAsStream(identityFile);
/*     */ 
/* 166 */       InputStream trustIs = Thread.currentThread().getContextClassLoader().getResourceAsStream(trustFile);
/*     */ 
/* 172 */       ks.load(identityIs, keyPsw.toCharArray());
/* 173 */       tks.load(trustIs, trustPsw.toCharArray());
/*     */ 
/* 175 */       kmf.init(ks, keyPsw.toCharArray());
/* 176 */       tmf.init(tks);
/*     */ 
/* 178 */       context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
/*     */ 
/* 181 */       socket = context.getSocketFactory().createSocket(host, port);
/*     */     } catch (NoSuchAlgorithmException e) {
/* 183 */       e.printStackTrace();
/*     */     } catch (KeyStoreException e) {
/* 185 */       e.printStackTrace();
/*     */     } catch (CertificateException e) {
/* 187 */       e.printStackTrace();
/*     */     } catch (FileNotFoundException e) {
/* 189 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 191 */       e.printStackTrace();
/*     */     } catch (UnrecoverableKeyException e) {
/* 193 */       e.printStackTrace();
/*     */     } catch (KeyManagementException e) {
/* 195 */       e.printStackTrace();
/*     */     }
/* 197 */     return socket;
/*     */   }
/*     */ 
/*     */   protected void log(String msg) {
/* 201 */     if (this.logger.isInfoEnabled())
/* 202 */       this.logger.info(msg);
/*     */   }
/*     */ }