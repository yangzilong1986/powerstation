/*    */ package com.hisun.template;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class TemplateFactory
/*    */ {
/* 12 */   private static TemplateFactory instance = new TemplateFactory();
/*    */   private Map objectMap;
/*    */ 
/*    */   public TemplateFactory()
/*    */   {
/* 17 */     this.objectMap = new HashMap();
/* 18 */     synchronized (this) {
/* 19 */       this.objectMap.put("freemarker", new FreemarkerTemplateEngine());
/* 20 */       this.objectMap.put("velocity", new VelocityTemplateEngine());
/*    */     }
/*    */   }
/*    */ 
/*    */   public static TemplateFactory getInstance() {
/* 25 */     return instance;
/*    */   }
/*    */ 
/*    */   public Object getBean(String beanName)
/*    */   {
/* 34 */     return this.objectMap.get(beanName);
/*    */   }
/*    */ }