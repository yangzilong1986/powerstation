/*     */ package com.hisun.mon;
/*     */ 
/*     */ public class HiMonitorEventInfo
/*     */ {
/*  10 */   private String id = "";
/*     */ 
/*  18 */   private String type = "1";
/*     */ 
/*  23 */   private String subType = "";
/*     */ 
/*  32 */   private String level = "INFO";
/*     */ 
/*  37 */   private String msg = "";
/*     */ 
/*  42 */   private String extMsg = "";
/*     */ 
/*  47 */   private String origin = "";
/*     */   private long time;
/*  57 */   private String sip = "";
/*     */ 
/*     */   public HiMonitorEventInfo() { this.time = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   public HiMonitorEventInfo(String id, String msg) {
/*  63 */     id = this.id;
/*  64 */     msg = this.msg;
/*  65 */     this.time = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   public String getId() {
/*  69 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  73 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getType() {
/*  77 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/*  81 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getSubType() {
/*  85 */     return this.subType;
/*     */   }
/*     */ 
/*     */   public void setSubType(String subType) {
/*  89 */     this.subType = subType;
/*     */   }
/*     */ 
/*     */   public String getLevel() {
/*  93 */     return this.level;
/*     */   }
/*     */ 
/*     */   public void setLevel(String level) {
/*  97 */     this.level = level;
/*     */   }
/*     */ 
/*     */   public String getMsg() {
/* 101 */     return this.msg;
/*     */   }
/*     */ 
/*     */   public void setMsg(String msg) {
/* 105 */     this.msg = msg;
/*     */   }
/*     */ 
/*     */   public String getExtMsg() {
/* 109 */     return this.extMsg;
/*     */   }
/*     */ 
/*     */   public void setExtMsg(String extMsg) {
/* 113 */     this.extMsg = extMsg;
/*     */   }
/*     */ 
/*     */   public String getOrigin() {
/* 117 */     return this.origin;
/*     */   }
/*     */ 
/*     */   public void setOrigin(String origin) {
/* 121 */     this.origin = origin;
/*     */   }
/*     */ 
/*     */   public long getTime()
/*     */   {
/* 126 */     return this.time;
/*     */   }
/*     */ 
/*     */   public void setTime(long time) {
/* 130 */     this.time = time;
/*     */   }
/*     */ 
/*     */   public String getSip() {
/* 134 */     return this.sip;
/*     */   }
/*     */ 
/*     */   public void setSip(String sip) {
/* 138 */     this.sip = sip;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 142 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 144 */     sb.append("EventInfo=>ID[");
/* 145 */     sb.append(getId());
/* 146 */     sb.append("]");
/* 147 */     sb.append("TYPE[");
/* 148 */     sb.append(getType());
/* 149 */     sb.append("]");
/* 150 */     sb.append("LEVEL[");
/* 151 */     sb.append(getLevel());
/* 152 */     sb.append("]");
/* 153 */     sb.append("MSG[");
/* 154 */     sb.append(getMsg());
/* 155 */     sb.append("]");
/* 156 */     sb.append("Time[");
/* 157 */     sb.append(getTime());
/* 158 */     sb.append("]");
/*     */ 
/* 160 */     return sb.toString(); }
/*     */ 
/*     */   public void send() {
/* 163 */     HiMonitorEventInfoPool monitorEventInfoPool = HiMonitorEventInfoPool.getInstance();
/* 164 */     monitorEventInfoPool.addEvent(this);
/*     */   }
/*     */ }