/*     */ package com.hisun.data.cache;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.parse.HiPretreatment;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.digester.Digester;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiDataCacheConfig
/*     */ {
/*     */   private Logger log;
/*     */   private HashMap<String, HiDataCacheConfigItem> configItems;
/*     */   private HashMap<String, HiDataCache> dataItems;
/*     */   private String dsName;
/*  33 */   private static HiDataCacheConfig dataCacheConfig = null;
/*     */ 
/*     */   public HiDataCacheConfig()
/*     */   {
/*  28 */     this.log = HiLog.getLogger("SYS.trc");
/*  29 */     this.configItems = new HashMap();
/*  30 */     this.dataItems = new HashMap();
/*     */   }
/*     */ 
/*     */   public static HiDataCacheConfig getInstance()
/*     */   {
/*  35 */     if (dataCacheConfig == null) {
/*  36 */       dataCacheConfig = new HiDataCacheConfig();
/*     */     }
/*  38 */     return dataCacheConfig;
/*     */   }
/*     */ 
/*     */   public static void setInstance(HiDataCacheConfig dataCacheConfig1) {
/*  42 */     dataCacheConfig = dataCacheConfig1;
/*     */   }
/*     */ 
/*     */   public void addItem(HiDataCacheConfigItem item) {
/*  46 */     if (this.log.isDebugEnabled()) {
/*  47 */       this.log.debug("addItem:[" + item.toString() + "]");
/*     */     }
/*  49 */     this.configItems.put(item.getId(), item);
/*     */   }
/*     */ 
/*     */   public void validate(String id, HiDBUtil dbUtil) throws HiException {
/*  53 */     HiDataCacheConfigItem item = (HiDataCacheConfigItem)this.configItems.get(id);
/*  54 */     if (item == null) {
/*  55 */       throw new HiException("id:[ " + id + "] not existed");
/*     */     }
/*     */ 
/*  58 */     if (this.log.isInfoEnabled()) {
/*  59 */       this.log.info("validate:[" + id + "]");
/*     */     }
/*     */ 
/*  62 */     HiDataCacheDBImpl dataCache = new HiDataCacheDBImpl();
/*  63 */     dbUtil.setDsName(this.dsName);
/*  64 */     dataCache.setDbUtil(dbUtil);
/*  65 */     dataCache.setConfigItem(item);
/*  66 */     dataCache.load();
/*  67 */     this.dataItems.put(id, dataCache);
/*  68 */     if (this.log.isInfoEnabled())
/*  69 */       this.log.info("validate:[" + id + "] succeed");
/*     */   }
/*     */ 
/*     */   public HiDataCache getDataCache(String id)
/*     */   {
/*  74 */     return ((HiDataCache)this.dataItems.get(id));
/*     */   }
/*     */ 
/*     */   public HashMap<String, HiDataCache> getDataMap() {
/*  78 */     return this.dataItems;
/*     */   }
/*     */ 
/*     */   public void process(HiDBUtil dbUtil) throws HiException {
/*  82 */     Iterator iter = this.configItems.keySet().iterator();
/*  83 */     while (iter.hasNext()) {
/*  84 */       HiDataCacheConfigItem item = (HiDataCacheConfigItem)this.configItems.get(iter.next());
/*  85 */       HiDataCacheDBImpl dataCache = new HiDataCacheDBImpl();
/*  86 */       dbUtil.setDsName(this.dsName);
/*  87 */       dataCache.setDbUtil(dbUtil);
/*  88 */       dataCache.setConfigItem(item);
/*  89 */       dataCache.load();
/*  90 */       this.dataItems.put(item.getId(), dataCache);
/*  91 */       this.log.info("load:[" + item.getId() + "] succeed"); }
/*     */   }
/*     */ 
/*     */   public static HiDataCacheConfig loadFile(String configFile) throws HiException {
/*  95 */     InputStream is = HiResource.getResourceAsStream(configFile);
/*  96 */     if (is == null)
/*     */     {
/*  98 */       throw new HiException("文件:[" + configFile + "]不存在!");
/*     */     }
/* 100 */     return loadStream(is);
/*     */   }
/*     */ 
/*     */   public static HiDataCacheConfig loadStream(InputStream is)
/*     */     throws HiException
/*     */   {
/*     */     Document document;
/* 107 */     SAXReader saxReader = new SAXReader();
/*     */     try
/*     */     {
/* 110 */       document = saxReader.read(is);
/*     */     } catch (DocumentException e) {
/* 112 */       throw new HiException(e);
/*     */     }
/* 114 */     Element rootNode = document.getRootElement();
/* 115 */     HashMap allElements = HiPretreatment.getAllElements(rootNode, null);
/* 116 */     HiPretreatment.parseInclude(allElements, document);
/* 117 */     String strXML = document.asXML();
/* 118 */     ByteArrayInputStream inFile = new ByteArrayInputStream(strXML.getBytes());
/*     */ 
/* 120 */     InputStreamReader in = new InputStreamReader(inFile);
/* 121 */     Digester digester = new Digester();
/* 122 */     digester.setValidating(false);
/* 123 */     digester.addObjectCreate("Config", "com.hisun.data.cache.HiDataCacheConfig");
/*     */ 
/* 125 */     digester.addSetProperties("Config");
/*     */ 
/* 127 */     digester.addObjectCreate("Config/Item", "com.hisun.data.cache.HiDataCacheConfigItem");
/*     */ 
/* 129 */     digester.addSetProperties("Config/Item");
/* 130 */     digester.addCallMethod("Config/Item/Col", "setColInfo", 2);
/* 131 */     digester.addCallParam("Config/Item/Col", 0, "SrcCol");
/* 132 */     digester.addCallParam("Config/Item/Col", 1, "DstCol");
/* 133 */     digester.addSetNext("Config/Item", "addItem", "com.hisun.data.cache.HiDataCacheConfigItem");
/*     */     try {
/* 135 */       return ((HiDataCacheConfig)digester.parse(in));
/*     */     } catch (Exception e) {
/* 137 */       e.printStackTrace();
/* 138 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 144 */     if (this.configItems != null)
/*     */     {
/* 146 */       this.configItems.clear();
/*     */     }
/* 148 */     if (this.dataItems == null)
/*     */       return;
/* 150 */     this.dataItems.clear();
/*     */   }
/*     */ 
/*     */   public String getDsName() {
/* 154 */     return this.dsName;
/*     */   }
/*     */ 
/*     */   public void setDsName(String dsName) {
/* 158 */     this.dsName = dsName;
/*     */   }
/*     */ }