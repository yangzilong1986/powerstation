/*    */ package com.hisun.atc.fil;
/*    */ 
/*    */ import com.hisun.engine.HiITFEngineModel;
/*    */ 
/*    */ public class HiInDesc extends HiITFEngineModel
/*    */ {
/*    */   private String _name;
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/*  9 */     this._name = name;
/*    */   }
/*    */ 
/*    */   public String getNodeName() {
/* 13 */     return "In";
/*    */   }
/*    */ }