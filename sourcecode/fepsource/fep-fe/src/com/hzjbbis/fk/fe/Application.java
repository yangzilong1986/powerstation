package com.hzjbbis.fk.fe;

import com.hzjbbis.fk.utils.ClassLoaderUtil;

public class Application {
    public static void main(String[] args) {
        ClassLoaderUtil.initializeClassPath();
        FeCommunication.main(args);
    }
}