package com.hzjbbis.fas.protocol.meter;

import com.hzjbbis.fas.protocol.meter.conf.MeterProtocolDataSet;
import com.hzjbbis.util.CastorUtil;

import java.util.Hashtable;

public class MeterProtocolFactory {
    private static Hashtable datamappings;
    private static Object lock = new Object();

    public static MeterProtocolDataSet createMeterProtocolDataSet(String key) {
        synchronized (lock) {
            if (datamappings == null) {
                datamappings = new Hashtable();
            }
            if (!(datamappings.containsKey(key))) {
                datamappings.put(key, createDataSet(key));
            }
            return ((MeterProtocolDataSet) datamappings.get(key));
        }
    }

    private static MeterProtocolDataSet createDataSet(String key) {
        MeterProtocolDataSet dataset = null;
        try {
            if (key.equals("ZJMeter")) {
                dataset = (MeterProtocolDataSet) CastorUtil.unmarshal("com/hzjbbis/fas/protocol/meter/conf/protocol-meter-zj-mapping.xml", "com/hzjbbis/fas/protocol/meter/conf/protocol-meter-zj-dataset.xml");
                dataset.packup();
                return dataset;
            }
            if (key.equals("BBMeter")) {
                dataset = (MeterProtocolDataSet) CastorUtil.unmarshal("com/hzjbbis/fas/protocol/meter/conf/protocol-meter-zj-mapping.xml", "com/hzjbbis/fas/protocol/meter/conf/protocol-meter-bb-dataset.xml");
                dataset.packup();
                return dataset;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }
}