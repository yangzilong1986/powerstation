/*    */ package com.hisun.xml;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Location
/*    */   implements Serializable
/*    */ {
/*    */   private final String module;
/*    */   private final int line;
/*    */   private final int col;
/*    */ 
/*    */   public int getColumnNo()
/*    */   {
/* 17 */     return this.col;
/*    */   }
/*    */ 
/*    */   public int getLineNo()
/*    */   {
/* 23 */     return this.line;
/*    */   }
/*    */ 
/*    */   public String getModule()
/*    */   {
/* 29 */     return this.module;
/*    */   }
/*    */ 
/*    */   public Location(String module, int line, int col)
/*    */   {
/* 38 */     this.col = col;
/* 39 */     this.line = line;
/* 40 */     this.module = module; }
/*    */ 
/*    */   public boolean equals(Object obj) {
/* 43 */     if (obj instanceof Location) {
/* 44 */       Location other = (Location)obj;
/* 45 */       return ((this.line == other.line) && (this.col == other.col) && (this.module.equals(other.module)));
/*    */     }
/* 47 */     return false; }
/*    */ 
/*    */   public int hashCode() {
/* 50 */     return ((this.module.hashCode() * 31 + this.line) * 31 + this.col); }
/*    */ 
/*    */   public String toString() {
/* 53 */     return this.module + "(line " + this.line + ")";
/*    */   }
/*    */ }