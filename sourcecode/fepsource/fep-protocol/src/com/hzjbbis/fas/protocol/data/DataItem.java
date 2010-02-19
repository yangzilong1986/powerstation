package com.hzjbbis.fas.protocol.data;

import java.util.Hashtable;

public class DataItem {
    private Hashtable property;

    public DataItem() {
        this.property = new Hashtable();
    }

    public Hashtable getPropertys() {
        return this.property;
    }

    public Object getProperty(String key) {
        if ((this.property != null) && (this.property.containsKey(key))) {
            return this.property.get(key);
        }

        return null;
    }

    public void addProperty(String key, Object value) {
        try {
            if (this.property != null) {
                if (this.property.containsKey(key)) {
                    this.property.remove(key);
                }
                this.property.put(key, value);
            }
        } catch (Exception e) {
        }
    }

    public String toString() {
        return ((String) this.property.get("des"));
    }
}