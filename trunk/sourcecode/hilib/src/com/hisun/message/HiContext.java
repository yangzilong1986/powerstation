/*     */ package com.hisun.message;
/*     */ 
/*     */ import com.hisun.util.JavaUtils;
/*     */ import com.hisun.util.LockableHashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiContext
/*     */ {
/*  22 */   public static String SERVER_PRE = "SVR.";
/*     */ 
/*  26 */   public static String APP_PRE = "APP.";
/*     */ 
/*  31 */   public static String TRN_PRE = "TRN.";
/*     */ 
/*  35 */   public static String ROOT_PRE = "ROOT.";
/*     */ 
/*  41 */   protected static ThreadLocal currentContext = new ThreadLocal();
/*     */ 
/* 118 */   protected static final HiContext RootContext = new HiContext("RootContext");
/*     */   protected String id;
/*     */   protected final LockableHashtable bag;
/*     */   protected HiContext parent;
/*     */   protected HiContext firstChild;
/*     */   protected HiContext nextBrother;
/*     */ 
/*     */   public static void pushCurrentContext(HiContext mc)
/*     */   {
/*     */     Stack s;
/*  51 */     if (currentContext.get() == null) {
/*  52 */       s = new Stack();
/*  53 */       currentContext.set(s);
/*     */     } else {
/*  55 */       s = (Stack)currentContext.get();
/*     */     }
/*  57 */     s.push(mc);
/*     */   }
/*     */ 
/*     */   public static HiContext popCurrentContext()
/*     */   {
/*  66 */     if (currentContext.get() != null) {
/*  67 */       Stack s = (Stack)currentContext.get();
/*  68 */       return ((HiContext)s.pop());
/*     */     }
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */   public static HiContext getCurrentContext()
/*     */   {
/*  79 */     if (currentContext.get() != null) {
/*  80 */       Stack s = (Stack)currentContext.get();
/*  81 */       return ((HiContext)s.peek());
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */   public static void setCurrentContext(HiContext mc)
/*     */   {
/*     */     Stack s;
/*  93 */     if (currentContext.get() == null) {
/*  94 */       s = new Stack();
/*  95 */       currentContext.set(s);
/*     */     } else {
/*  97 */       s = (Stack)currentContext.get();
/*     */     }
/*  99 */     if (!(s.isEmpty()))
/* 100 */       s.pop();
/* 101 */     s.push(mc);
/*     */   }
/*     */ 
/*     */   public static void removeCurrentContext()
/*     */   {
/* 112 */     currentContext.set(null);
/*     */   }
/*     */ 
/*     */   public static HiContext getRootContext()
/*     */   {
/* 126 */     return RootContext;
/*     */   }
/*     */ 
/*     */   public static HiContext createContext(String id, HiContext parent)
/*     */   {
/* 139 */     if (parent == null)
/* 140 */       parent = RootContext;
/* 141 */     return new HiContext(id, parent);
/*     */   }
/*     */ 
/*     */   public static HiContext createContext(HiContext parent)
/*     */   {
/* 154 */     if (parent == null)
/* 155 */       parent = RootContext;
/* 156 */     return new HiContext(null, parent);
/*     */   }
/*     */ 
/*     */   public static HiContext createAndPushContext()
/*     */   {
/* 169 */     HiContext parent = getCurrentContext();
/* 170 */     if (parent == null) {
/* 171 */       parent = RootContext;
/*     */     }
/* 173 */     HiContext mc = new HiContext(null, parent);
/* 174 */     pushCurrentContext(mc);
/* 175 */     return mc;
/*     */   }
/*     */ 
/*     */   public String getStrProp(String propName)
/*     */   {
/* 203 */     return ((String)getProperty(propName));
/*     */   }
/*     */ 
/*     */   public String getStrProp(String key1, String key2)
/*     */   {
/* 217 */     String key = key1 + "." + key2;
/* 218 */     return ((String)getProperty(key));
/*     */   }
/*     */ 
/*     */   public boolean isPropertyTrue(String propName)
/*     */   {
/* 231 */     return isPropertyTrue(propName, false);
/*     */   }
/*     */ 
/*     */   public boolean isPropertyTrue(String propName, boolean defaultVal)
/*     */   {
/* 258 */     return JavaUtils.isTrue(getProperty(propName), defaultVal);
/*     */   }
/*     */ 
/*     */   public void setProperty(String name, Object value)
/*     */   {
/* 275 */     if ((name == null) || (value == null)) {
/* 276 */       return;
/*     */     }
/*     */ 
/* 280 */     this.bag.put(name.toUpperCase(), value);
/*     */   }
/*     */ 
/*     */   public void delProperty(String name)
/*     */   {
/* 289 */     this.bag.remove(name.toUpperCase());
/*     */   }
/*     */ 
/*     */   public void setProperty(String key1, String key2, Object value)
/*     */   {
/* 298 */     setProperty(key1 + "." + key2, value);
/*     */   }
/*     */ 
/*     */   public void setProperty(String name, Object value, boolean locked) {
/* 302 */     if ((name == null) || (value == null)) {
/* 303 */       return;
/*     */     }
/*     */ 
/* 307 */     this.bag.put(name.toUpperCase(), value, locked);
/*     */   }
/*     */ 
/*     */   public void setProperty(String key1, String key2, Object value, boolean locked)
/*     */   {
/* 313 */     setProperty(key1 + key2, value, locked);
/*     */   }
/*     */ 
/*     */   public boolean containsProperty(String name)
/*     */   {
/* 326 */     Object propertyValue = getProperty(name);
/* 327 */     return (propertyValue != null);
/*     */   }
/*     */ 
/*     */   public Iterator getPropertyNames()
/*     */   {
/* 340 */     return this.bag.keySet().iterator();
/*     */   }
/*     */ 
/*     */   public Iterator getAllPropertyNames()
/*     */   {
/* 350 */     return this.bag.getAllKeys().iterator();
/*     */   }
/*     */ 
/*     */   public Object getProperty(String name)
/*     */   {
/* 362 */     if (name != null) {
/* 363 */       if (this.bag == null) {
/* 364 */         return null;
/*     */       }
/* 366 */       return this.bag.get(name.toUpperCase());
/*     */     }
/*     */ 
/* 369 */     return null;
/*     */   }
/*     */ 
/*     */   public Object getProperty(String key1, String key2)
/*     */   {
/* 379 */     String key = key1 + "." + key2;
/* 380 */     return getProperty(key);
/*     */   }
/*     */ 
/*     */   public HiContext getFirstChild()
/*     */   {
/* 387 */     return this.firstChild;
/*     */   }
/*     */ 
/*     */   public HiContext getNextBrother()
/*     */   {
/* 394 */     return this.nextBrother;
/*     */   }
/*     */ 
/*     */   public void setId(String id)
/*     */   {
/* 401 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 408 */     return this.id;
/*     */   }
/*     */ 
/*     */   protected HiContext(String id)
/*     */   {
/* 415 */     this.bag = new LockableHashtable();
/* 416 */     this.parent = null;
/* 417 */     this.id = id;
/*     */   }
/*     */ 
/*     */   protected HiContext(String id, HiContext parent) {
/* 421 */     this.bag = new LockableHashtable();
/* 422 */     if (parent == null) {
/* 423 */       parent = RootContext;
/*     */     }
/* 425 */     this.bag.setParent(parent.bag);
/* 426 */     this.parent = parent;
/* 427 */     this.id = id;
/*     */ 
/* 429 */     parent.addChild(this);
/*     */   }
/*     */ 
/*     */   protected void addChild(HiContext child) {
/* 433 */     child.nextBrother = null;
/*     */ 
/* 435 */     if (this.firstChild == null) {
/* 436 */       this.firstChild = child;
/*     */     } else {
/* 438 */       HiContext lastChild = this.firstChild;
/* 439 */       while (lastChild.nextBrother != null)
/* 440 */         lastChild = lastChild.nextBrother;
/* 441 */       lastChild.nextBrother = child;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 448 */     StringBuffer rs = new StringBuffer();
/* 449 */     rs.append("id=" + this.id);
/* 450 */     rs.append(";");
/* 451 */     Set set = this.bag.entrySet();
/* 452 */     Iterator iter = set.iterator();
/* 453 */     while (iter.hasNext()) {
/* 454 */       rs.append(iter.next());
/* 455 */       rs.append(";");
/*     */     }
/* 457 */     if (this.parent != null) {
/* 458 */       rs.append("parent=" + this.parent.id);
/* 459 */       rs.append(";");
/*     */     }
/* 461 */     if (this.firstChild != null) {
/* 462 */       rs.append("firstChild=" + this.firstChild.id);
/* 463 */       rs.append(";");
/*     */     }
/* 465 */     if (this.nextBrother != null) {
/* 466 */       rs.append("nextBrother=" + this.nextBrother.id);
/* 467 */       rs.append(";");
/*     */     }
/*     */ 
/* 470 */     return rs.toString();
/*     */   }
/*     */ 
/*     */   public HiContext getParent()
/*     */   {
/* 477 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public HiContext getServerContext()
/*     */   {
/* 485 */     return getNameContext("SVR.");
/*     */   }
/*     */ 
/*     */   public HiContext getApplicationContext()
/*     */   {
/* 493 */     return getNameContext("APP.");
/*     */   }
/*     */ 
/*     */   public HiContext getTransactionContext()
/*     */   {
/* 500 */     return getNameContext("TRN."); }
/*     */ 
/*     */   private HiContext getNameContext(String id) {
/* 503 */     for (HiContext tmp = this; tmp != null; tmp = tmp.parent)
/* 504 */       if (StringUtils.equalsIgnoreCase(tmp.id.substring(0, 4), id.substring(0, 4)))
/*     */       {
/* 506 */         return tmp;
/*     */       }
/* 508 */     return null;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 515 */     if (this.parent != null)
/* 516 */       this.parent.delchild(this);
/*     */   }
/*     */ 
/*     */   private void delchild(HiContext context)
/*     */   {
/* 521 */     HiContext before = null;
/* 522 */     if (this.firstChild != null) {
/* 523 */       HiContext lastChild = this.firstChild;
/*     */       do {
/* 525 */         if (lastChild == context) {
/* 526 */           if (before == null)
/* 527 */             this.firstChild = lastChild.nextBrother;
/*     */           else {
/* 529 */             before.nextBrother = lastChild.nextBrother;
/*     */           }
/* 531 */           lastChild.nextBrother = null;
/*     */         }
/* 533 */         before = lastChild;
/* 534 */         lastChild = lastChild.nextBrother; }
/* 535 */       while (lastChild != null);
/*     */     }
/*     */   }
/*     */ }