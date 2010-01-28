package com.hisun.server.manage.servlet;


import com.hisun.util.HiICSProperty;
import com.hisun.util.HiResource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

public class HiSYSCFG {
    private Properties property;
    private static HiSYSCFG instance;

    public HiSYSCFG() {

        this.property = new Properties();
    }

    public static synchronized HiSYSCFG getInstance() {

        instance = new HiSYSCFG();

        instance.parse(HiICSProperty.getProperty("sys.config"));

        return instance;
    }

    public String getProperty(String key) {

        return this.property.getProperty("Public." + key);
    }

    public String getProperty(String serverName, String key) {

        if (!(this.property.contains(serverName + "." + key))) {

            return this.property.getProperty("Public." + key);
        }

        return this.property.getProperty(serverName + "." + key);
    }

    public void parse(String file) {

        SAXReader reader = new SAXReader();

        URL url = HiResource.getResource(file);

        if (url == null) ;

        Document doc = null;
        try {

            doc = reader.read(url);
        } catch (DocumentException e) {

            e.printStackTrace();
        }

        Element root = doc.getRootElement();

        Iterator iter = root.elementIterator();

        while (iter.hasNext()) {

            Element element = (Element) iter.next();

            String serverName = element.getName();

            Iterator iter1 = element.elementIterator();

            while (iter1.hasNext()) {

                Element element1 = (Element) iter1.next();

                this.property.setProperty(serverName + "." + element1.getName(), element1.getTextTrim());
            }
        }
    }
}