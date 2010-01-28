package com.hisun.parser.xml;


import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class HiTestXML {
    public static void main(String[] args) {

        if (args.length == 0) {

            System.out.println("USAGE: testxml xmlfiles");

            return;
        }

        for (int i = 0; i < args.length; ++i)
            try {

                SAXReader saxReader = new SAXReader();

                saxReader.read(args[i]);

                System.out.println("valid xml file:[" + args[i] + "]");
            } catch (DocumentException e) {

                System.out.println("invalid xml file:[" + args[i] + "];error:[" + e.getMessage() + "]");
            }
    }
}