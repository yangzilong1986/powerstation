/*     */ package com.hisun.atc.rpt.xml;
/*     */ 
/*     */ import com.hisun.xml.SetLocationRule;
/*     */ import java.net.URL;
/*     */ import org.apache.commons.digester.AbstractObjectCreationFactory;
/*     */ import org.apache.commons.digester.BeanPropertySetterRule;
/*     */ import org.apache.commons.digester.Digester;
/*     */ import org.apache.commons.digester.ExtendedBaseRules;
/*     */ import org.apache.commons.digester.ObjectCreationFactory;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ public class HiReportConfigParser
/*     */   implements ErrorHandler
/*     */ {
/*     */   private final Digester parser;
/*     */ 
/*     */   public HiReportConfigParser()
/*     */   {
/*  32 */     this.parser = new Digester();
/*     */ 
/*  34 */     this.parser.setRules(new ExtendedBaseRules());
/*     */ 
/*  36 */     String pattern = "Application";
/*  37 */     this.parser.addObjectCreate(pattern, HiReportConfig.class);
/*  38 */     this.parser.addSetProperties(pattern);
/*     */ 
/*  40 */     pattern = "Application/VarDef";
/*  41 */     this.parser.addObjectCreate(pattern, HiVarDefNode.class);
/*  42 */     this.parser.addSetNext(pattern, "setVarDef");
/*     */ 
/*  44 */     pattern = "Application/VarDef/String";
/*  45 */     this.parser.addObjectCreate(pattern, HiVarDefNode.VarDefNode.class);
/*  46 */     this.parser.addSetProperties(pattern);
/*  47 */     this.parser.addSetNext(pattern, "addVarDef");
/*     */ 
/*  49 */     pattern = "Application/MsgConvert";
/*  50 */     this.parser.addObjectCreate(pattern, HiMsgConvertNode.class);
/*  51 */     this.parser.addSetNext(pattern, "setMsgConvert");
/*     */ 
/*  53 */     pattern = "Application/MsgConvert/Item";
/*  54 */     this.parser.addObjectCreate(pattern, HiMsgConvertNode.HiItemNode.class);
/*  55 */     this.parser.addSetProperties(pattern);
/*  56 */     this.parser.addSetNext(pattern, "addItem");
/*     */ 
/*  59 */     pattern = "Application/MsgConvert/Item/Convert";
/*  60 */     Class[] paramTypes = { String.class, String.class };
/*  61 */     this.parser.addCallMethod(pattern, "addConvert", 2, paramTypes);
/*  62 */     this.parser.addCallParam(pattern, 0, "value");
/*  63 */     this.parser.addCallParam(pattern, 1, "msg");
/*     */ 
/*  67 */     pattern = "Application/MsgConvert/Item/DEFAULT";
/*  68 */     paramTypes = new Class[] { String.class };
/*  69 */     this.parser.addCallMethod(pattern, "addDefault", 1, paramTypes);
/*  70 */     this.parser.addCallParam(pattern, 0, "msg");
/*     */ 
/*  73 */     pattern = "Application/DataSource";
/*     */ 
/*  75 */     this.parser.addFactoryCreate(pattern, new AbstractObjectCreationFactory()
/*     */     {
/*     */       public Object createObject(Attributes attributes) throws Exception {
/*  78 */         HiDataSourceNode ds = null;
/*  79 */         String object = attributes.getValue("object");
/*     */ 
/*  87 */         if (object.equalsIgnoreCase("file")) {
/*  88 */           HiFileDataSourceNode ds1 = new HiFileDataSourceNode();
/*  89 */           ds1.setFile(attributes.getValue("filename"));
/*  90 */           ds = ds1;
/*  91 */         } else if (object.equalsIgnoreCase("sql")) {
/*  92 */           HiSqlDataSourceNode ds1 = new HiSqlDataSourceNode();
/*  93 */           ds = ds1;
/*     */         }
/*  95 */         return ds;
/*     */       }
/*     */     });
/*  99 */     this.parser.addSetNext(pattern, "setDataSource");
/*     */ 
/* 102 */     pattern = "Application/DataSource/PageHeader";
/* 103 */     this.parser.addObjectCreate(pattern, HiPageHeaderNode.class);
/* 104 */     this.parser.addSetNext(pattern, "setPageheader");
/*     */ 
/* 106 */     pattern = "Application/DataSource/Iterative";
/* 107 */     this.parser.addObjectCreate(pattern, HiIterativeNode.class);
/* 108 */     this.parser.addSetNext(pattern, "setIterative");
/*     */ 
/* 110 */     pattern = "Application/DataSource/SubTotal";
/*     */ 
/* 112 */     this.parser.addFactoryCreate(pattern, new AbstractObjectCreationFactory()
/*     */     {
/*     */       public Object createObject(Attributes attributes) throws Exception {
/* 115 */         HiSubTotalNode st = null;
/* 116 */         String object = attributes.getValue("type");
/*     */ 
/* 118 */         if (object.equals("field")) {
/* 119 */           HiSubTotalNode st1 = new HiFieldSubTotalNode();
/* 120 */           st1.setGroupby(attributes.getValue("groupby"));
/* 121 */           st = st1;
/* 122 */         } else if (object.equals("sql")) {
/* 123 */           HiSqlSubTotalNode st1 = new HiSqlSubTotalNode();
/* 124 */           st1.setGroupby(attributes.getValue("groupby"));
/* 125 */           st = st1;
/*     */         }
/* 127 */         return st;
/*     */       }
/*     */     });
/* 131 */     this.parser.addSetNext(pattern, "setSubTotalNode");
/*     */ 
/* 134 */     pattern = "Application/DataSource/SubTotal/Field";
/* 135 */     this.parser.addObjectCreate(pattern, HiFieldNode.class);
/* 136 */     this.parser.addSetProperties(pattern);
/* 137 */     this.parser.addSetNext(pattern, "addField");
/*     */ 
/* 139 */     pattern = "Application/DataSource/SubTotal/Field/Sum";
/* 140 */     this.parser.addObjectCreate(pattern, HiSumNode.class);
/* 141 */     this.parser.addSetProperties(pattern);
/* 142 */     this.parser.addSetNext(pattern, "addSumNode");
/*     */ 
/* 145 */     pattern = "Application/DataSource/SubTotal/Sql";
/* 146 */     this.parser.addObjectCreate(pattern, HiSubTotalSqlNode.class);
/* 147 */     this.parser.addSetProperties(pattern);
/* 148 */     this.parser.addSetNext(pattern, "addSqlNode");
/*     */ 
/* 151 */     pattern = "Application/DataSource/Summed";
/*     */ 
/* 153 */     this.parser.addFactoryCreate(pattern, new AbstractObjectCreationFactory()
/*     */     {
/*     */       public Object createObject(Attributes attributes) throws Exception {
/* 156 */         HiSummedNode st = null;
/* 157 */         String object = attributes.getValue("type");
/*     */ 
/* 159 */         if (object.equals("field")) {
/* 160 */           HiFieldSummedNode st1 = new HiFieldSummedNode();
/* 161 */           st = st1;
/* 162 */         } else if (object.equals("sql")) {
/* 163 */           HiSqlSummedNode st1 = new HiSqlSummedNode();
/* 164 */           st = st1;
/*     */         }
/* 166 */         return st;
/*     */       }
/*     */     });
/* 170 */     this.parser.addSetNext(pattern, "setSummedNode");
/*     */ 
/* 173 */     pattern = "Application/DataSource/Summed/Field";
/* 174 */     this.parser.addObjectCreate(pattern, HiSumNode.class);
/* 175 */     this.parser.addSetProperties(pattern);
/* 176 */     this.parser.addSetNext(pattern, "addSumNode");
/*     */ 
/* 179 */     pattern = "*/Sql";
/* 180 */     this.parser.addObjectCreate(pattern, HiSqlNode.class);
/* 181 */     this.parser.addSetProperties(pattern);
/* 182 */     this.parser.addSetNext(pattern, "addSqlNode");
/*     */ 
/* 185 */     pattern = "*/Sql/FieldList";
/* 186 */     this.parser.addBeanPropertySetter(pattern, "fieldlist");
/*     */ 
/* 190 */     pattern = "Application/FormatDefine";
/* 191 */     this.parser.addObjectCreate(pattern, HiFormatDefineNode.class);
/* 192 */     this.parser.addSetProperties(pattern);
/* 193 */     this.parser.addSetNext(pattern, "setFormatDefine");
/*     */ 
/* 197 */     ObjectCreationFactory formatCreator = new AbstractObjectCreationFactory()
/*     */     {
/*     */       public Object createObject(Attributes attributes) throws Exception
/*     */       {
/* 201 */         HiFormatNode node = new HiFormatNode();
/* 202 */         HiReportConfig root = (HiReportConfig)this.digester.getRoot();
/* 203 */         node.setConvertor(root.convert);
/* 204 */         node.name = this.digester.getCurrentElementName();
/* 205 */         return node;
/*     */       }
/*     */     };
/* 209 */     pattern = "Application/FormatDefine/PageHeader";
/* 210 */     this.parser.addFactoryCreate(pattern, formatCreator);
/* 211 */     this.parser.addSetProperties(pattern);
/* 212 */     this.parser.addSetNext(pattern, "setPageHeader");
/*     */ 
/* 214 */     pattern = "Application/FormatDefine/ReportHeader";
/* 215 */     this.parser.addFactoryCreate(pattern, formatCreator);
/* 216 */     this.parser.addSetProperties(pattern);
/* 217 */     this.parser.addSetNext(pattern, "setReportHeader");
/*     */ 
/* 219 */     pattern = "Application/FormatDefine/TableHeader";
/* 220 */     this.parser.addFactoryCreate(pattern, formatCreator);
/* 221 */     this.parser.addSetProperties(pattern);
/* 222 */     this.parser.addSetNext(pattern, "setTableHeader");
/*     */ 
/* 224 */     pattern = "Application/FormatDefine/Iterative";
/* 225 */     this.parser.addFactoryCreate(pattern, formatCreator);
/* 226 */     this.parser.addSetProperties(pattern);
/* 227 */     this.parser.addSetNext(pattern, "setIterative");
/*     */ 
/* 229 */     pattern = "Application/FormatDefine/SubTotal";
/* 230 */     this.parser.addFactoryCreate(pattern, formatCreator);
/* 231 */     this.parser.addSetProperties(pattern);
/* 232 */     this.parser.addSetNext(pattern, "addSubTotal");
/*     */ 
/* 234 */     pattern = "Application/FormatDefine/Summed";
/* 235 */     this.parser.addFactoryCreate(pattern, formatCreator);
/* 236 */     this.parser.addSetProperties(pattern);
/* 237 */     this.parser.addSetNext(pattern, "setSummed");
/*     */ 
/* 239 */     pattern = "Application/FormatDefine/TableFooter";
/* 240 */     this.parser.addFactoryCreate(pattern, formatCreator);
/* 241 */     this.parser.addSetProperties(pattern);
/* 242 */     this.parser.addSetNext(pattern, "setTableFooter");
/*     */ 
/* 244 */     pattern = "Application/FormatDefine/ReportFooter";
/* 245 */     this.parser.addFactoryCreate(pattern, formatCreator);
/* 246 */     this.parser.addSetProperties(pattern);
/* 247 */     this.parser.addSetNext(pattern, "setReportFooter");
/*     */ 
/* 249 */     pattern = "Application/FormatDefine/PageFooter";
/* 250 */     this.parser.addFactoryCreate(pattern, formatCreator);
/* 251 */     this.parser.addSetProperties(pattern);
/* 252 */     this.parser.addSetNext(pattern, "setPageFooter");
/*     */ 
/* 255 */     pattern = "*/Para";
/* 256 */     this.parser.addObjectCreate(pattern, HiFormatNode.FormatParam.class);
/* 257 */     this.parser.addSetProperties(pattern);
/* 258 */     this.parser.addSetNext(pattern, "addParam");
/*     */ 
/* 261 */     pattern = "*/Format";
/*     */ 
/* 264 */     this.parser.addRule(pattern, new BeanPropertySetterRule("format")
/*     */     {
/*     */       public void body(String namespace, String name, String text) throws Exception
/*     */       {
/* 268 */         this.bodyText = text;
/*     */       }
/*     */     });
/* 273 */     this.parser.setErrorHandler(this);
/*     */ 
/* 281 */     this.parser.addRule("!*", new SetLocationRule());
/*     */   }
/*     */ 
/*     */   public void error(SAXParseException arg0) throws SAXException
/*     */   {
/* 286 */     throw arg0;
/*     */   }
/*     */ 
/*     */   public void fatalError(SAXParseException arg0)
/*     */     throws SAXException
/*     */   {
/* 292 */     throw arg0;
/*     */   }
/*     */ 
/*     */   public HiReportConfig parse(URL url) throws Exception {
/* 296 */     this.parser.clear();
/* 297 */     this.parser.setClassLoader(Thread.currentThread().getContextClassLoader());
/*     */ 
/* 299 */     this.parser.setPublicId(url.getPath());
/* 300 */     return ((HiReportConfig)this.parser.parse(url.openStream()));
/*     */   }
/*     */ 
/*     */   public void warning(SAXParseException arg0)
/*     */     throws SAXException
/*     */   {
/* 307 */     throw arg0;
/*     */   }
/*     */ }