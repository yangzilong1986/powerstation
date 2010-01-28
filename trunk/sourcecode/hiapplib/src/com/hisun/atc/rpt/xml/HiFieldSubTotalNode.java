 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiDataFile;
 import com.hisun.atc.rpt.HiDataRecord;
 import com.hisun.atc.rpt.HiReportConstants;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.atc.rpt.data.Appender;
 import com.hisun.atc.rpt.data.Appenders;
 import com.hisun.atc.rpt.data.RecordReader;
 import com.hisun.atc.rpt.data.RecordWriter;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 
 public class HiFieldSubTotalNode extends HiSubTotalNode
   implements HiReportConstants
 {
   Map fields;
   String[] aItemValue;
   Appender typeAppender;
 
   public HiFieldSubTotalNode()
   {
     this.fields = new HashMap();
 
     this.typeAppender = Appenders.type(4); }
 
   public void addField(HiFieldNode field) {
     this.fields.put(field.field, field);
   }
 
   public void setGroupby(String groups)
   {
     super.setGroupby(groups);
     this.aItemValue = new String[getGroupNum()];
   }
 
   public HiDataFile process(HiRptContext ctx, HiDataFile datafile, HiDataFile tmpfile)
   {
     boolean bfirstRec = true;
     int lastType = -1;
 
     RecordReader reader = datafile.getReader();
 
     RecordWriter writer = tmpfile.getWriter();
 
     while ((rec = reader.readRecord()) != null)
     {
       HiDataRecord rec;
       if (rec.type == 99)
         continue;
       if ((rec.type < 3) && (lastType > rec.type)) {
         lastType = rec.type;
 
         for (i = getGroupNum() - 1; i >= 0; --i) {
           addSTRecord(writer, this.groupName[i], this.aItemValue[i]);
         }
         bfirstRec = true;
       }
       if ((rec.type < 3) && (lastType <= rec.type))
       {
         writer.appendRecord(rec);
       }
       if (rec.type > 3) {
         lastType = rec.type;
 
         for (i = getGroupNum() - 1; i >= 0; --i) {
           addSTRecord(writer, this.groupName[i], this.aItemValue[i]);
         }
         bfirstRec = true;
       }
 
       if (bfirstRec) {
         for (i = 0; i < getGroupNum(); ++i) {
           this.aItemValue[i] = rec.get(this.groupName[i]);
         }
         bfirstRec = false;
       }
 
       for (int i = 0; i < getGroupNum(); ++i) {
         String srcvar = rec.get(this.groupName[i]);
         if (!(srcvar.equals(this.aItemValue[i]))) {
           for (int j = getGroupNum() - 1; j >= i; --j) {
             addSTRecord(writer, this.groupName[j], this.aItemValue[j]);
           }
           bfirstRec = true;
           break;
         }
       }
       writer.appendRecord(rec);
 
       loadSubTotal(rec);
 
       if (bfirstRec) {
         for (i = 0; i < getGroupNum(); ++i) {
           this.aItemValue[i] = rec.get(this.groupName[i]);
         }
         bfirstRec = false;
       }
       lastType = 3;
     }
 
     if (lastType <= 3)
     {
       for (int j = getGroupNum() - 1; j >= 0; --j) {
         addSTRecord(writer, this.groupName[j], this.aItemValue[j]);
       }
     }
     writer.close();
     reader.close();
 
     return tmpfile;
   }
 
   void loadSubTotal(HiDataRecord rec)
   {
     Iterator it = this.fields.values().iterator();
     while (it.hasNext()) {
       HiFieldNode field = (HiFieldNode)it.next();
       field.load(rec);
     }
   }
 
   private void addSTRecord(RecordWriter tmpfile, String name, String value)
   {
     HiFieldNode field = (HiFieldNode)this.fields.get(name);
 
     this.typeAppender.append(tmpfile);
     field.append(tmpfile);
     tmpfile.appendRecordValue(name, value);
   }
 }