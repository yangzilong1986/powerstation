 package com.hisun.atc.rpt.data;
 
 import com.hisun.atc.rpt.HiDataRecord;
 
 public abstract class Matcher
 {
   public abstract boolean match(HiDataRecord paramHiDataRecord);
 
   public Matcher and(Matcher a)
   {
     return Matchers.and(this, a);
   }
 
   public Matcher or(Matcher a) {
     return Matchers.or(this, a);
   }
 }