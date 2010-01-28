package com.hisun.util;


import java.io.Serializable;
import java.util.HashMap;

public class HiIgnoreCaseHashMap extends HashMap implements Serializable {
    private static final long serialVersionUID = 1L;

    public Object put(String key, Object value) {

        return super.put(key.toUpperCase(), value);
    }

    public Object get(String key) {

        return super.get(key.toUpperCase());
    }
}