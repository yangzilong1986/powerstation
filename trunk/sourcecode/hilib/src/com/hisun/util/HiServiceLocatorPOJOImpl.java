package com.hisun.util;


import com.hisun.exception.HiException;
import com.hisun.message.HiContext;
import org.apache.commons.lang.StringUtils;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.jms.*;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.*;


public class HiServiceLocatorPOJOImpl extends HiServiceLocator implements Serializable {
    private static final long serialVersionUID = -6022385922541611335L;
    private InitialContext ic;
    private static HiServiceLocatorPOJOImpl me;
    private HashMap _cacheDatasource = new HashMap();
    private HashMap _cacheObject = new HashMap();


    public HiServiceLocatorPOJOImpl() throws HiException {

        try {

            this.ic = new InitialContext();

        } catch (NameAlreadyBoundException nae) {

            throw new HiException(nae);

        } catch (NamingException ne) {

            throw new HiException(ne);

        } catch (Exception e) {

            throw new HiException(e);

        }

    }


    public HiServiceLocatorPOJOImpl(Hashtable environment) throws HiException {

        try {

            this.ic = new InitialContext(environment);

        } catch (NameAlreadyBoundException nae) {

        } catch (NamingException ne) {

            throw new HiException(ne);

        } catch (Exception e) {

            throw new HiException(e);

        }

    }


    public Object lookup(String jndiName) throws HiException {

        if (!(this._cacheObject.containsKey(jndiName))) {

            jndiName = getJndiName(jndiName);

        }

        return this._cacheObject.get(jndiName);

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

        this._cacheObject.put(name, obj);

    }


    public void unbind(String name) throws HiException {

        name = getJndiName(name);

        this._cacheObject.remove(name);

    }


    public void rebind(String name, Object obj) throws HiException {

        this._cacheObject.put(name, obj);

    }


    private String getJndiName(String jndiName) {

        Set set = this._cacheObject.keySet();

        Iterator iter = set.iterator();

        while (iter.hasNext()) {

            String key = (String) iter.next();

            if (key.indexOf(jndiName + ";") == 0) {

                return key;

            }

        }

        return jndiName;

    }


    public InitialContext getInitalContext() {

        return this.ic;

    }


    public EJBLocalHome getLocalHome(String jndiHomeName) throws HiException {

        return null;

    }


    public EJBHome getRemoteHome(String jndiHomeName, Class className) throws HiException {

        return null;

    }


    public Object getRemoteObject(String jndiHomeName, Class className) throws HiException {

        return null;

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

}