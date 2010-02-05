 package com.hisun.mng;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.parser.svrlst.HiFrontTabNode;
 import com.hisun.parser.svrlst.HiGroupNode;
 import com.hisun.parser.svrlst.HiSVRLSTParser;
 import com.hisun.parser.svrlst.HiServerNode;
 import com.hisun.register.HiRegisterService;
 import com.hisun.register.HiServiceObject;
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.LinkedHashSet;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 public class HiMonitor extends HiMngUtils
 {
   public static Logger log = HiLog.getLogger("mon.trc");
 
   private static final String[][] NODE_TABLE = { { "d_code", "STC" }, { "TCODE", "STC" }, { "t_list", "LogNo" }, { "a_code", "ActNo" }, { "t_tlr", "TlrId" }, { "t_amt", "TxnAmt" }, { "t_rlt", "RSP_CD" }, { "t_chnl", "ChnlId" }, { "t_node", "NodNo" } };
 
   private static final boolean[] fromHead = { true, true, false, false, false, false, false, false, false };
 
   public int StartService(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     Logger log = HiLog.getLogger(msg);
 
     String on_off = HiArgUtils.getStringNotNull(root, "ON_OFF");
     if ((!(StringUtils.equals(on_off, "1"))) && (!(StringUtils.equals(on_off, "0"))))
     {
       throw new HiAppException(-1, "220026", "ON_OFF");
     }
 
     List ls = root.getChildNodes("TCODE");
     for (int i = 0; i < ls.size(); ++i) {
       HiETF node = (HiETF)ls.get(i);
       String code = node.getValue();
       if (StringUtils.isEmpty(code)) {
         throw new HiAppException(-1, "220026", "TCODE");
       }
       if (StringUtils.equals(on_off, "1")) {
         HiRegisterService.start(code);
         if (log.isDebugEnabled())
           log.debug("启动服务:[" + code + "]成功");
       }
       else {
         HiRegisterService.stop(code);
         if (log.isDebugEnabled()) {
           log.debug("停止服务:[" + code + "]成功");
         }
       }
     }
     return 0;
   }
 
   public int GetServiceStatus(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     String code = HiArgUtils.getStringNotNull(argsMap, "code");
     HiETF root = msg.getETFBody();
     String curNodeName = argsMap.get("curNode");
     if (curNodeName == null)
       curNodeName = "ROOT";
     try
     {
       HiServiceObject service = HiRegisterService.getService(code);
       root.setGrandChildNode(curNodeName + ".Type", service.getServerType());
 
       root.setGrandChildNode(curNodeName + ".Log_LVL", service.getLogLevel());
 
       root.setGrandChildNode(curNodeName + ".Running", service.getRunning());
 
       root.setGrandChildNode(curNodeName + ".Mon_SWT", service.getMonSwitch());
     }
     catch (HiException e)
     {
     }
     return 0;
   }
 
   public int GetServerList(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiSVRLSTParser parser = new HiSVRLSTParser();
     String file = argsMap.get("file");
     if (StringUtils.isBlank(file)) {
       file = "etc/SVRLST_PUB.XML";
     }
     String groupName = argsMap.get("GrpNam");
     if (StringUtils.isBlank(groupName)) {
       groupName = "GRP";
     }
     int idx = 0;
     HiETF root = ctx.getCurrentMsg().getETFBody();
     try {
       HiFrontTabNode frontTabNode = parser.parser();
       for (int i = 0; i < frontTabNode.size(); ++i) {
         HiGroupNode groupNode = frontTabNode.getGroup(i);
         for (int j = 0; j < groupNode.size(); ++j) {
           ++idx;
           HiETF grp = root.addNode(groupName + "_" + idx);
           HiServerNode server = groupNode.getServer(j);
           grp.setChildValue("name", server.getName());
           grp.setChildValue("desc", server.getDesc());
         }
       }
     } catch (Exception e) {
       throw HiException.makeException(e);
     }
 
     return 0;
   }
 
   public int GetServerStatus(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     String code = HiArgUtils.getStringNotNull(argsMap, "code");
     HiETF root = msg.getETFBody();
     String curNodeName = argsMap.get("curNode");
     if (curNodeName == null)
       curNodeName = "ROOT";
     Logger log = HiLog.getLogger(msg);
     if (log.isInfoEnabled())
       log.info("code:[" + code + "]:[" + curNodeName + "]");
     try
     {
       HiServiceObject service = HiRegisterService.getService(code);
       root.setGrandChildNode(curNodeName + ".Type", service.getServerType());
 
       root.setGrandChildNode(curNodeName + ".Log_LVL", service.getLogLevel());
 
       root.setGrandChildNode(curNodeName + ".Running", service.getRunning());
 
       root.setGrandChildNode(curNodeName + ".Mon_Swt", service.getMonSwitch());
     }
     catch (HiException e)
     {
     }
     return 0;
   }
 
   public int SetServiceLogSwitch(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     String on_off = HiArgUtils.getStringNotNull(root, "ON_OFF");
     if ((!(StringUtils.equals(on_off, "1"))) && (!(StringUtils.equals(on_off, "0"))))
     {
       throw new HiAppException(-1, "220026", "ON_OFF");
     }
 
     List ls = root.getChildNodes("TCODE");
     for (int i = 0; i < ls.size(); ++i) {
       HiETF node = (HiETF)ls.get(i);
       String code = node.getValue();
       if (StringUtils.isEmpty(code)) {
         throw new HiAppException(-1, "220026", "TCODE");
       }
       HiRegisterService.setLogSwitch(code, on_off);
     }
     return 0;
   }
 
   public int StartServer(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     Logger log = HiLog.getLogger(msg);
 
     String on_off = HiArgUtils.getStringNotNull(root, "ON_OFF");
     if ((!(StringUtils.equals(on_off, "1"))) && (!(StringUtils.equals(on_off, "0"))))
     {
       throw new HiAppException(-1, "220026", "ON_OFF");
     }
 
     List ls = root.getChildNodes("TCODE");
     Runtime run = Runtime.getRuntime();
 
     for (int i = 0; i < ls.size(); ++i) {
       HiETF node = (HiETF)ls.get(i);
       String code = node.getValue();
       if (StringUtils.isEmpty(code)) {
         throw new HiAppException(-1, "220026", "TCODE");
       }
       if (StringUtils.equals(on_off, "1"))
       {
         try {
           Process process = run.exec("hiboot -s " + code);
           process.waitFor();
         } catch (Exception e) {
           log.error("startServer Exception");
           log.error(e);
         }
 
         if (log.isDebugEnabled())
           log.debug("启动服务:[" + code + "]成功");
       }
       else {
         HiRegisterService.stop(code);
         if (log.isDebugEnabled()) {
           log.debug("停止服务:[" + code + "]成功");
         }
       }
     }
     return 0;
   }
 
   public int SetServerLogSwitch(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     Logger log = HiLog.getLogger(msg);
 
     String on_off = HiArgUtils.getStringNotNull(root, "ON_OFF");
     if ((!(StringUtils.equals(on_off, "1"))) && (!(StringUtils.equals(on_off, "0"))))
     {
       throw new HiAppException(-1, "220026", "ON_OFF");
     }
 
     List ls = root.getChildNodes("TCODE");
     for (int i = 0; i < ls.size(); ++i) {
       HiETF node = (HiETF)ls.get(i);
       String code = node.getValue();
       if (StringUtils.isEmpty(code)) {
         throw new HiAppException(-1, "220026", "TCODE");
       }
       if (StringUtils.equals(on_off, "1")) {
         HiRegisterService.start(code);
         if (log.isDebugEnabled())
           log.debug("启动服务:[" + code + "]成功");
       }
       else {
         HiRegisterService.stop(code);
         if (log.isDebugEnabled()) {
           log.debug("停止服务:[" + code + "]成功");
         }
       }
     }
     return 0;
   }
 
   public int MonRegiste(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etf = msg.getETFBody();
 
     Logger log = HiLog.getLogger(msg);
 
     String on_off = etf.getChildValue("ON_OFF");
     if (StringUtils.isEmpty(on_off)) {
       createErrReturn(etf, "ERR804", "接收数据不合法－获得监控开关失败!");
       return -1;
     }
 
     String uid = etf.getChildValue("UID");
     if (StringUtils.isEmpty(uid)) {
       createErrReturn(etf, "ERR805", "接收数据不合法－获得目标uid失败。");
       return 0;
     }
     log.info("uid:[" + uid + "]");
 
     if (regTranMonSwitch(etf, on_off, uid) != 0) {
       createErrReturn(etf, "ERR808", "监控请求失败。");
       return 0;
     }
 
     etf.addNode("RSP_CD", "000000");
 
     String retmsg = (on_off.equals("1")) ? "监控启动成功。" : "监控停止成功。";
     etf.addNode("RSP_MSG", retmsg);
 
     return 0;
   }
 
   private int regTranMonSwitch(HiETF etf, String on_off, String uid) throws HiException
   {
     Map MON_MAP = getMonMap();
     boolean bb = on_off.equals("1");
     log.info("regTranMonSwitch");
 
     List ls = etf.getChildNodes("TCODE");
     for (int i = 0; i < ls.size(); ++i) {
       HiETF node = (HiETF)ls.get(i);
       String code = node.getValue();
       if (StringUtils.isNotBlank(code))
       {
         LinkedHashSet uids;
         if (log.isInfoEnabled()) {
           log.info("启动监控:[" + code + "]:[" + uid + "]");
         }
         if (bb) {
           if (MON_MAP.containsKey(code)) {
             uids = (LinkedHashSet)MON_MAP.get(code);
             uids.add(uid);
           } else {
             uids = new LinkedHashSet();
             uids.add(uid);
             MON_MAP.put(code, uids);
             HiRegisterService.setMonSwitch(code, bb);
           }
 
         }
         else
         {
           if (log.isInfoEnabled()) {
             log.info("关闭监控:[" + code + "]:[" + uid + "]");
           }
           if (MON_MAP.containsKey(code)) {
             uids = (LinkedHashSet)MON_MAP.get(code);
             uids.remove(uid);
             if (uids.isEmpty()) {
               MON_MAP.remove(code);
               HiRegisterService.setMonSwitch(code, bb);
             }
           }
         }
 
         if (bb)
           etf.addNode("code", code);
       }
     }
     if (log.isInfoEnabled()) {
       log.info("regTranMonSwitch:[ " + MON_MAP + "]");
     }
 
     return 0;
   }
 
   public int MonCloseMonitor(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     Map MON_MAP = getMonMap();
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etf = msg.getETFBody();
 
     Logger log = HiLog.getLogger(msg);
 
     String closeall = etf.getChildValue("CLOSEALL");
     if (!(StringUtils.isEmpty(closeall)))
     {
       if (log.isInfoEnabled())
         log.info("关闭所有监控！！！");
       closeAllMonitor(MON_MAP);
       return 0;
     }
 
     List ls = etf.getChildNodes("REMOVE_UID");
     for (int i = 0; i < ls.size(); ++i) {
       HiETF node = (HiETF)ls.get(i);
       String uid = node.getValue();
 
       if (log.isInfoEnabled())
         log.info("关闭用户监控：" + uid);
       closeMonitor(MON_MAP, uid);
     }
     return 0;
   }
 
   public static void closeMonitor(Map MON_MAP, String uid) {
     Iterator it = MON_MAP.entrySet().iterator();
     log.info("closeMonitor:[" + MON_MAP + "]");
 
     while (it.hasNext()) {
       Map.Entry entry = (Map.Entry)it.next();
       String code = (String)entry.getKey();
       LinkedHashSet uids = (LinkedHashSet)entry.getValue();
       log.info("closeMonitor:[" + code + "]:[" + uid + "]");
 
       uids.remove(uid);
       if (uids.isEmpty()) {
         log.info("closeMonitor:[" + uids + "]");
         it.remove();
         try {
           HiRegisterService.setMonSwitch(code, false);
         }
         catch (HiException e) {
         }
       }
     }
   }
 
   public static void closeAllMonitor(Map MON_MAP) {
     Iterator it = MON_MAP.entrySet().iterator();
     log.info("closeAllMonitor01:[" + MON_MAP + "]");
     while (it.hasNext()) {
       Map.Entry entry = (Map.Entry)it.next();
       String code = (String)entry.getKey();
       log.info("closeAllMonitor01:[" + code + "]");
       LinkedHashSet uids = (LinkedHashSet)entry.getValue();
 
       it.remove();
       try {
         HiRegisterService.setMonSwitch(code, false);
       }
       catch (HiException e)
       {
       }
     }
   }
 
   public int MonSwitch(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etf = msg.getETFBody();
 
     Logger log = HiLog.getLogger(msg);
 
     String on_off = argsMap.get("ON_OFF");
     if (log.isDebugEnabled()) {
       log.debug("on_off:" + on_off);
     }
 
     if (doTranMonSwitch(etf, on_off, log) != 0) {
       log.error("ERR808");
       createErrReturn(etf, "ERR808", "监控请求失败。");
       return 0;
     }
 
     etf.addNode("RSP_CD", "000000");
     String retmsg = (on_off.equals("1")) ? "监控启动成功。" : "监控停止成功。";
     etf.addNode("RSP_MSG", retmsg);
     return 0;
   }
 
   private int doTranMonSwitch(HiETF etf, String on_off, Logger log)
   {
     boolean bb = on_off.equals("1");
 
     List ls = etf.getChildNodes("TCODE");
     for (int i = 0; i < ls.size(); ++i) {
       HiETF node = (HiETF)ls.get(i);
       String code = node.getValue();
       if (!(StringUtils.isNotBlank(code))) continue;
       try {
         HiRegisterService.setMonSwitch(code, bb);
       } catch (HiException e) {
         log.error("操作交易开关失败:" + e.toString(), e);
         return -1;
       }
 
     }
 
     return 0;
   }
 
   public int MonDispatch(HiATLParam argsMap, HiMessageContext ctx) throws HiException
   {
     Map MON_MAP = getMonMap();
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etf = msg.getETFBody();
 
     msg.delHeadItemVal("STC");
 
     String code = etf.getChildValue("MonCod");
     if (log.isInfoEnabled()) {
       log.info("MonDispatch:" + MON_MAP + ";" + code);
     }
 
     if (MON_MAP.containsKey(code))
     {
       LinkedHashSet uids = (LinkedHashSet)MON_MAP.get(code);
       for (Iterator i = uids.iterator(); i.hasNext(); ) {
         String uid = (String)i.next();
         etf.addNode("UID", uid);
       }
 
       return 0;
     }
 
     return -1;
   }
 
   private void buildMonMsg(HiMessage msg, Logger log)
   {
     HiETF etf = msg.getETFBody();
 
     StringBuffer sf = new StringBuffer("<root>");
 
     for (int i = 0; i < NODE_TABLE.length; ++i)
     {
       String value;
       if (fromHead[i] != 0)
         value = msg.getHeadItem(NODE_TABLE[i][1]);
       else {
         value = etf.getChildValue(NODE_TABLE[i][1]);
       }
       if (value == null) {
         value = "";
       }
       String node = NODE_TABLE[i][0];
       sf.append("<").append(node).append(">").append(value).append("</").append(node).append(">");
     }
 
     sf.append("</root>");
     try {
       Element xmlContent = DocumentHelper.parseText(sf.toString()).getRootElement();
 
       if (log.isDebugEnabled()) {
         log.debug(xmlContent.asXML());
       }
       msg.setHeadItem("MNGXML", xmlContent);
     } catch (DocumentException e) {
       log.error(e, e);
     }
   }
 
   public static Map getMonMap() {
     HiContext rootCtx = HiContext.getRootContext();
     Map monmap = (Map)rootCtx.getProperty("MON_MAP");
     if (monmap == null) {
       monmap = Collections.synchronizedMap(new HashMap());
       rootCtx.setProperty("MON_MAP", monmap);
       if (log.isDebugEnabled())
         log.debug("create monitor map");
     }
     return monmap;
   }
 }