package com.hisun.message;


import com.hisun.exception.HiException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.dom4j.*;
import org.dom4j.io.DOMWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


public class HiXmlETF implements HiETF {
    private static final long serialVersionUID = 3975875382709996694L;
    private Element node = null;


    public HiXmlETF() {

        this.node = DocumentHelper.createDocument().addElement("ROOT");

    }


    public HiXmlETF(String name, String value) {

        createNode(name, value);

    }


    public HiXmlETF(String text) throws HiException {

        try {

            this.node = DocumentHelper.parseText(text).getRootElement();

        } catch (DocumentException e) {

            throw new HiException("215012", text, e);

        }

    }


    public HiXmlETF(Element node) {

        this.node = node;

    }


    private Element getNodeCopy() {

        return this.node.createCopy();

    }


    public Element getNode() {

        return this.node;

    }


    private void createNode(String name, String value) {

        this.node = DocumentHelper.createDocument().addElement(name.toUpperCase());


        if (!(StringUtils.isNotEmpty(value))) {

            return;

        }

        setText(this.node, value);

    }


    private List createEtfList(List nodeList) {

        List etfList = new ArrayList();

        for (Iterator iter = nodeList.iterator(); iter.hasNext();) {

            etfList.add(new HiXmlETF((Element) iter.next()));

        }

        return etfList;

    }


    public boolean isNullNode() {

        return (this.node == null);

    }


    public HiETF addNode(String name) {

        return addNodeBase(name.toUpperCase(), "");

    }


    public HiETF addNode(String name, String value) {

        return addNodeBase(name.toUpperCase(), value);

    }


    public HiETF addNodeBase(String name, String value) {

        if ((StringUtils.isEmpty(name)) || (this.node == null)) {

            return null;

        }


        Element curNode = this.node;

        Element posNode = null;


        int pos = StringUtils.indexOf(name, ".");


        while (pos >= 0) {

            String item = StringUtils.substring(name, 0, pos);


            if (item.length() > 0) {

                posNode = curNode.element(item);


                if (posNode == null) {

                    curNode = curNode.addElement(item);

                } else {

                    curNode = posNode;

                }


            }


            name = StringUtils.substring(name, pos + 1);

            pos = StringUtils.indexOf(name, ".");

        }


        if (name.length() > 0) {

            curNode = curNode.addElement(name);

            if (StringUtils.isNotEmpty(value)) {

                setText(curNode, value);

            }

            return new HiXmlETF(curNode);

        }


        return null;

    }


    public HiETF addNodeBase(String name, String value, String prefix, String uri) {

        if (StringUtils.isBlank(prefix)) {

            return addNodeBase(name, value);

        }


        if ((StringUtils.isEmpty(name)) || (this.node == null)) {

            return null;

        }


        if (name.endsWith(".")) {

            return null;

        }


        Element curNode = this.node;

        Element posNode = null;


        int pos = StringUtils.indexOf(name, ".");


        while (pos >= 0) {

            String item = StringUtils.substring(name, 0, pos);


            if (item.length() > 0) {

                posNode = curNode.element(QName.get(item, prefix, uri));


                if (posNode == null) {

                    curNode = curNode.addElement(QName.get(item, prefix, uri));

                } else {

                    curNode = posNode;

                }


            }


            name = StringUtils.substring(name, pos + 1);

            pos = StringUtils.indexOf(name, ".");

        }


        if (name.length() > 0) {

            curNode = curNode.addElement(QName.get(name, prefix, uri));

            if (StringUtils.isNotEmpty(value)) {

                setText(curNode, value);

            }

            return new HiXmlETF(curNode);

        }


        return null;

    }


    public HiETF appendNode(String name) {

        return appendNode(name, "");

    }


    public HiETF appendNode(String name, String value) {

        if (StringUtils.isEmpty(name)) {

            return null;

        }


        if (this.node == null) {

            return null;

        }


        name = name.toUpperCase();


        Element curNode = null;

        curNode = this.node.addElement(name);


        if (StringUtils.isNotEmpty(value)) {

            setText(curNode, value);

        }

        return new HiXmlETF(curNode);

    }


