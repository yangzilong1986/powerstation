package com.hisun.tools;


import com.hisun.parse.HiPretreatment;
import com.hisun.tools.parser.HiFileBpRecover;
import com.hisun.util.HiResource;
import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;


public class HiFilBrc {
    private String _file;
    private String _fileType;


    public String get_file() {

        return this._file;

    }


    public void set_file(String _file) {

        this._file = _file;

    }


    public String get_fileType() {

        return this._fileType;

    }


    public void set_fileType(String type) {

        this._fileType = type;

    }


    public static void main(String[] args) throws Exception {

        if (args.length < 2) {

            System.out.println("[1]Usage:filBrc confFilename -action [-s systemdate] [-w workdate] [-t type]");


            System.exit(-1);

        }


        HiFilBrc filBrc = new HiFilBrc();

        HiFilBrcParam param = new HiFilBrcParam();


        filBrc.set_file(args[0]);

        if (StringUtils.equalsIgnoreCase(args[1], "-b")) {

            param._action = 1;

        } else if (StringUtils.equalsIgnoreCase(args[1], "-r")) {

            param._action = 2;

        } else if (StringUtils.equalsIgnoreCase(args[1], "-c")) {

            param._action = 0;

        } else {

            System.out.println("[2]第二个参数非法。应取值-b,-r,-c");

            System.exit(-1);

        }


        for (int i = 2; i < args.length - 1; i += 2) {

            if (StringUtils.equalsIgnoreCase(args[i], "-s")) param._sysdate = args[(i + 1)];

            else if (StringUtils.equalsIgnoreCase(args[i], "-w")) param._workdate = args[(i + 1)];

            else if (StringUtils.equalsIgnoreCase(args[i], "-t")) {

                filBrc.set_fileType(args[(i + 1)]);

            }

        }

        if (StringUtils.isEmpty(param._sysdate)) {

            param._sysdate = DateFormatUtils.format(new Date(), "yyyyMMdd");

        }


        if (StringUtils.isEmpty(param._workdate)) {

            param._workdate = param._sysdate;

        }

        try {

            filBrc.process(param);

        } catch (Exception e) {

            e.printStackTrace();

            System.exit(-1);

        }

    }


    public void process(HiFilBrcParam param) throws Exception {

        HiFileBpRecover fileBpRecover = parser(this._file);

        if (StringUtils.equalsIgnoreCase("ALL", this._fileType)) fileBpRecover.process(param);

        else fileBpRecover.process(this._fileType, param);

    }


    public HiFileBpRecover parser(String file) throws Exception {

        SAXReader saxReader = new SAXReader();

        InputStream is = HiResource.getResourceAsStream(file);

        if (is == null) {

            throw new IOException("file:[" + file + "] not existed!");

        }

        Document document = saxReader.read(is);

        Element rootNode = document.getRootElement();

        HashMap allElements = HiPretreatment.getAllElements(rootNode, null);

        HiPretreatment.parseInclude(allElements, document);

        String strXML = document.asXML();

        ByteArrayInputStream inFile = new ByteArrayInputStream(strXML.getBytes());


        InputStreamReader in = new InputStreamReader(inFile);


        Digester digester = new Digester();

        digester.setValidating(false);

        digester.addObjectCreate("FileBpRecover", "com.hisun.tools.parser.HiFileBpRecover");


        digester.addObjectCreate("FileBpRecover/Item", "com.hisun.tools.parser.HiFileBpRecoverItem");


        digester.addSetProperties("FileBpRecover/Item");

        digester.addSetNext("FileBpRecover/Item", "addItem", "com.hisun.tools.parser.HiFileBpRecoverItem");


        digester.addCallMethod("FileBpRecover/Item", "setRecoverScript", 1);

        digester.addCallParam("FileBpRecover/Item/RecoverScript", 0);

        digester.addCallMethod("FileBpRecover/Item", "setCleanScript", 1);

        digester.addCallParam("FileBpRecover/Item/CleanScript ", 0);

        digester.addCallMethod("FileBpRecover/Item", "setBackupScript", 1);

        digester.addCallParam("FileBpRecover/Item/BackupScript", 0);


        return ((HiFileBpRecover) digester.parse(in));

    }

}