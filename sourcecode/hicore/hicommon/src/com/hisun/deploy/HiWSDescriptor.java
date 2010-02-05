package com.hisun.deploy;

import com.hisun.exception.HiException;
import com.hisun.hilog4j.Logger;
import com.hisun.util.HiStringManager;
import com.hisun.util.HiXmlHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class HiWSDescriptor implements HiDeploymentDescriptor {
    private Element ejb_jar_root = null;

    private Element ibm_bnd_root = null;

    private Element ibm_ext_root = null;
    private Logger log;
    final HiStringManager sm = HiStringManager.getManager();

    public HiWSDescriptor() throws HiException {
        init();
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    private void init() throws HiException {
        String ejb_jar_template = "template/websphere/template_ejb_jar.xml";
        String bnd_template = "template/websphere/template_bnd.xmi";
        String ext_template = "template/websphere/template_ext.xmi";

        SAXReader saxReader = new SAXReader();

        this.ejb_jar_root = HiDeploymentHelper.loadTemplate(saxReader, ejb_jar_template);
        this.ibm_bnd_root = HiDeploymentHelper.loadTemplate(saxReader, bnd_template);
        this.ibm_ext_root = HiDeploymentHelper.loadTemplate(saxReader, ext_template);
    }

    public void createDescriptor(String serviceName, String destPath) throws HiException {
        if ((this.ejb_jar_root == null) || (this.ibm_bnd_root == null) || (this.ibm_ext_root == null)) {
            throw new HiException("", "Template is null");
        }
        Element descriptorRoot = null;

        descriptorRoot = this.ejb_jar_root.createCopy();

        HiXmlHelper.updateChildNode(descriptorRoot, "display-name", serviceName + "_EJB");

        Element updateNode = HiXmlHelper.getChildNode(descriptorRoot, "enterprise-beans");
        updateNode = HiXmlHelper.updateChildAttr(updateNode, "session", "id", "Session_" + serviceName);

        if (updateNode == null) {
            throw new HiException("", "ejb-jar.xml");
        }
        HiXmlHelper.updateChildNode(updateNode, "ejb-name", serviceName);

        updateNode = HiXmlHelper.getChildNode(updateNode, "env-entry");
        HiXmlHelper.updateChildNode(updateNode, "env-entry-value", serviceName);
        try {
            HiXmlHelper.fileWriter(descriptorRoot, destPath + "ejb-jar.xml", "UTF-8");
        } catch (Exception e) {
            throw new HiException("", "ejb-jar.xml", e);
        }

        descriptorRoot = this.ibm_bnd_root.createCopy();

        updateNode = HiXmlHelper.updateChildAttr(descriptorRoot, "ejbBindings", "jndiName", "ibs/ejb/" + serviceName);
        if (updateNode == null) {
            throw new HiException("", "ibm-ejb-jar-bnd.xmi");
        }
        HiXmlHelper.updateChildAttr(updateNode, "enterpriseBean", "href", "META-INF/ejb-jar.xml#Session_" + serviceName);
        try {
            HiXmlHelper.fileWriter(descriptorRoot, destPath + "ibm-ejb-jar-bnd.xmi", "UTF-8");
        } catch (Exception e) {
            throw new HiException("", "ibm-ejb-jar-bnd.xmi", e);
        }

        descriptorRoot = this.ibm_ext_root.createCopy();

        updateNode = HiXmlHelper.getChildNode(descriptorRoot, "ejbExtensions");
        updateNode = HiXmlHelper.updateChildAttr(updateNode, "enterpriseBean", "href", "META-INF/ejb-jar.xml#Session_" + serviceName);
        if (updateNode == null) {
            throw new HiException("", "ibm-ejb-jar-ext.xmi");
        }
        try {
            HiXmlHelper.fileWriter(descriptorRoot, destPath + "ibm-ejb-jar-ext.xmi", null);
        } catch (Exception e) {
            throw new HiException("", "ibm-ejb-jar-ext.xmi", e);
        }
    }
}