    public HiETF appendNode(HiETF child) {

        if ((isNullNode()) || (child == null) || (child.isNullNode())) {

            return null;

        }


        Element childNode = ((HiXmlETF) child).getNodeCopy();

        this.node.add(childNode);


        return new HiXmlETF(childNode);

    }


    public HiETF getChildNode(HiETF refChild, String child) {

        child = child.toUpperCase();


        if (this.node == null) {

            return null;

        }

        if (refChild == null) {

            return getChildNode(child);

        }


        int pos = this.node.indexOf(((HiXmlETF) refChild).getNode());

        if (pos < 0) {

            return null;

        }


        int nodeCount = this.node.nodeCount();


        for (int i = pos + 1; i < nodeCount; ++i) {

            Node nodeItem = this.node.node(i);

            if (!(nodeItem instanceof Element)) continue;

            Element childNode = (Element) nodeItem;

            if (childNode.getName().equals(child)) {

                return new HiXmlETF(childNode);

            }

        }


        return null;

    }


    public HiETF getChildNode(String child) {

        return getChildNodeBase(child.toUpperCase());

    }


    public HiETF getChildNodeBase(String child) {

        if (this.node == null) {

            return null;

        }


        Element childNode = this.node.element(child);

        if (childNode == null) {

            return null;

        }

        return new HiXmlETF(childNode);

    }


    public HiETF getChildNodeBase(String child, String prefix, String uri) {

        if (this.node == null) {

            return null;

        }


        Element childNode = this.node.element(QName.get(child, prefix, uri));

        if (childNode == null) {

            return null;

        }

        return new HiXmlETF(childNode);

    }


    public List getChildNodes(String child) {

        return getChildNodesBase(child.toUpperCase());

    }


    public List getChildNodesBase(String child) {

        if (this.node == null) {

            return null;

        }


        return createEtfList(this.node.elements(child));

    }


    public List getChildNodesBase(String child, String prefix, String uri) {

        if (StringUtils.isBlank(prefix)) {

            return getChildNodesBase(child);

        }


        if (this.node == null) {

            return null;

        }


        return createEtfList(this.node.elements(QName.get(child, prefix, uri)));

    }


    public List getGrandChildNodesBase(String grandChild) {

        grandChild = StringUtils.removeStart(grandChild, ".");


        if (!(StringUtils.contains(grandChild, '.'))) {

            return getChildNodesBase(grandChild);

        }


        if ((this.node == null) || (StringUtils.isEmpty(grandChild))) {

            return null;

        }


        HiETF curParent = getGrandChildNodeBase(StringUtils.substringBeforeLast(grandChild, "."));


        if (curParent == null) {

            return null;

        }


        String curChild = StringUtils.substringAfterLast(grandChild, ".");


        return curParent.getChildNodesBase(curChild);

    }


    public List getGrandChildNodesBase(String grandChild, String prefix, String uri) {

        grandChild = StringUtils.removeStart(grandChild, ".");


        if (!(StringUtils.contains(grandChild, '.'))) {

            return getChildNodesBase(grandChild, prefix, uri);

        }


        if ((this.node == null) || (StringUtils.isEmpty(grandChild))) {

            return null;

        }


        HiETF curParent = getGrandChildNodeBase(StringUtils.substringBeforeLast(grandChild, "."));


        if (curParent == null) {

            return null;

        }


        String curChild = StringUtils.substringAfterLast(grandChild, ".");


        return curParent.getChildNodesBase(curChild, prefix, uri);

    }


    public List getChildNodes() {

        if (this.node == null) {

            return null;

        }


        return createEtfList(this.node.elements());

    }


    public List getChildFuzzyEnd(String fuzzyChild) {

        if ((this.node == null) || (StringUtils.isEmpty(fuzzyChild))) {

            return null;

        }


        return getChildFuzzyEndBase(fuzzyChild.toUpperCase());

    }


