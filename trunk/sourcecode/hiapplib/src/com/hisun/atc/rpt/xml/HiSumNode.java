/*     */ package com.hisun.atc.rpt.xml;
/*     */ 
/*     */ import com.hisun.atc.rpt.HiDataRecord;
/*     */ import com.hisun.atc.rpt.data.Matcher;
/*     */ import com.hisun.atc.rpt.data.Matchers;
/*     */ import com.hisun.atc.rpt.data.RecordWriter;
/*     */ import com.hisun.xml.Located;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ public class HiSumNode extends Located
/*     */ {
/*     */   String cntname;
/*     */   LinkedList suminfo;
/*     */   LinkedList where;
/*     */   Matcher m;
/*     */   private String sum;
/*     */   private String sum_alias;
/*     */   int count;
/*     */ 
/*     */   public HiSumNode()
/*     */   {
/*  24 */     this.where = new LinkedList();
/*     */ 
/*  26 */     this.m = Matchers.one();
/*     */   }
/*     */ 
/*     */   public void setCount(String count)
/*     */   {
/*  34 */     this.cntname = count;
/*     */   }
/*     */ 
/*     */   public void setWhere(String where)
/*     */   {
/*  39 */     this.m = parse(where);
/*     */   }
/*     */ 
/*     */   public void setSum(String sum) {
/*  43 */     this.sum = sum;
/*     */   }
/*     */ 
/*     */   public void setSum_alias(String sum_alias) {
/*  47 */     this.sum_alias = sum_alias;
/*     */   }
/*     */ 
/*     */   public void init() {
/*  51 */     this.suminfo = initSumInfo(this.sum, this.sum_alias);
/*     */   }
/*     */ 
/*     */   public void load(HiDataRecord rec)
/*     */   {
/*  70 */     boolean isok = this.m.match(rec);
/*  71 */     if (!(isok))
/*     */       return;
/*  73 */     if ((this.cntname != null) && (this.cntname.length() != 0)) {
/*  74 */       this.count += 1;
/*     */     }
/*     */ 
/*  77 */     Iterator sit = this.suminfo.iterator();
/*  78 */     while (sit.hasNext()) {
/*  79 */       VarDef var = (VarDef)sit.next();
/*  80 */       String value = rec.get(var.name);
/*     */ 
/*  85 */       BigInteger oldvalue = new BigInteger(var.value);
/*  86 */       oldvalue = oldvalue.add(new BigInteger(value));
/*  87 */       var.value = oldvalue.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void append(RecordWriter tmpfile)
/*     */   {
/*  94 */     if ((this.cntname != null) && (this.cntname.length() != 0)) {
/*  95 */       tmpfile.appendRecordValue(this.cntname, Integer.toString(this.count));
/*     */ 
/*  98 */       this.count = 0;
/*     */     }
/*     */ 
/* 102 */     Iterator vvi = this.suminfo.iterator();
/* 103 */     while (vvi.hasNext()) {
/* 104 */       VarDef var1 = (VarDef)vvi.next();
/* 105 */       String v = formatMumber(var1.value);
/* 106 */       tmpfile.appendRecordValue(var1.alias, v);
/*     */ 
/* 109 */       var1.value = "0";
/*     */     }
/*     */   }
/*     */ 
/*     */   private String formatMumber(String number)
/*     */   {
/* 118 */     if (number.indexOf(46) == -1) {
/* 119 */       return number;
/*     */     }
/*     */ 
/* 122 */     StringBuffer buf = new StringBuffer(number);
/* 123 */     while (buf.charAt(buf.length() - 1) == '0')
/* 124 */       buf.deleteCharAt(buf.length() - 1);
/* 125 */     if (buf.charAt(buf.length() - 1) == '.')
/* 126 */       buf.deleteCharAt(buf.length() - 1);
/* 127 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   private LinkedList initWhere(String where) {
/* 131 */     LinkedList list = this.where;
/* 132 */     if (where != null)
/*     */     {
/* 134 */       String[] ss = where.split("\\|");
/* 135 */       for (int i = 0; i < ss.length; ++i) {
/* 136 */         VarDef var = new VarDef();
/* 137 */         String[] ttt = ss[i].split("=");
/* 138 */         var.name = ttt[0];
/* 139 */         var.value = ttt[1];
/* 140 */         list.add(var);
/*     */       }
/*     */     }
/* 143 */     return list;
/*     */   }
/*     */ 
/*     */   private LinkedList initSumInfo(String sum, String alias) {
/* 147 */     LinkedList list = new LinkedList();
/*     */ 
/* 149 */     String[] ss = sum.split("\\|");
/* 150 */     String[] tt = alias.split("\\|");
/* 151 */     for (int i = 0; i < ss.length; ++i) {
/* 152 */       VarDef var = new VarDef();
/* 153 */       var.name = ss[i];
/* 154 */       var.alias = tt[i];
/* 155 */       var.value = "0";
/* 156 */       list.add(var);
/*     */     }
/* 158 */     return list;
/*     */   }
/*     */ 
/*     */   public static Matcher parse(String condition) {
/* 162 */     if (condition != null)
/*     */     {
/* 164 */       String[] ss = condition.split("\\|");
/* 165 */       Matcher[] ms = new Matcher[ss.length];
/* 166 */       for (int i = 0; i < ss.length; ++i) {
/* 167 */         String[] t = ss[i].split("=");
/* 168 */         ms[i] = Matchers.withValue(t[0], t[1]);
/*     */       }
/* 170 */       return Matchers.and(ms);
/*     */     }
/* 172 */     return Matchers.one();
/*     */   }
/*     */ }