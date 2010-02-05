package com.hisun.cnaps.messages;

public class HiCnapsMessageAreaFactory {
    public static HiCnapsMessageArea createCnapsMessageArea(String mark) {
        if (mark.equals("{2:")) {
            return new HiCnapsMessageBusArea();
        }
        if (mark.equals("{3:")) {
            return new HiCnapsTagMessageArea();
        }
        return null;
    }

    public static HiCnapsMessageArea createCnapsMessageArea(int body) {
        switch (body) {
            case 2:
                return new HiCnapsMessageBusArea();
            case 3:
                return new HiCnapsTagMessageArea();
        }
        return null;
    }
}