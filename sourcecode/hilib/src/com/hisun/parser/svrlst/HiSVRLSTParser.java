/*     */ package com.hisun.parser.svrlst;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.digester.Digester;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.rule.Rule;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class HiSVRLSTParser
/*     */ {
/*  28 */   private HiFrontTabNode _frontTab = null;
/*     */ 
/*  30 */   private String file = null;
/*     */ 
/*     */   public HiSVRLSTParser() {
/*     */   }
/*     */ 
/*     */   public HiSVRLSTParser(String file) {
/*  36 */     this.file = file;
/*     */   }
/*     */ 
/*     */   public HiFrontTabNode parser() throws IOException, SAXException, HiException, DocumentException
/*     */   {
/*  41 */     File directory = new File(HiICSProperty.getAppDir());
/*  42 */     String[] filenames = directory.list();
/*  43 */     Arrays.sort(filenames, String.CASE_INSENSITIVE_ORDER);
/*  44 */     HiFrontTabNode frontTabNode1 = null;
/*  45 */     for (int i = 0; i < filenames.length; ++i) {
/*  46 */       SAXReader reader = new SAXReader();
/*  47 */       File f = new File(directory, File.separator + filenames[i] + File.separator + "etc/SVRLST.XML");
/*     */ 
/*  49 */       if (!(f.exists())) {
/*     */         continue;
/*     */       }
/*  52 */       if (frontTabNode1 == null) {
/*  53 */         frontTabNode1 = parser(f);
/*  54 */         for (int j = 0; j < frontTabNode1.size(); ++j) {
/*  55 */           HiGroupNode grpNod = frontTabNode1.getGroup(j);
/*  56 */           for (int k = 0; k < grpNod.size(); ++k)
/*  57 */             grpNod.getServer(k).setAppName(filenames[i]);
/*     */         }
/*     */       }
/*     */       else {
/*  61 */         HiFrontTabNode frontTabNode2 = parser(f);
/*  62 */         for (int j = 0; j < frontTabNode2.size(); ++j) {
/*  63 */           HiGroupNode grpNod = frontTabNode2.getGroup(j);
/*  64 */           for (int k = 0; k < grpNod.size(); ++k) {
/*  65 */             grpNod.getServer(k).setAppName(filenames[i]);
/*     */           }
/*  67 */           frontTabNode1.addGroup(grpNod);
/*     */         }
/*     */       }
/*     */     }
/*  71 */     return frontTabNode1;
/*     */   }
/*     */ 
/*     */   private HiFrontTabNode parser(String file) throws IOException, SAXException, HiException, DocumentException
/*     */   {
/*  76 */     return parser(new File(file));
/*     */   }
/*     */ 
/*     */   private HiFrontTabNode parser(File f) throws IOException, SAXException, HiException, DocumentException
/*     */   {
/*  81 */     if (!(f.exists())) {
/*  82 */       throw new IOException("文件:[" + this.file + "]不存在!");
/*     */     }
/*     */ 
/*  99 */     Digester digester = new Digester();
/* 100 */     digester.setValidating(false);
/* 101 */     digester.addObjectCreate("FrontTab", "com.hisun.parser.svrlst.HiFrontTabNode");
/*     */ 
/* 104 */     digester.addCallMethod("*/Param", "setParam");
/*     */ 
/* 106 */     digester.addSetProperties("FrontTab");
/* 107 */     digester.addObjectCreate("FrontTab/Include", "com.hisun.parser.svrlst.HiIncludeNode");
/*     */ 
/* 109 */     digester.addSetProperties("FrontTab/Include");
/* 110 */     digester.addObjectCreate("FrontTab/Group", "com.hisun.parser.svrlst.HiGroupNode");
/*     */ 
/* 112 */     digester.addSetProperties("FrontTab/Group");
/* 113 */     digester.addSetNext("FrontTab/Group", "addGroup", "com.hisun.parser.svrlst.HiServerNode");
/*     */ 
/* 115 */     digester.addObjectCreate("FrontTab/Group/Server", "com.hisun.parser.svrlst.HiServerNode");
/*     */ 
/* 117 */     digester.addSetProperties("FrontTab/Group/Server");
/* 118 */     digester.addSetNext("FrontTab/Group/Server", "addServer", "com.hisun.parser.svrlst.HiServerNode");
/*     */ 
/* 123 */     return ((HiFrontTabNode)digester.parse(f));
/*     */   }
/*     */ 
/*     */   public HiFrontTabNode getFrontTabNode() {
/* 127 */     return this._frontTab;
/*     */   }
/*     */ 
/*     */   public void update() throws IOException, SAXException, HiException, DocumentException
/*     */   {
/* 132 */     HiFrontTabNode frontTab1 = parser(this.file);
/* 133 */     for (int i = 0; i < frontTab1.size(); ++i) {
/* 134 */       HiGroupNode group = frontTab1.getGroup(i);
/* 135 */       for (int j = 0; j < group.size(); ++j) {
/* 136 */         HiServerNode server1 = group.getServer(j);
/* 137 */         HiServerNode server2 = null;
/* 138 */         if ((server2 = this._frontTab.getServer(server1.getName())) != null) {
/* 139 */           server1.setStatus(server2.getStatus());
/*     */         }
/*     */       }
/*     */     }
/* 143 */     this._frontTab.clear();
/* 144 */     this._frontTab = null;
/* 145 */     this._frontTab = frontTab1;
/*     */   }
/*     */ 
/*     */   class HiRule extends Rule
/*     */   {
/*     */     public void begin(String namespace, String name, Attributes attributes) throws Exception {
/* 151 */       System.out.println("begin:" + namespace + ":" + name);
/* 152 */       for (int i = 0; i < attributes.getLength(); ++i)
/* 153 */         if (!(StringUtils.equals(attributes.getValue(i), "dd")))
/*     */           continue;
/*     */     }
/*     */ 
/*     */     public void body(String namespace, String name, String text)
/*     */     {
/* 159 */       System.out.println("body:" + namespace + ":" + name + ":" + text);
/*     */     }
/*     */ 
/*     */     public void end(String namespace, String name) {
/* 163 */       System.out.println("end:" + namespace + ":" + name);
/*     */     }
/*     */   }
/*     */ }