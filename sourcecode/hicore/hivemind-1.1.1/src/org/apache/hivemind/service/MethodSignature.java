 package org.apache.hivemind.service;
 
 import java.lang.reflect.Method;
 
 public class MethodSignature
 {
   private int _hashCode;
   private Class _returnType;
   private String _name;
   private Class[] _parameterTypes;
   private Class[] _exceptionTypes;
 
   public MethodSignature(Class returnType, String name, Class[] parameterTypes, Class[] exceptionTypes)
   {
     this._hashCode = -1;
 
     this._returnType = returnType;
     this._name = name;
     this._parameterTypes = parameterTypes;
     this._exceptionTypes = exceptionTypes;
   }
 
   public MethodSignature(Method m)
   {
     this(m.getReturnType(), m.getName(), m.getParameterTypes(), m.getExceptionTypes());
   }
 
   public Class[] getExceptionTypes()
   {
     return this._exceptionTypes;
   }
 
   public String getName()
   {
     return this._name;
   }
 
   public Class[] getParameterTypes()
   {
     return this._parameterTypes;
   }
 
   public Class getReturnType()
   {
     return this._returnType;
   }
 
   public int hashCode()
   {
     if (this._hashCode == -1)
     {
       this._hashCode = this._returnType.hashCode();
 
       this._hashCode = (31 * this._hashCode + this._name.hashCode());
 
       int count = count(this._parameterTypes);
 
       for (int i = 0; i < count; ++i) {
         this._hashCode = (31 * this._hashCode + this._parameterTypes[i].hashCode());
       }
       count = count(this._exceptionTypes);
 
       for (i = 0; i < count; ++i) {
         this._hashCode = (31 * this._hashCode + this._exceptionTypes[i].hashCode());
       }
     }
     return this._hashCode;
   }
 
   private static int count(Object[] array)
   {
     return ((array == null) ? 0 : array.length);
   }
 
   public boolean equals(Object o)
   {
     if ((o == null) || (!(o instanceof MethodSignature))) {
       return false;
     }
     MethodSignature ms = (MethodSignature)o;
 
     if (this._returnType != ms._returnType) {
       return false;
     }
     if (!(this._name.equals(ms._name))) {
       return false;
     }
     if (mismatch(this._parameterTypes, ms._parameterTypes)) {
       return false;
     }
     return (!(mismatch(this._exceptionTypes, ms._exceptionTypes)));
   }
 
   private boolean mismatch(Class[] a1, Class[] a2)
   {
     int a1Count = count(a1);
     int a2Count = count(a2);
 
     if (a1Count != a2Count) {
       return true;
     }
 
     for (int i = 0; i < a1Count; ++i)
     {
       if (a1[i] != a2[i]) {
         return true;
       }
     }
     return false;
   }
 
   public String toString()
   {
     StringBuffer buffer = new StringBuffer();
 
     buffer.append(ClassFabUtils.getJavaClassName(this._returnType));
     buffer.append(" ");
     buffer.append(this._name);
     buffer.append("(");
 
     for (int i = 0; i < count(this._parameterTypes); ++i)
     {
       if (i > 0) {
         buffer.append(", ");
       }
       buffer.append(ClassFabUtils.getJavaClassName(this._parameterTypes[i]));
     }
 
     buffer.append(")");
 
     for (i = 0; i < count(this._exceptionTypes); ++i)
     {
       if (i == 0)
         buffer.append(" throws ");
       else {
         buffer.append(", ");
       }
       buffer.append(this._exceptionTypes[i].getName());
     }
 
     return buffer.toString();
   }
 
   public String getUniqueId()
   {
     StringBuffer buffer = new StringBuffer(this._name);
     buffer.append("(");
 
     for (int i = 0; i < count(this._parameterTypes); ++i)
     {
       if (i > 0) {
         buffer.append(",");
       }
       buffer.append(ClassFabUtils.getJavaClassName(this._parameterTypes[i]));
     }
 
     buffer.append(")");
 
     return buffer.toString();
   }
 
   public boolean isOverridingSignatureOf(MethodSignature ms)
   {
     if (this._returnType != ms._returnType) {
       return false;
     }
     if (!(this._name.equals(ms._name))) {
       return false;
     }
     if (mismatch(this._parameterTypes, ms._parameterTypes)) {
       return false;
     }
     return exceptionsEncompass(ms._exceptionTypes);
   }
 
   private boolean exceptionsEncompass(Class[] otherExceptions)
   {
     int ourCount = count(this._exceptionTypes);
     int otherCount = count(otherExceptions);
 
     if (ourCount == 0) {
       return (otherCount == 0);
     }
     boolean[] matched = new boolean[otherCount];
     int unmatched = otherCount;
 
     for (int i = 0; (i < ourCount) && (unmatched > 0); ++i)
     {
       for (int j = 0; j < otherCount; ++j)
       {
         if (matched[j] != 0)
         {
           continue;
         }
 
         if (!(this._exceptionTypes[i].isAssignableFrom(otherExceptions[j])))
           continue;
         matched[j] = true;
         --unmatched;
       }
 
     }
 
     return (unmatched == 0);
   }
 }