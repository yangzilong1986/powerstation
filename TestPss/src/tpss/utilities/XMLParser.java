package tpss.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class XMLParser {

    public static XMLParser xmlParser = null;

    private XMLParser() {
    }

    /**
     * @return
     */
    public static XMLParser getInstance() {
        if (xmlParser == null)
            return new XMLParser();
        return xmlParser;
    }

    /**
     * @param xmlFile
     * @return
     */
    public Map<String, Object> parserXml(String xmlFile) {
        XMLParserHandler xmlPasHandler = null;
        SAXParserFactory saxfac = SAXParserFactory.newInstance();
        try {
            SAXParser saxparser = saxfac.newSAXParser();
            System.out.println("The path of file is " + xmlFile);
            InputStream is = getClass().getResourceAsStream(xmlFile);
            if (is == null) {

                is = new FileInputStream((new File(getClass().getResource("/")
                        .getFile())).getParent()
                        + xmlFile);
            }

            xmlPasHandler = new XMLParserHandler();
            saxparser.parse(is, xmlPasHandler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (xmlPasHandler == null ? new HashMap<String, Object>()
                : xmlPasHandler.getElements());
    }

    /**
     * @param xmlPath
     * @return
     */
    public Map<String, Object> parserXmlByPath(String xmlPath) {
        return parserXml(xmlPath);
    }
}
