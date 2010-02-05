 package org.apache.hivemind.schema.rules;
 
 import java.lang.reflect.Method;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.Element;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.schema.SchemaProcessor;
 
 public class InvokeParentRule extends BaseRule
 {
   private String _methodName;
   private int _depth = 1;
 
   public InvokeParentRule()
   {
   }
 
   public InvokeParentRule(String methodName)
   {
     this._methodName = methodName;
   }
 
   public void begin(SchemaProcessor processor, Element element)
   {
     Object child = processor.peek();
     Class childClass = (child == null) ? null : child.getClass();
     Object parent = processor.peek(this._depth);
     try
     {
       Method m = findMethod(parent, this._methodName, childClass);
 
       m.invoke(parent, new Object[] { child });
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(RulesMessages.errorInvokingMethod(this._methodName, parent, super.getLocation(), ex), super.getLocation(), ex);
     }
   }
 
   public String getMethodName()
   {
     return this._methodName;
   }
 
   public void setMethodName(String string)
   {
     this._methodName = string;
   }
 
   public int getDepth()
   {
     return this._depth;
   }
 
   public void setDepth(int i)
   {
     this._depth = i;
   }
 
   private Method findMethod(Object target, String name, Class parameterType)
     throws NoSuchMethodException
   {
     Method[] methods = target.getClass().getMethods();
 
     for (int i = 0; i < methods.length; ++i)
     {
       Method m = methods[i];
       Class[] parameterTypes = m.getParameterTypes();
 
       if (parameterTypes.length != 1) {
         continue;
       }
       if (!(m.getName().equals(name))) {
         continue;
       }
       if (((parameterType != null) && (parameterTypes[0].isAssignableFrom(parameterType))) || ((parameterType == null) && (!(parameterTypes[0].isPrimitive()))))
       {
         return m;
       }
     }
     throw new NoSuchMethodException(name);
   }
 }