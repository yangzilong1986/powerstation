package com.hisun.message;


import com.hisun.common.HiIgnoreHashMap;
import com.hisun.exception.HiSysException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.io.DOMReader;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class HiETFUtils {
    static final String PRE_ATTR = "ATTR_";


    public static HiETF convertToXmlETF(org.w3c.dom.Document domDoc) {

        DOMReader reader = new DOMReader();

        org.dom4j.Document doc = reader.read(domDoc);


        return convertToXmlETF(doc);

    }


    public static HiETF convertToXmlETF(org.dom4j.Document doc) {

        if (doc != null) {

            org.dom4j.Element root = doc.getRootElement();

            org.dom4j.Element etfRoot = DocumentHelper.createDocument().addElement(root.getName());


            xmlToETF(root, etfRoot);


            return new HiXmlETF(etfRoot);

        }


        return null;

    }


    public static org.w3c.dom.Document convertToDOM(HiETF etf) throws HiSysException {

        HiXmlETF xmlEtf = (HiXmlETF) etf;

        if (xmlEtf == null) {

            return null;

        }

        org.dom4j.Element etfRoot = xmlEtf.getNode();


        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();

        DocumentBuilder build = null;

        try {

            build = fac.newDocumentBuilder();

        } catch (ParserConfigurationException e) {

            throw new HiSysException("215013", "ETF", e);

        }

        org.w3c.dom.Document domDoc = build.newDocument();

        org.w3c.dom.Element root = domDoc.createElement(etfRoot.getName());

        domDoc.appendChild(root);


        etfToXml(etfRoot, root);


        return domDoc;

    }


    public static org.dom4j.Document convertToDocument(HiETF etf) {

        HiXmlETF xmlEtf = (HiXmlETF) etf;

        if (xmlEtf == null) {

            return null;

        }

        org.dom4j.Element etfRoot = xmlEtf.getNode();

        org.dom4j.Element root = DocumentHelper.createDocument().addElement(etfRoot.getName());


        etfToXml(etfRoot, root);


        return root.getDocument();

    }


    private static void etfToXml(org.dom4j.Element etfRoot, org.dom4j.Element root) {

        Iterator it = etfRoot.elementIterator();

        org.dom4j.Element child = null;
        org.dom4j.Element etfChild = null;


        while (it.hasNext()) {

            etfChild = (org.dom4j.Element) it.next();

            String childName = etfChild.getName();

            if (childName.startsWith("ATTR_")) {

                root.addAttribute(StringUtils.substringAfter(childName, "ATTR_"), etfChild.getText());

            }


            child = root.addElement(childName);

            child.setText(etfChild.getText());


            etfToXml(etfChild, child);

        }

    }


    private static void etfToXml(org.dom4j.Element etfRoot, org.w3c.dom.Element root) {

        Iterator it = etfRoot.elementIterator();

        org.dom4j.Element etfChild = null;


        org.w3c.dom.Document domDoc = root.getOwnerDocument();


        while (it.hasNext()) {

            etfChild = (org.dom4j.Element) it.next();

            String childName = etfChild.getName();

            if (childName.startsWith("ATTR_")) {

                root.setAttribute(StringUtils.substringAfter(childName, "ATTR_"), etfChild.getText());

            }


            org.w3c.dom.Element child = domDoc.createElement(childName);

            Text text = domDoc.createTextNode(etfChild.getText());

            child.appendChild(text);

            root.appendChild(child);


            etfToXml(etfChild, child);

        }

    }


    private static void xmlToETF(org.dom4j.Element root, org.dom4j.Element etfRoot) {

        Iterator it = root.elementIterator();

        org.dom4j.Element child = null;
        org.dom4j.Element etfChild = null;


        Iterator attrIt = null;

        Attribute attr = null;

        while (it.hasNext()) {

            child = (org.dom4j.Element) it.next();

            etfChild = etfRoot.addElement(child.getName());


            attrIt = child.attributeIterator();

            while (attrIt.hasNext()) {

                attr = (Attribute) attrIt.next();

                org.dom4j.Element attrChild = etfChild.addElement("ATTR_" + attr.getName());

                attrChild.setText(attr.getValue());

            }


            etfChild.setText(child.getText());


            xmlToETF(child, etfChild);

        }

    }


    public static HiETF form2etf(HashMap map) throws Exception {

        return form2etf(map, false);

    }


    public static HiETF form2etf(HashMap map, boolean ignoreCase) throws Exception {

        Iterator iter = map.keySet().iterator();

        int idx1 = 0;

        HiETF etf = HiETFFactory.createETF();

        while (iter.hasNext()) {

            String name = (String) iter.next();

            String[] values = (String[]) (String[]) map.get(name);


            String groupName = "GROUP";

            String varName = name;

            idx1 = name.indexOf(46);


            if (values.length == 1) {

                if (StringUtils.isBlank(values[0])) continue;

                if (idx1 == -1) {

                    setValue(etf, name, values[0], ignoreCase);

                }

                groupName = name.substring(0, idx1);

                varName = name.substring(idx1 + 1);

                HiETF group = null;

                if (!(ignoreCase)) group = etf.addNodeBase(groupName + "_1", "");

                else {

                    group = etf.addNode(groupName + "_1");

                }


                setValue(group, varName, values[0], ignoreCase);


                if (!(ignoreCase)) etf.setChildValueBase(groupName + "_NUM", "1");

                else {

                    etf.setChildValue(groupName + "_NUM", "1");

                }


            }


            if (idx1 == -1) {

                setValue(etf, name, values[0], ignoreCase);

            }


            if (idx1 != -1) {

                groupName = name.substring(0, idx1);

                varName = name.substring(idx1 + 1);

            }

            int count = 0;

            for (int i = 0; i < values.length; ++i) {

                if (StringUtils.isBlank(values[i])) continue;

                String tmp = groupName + "_" + (count + 1);

                HiETF group = null;

                if (!(ignoreCase)) group = etf.getChildNodeBase(tmp);

                else {

                    group = etf.getChildNode(tmp);

                }

                if (group == null) {

                    if (!(ignoreCase)) group = etf.addNodeBase(tmp, "");

                    else {

                        group = etf.addNode(tmp);

                    }

                }

                setValue(group, varName, values[i], ignoreCase);

                ++count;

            }


            if (count != 0) {

                if (!(ignoreCase)) etf.setChildValueBase(groupName + "_NUM", String.valueOf(count));

                else {

                    etf.setChildValue(groupName + "_NUM", String.valueOf(count));

                }

            }

        }

        return etf;

    }


    private static void setValue(HiETF node, String name, String value, boolean ignoreCase) throws Exception {

        if (StringUtils.isNotBlank(value)) if (!(ignoreCase)) node.setChildValueBase(name, value);

        else node.setChildValue(name, value);

    }


    public static String etf2form(HiETF etf) {

        StringBuffer buf = new StringBuffer();

        etf2form(etf.getChildNodes(), "", buf);

        return buf.toString();

    }


    public static void etf2form(List list, String prefix, StringBuffer buf) {

        for (int i = 0; i < list.size(); ++i) {

            HiETF node = (HiETF) list.get(i);

            List list1 = node.getChildNodes();

            if (list1.size() != 0) {

                etf2form(list1, node.getName(), buf);

            } else {

                String value = node.getValue();

                if (buf.length() > 0) {

                    buf.append("&");

                }

                if ("null".equals(value)) {

                    value = "";

                }

                if (prefix == "") buf.append(node.getName() + "=" + value);

                else buf.append(prefix + "." + node.getName() + "=" + value);

            }

        }

    }


    public static HiIgnoreHashMap etf2HashMapList(HiETF etf) {

        HiIgnoreHashMap map = new HiIgnoreHashMap();

        toHashMapList(etf.getChildNodes(), map, map);

        return map;

    }


    public static void toHashMapList(List list, HiIgnoreHashMap map, HiIgnoreHashMap parentMap) {

        for (int i = 0; i < list.size(); ++i) {

            HiETF node = (HiETF) list.get(i);

            List list1 = node.getChildNodes();

            if (list1.size() != 0) {

                ArrayList tmpList;

                parentMap = map;

                String tmp = node.getName();

                int idx = tmp.lastIndexOf(95);

                if (idx == -1) {

                    idx = tmp.length();

                }

                tmp = tmp.substring(0, idx);


                if ((tmpList = (ArrayList) parentMap.get(tmp)) == null) {

                    tmpList = new ArrayList();

                    parentMap.put(tmp, tmpList);

                }

                HiIgnoreHashMap tmpMap = new HiIgnoreHashMap();

                tmpList.add(tmpMap);

                toHashMapList(list1, tmpMap, parentMap);

            } else {

                String value = node.getValue();

                if ("null".equals(value)) map.put(node.getName(), "");

                else map.put(node.getName(), value);

            }

        }

    }


    public static JSONObject toJSON(HiETF etf) {

        return JSONObject.fromObject(etf2HashMapList(etf));

    }


    public static HiETF fromJSONStr(HiETF root, String JSONStr) {

        return fromJSON(root, JSONObject.fromObject(JSONStr));

    }


    public static HiETF fromJSONStr(String jsonStr) {

        return fromJSON(JSONObject.fromObject(jsonStr));

    }


    public static HiETF fromJSON(JSONObject object) {

        HiETF root = HiETFFactory.createETF();

        Iterator iter = object.keys();

        while (iter.hasNext()) {

            String key = (String) iter.next();

            Object o = object.get(key);

            if (o instanceof JSONObject) {

                fromJSON(root, object.getJSONObject(key));

            } else if (o instanceof JSONArray) {

                JSONArray array = (JSONArray) o;

                for (int i = 0; i < array.size(); ++i) {

                    HiETF grpNod = root.addNode(key + (i + 1));

                    fromJSON(grpNod, array.getJSONObject(i));

                }

            } else {

                root.setChildValue(key, o.toString());

            }

        }

        return root;

    }


    public static HiETF fromJSON(HiETF root, JSONObject object) {

        Iterator iter = object.keys();

        while (iter.hasNext()) {

            String key = (String) iter.next();

            Object o = object.get(key);

            if (o instanceof JSONObject) {

                HiETF grpNod = root.addNode(key);

                fromJSON(root, object);

            } else {

                root.setChildValue(key, o.toString());

            }

        }

        return null;

    }

}