package com.hisun.client;

import java.util.HashMap;

public class HiIgnoreHashMap extends HashMap {
    public Object put(Object key, Object value) {
        return super.put(((String) key).toUpperCase(), value);
    }

    public Object get(Object key) {
        return super.get(((String) key).toUpperCase());
    }
}