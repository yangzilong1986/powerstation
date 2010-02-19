package com.hzjbbis.fas.protocol.data;

import java.util.Hashtable;

public class DataMappingFactory {
    private static Hashtable datamappings;
    private static Object lock = new Object();

    public static IMapping createDataMapping(String key) {
        synchronized (lock) {
            if (datamappings == null) {
                datamappings = new Hashtable();
            }
            if (!(datamappings.containsKey(key))) {
                datamappings.put(key, createMapping(key));
            }
            return ((IMapping) datamappings.get(key));
        }
    }

    private static IMapping createMapping(String key) {
        if (key.equals("ZJ")) {
            return new DataMappingZJ();
        }
        return null;
    }
}