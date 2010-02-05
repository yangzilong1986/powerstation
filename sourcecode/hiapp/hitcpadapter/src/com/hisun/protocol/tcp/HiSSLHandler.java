 package com.hisun.protocol.tcp;
 
 import com.hisun.exception.HiException;
 import com.hisun.util.HiResource;
 import java.io.InputStream;
 import java.net.ServerSocket;
 import java.net.Socket;
 import java.security.KeyStore;
 import java.security.cert.Certificate;
 import java.security.cert.CertificateFactory;
 import javax.net.ssl.KeyManagerFactory;
 import javax.net.ssl.SSLContext;
 import javax.net.ssl.SSLServerSocket;
 import javax.net.ssl.SSLServerSocketFactory;
 import javax.net.ssl.SSLSocket;
 import javax.net.ssl.SSLSocketFactory;
 import javax.net.ssl.TrustManagerFactory;
 
 public class HiSSLHandler
 {
   static final String KEYSTORE_SUF = ".keystore";
   SSLContext sslContext;
   private int sslMode;
   private int methType;
   private String certPath;
   private String identityKS;
   private String keyPsw;
   private String alg;
   private String trustKS;
   private int authFlag;
 
   public HiSSLHandler()
   {
     this.sslMode = 0;
 
     this.methType = 1;
 
     this.authFlag = 0; }
 
   public int getSslMode() {
     return this.sslMode;
   }
 
   public void setSslMode(int sslMode) {
     this.sslMode = sslMode;
   }
 
   public int getMethType() {
     return this.methType;
   }
 
   public void setMethType(int methType) {
     this.methType = methType;
   }
 
   public String getCertPath() {
     return this.certPath;
   }
 
   public void TrustKS(String certPath) {
     this.certPath = certPath;
   }
 
   public String getIdentityKS() {
     return this.identityKS;
   }
 
   public void setIdentityKS(String identityKS) {
     this.identityKS = identityKS;
   }
 
   public String getKeyPsw() {
     return this.keyPsw;
   }
 
   public void setKeyPsw(String keyPsw) {
     this.keyPsw = keyPsw;
   }
 
   public String getAlg() {
     return this.alg;
   }
 
   public void setAlg(String alg) {
     this.alg = alg;
   }
 
   public String getTrustKS() {
     return this.trustKS;
   }
 
   public void setTrustKS(String trustKS) {
     this.trustKS = trustKS;
   }
 
   public int getAuthFlag() {
     return this.authFlag;
   }
 
   public void setAuthFlag(int authFlag) {
     this.authFlag = authFlag;
   }
 
   public boolean isSSLMode()
   {
     return (this.sslMode == 0);
   }
 
   public static HiSSLHandler getInstance()
   {
     return new HiSSLHandler();
   }
 
   public void init() throws HiException
   {
     if (!(isSSLMode()))
       return;
     try
     {
       this.sslContext = SSLContext.getInstance("SSLv3");
       KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
       TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
 
       KeyStore ks = KeyStore.getInstance("JKS");
       KeyStore tks = KeyStore.getInstance("JKS");
 
       loadKeyStore(ks, tks);
 
       kmf.init(ks, this.keyPsw.toCharArray());
       tmf.init(tks);
 
       this.sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
     }
     catch (Exception e) {
       throw HiException.makeException(e);
     }
   }
 
   public SSLServerSocketFactory getServerSocketFactory()
   {
     if (this.sslContext != null)
     {
       return this.sslContext.getServerSocketFactory();
     }
     return null;
   }
 
   public SSLSocketFactory getSocketFactory()
   {
     if (this.sslContext != null)
     {
       return this.sslContext.getSocketFactory();
     }
     return null;
   }
 
   public void initServerSocket(ServerSocket serverSocket)
   {
     SSLServerSocket sslSS = (SSLServerSocket)serverSocket;
 
     if (this.authFlag == 0)
     {
       sslSS.setNeedClientAuth(true);
     }
     else
     {
       sslSS.setNeedClientAuth(false);
     }
   }
 
   public void initClientSocket(Socket socket)
   {
     SSLSocket ss = (SSLSocket)socket;
 
     ss.setUseClientMode(true);
   }
 
   void loadKeyStore(KeyStore ks, KeyStore tks) throws HiException
   {
     InputStream is = null;
     try
     {
       CertificateFactory cf;
       Certificate cacert;
       if ((this.identityKS != null) && (this.identityKS.trim().length() != 0))
       {
         is = HiResource.getResourceAsStream(this.identityKS);
         if (is == null) {
           throw new HiException("213302", this.identityKS);
         }
         if (this.identityKS.endsWith(".keystore"))
         {
           ks.load(is, this.keyPsw.toCharArray());
         }
         else
         {
           ks.load(null, null);
           cf = CertificateFactory.getInstance("X.509");
           cacert = cf.generateCertificate(is);
           ks.setCertificateEntry("USER_CERT", cacert);
         }
         if (is != null)
         {
           is.close();
         }
       }
       if ((this.trustKS != null) && (this.trustKS.trim().length() != 0))
       {
         is = HiResource.getResourceAsStream(this.trustKS);
         if (is == null) {
           throw new HiException("213302", this.trustKS);
         }
 
         if (this.trustKS.endsWith(".keystore"))
         {
           tks.load(is, this.keyPsw.toCharArray());
         }
         else
         {
           tks.load(null, null);
           cf = CertificateFactory.getInstance("X.509");
           cacert = cf.generateCertificate(is);
           tks.setCertificateEntry("ROOT_CACERT", cacert);
         }
         if (is != null)
         {
           is.close();
         }
       }
     }
     catch (Exception e)
     {
       throw new HiException(e);
     }
   }
 }