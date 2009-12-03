/*    */ package com.hisun.ccb.atc.multi;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class HiMultiService
/*    */ {
/*    */   public static Collection<HiETF> query(HiMultiDTO md)
/*    */     throws HiException
/*    */   {
/* 35 */     Collection result_Strs = md.getMultiQuery().process();
/* 36 */     Collection result_ETF = new ArrayList();
/* 37 */     int i = 1;
/* 38 */     for (String str : result_Strs)
/*    */     {
/* 40 */       HiETF recEtf = null;
/*    */ 
/* 42 */       recEtf = HiETFFactory.createETF(str);
/* 43 */       recEtf.setName("REC_" + i);
/* 44 */       result_ETF.add(recEtf);
/*    */ 
/* 46 */       ++i;
/*    */     }
/*    */ 
/* 49 */     return result_ETF;
/*    */   }
/*    */ 
/*    */   public static int getTotalPage(HiMultiDTO md)
/*    */     throws HiException
/*    */   {
/* 63 */     return md.getMultiQuery().getTotalPage();
/*    */   }
/*    */ 
/*    */   public static int getCurrPageRecCounts(HiMultiDTO md)
/*    */     throws HiException
/*    */   {
/* 78 */     Collection result_Strs = md.getMultiQuery().process();
/* 79 */     return ((result_Strs != null) ? result_Strs.size() : 0);
/*    */   }
/*    */ 
/*    */   public static int getTotalRec(HiMultiDTO md)
/*    */     throws HiException
/*    */   {
/* 93 */     return md.getMultiQuery().getTotalCounts();
/*    */   }
/*    */ }