    public List getChildFuzzyEndBase(String fuzzyChild) {

        if ((this.node == null) || (StringUtils.isEmpty(fuzzyChild))) {

            return null;

        }


        List childResult = new ArrayList();


        for (Iterator iter = this.node.elements().iterator(); iter.hasNext();) {

            Element childNode = (Element) iter.next();

            if (!(childNode.getName().startsWith(fuzzyChild))) continue;

            childResult.add(childNode);

        }


        return createEtfList(childResult);

    }


    public List getGrandChildFuzzyEndBase(String fuzzyGrandChild) {

        if ((this.node == null) || (StringUtils.isEmpty(fuzzyGrandChild))) {

            return null;

        }


        fuzzyGrandChild = StringUtils.removeStart(fuzzyGrandChild, ".");


        if (!(StringUtils.contains(fuzzyGrandChild, '.'))) {

            return getChildFuzzyEnd(fuzzyGrandChild);

        }


        HiETF curParent = getGrandChildNode(StringUtils.substringBeforeLast(fuzzyGrandChild, "."));


        if (curParent == null) {

            return null;

        }


        String curChild = StringUtils.substringAfterLast(fuzzyGrandChild, ".");


        return curParent.getChildFuzzyEnd(curChild);

    }


    public List getGrandChildFuzzyEnd(String fuzzyGrandChild) {

        if (StringUtils.isEmpty(fuzzyGrandChild)) {

            return null;

        }


        return getGrandChildFuzzyEndBase(fuzzyGrandChild.toUpperCase());

    }


    public HiETF getGrandChildByCur(String grandChild) {

        if ((this.node == null) || (StringUtils.isEmpty(grandChild))) {

            return null;

        }


        if (!(StringUtils.contains(grandChild, '.'))) {

            return getChildNode(grandChild);

        }


        if (grandChild.endsWith(".")) {

            return null;

        }


        grandChild = grandChild.toUpperCase();


        Element curNode = this.node;

        Element posNode = null;


        int pos = StringUtils.indexOf(grandChild, ".");


        while (pos >= 0) {

            String item = StringUtils.substring(grandChild, 0, pos);


            if (item.length() > 0) {

                posNode = curNode.element(item);


                if (posNode == null) {

                    return null;

                }


                curNode = posNode;

            }


            grandChild = StringUtils.substring(grandChild, pos + 1);

            pos = StringUtils.indexOf(grandChild, ".");

        }


        if (grandChild.length() > 0) {

            curNode = curNode.element(grandChild);


            if (curNode == null) {

                return null;

            }

            return new HiXmlETF(curNode);

        }


        return null;

    }


    public void setGrandChildByCur(String grandChild, String value) {

        if ((this.node == null) || (StringUtils.isEmpty(grandChild))) {

            return;

        }


        if (!(StringUtils.contains(grandChild, '.'))) {

            setChildValue(grandChild, value);

            return;

        }


        if (grandChild.endsWith(".")) {

            return;

        }


        grandChild = grandChild.toUpperCase();


        Element curNode = this.node;

        Element posNode = null;

        String item = "";


        StringTokenizer tokenizer = new StringTokenizer(grandChild, ".");

        while (tokenizer.hasMoreElements()) {

            item = tokenizer.nextToken();


            if (item.length() <= 0) continue;

            posNode = curNode.element(item);


            if (posNode == null) {

                curNode = curNode.addElement(item);

            }


            curNode = posNode;

        }


        if (value == null) {

            return;

        }

        setText(curNode, value);

    }


    public HiETF getGrandChildNode(String grandChild) {

        return getGrandChildNodeBase(grandChild.toUpperCase());

    }


    public void setGrandChildNode(String grandChild, String value) {

        setGrandChildNodeBase(grandChild.toUpperCase(), value);

    }


