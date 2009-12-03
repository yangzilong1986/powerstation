/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiConvert
/*     */ {
/*     */   private ArrayList<HiConvertItem> _convertItems;
/*     */   private long lastModified;
/*     */   private long lastAccessTime;
/*     */   private static HiConvert instance;
/*     */ 
/*     */   public HiConvert()
/*     */   {
/*  26 */     this._convertItems = new ArrayList();
/*     */ 
/*  30 */     this.lastModified = -1L;
/*     */ 
/*  34 */     this.lastAccessTime = -1L;
/*     */   }
/*     */ 
/*     */   public static synchronized HiConvert getInstance() {
/*  38 */     if (instance == null) {
/*  39 */       instance = new HiConvert();
/*     */     }
/*  41 */     return instance; }
/*     */ 
/*     */   public synchronized void init(String file) throws DocumentException, MalformedURLException {
/*  44 */     if (!(isNeedReload(file))) {
/*  45 */       return;
/*     */     }
/*  47 */     SAXReader saxReader = new SAXReader();
/*  48 */     Document doc = saxReader.read(file);
/*  49 */     Element root = doc.getRootElement();
/*  50 */     Iterator iter = root.elementIterator();
/*  51 */     this._convertItems.clear();
/*  52 */     while (iter.hasNext()) {
/*  53 */       HiConvertItem item = new HiConvertItem();
/*  54 */       Element element = (Element)iter.next();
/*  55 */       item.name = element.attributeValue("name");
/*  56 */       item.type = element.attributeValue("type");
/*  57 */       Iterator iter1 = element.elementIterator();
/*  58 */       while (iter1.hasNext()) {
/*  59 */         Element element1 = (Element)iter1.next();
/*  60 */         String _str1 = element1.attributeValue("name");
/*  61 */         String _str2 = element1.attributeValue("value");
/*  62 */         item._map.put(_str1, _str2);
/*     */       }
/*  64 */       this._convertItems.add(item);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String convert(String name, String value) {
/*  69 */     this.lastAccessTime = System.currentTimeMillis();
/*  70 */     HiConvertItem item = null;
/*  71 */     boolean founded = false;
/*  72 */     for (int i = 0; i < this._convertItems.size(); ++i) {
/*  73 */       item = (HiConvertItem)this._convertItems.get(i);
/*  74 */       if (StringUtils.equalsIgnoreCase(item.name, name)) {
/*  75 */         founded = true;
/*  76 */         break;
/*     */       }
/*     */     }
/*  79 */     if (!(founded)) {
/*  80 */       return value;
/*     */     }
/*     */ 
/*  83 */     Iterator iter = item._map.keySet().iterator();
/*  84 */     while (iter.hasNext()) {
/*  85 */       String key = (String)iter.next();
/*  86 */       if (StringUtils.equals(value, key)) {
/*  87 */         value = (String)item._map.get(key);
/*  88 */         break;
/*     */       }
/*     */     }
/*  91 */     if (!(iter.hasNext())) {
/*  92 */       if (item._map.containsKey("Default"))
/*  93 */         value = (String)item._map.get("Default");
/*     */       else {
/*  95 */         System.out.println("[" + name + "][" + value + "] not founded");
/*     */       }
/*     */     }
/*  98 */     if (StringUtils.equalsIgnoreCase(item.type, "IMAGE")) {
/*  99 */       return "<img src='" + value + "'/>";
/*     */     }
/* 101 */     return value;
/*     */   }
/*     */ 
/*     */   private boolean isNeedReload(String file) {
/* 105 */     if (System.currentTimeMillis() - this.lastAccessTime < 1000L) {
/* 106 */       return false;
/*     */     }
/* 108 */     File f = new File(file);
/* 109 */     if (this.lastModified != f.lastModified()) {
/* 110 */       this.lastModified = f.lastModified();
/* 111 */       return true;
/*     */     }
/* 113 */     return false;
/*     */   }
/*     */ }