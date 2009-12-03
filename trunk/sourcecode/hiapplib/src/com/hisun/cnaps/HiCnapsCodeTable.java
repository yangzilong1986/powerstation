/*     */ package com.hisun.cnaps;
/*     */ 
/*     */ import com.hisun.cnaps.tags.HiCnapsFixTag;
/*     */ import com.hisun.cnaps.tags.HiCnapsGroupTag;
/*     */ import com.hisun.cnaps.tags.HiCnapsTag;
/*     */ import com.hisun.cnaps.tags.HiCnapsTagImpl;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiCnapsCodeTable
/*     */ {
/*     */   private long tableTime;
/*     */   Map tags;
/*     */ 
/*     */   public static HiCnapsCodeTable load(URL cfgFile)
/*     */     throws DocumentException
/*     */   {
/*  30 */     SAXReader sax = new SAXReader();
/*  31 */     HiCnapsCodeTable currTable = new HiCnapsCodeTable();
/*  32 */     Element rootDoc = sax.read(cfgFile).getRootElement();
/*  33 */     List nodeLs = rootDoc.selectNodes("Tag");
/*  34 */     for (int i = 0; i < nodeLs.size(); ++i)
/*     */     {
/*  36 */       Element tagElement = (Element)nodeLs.get(i);
/*  37 */       currTable.addTagElement(tagElement.attributeValue("mark").toUpperCase(), tagElement);
/*     */     }
/*     */ 
/*  41 */     return currTable;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  46 */     Iterator keys = this.tags.keySet().iterator();
/*  47 */     StringBuffer sb = new StringBuffer("{");
/*  48 */     for (; keys.hasNext(); sb.append("}"))
/*     */     {
/*  50 */       Object key = keys.next();
/*  51 */       sb.append(key);
/*  52 */       sb.append(":");
/*  53 */       sb.append(this.tags.get(key));
/*     */     }
/*     */ 
/*  56 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private void addTagElement(String mark, Element element)
/*     */   {
/*  63 */     this.tags.put(mark, element);
/*     */   }
/*     */ 
/*     */   private HiCnapsCodeTable()
/*     */   {
/*  68 */     this.tableTime = 0L;
/*  69 */     this.tags = null;
/*  70 */     this.tableTime = -1L;
/*  71 */     this.tags = new HashMap();
/*     */   }
/*     */ 
/*     */   public HiCnapsTag getTagFormElement(Element element)
/*     */   {
/*  76 */     String split = element.attributeValue("split");
/*  77 */     if (StringUtils.isNotEmpty(split))
/*     */     {
/*  79 */       split = split.toUpperCase();
/*  80 */       HiCnapsTag tag = null;
/*  81 */       if (split.equals("FIXED")) {
/*  82 */         tag = new HiCnapsFixTag(element);
/*     */       }
/*  84 */       else if (split.equals("GROUP"))
/*  85 */         tag = new HiCnapsGroupTag(element);
/*     */       else
/*  87 */         tag = new HiCnapsTagImpl(element);
/*  88 */       return tag;
/*     */     }
/*     */ 
/*  91 */     return new HiCnapsTagImpl(element);
/*     */   }
/*     */ 
/*     */   public HiCnapsTag getTagFromMark(String tagName)
/*     */   {
/*  97 */     Element element = (Element)this.tags.get(tagName.toUpperCase());
/*  98 */     if (element == null) {
/*  99 */       return null;
/*     */     }
/* 101 */     return getTagFormElement(element);
/*     */   }
/*     */ }