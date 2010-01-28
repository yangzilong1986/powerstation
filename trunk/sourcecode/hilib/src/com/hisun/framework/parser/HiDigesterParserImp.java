package com.hisun.framework.parser;


import com.hisun.exception.HiException;
import com.hisun.framework.HiConfigParser;
import com.hisun.framework.HiDefaultServer;
import com.hisun.framework.HiFrameworkBuilder;
import com.hisun.parse.HiConfigurationRuleParser;
import com.hisun.parse.HiVariableExpander;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.digester.substitution.VariableSubstitutor;
import org.apache.commons.digester.xmlrules.FromXmlRuleSet;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;


public class HiDigesterParserImp implements HiConfigParser, ErrorHandler {
    private static final String DOCTYPE_ID = "-//HISUN//DTD//ATR";
    private static final String ATR_DTD = "ATR.dtd";
    private static final String SERVER_RULE_PATH = "serverRules.xml";
    private Digester serverParser;


    public HiDigesterParserImp() {

        RuleSet ruleSet = new FromXmlRuleSet(HiDigesterParserImp.class.getResource("serverRules.xml"), new HiConfigurationRuleParser());


        this.serverParser = new Digester();

        this.serverParser.addRuleSet(ruleSet);


        this.serverParser.setErrorHandler(this);

        String dtdpath = HiDigesterParserImp.class.getResource("ATR.dtd").toString();


        this.serverParser.register("-//HISUN//DTD//ATR", dtdpath);

        this.serverParser.setValidating(true);


        this.serverParser.setSubstitutor(new VariableSubstitutor(new HiVariableExpander(), null));

    }


    public HiDefaultServer parseServerXML(InputStream serverXml) throws HiException {

        HiDefaultServer server;

        this.serverParser.clear();

        try {

            this.serverParser.setClassLoader(Thread.currentThread().getContextClassLoader());


            HiDefaultServer server = (HiDefaultServer) this.serverParser.parse(serverXml);


            server = (HiDefaultServer) HiFrameworkBuilder.getObjectDecorator().decorate(server, "addFilter");


            server.endBuild();


            return server;

        } catch (SAXException e) {

            if (this.serverParser.getRoot() != null) {

                server = (HiDefaultServer) this.serverParser.getRoot();


                if (e.getException() != null) server.getLog().error(e.getException(), e.getException());

                else {

                    server.getLog().error(e, e);

                }

                server.endBuild();

                server.destroy();

            }


            Exception ec = e.getException();

            if (ec == null) {

                throw new HiException("211001", e.getMessage(), e);

            }


            if (ec instanceof InvocationTargetException) {

                Throwable t = ((InvocationTargetException) ec).getTargetException();


                if (t instanceof Exception) {

                    ec = (Exception) t;

                }

            }


            if (ec instanceof HiException) {

                throw ((HiException) ec);

            }


            throw new HiException("211001", ec.getMessage(), ec);

        } catch (Throwable e) {

            if (this.serverParser.getRoot() != null) {

                server = (HiDefaultServer) this.serverParser.getRoot();


                server.getLog().error(e, e);

                server.endBuild();

                server.destroy();

            }


            throw new HiException("211001", e.getMessage(), e);

        }

    }


    public void warning(SAXParseException arg0) throws SAXException {

        throw arg0;

    }


    public void error(SAXParseException arg0) throws SAXException {

        throw arg0;

    }


    public void fatalError(SAXParseException arg0) throws SAXException {

        throw arg0;

    }

}