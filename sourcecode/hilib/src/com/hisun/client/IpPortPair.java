/*     */ package com.hisun.client;
/*     */ 
/*     */ class IpPortPair
/*     */ {
/*     */   int port;
/*     */   String ip;
/* 159 */   int tmOut = 30;
/* 160 */   int sslMode = 0;
/* 161 */   String identityFile = "";
/* 162 */   String trustFile = "";
/* 163 */   String keyPsw = "";
/* 164 */   String trustPsw = "";
/* 165 */   boolean logSwitch = false;
/*     */ 
/* 169 */   boolean isSRNConn = false;
/*     */ 
/*     */   public IpPortPair() { }
/*     */ 
/*     */   public IpPortPair(String ip, int port) {
/* 174 */     this.ip = ip;
/* 175 */     this.port = port; }
/*     */ 
/*     */   public IpPortPair(String ip, int port, boolean isSRNConn) {
/* 178 */     this.ip = ip;
/* 179 */     this.port = port;
/* 180 */     this.isSRNConn = isSRNConn;
/*     */   }
/*     */ 
/*     */   public IpPortPair(String ip, int port, int sslMode, String identityFile, String trustFile, String keyPsw, String trustPsw) {
/* 184 */     this.ip = ip;
/* 185 */     this.port = port;
/* 186 */     this.sslMode = sslMode;
/* 187 */     this.identityFile = identityFile;
/* 188 */     this.trustFile = trustFile;
/* 189 */     this.keyPsw = keyPsw;
/* 190 */     this.trustPsw = trustPsw;
/*     */   }
/*     */ 
/*     */   public IpPortPair(String ip, int port, int sslMode, String identityFile, String trustFile, String keyPsw, String trustPsw, boolean isSRNConn) {
/* 194 */     this.ip = ip;
/* 195 */     this.port = port;
/* 196 */     this.sslMode = sslMode;
/* 197 */     this.identityFile = identityFile;
/* 198 */     this.trustFile = trustFile;
/* 199 */     this.keyPsw = keyPsw;
/* 200 */     this.trustPsw = trustPsw;
/* 201 */     this.isSRNConn = isSRNConn;
/*     */   }
/*     */ 
/*     */   public boolean isSRNConn() {
/* 205 */     return this.isSRNConn;
/*     */   }
/*     */ 
/*     */   public void setSRNConn(boolean isSRNConn) {
/* 209 */     this.isSRNConn = isSRNConn;
/*     */   }
/*     */ 
/*     */   public int getTmOut()
/*     */   {
/* 215 */     return this.tmOut;
/*     */   }
/*     */ 
/*     */   public void setTmOut(int tmOut)
/*     */   {
/* 221 */     this.tmOut = tmOut;
/*     */   }
/*     */ 
/*     */   public int getPort()
/*     */   {
/* 227 */     return this.port;
/*     */   }
/*     */ 
/*     */   public void setPort(int port)
/*     */   {
/* 233 */     this.port = port;
/*     */   }
/*     */ 
/*     */   public String getIp()
/*     */   {
/* 239 */     return this.ip;
/*     */   }
/*     */ 
/*     */   public void setIp(String ip)
/*     */   {
/* 245 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */   public int getSslMode()
/*     */   {
/* 251 */     return this.sslMode;
/*     */   }
/*     */ 
/*     */   public void setSslMode(int sslMode)
/*     */   {
/* 257 */     this.sslMode = sslMode;
/*     */   }
/*     */ 
/*     */   public String getIdentityFile()
/*     */   {
/* 263 */     return this.identityFile;
/*     */   }
/*     */ 
/*     */   public void setIdentityFile(String identityFile)
/*     */   {
/* 269 */     this.identityFile = identityFile;
/*     */   }
/*     */ 
/*     */   public String getTrustFile()
/*     */   {
/* 275 */     return this.trustFile;
/*     */   }
/*     */ 
/*     */   public void setTrustFile(String trustFile)
/*     */   {
/* 281 */     this.trustFile = trustFile;
/*     */   }
/*     */ 
/*     */   public String getKeyPsw()
/*     */   {
/* 287 */     return this.keyPsw;
/*     */   }
/*     */ 
/*     */   public void setKeyPsw(String keyPsw)
/*     */   {
/* 293 */     this.keyPsw = keyPsw;
/*     */   }
/*     */ 
/*     */   public String getTrustPsw()
/*     */   {
/* 299 */     return this.trustPsw;
/*     */   }
/*     */ 
/*     */   public void setTrustPsw(String trustPsw)
/*     */   {
/* 305 */     this.trustPsw = trustPsw;
/*     */   }
/*     */ 
/*     */   public boolean isLogSwitch()
/*     */   {
/* 311 */     return this.logSwitch;
/*     */   }
/*     */ 
/*     */   public void setLogSwitch(boolean logSwitch)
/*     */   {
/* 317 */     this.logSwitch = logSwitch;
/*     */   }
/*     */ }