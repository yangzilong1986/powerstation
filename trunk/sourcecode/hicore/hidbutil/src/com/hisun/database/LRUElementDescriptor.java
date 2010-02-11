package com.hisun.database;

public class LRUElementDescriptor extends DoubleLinkedListNode {
    private static final long serialVersionUID = 8249555756363020156L;
    private Object key;

    public LRUElementDescriptor(Object key, Object payloadP) {
        super(payloadP);
        setKey(key);
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getKey() {
        return this.key;
    }
}