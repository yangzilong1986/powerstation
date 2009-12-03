/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.atc.common.HiDbtSqlHelper;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiStringUtils;
/*     */ import com.hisun.util.HiXmlHelper;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiAppInfo
/*     */ {
/*     */   public static int GetAppInfo(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  58 */     HiMessage mess = ctx.getCurrentMsg();
/*  59 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/*  61 */     if ((mess == null) || (ctx == null))
/*     */     {
/*  63 */       throw new HiException("211007", "前置系统错");
/*     */     }
/*  65 */     HiETF etfRoot = (HiETF)mess.getBody();
/*  66 */     if ((etfRoot == null) || (etfRoot.isNullNode()))
/*     */     {
/*  68 */       throw new HiException("220058", "ETF is NULL");
/*     */     }
/*  70 */     StringBuffer condition = new StringBuffer();
/*     */ 
/*  72 */     String argVal = argsMap.get("CndSts");
/*     */ 
/*  74 */     if (!(StringUtils.isEmpty(argVal)))
/*     */     {
/*     */       try
/*     */       {
/*  78 */         condition.append(HiDbtSqlHelper.getDynSentence(ctx, argVal));
/*     */       }
/*     */       catch (HiException e)
/*     */       {
/*  82 */         throw new HiException("220026", argVal);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  87 */     String aplCls = etfRoot.getChildValue("APP_CLS");
/*  88 */     String busTyp = etfRoot.getChildValue("BUS_TYP");
/*  89 */     String busSub = etfRoot.getChildValue("BUS_SUB");
/*     */ 
/*  91 */     if ((aplCls == null) || (busTyp == null))
/*     */     {
/*  93 */       throw new HiException("220058", "没有键值![APP_CLS][BUS_TYP]");
/*     */     }
/*     */ 
/*  96 */     if (busSub == null)
/*     */     {
/*  98 */       busSub = "";
/*     */     }
/* 100 */     condition = new StringBuffer(HiStringUtils.format(" APP_CLS = '%s' AND BUS_TYP = '%s' AND ( BUS_SUB = '%s' OR BUS_SUB = ' ' )", aplCls, busTyp, busSub));
/*     */ 
/* 110 */     String brNo = etfRoot.getChildValue("Br_No");
/* 111 */     if (!(StringUtils.isEmpty(brNo)))
/*     */     {
/* 113 */       condition.insert(0, "' AND ");
/* 114 */       condition.insert(0, brNo);
/* 115 */       condition.insert(0, "SELECT * FROM PUBAPLMNG WHERE BR_NO = '");
/*     */     }
/*     */     else
/*     */     {
/* 119 */       condition.insert(0, "SELECT * FROM PUBAPLMNG WHERE ");
/*     */     }
/*     */ 
/* 122 */     List list = null;
/*     */     try
/*     */     {
/* 125 */       if (log.isDebugEnabled())
/*     */       {
/* 127 */         log.debug("SQL is " + condition.toString());
/*     */       }
/* 129 */       list = ctx.getDataBaseUtil().execQuery(condition.toString());
/*     */ 
/* 131 */       if ((list != null) && (list.size() == 1))
/*     */       {
/* 133 */         HashMap resMap = (HashMap)list.get(0);
/* 134 */         Iterator iter = resMap.keySet().iterator();
/* 135 */         while (iter.hasNext())
/*     */         {
/* 137 */           String key = (String)iter.next();
/* 138 */           String val = (String)resMap.get(key);
/* 139 */           if (!(StringUtils.isEmpty(val)))
/*     */           {
/* 141 */             etfRoot.setChildValue(key, val);
/*     */           }
/*     */         }
/*     */       } else {
/* 145 */         if (list.size() == 0)
/*     */         {
/* 147 */           return 2;
/*     */         }
/*     */ 
/* 153 */         throw new HiException("220038");
/*     */       }
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/* 158 */       throw new HiAppException(-1, "220038", "DB SYSTEM ERROR");
/*     */     }
/*     */ 
/* 161 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int DeleteGroup(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 182 */     HiMessage mess = ctx.getCurrentMsg();
/* 183 */     if ((argsMap == null) || (mess == null))
/*     */     {
/* 185 */       throw new HiException("220026", "[HiATLParam][HiMessage]");
/*     */     }
/*     */ 
/* 189 */     String groupName = HiArgUtils.getStringNotNull(argsMap, "GroupName");
/*     */ 
/* 192 */     String num = argsMap.get("RecordNum");
/* 193 */     HiETF etfRoot = mess.getETFBody();
/* 194 */     int count = 0;
/* 195 */     if (num == null)
/*     */     {
/* 197 */       List list = etfRoot.getGrandChildFuzzyEnd(groupName + "_");
/* 198 */       count = list.size();
/*     */     }
/*     */     else
/*     */     {
/* 202 */       if (!(NumberUtils.isNumber(num)))
/*     */       {
/* 205 */         throw new HiAppException(-1, "", "RecordNum");
/*     */       }
/* 207 */       count = NumberUtils.toInt(num);
/*     */     }
/*     */ 
/* 210 */     for (int i = 1; i < count + 1; ++i)
/*     */     {
/* 212 */       String tmp = HiStringUtils.format("%s_%s", groupName, Integer.toString(i));
/*     */ 
/* 214 */       etfRoot.removeChildNode(tmp);
/*     */     }
/*     */ 
/* 217 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int ReadModuleCfg(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 247 */     HiMessage mess = ctx.getCurrentMsg();
/* 248 */     Logger log = HiLog.getLogger(mess);
/* 249 */     if (log.isDebugEnabled())
/*     */     {
/* 251 */       log.debug("开始执行原子组件: ReadModuleCfg ");
/*     */     }
/*     */ 
/* 254 */     if ((argsMap == null) || (argsMap.size() == 0))
/*     */     {
/* 256 */       if (log.isDebugEnabled())
/*     */       {
/* 258 */         log.debug("失败: 参数为空");
/*     */       }
/* 260 */       throw new HiException("220026", "参数为空");
/*     */     }
/*     */ 
/* 264 */     String paraFile = "CONFIGDECLARE.PARAFILE";
/*     */ 
/* 267 */     String appName = HiArgUtils.getStringNotNull(argsMap, "Application");
/* 268 */     String tranCode = HiArgUtils.getStringNotNull(argsMap, "Transaction");
/*     */ 
/* 270 */     if (log.isDebugEnabled())
/*     */     {
/* 272 */       log.debug("ReadModuleCfg 参数:Application [" + appName + "], Transaction[" + tranCode + "]");
/*     */     }
/*     */ 
/* 277 */     Element paraRoot = (Element)ctx.getProperty(paraFile);
/*     */ 
/* 279 */     if (paraRoot == null)
/*     */     {
/* 281 */       if (log.isDebugEnabled())
/*     */       {
/* 283 */         log.debug("失败: 根据键值ParaFile,从共享区获取失败");
/*     */       }
/* 285 */       throw new HiException("220026", "ReadModuleCfg : 根据键值ParaFile,从共享区获取失败");
/*     */     }
/*     */ 
/* 290 */     Element appNode = HiXmlHelper.getNodeByAttr(paraRoot, "Application", "name", appName);
/*     */ 
/* 292 */     if (appNode == null)
/*     */     {
/* 294 */       if (log.isDebugEnabled())
/*     */       {
/* 296 */         log.debug("失败: 取参数文件中的Applicatin节点失败");
/*     */       }
/* 298 */       throw new HiException("220026", "ReadModuleCfg : 根据键值ParaFile,从共享区获取失败");
/*     */     }
/*     */ 
/* 308 */     for (int i = 0; i < 2; ++i)
/*     */     {
/*     */       Element tranNode;
/* 310 */       if (i == 0)
/*     */       {
/* 313 */         tranNode = appNode.element("Public");
/*     */       }
/*     */       else
/*     */       {
/* 318 */         tranNode = HiXmlHelper.getNodeByAttr(appNode, "Transaction", "code", tranCode);
/*     */       }
/*     */ 
/* 321 */       if (tranNode == null) {
/*     */         continue;
/*     */       }
/* 324 */       Iterator it = tranNode.elementIterator("Arg");
/* 325 */       while (it.hasNext())
/*     */       {
/* 327 */         Element argNode = (Element)it.next();
/* 328 */         String argNam = argNode.attributeValue("name");
/* 329 */         String argVal = argNode.attributeValue("value");
/*     */ 
/* 331 */         if ((argNam == null) || (argVal == null))
/*     */           continue;
/* 333 */         argVal = HiArgUtils.getRealValue(argVal, ctx);
/*     */ 
/* 335 */         ctx.setPara(argNam, argVal);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 341 */     if (log.isDebugEnabled())
/*     */     {
/* 343 */       log.debug("ReadModuleCfg: 完成");
/*     */     }
/* 345 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int ReadCfgInfo(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     Document doc;
/* 362 */     HiMessage mess = ctx.getCurrentMsg();
/* 363 */     Logger log = HiLog.getLogger(mess);
/* 364 */     if (log.isDebugEnabled())
/*     */     {
/* 366 */       log.debug("开始执行原子组件: ReadCfgInfo ");
/*     */     }
/*     */ 
/* 369 */     String cfg = argsMap.get("Cfg");
/* 370 */     if (cfg == null)
/*     */     {
/* 372 */       cfg = "etc/SYSCFG.XML";
/*     */     }
/*     */ 
/* 375 */     String oprNodeNam = argsMap.get("Node");
/* 376 */     if (oprNodeNam == null)
/*     */     {
/* 378 */       oprNodeNam = "Public";
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 383 */       doc = HiXmlHelper.parser(cfg);
/*     */     }
/*     */     catch (Exception e) {
/* 386 */       throw new HiException("", e.getMessage());
/*     */     }
/*     */ 
/* 389 */     if (doc == null)
/*     */     {
/* 391 */       throw new HiException("", "加载指定配置文件 [" + cfg + "] 失败.");
/*     */     }
/*     */ 
/* 394 */     HiETF oprETF = mess.getETFBody().getChildNode(oprNodeNam);
/* 395 */     if (oprETF == null)
/*     */     {
/* 397 */       oprETF = mess.getETFBody().addNode(oprNodeNam);
/*     */     }
/*     */ 
/* 400 */     Element pubNode = doc.getRootElement().element(oprNodeNam);
/* 401 */     if (pubNode != null)
/*     */     {
/* 403 */       Iterator it = pubNode.elementIterator();
/*     */ 
/* 405 */       while (it.hasNext())
/*     */       {
/* 407 */         Element node = (Element)it.next();
/* 408 */         oprETF.setChildValue(node.getName(), node.getText());
/*     */       }
/*     */     }
/* 411 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int UpdateCfgInfo(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     Document doc;
/* 428 */     HiMessage mess = ctx.getCurrentMsg();
/* 429 */     Logger log = HiLog.getLogger(mess);
/* 430 */     if (log.isDebugEnabled())
/*     */     {
/* 432 */       log.debug("开始执行原子组件: UpdateCfgInfo ");
/*     */     }
/*     */ 
/* 435 */     String cfg = argsMap.get("Cfg");
/* 436 */     if (cfg == null)
/*     */     {
/* 438 */       cfg = "etc/SYSCFG.XML";
/*     */     }
/*     */ 
/* 441 */     String oprNodeNam = argsMap.get("Node");
/* 442 */     if (oprNodeNam == null)
/*     */     {
/* 444 */       oprNodeNam = "Public";
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 450 */       doc = HiXmlHelper.parser(cfg);
/*     */     }
/*     */     catch (Exception e) {
/* 453 */       throw new HiException("", e.getMessage());
/*     */     }
/*     */ 
/* 456 */     if (doc == null)
/*     */     {
/* 458 */       throw new HiException("", "加载指定配置文件 [" + cfg + "] 失败.");
/*     */     }
/*     */ 
/* 461 */     HiETF etfBody = mess.getETFBody();
/* 462 */     HiETF updETF = etfBody.getChildNode(oprNodeNam);
/* 463 */     List updList = updETF.getChildNodes();
/* 464 */     if (updList == null)
/*     */     {
/* 466 */       return 0;
/*     */     }
/*     */ 
/* 469 */     Element paraRoot = doc.getRootElement();
/* 470 */     Element pubNode = paraRoot.element(oprNodeNam);
/* 471 */     if (pubNode == null)
/*     */     {
/* 473 */       pubNode = paraRoot.addElement(oprNodeNam);
/*     */     }
/*     */ 
/* 477 */     for (int i = 0; i < updList.size(); ++i)
/*     */     {
/* 479 */       HiETF updItem = (HiETF)updList.get(i);
/* 480 */       HiXmlHelper.updateOrInsertChildNode(pubNode, updItem.getName(), updItem.getValue());
/*     */     }
/*     */ 
/* 483 */     HiXmlHelper.saveDoc(doc, HiICSProperty.getWorkDir() + "/" + cfg, "ISO-8859-1");
/* 484 */     updList.clear();
/* 485 */     return 0;
/*     */   }
/*     */ }