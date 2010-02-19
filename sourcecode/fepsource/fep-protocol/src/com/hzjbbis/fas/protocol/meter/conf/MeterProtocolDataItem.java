package com.hzjbbis.fas.protocol.meter.conf;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MeterProtocolDataItem {
    private String code;
    private String description;
    private String zjcode;
    private String zjcode2;
    private int length;
    private int type;
    private int fraction;
    private String familycode;
    private Hashtable children;
    private List childarray;

    public MeterProtocolDataItem() {
        this("", "", "", 0, 0, 0, "");
    }

    public MeterProtocolDataItem(String code, String zjcode, String description, int len, int type, int fraction, String familycode) {
        this.childarray = new ArrayList();

        this.code = code;
        this.zjcode = zjcode;
        this.length = len;
        this.type = type;
        this.fraction = fraction;
        this.description = description;
        this.familycode = familycode;
        this.children = new Hashtable();
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFraction() {
        return this.fraction;
    }

    public void setFraction(int fraction) {
        this.fraction = fraction;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int len) {
        this.length = len;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getZjcode() {
        return this.zjcode;
    }

    public void setZjcode(String zjcode) {
        this.zjcode = zjcode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFamilycode() {
        return this.familycode;
    }

    public void setFamilycode(String familycode) {
        this.familycode = familycode;
    }

    public Hashtable getChildren() {
        return this.children;
    }

    public void setChildren(Hashtable children) {
        this.children = children;
    }

    public List getChildarray() {
        return this.childarray;
    }

    public void setChildarray(ArrayList childarray) {
        this.childarray = childarray;
    }

    public String getZjcode2() {
        return this.zjcode2;
    }

    public void setZjcode2(String zjcode2) {
        this.zjcode2 = zjcode2;
    }
}