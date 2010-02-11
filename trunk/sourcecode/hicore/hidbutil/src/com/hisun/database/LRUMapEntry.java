package com.hisun.database;

import java.io.Serializable;

public class LRUMapEntry implements Map.Entry, Serializable {
    private static final long serialVersionUID = -8176116317739129331L;
    private Object key;
    private Object value;

    public LRUMapEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return this.key;
    }

    public Object getValue() {
        return this.value;
    }

    public Object setValue(Object valueArg) {
        Object old = this.value;
        this.value = valueArg;
        return old;
    }
}