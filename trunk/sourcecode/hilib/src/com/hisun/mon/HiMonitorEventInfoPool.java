package com.hisun.mon;


import edu.emory.mathcs.backport.java.util.concurrent.LinkedBlockingQueue;

import java.util.Iterator;

public class HiMonitorEventInfoPool {
    private static final int MAX_EVENT_STACK = 20;
    private int stackSize;
    private LinkedBlockingQueue queue;
    private static HiMonitorEventInfoPool instance;

    public HiMonitorEventInfoPool() {

        this.queue = new LinkedBlockingQueue();
    }

    public static synchronized HiMonitorEventInfoPool getInstance() {

        if (instance == null) {

            instance = new HiMonitorEventInfoPool();


            instance.setStackSize(20);
        }

        return instance;
    }

    public Iterator getEventInfo() {

        return this.queue.iterator();
    }

    public synchronized void addEvent(HiMonitorEventInfo eventInfo) {

        if (this.queue.size() >= this.stackSize) {

            this.queue.poll();
        }

        this.queue.offer(eventInfo);
    }

    public int getStackSize() {

        return this.stackSize;
    }

    public void setStackSize(int stackSize) {

        this.stackSize = stackSize;
    }
}