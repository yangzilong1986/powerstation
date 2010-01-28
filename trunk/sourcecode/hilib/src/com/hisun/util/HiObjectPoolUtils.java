package com.hisun.util;


import org.apache.commons.pool.impl.GenericObjectPool;

import java.util.ArrayList;
import java.util.HashMap;

public class HiObjectPoolUtils {
    private static int MAX_ATIVED = 200;
    private GenericObjectPool stringBufferPool;
    private GenericObjectPool hashMapPool;
    private GenericObjectPool arrayListPool;
    private static HiObjectPoolUtils instance;

    public HiObjectPoolUtils() {

        this.stringBufferPool = new GenericObjectPool(new StringBuidlerFactory(), MAX_ATIVED, 2, 10L);


        this.hashMapPool = new GenericObjectPool(new HashMapFactory(), MAX_ATIVED, 2, 10L);


        this.arrayListPool = new GenericObjectPool(new ArrayListFactory(), MAX_ATIVED, 2, 10L);
    }

    public static synchronized HiObjectPoolUtils getInstance() {

        if (instance == null) {

            instance = new HiObjectPoolUtils();
        }

        return instance;
    }

    public StringBuilder borrowStringBuilder() {
        try {

            return ((StringBuilder) this.stringBufferPool.borrowObject());
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public void returnStringBuilder(StringBuilder arg0) {
        try {

            this.stringBufferPool.returnObject(arg0);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public HashMap borrowHashMap() {
        try {

            return ((HashMap) this.hashMapPool.borrowObject());
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public void returnHashMap(HashMap arg0) {
        try {

            this.hashMapPool.returnObject(arg0);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public ArrayList borrowArrayList() {
        try {

            return ((ArrayList) this.arrayListPool.borrowObject());
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public void returnArrayList(ArrayList arg0) {
        try {

            this.arrayListPool.returnObject(arg0);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}