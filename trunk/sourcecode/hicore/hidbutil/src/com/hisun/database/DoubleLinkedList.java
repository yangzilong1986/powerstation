package com.hisun.database;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DoubleLinkedList {
    private int size = 0;

    private static final Log log = LogFactory.getLog(DoubleLinkedList.class);
    private DoubleLinkedListNode first;
    private DoubleLinkedListNode last;

    public synchronized void addLast(DoubleLinkedListNode me) {
        if (this.first == null) {
            this.first = me;
        } else {
            this.last.next = me;
            me.prev = this.last;
        }
        this.last = me;
        this.size += 1;
    }

    public synchronized void addFirst(DoubleLinkedListNode me) {
        if (this.last == null) {
            this.last = me;
        } else {
            this.first.prev = me;
            me.next = this.first;
        }
        this.first = me;
        this.size += 1;
    }

    public synchronized DoubleLinkedListNode getLast() {
        if (log.isDebugEnabled()) {
            log.debug("returning last node");
        }
        return this.last;
    }

    public synchronized DoubleLinkedListNode getFirst() {
        if (log.isDebugEnabled()) {
            log.debug("returning first node");
        }
        return this.first;
    }

    public synchronized void makeFirst(DoubleLinkedListNode ln) {
        if (ln.prev == null) {
            return;
        }
        ln.prev.next = ln.next;

        if (ln.next == null) {
            this.last = ln.prev;
            this.last.next = null;
        } else {
            ln.next.prev = ln.prev;
        }
        this.first.prev = ln;
        ln.next = this.first;
        ln.prev = null;
        this.first = ln;
    }

    public synchronized void removeAll() {
        for (DoubleLinkedListNode me = this.first; me != null;) {
            if (me.prev != null) {
                me.prev = null;
            }
            DoubleLinkedListNode next = me.next;
            me = next;
        }
        this.first = (this.last = null);

        this.size = 0;
    }

    public synchronized boolean remove(DoubleLinkedListNode me) {
        if (log.isDebugEnabled()) {
            log.debug("removing node");
        }

        if (me.next == null) {
            if (me.prev == null) {
                if ((me == this.first) && (me == this.last)) {
                    this.first = (this.last = null);
                }

            } else {
                this.last = me.prev;
                this.last.next = null;
                me.prev = null;
            }
        } else if (me.prev == null) {
            this.first = me.next;
            this.first.prev = null;
            me.next = null;
        } else {
            me.prev.next = me.next;
            me.next.prev = me.prev;
            me.prev = (me.next = null);
        }
        this.size -= 1;

        return true;
    }

    public synchronized DoubleLinkedListNode removeLast() {
        if (log.isDebugEnabled()) {
            log.debug("removing last node");
        }
        DoubleLinkedListNode temp = this.last;
        if (this.last != null) {
            remove(this.last);
        }
        return temp;
    }

    public synchronized int size() {
        return this.size;
    }

    public synchronized void debugDumpEntries() {
        log.debug("dumping Entries");
        for (DoubleLinkedListNode me = this.first; me != null; me = me.next) {
            log.debug("dump Entries> payload= '" + me.getPayload() + "'");
        }
    }
}