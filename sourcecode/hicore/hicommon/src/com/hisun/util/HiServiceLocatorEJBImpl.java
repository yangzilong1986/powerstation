package com.hisun.util;

import com.hisun.exception.HiException;
import com.hisun.message.HiContext;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.jms.*;
import javax.jms.Queue;
import javax.naming.*;
import javax.naming.event.NamingEvent;
import javax.naming.event.NamingExceptionEvent;
import javax.naming.event.ObjectChangeListener;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.*;

public class HiServiceLocatorEJBImpl extends HiServiceLocator implements Serializable {
    private static final long serialVersionUID = -6022385922541611335L;
    private InitialContext ic;
    private HashMap _cacheRemoteHome = new HashMap();
    private HashMap _cacheRemoteObject = new HashMap();
    private HashMap _cacheDatasource = new HashMap();
    private HashMap _cacheObject = new HashMap();

    public HiServiceLocatorEJBImpl() throws HiException {
        try {
            this.ic = new InitialContext();
        } catch (NameAlreadyBoundException nae) {
        } catch (NamingException ne) {
            throw new HiException(ne);
        } catch (Exception e) {
            throw new HiException(e);
        }
    }

    public HiServiceLocatorEJBImpl(Hashtable environment) throws HiException {
        try {
            this.ic = new InitialContext(environment);
        } catch (NameAlreadyBoundException nae) {
        } catch (NamingException ne) {
            throw new HiException(ne);
        } catch (Exception e) {
            throw new HiException(e);
        }
    }

    public InitialContext getInitalContext() {
        return this.ic;
    }

    public Object lookup(String jndiName) throws HiException {
        Object object = null;
        try {
            jndiName = getJndiName(jndiName);

            if (this._cacheObject.containsKey(jndiName)) return this._cacheObject.get(jndiName);
            object = this.ic.lookup(jndiName);
            this._cacheObject.put(jndiName, object);
        } catch (NamingException ne) {
            throw new HiException(ne);
        } catch (Exception e) {
            throw new HiException(e);
        }
        return object;
    }

    public EJBLocalHome getLocalHome(String jndiHomeName) throws HiException {
        EJBLocalHome home = null;
        try {
            home = (EJBLocalHome) this.ic.lookup(jndiHomeName);
        } catch (NamingException ne) {
            throw new HiException(ne);
        } catch (Exception e) {
            throw new HiException(e);
        }
        return home;
    }

    public EJBHome getRemoteHome(String jndiHomeName, Class className) throws HiException {
        EJBHome home = null;
        try {
            if (this._cacheRemoteHome.containsKey(jndiHomeName)) {
                return ((EJBHome) this._cacheRemoteHome.get(jndiHomeName));
            }
            Object objref = this.ic.lookup(jndiHomeName);
            Object obj = PortableRemoteObject.narrow(objref, className);
            home = (EJBHome) obj;
            this._cacheRemoteHome.put(jndiHomeName, home);
        } catch (NamingException ne) {
            throw new HiException(ne);
        } catch (Exception e) {
            throw new HiException(e);
        }
        return home;
    }

    public Object getRemoteObject(String jndiHomeName, Class className) throws HiException {
        Object obj = null;
        try {
            if (this._cacheRemoteObject.containsKey(jndiHomeName)) {
                return this._cacheRemoteObject.get(jndiHomeName);
            }
            EJBHome home = getRemoteHome(jndiHomeName, className);
            obj = MethodUtils.invokeExactMethod(home, "create", null);
            this._cacheRemoteObject.put(jndiHomeName, obj);
        } catch (Exception e) {
            throw new HiException(e);
        }
        return obj;
    }

    public QueueConnectionFactory getQueueConnectionFactory(String qConnFactoryName) throws HiException {
        QueueConnectionFactory factory = null;
        try {
            factory = (QueueConnectionFactory) this.ic.lookup(qConnFactoryName);
        } catch (NamingException ne) {
            throw new HiException(ne);
        } catch (Exception e) {
            throw new HiException(e);
        }
        return factory;
    }

    public Queue getQueue(String queueName) throws HiException {
        Queue queue = null;
        try {
            queue = (Queue) this.ic.lookup(queueName);
        } catch (NamingException ne) {
            throw new HiException(ne);
        } catch (Exception e) {
            throw new HiException(e);
        }
        return queue;
    }

