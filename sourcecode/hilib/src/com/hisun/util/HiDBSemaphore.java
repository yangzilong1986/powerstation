package com.hisun.util;


public class HiDBSemaphore extends HiSemaphore {

    public HiDBSemaphore(int maxNum, int tmOut) {

        super(maxNum, tmOut);

        this.msg = "DB Connection";

    }

}