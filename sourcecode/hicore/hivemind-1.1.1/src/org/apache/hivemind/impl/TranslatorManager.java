 package org.apache.hivemind.impl;
 
 import java.lang.reflect.Constructor;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.RegistryInfrastructure;
 import org.apache.hivemind.schema.Translator;
 import org.apache.hivemind.schema.rules.ClassTranslator;
 import org.apache.hivemind.schema.rules.InstanceTranslator;
 import org.apache.hivemind.schema.rules.ServiceTranslator;
 import org.apache.hivemind.schema.rules.SmartTranslator;
 
 public class TranslatorManager
 {
   static final Log LOG = LogFactory.getLog(TranslatorManager.class);
   public static final String TRANSLATORS_CONFIGURATION_ID = "hivemind.Translators";
   private ErrorHandler _errorHandler;
   private RegistryInfrastructure _registry;
   private Map _translatorClasses = new HashMap();
 
   private Map _translatorsCache = new HashMap();
   private boolean _translatorsLoaded;
 
   public TranslatorManager(RegistryInfrastructure registry, ErrorHandler errorHandler)
   {
     this._registry = registry;
     this._errorHandler = errorHandler;
 
     this._translatorsCache.put("class", new ClassTranslator());
     this._translatorsCache.put("service", new ServiceTranslator());
     this._translatorsCache.put("smart", new SmartTranslator());
     this._translatorsCache.put("instance", new InstanceTranslator());
 
     this._translatorClasses.put("smart", SmartTranslator.class);
   }
 
   public synchronized Translator getTranslator(String constructor)
   {
     if ((!(this._translatorsLoaded)) && (!(this._translatorsCache.containsKey(constructor)))) {
       loadTranslators();
     }
     Translator result = (Translator)this._translatorsCache.get(constructor);
 
     if (result == null)
     {
       result = constructTranslator(constructor);
       this._translatorsCache.put(constructor, result);
     }
 
     return result;
   }
 
   private Translator constructTranslator(String constructor)
   {
     String name = constructor;
     String initializer = null;
 
     int commax = constructor.indexOf(44);
 
     if (commax > 0)
     {
       name = constructor.substring(0, commax);
       initializer = constructor.substring(commax + 1);
     }
 
     Class translatorClass = findTranslatorClass(name);
 
     return createTranslator(translatorClass, initializer);
   }
 
   private Translator createTranslator(Class translatorClass, String initializer)
   {
     try
     {
       if (initializer == null) {
         return ((Translator)translatorClass.newInstance());
       }
       Constructor c = translatorClass.getConstructor(new Class[] { String.class });
 
       return ((Translator)c.newInstance(new Object[] { initializer }));
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ImplMessages.translatorInstantiationFailure(translatorClass, ex), ex);
     }
   }
 
   private Class findTranslatorClass(String translatorName)
   {
     Class result = (Class)this._translatorClasses.get(translatorName);
 
     if (result == null) {
       throw new ApplicationRuntimeException(ImplMessages.unknownTranslatorName(translatorName, "hivemind.Translators"));
     }
 
     return result;
   }
 
   private void loadTranslators()
   {
     this._translatorsLoaded = true;
 
     List contributions = this._registry.getConfiguration("hivemind.Translators", null);
 
     Map locations = new HashMap();
     locations.put("class", null);
 
     Iterator i = contributions.iterator();
     while (i.hasNext())
     {
       TranslatorContribution c = (TranslatorContribution)i.next();
 
       String name = c.getName();
       Location oldLocation = (Location)locations.get(name);
 
       if (oldLocation != null)
       {
         this._errorHandler.error(LOG, ImplMessages.duplicateTranslatorName(name, oldLocation), c.getLocation(), null);
       }
 
       locations.put(name, c.getLocation());
 
       Translator t = c.getTranslator();
 
       if (t != null)
       {
         this._translatorsCache.put(name, t);
       }
 
       Class tClass = c.getTranslatorClass();
 
       if (tClass == null)
       {
         this._errorHandler.error(LOG, ImplMessages.incompleteTranslator(c), c.getLocation(), null);
       }
 
       this._translatorClasses.put(name, tClass);
     }
   }
 }