package com.hisun.common;


import com.hisun.exception.HiException;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

public class HiConvertorUtils {
    private static final String cfgfile = "convert.xml";
    private static final String TOIMAGE = "TOIMAGE";
    private static final String TOSTRING = "TOSTRING";

    public static String getConfigValue(String convertId, String srcValue, ServletContext context) throws HiException {

        if (StringUtils.isEmpty(convertId)) return "";


        if (StringUtils.isEmpty(srcValue)) srcValue = "null";

        List list = getComponentNodes("convert.xml", "/Root/CONVERT", context);


        for (Iterator i = list.iterator(); i.hasNext();) {

            Element el = (Element) i.next();

            if (el.attributeValue("id").equals(convertId)) {

                String type = el.attributeValue("type");

                Iterator iter = el.elementIterator();

                String val = "";

                while (iter.hasNext()) {

                    Element child = (Element) iter.next();


                    if (srcValue.equals(child.attributeValue("name"))) {

                        val = child.attributeValue("value");


                        break;
                    }
                }

                return dispatch(type, val);
            }
        }


        return "";
    }

    private static String dispatch(String type, String value) {

        return value;
    }

    public static List getComponentNodes(String arg, String node, ServletContext context) {

        URI uri = null;


        Document doc = null;

        SAXReader reader = new SAXReader();
        try {

            InputStream in = context.getResourceAsStream("/conf/convert.xml");

            doc = reader.read(in);
        } catch (DocumentException e) {

            e.printStackTrace();
        }


        if (doc == null) {

            return null;
        }

        return doc.selectNodes(node);
    }
}