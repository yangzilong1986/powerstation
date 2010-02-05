 package com.hisun.engine.invoke.impl;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiByteBuffer;
 import java.lang.reflect.Method;
 
 public class HiMethodItem
 {
   public Method method;
   public Object object;
 
   public HiByteBuffer invoke(HiByteBuffer arg1)
     throws HiException
   {
     return invoke(arg1, null);
   }
 
   public HiByteBuffer invoke(HiByteBuffer arg1, HiMessageContext context) throws HiException
   {
     Object[] args = { arg1, context };
     try
     {
       return ((HiByteBuffer)this.method.invoke(this.object, args));
     }
     catch (Exception e)
     {
       throw new HiException("213145", new String[] { this.object.getClass().getName(), this.method.getName() }, e);
     }
   }
 
   public HiByteBuffer invoke(String arg1, HiMessageContext context) throws HiException
   {
     Object[] args = { arg1, context };
     try
     {
       return ((HiByteBuffer)this.method.invoke(this.object, args));
     }
     catch (Exception e)
     {
       throw new HiException("213145", new String[] { this.object.getClass().getName(), this.method.getName() }, e);
     }
   }
 }