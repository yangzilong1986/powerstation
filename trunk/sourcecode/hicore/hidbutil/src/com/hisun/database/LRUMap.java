package com.hisun.database;

import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;

import java.util.*;

public class LRUMap implements Map {
    private static final Logger log = HiLog.getLogger("lrumap.trc");
    private DoubleLinkedList list;
    protected Map map;
    int hitCnt;
    int missCnt;
    int putCnt;
    int maxObjects;
    private int chunkSize;

    public LRUMap() {
        this.hitCnt = 0;

        this.missCnt = 0;

        this.putCnt = 0;

        this.maxObjects = -1;

        this.chunkSize = 1;

        this.list = new DoubleLinkedList();

        this.map = new Hashtable();
    }

    public LRUMap(int maxObjects) {
        this.maxObjects = maxObjects;
    }

    public int size() {
        return this.map.size();
    }

    public void clear() {
        this.map.clear();
        this.list.removeAll();
    }

    public boolean isEmpty() {
        return (this.map.size() == 0);
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    public Collection values() {
        return this.map.values();
    }

    public void putAll(Map source) {
        if (source == null) return;
        Set entries = source.entrySet();
        Iterator it = entries.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            put(entry.getKey(), entry.getValue());
        }
    }

    public Object get(Object key) {
        Object retVal = null;

        if (log.isDebugEnabled()) {
            log.debug("getting item  for key " + key);
        }

        LRUElementDescriptor me = (LRUElementDescriptor) this.map.get(key);

        if (me != null) {
            this.hitCnt += 1;
            if (log.isDebugEnabled()) {
                log.debug("LRUMap hit for " + key);
            }

            retVal = me.getPayload();

            this.list.makeFirst(me);
        } else {
            this.missCnt += 1;
            log.debug("LRUMap miss for " + key);
        }

        return retVal;
    }

    public Object getQuiet(Object key) {
        Object ce = null;

        LRUElementDescriptor me = (LRUElementDescriptor) this.map.get(key);
        if (me != null) {
            if (log.isDebugEnabled()) {
                log.debug("LRUMap quiet hit for " + key);
            }

            ce = me.getPayload();
        } else if (log.isDebugEnabled()) {
            log.debug("LRUMap quiet miss for " + key);
        }

        return ce;
    }

    public Object remove(Object key) {
        if (log.isDebugEnabled()) {
            log.debug("removing item for key: " + key);
        }

        LRUElementDescriptor me = (LRUElementDescriptor) this.map.remove(key);

        if (me != null) {
            this.list.remove(me);

            return me.getPayload();
        }

        return null;
    }

