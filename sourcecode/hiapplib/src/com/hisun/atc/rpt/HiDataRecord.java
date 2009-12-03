/*    */ package com.hisun.atc.rpt;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class HiDataRecord
/*    */   implements HiReportConstants
/*    */ {
/*    */   public int type;
/*    */   public int seq;
/*    */   public Map vars;
/*    */   public String record;
/*    */ 
/*    */   public HiDataRecord()
/*    */   {
/* 18 */     this.vars = new HashMap();
/*    */   }
/*    */ 
/*    */   public void put(String name, String value) {
/* 22 */     this.vars.put(name.toUpperCase(), value);
/*    */   }
/*    */ 
/*    */   public String get(String name) {
/* 26 */     return ((String)this.vars.get(name.toUpperCase()));
/*    */   }
/*    */ 
/*    */   public Map getVars() {
/* 30 */     return this.vars;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 34 */     StringBuffer buf = new StringBuffer();
/* 35 */     buf.append("记录:[").append(this.type).append("]:");
/* 36 */     if (this.seq != 0)
/* 37 */       buf.append(this.seq).append(":");
/* 38 */     Iterator it = this.vars.entrySet().iterator();
/* 39 */     while (it.hasNext()) {
/* 40 */       Map.Entry entry = (Map.Entry)it.next();
/* 41 */       buf.append(entry.getKey()).append("=").append(entry.getValue());
/* 42 */       if (it.hasNext())
/* 43 */         buf.append("|");
/*    */     }
/* 45 */     return buf.toString();
/*    */   }
/*    */ }