 package org.apache.hivemind.util;
 
 import org.apache.hivemind.HiveMind;
 
 public class IdUtils
 {
   public static String qualify(String moduleId, String id)
   {
     if (id.indexOf(46) > 0) {
       return id;
     }
     return moduleId + "." + id;
   }
 
   public static String qualifyList(String sourceModuleId, String list)
   {
     if ((HiveMind.isBlank(list)) || (list.equals("*"))) {
       return list;
     }
     String[] items = StringUtils.split(list);
 
     for (int i = 0; i < items.length; ++i) {
       items[i] = qualify(sourceModuleId, items[i]);
     }
     return StringUtils.join(items, ',');
   }
 
   public static String stripModule(String id)
   {
     int lastPoint = id.lastIndexOf(46);
     if (lastPoint > 0) {
       return id.substring(lastPoint + 1, id.length());
     }
     return id;
   }
 
   public static String extractModule(String id)
   {
     int lastPoint = id.lastIndexOf(46);
     if (lastPoint > 0) {
       return id.substring(0, lastPoint);
     }
     return null;
   }
 }