package com.hisun.util;


import java.util.concurrent.ArrayBlockingQueue;


class HiArrayBlockingQueue extends ArrayBlockingQueue {
    private static final long serialVersionUID = -6024891247208676281L;


    public HiArrayBlockingQueue() {

        super(1);

    }


    public boolean offer(Object e) {

        return false;

    }

}