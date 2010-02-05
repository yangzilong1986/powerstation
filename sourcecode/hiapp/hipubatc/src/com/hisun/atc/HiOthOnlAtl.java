 package com.hisun.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiFTP;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiStringManager;
 import java.io.File;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 
 public class HiOthOnlAtl
 {
   private static HiStringManager sm = HiStringManager.getManager();
 
   public int FtpPut(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HashMap ftpInfo = null;
     if (argsMap.size() == 1)
     {
       if (argsMap.contains("FtpId"))
       {
         String strFtpID = argsMap.get("FtpId");
         ftpInfo = GetFtpConfigFromDB(ctx, strFtpID);
       }
       else
       {
         String strFtpCfg = argsMap.get("FtpCfg");
         ftpInfo = GetFtpConfigFromFile(ctx, strFtpCfg);
       }
 
     }
     else {
       ftpInfo = argsMap.toMap();
     }
 
     HiFTP ftp = new HiFTP();
 
     ftp.setServer((String)ftpInfo.get("IPADR"));
     if (StringUtils.isEmpty(ftp.getServer()))
     {
       throw new HiException("220005", "IPADR");
     }
     ftp.setRemoteDir((String)ftpInfo.get("OBJDIR"));
     if (StringUtils.isEmpty(ftp.getRemoteDir()))
     {
       throw new HiException("220005", "OBJDIR");
     }
     String strLclDir = (String)ftpInfo.get("LCLDIR");
     if (StringUtils.isEmpty(strLclDir))
     {
       throw new HiException("220005", "LCLDIR");
     }
     if (!(strLclDir.startsWith(File.separator)))
       ftp.setLocalDir(HiICSProperty.getWorkDir() + File.separator + strLclDir);
     else {
       ftp.setLocalDir(strLclDir);
     }
     ftp.setUsername((String)ftpInfo.get("USRNAM"));
     if (StringUtils.isEmpty(ftp.getUsername()))
     {
       throw new HiException("220005", "USRNAM");
     }
 
     ftp.setPassword((String)ftpInfo.get("USRPWD"));
     if (StringUtils.isEmpty(ftp.getPassword()))
     {
       throw new HiException("220005", "USRPWD");
     }
     ftp.setLocalFile((String)ftpInfo.get("LCLFIL"));
     if (StringUtils.isEmpty(ftp.getLocalFile()))
     {
       throw new HiException("220005", "LCLFIL");
     }
 
     ftp.setRemoteFile((String)ftpInfo.get("OBJFIL"));
     ftp.setParam((String)ftpInfo.get("PARAM"));
     ftp.setBinaryTransfer(true);
 
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiOthOnlAtl.FtpPut.Param", new String[] { ftp.getServer(), ftp.getUsername(), ftp.getPassword(), ftp.getRemoteDir(), ftp.getLocalDir(), ftp.getLocalFile(), ftp.getRemoteFile() }));
     }
 
     ftp.put();
     return 0;
   }
 
   public int HiFtpPut(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     return 0;
   }
 
   public static int HiFtpOp(HiATLParam argsMap, HiMessageContext ctx, String op)
     throws HiException
   {
     HashMap ftpInfo = null;
     if (argsMap.size() == 1)
     {
       if (argsMap.contains("FtpId"))
       {
         String strFtpID = argsMap.get("FtpId");
         ftpInfo = GetFtpConfigFromDB(ctx, strFtpID);
       }
       else
       {
         String strFtpCfg = argsMap.get("FtpCfg");
         ftpInfo = GetFtpConfigFromFile(ctx, strFtpCfg);
       }
 
     }
     else {
       ftpInfo = argsMap.toMap();
     }
     String strIpAdr = (String)ftpInfo.get("IPADR");
     if (StringUtils.isEmpty(strIpAdr))
     {
       throw new HiException("220005", "IPADR", strIpAdr);
     }
 
     String strObjDir = (String)ftpInfo.get("OBJDIR");
     if (StringUtils.isEmpty(strObjDir))
     {
       throw new HiException("220005", "OBJDIR", strObjDir);
     }
 
     String strLclDir = (String)ftpInfo.get("LCLDIR");
     if (StringUtils.isEmpty(strLclDir))
     {
       throw new HiException("220005", "LCLDIR", strLclDir);
     }
 
     String Port = (String)ftpInfo.get("PROT");
     if (StringUtils.isEmpty(Port))
     {
       throw new HiException("220005", "Port", Port);
     }
     String Mode = "bin";
     if (ftpInfo.containsKey("MODE")) {
       Mode = (String)ftpInfo.get("MODE");
     }
     String strObjFile = (String)ftpInfo.get("OBJFIL");
     if (StringUtils.isEmpty(strObjFile))
     {
       throw new HiException("220005", "OBJFIL", strObjFile);
     }
 
     String strLclFile = (String)ftpInfo.get("LCLFIL");
     if (StringUtils.isEmpty(strLclFile))
     {
       throw new HiException("220005", "LCLFIL", strLclFile);
     }
 
     String fullFile = HiArgUtils.absoutePath(strLclDir + File.separator + strLclFile);
 
     return 0;
   }
 
   public static HashMap GetFtpConfigFromArgs(HiMessageContext ctx, String strFtpID)
     throws HiException
   {
     HashMap ftpInfo = null;
 
     return ftpInfo;
   }
 
   private static HashMap GetFtpConfigFromDB(HiMessageContext ctx, String strFtpID)
     throws HiException
   {
     HashMap ftpInfo = null;
     String strBrNo = ctx.getCurrentMsg().getETFBody().getChildValue("BR_NO");
 
     String strSQL = "SELECT IP_ADR,USR_NAM,USR_PWD,OBJ_DIR,OBJ_FIL, LCL_DIR,LCL_FIL,FTP_MOD,Port FROM \tpubftpcfg WHERE BR_NO='" + strBrNo + "' AND FTP_ID='" + strFtpID + "'";
     try
     {
       List list = ctx.getDataBaseUtil().execQuery(strSQL);
 
       if ((list == null) || (list.size() == 0))
       {
         throw new HiException("220001", strBrNo, strFtpID);
       }
 
       ftpInfo = (HashMap)list.get(0);
     }
     catch (HiException e)
     {
       e.addMsgStack("220001", strBrNo, strFtpID);
       throw e;
     }
 
     return ftpInfo;
   }
 
   private static HashMap GetFtpConfigFromFile(HiMessageContext ctx, String strFtpCfg)
     throws HiException
   {
     HashMap ftpInfo = new HashMap();
 
     Element rootNode = (Element)ctx.getProperty("CONFIGDECLARE", strFtpCfg);
 
     Iterator list = rootNode.elementIterator();
     while (list.hasNext())
     {
       Element item = (Element)list.next();
       String strNodeName = item.getName();
       String strValue = item.getText();
       if ((strValue != null) && (strValue.startsWith("$")) && (strValue.length() > 1))
       {
         strValue = strValue.substring(1);
         strValue = ctx.getETFValue(ctx.getCurrentMsg().getETFBody(), strValue);
       }
       ftpInfo.put(strNodeName.toUpperCase(), strValue);
     }
 
     return ftpInfo;
   }
 
   public int FtpGet(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HashMap ftpInfo = null;
     if (argsMap.size() == 1)
     {
       if (argsMap.contains("FtpId"))
       {
         String strFtpID = argsMap.get("FtpId");
         ftpInfo = GetFtpConfigFromDB(ctx, strFtpID);
       }
       else
       {
         String strFtpCfg = argsMap.get("FtpCfg");
         ftpInfo = GetFtpConfigFromFile(ctx, strFtpCfg);
       }
 
     }
     else {
       ftpInfo = argsMap.toMap();
     }
     HiFTP ftp = new HiFTP();
 
     ftp.setServer((String)ftpInfo.get("IPADR"));
     if (StringUtils.isEmpty(ftp.getServer()))
     {
       throw new HiException("220005", "IPADR");
     }
     ftp.setRemoteDir((String)ftpInfo.get("OBJDIR"));
     if (StringUtils.isEmpty(ftp.getRemoteDir()))
     {
       throw new HiException("220005", "OBJDIR");
     }
     String strLclDir = (String)ftpInfo.get("LCLDIR");
     if (StringUtils.isEmpty(strLclDir))
     {
       throw new HiException("220005", "LCLDIR");
     }
     if (!(strLclDir.startsWith(File.separator)))
       ftp.setLocalDir(HiICSProperty.getWorkDir() + File.separator + strLclDir);
     else {
       ftp.setLocalDir(strLclDir);
     }
 
     ftp.setUsername((String)ftpInfo.get("USRNAM"));
     if (StringUtils.isEmpty(ftp.getUsername()))
     {
       throw new HiException("220005", "USRNAM");
     }
     ftp.setPassword((String)ftpInfo.get("USRPWD"));
     if (StringUtils.isEmpty(ftp.getPassword()))
     {
       throw new HiException("220005", "USRPWD");
     }
     ftp.setRemoteFile((String)ftpInfo.get("OBJFIL"));
 
     if (StringUtils.isEmpty(ftp.getRemoteFile()))
     {
       throw new HiException("220005", "OBJFIL");
     }
     ftp.setLocalFile((String)ftpInfo.get("LCLFIL"));
 
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiOthOnlAtl.FtpPut.Param", new String[] { ftp.getServer(), ftp.getUsername(), ftp.getPassword(), ftp.getRemoteDir(), ftp.getLocalDir(), ftp.getLocalFile(), ftp.getRemoteFile() }));
     }
 
     ftp.setParam((String)ftpInfo.get("PARAM"));
     ftp.setBinaryTransfer(true);
 
     ftp.get();
     return 0;
   }
 
   public int HiFtpGet(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     throw new HiException("not implement");
   }
 }