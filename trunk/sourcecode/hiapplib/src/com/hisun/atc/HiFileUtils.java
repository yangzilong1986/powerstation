 package com.hisun.atc;
 
 import com.hisun.atc.bat.HiBatchProcess;
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.atc.common.HiFile;
 import com.hisun.atc.fil.HiRoot;
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.invoke.load.HiDelegate;
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
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiFileInputStream;
 import com.hisun.util.HiFileOutputStream;
 import com.hisun.util.HiICSProperty;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.Iterator;
 import org.apache.commons.lang.CharUtils;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 
 public class HiFileUtils
 {
   public static int CatFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String srcFile = HiArgUtils.getStringNotNull(argsMap, "SrcFil");
     srcFile = HiArgUtils.absoutePath(srcFile);
     String objFile = HiArgUtils.getStringNotNull(argsMap, "ObjFil");
     objFile = HiArgUtils.absoutePath(objFile);
     FileReader srcReader = null;
     FileWriter destWriter = null;
     try {
       File f = new File(srcFile);
       srcReader = new FileReader(f);
       destWriter = new FileWriter(objFile, true);
       char[] buffer = new char[(int)f.length()];
       srcReader.read(buffer);
       try
       {
         if (srcReader != null)
           srcReader.close();
         if (destWriter != null)
           destWriter.close();
       } catch (IOException e) {
         throw new HiException("220079", srcFile, e);
       }
     }
     catch (FileNotFoundException e)
     {
     }
     catch (IOException e)
     {
     }
     finally
     {
       try
       {
         if (srcReader != null)
           srcReader.close();
         if (destWriter != null)
           destWriter.close();
       } catch (IOException e) {
         throw new HiException("220079", srcFile, e);
       }
     }
     return 0;
   }
 
   public static int CopyFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String srcFile = HiArgUtils.getStringNotNull(argsMap, "SrcFil");
     srcFile = HiArgUtils.absoutePath(srcFile);
     String objFile = HiArgUtils.getStringNotNull(argsMap, "ObjFil");
     objFile = HiArgUtils.absoutePath(objFile);
 
     HiFile.copy(srcFile, objFile);
 
     return 0;
   }
 
   public static int ConvertFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String cfgFile = HiArgUtils.getStringNotNull(argsMap, "CfgFile");
 
     String inFile = HiArgUtils.getStringNotNull(argsMap, "InFile");
     inFile = HiArgUtils.absoutePath(inFile);
     String outFile = HiArgUtils.getStringNotNull(argsMap, "OutFile");
     outFile = HiArgUtils.absoutePath(outFile);
     HiRoot root = (HiRoot)ctx.getProperty("CONFIGDECLARE", cfgFile);
 
     if (root == null) {
       throw new HiAppException(-1, "220314", cfgFile);
     }
     if (root.getInNode() == null) {
       throw new HiAppException(-1, "220315", "In");
     }
 
     if (root.getOutNode() == null) {
       throw new HiAppException(-1, "220315", "Out");
     }
     HiMessage msg = ctx.getCurrentMsg();
     HiByteBuffer buffer = new HiByteBuffer(1024);
     HiFileInputStream.read(inFile, buffer);
     HiETF etf = HiETFFactory.createETF();
     unpack(root.getInNode(), ctx, etf, buffer);
     buffer.clear();
     pack(root.getOutNode(), ctx, etf, buffer);
     HiFileOutputStream.write(outFile, buffer);
     return 0;
   }
 
   public static int DeleteFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String strFileName = HiArgUtils.getStringNotNull(argsMap, "FilNam");
     strFileName = HiArgUtils.absoutePath(strFileName);
     File file = new File(strFileName);
 
     if (!(file.exists())) {
       return 0;
     }
     try
     {
       file.delete();
     } catch (Exception e) {
       throw new HiException("220010", file.getPath(), e);
     }
     return 0;
   }
 
   public static int DumpFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String fileName = HiArgUtils.getStringNotNull(argsMap, "FileName");
     String configName = HiArgUtils.getStringNotNull(argsMap, "ConfigName");
     String rootName = HiArgUtils.getStringNotNull(argsMap, "RootName");
     String nodeName = argsMap.get("NodeName");
     String attrName = argsMap.get("AttrName");
     String attrValue = argsMap.get("AttrValue");
 
     HiRoot root = (HiRoot)ctx.getProperty("CONFIGDECLARE", configName);
 
     if (root == null) {
       throw new HiAppException(-1, "220314", configName);
     }
 
     HiEngineModel model = root.getNode(nodeName, attrName, attrValue);
     if (model == null) {
       throw new HiAppException(-1, "220315", nodeName);
     }
 
     HiByteBuffer buffer = new HiByteBuffer(1024);
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     pack(model, ctx, etf, buffer);
     HiFileOutputStream.write(fileName, buffer);
     return 0;
   }
 
   public static int IsExistFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     for (int i = 0; i < argsMap.size(); ++i) {
       String fileName = argsMap.getValue(i);
       fileName = HiArgUtils.absoutePath(fileName);
       File f = new File(fileName);
       if (!(f.exists()))
         return 1;
     }
     return 0;
   }
 
   public static int IsValidFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String strFileName = argsMap.get("FileName");
     File file = new File(strFileName);
     if (!(file.isAbsolute())) {
       String strPath = HiICSProperty.getWorkDir() + File.separator + strFileName;
 
       file = new File(strPath);
     }
 
     if (!(file.exists())) {
       return 1;
     }
     FileReader fr = null;
     BufferedReader br = null;
     try {
       fr = new FileReader(file);
       br = new BufferedReader(fr);
       String Line = br.readLine();
       int row = 0;
       while (Line != null) {
         ++row;
         Line = br.readLine();
       }
       HiETF etf = ctx.getCurrentMsg().getETFBody();
       etf.setChildValue("FIL_LIN", String.valueOf(row));
       etf.setChildValue("FIL_BYT", String.valueOf(file.length()));
     }
     catch (IOException e)
     {
     }
     finally {
       try {
         if (br != null)
           br.close();
         if (fr != null)
           fr.close();
       }
       catch (Exception e) {
       }
     }
     return 0;
   }
 
   public static int WriteFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String fileName = HiArgUtils.getStringNotNull(argsMap, "FileName");
     fileName = HiArgUtils.absoutePath(fileName);
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     String openMode = HiArgUtils.getStringNotNull(argsMap, "OpenMode");
 
     HiFile file = new HiFile();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     try {
       file.open(fileName, openMode);
       for (int i = 2; i < argsMap.size(); ++i)
       {
         String value;
         String name = argsMap.getName(i);
         if (StringUtils.equalsIgnoreCase(name, "PRNFMT")) {
           value = HiDbtSqlHelper.getDynSentence(ctx, argsMap.getValue(i));
 
           value = ESCFormat(value);
         } else if (StringUtils.equalsIgnoreCase(name, "ESCFMT")) {
           value = argsMap.get(name);
           if (log.isInfoEnabled())
             log.info("name[" + name + "] value[" + value + "]");
           value = ESCFormat(value);
           if (log.isInfoEnabled())
             log.info("ESCFormat value[" + value + "]");
         } else if (StringUtils.equalsIgnoreCase(name, "NodeName")) {
           value = argsMap.get(name);
           value = etf.getChildValue(value);
         } else {
           value = argsMap.get(name);
         }
         file.write(value);
       }
     } finally {
       file.close();
     }
     return 0;
   }
 
   public static int OpenFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String fileName = HiArgUtils.getStringNotNull(argsMap, "FileName");
     fileName = HiArgUtils.absoutePath(fileName);
     String mode = HiArgUtils.getStringNotNull(argsMap, "Mode");
     String objectName = argsMap.get("ObjectName");
     if (StringUtils.isEmpty(objectName)) {
       objectName = "PUB_FILEPOINTER";
     }
     HiFile file = new HiFile();
     file.open(fileName, mode);
     ctx.setBaseSource(objectName, file);
     return 0;
   }
 
   public static int CloseFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String objectName = argsMap.get("ObjectName");
     if (StringUtils.isEmpty(objectName)) {
       objectName = "PUB_FILEPOINTER";
     }
     HiFile file = (HiFile)ctx.getBaseSource(objectName);
     file.close();
     ctx.removeBaseSource(objectName);
     return 0;
   }
 
   public static int GetFileLine(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String tmp;
     String fieldName = HiArgUtils.getStringNotNull(argsMap, "FieldName");
     String objectName = argsMap.get("ObjectName");
     if (StringUtils.isEmpty(objectName))
       objectName = "PUB_FILEPOINTER";
     HiFile file = (HiFile)ctx.getProperty(objectName);
 
     if ((tmp = file.readLine()) == null) {
       return 1;
     }
     ctx.getCurrentMsg().getETFBody().setChildValue(fieldName, tmp);
     return 0;
   }
 
   public static int GetFileLineAndParse(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String configName = HiArgUtils.getStringNotNull(argsMap, "ConfigName");
     String rootName = HiArgUtils.getStringNotNull(argsMap, "RootName");
     String nodeName = argsMap.get("NodeName");
     String attrName = argsMap.get("AttrName");
     String attrValue = argsMap.get("AttrValue");
     String objectName = argsMap.get("ObjectName");
     if (StringUtils.isEmpty(objectName)) {
       objectName = "PUB_FILEPOINTER";
     }
 
     HiDelegate rootObj = (HiDelegate)ctx.getProperty("CONFIGDECLARE", configName);
 
     if (rootObj == null) {
       throw new HiException("220204", configName);
     }
 
     HiBatchProcess pro = (HiBatchProcess)rootObj.getChildsMap().get(attrValue);
 
     if (pro == null) {
       throw new HiException("220204", attrValue);
     }
 
     HiFile file = (HiFile)ctx.getBaseSource(objectName);
     String data = file.readLine();
     if (data == null) {
       return 1;
     }
 
     String tmpPlainOffset = ctx.getCurrentMsg().getHeadItem("PlainOffset");
     HiByteBuffer buffer = new HiByteBuffer(data.getBytes());
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     unpack(pro, ctx, etf, buffer);
     if (tmpPlainOffset == null)
       ctx.getCurrentMsg().delHeadItem("PlainOffset");
     else {
       ctx.getCurrentMsg().setHeadItem("PlainOffset", tmpPlainOffset);
     }
     return 0;
   }
 
   public static int ReadFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String fieldName = HiArgUtils.getStringNotNull(argsMap, "FieldName");
     String objectName = argsMap.get("ObjectName");
     if (StringUtils.isEmpty(objectName))
       objectName = "PUB_FILEPOINTER";
     HiFile file = (HiFile)ctx.getBaseSource(objectName);
     int len = argsMap.getInt("ReadLen");
     if (len <= 0) {
       throw new HiAppException(-1, "220026", "ReadLen");
     }
     StringBuffer buffer = new StringBuffer();
     int ret = file.readBytes(buffer, len);
     ctx.getCurrentMsg().getETFBody().setChildValue(fieldName, buffer.toString());
 
     if (ret == -1) {
       return 1;
     }
     return 0;
   }
 
   public static int ReadFileAndParse(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String configName = HiArgUtils.getStringNotNull(argsMap, "ConfigName");
     String rootName = HiArgUtils.getStringNotNull(argsMap, "RootName");
     String nodeName = argsMap.get("NodeName");
     String attrName = argsMap.get("AttrName");
     String attrValue = argsMap.get("AttrValue");
     String objectName = argsMap.get("ObjectName");
     if (StringUtils.isEmpty(objectName)) {
       objectName = "PUB_FILEPOINTER";
     }
     int maxLen = argsMap.getInt("MaxLen");
     HiDelegate rootObj = (HiDelegate)ctx.getProperty("CONFIGDECLARE", configName);
 
     if (rootObj == null) {
       throw new HiException("220204", configName);
     }
 
     HiBatchProcess pro = (HiBatchProcess)rootObj.getChildsMap().get(attrValue);
 
     if (pro == null) {
       throw new HiException("220204", attrValue);
     }
 
     HiFile file = (HiFile)ctx.getBaseSource(objectName);
 
     StringBuffer dataBuffer = new StringBuffer();
     int ret = file.read(dataBuffer, maxLen);
     System.out.println(dataBuffer);
     if (ret == -1) {
       return 1;
     }
 
     HiByteBuffer buffer = new HiByteBuffer(dataBuffer.toString().getBytes());
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     String tmpPlainOffset = ctx.getCurrentMsg().getHeadItem("PlainOffset");
     unpack(pro, ctx, etf, buffer);
     if (tmpPlainOffset == null)
       ctx.getCurrentMsg().delHeadItem("PlainOffset");
     else {
       ctx.getCurrentMsg().setHeadItem("PlainOffset", tmpPlainOffset);
     }
 
     return 0;
   }
 
   public static int GetFileFromDir(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String dir = HiArgUtils.getStringNotNull(argsMap, "Directory");
     dir = HiArgUtils.absoutePath(dir);
     String fieldName = HiArgUtils.getStringNotNull(argsMap, "FieldName");
     String separator = HiArgUtils.getStringNotNull(argsMap, "Separator");
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     etf.setChildValue(fieldName, StringUtils.join(new File(dir).list(), separator));
 
     return 0;
   }
 
   public static int ReadXmlConfig(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String configName = HiArgUtils.getStringNotNull(argsMap, "ConfigName");
     String nodeName = argsMap.get("NodeName");
     if (StringUtils.isEmpty(nodeName))
       nodeName = "Config";
     String attrName = argsMap.get("AttrName");
     if (StringUtils.isEmpty(attrName))
       attrName = "value";
     String value = HiArgUtils.getStringNotNull(argsMap, "Value");
     Element root = (Element)ctx.getProperty("CONFIGDECLARE", configName);
 
     if (root == null);
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     Iterator iter = root.elementIterator(nodeName);
     while ((iter != null) && (iter.hasNext())) {
       Element element = (Element)iter.next();
       if ((!(element.getName().startsWith("#"))) && (StringUtils.equals(element.attributeValue(attrName), value)))
       {
         Iterator iter1 = element.elementIterator();
         while ((iter1 != null) && (iter1.hasNext())) {
           Element element1 = (Element)iter1.next();
           etf.setChildValue(element1.getName(), element1.getTextTrim());
         }
 
         break;
       }
     }
 
     return 0;
   }
 
   private static String ESCFormat(String str) {
     HiByteBuffer buffer = new HiByteBuffer(str.length());
     for (int i = 0; i < str.length(); ++i)
       if (str.charAt(i) == '\\') {
         ++i;
         if (i == str.length())
           buffer.append(str.charAt(i - 1));
       }
       else
       {
         switch (str.charAt(i))
         {
         case 't':
           buffer.append(9);
           break;
         case 'n':
           buffer.append(10);
           break;
         case 'f':
           buffer.append(12);
           break;
         case 'r':
           buffer.append(13);
           break;
         case '\\':
           buffer.append(92);
           break;
         default:
           if ((i + 2 < str.length()) && (CharUtils.isAsciiNumeric(str.charAt(i))) && (CharUtils.isAsciiNumeric(str.charAt(i + 1))) && (CharUtils.isAsciiNumeric(str.charAt(i + 2))))
           {
             buffer.append((char)Integer.valueOf(str.substring(i, i + 3), 8).intValue());
 
             i += 3;
           } else {
             i -= 1;
             buffer.append(str.charAt(i));
 
             continue;
 
             buffer.append(str.charAt(i)); } }
       }
     return buffer.toString();
   }
 
   private static void unpack(HiEngineModel model, HiMessageContext ctx, HiETF etf, HiByteBuffer buffer) throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     msg.setBody(etf);
     HiMessageHelper.setUnpackMessage(msg, buffer);
     try {
       model.process(ctx);
     } finally {
       msg.setBody(root);
     }
   }
 
   private static void pack(HiEngineModel model, HiMessageContext ctx, HiETF etf, HiByteBuffer buffer) throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     msg.setBody(etf);
     HiMessageHelper.setPackMessage(msg, buffer);
     try {
       model.process(ctx);
     } finally {
       msg.setBody(root);
     }
   }
 }