 package com.hisun.atc.bat;
 
 import com.hisun.exception.HiException;
 import com.hisun.util.HiByteBuffer;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import org.apache.commons.lang.StringUtils;
 
 public class HiBatchFile
 {
   private boolean _ignoreBlankLine = false;
 
   private int _beginIgnoreLine = 0;
 
   private int _endIgnoreLine = 0;
 
   private String _file = null;
 
   private int _sumFlag = 0;
 
   private int _sumRecordLength = -1;
 
   private int _dataRecordLength = -1;
 
   private long _sumRecordPos = -1L;
 
   private long _dataRecords = 0L;
 
   private int _currRecordNo = 0;
   private int _nextRecordNo = 1;
 
   private long _totalRecords = 0L;
 
   private long _totalLen = 0L;
 
   private FileInputStream _fis = null;
   private BufferedReader _br = null;
   private HiByteBuffer _byteBuffer = null;
 
   public HiBatchFile() {
   }
 
   public HiBatchFile(String file) {
     this._file = file;
   }
 
   public void setFile(String file) {
     this._file = file;
   }
 
   public String getFile()
   {
     return this._file;
   }
 
   public void setSumFlag(int sumFlag) {
     this._sumFlag = sumFlag;
   }
 
   public void setSumRecordLength(int length) {
     this._sumRecordLength = length;
   }
 
   public void setDataRecordLength(int length) {
     this._dataRecordLength = length;
   }
 
   public int getTotalCnt()
   {
     return (int)this._totalRecords;
   }
 
   public int getDataCnt()
   {
     return (int)this._dataRecords;
   }
 
   public HiByteBuffer getNextRecord(HiByteBuffer byteBuffer) throws HiException
   {
     while (this._nextRecordNo <= this._beginIgnoreLine) {
       if (readRecord(byteBuffer) == null)
         return null;
       byteBuffer.clear();
     }
 
     if (this._nextRecordNo > this._totalRecords - this._endIgnoreLine) {
       return null;
     }
 
     byteBuffer = readRecord(byteBuffer);
     this._byteBuffer = byteBuffer;
     return byteBuffer;
   }
 
   public HiByteBuffer getCurrentRecord() {
     return this._byteBuffer;
   }
 
   private HiByteBuffer readRecord(HiByteBuffer byteBuffer) throws HiException {
     try {
       if ((this._sumRecordLength == -1) && (this._dataRecordLength == -1))
       {
         String buffer = null;
         do
           if ((buffer = this._br.readLine()) == null)
             return null;
         while (StringUtils.isEmpty(buffer));
 
         byteBuffer.append(buffer);
       }
       else {
         byte[] buffer = null;
         if (isSumRecord()) {
           buffer = new byte[this._sumRecordLength];
           this._fis.read(buffer);
         } else {
           buffer = new byte[this._dataRecordLength];
           this._fis.read(buffer);
         }
         if (buffer == null)
           return null;
         byteBuffer.append(buffer);
       }
     } catch (IOException e) {
       return null;
     }
     this._currRecordNo = this._nextRecordNo;
     this._nextRecordNo += 1;
     return byteBuffer;
   }
 
   public boolean isSumRecord() {
     return (this._currRecordNo == this._sumRecordPos);
   }
 
   public int getCurrRecordNo() {
     return this._currRecordNo;
   }
 
   public int getBeginIgnoreLine() {
     return this._beginIgnoreLine;
   }
 
   public void setBeginIgnoreLine(int beginLine) {
     this._beginIgnoreLine = beginLine;
   }
 
   public boolean isIgnoreBlankLine() {
     return this._ignoreBlankLine;
   }
 
   public void setIgnoreBlankLine(boolean blankLine) {
     this._ignoreBlankLine = blankLine;
   }
 
   public int getEndIgnoreLine() {
     return this._endIgnoreLine;
   }
 
   public void setEndIgnoreLine(int endLine) {
     this._endIgnoreLine = endLine;
   }
 
   public void close() {
     try {
       this._br.close();
       this._fis.close();
     } catch (IOException e) {
     }
   }
 
   public void open() throws HiException {
     try {
       File f = new File(this._file);
       this._totalLen = f.length();
       this._fis = new FileInputStream(f);
       this._totalRecords = linesOfFile(this._fis);
 
       this._fis.close();
       this._fis = new FileInputStream(f);
       this._br = new BufferedReader(new InputStreamReader(this._fis));
       init();
     } catch (Exception e) {
       throw new HiException("220079", this._file, e);
     }
   }
 
   private void init() throws HiException {
     if (this._sumFlag == 2)
     {
       if ((this._dataRecordLength == -1) && (this._sumRecordLength == -1)) {
         if (this._totalRecords < 2L) {
           throw new HiException("220313", "批量文件总行数错误");
         }
         this._sumRecordPos = (this._totalRecords - this._endIgnoreLine);
         this._dataRecords = (this._totalRecords - 1L - this._beginIgnoreLine - this._endIgnoreLine); return;
       }
       if ((this._dataRecordLength != -1) && (this._sumRecordLength != -1)) {
         this._dataRecords = ((this._totalLen - this._sumRecordLength) / this._dataRecordLength - this._beginIgnoreLine - this._endIgnoreLine);
 
         this._sumRecordPos = (this._dataRecords + 1L + this._beginIgnoreLine);
         this._totalRecords = (this._dataRecords + 1L + this._beginIgnoreLine + this._endIgnoreLine); return;
       }
 
       throw new HiException("220313", "必须同时配置recrod_length属性");
     }
     if (this._sumFlag == 1)
     {
       if ((this._dataRecordLength == -1) && (this._sumRecordLength == -1)) {
         if (this._totalRecords < 2L) {
           throw new HiException("220313", "批量文件总行数错误 < 2");
         }
         this._sumRecordPos = (this._beginIgnoreLine + 1);
         this._dataRecords = (this._totalRecords - 1L - this._beginIgnoreLine - this._endIgnoreLine); return;
       }
       if ((this._dataRecordLength != -1) && (this._sumRecordLength != -1)) {
         this._dataRecords = ((this._totalLen - this._sumRecordLength) / this._dataRecordLength - this._beginIgnoreLine - this._endIgnoreLine);
 
         this._sumRecordPos = (this._beginIgnoreLine + 1);
         this._totalRecords = (this._dataRecords + 1L + this._beginIgnoreLine + this._endIgnoreLine); return;
       }
 
       throw new HiException("220313", "汇总、数据节点必须同时配置recrod_length属性");
     }
 
     if (this._dataRecordLength == -1) {
       this._dataRecords = (this._totalRecords - this._beginIgnoreLine - this._endIgnoreLine);
     }
     else {
       this._dataRecords = (this._totalLen / this._dataRecordLength - this._beginIgnoreLine - this._endIgnoreLine);
 
       this._totalRecords = (this._dataRecords + 1L + this._beginIgnoreLine + this._endIgnoreLine);
     }
   }
 
   private int linesOfFile(FileInputStream fis)
     throws IOException
   {
     BufferedReader br = new BufferedReader(new InputStreamReader(fis));
     int totalLines = 0;
     String s = null;
     while ((s = br.readLine()) != null) {
       if (StringUtils.isEmpty(s))
         continue;
       ++totalLines;
     }
     return totalLines;
   }
 
   public String toString() {
     StringBuffer result = new StringBuffer();
     result.append("_ignoreBlankLine:" + this._ignoreBlankLine);
     result.append(";_beginIgnoreLine:" + this._beginIgnoreLine);
     result.append(";_endIgnoreLine:" + this._endIgnoreLine);
     result.append(";_file:" + this._file);
     result.append(";_sumFlag:" + this._sumFlag);
     result.append(";_sumRecordLength:" + this._sumRecordLength);
     result.append(";_dataRecordLength:" + this._dataRecordLength);
     result.append(";_sumRecordPos:" + this._sumRecordPos);
     result.append(";_dataRecords:" + this._dataRecords);
     result.append(";_totalRecords:" + this._totalRecords);
     result.append(";_totalLen:" + this._totalLen);
     return result.toString();
   }
 }