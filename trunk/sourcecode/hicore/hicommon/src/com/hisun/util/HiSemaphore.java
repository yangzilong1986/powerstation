package com.hisun.util;

import com.hisun.exception.HiException;
import edu.emory.mathcs.backport.java.util.concurrent.Semaphore;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class HiSemaphore {
    private Semaphore available = null;

    private int tmOut = -1;
    protected String msg = null;

    public HiSemaphore(int maxNum, int tmOut) {
        if (maxNum != -1) {
            this.available = new Semaphore(maxNum, true);
        }
        this.tmOut = tmOut;
    }

    public Semaphore getSemaphore() {
        return this.available;
    }

    public void acquire() throws HiException {
        if (this.available == null) return;
        try {
            if (this.tmOut == -1) {
                this.available.acquire();
            } else if (!(this.available.tryAcquire(this.tmOut, TimeUnit.SECONDS)))
                throw new HiException("215029", this.msg);
        } catch (InterruptedException e) {
            throw new HiException(e);
        }
    }

    public void release() throws HiException {
        this.available.release();
    }
}