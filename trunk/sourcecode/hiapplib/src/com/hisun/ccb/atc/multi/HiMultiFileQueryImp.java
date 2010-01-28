 package com.hisun.ccb.atc.multi;
 
 import com.hisun.exception.HiException;
 import java.io.File;
 import java.io.FileReader;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Collection;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiMultiFileQueryImp
   implements HiIMultiQuery
 {
   private HiMultiDTO _md;
   private Collection<String> _results;
   private int _totalCounts = -1;
 
   private int _totalPage = -1;
   private ArrayList _fileContent = new ArrayList();
 
   HiMultiFileQueryImp(HiMultiDTO md)
   {
     this._md = md;
     this._results = new ArrayList();
   }
 
   private int getLineCounts(String fileName)
     throws HiException
   {
     if (this._fileContent.size() == 0) {
       loadFileContent(fileName, this._fileContent);
       this._totalCounts = this._fileContent.size();
     }
     return this._totalCounts;
   }
 
   public int getTotalCounts() throws HiException {
     if (this._totalCounts == -1) {
       String fileName = this._md.getFileDir() + this._md.getRecKey() + ".dat";
 
       getLineCounts(fileName);
     }
     return this._totalCounts;
   }
 
   public int getTotalPage() throws HiException {
     int counts = getTotalCounts();
     if (counts % this._md.getPageRecCounts() == 0)
       this._totalPage = (counts / this._md.getPageRecCounts());
     else {
       this._totalPage = (counts / this._md.getPageRecCounts() + 1);
     }
     return this._totalPage;
   }
 
   public Collection<String> process() throws HiException {
     this._results.clear();
     queryFromFile();
     return this._results;
   }
 
   private void queryFromFile()
     throws HiException
   {
     String fileName = this._md.getFileDir() + this._md.getRecKey() + ".dat";
 
     int firstLineNum = 1;
     int queryCounts = this._md.getPageRecCounts();
     if (this._md.isFirstPage())
     {
       firstLineNum = 1;
     } else if (this._md.isEndPage())
     {
       firstLineNum = -1;
     }
     else {
       firstLineNum = this._md.getPageRecCounts() * (this._md.getPageNum() - 1) + 1;
 
       if (firstLineNum >= getTotalCounts())
       {
         firstLineNum = -1;
       } else if (firstLineNum < 0)
       {
         firstLineNum = 1;
       }
 
     }
 
     if (firstLineNum == -1)
     {
       int totalLineCounts = getLineCounts(fileName);
       int totalPageCounts = 0;
       if (totalLineCounts % this._md.getPageRecCounts() == 0) {
         totalPageCounts = totalLineCounts / this._md.getPageRecCounts();
       }
       else {
         totalPageCounts = totalLineCounts / this._md.getPageRecCounts() + 1;
       }
 
       firstLineNum = this._md.getPageRecCounts() * (totalPageCounts - 1) + 1;
     }
 
     if (this._fileContent.size() == 0) {
       loadFileContent(fileName, this._fileContent);
     }
 
     int start = 1;
     String tempStr = null;
 
     for (int j = 0; j < this._fileContent.size(); ++j) {
       if (start == firstLineNum + queryCounts) {
         return;
       }
       if (start >= firstLineNum) {
         this._results.add((String)this._fileContent.get(j));
       }
       ++start;
     }
   }
 
   private ArrayList loadFileContent(String file, ArrayList list)
     throws HiException
   {
     list.clear();
     FileReader fr = null;
     try {
       File f = new File(file);
       if (!(f.exists())) {
         ArrayList localArrayList = list;
         return localArrayList;
       }
       fr = new FileReader(f);
       char[] tmp = new char[8];
       int count = 0;
       while (true) {
         ret = fr.read(tmp, 0, 8);
         if (ret == -1) break; if (ret == 0) {
           break;
         }
         ++count;
         if (ret != 8) {
           throw new HiException("215117", file);
         }
 
         int len = NumberUtils.toInt(new String(tmp, 0, 8));
         char[] buf = new char[len];
         ret = fr.read(buf, 0, len);
         if (ret == -1) break; if (ret == 0) {
           break;
         }
         if (ret != len) {
           throw new HiException("215117", file);
         }
         list.add(new String(buf, 0, len));
       }
       int ret = list;
 
       return ret;
     }
     catch (IOException e)
     {
     }
     finally
     {
       try
       {
         if (fr != null)
           fr.close();
       }
       catch (IOException e) {
         throw new HiException("220079", file, e);
       }
     }
   }
 }