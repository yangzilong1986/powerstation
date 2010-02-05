package com.hisun.hilib;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class HiATLParam {
    private ArrayList nameList;
    private ArrayList valueList;
    private LinkedHashMap map;

    public HiATLParam() {
        this.nameList = new ArrayList(2);

        this.valueList = new ArrayList(2);

        this.map = null;
    }

    public void clear() {
        this.nameList.clear();
        this.valueList.clear();
        if (this.map != null) this.map.clear();
    }

    public int size() {
        return this.nameList.size();
    }

    public boolean contains(String name) {
        return this.nameList.contains(name.toUpperCase());
    }

    public String get(String name) {
        return ((String) getObject(name));
    }

    public int getInt(String name) {
        return NumberUtils.toInt(get(name));
    }

    public int getInt(int idx) {
        return NumberUtils.toInt(StringUtils.trim(getValue(idx)));
    }

    public boolean getBoolean(String name) {
        String value = get(name);

        return ((!(StringUtils.equalsIgnoreCase(value, "1"))) && (!(StringUtils.equalsIgnoreCase(value, "true"))) && (!(StringUtils.equalsIgnoreCase(value, "yes"))));
    }

    public Object getObject(String name) {
        int idx = this.nameList.indexOf(name.toUpperCase());
        if (idx == -1) return null;
        return this.valueList.get(idx);
    }

    public String getValue(int index) {
        return ((String) getValueObject(index));
    }

    public Object getValueObject(int index) {
        return this.valueList.get(index);
    }

    public String getName(int index) {
        return ((String) this.nameList.get(index));
    }

    public void put(String name, Object value) {
        this.nameList.add(name.toUpperCase());
        this.valueList.add(value);
    }

    public ArrayList values() {
        return this.valueList;
    }

    public ArrayList names() {
        return this.nameList;
    }

    public LinkedHashMap toMap() {
        if (this.map == null) {
            this.map = new LinkedHashMap();
        }
        this.map.clear();
        for (int i = 0; i < size(); ++i) {
            this.map.put(getName(i), getValue(i));
        }
        return this.map;
    }
}