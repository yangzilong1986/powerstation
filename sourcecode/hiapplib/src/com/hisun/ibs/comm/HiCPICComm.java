/*     */ package com.hisun.ibs.comm;
/*     */ 
/*     */ import COM.ibm.eNetwork.cpic.CPIC;
/*     */ import COM.ibm.eNetwork.cpic.CPICControlInformationReceived;
/*     */ import COM.ibm.eNetwork.cpic.CPICConversationState;
/*     */ import COM.ibm.eNetwork.cpic.CPICDataReceivedType;
/*     */ import COM.ibm.eNetwork.cpic.CPICDeallocateType;
/*     */ import COM.ibm.eNetwork.cpic.CPICLength;
/*     */ import COM.ibm.eNetwork.cpic.CPICReceiveType;
/*     */ import COM.ibm.eNetwork.cpic.CPICReturnCode;
/*     */ import COM.ibm.eNetwork.cpic.CPICStatusReceived;
/*     */ import COM.ibm.eNetwork.cpic.CPICSyncLevel;
/*     */ 
/*     */ public class HiCPICComm
/*     */ {
/*     */   private String _destName;
/*     */   private String _tpName;
/*     */   private byte[] conversationId;
/*     */   private CPIC _cpic;
/*     */   private boolean _confirm;
/*     */   private int _flag;
/*     */ 
/*     */   public HiCPICComm()
/*     */   {
/*  46 */     this.conversationId = new byte[8];
/*  47 */     this._cpic = new CPIC();
/*  48 */     this._confirm = false;
/*  49 */     this._flag = 0;
/*     */   }
/*     */ 
/*     */   public int appcComm(String destName, String tpName, byte[] sendData, int sendLen, byte[] recvBuf)
/*     */     throws CPICReturnCode
/*     */   {
/*  62 */     this._destName = destName;
/*  63 */     this._tpName = tpName;
/*  64 */     this._flag = -1;
/*     */ 
/*  66 */     appcInit();
/*     */ 
/*  68 */     appcAllocate();
/*     */ 
/*  70 */     this._flag = -2;
/*  71 */     appcSend(sendData, sendLen);
/*  72 */     this._flag = -3;
/*     */ 
/*  74 */     int len = appcRecv(recvBuf);
/*  75 */     if (this._confirm) {
/*  76 */       appcConfirm();
/*     */     }
/*  78 */     appcEnd();
/*  79 */     return len;
/*     */   }
/*     */ 
/*     */   public int appcComm(String destName, String tpName, byte[] sendData, byte[] recvBuf)
/*     */     throws CPICReturnCode
/*     */   {
/*  92 */     this._destName = destName;
/*  93 */     this._tpName = tpName;
/*  94 */     this._flag = 0;
/*     */ 
/*  96 */     appcInit();
/*     */ 
/*  98 */     appcAllocate();
/*     */ 
/* 100 */     this._flag = -1;
/* 101 */     appcSend(sendData);
/* 102 */     this._flag = -2;
/*     */ 
/* 104 */     int len = appcRecv(recvBuf);
/* 105 */     if (this._confirm) {
/* 106 */       appcConfirm();
/*     */     }
/* 108 */     appcEnd();
/* 109 */     return len;
/*     */   }
/*     */ 
/*     */   private void appcInit()
/*     */     throws CPICReturnCode
/*     */   {
/* 116 */     CPICReturnCode cmRetCode = new CPICReturnCode(0);
/*     */ 
/* 118 */     this._cpic.cminit(this.conversationId, this._destName, cmRetCode);
/* 119 */     if (!(cmRetCode.equals(0))) {
/* 120 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */     }
/*     */ 
/* 123 */     CPICConversationState cmState = new CPICConversationState();
/* 124 */     this._cpic.cmecs(this.conversationId, cmState, cmRetCode);
/* 125 */     if (!(cmRetCode.equals(0))) {
/* 126 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */     }
/* 128 */     if (!(cmState.equals(2))) {
/* 129 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */     }
/*     */ 
/* 133 */     if (this._tpName != null)
/*     */     {
/* 135 */       CPICLength length = new CPICLength(this._tpName.length());
/* 136 */       this._cpic.cmstpn(this.conversationId, this._tpName.getBytes(), length, cmRetCode);
/* 137 */       if (!(cmRetCode.equals(0))) {
/* 138 */         throw new CPICReturnCode(cmRetCode.intValue());
/*     */       }
/*     */     }
/*     */ 
/* 142 */     CPICSyncLevel cmSyncLevel = new CPICSyncLevel(1);
/* 143 */     this._cpic.cmssl(this.conversationId, cmSyncLevel, cmRetCode);
/* 144 */     if (!(cmRetCode.equals(0))) {
/* 145 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */     }
/*     */ 
/* 148 */     CPICDeallocateType cmDealType = new CPICDeallocateType(3);
/*     */ 
/* 150 */     this._cpic.cmsdt(this.conversationId, cmDealType, cmRetCode);
/* 151 */     if (!(cmRetCode.equals(0)))
/* 152 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */   }
/*     */ 
/*     */   private void appcAllocate()
/*     */     throws CPICReturnCode
/*     */   {
/* 160 */     CPICReturnCode cmRetCode = new CPICReturnCode(0);
/* 161 */     CPICConversationState cmState = new CPICConversationState();
/*     */ 
/* 163 */     this._cpic.cmallc(this.conversationId, cmRetCode);
/* 164 */     if (!(cmRetCode.equals(0))) {
/* 165 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */     }
/*     */ 
/* 168 */     this._cpic.cmecs(this.conversationId, cmState, cmRetCode);
/* 169 */     if (!(cmRetCode.equals(0))) {
/* 170 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */     }
/* 172 */     if (!(cmState.equals(3)))
/* 173 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */   }
/*     */ 
/*     */   private void appcSend(byte[] data, int len)
/*     */     throws CPICReturnCode
/*     */   {
/* 183 */     CPICReturnCode cmRetCode = new CPICReturnCode(0);
/* 184 */     CPICLength length = new CPICLength(len);
/* 185 */     CPICControlInformationReceived cmRequestToSendReceived = new CPICControlInformationReceived();
/* 186 */     this._cpic.cmsend(this.conversationId, data, length, cmRequestToSendReceived, cmRetCode);
/*     */ 
/* 188 */     if (!(cmRetCode.equals(0)))
/* 189 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */   }
/*     */ 
/*     */   private void appcSend(byte[] data)
/*     */     throws CPICReturnCode
/*     */   {
/* 197 */     appcSend(data, data.length);
/*     */   }
/*     */ 
/*     */   private int appcRecv(byte[] buffer)
/*     */     throws CPICReturnCode
/*     */   {
/* 207 */     CPICReturnCode cmRetCode = new CPICReturnCode(0);
/*     */ 
/* 210 */     this._cpic.cmptr(this.conversationId, cmRetCode);
/* 211 */     if (!(cmRetCode.equals(0))) {
/* 212 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */     }
/*     */ 
/* 215 */     CPICConversationState cmState = new CPICConversationState();
/* 216 */     this._cpic.cmecs(this.conversationId, cmState, cmRetCode);
/* 217 */     if (!(cmRetCode.equals(0))) {
/* 218 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */     }
/* 220 */     if (!(cmState.equals(4))) {
/* 221 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */     }
/*     */ 
/* 233 */     CPICReceiveType cmRecvType = new CPICReceiveType(0);
/*     */ 
/* 235 */     this._cpic.cmsrt(this.conversationId, cmRecvType, cmRetCode);
/* 236 */     if (!(cmRetCode.equals(0))) {
/* 237 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */     }
/*     */ 
/* 240 */     CPICDataReceivedType cmDataRecvType = new CPICDataReceivedType();
/* 241 */     CPICStatusReceived cmStatusRecv = new CPICStatusReceived();
/* 242 */     CPICControlInformationReceived cmRequestToSendReceived = new CPICControlInformationReceived();
/* 243 */     CPICLength cmRequestLen = new CPICLength(buffer.length);
/* 244 */     CPICLength cmRecvLen = new CPICLength();
/*     */ 
/* 246 */     this._cpic.cmrcv(this.conversationId, buffer, cmRequestLen, cmDataRecvType, cmRecvLen, cmStatusRecv, cmRequestToSendReceived, cmRetCode);
/*     */ 
/* 248 */     if ((!(cmRetCode.equals(0))) && (!(cmRetCode.equals(18))))
/*     */     {
/* 250 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */     }
/* 252 */     if ((cmStatusRecv.equals(4)) || (cmStatusRecv.equals(3)))
/*     */     {
/* 256 */       this._confirm = true;
/*     */     }
/* 258 */     return cmRecvLen.intValue();
/*     */   }
/*     */ 
/*     */   private void appcConfirm()
/*     */     throws CPICReturnCode
/*     */   {
/* 266 */     CPICReturnCode cmRetCode = new CPICReturnCode(0);
/* 267 */     this._cpic.cmcfmd(this.conversationId, cmRetCode);
/* 268 */     if (!(cmRetCode.equals(0)))
/* 269 */       throw new CPICReturnCode(cmRetCode.intValue());
/*     */   }
/*     */ 
/*     */   private void appcEnd()
/*     */     throws CPICReturnCode
/*     */   {
/* 277 */     CPICReturnCode cmRetCode = new CPICReturnCode(0);
/* 278 */     this._cpic.cmdeal(this.conversationId, cmRetCode);
/*     */   }
/*     */ 
/*     */   public int getFlag()
/*     */   {
/* 300 */     return this._flag;
/*     */   }
/*     */ 
/*     */   public void setFlag(int flag) {
/* 304 */     this._flag = flag;
/*     */   }
/*     */ }