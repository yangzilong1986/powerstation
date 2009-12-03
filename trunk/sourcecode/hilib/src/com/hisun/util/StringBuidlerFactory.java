/*     */ package com.hisun.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ class StringBuidlerFactory
/*     */   implements PoolableObjectFactory
/*     */ {
/*     */   public void activateObject(Object arg0)
/*     */     throws Exception
/*     */   {
/*  88 */     clear((StringBuilder)arg0);
/*     */   }
/*     */ 
/*     */   public void destroyObject(Object arg0) throws Exception {
/*  92 */     clear((StringBuilder)arg0);
/*     */   }
/*     */ 
/*     */   public Object makeObject() throws Exception {
/*  96 */     System.out.println("makeObject");
/*  97 */     return new StringBuilder();
/*     */   }
/*     */ 
/*     */   public void passivateObject(Object arg0) throws Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean validateObject(Object arg0) {
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   public void clear(StringBuilder arg0) {
/* 109 */     arg0.setLength(0);
/*     */   }
/*     */ }