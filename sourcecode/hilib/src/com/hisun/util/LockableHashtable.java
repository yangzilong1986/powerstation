/*     */ package com.hisun.util;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class LockableHashtable extends HashMap
/*     */ {
/*     */   Vector lockedEntries;
/*  48 */   private HashMap parent = null;
/*     */ 
/*     */   public LockableHashtable()
/*     */   {
/*     */   }
/*     */ 
/*     */   public LockableHashtable(int p1, float p2) {
/*  55 */     super(p1, p2);
/*     */   }
/*     */ 
/*     */   public LockableHashtable(Map p1) {
/*  59 */     super(p1);
/*     */   }
/*     */ 
/*     */   public LockableHashtable(int p1) {
/*  63 */     super(p1);
/*     */   }
/*     */ 
/*     */   public void setParent(HashMap parent)
/*     */   {
/*  72 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   public HashMap getParent()
/*     */   {
/*  80 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public Set getAllKeys()
/*     */   {
/*  87 */     HashSet set = new HashSet();
/*  88 */     set.addAll(super.keySet());
/*  89 */     HashMap p = this.parent;
/*  90 */     while (p != null) {
/*  91 */       set.addAll(p.keySet());
/*  92 */       if (p instanceof LockableHashtable) {
/*  93 */         p = ((LockableHashtable)p).getParent();
/*     */       }
/*  95 */       p = null;
/*     */     }
/*     */ 
/*  98 */     return set;
/*     */   }
/*     */ 
/*     */   public Object get(Object key)
/*     */   {
/* 107 */     Object ret = super.get(key);
/* 108 */     if ((ret == null) && (this.parent != null)) {
/* 109 */       ret = this.parent.get(key);
/*     */     }
/* 111 */     return ret;
/*     */   }
/*     */ 
/*     */   public Object put(Object p1, Object p2, boolean locked)
/*     */   {
/* 119 */     if ((this.lockedEntries != null) && (containsKey(p1)) && (this.lockedEntries.contains(p1)))
/*     */     {
/* 122 */       return null;
/*     */     }
/* 124 */     if (locked) {
/* 125 */       if (this.lockedEntries == null) {
/* 126 */         this.lockedEntries = new Vector();
/*     */       }
/* 128 */       this.lockedEntries.add(p1);
/*     */     }
/* 130 */     return super.put(p1, p2);
/*     */   }
/*     */ 
/*     */   public Object put(Object p1, Object p2)
/*     */   {
/* 138 */     return put(p1, p2, false);
/*     */   }
/*     */ 
/*     */   public Object remove(Object p1)
/*     */   {
/* 146 */     if ((this.lockedEntries != null) && (this.lockedEntries.contains(p1))) {
/* 147 */       return null;
/*     */     }
/* 149 */     return super.remove(p1);
/*     */   }
/*     */ 
/*     */   public boolean isKeyLocked(Object key)
/*     */   {
/* 157 */     return ((this.lockedEntries != null) && (this.lockedEntries.contains(key)));
/*     */   }
/*     */ }