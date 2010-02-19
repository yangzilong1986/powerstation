package com.hzjbbis.fk.exception;

public class EventQueueFullException extends Exception {
    private static final long serialVersionUID = -2635406350673686184L;

    public EventQueueFullException() {
        super("event queue is full");
    }

    public EventQueueFullException(String info) {
        super(info);
    }
}