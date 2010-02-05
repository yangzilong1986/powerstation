 package org.apache.hivemind.impl;
 
 import org.apache.hivemind.SymbolSource;
 
 public class SystemPropertiesSymbolSource
   implements SymbolSource
 {
   public String valueForSymbol(String name)
   {
     return System.getProperty(name);
   }
 }