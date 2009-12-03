/*     */ package com.hisun.atc.bat;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiBatchFile
/*     */ {
/*  20 */   private boolean _ignoreBlankLine = false;
/*     */ 
/*  25 */   private int _beginIgnoreLine = 0;
/*     */ 
/*  30 */   private int _endIgnoreLine = 0;
/*     */ 
/*  35 */   private String _file = null;
/*     */ 
/*  40 */   private int _sumFlag = 0;
/*     */ 
/*  45 */   private int _sumRecordLength = -1;
/*     */ 
/*  50 */   private int _dataRecordLength = -1;
/*     */ 
/*  55 */   private long _sumRecordPos = -1L;
/*     */ 
/*  60 */   private long _dataRecords = 0L;
/*     */ 
/*  65 */   private int _currRecordNo = 0;
/*  66 */   private int _nextRecordNo = 1;
/*     */ 
/*  68 */   private long _totalRecords = 0L;
/*     */ 
/*  70 */   private long _totalLen = 0L;
/*     */ 
/*  72 */   private FileInputStream _fis = null;
/*  73 */   private BufferedReader _br = null;
/*  74 */   private HiByteBuffer _byteBuffer = null;
/*     */ 
/*     */   public HiBatchFile() {
/*     */   }
/*     */ 
/*     */   public HiBatchFile(String file) {
/*  80 */     this._file = file;
/*     */   }
/*     */ 
/*     */   public void setFile(String file) {
/*  84 */     this._file = file;
/*     */   }
/*     */ 
/*     */   public String getFile()
/*     */   {
/*  89 */     return this._file;
/*     */   }
/*     */ 
/*     */   public void setSumFlag(int sumFlag) {
/*  93 */     this._sumFlag = sumFlag;
/*     */   }
/*     */ 
/*     */   public void setSumRecordLength(int length) {
/*  97 */     this._sumRecordLength = length;
/*     */   }
/*     */ 
/*     */   public void setDataRecordLength(int length) {
/* 101 */     this._dataRecordLength = length;
/*     */   }
/*     */ 
/*     */   public int getTotalCnt()
/*     */   {
/* 110 */     return (int)this._totalRecords;
/*     */   }
/*     */ 
/*     */   public int getDataCnt()
/*     */   {
/* 119 */     return (int)this._dataRecords;
/*     */   }
/*     */ 
/*     */   public HiByteBuffer getNextRecord(HiByteBuffer byteBuffer) throws HiException
/*     */   {
/* 124 */     while (this._nextRecordNo <= this._beginIgnoreLine) {
/* 125 */       if (readRecord(byteBuffer) == null)
/* 126 */         return null;
/* 127 */       byteBuffer.clear();
/*     */     }
/*     */ 
/* 130 */     if (this._nextRecordNo > this._totalRecords - this._endIgnoreLine) {
/* 131 */       return null;
/*     */     }
/*     */ 
/* 134 */     byteBuffer = readRecord(byteBuffer);
/* 135 */     this._byteBuffer = byteBuffer;
/* 136 */     return byteBuffer;
/*     */   }
/*     */ 
/*     */   public HiByteBuffer getCurrentRecord() {
/* 140 */     return this._byteBuffer;
/*     */   }
/*     */ 
/*     */   private HiByteBuffer readRecord(HiByteBuffer byteBuffer) throws HiException {
/*     */     try {
/* 145 */       if ((this._sumRecordLength == -1) && (this._dataRecordLength == -1))
/*     */       {
/* 149 */         String buffer = null;
/*     */         do
/* 151 */           if ((buffer = this._br.readLine()) == null)
/* 152 */             return null;
/* 153 */         while (StringUtils.isEmpty(buffer));
/*     */ 
/* 156 */         byteBuffer.append(buffer);
/*     */       }
/*     */       else {
/* 159 */         byte[] buffer = null;
/* 160 */         if (isSumRecord()) {
/* 161 */           buffer = new byte[this._sumRecordLength];
/* 162 */           this._fis.read(buffer);
/*     */         } else {
/* 164 */           buffer = new byte[this._dataRecordLength];
/* 165 */           this._fis.read(buffer);
/*     */         }
/* 167 */         if (buffer == null)
/* 168 */           return null;
/* 169 */         byteBuffer.append(buffer);
/*     */       }
/*     */     } catch (IOException e) {
/* 172 */       return null;
/*     */     }
/* 174 */     this._currRecordNo = this._nextRecordNo;
/* 175 */     this._nextRecordNo += 1;
/* 176 */     return byteBuffer;
/*     */   }
/*     */ 
/*     */   public boolean isSumRecord() {
/* 180 */     return (this._currRecordNo == this._sumRecordPos);
/*     */   }
/*     */ 
/*     */   public int getCurrRecordNo() {
/* 184 */     return this._currRecordNo;
/*     */   }
/*     */ 
/*     */   public int getBeginIgnoreLine() {
/* 188 */     return this._beginIgnoreLine;
/*     */   }
/*     */ 
/*     */   public void setBeginIgnoreLine(int beginLine) {
/* 192 */     this._beginIgnoreLine = beginLine;
/*     */   }
/*     */ 
/*     */   public boolean isIgnoreBlankLine() {
/* 196 */     return this._ignoreBlankLine;
/*     */   }
/*     */ 
/*     */   public void setIgnoreBlankLine(boolean blankLine) {
/* 200 */     this._ignoreBlankLine = blankLine;
/*     */   }
/*     */ 
/*     */   public int getEndIgnoreLine() {
/* 204 */     return this._endIgnoreLine;
/*     */   }
/*     */ 
/*     */   public void setEndIgnoreLine(int endLine) {
/* 208 */     this._endIgnoreLine = endLine;
/*     */   }
/*     */ 
/*     */   public void close() {
/*     */     try {
/* 213 */       this._br.close();
/* 214 */       this._fis.close();
/*     */     } catch (IOException e) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void open() throws HiException {
/*     */     try {
/* 221 */       File f = new File(this._file);
/* 222 */       this._totalLen = f.length();
/* 223 */       this._fis = new FileInputStream(f);
/* 224 */       this._totalRecords = linesOfFile(this._fis);
/*     */ 
/* 226 */       this._fis.close();
/* 227 */       this._fis = new FileInputStream(f);
/* 228 */       this._br = new BufferedReader(new InputStreamReader(this._fis));
/* 229 */       init();
/*     */     } catch (Exception e) {
/* 231 */       throw new HiException("220079", this._file, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void init() throws HiException {
/* 236 */     if (this._sumFlag == 2)
/*     */     {
/* 238 */       if ((this._dataRecordLength == -1) && (this._sumRecordLength == -1)) {
/* 239 */         if (this._totalRecords < 2L) {
/* 240 */           throw new HiException("220313", "批量文件总行数错误");
/*     */         }
/* 242 */         this._sumRecordPos = (this._totalRecords - this._endIgnoreLine);
/* 243 */         this._dataRecords = (this._totalRecords - 1L - this._beginIgnoreLine - this._endIgnoreLine); return;
/*     */       }
/* 245 */       if ((this._dataRecordLength != -1) && (this._sumRecordLength != -1)) {
/* 246 */         this._dataRecords = ((this._totalLen - this._sumRecordLength) / this._dataRecordLength - this._beginIgnoreLine - this._endIgnoreLine);
/*     */ 
/* 248 */         this._sumRecordPos = (this._dataRecords + 1L + this._beginIgnoreLine);
/* 249 */         this._totalRecords = (this._dataRecords + 1L + this._beginIgnoreLine + this._endIgnoreLine); return;
/*     */       }
/*     */ 
/* 252 */       throw new HiException("220313", "必须同时配置recrod_length属性");
/*     */     }
/* 254 */     if (this._sumFlag == 1)
/*     */     {
/* 256 */       if ((this._dataRecordLength == -1) && (this._sumRecordLength == -1)) {
/* 257 */         if (this._totalRecords < 2L) {
/* 258 */           throw new HiException("220313", "批量文件总行数错误 < 2");
/*     */         }
/* 260 */         this._sumRecordPos = (this._beginIgnoreLine + 1);
/* 261 */         this._dataRecords = (this._totalRecords - 1L - this._beginIgnoreLine - this._endIgnoreLine); return;
/*     */       }
/* 263 */       if ((this._dataRecordLength != -1) && (this._sumRecordLength != -1)) {
/* 264 */         this._dataRecords = ((this._totalLen - this._sumRecordLength) / this._dataRecordLength - this._beginIgnoreLine - this._endIgnoreLine);
/*     */ 
/* 268 */         this._sumRecordPos = (this._beginIgnoreLine + 1);
/* 269 */         this._totalRecords = (this._dataRecords + 1L + this._beginIgnoreLine + this._endIgnoreLine); return;
/*     */       }
/*     */ 
/* 272 */       throw new HiException("220313", "汇总、数据节点必须同时配置recrod_length属性");
/*     */     }
/*     */ 
/* 276 */     if (this._dataRecordLength == -1) {
/* 277 */       this._dataRecords = (this._totalRecords - this._beginIgnoreLine - this._endIgnoreLine);
/*     */     }
/*     */     else {
/* 280 */       this._dataRecords = (this._totalLen / this._dataRecordLength - this._beginIgnoreLine - this._endIgnoreLine);
/*     */ 
/* 282 */       this._totalRecords = (this._dataRecords + 1L + this._beginIgnoreLine + this._endIgnoreLine);
/*     */     }
/*     */   }
/*     */ 
/*     */   private int linesOfFile(FileInputStream fis)
/*     */     throws IOException
/*     */   {
/* 289 */     BufferedReader br = new BufferedReader(new InputStreamReader(fis));
/* 290 */     int totalLines = 0;
/* 291 */     String s = null;
/* 292 */     while ((s = br.readLine()) != null) {
/* 293 */       if (StringUtils.isEmpty(s))
/*     */         continue;
/* 295 */       ++totalLines;
/*     */     }
/* 297 */     return totalLines;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 301 */     StringBuffer result = new StringBuffer();
/* 302 */     result.append("_ignoreBlankLine:" + this._ignoreBlankLine);
/* 303 */     result.append(";_beginIgnoreLine:" + this._beginIgnoreLine);
/* 304 */     result.append(";_endIgnoreLine:" + this._endIgnoreLine);
/* 305 */     result.append(";_file:" + this._file);
/* 306 */     result.append(";_sumFlag:" + this._sumFlag);
/* 307 */     result.append(";_sumRecordLength:" + this._sumRecordLength);
/* 308 */     result.append(";_dataRecordLength:" + this._dataRecordLength);
/* 309 */     result.append(";_sumRecordPos:" + this._sumRecordPos);
/* 310 */     result.append(";_dataRecords:" + this._dataRecords);
/* 311 */     result.append(";_totalRecords:" + this._totalRecords);
/* 312 */     result.append(";_totalLen:" + this._totalLen);
/* 313 */     return result.toString();
/*     */   }
/*     */ }