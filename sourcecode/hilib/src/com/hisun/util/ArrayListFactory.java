package com.hisun.util;


import org.apache.commons.pool.PoolableObjectFactory;

import java.util.ArrayList;


class ArrayListFactory implements PoolableObjectFactory {

    public void activateObject(Object arg0) throws Exception {

        ((ArrayList) arg0).clear();

    }


    public void destroyObject(Object arg0) throws Exception {

        ((ArrayList) arg0).clear();

    }


    public Object makeObject() throws Exception {

        return new ArrayList();

    }


    public void passivateObject(Object arg0) throws Exception {

        ((ArrayList) arg0).clear();

    }


    public boolean validateObject(Object arg0) {

        return true;

    }

}