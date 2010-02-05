 package com.hisun.atc.rpt.data;
 
 import com.hisun.atc.rpt.HiDataRecord;
 import com.hisun.atc.rpt.HiReportRuntimeException;
 import com.hisun.common.util.pattern.Pattern;
 import com.hisun.common.util.pattern.Patterns;
 import com.hisun.hilog4j.Logger;
 import java.io.BufferedReader;
 import java.io.IOException;
 
 public class BufferDataFileReader
   implements RecordReader
 {
   private BufferedReader in;
   private Logger log;
   private final String sDeli;
   private static final Pattern head;
 
   public BufferDataFileReader(BufferedReader in, Logger log, String deli)
   {
     this.in = in;
     this.log = log;
     this.sDeli = deli;
   }
 
   public void close() {
     try {
       this.in.close();
     }
     catch (IOException e) {
     }
     this.in = null;
   }
 
   public HiDataRecord readRecord()
   {
     try
     {
       String str = this.in.readLine();
       if ((str != null) && (str.length() > 0))
         return parseRecord(str);
     }
     catch (IOException e) {
       throw new HiReportRuntimeException(e);
     }
     return null;
   }
 
   private HiDataRecord parseRecord(String record) {
     HiDataRecord rec = new HiDataRecord();
     rec.record = record;
 
     int headlen = getHeadLen(record);
     if (headlen == -1) {
       this.log.warn("记录解析失败:" + record);
       return null;
     }
 
     if (headlen == 1) {
       rec.type = 99;
       return rec;
     }
 
     rec.type = (record.charAt(1) - '0');
     rec.seq = 0;
     if (headlen == 6) {
       rec.seq = (record.charAt(4) - '0');
     }
 
     String[] vars = record.substring(headlen).split(this.sDeli);
     for (int i = 0; i < vars.length; ++i) {
       String[] nv = vars[i].split("=");
       if (nv.length == 2)
       {
         rec.put(nv[0], nv[1]); } else {
         if (nv.length != 1)
           continue;
         rec.put(nv[0], " ");
       }
     }
     return rec;
   }
 
   private static int getHeadLen(String rec)
   {
     return head.match(rec, rec.length(), 0);
   }
 
   static
   {
     Pattern type = Patterns.seq(new Pattern[] { Patterns.isChar('['), Patterns.range('0', '9'), Patterns.isString("]:") });
 
     Pattern seq = Patterns.range('0', '9').seq(Patterns.isChar(':'));
     Pattern head1 = type.seq(seq.optional());
     head = Patterns.or(head1, Patterns.isChar('#'));
   }
 }