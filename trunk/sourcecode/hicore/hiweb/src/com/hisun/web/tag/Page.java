 package com.hisun.web.tag;
 
 public class Page
 {
   private int page = 1;
 
   private int pageSize = 20;
 
   private int rowCount = 0;
 
   private int currPage = 1;
 
   private int currGroup = 1;
 
   private int group = 1;
 
   public int getPageSize()
   {
     return this.pageSize;
   }
 
   public int getGroupPageSize()
   {
     return (this.pageSize * getGroupPages());
   }
 
   public void setPageSize(int pageSize) {
     this.pageSize = pageSize;
   }
 
   public int getRowCount()
   {
     return this.rowCount;
   }
 
   public void setRowCount(int rowCount) {
     this.rowCount = rowCount;
   }
 
   public int getPage()
   {
     return this.page;
   }
 
   public void setPage(int page) {
     this.page = page;
   }
 
   public int getCurrPage()
   {
     return this.currPage;
   }
 
   public void setCurrPage(int currPage) {
     this.currPage = currPage;
   }
 
   public int getCurrGroup()
   {
     return this.currGroup;
   }
 
   public void setCurrGroup(int currGroup) {
     this.currGroup = currGroup;
   }
 
   public int getGroup()
   {
     return this.group;
   }
 
   public void setGroup(int group) {
     this.group = group;
   }
 
   public static int getGroupPages() {
     return 10;
   }
 }