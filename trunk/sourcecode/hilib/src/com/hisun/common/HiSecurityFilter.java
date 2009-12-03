/*     */ package com.hisun.common;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.io.File;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiSecurityFilter
/*     */ {
/*     */   private ArrayList _excludeList;
/*     */   private ArrayList _includeList;
/*     */   private URL _url;
/*     */   private long _lastModified;
/*     */   private String _file;
/*     */   private Logger _log;
/*     */ 
/*     */   public HiSecurityFilter()
/*     */   {
/*  26 */     this._excludeList = new ArrayList();
/*  27 */     this._includeList = new ArrayList();
/*  28 */     this._url = null;
/*  29 */     this._lastModified = -1L;
/*  30 */     this._file = null;
/*  31 */     this._log = HiLog.getLogger("SYS.trc"); }
/*     */ 
/*     */   public void setUrl(URL url) {
/*  34 */     this._url = url;
/*     */   }
/*     */ 
/*     */   public void setFile(String file) {
/*  38 */     this._file = file;
/*     */   }
/*     */ 
/*     */   public void load() throws HiException {
/*  42 */     loadFilterURL(this._url);
/*     */   }
/*     */ 
/*     */   public void loadFilterURL(URL url)
/*     */     throws HiException
/*     */   {
/*     */     Element element;
/*     */     String value;
/*  46 */     if (this._log.isDebugEnabled()) {
/*  47 */       this._log.debug("loadFilterURL:[" + url + "]");
/*     */     }
/*     */ 
/*  50 */     this._excludeList.clear();
/*  51 */     this._includeList.clear();
/*  52 */     this._url = url;
/*  53 */     SAXReader reader = new SAXReader();
/*  54 */     Document doc = null;
/*     */     try {
/*  56 */       doc = reader.read(url);
/*     */     } catch (Exception e) {
/*  58 */       throw new HiException(e);
/*     */     }
/*  60 */     Element root = doc.getRootElement();
/*  61 */     Element include = root.element("include");
/*  62 */     Element exclude = root.element("exclude");
/*  63 */     Iterator iter = null;
/*  64 */     if (include != null) {
/*  65 */       iter = include.elementIterator();
/*  66 */       while (iter.hasNext()) {
/*  67 */         element = (Element)iter.next();
/*     */ 
/*  69 */         value = element.attributeValue("value");
/*  70 */         if (StringUtils.isBlank(value)) {
/*     */           continue;
/*     */         }
/*  73 */         this._includeList.add(value);
/*     */       }
/*     */     }
/*     */ 
/*  77 */     if (exclude != null) {
/*  78 */       iter = exclude.elementIterator();
/*  79 */       while (iter.hasNext()) {
/*  80 */         element = (Element)iter.next();
/*     */ 
/*  82 */         value = element.attributeValue("value");
/*  83 */         if (StringUtils.isBlank(value)) {
/*     */           continue;
/*     */         }
/*  86 */         this._excludeList.add(value);
/*     */       }
/*     */     }
/*  89 */     if (this._log.isDebugEnabled())
/*  90 */       this._log.debug("exclude:" + this._excludeList + "; include:" + this._includeList);
/*     */   }
/*     */ 
/*     */   public void reloaded()
/*     */     throws HiException
/*     */   {
/*  96 */     if (isNeedReloaded(this._file))
/*  97 */       loadFilterURL(this._url);
/*     */   }
/*     */ 
/*     */   public boolean isNeedReloaded(String file)
/*     */   {
/* 102 */     if (this._log.isDebugEnabled()) {
/* 103 */       this._log.debug("file:[" + file + "]");
/*     */     }
/*     */ 
/* 106 */     File f = new File(file);
/* 107 */     this._log.info("lastModified:[" + f.lastModified() + "]");
/* 108 */     if (f.lastModified() != this._lastModified) {
/* 109 */       this._lastModified = f.lastModified();
/* 110 */       return true;
/*     */     }
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean contains(String value)
/*     */     throws HiException
/*     */   {
/*     */     String tmp;
/* 117 */     reloaded();
/*     */ 
/* 119 */     for (int i = 0; i < this._includeList.size(); ++i) {
/* 120 */       tmp = (String)this._includeList.get(i);
/* 121 */       if (this._log.isDebugEnabled()) {
/* 122 */         this._log.debug("include[" + value + "]:[" + tmp + "]");
/*     */       }
/* 124 */       if (value.matches(tmp)) {
/* 125 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 129 */     for (i = 0; i < this._excludeList.size(); ++i) {
/* 130 */       tmp = (String)this._excludeList.get(i);
/* 131 */       if (this._log.isDebugEnabled()) {
/* 132 */         this._log.debug("exclude: [" + value + "]:[" + tmp + "]");
/*     */       }
/* 134 */       if (value.matches(tmp)) {
/* 135 */         return false;
/*     */       }
/*     */     }
/* 138 */     if (this._log.isDebugEnabled()) {
/* 139 */       this._log.debug("contains: empty");
/*     */     }
/*     */ 
/* 142 */     return (this._includeList.size() != 0);
/*     */   }
/*     */ }