    public HiETF getGrandChildNodeBase(String grandChild) {

        if ((this.node == null) || (StringUtils.isEmpty(grandChild))) {

            return null;

        }


        if (!(StringUtils.contains(grandChild, '.'))) {

            return getChildNodeBase(grandChild);

        }


        if (grandChild.endsWith(".")) {

            return null;

        }


        Element curNode = this.node;

        Element posNode = null;

        String item = "";


        if (grandChild.startsWith("ROOT.")) {

            curNode = this.node.getDocument().getRootElement();

            grandChild = StringUtils.substringAfter(grandChild, ".");

        }


        StringTokenizer tokenizer = new StringTokenizer(grandChild, ".");

        while (tokenizer.hasMoreElements()) {

            item = tokenizer.nextToken();


            if (item.length() <= 0) continue;

            int idx = NumberUtils.toInt(item);

            if (idx > 0) {

                List list = curNode.elements();

                if (idx > list.size()) posNode = null;

                else posNode = (Element) list.get(idx - 1);

            } else {

                posNode = curNode.element(item);

            }


            if (posNode == null) {

                return null;

            }


            curNode = posNode;

        }


        return new HiXmlETF(curNode);

    }


    public void setGrandChildNodeBase(String grandChild, String value) {

        if ((this.node == null) || (StringUtils.isEmpty(grandChild))) {

            return;

        }


        if (!(StringUtils.contains(grandChild, '.'))) {

            setChildValueBase(grandChild, value);

            return;

        }


        if (grandChild.endsWith(".")) {

            return;

        }


        Element curNode = this.node;

        Element posNode = null;

        String item = "";


        if (grandChild.startsWith("ROOT.")) {

            curNode = this.node.getDocument().getRootElement();

            grandChild = StringUtils.substringAfter(grandChild, ".");

        }


        StringTokenizer tokenizer = new StringTokenizer(grandChild, ".");

        while (tokenizer.hasMoreElements()) {

            item = tokenizer.nextToken();


            if (item.length() <= 0) {

                continue;

            }

            posNode = curNode.element(item);


            if (posNode == null) {

                curNode = curNode.addElement(item);

            }


            curNode = posNode;

        }


        if (value == null) {

            return;

        }

        setText(curNode, value);

    }


    public HiETF getGrandChildNodeBase(String grandChild, String prefix, String uri) {

        if (StringUtils.isBlank(prefix)) {

            return getGrandChildNodeBase(grandChild);

        }


        if ((this.node == null) || (StringUtils.isEmpty(grandChild))) {

            return null;

        }


        if (!(StringUtils.contains(grandChild, '.'))) {

            return getChildNodeBase(grandChild, prefix, uri);

        }


        if (grandChild.endsWith(".")) {

            return null;

        }


        Element curNode = this.node;

        Element posNode = null;

        String item = "";


        if (grandChild.startsWith("ROOT.")) {

            curNode = this.node.getDocument().getRootElement();

            grandChild = StringUtils.substringAfter(grandChild, ".");

        }


        StringTokenizer tokenizer = new StringTokenizer(grandChild, ".");

        while (tokenizer.hasMoreElements()) {

            item = tokenizer.nextToken();


            if (item.length() <= 0) continue;

            posNode = curNode.element(QName.get(item, prefix, uri));


            if (posNode == null) {

                return null;

            }


            curNode = posNode;

        }


        return new HiXmlETF(curNode);

    }


    public HiETF getFirstChild() {

        if (this.node == null) {

            return null;

        }


        List listChild = this.node.elements();

        if (listChild.size() == 0) {

            return null;

        }

        return new HiXmlETF((Element) listChild.get(0));

    }


    public HiETF getNext() {

        if ((this.node == null) || (this.node.isRootElement())) {

            return null;

        }


        Element parent = this.node.getParent();

        int pos = parent.indexOf(this.node);


        if (parent.nodeCount() <= pos + 1) {

            return null;

        }


        return new HiXmlETF((Element) parent.node(pos + 1));

    }


    public String getName() {

        if (this.node == null) {

            return null;

        }

        return this.node.getName();

    }


    public void setName(String name) {

        try {

            this.node.setName(name.toUpperCase());

        } catch (Exception e) {

        }

    }


    public String getValue() {

        if (this.node == null) {

            return null;

        }

        return this.node.getText();

    }


