 package com.hisun.engine.invoke.load;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map.Entry;
 import java.util.Set;
 
 public class HiBusDefDeclare extends HiEngineModel
 {
   private HashMap busMaps = new HashMap();
 
   public String getNodeName()
   {
     return "BusDefDeclare";
   }
 
   public void setBusDef(String strName, String strValue)
   {
     this.busMaps.put(strName.trim(), strValue.trim());
   }
 
   public void process(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (this.busMaps.isEmpty())
       return;
     HiETF etf = (HiETF)mess.getBody();
     Set set = this.busMaps.entrySet();
     Iterator iter = set.iterator();
     while (iter.hasNext()) {
       Map.Entry en = (Map.Entry)iter.next();
       String strName = (String)en.getKey();
       String strValue = (String)en.getValue();
       if (log.isDebugEnabled()) {
         log.debug(HiStringManager.getManager().getString("HiBusDefDeclare.process", strName, strValue));
       }
 
       etf.setChildValue(strName, strValue);
     }
   }
 }