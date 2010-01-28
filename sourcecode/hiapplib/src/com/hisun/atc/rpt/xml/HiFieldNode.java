 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiDataRecord;
 import com.hisun.atc.rpt.data.RecordWriter;
 import com.hisun.xml.Located;
 import java.util.Iterator;
 import java.util.LinkedList;
 
 public class HiFieldNode extends Located
 {
   String field;
   int fmtseq;
   LinkedList sums;
 
   public HiFieldNode()
   {
     this.sums = new LinkedList(); }
 
   public void addSumNode(HiSumNode sum) {
     sum.init();
     this.sums.add(sum);
   }
 
   public void load(HiDataRecord rec)
   {
     Iterator it = this.sums.iterator();
     while (it.hasNext()) {
       HiSumNode sum = (HiSumNode)it.next();
       sum.load(rec);
     }
   }
 
   public void append(RecordWriter tmpfile) {
     tmpfile.appendSeq(this.fmtseq);
     Iterator it = this.sums.iterator();
     while (it.hasNext()) {
       HiSumNode sum = (HiSumNode)it.next();
       sum.append(tmpfile);
     }
   }
 
   public void setField(String field) {
     this.field = field;
   }
 
   public void setFmtseq(int fmtseq) {
     this.fmtseq = fmtseq;
   }
 }