/*    */ package com.hisun.web.tag;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.net.MalformedURLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class HiDict
/*    */ {
/*    */   public ArrayList<HiDictItem> _dictItems;
/*    */   private long lastModified;
/*    */   private long lastAccessTime;
/*    */   private static HiDict instance;
/*    */ 
/*    */   public HiDict()
/*    */   {
/* 22 */     this._dictItems = new ArrayList();
/*    */ 
/* 26 */     this.lastModified = -1L;
/*    */ 
/* 30 */     this.lastAccessTime = -1L;
/*    */   }
/*    */ 
/*    */   public static synchronized HiDict getInstance() {
/* 34 */     if (instance == null) {
/* 35 */       instance = new HiDict();
/*    */     }
/* 37 */     return instance;
/*    */   }
/*    */ 
/*    */   public synchronized void init(String file) throws DocumentException, MalformedURLException {
/* 41 */     if (!(isNeedReload(file))) {
/* 42 */       return;
/*    */     }
/* 44 */     SAXReader saxReader = new SAXReader();
/* 45 */     Document doc = saxReader.read(file);
/* 46 */     Element root = doc.getRootElement();
/* 47 */     Iterator iter = root.elementIterator();
/* 48 */     this._dictItems.clear();
/* 49 */     while (iter.hasNext()) {
/* 50 */       HiDictItem dictItem = new HiDictItem();
/* 51 */       Element element = (Element)iter.next();
/*    */ 
/* 53 */       String name = element.attributeValue("name");
/* 54 */       dictItem.name = name;
/*    */ 
/* 56 */       int length = NumberUtils.toInt(element.attributeValue("length"));
/* 57 */       dictItem.length = length;
/*    */ 
/* 59 */       String type = element.attributeValue("type");
/* 60 */       dictItem.type = type;
/*    */ 
/* 62 */       int size = NumberUtils.toInt(element.attributeValue("size"));
/* 63 */       if (size == 0) {
/* 64 */         size = length;
/*    */       }
/* 66 */       dictItem.setSize(size);
/* 67 */       add(dictItem); }
/*    */   }
/*    */ 
/*    */   public void add(HiDictItem item) {
/* 71 */     this._dictItems.add(item);
/*    */   }
/*    */ 
/*    */   public HiDictItem get(String name)
/*    */   {
/* 77 */     for (HiDictItem item : this._dictItems) {
/* 78 */       if (StringUtils.equalsIgnoreCase(item.name, name))
/* 79 */         return item;
/*    */     }
/* 81 */     return null;
/*    */   }
/*    */ 
/*    */   private boolean isNeedReload(String file) {
/* 85 */     if (System.currentTimeMillis() - this.lastAccessTime < 1000L) {
/* 86 */       return false;
/*    */     }
/* 88 */     File f = new File(file);
/* 89 */     if (this.lastModified != f.lastModified()) {
/* 90 */       this.lastModified = f.lastModified();
/* 91 */       return true;
/*    */     }
/* 93 */     return false;
/*    */   }
/*    */ }