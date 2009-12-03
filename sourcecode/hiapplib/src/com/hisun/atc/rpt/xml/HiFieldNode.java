/*    */ package com.hisun.atc.rpt.xml;
/*    */ 
/*    */ import com.hisun.atc.rpt.HiDataRecord;
/*    */ import com.hisun.atc.rpt.data.RecordWriter;
/*    */ import com.hisun.xml.Located;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ 
/*    */ public class HiFieldNode extends Located
/*    */ {
/*    */   String field;
/*    */   int fmtseq;
/*    */   LinkedList sums;
/*    */ 
/*    */   public HiFieldNode()
/*    */   {
/* 20 */     this.sums = new LinkedList(); }
/*    */ 
/*    */   public void addSumNode(HiSumNode sum) {
/* 23 */     sum.init();
/* 24 */     this.sums.add(sum);
/*    */   }
/*    */ 
/*    */   public void load(HiDataRecord rec)
/*    */   {
/* 33 */     Iterator it = this.sums.iterator();
/* 34 */     while (it.hasNext()) {
/* 35 */       HiSumNode sum = (HiSumNode)it.next();
/* 36 */       sum.load(rec);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void append(RecordWriter tmpfile) {
/* 41 */     tmpfile.appendSeq(this.fmtseq);
/* 42 */     Iterator it = this.sums.iterator();
/* 43 */     while (it.hasNext()) {
/* 44 */       HiSumNode sum = (HiSumNode)it.next();
/* 45 */       sum.append(tmpfile);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setField(String field) {
/* 50 */     this.field = field;
/*    */   }
/*    */ 
/*    */   public void setFmtseq(int fmtseq) {
/* 54 */     this.fmtseq = fmtseq;
/*    */   }
/*    */ }