 package com.hisun.atc.common;
 
 import com.hisun.atc.fil.HiRoot;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.message.HiMessageHelper;
 import com.hisun.parse.HiCfgFile;
 import com.hisun.parse.HiResourceRule;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiICSProperty;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.FileReader;
 import java.io.IOException;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.StringTokenizer;
 import org.apache.commons.lang.StringUtils;
 
 public class HiMultiQueryLib
 {
   private static final int MAXRECORD = 1000;
   private static final int RecNumPerPage = 19;
   private static final String MULT_FOLDER = "MULTIDATDIR";
   private static String MULT_FOLDER_TEST = null;
 
   public static int queryFirst(HiATLParam argsMap, HiMessage mess, HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(mess);
 
     String strValue = "MULTIQUERY";
     if (argsMap.contains("SqlCmd")) {
       strValue = argsMap.get("SqlCmd");
     }
     int iMaxLinePerPage = 19;
     if (argsMap.contains("RecNumPerPage")) {
       iMaxLinePerPage = Integer.valueOf(argsMap.get("RecNumPerPage")).intValue();
     }
     String strFileKey = getMultiQueryKey(argsMap, mess);
     File file = null;
     try {
       file = getMultFile(strFileKey);
       if (log.isDebugEnabled()) {
         log.debug(file.getPath());
       }
       File parentFile = file.getParentFile();
       if (!(parentFile.exists())) {
         if (log.isDebugEnabled()) {
           log.debug(parentFile.getPath());
         }
         parentFile.mkdirs();
       }
       file.createNewFile();
     } catch (IOException e) {
       throw new HiException(e);
     }
 
     String strSQL = HiDbtSqlHelper.getDynSentence(ctx, strValue);
 
     List list = HiDbtSqlHelper.execMultiQuery(file, strSQL, 1000, ctx, iMaxLinePerPage);
 
     if ((list == null) || (list.size() == 0)) {
       return 2;
     }
     HiETF etf = mess.getETFBody();
 
     if (list.size() > iMaxLinePerPage) {
       etf.addNode("REC_NUM", String.valueOf(iMaxLinePerPage));
     }
     else
     {
       etf.addNode("REC_NUM", String.valueOf(list.size()));
     }
 
     savePageNo(mess, "0", list.size(), strFileKey, ctx, iMaxLinePerPage);
     return 0;
   }
 
   public static int QueryFirstExt(HiATLParam argsMap, HiMessage mess, HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(mess);
     if ((argsMap == null) || (argsMap.size() == 0)) {
       throw new HiException("220026", "参数为空");
     }
     String aSqlName = argsMap.get("CndSts");
     String tblnam = argsMap.get("TblNam").toUpperCase();
 
     String cndsts = HiDbtSqlHelper.getDynSentence(ctx, aSqlName);
     String sqlcmd = "SELECT * FROM " + tblnam + " WHERE " + cndsts;
 
     int iMaxLinePerPage = 19;
     if (argsMap.contains("RecNumPerPage")) {
       iMaxLinePerPage = Integer.valueOf(argsMap.get("RecNumPerPage")).intValue();
     }
 
     String strFileKey = getMultiQueryKey(argsMap, mess);
     File file = null;
     try {
       file = getMultFile(strFileKey);
       if (log.isDebugEnabled()) {
         log.debug(file.getPath());
       }
       File parentFile = file.getParentFile();
       if (!(parentFile.exists())) {
         if (log.isDebugEnabled()) {
           log.debug(parentFile.getPath());
         }
         parentFile.mkdirs();
       }
       file.createNewFile();
     } catch (IOException e) {
       throw new HiException(e);
     }
 
     List list = HiDbtSqlHelper.execMultiQueryExt(tblnam, file, sqlcmd, iMaxLinePerPage, ctx, iMaxLinePerPage);
 
     if ((list == null) || (list.size() == 0)) {
       return 2;
     }
     HiETF etf = mess.getETFBody();
 
     if (list.size() > iMaxLinePerPage) {
       etf.addNode("REC_NUM", String.valueOf(iMaxLinePerPage));
     }
     else
     {
       etf.addNode("REC_NUM", String.valueOf(list.size()));
     }
     savePageNo(mess, "0", list.size(), strFileKey, ctx, iMaxLinePerPage);
     return 0;
   }
 
   public static String getMultiQueryKey(HiATLParam argsMap, HiMessage mess) throws HiException
   {
     String strFileKey = null;
 
     if (argsMap.contains("RECKEY")) {
       strFileKey = argsMap.get("RECKEY");
     }
     else {
       HiETF etf = mess.getETFBody();
       String strNodNo = etf.getChildValue("NOD_NO");
       String strTlrId = etf.getChildValue("TLR_ID");
       if ((StringUtils.isEmpty(strTlrId)) || (StringUtils.isEmpty(strNodNo))) {
         throw new HiException("交易要素不全:NOD_NO[" + strNodNo + "]," + "TLR_ID" + "[" + strTlrId + "]");
       }
 
       strFileKey = strNodNo + strTlrId;
     }
     return strFileKey;
   }
 
   public static void savePageNo(HiMessage mess, String strCurPag, int nColCounts, String strRecKey, HiMessageContext ctx, int GETCORD)
     throws HiException
   {
     Logger log = HiLog.getLogger(mess);
     mess.getETFBody().setChildValue("PAG_NO", StringUtils.leftPad(strCurPag, 5, "0"));
 
     String strSql = null;
     if (strCurPag.equals("0")) {
       strCurPag = "1";
       mess.getETFBody().setChildValue("PAG_NO", StringUtils.leftPad(strCurPag, 5, "0"));
 
       strSql = "SELECT REC_KEY FROM PUBUSRDAT WHERE  REC_KEY='" + strRecKey + "'";
 
       List list = ctx.getDataBaseUtil().execQuery(strSql);
       int nEndPag = nColCounts / GETCORD;
       if (nColCounts % GETCORD != 0) {
         ++nEndPag;
       }
       if ((list != null) && (list.size() > 0))
       {
         strSql = "UPDATE PUBUSRDAT SET CUR_PAG='%s',END_PAG='%s' WHERE REC_KEY='%s'";
         ctx.getDataBaseUtil().execUpdate(strSql, strCurPag, String.valueOf(nEndPag), strRecKey);
         return;
       }
 
       strSql = "INSERT INTO PUBUSRDAT(REC_KEY,USR_DAT,CUR_PAG,END_PAG) VALUES ('" + strRecKey + "','','" + strCurPag + "','" + String.valueOf(nEndPag) + "')";
     }
     else
     {
       strSql = "UPDATE PUBUSRDAT SET CUR_PAG='" + strCurPag + "' WHERE REC_KEY='" + strRecKey + "'";
     }
 
     if (log.isDebugEnabled()) {
       log.debug(nColCounts + "=" + strSql);
     }
     ctx.getDataBaseUtil().execUpdate(strSql);
   }
 
   public static int queryNext(HiATLParam argsMap, HiMessage mess, HiMessageContext ctx) throws HiException
   {
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled()) {
       log.info("多页查询中的翻页查询");
     }
     int iMaxLinePerPage = 19;
     if (argsMap.contains("RecNumPerPage")) {
       iMaxLinePerPage = Integer.valueOf(argsMap.get("RecNumPerPage")).intValue();
     }
     String strKey = getMultiQueryKey(argsMap, mess);
     List list = ctx.getDataBaseUtil().execQuery("SELECT CUR_PAG,END_PAG FROM PUBUSRDAT WHERE REC_KEY='" + strKey + "'");
 
     if ((list == null) || (list.size() == 0)) {
       return 2;
     }
     HashMap values = (HashMap)list.get(0);
     String strCurPag = (String)values.get("CUR_PAG");
     String strEndPag = (String)values.get("END_PAG");
     if (log.isInfoEnabled()) {
       log.info("CURPAG[" + strCurPag + "],ENDPAG[" + strEndPag + "]");
     }
     queryCmdConvert(mess, Integer.valueOf(strCurPag).intValue(), Integer.valueOf(strEndPag).intValue(), strKey, ctx, iMaxLinePerPage);
 
     return 0;
   }
 
   public static int queryCmdConvert(HiMessage mess, int nCurPag, int nEndPag, String strRecKey, HiMessageContext ctx, int iMaxLinePerPage)
     throws HiException
   {
     File file = getMultFile(strRecKey);
 
     String strValue = mess.getETFBody().getChildValue("PAG_IDX");
 
     if (strValue.equals("030000"))
     {
       return deleteMultFile(file); }
     if (strValue.equals("050000"))
     {
       getFirstPage(file, strRecKey, ctx, iMaxLinePerPage);
     } else if (strValue.equals("060000"))
     {
       getLastPage(file, strRecKey, mess, nEndPag, ctx, iMaxLinePerPage);
     } else if (strValue.equals("070000"))
     {
       getBackPage(file, strRecKey, mess, nCurPag, ctx, iMaxLinePerPage);
     } else if (strValue.equals("080000"))
     {
       getNextPage(file, strRecKey, mess, nCurPag, nEndPag, ctx, iMaxLinePerPage);
     }
     else if (strValue.equals("00"))
     {
       getAllPage(file, mess);
     } else {
       Logger log = HiLog.getLogger(mess);
       if (log.isInfoEnabled()) {
         log.info("页码不正确");
       }
       return -1;
     }
     return 0;
   }
 
   private static File getMultFile(String strRecKey)
   {
     File file = new File(getMultFolder() + File.separator + "MULTIDATDIR" + File.separator + "MultiQuery" + strRecKey + ".dat");
 
     return file;
   }
 
   private static int deleteMultFile(File file) throws HiException {
     if (file.exists()) {
       file.delete();
     }
     return 0;
   }
 
   public static int getFirstPage(File file, String strRecKey, HiMessageContext ctx, int iMaxLinePerPage) throws HiException
   {
     savePageNo(ctx.getCurrentMsg(), "1", -1, strRecKey, ctx, iMaxLinePerPage);
 
     return readPageNo(file, ctx.getCurrentMsg(), 1, iMaxLinePerPage);
   }
 
   public static int getBackPage(File file, String strRecKey, HiMessage mess, int nCurPag, HiMessageContext ctx, int iMaxLinePerPage)
     throws HiException
   {
     int startLine = 1;
     if (nCurPag - 1 >= 1)
       --nCurPag;
     if (nCurPag > 1) {
       startLine = (nCurPag - 1) * iMaxLinePerPage + 1;
     }
     savePageNo(mess, String.valueOf(nCurPag), -1, strRecKey, ctx, iMaxLinePerPage);
 
     return readPageNo(file, mess, startLine, iMaxLinePerPage);
   }
 
   public static int getNextPage(File file, String strRecKey, HiMessage mess, int nCurPag, int nEndPag, HiMessageContext ctx, int iMaxLinePerPage)
     throws HiException
   {
     int startLine = 1;
     if (nCurPag + 1 <= nEndPag) {
       ++nCurPag;
     }
     startLine = (nCurPag - 1) * iMaxLinePerPage + 1;
     savePageNo(mess, String.valueOf(nCurPag), -1, strRecKey, ctx, iMaxLinePerPage);
 
     return readPageNo(file, mess, startLine, iMaxLinePerPage);
   }
 
   public static int getLastPage(File file, String strRecKey, HiMessage mess, int nEndPag, HiMessageContext ctx, int iMaxLinePerPage)
     throws HiException
   {
     int startLine = (nEndPag - 1) * iMaxLinePerPage + 1;
     savePageNo(mess, String.valueOf(nEndPag), -1, strRecKey, ctx, iMaxLinePerPage);
 
     return readPageNo(file, mess, startLine, iMaxLinePerPage);
   }
 
   public static int getAllPage(File file, HiMessage mess) throws HiException {
     return readPageNo(file, mess, -1, -1);
   }
 
   public static int readPageNo(File file, HiMessage mess, int startLine, int GETCORD) throws HiException
   {
     BufferedReader br = null;
     try {
       Logger log = HiLog.getLogger(mess);
       if (log.isInfoEnabled()) {
         log.info("startLine=" + startLine);
       }
       HiETF etf = mess.getETFBody();
       br = new BufferedReader(new FileReader(file));
       int line = 1;
       while (true)
       {
         HiETF recEtf;
         strLine = br.readLine();
         if (StringUtils.isEmpty(strLine)) {
           break;
         }
         if (startLine == -1) {
           recEtf = HiETFFactory.createETF(strLine);
           etf.appendNode(recEtf);
         } else {
           if ((line >= startLine) && (line < startLine + GETCORD)) {
             recEtf = HiETFFactory.createETF(strLine);
             recEtf.setName("REC_" + (line - startLine + 1));
             etf.appendNode(recEtf);
           }
           if (line >= startLine + GETCORD) {
             break;
           }
         }
         ++line;
       }
       br.close();
 
       etf.addNode("REC_NUM", String.valueOf(line - startLine));
 
       if (log.isInfoEnabled()) {
         log.info("add RecNum[" + String.valueOf(line - startLine) + "]");
       }
 
       String strLine = 0;
 
       return strLine;
     }
     catch (Exception e)
     {
     }
     finally
     {
       if (br != null)
         try {
           br.close();
         }
         catch (IOException e)
         {
         }
     }
   }
 
   private static void setETFNode(String strLine, HiETF recNode, HashMap colMap) throws HiException {
     StringTokenizer st = new StringTokenizer(strLine, "\t");
     int index = 1;
     while (st.hasMoreTokens()) {
       String strColValue = st.nextToken();
       String strArgName = (String)colMap.get(String.valueOf(index));
       recNode.addNode(strArgName, strColValue);
       ++index;
     }
   }
 
   private static String getMultFolder() {
     return HiICSProperty.getWorkDir();
   }
 
   public static int MultiQueryFromFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     int iMaxLinePerPage = 19;
     if (argsMap.contains("RecNumPerPage")) {
       iMaxLinePerPage = Integer.valueOf(argsMap.get("RecNumPerPage")).intValue();
     }
 
     if (!(argsMap.contains("CfgFil"))) {
       throw new HiAppException(-1, "220307");
     }
 
     String aCfgFil = argsMap.get("CfgFil");
     if (!(aCfgFil.startsWith("/"))) {
       aCfgFil = getMultFolder() + File.separator + aCfgFil;
     }
     if (!(argsMap.contains("InFile"))) {
       throw new HiAppException(-1, "220308");
     }
 
     String aInFile = argsMap.get("InFile");
     if (!(aInFile.startsWith("/"))) {
       aInFile = getMultFolder() + File.separator + aInFile;
     }
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     String strFileKey = getMultiQueryKey(argsMap, ctx.getCurrentMsg());
     File file = null;
     try {
       file = getMultFile(strFileKey);
       if (log.isDebugEnabled()) {
         log.debug(file.getPath());
       }
       File parentFile = file.getParentFile();
       if (!(parentFile.exists())) {
         if (log.isDebugEnabled()) {
           log.debug(parentFile.getPath());
         }
         parentFile.mkdirs();
       }
       file.createNewFile();
     } catch (IOException e) {
       throw new HiException(e);
     }
     HiETF etf = ctx.getCurrentMsg().getETFBody();
 
     List list = ParseFileToEtf(aCfgFil, aInFile, ctx);
 
     writeFile(iMaxLinePerPage, log, file, etf, list);
 
     if (list.size() > iMaxLinePerPage) {
       etf.addNode("REC_NUM", String.valueOf(iMaxLinePerPage));
     }
     else
     {
       etf.addNode("REC_NUM", String.valueOf(list.size()));
     }
 
     savePageNo(ctx.getCurrentMsg(), "0", list.size(), strFileKey, ctx, iMaxLinePerPage);
 
     return 0;
   }
 
   private static void writeFile(int iMaxLinePerPage, Logger log, File file, HiETF etf, List list) throws HiException
   {
     try {
       if (log.isDebugEnabled()) {
         log.debug("RecNumPerPage[" + String.valueOf(iMaxLinePerPage) + "]");
       }
 
       FileOutputStream out = new FileOutputStream(file);
 
       for (int i = 0; i < list.size(); ++i) {
         HiETF recETF = (HiETF)list.get(i);
 
         if (log.isDebugEnabled()) {
           log.debug("index [" + String.valueOf(i + 1) + "]");
           log.debug("recETF [" + recETF.toString() + "]");
         }
         if (i < iMaxLinePerPage) {
           etf.appendNode(recETF);
         }
         out.write(recETF.toString().getBytes());
 
         out.write("\n".getBytes());
       }
       if (log.isDebugEnabled()) {
         log.debug("execMultiQuery is end.......");
       }
       out.close();
     } catch (Exception e) {
       throw new HiException(e);
     }
   }
 
   public static List ParseFileToEtf(String strCfgFile, String strInFile, HiMessageContext ctx) throws HiException
   {
     URL rule = HiResourceRule.getRuleForFile(strCfgFile);
 
     File file = new File(strCfgFile);
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug("rule[" + rule + "] file[" + strCfgFile + "]");
     }
     if (file == null)
       throw new HiException("213302", strCfgFile);
     HiCfgFile cfg = HiCfgFile.getCfgFile(file, rule);
 
     HiRoot root = (HiRoot)cfg.getRootInstance();
 
     HiMessage msg = ctx.getCurrentMsg();
     HiMessage newMsg = msg.cloneNoBody();
     List list = new ArrayList();
     try {
       FileReader fr = new FileReader(new File(strInFile));
       BufferedReader br = new BufferedReader(fr);
       String Line = br.readLine();
       HiByteBuffer byteBuffer = new HiByteBuffer(1024, 256);
       int row = 0;
 
       while (Line != null) {
         ++row;
         if (row == 1000) {
           log.warn("[Warning!] 超过最大限制数[1000]");
           break;
         }
 
         byteBuffer.append(Line);
 
         HiETF recNode = HiETFFactory.createETF("REC_" + row, "");
         newMsg.setBody(recNode);
 
         HiMessageHelper.setUnpackMessage(newMsg, byteBuffer);
         ctx.setCurrentMsg(newMsg);
 
         root.processInNode(ctx);
 
         list.add(recNode);
 
         HiMessageHelper.setPlainOffset(newMsg, 0);
 
         byteBuffer.clear();
 
         Line = br.readLine();
       }
       br.close();
       fr.close();
     } catch (Exception e) {
     }
     finally {
       ctx.setCurrentMsg(msg);
     }
     return list;
   }
 }