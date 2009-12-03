/*    */ package com.hisun.atc.fil;
/*    */ 
/*    */ import com.hisun.engine.HiITFEngineModel;
/*    */ 
/*    */ public class HiOutDesc extends HiITFEngineModel
/*    */ {
/*    */   private String _name;
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 10 */     this._name = name;
/*    */   }
/*    */ 
/*    */   public String getNodeName() {
/* 14 */     return "Out";
/*    */   }
/*    */ }