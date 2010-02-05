 package org.apache.hivemind.service;
 
 import java.lang.reflect.Method;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.NoSuchElementException;
 import org.apache.hivemind.util.Defense;
 
 public class MethodIterator
 {
   private boolean _toString;
   private int _index = 0;
   private int _count;
   private List _signatures;
 
   public MethodIterator(Class subjectClass)
   {
     Defense.notNull(subjectClass, "subjectClass");
 
     Method[] methods = subjectClass.getMethods();
 
     Map map = new HashMap();
 
     for (int i = 0; i < methods.length; ++i) {
       processMethod(methods[i], map);
     }
     this._signatures = new ArrayList(map.values());
     this._count = this._signatures.size();
   }
 
   private void processMethod(Method m, Map map)
   {
     this._toString |= ClassFabUtils.isToString(m);
 
     MethodSignature sig = new MethodSignature(m);
     String uid = sig.getUniqueId();
 
     MethodSignature existing = (MethodSignature)map.get(uid);
 
     if ((existing == null) || (sig.isOverridingSignatureOf(existing)))
       map.put(uid, sig);
   }
 
   public boolean hasNext()
   {
     return (this._index < this._count);
   }
 
   public MethodSignature next()
   {
     if (this._index >= this._count) {
       throw new NoSuchElementException();
     }
     return ((MethodSignature)this._signatures.get(this._index++));
   }
 
   public boolean getToString()
   {
     return this._toString;
   }
 }