    public TopicConnectionFactory getTopicConnectionFactory(String topicConnFactoryName) throws HiException {
        TopicConnectionFactory factory = null;
        try {
            factory = (TopicConnectionFactory) this.ic.lookup(topicConnFactoryName);
        } catch (NamingException ne) {
            throw new HiException(ne);
        } catch (Exception e) {
            throw new HiException(e);
        }
        return factory;
    }

    public ConnectionFactory getConnectionFactory(String connFactoryName) throws HiException {
        ConnectionFactory factory = null;
        try {
            factory = (ConnectionFactory) this.ic.lookup(connFactoryName);
        } catch (NamingException ne) {
            throw new HiException(ne);
        } catch (Exception e) {
            throw new HiException(e);
        }
        return factory;
    }

    public Topic getTopic(String topicName) throws HiException {
        Topic topic = null;
        try {
            topic = (Topic) this.ic.lookup(topicName);
        } catch (NamingException ne) {
            throw new HiException(ne);
        } catch (Exception e) {
            throw new HiException(e);
        }
        return topic;
    }

    public DataSource getDataSource(String dataSourceName) throws HiException {
        DataSource dataSource = null;
        try {
            if (this._cacheDatasource.containsKey(dataSourceName))
                return ((DataSource) this._cacheDatasource.get(dataSourceName));
            dataSource = (DataSource) this.ic.lookup(dataSourceName);
            this._cacheDatasource.put(dataSourceName, dataSource);
        } catch (NamingException ne) {
            throw new HiException(ne);
        } catch (Exception e) {
            throw new HiException(e);
        }
        return dataSource;
    }

    public DataSource getDBDataSource(String name) throws HiException {
        DataSource dataSource = null;
        try {
            String dataSourceName = HiContext.getCurrentContext().getStrProp("@PARA", name);

            if (StringUtils.isEmpty(dataSourceName)) {
                throw new HiException("212008", name);
            }
            if (this._cacheDatasource.containsKey(dataSourceName))
                return ((DataSource) this._cacheDatasource.get(dataSourceName));
            dataSource = (DataSource) this.ic.lookup(dataSourceName);
            this._cacheDatasource.put(dataSourceName, dataSource);
        } catch (NamingException ne) {
            throw new HiException(ne);
        } catch (Exception e) {
            throw new HiException(e);
        }
        return dataSource;
    }

    public void bind(String name, Object obj) throws HiException {
        try {
            this.ic.bind(name, obj);
            this._cacheObject.put(name, obj);
        } catch (Exception e) {
            throw new HiException(e);
        }
    }

    public void unbind(String name) throws HiException {
        try {
            name = getJndiName(name);
            this.ic.unbind(name);
            this._cacheObject.remove(name);
        } catch (Exception e) {
            throw new HiException(e);
        }
    }

    public void rebind(String name, Object obj) throws HiException {
        try {
            this.ic.rebind(name, obj);
            this._cacheObject.put(name, obj);
        } catch (Exception e) {
            throw new HiException(e);
        }
    }

    private String getJndiName(String jndiName) throws NamingException {
        Set set = this._cacheObject.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (key.indexOf(jndiName + ";") == 0) {
                return key;
            }
        }

        NamingEnumeration list = this.ic.list("");
        while (list.hasMore()) {
            NameClassPair item = (NameClassPair) list.next();
            String name = item.getName();
            if (name.indexOf(jndiName + ";") == 0) {
                jndiName = name;
                break;
            }
        }
        return jndiName;
    }

    public ArrayList list(String prefix) {
        ArrayList list = new ArrayList();
        Set set = this._cacheObject.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (key.indexOf(prefix) == 0) {
                list.add(key);
            }
        }
        return list;
    }

    class HiChangeHandler implements ObjectChangeListener {
        public void objectChanged(NamingEvent evt) {
            String name = evt.getNewBinding().getName();
            if (HiServiceLocatorEJBImpl.this._cacheObject.containsKey(evt.getNewBinding().getName()))
                HiServiceLocatorEJBImpl.this._cacheObject.remove(name);
        }

        public void namingExceptionThrown(NamingExceptionEvent evt) {
        }
    }
}