package com.hzjbbis.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;

public class EnvironmentBag {
    private static final Log log = LogFactory.getLog(EnvironmentBag.class);
    private static EnvironmentBag _instance;
    private HashMap fronts;
    private long time;

    private EnvironmentBag() {
        this.fronts = new HashMap();
        this.time = System.currentTimeMillis();
    }

    public static EnvironmentBag getInstance() {
        if (_instance == null) {
            synchronized (EnvironmentBag.class) {
                _instance = new EnvironmentBag();
            }
        }
        return _instance;
    }

    public void onFrontConnected(String ip, Object dthread) {
        synchronized (this.fronts) {
            if (this.fronts.containsKey(ip)) {
                this.fronts.remove(ip);
            }
            this.fronts.put(ip, dthread);
        }
    }

    public void onFrontClose(String ip) {
        synchronized (this.fronts) {
            this.fronts.remove(ip);
        }
    }

    public boolean isAliveFront(String ip) {
        boolean rt = false;
        try {
            if (this.fronts != null) rt = this.fronts.containsKey(ip);
        } catch (Exception e) {
        }
        return rt;
    }
}