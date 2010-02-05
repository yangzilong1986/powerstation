package com.hisun.cnaps.common;

import java.util.HashMap;

public class HiRepeatTagManager {
    public HashMap rTags;

    public HiRepeatTagManager() {
        this.rTags = new HashMap(3);
    }

    public synchronized String nextTagEtfName(String etfName) {
        Integer count = (Integer) this.rTags.remove(etfName);
        if (count == null) {
            count = new Integer(1);
        } else if (count != null) count = new Integer(count.intValue() + 1);
        this.rTags.put(etfName, count);
        return count.toString();
    }

    public synchronized String getTagCount(String etfName) {
        return this.rTags.get(etfName).toString();
    }
}