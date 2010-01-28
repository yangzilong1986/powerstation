package com.hisun.startup;


import com.hisun.exception.HiException;

public class HiBootStrap {
    public static void main(String[] args) throws HiException {

        if (args.length != 2) {

            System.out.println("USAGE: jbserv SERVER_NAME ATR_FILE");

            return;
        }


        HiStartup startup = HiStartup.getInstance(args[0]);

        startup.load(args[0], args[1]);

        await();
    }

    public static void await() {
        try {

            Thread.sleep(100000L);
        } catch (InterruptedException ex) {
        }
    }
}