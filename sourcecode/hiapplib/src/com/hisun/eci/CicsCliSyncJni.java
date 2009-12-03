/*     */ package com.hisun.eci;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class CicsCliSyncJni
/*     */ {
/*     */   private String systemName;
/*     */   private String userName;
/*     */   private String password;
/*     */   private int preLen;
/*     */   private String progId;
/*     */   private int timeOut;
/*     */ 
/*     */   private native int clientSyncOutJni(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
/*     */ 
/*     */   public String getSystemName()
/*     */   {
/*  25 */     return this.systemName; }
/*     */ 
/*     */   public void setSystemName(String systemName) {
/*  28 */     this.systemName = systemName; }
/*     */ 
/*     */   public String getUserName() {
/*  31 */     return this.userName; }
/*     */ 
/*     */   public void setUserName(String userName) {
/*  34 */     this.userName = userName; }
/*     */ 
/*     */   public String getPassword() {
/*  37 */     return this.password; }
/*     */ 
/*     */   public void setPassword(String password) {
/*  40 */     this.password = password; }
/*     */ 
/*     */   public int getPreLen() {
/*  43 */     return this.preLen; }
/*     */ 
/*     */   public void setPreLen(int preLen) {
/*  46 */     this.preLen = preLen; }
/*     */ 
/*     */   public String getProgId() {
/*  49 */     return this.progId; }
/*     */ 
/*     */   public void setProgId(String progId) {
/*  52 */     this.progId = progId;
/*     */   }
/*     */ 
/*     */   public int getTimeOut() {
/*  56 */     return this.timeOut; }
/*     */ 
/*     */   public void setTimeOut(int timeOut) {
/*  59 */     this.timeOut = timeOut;
/*     */   }
/*     */ 
/*     */   public void init(String systemName, String userName, String password, String progId, int timeOut) {
/*  63 */     this.systemName = systemName;
/*  64 */     this.userName = userName;
/*  65 */     this.password = password;
/*  66 */     this.progId = progId;
/*  67 */     this.timeOut = timeOut;
/*     */   }
/*     */ 
/*     */   public int clientSyncOut(byte[] sendData, byte[] recvBuffer) {
/*  71 */     return clientSyncOutJni(this.systemName, this.userName, this.password, this.progId, this.timeOut, sendData, recvBuffer);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  76 */     System.out.println("start");
/*  77 */     CicsCliSyncJni out = new CicsCliSyncJni();
/*  78 */     byte[] recv = new byte[10241];
/*     */ 
/*  82 */     out.init(args[0], args[1], args[2], args[3], 30);
/*  83 */     String data = "V6R000T 0  TLR  IBSFE   000TLU600                                     9994930        HDCPT03HDCPT03                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       0000                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   000032HDCPT03                  HDCPT03";
/*  84 */     int num = 1;
/*     */ 
/*  87 */     if (args.length >= 5)
/*     */     {
/*  89 */       num = Integer.parseInt(args[4]);
/*     */     }
/*  91 */     for (int i = 0; i < num; ++i)
/*     */     {
/*  93 */       System.out.println("Test Call CicsClient:" + i);
/*  94 */       long t = System.currentTimeMillis();
/*  95 */       int ret = out.clientSyncOut(data.getBytes(), recv);
/*  96 */       if (ret > 0)
/*     */       {
/*  98 */         System.out.println("Receive Data:[" + ret + "][" + new String(recv, 0, ret) + "]");
/*     */       }
/*     */       else
/*     */       {
/* 102 */         System.out.println("Call Failure:[" + ret + "][" + new String(recv) + "]");
/*     */       }
/* 104 */       System.out.println("Finish, Spent time:" + (System.currentTimeMillis() - t));
/*     */     }
/* 106 */     System.out.println("end");
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*   7 */     System.loadLibrary("cicsclisync");
/*     */   }
/*     */ }