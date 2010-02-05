 package org.apache.hivemind.service.impl;
 
 import java.util.HashMap;
 import java.util.Map;
 import org.apache.hivemind.service.ThreadLocalStorage;
 
 public class ThreadLocalStorageImpl
   implements ThreadLocalStorage
 {
   private final Map _map;
 
   public ThreadLocalStorageImpl()
   {
     this._map = new HashMap();
   }
 
   public Object get(String key) {
     return this._map.get(key);
   }
 
   public void put(String key, Object value)
   {
     this._map.put(key, value);
   }
 
   public void clear()
   {
     this._map.clear();
   }
 }