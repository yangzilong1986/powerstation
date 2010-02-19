package com.hzjbbis.fas.model;

import java.io.Serializable;

public class FaalRequestParam implements Serializable {
    private static final long serialVersionUID = 8826872062189860703L;
    private String name;
    private String value;

    public FaalRequestParam() {
    }

    public FaalRequestParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        return "DI" + this.name + "=" + this.value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}