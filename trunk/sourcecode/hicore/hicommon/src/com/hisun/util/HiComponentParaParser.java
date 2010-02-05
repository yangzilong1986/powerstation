package com.hisun.util;

import com.hisun.message.HiContext;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;

public class HiComponentParaParser {
    public static void setAppParam(HiContext ctx, String appName, Element element) {
        Iterator iter = element.elementIterator("Application");
        do {
            if (!(iter.hasNext())) break label75;
            element1 = (Element) iter.next();
        } while (!(StringUtils.equals(appName, element1.attributeValue("name"))));

        Element element1 = element1.element("Public");
        if (element1 != null) {
            parsePara(ctx, "@CMP", element1);
        }

        label75:
        element1 = element.element("Public");
        if (element1 != null) parsePara(ctx, "@CMP", element1);
    }

    public static void setTrnParam(HiContext ctx, String appName, String txnCode, Element element) {
        Iterator iter = element.elementIterator("Application");
        boolean findFlag = false;
        while (iter.hasNext()) {
            Element element2;
            Element element1 = (Element) iter.next();
            if (!(StringUtils.equals(appName, element1.attributeValue("name")))) {
                continue;
            }
            Iterator iter1 = element1.elementIterator("Transaction");
            do {
                if (!(iter1.hasNext())) break label120;
                element2 = (Element) iter1.next();
            } while (!(StringUtils.equals(txnCode, element2.attributeValue("code"))));

            parsePara(ctx, "@CMP", element2);
            findFlag = true;

            if (findFlag) label120:return;
        }
    }

    private static void parsePara(HiContext ctx, String namespace, Element element) {
        Iterator iter = element.elementIterator("Component");
        while (iter.hasNext()) {
            Element element1 = (Element) iter.next();
            String name = element1.attributeValue("name");
            if (name == null) continue;
            Iterator iter1 = element1.elementIterator("Param");
            HashMap map = new HashMap(2);
            while (iter1.hasNext()) {
                Element element2 = (Element) iter1.next();
                String name1 = element2.attributeValue("name");
                if (name1 == null) continue;
                map.put(name1.toUpperCase(), element2.attributeValue("value"));
            }
            ctx.setProperty(namespace, name, map);
        }
    }
}