package com.hisun.util;


import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;

public class HiIndexedDocumentFactory extends DocumentFactory {
    protected static transient HiIndexedDocumentFactory singleton = new HiIndexedDocumentFactory();

    public static DocumentFactory getInstance() {

        return singleton;
    }

    public Element createElement(QName qname) {

        return new HiIndexedElement(qname);
    }

    public Element createElement(QName qname, int attributeCount) {

        return new HiIndexedElement(qname, attributeCount);
    }
}