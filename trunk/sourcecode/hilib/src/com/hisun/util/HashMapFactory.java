/*     */ package com.hisun.util;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ class HashMapFactory
/*     */   implements PoolableObjectFactory
/*     */ {
/*     */   public void activateObject(Object arg0)
/*     */     throws Exception
/*     */   {
/* 117 */     clear((HashMap)arg0);
/*     */   }
/*     */ 
/*     */   public void destroyObject(Object arg0) throws Exception
/*     */   {
/* 122 */     clear((HashMap)arg0);
/*     */   }
/*     */ 
/*     */   public Object makeObject() throws Exception {
/* 126 */     return new HashMap();
/*     */   }
/*     */ 
/*     */   public void passivateObject(Object arg0) throws Exception {
/*     */   }
/*     */ 
/*     */   public boolean validateObject(Object arg0) {
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   public void clear(HashMap arg0) {
/* 137 */     arg0.clear();
/*     */   }
/*     */ }