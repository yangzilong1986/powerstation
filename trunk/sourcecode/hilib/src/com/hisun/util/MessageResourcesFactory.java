package com.hisun.util;


import java.io.Serializable;


public abstract class MessageResourcesFactory implements Serializable {
    protected static transient Class clazz = null;

    protected static String factoryClass = "com.hisun.util.PropertyMessageResourcesFactory";
    protected boolean returnNull;


    public MessageResourcesFactory() {

        this.returnNull = false;

    }


    public boolean getReturnNull() {

        return this.returnNull;

    }


    public void setReturnNull(boolean returnNull) {

        this.returnNull = returnNull;

    }


    public abstract MessageResources createResources(String paramString);


    public static String getFactoryClass() {

        return factoryClass;

    }


    public static void setFactoryClass(String factoryClass) {

        factoryClass = factoryClass;

        clazz = null;

    }


    public static MessageResourcesFactory createFactory() {

        try {

            if (clazz == null) {

                clazz = Class.forName(factoryClass);

            }


            MessageResourcesFactory factory = (MessageResourcesFactory) clazz.newInstance();


            return factory;

        } catch (Throwable t) {

        }

        return null;

    }

}