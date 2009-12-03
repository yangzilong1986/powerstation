/*     */ package com.hisun.mng;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.zip.ZipEntry;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiApplicationManage
/*     */ {
/*     */   public int oneAppQuery(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     String name;
/*     */     String code;
/*  56 */     String curNod = args.get("CurNod");
/*  57 */     if (StringUtils.isBlank(curNod)) {
/*  58 */       curNod = "ROOT";
/*     */     }
/*  60 */     String appNam = HiArgUtils.getStringNotNull(args, "AppNam");
/*     */ 
/*  62 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/*  63 */     ArrayList list = HiRegisterService.listServices("");
/*  64 */     HashMap appMap = new HashMap();
/*  65 */     for (int i = 0; i < list.size(); ++i) {
/*  66 */       HiServiceObject serivceObject = (HiServiceObject)list.get(i);
/*  67 */       if (StringUtils.isBlank(serivceObject.getAppName())) {
/*     */         continue;
/*     */       }
/*  70 */       appMap.put(serivceObject.getAppName(), serivceObject.getAppCode());
/*     */     }
/*  72 */     Iterator iter = appMap.keySet().iterator();
/*  73 */     boolean founded = false;
/*     */     do { if (!(iter.hasNext())) break label235;
/*  75 */       name = (String)iter.next();
/*  76 */       code = (String)appMap.get(name); }
/*  77 */     while (!(StringUtils.equalsIgnoreCase(appNam, name)));
/*     */ 
/*  80 */     root.setGrandChildNode(curNod + ".APP_STS", "1");
/*  81 */     root.setGrandChildNode(curNod + ".APP_CD", code);
/*  82 */     founded = true;
/*     */ 
/*  86 */     if (!(founded)) {
/*  87 */       label235: root.setGrandChildNode(curNod + ".APP_STS", "0");
/*     */     }
/*  89 */     return 0;
/*     */   }
/*     */ 
/*     */   public int appQuery(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  99 */     String grpNam = args.get("GrpNam");
/* 100 */     if (StringUtils.isBlank(grpNam)) {
/* 101 */       grpNam = "GRP";
/*     */     }
/* 103 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 104 */     ArrayList list = HiRegisterService.listServices("");
/* 105 */     HashMap appMap = new HashMap();
/* 106 */     for (int i = 0; i < list.size(); ++i) {
/* 107 */       HiServiceObject serivceObject = (HiServiceObject)list.get(i);
/* 108 */       if (StringUtils.isBlank(serivceObject.getAppName())) {
/*     */         continue;
/*     */       }
/* 111 */       appMap.put(serivceObject.getAppName(), serivceObject.getAppCode());
/*     */     }
/* 113 */     Iterator iter = appMap.keySet().iterator();
/* 114 */     int k = 1;
/* 115 */     while (iter.hasNext()) {
/* 116 */       String name = (String)iter.next();
/* 117 */       String code = (String)appMap.get(name);
/* 118 */       HiETF grpNod = root.addNode(grpNam + "_" + k);
/* 119 */       grpNod.setChildValue("APP_ID", name);
/* 120 */       grpNod.setChildValue("APP_CD", code);
/* 121 */       grpNod.setChildValue("APP_DESC", code);
/* 122 */       ++k;
/*     */     }
/* 124 */     root.setChildValue(grpNam + "_NUM", String.valueOf(k - 1));
/* 125 */     return 0;
/*     */   }
/*     */ 
/*     */   public int appVerLst(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 137 */     String appNam = HiArgUtils.getStringNotNull(args, "AppNam");
/* 138 */     String grpNam = args.get("GrpNam");
/* 139 */     if (StringUtils.isBlank(grpNam)) {
/* 140 */       grpNam = "GRP";
/*     */     }
/* 142 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 143 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 144 */     ArrayList appInfos = new ArrayList();
/* 145 */     int k = 1;
/* 146 */     String appInfoFile = HiICSProperty.getWorkDir() + "/app/" + appNam + "/APP_INF.XML";
/* 147 */     File f = new File(appInfoFile);
/* 148 */     if (!(f.exists())) {
/* 149 */       throw new HiException("213302", appInfoFile);
/*     */     }
/* 151 */     FileInputStream fis = null;
/*     */     try {
/* 153 */       fis = new FileInputStream(f);
/* 154 */       HiETF grpNod = root.addNode(grpNam + "_" + k);
/* 155 */       ++k;
/* 156 */       doAppVerLst(grpNod, fis);
/*     */     } catch (FileNotFoundException e1) {
/*     */     }
/*     */     finally {
/* 160 */       if (fis != null) {
/*     */         try {
/* 162 */           fis.close();
/*     */         } catch (IOException e) {
/* 164 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 170 */     File dir = new File(HiICSProperty.getWorkDir() + "/tmp/har/" + appNam + "_*.har");
/* 171 */     String[] files = dir.list();
/* 172 */     for (int i = 0; i < files.length; ++i) {
/* 173 */       JarFile jarFile = null;
/*     */       try {
/* 175 */         jarFile = new JarFile(files[i]);
/*     */       } catch (IOException e) {
/* 177 */         throw new HiException("213302", files[i], e);
/*     */       }
/* 179 */       ZipEntry zipEntry = jarFile.getEntry("APP_INF.XML");
/* 180 */       if (zipEntry == null) {
/*     */         try {
/* 182 */           jarFile.close();
/*     */         } catch (IOException e) {
/* 184 */           e.printStackTrace();
/*     */         }
/* 186 */         log.info(files[i] + " is illegal har");
/*     */       }
/*     */       else
/*     */       {
/*     */         try {
/* 191 */           HiETF grpNod = root.addNode(grpNam + "_" + k);
/* 192 */           ++k;
/* 193 */           doAppVerLst(grpNod, jarFile.getInputStream(zipEntry));
/*     */         } catch (IOException grpNod) {
/*     */         }
/*     */         finally {
/*     */           try {
/* 198 */             jarFile.close();
/*     */           } catch (IOException e) {
/* 200 */             e.printStackTrace(); }
/*     */         }
/*     */       }
/*     */     }
/* 204 */     return 0;
/*     */   }
/*     */ 
/*     */   private void doAppVerLst(HiETF grpNod, InputStream is) throws HiException {
/* 208 */     SAXReader reader = new SAXReader();
/* 209 */     Document doc = null;
/*     */     try {
/* 211 */       doc = reader.read(is);
/*     */     } catch (DocumentException e) {
/* 213 */       throw new HiException("213319", "APP_INF.XML", e);
/*     */     }
/* 215 */     Element root = doc.getRootElement();
/* 216 */     Element element1 = root.element("Date");
/* 217 */     if (element1 != null) {
/* 218 */       grpNod.setChildValue("DATE", element1.getTextTrim());
/*     */     }
/* 220 */     element1 = root.element("Author");
/* 221 */     if (element1 != null) {
/* 222 */       grpNod.setChildValue("AUTHOR", element1.getTextTrim());
/*     */     }
/* 224 */     element1 = root.element("Version");
/* 225 */     if (element1 != null) {
/* 226 */       grpNod.setChildValue("VERSION", element1.getTextTrim());
/*     */     }
/* 228 */     element1 = root.element("Description");
/* 229 */     if (element1 != null)
/* 230 */       grpNod.setChildValue("DESC", element1.getTextTrim());
/*     */   }
/*     */ }