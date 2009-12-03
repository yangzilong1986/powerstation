/*    */ package com.hisun.version;
/*    */ 
/*    */ public class HiFileVersionInfo
/*    */ {
/*    */   private String version;
/*    */   private String compileTm;
/*    */   private String file;
/*    */ 
/*    */   public String getFile()
/*    */   {
/* 13 */     return this.file; }
/*    */ 
/*    */   public void setFile(String file) {
/* 16 */     this.file = file; }
/*    */ 
/*    */   public String getVersion() {
/* 19 */     return this.version; }
/*    */ 
/*    */   public void setVersion(String version) {
/* 22 */     this.version = version; }
/*    */ 
/*    */   public String getCompileTm() {
/* 25 */     return this.compileTm; }
/*    */ 
/*    */   public void setCompileTm(String compileTm) {
/* 28 */     this.compileTm = compileTm;
/*    */   }
/*    */ 
/*    */   public HiFileVersionInfo(String file, String version, String compileTm) {
/* 32 */     this.file = file;
/* 33 */     this.version = version;
/* 34 */     this.compileTm = compileTm;
/*    */   }
/*    */ }