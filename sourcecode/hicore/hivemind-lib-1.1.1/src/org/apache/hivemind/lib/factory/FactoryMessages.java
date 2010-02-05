 package org.apache.hivemind.lib.factory;
 
 import org.apache.hivemind.impl.AbstractMessages;
 import org.apache.hivemind.impl.MessageFormatter;
 import org.apache.hivemind.service.ClassFabUtils;
 
 class FactoryMessages
 {
   protected static MessageFormatter _formatter = new MessageFormatter(FactoryMessages.class);
 
   public static String wrongContributionType(BeanFactoryContribution c, Class vendType)
   {
     return _formatter.format("wrong-contribution-type", c.getName(), c.getBeanClass().getName(), vendType);
   }
 
   public static String invalidContributionClass(BeanFactoryContribution c)
   {
     return _formatter.format("invalid-contribution-class", c.getName(), ClassFabUtils.getJavaClassName(c.getBeanClass()));
   }
 
   public static String unknownContribution(String name)
   {
     return _formatter.format("unknown-contribution", name);
   }
 
   public static String unableToInstantiate(Class objectClass, Throwable cause)
   {
     return _formatter.format("unable-to-instantiate", objectClass.getName(), cause);
   }
 
   public static String invalidBeanTranslatorFormat(String inputValue)
   {
     return _formatter.format("invalid-bean-translator-format", inputValue);
   }
 }