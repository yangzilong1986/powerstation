package com.hisun.data.cache;

public class HiDataCacheFactory {
    public HiDataCache createDBCache() {

        return new HiDataCacheDBImpl();
    }

    public HiDataCache createFileCache() {

        return new HiDataCacheFileImpl();
    }
}