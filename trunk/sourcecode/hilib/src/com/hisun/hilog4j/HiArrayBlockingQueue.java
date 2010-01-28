package com.hisun.hilog4j;


import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;


class HiArrayBlockingQueue {
    private HashMap map = new HashMap();
    private ArrayBlockingQueue list = null;
    private int capacity;
    private Object lock = new Object();


    public HiArrayBlockingQueue(int capacity) {

        this.list = new ArrayBlockingQueue(capacity);

        this.capacity = capacity;

    }


    public void put(Object name, Object value) {

        HiNameValuePair nameValuePair = new HiNameValuePair(name, value);

        put(nameValuePair);

    }


    public Object get(Object name) {

        if (!(this.map.containsKey(name))) {

            return null;

        }

        HiNameValuePair nameValuePair = (HiNameValuePair) this.map.get(name);

        return nameValuePair.value;

    }


    public void remove(Object name) {

        if (!(this.map.containsKey(name))) {

            return;

        }

        synchronized (this.lock) {

            HiNameValuePair nameValuePair = (HiNameValuePair) this.map.remove(name);

            this.list.remove(nameValuePair);

            if (nameValuePair.value instanceof HiCloseable) {

                HiCloseable closeable = (HiCloseable) nameValuePair.value;

                closeable.close();

            }

        }

    }


    public void put(HiNameValuePair nameValuePair) {

        synchronized (this.lock) {

            if (this.list.offer(nameValuePair)) {

                this.map.put(nameValuePair.name, nameValuePair);

                return;

            }

            for (int i = 0; i < this.capacity / 10; ++i) {

                nameValuePair = (HiNameValuePair) this.list.poll();

                this.map.remove(nameValuePair.name);

                if (nameValuePair.value instanceof HiCloseable) {

                    HiCloseable closeable = (HiCloseable) nameValuePair.value;

                    closeable.close();

                }

            }

        }

    }


    public boolean containsKey(Object name) {

        return this.map.containsKey(name);

    }


    class HiNameValuePair {
        Object name;
        Object value;


        public HiNameValuePair(Object paramObject1, Object paramObject2) {

            this.name = paramObject1;

            this.value = value;

        }


        public String toString() {

            return this.name + ":" + this.value;

        }

    }

}