 package com.hisun.atc.rpt;
 
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 
 public class HiDataRecord
   implements HiReportConstants
 {
   public int type;
   public int seq;
   public Map vars;
   public String record;
 
   public HiDataRecord()
   {
     this.vars = new HashMap();
   }
 
   public void put(String name, String value) {
     this.vars.put(name.toUpperCase(), value);
   }
 
   public String get(String name) {
     return ((String)this.vars.get(name.toUpperCase()));
   }
 
   public Map getVars() {
     return this.vars;
   }
 
   public String toString() {
     StringBuffer buf = new StringBuffer();
     buf.append("记录:[").append(this.type).append("]:");
     if (this.seq != 0)
       buf.append(this.seq).append(":");
     Iterator it = this.vars.entrySet().iterator();
     while (it.hasNext()) {
       Map.Entry entry = (Map.Entry)it.next();
       buf.append(entry.getKey()).append("=").append(entry.getValue());
       if (it.hasNext())
         buf.append("|");
     }
     return buf.toString();
   }
 }