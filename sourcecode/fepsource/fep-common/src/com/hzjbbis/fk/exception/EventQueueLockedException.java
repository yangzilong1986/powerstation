package com.hzjbbis.fk.exception;

public class EventQueueLockedException extends Exception {
    private static final long serialVersionUID = 1L;

    public EventQueueLockedException() {
        super("Event queue locked");
    }

    public EventQueueLockedException(String info) {
        super(info);
    }
}