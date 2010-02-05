 package org.apache.hivemind.util;
 
 import java.beans.FeatureDescriptor;
 import java.beans.PropertyDescriptor;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.StringTokenizer;
 import org.apache.hivemind.ApplicationRuntimeException;
 
 class ClassAdaptor
 {
   private final Map _propertyAdaptorMap = new HashMap();
 
   ClassAdaptor(PropertyDescriptor[] properties)
   {
     for (int i = 0; i < properties.length; ++i)
     {
       PropertyDescriptor d = properties[i];
 
       String name = d.getName();
 
       this._propertyAdaptorMap.put(name, new PropertyAdaptor(name, d.getPropertyType(), d.getReadMethod(), d.getWriteMethod()));
     }
   }
 
   public void write(Object target, String propertyName, Object value)
   {
     PropertyAdaptor a = getPropertyAdaptor(target, propertyName);
 
     a.write(target, value);
   }
 
   public void smartWrite(Object target, String propertyName, String value)
   {
     PropertyAdaptor a = getPropertyAdaptor(target, propertyName);
 
     a.smartWrite(target, value);
   }
 
   public Object read(Object target, String propertyName)
   {
     PropertyAdaptor a = getPropertyAdaptor(target, propertyName);
 
     return a.read(target);
   }
 
   public Class getPropertyType(Object target, String propertyName)
   {
     PropertyAdaptor a = getPropertyAdaptor(target, propertyName);
 
     return a.getPropertyType();
   }
 
   public boolean isReadable(String propertyName)
   {
     PropertyAdaptor result = (PropertyAdaptor)this._propertyAdaptorMap.get(propertyName);
 
     return ((result != null) && (result.isReadable()));
   }
 
   public boolean isWritable(String propertyName)
   {
     PropertyAdaptor result = (PropertyAdaptor)this._propertyAdaptorMap.get(propertyName);
 
     return ((result != null) && (result.isWritable()));
   }
 
   PropertyAdaptor getPropertyAdaptor(Object target, String propertyName)
   {
     PropertyAdaptor result = (PropertyAdaptor)this._propertyAdaptorMap.get(propertyName);
 
     if (result == null) {
       throw new ApplicationRuntimeException(UtilMessages.noSuchProperty(target, propertyName), target, null, null);
     }
 
     return result;
   }
 
   public List getReadableProperties()
   {
     List result = new ArrayList(this._propertyAdaptorMap.size());
 
     Iterator i = this._propertyAdaptorMap.values().iterator();
 
     while (i.hasNext())
     {
       PropertyAdaptor a = (PropertyAdaptor)i.next();
 
       if (a.isReadable()) {
         result.add(a.getPropertyName());
       }
     }
     return result;
   }
 
   public List getWriteableProperties()
   {
     List result = new ArrayList(this._propertyAdaptorMap.size());
 
     Iterator i = this._propertyAdaptorMap.values().iterator();
 
     while (i.hasNext())
     {
       PropertyAdaptor a = (PropertyAdaptor)i.next();
 
       if (a.isWritable()) {
         result.add(a.getPropertyName());
       }
     }
     return result;
   }
 
   public void configureProperties(Object target, String initializer)
   {
     StringTokenizer tokenizer = new StringTokenizer(initializer, ",");
 
     while (tokenizer.hasMoreTokens())
     {
       configurePropertyFromToken(target, tokenizer.nextToken());
     }
   }
 
   private void configurePropertyFromToken(Object target, String token)
   {
     int equalsx = token.indexOf(61);
 
     if (equalsx > 0)
     {
       String propertyName = token.substring(0, equalsx).trim();
       String value = token.substring(equalsx + 1);
 
       smartWrite(target, propertyName, value);
       return;
     }
 
     boolean negate = token.startsWith("!");
 
     String propertyName = (negate) ? token.substring(1) : token;
 
     Boolean value = (negate) ? Boolean.FALSE : Boolean.TRUE;
 
     write(target, propertyName, value);
   }
 }