package com.hisun.data.cache;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class HiDataCacheConfigItem {
    private String sql;
    private String file;
    private String id;
    private String key;
    private String className;
    private LinkedHashMap<String, String> colMaps;

    public HiDataCacheConfigItem() {
        this.colMaps = new LinkedHashMap();
    }

    boolean isSql() {
        return (!(StringUtils.isBlank(this.sql)));
    }

    boolean isFile() {
        return (!(StringUtils.isBlank(this.file)));
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setColInfo(String srcCol, String dstCol) {
        this.colMaps.put(srcCol, dstCol);
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getNewObject() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        super.getClass();
        return Class.forName(this.className).newInstance();
    }

    public HashMap<String, String> getColMaps() {
        return this.colMaps;
    }

    public String toString() {
        return String.format("[%s][%s][%s][%s][%s][%s]", new Object[]{this.sql, this.file, this.id, this.key, this.className, this.colMaps});
    }
}