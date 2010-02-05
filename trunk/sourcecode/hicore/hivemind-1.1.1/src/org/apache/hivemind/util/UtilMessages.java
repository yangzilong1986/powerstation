 package org.apache.hivemind.util;
 
 import java.lang.reflect.Constructor;
 import java.net.MalformedURLException;
 import org.apache.hivemind.impl.AbstractMessages;
 import org.apache.hivemind.impl.MessageFormatter;
 
 class UtilMessages
 {
   protected static MessageFormatter _formatter = new MessageFormatter(UtilMessages.class);
 
   static String noSuchProperty(Object target, String propertyName)
   {
     return _formatter.format("no-such-property", target.getClass().getName(), propertyName);
   }
 
   static String noMatchingConstructor(Class targetClass)
   {
     return _formatter.format("no-matching-constructor", targetClass.getName());
   }
 
   static String invokeFailed(Constructor constructor, Throwable cause)
   {
     return _formatter.format("invoke-failed", constructor.getDeclaringClass().getName(), cause);
   }
 
   static String noPropertyWriter(String propertyName, Object target)
   {
     return _formatter.format("no-writer", propertyName, target);
   }
 
   static String writeFailure(String propertyName, Object target, Throwable cause)
   {
     return _formatter.format("write-failure", new Object[] { propertyName, target, cause });
   }
 
   static String noReader(String propertyName, Object target)
   {
     return _formatter.format("no-reader", propertyName, target);
   }
 
   static String readFailure(String propertyName, Object target, Throwable cause)
   {
     return _formatter.format("read-failure", propertyName, target, cause);
   }
 
   static String nullObject()
   {
     return _formatter.getMessage("null-object");
   }
 
   static String unableToIntrospect(Class targetClass, Throwable cause)
   {
     return _formatter.format("unable-to-introspect", targetClass.getName(), cause);
   }
 
   static String badFileURL(String path, Throwable cause)
   {
     return _formatter.format("bad-file-url", path, cause);
   }
 
   static String unableToReferenceContextPath(String path, MalformedURLException ex)
   {
     return _formatter.format("unable-to-reference-context-path", path, ex);
   }
 
   static String noPropertyEditor(String propertyName, Class targetClass)
   {
     return _formatter.format("no-property-editor", propertyName, targetClass.getName());
   }
 
   static String unableToConvert(String value, Class propertyType, String propertyName, Object target, Throwable cause)
   {
     return _formatter.format("unable-to-convert", new Object[] { value, propertyType.getName(), propertyName, target, cause });
   }
 
   static String unableToInstantiateInstanceOfClass(Class clazz, Throwable cause)
   {
     return _formatter.format("unable-to-instantiate-instance-of-class", clazz.getName(), cause);
   }
 }