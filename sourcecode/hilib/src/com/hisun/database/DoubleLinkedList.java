/*     */ package com.hisun.database;
/*     */ 
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class DoubleLinkedList
/*     */ {
/*  33 */   private int size = 0;
/*     */ 
/*  36 */   private static final Log log = LogFactory.getLog(DoubleLinkedList.class);
/*     */   private DoubleLinkedListNode first;
/*     */   private DoubleLinkedListNode last;
/*     */ 
/*     */   public synchronized void addLast(DoubleLinkedListNode me)
/*     */   {
/*  60 */     if (this.first == null)
/*     */     {
/*  63 */       this.first = me;
/*     */     }
/*     */     else
/*     */     {
/*  67 */       this.last.next = me;
/*  68 */       me.prev = this.last;
/*     */     }
/*  70 */     this.last = me;
/*  71 */     this.size += 1;
/*     */   }
/*     */ 
/*     */   public synchronized void addFirst(DoubleLinkedListNode me)
/*     */   {
/*  82 */     if (this.last == null)
/*     */     {
/*  85 */       this.last = me;
/*     */     }
/*     */     else
/*     */     {
/*  89 */       this.first.prev = me;
/*  90 */       me.next = this.first;
/*     */     }
/*  92 */     this.first = me;
/*  93 */     this.size += 1;
/*     */   }
/*     */ 
/*     */   public synchronized DoubleLinkedListNode getLast()
/*     */   {
/* 104 */     if (log.isDebugEnabled())
/*     */     {
/* 106 */       log.debug("returning last node");
/*     */     }
/* 108 */     return this.last;
/*     */   }
/*     */ 
/*     */   public synchronized DoubleLinkedListNode getFirst()
/*     */   {
/* 118 */     if (log.isDebugEnabled())
/*     */     {
/* 120 */       log.debug("returning first node");
/*     */     }
/* 122 */     return this.first;
/*     */   }
/*     */ 
/*     */   public synchronized void makeFirst(DoubleLinkedListNode ln)
/*     */   {
/* 133 */     if (ln.prev == null)
/*     */     {
/* 136 */       return;
/*     */     }
/* 138 */     ln.prev.next = ln.next;
/*     */ 
/* 140 */     if (ln.next == null)
/*     */     {
/* 143 */       this.last = ln.prev;
/* 144 */       this.last.next = null;
/*     */     }
/*     */     else
/*     */     {
/* 149 */       ln.next.prev = ln.prev;
/*     */     }
/* 151 */     this.first.prev = ln;
/* 152 */     ln.next = this.first;
/* 153 */     ln.prev = null;
/* 154 */     this.first = ln;
/*     */   }
/*     */ 
/*     */   public synchronized void removeAll()
/*     */   {
/* 162 */     for (DoubleLinkedListNode me = this.first; me != null; )
/*     */     {
/* 164 */       if (me.prev != null)
/*     */       {
/* 166 */         me.prev = null;
/*     */       }
/* 168 */       DoubleLinkedListNode next = me.next;
/* 169 */       me = next;
/*     */     }
/* 171 */     this.first = (this.last = null);
/*     */ 
/* 173 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   public synchronized boolean remove(DoubleLinkedListNode me)
/*     */   {
/* 185 */     if (log.isDebugEnabled())
/*     */     {
/* 187 */       log.debug("removing node");
/*     */     }
/*     */ 
/* 190 */     if (me.next == null)
/*     */     {
/* 192 */       if (me.prev == null)
/*     */       {
/* 199 */         if ((me == this.first) && (me == this.last))
/*     */         {
/* 201 */           this.first = (this.last = null);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 207 */         this.last = me.prev;
/* 208 */         this.last.next = null;
/* 209 */         me.prev = null;
/*     */       }
/*     */     }
/* 212 */     else if (me.prev == null)
/*     */     {
/* 215 */       this.first = me.next;
/* 216 */       this.first.prev = null;
/* 217 */       me.next = null;
/*     */     }
/*     */     else
/*     */     {
/* 222 */       me.prev.next = me.next;
/* 223 */       me.next.prev = me.prev;
/* 224 */       me.prev = (me.next = null);
/*     */     }
/* 226 */     this.size -= 1;
/*     */ 
/* 228 */     return true;
/*     */   }
/*     */ 
/*     */   public synchronized DoubleLinkedListNode removeLast()
/*     */   {
/* 238 */     if (log.isDebugEnabled())
/*     */     {
/* 240 */       log.debug("removing last node");
/*     */     }
/* 242 */     DoubleLinkedListNode temp = this.last;
/* 243 */     if (this.last != null)
/*     */     {
/* 245 */       remove(this.last);
/*     */     }
/* 247 */     return temp;
/*     */   }
/*     */ 
/*     */   public synchronized int size()
/*     */   {
/* 257 */     return this.size;
/*     */   }
/*     */ 
/*     */   public synchronized void debugDumpEntries()
/*     */   {
/* 266 */     log.debug("dumping Entries");
/* 267 */     for (DoubleLinkedListNode me = this.first; me != null; me = me.next)
/*     */     {
/* 269 */       log.debug("dump Entries> payload= '" + me.getPayload() + "'");
/*     */     }
/*     */   }
/*     */ }