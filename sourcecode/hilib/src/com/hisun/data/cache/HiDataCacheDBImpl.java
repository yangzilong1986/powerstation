/*     */ package com.hisun.data.cache;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.beanutils.BeanUtils;
/*     */ 
/*     */ public class HiDataCacheDBImpl
/*     */   implements HiDataCache
/*     */ {
/*     */   private HiDBUtil dbUtil;
/*     */   private HiDataCacheConfigItem configItem;
/*     */   private ArrayList dataList;
/*     */   private HashMap dataMap;
/*     */   private Logger log;
/*     */ 
/*     */   public HiDataCacheDBImpl()
/*     */   {
/*  27 */     this.log = HiLog.getLogger("SYS.trc");
/*     */   }
/*     */ 
/*     */   public void validate()
/*     */     throws HiException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void load()
/*     */     throws HiException
/*     */   {
/*     */     HashMap record;
/*  33 */     List list = this.dbUtil.execQuery(this.configItem.getSql());
/*  34 */     String className = this.configItem.getClassName();
/*  35 */     if (className == null) {
/*  36 */       this.dataList = ((ArrayList)list);
/*  37 */       return;
/*     */     }
/*     */ 
/*  40 */     if ("java.util.HashMap".equals(className)) {
/*     */       Object o;
/*     */       try {
/*  43 */         o = this.configItem.getNewObject();
/*     */       } catch (Exception e1) {
/*  45 */         throw new HiException(e1);
/*     */       }
/*  47 */       this.dataMap = ((HashMap)o);
/*  48 */       for (int i = 0; i < list.size(); ++i) {
/*  49 */         record = (HashMap)list.get(i);
/*  50 */         Iterator iter = this.configItem.getColMaps().keySet().iterator();
/*  51 */         if (!(iter.hasNext()))
/*     */           continue;
/*  53 */         String key = (String)iter.next();
/*  54 */         String value1 = (String)record.get(key);
/*  55 */         if (!(iter.hasNext()))
/*     */           continue;
/*  57 */         key = (String)iter.next();
/*  58 */         String value2 = (String)record.get(key);
/*  59 */         this.dataMap.put(value1, value2);
/*     */       }
/*  61 */       if (this.log.isDebugEnabled()) {
/*  62 */         this.log.debug(this.dataMap);
/*     */       }
/*  64 */       return;
/*     */     }
/*     */ 
/*  67 */     this.dataList = new ArrayList();
/*  68 */     for (int i = 0; i < list.size(); ++i)
/*     */       try {
/*  70 */         Object o = this.configItem.getNewObject();
/*  71 */         record = (HashMap)list.get(i);
/*  72 */         HashMap colsMap = this.configItem.getColMaps();
/*  73 */         Iterator iter = colsMap.keySet().iterator();
/*  74 */         while (iter.hasNext()) {
/*  75 */           String srcCol = (String)iter.next();
/*  76 */           String dstCol = (String)colsMap.get(srcCol);
/*  77 */           String value = (String)record.get(srcCol);
/*  78 */           if (value == null) {
/*     */             continue;
/*     */           }
/*  81 */           record.remove(srcCol);
/*  82 */           record.put(dstCol, value);
/*     */         }
/*  84 */         BeanUtils.populate(o, record);
/*  85 */         this.dataList.add(o);
/*     */       } catch (Exception e) {
/*  87 */         this.log.error(e, e);
/*  88 */         throw new HiException(e);
/*     */       }
/*     */   }
/*     */ 
/*     */   public HiDBUtil getDbUtil()
/*     */   {
/*  94 */     return this.dbUtil;
/*     */   }
/*     */ 
/*     */   public void setDbUtil(HiDBUtil dbUtil) {
/*  98 */     this.dbUtil = dbUtil;
/*     */   }
/*     */ 
/*     */   public HiDataCacheConfigItem getConfigItem() {
/* 102 */     return this.configItem;
/*     */   }
/*     */ 
/*     */   public void setConfigItem(HiDataCacheConfigItem configItem) {
/* 106 */     this.configItem = configItem;
/*     */   }
/*     */ 
/*     */   public ArrayList getDataList() {
/* 110 */     return this.dataList;
/*     */   }
/*     */ 
/*     */   public String getValue(String name) {
/* 114 */     if (this.dataMap == null) {
/* 115 */       return null;
/*     */     }
/* 117 */     return ((String)this.dataMap.get(name));
/*     */   }
/*     */ }