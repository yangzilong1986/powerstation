/*    */ package com.hisun.framework.parser;
/*    */ 
/*    */ import com.hisun.framework.HiDefaultServer;
/*    */ import com.hisun.framework.HiFrameworkBuilder;
/*    */ import com.hisun.service.IObjectDecorator;
/*    */ import org.apache.commons.digester.AbstractObjectCreationFactory;
/*    */ import org.xml.sax.Attributes;
/*    */ 
/*    */ public class HiServerFactory extends AbstractObjectCreationFactory
/*    */ {
/*    */   public Object createObject(Attributes attributes)
/*    */     throws Exception
/*    */   {
/* 16 */     HiDefaultServer server = new HiDefaultServer();
/*    */ 
/* 19 */     server.setName(attributes.getValue("name"));
/* 20 */     server.setType(attributes.getValue("type"));
/* 21 */     String trace = attributes.getValue("trace");
/* 22 */     if (trace != null) {
/* 23 */       server.setTrace(trace);
/*    */     }
/*    */ 
/* 27 */     server.startBuild();
/*    */ 
/* 29 */     return HiFrameworkBuilder.getObjectDecorator().decorate(server, "addDeclare");
/*    */   }
/*    */ }