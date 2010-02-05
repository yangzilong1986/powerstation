 package com.hisun.mng;
 
 import com.hisun.exception.HiException;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiResource;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Iterator;
 import org.apache.commons.digester.Digester;
 import org.apache.commons.lang.StringUtils;
 
 public class HiRegionInfo
 {
   private String name;
   private ArrayList nodeList;
 
   public HiRegionInfo()
   {
     this.nodeList = new ArrayList(); }
 
   public static HiRegionInfo parse() throws HiException { String regionFile = HiICSProperty.getProperty("region.config");
     return parse(regionFile);
   }
 
   public static HiRegionInfo parse(String file) throws HiException {
     InputStream is = HiResource.getResourceAsStream(file);
     if (is == null)
     {
       throw new HiException("212004", file);
     }
     Digester digester = new Digester();
     digester.setClassLoader(Thread.currentThread().getContextClassLoader());
     digester.setValidating(false);
     digester.addObjectCreate("Region", "com.hisun.mng.HiRegionInfo");
 
     digester.addSetProperties("Region");
     digester.addObjectCreate("Region/Node", "com.hisun.mng.HiNodeInfo");
 
     digester.addSetProperties("Region/Node");
     digester.addSetProperty("Region/Node/Param", "name", "value");
     digester.addSetNext("Region/Node", "addNode", "com.hisun.mng.HiNodeInfo");
     try
     {
       return ((HiRegionInfo)digester.parse(is));
     } catch (Exception e) {
       throw HiException.makeException(e);
     }
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public void addNode(HiNodeInfo node) {
     this.nodeList.add(node);
   }
 
   public Iterator iterator() {
     return this.nodeList.iterator();
   }
 
   public int size() {
     return this.nodeList.size();
   }
 
   public HiNodeInfo get(int idx) {
     if ((idx < 0) || (idx >= this.nodeList.size())) {
       return null;
     }
     return ((HiNodeInfo)this.nodeList.get(idx));
   }
 
   public HiNodeInfo get(String id) {
     for (int i = 0; i < this.nodeList.size(); ++i) {
       HiNodeInfo info = (HiNodeInfo)this.nodeList.get(i);
       if (StringUtils.equals(info.getId(), id)) {
         return info;
       }
     }
     return null;
   }
 }