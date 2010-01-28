 package com.hisun.atc;
 
 import com.hisun.atc.common.HiAtcLib;
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
 import com.hisun.util.HiStringUtils;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 
 public class HiAgentAgr
 {
   public int InsertAgentAgreement(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiAtcLib.insertTable("PUBCRPAGT", ctx);
 
     return 0;
   }
 
   public int GetAgentAgreement(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if ((mess == null) || (ctx == null)) {
       throw new HiException("220026", "[HiMessage][HiMessageContext] IS NULL");
     }
     HiETF etfRoot = (HiETF)mess.getBody();
     StringBuffer sqlcmd = new StringBuffer();
 
     String argVal = argsMap.get("CndSts");
 
     if (!(StringUtils.isEmpty(argVal)))
     {
       try
       {
         String CndSts = HiDbtSqlHelper.getDynSentence(ctx, argVal);
 
         sqlcmd = new StringBuffer("SELECT * FROM PUBCRPAGT WHERE " + CndSts);
       }
       catch (HiException e) {
         throw e;
       }
     }
 
     String busTyp = etfRoot.getChildValue("BUSTYP");
     String crpCod = etfRoot.getChildValue("CRPCOD");
     String brNo = etfRoot.getChildValue("BRNO");
     String crpSub = etfRoot.getChildValue("CRPSUB");
 
     if ((busTyp == null) || (crpCod == null) || (brNo == null)) {
       log.error("没有键值![BUSTYP][CRPCOD][BRNO]");
       throw new HiException("220058", "[BUSTYP][CRPCOD][BRNO]");
     }
     if (crpSub == null) {
       crpSub = "";
     }
 
     sqlcmd = new StringBuffer(HiStringUtils.format("SELECT * FROM PUBCRPAGT WHERE BRNO='%s' AND BUSTYP='%s' AND CRPCOD='%s' AND (CRPSUB='%s' OR CRPSUB=' ')", brNo, busTyp, crpCod, crpSub));
     try
     {
       List list = ctx.getDataBaseUtil().execQuery(sqlcmd.toString());
       if ((list != null) && (list.size() == 1)) {
         HashMap resMap = (HashMap)list.get(0);
         Iterator iter = resMap.keySet().iterator();
         while (iter.hasNext()) {
           String key = (String)iter.next();
           String val = (String)resMap.get(key);
           if (!(StringUtils.isEmpty(val)))
             etfRoot.setChildValue(key, val);
         }
       }
       else {
         if (list.size() == 0) {
           throw new HiAppException(-100, "220040", "GetAgentAgreement,数据库无此记录");
         }
 
         throw new HiException("220039", "GetAgentAgreement,数据库操作错误");
       }
     }
     catch (HiException e) {
       throw e;
     }
 
     return 0;
   }
 
   public int UpdateAgentAgreement(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     if ((mess == null) || (ctx == null)) {
       throw new HiException("220026", "[HiMessage][HiMessageContext]IS NULL");
     }
 
     StringBuffer sqlcmd = new StringBuffer();
     HiETF etfRoot = (HiETF)mess.getBody();
     if (etfRoot == null) {
       throw new HiException("220026", "ETF is NULL");
     }
     String argVal = argsMap.get("CndSts");
 
     if (StringUtils.isEmpty(argVal)) {
       String busTyp = etfRoot.getChildValue("BUSTYP");
       String crpCod = etfRoot.getChildValue("CRPCOD");
       String brNo = etfRoot.getChildValue("BRNO");
       String crpSub = etfRoot.getChildValue("CRPSUB");
 
       if ((busTyp == null) || (crpCod == null) || (brNo == null)) {
         throw new HiException("220026", "没有键值![BUSTYP][CRPCOD][BRNO]");
       }
       if (crpSub == null) {
         crpSub = "";
       }
 
       sqlcmd = new StringBuffer(HiStringUtils.format("BRNO='%s' AND BUSTYP='%s' AND CRPCOD='%s' AND (CRPSUB='%s' OR CRPSUB=' ')", brNo, busTyp, crpCod, crpSub));
     }
     else
     {
       try
       {
         String CndSts = HiDbtSqlHelper.getDynSentence(ctx, argVal);
 
         sqlcmd = new StringBuffer(CndSts);
       }
       catch (HiException e) {
         throw e;
       }
 
     }
 
     String sql = HiAtcLib.updateFormat("PUBCRPAGT", sqlcmd.toString(), etfRoot, ctx);
     try {
       int row = ctx.getDataBaseUtil().execUpdate(sql);
       if (row == 1) {
         return 0;
       }
 
       throw new HiException("220042", "UpdateAgentAgreement: 更新数据库pubcrpact失败");
     }
     catch (HiException e)
     {
       throw e;
     }
   }
 
   public int CheckAgentAgreement(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     if ((mess == null) || (ctx == null)) {
       throw new HiException("220026", "[HiMessage][HiMessageContext]Is NULL");
     }
 
     HiETF etfRoot = (HiETF)mess.getBody();
 
     String busTyp = etfRoot.getChildValue("BUSTYP");
     String crpCod = etfRoot.getChildValue("CRPCOD");
     String brNo = etfRoot.getChildValue("BRNO");
     String crpSub = etfRoot.getChildValue("CRPSUB");
 
     if ((busTyp == null) || (crpCod == null) || (brNo == null))
     {
       throw new HiException("220058", "ETF Has Not [BUSTYP][CRPCOD][BRNO]");
     }
     if (crpSub == null) {
       crpSub = " ";
     }
 
     String sqlcmd = HiStringUtils.format("SELECT SVRSTS FROM PUBCRPAGT WHERE BRNO='%s' And BUSTYP='%s' And CRPCOD='%s' And CRPSUB='%s'", brNo, busTyp, crpCod, crpSub);
 
     List list = null;
     try {
       list = ctx.getDataBaseUtil().execQuery(sqlcmd);
       if ((list != null) && (list.size() > 0)) {
         HashMap svrsts = (HashMap)list.get(0);
         if (!(svrsts.get("SVRSTS").equals("1")))
           throw new HiAppException(-3, "215027", "代理服务已经关闭");
       }
       else {
         if (list.size() == 0) {
           throw new HiAppException(-2, "215027", "代理服务未找到");
         }
 
         throw new HiException("215027", "前置系统错误");
       }
     }
     catch (HiException e) {
       throw e;
     }
 
     return 0;
   }
 }