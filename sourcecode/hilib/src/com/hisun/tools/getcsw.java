package com.hisun.tools;


import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Iterator;


public class getcsw {
    private static int ifStartSeqNo;


    public static void main(String[] args) {

        try {

            String[] files = new String[args.length - 2];

            for (int i = 0; i < files.length; ++i) {

                files[i] = args[(2 + i)];

            }

            testCreateCsw(args[0], args[1], files);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public static void testCreateCsw(String csw_file, String csw_etc_file, String[] itf_files) throws Exception {

        System.out.println("-----CreateCsw Start");

        Document cswDoc = DocumentHelper.createDocument();

        Document cswEtcDoc = DocumentHelper.createDocument();

        Element cswEtcRoot = cswEtcDoc.addElement("Root");


        Element cswRoot = cswDoc.addElement("Root");

        for (int i = 0; i < itf_files.length; ++i) {

            System.out.println("=======> parser:[" + itf_files[i] + "]");

            Document doc = parser(itf_files[i]);


            process(doc, cswRoot, cswEtcRoot, csw_file);

        }

        saveDoc(cswDoc, csw_file, "ISO-8859-1");

        saveDoc(cswEtcDoc, csw_etc_file, "ISO-8859-1");

        System.out.println("-----CreateCsw End");

    }


    public static void process(Document doc, Element cswRoot, Element cswEtcRoot, String csw_file) {

        Element switchingTab = null;

        Element colNode = null;


        Element cswTab = null;


        Element element = doc.getRootElement();

        Element tranNode = null;

        Element reqNode = null;

        Element defineNode = element.element("Define");


        Iterator it = element.elementIterator("Transaction");

        int seqNo = 1;

        while (it.hasNext()) {

            seqNo = 1;

            tranNode = (Element) it.next();

            cswTab = cswRoot.addElement("Table");

            cswTab.addAttribute("name", tranNode.attributeValue("code"));


            switchingTab = cswEtcRoot.addElement("Table");

            switchingTab.addAttribute("name", tranNode.attributeValue("code"));

            switchingTab.addAttribute("file", "etc/" + csw_file);

            colNode = switchingTab.addElement("Column");

            colNode.addAttribute("name", "FldNam");

            colNode.addAttribute("sort", "yes");

            colNode = switchingTab.addElement("Column");

            colNode.addAttribute("name", "SeqNo");

            colNode.addAttribute("sort", "yes");


            reqNode = tranNode.element("Request");


            Iterator childIt = reqNode.elementIterator();

            while (childIt.hasNext()) seqNo = doProcess((Element) childIt.next(), cswTab, seqNo, defineNode);

        }

    }


    private static int doProcess(Element childNode, Element cswTab, int seqNo, Element defineNode) {

        String nodeName = childNode.getName();

        if (nodeName.equals("Item")) {

            String name = childNode.attributeValue("name");

            if (name.equalsIgnoreCase("CTRL_B")) {

                return seqNo;

            }

            Element item = cswTab.addElement("Item");

            item.addAttribute("FldNam", name);

            item.addAttribute("SeqNo", StringUtils.leftPad(String.valueOf(seqNo), 4, '0'));


            ++seqNo;

            return seqNo;
        }

        if ((nodeName.equals("If")) || (nodeName.equals("ElseIf")) || (nodeName.equals("Else"))) {

            cswTab.addComment(nodeName + " start");

            if (nodeName.equals("If")) ifStartSeqNo = seqNo;

            else {

                seqNo = ifStartSeqNo;

            }


            Iterator iter = childNode.elementIterator();

            while (iter.hasNext()) {

                seqNo = doProcess((Element) iter.next(), cswTab, seqNo, defineNode);

            }


            cswTab.addComment(nodeName + " end");


            return seqNo;
        }

        if ((nodeName.equals("Quote")) && (defineNode != null)) {

            String macro = childNode.attributeValue("name");

            Element macroNode = (Element) defineNode.selectSingleNode("Macro[@name='" + macro + "']");


            if (macroNode != null) {

                cswTab.addComment(nodeName + " " + macro + " start");


                Iterator iter = macroNode.elementIterator();

                while (iter.hasNext()) {

                    seqNo = doProcess((Element) iter.next(), cswTab, seqNo, defineNode);

                }


                cswTab.addComment(nodeName + " " + macro + " end");

                return seqNo;

            }

        }

        return seqNo;

    }


    public static void saveDoc(Document doc, String fileName, String encoding) {

        XMLWriter output = null;


        OutputFormat format = OutputFormat.createPrettyPrint();

        format.setEncoding(encoding);

        try {

            output = new XMLWriter(new FileOutputStream(new File(fileName)), format);


            output.write(doc);


            output.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public static Document parser(String file) throws IOException, DocumentException {

        SAXReader saxReader = new SAXReader();

        InputStream is = new FileInputStream(file);

        if (is == null) {

            throw new IOException("文件:[" + file + "]不存在!");

        }

        return saxReader.read(is);

    }

}