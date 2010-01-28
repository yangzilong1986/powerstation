 package com.hisun.atc.rpt.data;
 
 import com.hisun.atc.rpt.HiDataRecord;
 import com.hisun.atc.rpt.HiReportConstants;
 import com.hisun.atc.rpt.HiReportRuntimeException;
 import com.hisun.hilog4j.Logger;
 import java.io.IOException;
 import java.io.OutputStream;
 
 public class OutputDataFileWriter
   implements RecordWriter, HiReportConstants
 {
   private static final int BUF_SIZE = 256;
   private final OutputStream out;
   private final String aDeli;
   private final Logger log;
   private StringBuffer linebuf;
 
   public OutputDataFileWriter(OutputStream out, String deli, Logger log)
   {
     this.out = out;
     this.aDeli = deli;
     this.log = log;
   }
 
   public void appendRecord(HiDataRecord rec) {
     if (this.linebuf == null) {
       this.linebuf = new StringBuffer(256);
     } else {
       endRecord();
       this.linebuf.append("\n");
     }
     this.linebuf.append(rec.record);
     try
     {
       this.out.write(this.linebuf.toString().getBytes());
     }
     catch (IOException e) {
       throw new HiReportRuntimeException(e);
     }
 
     this.linebuf.setLength(0);
   }
 
   public void appendRecordValue(String name, String value) {
     this.linebuf.append(name).append("=").append(value);
     if (this.aDeli != null)
       this.linebuf.append(this.aDeli);
   }
 
   private void endRecord()
   {
     if ((this.linebuf == null) || (this.linebuf.length() <= 0)) return;
     try {
       this.out.write(this.linebuf.toString().getBytes());
     } catch (IOException e) {
       e.printStackTrace();
       return;
     }
 
     this.linebuf.delete(0, this.linebuf.length());
   }
 
   public void appendSeq(int seq)
   {
     if (seq != 0)
       this.linebuf.append(seq).append(":");
   }
 
   public int newRecord(int iType)
   {
     if (this.linebuf == null) {
       this.linebuf = new StringBuffer(256);
     } else {
       endRecord();
       this.linebuf.append("\n");
     }
     switch (iType)
     {
     case 0:
       this.linebuf.append("[0]");
       break;
     case 1:
       this.linebuf.append("[1]");
       break;
     case 2:
       this.linebuf.append("[2]");
       break;
     case 3:
       this.linebuf.append("[3]");
       break;
     case 4:
       this.linebuf.append("[4]");
       break;
     case 5:
       this.linebuf.append("[5]");
       break;
     case 6:
       this.linebuf.append("[6]");
       break;
     case 7:
       this.linebuf.append("[7]");
       break;
     case 8:
       this.linebuf.append("[8]");
       break;
     default:
       return -1;
     }
 
     this.linebuf.append(":");
 
     return 0;
   }
 
   public void close()
   {
     endRecord();
     try {
       this.out.close();
     }
     catch (IOException e)
     {
     }
   }
 }