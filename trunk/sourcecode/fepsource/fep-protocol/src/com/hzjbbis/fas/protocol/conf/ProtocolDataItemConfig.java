package com.hzjbbis.fas.protocol.conf;

import com.hzjbbis.fas.protocol.zj.parse.ParseTool;

import java.util.Iterator;
import java.util.List;

public class ProtocolDataItemConfig implements IDataItem {
    private String code;
    private String parentCode;
    private int length;
    private String type;
    private String format;
    private int parserno;
    private int fraction;
    private String keychar;
    private String bean;
    private String property;
    private List childItems;
    private int datakey;
    private List items;
    private int dkey;

    public ProtocolDataItemConfig() {
        this.parserno = 0;

        this.fraction = 0;
    }

    public int getDataKey() {
        return this.datakey;
    }

    public int getDatakey() {
        return this.datakey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
        this.datakey = ParseTool.HexToDecimal(code);
    }

    public int getLength() {
        if ((this.length == 0) && (this.childItems != null)) {
            for (int i = 0; i < this.childItems.size(); ++i) {
                this.length += ((ProtocolDataItemConfig) this.childItems.get(i)).getLength();
            }
        }
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
        try {
            if ((type != null) && (type.length() > 3)) {
                this.parserno = Integer.parseInt(type.substring(0, 2));
                this.fraction = Integer.parseInt(type.substring(2, 4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getBean() {
        return this.bean;
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public List getChildItems() {
        return this.childItems;
    }

    public void setChildItems(List childItems) {
        this.childItems = childItems;
        this.length = 0;
    }

    public int getFraction() {
        return this.fraction;
    }

    public int getParserno() {
        return this.parserno;
    }

    public String getSdRobot() {
        return null;
    }

    public List getStandardDatas() {
        return this.items;
    }

    public boolean isMe(String dataid) {
        Iterator iter;
        boolean rt = false;
        if (this.items != null) {
            for (iter = this.items.iterator(); iter.hasNext();) {
                String dk = (String) iter.next();
                if (dk.equalsIgnoreCase(dataid)) {
                    rt = true;
                    break;
                }
            }
        }
        return rt;
    }

    public List getItems() {
        return this.items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public String getKeychar() {
        return this.keychar;
    }

    public void setKeychar(String keychar) {
        this.keychar = keychar;
    }

    public int getDkey() {
        return this.dkey;
    }

    public void setDkey(int dkey) {
        this.dkey = dkey;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}