package com.hisun.util;

import java.util.HashMap;

public class PropertyMessageResourcesFactory extends MessageResourcesFactory {
    private HashMap messageResourceCache;

    public PropertyMessageResourcesFactory() {
        this.messageResourceCache = new HashMap();
    }

    public MessageResources createResources(String config) {
        if (!(this.messageResourceCache.containsKey(config))) {
            PropertyMessageResources messageResources = new PropertyMessageResources(this, config, this.returnNull);

            messageResources.setCheckInterval(HiICSProperty.getInt("sys.checkinterval", 10));
            this.messageResourceCache.put(config, messageResources);
        }
        return ((MessageResources) this.messageResourceCache.get(config));
    }
}