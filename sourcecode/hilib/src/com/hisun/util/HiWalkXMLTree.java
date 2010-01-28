package com.hisun.util;


import org.apache.commons.beanutils.MethodUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Iterator;

public class HiWalkXMLTree {
    Object o;

    public void setVisitor(Object o) {

        this.o = o;
    }

    public void process(Document doc) throws Exception {

        walkTree(doc.getRootElement());
    }

    private void walkTree(Element node) throws Exception {

        Iterator iter = node.attributeIterator();

        MethodUtils.invokeMethod(this.o, "handlerElement", node);

        while (iter.hasNext()) {

            MethodUtils.invokeMethod(this.o, "handlerAttribute", (Attribute) iter.next());
        }

        for (int i = 0; i < node.elements().size(); ++i)

            walkTree((Element) node.elements().get(i));
    }
}