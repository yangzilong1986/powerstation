package com.hisun.util;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;


class TimeOutCtlThreadPool extends HiThreadPool {
    private int timeout;
    private Map futures = new HashMap();

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    protected void afterExecute(Runnable r, Throwable t) {

        System.out.println("after execute task:" + r);

        if (this.futures.containsKey(r)) {

            ScheduledFuture future = (ScheduledFuture) this.futures.remove(r);

            future.cancel(true);

            System.out.println("cancle check timeout task");

        }

    }


    public void shutdown() {

        super.shutdown();

        this.executor.shutdown();

    }


    public TimeOutCtlThreadPool(int timeout) {

        this.timeout = timeout;

    }


    final class TimeOutChecker implements Runnable {
        final Runnable task;
        final Future future;


        TimeOutChecker(Runnable paramRunnable, Future paramFuture) {

            this.task = paramRunnable;

            this.future = future;

        }


        public void run() {

            if (this.future.isDone()) return;

            if (this.future.cancel(true)) System.out.println("成功cancle任务执行!");

            else {

                System.out.println("终止线程失败!");

            }


            TimeOutCtlThreadPool.this.futures.remove(this.task);

            System.out.println("remove check timeout task:" + this.task);

        }

    }

}