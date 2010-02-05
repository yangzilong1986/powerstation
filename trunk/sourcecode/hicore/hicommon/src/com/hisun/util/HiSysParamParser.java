package com.hisun.util;

import com.hisun.exception.HiException;
import com.hisun.message.HiContext;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import java.util.Iterator;

public class HiSysParamParser {
    public static void load(HiContext ctx, String serverName) throws HiException {
        String file = HiICSProperty.getProperty("sys.config");
        Element root = HiXmlHelper.getRootElement(file);
        if (root == null) return;
        load(ctx, root, serverName);
    }

    public static void load(HiContext ctx, Element root, String serverName) {
        Element element1 = root.element("Public");
        if (element1 != null) {
            parsePara(ctx, "@PARA", element1);
        }

        Iterator iter = root.elementIterator();
        while (iter.hasNext()) {
            element1 = (Element) iter.next();
            if (!(StringUtils.equals(serverName, element1.getName()))) {
                continue;
            }
            parsePara(ctx, "@PARA", element1);
        }
    }

    private static void parsePara(HiContext ctx, String namespace, Element element) {
        Iterator iter = element.elementIterator();
        HiSymbolExpander symbolExpander = new HiSymbolExpander(ctx, namespace);
        while (iter.hasNext()) {
            Element element1 = (Element) iter.next();
            String name = element1.getName();
            String value = element1.getTextTrim();
            if (name == null) continue;
            ctx.setProperty(namespace, name, symbolExpander.expandSymbols(value));
        }
    }
}