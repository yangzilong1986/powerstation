package com.hisun.service.imp;

import com.hisun.service.IThreadPoolFactory;
import com.hisun.util.HiThreadPool;

import java.util.HashMap;
import java.util.Map;

public class HiThreadPoolFactory implements IThreadPoolFactory {
    private Map pools;

    public HiThreadPoolFactory() {
        this.pools = new HashMap();
    }

    public synchronized HiThreadPool getThreadPool(String id) {
        if (this.pools.containsKey(id)) {
            return ((HiThreadPool) this.pools.get(id));
        }

        HiThreadPool pool = new HiThreadPool();
        this.pools.put(id, pool);
        return pool;
    }
}