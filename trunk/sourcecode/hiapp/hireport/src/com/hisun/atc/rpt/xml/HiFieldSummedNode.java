 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiDataFile;
 import com.hisun.atc.rpt.HiDataRecord;
 import com.hisun.atc.rpt.HiReportConstants;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.atc.rpt.data.Appender;
 import com.hisun.atc.rpt.data.Appenders;
 import com.hisun.atc.rpt.data.RecordReader;
 import com.hisun.atc.rpt.data.RecordWriter;
 import java.util.Iterator;
 import java.util.LinkedList;
 
 public class HiFieldSummedNode extends HiSummedNode
   implements HiReportConstants
 {
   LinkedList sums;
   Appender typeAppender;
 
   public HiFieldSummedNode()
   {
     this.sums = new LinkedList();
 
     this.typeAppender = Appenders.type(5); }
 
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
 
   public void append(RecordWriter tmpfile)
   {
     this.typeAppender.append(tmpfile);
     Iterator it = this.sums.iterator();
     while (it.hasNext()) {
       HiSumNode sum = (HiSumNode)it.next();
       sum.append(tmpfile);
     }
   }
 
   public HiDataFile process(HiRptContext ctx, HiDataFile datafile, HiDataFile tmpfile) {
     boolean bFlag = false;
     int lastType = -1;
 
     RecordReader reader = datafile.getReader();
 
     RecordWriter writer = tmpfile.getWriter();
 
     while ((rec = reader.readRecord()) != null)
     {
       HiDataRecord rec;
       bFlag = true;
 
       if (rec.type == 99)
         continue;
       if (rec.type == 3) {
         lastType = rec.type;
 
         load(rec);
         writer.appendRecord(rec);
       }
       if ((rec.type < 5) && (lastType > rec.type)) {
         lastType = rec.type;
 
         append(writer);
         writer.appendRecord(rec);
         bFlag = false;
       }
       if ((rec.type < 5) && (lastType <= rec.type)) {
         lastType = rec.type;
 
         writer.appendRecord(rec);
       }
       if (rec.type >= 5);
       lastType = rec.type;
 
       append(writer);
 
       writer.appendRecord(rec);
 
       bFlag = false;
     }
 
     if (bFlag)
     {
       append(writer);
     }
     writer.close();
     reader.close();
 
     return tmpfile;
   }
 }