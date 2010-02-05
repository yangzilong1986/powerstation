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
 
 public class HiFieldHlp
 {
   public ArrayList<HiFieldHlpItem> _fieldHlpItems;
   public static final String FILE = "fieldhlp.xml";
   private long lastModified;
   private long lastAccessTime;
   private static HiFieldHlp instance;
 
   public HiFieldHlp()
   {
     this._fieldHlpItems = new ArrayList();
 
     this.lastModified = -1L;
 
     this.lastAccessTime = -1L;
   }
 
   public static synchronized HiFieldHlp getInstance()
   {
     if (instance == null) {
       instance = new HiFieldHlp();
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
     this._fieldHlpItems.clear();
     while (iter.hasNext()) {
       HiFieldHlpItem fieldHlpItem = new HiFieldHlpItem();
       Element element = (Element)iter.next();
       String name = element.attributeValue("name");
       fieldHlpItem.name = name;
       Iterator iter1 = element.elementIterator();
       while (iter1.hasNext()) {
         Element element1 = (Element)iter1.next();
         String _str1 = element1.attributeValue("name");
         String _str2 = element1.attributeValue("value");
         fieldHlpItem._map.put(_str1, _str2);
       }
       add(fieldHlpItem);
     }
   }
 
   private void add(HiFieldHlpItem item) {
     this._fieldHlpItems.add(item);
   }
 
   public HiFieldHlpItem get(String name)
   {
     for (HiFieldHlpItem item : this._fieldHlpItems) {
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