package com.hisun.util;

import org.apache.commons.pool.PoolableObjectFactory;

import java.util.HashMap;

class HashMapFactory implements PoolableObjectFactory {
    public void activateObject(Object arg0) throws Exception {
        clear((HashMap) arg0);
    }

    public void destroyObject(Object arg0) throws Exception {
        clear((HashMap) arg0);
    }

    public Object makeObject() throws Exception {
        return new HashMap();
    }

    public void passivateObject(Object arg0) throws Exception {
    }

    public boolean validateObject(Object arg0) {
        return true;
    }

    public void clear(HashMap arg0) {
        arg0.clear();
    }
}