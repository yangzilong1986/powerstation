package com.hisun.cnaps.messages;

import com.hisun.cnaps.CnapsTag;
import com.hisun.exception.HiException;
import com.hisun.message.HiETF;

public class HiCnapsMessageBusArea extends HiCnapsMessageArea {
    public HiCnapsMessageArea bodyarea;
    private int length;

    public HiCnapsMessageBusArea() {
        this.bodyarea = null;
        this.length = 0;
    }

    public String getString() {
        return this.bodyarea.getString();
    }

    public void packFromETF(String[] as, String[] optFields, HiETF hietf) throws HiException {
        this.bodyarea = HiCnapsMessageAreaFactory.createCnapsMessageArea(3);
        this.bodyarea.setCodeTable(getCnapsCodeTable());
        this.bodyarea.packFromETF(as, optFields, hietf);
    }

    public void unpack(String s) throws HiException {
        int index = s.indexOf("{3:");
        if (index == -1) {
            throw new HiException("241098", s);
        }
        String mark = s.substring(index, index + 3);
        this.length += index;
        this.bodyarea = HiCnapsMessageAreaFactory.createCnapsMessageArea(mark);
        this.bodyarea.setCodeTable(getCnapsCodeTable());
        if (this.bodyarea == null) {
            throw new HiException("241098", s);
        }
        this.bodyarea.unpack(s.substring(index));
        this.length += this.bodyarea.getLength();
    }

    public CnapsTag getTagByIndex(int index) {
        return this.bodyarea.getTagByIndex(index);
    }

    public int getTagCount() {
        return this.bodyarea.getTagCount();
    }

    public int getLength() {
        return this.length;
    }
}