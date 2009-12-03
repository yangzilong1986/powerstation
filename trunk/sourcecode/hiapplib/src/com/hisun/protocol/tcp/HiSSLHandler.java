/*     */ package com.hisun.protocol.tcp;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.io.InputStream;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLServerSocket;
/*     */ import javax.net.ssl.SSLServerSocketFactory;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ 
/*     */ public class HiSSLHandler
/*     */ {
/*     */   static final String KEYSTORE_SUF = ".keystore";
/*     */   SSLContext sslContext;
/*     */   private int sslMode;
/*     */   private int methType;
/*     */   private String certPath;
/*     */   private String identityKS;
/*     */   private String keyPsw;
/*     */   private String alg;
/*     */   private String trustKS;
/*     */   private int authFlag;
/*     */ 
/*     */   public HiSSLHandler()
/*     */   {
/*  31 */     this.sslMode = 0;
/*     */ 
/*  38 */     this.methType = 1;
/*     */ 
/*  69 */     this.authFlag = 0; }
/*     */ 
/*     */   public int getSslMode() {
/*  72 */     return this.sslMode;
/*     */   }
/*     */ 
/*     */   public void setSslMode(int sslMode) {
/*  76 */     this.sslMode = sslMode;
/*     */   }
/*     */ 
/*     */   public int getMethType() {
/*  80 */     return this.methType;
/*     */   }
/*     */ 
/*     */   public void setMethType(int methType) {
/*  84 */     this.methType = methType;
/*     */   }
/*     */ 
/*     */   public String getCertPath() {
/*  88 */     return this.certPath;
/*     */   }
/*     */ 
/*     */   public void TrustKS(String certPath) {
/*  92 */     this.certPath = certPath;
/*     */   }
/*     */ 
/*     */   public String getIdentityKS() {
/*  96 */     return this.identityKS;
/*     */   }
/*     */ 
/*     */   public void setIdentityKS(String identityKS) {
/* 100 */     this.identityKS = identityKS;
/*     */   }
/*     */ 
/*     */   public String getKeyPsw() {
/* 104 */     return this.keyPsw;
/*     */   }
/*     */ 
/*     */   public void setKeyPsw(String keyPsw) {
/* 108 */     this.keyPsw = keyPsw;
/*     */   }
/*     */ 
/*     */   public String getAlg() {
/* 112 */     return this.alg;
/*     */   }
/*     */ 
/*     */   public void setAlg(String alg) {
/* 116 */     this.alg = alg;
/*     */   }
/*     */ 
/*     */   public String getTrustKS() {
/* 120 */     return this.trustKS;
/*     */   }
/*     */ 
/*     */   public void setTrustKS(String trustKS) {
/* 124 */     this.trustKS = trustKS;
/*     */   }
/*     */ 
/*     */   public int getAuthFlag() {
/* 128 */     return this.authFlag;
/*     */   }
/*     */ 
/*     */   public void setAuthFlag(int authFlag) {
/* 132 */     this.authFlag = authFlag;
/*     */   }
/*     */ 
/*     */   public boolean isSSLMode()
/*     */   {
/* 138 */     return (this.sslMode == 0);
/*     */   }
/*     */ 
/*     */   public static HiSSLHandler getInstance()
/*     */   {
/* 147 */     return new HiSSLHandler();
/*     */   }
/*     */ 
/*     */   public void init() throws HiException
/*     */   {
/* 152 */     if (!(isSSLMode()))
/* 153 */       return;
/*     */     try
/*     */     {
/* 156 */       this.sslContext = SSLContext.getInstance("SSLv3");
/* 157 */       KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
/* 158 */       TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/*     */ 
/* 160 */       KeyStore ks = KeyStore.getInstance("JKS");
/* 161 */       KeyStore tks = KeyStore.getInstance("JKS");
/*     */ 
/* 163 */       loadKeyStore(ks, tks);
/*     */ 
/* 165 */       kmf.init(ks, this.keyPsw.toCharArray());
/* 166 */       tmf.init(tks);
/*     */ 
/* 168 */       this.sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
/*     */     }
/*     */     catch (Exception e) {
/* 171 */       throw HiException.makeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public SSLServerSocketFactory getServerSocketFactory()
/*     */   {
/* 177 */     if (this.sslContext != null)
/*     */     {
/* 179 */       return this.sslContext.getServerSocketFactory();
/*     */     }
/* 181 */     return null;
/*     */   }
/*     */ 
/*     */   public SSLSocketFactory getSocketFactory()
/*     */   {
/* 186 */     if (this.sslContext != null)
/*     */     {
/* 188 */       return this.sslContext.getSocketFactory();
/*     */     }
/* 190 */     return null;
/*     */   }
/*     */ 
/*     */   public void initServerSocket(ServerSocket serverSocket)
/*     */   {
/* 195 */     SSLServerSocket sslSS = (SSLServerSocket)serverSocket;
/*     */ 
/* 197 */     if (this.authFlag == 0)
/*     */     {
/* 199 */       sslSS.setNeedClientAuth(true);
/*     */     }
/*     */     else
/*     */     {
/* 203 */       sslSS.setNeedClientAuth(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void initClientSocket(Socket socket)
/*     */   {
/* 209 */     SSLSocket ss = (SSLSocket)socket;
/*     */ 
/* 211 */     ss.setUseClientMode(true);
/*     */   }
/*     */ 
/*     */   void loadKeyStore(KeyStore ks, KeyStore tks) throws HiException
/*     */   {
/* 216 */     InputStream is = null;
/*     */     try
/*     */     {
/*     */       CertificateFactory cf;
/*     */       Certificate cacert;
/* 219 */       if ((this.identityKS != null) && (this.identityKS.trim().length() != 0))
/*     */       {
/* 221 */         is = HiResource.getResourceAsStream(this.identityKS);
/* 222 */         if (is == null) {
/* 223 */           throw new HiException("213302", this.identityKS);
/*     */         }
/* 225 */         if (this.identityKS.endsWith(".keystore"))
/*     */         {
/* 228 */           ks.load(is, this.keyPsw.toCharArray());
/*     */         }
/*     */         else
/*     */         {
/* 233 */           ks.load(null, null);
/* 234 */           cf = CertificateFactory.getInstance("X.509");
/* 235 */           cacert = cf.generateCertificate(is);
/* 236 */           ks.setCertificateEntry("USER_CERT", cacert);
/*     */         }
/* 238 */         if (is != null)
/*     */         {
/* 240 */           is.close();
/*     */         }
/*     */       }
/* 243 */       if ((this.trustKS != null) && (this.trustKS.trim().length() != 0))
/*     */       {
/* 245 */         is = HiResource.getResourceAsStream(this.trustKS);
/* 246 */         if (is == null) {
/* 247 */           throw new HiException("213302", this.trustKS);
/*     */         }
/*     */ 
/* 250 */         if (this.trustKS.endsWith(".keystore"))
/*     */         {
/* 252 */           tks.load(is, this.keyPsw.toCharArray());
/*     */         }
/*     */         else
/*     */         {
/* 256 */           tks.load(null, null);
/* 257 */           cf = CertificateFactory.getInstance("X.509");
/* 258 */           cacert = cf.generateCertificate(is);
/* 259 */           tks.setCertificateEntry("ROOT_CACERT", cacert);
/*     */         }
/* 261 */         if (is != null)
/*     */         {
/* 263 */           is.close();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 269 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ }