/*    */ package com.hisun.xml;
/*    */ 
/*    */ public class Located
/*    */   implements LocationAware
/*    */ {
/*    */   private Location loc;
/*    */ 
/*    */   public Location getLocation()
/*    */   {
/*  7 */     return this.loc;
/*    */   }
/*    */ 
/*    */   public void setLocation(Location loc) {
/* 11 */     this.loc = loc;
/*    */   }
/*    */ }