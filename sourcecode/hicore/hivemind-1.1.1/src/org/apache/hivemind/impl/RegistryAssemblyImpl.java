 package org.apache.hivemind.impl;
 
 import java.util.ArrayList;
 import java.util.List;
 
 public class RegistryAssemblyImpl
   implements RegistryAssembly
 {
   private List _runnables;
 
   public RegistryAssemblyImpl()
   {
     this._runnables = new ArrayList();
   }
 
   public void addPostProcessor(Runnable postProcessor) {
     this._runnables.add(postProcessor);
   }
 
   public void performPostProcessing()
   {
     int count = this._runnables.size();
 
     for (int i = 0; i < count; ++i)
     {
       Runnable r = (Runnable)this._runnables.get(i);
 
       r.run();
     }
   }
 }