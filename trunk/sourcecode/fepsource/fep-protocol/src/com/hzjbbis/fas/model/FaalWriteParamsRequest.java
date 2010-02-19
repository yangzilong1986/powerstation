package com.hzjbbis.fas.model;

import java.util.HashMap;

public class FaalWriteParamsRequest extends FaalRequest {
    private static final long serialVersionUID = 6624139932292640887L;
    private String tn;
    private HashMap map;

    public void setMap(String key, String value) {
        if (this.map == null) this.map = new HashMap();
        this.map.put(key, value);
    }

    public String getValue(String key) {
        return ((String) this.map.get(key));
    }

    public FaalWriteParamsRequest() {
        this.type = 8;
    }

    public String getTn() {
        return this.tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }
}