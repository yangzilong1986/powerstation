 package org.apache.hivemind.service.impl;
 
 import java.util.Locale;
 import org.apache.hivemind.ServiceImplementationFactory;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.internal.Module;
 
 public class ThreadLocaleFactory
   implements ServiceImplementationFactory
 {
   public Object createCoreServiceImplementation(ServiceImplementationFactoryParameters factoryParameters)
   {
     Locale defaultLocale = factoryParameters.getInvokingModule().getLocale();
 
     return new ThreadLocaleImpl(defaultLocale);
   }
 }