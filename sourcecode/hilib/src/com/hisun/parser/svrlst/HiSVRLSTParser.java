package com.hisun.parser.svrlst;


import com.hisun.exception.HiException;
import com.hisun.util.HiICSProperty;
import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.rule.Rule;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class HiSVRLSTParser {
    private HiFrontTabNode _frontTab = null;

    private String file = null;


    public HiSVRLSTParser() {

    }


    public HiSVRLSTParser(String file) {

        this.file = file;

    }


    public HiFrontTabNode parser() throws IOException, SAXException, HiException, DocumentException {

        File directory = new File(HiICSProperty.getAppDir());

        String[] filenames = directory.list();

        Arrays.sort(filenames, String.CASE_INSENSITIVE_ORDER);

        HiFrontTabNode frontTabNode1 = null;

        for (int i = 0; i < filenames.length; ++i) {

            SAXReader reader = new SAXReader();

            File f = new File(directory, File.separator + filenames[i] + File.separator + "etc/SVRLST.XML");


            if (!(f.exists())) {

                continue;

            }

            if (frontTabNode1 == null) {

                frontTabNode1 = parser(f);

                for (int j = 0; j < frontTabNode1.size(); ++j) {

                    HiGroupNode grpNod = frontTabNode1.getGroup(j);

                    for (int k = 0; k < grpNod.size(); ++k)

                        grpNod.getServer(k).setAppName(filenames[i]);

                }

            } else {

                HiFrontTabNode frontTabNode2 = parser(f);

                for (int j = 0; j < frontTabNode2.size(); ++j) {

                    HiGroupNode grpNod = frontTabNode2.getGroup(j);

                    for (int k = 0; k < grpNod.size(); ++k) {

                        grpNod.getServer(k).setAppName(filenames[i]);

                    }

                    frontTabNode1.addGroup(grpNod);

                }

            }

        }

        return frontTabNode1;

    }


    private HiFrontTabNode parser(String file) throws IOException, SAXException, HiException, DocumentException {

        return parser(new File(file));

    }


    private HiFrontTabNode parser(File f) throws IOException, SAXException, HiException, DocumentException {

        if (!(f.exists())) {

            throw new IOException("文件:[" + this.file + "]不存在!");

        }


        Digester digester = new Digester();

        digester.setValidating(false);

        digester.addObjectCreate("FrontTab", "com.hisun.parser.svrlst.HiFrontTabNode");


        digester.addCallMethod("*/Param", "setParam");


        digester.addSetProperties("FrontTab");

        digester.addObjectCreate("FrontTab/Include", "com.hisun.parser.svrlst.HiIncludeNode");


        digester.addSetProperties("FrontTab/Include");

        digester.addObjectCreate("FrontTab/Group", "com.hisun.parser.svrlst.HiGroupNode");


        digester.addSetProperties("FrontTab/Group");

        digester.addSetNext("FrontTab/Group", "addGroup", "com.hisun.parser.svrlst.HiServerNode");


        digester.addObjectCreate("FrontTab/Group/Server", "com.hisun.parser.svrlst.HiServerNode");


        digester.addSetProperties("FrontTab/Group/Server");

        digester.addSetNext("FrontTab/Group/Server", "addServer", "com.hisun.parser.svrlst.HiServerNode");


        return ((HiFrontTabNode) digester.parse(f));

    }


    public HiFrontTabNode getFrontTabNode() {

        return this._frontTab;

    }


    public void update() throws IOException, SAXException, HiException, DocumentException {

        HiFrontTabNode frontTab1 = parser(this.file);

        for (int i = 0; i < frontTab1.size(); ++i) {

            HiGroupNode group = frontTab1.getGroup(i);

            for (int j = 0; j < group.size(); ++j) {

                HiServerNode server1 = group.getServer(j);

                HiServerNode server2 = null;

                if ((server2 = this._frontTab.getServer(server1.getName())) != null) {

                    server1.setStatus(server2.getStatus());

                }

            }

        }

        this._frontTab.clear();

        this._frontTab = null;

        this._frontTab = frontTab1;

    }


    class HiRule extends Rule {

        public void begin(String namespace, String name, Attributes attributes) throws Exception {

            System.out.println("begin:" + namespace + ":" + name);

            for (int i = 0; i < attributes.getLength(); ++i)

                if (!(StringUtils.equals(attributes.getValue(i), "dd"))) continue;

        }


        public void body(String namespace, String name, String text) {

            System.out.println("body:" + namespace + ":" + name + ":" + text);

        }


        public void end(String namespace, String name) {

            System.out.println("end:" + namespace + ":" + name);

        }

    }

}