/*     */ package com.hisun.mon;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class HiRunTimeInfo
/*     */ {
/*     */   String msgId;
/*     */   String id;
/*     */   long elapseTm;
/*     */   long sysTm;
/*     */   String msgTyp;
/*     */   String msgCod;
/*     */   String msg;
/*     */   HashMap extMap;
/*     */ 
/*     */   public HiRunTimeInfo(String msgId, String id, int elapseTm, long sysTm, String msgTyp, String msgCod, String msg)
/*     */   {
/*  46 */     this.msgId = msgId;
/*  47 */     this.id = id;
/*  48 */     this.elapseTm = elapseTm;
/*  49 */     this.sysTm = sysTm;
/*  50 */     this.msgTyp = msgTyp;
/*  51 */     this.msgCod = msgCod;
/*  52 */     this.msg = msg;
/*     */   }
/*     */ 
/*     */   public HiRunTimeInfo(String msgId, String id, int elapseTm, long sysTm, String msgTyp, String msgCod, String msg, HashMap extMap)
/*     */   {
/*  57 */     this.msgId = msgId;
/*  58 */     this.id = id;
/*  59 */     this.elapseTm = elapseTm;
/*  60 */     this.sysTm = sysTm;
/*  61 */     this.msgTyp = msgTyp;
/*  62 */     this.msgCod = msgCod;
/*  63 */     this.msg = msg;
/*  64 */     this.extMap = extMap;
/*     */   }
/*     */ 
/*     */   public String getMsgId() {
/*  68 */     return this.msgId;
/*     */   }
/*     */ 
/*     */   public void setMsgId(String msgId) {
/*  72 */     this.msgId = msgId;
/*     */   }
/*     */ 
/*     */   public String getId() {
/*  76 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  80 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public long getElapseTm() {
/*  84 */     return this.elapseTm;
/*     */   }
/*     */ 
/*     */   public void setElapseTm(long elapseTm) {
/*  88 */     this.elapseTm = elapseTm;
/*     */   }
/*     */ 
/*     */   public long getSysTm() {
/*  92 */     return this.sysTm;
/*     */   }
/*     */ 
/*     */   public void setSysTm(long sysTm) {
/*  96 */     this.sysTm = sysTm;
/*     */   }
/*     */ 
/*     */   public String getMsgTyp() {
/* 100 */     return this.msgTyp;
/*     */   }
/*     */ 
/*     */   public void setMsgTyp(String msgTyp) {
/* 104 */     this.msgTyp = msgTyp;
/*     */   }
/*     */ 
/*     */   public String getMsgCod() {
/* 108 */     return this.msgCod;
/*     */   }
/*     */ 
/*     */   public void setMsgCod(String msgCod) {
/* 112 */     this.msgCod = msgCod;
/*     */   }
/*     */ 
/*     */   public String getMsg() {
/* 116 */     return this.msg;
/*     */   }
/*     */ 
/*     */   public void setMsg(String msg) {
/* 120 */     this.msg = msg;
/*     */   }
/*     */ 
/*     */   public HashMap getExtMap() {
/* 124 */     return this.extMap;
/*     */   }
/*     */ 
/*     */   public void setExtMap(HashMap extMap) {
/* 128 */     this.extMap = extMap;
/*     */   }
/*     */ }