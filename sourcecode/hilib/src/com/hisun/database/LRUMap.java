/*     */ package com.hisun.database;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class LRUMap
/*     */   implements Map
/*     */ {
/*  50 */   private static final Logger log = HiLog.getLogger("lrumap.trc");
/*     */   private DoubleLinkedList list;
/*     */   protected Map map;
/*     */   int hitCnt;
/*     */   int missCnt;
/*     */   int putCnt;
/*     */   int maxObjects;
/*     */   private int chunkSize;
/*     */ 
/*     */   public LRUMap()
/*     */   {
/*  58 */     this.hitCnt = 0;
/*     */ 
/*  60 */     this.missCnt = 0;
/*     */ 
/*  62 */     this.putCnt = 0;
/*     */ 
/*  65 */     this.maxObjects = -1;
/*     */ 
/*  68 */     this.chunkSize = 1;
/*     */ 
/*  78 */     this.list = new DoubleLinkedList();
/*     */ 
/*  82 */     this.map = new Hashtable();
/*     */   }
/*     */ 
/*     */   public LRUMap(int maxObjects)
/*     */   {
/*  94 */     this.maxObjects = maxObjects;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 104 */     return this.map.size();
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 114 */     this.map.clear();
/* 115 */     this.list.removeAll();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 125 */     return (this.map.size() == 0);
/*     */   }
/*     */ 
/*     */   public boolean containsKey(Object key)
/*     */   {
/* 135 */     return this.map.containsKey(key);
/*     */   }
/*     */ 
/*     */   public boolean containsValue(Object value)
/*     */   {
/* 145 */     return this.map.containsValue(value);
/*     */   }
/*     */ 
/*     */   public Collection values()
/*     */   {
/* 154 */     return this.map.values();
/*     */   }
/*     */ 
/*     */   public void putAll(Map source)
/*     */   {
/* 163 */     if (source == null)
/*     */       return;
/* 165 */     Set entries = source.entrySet();
/* 166 */     Iterator it = entries.iterator();
/* 167 */     while (it.hasNext())
/*     */     {
/* 169 */       Map.Entry entry = (Map.Entry)it.next();
/* 170 */       put(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object get(Object key)
/*     */   {
/* 181 */     Object retVal = null;
/*     */ 
/* 183 */     if (log.isDebugEnabled())
/*     */     {
/* 185 */       log.debug("getting item  for key " + key);
/*     */     }
/*     */ 
/* 188 */     LRUElementDescriptor me = (LRUElementDescriptor)this.map.get(key);
/*     */ 
/* 190 */     if (me != null)
/*     */     {
/* 192 */       this.hitCnt += 1;
/* 193 */       if (log.isDebugEnabled())
/*     */       {
/* 195 */         log.debug("LRUMap hit for " + key);
/*     */       }
/*     */ 
/* 198 */       retVal = me.getPayload();
/*     */ 
/* 200 */       this.list.makeFirst(me);
/*     */     }
/*     */     else
/*     */     {
/* 204 */       this.missCnt += 1;
/* 205 */       log.debug("LRUMap miss for " + key);
/*     */     }
/*     */ 
/* 209 */     return retVal;
/*     */   }
/*     */ 
/*     */   public Object getQuiet(Object key)
/*     */   {
/* 222 */     Object ce = null;
/*     */ 
/* 224 */     LRUElementDescriptor me = (LRUElementDescriptor)this.map.get(key);
/* 225 */     if (me != null)
/*     */     {
/* 227 */       if (log.isDebugEnabled())
/*     */       {
/* 229 */         log.debug("LRUMap quiet hit for " + key);
/*     */       }
/*     */ 
/* 232 */       ce = me.getPayload();
/*     */     }
/* 234 */     else if (log.isDebugEnabled())
/*     */     {
/* 236 */       log.debug("LRUMap quiet miss for " + key);
/*     */     }
/*     */ 
/* 239 */     return ce;
/*     */   }
/*     */ 
/*     */   public Object remove(Object key)
/*     */   {
/* 248 */     if (log.isDebugEnabled())
/*     */     {
/* 250 */       log.debug("removing item for key: " + key);
/*     */     }
/*     */ 
/* 254 */     LRUElementDescriptor me = (LRUElementDescriptor)this.map.remove(key);
/*     */ 
/* 256 */     if (me != null)
/*     */     {
/* 258 */       this.list.remove(me);
/*     */ 
/* 260 */       return me.getPayload();
/*     */     }
/*     */ 
/* 263 */     return null;
/*     */   }
/*     */ 
/*     */   public Object put(Object key, Object value)
/*     */   {
/* 272 */     this.putCnt += 1;
/*     */ 
/* 274 */     LRUElementDescriptor old = null;
/* 275 */     synchronized (this)
/*     */     {
/* 278 */       addFirst(key, value);
/*     */ 
/* 280 */       old = (LRUElementDescriptor)this.map.put(((LRUElementDescriptor)this.list.getFirst()).getKey(), this.list.getFirst());
/*     */ 
/* 283 */       if ((old != null) && (((LRUElementDescriptor)this.list.getFirst()).getKey().equals(old.getKey())))
/*     */       {
/* 285 */         this.list.remove(old);
/*     */       }
/*     */     }
/*     */ 
/* 289 */     int size = this.map.size();
/*     */ 
/* 292 */     if ((this.maxObjects >= 0) && (size > this.maxObjects))
/*     */     {
/* 294 */       if (log.isDebugEnabled())
/*     */       {
/* 296 */         log.debug("In memory limit reached, removing least recently used.");
/*     */       }
/*     */ 
/* 300 */       int chunkSizeCorrected = Math.min(size, getChunkSize());
/*     */ 
/* 302 */       if (log.isDebugEnabled())
/*     */       {
/* 304 */         log.debug("About to remove the least recently used. map size: " + size + ", max objects: " + this.maxObjects + ", items to spool: " + chunkSizeCorrected);
/*     */       }
/*     */ 
/* 312 */       for (int i = 0; i < chunkSizeCorrected; ++i)
/*     */       {
/* 314 */         synchronized (this)
/*     */         {
/* 316 */           if (this.list.getLast() != null)
/*     */           {
/* 318 */             if ((LRUElementDescriptor)this.list.getLast() != null)
/*     */             {
/* 320 */               processRemovedLRU(((LRUElementDescriptor)this.list.getLast()).getKey(), ((LRUElementDescriptor)this.list.getLast()).getPayload());
/*     */ 
/* 322 */               if (!(this.map.containsKey(((LRUElementDescriptor)this.list.getLast()).getKey())))
/*     */               {
/* 324 */                 log.error("update: map does not contain key: " + ((LRUElementDescriptor)this.list.getLast()).getKey());
/*     */ 
/* 326 */                 verifyCache();
/*     */               }
/* 328 */               if (this.map.remove(((LRUElementDescriptor)this.list.getLast()).getKey()) == null)
/*     */               {
/* 330 */                 log.warn("update: remove failed for key: " + ((LRUElementDescriptor)this.list.getLast()).getKey());
/*     */ 
/* 332 */                 verifyCache();
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 337 */               throw new Error("update: last.ce is null!");
/*     */             }
/* 339 */             this.list.removeLast();
/*     */           }
/*     */           else
/*     */           {
/* 343 */             verifyCache();
/* 344 */             throw new Error("update: last is null!");
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 349 */       if (log.isDebugEnabled())
/*     */       {
/* 351 */         log.debug("update: After spool map size: " + this.map.size());
/*     */       }
/* 353 */       if (this.map.size() != dumpCacheSize())
/*     */       {
/* 355 */         log.error("update: After spool, size mismatch: map.size() = " + this.map.size() + ", linked list size = " + dumpCacheSize());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 360 */     if (old != null)
/*     */     {
/* 362 */       return old.getPayload();
/*     */     }
/* 364 */     return null;
/*     */   }
/*     */ 
/*     */   private synchronized void addFirst(Object key, Object val)
/*     */   {
/* 376 */     LRUElementDescriptor me = new LRUElementDescriptor(key, val);
/* 377 */     this.list.addFirst(me);
/*     */   }
/*     */ 
/*     */   private int dumpCacheSize()
/*     */   {
/* 388 */     return this.list.size();
/*     */   }
/*     */ 
/*     */   public void dumpCacheEntries()
/*     */   {
/* 396 */     log.debug("dumpingCacheEntries");
/* 397 */     for (LRUElementDescriptor me = (LRUElementDescriptor)this.list.getFirst(); me != null; me = (LRUElementDescriptor)me.next)
/*     */     {
/* 399 */       if (!(log.isDebugEnabled()))
/*     */         continue;
/* 401 */       log.debug("dumpCacheEntries> key=" + me.getKey() + ", val=" + me.getPayload());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dumpMap()
/*     */   {
/* 411 */     log.debug("dumpingMap");
/* 412 */     for (Iterator itr = this.map.entrySet().iterator(); itr.hasNext(); )
/*     */     {
/* 414 */       Map.Entry e = (Map.Entry)itr.next();
/* 415 */       LRUElementDescriptor me = (LRUElementDescriptor)e.getValue();
/* 416 */       if (log.isDebugEnabled())
/*     */       {
/* 418 */         log.debug("dumpMap> key=" + e.getKey() + ", val=" + me.getPayload());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 428 */     StringBuffer buffer = new StringBuffer();
/* 429 */     buffer.append("\n");
/* 430 */     buffer.append("size: " + dumpCacheSize());
/* 431 */     buffer.append("\n");
/* 432 */     for (Iterator itr = this.map.entrySet().iterator(); itr.hasNext(); )
/*     */     {
/* 434 */       Map.Entry e = (Map.Entry)itr.next();
/* 435 */       LRUElementDescriptor me = (LRUElementDescriptor)e.getValue();
/* 436 */       buffer.append("dumpMap> key=" + e.getKey() + ", val=" + me.getPayload() + "\n");
/*     */     }
/* 438 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   protected void verifyCache()
/*     */   {
/* 446 */     if (log.isDebugEnabled())
/*     */       return;
/* 448 */     return;
/*     */   }
/*     */ 
/*     */   protected void verifyCache(Object key)
/*     */   {
/* 537 */     if (!(log.isDebugEnabled()))
/*     */     {
/* 539 */       return;
/*     */     }
/*     */ 
/* 542 */     boolean found = false;
/*     */ 
/* 545 */     for (LRUElementDescriptor li = (LRUElementDescriptor)this.list.getFirst(); li != null; li = (LRUElementDescriptor)li.next)
/*     */     {
/* 547 */       if (li.getKey() != key)
/*     */         continue;
/* 549 */       found = true;
/* 550 */       log.debug("verifycache(key) key match: " + key);
/* 551 */       break;
/*     */     }
/*     */ 
/* 554 */     if (found)
/*     */       return;
/* 556 */     log.error("verifycache(key), couldn't find key! : " + key);
/*     */   }
/*     */ 
/*     */   protected void processRemovedLRU(Object key, Object value)
/*     */   {
/* 569 */     if (!(log.isDebugEnabled()))
/*     */       return;
/* 571 */     log.debug("Removing key: [" + key + "] from LRUMap store, value = [" + value + "]");
/* 572 */     log.debug("LRUMap store size: '" + size() + "'.");
/*     */   }
/*     */ 
/*     */   public void setChunkSize(int chunkSize)
/*     */   {
/* 583 */     this.chunkSize = chunkSize;
/*     */   }
/*     */ 
/*     */   public int getChunkSize()
/*     */   {
/* 591 */     return this.chunkSize;
/*     */   }
/*     */ 
/*     */   public synchronized Set entrySet()
/*     */   {
/* 652 */     Set entries = this.map.entrySet();
/*     */ 
/* 654 */     Set unWrapped = new HashSet();
/*     */ 
/* 656 */     Iterator it = entries.iterator();
/* 657 */     while (it.hasNext())
/*     */     {
/* 659 */       Map.Entry pre = (Map.Entry)it.next();
/* 660 */       Map.Entry post = new LRUMapEntry(pre.getKey(), ((LRUElementDescriptor)pre.getValue()).getPayload());
/* 661 */       unWrapped.add(post);
/*     */     }
/*     */ 
/* 664 */     return unWrapped;
/*     */   }
/*     */ 
/*     */   public Set keySet()
/*     */   {
/* 674 */     return this.map.keySet();
/*     */   }
/*     */ }