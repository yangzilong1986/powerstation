 package com.hisun.ccb.atc.multi;
 
 public class HiMultiDTO
 {
   private String _recKey;
   private int _pageNum;
   private int _pageRecCounts;
   private boolean _isEndPage;
   private boolean _isFirstPage;
   private String _dataSourceType;
   private String _fileDir;
   private HiIMultiQuery _multiQuery;
 
   public HiMultiDTO(String recKey, int pageRecCounts)
   {
     this._dataSourceType = "fileSource";
     this._recKey = recKey;
     this._pageRecCounts = pageRecCounts;
   }
 
   public void setMultiQuery(HiIMultiQuery multiQuery)
   {
     this._multiQuery = multiQuery;
   }
 
   public HiIMultiQuery getMultiQuery() {
     return this._multiQuery;
   }
 
   public HiMultiDTO(String recKey, int pageRecCounts, String dataSourceType)
   {
     this._recKey = recKey;
     this._pageRecCounts = pageRecCounts;
     this._dataSourceType = dataSourceType;
     this._multiQuery = HiQueryFactory.getProcessor(this);
   }
 
   public boolean isEndPage()
   {
     return this._isEndPage;
   }
 
   public void setEndPage(boolean isEndPage)
   {
     this._isEndPage = isEndPage;
   }
 
   public boolean isFirstPage()
   {
     return this._isFirstPage;
   }
 
   public void setFirstPage(boolean isFirstPage)
   {
     this._isFirstPage = isFirstPage;
   }
 
   public int getPageNum()
   {
     return this._pageNum;
   }
 
   public void setPageNum(int pageNum)
   {
     this._pageNum = pageNum;
   }
 
   public int getPageRecCounts()
   {
     return this._pageRecCounts;
   }
 
   public void setPageRecCounts(int pageRecCounts)
   {
     this._pageRecCounts = pageRecCounts;
   }
 
   public String getRecKey()
   {
     return this._recKey;
   }
 
   public void setRecKey(String recKey)
   {
     this._recKey = recKey;
   }
 
   public String getDataSourceType()
   {
     return this._dataSourceType;
   }
 
   public void setDataSourceType(String dataSourceType)
   {
     this._dataSourceType = dataSourceType;
   }
 
   public String getFileDir()
   {
     return this._fileDir;
   }
 
   public void setFileDir(String fileDir)
   {
     this._fileDir = fileDir;
   }
 }