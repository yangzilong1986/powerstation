 package org.apache.hivemind.order;
 
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.impl.AbstractMessages;
 import org.apache.hivemind.impl.MessageFormatter;
 import org.apache.hivemind.util.StringUtils;
 
 class OrdererMessages
 {
   protected static MessageFormatter _formatter = new MessageFormatter(OrdererMessages.class);
 
   static String duplicateName(String objectType, String objectName, Object newObject, Object existingObject)
   {
     return _formatter.format("duplicate-name", StringUtils.capitalize(objectType), objectName, HiveMind.getLocationString(existingObject));
   }
 
   static String exception(String objectType, Throwable cause)
   {
     return _formatter.format("exception", objectType, cause);
   }
 
   static String dupeLeader(String objectType, ObjectOrdering newOrdering, ObjectOrdering existingLeader)
   {
     return _formatter.format("dupe-leader", new Object[] { StringUtils.capitalize(objectType), newOrdering.getName(), existingLeader.getName(), HiveMind.getLocationString(existingLeader.getObject()) });
   }
 
   static String dupeTrailer(String objectType, ObjectOrdering newOrdering, ObjectOrdering existingTrailer)
   {
     return _formatter.format("dupe-trailer", new Object[] { StringUtils.capitalize(objectType), newOrdering.getName(), existingTrailer.getName(), HiveMind.getLocationString(existingTrailer.getObject()) });
   }
 
   static String dependencyCycle(String objectType, ObjectOrdering trigger, Throwable cause)
   {
     return _formatter.format("dependency-cycle", objectType, trigger.getName(), cause);
   }
 
   static String badDependency(String objectType, String dependencyName, ObjectOrdering ordering)
   {
     return _formatter.format("bad-dependency", objectType, dependencyName, ordering.getName());
   }
 }