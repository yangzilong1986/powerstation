package tpss.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLParserHandler extends DefaultHandler {

    private String              tempVal  = null;

    private String              tempKey  = null;

    private List<String>        tList    = null;

    private Set<String>         tSet     = new TreeSet<String>();

    private Map<String, Object> elements = new HashMap<String, Object>();

    public Map<String, Object> getElements() {
        return elements;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startDocument()
     */
    public void startDocument() throws SAXException {
        System.out.println("Document starting");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#endDocument()
     */
    public void endDocument() throws SAXException {
        System.out.println("Document end");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
     * java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if (qName.equals(XMLParserConstants.FVT_ELEMENT)) {
            return;
        } else if (XMLParserConstants.ARG.equalsIgnoreCase(qName)) {
            xmlArg(true, attributes);
        } else if (XMLParserConstants.VALUE.equalsIgnoreCase(qName)) {
            xmlValue(true, attributes);
        } else {
            if (!isContainedTag(qName)) {
                throw new SAXException("This element tag <" + qName
                        + "> is invalid.");
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equals(XMLParserConstants.FVT_ELEMENT)) {
            return;
        } else if (XMLParserConstants.ARG.equalsIgnoreCase(qName)) {
            xmlArg(false, null);
        } else if (XMLParserConstants.VALUE.equalsIgnoreCase(qName)) {
            xmlValue(false, null);
        } else {
            if (!isContainedTag(qName)) {
                throw new SAXException("This element tag <" + qName
                        + "> is invalid");
            }
        }
    }

    /**
     * @param start
     * @param attributes
     * @throws SAXException
     */
    public void xmlArg(boolean start, Attributes attributes)
            throws SAXException {
        if (start) {
            String key = attributes.getQName(0);
            String value = attributes.getValue(0);
            if (isContainsKey(value)) {
                throw new SAXException("There are duplicate ids does not allow");
            }
            if (value == null) {
                throw new SAXException(
                        "The id attribute is required for element <"
                                + XMLParserConstants.ARG + ">");
            }
            if (value.trim().equals("")
                    || !value.trim().equalsIgnoreCase(value)) {
                throw new SAXException(key + " attribute is invalid");
            }
            // elements.put(value, argMap);
            tempKey = value;
        } else {
            if (!elements.containsKey(tempKey)) {
                tSet.add(String.valueOf(tList.size()));
                if (tList != null && tList.size() == 1) {
                    elements.put(tempKey, tList.get(0));
                } else {
                    elements.put(tempKey, tList);
                }
            }
            tempVal = null;
            tempKey = null;
            tList = null;
        }
    }

    /**
     * @param start
     * @param attributes
     * @throws SAXException
     */
    public void xmlValue(boolean start, Attributes attributes)
            throws SAXException {
        if (start) {
            if (attributes != null && attributes.getLength() > 0) {
                throw new SAXException(
                        "The attribute is not required for element "
                                + XMLParserConstants.VALUE);
            }
            if (tList == null) {
                tList = new ArrayList<String>();
            }
        } else {
            tList.add(tempVal);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int) Get
     * the value of this TAG
     */
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        tempVal = new String(ch, start, length);
    }

    /**
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean isContainsKey(String key) {
        return elements.containsKey(key);
    }

    /**
     * @param tagName
     * @return
     */
    private boolean isContainedTag(String tagName) {
        return Arrays.asList(XMLParserConstants.NOATTRTAGS).contains(tagName);
    }

    /**
     * Print the data of elements
     */
    public void printData() {
        System.out.println("No of Elements '" + elements.size() + "'.");
        System.out.println("Elements:" + elements);
    }
}
