/*    */ package com.hisun.framework.parser;
/*    */ 
/*    */ import com.hisun.framework.HiFrameworkBuilder;
/*    */ import org.apache.commons.digester.AbstractObjectCreationFactory;
/*    */ import org.apache.commons.digester.Digester;
/*    */ import org.apache.hivemind.lib.BeanFactory;
/*    */ import org.xml.sax.Attributes;
/*    */ 
/*    */ public class HiBeanFactory extends AbstractObjectCreationFactory
/*    */ {
/*    */   public Object createObject(Attributes attributes)
/*    */     throws Exception
/*    */   {
/*    */     Object instance;
/* 20 */     String className = attributes.getValue("class");
/* 21 */     if (className != null)
/*    */     {
/* 23 */       Class clazz = this.digester.getClassLoader().loadClass(className);
/* 24 */       instance = clazz.newInstance();
/* 25 */       return instance;
/*    */     }
/*    */ 
/* 28 */     String alias = attributes.getValue("alias");
/* 29 */     if (alias != null) {
/* 30 */       instance = HiFrameworkBuilder.getCommFactory().get(alias);
/* 31 */       if (instance != null)
/* 32 */         return instance;
/*    */     }
/* 34 */     throw new Exception("create object failed!");
/*    */   }
/*    */ }