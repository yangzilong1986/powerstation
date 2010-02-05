 package org.apache.hivemind.impl;
 
 import java.io.Serializable;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.internal.ser.ServiceSerializationHelper;
 import org.apache.hivemind.service.BodyBuilder;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.service.ClassFactory;
 import org.apache.hivemind.service.MethodIterator;
 import org.apache.hivemind.service.MethodSignature;
 
 public final class ProxyBuilder
 {
   private ServicePoint _point;
   private Class _serviceInterface;
   private ClassFab _classFab;
   private String _type;
 
   public ProxyBuilder(String type, ServicePoint point)
   {
     this(type, point, false);
   }
 
   public ProxyBuilder(String type, ServicePoint point, boolean outerProxy)
   {
     this._point = point;
     this._type = type;
     this._serviceInterface = point.getServiceInterface();
 
     Class declaredInterface = point.getDeclaredInterface();
 
     Module module = point.getModule();
     ClassFactory factory = (ClassFactory)module.getService("hivemind.ClassFactory", ClassFactory.class);
 
     boolean extendBeanClass = (outerProxy) && (!(declaredInterface.isInterface()));
     Class baseClass = Object.class;
 
     this._classFab = factory.newClass(ClassFabUtils.generateClassName(this._serviceInterface), baseClass);
 
     if (!(extendBeanClass)) {
       this._classFab.addInterface(this._serviceInterface);
     }
 
     if (outerProxy)
       addSerializable(point.getExtensionPointId());
   }
 
   private void addSerializable(String pointId)
   {
     this._classFab.addInterface(Serializable.class);
 
     BodyBuilder bb = new BodyBuilder();
 
     bb.add("return {0}.getServiceSerializationSupport().getServiceTokenForService(\"{1}\");", ServiceSerializationHelper.class.getName(), pointId);
 
     MethodSignature sig = new MethodSignature(Object.class, "writeReplace", null, null);
 
     this._classFab.addMethod(2, sig, bb.toString());
   }
 
   public ClassFab getClassFab()
   {
     return this._classFab;
   }
 
   public void addServiceMethods(String indirection)
   {
     BodyBuilder builder = new BodyBuilder();
 
     MethodIterator mi = new MethodIterator(this._serviceInterface);
     while (mi.hasNext())
     {
       MethodSignature m = mi.next();
       if (!(this._classFab.containsMethod(m)))
       {
         builder.clear();
         builder.begin();
         builder.add("return ($r) ");
         builder.add(indirection);
         builder.add(".");
         builder.add(m.getName());
         builder.addln("($$);");
         builder.end();
         this._classFab.addMethod(1, m, builder.toString());
       }
     }
 
     if (!(mi.getToString()))
       ClassFabUtils.addToStringMethod(this._classFab, "<" + this._type + " for " + this._point.getExtensionPointId() + "(" + this._serviceInterface.getName() + ")>");
   }
 }