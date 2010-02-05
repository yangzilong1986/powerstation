 package com.hisun.workload;
 
 import com.hisun.exception.HiException;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiResource;
 import java.io.IOException;
 import java.io.InputStream;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Set;
 import org.apache.commons.digester.Digester;
 import org.dom4j.io.SAXReader;
 
 public class HiWorkLoadPool
 {
   public static final String DEFAULT_WORK_LOAD = "Default";
   private HashMap workLoadMap;
   private static HiWorkLoadPool instance = null;
 
   public HiWorkLoadPool()
   {
     this.workLoadMap = new HashMap(); }
 
   public static synchronized HiWorkLoadPool getInstance() throws HiException {
     if (instance == null) {
       String file = HiICSProperty.getProperty("workload.config");
       instance = loadWorkLoadConfig(file);
       instance.init();
     }
     return instance;
   }
 
   public HiWorkLoad getDefaultWorkLoad() {
     return ((HiWorkLoad)this.workLoadMap.get("Default"));
   }
 
   public HiWorkLoad getWorkLoad(String workId) {
     return ((HiWorkLoad)this.workLoadMap.get(workId));
   }
 
   public void addWorkLoad(HiWorkLoad workLoad) throws HiException {
     if (this.workLoadMap.containsKey(workLoad.getName())) {
       throw new HiException("241147", workLoad.getName());
     }
     this.workLoadMap.put(workLoad.getName(), workLoad);
   }
 
   public void init() {
     Iterator iter = this.workLoadMap.keySet().iterator();
     while (iter.hasNext())
       ((HiWorkLoad)this.workLoadMap.get(iter.next())).init();
   }
 
   public static HiWorkLoadPool loadWorkLoadConfig(String file)
     throws HiException
   {
     SAXReader reader = new SAXReader();
     InputStream is = HiResource.getResourceAsStream(file);
     if (is == null) {
       throw new HiException("213302", file);
     }
 
     Digester digester = new Digester();
     digester.setValidating(false);
     digester.addObjectCreate("WorkLoadPool", "com.hisun.workload.HiWorkLoadPool");
 
     digester.addSetProperties("WorkLoadPool");
     digester.addObjectCreate("WorkLoadPool/WorkLoad", "com.hisun.workload.HiWorkLoad");
 
     digester.addSetProperties("WorkLoadPool/WorkLoad");
     digester.addSetProperty("WorkLoadPool/WorkLoad/Param", "name", "value");
 
     digester.addSetNext("WorkLoadPool/WorkLoad", "addWorkLoad", "com.hisun.workload.HiWorkLoad");
     try
     {
       HiWorkLoadPool workLoadPool = (HiWorkLoadPool)digester.parse(is);
       HiWorkLoadPool localHiWorkLoadPool1 = workLoadPool;
 
       return localHiWorkLoadPool1;
     }
     catch (Exception e)
     {
     }
     finally
     {
       try
       {
         is.close();
       } catch (IOException e) {
         e.printStackTrace();
       }
     }
   }
 }