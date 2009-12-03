/*    */ package com.hisun.web.tag;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.net.MalformedURLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class HiFieldHlp
/*    */ {
/*    */   public ArrayList<HiFieldHlpItem> _fieldHlpItems;
/*    */   public static final String FILE = "fieldhlp.xml";
/*    */   private long lastModified;
/*    */   private long lastAccessTime;
/*    */   private static HiFieldHlp instance;
/*    */ 
/*    */   public HiFieldHlp()
/*    */   {
/* 24 */     this._fieldHlpItems = new ArrayList();
/*    */ 
/* 29 */     this.lastModified = -1L;
/*    */ 
/* 33 */     this.lastAccessTime = -1L;
/*    */   }
/*    */ 
/*    */   public static synchronized HiFieldHlp getInstance()
/*    */   {
/* 38 */     if (instance == null) {
/* 39 */       instance = new HiFieldHlp();
/*    */     }
/* 41 */     return instance;
/*    */   }
/*    */ 
/*    */   public synchronized void init(String file) throws DocumentException, MalformedURLException {
/* 45 */     if (!(isNeedReload(file))) {
/* 46 */       return;
/*    */     }
/* 48 */     SAXReader saxReader = new SAXReader();
/*    */ 
/* 50 */     Document doc = saxReader.read(file);
/* 51 */     Element root = doc.getRootElement();
/* 52 */     Iterator iter = root.elementIterator();
/* 53 */     this._fieldHlpItems.clear();
/* 54 */     while (iter.hasNext()) {
/* 55 */       HiFieldHlpItem fieldHlpItem = new HiFieldHlpItem();
/* 56 */       Element element = (Element)iter.next();
/* 57 */       String name = element.attributeValue("name");
/* 58 */       fieldHlpItem.name = name;
/* 59 */       Iterator iter1 = element.elementIterator();
/* 60 */       while (iter1.hasNext()) {
/* 61 */         Element element1 = (Element)iter1.next();
/* 62 */         String _str1 = element1.attributeValue("name");
/* 63 */         String _str2 = element1.attributeValue("value");
/* 64 */         fieldHlpItem._map.put(_str1, _str2);
/*    */       }
/* 66 */       add(fieldHlpItem);
/*    */     }
/*    */   }
/*    */ 
/*    */   private void add(HiFieldHlpItem item) {
/* 71 */     this._fieldHlpItems.add(item);
/*    */   }
/*    */ 
/*    */   public HiFieldHlpItem get(String name)
/*    */   {
/* 76 */     for (HiFieldHlpItem item : this._fieldHlpItems) {
/* 77 */       if (StringUtils.equalsIgnoreCase(item.name, name))
/* 78 */         return item;
/*    */     }
/* 80 */     return null;
/*    */   }
/*    */ 
/*    */   private boolean isNeedReload(String file) {
/* 84 */     if (System.currentTimeMillis() - this.lastAccessTime < 1000L) {
/* 85 */       return false;
/*    */     }
/* 87 */     File f = new File(file);
/* 88 */     if (this.lastModified != f.lastModified()) {
/* 89 */       this.lastModified = f.lastModified();
/* 90 */       return true;
/*    */     }
/* 92 */     return false;
/*    */   }
/*    */ }