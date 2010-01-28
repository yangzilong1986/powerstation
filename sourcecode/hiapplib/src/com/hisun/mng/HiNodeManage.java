 package com.hisun.mng;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.database.HiResultSet;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiICSProperty;
 import com.hisun.version.HiFileVersionInfo;
 import com.hisun.version.HiVersion;
 import java.io.BufferedInputStream;
 import java.io.BufferedOutputStream;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.PrintStream;
 import java.io.StringReader;
 import java.io.UnsupportedEncodingException;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.Properties;
 import java.util.jar.JarFile;
 import java.util.zip.ZipEntry;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.lang.StringUtils;
 
 public class HiNodeManage
 {
   public int nodeQuery(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String grpNam = args.get("GrpNam");
     if (StringUtils.isBlank(grpNam)) {
       grpNam = "GRP";
     }
     String nodNam = args.get("NodNam");
     HiRegionInfo regionInfo = HiRegionInfo.parse();
     HiETF rootETF = ctx.getCurrentMsg().getETFBody();
 
     rootETF.setChildValue("RGN_NM", regionInfo.getName());
     Iterator iter = regionInfo.iterator();
     int k = 1;
     while (iter.hasNext()) {
       HiNodeInfo nodeInfo = (HiNodeInfo)iter.next();
       if ((StringUtils.isNotBlank(nodNam)) && (!(StringUtils.equalsIgnoreCase(nodNam, nodeInfo.getId())))) {
         continue;
       }
 
       HiETF grpNod = rootETF;
       grpNod = rootETF.addNode(grpNam + "_" + k);
       grpNod.setChildValue("NOD_ID", nodeInfo.getId());
       grpNod.setChildValue("NOD_DESC", nodeInfo.getDesc());
       grpNod.setChildValue("URL", nodeInfo.getUrl());
       ++k;
     }
     rootETF.setChildValue(grpNam + "_NUM", String.valueOf(k - 1));
     return 0;
   }
 
   public int sysInfoQuery(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiETF root = ctx.getCurrentMsg().getETFBody();
     root.setChildValue("FREE_MEMORY", String.valueOf(Runtime.getRuntime().freeMemory()));
 
     root.setChildValue("MAX_MEMORY", String.valueOf(Runtime.getRuntime().maxMemory()));
 
     root.setChildValue("INTEGRATOR_HOME", HiICSProperty.getWorkDir());
     root.setChildValue("INTEGRATOR_VERSION", System.getProperty("VERSION"));
     root.setChildValue("APP_SVR_IP", System.getProperty("APP_SVR_IP"));
     root.setChildValue("APP_SVR_ADM_PORT", System.getProperty("APP_SVR_ADM_PORT"));
 
     return 0;
   }
 
   public int JVM_GC(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     System.gc();
     return 0;
   }
 
   public int jarChangeLogQry(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String file = HiArgUtils.getStringNotNull(args, "JarFil");
     String grpNam = args.get("GrpNam");
     if (StringUtils.isBlank(grpNam)) {
       grpNam = "GRP";
     }
     if (!(file.startsWith("/"))) {
       file = HiICSProperty.getWorkDir() + "/" + file;
     }
     JarFile jarFile = null;
     try {
       jarFile = new JarFile(file);
       ZipEntry zipEntry = jarFile.getEntry("Change.Log");
       if (zipEntry == null) {
         throw new HiException("213302", "Change.Log");
       }
 
       BufferedReader br = new BufferedReader(new InputStreamReader(jarFile.getInputStream(zipEntry)));
 
       int k = 1;
       HiETF root = ctx.getCurrentMsg().getETFBody();
       while ((line = br.readLine()) != null)
       {
         String line;
         String[] tmps = StringUtils.split(line, " ");
         if (tmps.length < 4) {
           continue;
         }
         HiETF grpNod = root.addNode(grpNam + "_" + k);
         ++k;
         int i = 0;
         grpNod.setChildValue("DATE", tmps[(i++)]);
         grpNod.setChildValue("AUTHOR", tmps[(i++)]);
         if (tmps.length == 4)
           grpNod.setChildValue("BUGID", "");
         else {
           grpNod.setChildValue("BUGID", tmps[(i++)]);
         }
         grpNod.setChildValue("FILE", tmps[(i++)]);
         grpNod.setChildValue("DESC", tmps[(i++)]);
       }
     } catch (IOException e) {
     }
     finally {
       if (jarFile != null) {
         try {
           jarFile.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
       }
     }
     return 0;
   }
 
   public int jarQry(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String grpNam = args.get("GrpNam");
     if (StringUtils.isBlank(grpNam)) {
       grpNam = "GRP";
     }
     ArrayList versionFiles = new ArrayList();
     HiVersion.dumpDirVersion(HiICSProperty.getWorkDir() + "/lib", versionFiles);
 
     HiVersion.dumpDirVersion(HiICSProperty.getWorkDir() + "/applib", versionFiles);
 
     HiETF root = ctx.getCurrentMsg().getETFBody();
     int i = 0;
     for (i = 0; i < versionFiles.size(); ++i) {
       HiFileVersionInfo info = (HiFileVersionInfo)versionFiles.get(i);
       HiETF grpNod = root.addNode(grpNam + "_" + (i + 1));
       grpNod.setChildValue("FILE", info.getFile());
       grpNod.setChildValue("VERSION", info.getVersion());
       grpNod.setChildValue("COMPILE_TM", info.getCompileTm());
     }
     return 0;
   }
 
   public int shellCmd(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     Process p = null;
     int ret = 0;
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     HiETF root = ctx.getCurrentMsg().getETFBody();
     String cmd = HiArgUtils.getStringNotNull(args, "cmd");
     boolean waitFlag = args.getBoolean("waitFlag");
     try {
       ProcessBuilder pb = new ProcessBuilder(new String[] { "/bin/sh", "-c", cmd });
       pb.directory(new File(HiICSProperty.getWorkDir()));
       pb.redirectErrorStream(true);
       p = pb.start();
       InputStream stdIs = p.getInputStream();
       Thread receiverThd = new Thread(new Receiver(root, stdIs));
       receiverThd.start();
       try {
         if (waitFlag)
           ret = p.waitFor();
       }
       catch (InterruptedException e) {
         throw new HiException("220309", cmd, e);
       }
       receiverThd.join(5000L);
     } catch (IOException e) {
     }
     catch (InterruptedException e) {
     }
     finally {
       if (p != null) {
         p.destroy();
       }
     }
     return ret;
   }
 
   public int ExpToPropFile(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String sqlCmd = HiArgUtils.getStringNotNull(args, "SqlCmd");
     String encoding = args.get("encoding");
     String file = HiArgUtils.getStringNotNull(args, "file");
     if (!(file.startsWith(File.separator))) {
       file = HiICSProperty.getWorkDir() + File.separator + file;
     }
     HiETF root = ctx.getCurrentMsg().getETFBody();
     String sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlCmd, root, true);
 
     HiResultSet rs = ctx.getDataBaseUtil().execQuerySQL(sqlSentence);
 
     if ((rs == null) || (rs.size() == 0)) {
       return 2;
     }
     Properties p = new Properties();
     for (int i = 0; i < rs.size(); ++i) {
       HiATLParam record = rs.getRecord(i);
       if (record.size() < 2) {
         continue;
       }
       if (StringUtils.isBlank(encoding))
         p.put(record.getValue(0), record.getValue(1));
       else {
         try {
           p.put(record.getValue(0), new String(record.getValue(1).getBytes(), encoding));
         }
         catch (UnsupportedEncodingException e) {
           throw new HiException("220079", file, e);
         }
       }
     }
 
     FileOutputStream fos = null;
     try {
       fos = new FileOutputStream(file);
       p.store(fos, "auto generate message code");
     } catch (IOException e) {
     }
     finally {
       if (fos != null) {
         try {
           fos.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
       }
     }
     p.clear();
     return 0;
   }
 
   public int FileDownLoad(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     boolean isInline = false;
     String file = HiArgUtils.getStringNotNull(args, "FILNAM");
     File f = new File(file);
     if ((!(f.exists())) || (!(f.canRead()))) {
       return 2;
     }
     HiMessage msg = ctx.getCurrentMsg();
     HttpServletResponse response = (HttpServletResponse)msg.getObjectHeadItem("_WEB_RESPONSE");
 
     HttpServletRequest request = (HttpServletRequest)msg.getObjectHeadItem("_WEB_REQUEST");
 
     String mimetype = "application/octet-stream;charset=ISO8859-1";
     response.setContentType(mimetype);
     String ua = request.getHeader("User-Agent");
     if (ua == null) {
       ua = "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0;)";
     }
     boolean isIE = ua.toLowerCase().indexOf("msie") != -1;
     if ((isIE) && (!(isInline))) {
       mimetype = "application/x-msdownload";
     }
     String downFileName = null;
     try {
       downFileName = new String(f.getName().getBytes(), "ISO8859-1");
     } catch (UnsupportedEncodingException e) {
       e.printStackTrace();
     }
     String inlineType = (isInline) ? "inline" : "attachment";
     response.setHeader("Content-Disposition", inlineType + ";filename=\"" + downFileName + "\"");
 
     response.setContentLength((int)f.length());
     byte[] buffer = new byte[4096];
     BufferedOutputStream output = null;
     BufferedInputStream input = null;
     try {
       output = new BufferedOutputStream(response.getOutputStream());
       input = new BufferedInputStream(new FileInputStream(f));
 
       int n = -1;
       while ((n = input.read(buffer, 0, 4096)) > -1) {
         output.write(buffer, 0, n);
       }
       response.flushBuffer();
     } catch (Exception e) {
     } finally {
       if (input != null) {
         try {
           input.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
       }
       if (output != null) {
         try {
           output.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
       }
     }
     return 0;
   }
 
   public int ParseBatchMsgInf(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String batchInfo = HiArgUtils.getStringNotNull(args, "BatInf");
     String msgCod = "";
     String msgInf = "";
     String msgNam = "";
     String msgCls = "";
     BufferedReader br = new BufferedReader(new StringReader(batchInfo));
 
     HiETF root = ctx.getCurrentMsg().getETFBody();
     int k = 1;
     try {
       while ((line = br.readLine()) != null)
       {
         String line;
         int idx1;
         String[] tmps;
         if ((idx1 = line.indexOf("package ")) != -1) {
           msgCls = line.substring(idx1 + 8);
           msgCls = msgCls.replace(";", ".");
         }
 
         if ((StringUtils.contains(line, "public ")) && (StringUtils.contains(line, " class "))) {
           tmps = StringUtils.split(line, " ");
           if (tmps.length >= 3) {
             msgCls = msgCls + tmps[2];
           }
         }
 
         if ((idx1 = line.indexOf("@msg ")) != -1) {
           msgInf = StringUtils.trim(line.substring(idx1 + 4));
         }
 
         if ((StringUtils.contains(line, "public ")) && (StringUtils.contains(line, " static ")) && (StringUtils.contains(line, " final ")) && (StringUtils.contains(line, " String ")))
         {
           tmps = StringUtils.split(line, " =\"");
           if (tmps.length >= 6) {
             msgNam = tmps[4];
             msgCod = tmps[5];
           }
         }
         if ((StringUtils.isNotBlank(msgCod)) && (StringUtils.isNotBlank(msgInf))) {
           HiETF grp = root.addNode("GRP_" + k);
           ++k;
           grp.setChildValue("MSG_CD", msgCod);
           grp.setChildValue("MSG_INF", msgInf);
           grp.setChildValue("MSG_NM", msgNam);
           if (StringUtils.isBlank(msgCls))
             grp.setChildValue("MSG_CLS", "undefined");
           else {
             grp.setChildValue("MSG_CLS", msgCls);
           }
           msgCod = "";
           msgInf = "";
           msgNam = "";
         }
       }
       root.setChildValue("GRP_NUM", String.valueOf(k - 1));
     } catch (IOException e) {
       e.printStackTrace();
     }
 
     return 0;
   }
 
   public static void main(String[] args) {
     String line = "2009.03.04 xu_lw              HiIntegrator                增加AutoCommit标志";
     String[] tmps = StringUtils.split(line, " ");
     for (int i = 0; i < tmps.length; ++i)
       System.out.println(tmps[i]);
   }
 }