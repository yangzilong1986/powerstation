package com.hisun.client;


import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class HiConfigInfo {
    private String msgType;
    private String serverName;
    private String logFile;
    private String mode;
    private ArrayList<IpPortPair> ipPortPairs;
    private int idx;


    public HiConfigInfo() {

        this.msgType = "PLTIN0";

        this.serverName = "CAPPAPI1";


        this.ipPortPairs = new ArrayList();

        this.idx = 0;
    }


    public String getLogFile() {
        return this.logFile;

    }


    public void SetLogFile(String logFile) {

        this.logFile = logFile;

    }


    public void load(String file) throws IllegalAccessException, DocumentException, IOException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        InputStream is = loader.getResourceAsStream(file);

        if (is == null) throw new RuntimeException(file + " not exists");

        try {

            load(is);

        } finally {

            is.close();

        }

    }


    public void load(InputStream is) throws IllegalAccessException, DocumentException {

        SAXReader reader = new SAXReader();

        Document doc = reader.read(is);

        Element root = doc.getRootElement();

        this.msgType = root.attributeValue("msgType");

        if (StringUtils.isBlank(this.msgType)) {

            this.msgType = "PLTIN0";

        }

        this.serverName = root.attributeValue("serverName");

        if (StringUtils.isBlank(this.serverName)) {

            this.serverName = "CAPPAPI1";

        }

        this.logFile = root.attributeValue("logFile");

        if (StringUtils.isBlank(this.logFile)) {

            this.logFile = "invokeservice.trc";

        }


        this.mode = root.attributeValue("mode");

        if (StringUtils.isBlank(this.mode)) {

            this.mode = "POJO";

        }


        Iterator iter = root.elementIterator("Item");

        while (iter.hasNext()) {

            String tmp;

            Element element = (Element) iter.next();

            IpPortPair ipPortPair = new IpPortPair();

            if ((tmp = element.attributeValue("ip")) != null) {

                ipPortPair.setIp(tmp);

            }


            if (StringUtils.isBlank(ipPortPair.getIp())) {

                throw new RuntimeException("ip is empty");

            }


            if ((tmp = element.attributeValue("port")) != null) {

                ipPortPair.setPort(NumberUtils.toInt(tmp));

            }


            if (ipPortPair.getPort() <= 0) {

                throw new IllegalAccessException("port is not number");

            }


            if ((tmp = element.attributeValue("isSRNConn")) != null) {

                ipPortPair.setSRNConn(BooleanUtils.toBoolean(tmp));

            }


            if ((tmp = element.attributeValue("tmOut")) != null) {

                ipPortPair.setTmOut(NumberUtils.toInt(tmp));

            }


            if ((tmp = element.attributeValue("logSwitch")) != null) {

                ipPortPair.setLogSwitch(BooleanUtils.toBoolean(tmp));

            }


            int sslMode = NumberUtils.toInt(element.attributeValue("sslMode"));

            if (sslMode == 1) {

                ipPortPair.setIdentityFile(element.attributeValue("identityFile"));

                ipPortPair.setTrustFile(element.attributeValue("trustFile"));

                ipPortPair.setKeyPsw(element.attributeValue("keyPsw"));

                ipPortPair.setTrustPsw(element.attributeValue("trustPsw"));

            }


            this.ipPortPairs.add(ipPortPair);

        }

    }


    public String getMsgType() {

        return this.msgType;

    }


    public void setMsgType(String msgType) {

        this.msgType = msgType;
    }


    public IpPortPair getIpPortPair() {

        if (this.idx >= this.ipPortPairs.size()) this.idx = 0;

        IpPortPair ipPortPair = (IpPortPair) this.ipPortPairs.get(this.idx);

        this.idx += 1;

        return ipPortPair;

    }


    public String getServerName() {

        return this.serverName;

    }


    public void setServerName(String serverName) {

        this.serverName = serverName;

    }


    public String getMode() {

        return this.mode;

    }


    public void setMode(String mode) {

        this.mode = mode;

    }

}