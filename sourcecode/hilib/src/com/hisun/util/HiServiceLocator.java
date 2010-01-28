package com.hisun.util;


import com.hisun.exception.HiException;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public abstract class HiServiceLocator implements Serializable {
    private static final long serialVersionUID = -6022385922541611335L;
    private static HiServiceLocator me;

    public static synchronized HiServiceLocator getInstance() throws HiException {

        if (me == null) {

            String tmp = HiICSProperty.getProperty("framework");

            if ("EJB".equalsIgnoreCase(tmp)) me = new HiServiceLocatorEJBImpl();
            else {

                me = new HiServiceLocatorPOJOImpl();
            }
        }

        return me;
    }

    public static synchronized HiServiceLocator getInstance(Hashtable environment) throws HiException {

        if (me == null) {

            me = new HiServiceLocatorPOJOImpl(environment);
        }

        return me;
    }

    public abstract InitialContext getInitalContext();

    public abstract Object lookup(String paramString) throws HiException;

    public abstract EJBLocalHome getLocalHome(String paramString) throws HiException;

    public abstract EJBHome getRemoteHome(String paramString, Class paramClass) throws HiException;

    public abstract Object getRemoteObject(String paramString, Class paramClass) throws HiException;

    public abstract QueueConnectionFactory getQueueConnectionFactory(String paramString) throws HiException;

    public abstract Queue getQueue(String paramString) throws HiException;

    public abstract TopicConnectionFactory getTopicConnectionFactory(String paramString) throws HiException;

    public abstract ConnectionFactory getConnectionFactory(String paramString) throws HiException;

    public abstract Topic getTopic(String paramString) throws HiException;

    public abstract DataSource getDataSource(String paramString) throws HiException;

    public abstract DataSource getDBDataSource(String paramString) throws HiException;

    public abstract void bind(String paramString, Object paramObject) throws HiException;

    public abstract void unbind(String paramString) throws HiException;

    public abstract void rebind(String paramString, Object paramObject) throws HiException;

    public abstract ArrayList list(String paramString);
}