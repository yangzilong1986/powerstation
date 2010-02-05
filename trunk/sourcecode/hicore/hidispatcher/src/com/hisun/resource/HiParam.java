package com.hisun.resource;

import com.hisun.message.HiContext;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Iterator;

public class HiParam {
    public static void process(Document doc) {
        Element element = doc.getRootElement();
        String name = element.attributeValue("name");
        walkTree(name, element);
    }

    private static void walkTree(String name, Element element) {
        replaceParam(name, element);

        Iterator iter = element.elementIterator();
        while (iter.hasNext()) walkTree(name, (Element) iter.next());
    }

    private static void replaceParam(String name, Element element) {
        String value;
        Iterator iter = element.attributeIterator();

        if (!(StringUtils.isEmpty(element.getTextTrim()))) {
            value = element.getTextTrim();
            if (value.startsWith("_")) {
                element.setText(getParamValue(name, value));
            }
        }

        while (iter.hasNext()) {
            Attribute attr = (Attribute) iter.next();
            value = attr.getValue();
            if ((!(StringUtils.isEmpty(value))) && (value.startsWith("_"))) attr.setValue(getParamValue(name, value));
        }
    }

    private static String getParamValue(String name, String param) {
        name = "ParaList." + name + "." + param;

        String value = HiContext.getRootContext().getStrProp(name);
        if (StringUtils.isEmpty(value)) {
            value = param;
        }
        return value;
    }
}