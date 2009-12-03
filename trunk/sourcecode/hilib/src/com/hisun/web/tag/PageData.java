/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class PageData
/*     */ {
/* 233 */   public static final PageData DEFAULT = new PageData();
/*     */ 
/* 235 */   protected int pageSize = 40;
/*     */ 
/* 237 */   protected int rowCount = 0;
/*     */ 
/* 239 */   protected int pageCount = 0;
/*     */ 
/* 241 */   protected int currPage = 1;
/*     */ 
/* 243 */   protected int startPosition = 0;
/*     */ 
/* 245 */   protected int endPosition = 0;
/*     */   protected List list;
/*     */   protected Object appendix;
/* 251 */   protected int currGroup = 1;
/* 252 */   protected int oldGroup = 1;
/*     */ 
/* 254 */   protected int groupPage = 0;
/*     */ 
/*     */   public List getList()
/*     */   {
/*  16 */     return this.list;
/*     */   }
/*     */ 
/*     */   public int getPageSize()
/*     */   {
/*  24 */     return this.pageSize;
/*     */   }
/*     */ 
/*     */   public int getRowCount()
/*     */   {
/*  32 */     return this.rowCount;
/*     */   }
/*     */ 
/*     */   public int getPageCount()
/*     */   {
/*  40 */     return this.pageCount;
/*     */   }
/*     */ 
/*     */   public int getCurrPage()
/*     */   {
/*  48 */     return this.currPage;
/*     */   }
/*     */ 
/*     */   public int getStartPosition()
/*     */   {
/*  56 */     return this.startPosition;
/*     */   }
/*     */ 
/*     */   public int getEndPosition()
/*     */   {
/*  64 */     return this.endPosition;
/*     */   }
/*     */ 
/*     */   public Object getAppendix() {
/*  68 */     return this.appendix;
/*     */   }
/*     */ 
/*     */   public void setAppendix(Object appendix) {
/*  72 */     this.appendix = appendix;
/*     */   }
/*     */ 
/*     */   public int getCurrGroup()
/*     */   {
/*  80 */     return this.currGroup;
/*     */   }
/*     */ 
/*     */   public int getOldGroup() {
/*  84 */     return this.oldGroup;
/*     */   }
/*     */ 
/*     */   public PageData(int currPage, int pageSize, int rowCount, List list)
/*     */   {
/*  94 */     this.currPage = currPage;
/*  95 */     this.pageSize = pageSize;
/*  96 */     this.rowCount = rowCount;
/*  97 */     this.list = list;
/*  98 */     init();
/*     */   }
/*     */ 
/*     */   public PageData(Page page, List list)
/*     */   {
/* 105 */     this.currPage = page.getPage();
/* 106 */     this.pageSize = page.getPageSize();
/* 107 */     this.rowCount = page.getRowCount();
/* 108 */     this.list = list;
/* 109 */     init();
/*     */   }
/*     */ 
/*     */   public PageData(int currPage, int pageSize, List list)
/*     */   {
/* 119 */     this.pageSize = pageSize;
/* 120 */     this.currPage = currPage;
/* 121 */     this.list = setData(list);
/*     */   }
/*     */ 
/*     */   public PageData(int currPage, int pageSize, List tempList, int groupPage, int oldGroup, int currGroup)
/*     */   {
/* 131 */     this.currPage = currPage;
/* 132 */     this.pageSize = pageSize;
/* 133 */     this.oldGroup = oldGroup;
/* 134 */     this.currGroup = currGroup;
/* 135 */     this.rowCount = tempList.size();
/* 136 */     this.groupPage = groupPage;
/* 137 */     int startLine = 0;
/* 138 */     if (groupPage > 0)
/* 139 */       startLine = (currPage - 1) % groupPage * pageSize;
/* 140 */     int endLine = startLine + pageSize;
/* 141 */     int len = tempList.size();
/* 142 */     if (endLine > len)
/* 143 */       endLine = len;
/* 144 */     List dataList = new ArrayList();
/* 145 */     for (int i = startLine; i < endLine; ++i) {
/* 146 */       dataList.add(tempList.get(i));
/*     */     }
/*     */ 
/* 149 */     if ((startLine >= len) && (len > 10))
/*     */     {
/* 151 */       for (i = len - 10; i < len; ++i) {
/* 152 */         dataList.add(tempList.get(i));
/*     */       }
/* 154 */       this.currPage -= 1;
/*     */     }
/* 156 */     this.list = dataList;
/*     */ 
/* 158 */     if (currPage <= 0)
/* 159 */       currPage = 1;
/* 160 */     int start = (currPage - 1) * pageSize;
/* 161 */     int end = start + pageSize;
/* 162 */     if (end > this.rowCount + (currGroup - 1) * pageSize * groupPage) {
/* 163 */       end = this.rowCount + (currGroup - 1) * pageSize * groupPage;
/*     */     }
/* 165 */     if (this.rowCount != 0) {
/* 166 */       this.startPosition = (start + 1);
/* 167 */       this.endPosition = end;
/*     */     }
/*     */   }
/*     */ 
/*     */   private List setData(List list)
/*     */   {
/* 176 */     this.rowCount = list.size();
/* 177 */     this.pageCount = ((this.rowCount % this.pageSize == 0) ? this.rowCount / this.pageSize : this.rowCount / this.pageSize + 1);
/* 178 */     if (this.currPage <= 0)
/* 179 */       this.currPage = 1;
/* 180 */     if ((this.pageCount > 0) && (this.currPage > this.pageCount))
/* 181 */       this.currPage = this.pageCount;
/* 182 */     int absolute = (this.currPage - 1) * this.pageSize;
/* 183 */     int end = absolute + this.pageSize;
/* 184 */     if (end > this.rowCount) {
/* 185 */       end = this.rowCount;
/*     */     }
/* 187 */     this.startPosition = (absolute + 1);
/* 188 */     this.endPosition = end;
/* 189 */     return list.subList(absolute, end);
/*     */   }
/*     */ 
/*     */   public PageData()
/*     */   {
/* 196 */     this.list = new ArrayList();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 200 */     StringBuffer sb = new StringBuffer();
/* 201 */     sb.append("pageSize = ").append(this.pageSize).append(" ; \r\n");
/* 202 */     sb.append("rowCount = ").append(this.rowCount).append(" ; \r\n");
/* 203 */     sb.append("pageCount = ").append(this.pageCount).append(" ; \r\n");
/* 204 */     sb.append("currPage = ").append(this.currPage).append(" ; \r\n");
/* 205 */     sb.append("startPosition = ").append(this.startPosition).append(" ; \r\n");
/* 206 */     sb.append("endPosition = ").append(this.endPosition).append(" ; \r\n");
/* 207 */     sb.append("list = \r\n").append(this.list).append(" ; \r\n");
/* 208 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/* 215 */     if (this.rowCount != 0)
/* 216 */       this.pageCount = ((this.rowCount % this.pageSize == 0) ? this.rowCount / this.pageSize : this.rowCount / this.pageSize + 1);
/* 217 */     if (this.currPage <= 0)
/* 218 */       this.currPage = 1;
/* 219 */     if (this.currPage > this.pageCount)
/* 220 */       this.currPage = this.pageCount;
/* 221 */     int start = (this.currPage - 1) * this.pageSize;
/* 222 */     int end = start + this.pageSize;
/* 223 */     if (end > this.rowCount) {
/* 224 */       end = this.rowCount;
/*     */     }
/* 226 */     if (this.rowCount != 0) {
/* 227 */       this.startPosition = (start + 1);
/* 228 */       this.endPosition = end;
/*     */     }
/*     */   }
/*     */ }