/*     */ package com.hisun.tools;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.OutputFormat;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.io.XMLWriter;
/*     */ 
/*     */ public class getcsw
/*     */ {
/*     */   private static int ifStartSeqNo;
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/*  24 */       String[] files = new String[args.length - 2];
/*  25 */       for (int i = 0; i < files.length; ++i) {
/*  26 */         files[i] = args[(2 + i)];
/*     */       }
/*  28 */       testCreateCsw(args[0], args[1], files);
/*     */     }
/*     */     catch (Exception e) {
/*  31 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void testCreateCsw(String csw_file, String csw_etc_file, String[] itf_files)
/*     */     throws Exception
/*     */   {
/*  39 */     System.out.println("-----CreateCsw Start");
/*  40 */     Document cswDoc = DocumentHelper.createDocument();
/*  41 */     Document cswEtcDoc = DocumentHelper.createDocument();
/*  42 */     Element cswEtcRoot = cswEtcDoc.addElement("Root");
/*     */ 
/*  44 */     Element cswRoot = cswDoc.addElement("Root");
/*  45 */     for (int i = 0; i < itf_files.length; ++i) {
/*  46 */       System.out.println("=======> parser:[" + itf_files[i] + "]");
/*  47 */       Document doc = parser(itf_files[i]);
/*     */ 
/*  50 */       process(doc, cswRoot, cswEtcRoot, csw_file);
/*     */     }
/*  52 */     saveDoc(cswDoc, csw_file, "ISO-8859-1");
/*  53 */     saveDoc(cswEtcDoc, csw_etc_file, "ISO-8859-1");
/*  54 */     System.out.println("-----CreateCsw End");
/*     */   }
/*     */ 
/*     */   public static void process(Document doc, Element cswRoot, Element cswEtcRoot, String csw_file)
/*     */   {
/*  64 */     Element switchingTab = null;
/*  65 */     Element colNode = null;
/*     */ 
/*  68 */     Element cswTab = null;
/*     */ 
/*  70 */     Element element = doc.getRootElement();
/*  71 */     Element tranNode = null;
/*  72 */     Element reqNode = null;
/*  73 */     Element defineNode = element.element("Define");
/*     */ 
/*  75 */     Iterator it = element.elementIterator("Transaction");
/*  76 */     int seqNo = 1;
/*  77 */     while (it.hasNext()) {
/*  78 */       seqNo = 1;
/*  79 */       tranNode = (Element)it.next();
/*  80 */       cswTab = cswRoot.addElement("Table");
/*  81 */       cswTab.addAttribute("name", tranNode.attributeValue("code"));
/*     */ 
/*  84 */       switchingTab = cswEtcRoot.addElement("Table");
/*  85 */       switchingTab.addAttribute("name", tranNode.attributeValue("code"));
/*  86 */       switchingTab.addAttribute("file", "etc/" + csw_file);
/*  87 */       colNode = switchingTab.addElement("Column");
/*  88 */       colNode.addAttribute("name", "FldNam");
/*  89 */       colNode.addAttribute("sort", "yes");
/*  90 */       colNode = switchingTab.addElement("Column");
/*  91 */       colNode.addAttribute("name", "SeqNo");
/*  92 */       colNode.addAttribute("sort", "yes");
/*     */ 
/*  94 */       reqNode = tranNode.element("Request");
/*     */ 
/*  96 */       Iterator childIt = reqNode.elementIterator();
/*  97 */       while (childIt.hasNext())
/*  98 */         seqNo = doProcess((Element)childIt.next(), cswTab, seqNo, defineNode);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static int doProcess(Element childNode, Element cswTab, int seqNo, Element defineNode)
/*     */   {
/* 108 */     String nodeName = childNode.getName();
/* 109 */     if (nodeName.equals("Item")) {
/* 110 */       String name = childNode.attributeValue("name");
/* 111 */       if (name.equalsIgnoreCase("CTRL_B")) {
/* 112 */         return seqNo;
/*     */       }
/* 114 */       Element item = cswTab.addElement("Item");
/* 115 */       item.addAttribute("FldNam", name);
/* 116 */       item.addAttribute("SeqNo", StringUtils.leftPad(String.valueOf(seqNo), 4, '0'));
/*     */ 
/* 118 */       ++seqNo;
/* 119 */       return seqNo; }
/* 120 */     if ((nodeName.equals("If")) || (nodeName.equals("ElseIf")) || (nodeName.equals("Else")))
/*     */     {
/* 122 */       cswTab.addComment(nodeName + " start");
/* 123 */       if (nodeName.equals("If"))
/* 124 */         ifStartSeqNo = seqNo;
/*     */       else {
/* 126 */         seqNo = ifStartSeqNo;
/*     */       }
/*     */ 
/* 129 */       Iterator iter = childNode.elementIterator();
/* 130 */       while (iter.hasNext()) {
/* 131 */         seqNo = doProcess((Element)iter.next(), cswTab, seqNo, defineNode);
/*     */       }
/*     */ 
/* 134 */       cswTab.addComment(nodeName + " end");
/*     */ 
/* 136 */       return seqNo; }
/* 137 */     if ((nodeName.equals("Quote")) && (defineNode != null)) {
/* 138 */       String macro = childNode.attributeValue("name");
/* 139 */       Element macroNode = (Element)defineNode.selectSingleNode("Macro[@name='" + macro + "']");
/*     */ 
/* 141 */       if (macroNode != null) {
/* 142 */         cswTab.addComment(nodeName + " " + macro + " start");
/*     */ 
/* 144 */         Iterator iter = macroNode.elementIterator();
/* 145 */         while (iter.hasNext()) {
/* 146 */           seqNo = doProcess((Element)iter.next(), cswTab, seqNo, defineNode);
/*     */         }
/*     */ 
/* 149 */         cswTab.addComment(nodeName + " " + macro + " end");
/* 150 */         return seqNo;
/*     */       }
/*     */     }
/* 153 */     return seqNo;
/*     */   }
/*     */ 
/*     */   public static void saveDoc(Document doc, String fileName, String encoding) {
/* 157 */     XMLWriter output = null;
/*     */ 
/* 159 */     OutputFormat format = OutputFormat.createPrettyPrint();
/* 160 */     format.setEncoding(encoding);
/*     */     try
/*     */     {
/* 163 */       output = new XMLWriter(new FileOutputStream(new File(fileName)), format);
/*     */ 
/* 166 */       output.write(doc);
/*     */ 
/* 168 */       output.close();
/*     */     } catch (Exception e) {
/* 170 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Document parser(String file) throws IOException, DocumentException
/*     */   {
/* 176 */     SAXReader saxReader = new SAXReader();
/* 177 */     InputStream is = new FileInputStream(file);
/* 178 */     if (is == null) {
/* 179 */       throw new IOException("文件:[" + file + "]不存在!");
/*     */     }
/* 181 */     return saxReader.read(is);
/*     */   }
/*     */ }