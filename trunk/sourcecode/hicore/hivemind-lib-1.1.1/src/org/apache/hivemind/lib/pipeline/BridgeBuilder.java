 package org.apache.hivemind.lib.pipeline;
 
 import java.lang.reflect.Constructor;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.service.BodyBuilder;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.service.ClassFactory;
 import org.apache.hivemind.service.MethodIterator;
 import org.apache.hivemind.service.MethodSignature;
 
 class BridgeBuilder
 {
   private ErrorLog _errorLog;
   private String _serviceId;
   private Class _serviceInterface;
   private Class _filterInterface;
   private ClassFab _classFab;
   private FilterMethodAnalyzer _filterMethodAnalyzer;
   private Constructor _constructor;
 
   BridgeBuilder(ErrorLog errorLog, String serviceId, Class serviceInterface, Class filterInterface, ClassFactory classFactory)
   {
     this._errorLog = errorLog;
     this._serviceId = serviceId;
     this._serviceInterface = serviceInterface;
     this._filterInterface = filterInterface;
 
     String name = ClassFabUtils.generateClassName(this._serviceInterface);
 
     this._classFab = classFactory.newClass(name, Object.class);
 
     this._filterMethodAnalyzer = new FilterMethodAnalyzer(serviceInterface);
   }
 
   private void createClass()
   {
     List serviceMethods = new ArrayList();
     List filterMethods = new ArrayList();
 
     createInfrastructure();
 
     MethodIterator mi = new MethodIterator(this._serviceInterface);
 
     while (mi.hasNext())
     {
       serviceMethods.add(mi.next());
     }
 
     boolean toStringMethodExists = mi.getToString();
 
     mi = new MethodIterator(this._filterInterface);
 
     while (mi.hasNext())
     {
       filterMethods.add(mi.next());
     }
 
     while (!(serviceMethods.isEmpty()))
     {
       MethodSignature ms = (MethodSignature)serviceMethods.remove(0);
 
       addBridgeMethod(ms, filterMethods);
     }
 
     reportExtraFilterMethods(filterMethods);
 
     if (!(toStringMethodExists)) {
       ClassFabUtils.addToStringMethod(this._classFab, PipelineMessages.bridgeInstanceDescription(this._serviceId, this._serviceInterface));
     }
 
     Class bridgeClass = this._classFab.createClass();
 
     this._constructor = bridgeClass.getConstructors()[0];
   }
 
   private void createInfrastructure()
   {
     this._classFab.addField("_next", this._serviceInterface);
     this._classFab.addField("_filter", this._filterInterface);
 
     this._classFab.addConstructor(new Class[] { this._serviceInterface, this._filterInterface }, null, "{ _next = $1; _filter = $2; }");
 
     this._classFab.addInterface(this._serviceInterface);
   }
 
   public Object instantiateBridge(Object nextBridge, Object filter)
   {
     if (this._constructor == null) {
       createClass();
     }
     try
     {
       return this._constructor.newInstance(new Object[] { nextBridge, filter });
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ex);
     }
   }
 
   private void reportExtraFilterMethods(List filterMethods)
   {
     Iterator i = filterMethods.iterator();
 
     while (i.hasNext())
     {
       MethodSignature ms = (MethodSignature)i.next();
 
       this._errorLog.error(PipelineMessages.extraFilterMethod(ms, this._filterInterface, this._serviceInterface, this._serviceId), null, null);
     }
   }
 
   private void addBridgeMethod(MethodSignature ms, List filterMethods)
   {
     Iterator i = filterMethods.iterator();
 
     while (i.hasNext())
     {
       MethodSignature fms = (MethodSignature)i.next();
 
       int position = this._filterMethodAnalyzer.findServiceInterfacePosition(ms, fms);
 
       if (position >= 0)
       {
         addBridgeMethod(position, ms, fms);
         i.remove();
         return;
       }
     }
 
     String message = PipelineMessages.unmatchedServiceMethod(ms, this._filterInterface);
 
     this._errorLog.error(message, null, null);
 
     BodyBuilder b = new BodyBuilder();
 
     b.add("throw new org.apache.hivemind.ApplicationRuntimeException(");
     b.addQuoted(message);
     b.addln(");");
 
     this._classFab.addMethod(1, ms, b.toString());
   }
 
   private void addBridgeMethod(int position, MethodSignature ms, MethodSignature fms)
   {
     StringBuffer buffer = new StringBuffer(100);
 
     if (ms.getReturnType() != Void.TYPE) {
       buffer.append("return ");
     }
     buffer.append("_filter.");
     buffer.append(ms.getName());
     buffer.append("(");
 
     boolean comma = false;
     int filterParameterCount = fms.getParameterTypes().length;
 
     for (int i = 0; i < position; ++i)
     {
       if (comma) {
         buffer.append(", ");
       }
       buffer.append("$");
 
       buffer.append(i + 1);
 
       comma = true;
     }
 
     if (comma) {
       buffer.append(", ");
     }
 
     buffer.append("_next");
 
     for (i = position + 1; i < filterParameterCount; ++i)
     {
       buffer.append(", $");
       buffer.append(i);
     }
 
     buffer.append(");");
 
     this._classFab.addMethod(1, ms, buffer.toString());
   }
 }