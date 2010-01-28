package com.hisun.service;

import com.hisun.util.HiThreadPool;

public abstract interface IThreadPoolFactory {
    public abstract HiThreadPool getThreadPool(String paramString);
}