package com.hisun.parse;

import com.hisun.exception.HiException;
import com.hisun.file.HiFileUtil;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiContext;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.NodeCreateRule;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.digester.xmlrules.FromXmlRuleSet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class HiCfgFile {
    private final Logger logger = (Logger) HiContext.getCurrentContext().getProperty("SVR.log");
    private URL urlFilePath;
    private URL urlFileRulePath;
    private String strNodeRule = null;

    private String strLogName = "engine.log";

    private Document document = null;

    private Object rootObj = null;

    private HiCfgFile(URL urlFilePath, URL urlFileRulePath, String strNodeRule) throws HiException {
        this.urlFilePath = urlFilePath;
        this.urlFileRulePath = urlFileRulePath;
        this.strNodeRule = strNodeRule;
        readFile();
        jbInit();
        loadFile();
    }

    private HiCfgFile(URL urlFilePath, URL urlFileRulePath, String strNodeRule, String strLogName) throws HiException {
        this.strLogName = strLogName;
        this.urlFilePath = urlFilePath;
        this.urlFileRulePath = urlFileRulePath;
        this.strNodeRule = strNodeRule;
        readFile();
        jbInit();
        loadFile();
    }

    private HiCfgFile(Document document, URL urlFileRulePath) throws HiException {
        this.document = document;
        this.urlFileRulePath = urlFileRulePath;
        jbInit();
        loadFile();
    }

    private HiCfgFile(File file, URL urlFileRulePath) throws HiException {
        try {
            SAXReader saxReader = new SAXReader();
            this.document = saxReader.read(file);
        } catch (DocumentException e) {
            throw new HiException("213319", new String[]{file.getPath(), e.getMessage()}, e);
        }

        this.urlFileRulePath = urlFileRulePath;
        jbInit();
        loadFile();
    }

    void readFile() throws HiException {
        try {
            SAXReader saxReader = new SAXReader();
            this.document = saxReader.read(this.urlFilePath);
        } catch (DocumentException e) {
            throw new HiException("213319", new String[]{this.urlFilePath.getFile(), e.getMessage()}, e);
        }
    }

    void jbInit() throws HiException {
        int i;
        Element element;
        Element rootNode = this.document.getRootElement();

        HashMap allElements = HiPretreatment.getAllElements(rootNode, null);

        HiPretreatment.parseInclude(allElements, this.document);

        List list = (List) allElements.get("Define");
        if ((list != null) && (list.size() > 0)) {
            for (i = 0; i < list.size(); ++i) {
                element = (Element) list.get(i);
                HiPretreatment.parseMacro(HiPretreatment.getAllElements(element, null));
            }

            allElements = HiPretreatment.getAllElements(rootNode, null);
        }
        HiPretreatment.parseMacro(allElements);

        if ((list != null) && (list.size() > 0)) for (i = 0; i < list.size(); ++i) {
            element = (Element) list.get(i);

            Element parEl = element.getParent();
            if (parEl == null) continue;
            List li = parEl.elements();
            li.remove(element);
        }
    }

    void loadFile() throws HiException {
        ByteArrayInputStream inFile = null;
        InputStreamReader in = null;
        String strXML = null;
        Digester d = null;
        if (this.urlFileRulePath == null) {
            throw new HiException("212004", this.urlFilePath.getFile());
        }
        try {
            RuleSet ruleSet = new FromXmlRuleSet(this.urlFileRulePath, new HiConfigurationRuleParser());

            d = new Digester();
            d.addRuleSet(ruleSet);

            if (this.strNodeRule != null) {
                Rule rule = new NodeCreateRule();
                d.addRule("*/" + this.strNodeRule, rule);
                d.addSetNext("*/" + this.strNodeRule, "setProperty");
            }

            d.setValidating(false);

            strXML = this.document.asXML();

            inFile = new ByteArrayInputStream(strXML.getBytes());

            in = new InputStreamReader(inFile);
            d.setClassLoader(Thread.currentThread().getContextClassLoader());
            this.rootObj = d.parse(in);

            if ((this.urlFilePath != null) && (this.logger.isInfoEnabled())) {
                StringWriter buffer = new StringWriter();
                OutputFormat format = OutputFormat.createPrettyPrint();
                format.setEncoding("ISO-8859-1");
                XMLWriter writer = new XMLWriter(buffer, format);
                writer.write(this.document);

                String xml = buffer.toString();
                HiFileUtil.saveTempFile(this.urlFilePath, xml);
                writer.close();
                buffer.close();
            }
        } catch (SAXException sx) {
            this.logger.error("filename[" + this.urlFilePath + "]");

            HiFileUtil.saveTempFile(this.urlFilePath, strXML);

            Exception ex = sx.getException();

            if (ex == null) ;
            throw HiException.makeException("213319", sx.getMessage(), ex);
        } catch (Exception sx) {
        } finally {
            try {
                if (in != null) in.close();
                if (inFile != null) inFile.close();
                d.clear();
                d = null;
            } catch (Exception e) {
                this.logger.error("loadFile()", e);
            }

        }

        if (this.logger.isDebugEnabled()) this.logger.debug("loadFile() - end");
    }

    void processCommon() throws HiException {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("processCommon() - start");
        }

        if (this.logger.isDebugEnabled()) this.logger.debug("processCommon() - end");
    }

    public Object getRootInstance() {
        return this.rootObj;
    }

    public static HiCfgFile getDefaultCfgFile(URL urlFilePath, URL urlFileRulePath, String strNodeRule) throws HiException {
        HiCfgFile cfgFile = new HiCfgFile(urlFilePath, urlFileRulePath, strNodeRule);

        return cfgFile;
    }

    public static HiCfgFile getDefaultCfgFile(URL urlFilePath, URL urlFileRulePath, String strNodeRule, String strLogName) throws HiException {
        HiCfgFile cfgFile = new HiCfgFile(urlFilePath, urlFileRulePath, strNodeRule, strLogName);

        return cfgFile;
    }

    public static HiCfgFile getCfgFile(Document document, URL urlFileRulePath) throws HiException {
        HiCfgFile cfgFile = new HiCfgFile(document, urlFileRulePath);
        return cfgFile;
    }

    public static HiCfgFile getCfgFile(File file, URL urlFileRulePath) throws HiException {
        HiCfgFile cfgFile = new HiCfgFile(file, urlFileRulePath);
        return cfgFile;
    }
}