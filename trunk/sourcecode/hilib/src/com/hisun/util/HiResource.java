package com.hisun.util;


import java.io.InputStream;
import java.net.URL;

public class HiResource {
    public static InputStream getResourceAsStream(String name) {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        return loader.getResourceAsStream(name);
    }

    public static URL getResource(String name) {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        return loader.getResource(name);
    }

    public static Class loadClass(String name) throws ClassNotFoundException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        return loader.loadClass(name);
    }

    public static Class loadClassPrefix(String name) throws ClassNotFoundException {

        return loadClass("com.hisun.specproc." + name);
    }
}