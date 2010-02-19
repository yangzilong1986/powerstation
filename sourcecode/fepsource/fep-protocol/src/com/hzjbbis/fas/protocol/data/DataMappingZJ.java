package com.hzjbbis.fas.protocol.data;

import java.util.Hashtable;

public class DataMappingZJ implements IMapping {
    public static final int ORIENTATION_TO_RTU = 0;
    public static final int ORIENTATION_TO_APP = 1;
    public static final int ERROR_CODE_OK = 0;
    public static final int ERROR_CODE_NOREP = 1;
    public static final int ERROR_CODE_INVALIDCONT = 2;
    public static final int ERROR_CODE_LOWRIGHTS = 3;
    public static final int ERROR_CODE_NOITEM = 4;
    public static final int ERROR_CODE_NOTARGET = 17;
    public static final int ERROR_CODE_SENDFAILUER = 18;
    public static final int ERROR_CODE_SMSLONG = 19;
    private Hashtable dataitems;

    public DataMappingZJ() {
        loadMapping();
    }

    private void loadMapping() {
    }

    public DataItem getDataItem(String key) {
        DataItem rt = null;
        try {
            if (this.dataitems.containsKey(key)) rt = (DataItem) this.dataitems.get(key);
        } catch (Exception e) {
        }
        return rt;
    }
}