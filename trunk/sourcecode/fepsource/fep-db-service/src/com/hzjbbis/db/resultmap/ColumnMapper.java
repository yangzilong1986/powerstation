package com.hzjbbis.db.resultmap;

import java.lang.reflect.Method;

public class ColumnMapper {
    private String property;
    private String column;
    private int index;
    public Method method;

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getColumn() {
        return this.column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}