/*    */ package com.hisun.cnaps.tags;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public abstract class HiCnapsSplitedTag extends HiCnapsTagImpl
/*    */   implements HiCnapsSplitableTag
/*    */ {
/*    */   protected List subTags;
/*    */   private String subRepeatName;
/*    */   private Element rootElement;
/*    */ 
/*    */   public HiCnapsSplitedTag(Element element)
/*    */   {
/* 23 */     super(element);
/* 24 */     this.rootElement = element;
/* 25 */     this.subTags = new ArrayList(3);
/* 26 */     this.subRepeatName = element.attributeValue("sub_repeat");
/*    */   }
/*    */ 
/*    */   public int getSubTagCount()
/*    */   {
/* 31 */     return this.subTags.size();
/*    */   }
/*    */ 
/*    */   protected Element getRootElement()
/*    */   {
/* 36 */     return this.rootElement;
/*    */   }
/*    */ 
/*    */   protected void setSubTagRepeatName(String subTag)
/*    */   {
/* 41 */     this.subRepeatName = subTag;
/*    */   }
/*    */ 
/*    */   protected void addSubTag(HiCnapsTag tag)
/*    */   {
/* 46 */     this.subTags.add(tag);
/*    */   }
/*    */ 
/*    */   public String getSubTagRepeatName()
/*    */   {
/* 51 */     return this.subRepeatName;
/*    */   }
/*    */ 
/*    */   public HiCnapsTag getSubTagbyIndex(int index)
/*    */   {
/* 56 */     if (this.subTags.isEmpty())
/* 57 */       return null;
/* 58 */     if (index > this.subTags.size()) {
/* 59 */       return null;
/*    */     }
/* 61 */     return ((HiCnapsTag)this.subTags.get(index));
/*    */   }
/*    */ 
/*    */   public void unpack2Etf(HiETF etf)
/*    */     throws HiException
/*    */   {
/* 67 */     int subCnt = getSubTagCount();
/* 68 */     for (int i = 0; i < subCnt; ++i)
/*    */     {
/* 70 */       HiCnapsTag tag = getSubTagbyIndex(i);
/* 71 */       tag.setRepeatManager(getRepeatManager());
/* 72 */       tag.unpack2Etf(etf);
/*    */     }
/*    */ 
/* 75 */     if (!(StringUtils.isBlank(getSubTagRepeatName())))
/* 76 */       etf.addNode(getSubTagRepeatName(), String.valueOf(subCnt));
/*    */   }
/*    */ 
/*    */   public String parse(HiETF etf)
/*    */     throws HiException
/*    */   {
/* 82 */     StringBuffer sb = new StringBuffer();
/* 83 */     for (int i = 0; i < this.subTags.size(); ++i)
/*    */     {
/* 85 */       HiCnapsTag tag = (HiCnapsTag)this.subTags.get(i);
/* 86 */       tag.parse(etf);
/* 87 */       sb.append(tag.getValue());
/*    */     }
/*    */ 
/* 90 */     this.value = sb.toString();
/* 91 */     return this.value;
/*    */   }
/*    */ }