 package com.hisun.atc.rpt.xml;
 
 import com.hisun.xml.SetLocationRule;
 import java.net.URL;
 import org.apache.commons.digester.AbstractObjectCreationFactory;
 import org.apache.commons.digester.BeanPropertySetterRule;
 import org.apache.commons.digester.Digester;
 import org.apache.commons.digester.ExtendedBaseRules;
 import org.apache.commons.digester.ObjectCreationFactory;
 import org.xml.sax.Attributes;
 import org.xml.sax.ErrorHandler;
 import org.xml.sax.SAXException;
 import org.xml.sax.SAXParseException;
 
 public class HiReportConfigParser
   implements ErrorHandler
 {
   private final Digester parser;
 
   public HiReportConfigParser()
   {
     this.parser = new Digester();
 
     this.parser.setRules(new ExtendedBaseRules());
 
     String pattern = "Application";
     this.parser.addObjectCreate(pattern, HiReportConfig.class);
     this.parser.addSetProperties(pattern);
 
     pattern = "Application/VarDef";
     this.parser.addObjectCreate(pattern, HiVarDefNode.class);
     this.parser.addSetNext(pattern, "setVarDef");
 
     pattern = "Application/VarDef/String";
     this.parser.addObjectCreate(pattern, HiVarDefNode.VarDefNode.class);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "addVarDef");
 
     pattern = "Application/MsgConvert";
     this.parser.addObjectCreate(pattern, HiMsgConvertNode.class);
     this.parser.addSetNext(pattern, "setMsgConvert");
 
     pattern = "Application/MsgConvert/Item";
     this.parser.addObjectCreate(pattern, HiMsgConvertNode.HiItemNode.class);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "addItem");
 
     pattern = "Application/MsgConvert/Item/Convert";
     Class[] paramTypes = { String.class, String.class };
     this.parser.addCallMethod(pattern, "addConvert", 2, paramTypes);
     this.parser.addCallParam(pattern, 0, "value");
     this.parser.addCallParam(pattern, 1, "msg");
 
     pattern = "Application/MsgConvert/Item/DEFAULT";
     paramTypes = new Class[] { String.class };
     this.parser.addCallMethod(pattern, "addDefault", 1, paramTypes);
     this.parser.addCallParam(pattern, 0, "msg");
 
     pattern = "Application/DataSource";
 
     this.parser.addFactoryCreate(pattern, new AbstractObjectCreationFactory()
     {
       public Object createObject(Attributes attributes) throws Exception {
         HiDataSourceNode ds = null;
         String object = attributes.getValue("object");
 
         if (object.equalsIgnoreCase("file")) {
           HiFileDataSourceNode ds1 = new HiFileDataSourceNode();
           ds1.setFile(attributes.getValue("filename"));
           ds = ds1;
         } else if (object.equalsIgnoreCase("sql")) {
           HiSqlDataSourceNode ds1 = new HiSqlDataSourceNode();
           ds = ds1;
         }
         return ds;
       }
     });
     this.parser.addSetNext(pattern, "setDataSource");
 
     pattern = "Application/DataSource/PageHeader";
     this.parser.addObjectCreate(pattern, HiPageHeaderNode.class);
     this.parser.addSetNext(pattern, "setPageheader");
 
     pattern = "Application/DataSource/Iterative";
     this.parser.addObjectCreate(pattern, HiIterativeNode.class);
     this.parser.addSetNext(pattern, "setIterative");
 
     pattern = "Application/DataSource/SubTotal";
 
     this.parser.addFactoryCreate(pattern, new AbstractObjectCreationFactory()
     {
       public Object createObject(Attributes attributes) throws Exception {
         HiSubTotalNode st = null;
         String object = attributes.getValue("type");
 
         if (object.equals("field")) {
           HiSubTotalNode st1 = new HiFieldSubTotalNode();
           st1.setGroupby(attributes.getValue("groupby"));
           st = st1;
         } else if (object.equals("sql")) {
           HiSqlSubTotalNode st1 = new HiSqlSubTotalNode();
           st1.setGroupby(attributes.getValue("groupby"));
           st = st1;
         }
         return st;
       }
     });
     this.parser.addSetNext(pattern, "setSubTotalNode");
 
     pattern = "Application/DataSource/SubTotal/Field";
     this.parser.addObjectCreate(pattern, HiFieldNode.class);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "addField");
 
     pattern = "Application/DataSource/SubTotal/Field/Sum";
     this.parser.addObjectCreate(pattern, HiSumNode.class);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "addSumNode");
 
     pattern = "Application/DataSource/SubTotal/Sql";
     this.parser.addObjectCreate(pattern, HiSubTotalSqlNode.class);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "addSqlNode");
 
     pattern = "Application/DataSource/Summed";
 
     this.parser.addFactoryCreate(pattern, new AbstractObjectCreationFactory()
     {
       public Object createObject(Attributes attributes) throws Exception {
         HiSummedNode st = null;
         String object = attributes.getValue("type");
 
         if (object.equals("field")) {
           HiFieldSummedNode st1 = new HiFieldSummedNode();
           st = st1;
         } else if (object.equals("sql")) {
           HiSqlSummedNode st1 = new HiSqlSummedNode();
           st = st1;
         }
         return st;
       }
     });
     this.parser.addSetNext(pattern, "setSummedNode");
 
     pattern = "Application/DataSource/Summed/Field";
     this.parser.addObjectCreate(pattern, HiSumNode.class);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "addSumNode");
 
     pattern = "*/Sql";
     this.parser.addObjectCreate(pattern, HiSqlNode.class);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "addSqlNode");
 
     pattern = "*/Sql/FieldList";
     this.parser.addBeanPropertySetter(pattern, "fieldlist");
 
     pattern = "Application/FormatDefine";
     this.parser.addObjectCreate(pattern, HiFormatDefineNode.class);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "setFormatDefine");
 
     ObjectCreationFactory formatCreator = new AbstractObjectCreationFactory()
     {
       public Object createObject(Attributes attributes) throws Exception
       {
         HiFormatNode node = new HiFormatNode();
         HiReportConfig root = (HiReportConfig)this.digester.getRoot();
         node.setConvertor(root.convert);
         node.name = this.digester.getCurrentElementName();
         return node;
       }
     };
     pattern = "Application/FormatDefine/PageHeader";
     this.parser.addFactoryCreate(pattern, formatCreator);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "setPageHeader");
 
     pattern = "Application/FormatDefine/ReportHeader";
     this.parser.addFactoryCreate(pattern, formatCreator);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "setReportHeader");
 
     pattern = "Application/FormatDefine/TableHeader";
     this.parser.addFactoryCreate(pattern, formatCreator);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "setTableHeader");
 
     pattern = "Application/FormatDefine/Iterative";
     this.parser.addFactoryCreate(pattern, formatCreator);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "setIterative");
 
     pattern = "Application/FormatDefine/SubTotal";
     this.parser.addFactoryCreate(pattern, formatCreator);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "addSubTotal");
 
     pattern = "Application/FormatDefine/Summed";
     this.parser.addFactoryCreate(pattern, formatCreator);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "setSummed");
 
     pattern = "Application/FormatDefine/TableFooter";
     this.parser.addFactoryCreate(pattern, formatCreator);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "setTableFooter");
 
     pattern = "Application/FormatDefine/ReportFooter";
     this.parser.addFactoryCreate(pattern, formatCreator);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "setReportFooter");
 
     pattern = "Application/FormatDefine/PageFooter";
     this.parser.addFactoryCreate(pattern, formatCreator);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "setPageFooter");
 
     pattern = "*/Para";
     this.parser.addObjectCreate(pattern, HiFormatNode.FormatParam.class);
     this.parser.addSetProperties(pattern);
     this.parser.addSetNext(pattern, "addParam");
 
     pattern = "*/Format";
 
     this.parser.addRule(pattern, new BeanPropertySetterRule("format")
     {
       public void body(String namespace, String name, String text) throws Exception
       {
         this.bodyText = text;
       }
     });
     this.parser.setErrorHandler(this);
 
     this.parser.addRule("!*", new SetLocationRule());
   }
 
   public void error(SAXParseException arg0) throws SAXException
   {
     throw arg0;
   }
 
   public void fatalError(SAXParseException arg0)
     throws SAXException
   {
     throw arg0;
   }
 
   public HiReportConfig parse(URL url) throws Exception {
     this.parser.clear();
     this.parser.setClassLoader(Thread.currentThread().getContextClassLoader());
 
     this.parser.setPublicId(url.getPath());
     return ((HiReportConfig)this.parser.parse(url.openStream()));
   }
 
   public void warning(SAXParseException arg0)
     throws SAXException
   {
     throw arg0;
   }
 }