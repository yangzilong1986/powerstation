/*    */ package com.hisun.client;
/*    */ 
/*    */ public class HiRquestParam
/*    */   implements RequestParam
/*    */ {
/*    */   private String name;
/*    */   private int index;
/*    */   private String type;
/*    */   private String value;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 14 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 18 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public int getIndex() {
/* 22 */     return this.index;
/*    */   }
/*    */ 
/*    */   public void setIndex(int index) {
/* 26 */     this.index = index;
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 30 */     return this.type;
/*    */   }
/*    */ 
/*    */   public void setType(String type) {
/* 34 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public Object getValue() {
/* 38 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value) {
/* 42 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 46 */     StringBuilder sb = new StringBuilder("REQUEST PARAM [");
/* 47 */     sb.append("index=" + this.index);
/* 48 */     sb.append(";name=" + this.name);
/* 49 */     sb.append(";type=" + this.type);
/* 50 */     sb.append(";value=" + this.value);
/* 51 */     return " ]";
/*    */   }
/*    */ }