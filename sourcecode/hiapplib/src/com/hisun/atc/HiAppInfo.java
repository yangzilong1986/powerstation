 package com.hisun.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiStringUtils;
 import com.hisun.util.HiXmlHelper;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.dom4j.Document;
 import org.dom4j.Element;
 
 public class HiAppInfo
 {
   public static int GetAppInfo(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     if ((mess == null) || (ctx == null))
     {
       throw new HiException("211007", "前置系统错");
     }
     HiETF etfRoot = (HiETF)mess.getBody();
     if ((etfRoot == null) || (etfRoot.isNullNode()))
     {
       throw new HiException("220058", "ETF is NULL");
     }
     StringBuffer condition = new StringBuffer();
 
     String argVal = argsMap.get("CndSts");
 
     if (!(StringUtils.isEmpty(argVal)))
     {
       try
       {
         condition.append(HiDbtSqlHelper.getDynSentence(ctx, argVal));
       }
       catch (HiException e)
       {
         throw new HiException("220026", argVal);
       }
 
     }
 
     String aplCls = etfRoot.getChildValue("APP_CLS");
     String busTyp = etfRoot.getChildValue("BUS_TYP");
     String busSub = etfRoot.getChildValue("BUS_SUB");
 
     if ((aplCls == null) || (busTyp == null))
     {
       throw new HiException("220058", "没有键值![APP_CLS][BUS_TYP]");
     }
 
     if (busSub == null)
     {
       busSub = "";
     }
     condition = new StringBuffer(HiStringUtils.format(" APP_CLS = '%s' AND BUS_TYP = '%s' AND ( BUS_SUB = '%s' OR BUS_SUB = ' ' )", aplCls, busTyp, busSub));
 
     String brNo = etfRoot.getChildValue("Br_No");
     if (!(StringUtils.isEmpty(brNo)))
     {
       condition.insert(0, "' AND ");
       condition.insert(0, brNo);
       condition.insert(0, "SELECT * FROM PUBAPLMNG WHERE BR_NO = '");
     }
     else
     {
       condition.insert(0, "SELECT * FROM PUBAPLMNG WHERE ");
     }
 
     List list = null;
     try
     {
       if (log.isDebugEnabled())
       {
         log.debug("SQL is " + condition.toString());
       }
       list = ctx.getDataBaseUtil().execQuery(condition.toString());
 
       if ((list != null) && (list.size() == 1))
       {
         HashMap resMap = (HashMap)list.get(0);
         Iterator iter = resMap.keySet().iterator();
         while (iter.hasNext())
         {
           String key = (String)iter.next();
           String val = (String)resMap.get(key);
           if (!(StringUtils.isEmpty(val)))
           {
             etfRoot.setChildValue(key, val);
           }
         }
       } else {
         if (list.size() == 0)
         {
           return 2;
         }
 
         throw new HiException("220038");
       }
     }
     catch (HiException e)
     {
       throw new HiAppException(-1, "220038", "DB SYSTEM ERROR");
     }
 
     return 0;
   }
 
   public static int DeleteGroup(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     if ((argsMap == null) || (mess == null))
     {
       throw new HiException("220026", "[HiATLParam][HiMessage]");
     }
 
     String groupName = HiArgUtils.getStringNotNull(argsMap, "GroupName");
 
     String num = argsMap.get("RecordNum");
     HiETF etfRoot = mess.getETFBody();
     int count = 0;
     if (num == null)
     {
       List list = etfRoot.getGrandChildFuzzyEnd(groupName + "_");
       count = list.size();
     }
     else
     {
       if (!(NumberUtils.isNumber(num)))
       {
         throw new HiAppException(-1, "", "RecordNum");
       }
       count = NumberUtils.toInt(num);
     }
 
     for (int i = 1; i < count + 1; ++i)
     {
       String tmp = HiStringUtils.format("%s_%s", groupName, Integer.toString(i));
 
       etfRoot.removeChildNode(tmp);
     }
 
     return 0;
   }
 
   public static int ReadModuleCfg(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isDebugEnabled())
     {
       log.debug("开始执行原子组件: ReadModuleCfg ");
     }
 
     if ((argsMap == null) || (argsMap.size() == 0))
     {
       if (log.isDebugEnabled())
       {
         log.debug("失败: 参数为空");
       }
       throw new HiException("220026", "参数为空");
     }
 
     String paraFile = "CONFIGDECLARE.PARAFILE";
 
     String appName = HiArgUtils.getStringNotNull(argsMap, "Application");
     String tranCode = HiArgUtils.getStringNotNull(argsMap, "Transaction");
 
     if (log.isDebugEnabled())
     {
       log.debug("ReadModuleCfg 参数:Application [" + appName + "], Transaction[" + tranCode + "]");
     }
 
     Element paraRoot = (Element)ctx.getProperty(paraFile);
 
     if (paraRoot == null)
     {
       if (log.isDebugEnabled())
       {
         log.debug("失败: 根据键值ParaFile,从共享区获取失败");
       }
       throw new HiException("220026", "ReadModuleCfg : 根据键值ParaFile,从共享区获取失败");
     }
 
     Element appNode = HiXmlHelper.getNodeByAttr(paraRoot, "Application", "name", appName);
 
     if (appNode == null)
     {
       if (log.isDebugEnabled())
       {
         log.debug("失败: 取参数文件中的Applicatin节点失败");
       }
       throw new HiException("220026", "ReadModuleCfg : 根据键值ParaFile,从共享区获取失败");
     }
 
     for (int i = 0; i < 2; ++i)
     {
       Element tranNode;
       if (i == 0)
       {
         tranNode = appNode.element("Public");
       }
       else
       {
         tranNode = HiXmlHelper.getNodeByAttr(appNode, "Transaction", "code", tranCode);
       }
 
       if (tranNode == null) {
         continue;
       }
       Iterator it = tranNode.elementIterator("Arg");
       while (it.hasNext())
       {
         Element argNode = (Element)it.next();
         String argNam = argNode.attributeValue("name");
         String argVal = argNode.attributeValue("value");
 
         if ((argNam == null) || (argVal == null))
           continue;
         argVal = HiArgUtils.getRealValue(argVal, ctx);
 
         ctx.setPara(argNam, argVal);
       }
 
     }
 
     if (log.isDebugEnabled())
     {
       log.debug("ReadModuleCfg: 完成");
     }
     return 0;
   }
 
   public static int ReadCfgInfo(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     Document doc;
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isDebugEnabled())
     {
       log.debug("开始执行原子组件: ReadCfgInfo ");
     }
 
     String cfg = argsMap.get("Cfg");
     if (cfg == null)
     {
       cfg = "etc/SYSCFG.XML";
     }
 
     String oprNodeNam = argsMap.get("Node");
     if (oprNodeNam == null)
     {
       oprNodeNam = "Public";
     }
 
     try
     {
       doc = HiXmlHelper.parser(cfg);
     }
     catch (Exception e) {
       throw new HiException("", e.getMessage());
     }
 
     if (doc == null)
     {
       throw new HiException("", "加载指定配置文件 [" + cfg + "] 失败.");
     }
 
     HiETF oprETF = mess.getETFBody().getChildNode(oprNodeNam);
     if (oprETF == null)
     {
       oprETF = mess.getETFBody().addNode(oprNodeNam);
     }
 
     Element pubNode = doc.getRootElement().element(oprNodeNam);
     if (pubNode != null)
     {
       Iterator it = pubNode.elementIterator();
 
       while (it.hasNext())
       {
         Element node = (Element)it.next();
         oprETF.setChildValue(node.getName(), node.getText());
       }
     }
     return 0;
   }
 
   public static int UpdateCfgInfo(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     Document doc;
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isDebugEnabled())
     {
       log.debug("开始执行原子组件: UpdateCfgInfo ");
     }
 
     String cfg = argsMap.get("Cfg");
     if (cfg == null)
     {
       cfg = "etc/SYSCFG.XML";
     }
 
     String oprNodeNam = argsMap.get("Node");
     if (oprNodeNam == null)
     {
       oprNodeNam = "Public";
     }
 
     try
     {
       doc = HiXmlHelper.parser(cfg);
     }
     catch (Exception e) {
       throw new HiException("", e.getMessage());
     }
 
     if (doc == null)
     {
       throw new HiException("", "加载指定配置文件 [" + cfg + "] 失败.");
     }
 
     HiETF etfBody = mess.getETFBody();
     HiETF updETF = etfBody.getChildNode(oprNodeNam);
     List updList = updETF.getChildNodes();
     if (updList == null)
     {
       return 0;
     }
 
     Element paraRoot = doc.getRootElement();
     Element pubNode = paraRoot.element(oprNodeNam);
     if (pubNode == null)
     {
       pubNode = paraRoot.addElement(oprNodeNam);
     }
 
     for (int i = 0; i < updList.size(); ++i)
     {
       HiETF updItem = (HiETF)updList.get(i);
       HiXmlHelper.updateOrInsertChildNode(pubNode, updItem.getName(), updItem.getValue());
     }
 
     HiXmlHelper.saveDoc(doc, HiICSProperty.getWorkDir() + "/" + cfg, "ISO-8859-1");
     updList.clear();
     return 0;
   }
 }