    public void setValue(String value) {

        try {

            if (value != null) {

                setText(this.node, value);

            }

        } catch (Exception e) {

        }

    }


    public String getAttrValue(String attrName) {

        if (this.node == null) {

            return null;

        }

        return this.node.attributeValue(attrName);

    }


    public void setAttrValue(String attrName, String attrVal) {

        if (this.node == null) {

            return;

        }

        this.node.addAttribute(attrName, attrVal);

    }


    public String getAttrValue(String attrName, String prefix, String uri) {

        if (this.node == null) {

            return null;

        }

        if ((StringUtils.isBlank(prefix)) || (StringUtils.isBlank(uri))) {

            return this.node.attributeValue(attrName);

        }

        return this.node.attributeValue(QName.get(attrName, prefix, uri));

    }


    public void setAttrValue(String attrName, String attrVal, String prefix, String uri) {

        if (this.node == null) {

            return;

        }

        if ((StringUtils.isBlank(prefix)) || (StringUtils.isBlank(uri))) {

            this.node.addAttribute(attrName, attrVal);

        } else {

            this.node.addAttribute(QName.get(attrName, prefix, uri), attrVal);

        }

    }


    public void setChildValue(String child, String value) {

        setChildValueBase(child.toUpperCase(), value);

    }


    public void setChildValueBase(String child, String value) {

        Element childNode = this.node.element(child);

        if (childNode == null) {

            childNode = this.node.addElement(child);

            if (value == null) {

                return;

            }

            setText(childNode, value);

        } else {

            if (value == null) {

                return;

            }


            setText(childNode, value);

        }

    }


    public String getChildValue(String child) {

        return getChildValueBase(child.toUpperCase());

    }


    public String getChildValueBase(String child) {

        Element childNode = this.node.element(child);

        if (childNode == null) {

            return null;

        }

        return childNode.getText();

    }


    public String getGrandChildValue(String grandChild) {

        return getGrandChildValueBase(grandChild.toUpperCase());

    }


    public String getGrandChildValueBase(String grandChild) {

        if ((this.node == null) || (StringUtils.isEmpty(grandChild))) {

            return null;

        }


        if (!(StringUtils.contains(grandChild, '.'))) {

            return getChildValueBase(grandChild);

        }


        if (grandChild.endsWith(".")) {

            return null;

        }


        Element curNode = this.node;

        Element posNode = null;

        String item = "";


        if (grandChild.startsWith("ROOT.")) {

            curNode = this.node.getDocument().getRootElement();

            grandChild = StringUtils.substringAfter(grandChild, ".");

        }


        StringTokenizer tokenizer = new StringTokenizer(grandChild, ".");

        while (tokenizer.hasMoreElements()) {

            item = tokenizer.nextToken();


            if (item.length() <= 0) continue;

            int idx = NumberUtils.toInt(item);

            if (idx > 0) {

                List list = curNode.elements();

                if (idx > list.size()) posNode = null;

                else posNode = (Element) list.get(idx - 1);

            } else {

                posNode = curNode.element(item);

            }


            if (posNode == null) {

                return null;

            }


            curNode = posNode;

        }


        return curNode.getText();

    }


    public HiETF getRootNode() {

        if (this.node == null) {

            return null;

        }


        return new HiXmlETF(this.node.getDocument().getRootElement());

    }


    public HiETF getParent() {

        if (this.node == null) {

            return null;

        }

        Element parent = this.node.getParent();

        if (parent == null) {

            return null;

        }


        return new HiXmlETF(parent);

    }


    public void removeNode() {

        if (this.node == null) {

            return;

        }


        if (this.node.isRootElement()) {

            this.node.clearContent();

            this.node.getDocument().remove(this.node);

            this.node = null;

        } else {

            this.node.clearContent();

            this.node.getParent().remove(this.node);

            this.node = null;

        }

    }


    public void removeChildNode(String child) {

        if ((this.node == null) || (StringUtils.isEmpty(child))) {

            return;

        }


        this.node.remove(this.node.element(child.toUpperCase()));

    }


