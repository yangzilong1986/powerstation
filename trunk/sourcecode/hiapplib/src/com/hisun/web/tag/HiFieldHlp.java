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
/* 22 */     this._fieldHlpItems = new ArrayList();
/*    */ 
/* 27 */     this.lastModified = -1L;
/*    */ 
/* 31 */     this.lastAccessTime = -1L;
/*    */   }
/*    */ 
/*    */   public static synchronized HiFieldHlp getInstance()
/*    */   {
/* 36 */     if (instance == null) {
/* 37 */       instance = new HiFieldHlp();
/*    */     }
/* 39 */     return instance;
/*    */   }
/*    */ 
/*    */   public synchronized void init(String file) throws DocumentException, MalformedURLException {
/* 43 */     if (!(isNeedReload(file))) {
/* 44 */       return;
/*    */     }
/* 46 */     SAXReader saxReader = new SAXReader();
/*    */ 
/* 48 */     Document doc = saxReader.read(file);
/* 49 */     Element root = doc.getRootElement();
/* 50 */     Iterator iter = root.elementIterator();
/* 51 */     this._fieldHlpItems.clear();
/* 52 */     while (iter.hasNext()) {
/* 53 */       HiFieldHlpItem fieldHlpItem = new HiFieldHlpItem();
/* 54 */       Element element = (Element)iter.next();
/* 55 */       String name = element.attributeValue("name");
/* 56 */       fieldHlpItem.name = name;
/* 57 */       Iterator iter1 = element.elementIterator();
/* 58 */       while (iter1.hasNext()) {
/* 59 */         Element element1 = (Element)iter1.next();
/* 60 */         String _str1 = element1.attributeValue("name");
/* 61 */         String _str2 = element1.attributeValue("value");
/* 62 */         fieldHlpItem._map.put(_str1, _str2);
/*    */       }
/* 64 */       add(fieldHlpItem);
/*    */     }
/*    */   }
/*    */ 
/*    */   private void add(HiFieldHlpItem item) {
/* 69 */     this._fieldHlpItems.add(item);
/*    */   }
/*    */ 
/*    */   public HiFieldHlpItem get(String name)
/*    */   {
/* 74 */     for (HiFieldHlpItem item : this._fieldHlpItems) {
/* 75 */       if (StringUtils.equalsIgnoreCase(item.name, name))
/* 76 */         return item;
/*    */     }
/* 78 */     return null;
/*    */   }
/*    */ 
/*    */   private boolean isNeedReload(String file) {
/* 82 */     if (System.currentTimeMillis() - this.lastAccessTime < 1000L) {
/* 83 */       return false;
/*    */     }
/* 85 */     File f = new File(file);
/* 86 */     if (this.lastModified != f.lastModified()) {
/* 87 */       this.lastModified = f.lastModified();
/* 88 */       return true;
/*    */     }
/* 90 */     return false;
/*    */   }
/*    */ }