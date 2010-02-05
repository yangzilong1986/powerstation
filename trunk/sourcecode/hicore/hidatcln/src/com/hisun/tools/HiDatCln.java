 package com.hisun.tools;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.message.HiContext;
 import com.hisun.parse.HiPretreatment;
 import com.hisun.tools.parser.HiClnTran;
 import com.hisun.util.HiResource;
 import com.hisun.util.HiSysParamParser;
 import java.io.ByteArrayInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.PrintStream;
 import java.sql.Connection;
 import java.util.HashMap;
 import org.apache.commons.digester.Digester;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class HiDatCln
 {
   private String _file;
   private String _id;
 
   public String getFile()
   {
     return this._file;
   }
 
   public void setFile(String file) {
     this._file = file;
   }
 
   public String getId() {
     return this._id;
   }
 
   public void setId(String id) {
     this._id = id;
   }
 
   public static void main(String[] args)
   {
     if (args.length < 3) {
       System.out.println("at least 3 params");
       System.exit(-1);
     }
 
     HiDatCln datClr = new HiDatCln();
     try {
       datClr.setFile(args[0]);
       datClr.setId(args[1]);
       datClr.process(setParam(args));
     } catch (Throwable t) {
       t.printStackTrace();
       System.exit(-1);
     }
   }
 
   public static HiClnParam setParam(String[] args) throws Exception
   {
     HiSysParamParser.load(HiContext.getRootContext(), "Public");
     HiDataBaseUtil dbUtil = new HiDataBaseUtil();
     Connection conn = null;
 
     HiClnParam param = new HiClnParam();
     param._sysDate = args[2];
     param._dbUtil = new HiDBUtil();
     param._log = HiLog.getLogger("datcln.trc");
     param._args = new String[args.length - 2];
     System.arraycopy(args, 2, param._args, 0, args.length - 2);
     return param;
   }
 
   public void process(HiClnParam param) throws Exception {
     HiClnTran clnTran = parser(this._file);
     if (StringUtils.equalsIgnoreCase("ALL", this._id))
       clnTran.process(param);
     else
       clnTran.process(this._id, param);
   }
 
   public HiClnTran parser(String file) throws Exception
   {
     SAXReader saxReader = new SAXReader();
     InputStream is = HiResource.getResourceAsStream(file);
     if (is == null) {
       throw new IOException("file:[" + file + "] not existed!");
     }
     Document document = saxReader.read(is);
     Element rootNode = document.getRootElement();
     HashMap allElements = HiPretreatment.getAllElements(rootNode, null);
     HiPretreatment.parseInclude(allElements, document);
     String strXML = document.asXML();
     ByteArrayInputStream inFile = new ByteArrayInputStream(strXML.getBytes());
 
     InputStreamReader in = new InputStreamReader(inFile);
 
     Digester digester = new Digester();
     digester.setValidating(false);
     digester.addObjectCreate("ClnTran", "com.hisun.tools.parser.HiClnTran");
 
     digester.addObjectCreate("ClnTran/ClnGroup", "com.hisun.tools.parser.HiClnGroup");
 
     digester.addSetProperties("ClnTran/ClnGroup");
     digester.addSetNext("ClnTran/ClnGroup", "addClnGroup", "com.hisun.tools.parser.HiClnGroup");
 
     digester.addObjectCreate("ClnTran/ClnGroup/ClnRec", "com.hisun.tools.parser.HiClnRec");
 
     digester.addSetProperties("ClnTran/ClnGroup/ClnRec");
     digester.addSetNext("ClnTran/ClnGroup/ClnRec", "addClnRec", "com.hisun.tools.parser.HiClnRec");
 
     return ((HiClnTran)digester.parse(in));
   }
 }