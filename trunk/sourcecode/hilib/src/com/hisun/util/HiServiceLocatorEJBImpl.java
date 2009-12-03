/*     */ package com.hisun.util;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiContext;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.ejb.EJBHome;
/*     */ import javax.ejb.EJBLocalHome;
/*     */ import javax.jms.ConnectionFactory;
/*     */ import javax.jms.Queue;
/*     */ import javax.jms.QueueConnectionFactory;
/*     */ import javax.jms.Topic;
/*     */ import javax.jms.TopicConnectionFactory;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NameAlreadyBoundException;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.event.NamingEvent;
/*     */ import javax.naming.event.NamingExceptionEvent;
/*     */ import javax.naming.event.ObjectChangeListener;
/*     */ import javax.rmi.PortableRemoteObject;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.commons.beanutils.MethodUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiServiceLocatorEJBImpl extends HiServiceLocator
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6022385922541611335L;
/*     */   private InitialContext ic;
/*  46 */   private HashMap _cacheRemoteHome = new HashMap();
/*  47 */   private HashMap _cacheRemoteObject = new HashMap();
/*  48 */   private HashMap _cacheDatasource = new HashMap();
/*  49 */   private HashMap _cacheObject = new HashMap();
/*     */ 
/*     */   public HiServiceLocatorEJBImpl() throws HiException {
/*     */     try {
/*  53 */       this.ic = new InitialContext();
/*     */     }
/*     */     catch (NameAlreadyBoundException nae)
/*     */     {
/*     */     }
/*     */     catch (NamingException ne)
/*     */     {
/*  62 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/*  64 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiServiceLocatorEJBImpl(Hashtable environment) throws HiException {
/*     */     try {
/*  70 */       this.ic = new InitialContext(environment);
/*     */     }
/*     */     catch (NameAlreadyBoundException nae) {
/*     */     }
/*     */     catch (NamingException ne) {
/*  75 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/*  77 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public InitialContext getInitalContext() {
/*  82 */     return this.ic;
/*     */   }
/*     */ 
/*     */   public Object lookup(String jndiName)
/*     */     throws HiException
/*     */   {
/*  92 */     Object object = null;
/*     */     try {
/*  94 */       jndiName = getJndiName(jndiName);
/*     */ 
/*  96 */       if (this._cacheObject.containsKey(jndiName))
/*  97 */         return this._cacheObject.get(jndiName);
/*  98 */       object = this.ic.lookup(jndiName);
/*  99 */       this._cacheObject.put(jndiName, object);
/*     */     } catch (NamingException ne) {
/* 101 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 103 */       throw new HiException(e);
/*     */     }
/* 105 */     return object;
/*     */   }
/*     */ 
/*     */   public EJBLocalHome getLocalHome(String jndiHomeName)
/*     */     throws HiException
/*     */   {
/* 115 */     EJBLocalHome home = null;
/*     */     try {
/* 117 */       home = (EJBLocalHome)this.ic.lookup(jndiHomeName);
/*     */     } catch (NamingException ne) {
/* 119 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 121 */       throw new HiException(e);
/*     */     }
/* 123 */     return home;
/*     */   }
/*     */ 
/*     */   public EJBHome getRemoteHome(String jndiHomeName, Class className)
/*     */     throws HiException
/*     */   {
/* 134 */     EJBHome home = null;
/*     */     try {
/* 136 */       if (this._cacheRemoteHome.containsKey(jndiHomeName)) {
/* 137 */         return ((EJBHome)this._cacheRemoteHome.get(jndiHomeName));
/*     */       }
/* 139 */       Object objref = this.ic.lookup(jndiHomeName);
/* 140 */       Object obj = PortableRemoteObject.narrow(objref, className);
/* 141 */       home = (EJBHome)obj;
/* 142 */       this._cacheRemoteHome.put(jndiHomeName, home);
/*     */     } catch (NamingException ne) {
/* 144 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 146 */       throw new HiException(e);
/*     */     }
/* 148 */     return home;
/*     */   }
/*     */ 
/*     */   public Object getRemoteObject(String jndiHomeName, Class className)
/*     */     throws HiException
/*     */   {
/* 159 */     Object obj = null;
/*     */     try {
/* 161 */       if (this._cacheRemoteObject.containsKey(jndiHomeName)) {
/* 162 */         return this._cacheRemoteObject.get(jndiHomeName);
/*     */       }
/* 164 */       EJBHome home = getRemoteHome(jndiHomeName, className);
/* 165 */       obj = MethodUtils.invokeExactMethod(home, "create", null);
/* 166 */       this._cacheRemoteObject.put(jndiHomeName, obj);
/*     */     } catch (Exception e) {
/* 168 */       throw new HiException(e);
/*     */     }
/* 170 */     return obj;
/*     */   }
/*     */ 
/*     */   public QueueConnectionFactory getQueueConnectionFactory(String qConnFactoryName)
/*     */     throws HiException
/*     */   {
/* 178 */     QueueConnectionFactory factory = null;
/*     */     try {
/* 180 */       factory = (QueueConnectionFactory)this.ic.lookup(qConnFactoryName);
/*     */     } catch (NamingException ne) {
/* 182 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 184 */       throw new HiException(e);
/*     */     }
/* 186 */     return factory;
/*     */   }
/*     */ 
/*     */   public Queue getQueue(String queueName)
/*     */     throws HiException
/*     */   {
/* 193 */     Queue queue = null;
/*     */     try {
/* 195 */       queue = (Queue)this.ic.lookup(queueName);
/*     */     } catch (NamingException ne) {
/* 197 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 199 */       throw new HiException(e);
/*     */     }
/* 201 */     return queue;
/*     */   }
/*     */ 
/*     */   public TopicConnectionFactory getTopicConnectionFactory(String topicConnFactoryName)
/*     */     throws HiException
/*     */   {
/* 211 */     TopicConnectionFactory factory = null;
/*     */     try {
/* 213 */       factory = (TopicConnectionFactory)this.ic.lookup(topicConnFactoryName);
/*     */     } catch (NamingException ne) {
/* 215 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 217 */       throw new HiException(e);
/*     */     }
/* 219 */     return factory;
/*     */   }
/*     */ 
/*     */   public ConnectionFactory getConnectionFactory(String connFactoryName) throws HiException
/*     */   {
/* 224 */     ConnectionFactory factory = null;
/*     */     try {
/* 226 */       factory = (ConnectionFactory)this.ic.lookup(connFactoryName);
/*     */     } catch (NamingException ne) {
/* 228 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 230 */       throw new HiException(e);
/*     */     }
/* 232 */     return factory;
/*     */   }
/*     */ 
/*     */   public Topic getTopic(String topicName)
/*     */     throws HiException
/*     */   {
/* 241 */     Topic topic = null;
/*     */     try {
/* 243 */       topic = (Topic)this.ic.lookup(topicName);
/*     */     } catch (NamingException ne) {
/* 245 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 247 */       throw new HiException(e);
/*     */     }
/* 249 */     return topic;
/*     */   }
/*     */ 
/*     */   public DataSource getDataSource(String dataSourceName)
/*     */     throws HiException
/*     */   {
/* 258 */     DataSource dataSource = null;
/*     */     try {
/* 260 */       if (this._cacheDatasource.containsKey(dataSourceName))
/* 261 */         return ((DataSource)this._cacheDatasource.get(dataSourceName));
/* 262 */       dataSource = (DataSource)this.ic.lookup(dataSourceName);
/* 263 */       this._cacheDatasource.put(dataSourceName, dataSource);
/*     */     } catch (NamingException ne) {
/* 265 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 267 */       throw new HiException(e);
/*     */     }
/* 269 */     return dataSource;
/*     */   }
/*     */ 
/*     */   public DataSource getDBDataSource(String name) throws HiException {
/* 273 */     DataSource dataSource = null;
/*     */     try
/*     */     {
/* 277 */       String dataSourceName = HiContext.getCurrentContext().getStrProp("@PARA", name);
/*     */ 
/* 279 */       if (StringUtils.isEmpty(dataSourceName)) {
/* 280 */         throw new HiException("212008", name);
/*     */       }
/* 282 */       if (this._cacheDatasource.containsKey(dataSourceName))
/* 283 */         return ((DataSource)this._cacheDatasource.get(dataSourceName));
/* 284 */       dataSource = (DataSource)this.ic.lookup(dataSourceName);
/* 285 */       this._cacheDatasource.put(dataSourceName, dataSource);
/*     */     } catch (NamingException ne) {
/* 287 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 289 */       throw new HiException(e);
/*     */     }
/* 291 */     return dataSource;
/*     */   }
/*     */ 
/*     */   public void bind(String name, Object obj) throws HiException {
/*     */     try {
/* 296 */       this.ic.bind(name, obj);
/* 297 */       this._cacheObject.put(name, obj);
/*     */     } catch (Exception e) {
/* 299 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void unbind(String name) throws HiException {
/*     */     try {
/* 305 */       name = getJndiName(name);
/* 306 */       this.ic.unbind(name);
/* 307 */       this._cacheObject.remove(name);
/*     */     } catch (Exception e) {
/* 309 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void rebind(String name, Object obj) throws HiException {
/*     */     try {
/* 315 */       this.ic.rebind(name, obj);
/* 316 */       this._cacheObject.put(name, obj);
/*     */     } catch (Exception e) {
/* 318 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getJndiName(String jndiName) throws NamingException {
/* 323 */     Set set = this._cacheObject.keySet();
/* 324 */     Iterator iter = set.iterator();
/* 325 */     while (iter.hasNext()) {
/* 326 */       String key = (String)iter.next();
/* 327 */       if (key.indexOf(jndiName + ";") == 0) {
/* 328 */         return key;
/*     */       }
/*     */     }
/*     */ 
/* 332 */     NamingEnumeration list = this.ic.list("");
/* 333 */     while (list.hasMore()) {
/* 334 */       NameClassPair item = (NameClassPair)list.next();
/* 335 */       String name = item.getName();
/* 336 */       if (name.indexOf(jndiName + ";") == 0) {
/* 337 */         jndiName = name;
/* 338 */         break;
/*     */       }
/*     */     }
/* 341 */     return jndiName;
/*     */   }
/*     */ 
/*     */   public ArrayList list(String prefix)
/*     */   {
/* 356 */     ArrayList list = new ArrayList();
/* 357 */     Set set = this._cacheObject.keySet();
/* 358 */     Iterator iter = set.iterator();
/* 359 */     while (iter.hasNext()) {
/* 360 */       String key = (String)iter.next();
/* 361 */       if (key.indexOf(prefix) == 0) {
/* 362 */         list.add(key);
/*     */       }
/*     */     }
/* 365 */     return list;
/*     */   }
/*     */ 
/*     */   class HiChangeHandler
/*     */     implements ObjectChangeListener
/*     */   {
/*     */     public void objectChanged(NamingEvent evt)
/*     */     {
/* 346 */       String name = evt.getNewBinding().getName();
/* 347 */       if (HiServiceLocatorEJBImpl.this._cacheObject.containsKey(evt.getNewBinding().getName()))
/* 348 */         HiServiceLocatorEJBImpl.this._cacheObject.remove(name);
/*     */     }
/*     */ 
/*     */     public void namingExceptionThrown(NamingExceptionEvent evt)
/*     */     {
/*     */     }
/*     */   }
/*     */ }