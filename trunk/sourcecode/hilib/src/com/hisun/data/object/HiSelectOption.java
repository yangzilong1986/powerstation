package com.hisun.data.object;

public class HiSelectOption {
    private String id;
    private String value;
    private String desc;
    private String sort;

    public String getId() {

        return this.id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getValue() {

        return this.value;
    }

    public void setValue(String value) {

        this.value = value;
    }

    public String getDesc() {

        return this.desc;
    }

    public void setDesc(String desc) {

        this.desc = desc;
    }

    public String getSort() {

        return this.sort;
    }

    public void setSort(String sort) {

        this.sort = sort;
    }

    public String toString() {

        return String.format("[%s][%s][%s][%s]", new Object[]{this.id, this.value, this.desc, this.sort});
    }
}