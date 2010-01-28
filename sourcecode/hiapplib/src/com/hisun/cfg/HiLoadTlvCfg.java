 package com.hisun.cfg;
 
 import com.hisun.exception.HiException;
 import java.util.Iterator;
 import java.util.Map;
 import org.dom4j.Element;
 
 public class HiLoadTlvCfg
 {
   public static void load(Element cfgRoot, Map cfgMap, HiTlvItem rootTlv)
     throws HiException
   {
     if (cfgRoot == null) {
       throw new HiException("231500", " is null ");
     }
 
     rootTlv.load(cfgRoot);
 
     Iterator it = cfgRoot.elementIterator("TLV");
 
     while (it.hasNext()) {
       Element itemNode = (Element)it.next();
       HiTlvItem item = new HiTlvItem();
       item.init(rootTlv, itemNode);
 
       cfgMap.put(item.tag, item);
     }
   }
 }