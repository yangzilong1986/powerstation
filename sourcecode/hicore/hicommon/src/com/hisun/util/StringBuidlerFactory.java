package com.hisun.util;

import org.apache.commons.pool.PoolableObjectFactory;

class StringBuidlerFactory implements PoolableObjectFactory {
    public void activateObject(Object arg0) throws Exception {
        clear((StringBuilder) arg0);
    }

    public void destroyObject(Object arg0) throws Exception {
        clear((StringBuilder) arg0);
    }

    public Object makeObject() throws Exception {
        System.out.println("makeObject");
        return new StringBuilder();
    }

    public void passivateObject(Object arg0) throws Exception {
    }

    public boolean validateObject(Object arg0) {
        return true;
    }

    public void clear(StringBuilder arg0) {
        arg0.setLength(0);
    }
}