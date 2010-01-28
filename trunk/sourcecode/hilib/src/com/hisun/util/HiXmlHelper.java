package com.hisun.util;


import com.hisun.exception.HiException;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.*;

import java.io.*;
import java.net.URL;
import java.util.Iterator;


public class HiXmlHelper {

    public static Element parseText(String text) throws HiException {

        try {

            return DocumentHelper.parseText(text).getRootElement();

        } catch (DocumentException e) {

            throw new HiException("CO0010", "Build ETF Tree failure from text.", e);

        }

    }


    public static Element getNodeByAttr(Element root, String nodeNam, String attrNam, String attrVal) {

        Element retNode = null;

        Iterator it = root.elementIterator(nodeNam);

        while (it.hasNext()) {

            retNode = (Element) it.next();

            if (StringUtils.equals(retNode.attributeValue(attrNam), attrVal)) {

                return retNode;

            }

        }

        return null;

    }


    public static org.w3c.dom.Document toW3CDocument(org.dom4j.Document d4doc) {

        DOMWriter d4Writer = new DOMWriter();

        try {

            return d4Writer.write(d4doc);

        } catch (DocumentException e) {

        }

        return null;

    }


    public static org.dom4j.Document buildDocment(org.w3c.dom.Document domDocument) {

        DOMReader xmlReader = new DOMReader();

        return xmlReader.read(domDocument);

    }


    public static Element selectSingleNode(Element parent, String childNam, String attrNam, String attrVal) {

        return ((Element) parent.selectSingleNode(childNam + "[@" + attrNam + "='" + attrVal + "']"));

    }


    public static Element selectSingleNode(Element parent, String childNam) {

        return ((Element) parent.selectSingleNode(childNam));

    }


    public static void saveDoc(org.dom4j.Document doc, String fileName, String encoding) throws HiException {

        XMLWriter output = null;


        OutputFormat format = OutputFormat.createPrettyPrint();

        format.setEncoding(encoding);

        try {

            output = new XMLWriter(new FileOutputStream(new File(fileName)), format);


            output.write(doc);


            output.close();

        } catch (Exception e) {

            throw new HiException(e);

        }

    }


    public static void fileWriter(Element root, String fileName, String encoding) throws HiException, IOException {

        FileWriter fw = new FileWriter(fileName);

        if ((encoding != null) && (encoding != "")) {

            fw.write("<?xml version='1.0' encoding='" + encoding + "'?>\n");

        }

        fw.write(root.asXML());

        fw.flush();

        fw.close();

    }


    public static Element updateChildNodeXPath(Element root, String updateNodeName, String value) {

        Element updateNode = selectSingleNode(root, updateNodeName);

        if (updateNode == null) {

            return null;

        }


        updateNode.setText(value);


        return updateNode;

    }


    public static Element updateChildAttrXPath(Element root, String updateNodeName, String attrName, String value) {

        Element updateNode = selectSingleNode(root, updateNodeName);

        if (updateNode == null) {

            return null;

        }


        updateNode.addAttribute(attrName, value);

        return updateNode;

    }


    public static Element updateChildAttr(Element root, String updateNodeName, String attrName, String value) {

        Element updateNode = getChildNode(root, updateNodeName);

        if (updateNode == null) {

            return null;

        }


        updateNode.addAttribute(attrName, value);

        return updateNode;

    }


    public static Element updateChildNode(Element parent, String childName, String value) {

        if (parent == null) {

            return null;

        }


        Element child = parent.element(childName);

        if (child != null) {

            child.setText(value);

        }

        return child;

    }


    public static Element getChildNode(Element parent, String childName) {

        if (parent == null) {

            return null;

        }

        return parent.element(childName);

    }


    public static org.dom4j.Document parser(String file) throws IOException, DocumentException {

        SAXReader saxReader = new SAXReader();

        InputStream is = HiResource.getResourceAsStream(file);

        if (is == null) {

            throw new IOException("文件:[" + file + "]不存在!");

        }

        return saxReader.read(is);

    }


    public static Element updateOrInsertChildNode(Element parent, String childName, String value) {

        if (parent == null) {

            return null;

        }


        Element child = parent.element(childName);

        if (child == null) {

            child = parent.addElement(childName);

        }

        child.setText(value);

        return child;

    }


    public static Element getRootElement(String file) throws HiException {

        SAXReader reader = new SAXReader();

        org.dom4j.Document doc = null;

        try {

            URL url = HiResource.getResource(file);


            doc = reader.read(url);

        } catch (DocumentException e) {

            throw HiException.makeException("210001", file, e);

        }

        if (doc == null) return null;

        return doc.getRootElement();

    }

}