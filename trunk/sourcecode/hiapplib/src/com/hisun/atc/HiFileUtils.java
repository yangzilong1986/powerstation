/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.bat.HiBatchProcess;
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.atc.common.HiDbtSqlHelper;
/*     */ import com.hisun.atc.common.HiFile;
/*     */ import com.hisun.atc.fil.HiRoot;
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.invoke.load.HiDelegate;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.message.HiMessageHelper;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiFileInputStream;
/*     */ import com.hisun.util.HiFileOutputStream;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.lang.CharUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiFileUtils
/*     */ {
/*     */   public static int CatFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  59 */     String srcFile = HiArgUtils.getStringNotNull(argsMap, "SrcFil");
/*  60 */     srcFile = HiArgUtils.absoutePath(srcFile);
/*  61 */     String objFile = HiArgUtils.getStringNotNull(argsMap, "ObjFil");
/*  62 */     objFile = HiArgUtils.absoutePath(objFile);
/*  63 */     FileReader srcReader = null;
/*  64 */     FileWriter destWriter = null;
/*     */     try {
/*  66 */       File f = new File(srcFile);
/*  67 */       srcReader = new FileReader(f);
/*  68 */       destWriter = new FileWriter(objFile, true);
/*  69 */       char[] buffer = new char[(int)f.length()];
/*  70 */       srcReader.read(buffer);
/*     */       try
/*     */       {
/*  78 */         if (srcReader != null)
/*  79 */           srcReader.close();
/*  80 */         if (destWriter != null)
/*  81 */           destWriter.close();
/*     */       } catch (IOException e) {
/*  83 */         throw new HiException("220079", srcFile, e);
/*     */       }
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  78 */         if (srcReader != null)
/*  79 */           srcReader.close();
/*  80 */         if (destWriter != null)
/*  81 */           destWriter.close();
/*     */       } catch (IOException e) {
/*  83 */         throw new HiException("220079", srcFile, e);
/*     */       }
/*     */     }
/*  86 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int CopyFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 108 */     String srcFile = HiArgUtils.getStringNotNull(argsMap, "SrcFil");
/* 109 */     srcFile = HiArgUtils.absoutePath(srcFile);
/* 110 */     String objFile = HiArgUtils.getStringNotNull(argsMap, "ObjFil");
/* 111 */     objFile = HiArgUtils.absoutePath(objFile);
/*     */ 
/* 113 */     HiFile.copy(srcFile, objFile);
/*     */ 
/* 115 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int ConvertFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 133 */     String cfgFile = HiArgUtils.getStringNotNull(argsMap, "CfgFile");
/*     */ 
/* 135 */     String inFile = HiArgUtils.getStringNotNull(argsMap, "InFile");
/* 136 */     inFile = HiArgUtils.absoutePath(inFile);
/* 137 */     String outFile = HiArgUtils.getStringNotNull(argsMap, "OutFile");
/* 138 */     outFile = HiArgUtils.absoutePath(outFile);
/* 139 */     HiRoot root = (HiRoot)ctx.getProperty("CONFIGDECLARE", cfgFile);
/*     */ 
/* 141 */     if (root == null) {
/* 142 */       throw new HiAppException(-1, "220314", cfgFile);
/*     */     }
/* 144 */     if (root.getInNode() == null) {
/* 145 */       throw new HiAppException(-1, "220315", "In");
/*     */     }
/*     */ 
/* 148 */     if (root.getOutNode() == null) {
/* 149 */       throw new HiAppException(-1, "220315", "Out");
/*     */     }
/* 151 */     HiMessage msg = ctx.getCurrentMsg();
/* 152 */     HiByteBuffer buffer = new HiByteBuffer(1024);
/* 153 */     HiFileInputStream.read(inFile, buffer);
/* 154 */     HiETF etf = HiETFFactory.createETF();
/* 155 */     unpack(root.getInNode(), ctx, etf, buffer);
/* 156 */     buffer.clear();
/* 157 */     pack(root.getOutNode(), ctx, etf, buffer);
/* 158 */     HiFileOutputStream.write(outFile, buffer);
/* 159 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int DeleteFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 173 */     String strFileName = HiArgUtils.getStringNotNull(argsMap, "FilNam");
/* 174 */     strFileName = HiArgUtils.absoutePath(strFileName);
/* 175 */     File file = new File(strFileName);
/*     */ 
/* 177 */     if (!(file.exists())) {
/* 178 */       return 0;
/*     */     }
/*     */     try
/*     */     {
/* 182 */       file.delete();
/*     */     } catch (Exception e) {
/* 184 */       throw new HiException("220010", file.getPath(), e);
/*     */     }
/* 186 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int DumpFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 220 */     String fileName = HiArgUtils.getStringNotNull(argsMap, "FileName");
/* 221 */     String configName = HiArgUtils.getStringNotNull(argsMap, "ConfigName");
/* 222 */     String rootName = HiArgUtils.getStringNotNull(argsMap, "RootName");
/* 223 */     String nodeName = argsMap.get("NodeName");
/* 224 */     String attrName = argsMap.get("AttrName");
/* 225 */     String attrValue = argsMap.get("AttrValue");
/*     */ 
/* 227 */     HiRoot root = (HiRoot)ctx.getProperty("CONFIGDECLARE", configName);
/*     */ 
/* 229 */     if (root == null) {
/* 230 */       throw new HiAppException(-1, "220314", configName);
/*     */     }
/*     */ 
/* 234 */     HiEngineModel model = root.getNode(nodeName, attrName, attrValue);
/* 235 */     if (model == null) {
/* 236 */       throw new HiAppException(-1, "220315", nodeName);
/*     */     }
/*     */ 
/* 239 */     HiByteBuffer buffer = new HiByteBuffer(1024);
/* 240 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 241 */     pack(model, ctx, etf, buffer);
/* 242 */     HiFileOutputStream.write(fileName, buffer);
/* 243 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int IsExistFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 259 */     for (int i = 0; i < argsMap.size(); ++i) {
/* 260 */       String fileName = argsMap.getValue(i);
/* 261 */       fileName = HiArgUtils.absoutePath(fileName);
/* 262 */       File f = new File(fileName);
/* 263 */       if (!(f.exists()))
/* 264 */         return 1;
/*     */     }
/* 266 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int IsValidFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 280 */     String strFileName = argsMap.get("FileName");
/* 281 */     File file = new File(strFileName);
/* 282 */     if (!(file.isAbsolute())) {
/* 283 */       String strPath = HiICSProperty.getWorkDir() + File.separator + strFileName;
/*     */ 
/* 285 */       file = new File(strPath);
/*     */     }
/*     */ 
/* 288 */     if (!(file.exists())) {
/* 289 */       return 1;
/*     */     }
/* 291 */     FileReader fr = null;
/* 292 */     BufferedReader br = null;
/*     */     try {
/* 294 */       fr = new FileReader(file);
/* 295 */       br = new BufferedReader(fr);
/* 296 */       String Line = br.readLine();
/* 297 */       int row = 0;
/* 298 */       while (Line != null) {
/* 299 */         ++row;
/* 300 */         Line = br.readLine();
/*     */       }
/* 302 */       HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 303 */       etf.setChildValue("FIL_LIN", String.valueOf(row));
/* 304 */       etf.setChildValue("FIL_BYT", String.valueOf(file.length()));
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */     finally {
/*     */       try {
/* 311 */         if (br != null)
/* 312 */           br.close();
/* 313 */         if (fr != null)
/* 314 */           fr.close();
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/*     */     }
/* 319 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int WriteFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 346 */     String fileName = HiArgUtils.getStringNotNull(argsMap, "FileName");
/* 347 */     fileName = HiArgUtils.absoutePath(fileName);
/* 348 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 349 */     String openMode = HiArgUtils.getStringNotNull(argsMap, "OpenMode");
/*     */ 
/* 351 */     HiFile file = new HiFile();
/* 352 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */     try {
/* 354 */       file.open(fileName, openMode);
/* 355 */       for (int i = 2; i < argsMap.size(); ++i)
/*     */       {
/*     */         String value;
/* 356 */         String name = argsMap.getName(i);
/* 357 */         if (StringUtils.equalsIgnoreCase(name, "PRNFMT")) {
/* 358 */           value = HiDbtSqlHelper.getDynSentence(ctx, argsMap.getValue(i));
/*     */ 
/* 361 */           value = ESCFormat(value);
/* 362 */         } else if (StringUtils.equalsIgnoreCase(name, "ESCFMT")) {
/* 363 */           value = argsMap.get(name);
/* 364 */           if (log.isInfoEnabled())
/* 365 */             log.info("name[" + name + "] value[" + value + "]");
/* 366 */           value = ESCFormat(value);
/* 367 */           if (log.isInfoEnabled())
/* 368 */             log.info("ESCFormat value[" + value + "]");
/* 369 */         } else if (StringUtils.equalsIgnoreCase(name, "NodeName")) {
/* 370 */           value = argsMap.get(name);
/* 371 */           value = etf.getChildValue(value);
/*     */         } else {
/* 373 */           value = argsMap.get(name);
/*     */         }
/* 375 */         file.write(value);
/*     */       }
/*     */     } finally {
/* 378 */       file.close();
/*     */     }
/* 380 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int OpenFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 398 */     String fileName = HiArgUtils.getStringNotNull(argsMap, "FileName");
/* 399 */     fileName = HiArgUtils.absoutePath(fileName);
/* 400 */     String mode = HiArgUtils.getStringNotNull(argsMap, "Mode");
/* 401 */     String objectName = argsMap.get("ObjectName");
/* 402 */     if (StringUtils.isEmpty(objectName)) {
/* 403 */       objectName = "PUB_FILEPOINTER";
/*     */     }
/* 405 */     HiFile file = new HiFile();
/* 406 */     file.open(fileName, mode);
/* 407 */     ctx.setBaseSource(objectName, file);
/* 408 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int CloseFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 422 */     String objectName = argsMap.get("ObjectName");
/* 423 */     if (StringUtils.isEmpty(objectName)) {
/* 424 */       objectName = "PUB_FILEPOINTER";
/*     */     }
/* 426 */     HiFile file = (HiFile)ctx.getBaseSource(objectName);
/* 427 */     file.close();
/* 428 */     ctx.removeBaseSource(objectName);
/* 429 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int GetFileLine(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     String tmp;
/* 447 */     String fieldName = HiArgUtils.getStringNotNull(argsMap, "FieldName");
/* 448 */     String objectName = argsMap.get("ObjectName");
/* 449 */     if (StringUtils.isEmpty(objectName))
/* 450 */       objectName = "PUB_FILEPOINTER";
/* 451 */     HiFile file = (HiFile)ctx.getProperty(objectName);
/*     */ 
/* 453 */     if ((tmp = file.readLine()) == null) {
/* 454 */       return 1;
/*     */     }
/* 456 */     ctx.getCurrentMsg().getETFBody().setChildValue(fieldName, tmp);
/* 457 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int GetFileLineAndParse(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 483 */     String configName = HiArgUtils.getStringNotNull(argsMap, "ConfigName");
/* 484 */     String rootName = HiArgUtils.getStringNotNull(argsMap, "RootName");
/* 485 */     String nodeName = argsMap.get("NodeName");
/* 486 */     String attrName = argsMap.get("AttrName");
/* 487 */     String attrValue = argsMap.get("AttrValue");
/* 488 */     String objectName = argsMap.get("ObjectName");
/* 489 */     if (StringUtils.isEmpty(objectName)) {
/* 490 */       objectName = "PUB_FILEPOINTER";
/*     */     }
/*     */ 
/* 493 */     HiDelegate rootObj = (HiDelegate)ctx.getProperty("CONFIGDECLARE", configName);
/*     */ 
/* 496 */     if (rootObj == null) {
/* 497 */       throw new HiException("220204", configName);
/*     */     }
/*     */ 
/* 500 */     HiBatchProcess pro = (HiBatchProcess)rootObj.getChildsMap().get(attrValue);
/*     */ 
/* 502 */     if (pro == null) {
/* 503 */       throw new HiException("220204", attrValue);
/*     */     }
/*     */ 
/* 513 */     HiFile file = (HiFile)ctx.getBaseSource(objectName);
/* 514 */     String data = file.readLine();
/* 515 */     if (data == null) {
/* 516 */       return 1;
/*     */     }
/*     */ 
/* 519 */     String tmpPlainOffset = ctx.getCurrentMsg().getHeadItem("PlainOffset");
/* 520 */     HiByteBuffer buffer = new HiByteBuffer(data.getBytes());
/* 521 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 522 */     unpack(pro, ctx, etf, buffer);
/* 523 */     if (tmpPlainOffset == null)
/* 524 */       ctx.getCurrentMsg().delHeadItem("PlainOffset");
/*     */     else {
/* 526 */       ctx.getCurrentMsg().setHeadItem("PlainOffset", tmpPlainOffset);
/*     */     }
/* 528 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int ReadFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 546 */     String fieldName = HiArgUtils.getStringNotNull(argsMap, "FieldName");
/* 547 */     String objectName = argsMap.get("ObjectName");
/* 548 */     if (StringUtils.isEmpty(objectName))
/* 549 */       objectName = "PUB_FILEPOINTER";
/* 550 */     HiFile file = (HiFile)ctx.getBaseSource(objectName);
/* 551 */     int len = argsMap.getInt("ReadLen");
/* 552 */     if (len <= 0) {
/* 553 */       throw new HiAppException(-1, "220026", "ReadLen");
/*     */     }
/* 555 */     StringBuffer buffer = new StringBuffer();
/* 556 */     int ret = file.readBytes(buffer, len);
/* 557 */     ctx.getCurrentMsg().getETFBody().setChildValue(fieldName, buffer.toString());
/*     */ 
/* 561 */     if (ret == -1) {
/* 562 */       return 1;
/*     */     }
/* 564 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int ReadFileAndParse(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 590 */     String configName = HiArgUtils.getStringNotNull(argsMap, "ConfigName");
/* 591 */     String rootName = HiArgUtils.getStringNotNull(argsMap, "RootName");
/* 592 */     String nodeName = argsMap.get("NodeName");
/* 593 */     String attrName = argsMap.get("AttrName");
/* 594 */     String attrValue = argsMap.get("AttrValue");
/* 595 */     String objectName = argsMap.get("ObjectName");
/* 596 */     if (StringUtils.isEmpty(objectName)) {
/* 597 */       objectName = "PUB_FILEPOINTER";
/*     */     }
/* 599 */     int maxLen = argsMap.getInt("MaxLen");
/* 600 */     HiDelegate rootObj = (HiDelegate)ctx.getProperty("CONFIGDECLARE", configName);
/*     */ 
/* 603 */     if (rootObj == null) {
/* 604 */       throw new HiException("220204", configName);
/*     */     }
/*     */ 
/* 607 */     HiBatchProcess pro = (HiBatchProcess)rootObj.getChildsMap().get(attrValue);
/*     */ 
/* 609 */     if (pro == null) {
/* 610 */       throw new HiException("220204", attrValue);
/*     */     }
/*     */ 
/* 613 */     HiFile file = (HiFile)ctx.getBaseSource(objectName);
/*     */ 
/* 615 */     StringBuffer dataBuffer = new StringBuffer();
/* 616 */     int ret = file.read(dataBuffer, maxLen);
/* 617 */     System.out.println(dataBuffer);
/* 618 */     if (ret == -1) {
/* 619 */       return 1;
/*     */     }
/*     */ 
/* 622 */     HiByteBuffer buffer = new HiByteBuffer(dataBuffer.toString().getBytes());
/* 623 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 624 */     String tmpPlainOffset = ctx.getCurrentMsg().getHeadItem("PlainOffset");
/* 625 */     unpack(pro, ctx, etf, buffer);
/* 626 */     if (tmpPlainOffset == null)
/* 627 */       ctx.getCurrentMsg().delHeadItem("PlainOffset");
/*     */     else {
/* 629 */       ctx.getCurrentMsg().setHeadItem("PlainOffset", tmpPlainOffset);
/*     */     }
/*     */ 
/* 632 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int GetFileFromDir(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 650 */     String dir = HiArgUtils.getStringNotNull(argsMap, "Directory");
/* 651 */     dir = HiArgUtils.absoutePath(dir);
/* 652 */     String fieldName = HiArgUtils.getStringNotNull(argsMap, "FieldName");
/* 653 */     String separator = HiArgUtils.getStringNotNull(argsMap, "Separator");
/* 654 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 655 */     etf.setChildValue(fieldName, StringUtils.join(new File(dir).list(), separator));
/*     */ 
/* 657 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int ReadXmlConfig(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 685 */     String configName = HiArgUtils.getStringNotNull(argsMap, "ConfigName");
/* 686 */     String nodeName = argsMap.get("NodeName");
/* 687 */     if (StringUtils.isEmpty(nodeName))
/* 688 */       nodeName = "Config";
/* 689 */     String attrName = argsMap.get("AttrName");
/* 690 */     if (StringUtils.isEmpty(attrName))
/* 691 */       attrName = "value";
/* 692 */     String value = HiArgUtils.getStringNotNull(argsMap, "Value");
/* 693 */     Element root = (Element)ctx.getProperty("CONFIGDECLARE", configName);
/*     */ 
/* 695 */     if (root == null);
/* 698 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 699 */     Iterator iter = root.elementIterator(nodeName);
/* 700 */     while ((iter != null) && (iter.hasNext())) {
/* 701 */       Element element = (Element)iter.next();
/* 702 */       if ((!(element.getName().startsWith("#"))) && (StringUtils.equals(element.attributeValue(attrName), value)))
/*     */       {
/* 705 */         Iterator iter1 = element.elementIterator();
/* 706 */         while ((iter1 != null) && (iter1.hasNext())) {
/* 707 */           Element element1 = (Element)iter1.next();
/* 708 */           etf.setChildValue(element1.getName(), element1.getTextTrim());
/*     */         }
/*     */ 
/* 711 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 715 */     return 0;
/*     */   }
/*     */ 
/*     */   private static String ESCFormat(String str) {
/* 719 */     HiByteBuffer buffer = new HiByteBuffer(str.length());
/* 720 */     for (int i = 0; i < str.length(); ++i)
/* 721 */       if (str.charAt(i) == '\\') {
/* 722 */         ++i;
/* 723 */         if (i == str.length())
/* 724 */           buffer.append(str.charAt(i - 1));
/*     */       }
/*     */       else
/*     */       {
/* 728 */         switch (str.charAt(i))
/*     */         {
/*     */         case 't':
/* 730 */           buffer.append(9);
/* 731 */           break;
/*     */         case 'n':
/* 733 */           buffer.append(10);
/* 734 */           break;
/*     */         case 'f':
/* 736 */           buffer.append(12);
/* 737 */           break;
/*     */         case 'r':
/* 739 */           buffer.append(13);
/* 740 */           break;
/*     */         case '\\':
/* 742 */           buffer.append(92);
/* 743 */           break;
/*     */         default:
/* 746 */           if ((i + 2 < str.length()) && (CharUtils.isAsciiNumeric(str.charAt(i))) && (CharUtils.isAsciiNumeric(str.charAt(i + 1))) && (CharUtils.isAsciiNumeric(str.charAt(i + 2))))
/*     */           {
/* 750 */             buffer.append((char)Integer.valueOf(str.substring(i, i + 3), 8).intValue());
/*     */ 
/* 752 */             i += 3;
/*     */           } else {
/* 754 */             i -= 1;
/* 755 */             buffer.append(str.charAt(i));
/*     */ 
/* 757 */             continue;
/*     */ 
/* 761 */             buffer.append(str.charAt(i)); } }
/*     */       }
/* 763 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   private static void unpack(HiEngineModel model, HiMessageContext ctx, HiETF etf, HiByteBuffer buffer) throws HiException
/*     */   {
/* 768 */     HiMessage msg = ctx.getCurrentMsg();
/* 769 */     HiETF root = msg.getETFBody();
/* 770 */     msg.setBody(etf);
/* 771 */     HiMessageHelper.setUnpackMessage(msg, buffer);
/*     */     try {
/* 773 */       model.process(ctx);
/*     */     } finally {
/* 775 */       msg.setBody(root);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void pack(HiEngineModel model, HiMessageContext ctx, HiETF etf, HiByteBuffer buffer) throws HiException
/*     */   {
/* 781 */     HiMessage msg = ctx.getCurrentMsg();
/* 782 */     HiETF root = msg.getETFBody();
/* 783 */     msg.setBody(etf);
/* 784 */     HiMessageHelper.setPackMessage(msg, buffer);
/*     */     try {
/* 786 */       model.process(ctx);
/*     */     } finally {
/* 788 */       msg.setBody(root);
/*     */     }
/*     */   }
/*     */ }