/*    */ package com.hisun.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class PropertyMessageResourcesFactory extends MessageResourcesFactory
/*    */ {
/*    */   private HashMap messageResourceCache;
/*    */ 
/*    */   public PropertyMessageResourcesFactory()
/*    */   {
/* 33 */     this.messageResourceCache = new HashMap();
/*    */   }
/*    */ 
/*    */   public MessageResources createResources(String config)
/*    */   {
/* 44 */     if (!(this.messageResourceCache.containsKey(config))) {
/* 45 */       PropertyMessageResources messageResources = new PropertyMessageResources(this, config, this.returnNull);
/*    */ 
/* 51 */       messageResources.setCheckInterval(HiICSProperty.getInt("sys.checkinterval", 10));
/* 52 */       this.messageResourceCache.put(config, messageResources);
/*    */     }
/* 54 */     return ((MessageResources)this.messageResourceCache.get(config));
/*    */   }
/*    */ }