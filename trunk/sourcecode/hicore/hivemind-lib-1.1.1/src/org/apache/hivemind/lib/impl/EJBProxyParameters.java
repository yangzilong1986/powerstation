 package org.apache.hivemind.lib.impl;
 
 import org.apache.hivemind.lib.NameLookup;
 
 public class EJBProxyParameters
 {
   private String _jndiName;
   private String _homeInterfaceClassName;
   private NameLookup _nameLookup;
 
   public String getJndiName()
   {
     return this._jndiName;
   }
 
   public void setJndiName(String string)
   {
     this._jndiName = string;
   }
 
   public String getHomeInterfaceClassName()
   {
     return this._homeInterfaceClassName;
   }
 
   public void setHomeInterfaceClassName(String string)
   {
     this._homeInterfaceClassName = string;
   }
 
   public void setNameLookup(NameLookup lookup)
   {
     this._nameLookup = lookup;
   }
 
   public NameLookup getNameLookup(NameLookup defaultValue)
   {
     if (this._nameLookup == null) {
       return defaultValue;
     }
     return this._nameLookup;
   }
 }