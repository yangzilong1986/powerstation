package com.hzjbbis.fas.protocol.meter.conf;

import java.util.*;

public class MeterProtocolDataSet {
    private static final String PROTOCOL_NAME = "ZJMETER";
    private String name;
    private Hashtable dataset;
    private Map<String, String> codeConvertMap;
    private List dataarray;

    public MeterProtocolDataSet() {
        this("ZJMETER", new Hashtable());
    }

    public MeterProtocolDataSet(String name, Hashtable dataset) {
        this.codeConvertMap = new HashMap();
        this.dataarray = new ArrayList();

        this.name = name;
        this.dataset = dataset;
    }

    public MeterProtocolDataItem getDataItem(String code) {
        return ((MeterProtocolDataItem) this.dataset.get(code));
    }

    public Hashtable getDataset() {
        return this.dataset;
    }

    public void setDataset(Hashtable dataset) {
        this.dataset = dataset;
    }

    public List getDataarray() {
        return this.dataarray;
    }

    public void setDataarray(List dataarray) {
        this.dataarray = dataarray;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void packup() {
        arrayToMap(this.dataarray);
    }

    private void arrayToMap(List datas) {
        Iterator iter;
        if (datas != null) for (iter = datas.iterator(); iter.hasNext();) {
            MeterProtocolDataItem child = (MeterProtocolDataItem) iter.next();
            addChild(child);
        }
    }

    private void addChild(MeterProtocolDataItem item) {
        this.dataset.put(item.getCode(), item);
        this.codeConvertMap.put(item.getZjcode(), item.getCode());
        List cnodes = item.getChildarray();
        arrayToMap(cnodes);
    }

    public String getConvertCode(String code) {
        return ((String) this.codeConvertMap.get(code));
    }
}