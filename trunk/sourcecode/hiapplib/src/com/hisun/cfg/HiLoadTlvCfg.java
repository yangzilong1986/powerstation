/*    */ package com.hisun.cfg;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class HiLoadTlvCfg
/*    */ {
/*    */   public static void load(Element cfgRoot, Map cfgMap, HiTlvItem rootTlv)
/*    */     throws HiException
/*    */   {
/* 21 */     if (cfgRoot == null) {
/* 22 */       throw new HiException("231500", " is null ");
/*    */     }
/*    */ 
/* 25 */     rootTlv.load(cfgRoot);
/*    */ 
/* 27 */     Iterator it = cfgRoot.elementIterator("TLV");
/*    */ 
/* 31 */     while (it.hasNext()) {
/* 32 */       Element itemNode = (Element)it.next();
/* 33 */       HiTlvItem item = new HiTlvItem();
/* 34 */       item.init(rootTlv, itemNode);
/*    */ 
/* 36 */       cfgMap.put(item.tag, item);
/*    */     }
/*    */   }
/*    */ }