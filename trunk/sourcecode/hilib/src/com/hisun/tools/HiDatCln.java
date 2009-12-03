/*     */ package com.hisun.tools;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.parse.HiPretreatment;
/*     */ import com.hisun.tools.parser.HiClnTran;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.hisun.util.HiSysParamParser;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.digester.Digester;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiDatCln
/*     */ {
/*     */   private String _file;
/*     */   private String _id;
/*     */ 
/*     */   public String getFile()
/*     */   {
/*  32 */     return this._file;
/*     */   }
/*     */ 
/*     */   public void setFile(String file) {
/*  36 */     this._file = file;
/*     */   }
/*     */ 
/*     */   public String getId() {
/*  40 */     return this._id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  44 */     this._id = id;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  63 */     if (args.length < 3) {
/*  64 */       System.out.println("at least 3 params");
/*  65 */       System.exit(-1);
/*     */     }
/*     */ 
/*  68 */     HiDatCln datClr = new HiDatCln();
/*     */     try {
/*  70 */       datClr.setFile(args[0]);
/*  71 */       datClr.setId(args[1]);
/*  72 */       datClr.process(setParam(args));
/*     */     } catch (Throwable t) {
/*  74 */       t.printStackTrace();
/*  75 */       System.exit(-1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static HiClnParam setParam(String[] args) throws Exception
/*     */   {
/*  81 */     HiSysParamParser.load(HiContext.getRootContext(), "Public");
/*  82 */     HiDataBaseUtil dbUtil = new HiDataBaseUtil();
/*  83 */     Connection conn = null;
/*     */ 
/*  85 */     HiClnParam param = new HiClnParam();
/*  86 */     param._sysDate = args[2];
/*  87 */     param._dbUtil = new HiDBUtil();
/*  88 */     param._log = HiLog.getLogger("datcln.trc");
/*  89 */     param._args = new String[args.length - 2];
/*  90 */     System.arraycopy(args, 2, param._args, 0, args.length - 2);
/*  91 */     return param;
/*     */   }
/*     */ 
/*     */   public void process(HiClnParam param) throws Exception {
/*  95 */     HiClnTran clnTran = parser(this._file);
/*  96 */     if (StringUtils.equalsIgnoreCase("ALL", this._id))
/*  97 */       clnTran.process(param);
/*     */     else
/*  99 */       clnTran.process(this._id, param);
/*     */   }
/*     */ 
/*     */   public HiClnTran parser(String file) throws Exception
/*     */   {
/* 104 */     SAXReader saxReader = new SAXReader();
/* 105 */     InputStream is = HiResource.getResourceAsStream(file);
/* 106 */     if (is == null) {
/* 107 */       throw new IOException("file:[" + file + "] not existed!");
/*     */     }
/* 109 */     Document document = saxReader.read(is);
/* 110 */     Element rootNode = document.getRootElement();
/* 111 */     HashMap allElements = HiPretreatment.getAllElements(rootNode, null);
/* 112 */     HiPretreatment.parseInclude(allElements, document);
/* 113 */     String strXML = document.asXML();
/* 114 */     ByteArrayInputStream inFile = new ByteArrayInputStream(strXML.getBytes());
/*     */ 
/* 116 */     InputStreamReader in = new InputStreamReader(inFile);
/*     */ 
/* 118 */     Digester digester = new Digester();
/* 119 */     digester.setValidating(false);
/* 120 */     digester.addObjectCreate("ClnTran", "com.hisun.tools.parser.HiClnTran");
/*     */ 
/* 123 */     digester.addObjectCreate("ClnTran/ClnGroup", "com.hisun.tools.parser.HiClnGroup");
/*     */ 
/* 125 */     digester.addSetProperties("ClnTran/ClnGroup");
/* 126 */     digester.addSetNext("ClnTran/ClnGroup", "addClnGroup", "com.hisun.tools.parser.HiClnGroup");
/*     */ 
/* 129 */     digester.addObjectCreate("ClnTran/ClnGroup/ClnRec", "com.hisun.tools.parser.HiClnRec");
/*     */ 
/* 131 */     digester.addSetProperties("ClnTran/ClnGroup/ClnRec");
/* 132 */     digester.addSetNext("ClnTran/ClnGroup/ClnRec", "addClnRec", "com.hisun.tools.parser.HiClnRec");
/*     */ 
/* 135 */     return ((HiClnTran)digester.parse(in));
/*     */   }
/*     */ }