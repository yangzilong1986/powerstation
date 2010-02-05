 package org.apache.hivemind.util;
 
 import java.beans.PropertyEditor;
 import java.beans.PropertyEditorManager;
 import java.lang.reflect.Constructor;
 import java.lang.reflect.Method;
 import org.apache.hivemind.ApplicationRuntimeException;
 
 public class PropertyAdaptor
 {
   private String _propertyName;
   private Class _propertyType;
   private Method _readMethod;
   private Method _writeMethod;
 
   PropertyAdaptor(String propertyName, Class propertyType, Method readMethod, Method writeMethod)
   {
     this._propertyName = propertyName;
     this._propertyType = propertyType;
     this._readMethod = readMethod;
     this._writeMethod = writeMethod;
   }
 
   public String getReadMethodName()
   {
     return ((this._readMethod == null) ? null : this._readMethod.getName());
   }
 
   public String getWriteMethodName()
   {
     return ((this._writeMethod == null) ? null : this._writeMethod.getName());
   }
 
   public String getPropertyName()
   {
     return this._propertyName;
   }
 
   public Class getPropertyType()
   {
     return this._propertyType;
   }
 
   public void write(Object target, Object value)
   {
     if (this._writeMethod == null) {
       throw new ApplicationRuntimeException(UtilMessages.noPropertyWriter(this._propertyName, target), target, null, null);
     }
 
     try
     {
       this._writeMethod.invoke(target, new Object[] { value });
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(UtilMessages.writeFailure(this._propertyName, target, ex), target, null, ex);
     }
   }
 
   public void smartWrite(Object target, String value)
   {
     Object convertedValue = convertValueForAssignment(target, value);
 
     write(target, convertedValue);
   }
 
   private Object convertValueForAssignment(Object target, String value)
   {
     if ((value == null) || (this._propertyType.isInstance(value))) {
       return value;
     }
     PropertyEditor e = PropertyEditorManager.findEditor(this._propertyType);
 
     if (e == null)
     {
       Object convertedValue = instantiateViaStringConstructor(target, value);
 
       if (convertedValue != null) {
         return convertedValue;
       }
       throw new ApplicationRuntimeException(UtilMessages.noPropertyEditor(this._propertyName, target.getClass()));
     }
 
     try
     {
       e.setAsText(value);
 
       return e.getValue();
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(UtilMessages.unableToConvert(value, this._propertyType, this._propertyName, target, ex), null, ex);
     }
   }
 
   private Object instantiateViaStringConstructor(Object target, String value)
   {
     try
     {
       Constructor c = this._propertyType.getConstructor(new Class[] { String.class });
 
       return c.newInstance(new Object[] { value });
     }
     catch (Exception ex)
     {
     }
     return null;
   }
 
   public boolean isWritable()
   {
     return (this._writeMethod != null);
   }
 
   public Object read(Object target)
   {
     if (this._readMethod == null) {
       throw new ApplicationRuntimeException(UtilMessages.noReader(this._propertyName, target), target, null, null);
     }
 
     try
     {
       return this._readMethod.invoke(target, null);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(UtilMessages.readFailure(this._propertyName, target, ex), target, null, ex);
     }
   }
 
   public boolean isReadable()
   {
     return (this._readMethod != null);
   }
 }