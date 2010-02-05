 package com.hisun.atc.rpt.data;
 
 import com.hisun.atc.rpt.HiDataRecord;
 
 public class RecordReaders
 {
   public static RecordReader append(RecordReader r1, RecordReader r2)
   {
     return new RecordReader(r1, r2) { private RecordReader curReader = this.val$r1;
       private final RecordReader val$r1;
       private final RecordReader val$r2;
 
       public void close() { this.val$r1.close();
         this.val$r2.close();
       }
 
       public HiDataRecord readRecord() {
         HiDataRecord rec = this.curReader.readRecord();
         if ((rec == null) && (this.curReader == this.val$r1)) {
           this.val$r1.close();
           this.curReader = this.val$r2;
           rec = readRecord();
         }
         return rec;
       }
     };
   }
 
   public static RecordReader filter(RecordReader r, Matcher m)
   {
     return new RecordReader(r, m) { private final RecordReader val$r;
       private final Matcher val$m;
 
       public void close() { this.val$r.close();
       }
 
       public HiDataRecord readRecord() {
         HiDataRecord rec = this.val$r.readRecord();
         while ((rec != null) && (!(this.val$m.match(rec))))
           rec = this.val$r.readRecord();
         return rec;
       }
     };
   }
 
   public static RecordReader takeWhile(RecordReader r, Matcher m)
   {
     return new RecordReader(r, m) { boolean isEnd = false;
       private final RecordReader val$r;
       private final Matcher val$m;
 
       public void close() { this.val$r.close();
       }
 
       public HiDataRecord readRecord() {
         if (this.isEnd) {
           return null;
         }
         HiDataRecord rec = this.val$r.readRecord();
         if ((rec == null) || (!(this.val$m.match(rec)))) {
           this.isEnd = true;
           rec = null;
         }
 
         return rec;
       }
     };
   }
 }