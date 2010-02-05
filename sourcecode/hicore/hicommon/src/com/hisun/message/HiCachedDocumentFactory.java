package com.hisun.message;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;

public class HiCachedDocumentFactory extends DocumentFactory {
    private static final long serialVersionUID = 1L;
    protected transient HiElementCache elemcache;
    protected static transient HiCachedDocumentFactory singleton = new HiCachedDocumentFactory();

    public HiCachedDocumentFactory() {
        this.elemcache = new HiElementCache();
    }

    public Element createElement(QName qname) {
        return this.elemcache.get(qname);
    }

    public void recycle(Element e) {
        this.elemcache.recycle(e);
    }

    public static HiCachedDocumentFactory getSingleton() {
        return singleton;
    }
}