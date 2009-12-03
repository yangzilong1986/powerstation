/*     */ package com.hisun.client;
/*     */ 
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.beanutils.PropertyUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public abstract class HiInvokeService
/*     */ {
/*  22 */   protected static HiConfigInfo info = new HiConfigInfo();
/*     */   private static final String SERVICE_CONFIG = "service_config.xml";
/*  24 */   protected HiETF etf = HiETFFactory.createETF();
/*  25 */   protected Logger logger = Logger.getLogger("invokeservice");
/*     */   protected String code;
/*     */ 
/*     */   public static HiInvokeService createService()
/*     */     throws Exception
/*     */   {
/*  36 */     if ("TCP".equalsIgnoreCase(info.getMode()))
/*  37 */       return createTCPService();
/*  38 */     if ("POJO".equalsIgnoreCase(info.getMode())) {
/*  39 */       return createPOJOService();
/*     */     }
/*  41 */     return createPOJOService();
/*     */   }
/*     */ 
/*     */   public static HiInvokeService createService(String code)
/*     */     throws Exception
/*     */   {
/*  48 */     if ("TCP".equalsIgnoreCase(info.getMode()))
/*  49 */       return createTCPService(code);
/*  50 */     if ("POJO".equalsIgnoreCase(info.getMode())) {
/*  51 */       return createPOJOService(code);
/*     */     }
/*  53 */     return createPOJOService(code);
/*     */   }
/*     */ 
/*     */   public static HiInvokeService createPOJOService()
/*     */   {
/*  58 */     return new HiPOJOInvokeService();
/*     */   }
/*     */ 
/*     */   public static HiInvokeService createTCPService() throws Exception {
/*  62 */     return new HiTCPInvokeService();
/*     */   }
/*     */ 
/*     */   public static HiInvokeService createPOJOService(String code) {
/*  66 */     return new HiPOJOInvokeService(code);
/*     */   }
/*     */ 
/*     */   public static HiInvokeService createTCPService(String code) throws Exception {
/*  70 */     return new HiTCPInvokeService(code);
/*     */   }
/*     */ 
/*     */   public HiInvokeService()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HiInvokeService(String code) {
/*  78 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public void setETF(HiETF etf) {
/*  82 */     this.etf = etf;
/*     */   }
/*     */ 
/*     */   public HiETF getETF() {
/*  86 */     return this.etf;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  91 */     return this.etf.toString();
/*     */   }
/*     */ 
/*     */   public void clear() {
/*  95 */     this.etf = HiETFFactory.createETF();
/*     */   }
/*     */ 
/*     */   public void put(String key, String value)
/*     */   {
/* 107 */     if ((key == null) || (value == null)) {
/* 108 */       throw new RuntimeException("key or value is null");
/*     */     }
/* 110 */     this.etf.setChildValue(key, value);
/*     */   }
/*     */ 
/*     */   public void putArray(String grpNam, List objectList)
/*     */     throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
/*     */   {
/* 127 */     putArray(this.etf, objectList, grpNam);
/*     */   }
/*     */ 
/*     */   public void putObject(String key, Object object)
/*     */     throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
/*     */   {
/* 144 */     if ((key == null) || (object == null)) {
/* 145 */       throw new RuntimeException("key or object is null");
/*     */     }
/* 147 */     HiETF grp = this.etf.addNode(key);
/* 148 */     putObject(grp, object);
/*     */   }
/*     */ 
/*     */   public void putObject(Object object)
/*     */     throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
/*     */   {
/* 162 */     if (object == null) {
/* 163 */       throw new RuntimeException("object is null");
/*     */     }
/* 165 */     putObject(this.etf, object);
/*     */   }
/*     */ 
/*     */   public HashMap invoke(String code)
/*     */     throws Exception
/*     */   {
/* 177 */     this.etf = doInvoke(code, this.etf);
/* 178 */     HiETF2HashMapList etf2HashMapList = new HiETF2HashMapList(this.etf);
/*     */ 
/* 180 */     return etf2HashMapList.map();
/*     */   }
/*     */ 
/*     */   public HashMap invoke()
/*     */     throws Exception
/*     */   {
/* 190 */     return invoke(this.code);
/*     */   }
/*     */ 
/*     */   public HiETF invokeRetETF(String code)
/*     */     throws Exception
/*     */   {
/* 202 */     if (this.logger.isInfoEnabled()) {
/* 203 */       this.logger.info("request data:[" + code + ":" + this.etf + "]");
/*     */     }
/* 205 */     return (this.etf = doInvoke(code, this.etf));
/*     */   }
/*     */ 
/*     */   public HiETF invokeRetETF()
/*     */     throws Exception
/*     */   {
/* 215 */     return invokeRetETF(this.code);
/*     */   }
/*     */ 
/*     */   private void putObject(HiETF etf, Object object)
/*     */     throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
/*     */   {
/* 221 */     if (object == null) {
/* 222 */       throw new RuntimeException("object is null");
/*     */     }
/* 224 */     if (object instanceof HashMap) {
/* 225 */       putHashMap(etf, (HashMap)object);
/* 226 */       return;
/*     */     }
/*     */ 
/* 229 */     Map map = PropertyUtils.describe(object);
/* 230 */     Iterator iter = map.keySet().iterator();
/* 231 */     while (iter.hasNext()) {
/* 232 */       String name = (String)iter.next();
/* 233 */       if ("class".equals(name)) {
/*     */         continue;
/*     */       }
/* 236 */       Object value = map.get(name);
/* 237 */       if (value == null) {
/*     */         continue;
/*     */       }
/* 240 */       if (value instanceof String) {
/* 241 */         etf.setChildValue(name, value.toString());
/* 242 */       } else if (value instanceof List) {
/* 243 */         putArray(etf, (List)value, name);
/* 244 */       } else if (value instanceof HashMap) {
/* 245 */         HiETF node = etf.addNode(name);
/* 246 */         putHashMap(node, (HashMap)value);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void putArray(HiETF node, List objectList, String grpNam)
/*     */     throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
/*     */   {
/* 255 */     if ((grpNam == null) || (objectList == null)) {
/* 256 */       throw new RuntimeException("grpNam or objectList is null");
/*     */     }
/*     */ 
/* 259 */     for (int i = 0; i < objectList.size(); ++i) {
/* 260 */       HiETF grp = node.addNode(grpNam + "_" + (i + 1));
/* 261 */       putObject(grp, objectList.get(i));
/*     */     }
/* 263 */     node.setChildValue(grpNam + "_NUM", String.valueOf(objectList.size()));
/*     */   }
/*     */ 
/*     */   public void putHashMap(HiETF node, HashMap map)
/*     */     throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
/*     */   {
/* 281 */     for (Iterator iter = map.keySet().iterator(); iter.hasNext(); ) {
/* 282 */       String key = (String)iter.next();
/* 283 */       Object obj = map.get(key);
/* 284 */       if (obj instanceof String) {
/* 285 */         node.setChildValue(key, (String)obj);
/* 286 */       } else if (obj instanceof HashMap) {
/* 287 */         HiETF node1 = node.addNode(key);
/* 288 */         putHashMap(node1, (HashMap)obj);
/* 289 */       } else if (obj instanceof List) {
/* 290 */         putArray(node, (List)obj, key);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract HiETF doInvoke(String paramString, HiETF paramHiETF)
/*     */     throws Exception;
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  29 */       info.load("service_config.xml");
/*     */     } catch (Throwable t) {
/*  31 */       t.printStackTrace();
/*     */     }
/*     */   }
/*     */ }