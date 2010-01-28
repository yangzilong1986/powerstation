package com.hisun.util;


import com.hisun.message.HiContext;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import java.util.Iterator;

public class HiParaParser {
    public static void setSysParam(HiContext ctx, Element element) {

        Element element1 = element.element("Public");

        if (element1 != null) parsePara(ctx, "@SYS", element1);
    }

    public static void setAppParam(HiContext ctx, String appName, Element element) {

        Iterator iter = element.elementIterator("Application");
        do {
            if (!(iter.hasNext())) break;
            element1 = (Element) iter.next();
        } while (!(StringUtils.equals(appName, element1.attributeValue("name"))));


        Element element1 = element1.element("Public");

        if (element1 != null) {

            parsePara(ctx, "@PARA", element1);
        }


        
        element1 = element.element("Public");

        if (element1 != null) parsePara(ctx, "@PARA", element1);
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
                if (!(iter1.hasNext())) break ;

                element2 = (Element) iter1.next();
            } while (!(StringUtils.equals(txnCode, element2.attributeValue("code"))));


            parsePara(ctx, "@PARA", element2);

            findFlag = true;


            if (findFlag) return;
        }
    }

    public static void setSvrParam(HiContext ctx, String svrName, Element element) {

        Iterator iter = element.elementIterator("Server");

        while (iter.hasNext()) {

            element1 = (Element) iter.next();

            if (!(StringUtils.equals(svrName, element1.attributeValue("name")))) {
                continue;
            }


            parsePara(ctx, "@PARA", element1);
        }

        Element element1 = element.element("Public");

        if (element1 != null) parsePara(ctx, "@PARA", element1);
    }

    private static void parsePara(HiContext ctx, String namespace, Element element) {

        Iterator iter = element.elementIterator("Param");

        while (iter.hasNext()) {

            Element element1 = (Element) iter.next();

            String name = element1.attributeValue("name");

            String value = element1.attributeValue("value");

            if (name == null) continue;

            int i1 = value.indexOf("${");

            int i2 = value.indexOf("}");

            if (i1 + 2 < i2) {

                String pattern1 = value.substring(i1 + 2, i2);

                String pattern2 = value.substring(i1, i2 + 1);

                String value1 = ctx.getStrProp(namespace, pattern1);

                if (value1 != null) {

                    value = StringUtils.replace(value, pattern2, value1);
                }
            }

            ctx.setProperty(namespace, name, value);
        }
    }
}