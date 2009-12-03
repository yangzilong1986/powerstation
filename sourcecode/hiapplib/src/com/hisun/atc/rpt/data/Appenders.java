/*    */ package com.hisun.atc.rpt.data;
/*    */ 
/*    */ public class Appenders
/*    */ {
/*  6 */   private static Appender _zero = new Appender() {
/*    */     public void append(RecordWriter file) {  } } ;
/*    */ 
/*    */   public static Appender zero()
/*    */   {
/* 12 */     return _zero;
/*    */   }
/*    */ 
/*    */   public static Appender type(int type)
/*    */   {
/* 17 */     return new Appender(type) { private final int val$type;
/*    */ 
/*    */       public void append(RecordWriter file) { file.newRecord(this.val$type);
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   public static Appender fmtseq(int seq)
/*    */   {
/* 26 */     return new Appender(seq) { private final int val$seq;
/*    */ 
/*    */       public void append(RecordWriter file) { file.appendSeq(this.val$seq);
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   public static Appender seq(Appender[] appenders)
/*    */   {
/* 35 */     return new Appender(appenders) { private final Appender[] val$appenders;
/*    */ 
/*    */       public void append(RecordWriter file) { for (int i = 0; i < this.val$appenders.length; ++i)
/* 38 */           this.val$appenders[i].append(file);
/*    */       }
/*    */     };
/*    */   }
/*    */ }