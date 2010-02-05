 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiDataRecord;
 import com.hisun.atc.rpt.data.Matcher;
 import com.hisun.atc.rpt.data.Matchers;
 import com.hisun.atc.rpt.data.RecordWriter;
 import com.hisun.xml.Located;
 import java.math.BigInteger;
 import java.util.Iterator;
 import java.util.LinkedList;
 
 public class HiSumNode extends Located
 {
   String cntname;
   LinkedList suminfo;
   LinkedList where;
   Matcher m;
   private String sum;
   private String sum_alias;
   int count;
 
   public HiSumNode()
   {
     this.where = new LinkedList();
 
     this.m = Matchers.one();
   }
 
   public void setCount(String count)
   {
     this.cntname = count;
   }
 
   public void setWhere(String where)
   {
     this.m = parse(where);
   }
 
   public void setSum(String sum) {
     this.sum = sum;
   }
 
   public void setSum_alias(String sum_alias) {
     this.sum_alias = sum_alias;
   }
 
   public void init() {
     this.suminfo = initSumInfo(this.sum, this.sum_alias);
   }
 
   public void load(HiDataRecord rec)
   {
     boolean isok = this.m.match(rec);
     if (!(isok))
       return;
     if ((this.cntname != null) && (this.cntname.length() != 0)) {
       this.count += 1;
     }
 
     Iterator sit = this.suminfo.iterator();
     while (sit.hasNext()) {
       VarDef var = (VarDef)sit.next();
       String value = rec.get(var.name);
 
       BigInteger oldvalue = new BigInteger(var.value);
       oldvalue = oldvalue.add(new BigInteger(value));
       var.value = oldvalue.toString();
     }
   }
 
   public void append(RecordWriter tmpfile)
   {
     if ((this.cntname != null) && (this.cntname.length() != 0)) {
       tmpfile.appendRecordValue(this.cntname, Integer.toString(this.count));
 
       this.count = 0;
     }
 
     Iterator vvi = this.suminfo.iterator();
     while (vvi.hasNext()) {
       VarDef var1 = (VarDef)vvi.next();
       String v = formatMumber(var1.value);
       tmpfile.appendRecordValue(var1.alias, v);
 
       var1.value = "0";
     }
   }
 
   private String formatMumber(String number)
   {
     if (number.indexOf(46) == -1) {
       return number;
     }
 
     StringBuffer buf = new StringBuffer(number);
     while (buf.charAt(buf.length() - 1) == '0')
       buf.deleteCharAt(buf.length() - 1);
     if (buf.charAt(buf.length() - 1) == '.')
       buf.deleteCharAt(buf.length() - 1);
     return buf.toString();
   }
 
   private LinkedList initWhere(String where) {
     LinkedList list = this.where;
     if (where != null)
     {
       String[] ss = where.split("\\|");
       for (int i = 0; i < ss.length; ++i) {
         VarDef var = new VarDef();
         String[] ttt = ss[i].split("=");
         var.name = ttt[0];
         var.value = ttt[1];
         list.add(var);
       }
     }
     return list;
   }
 
   private LinkedList initSumInfo(String sum, String alias) {
     LinkedList list = new LinkedList();
 
     String[] ss = sum.split("\\|");
     String[] tt = alias.split("\\|");
     for (int i = 0; i < ss.length; ++i) {
       VarDef var = new VarDef();
       var.name = ss[i];
       var.alias = tt[i];
       var.value = "0";
       list.add(var);
     }
     return list;
   }
 
   public static Matcher parse(String condition) {
     if (condition != null)
     {
       String[] ss = condition.split("\\|");
       Matcher[] ms = new Matcher[ss.length];
       for (int i = 0; i < ss.length; ++i) {
         String[] t = ss[i].split("=");
         ms[i] = Matchers.withValue(t[0], t[1]);
       }
       return Matchers.and(ms);
     }
     return Matchers.one();
   }
 }