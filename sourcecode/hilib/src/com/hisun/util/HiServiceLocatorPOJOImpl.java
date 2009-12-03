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
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NameAlreadyBoundException;
/*     */ import javax.naming.NamingException;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiServiceLocatorPOJOImpl extends HiServiceLocator
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6022385922541611335L;
/*     */   private InitialContext ic;
/*     */   private static HiServiceLocatorPOJOImpl me;
/*  48 */   private HashMap _cacheDatasource = new HashMap();
/*  49 */   private HashMap _cacheObject = new HashMap();
/*     */ 
/*     */   public HiServiceLocatorPOJOImpl() throws HiException {
/*     */     try {
/*  53 */       this.ic = new InitialContext();
/*     */     } catch (NameAlreadyBoundException nae) {
/*  55 */       throw new HiException(nae);
/*     */     } catch (NamingException ne) {
/*  57 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/*  59 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiServiceLocatorPOJOImpl(Hashtable environment) throws HiException {
/*     */     try {
/*  65 */       this.ic = new InitialContext(environment);
/*     */     }
/*     */     catch (NameAlreadyBoundException nae) {
/*     */     }
/*     */     catch (NamingException ne) {
/*  70 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/*  72 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object lookup(String jndiName)
/*     */     throws HiException
/*     */   {
/*  83 */     if (!(this._cacheObject.containsKey(jndiName))) {
/*  84 */       jndiName = getJndiName(jndiName);
/*     */     }
/*  86 */     return this._cacheObject.get(jndiName);
/*     */   }
/*     */ 
/*     */   public QueueConnectionFactory getQueueConnectionFactory(String qConnFactoryName)
/*     */     throws HiException
/*     */   {
/*  94 */     QueueConnectionFactory factory = null;
/*     */     try {
/*  96 */       factory = (QueueConnectionFactory)this.ic.lookup(qConnFactoryName);
/*     */     } catch (NamingException ne) {
/*  98 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 100 */       throw new HiException(e);
/*     */     }
/* 102 */     return factory;
/*     */   }
/*     */ 
/*     */   public Queue getQueue(String queueName)
/*     */     throws HiException
/*     */   {
/* 109 */     Queue queue = null;
/*     */     try {
/* 111 */       queue = (Queue)this.ic.lookup(queueName);
/*     */     } catch (NamingException ne) {
/* 113 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 115 */       throw new HiException(e);
/*     */     }
/* 117 */     return queue;
/*     */   }
/*     */ 
/*     */   public TopicConnectionFactory getTopicConnectionFactory(String topicConnFactoryName)
/*     */     throws HiException
/*     */   {
/* 127 */     TopicConnectionFactory factory = null;
/*     */     try {
/* 129 */       factory = (TopicConnectionFactory)this.ic.lookup(topicConnFactoryName);
/*     */     } catch (NamingException ne) {
/* 131 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 133 */       throw new HiException(e);
/*     */     }
/* 135 */     return factory;
/*     */   }
/*     */ 
/*     */   public ConnectionFactory getConnectionFactory(String connFactoryName) throws HiException
/*     */   {
/* 140 */     ConnectionFactory factory = null;
/*     */     try {
/* 142 */       factory = (ConnectionFactory)this.ic.lookup(connFactoryName);
/*     */     } catch (NamingException ne) {
/* 144 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 146 */       throw new HiException(e);
/*     */     }
/* 148 */     return factory;
/*     */   }
/*     */ 
/*     */   public Topic getTopic(String topicName)
/*     */     throws HiException
/*     */   {
/* 157 */     Topic topic = null;
/*     */     try {
/* 159 */       topic = (Topic)this.ic.lookup(topicName);
/*     */     } catch (NamingException ne) {
/* 161 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 163 */       throw new HiException(e);
/*     */     }
/* 165 */     return topic;
/*     */   }
/*     */ 
/*     */   public DataSource getDataSource(String dataSourceName)
/*     */     throws HiException
/*     */   {
/* 174 */     DataSource dataSource = null;
/*     */     try {
/* 176 */       if (this._cacheDatasource.containsKey(dataSourceName))
/* 177 */         return ((DataSource)this._cacheDatasource.get(dataSourceName));
/* 178 */       dataSource = (DataSource)this.ic.lookup(dataSourceName);
/* 179 */       this._cacheDatasource.put(dataSourceName, dataSource);
/*     */     } catch (NamingException ne) {
/* 181 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 183 */       throw new HiException(e);
/*     */     }
/* 185 */     return dataSource;
/*     */   }
/*     */ 
/*     */   public DataSource getDBDataSource(String name) throws HiException {
/* 189 */     DataSource dataSource = null;
/*     */     try
/*     */     {
/* 193 */       String dataSourceName = HiContext.getCurrentContext().getStrProp("@PARA", name);
/*     */ 
/* 195 */       if (StringUtils.isEmpty(dataSourceName)) {
/* 196 */         throw new HiException("212008", name);
/*     */       }
/* 198 */       if (this._cacheDatasource.containsKey(dataSourceName))
/* 199 */         return ((DataSource)this._cacheDatasource.get(dataSourceName));
/* 200 */       dataSource = (DataSource)this.ic.lookup(dataSourceName);
/* 201 */       this._cacheDatasource.put(dataSourceName, dataSource);
/*     */     } catch (NamingException ne) {
/* 203 */       throw new HiException(ne);
/*     */     } catch (Exception e) {
/* 205 */       throw new HiException(e);
/*     */     }
/* 207 */     return dataSource;
/*     */   }
/*     */ 
/*     */   public void bind(String name, Object obj) throws HiException {
/* 211 */     this._cacheObject.put(name, obj);
/*     */   }
/*     */ 
/*     */   public void unbind(String name) throws HiException {
/* 215 */     name = getJndiName(name);
/* 216 */     this._cacheObject.remove(name);
/*     */   }
/*     */ 
/*     */   public void rebind(String name, Object obj) throws HiException {
/* 220 */     this._cacheObject.put(name, obj);
/*     */   }
/*     */ 
/*     */   private String getJndiName(String jndiName) {
/* 224 */     Set set = this._cacheObject.keySet();
/* 225 */     Iterator iter = set.iterator();
/* 226 */     while (iter.hasNext()) {
/* 227 */       String key = (String)iter.next();
/* 228 */       if (key.indexOf(jndiName + ";") == 0) {
/* 229 */         return key;
/*     */       }
/*     */     }
/* 232 */     return jndiName;
/*     */   }
/*     */ 
/*     */   public InitialContext getInitalContext() {
/* 236 */     return this.ic;
/*     */   }
/*     */ 
/*     */   public EJBLocalHome getLocalHome(String jndiHomeName) throws HiException
/*     */   {
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */   public EJBHome getRemoteHome(String jndiHomeName, Class className)
/*     */     throws HiException
/*     */   {
/* 247 */     return null;
/*     */   }
/*     */ 
/*     */   public Object getRemoteObject(String jndiHomeName, Class className)
/*     */     throws HiException
/*     */   {
/* 253 */     return null;
/*     */   }
/*     */ 
/*     */   public ArrayList list(String prefix) {
/* 257 */     ArrayList list = new ArrayList();
/* 258 */     Set set = this._cacheObject.keySet();
/* 259 */     Iterator iter = set.iterator();
/* 260 */     while (iter.hasNext()) {
/* 261 */       String key = (String)iter.next();
/* 262 */       if (key.indexOf(prefix) == 0) {
/* 263 */         list.add(key);
/*     */       }
/*     */     }
/* 266 */     return list;
/*     */   }
/*     */ }