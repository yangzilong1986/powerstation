/*    */ package com.hisun.pipeline;
/*    */ 
/*    */ import org.apache.hivemind.service.MethodSignature;
/*    */ 
/*    */ class FilterMethodAnalyzer
/*    */ {
/*    */   private Class _serviceInterface;
/*    */ 
/*    */   FilterMethodAnalyzer(Class serviceInterface)
/*    */   {
/* 20 */     this._serviceInterface = serviceInterface;
/*    */   }
/*    */ 
/*    */   public int findServiceInterfacePosition(MethodSignature ms, MethodSignature fms)
/*    */   {
/* 25 */     if (ms.getReturnType() != fms.getReturnType()) {
/* 26 */       return -1;
/*    */     }
/* 28 */     if (!(ms.getName().equals(fms.getName()))) {
/* 29 */       return -1;
/*    */     }
/* 31 */     Class[] filterParameters = fms.getParameterTypes();
/* 32 */     int filterParameterCount = filterParameters.length;
/* 33 */     Class[] serviceParameters = ms.getParameterTypes();
/*    */ 
/* 35 */     if (filterParameterCount != serviceParameters.length + 1) {
/* 36 */       return -1;
/*    */     }
/*    */ 
/* 44 */     boolean found = false;
/* 45 */     int result = -1;
/*    */ 
/* 47 */     for (int i = 0; i < filterParameterCount; ++i)
/*    */     {
/* 49 */       if (filterParameters[i] != this._serviceInterface)
/*    */         continue;
/* 51 */       result = i;
/* 52 */       found = true;
/* 53 */       break;
/*    */     }
/*    */ 
/* 57 */     if (!(found)) {
/* 58 */       return -1;
/*    */     }
/*    */ 
/* 63 */     for (i = 0; i < result; ++i)
/*    */     {
/* 65 */       if (filterParameters[i] != serviceParameters[i]) {
/* 66 */         return -1;
/*    */       }
/*    */     }
/* 69 */     for (i = result + 1; i < filterParameterCount; ++i)
/*    */     {
/* 71 */       if (filterParameters[i] != serviceParameters[(i - 1)]) {
/* 72 */         return -1;
/*    */       }
/*    */     }
/* 75 */     return result;
/*    */   }
/*    */ }