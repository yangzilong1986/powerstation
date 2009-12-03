/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.PageContext;
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
/*  24 */     this._convertItems = new ArrayList();
/*     */ 
/*  28 */     this.lastModified = -1L;
/*     */ 
/*  32 */     this.lastAccessTime = -1L;
/*     */   }
/*     */ 
/*     */   public static synchronized HiConvert getInstance() {
/*  36 */     if (instance == null) {
/*  37 */       instance = new HiConvert();
/*     */     }
/*  39 */     return instance; }
/*     */ 
/*     */   public synchronized void init(String file) throws DocumentException, MalformedURLException {
/*  42 */     if (!(isNeedReload(file))) {
/*  43 */       return;
/*     */     }
/*  45 */     SAXReader saxReader = new SAXReader();
/*  46 */     Document doc = saxReader.read(file);
/*  47 */     Element root = doc.getRootElement();
/*  48 */     Iterator iter = root.elementIterator();
/*  49 */     this._convertItems.clear();
/*  50 */     while (iter.hasNext()) {
/*  51 */       HiConvertItem item = new HiConvertItem();
/*  52 */       Element element = (Element)iter.next();
/*  53 */       item.name = element.attributeValue("name");
/*  54 */       item.type = element.attributeValue("type");
/*  55 */       Iterator iter1 = element.elementIterator();
/*  56 */       while (iter1.hasNext()) {
/*  57 */         Element element1 = (Element)iter1.next();
/*  58 */         String _str1 = element1.attributeValue("name");
/*  59 */         String _str2 = element1.attributeValue("value");
/*  60 */         item._map.put(_str1, _str2);
/*     */       }
/*  62 */       this._convertItems.add(item);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String convert(PageContext pageContext, String name, String value) {
/*  67 */     this.lastAccessTime = System.currentTimeMillis();
/*  68 */     HiConvertItem item = null;
/*  69 */     boolean founded = false;
/*  70 */     for (int i = 0; i < this._convertItems.size(); ++i) {
/*  71 */       item = (HiConvertItem)this._convertItems.get(i);
/*  72 */       if (StringUtils.equalsIgnoreCase(item.name, name)) {
/*  73 */         founded = true;
/*  74 */         break;
/*     */       }
/*     */     }
/*  77 */     if (!(founded)) {
/*  78 */       return value;
/*     */     }
/*     */ 
/*  81 */     Iterator iter = item._map.keySet().iterator();
/*  82 */     while (iter.hasNext()) {
/*  83 */       String key = (String)iter.next();
/*  84 */       if (StringUtils.equals(value, key)) {
/*  85 */         value = (String)item._map.get(key);
/*  86 */         break;
/*     */       }
/*     */     }
/*  89 */     if (!(iter.hasNext())) {
/*  90 */       if (item._map.containsKey("Default"))
/*  91 */         value = (String)item._map.get("Default");
/*     */       else {
/*  93 */         System.out.println("[" + name + "][" + value + "] not founded");
/*     */       }
/*     */     }
/*  96 */     if (StringUtils.equalsIgnoreCase(item.type, "IMAGE")) {
/*  97 */       HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
/*  98 */       value = StringUtils.replace(value, "${context_root}", request.getContextPath());
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