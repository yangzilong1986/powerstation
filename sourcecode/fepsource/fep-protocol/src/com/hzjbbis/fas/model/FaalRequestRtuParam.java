package com.hzjbbis.fas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FaalRequestRtuParam implements Serializable {
    private static final long serialVersionUID = 8826872062189860710L;
    private String rtuId;
    private Long cmdId;
    private int[] tn;
    private List<FaalRequestParam> params;

    public void addParam(String name, String value) {
        addParam(new FaalRequestParam(name, value));
    }

    public void addParam(FaalRequestParam param) {
        if (this.params == null) {
            this.params = new ArrayList();
        }
        this.params.add(param);
    }

    public String getRtuId() {
        return this.rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public Long getCmdId() {
        return this.cmdId;
    }

    public void setCmdId(Long cmdId) {
        this.cmdId = cmdId;
    }

    public int[] getTn() {
        return this.tn;
    }

    public void setTn(int[] tn) {
        this.tn = tn;
    }

    public List<FaalRequestParam> getParams() {
        return this.params;
    }

    public void setParams(List<FaalRequestParam> params) {
        this.params = params;
    }
}