/*     */ package com.hisun.ccb.atc.multi;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiMultiFileQueryImp
/*     */   implements HiIMultiQuery
/*     */ {
/*     */   private HiMultiDTO _md;
/*     */   private Collection<String> _results;
/*  38 */   private int _totalCounts = -1;
/*     */ 
/*  43 */   private int _totalPage = -1;
/*  44 */   private ArrayList _fileContent = new ArrayList();
/*     */ 
/*     */   HiMultiFileQueryImp(HiMultiDTO md)
/*     */   {
/*  53 */     this._md = md;
/*  54 */     this._results = new ArrayList();
/*     */   }
/*     */ 
/*     */   private int getLineCounts(String fileName)
/*     */     throws HiException
/*     */   {
/*  86 */     if (this._fileContent.size() == 0) {
/*  87 */       loadFileContent(fileName, this._fileContent);
/*  88 */       this._totalCounts = this._fileContent.size();
/*     */     }
/*  90 */     return this._totalCounts;
/*     */   }
/*     */ 
/*     */   public int getTotalCounts() throws HiException {
/*  94 */     if (this._totalCounts == -1) {
/*  95 */       String fileName = this._md.getFileDir() + this._md.getRecKey() + ".dat";
/*     */ 
/*  97 */       getLineCounts(fileName);
/*     */     }
/*  99 */     return this._totalCounts;
/*     */   }
/*     */ 
/*     */   public int getTotalPage() throws HiException {
/* 103 */     int counts = getTotalCounts();
/* 104 */     if (counts % this._md.getPageRecCounts() == 0)
/* 105 */       this._totalPage = (counts / this._md.getPageRecCounts());
/*     */     else {
/* 107 */       this._totalPage = (counts / this._md.getPageRecCounts() + 1);
/*     */     }
/* 109 */     return this._totalPage;
/*     */   }
/*     */ 
/*     */   public Collection<String> process() throws HiException {
/* 113 */     this._results.clear();
/* 114 */     queryFromFile();
/* 115 */     return this._results;
/*     */   }
/*     */ 
/*     */   private void queryFromFile()
/*     */     throws HiException
/*     */   {
/* 124 */     String fileName = this._md.getFileDir() + this._md.getRecKey() + ".dat";
/*     */ 
/* 126 */     int firstLineNum = 1;
/* 127 */     int queryCounts = this._md.getPageRecCounts();
/* 128 */     if (this._md.isFirstPage())
/*     */     {
/* 130 */       firstLineNum = 1;
/* 131 */     } else if (this._md.isEndPage())
/*     */     {
/* 133 */       firstLineNum = -1;
/*     */     }
/*     */     else {
/* 136 */       firstLineNum = this._md.getPageRecCounts() * (this._md.getPageNum() - 1) + 1;
/*     */ 
/* 138 */       if (firstLineNum >= getTotalCounts())
/*     */       {
/* 140 */         firstLineNum = -1;
/* 141 */       } else if (firstLineNum < 0)
/*     */       {
/* 143 */         firstLineNum = 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 149 */     if (firstLineNum == -1)
/*     */     {
/* 151 */       int totalLineCounts = getLineCounts(fileName);
/* 152 */       int totalPageCounts = 0;
/* 153 */       if (totalLineCounts % this._md.getPageRecCounts() == 0) {
/* 154 */         totalPageCounts = totalLineCounts / this._md.getPageRecCounts();
/*     */       }
/*     */       else {
/* 157 */         totalPageCounts = totalLineCounts / this._md.getPageRecCounts() + 1;
/*     */       }
/*     */ 
/* 160 */       firstLineNum = this._md.getPageRecCounts() * (totalPageCounts - 1) + 1;
/*     */     }
/*     */ 
/* 164 */     if (this._fileContent.size() == 0) {
/* 165 */       loadFileContent(fileName, this._fileContent);
/*     */     }
/*     */ 
/* 168 */     int start = 1;
/* 169 */     String tempStr = null;
/*     */ 
/* 171 */     for (int j = 0; j < this._fileContent.size(); ++j) {
/* 172 */       if (start == firstLineNum + queryCounts) {
/*     */         return;
/*     */       }
/* 175 */       if (start >= firstLineNum) {
/* 176 */         this._results.add((String)this._fileContent.get(j));
/*     */       }
/* 178 */       ++start;
/*     */     }
/*     */   }
/*     */ 
/*     */   private ArrayList loadFileContent(String file, ArrayList list)
/*     */     throws HiException
/*     */   {
/* 197 */     list.clear();
/* 198 */     FileReader fr = null;
/*     */     try {
/* 200 */       File f = new File(file);
/* 201 */       if (!(f.exists())) {
/* 202 */         ArrayList localArrayList = list;
/*     */         return localArrayList;
/*     */       }
/* 204 */       fr = new FileReader(f);
/* 205 */       char[] tmp = new char[8];
/* 206 */       int count = 0;
/*     */       while (true) {
/* 208 */         ret = fr.read(tmp, 0, 8);
/* 209 */         if (ret == -1) break; if (ret == 0) {
/*     */           break;
/*     */         }
/* 212 */         ++count;
/* 213 */         if (ret != 8) {
/* 214 */           throw new HiException("215117", file);
/*     */         }
/*     */ 
/* 217 */         int len = NumberUtils.toInt(new String(tmp, 0, 8));
/* 218 */         char[] buf = new char[len];
/* 219 */         ret = fr.read(buf, 0, len);
/* 220 */         if (ret == -1) break; if (ret == 0) {
/*     */           break;
/*     */         }
/* 223 */         if (ret != len) {
/* 224 */           throw new HiException("215117", file);
/*     */         }
/* 226 */         list.add(new String(buf, 0, len));
/*     */       }
/* 228 */       int ret = list;
/*     */ 
/* 239 */       return ret;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 233 */         if (fr != null)
/* 234 */           fr.close();
/*     */       }
/*     */       catch (IOException e) {
/* 237 */         throw new HiException("220079", file, e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }