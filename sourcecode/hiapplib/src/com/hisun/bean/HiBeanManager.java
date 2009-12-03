/*     */ package com.hisun.bean;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.context.support.AbstractApplicationContext;
/*     */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*     */ 
/*     */ public class HiBeanManager
/*     */ {
/*  22 */   private static Logger log = HiLog.getLogger("SYS.trc");
/*  23 */   private static Map managers = new HashMap();
/*     */   public static final String TSBEANPRE = "TRANS:";
/*     */   public static final String SERVERSPRINGCTX = "_CURSVRSPRINGCTX";
/*     */ 
/*     */   public static ApplicationContext getSpringCtx()
/*     */   {
/*  29 */     String serverName = HiContext.getCurrentContext().getStrProp("SVR.name");
/*  30 */     return getSpringCtx(serverName, null);
/*     */   }
/*     */ 
/*     */   public static ApplicationContext getSpringCtx(String config)
/*     */   {
/*  35 */     String serverName = HiContext.getCurrentContext().getStrProp("SVR.name");
/*  36 */     return getSpringCtx(serverName, config);
/*     */   }
/*     */ 
/*     */   public static ApplicationContext getSpringCtx(String moduleName, String config)
/*     */   {
/*  41 */     if (managers.containsKey(moduleName))
/*     */     {
/*  43 */       return ((ApplicationContext)managers.get(moduleName));
/*     */     }
/*     */ 
/*  47 */     if (config == null)
/*     */     {
/*  49 */       config = HiICSProperty.getProperty("spring.config");
/*     */     }
/*  51 */     if (config == null)
/*     */     {
/*  53 */       log.warn("springlibmanager->Read File sys.properties: spring.config is null!");
/*  54 */       return null;
/*     */     }
/*     */ 
/*  57 */     String[] configLocations = StringUtils.split(config, ", ");
/*  58 */     ApplicationContext springCtx = new ClassPathXmlApplicationContext(configLocations, true);
/*  59 */     managers.put(moduleName, springCtx);
/*     */ 
/*  61 */     return springCtx;
/*     */   }
/*     */ 
/*     */   public static ApplicationContext loadSpringCtxAndBindServer(String config)
/*     */   {
/*  72 */     HiContext context = HiContext.getCurrentContext().getServerContext();
/*  73 */     if (context == null)
/*     */     {
/*  75 */       log.warn("getServerSpringCtx: getServerContext is null");
/*     */ 
/*  78 */       return null;
/*     */     }
/*     */ 
/*  81 */     String[] configLocations = StringUtils.split(config, ", ");
/*  82 */     ApplicationContext springCtx = new ClassPathXmlApplicationContext(configLocations, true);
/*     */ 
/*  84 */     context.setProperty("_CURSVRSPRINGCTX", springCtx);
/*  85 */     return springCtx;
/*     */   }
/*     */ 
/*     */   public static ApplicationContext getServerSpringCtx(String config)
/*     */   {
/*  97 */     ApplicationContext springCtx = getServerSpringCtx();
/*  98 */     if (springCtx != null)
/*     */     {
/* 100 */       return springCtx;
/*     */     }
/* 102 */     if (config == null)
/*     */     {
/* 104 */       log.warn("getServerSpringCtx Failure: config is null!");
/* 105 */       return null;
/*     */     }
/*     */ 
/* 108 */     return loadSpringCtxAndBindServer(config);
/*     */   }
/*     */ 
/*     */   public static ApplicationContext getServerSpringCtx()
/*     */   {
/* 117 */     HiContext context = HiContext.getCurrentContext();
/* 118 */     return ((ApplicationContext)(ApplicationContext)context.getServerContext().getProperty("_CURSVRSPRINGCTX"));
/*     */   }
/*     */ 
/*     */   public static ApplicationContext getServerSpringCtx(HiContext context)
/*     */   {
/* 127 */     return ((ApplicationContext)(ApplicationContext)context.getServerContext().getProperty("_CURSVRSPRINGCTX"));
/*     */   }
/*     */ 
/*     */   public static List getTransBeanList(String config)
/*     */   {
/* 132 */     ApplicationContext springCtx = getSpringCtx(config);
/* 133 */     List transList = new ArrayList();
/* 134 */     String[] transNames = springCtx.getBeanDefinitionNames();
/* 135 */     for (int i = 0; i < transNames.length; ++i)
/*     */     {
/* 137 */       if (!(transNames[i].startsWith("TRANS:")))
/*     */         continue;
/* 139 */       transList.add(springCtx.getBean(transNames[i]));
/*     */     }
/*     */ 
/* 143 */     return transList;
/*     */   }
/*     */ 
/*     */   public static Map getTransBeanMap(String config)
/*     */   {
/* 148 */     ApplicationContext springCtx = getServerSpringCtx(config);
/* 149 */     if (springCtx == null)
/*     */     {
/* 151 */       return null;
/*     */     }
/* 153 */     Map transMap = new HashMap();
/* 154 */     String[] transNames = springCtx.getBeanDefinitionNames();
/*     */ 
/* 156 */     for (int i = 0; i < transNames.length; ++i)
/*     */     {
/* 158 */       if (!(transNames[i].startsWith("TRANS:")))
/*     */         continue;
/* 160 */       transMap.put(transNames[i], springCtx.getBean(transNames[i]));
/*     */     }
/*     */ 
/* 164 */     return transMap;
/*     */   }
/*     */ 
/*     */   public static String[] getAllBeanNames(String config)
/*     */   {
/* 169 */     ApplicationContext springCtx = getServerSpringCtx(config);
/*     */ 
/* 171 */     return springCtx.getBeanDefinitionNames();
/*     */   }
/*     */ 
/*     */   public static String[] getAllBeanNames(ApplicationContext springCtx)
/*     */   {
/* 176 */     return springCtx.getBeanDefinitionNames();
/*     */   }
/*     */ 
/*     */   public static Object getBean(String beanName)
/*     */   {
/* 181 */     ApplicationContext springCtx = getServerSpringCtx();
/* 182 */     if (springCtx == null) {
/* 183 */       return null;
/*     */     }
/* 185 */     return springCtx.getBean(beanName);
/*     */   }
/*     */ 
/*     */   public static void destroy(HiContext context)
/*     */   {
/* 190 */     HiContext serverContext = context.getServerContext();
/* 191 */     ApplicationContext springCtx = (ApplicationContext)(ApplicationContext)serverContext.getProperty("_CURSVRSPRINGCTX");
/* 192 */     if (springCtx == null) {
/* 193 */       return;
/*     */     }
/* 195 */     AbstractApplicationContext aspringCtx = (AbstractApplicationContext)springCtx;
/* 196 */     aspringCtx.close();
/* 197 */     serverContext.getServerContext().delProperty("_CURSVRSPRINGCTX");
/*     */   }
/*     */ 
/*     */   public static void destroy(String module) {
/* 201 */     ApplicationContext springCtx = (ApplicationContext)managers.get(module);
/* 202 */     if (springCtx == null)
/*     */     {
/* 204 */       return;
/*     */     }
/*     */ 
/* 207 */     AbstractApplicationContext aspringCtx = (AbstractApplicationContext)springCtx;
/* 208 */     aspringCtx.close();
/* 209 */     managers.remove(module);
/*     */   }
/*     */ 
/*     */   public static void list() {
/* 213 */     Iterator it = managers.keySet().iterator();
/* 214 */     log.debug("Spring Manager Item number:" + managers.size());
/* 215 */     while (it.hasNext())
/*     */     {
/* 217 */       if (!(log.isDebugEnabled()))
/*     */         continue;
/* 219 */       log.debug("Spring Manager Item:" + it.next());
/*     */     }
/*     */   }
/*     */ }