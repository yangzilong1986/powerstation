package com.hisun.cnaps;

import com.hisun.cnaps.tags.HiCnapsFixTag;
import com.hisun.cnaps.tags.HiCnapsGroupTag;
import com.hisun.cnaps.tags.HiCnapsTag;
import com.hisun.cnaps.tags.HiCnapsTagImpl;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HiCnapsCodeTable {
    private long tableTime;
    Map tags;

    public static HiCnapsCodeTable load(URL cfgFile) throws DocumentException {
        SAXReader sax = new SAXReader();
        HiCnapsCodeTable currTable = new HiCnapsCodeTable();
        Element rootDoc = sax.read(cfgFile).getRootElement();
        List nodeLs = rootDoc.selectNodes("Tag");
        for (int i = 0; i < nodeLs.size(); ++i) {
            Element tagElement = (Element) nodeLs.get(i);
            currTable.addTagElement(tagElement.attributeValue("mark").toUpperCase(), tagElement);
        }

        return currTable;
    }

    public String toString() {
        Iterator keys = this.tags.keySet().iterator();
        StringBuffer sb = new StringBuffer("{");
        for (; keys.hasNext(); sb.append("}")) {
            Object key = keys.next();
            sb.append(key);
            sb.append(":");
            sb.append(this.tags.get(key));
        }

        return sb.toString();
    }

    private void addTagElement(String mark, Element element) {
        this.tags.put(mark, element);
    }

    private HiCnapsCodeTable() {
        this.tableTime = 0L;
        this.tags = null;
        this.tableTime = -1L;
        this.tags = new HashMap();
    }

    public HiCnapsTag getTagFormElement(Element element) {
        String split = element.attributeValue("split");
        if (StringUtils.isNotEmpty(split)) {
            split = split.toUpperCase();
            HiCnapsTag tag = null;
            if (split.equals("FIXED")) {
                tag = new HiCnapsFixTag(element);
            } else if (split.equals("GROUP")) tag = new HiCnapsGroupTag(element);
            else tag = new HiCnapsTagImpl(element);
            return tag;
        }

        return new HiCnapsTagImpl(element);
    }

    public HiCnapsTag getTagFromMark(String tagName) {
        Element element = (Element) this.tags.get(tagName.toUpperCase());
        if (element == null) {
            return null;
        }
        return getTagFormElement(element);
    }
}