/*     */ package com.hisun.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.lang.BooleanUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiConfigInfo
/*     */ {
/*     */   private String msgType;
/*     */   private String serverName;
/*     */   private String logFile;
/*     */   private String mode;
/*     */   private ArrayList<IpPortPair> ipPortPairs;
/*     */   private int idx;
/*     */ 
/*     */   public HiConfigInfo()
/*     */   {
/*  23 */     this.msgType = "PLTIN0";
/*  24 */     this.serverName = "CAPPAPI1";
/*     */ 
/*  27 */     this.ipPortPairs = new ArrayList();
/*  28 */     this.idx = 0; }
/*     */ 
/*     */   public String getLogFile() { return this.logFile;
/*     */   }
/*     */ 
/*     */   public void SetLogFile(String logFile) {
/*  34 */     this.logFile = logFile;
/*     */   }
/*     */ 
/*     */   public void load(String file) throws IllegalAccessException, DocumentException, IOException {
/*  38 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/*  39 */     InputStream is = loader.getResourceAsStream(file);
/*  40 */     if (is == null)
/*  41 */       throw new RuntimeException(file + " not exists");
/*     */     try
/*     */     {
/*  44 */       load(is);
/*     */     } finally {
/*  46 */       is.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void load(InputStream is) throws IllegalAccessException, DocumentException {
/*  51 */     SAXReader reader = new SAXReader();
/*  52 */     Document doc = reader.read(is);
/*  53 */     Element root = doc.getRootElement();
/*  54 */     this.msgType = root.attributeValue("msgType");
/*  55 */     if (StringUtils.isBlank(this.msgType)) {
/*  56 */       this.msgType = "PLTIN0";
/*     */     }
/*  58 */     this.serverName = root.attributeValue("serverName");
/*  59 */     if (StringUtils.isBlank(this.serverName)) {
/*  60 */       this.serverName = "CAPPAPI1";
/*     */     }
/*  62 */     this.logFile = root.attributeValue("logFile");
/*  63 */     if (StringUtils.isBlank(this.logFile)) {
/*  64 */       this.logFile = "invokeservice.trc";
/*     */     }
/*     */ 
/*  67 */     this.mode = root.attributeValue("mode");
/*  68 */     if (StringUtils.isBlank(this.mode)) {
/*  69 */       this.mode = "POJO";
/*     */     }
/*     */ 
/*  73 */     Iterator iter = root.elementIterator("Item");
/*  74 */     while (iter.hasNext())
/*     */     {
/*     */       String tmp;
/*  76 */       Element element = (Element)iter.next();
/*  77 */       IpPortPair ipPortPair = new IpPortPair();
/*  78 */       if ((tmp = element.attributeValue("ip")) != null) {
/*  79 */         ipPortPair.setIp(tmp);
/*     */       }
/*     */ 
/*  82 */       if (StringUtils.isBlank(ipPortPair.getIp())) {
/*  83 */         throw new RuntimeException("ip is empty");
/*     */       }
/*     */ 
/*  86 */       if ((tmp = element.attributeValue("port")) != null) {
/*  87 */         ipPortPair.setPort(NumberUtils.toInt(tmp));
/*     */       }
/*     */ 
/*  90 */       if (ipPortPair.getPort() <= 0) {
/*  91 */         throw new IllegalAccessException("port is not number");
/*     */       }
/*     */ 
/*  94 */       if ((tmp = element.attributeValue("isSRNConn")) != null) {
/*  95 */         ipPortPair.setSRNConn(BooleanUtils.toBoolean(tmp));
/*     */       }
/*     */ 
/*  98 */       if ((tmp = element.attributeValue("tmOut")) != null) {
/*  99 */         ipPortPair.setTmOut(NumberUtils.toInt(tmp));
/*     */       }
/*     */ 
/* 102 */       if ((tmp = element.attributeValue("logSwitch")) != null) {
/* 103 */         ipPortPair.setLogSwitch(BooleanUtils.toBoolean(tmp));
/*     */       }
/*     */ 
/* 106 */       int sslMode = NumberUtils.toInt(element.attributeValue("sslMode"));
/* 107 */       if (sslMode == 1) {
/* 108 */         ipPortPair.setIdentityFile(element.attributeValue("identityFile"));
/* 109 */         ipPortPair.setTrustFile(element.attributeValue("trustFile"));
/* 110 */         ipPortPair.setKeyPsw(element.attributeValue("keyPsw"));
/* 111 */         ipPortPair.setTrustPsw(element.attributeValue("trustPsw"));
/*     */       }
/*     */ 
/* 114 */       this.ipPortPairs.add(ipPortPair);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getMsgType() {
/* 119 */     return this.msgType;
/*     */   }
/*     */ 
/*     */   public void setMsgType(String msgType) {
/* 123 */     this.msgType = msgType; }
/*     */ 
/*     */   public IpPortPair getIpPortPair() {
/* 126 */     if (this.idx >= this.ipPortPairs.size())
/* 127 */       this.idx = 0;
/* 128 */     IpPortPair ipPortPair = (IpPortPair)this.ipPortPairs.get(this.idx);
/* 129 */     this.idx += 1;
/* 130 */     return ipPortPair;
/*     */   }
/*     */ 
/*     */   public String getServerName() {
/* 134 */     return this.serverName;
/*     */   }
/*     */ 
/*     */   public void setServerName(String serverName) {
/* 138 */     this.serverName = serverName;
/*     */   }
/*     */ 
/*     */   public String getMode()
/*     */   {
/* 145 */     return this.mode;
/*     */   }
/*     */ 
/*     */   public void setMode(String mode)
/*     */   {
/* 152 */     this.mode = mode;
/*     */   }
/*     */ }