package com.hisun.cnaps.messages;

import com.hisun.cnaps.CnapsTag;
import com.hisun.cnaps.common.HiRepeatTagManager;
import com.hisun.cnaps.tags.HiCnapsTag;
import com.hisun.exception.HiException;
import com.hisun.message.HiETF;
import org.apache.commons.lang.StringUtils;

public class HiCnapsTagMessageArea extends HiCnapsMessageArea {
    private static String BODY_TAG_SEP = ":";
    private String buffer;

    public HiCnapsTagMessageArea() {
        this.buffer = "";
    }

    public void unpack(String bodyBuffer) throws HiException {
        int index = 3;
        int endInx = -1;
        int tmpInx = 3;
        HiRepeatTagManager manager = new HiRepeatTagManager();
        while (true) {
            String value;
            index = bodyBuffer.indexOf(BODY_TAG_SEP, tmpInx);
            endInx = bodyBuffer.indexOf(BODY_TAG_SEP, ++index);
            if (endInx == -1) {
                throw new HiException("241098", bodyBuffer);
            }

            String tagName = bodyBuffer.substring(index, endInx++);
            HiCnapsTag tag = getCnapsCodeTable().getTagFromMark(tagName);
            if (tag == null) {
                throw new HiException("241099", tagName);
            }

            tag.setRepeatManager(manager);
            index = tmpInx = endInx;

            endInx = bodyBuffer.indexOf(BODY_TAG_SEP, index);
            if (endInx != -1) {
                if (bodyBuffer.charAt(endInx + 1) == ':') {
                    endInx -= 2;
                    value = bodyBuffer.substring(index, endInx);
                    this.buffer = bodyBuffer.substring(0, endInx);
                    tag.parse(value);
                    addTag(tag);
                    return;
                }
                value = bodyBuffer.substring(index, endInx++);

                if (tag == null) continue;
                tag.parse(value);
                addTag(tag);
            } else {
                value = bodyBuffer.substring(index, bodyBuffer.length() - 1);
                if (tag == null) continue;
                tag.parse(value);
                addTag(tag);
                this.buffer = bodyBuffer.substring(0, bodyBuffer.length());
                return;
            }
        }
    }

    public String getString() {
        if (StringUtils.isBlank(this.buffer)) {
            this.buffer = toString();
        }
        return this.buffer;
    }

    public void packFromETF(String[] fileds, String[] optFileds, HiETF etf) throws HiException {
        HiCnapsTag tag;
        StringBuffer sb = new StringBuffer("{3:");
        HiRepeatTagManager manager = new HiRepeatTagManager();
        for (int i = 0; i < fileds.length; ++i) {
            tag = getCnapsCodeTable().getTagFromMark(fileds[i]);
            if (tag == null) {
                throw new HiException("241104", fileds[i]);
            }
            tag.setRepeatManager(manager);
            tag.parse(etf);
            sb.append(BODY_TAG_SEP).append(tag.getMarkName()).append(BODY_TAG_SEP).append(tag.getValue());
        }

        if (optFileds != null) {
            for (int p = 0; p < optFileds.length; ++p) {
                tag = getCnapsCodeTable().getTagFromMark(optFileds[p]);

                if (tag == null) continue;
                tag.setRepeatManager(manager);
                tag.parse(etf);
                sb.append(BODY_TAG_SEP).append(tag.getMarkName()).append(BODY_TAG_SEP).append(tag.getValue());
            }
        }

        sb.append("}");
        this.buffer = sb.toString();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("{3:");
        int count = getTagCount();
        for (int i = 0; i < count; ++i) {
            CnapsTag tag = getTagByIndex(i);
            sb.append(BODY_TAG_SEP).append(tag.getMarkName()).append(BODY_TAG_SEP).append(tag.getValue());
        }

        sb.append("}");
        return sb.toString();
    }
}