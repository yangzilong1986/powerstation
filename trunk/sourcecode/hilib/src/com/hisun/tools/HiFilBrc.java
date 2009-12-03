/*     */ package com.hisun.tools;
/*     */ 
/*     */ import com.hisun.parse.HiPretreatment;
/*     */ import com.hisun.tools.parser.HiFileBpRecover;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.digester.Digester;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.time.DateFormatUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiFilBrc
/*     */ {
/*     */   private String _file;
/*     */   private String _fileType;
/*     */ 
/*     */   public String get_file()
/*     */   {
/*  27 */     return this._file;
/*     */   }
/*     */ 
/*     */   public void set_file(String _file) {
/*  31 */     this._file = _file;
/*     */   }
/*     */ 
/*     */   public String get_fileType() {
/*  35 */     return this._fileType;
/*     */   }
/*     */ 
/*     */   public void set_fileType(String type) {
/*  39 */     this._fileType = type;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/*  43 */     if (args.length < 2) {
/*  44 */       System.out.println("[1]Usage:filBrc confFilename -action [-s systemdate] [-w workdate] [-t type]");
/*     */ 
/*  46 */       System.exit(-1);
/*     */     }
/*     */ 
/*  49 */     HiFilBrc filBrc = new HiFilBrc();
/*  50 */     HiFilBrcParam param = new HiFilBrcParam();
/*     */ 
/*  52 */     filBrc.set_file(args[0]);
/*  53 */     if (StringUtils.equalsIgnoreCase(args[1], "-b")) {
/*  54 */       param._action = 1;
/*  55 */     } else if (StringUtils.equalsIgnoreCase(args[1], "-r")) {
/*  56 */       param._action = 2;
/*  57 */     } else if (StringUtils.equalsIgnoreCase(args[1], "-c")) {
/*  58 */       param._action = 0;
/*     */     } else {
/*  60 */       System.out.println("[2]第二个参数非法。应取值-b,-r,-c");
/*  61 */       System.exit(-1);
/*     */     }
/*     */ 
/*  64 */     for (int i = 2; i < args.length - 1; i += 2) {
/*  65 */       if (StringUtils.equalsIgnoreCase(args[i], "-s"))
/*  66 */         param._sysdate = args[(i + 1)];
/*  67 */       else if (StringUtils.equalsIgnoreCase(args[i], "-w"))
/*  68 */         param._workdate = args[(i + 1)];
/*  69 */       else if (StringUtils.equalsIgnoreCase(args[i], "-t")) {
/*  70 */         filBrc.set_fileType(args[(i + 1)]);
/*     */       }
/*     */     }
/*  73 */     if (StringUtils.isEmpty(param._sysdate)) {
/*  74 */       param._sysdate = DateFormatUtils.format(new Date(), "yyyyMMdd");
/*     */     }
/*     */ 
/*  77 */     if (StringUtils.isEmpty(param._workdate)) {
/*  78 */       param._workdate = param._sysdate;
/*     */     }
/*     */     try
/*     */     {
/*  82 */       filBrc.process(param);
/*     */     } catch (Exception e) {
/*  84 */       e.printStackTrace();
/*  85 */       System.exit(-1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void process(HiFilBrcParam param) throws Exception {
/*  90 */     HiFileBpRecover fileBpRecover = parser(this._file);
/*  91 */     if (StringUtils.equalsIgnoreCase("ALL", this._fileType))
/*  92 */       fileBpRecover.process(param);
/*     */     else
/*  94 */       fileBpRecover.process(this._fileType, param);
/*     */   }
/*     */ 
/*     */   public HiFileBpRecover parser(String file) throws Exception
/*     */   {
/*  99 */     SAXReader saxReader = new SAXReader();
/* 100 */     InputStream is = HiResource.getResourceAsStream(file);
/* 101 */     if (is == null) {
/* 102 */       throw new IOException("file:[" + file + "] not existed!");
/*     */     }
/* 104 */     Document document = saxReader.read(is);
/* 105 */     Element rootNode = document.getRootElement();
/* 106 */     HashMap allElements = HiPretreatment.getAllElements(rootNode, null);
/* 107 */     HiPretreatment.parseInclude(allElements, document);
/* 108 */     String strXML = document.asXML();
/* 109 */     ByteArrayInputStream inFile = new ByteArrayInputStream(strXML.getBytes());
/*     */ 
/* 111 */     InputStreamReader in = new InputStreamReader(inFile);
/*     */ 
/* 113 */     Digester digester = new Digester();
/* 114 */     digester.setValidating(false);
/* 115 */     digester.addObjectCreate("FileBpRecover", "com.hisun.tools.parser.HiFileBpRecover");
/*     */ 
/* 117 */     digester.addObjectCreate("FileBpRecover/Item", "com.hisun.tools.parser.HiFileBpRecoverItem");
/*     */ 
/* 119 */     digester.addSetProperties("FileBpRecover/Item");
/* 120 */     digester.addSetNext("FileBpRecover/Item", "addItem", "com.hisun.tools.parser.HiFileBpRecoverItem");
/*     */ 
/* 123 */     digester.addCallMethod("FileBpRecover/Item", "setRecoverScript", 1);
/* 124 */     digester.addCallParam("FileBpRecover/Item/RecoverScript", 0);
/* 125 */     digester.addCallMethod("FileBpRecover/Item", "setCleanScript", 1);
/* 126 */     digester.addCallParam("FileBpRecover/Item/CleanScript ", 0);
/* 127 */     digester.addCallMethod("FileBpRecover/Item", "setBackupScript", 1);
/* 128 */     digester.addCallParam("FileBpRecover/Item/BackupScript", 0);
/*     */ 
/* 130 */     return ((HiFileBpRecover)digester.parse(in));
/*     */   }
/*     */ }