package com.hzjbbis.fas.protocol.meter;

public class MeterParserFactory {
    public static IMeterParser getMeterParser(String type) {
        IMeterParser rt = null;
        try {
            if (type.equals("ZJMeter")) {
                rt = new ZjMeterParser();
            }
            if (type.equals("BBMeter")) {
                rt = new BbMeterParser();
            }
            if (type.equals("SMMeter")) rt = new SmMeterParser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }
}