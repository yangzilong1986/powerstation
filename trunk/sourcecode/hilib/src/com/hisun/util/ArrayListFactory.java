/*     */ package com.hisun.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ class ArrayListFactory
/*     */   implements PoolableObjectFactory
/*     */ {
/*     */   public void activateObject(Object arg0)
/*     */     throws Exception
/*     */   {
/* 144 */     ((ArrayList)arg0).clear();
/*     */   }
/*     */ 
/*     */   public void destroyObject(Object arg0) throws Exception
/*     */   {
/* 149 */     ((ArrayList)arg0).clear();
/*     */   }
/*     */ 
/*     */   public Object makeObject() throws Exception {
/* 153 */     return new ArrayList();
/*     */   }
/*     */ 
/*     */   public void passivateObject(Object arg0) throws Exception {
/* 157 */     ((ArrayList)arg0).clear();
/*     */   }
/*     */ 
/*     */   public boolean validateObject(Object arg0) {
/* 161 */     return true;
/*     */   }
/*     */ }