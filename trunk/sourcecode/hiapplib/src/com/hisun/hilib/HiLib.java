 package com.hisun.hilib;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiResource;
 import java.io.File;
 import java.io.InputStream;
 import java.lang.reflect.InvocationTargetException;
 import java.lang.reflect.Method;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Properties;
 import org.apache.commons.lang.StringUtils;
 
 public class HiLib
 {
   private static final String HIPUBJAR = "hipubatc.jar";
   private static final String IMP_PATH_DEFINE = "PACKAGE";
   private static final String METHOD = "execute";
   private static final String IMP_CLASS_DEFINE = "CLASS";
   private static Logger log = HiLog.getLogger("lib.trc");
   private static Object _lock = new Object();
   private static HashMap entry = new HashMap();
 
   private static ArrayList entryIndex = new ArrayList();
   private static final String ATCLOCALDEF = "conf/atclocal.properties";
   private static final String LOCAL = "";
 
   public static synchronized void load(String pkgNS, String jarName)
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("load:[" + pkgNS + "]:[" + jarName + "]");
     }
 
     String[] packages = StringUtils.split(jarName, ", ");
     for (int i = 0; i < packages.length; ++i)
       doLoad(pkgNS, packages[i]);
   }
 
   public static void doLoad(String pkgNS, String jarName)
     throws HiException
   {
     try
     {
       if (log.isDebugEnabled()) {
         log.debug("doLoad:[" + pkgNS + "]:[" + jarName + "]");
       }
       String propertyName = getPropertyName(jarName);
       if (StringUtils.isEmpty(propertyName)) {
         propertyName = pkgNS;
       }
 
       String resource = "conf" + File.separator + propertyName.toUpperCase() + ".properties";
 
       Properties p = new Properties();
       InputStream stream = HiResource.getResourceAsStream(resource);
       if (stream == null) {
         throw new HiException("215010", resource);
       }
 
       p.load(stream);
       stream.close();
       String packageName = p.getProperty("PACKAGE");
       String cls = p.getProperty("CLASS");
       p.clear();
       p = null;
 
       if (packageName == null) {
         throw new HiException("215001", jarName, "PACKAGE");
       }
 
       packageName = packageName.trim();
       for (int i = 0; i < entryIndex.size(); ++i) {
         HiPackageNSItem item = (HiPackageNSItem)entryIndex.get(i);
         if ((StringUtils.equalsIgnoreCase(item.packageNS, pkgNS)) && (StringUtils.equalsIgnoreCase(item.pkgName, packageName)))
         {
           return;
         }
       }
 
       if (cls == null) {
         loadPackage(pkgNS, packageName);
         return;
       }
 
       if (log.isDebugEnabled()) {
         log.debug(packageName + ":" + cls);
       }
       String[] names = StringUtils.split(cls, " , ");
 
       for (int i = 0; i < names.length; ++i)
       {
         String fullClsName = packageName.trim() + "." + names[i].trim();
         if (log.isDebugEnabled()) {
           log.debug(names[i]);
         }
 
         Class clazz = Class.forName(fullClsName);
         Method[] ms = clazz.getMethods();
         Object obj = clazz.newInstance();
 
         for (int j = 0; j < ms.length; ++j)
         {
           if (ms[j].getDeclaringClass() == Object.class) {
             continue;
           }
 
           String key = pkgNS + ":" + ms[j].getName();
 
           if (log.isDebugEnabled()) {
             log.debug("key1: " + key);
           }
           entry.put(key.toUpperCase(), new HiFunctionItem(obj, ms[j]));
         }
 
       }
 
       entryIndex.add(new HiPackageNSItem(pkgNS, packageName));
     }
     catch (Exception e) {
       if (e instanceof HiException) {
         throw ((HiException)e);
       }
       throw new HiException("215003", jarName, e);
     }
   }
 
   public static synchronized boolean loadComponent(String name)
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("loadLocalComponent:[" + name + "]");
     }
 
     String[] values = StringUtils.split(name, ":");
     if (values.length != 2) {
       throw new HiException("215004", name);
     }
     if (!(procPkgNS(values[0]))) {
       if (log.isDebugEnabled()) {
         log.debug("loadLocalComponent:[" + name + "], not find");
       }
       return false;
     }
     if (!(doLoadComponent(name, values[0], values[1]))) {
       throw new HiException("215004", name);
     }
 
     return true;
   }
 
   public static synchronized boolean loadComponent()
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("loadComponent");
     }
 
     String tmp = HiICSProperty.getProperty("pubatc.jar");
     if (StringUtils.isEmpty(tmp))
     {
       if (log.isDebugEnabled()) {
         log.debug("loadComponent,no startup: can't find item[pubatc.jar] at sys.properties");
       }
       return false;
     }
 
     load("PUB", tmp);
 
     return true;
   }
 
   public static boolean contains(String name)
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("contains:[" + name + "]");
     }
 
     return entry.containsKey(name);
   }
 
   private static boolean procPkgNS(String pkgNS)
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("procPkgNS:[" + pkgNS + "]");
     }
 
     boolean founded = false;
     for (int i = 0; i < entryIndex.size(); ++i) {
       HiPackageNSItem item = (HiPackageNSItem)entryIndex.get(i);
       if (StringUtils.equalsIgnoreCase(item.packageNS, pkgNS)) {
         founded = true;
         break;
       }
     }
 
     if (founded) {
       return true;
     }
 
     if (!("PUB".equalsIgnoreCase(pkgNS))) {
       return false;
     }
 
     String tmp = HiICSProperty.getProperty("pubatc.jar");
     if (StringUtils.isEmpty(tmp)) {
       tmp = "hipubatc.jar";
     }
     load("PUB", tmp);
     return true;
   }
 
   private static boolean doLoadComponent(String name, String pkgNS, String clsName)
     throws HiException
   {
     HiPackageNSItem item;
     if (log.isDebugEnabled()) {
       log.debug("doLoadComponent:[" + name + "]:[" + pkgNS + "]:[" + clsName + "]");
     }
 
     if (entry.containsKey(name.toUpperCase())) {
       return true;
     }
 
     boolean founded = false;
 
     for (int i = 0; i < entryIndex.size(); ++i) {
       item = (HiPackageNSItem)entryIndex.get(i);
       if (StringUtils.equalsIgnoreCase(item.packageNS, pkgNS)) {
         founded = true;
         break;
       }
     }
     if (!(founded)) {
       throw new HiException("215004", name);
     }
     founded = false;
 
     for (i = 0; i < entryIndex.size(); ++i) {
       item = (HiPackageNSItem)entryIndex.get(i);
       if (!(StringUtils.equalsIgnoreCase(item.packageNS, pkgNS))) {
         continue;
       }
       founded = true;
       String fullClsName = item.pkgName + "." + clsName;
       try
       {
         Class clazz = Class.forName(fullClsName);
         Object obj = clazz.newInstance();
         Method m = clazz.getMethod("execute", new Class[] { HiATLParam.class, HiMessageContext.class });
 
         entry.put(name.toUpperCase(), new HiFunctionItem(obj, m));
         label404: if (log.isDebugEnabled())
           log.debug("found component:[" + name + "]");
       }
       catch (ClassNotFoundException e) {
         log.error(e, e);
 
         break label404:
       }
       catch (InstantiationException e)
       {
         throw HiException.makeException(clsName, e);
       } catch (IllegalAccessException e) {
         throw HiException.makeException(clsName, e);
       } catch (SecurityException e) {
         throw HiException.makeException(clsName, e);
       } catch (NoSuchMethodException e) {
         throw HiException.makeException(clsName, e);
       }
     }
     return founded;
   }
 
   public static Object invoke(String name, HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("invoke:[" + name + "]");
     }
 
     String[] values = StringUtils.split(name, ":");
     if (values.length != 2) {
       throw new HiException("215004", name);
     }
     String packageName = getPackage(values[0], ctx);
     if (packageName == null) {
       throw new HiException("215004", name);
     }
 
     return invoke(packageName, name, args, ctx);
   }
 
   public static Object invoke(String packageName, String name, HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("invoke:[" + packageName + "]:[" + name + "]");
     }
 
     HiFunctionItem function = getFunction(packageName, name);
     if (function == null) {
       loadComponent(name);
     }
 
     function = getFunction(packageName, name);
     if (function == null) {
       throw new HiException("215006", name);
     }
     if (log.isDebugEnabled()) {
       log.debug(function);
     }
     Object[] params = new Object[2];
     params[0] = args;
     params[1] = ctx;
     try
     {
       return function.method.invoke(function.object, params);
     } catch (InvocationTargetException e) {
       Throwable t = e.getTargetException();
       if (t == null) {
         throw new HiException("215007", new String[] { name, e.getMessage() }, e);
       }
       if (t instanceof HiException) {
         throw ((HiException)t);
       }
       throw new HiException("215007", new String[] { name, t.getMessage() }, t);
     }
     catch (Exception e)
     {
       if (e instanceof HiException) {
         throw ((HiException)e);
       }
       throw new HiException("215007", new String[] { name, e.getMessage() }, e);
     }
   }
 
   private static String getPackage(String alias, HiMessageContext ctx)
     throws HiException
   {
     if (log.isDebugEnabled()) {
       log.debug("getPackage:[" + alias + "]");
     }
 
     if (StringUtils.equalsIgnoreCase(alias, "PUB")) {
       String tmp = HiICSProperty.getProperty("pubatc.jar");
       if (StringUtils.isEmpty(tmp)) {
         tmp = "hipubatc.jar";
       }
       synchronized (_lock) {
         if (!(containtsEntryIndex("PUB"))) {
           load("PUB", tmp);
         }
       }
       return tmp;
     }
     return ctx.getStrProp("LIBDECLARE." + alias);
   }
 
   private static HiFunctionItem getFunction(String packageName, String name)
   {
     return ((HiFunctionItem)entry.get(name.toUpperCase()));
   }
 
   private static String getPropertyName(String packageName)
   {
     int idx1 = packageName.indexOf(47);
     if (idx1 == -1)
       idx1 = 0;
     int idx2 = packageName.indexOf(46);
     if (idx2 == -1) {
       idx2 = 0;
     }
     return packageName.substring(idx1, idx2);
   }
 
   private static void loadPackage(String pkgNS, String packageDef) {
     if (log.isDebugEnabled()) {
       log.debug("loadPackage:[" + pkgNS + "]:[" + packageDef + "]");
     }
     String[] names = StringUtils.split(packageDef, " , ");
     for (int i = 0; i < names.length; ++i)
       entryIndex.add(new HiPackageNSItem(pkgNS, names[i]));
   }
 
   private static boolean containtsEntryIndex(String pkgNS)
   {
     boolean founded = false;
     for (int i = 0; i < entryIndex.size(); ++i) {
       HiPackageNSItem NSItem = (HiPackageNSItem)entryIndex.get(i);
       if (StringUtils.equalsIgnoreCase(NSItem.packageNS, pkgNS)) {
         founded = true;
         break;
       }
     }
     return founded;
   }
 }