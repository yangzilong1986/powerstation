 package org.apache.hivemind.test;
 
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.log4j.AppenderSkeleton;
 import org.apache.log4j.spi.LoggingEvent;
 
 class StoreAppender extends AppenderSkeleton
 {
   private List _events;
 
   StoreAppender()
   {
     this._events = new ArrayList(0);
   }
 
   public List getEvents()
   {
     List result = new ArrayList(this._events);
 
     this._events.clear();
 
     return result;
   }
 
   protected void append(LoggingEvent event)
   {
     this._events.add(event);
   }
 
   public void close()
   {
   }
 
   public boolean requiresLayout()
   {
     return false;
   }
 }