 package com.hisun.eci;
 
 import java.io.PrintStream;
 
 public class CicsCliSyncJni
 {
   private String systemName;
   private String userName;
   private String password;
   private int preLen;
   private String progId;
   private int timeOut;
 
   private native int clientSyncOutJni(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
 
   public String getSystemName()
   {
     return this.systemName; }
 
   public void setSystemName(String systemName) {
     this.systemName = systemName; }
 
   public String getUserName() {
     return this.userName; }
 
   public void setUserName(String userName) {
     this.userName = userName; }
 
   public String getPassword() {
     return this.password; }
 
   public void setPassword(String password) {
     this.password = password; }
 
   public int getPreLen() {
     return this.preLen; }
 
   public void setPreLen(int preLen) {
     this.preLen = preLen; }
 
   public String getProgId() {
     return this.progId; }
 
   public void setProgId(String progId) {
     this.progId = progId;
   }
 
   public int getTimeOut() {
     return this.timeOut; }
 
   public void setTimeOut(int timeOut) {
     this.timeOut = timeOut;
   }
 
   public void init(String systemName, String userName, String password, String progId, int timeOut) {
     this.systemName = systemName;
     this.userName = userName;
     this.password = password;
     this.progId = progId;
     this.timeOut = timeOut;
   }
 
   public int clientSyncOut(byte[] sendData, byte[] recvBuffer) {
     return clientSyncOutJni(this.systemName, this.userName, this.password, this.progId, this.timeOut, sendData, recvBuffer);
   }
 
   public static void main(String[] args)
   {
     System.out.println("start");
     CicsCliSyncJni out = new CicsCliSyncJni();
     byte[] recv = new byte[10241];
 
     out.init(args[0], args[1], args[2], args[3], 30);
     String data = "V6R000T 0  TLR  IBSFE   000TLU600                                     9994930        HDCPT03HDCPT03                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       0000                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   000032HDCPT03                  HDCPT03";
     int num = 1;
 
     if (args.length >= 5)
     {
       num = Integer.parseInt(args[4]);
     }
     for (int i = 0; i < num; ++i)
     {
       System.out.println("Test Call CicsClient:" + i);
       long t = System.currentTimeMillis();
       int ret = out.clientSyncOut(data.getBytes(), recv);
       if (ret > 0)
       {
         System.out.println("Receive Data:[" + ret + "][" + new String(recv, 0, ret) + "]");
       }
       else
       {
         System.out.println("Call Failure:[" + ret + "][" + new String(recv) + "]");
       }
       System.out.println("Finish, Spent time:" + (System.currentTimeMillis() - t));
     }
     System.out.println("end");
   }
 
   static
   {
     System.loadLibrary("cicsclisync");
   }
 }