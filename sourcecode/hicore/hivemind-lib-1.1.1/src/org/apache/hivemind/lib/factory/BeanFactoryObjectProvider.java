 package org.apache.hivemind.lib.factory;
 
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.lib.BeanFactory;
 import org.apache.hivemind.service.ObjectProvider;
 
 public class BeanFactoryObjectProvider
   implements ObjectProvider
 {
   public Object provideObject(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     if (HiveMind.isBlank(inputValue)) {
       return null;
     }
     int colonx = inputValue.indexOf(58);
 
     if (colonx < 0) {
       throw new ApplicationRuntimeException(FactoryMessages.invalidBeanTranslatorFormat(inputValue), location, null);
     }
 
     String serviceId = inputValue.substring(0, colonx);
 
     if (serviceId.length() == 0) {
       throw new ApplicationRuntimeException(FactoryMessages.invalidBeanTranslatorFormat(inputValue), location, null);
     }
 
     String locator = inputValue.substring(colonx + 1);
 
     if (locator.length() == 0) {
       throw new ApplicationRuntimeException(FactoryMessages.invalidBeanTranslatorFormat(inputValue), location, null);
     }
 
     BeanFactory f = (BeanFactory)contributingModule.getService(serviceId, BeanFactory.class);
 
     return f.get(locator);
   }
 }