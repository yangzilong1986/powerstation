/*    */ package com.hisun.data.object;
/*    */ 
/*    */ public class HiSelectOption
/*    */ {
/*    */   private String id;
/*    */   private String value;
/*    */   private String desc;
/*    */   private String sort;
/*    */ 
/*    */   public String getId()
/*    */   {
/* 16 */     return this.id; }
/*    */ 
/*    */   public void setId(String id) {
/* 19 */     this.id = id; }
/*    */ 
/*    */   public String getValue() {
/* 22 */     return this.value; }
/*    */ 
/*    */   public void setValue(String value) {
/* 25 */     this.value = value; }
/*    */ 
/*    */   public String getDesc() {
/* 28 */     return this.desc; }
/*    */ 
/*    */   public void setDesc(String desc) {
/* 31 */     this.desc = desc; }
/*    */ 
/*    */   public String getSort() {
/* 34 */     return this.sort; }
/*    */ 
/*    */   public void setSort(String sort) {
/* 37 */     this.sort = sort;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 42 */     return String.format("[%s][%s][%s][%s]", new Object[] { this.id, this.value, this.desc, this.sort });
/*    */   }
/*    */ }