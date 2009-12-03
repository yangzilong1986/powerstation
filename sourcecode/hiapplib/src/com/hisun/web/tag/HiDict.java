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
/* 20 */     this._dictItems = new ArrayList();
/*    */ 
/* 24 */     this.lastModified = -1L;
/*    */ 
/* 28 */     this.lastAccessTime = -1L;
/*    */   }
/*    */ 
/*    */   public static synchronized HiDict getInstance() {
/* 32 */     if (instance == null) {
/* 33 */       instance = new HiDict();
/*    */     }
/* 35 */     return instance;
/*    */   }
/*    */ 
/*    */   public synchronized void init(String file) throws DocumentException, MalformedURLException {
/* 39 */     if (!(isNeedReload(file))) {
/* 40 */       return;
/*    */     }
/* 42 */     SAXReader saxReader = new SAXReader();
/* 43 */     Document doc = saxReader.read(file);
/* 44 */     Element root = doc.getRootElement();
/* 45 */     Iterator iter = root.elementIterator();
/* 46 */     this._dictItems.clear();
/* 47 */     while (iter.hasNext()) {
/* 48 */       HiDictItem dictItem = new HiDictItem();
/* 49 */       Element element = (Element)iter.next();
/*    */ 
/* 51 */       String name = element.attributeValue("name");
/* 52 */       dictItem.name = name;
/*    */ 
/* 54 */       int length = NumberUtils.toInt(element.attributeValue("length"));
/* 55 */       dictItem.length = length;
/*    */ 
/* 57 */       String type = element.attributeValue("type");
/* 58 */       dictItem.type = type;
/*    */ 
/* 60 */       int size = NumberUtils.toInt(element.attributeValue("size"));
/* 61 */       if (size == 0) {
/* 62 */         size = length;
/*    */       }
/* 64 */       dictItem.setSize(size);
/* 65 */       add(dictItem); }
/*    */   }
/*    */ 
/*    */   public void add(HiDictItem item) {
/* 69 */     this._dictItems.add(item);
/*    */   }
/*    */ 
/*    */   public HiDictItem get(String name)
/*    */   {
/* 75 */     for (HiDictItem item : this._dictItems) {
/* 76 */       if (StringUtils.equalsIgnoreCase(item.name, name))
/* 77 */         return item;
/*    */     }
/* 79 */     return null;
/*    */   }
/*    */ 
/*    */   private boolean isNeedReload(String file) {
/* 83 */     if (System.currentTimeMillis() - this.lastAccessTime < 1000L) {
/* 84 */       return false;
/*    */     }
/* 86 */     File f = new File(file);
/* 87 */     if (this.lastModified != f.lastModified()) {
/* 88 */       this.lastModified = f.lastModified();
/* 89 */       return true;
/*    */     }
/* 91 */     return false;
/*    */   }
/*    */ }