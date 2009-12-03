/*     */ package com.hisun.database;
/*     */ 
/*     */ import org.hibernate.Session;
/*     */ 
/*     */ class HiSession
/*     */ {
/*     */   public String dsname;
/*     */   public Session session;
/*     */ 
/*     */   public HiSession(String daname, Session session)
/*     */   {
/* 340 */     this.dsname = daname;
/* 341 */     this.session = session;
/*     */   }
/*     */ }