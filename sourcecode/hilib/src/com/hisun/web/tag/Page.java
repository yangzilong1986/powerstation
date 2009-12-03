/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ public class Page
/*     */ {
/*  90 */   private int page = 1;
/*     */ 
/*  92 */   private int pageSize = 20;
/*     */ 
/*  94 */   private int rowCount = 0;
/*     */ 
/*  96 */   private int currPage = 1;
/*     */ 
/*  98 */   private int currGroup = 1;
/*     */ 
/* 100 */   private int group = 1;
/*     */ 
/*     */   public int getPageSize()
/*     */   {
/*  12 */     return this.pageSize;
/*     */   }
/*     */ 
/*     */   public int getGroupPageSize()
/*     */   {
/*  20 */     return (this.pageSize * getGroupPages());
/*     */   }
/*     */ 
/*     */   public void setPageSize(int pageSize) {
/*  24 */     this.pageSize = pageSize;
/*     */   }
/*     */ 
/*     */   public int getRowCount()
/*     */   {
/*  31 */     return this.rowCount;
/*     */   }
/*     */ 
/*     */   public void setRowCount(int rowCount) {
/*  35 */     this.rowCount = rowCount;
/*     */   }
/*     */ 
/*     */   public int getPage()
/*     */   {
/*  43 */     return this.page;
/*     */   }
/*     */ 
/*     */   public void setPage(int page) {
/*  47 */     this.page = page;
/*     */   }
/*     */ 
/*     */   public int getCurrPage()
/*     */   {
/*  55 */     return this.currPage;
/*     */   }
/*     */ 
/*     */   public void setCurrPage(int currPage) {
/*  59 */     this.currPage = currPage;
/*     */   }
/*     */ 
/*     */   public int getCurrGroup()
/*     */   {
/*  67 */     return this.currGroup;
/*     */   }
/*     */ 
/*     */   public void setCurrGroup(int currGroup) {
/*  71 */     this.currGroup = currGroup;
/*     */   }
/*     */ 
/*     */   public int getGroup()
/*     */   {
/*  79 */     return this.group;
/*     */   }
/*     */ 
/*     */   public void setGroup(int group) {
/*  83 */     this.group = group;
/*     */   }
/*     */ 
/*     */   public static int getGroupPages() {
/*  87 */     return 10;
/*     */   }
/*     */ }