/*     */ package com.hisun.deploy;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import com.hisun.util.HiXmlHelper;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiWSDescriptor
/*     */   implements HiDeploymentDescriptor
/*     */ {
/*  18 */   private Element ejb_jar_root = null;
/*     */ 
/*  23 */   private Element ibm_bnd_root = null;
/*     */ 
/*  25 */   private Element ibm_ext_root = null;
/*     */   private Logger log;
/*  29 */   final HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public HiWSDescriptor() throws HiException
/*     */   {
/*  33 */     init();
/*     */   }
/*     */ 
/*     */   public void setLog(Logger log)
/*     */   {
/*  38 */     this.log = log;
/*     */   }
/*     */ 
/*     */   private void init()
/*     */     throws HiException
/*     */   {
/*  48 */     String ejb_jar_template = "template/websphere/template_ejb_jar.xml";
/*  49 */     String bnd_template = "template/websphere/template_bnd.xmi";
/*  50 */     String ext_template = "template/websphere/template_ext.xmi";
/*     */ 
/*  52 */     SAXReader saxReader = new SAXReader();
/*     */ 
/*  54 */     this.ejb_jar_root = HiDeploymentHelper.loadTemplate(saxReader, ejb_jar_template);
/*  55 */     this.ibm_bnd_root = HiDeploymentHelper.loadTemplate(saxReader, bnd_template);
/*  56 */     this.ibm_ext_root = HiDeploymentHelper.loadTemplate(saxReader, ext_template);
/*     */   }
/*     */ 
/*     */   public void createDescriptor(String serviceName, String destPath)
/*     */     throws HiException
/*     */   {
/*  67 */     if ((this.ejb_jar_root == null) || (this.ibm_bnd_root == null) || (this.ibm_ext_root == null))
/*     */     {
/*  69 */       throw new HiException("", "Template is null");
/*     */     }
/*  71 */     Element descriptorRoot = null;
/*     */ 
/*  75 */     descriptorRoot = this.ejb_jar_root.createCopy();
/*     */ 
/*  77 */     HiXmlHelper.updateChildNode(descriptorRoot, "display-name", serviceName + "_EJB");
/*     */ 
/*  79 */     Element updateNode = HiXmlHelper.getChildNode(descriptorRoot, "enterprise-beans");
/*  80 */     updateNode = HiXmlHelper.updateChildAttr(updateNode, "session", "id", "Session_" + serviceName);
/*     */ 
/*  82 */     if (updateNode == null)
/*     */     {
/*  84 */       throw new HiException("", "ejb-jar.xml");
/*     */     }
/*  86 */     HiXmlHelper.updateChildNode(updateNode, "ejb-name", serviceName);
/*     */ 
/*  88 */     updateNode = HiXmlHelper.getChildNode(updateNode, "env-entry");
/*  89 */     HiXmlHelper.updateChildNode(updateNode, "env-entry-value", serviceName);
/*     */     try
/*     */     {
/*  93 */       HiXmlHelper.fileWriter(descriptorRoot, destPath + "ejb-jar.xml", "UTF-8");
/*     */     }
/*     */     catch (Exception e) {
/*  96 */       throw new HiException("", "ejb-jar.xml", e);
/*     */     }
/*     */ 
/* 101 */     descriptorRoot = this.ibm_bnd_root.createCopy();
/*     */ 
/* 103 */     updateNode = HiXmlHelper.updateChildAttr(descriptorRoot, "ejbBindings", "jndiName", "ibs/ejb/" + serviceName);
/* 104 */     if (updateNode == null)
/*     */     {
/* 106 */       throw new HiException("", "ibm-ejb-jar-bnd.xmi");
/*     */     }
/* 108 */     HiXmlHelper.updateChildAttr(updateNode, "enterpriseBean", "href", "META-INF/ejb-jar.xml#Session_" + serviceName);
/*     */     try
/*     */     {
/* 112 */       HiXmlHelper.fileWriter(descriptorRoot, destPath + "ibm-ejb-jar-bnd.xmi", "UTF-8");
/*     */     }
/*     */     catch (Exception e) {
/* 115 */       throw new HiException("", "ibm-ejb-jar-bnd.xmi", e);
/*     */     }
/*     */ 
/* 119 */     descriptorRoot = this.ibm_ext_root.createCopy();
/*     */ 
/* 121 */     updateNode = HiXmlHelper.getChildNode(descriptorRoot, "ejbExtensions");
/* 122 */     updateNode = HiXmlHelper.updateChildAttr(updateNode, "enterpriseBean", "href", "META-INF/ejb-jar.xml#Session_" + serviceName);
/* 123 */     if (updateNode == null)
/*     */     {
/* 125 */       throw new HiException("", "ibm-ejb-jar-ext.xmi");
/*     */     }
/*     */     try
/*     */     {
/* 129 */       HiXmlHelper.fileWriter(descriptorRoot, destPath + "ibm-ejb-jar-ext.xmi", null);
/*     */     }
/*     */     catch (Exception e) {
/* 132 */       throw new HiException("", "ibm-ejb-jar-ext.xmi", e);
/*     */     }
/*     */   }
/*     */ }