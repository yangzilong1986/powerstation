 package com.hisun.mng;
 
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import java.util.LinkedHashSet;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 
 public class HiMngUtils
   implements IConstants
 {
   public static final Set ipTable = new LinkedHashSet();
 
   public static void addElement(HiETF etf, String name, String text)
   {
     etf.setChildValue(name, text);
   }
 
   public static void createErrReturn(HiETF etf, String code, String msg) {
     addElement(etf, "RSP_CD", code);
     addElement(etf, "RSP_MSG", msg);
   }
 
   public static Element getRoot(HiMessage msg) {
     return ((Element)msg.getObjectHeadItem("MNGXML")); }
 
   public static void setRoot(HiMessage msg, Element root) {
     msg.setHeadItem("MNGXML", root);
   }
 
   class IP_PORT
   {
     public String ip;
     public String port;
 
     public IP_PORT(String paramString1, String paramString2)
     {
       this.ip = paramString1;
       this.port = sport;
     }
 
     public boolean equals(Object o) {
       if (o instanceof IP_PORT) {
         IP_PORT oo = (IP_PORT)(IP_PORT)o;
         return ((StringUtils.equals(this.ip, oo.ip)) && (StringUtils.equals(this.port, oo.port)));
       }
 
       return false;
     }
   }
 }