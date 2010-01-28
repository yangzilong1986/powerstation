package com.hisun.util;


import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.tree.BackedList;
import org.dom4j.tree.DefaultElement;

import java.util.*;


public class HiIndexedElement extends DefaultElement {

    private Map elementIndex;

    private Map attributeIndex;


    public HiIndexedElement(String name) {

        super(name);

    }


    public HiIndexedElement(QName qname) {

        super(qname);

    }


    public HiIndexedElement(QName qname, int attributeCount) {

        super(qname, attributeCount);

    }


    public Attribute attribute(String name) {

        return ((Attribute) attributeIndex().get(name));

    }


    public Attribute attribute(QName qName) {

        return ((Attribute) attributeIndex().get(qName));

    }


    public Element element(String name) {

        return asElement(elementIndex().get(name));

    }


    public Element element(QName qName) {

        return asElement(elementIndex().get(qName));

    }


    public List elements(String name) {

        return asElementList(elementIndex().get(name));

    }


    public List elements(QName qName) {

        return asElementList(elementIndex().get(qName));

    }


    protected Element asElement(Object object) {

        if (object instanceof Element) return ((Element) object);

        if (object != null) {

            List list = (List) object;


            if (list.size() >= 1) {

                return ((Element) list.get(0));

            }

        }


        return null;

    }


    protected List asElementList(Object object) {

        if (object instanceof Element) return createSingleResultList(object);

        if (object != null) {

            List list = (List) object;

            BackedList answer = createResultList();


            int i = 0;
            for (int size = list.size(); i < size; ++i) {

                answer.addLocal(list.get(i));

            }


            return answer;

        }


        return createEmptyList();

    }


    /**
     * @deprecated
     */

    protected Iterator asElementIterator(Object object) {

        return asElementList(object).iterator();

    }


    public void add(Attribute attribute) {

        System.out.println("addAttribute");

        super.add(attribute);

        addToAttributeIndex(attribute);

    }


    public boolean remove(Attribute attribute) {

        if (super.remove(attribute)) {

            removeFromAttributeIndex(attribute);

            return true;

        }

        return false;

    }


    protected void addNewNode(int index, Node node) {

        super.addNewNode(index, node);

        if (node instanceof Element) addToElementIndex((Element) node);

        else if (node instanceof Attribute) addToAttributeIndex((Attribute) node);

    }


    protected void addNewNode(Node node) {

        super.addNewNode(node);

        if (node instanceof Element) addToElementIndex((Element) node);

        else if (node instanceof Attribute) addToAttributeIndex((Attribute) node);

    }


    protected void addNode(Node node) {

        super.addNode(node);

        if ((this.elementIndex != null) && (node instanceof Element)) addToElementIndex((Element) node);

        else if ((this.attributeIndex != null) && (node instanceof Attribute)) addToAttributeIndex((Attribute) node);

    }


    protected boolean removeNode(Node node) {

        if (super.removeNode(node)) {

            if ((this.elementIndex != null) && (node instanceof Element)) removeFromElementIndex((Element) node);

            else if ((this.attributeIndex != null) && (node instanceof Attribute)) {

                removeFromAttributeIndex((Attribute) node);

            }


            return true;

        }


        return false;

    }


    protected Map attributeIndex() {

        Iterator iter;

        if (this.attributeIndex == null) {

            this.attributeIndex = createAttributeIndex();


            for (iter = attributeIterator(); iter.hasNext();) {

                addToAttributeIndex((Attribute) iter.next());

            }

        }


        return this.attributeIndex;

    }


    protected Map elementIndex() {

        Iterator iter;

        if (this.elementIndex == null) {

            this.elementIndex = createElementIndex();


            for (iter = elementIterator(); iter.hasNext();) {

                addToElementIndex((Element) iter.next());

            }

        }


        return this.elementIndex;

    }


    protected Map createAttributeIndex() {

        Map answer = createIndex();


        return answer;

    }


    protected Map createElementIndex() {

        Map answer = createIndex();


        return answer;

    }


    protected void addToElementIndex(Element element) {

        QName qName = element.getQName();

        String name = qName.getName();

        addToElementIndex(qName, element);

        addToElementIndex(name, element);

    }


    protected void addToElementIndex(Object key, Element value) {

        if (this.elementIndex == null) {

            this.elementIndex = createIndex();

        }

        Object oldValue = this.elementIndex.get(key);


        if (oldValue == null) {

            this.elementIndex.put(key, value);

        } else {

            List list;

            if (oldValue instanceof List) {

                list = (List) oldValue;

                list.add(value);

            } else {

                list = createList();

                list.add(oldValue);

                list.add(value);

                this.elementIndex.put(key, list);

            }

        }

    }


    protected void removeFromElementIndex(Element element) {

        QName qName = element.getQName();

        String name = qName.getName();

        removeFromElementIndex(qName, element);

        removeFromElementIndex(name, element);

    }


    protected void removeFromElementIndex(Object key, Element value) {

        Object oldValue = this.elementIndex.get(key);


        if (oldValue instanceof List) {

            List list = (List) oldValue;

            list.remove(value);

        } else {

            this.elementIndex.remove(key);

        }

    }


    protected void addToAttributeIndex(Attribute attribute) {

        QName qName = attribute.getQName();

        String name = qName.getName();

        addToAttributeIndex(qName, attribute);

        addToAttributeIndex(name, attribute);

    }


    protected void addToAttributeIndex(Object key, Attribute value) {

        if (this.attributeIndex == null) {

            this.attributeIndex = createAttributeIndex();

        }


        this.attributeIndex.put(key, value);

    }


    protected void removeFromAttributeIndex(Attribute attribute) {

        QName qName = attribute.getQName();

        String name = qName.getName();

        removeFromAttributeIndex(qName, attribute);

        removeFromAttributeIndex(name, attribute);

    }


    protected void removeFromAttributeIndex(Object key, Attribute value) {

        Object oldValue = this.attributeIndex.get(key);


        if ((oldValue != null) && (oldValue.equals(value))) this.attributeIndex.remove(key);

    }


    protected Map createIndex() {

        return new HashMap(100, 10.0F);

    }


    protected List createList() {

        return new ArrayList();

    }

}