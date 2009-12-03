/*    */ package com.hisun.engine.invoke.load;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class HiBusDefDeclare extends HiEngineModel
/*    */ {
/* 41 */   private HashMap busMaps = new HashMap();
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 38 */     return "BusDefDeclare";
/*    */   }
/*    */ 
/*    */   public void setBusDef(String strName, String strValue)
/*    */   {
/* 44 */     this.busMaps.put(strName.trim(), strValue.trim());
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 51 */     HiMessage mess = messContext.getCurrentMsg();
/* 52 */     Logger log = HiLog.getLogger(mess);
/* 53 */     if (this.busMaps.isEmpty())
/* 54 */       return;
/* 55 */     HiETF etf = (HiETF)mess.getBody();
/* 56 */     Set set = this.busMaps.entrySet();
/* 57 */     Iterator iter = set.iterator();
/* 58 */     while (iter.hasNext()) {
/* 59 */       Map.Entry en = (Map.Entry)iter.next();
/* 60 */       String strName = (String)en.getKey();
/* 61 */       String strValue = (String)en.getValue();
/* 62 */       if (log.isDebugEnabled()) {
/* 63 */         log.debug(HiStringManager.getManager().getString("HiBusDefDeclare.process", strName, strValue));
/*    */       }
/*    */ 
/* 66 */       etf.setChildValue(strName, strValue);
/*    */     }
/*    */   }
/*    */ }