    public void removeChildNode(HiETF child) {

        if ((this.node == null) || (child == null)) {

            return;

        }

        this.node.remove(((HiXmlETF) child).getNode());

    }


    public void removeChildNode(int index) {

        if ((this.node == null) || (index < 0) || (index + 1 > this.node.elements().size())) {

            return;

        }

        this.node.elements().remove(index);

    }


    public void removeChildNodes() {

        if (this.node == null) {

            return;

        }

        this.node.elements().clear();

    }


    public void removeGrandChild(String grandChild) {

        if ((this.node == null) || (StringUtils.isEmpty(grandChild))) {

            return;

        }


        grandChild = grandChild.toUpperCase();


        grandChild = StringUtils.removeStart(grandChild, ".");


        if (!(StringUtils.contains(grandChild, '.'))) {

            this.node.remove(this.node.element(grandChild));

            return;

        }


        HiETF curParent = getGrandChildNode(StringUtils.substringBeforeLast(grandChild, "."));


        if (curParent == null) {

            return;

        }

        curParent.removeChildNode(StringUtils.substringAfterLast(grandChild, "."));

    }


    public HiETF cloneRootNode() {

        if (this.node == null) {

            return null;

        }

        return new HiXmlETF(DocumentHelper.createDocument(getNodeCopy()).getRootElement());

    }


    public HiETF cloneNode() {

        if (this.node == null) {

            return null;

        }


        return new HiXmlETF(getNodeCopy());

    }


    public void copyTo(HiETF destEtf) {

        if (destEtf == null) return;

        ((HiXmlETF) destEtf).node = this.node;

    }


    public boolean combineAll(HiETF etf) {

        if ((etf == null) || (etf.isNullNode())) {

            return false;

        }


        Iterator childIt = ((HiXmlETF) etf).getNode().elementIterator();

        Element childNode = null;


        while (childIt.hasNext()) {

            childNode = (Element) childIt.next();

            this.node.add(childNode.createCopy());

        }

        return true;

    }


    public boolean combine(HiETF etf, boolean isReplace) {

        return combine(etf, isReplace, false);

    }


    public boolean combine(HiETF etf, boolean isReplace, boolean isAppend) {

        if ((etf == null) || (etf.isNullNode())) {

            return false;

        }


        Iterator childIt = ((HiXmlETF) etf).getNode().elementIterator();

        Element childNode = null;
        Element selfChild = null;


        if ((!(isReplace)) && (isAppend)) {

            while (childIt.hasNext()) {

                childNode = (Element) childIt.next();

                this.node.add(childNode.createCopy());

            }

            return true;

        }


        while (childIt.hasNext()) {

            childNode = (Element) childIt.next();


            selfChild = this.node.element(childNode.getName());

            if (selfChild == null) {

                this.node.add(childNode.createCopy());

            }


            if (!(isReplace)) continue;

            this.node.remove(selfChild);

            this.node.add(childNode.createCopy());

        }


        return true;

    }


    public boolean isEndNode() {

        return (this.node.elements().size() > 0);

    }


    public boolean isExist(String childName) {

        if (this.node == null) break label27;

        Element t = (Element) this.node.selectSingleNode(childName);


        label27:
        return (t == null);

    }


    public String getXmlString() {

        return ((this.node == null) ? null : this.node.asXML());

    }


    public String toString() {

        return ((this.node == null) ? null : this.node.asXML());

    }


    public org.w3c.dom.Document toDOMDocument() throws HiException {

        if (this.node != null) {

            DOMWriter domWriter = new DOMWriter();

            try {

                return domWriter.write(this.node.getDocument());

            } catch (DocumentException e) {

                throw new HiException("215011", "ETF", e);

            }

        }


        return null;

    }


    private void setText(Element node, String value) {

        node.setText(value);

    }


    public HiETF addArrayNode(String name, int idx) {

        return addNode(name + "_" + idx);

    }


    public HiETF getArrayChildNode(String child, int idx) {

        return getChildNode(child + "_" + idx);

    }

}