package com.hisun.message;


import edu.emory.mathcs.backport.java.util.LinkedList;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.tree.DefaultElement;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public class HiElementCache {
    protected Map elementCache;

    public HiElementCache() {

        this.elementCache = new WeakHashMap();
    }

    public Element get(QName qname) {
        Element answer;

        LinkedList l = (LinkedList) this.elementCache.get(qname);


        if ((l == null) || (l.size() == 0)) answer = new DefaultElement(qname);
        else {

            answer = (Element) l.pop();
        }


        return answer;
    }

    public void recycle(Element e) {

        Iterator it = e.elementIterator();

        while (it.hasNext()) {

            recycle((Element) it.next());
        }

        e.clearContent();

        for (int i = 0; i < e.attributeCount(); ++i) {

            e.remove(e.attribute(i));
        }

        add(e.getQName(), e);
    }

    void add(QName qname, Element e) {

        LinkedList l = (LinkedList) this.elementCache.get(qname);

        if (l == null) {

            l = new LinkedList();

            this.elementCache.put(qname, l);
        }

        l.push(e);
    }
}