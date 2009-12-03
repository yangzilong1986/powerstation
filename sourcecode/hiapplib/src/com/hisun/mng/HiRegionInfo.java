/*    */ package com.hisun.mng;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import com.hisun.util.HiResource;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import org.apache.commons.digester.Digester;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiRegionInfo
/*    */ {
/*    */   private String name;
/*    */   private ArrayList nodeList;
/*    */ 
/*    */   public HiRegionInfo()
/*    */   {
/* 32 */     this.nodeList = new ArrayList(); }
/*    */ 
/*    */   public static HiRegionInfo parse() throws HiException { String regionFile = HiICSProperty.getProperty("region.config");
/* 35 */     return parse(regionFile);
/*    */   }
/*    */ 
/*    */   public static HiRegionInfo parse(String file) throws HiException {
/* 39 */     InputStream is = HiResource.getResourceAsStream(file);
/* 40 */     if (is == null)
/*    */     {
/* 42 */       throw new HiException("212004", file);
/*    */     }
/* 44 */     Digester digester = new Digester();
/* 45 */     digester.setClassLoader(Thread.currentThread().getContextClassLoader());
/* 46 */     digester.setValidating(false);
/* 47 */     digester.addObjectCreate("Region", "com.hisun.mng.HiRegionInfo");
/*    */ 
/* 49 */     digester.addSetProperties("Region");
/* 50 */     digester.addObjectCreate("Region/Node", "com.hisun.mng.HiNodeInfo");
/*    */ 
/* 52 */     digester.addSetProperties("Region/Node");
/* 53 */     digester.addSetProperty("Region/Node/Param", "name", "value");
/* 54 */     digester.addSetNext("Region/Node", "addNode", "com.hisun.mng.HiNodeInfo");
/*    */     try
/*    */     {
/* 57 */       return ((HiRegionInfo)digester.parse(is));
/*    */     } catch (Exception e) {
/* 59 */       throw HiException.makeException(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 64 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 68 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public void addNode(HiNodeInfo node) {
/* 72 */     this.nodeList.add(node);
/*    */   }
/*    */ 
/*    */   public Iterator iterator() {
/* 76 */     return this.nodeList.iterator();
/*    */   }
/*    */ 
/*    */   public int size() {
/* 80 */     return this.nodeList.size();
/*    */   }
/*    */ 
/*    */   public HiNodeInfo get(int idx) {
/* 84 */     if ((idx < 0) || (idx >= this.nodeList.size())) {
/* 85 */       return null;
/*    */     }
/* 87 */     return ((HiNodeInfo)this.nodeList.get(idx));
/*    */   }
/*    */ 
/*    */   public HiNodeInfo get(String id) {
/* 91 */     for (int i = 0; i < this.nodeList.size(); ++i) {
/* 92 */       HiNodeInfo info = (HiNodeInfo)this.nodeList.get(i);
/* 93 */       if (StringUtils.equals(info.getId(), id)) {
/* 94 */         return info;
/*    */       }
/*    */     }
/* 97 */     return null;
/*    */   }
/*    */ }