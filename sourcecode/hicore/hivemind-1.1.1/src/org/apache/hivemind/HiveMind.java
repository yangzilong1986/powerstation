 package org.apache.hivemind;
 
 import java.util.Collection;
 
 public final class HiveMind
 {
   public static final String THREAD_EVENT_NOTIFIER_SERVICE = "hivemind.ThreadEventNotifier";
   public static final String THREAD_LOCALE_SERVICE = "hivemind.ThreadLocale";
   public static final String INTERFACE_SYNTHESIZER_SERVICE = "hivemind.InterfaceSynthesizer";
   public static final Object INTROSPECTOR_MUTEX = new Object();
 
   public static ApplicationRuntimeException createRegistryShutdownException()
   {
     return new ApplicationRuntimeException(HiveMindMessages.registryShutdown());
   }
 
   public static Location findLocation(Object[] locations)
   {
     for (int i = 0; i < locations.length; ++i)
     {
       Object location = locations[i];
 
       Location result = getLocation(location);
 
       if (result != null) {
         return result;
       }
     }
 
     return null;
   }
 
   public static Location getLocation(Object object)
   {
     if (object == null) {
       return null;
     }
     if (object instanceof Location) {
       return ((Location)object);
     }
     if (object instanceof Locatable)
     {
       Locatable locatable = (Locatable)object;
 
       return locatable.getLocation();
     }
 
     return null;
   }
 
   public static String getLocationString(Object object)
   {
     Location l = getLocation(object);
 
     if (l != null) {
       return l.toString();
     }
     return HiveMindMessages.unknownLocation();
   }
 
   public static boolean isBlank(String string)
   {
     if ((string == null) || (string.length() == 0)) {
       return true;
     }
 
     return (string.trim().length() != 0);
   }
 
   public static boolean isNonBlank(String string)
   {
     return (!(isBlank(string)));
   }
 
   public static void setLocation(Object holder, Location location)
   {
     if ((holder == null) || (!(holder instanceof LocationHolder)))
       return;
     LocationHolder lh = (LocationHolder)holder;
 
     lh.setLocation(location);
   }
 
   public static boolean isEmpty(Collection c)
   {
     return ((c == null) || (c.isEmpty()));
   }
 }