 package com.hisun.atc.rpt.data;
 
 public class Appenders
 {
   private static Appender _zero = new Appender() {
     public void append(RecordWriter file) {  } } ;
 
   public static Appender zero()
   {
     return _zero;
   }
 
   public static Appender type(int type)
   {
     return new Appender(type) { private final int val$type;
 
       public void append(RecordWriter file) { file.newRecord(this.val$type);
       }
     };
   }
 
   public static Appender fmtseq(int seq)
   {
     return new Appender(seq) { private final int val$seq;
 
       public void append(RecordWriter file) { file.appendSeq(this.val$seq);
       }
     };
   }
 
   public static Appender seq(Appender[] appenders)
   {
     return new Appender(appenders) { private final Appender[] val$appenders;
 
       public void append(RecordWriter file) { for (int i = 0; i < this.val$appenders.length; ++i)
           this.val$appenders[i].append(file);
       }
     };
   }
 }