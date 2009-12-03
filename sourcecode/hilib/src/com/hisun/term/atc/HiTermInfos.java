/*    */ package com.hisun.term.atc;
/*    */ 
/*    */ import com.hisun.message.HiContext;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class HiTermInfos
/*    */ {
/*    */   private static final String AGREEMENT_TERM_INFOS_KEY = "AGREEMENT_TERM_INFOS_KEY";
/*    */   private ConcurrentHashMap termInfoHashMap;
/*    */ 
/*    */   public HiTermInfos()
/*    */   {
/* 18 */     this.termInfoHashMap = new ConcurrentHashMap(10000);
/*    */   }
/*    */ 
/*    */   public HiTermInfos(ConcurrentHashMap termInfoHashMap) {
/* 22 */     this.termInfoHashMap = termInfoHashMap;
/*    */   }
/*    */ 
/*    */   public static synchronized HiTermInfos getInstance() {
/* 26 */     HiTermInfos instance = null;
/* 27 */     ConcurrentHashMap tihm = (ConcurrentHashMap)HiContext.getRootContext().getProperty("AGREEMENT_TERM_INFOS_KEY");
/* 28 */     if (tihm == null) {
/* 29 */       instance = new HiTermInfos();
/* 30 */       HiContext.getRootContext().setProperty("AGREEMENT_TERM_INFOS_KEY", instance.termInfoHashMap);
/*    */     } else {
/* 32 */       instance = new HiTermInfos(tihm);
/*    */     }
/* 34 */     return instance;
/*    */   }
/*    */ 
/*    */   public void add(HiTermInfo termInfo) {
/* 38 */     if ((termInfo != null) && (termInfo.getLOGICAL_ADDR() != null))
/* 39 */       this.termInfoHashMap.put(termInfo.getLOGICAL_ADDR(), termInfo);
/*    */   }
/*    */ 
/*    */   public void clear()
/*    */   {
/* 44 */     this.termInfoHashMap.clear();
/*    */   }
/*    */ 
/*    */   public HiTermInfo get(String logicalAddr) {
/* 48 */     return ((HiTermInfo)this.termInfoHashMap.get(logicalAddr));
/*    */   }
/*    */ 
/*    */   public static HiTermInfo getTermInfo(String logicalAddr) {
/* 52 */     return getInstance().get(logicalAddr);
/*    */   }
/*    */ }