 package org.apache.hivemind.util;
 
 import java.util.Iterator;
 
 public class EventListenerList
 {
   private static final int START_SIZE = 5;
   private Object[] _listeners;
   private int _count;
   private int _iteratorCount;
   private int _uid;
 
   public synchronized Iterator getListeners()
   {
     this._iteratorCount += 1;
 
     return new ListenerIterator(null);
   }
 
   public synchronized void addListener(Object listener)
   {
     copyOnWrite(this._count + 1);
 
     this._listeners[this._count] = listener;
 
     this._count += 1;
   }
 
   public synchronized void removeListener(Object listener)
   {
     for (int i = 0; i < this._count; ++i)
     {
       if (this._listeners[i] != listener)
         continue;
       removeListener(i);
       return;
     }
   }
 
   private void removeListener(int index)
   {
     copyOnWrite(this._count);
 
     this._listeners[index] = this._listeners[(this._count - 1)];
 
     this._listeners[(this._count - 1)] = null;
 
     this._count -= 1;
   }
 
   private void copyOnWrite(int requiredSize)
   {
     int size = (this._listeners == null) ? 0 : this._listeners.length;
 
     if ((this._iteratorCount <= 0) && (size >= requiredSize))
       return;
     int nominalSize = (size == 0) ? 5 : 2 * size;
 
     if (size >= requiredSize)
     {
       nominalSize = size;
     }
 
     int newSize = Math.max(requiredSize, nominalSize);
 
     Object[] newListeners = new Object[newSize];
 
     if (this._count > 0) {
       System.arraycopy(this._listeners, 0, newListeners, 0, this._count);
     }
     this._listeners = newListeners;
 
     this._iteratorCount = 0;
     this._uid += 1;
   }
 
   private synchronized void adjustIteratorCount(int iteratorUid)
   {
     if (this._uid == iteratorUid)
       this._iteratorCount -= 1;
   }
 
   private class ListenerIterator
     implements Iterator
   {
     private Object[] _localListeners;
     private int _localCount;
     private int _localUid;
     private int _pos;
     private final EventListenerList this$0;
 
     private ListenerIterator()
     {
       this.this$0 = ???;
       this._localListeners = ???._listeners;
       this._localCount = ???._count;
       this._localUid = ???._uid;
     }
 
     public boolean hasNext()
     {
       if (this._pos >= this._localCount)
       {
         this.this$0.adjustIteratorCount(this._localUid);
 
         this._localListeners = null;
         this._localCount = 0;
         this._localUid = -1;
         this._pos = 0;
 
         return false;
       }
 
       return true;
     }
 
     public Object next()
     {
       return this._localListeners[(this._pos++)];
     }
 
     public void remove()
     {
       throw new UnsupportedOperationException();
     }
 
     ListenerIterator(EventListenerList.1 x1)
     {
       this(x0);
     }
   }
 }