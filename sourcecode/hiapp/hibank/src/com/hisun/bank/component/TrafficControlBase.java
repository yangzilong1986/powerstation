package com.hisun.bank.component;

import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public abstract class TrafficControlBase {
    private HashMap semaphores;
    private Object lock;
    private static String ID = "id";
    private static String COUNT = "count";
    private static String TIMEOUT = "timeout";

    public TrafficControlBase() {
        this.semaphores = new HashMap(10);
        this.lock = new Object();
    }

    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        Logger log = HiLog.getLogger(ctx.getCurrentMsg());
        String id = args.get(ID);
        int count = args.getInt(COUNT);
        if (StringUtils.isBlank(id)) {
            throw new HiException("213307", ID);
        }
        if (count == 0) {
            throw new HiException("213309", COUNT, "the args [" + COUNT + "] not found  or value is 0");
        }

        if (count < 0) {
            throw new HiException("213309", COUNT, "the args [" + COUNT + "] value is invaild");
        }

        long timeout = args.getInt(TIMEOUT);
        if (timeout == 0L) {
            timeout = 200L;
        }
        if (log.isDebugEnabled()) {
            log.debug("traffic control： id = [" + id + "]" + " count =[" + count + "]" + " timeout = [" + timeout + "]");
        }

        Semaphore semaphore = null;
        synchronized (this.lock) {
            boolean existed = this.semaphores.containsKey(id);
            if (existed) {
                semaphore = (Semaphore) this.semaphores.get(id);
            } else {
                semaphore = new Semaphore(count);
                this.semaphores.put(id, semaphore);
            }
        }

        boolean oked = false;
        try {
            oked = semaphore.tryAcquire(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new HiException(e);
        }

        if (!(oked)) {
            log.warn("[" + id + "]; traffic full");
            return 90;
        }

        if (log.isDebugEnabled()) {
            log.debug(Thread.currentThread().getName() + "[" + id + "]; free traffic:[" + semaphore.availablePermits() + "]");
        }

        try {
            e = doExecute(args, ctx);

            return e;
        } finally {
            semaphore.release();
        }
    }

    protected abstract int doExecute(HiATLParam paramHiATLParam, HiMessageContext paramHiMessageContext) throws HiException;
}