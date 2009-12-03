/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.atc.common.HiReportHelper;
/*     */ import com.hisun.atc.rpt.HiDataFile;
/*     */ import com.hisun.atc.rpt.HiReportConstants;
/*     */ import com.hisun.atc.rpt.HiReportRuntimeException;
/*     */ import com.hisun.atc.rpt.HiReportUtil;
/*     */ import com.hisun.atc.rpt.HiRptContext;
/*     */ import com.hisun.atc.rpt.xml.HiReportConfig;
/*     */ import com.hisun.atc.rpt.xml.HiReportConfigParser;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.runqian.report4.model.ReportDefine;
/*     */ import com.runqian.report4.model.engine.ExtCellSet;
/*     */ import com.runqian.report4.usermodel.Context;
/*     */ import com.runqian.report4.usermodel.Engine;
/*     */ import com.runqian.report4.usermodel.IReport;
/*     */ import com.runqian.report4.usermodel.Param;
/*     */ import com.runqian.report4.usermodel.ParamMetaData;
/*     */ import com.runqian.report4.util.ReportUtils;
/*     */ import com.runqian.report4.view.excel.ExcelReport;
/*     */ import java.io.File;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.Node;
/*     */ 
/*     */ public class HiReport
/*     */   implements HiReportConstants
/*     */ {
/* 400 */   static String RPTMNGFIL = "RPTMNG_PUB.XML";
/*     */ 
/*     */   public int GenerateReportNew(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     ReportDefine rd;
/*  81 */     HiMessage msg = ctx.getCurrentMsg();
/*  82 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/*  84 */     String rptFile = HiArgUtils.getStringNotNull(args, "RptFile");
/*  85 */     if (!(rptFile.startsWith(File.separator))) {
/*  86 */       rptFile = HiICSProperty.getWorkDir() + File.separator + rptFile;
/*     */     }
/*  88 */     if (!(new File(rptFile).exists())) {
/*  89 */       log.error("file:[" + rptFile + "] not exists");
/*  90 */       return -1;
/*     */     }
/*     */ 
/*  93 */     String rptName = HiArgUtils.getStringNotNull(args, "RptName");
/*  94 */     if (!(rptName.startsWith(File.separator))) {
/*  95 */       rptName = HiICSProperty.getWorkDir() + File.separator + rptName;
/*     */     }
/*     */ 
/*  98 */     Context cxt = new Context();
/*  99 */     ExtCellSet.setLicenseFileName(HiICSProperty.getWorkDir() + File.separator + "conf/quieeServer.lic");
/* 100 */     String reportFile = rptFile;
/*     */     try
/*     */     {
/* 103 */       rd = (ReportDefine)ReportUtils.read(reportFile);
/*     */     } catch (Exception e) {
/* 105 */       throw new HiException(e);
/*     */     }
/* 107 */     ParamMetaData pmd = rd.getParamMetaData();
/* 108 */     if (pmd != null) {
/* 109 */       int i = 0; for (int count = pmd.getParamCount(); i < count; ++i) {
/* 110 */         String name = pmd.getParam(i).getParamName();
/* 111 */         if (!(args.contains(name))) {
/*     */           continue;
/*     */         }
/* 114 */         cxt.setParamValue(name, args.get(name));
/*     */       }
/*     */     }
/* 117 */     Context.setMainDir(HiICSProperty.getWorkDir());
/* 118 */     cxt.setConnection("default_ds", ctx.getDataBaseUtil().getConnection());
/*     */ 
/* 120 */     Engine engine = new Engine(rd, cxt);
/* 121 */     IReport iReport = engine.calc();
/*     */ 
/* 123 */     ExcelReport excelReport = new ExcelReport();
/* 124 */     excelReport.export(iReport);
/* 125 */     excelReport.saveTo(rptName);
/*     */ 
/* 127 */     return 0;
/*     */   }
/*     */ 
/*     */   public int GenerateReportByIDNew(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 151 */     return 0;
/*     */   }
/*     */ 
/*     */   public int GenerateReport(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     HiReportConfig config;
/* 175 */     String rptName = argsMap.get("RptName");
/* 176 */     String rptFile = argsMap.get("RptFile");
/* 177 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/* 180 */     HiRptContext vsource = createRptContext(argsMap, ctx);
/*     */ 
/* 183 */     HiReportConfigParser parser = new HiReportConfigParser();
/* 184 */     URL resource = HiResource.getResource(rptFile);
/* 185 */     if (resource == null) {
/* 186 */       throw new HiException("213302", rptFile);
/*     */     }
/*     */     try
/*     */     {
/* 190 */       config = parser.parse(resource);
/*     */     } catch (Exception e1) {
/* 192 */       throw new HiException("220325", rptFile, e1);
/*     */     }
/*     */ 
/* 195 */     vsource.logger = config.log;
/* 196 */     vsource.trc = ctx.getCurrentMsg().getHeadItem("STF");
/*     */     try {
/* 198 */       config.process(vsource, HiReportUtil.getFullPath(rptName));
/*     */     } catch (HiReportRuntimeException e) {
/* 200 */       log.error("报表生成错误:" + e.getErrCode(), e);
/* 201 */       if (e.getErrCode() > 0)
/* 202 */         return e.getErrCode();
/* 203 */       if (e.getCause() instanceof HiException)
/* 204 */         throw ((HiException)e.getCause());
/* 205 */       throw new HiException("220326", rptName, e.getCause());
/*     */     }
/*     */ 
/* 208 */     return 0;
/*     */   }
/*     */ 
/*     */   private HiRptContext createRptContext(HiATLParam argsMap, HiMessageContext ctx)
/*     */   {
/* 213 */     HiRptContext vsource = new HiRptContext(ctx, argsMap) { private final HiMessageContext val$ctx;
/*     */       private final HiATLParam val$argsMap;
/*     */ 
/*     */       public String getValueByName(String name) { return this.val$ctx.getCurrentMsg().getETFBody().getChildValue(name);
/*     */       }
/*     */ 
/*     */       public String getValueByPos(int pos) {
/* 220 */         return this.val$argsMap.getValue(pos + 1);
/*     */       }
/*     */ 
/*     */       public HiDataBaseUtil getDBUtil() {
/* 224 */         return this.val$ctx.getDataBaseUtil();
/*     */       }
/*     */ 
/*     */       public HiDataFile createDataFile(String path) {
/* 228 */         return super.createDataFile(HiReportUtil.getFullPath(path));
/*     */       }
/*     */     };
/* 232 */     return vsource;
/*     */   }
/*     */ 
/*     */   public int GenerateReportByID(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     HiReportConfig config;
/* 257 */     String mngFile = argsMap.get("ManageFile");
/* 258 */     if (mngFile == null) {
/* 259 */       mngFile = "rptManage";
/*     */     }
/*     */ 
/* 263 */     Element root = (Element)ctx.getProperty("CONFIGDECLARE", mngFile);
/*     */ 
/* 265 */     if (root == null) {
/* 266 */       throw new HiException("220325", mngFile);
/*     */     }
/*     */ 
/* 270 */     String id = argsMap.get("RptID");
/* 271 */     if (id == null) {
/* 272 */       id = ctx.getCurrentMsg().getETFBody().getChildValue("Rpt_ID");
/*     */     }
/* 274 */     if (id == null) {
/* 275 */       throw new HiException("220325", "no Rpt_ID");
/*     */     }
/*     */ 
/* 279 */     Element rptnode = elementByID(root, "id", id);
/*     */ 
/* 282 */     String cfg = rptnode.attributeValue("config");
/* 283 */     if (cfg == null)
/*     */     {
/* 285 */       throw new HiException("220325", "no config file");
/*     */     }
/*     */ 
/* 288 */     String rptFile = getExpValue(cfg, ctx);
/* 289 */     if (rptFile == null)
/*     */     {
/* 291 */       throw new HiException("220325", "err read " + cfg);
/*     */     }
/*     */ 
/* 296 */     String name = rptnode.attributeValue("name");
/* 297 */     if (name == null)
/*     */     {
/* 299 */       throw new HiException("220325", "no report name");
/*     */     }
/*     */ 
/* 302 */     String rptName = getExpValue(name, ctx);
/* 303 */     if (rptName == null)
/*     */     {
/* 305 */       throw new HiException("220325", "err read " + rptName);
/*     */     }
/*     */ 
/* 309 */     HiATLParam args = new HiATLParam();
/* 310 */     args.put("RptName", rptName);
/* 311 */     args.put("RptFile", rptFile);
/*     */ 
/* 313 */     Iterator it = rptnode.elementIterator("Para");
/* 314 */     while (it.hasNext()) {
/* 315 */       Element paranode = (Element)it.next();
/* 316 */       String label = paranode.attributeValue("label");
/* 317 */       String value = paranode.attributeValue("value");
/* 318 */       if (value == null)
/*     */       {
/* 320 */         throw new HiException("220325", "Para no label");
/*     */       }
/*     */ 
/* 323 */       value = getExpValue(value, ctx);
/* 324 */       if (value == null)
/*     */       {
/* 326 */         throw new HiException("220325", "Para no value");
/*     */       }
/*     */ 
/* 329 */       args.put(label, value);
/*     */     }
/*     */ 
/* 333 */     HiRptContext vsource = createRptContext(args, ctx);
/*     */ 
/* 336 */     HiReportConfigParser parser = new HiReportConfigParser();
/* 337 */     URL resource = HiResource.getResource(rptFile);
/* 338 */     if (resource == null) {
/* 339 */       throw new HiException("213302", rptFile);
/*     */     }
/*     */     try
/*     */     {
/* 343 */       config = parser.parse(resource);
/*     */     } catch (Exception e1) {
/* 345 */       throw new HiException("220325", rptFile, e1);
/*     */     }
/*     */ 
/* 348 */     vsource.logger = config.log;
/*     */     try {
/* 350 */       config.process(vsource, rptName);
/*     */     } catch (HiReportRuntimeException e) {
/* 352 */       if (e.getCause() instanceof HiException)
/* 353 */         throw ((HiException)e.getCause());
/* 354 */       throw new HiException("220326", rptName, e.getCause());
/*     */     }
/*     */ 
/* 358 */     return 0;
/*     */   }
/*     */ 
/*     */   public Element elementByID(Element e, String attr, String elementID) {
/* 362 */     int i = 0; for (int size = e.nodeCount(); i < size; ++i) {
/* 363 */       Node node = e.node(i);
/* 364 */       if (node instanceof Element) {
/* 365 */         Element element = (Element)node;
/* 366 */         String id = element.attributeValue(attr);
/* 367 */         if ((id != null) && (id.equals(elementID))) {
/* 368 */           return element;
/*     */         }
/* 370 */         element = elementByID(element, attr, elementID);
/* 371 */         if (element != null) {
/* 372 */           return element;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 377 */     return null;
/*     */   }
/*     */ 
/*     */   private String getExpValue(String exp, HiMessageContext ctx)
/*     */   {
/* 388 */     HiExpression expression = HiExpFactory.createExp(exp);
/*     */     try {
/* 390 */       return expression.getValue(ctx);
/*     */     } catch (HiException e) {
/* 392 */       e.printStackTrace();
/*     */     }
/* 394 */     return null;
/*     */   }
/*     */ 
/*     */   public int GetRptAppList(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 417 */     HiMessage msg = ctx.getCurrentMsg();
/* 418 */     HiETF etfBody = msg.getETFBody();
/* 419 */     Logger log = HiLog.getLogger(msg);
/* 420 */     if (log.isDebugEnabled()) {
/* 421 */       log.debug("开始执行原子组件: GetRptAppList");
/*     */     }
/*     */ 
/* 424 */     String brNo = argsMap.get("BrNo");
/* 425 */     if (brNo == null) {
/* 426 */       brNo = etfBody.getChildValue("BR_NO");
/*     */     }
/* 428 */     if (brNo == null) {
/* 429 */       throw new HiException("220026", "找不到分行号Br_No,若没配置参数,则必须存在ETF树;");
/*     */     }
/*     */ 
/* 434 */     List queryRs = ctx.getDataBaseUtil().execQuery("select distinct APP_ID,APP_DESC from mngaplinf where BR_NO='" + brNo + "'");
/*     */ 
/* 437 */     Iterator queryIt = queryRs.iterator();
/* 438 */     Map queryRec = null;
/* 439 */     Map appMap = new HashMap();
/* 440 */     while (queryIt.hasNext()) {
/* 441 */       queryRec = (Map)queryIt.next();
/* 442 */       appMap.put(queryRec.get("APP_ID"), queryRec.get("APP_DESC"));
/*     */     }
/*     */ 
/* 446 */     Document rptMngDoc = null;
/*     */     try {
/* 448 */       rptMngDoc = HiReportHelper.parser("etc/" + RPTMNGFIL);
/*     */     } catch (HiException he) {
/* 450 */       throw he;
/*     */     } catch (Exception e) {
/* 452 */       throw new HiException("", e.getMessage());
/*     */     }
/*     */ 
/* 455 */     if (rptMngDoc == null) {
/* 456 */       throw new HiException("", "加载配置文件失败: etc/" + RPTMNGFIL);
/*     */     }
/*     */ 
/* 464 */     Iterator it = rptMngDoc.getRootElement().elementIterator("Application");
/* 465 */     int i = 1;
/*     */ 
/* 467 */     while (it.hasNext()) {
/* 468 */       Element appNode = (Element)it.next();
/* 469 */       String id = appNode.attributeValue("name");
/* 470 */       String desc = (String)appMap.get(id);
/* 471 */       if (desc == null)
/*     */       {
/* 475 */         desc = id;
/*     */       }
/* 477 */       HiETF appGrpNode = etfBody.addNode("App_" + i);
/* 478 */       appGrpNode.setChildValue("App_ID", id);
/* 479 */       appGrpNode.setChildValue("App_NM", desc);
/* 480 */       ++i;
/*     */     }
/*     */ 
/* 484 */     etfBody.setChildValue("App_Num", String.valueOf(i - 1));
/*     */ 
/* 486 */     appMap = null;
/*     */ 
/* 488 */     return 0;
/*     */   }
/*     */ 
/*     */   public int GetRptList(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     Iterator attrIt;
/* 504 */     HiMessage msg = ctx.getCurrentMsg();
/* 505 */     HiETF etfBody = msg.getETFBody();
/* 506 */     Logger log = HiLog.getLogger(msg);
/* 507 */     if (log.isDebugEnabled()) {
/* 508 */       log.debug("开始执行原子组件: GetRptList");
/*     */     }
/*     */ 
/* 511 */     Document rptMngDoc = null;
/*     */     try {
/* 513 */       rptMngDoc = HiReportHelper.parser("etc/" + RPTMNGFIL);
/*     */     } catch (HiException he) {
/* 515 */       throw he;
/*     */     } catch (Exception e) {
/* 517 */       throw new HiException("", e.getMessage());
/*     */     }
/*     */ 
/* 520 */     if (rptMngDoc == null) {
/* 521 */       throw new HiException("", "加载配置文件失败: etc/" + RPTMNGFIL);
/*     */     }
/*     */ 
/* 524 */     Iterator appIt = rptMngDoc.getRootElement().elementIterator("Application");
/*     */ 
/* 526 */     Element appNode = null;
/*     */ 
/* 529 */     Element attrNode = null;
/*     */ 
/* 533 */     Element rptNode = null;
/*     */ 
/* 536 */     int i = 1;
/*     */     do
/*     */     {
/* 540 */       if (!(appIt.hasNext())) break label382;
/* 541 */       appNode = (Element)appIt.next();
/* 542 */       attrIt = appNode.elementIterator("Attribute"); }
/* 543 */     while (!(attrIt.hasNext()));
/* 544 */     attrNode = (Element)attrIt.next();
/* 545 */     String attrName = attrNode.attributeValue("attr");
/* 546 */     if (attrName == null) {
/* 547 */       throw new HiException("", "配置文件RPTMNG_PUB.XML非法--缺少Attribute节点attr属性");
/*     */     }
/*     */ 
/* 551 */     Iterator rptIt = attrNode.elementIterator("Rpt");
/*     */     while (true) { if (rptIt.hasNext());
/* 553 */       rptNode = (Element)rptIt.next();
/* 554 */       String rptId = rptNode.attributeValue("id");
/* 555 */       if (rptId == null) {
/* 556 */         throw new HiException("", "配置文件RPTMNG_PUB.XML非法--缺少Rpt节点id属性");
/*     */       }
/*     */ 
/* 560 */       String chnnam = rptNode.attributeValue("chnnam");
/* 561 */       if (chnnam == null) {
/* 562 */         chnnam = rptId;
/*     */       }
/*     */ 
/* 565 */       HiETF rptGrpNode = etfBody.addNode("Rpt_" + i);
/* 566 */       rptGrpNode.setChildValue("Rpt_Id", rptId);
/* 567 */       rptGrpNode.setChildValue("Rpt_NM", chnnam);
/* 568 */       rptGrpNode.setChildValue("Rpt_Atr", attrName);
/*     */ 
/* 570 */       ++i;
/*     */     }
/*     */ 
/* 576 */     label382: return 0;
/*     */   }
/*     */ }