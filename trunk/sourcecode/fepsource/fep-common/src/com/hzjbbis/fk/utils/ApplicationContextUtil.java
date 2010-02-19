package com.hzjbbis.fk.utils;

import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil {
    private static ApplicationContext context = null;

    public static void setContext(ApplicationContext contextInput) {
        context = contextInput;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static Object getBean(String id) {
        return context.getBean(id);
    }
}