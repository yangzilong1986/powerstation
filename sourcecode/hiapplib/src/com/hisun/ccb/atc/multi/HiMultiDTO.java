/*     */ package com.hisun.ccb.atc.multi;
/*     */ 
/*     */ public class HiMultiDTO
/*     */ {
/*     */   private String _recKey;
/*     */   private int _pageNum;
/*     */   private int _pageRecCounts;
/*     */   private boolean _isEndPage;
/*     */   private boolean _isFirstPage;
/*     */   private String _dataSourceType;
/*     */   private String _fileDir;
/*     */   private HiIMultiQuery _multiQuery;
/*     */ 
/*     */   public HiMultiDTO(String recKey, int pageRecCounts)
/*     */   {
/*  37 */     this._dataSourceType = "fileSource";
/*  38 */     this._recKey = recKey;
/*  39 */     this._pageRecCounts = pageRecCounts;
/*     */   }
/*     */ 
/*     */   public void setMultiQuery(HiIMultiQuery multiQuery)
/*     */   {
/*  45 */     this._multiQuery = multiQuery;
/*     */   }
/*     */ 
/*     */   public HiIMultiQuery getMultiQuery() {
/*  49 */     return this._multiQuery;
/*     */   }
/*     */ 
/*     */   public HiMultiDTO(String recKey, int pageRecCounts, String dataSourceType)
/*     */   {
/*  61 */     this._recKey = recKey;
/*  62 */     this._pageRecCounts = pageRecCounts;
/*  63 */     this._dataSourceType = dataSourceType;
/*  64 */     this._multiQuery = HiQueryFactory.getProcessor(this);
/*     */   }
/*     */ 
/*     */   public boolean isEndPage()
/*     */   {
/*  69 */     return this._isEndPage;
/*     */   }
/*     */ 
/*     */   public void setEndPage(boolean isEndPage)
/*     */   {
/*  74 */     this._isEndPage = isEndPage;
/*     */   }
/*     */ 
/*     */   public boolean isFirstPage()
/*     */   {
/*  79 */     return this._isFirstPage;
/*     */   }
/*     */ 
/*     */   public void setFirstPage(boolean isFirstPage)
/*     */   {
/*  84 */     this._isFirstPage = isFirstPage;
/*     */   }
/*     */ 
/*     */   public int getPageNum()
/*     */   {
/*  89 */     return this._pageNum;
/*     */   }
/*     */ 
/*     */   public void setPageNum(int pageNum)
/*     */   {
/*  94 */     this._pageNum = pageNum;
/*     */   }
/*     */ 
/*     */   public int getPageRecCounts()
/*     */   {
/*  99 */     return this._pageRecCounts;
/*     */   }
/*     */ 
/*     */   public void setPageRecCounts(int pageRecCounts)
/*     */   {
/* 104 */     this._pageRecCounts = pageRecCounts;
/*     */   }
/*     */ 
/*     */   public String getRecKey()
/*     */   {
/* 109 */     return this._recKey;
/*     */   }
/*     */ 
/*     */   public void setRecKey(String recKey)
/*     */   {
/* 114 */     this._recKey = recKey;
/*     */   }
/*     */ 
/*     */   public String getDataSourceType()
/*     */   {
/* 119 */     return this._dataSourceType;
/*     */   }
/*     */ 
/*     */   public void setDataSourceType(String dataSourceType)
/*     */   {
/* 124 */     this._dataSourceType = dataSourceType;
/*     */   }
/*     */ 
/*     */   public String getFileDir()
/*     */   {
/* 129 */     return this._fileDir;
/*     */   }
/*     */ 
/*     */   public void setFileDir(String fileDir)
/*     */   {
/* 134 */     this._fileDir = fileDir;
/*     */   }
/*     */ }