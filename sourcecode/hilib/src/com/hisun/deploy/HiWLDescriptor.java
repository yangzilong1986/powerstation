/*     */ package com.hisun.deploy;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URL;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiWLDescriptor
/*     */   implements HiDeploymentDescriptor
/*     */ {
/*     */   private Element ejb_jar_root;
/*     */   private Element weblogic_ext_root;
/*     */   private Logger log;
/*     */   final HiStringManager sm;
/*     */ 
/*     */   public HiWLDescriptor()
/*     */   {
/*  22 */     this.ejb_jar_root = null;
/*     */ 
/*  27 */     this.weblogic_ext_root = null;
/*     */ 
/*  31 */     this.sm = HiStringManager.getManager();
/*     */   }
/*     */ 
/*     */   public void setLog(Logger log) {
/*  35 */     this.log = log;
/*     */   }
/*     */ 
/*     */   public void createDescriptor(String serviceName, String destPath)
/*     */   {
/*  46 */     createByTemplate(serviceName, destPath);
/*     */   }
/*     */ 
/*     */   private void init() throws HiException
/*     */   {
/*     */   }
/*     */ 
/*     */   private void createByTemplate(String serviceName, String destPath)
/*     */   {
/*  55 */     String ejb_jar_template = "template/weblogic/template-ejb-jar.xml";
/*  56 */     String weblogic_ejb_jar_template = "template/weblogic/template-weblogic-ejb-jar.xml";
/*     */ 
/*  58 */     replFileText(ejb_jar_template, destPath + "ejb-jar.xml", serviceName);
/*  59 */     replFileText(weblogic_ejb_jar_template, destPath + "weblogic-ejb-jar.xml", serviceName);
/*     */   }
/*     */ 
/*     */   private void replFileText(String templateFile, String outFile, String serviceName)
/*     */   {
/*  64 */     BufferedReader in = null;
/*     */ 
/*  66 */     PrintWriter out = null;
/*  67 */     String fromStr = "#var_name#";
/*     */ 
/*  69 */     URL fileUrl = HiResource.getResource(templateFile);
/*     */     try
/*     */     {
/*  72 */       in = new BufferedReader(new FileReader(fileUrl.getFile()));
/*  73 */       out = new PrintWriter(new FileWriter(outFile, false));
/*     */ 
/*  75 */       String line = in.readLine();
/*  76 */       while (line != null)
/*     */       {
/*  78 */         line = line.replaceAll(fromStr, serviceName);
/*  79 */         out.println(line);
/*     */ 
/*  81 */         line = in.readLine();
/*     */       }
/*  83 */       out.close();
/*  84 */       in = null;
/*  85 */       out = null;
/*     */     } catch (FileNotFoundException e) {
/*  87 */       System.out.println("Error: File [" + fileUrl.getFile() + "] not find!");
/*     */ 
/*  89 */       e.printStackTrace();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  93 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void fwDescriptor(String serivceName, String destPath)
/*     */   {
/*     */     try
/*     */     {
/* 101 */       FileWriter fw = new FileWriter(destPath + "ejb-jar.xml");
/* 102 */       fw.write("<?xml version='1.0' encoding='UTF-8'?>\n");
/* 103 */       fw.write("<!DOCTYPE ejb-jar PUBLIC '-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 2.0//EN' 'http://java.sun.com/dtd/ejb-jar_2_0.dtd'>\n");
/* 104 */       fw.write("<ejb-jar>\n");
/* 105 */       fw.write("  <description> <![CDATA[No Description.]]></description>\n");
/* 106 */       fw.write("  <display-name>" + serivceName + "_EJB</display-name>\n");
/* 107 */       fw.write("  <enterprise-beans>\n");
/* 108 */       fw.write("    <session>\n");
/* 109 */       fw.write("      <ejb-name>" + serivceName + "</ejb-name>\n");
/* 110 */       fw.write("      <home>com.hisun.dispatcher.interfaces.HiRouterInHome</home>\n");
/* 111 */       fw.write("      <remote>com.hisun.dispatcher.interfaces.HiRouterIn</remote>\n");
/* 112 */       fw.write("      <ejb-class>com.hisun.dispatcher.ejb.HiRouterInSession</ejb-class>\n");
/* 113 */       fw.write("      <session-type>Stateless</session-type>\n");
/* 114 */       fw.write("      <transaction-type>Bean</transaction-type>\n");
/* 115 */       fw.write("      <env-entry>\n");
/* 116 */       fw.write("        <env-entry-name>ServerName</env-entry-name>\n");
/* 117 */       fw.write("        <env-entry-type>java.lang.String</env-entry-type>\n");
/* 118 */       fw.write("        <env-entry-value>" + serivceName + "</env-entry-value>\n");
/* 119 */       fw.write("      </env-entry>\n");
/* 120 */       fw.write("      <env-entry>\n");
/* 121 */       fw.write("        <env-entry-name>ServerConfig</env-entry-name>\n");
/* 122 */       fw.write("        <env-entry-type>java.lang.String</env-entry-type>\n");
/* 123 */       fw.write("        <env-entry-value>etc/" + serivceName + "_ATR.XML</env-entry-value>\n");
/* 124 */       fw.write("      </env-entry>\n");
/* 125 */       fw.write("    </session>\n");
/* 126 */       fw.write("  </enterprise-beans>\n");
/* 127 */       fw.write("</ejb-jar>\n");
/* 128 */       fw.flush();
/* 129 */       fw.close();
/*     */ 
/* 132 */       fw = null;
/* 133 */       fw = new FileWriter(destPath + "weblogic-ejb-jar.xml");
/* 134 */       fw.write("<?xml version='1.0' encoding='UTF-8'?>\n");
/* 135 */       fw.write("<!DOCTYPE weblogic-ejb-jar PUBLIC '-//BEA Systems, Inc.//DTD WebLogic 8.1.0 EJB//EN' 'http://www.bea.com/servers/wls810/dtd/weblogic-ejb-jar.dtd'>\n");
/* 136 */       fw.write("<weblogic-ejb-jar>\n");
/* 137 */       fw.write("  <description> <![CDATA[Generated by XDoclet]]> </description>\n");
/* 138 */       fw.write("  <weblogic-enterprise-bean>\n");
/* 139 */       fw.write("  <ejb-name>" + serivceName + "</ejb-name>\n");
/* 140 */       fw.write("  <enable-call-by-reference>True</enable-call-by-reference>\n");
/* 141 */       fw.write("  <jndi-name>ibs/ejb/" + serivceName + "</jndi-name>\n");
/* 142 */       fw.write("  </weblogic-enterprise-bean>\n");
/* 143 */       fw.write("</weblogic-ejb-jar>\n");
/* 144 */       fw.flush();
/* 145 */       fw.close();
/* 146 */       fw = null;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */   }
/*     */ }