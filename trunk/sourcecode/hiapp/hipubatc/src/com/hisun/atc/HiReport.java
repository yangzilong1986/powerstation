 package com.hisun.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiReportHelper;
 import com.hisun.atc.rpt.HiDataFile;
 import com.hisun.atc.rpt.HiReportConstants;
 import com.hisun.atc.rpt.HiReportRuntimeException;
 import com.hisun.atc.rpt.HiReportUtil;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.atc.rpt.xml.HiReportConfig;
 import com.hisun.atc.rpt.xml.HiReportConfigParser;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiResource;
 import com.runqian.report4.model.ReportDefine;
 import com.runqian.report4.model.engine.ExtCellSet;
 import com.runqian.report4.usermodel.Context;
 import com.runqian.report4.usermodel.Engine;
 import com.runqian.report4.usermodel.IReport;
 import com.runqian.report4.usermodel.Param;
 import com.runqian.report4.usermodel.ParamMetaData;
 import com.runqian.report4.util.ReportUtils;
 import com.runqian.report4.view.excel.ExcelReport;
 import java.io.File;
 import java.net.URL;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.dom4j.Document;
 import org.dom4j.Element;
 import org.dom4j.Node;
 
 public class HiReport
   implements HiReportConstants
 {
   static String RPTMNGFIL = "RPTMNG_PUB.XML";
 
   public int GenerateReportNew(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     ReportDefine rd;
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
 
     String rptFile = HiArgUtils.getStringNotNull(args, "RptFile");
     if (!(rptFile.startsWith(File.separator))) {
       rptFile = HiICSProperty.getWorkDir() + File.separator + rptFile;
     }
     if (!(new File(rptFile).exists())) {
       log.error("file:[" + rptFile + "] not exists");
       return -1;
     }
 
     String rptName = HiArgUtils.getStringNotNull(args, "RptName");
     if (!(rptName.startsWith(File.separator))) {
       rptName = HiICSProperty.getWorkDir() + File.separator + rptName;
     }
 
     Context cxt = new Context();
     ExtCellSet.setLicenseFileName(HiICSProperty.getWorkDir() + File.separator + "conf/quieeServer.lic");
     String reportFile = rptFile;
     try
     {
       rd = (ReportDefine)ReportUtils.read(reportFile);
     } catch (Exception e) {
       throw new HiException(e);
     }
     ParamMetaData pmd = rd.getParamMetaData();
     if (pmd != null) {
       int i = 0; for (int count = pmd.getParamCount(); i < count; ++i) {
         String name = pmd.getParam(i).getParamName();
         if (!(args.contains(name))) {
           continue;
         }
         cxt.setParamValue(name, args.get(name));
       }
     }
     Context.setMainDir(HiICSProperty.getWorkDir());
     cxt.setConnection("default_ds", ctx.getDataBaseUtil().getConnection());
 
     Engine engine = new Engine(rd, cxt);
     IReport iReport = engine.calc();
 
     ExcelReport excelReport = new ExcelReport();
     excelReport.export(iReport);
     excelReport.saveTo(rptName);
 
     return 0;
   }
 
   public int GenerateReportByIDNew(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     return 0;
   }
 
   public int GenerateReport(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiReportConfig config;
     String rptName = argsMap.get("RptName");
     String rptFile = argsMap.get("RptFile");
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     HiRptContext vsource = createRptContext(argsMap, ctx);
 
     HiReportConfigParser parser = new HiReportConfigParser();
     URL resource = HiResource.getResource(rptFile);
     if (resource == null) {
       throw new HiException("213302", rptFile);
     }
     try
     {
       config = parser.parse(resource);
     } catch (Exception e1) {
       throw new HiException("220325", rptFile, e1);
     }
 
     vsource.logger = config.log;
     vsource.trc = ctx.getCurrentMsg().getHeadItem("STF");
     try {
       config.process(vsource, HiReportUtil.getFullPath(rptName));
     } catch (HiReportRuntimeException e) {
       log.error("报表生成错误:" + e.getErrCode(), e);
       if (e.getErrCode() > 0)
         return e.getErrCode();
       if (e.getCause() instanceof HiException)
         throw ((HiException)e.getCause());
       throw new HiException("220326", rptName, e.getCause());
     }
 
     return 0;
   }
 
   private HiRptContext createRptContext(HiATLParam argsMap, HiMessageContext ctx)
   {
     HiRptContext vsource = new HiRptContext(ctx, argsMap) { private final HiMessageContext val$ctx;
       private final HiATLParam val$argsMap;
 
       public String getValueByName(String name) { return this.val$ctx.getCurrentMsg().getETFBody().getChildValue(name);
       }
 
       public String getValueByPos(int pos) {
         return this.val$argsMap.getValue(pos + 1);
       }
 
       public HiDataBaseUtil getDBUtil() {
         return this.val$ctx.getDataBaseUtil();
       }
 
       public HiDataFile createDataFile(String path) {
         return super.createDataFile(HiReportUtil.getFullPath(path));
       }
     };
     return vsource;
   }
 
   public int GenerateReportByID(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiReportConfig config;
     String mngFile = argsMap.get("ManageFile");
     if (mngFile == null) {
       mngFile = "rptManage";
     }
 
     Element root = (Element)ctx.getProperty("CONFIGDECLARE", mngFile);
 
     if (root == null) {
       throw new HiException("220325", mngFile);
     }
 
     String id = argsMap.get("RptID");
     if (id == null) {
       id = ctx.getCurrentMsg().getETFBody().getChildValue("Rpt_ID");
     }
     if (id == null) {
       throw new HiException("220325", "no Rpt_ID");
     }
 
     Element rptnode = elementByID(root, "id", id);
 
     String cfg = rptnode.attributeValue("config");
     if (cfg == null)
     {
       throw new HiException("220325", "no config file");
     }
 
     String rptFile = getExpValue(cfg, ctx);
     if (rptFile == null)
     {
       throw new HiException("220325", "err read " + cfg);
     }
 
     String name = rptnode.attributeValue("name");
     if (name == null)
     {
       throw new HiException("220325", "no report name");
     }
 
     String rptName = getExpValue(name, ctx);
     if (rptName == null)
     {
       throw new HiException("220325", "err read " + rptName);
     }
 
     HiATLParam args = new HiATLParam();
     args.put("RptName", rptName);
     args.put("RptFile", rptFile);
 
     Iterator it = rptnode.elementIterator("Para");
     while (it.hasNext()) {
       Element paranode = (Element)it.next();
       String label = paranode.attributeValue("label");
       String value = paranode.attributeValue("value");
       if (value == null)
       {
         throw new HiException("220325", "Para no label");
       }
 
       value = getExpValue(value, ctx);
       if (value == null)
       {
         throw new HiException("220325", "Para no value");
       }
 
       args.put(label, value);
     }
 
     HiRptContext vsource = createRptContext(args, ctx);
 
     HiReportConfigParser parser = new HiReportConfigParser();
     URL resource = HiResource.getResource(rptFile);
     if (resource == null) {
       throw new HiException("213302", rptFile);
     }
     try
     {
       config = parser.parse(resource);
     } catch (Exception e1) {
       throw new HiException("220325", rptFile, e1);
     }
 
     vsource.logger = config.log;
     try {
       config.process(vsource, rptName);
     } catch (HiReportRuntimeException e) {
       if (e.getCause() instanceof HiException)
         throw ((HiException)e.getCause());
       throw new HiException("220326", rptName, e.getCause());
     }
 
     return 0;
   }
 
   public Element elementByID(Element e, String attr, String elementID) {
     int i = 0; for (int size = e.nodeCount(); i < size; ++i) {
       Node node = e.node(i);
       if (node instanceof Element) {
         Element element = (Element)node;
         String id = element.attributeValue(attr);
         if ((id != null) && (id.equals(elementID))) {
           return element;
         }
         element = elementByID(element, attr, elementID);
         if (element != null) {
           return element;
         }
       }
     }
 
     return null;
   }
 
   private String getExpValue(String exp, HiMessageContext ctx)
   {
     HiExpression expression = HiExpFactory.createExp(exp);
     try {
       return expression.getValue(ctx);
     } catch (HiException e) {
       e.printStackTrace();
     }
     return null;
   }
 
   public int GetRptAppList(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etfBody = msg.getETFBody();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: GetRptAppList");
     }
 
     String brNo = argsMap.get("BrNo");
     if (brNo == null) {
       brNo = etfBody.getChildValue("BR_NO");
     }
     if (brNo == null) {
       throw new HiException("220026", "找不到分行号Br_No,若没配置参数,则必须存在ETF树;");
     }
 
     List queryRs = ctx.getDataBaseUtil().execQuery("select distinct APP_ID,APP_DESC from mngaplinf where BR_NO='" + brNo + "'");
 
     Iterator queryIt = queryRs.iterator();
     Map queryRec = null;
     Map appMap = new HashMap();
     while (queryIt.hasNext()) {
       queryRec = (Map)queryIt.next();
       appMap.put(queryRec.get("APP_ID"), queryRec.get("APP_DESC"));
     }
 
     Document rptMngDoc = null;
     try {
       rptMngDoc = HiReportHelper.parser("etc/" + RPTMNGFIL);
     } catch (HiException he) {
       throw he;
     } catch (Exception e) {
       throw new HiException("", e.getMessage());
     }
 
     if (rptMngDoc == null) {
       throw new HiException("", "加载配置文件失败: etc/" + RPTMNGFIL);
     }
 
     Iterator it = rptMngDoc.getRootElement().elementIterator("Application");
     int i = 1;
 
     while (it.hasNext()) {
       Element appNode = (Element)it.next();
       String id = appNode.attributeValue("name");
       String desc = (String)appMap.get(id);
       if (desc == null)
       {
         desc = id;
       }
       HiETF appGrpNode = etfBody.addNode("App_" + i);
       appGrpNode.setChildValue("App_ID", id);
       appGrpNode.setChildValue("App_NM", desc);
       ++i;
     }
 
     etfBody.setChildValue("App_Num", String.valueOf(i - 1));
 
     appMap = null;
 
     return 0;
   }
 
   public int GetRptList(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     Iterator attrIt;
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etfBody = msg.getETFBody();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: GetRptList");
     }
 
     Document rptMngDoc = null;
     try {
       rptMngDoc = HiReportHelper.parser("etc/" + RPTMNGFIL);
     } catch (HiException he) {
       throw he;
     } catch (Exception e) {
       throw new HiException("", e.getMessage());
     }
 
     if (rptMngDoc == null) {
       throw new HiException("", "加载配置文件失败: etc/" + RPTMNGFIL);
     }
 
     Iterator appIt = rptMngDoc.getRootElement().elementIterator("Application");
 
     Element appNode = null;
 
     Element attrNode = null;
 
     Element rptNode = null;
 
     int i = 1;
     do
     {
       if (!(appIt.hasNext())) break label382;
       appNode = (Element)appIt.next();
       attrIt = appNode.elementIterator("Attribute"); }
     while (!(attrIt.hasNext()));
     attrNode = (Element)attrIt.next();
     String attrName = attrNode.attributeValue("attr");
     if (attrName == null) {
       throw new HiException("", "配置文件RPTMNG_PUB.XML非法--缺少Attribute节点attr属性");
     }
 
     Iterator rptIt = attrNode.elementIterator("Rpt");
     while (true) { if (rptIt.hasNext());
       rptNode = (Element)rptIt.next();
       String rptId = rptNode.attributeValue("id");
       if (rptId == null) {
         throw new HiException("", "配置文件RPTMNG_PUB.XML非法--缺少Rpt节点id属性");
       }
 
       String chnnam = rptNode.attributeValue("chnnam");
       if (chnnam == null) {
         chnnam = rptId;
       }
 
       HiETF rptGrpNode = etfBody.addNode("Rpt_" + i);
       rptGrpNode.setChildValue("Rpt_Id", rptId);
       rptGrpNode.setChildValue("Rpt_NM", chnnam);
       rptGrpNode.setChildValue("Rpt_Atr", attrName);
 
       ++i;
     }
 
     label382: return 0;
   }
 }