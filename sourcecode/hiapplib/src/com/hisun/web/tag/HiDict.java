 package com.hisun.web.tag;
 
 import java.io.File;
 import java.net.MalformedURLException;
 import java.util.ArrayList;
 import java.util.Iterator;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class HiDict
 {
   public ArrayList<HiDictItem> _dictItems;
   private long lastModified;
   private long lastAccessTime;
   private static HiDict instance;
 
   public HiDict()
   {
     this._dictItems = new ArrayList();
 
     this.lastModified = -1L;
 
     this.lastAccessTime = -1L;
   }
 
   public static synchronized HiDict getInstance() {
     if (instance == null) {
       instance = new HiDict();
     }
     return instance;
   }
 
   public synchronized void init(String file) throws DocumentException, MalformedURLException {
     if (!(isNeedReload(file))) {
       return;
     }
     SAXReader saxReader = new SAXReader();
     Document doc = saxReader.read(file);
     Element root = doc.getRootElement();
     Iterator iter = root.elementIterator();
     this._dictItems.clear();
     while (iter.hasNext()) {
       HiDictItem dictItem = new HiDictItem();
       Element element = (Element)iter.next();
 
       String name = element.attributeValue("name");
       dictItem.name = name;
 
       int length = NumberUtils.toInt(element.attributeValue("length"));
       dictItem.length = length;
 
       String type = element.attributeValue("type");
       dictItem.type = type;
 
       int size = NumberUtils.toInt(element.attributeValue("size"));
       if (size == 0) {
         size = length;
       }
       dictItem.setSize(size);
       add(dictItem); }
   }
 
   public void add(HiDictItem item) {
     this._dictItems.add(item);
   }
 
   public HiDictItem get(String name)
   {
     for (HiDictItem item : this._dictItems) {
       if (StringUtils.equalsIgnoreCase(item.name, name))
         return item;
     }
     return null;
   }
 
   private boolean isNeedReload(String file) {
     if (System.currentTimeMillis() - this.lastAccessTime < 1000L) {
       return false;
     }
     File f = new File(file);
     if (this.lastModified != f.lastModified()) {
       this.lastModified = f.lastModified();
       return true;
     }
     return false;
   }
 }