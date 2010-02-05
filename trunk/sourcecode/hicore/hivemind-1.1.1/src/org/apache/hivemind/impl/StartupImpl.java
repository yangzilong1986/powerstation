 package org.apache.hivemind.impl;
 
 import java.util.Iterator;
 import java.util.List;
 
 public class StartupImpl extends BaseLocatable
   implements Runnable
 {
   private List _runnables;
 
   public void run()
   {
     Iterator i = this._runnables.iterator();
     while (i.hasNext())
     {
       Runnable r = (Runnable)i.next();
 
       r.run();
     }
   }
 
   public void setRunnables(List list)
   {
     this._runnables = list;
   }
 }