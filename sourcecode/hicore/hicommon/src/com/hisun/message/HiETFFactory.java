package com.hisun.message;

import com.hisun.exception.HiException;
import org.dom4j.io.DOMReader;

public class HiETFFactory {
    public static HiETF createETF() {
        HiETF etf = new HiXmlETF();
        return etf;
    }

    public static HiETF createXmlETF() {
        HiETF etf = new HiXmlETF();
        return etf;
    }

    public static HiETF createXmlETF(org.w3c.dom.Document domDoc) {
        DOMReader reader = new DOMReader();
        org.dom4j.Document doc = reader.read(domDoc);
        if (doc != null) {
            HiETF etf = new HiXmlETF(doc.getRootElement());
            return etf;
        }

        return null;
    }

    public static HiETF createETF(String name, String value) {
        HiETF etf = new HiXmlETF(name, value);
        return etf;
    }

    public static HiETF createETF(String text) throws HiException {
        HiETF etf = null;

        etf = new HiXmlETF(text);
        return etf;
    }
}