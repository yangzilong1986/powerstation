/*    */ package com.hisun.pipeline;
/*    */ 
/*    */ import org.apache.hivemind.HiveMind;
/*    */ import org.apache.hivemind.Location;
/*    */ import org.apache.hivemind.impl.MessageFormatter;
/*    */ import org.apache.hivemind.service.MethodSignature;
/*    */ 
/*    */ class PipelineMessages
/*    */ {
/* 29 */   protected static MessageFormatter _formatter = new MessageFormatter(PipelineMessages.class);
/*    */ 
/*    */   static String bridgeInstanceDescription(String serviceId, Class serviceInterface)
/*    */   {
/* 33 */     return _formatter.format("bridge-instance-description", serviceId, serviceInterface.getName());
/*    */   }
/*    */ 
/*    */   static String extraFilterMethod(MethodSignature ms, Class filterInterface, Class serviceInterface, String serviceId)
/*    */   {
/* 40 */     return _formatter.format("extra-filter-method", new Object[] { ms, filterInterface.getName(), serviceInterface.getName(), serviceId });
/*    */   }
/*    */ 
/*    */   static String unmatchedServiceMethod(MethodSignature ms, Class filterInterface)
/*    */   {
/* 46 */     return _formatter.format("unmatched-service-method", ms, filterInterface.getName());
/*    */   }
/*    */ 
/*    */   static String duplicateTerminator(Object terminator, String serviceId, Object existingTerminator, Location existingLocation)
/*    */   {
/* 52 */     return _formatter.format("duplicate-terminator", new Object[] { terminator, serviceId, existingTerminator, HiveMind.getLocationString(existingLocation) });
/*    */   }
/*    */ 
/*    */   static String incorrectInterface(Object instance, Class interfaceType, String serviceId)
/*    */   {
/* 60 */     return _formatter.format("incorrect-interface", instance, interfaceType.getName(), serviceId);
/*    */   }
/*    */ }