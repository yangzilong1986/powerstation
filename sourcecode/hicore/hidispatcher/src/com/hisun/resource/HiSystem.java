package com.hisun.resource;

import com.hisun.exception.HiException;
import com.hisun.message.HiContext;
import com.hisun.util.HiICSProperty;
import com.hisun.util.HiResource;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class HiSystem {
    private static HashMap nameMap = new HashMap();
    private static boolean initialized = false;

    public static synchronized void initialize() throws HiException {
        if (initialized) {
            return;
        }

        String value = HiICSProperty.getProperty("config.file");

        if (StringUtils.isEmpty(value)) {
            throw new HiException("212007", "config.file");
        }

        String[] values = StringUtils.split(value, " ,");
        for (int i = 0; i < values.length; ++i) {
            initialize(values[i]);
            nameMap.clear();
        }

        nameMap.clear();
        initialized = true;
    }

    private static void initialize(String file) throws HiException {
        InputStream is = HiResource.getResourceAsStream(file);

        if (is == null) {
            throw new HiException("212004", file);
        }

        Document doc = null;
        SAXReader saxReader = new SAXReader();
        try {
            doc = saxReader.read(is);
        } catch (DocumentException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e1) {
            }
        }
        Element root = doc.getRootElement();
        walkTree(root.getName(), root);
    }

    private static void walkTree(String parentName, Element node) {
        handler(parentName, node);

        Iterator iter = node.elementIterator();

        while (iter.hasNext()) {
            Element child = (Element) iter.next();
            String childName = parentName + "." + child.getName();
            walkTree(childName, child);
        }
    }

    private static void handler(String parentName, Element node) {
        String value = node.getTextTrim();
        if (!(StringUtils.isEmpty(value))) {
            put(parentName, value);
        }

        Iterator iter = node.attributeIterator();
        while (iter.hasNext()) {
            Attribute attr = (Attribute) iter.next();
            String name = parentName + "@" + attr.getName();
            put(name, attr.getValue());
        }
    }

    private static String getName(String name) {
        if (!(nameMap.containsKey(name))) {
            nameMap.put(name, new Integer(1));
            return name;
        }
        int value = ((Integer) nameMap.get(name)).intValue();
        ++value;
        nameMap.put(name, new Integer(value));
        return name + "_" + value;
    }

    private static String getValue(String rootName, String name) {
        name = rootName + "." + name;
        return HiContext.getRootContext().getStrProp(name);
    }

    private static void put(String name, String value) {
        if (value.startsWith("_")) {
            value = getValue("ParaList.Public", value);
        }
        HiContext.getRootContext().setProperty(name, value, true);
    }
}