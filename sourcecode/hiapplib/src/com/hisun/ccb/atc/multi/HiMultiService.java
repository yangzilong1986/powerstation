 package com.hisun.ccb.atc.multi;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class HiMultiService
 {
   public static Collection<HiETF> query(HiMultiDTO md)
     throws HiException
   {
     Collection result_Strs = md.getMultiQuery().process();
     Collection result_ETF = new ArrayList();
     int i = 1;
     for (String str : result_Strs)
     {
       HiETF recEtf = null;
 
       recEtf = HiETFFactory.createETF(str);
       recEtf.setName("REC_" + i);
       result_ETF.add(recEtf);
 
       ++i;
     }
 
     return result_ETF;
   }
 
   public static int getTotalPage(HiMultiDTO md)
     throws HiException
   {
     return md.getMultiQuery().getTotalPage();
   }
 
   public static int getCurrPageRecCounts(HiMultiDTO md)
     throws HiException
   {
     Collection result_Strs = md.getMultiQuery().process();
     return ((result_Strs != null) ? result_Strs.size() : 0);
   }
 
   public static int getTotalRec(HiMultiDTO md)
     throws HiException
   {
     return md.getMultiQuery().getTotalCounts();
   }
 }