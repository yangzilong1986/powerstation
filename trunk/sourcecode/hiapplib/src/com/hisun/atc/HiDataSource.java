/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ class HiDataSource
/*     */ {
/*     */   public static Object get(HiMessageContext ctx, String name)
/*     */   {
/* 144 */     return ctx.getBaseSource(name);
/*     */   }
/*     */ 
/*     */   public static Object create(HiMessageContext ctx, String name, String type)
/*     */   {
/*     */     Object o;
/* 149 */     if ("ETF".equalsIgnoreCase(type))
/* 150 */       o = HiETFFactory.createETF();
/* 151 */     else if ("XML".equalsIgnoreCase(type))
/* 152 */       o = HiETFFactory.createETF();
/* 153 */     else if ("MSG".equalsIgnoreCase(type))
/* 154 */       o = new HiMessage();
/* 155 */     else if ("NAMED".equalsIgnoreCase(type))
/* 156 */       o = new ConcurrentHashMap();
/*     */     else {
/* 158 */       o = HiETFFactory.createETF();
/*     */     }
/*     */ 
/* 161 */     ctx.setBaseSource(name, o);
/* 162 */     return o;
/*     */   }
/*     */ 
/*     */   public static void delete(HiMessageContext ctx, String name) {
/* 166 */     ctx.removeBaseSource(name);
/*     */   }
/*     */ 
/*     */   public static void set(HiMessageContext ctx, String name, Object o) {
/* 170 */     ctx.setBaseSource(name, o);
/*     */   }
/*     */ 
/*     */   public static String getName(HiMessageContext ctx, Object o) {
/* 174 */     Iterator iter = ctx.getAllPropertyNames();
/* 175 */     while (iter.hasNext()) {
/* 176 */       String name = (String)iter.next();
/* 177 */       if (ctx.getBaseSource(name) == o) {
/* 178 */         return name;
/*     */       }
/*     */     }
/* 181 */     return null;
/*     */   }
/*     */ }