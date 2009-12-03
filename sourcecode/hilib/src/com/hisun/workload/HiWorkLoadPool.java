/*    */ package com.hisun.workload;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import com.hisun.util.HiResource;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.digester.Digester;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class HiWorkLoadPool
/*    */ {
/*    */   public static final String DEFAULT_WORK_LOAD = "Default";
/*    */   private HashMap workLoadMap;
/* 24 */   private static HiWorkLoadPool instance = null;
/*    */ 
/*    */   public HiWorkLoadPool()
/*    */   {
/* 23 */     this.workLoadMap = new HashMap(); }
/*    */ 
/*    */   public static synchronized HiWorkLoadPool getInstance() throws HiException {
/* 26 */     if (instance == null) {
/* 27 */       String file = HiICSProperty.getProperty("workload.config");
/* 28 */       instance = loadWorkLoadConfig(file);
/* 29 */       instance.init();
/*    */     }
/* 31 */     return instance;
/*    */   }
/*    */ 
/*    */   public HiWorkLoad getDefaultWorkLoad() {
/* 35 */     return ((HiWorkLoad)this.workLoadMap.get("Default"));
/*    */   }
/*    */ 
/*    */   public HiWorkLoad getWorkLoad(String workId) {
/* 39 */     return ((HiWorkLoad)this.workLoadMap.get(workId));
/*    */   }
/*    */ 
/*    */   public void addWorkLoad(HiWorkLoad workLoad) throws HiException {
/* 43 */     if (this.workLoadMap.containsKey(workLoad.getName())) {
/* 44 */       throw new HiException("241147", workLoad.getName());
/*    */     }
/* 46 */     this.workLoadMap.put(workLoad.getName(), workLoad);
/*    */   }
/*    */ 
/*    */   public void init() {
/* 50 */     Iterator iter = this.workLoadMap.keySet().iterator();
/* 51 */     while (iter.hasNext())
/* 52 */       ((HiWorkLoad)this.workLoadMap.get(iter.next())).init();
/*    */   }
/*    */ 
/*    */   public static HiWorkLoadPool loadWorkLoadConfig(String file)
/*    */     throws HiException
/*    */   {
/* 68 */     SAXReader reader = new SAXReader();
/* 69 */     InputStream is = HiResource.getResourceAsStream(file);
/* 70 */     if (is == null) {
/* 71 */       throw new HiException("213302", file);
/*    */     }
/*    */ 
/* 74 */     Digester digester = new Digester();
/* 75 */     digester.setValidating(false);
/* 76 */     digester.addObjectCreate("WorkLoadPool", "com.hisun.workload.HiWorkLoadPool");
/*    */ 
/* 78 */     digester.addSetProperties("WorkLoadPool");
/* 79 */     digester.addObjectCreate("WorkLoadPool/WorkLoad", "com.hisun.workload.HiWorkLoad");
/*    */ 
/* 81 */     digester.addSetProperties("WorkLoadPool/WorkLoad");
/* 82 */     digester.addSetProperty("WorkLoadPool/WorkLoad/Param", "name", "value");
/*    */ 
/* 84 */     digester.addSetNext("WorkLoadPool/WorkLoad", "addWorkLoad", "com.hisun.workload.HiWorkLoad");
/*    */     try
/*    */     {
/* 88 */       HiWorkLoadPool workLoadPool = (HiWorkLoadPool)digester.parse(is);
/* 89 */       HiWorkLoadPool localHiWorkLoadPool1 = workLoadPool;
/*    */ 
/* 98 */       return localHiWorkLoadPool1;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/*    */     }
/*    */     finally
/*    */     {
/*    */       try
/*    */       {
/* 95 */         is.close();
/*    */       } catch (IOException e) {
/* 97 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */ }