package com.hisun.util;

import java.util.*;

public class LockableHashtable extends HashMap {
    Vector lockedEntries;
    private HashMap parent = null;

    public LockableHashtable() {
    }

    public LockableHashtable(int p1, float p2) {
        super(p1, p2);
    }

    public LockableHashtable(Map p1) {
        super(p1);
    }

    public LockableHashtable(int p1) {
        super(p1);
    }

    public void setParent(HashMap parent) {
        this.parent = parent;
    }

    public HashMap getParent() {
        return this.parent;
    }

    public Set getAllKeys() {
        HashSet set = new HashSet();
        set.addAll(super.keySet());
        HashMap p = this.parent;
        while (p != null) {
            set.addAll(p.keySet());
            if (p instanceof LockableHashtable) {
                p = ((LockableHashtable) p).getParent();
            }
            p = null;
        }

        return set;
    }

    public Object get(Object key) {
        Object ret = super.get(key);
        if ((ret == null) && (this.parent != null)) {
            ret = this.parent.get(key);
        }
        return ret;
    }

    public Object put(Object p1, Object p2, boolean locked) {
        if ((this.lockedEntries != null) && (containsKey(p1)) && (this.lockedEntries.contains(p1))) {
            return null;
        }
        if (locked) {
            if (this.lockedEntries == null) {
                this.lockedEntries = new Vector();
            }
            this.lockedEntries.add(p1);
        }
        return super.put(p1, p2);
    }

    public Object put(Object p1, Object p2) {
        return put(p1, p2, false);
    }

    public Object remove(Object p1) {
        if ((this.lockedEntries != null) && (this.lockedEntries.contains(p1))) {
            return null;
        }
        return super.remove(p1);
    }

    public boolean isKeyLocked(Object key) {
        return ((this.lockedEntries != null) && (this.lockedEntries.contains(key)));
    }
}