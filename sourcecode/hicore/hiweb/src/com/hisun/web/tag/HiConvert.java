 package com.hisun.web.tag;

 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;

 import java.io.File;
 import java.net.MalformedURLException;
 import java.util.ArrayList;
 import java.util.Iterator;
 
 public class HiConvert
 {
   private ArrayList<HiConvertItem> _convertItems;
   private long lastModified;
   private long lastAccessTime;
   private static HiConvert instance;
 
   public HiConvert()
   {
     this._convertItems = new ArrayList();
 
     this.lastModified = -1L;
 
     this.lastAccessTime = -1L;
   }
 
   public static synchronized HiConvert getInstance() {
     if (instance == null) {
       instance = new HiConvert();
     }
     return instance; }
 
   public synchronized void init(String file) throws DocumentException, MalformedURLException {
     if (!(isNeedReload(file))) {
       return;
     }
     SAXReader saxReader = new SAXReader();
     Document doc = saxReader.read(file);
     Element root = doc.getRootElement();
     Iterator iter = root.elementIterator();
     this._convertItems.clear();
     while (iter.hasNext()) {
       HiConvertItem item = new HiConvertItem();
       Element element = (Element)iter.next();
       item.name = element.attributeValue("name");
       item.type = element.attributeValue("type");
       Iterator iter1 = element.elementIterator();
       while (iter1.hasNext()) {
         Element element1 = (Element)iter1.next();
         String _str1 = element1.attributeValue("name");
         String _str2 = element1.attributeValue("value");
         item._map.put(_str1, _str2);
       }
       this._convertItems.add(item);
     }
   }
 
   public String convert(String name, String value) {
     this.lastAccessTime = System.currentTimeMillis();
     HiConvertItem item = null;
     boolean founded = false;
     for (int i = 0; i < this._convertItems.size(); ++i) {
       item = (HiConvertItem)this._convertItems.get(i);
       if (StringUtils.equalsIgnoreCase(item.name, name)) {
         founded = true;
         break;
       }
     }
     if (!(founded)) {
       return value;
     }
 
     Iterator iter = item._map.keySet().iterator();
     while (iter.hasNext()) {
       String key = (String)iter.next();
       if (StringUtils.equals(value, key)) {
         value = (String)item._map.get(key);
         break;
       }
     }
     if (!(iter.hasNext())) {
       if (item._map.containsKey("Default"))
         value = (String)item._map.get("Default");
       else {
         System.out.println("[" + name + "][" + value + "] not founded");
       }
     }
     if (StringUtils.equalsIgnoreCase(item.type, "IMAGE")) {
       return "<img src='" + value + "'/>";
     }
     return value;
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