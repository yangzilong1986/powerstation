 package org.apache.hivemind.service.impl;
 
 import java.util.Iterator;
 import java.util.List;
 import org.apache.hivemind.internal.ServicePoint;
 
 public class EagerLoader
   implements Runnable
 {
   private List _servicePoints;
 
   public void run()
   {
     Iterator i = this._servicePoints.iterator();
     while (i.hasNext())
     {
       ServicePoint point = (ServicePoint)i.next();
 
       point.forceServiceInstantiation();
     }
   }
 
   public void setServicePoints(List list)
   {
     this._servicePoints = list;
   }
 }