package com.hisun.parse;

import com.hisun.exception.HiException;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiContext;
import com.hisun.util.HiResource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class HiPretreatment {
    private final Logger logger;
    private static final String DEFINE_NODE = "Define";
    private static final String INCLUDE_FILE = "file";
    private static final String INCLUDE_NODE = "Include";
    private static final String MACRO_NODE = "Macro";
    private static final String QUOTE_NODE = "Quote";

    public HiPretreatment() {
        this.logger = ((Logger) HiContext.getCurrentContext().getProperty("SVR.log"));
    }

    public static HashMap getAllElements(Element element, HashMap elements) {
        ArrayList list;
        if ((elements == null) || (elements.isEmpty())) {
            elements = new HashMap();
        }
        String strNodeName = element.getName();

        if (elements.containsKey(strNodeName)) {
            list = (ArrayList) elements.get(strNodeName);
            list.add(element);
        } else {
            list = new ArrayList();
            list.add(element);
            elements.put(strNodeName, list);
        }
        Iterator iter = element.elementIterator();
        while (iter.hasNext()) {
            Element sub = (Element) iter.next();
            getAllElements(sub, elements);
        }

        return elements;
    }

    public static void parseInclude(HashMap allElements, Document document) throws HiException {
        List list;
        int index;
        Iterator iter;
        ArrayList includeNodes = (ArrayList) allElements.get("Include");

        if ((includeNodes == null) || (includeNodes.size() == 0)) {
            return;
        }
        for (int i = 0; i < includeNodes.size(); ++i) {
            Element includeNode = (Element) includeNodes.get(i);
            String strFileName = includeNode.attributeValue("file");

            Element rootInNode = null;

            URL fileURL = HiResource.getResource(strFileName);

            if (fileURL == null) {
                throw new HiException("213321", strFileName);
            }

            try {
                SAXReader saxReader = new SAXReader();
                Document includeDoc = saxReader.read(fileURL);
                rootInNode = includeDoc.getRootElement();

                HashMap allInElements = getAllElements(rootInNode, null);

                parseInclude(allInElements, document);

                parseMacro(allInElements);

                if ((allElements.containsKey("Macro")) && (allInElements.containsKey("Macro"))) {
                    ArrayList macroNodes = (ArrayList) allElements.get("Macro");

                    macroNodes.addAll((ArrayList) allInElements.get("Macro"));
                }

            } catch (DocumentException e) {
                throw new HiException("213320", strFileName, e);
            }

            if (rootInNode == null) {
                return;
            }
            List replaceNode = rootInNode.elements();

            list = includeNode.getParent().elements();

            index = list.indexOf(includeNode);

            if (index < 0) continue;
            Object d = list.remove(index);
            if (index >= list.size()) {
                for (iter = replaceNode.iterator(); iter.hasNext();) {
                    list.add(((DefaultElement) iter.next()).clone());
                }

            } else for (iter = replaceNode.iterator(); iter.hasNext();) {
                list.add(index++, ((DefaultElement) iter.next()).clone());
            }
        }
    }

    public static void parseMacro(HashMap allElements) throws HiException {
        List list;
        int index;
        Iterator iter;
        ArrayList macroNodes = (ArrayList) allElements.get("Macro");

        if ((macroNodes == null) || (macroNodes.size() == 0)) {
            return;
        }
        HashMap macroMap = new HashMap();
        for (int i = 0; i < macroNodes.size(); ++i) {
            Element macroNode = (Element) macroNodes.get(i);
            String strName = macroNode.attributeValue("name");

            List childNodes = macroNode.elements();
            macroMap.put(strName, childNodes);
        }

        ArrayList quoteNodes = (ArrayList) allElements.get("Quote");

        if ((quoteNodes == null) || (quoteNodes.size() == 0)) {
            return;
        }
        for (int i = 0; i < quoteNodes.size(); ++i) {
            Element quoteNode = (Element) quoteNodes.get(i);
            String strName = quoteNode.attributeValue("name");
            if (!(macroMap.containsKey(strName))) {
                throw new HiException("213333", strName);
            }
            List macroChilds = (List) macroMap.get(strName);

            list = quoteNode.getParent().elements();

            index = list.indexOf(quoteNode);

            if (index < 0) continue;
            Object d = list.remove(index);
            if (index >= list.size()) {
                for (iter = macroChilds.iterator(); iter.hasNext();) {
                    list.add(((DefaultElement) iter.next()).clone());
                }

            } else for (iter = macroChilds.iterator(); iter.hasNext();) {
                list.add(index++, ((DefaultElement) iter.next()).clone());
            }
        }
    }
}