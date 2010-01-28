 package com.hisun.atc.rpt;
 
 import com.hisun.atc.rpt.data.BufferDataFileReader;
 import com.hisun.atc.rpt.data.OutputDataFileWriter;
 import com.hisun.atc.rpt.data.RecordReader;
 import com.hisun.atc.rpt.data.RecordWriter;
 import com.hisun.hilog4j.Logger;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.io.OutputStream;
 
 public class HiDataFile
   implements HiReportConstants
 {
   private final String path;
   private final String aDeli;
   private final String sDeli;
   private Logger log;
 
   HiDataFile(String path, String deli, Logger log)
   {
     this.path = path;
     this.aDeli = deli;
     this.sDeli = convertDeli(deli);
     this.log = log;
   }
 
   private String convertDeli(String deli) {
     String sDeli = deli;
 
     if ((deli.equals("|")) || (deli.equals(".")) || (deli.equals("*")))
       sDeli = "\\" + deli;
     return sDeli;
   }
 
   private static OutputStream open(String path)
   {
     try
     {
       return new FileOutputStream(path);
     }
     catch (FileNotFoundException e) {
       throw new HiReportRuntimeException(e);
     }
   }
 
   private static OutputStream openAppend(String path)
   {
     try
     {
       return new FileOutputStream(path, true);
     }
     catch (FileNotFoundException e) {
       throw new HiReportRuntimeException(e);
     }
   }
 
   private BufferedReader startRead() {
     try {
       return new BufferedReader(new InputStreamReader(new FileInputStream(this.path)));
     }
     catch (FileNotFoundException e) {
       this.log.error("can not open file:" + this.path, e);
       throw new HiReportRuntimeException(e);
     }
   }
 
   public String getPath() {
     return this.path;
   }
 
   public void delete() {
     boolean b = new File(this.path).delete();
 
     this.log.info("delete file:" + this.path + " " + b);
   }
 
   public RecordWriter getWriter() {
     OutputStream out = open(this.path);
     return new OutputDataFileWriter(out, this.aDeli, this.log);
   }
 
   public RecordWriter getAppendWriter() {
     if (new File(this.path).length() == 0L) {
       return getWriter();
     }
     OutputStream out = openAppend(this.path);
     try
     {
       out.write(10);
     } catch (IOException e) {
     }
     return new OutputDataFileWriter(out, this.aDeli, this.log);
   }
 
   public RecordReader getReader() {
     BufferedReader in = startRead();
     return new BufferDataFileReader(in, this.log, this.sDeli);
   }
 }