 package org.apache.hivemind.lib.factory;
 
 import java.lang.reflect.Constructor;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.lib.BeanFactory;
 
 public class BeanFactoryImpl extends BaseLocatable
   implements BeanFactory
 {
   private ErrorLog _errorLog;
   private Class _vendType;
   private Map _contributions = new HashMap();
 
   private Map _cache = new HashMap();
   private boolean _defaultCacheable;
 
   public BeanFactoryImpl(ErrorLog errorLog, Class vendType, List contributions, boolean defaultCacheable)
   {
     this._errorLog = errorLog;
     this._vendType = vendType;
     this._defaultCacheable = defaultCacheable;
 
     processContributions(contributions);
   }
 
   public boolean contains(String locator)
   {
     int commax = locator.indexOf(44);
 
     String name = (commax < 0) ? locator.trim() : locator.substring(0, commax);
 
     return this._contributions.containsKey(name);
   }
 
   private void processContributions(List list)
   {
     Iterator i = list.iterator();
 
     while (i.hasNext())
     {
       BeanFactoryContribution c = (BeanFactoryContribution)i.next();
 
       Class beanClass = c.getBeanClass();
 
       if ((beanClass.isInterface()) || (beanClass.isArray()) || (beanClass.isPrimitive()))
       {
         this._errorLog.error(FactoryMessages.invalidContributionClass(c), c.getLocation(), null);
       }
 
       if (!(this._vendType.isAssignableFrom(beanClass)))
       {
         this._errorLog.error(FactoryMessages.wrongContributionType(c, this._vendType), c.getLocation(), null);
       }
 
       this._contributions.put(c.getName(), c);
     }
   }
 
   public synchronized Object get(String locator)
   {
     Object result = this._cache.get(locator);
 
     if (result == null) {
       result = create(locator);
     }
     return result;
   }
 
   private Object create(String locator)
   {
     int commax = locator.indexOf(44);
 
     String name = (commax < 0) ? locator.trim() : locator.substring(0, commax);
     String initializer = (commax < 0) ? null : locator.substring(commax + 1).trim();
 
     BeanFactoryContribution c = (BeanFactoryContribution)this._contributions.get(name);
 
     if (c == null) {
       throw new ApplicationRuntimeException(FactoryMessages.unknownContribution(name));
     }
     Object result = construct(c, initializer);
 
     if (c.getStoreResultInCache(this._defaultCacheable)) {
       this._cache.put(locator, result);
     }
     return result;
   }
 
   private Object construct(BeanFactoryContribution contribution, String initializer)
   {
     Class beanClass = contribution.getBeanClass();
     try
     {
       if (HiveMind.isBlank(initializer)) {
         return beanClass.newInstance();
       }
       Constructor c = beanClass.getConstructor(new Class[] { String.class });
 
       return c.newInstance(new Object[] { initializer });
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(FactoryMessages.unableToInstantiate(beanClass, ex), contribution.getLocation(), ex);
     }
   }
 }