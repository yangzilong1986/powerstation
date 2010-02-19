package com.hzjbbis.fas.protocol.data;

import java.util.ArrayList;
import java.util.List;

public class RTUReply {
    private int type;
    private List data;
    private String des;

    public RTUReply() {
        this(0, null);
    }

    public RTUReply(int type, List data) {
        this.type = type;
        this.data = data;
    }

    public void addDataItem(DataItem item) {
        if (this.data == null) {
            this.data = new ArrayList();
        }
        this.data.add(item);
    }

    public List getData() {
        return this.data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toString() {
        return this.des;
    }
}