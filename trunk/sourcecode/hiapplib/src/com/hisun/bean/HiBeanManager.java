 package com.hisun.bean;
 
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.util.HiICSProperty;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 import org.springframework.context.ApplicationContext;
 import org.springframework.context.support.AbstractApplicationContext;
 import org.springframework.context.support.ClassPathXmlApplicationContext;
 
 public class HiBeanManager
 {
   private static Logger log = HiLog.getLogger("SYS.trc");
   private static Map managers = new HashMap();
   public static final String TSBEANPRE = "TRANS:";
   public static final String SERVERSPRINGCTX = "_CURSVRSPRINGCTX";
 
   public static ApplicationContext getSpringCtx()
   {
     String serverName = HiContext.getCurrentContext().getStrProp("SVR.name");
     return getSpringCtx(serverName, null);
   }
 
   public static ApplicationContext getSpringCtx(String config)
   {
     String serverName = HiContext.getCurrentContext().getStrProp("SVR.name");
     return getSpringCtx(serverName, config);
   }
 
   public static ApplicationContext getSpringCtx(String moduleName, String config)
   {
     if (managers.containsKey(moduleName))
     {
       return ((ApplicationContext)managers.get(moduleName));
     }
 
     if (config == null)
     {
       config = HiICSProperty.getProperty("spring.config");
     }
     if (config == null)
     {
       log.warn("springlibmanager->Read File sys.properties: spring.config is null!");
       return null;
     }
 
     String[] configLocations = StringUtils.split(config, ", ");
     ApplicationContext springCtx = new ClassPathXmlApplicationContext(configLocations, true);
     managers.put(moduleName, springCtx);
 
     return springCtx;
   }
 
   public static ApplicationContext loadSpringCtxAndBindServer(String config)
   {
     HiContext context = HiContext.getCurrentContext().getServerContext();
     if (context == null)
     {
       log.warn("getServerSpringCtx: getServerContext is null");
 
       return null;
     }
 
     String[] configLocations = StringUtils.split(config, ", ");
     ApplicationContext springCtx = new ClassPathXmlApplicationContext(configLocations, true);
 
     context.setProperty("_CURSVRSPRINGCTX", springCtx);
     return springCtx;
   }
 
   public static ApplicationContext getServerSpringCtx(String config)
   {
     ApplicationContext springCtx = getServerSpringCtx();
     if (springCtx != null)
     {
       return springCtx;
     }
     if (config == null)
     {
       log.warn("getServerSpringCtx Failure: config is null!");
       return null;
     }
 
     return loadSpringCtxAndBindServer(config);
   }
 
   public static ApplicationContext getServerSpringCtx()
   {
     HiContext context = HiContext.getCurrentContext();
     return ((ApplicationContext)(ApplicationContext)context.getServerContext().getProperty("_CURSVRSPRINGCTX"));
   }
 
   public static ApplicationContext getServerSpringCtx(HiContext context)
   {
     return ((ApplicationContext)(ApplicationContext)context.getServerContext().getProperty("_CURSVRSPRINGCTX"));
   }
 
   public static List getTransBeanList(String config)
   {
     ApplicationContext springCtx = getSpringCtx(config);
     List transList = new ArrayList();
     String[] transNames = springCtx.getBeanDefinitionNames();
     for (int i = 0; i < transNames.length; ++i)
     {
       if (!(transNames[i].startsWith("TRANS:")))
         continue;
       transList.add(springCtx.getBean(transNames[i]));
     }
 
     return transList;
   }
 
   public static Map getTransBeanMap(String config)
   {
     ApplicationContext springCtx = getServerSpringCtx(config);
     if (springCtx == null)
     {
       return null;
     }
     Map transMap = new HashMap();
     String[] transNames = springCtx.getBeanDefinitionNames();
 
     for (int i = 0; i < transNames.length; ++i)
     {
       if (!(transNames[i].startsWith("TRANS:")))
         continue;
       transMap.put(transNames[i], springCtx.getBean(transNames[i]));
     }
 
     return transMap;
   }
 
   public static String[] getAllBeanNames(String config)
   {
     ApplicationContext springCtx = getServerSpringCtx(config);
 
     return springCtx.getBeanDefinitionNames();
   }
 
   public static String[] getAllBeanNames(ApplicationContext springCtx)
   {
     return springCtx.getBeanDefinitionNames();
   }
 
   public static Object getBean(String beanName)
   {
     ApplicationContext springCtx = getServerSpringCtx();
     if (springCtx == null) {
       return null;
     }
     return springCtx.getBean(beanName);
   }
 
   public static void destroy(HiContext context)
   {
     HiContext serverContext = context.getServerContext();
     ApplicationContext springCtx = (ApplicationContext)(ApplicationContext)serverContext.getProperty("_CURSVRSPRINGCTX");
     if (springCtx == null) {
       return;
     }
     AbstractApplicationContext aspringCtx = (AbstractApplicationContext)springCtx;
     aspringCtx.close();
     serverContext.getServerContext().delProperty("_CURSVRSPRINGCTX");
   }
 
   public static void destroy(String module) {
     ApplicationContext springCtx = (ApplicationContext)managers.get(module);
     if (springCtx == null)
     {
       return;
     }
 
     AbstractApplicationContext aspringCtx = (AbstractApplicationContext)springCtx;
     aspringCtx.close();
     managers.remove(module);
   }
 
   public static void list() {
     Iterator it = managers.keySet().iterator();
     log.debug("Spring Manager Item number:" + managers.size());
     while (it.hasNext())
     {
       if (!(log.isDebugEnabled()))
         continue;
       log.debug("Spring Manager Item:" + it.next());
     }
   }
 }