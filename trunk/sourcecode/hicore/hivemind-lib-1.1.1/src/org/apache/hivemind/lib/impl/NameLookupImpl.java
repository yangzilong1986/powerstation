 package org.apache.hivemind.lib.impl;
 
 import java.util.Hashtable;
 import javax.naming.Context;
 import javax.naming.InitialContext;
 import javax.naming.NamingException;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.lib.NameLookup;
 import org.apache.hivemind.lib.RemoteExceptionCoordinator;
 import org.apache.hivemind.lib.RemoteExceptionEvent;
 import org.apache.hivemind.lib.RemoteExceptionListener;
 
 public class NameLookupImpl
   implements NameLookup, RemoteExceptionListener
 {
   private RemoteExceptionCoordinator _coordinator;
   private Context _initialContext;
   private String _initialFactory;
   private String _URLPackages;
   private String _providerURL;
 
   public Object lookup(String name, Class expected)
   {
     Object raw;
     int i = 0;
     while (true)
     {
       Context context = null;
       raw = null;
       try
       {
         context = getInitialContext();
 
         raw = context.lookup(name);
       }
       catch (NamingException ex)
       {
         if (i++ == 0)
           this._coordinator.fireRemoteExceptionDidOccur(this, ex);
         else {
           throw new ApplicationRuntimeException(ImplMessages.unableToLookup(name, context), ex);
         }
       }
 
     }
 
     if (raw == null) {
       throw new ApplicationRuntimeException(ImplMessages.noObject(name, expected));
     }
     if (!(expected.isAssignableFrom(raw.getClass()))) {
       throw new ApplicationRuntimeException(ImplMessages.wrongType(name, raw, expected));
     }
     return raw;
   }
 
   private Context getInitialContext()
     throws NamingException
   {
     if (this._initialContext == null)
     {
       Hashtable properties = new Hashtable();
 
       if (!(HiveMind.isBlank(this._initialFactory))) {
         properties.put("java.naming.factory.initial", this._initialFactory);
       }
       if (!(HiveMind.isBlank(this._providerURL))) {
         properties.put("java.naming.provider.url", this._providerURL);
       }
       if (!(HiveMind.isBlank(this._URLPackages))) {
         properties.put("java.naming.factory.url.pkgs", this._URLPackages);
       }
       this._initialContext = constructContext(properties);
     }
 
     return this._initialContext;
   }
 
   protected Context constructContext(Hashtable properties)
     throws NamingException
   {
     return new InitialContext(properties);
   }
 
   public void remoteExceptionDidOccur(RemoteExceptionEvent event)
   {
     this._initialContext = null;
   }
 
   public void setInitialFactory(String string)
   {
     this._initialFactory = string;
   }
 
   public void setProviderURL(String string)
   {
     this._providerURL = string;
   }
 
   public void setURLPackages(String string)
   {
     this._URLPackages = string;
   }
 
   public void setCoordinator(RemoteExceptionCoordinator coordinator)
   {
     this._coordinator = coordinator;
   }
 }