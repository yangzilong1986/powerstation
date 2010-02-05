 package com.hisun.mng;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.register.HiRegisterService;
 import com.hisun.register.HiServiceObject;
 import com.hisun.util.HiICSProperty;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Set;
 import java.util.jar.JarFile;
 import java.util.zip.ZipEntry;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class HiApplicationManage
 {
   public int oneAppQuery(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String name;
     String code;
     String curNod = args.get("CurNod");
     if (StringUtils.isBlank(curNod)) {
       curNod = "ROOT";
     }
     String appNam = HiArgUtils.getStringNotNull(args, "AppNam");
 
     HiETF root = ctx.getCurrentMsg().getETFBody();
     ArrayList list = HiRegisterService.listServices("");
     HashMap appMap = new HashMap();
     for (int i = 0; i < list.size(); ++i) {
       HiServiceObject serivceObject = (HiServiceObject)list.get(i);
       if (StringUtils.isBlank(serivceObject.getAppName())) {
         continue;
       }
       appMap.put(serivceObject.getAppName(), serivceObject.getAppCode());
     }
     Iterator iter = appMap.keySet().iterator();
     boolean founded = false;
     do { if (!(iter.hasNext())) break label235;
       name = (String)iter.next();
       code = (String)appMap.get(name); }
     while (!(StringUtils.equalsIgnoreCase(appNam, name)));
 
     root.setGrandChildNode(curNod + ".APP_STS", "1");
     root.setGrandChildNode(curNod + ".APP_CD", code);
     founded = true;
 
     if (!(founded)) {
       label235: root.setGrandChildNode(curNod + ".APP_STS", "0");
     }
     return 0;
   }
 
   public int appQuery(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String grpNam = args.get("GrpNam");
     if (StringUtils.isBlank(grpNam)) {
       grpNam = "GRP";
     }
     HiETF root = ctx.getCurrentMsg().getETFBody();
     ArrayList list = HiRegisterService.listServices("");
     HashMap appMap = new HashMap();
     for (int i = 0; i < list.size(); ++i) {
       HiServiceObject serivceObject = (HiServiceObject)list.get(i);
       if (StringUtils.isBlank(serivceObject.getAppName())) {
         continue;
       }
       appMap.put(serivceObject.getAppName(), serivceObject.getAppCode());
     }
     Iterator iter = appMap.keySet().iterator();
     int k = 1;
     while (iter.hasNext()) {
       String name = (String)iter.next();
       String code = (String)appMap.get(name);
       HiETF grpNod = root.addNode(grpNam + "_" + k);
       grpNod.setChildValue("APP_ID", name);
       grpNod.setChildValue("APP_CD", code);
       grpNod.setChildValue("APP_DESC", code);
       ++k;
     }
     root.setChildValue(grpNam + "_NUM", String.valueOf(k - 1));
     return 0;
   }
 
   public int appVerLst(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String appNam = HiArgUtils.getStringNotNull(args, "AppNam");
     String grpNam = args.get("GrpNam");
     if (StringUtils.isBlank(grpNam)) {
       grpNam = "GRP";
     }
     HiETF root = ctx.getCurrentMsg().getETFBody();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     ArrayList appInfos = new ArrayList();
     int k = 1;
     String appInfoFile = HiICSProperty.getWorkDir() + "/app/" + appNam + "/APP_INF.XML";
     File f = new File(appInfoFile);
     if (!(f.exists())) {
       throw new HiException("213302", appInfoFile);
     }
     FileInputStream fis = null;
     try {
       fis = new FileInputStream(f);
       HiETF grpNod = root.addNode(grpNam + "_" + k);
       ++k;
       doAppVerLst(grpNod, fis);
     } catch (FileNotFoundException e1) {
     }
     finally {
       if (fis != null) {
         try {
           fis.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
       }
 
     }
 
     File dir = new File(HiICSProperty.getWorkDir() + "/tmp/har/" + appNam + "_*.har");
     String[] files = dir.list();
     for (int i = 0; i < files.length; ++i) {
       JarFile jarFile = null;
       try {
         jarFile = new JarFile(files[i]);
       } catch (IOException e) {
         throw new HiException("213302", files[i], e);
       }
       ZipEntry zipEntry = jarFile.getEntry("APP_INF.XML");
       if (zipEntry == null) {
         try {
           jarFile.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
         log.info(files[i] + " is illegal har");
       }
       else
       {
         try {
           HiETF grpNod = root.addNode(grpNam + "_" + k);
           ++k;
           doAppVerLst(grpNod, jarFile.getInputStream(zipEntry));
         } catch (IOException grpNod) {
         }
         finally {
           try {
             jarFile.close();
           } catch (IOException e) {
             e.printStackTrace(); }
         }
       }
     }
     return 0;
   }
 
   private void doAppVerLst(HiETF grpNod, InputStream is) throws HiException {
     SAXReader reader = new SAXReader();
     Document doc = null;
     try {
       doc = reader.read(is);
     } catch (DocumentException e) {
       throw new HiException("213319", "APP_INF.XML", e);
     }
     Element root = doc.getRootElement();
     Element element1 = root.element("Date");
     if (element1 != null) {
       grpNod.setChildValue("DATE", element1.getTextTrim());
     }
     element1 = root.element("Author");
     if (element1 != null) {
       grpNod.setChildValue("AUTHOR", element1.getTextTrim());
     }
     element1 = root.element("Version");
     if (element1 != null) {
       grpNod.setChildValue("VERSION", element1.getTextTrim());
     }
     element1 = root.element("Description");
     if (element1 != null)
       grpNod.setChildValue("DESC", element1.getTextTrim());
   }
 }