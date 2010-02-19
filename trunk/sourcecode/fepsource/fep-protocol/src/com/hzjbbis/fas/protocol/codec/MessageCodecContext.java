package com.hzjbbis.fas.protocol.codec;

import java.util.ArrayList;
import java.util.List;

public class MessageCodecContext {
    private static final ThreadLocal task = new ThreadLocal();

    private static final ThreadLocal codes = new ThreadLocal();

    public static void setTaskNum(String taskNum) {
        task.set(taskNum);
    }

    public static String getTaskNum() {
        return ((String) task.get());
    }

    public static String pollTaskNum() {
        String taskNum = (String) task.get();
        if (taskNum != null) {
            task.set(null);
        }

        return taskNum;
    }

    public static void addAlertCode(int alertCode) {
        List alertCodes = (List) codes.get();
        if (alertCodes == null) {
            alertCodes = new ArrayList(1);
            codes.set(alertCodes);
        }
        alertCodes.add(new Integer(alertCode));
    }

    public static List getAlertCodes() {
        return ((List) codes.get());
    }

    public static List pollAlertCodes() {
        List alertCodes = (List) codes.get();
        if (alertCodes != null) {
            codes.set(null);
        }

        return alertCodes;
    }
}