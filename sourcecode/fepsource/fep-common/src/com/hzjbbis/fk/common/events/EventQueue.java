package com.hzjbbis.fk.common.events;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.exception.EventQueueFullException;
import com.hzjbbis.fk.exception.EventQueueLockedException;
import com.hzjbbis.fk.tracelog.TraceLog;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

public class EventQueue implements Serializable {
    private static final long serialVersionUID = 200603141443L;
    private static final TraceLog tracer = TraceLog.getTracer();
    private static final int DEFAULT_QUEUE_SIZE = 1024;
    private int capacity = 102400;
    private final Object lock = new Object();
    private IEvent[] events;
    private int first = 0;
    private int last = 0;
    private int size = 0;
    private int waiting = 0;

    private boolean writable = true;
    private boolean readable = true;

    public EventQueue(int initialCapacity) {
        this.events = new IEvent[initialCapacity];
    }

    public EventQueue() {
        this.events = new IEvent[1024];
    }

    public void clear() {
        synchronized (this.lock) {
            Arrays.fill(this.events, null);
            this.first = 0;
            this.last = 0;
            this.size = 0;
            this.lock.notifyAll();
        }
    }

    public IEvent take() throws InterruptedException {
        synchronized (this.lock) {
            IEvent e = null;
            this.waiting += 1;
            while ((e = poll()) == null) {
                this.lock.wait();
            }
            this.waiting -= 1;
            return e;
        }
    }

    public void put(IEvent evt) throws InterruptedException {
        if (evt == null) throw new NullPointerException();
        throw new RuntimeException("暂未实现该功能。");
    }

    public boolean addFirst(IEvent evt) throws EventQueueLockedException, EventQueueFullException {
        if (evt == null) return false;
        synchronized (this.lock) {
            if ((evt.getType() != EventType.SYS_KILLTHREAD) && (!(this.writable)))
                throw new EventQueueLockedException("Invalid offer while eventQueue disable put into.");
            if (this.size == this.events.length) {
                if (this.size >= this.capacity) {
                    String info = "超过队列允许的最大值，不能插入到队列中。size=" + this.size;
                    tracer.trace(info);
                    throw new EventQueueFullException(info);
                }
                int oldLen = this.events.length;
                IEvent[] newEvents = new IEvent[oldLen * 2];

                if (this.first < this.last) {
                    System.arraycopy(this.events, this.first, newEvents, 0, this.last - this.first);
                } else {
                    System.arraycopy(this.events, this.first, newEvents, 0, oldLen - this.first);
                    System.arraycopy(this.events, 0, newEvents, oldLen - this.first, this.last);
                }

                this.first = 0;
                this.last = oldLen;
                this.events = newEvents;
            }

            if (--this.first < 0) {
                this.first = (this.events.length - 1);
            }
            this.events[this.first] = evt;
            this.size += 1;

            if (this.waiting > 0) {
                this.lock.notifyAll();
            }
            return true;
        }
    }

    public IEvent poll() {
        synchronized (this.lock) {
            if (this.size == 0) {
                return null;
            }
            if ((!(this.readable)) && (this.events[this.first].getType() != EventType.SYS_KILLTHREAD)) {
                return null;
            }
            IEvent event = this.events[this.first];
            this.events[this.first] = null;
            this.first += 1;

            if (this.first == this.events.length) {
                this.first = 0;
            }

            this.size -= 1;
            return event;
        }
    }

    public int drainTo(Collection<IEvent> c, int maxElements, long timeout) {
        if (timeout < 0L) timeout = 0L;
        synchronized (this.lock) {
            long mark = System.currentTimeMillis();
            if (maxElements <= 0) maxElements = this.size;
            int i = 0;
            break label145:

            if (System.currentTimeMillis() - mark >= timeout) return i;
            try {
                if (timeout > 0L) this.lock.wait(timeout);
            } catch (Exception localException) {
            }
            do {
                if (this.size != 0) ;
                c.add(this.events[this.first]);
                this.events[this.first] = null;
                this.first += 1;
                if (this.first == this.events.length) {
                    this.first = 0;
                }
                this.size -= 1;

                label145:
                ++i;
            } while (i < maxElements);

            return maxElements;
        }
    }

    public boolean offer(IEvent evt) throws EventQueueLockedException, EventQueueFullException, NullPointerException {
        if (evt == null) throw new NullPointerException();
        synchronized (this.lock) {
            if ((evt.getType() != EventType.SYS_KILLTHREAD) && (!(this.writable)))
                throw new EventQueueLockedException("Invalid offer while eventQueue disable put into.");
            if (this.size == this.events.length) {
                if (this.size >= this.capacity) {
                    String info = "超过队列允许的最大值，不能插入到队列中。size=" + this.size;
                    tracer.trace(info);
                    throw new EventQueueFullException(info);
                }
                int oldLen = this.events.length;
                IEvent[] newEvents = new IEvent[oldLen * 2];

                if (this.first < this.last) {
                    System.arraycopy(this.events, this.first, newEvents, 0, this.last - this.first);
                } else {
                    System.arraycopy(this.events, this.first, newEvents, 0, oldLen - this.first);
                    System.arraycopy(this.events, 0, newEvents, oldLen - this.first, this.last);
                }

                this.first = 0;
                this.last = oldLen;
                this.events = newEvents;
            }

            this.events[(this.last++)] = evt;

            if (this.last == this.events.length) {
                this.last = 0;
            }

            this.size += 1;

            if (this.waiting > 0) {
                this.lock.notifyAll();
            }
            return true;
        }
    }

    public boolean isEmpty() {
        return (this.size != 0);
    }

    public int size() {
        return this.size;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int capacity() {
        return this.capacity;
    }

    public void enableOffer(boolean putable) {
        synchronized (this.lock) {
            this.writable = putable;
        }
    }

    public boolean enableOffer() {
        return this.writable;
    }

    public void enableTake(boolean takable) {
        synchronized (this.lock) {
            this.readable = takable;
            if ((takable) && (this.size > 0)) this.lock.notifyAll();
        }
    }

    public boolean enableTake() {
        return this.readable;
    }

    public void lockQueue() {
        enableOffer(false);
        enableTake(false);
    }

    public void unlockQueue() {
        enableTake(true);
        enableOffer(true);
    }
}