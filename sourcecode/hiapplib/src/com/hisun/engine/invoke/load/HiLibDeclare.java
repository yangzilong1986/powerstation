/*    */ package com.hisun.engine.invoke.load;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiLib;
/*    */ import com.hisun.message.HiContext;
/*    */ 
/*    */ public class HiLibDeclare extends HiEngineModel
/*    */ {
/*    */   public String getNodeName()
/*    */   {
/* 18 */     return "LibDeclares";
/*    */   }
/*    */ 
/*    */   public void setLibrary(String strName, String value) throws HiException
/*    */   {
/* 23 */     HiContext.getCurrentContext().setProperty("LIBDECLARE." + strName, value);
/*    */ 
/* 25 */     HiLib.load(strName, value);
/*    */   }
/*    */ }