    public Object put(Object key, Object value) {
        this.putCnt += 1;

        LRUElementDescriptor old = null;
        synchronized (this) {
            addFirst(key, value);

            old = (LRUElementDescriptor) this.map.put(((LRUElementDescriptor) this.list.getFirst()).getKey(), this.list.getFirst());

            if ((old != null) && (((LRUElementDescriptor) this.list.getFirst()).getKey().equals(old.getKey()))) {
                this.list.remove(old);
            }
        }

        int size = this.map.size();

        if ((this.maxObjects >= 0) && (size > this.maxObjects)) {
            if (log.isDebugEnabled()) {
                log.debug("In memory limit reached, removing least recently used.");
            }

            int chunkSizeCorrected = Math.min(size, getChunkSize());

            if (log.isDebugEnabled()) {
                log.debug("About to remove the least recently used. map size: " + size + ", max objects: " + this.maxObjects + ", items to spool: " + chunkSizeCorrected);
            }

            for (int i = 0; i < chunkSizeCorrected; ++i) {
                synchronized (this) {
                    if (this.list.getLast() != null) {
                        if ((LRUElementDescriptor) this.list.getLast() != null) {
                            processRemovedLRU(((LRUElementDescriptor) this.list.getLast()).getKey(), ((LRUElementDescriptor) this.list.getLast()).getPayload());

                            if (!(this.map.containsKey(((LRUElementDescriptor) this.list.getLast()).getKey()))) {
                                log.error("update: map does not contain key: " + ((LRUElementDescriptor) this.list.getLast()).getKey());

                                verifyCache();
                            }
                            if (this.map.remove(((LRUElementDescriptor) this.list.getLast()).getKey()) == null) {
                                log.warn("update: remove failed for key: " + ((LRUElementDescriptor) this.list.getLast()).getKey());

                                verifyCache();
                            }
                        } else {
                            throw new Error("update: last.ce is null!");
                        }
                        this.list.removeLast();
                    } else {
                        verifyCache();
                        throw new Error("update: last is null!");
                    }
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("update: After spool map size: " + this.map.size());
            }
            if (this.map.size() != dumpCacheSize()) {
                log.error("update: After spool, size mismatch: map.size() = " + this.map.size() + ", linked list size = " + dumpCacheSize());
            }

        }

        if (old != null) {
            return old.getPayload();
        }
        return null;
    }

    private synchronized void addFirst(Object key, Object val) {
        LRUElementDescriptor me = new LRUElementDescriptor(key, val);
        this.list.addFirst(me);
    }

    private int dumpCacheSize() {
        return this.list.size();
    }

    public void dumpCacheEntries() {
        log.debug("dumpingCacheEntries");
        for (LRUElementDescriptor me = (LRUElementDescriptor) this.list.getFirst(); me != null; me = (LRUElementDescriptor) me.next) {
            if (!(log.isDebugEnabled())) continue;
            log.debug("dumpCacheEntries> key=" + me.getKey() + ", val=" + me.getPayload());
        }
    }

    public void dumpMap() {
        log.debug("dumpingMap");
        for (Iterator itr = this.map.entrySet().iterator(); itr.hasNext();) {
            Map.Entry e = (Map.Entry) itr.next();
            LRUElementDescriptor me = (LRUElementDescriptor) e.getValue();
            if (log.isDebugEnabled()) {
                log.debug("dumpMap> key=" + e.getKey() + ", val=" + me.getPayload());
            }
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n");
        buffer.append("size: " + dumpCacheSize());
        buffer.append("\n");
        for (Iterator itr = this.map.entrySet().iterator(); itr.hasNext();) {
            Map.Entry e = (Map.Entry) itr.next();
            LRUElementDescriptor me = (LRUElementDescriptor) e.getValue();
            buffer.append("dumpMap> key=" + e.getKey() + ", val=" + me.getPayload() + "\n");
        }
        return buffer.toString();
    }

    protected void verifyCache() {
        if (log.isDebugEnabled()) return;
        return;
    }

    protected void verifyCache(Object key) {
        if (!(log.isDebugEnabled())) {
            return;
        }

        boolean found = false;

        for (LRUElementDescriptor li = (LRUElementDescriptor) this.list.getFirst(); li != null; li = (LRUElementDescriptor) li.next) {
            if (li.getKey() != key) continue;
            found = true;
            log.debug("verifycache(key) key match: " + key);
            break;
        }

        if (found) return;
        log.error("verifycache(key), couldn't find key! : " + key);
    }

    protected void processRemovedLRU(Object key, Object value) {
        if (!(log.isDebugEnabled())) return;
        log.debug("Removing key: [" + key + "] from LRUMap store, value = [" + value + "]");
        log.debug("LRUMap store size: '" + size() + "'.");
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public int getChunkSize() {
        return this.chunkSize;
    }

    public synchronized Set entrySet() {
        Set entries = this.map.entrySet();

        Set unWrapped = new HashSet();

        Iterator it = entries.iterator();
        while (it.hasNext()) {
            Map.Entry pre = (Map.Entry) it.next();
            Map.Entry post = new LRUMapEntry(pre.getKey(), ((LRUElementDescriptor) pre.getValue()).getPayload());
            unWrapped.add(post);
        }

        return unWrapped;
    }

    public Set keySet() {
        return this.map.keySet();
    }
}