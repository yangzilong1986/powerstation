package com.hisun.util;


public class HiThreadSemaphore extends HiSemaphore {

    public HiThreadSemaphore(int maxNum, int tmOut) {

        super(maxNum, tmOut);

        this.msg = "Thread";

    }

}