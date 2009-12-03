/*     */ package com.hisun.pipeline;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.hivemind.ApplicationRuntimeException;
/*     */ import org.apache.hivemind.ErrorLog;
/*     */ import org.apache.hivemind.service.BodyBuilder;
/*     */ import org.apache.hivemind.service.ClassFab;
/*     */ import org.apache.hivemind.service.ClassFabUtils;
/*     */ import org.apache.hivemind.service.ClassFactory;
/*     */ import org.apache.hivemind.service.MethodIterator;
/*     */ import org.apache.hivemind.service.MethodSignature;
/*     */ 
/*     */ class BridgeBuilder
/*     */ {
/*     */   private ErrorLog _errorLog;
/*     */   private String _serviceId;
/*     */   private Class _serviceInterface;
/*     */   private Class _filterInterface;
/*     */   private ClassFab _classFab;
/*     */   private FilterMethodAnalyzer _filterMethodAnalyzer;
/*     */   private Constructor _constructor;
/*     */ 
/*     */   BridgeBuilder(ErrorLog errorLog, String serviceId, Class serviceInterface, Class filterInterface, ClassFactory classFactory)
/*     */   {
/*  45 */     this._errorLog = errorLog;
/*  46 */     this._serviceId = serviceId;
/*  47 */     this._serviceInterface = serviceInterface;
/*  48 */     this._filterInterface = filterInterface;
/*     */ 
/*  50 */     String name = ClassFabUtils.generateClassName(this._serviceInterface);
/*     */ 
/*  52 */     this._classFab = classFactory.newClass(name, Object.class);
/*     */ 
/*  54 */     this._filterMethodAnalyzer = new FilterMethodAnalyzer(serviceInterface);
/*     */   }
/*     */ 
/*     */   private void createClass()
/*     */   {
/*  59 */     List serviceMethods = new ArrayList();
/*  60 */     List filterMethods = new ArrayList();
/*     */ 
/*  62 */     createInfrastructure();
/*     */ 
/*  64 */     MethodIterator mi = new MethodIterator(this._serviceInterface);
/*     */ 
/*  66 */     while (mi.hasNext())
/*     */     {
/*  68 */       serviceMethods.add(mi.next());
/*     */     }
/*     */ 
/*  71 */     boolean toStringMethodExists = mi.getToString();
/*     */ 
/*  73 */     mi = new MethodIterator(this._filterInterface);
/*     */ 
/*  75 */     while (mi.hasNext())
/*     */     {
/*  77 */       filterMethods.add(mi.next());
/*     */     }
/*     */ 
/*  80 */     while (!(serviceMethods.isEmpty()))
/*     */     {
/*  82 */       MethodSignature ms = (MethodSignature)serviceMethods.remove(0);
/*     */ 
/*  84 */       addBridgeMethod(ms, filterMethods);
/*     */     }
/*     */ 
/*  87 */     reportExtraFilterMethods(filterMethods);
/*     */ 
/*  89 */     if (!(toStringMethodExists)) {
/*  90 */       ClassFabUtils.addToStringMethod(this._classFab, PipelineMessages.bridgeInstanceDescription(this._serviceId, this._serviceInterface));
/*     */     }
/*     */ 
/*  94 */     Class bridgeClass = this._classFab.createClass();
/*     */ 
/*  96 */     this._constructor = bridgeClass.getConstructors()[0];
/*     */   }
/*     */ 
/*     */   private void createInfrastructure()
/*     */   {
/* 101 */     this._classFab.addField("_next", this._serviceInterface);
/* 102 */     this._classFab.addField("_filter", this._filterInterface);
/*     */ 
/* 104 */     this._classFab.addConstructor(new Class[] { this._serviceInterface, this._filterInterface }, null, "{ _next = $1; _filter = $2; }");
/*     */ 
/* 107 */     this._classFab.addInterface(this._serviceInterface);
/*     */   }
/*     */ 
/*     */   public Object instantiateBridge(Object nextBridge, Object filter)
/*     */   {
/* 120 */     if (this._constructor == null) {
/* 121 */       createClass();
/*     */     }
/*     */     try
/*     */     {
/* 125 */       return this._constructor.newInstance(new Object[] { nextBridge, filter });
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 130 */       throw new ApplicationRuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void reportExtraFilterMethods(List filterMethods)
/*     */   {
/* 136 */     Iterator i = filterMethods.iterator();
/*     */ 
/* 138 */     while (i.hasNext())
/*     */     {
/* 140 */       MethodSignature ms = (MethodSignature)i.next();
/*     */ 
/* 142 */       this._errorLog.error(PipelineMessages.extraFilterMethod(ms, this._filterInterface, this._serviceInterface, this._serviceId), null, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addBridgeMethod(MethodSignature ms, List filterMethods)
/*     */   {
/* 160 */     Iterator i = filterMethods.iterator();
/*     */ 
/* 162 */     while (i.hasNext())
/*     */     {
/* 164 */       MethodSignature fms = (MethodSignature)i.next();
/*     */ 
/* 166 */       int position = this._filterMethodAnalyzer.findServiceInterfacePosition(ms, fms);
/*     */ 
/* 168 */       if (position >= 0)
/*     */       {
/* 170 */         addBridgeMethod(position, ms, fms);
/* 171 */         i.remove();
/* 172 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 176 */     String message = PipelineMessages.unmatchedServiceMethod(ms, this._filterInterface);
/*     */ 
/* 178 */     this._errorLog.error(message, null, null);
/*     */ 
/* 180 */     BodyBuilder b = new BodyBuilder();
/*     */ 
/* 182 */     b.add("throw new org.apache.hivemind.ApplicationRuntimeException(");
/* 183 */     b.addQuoted(message);
/* 184 */     b.addln(");");
/*     */ 
/* 186 */     this._classFab.addMethod(1, ms, b.toString());
/*     */   }
/*     */ 
/*     */   private void addBridgeMethod(int position, MethodSignature ms, MethodSignature fms)
/*     */   {
/* 196 */     StringBuffer buffer = new StringBuffer(100);
/*     */ 
/* 198 */     if (ms.getReturnType() != Void.TYPE) {
/* 199 */       buffer.append("return ");
/*     */     }
/* 201 */     buffer.append("_filter.");
/* 202 */     buffer.append(ms.getName());
/* 203 */     buffer.append("(");
/*     */ 
/* 205 */     boolean comma = false;
/* 206 */     int filterParameterCount = fms.getParameterTypes().length;
/*     */ 
/* 208 */     for (int i = 0; i < position; ++i)
/*     */     {
/* 210 */       if (comma) {
/* 211 */         buffer.append(", ");
/*     */       }
/* 213 */       buffer.append("$");
/*     */ 
/* 216 */       buffer.append(i + 1);
/*     */ 
/* 218 */       comma = true;
/*     */     }
/*     */ 
/* 221 */     if (comma) {
/* 222 */       buffer.append(", ");
/*     */     }
/*     */ 
/* 228 */     buffer.append("_next");
/*     */ 
/* 230 */     for (int i = position + 1; i < filterParameterCount; ++i)
/*     */     {
/* 232 */       buffer.append(", $");
/* 233 */       buffer.append(i);
/*     */     }
/*     */ 
/* 236 */     buffer.append(");");
/*     */ 
/* 241 */     this._classFab.addMethod(1, ms, buffer.toString());
/*     */   }
/*     */ }