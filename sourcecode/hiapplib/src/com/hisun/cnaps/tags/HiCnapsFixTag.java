/*    */ package com.hisun.cnaps.tags;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class HiCnapsFixTag extends HiCnapsSplitedTag
/*    */ {
/*    */   public HiCnapsFixTag(Element element)
/*    */   {
/* 19 */     super(element);
/*    */   }
/*    */ 
/*    */   public void parse(String value)
/*    */     throws HiException
/*    */   {
/* 26 */     List subLs = getRootElement().selectNodes("SubTag");
/* 27 */     int pos = 0;
/* 28 */     int endPos = 0;
/* 29 */     int totLength = value.length();
/* 30 */     for (int i = 0; i < subLs.size(); ++i)
/*    */     {
/* 32 */       Element subElement = (Element)subLs.get(i);
/* 33 */       HiCnapsTagImpl tag = new HiCnapsTagImpl(subElement);
/* 34 */       if (StringUtils.isNotBlank(this.etfName))
/* 35 */         tag.setEtfname(this.etfName.concat(".").concat(tag.getEtfName()).toUpperCase());
/* 36 */       tag.setRepeatName(getRepeatName());
/* 37 */       tag.setRepeatManager(getRepeatManager());
/* 38 */       long length = tag.getLength();
/* 39 */       if (length == -1L)
/* 40 */         throw new HiException("241100", String.valueOf(length));
/* 41 */       if (totLength - pos < length)
/* 42 */         throw new HiException("241100", String.valueOf(length));
/* 43 */       endPos = pos + tag.getLength();
/* 44 */       String subValue = value.substring(pos, endPos);
/* 45 */       tag.parse(subValue);
/* 46 */       pos = endPos;
/* 47 */       addSubTag(tag);
/*    */     }
/* 49 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public int getSplitedMode()
/*    */   {
/* 55 */     return 1;
/*    */   }
/*    */ 
/*    */   public String parse(HiETF etf)
/*    */     throws HiException
/*    */   {
/* 61 */     List subLs = getRootElement().selectNodes("SubTag");
/* 62 */     for (int i = 0; i < subLs.size(); ++i)
/*    */     {
/* 64 */       Element subElement = (Element)subLs.get(i);
/* 65 */       HiCnapsTagImpl tag = new HiCnapsTagImpl(subElement);
/* 66 */       if (StringUtils.isNotBlank(this.etfName))
/* 67 */         tag.setEtfname(this.etfName.concat(".").concat(tag.getEtfName()).toUpperCase());
/* 68 */       tag.setRepeatName(getRepeatName());
/* 69 */       tag.setRepeatManager(getRepeatManager());
/* 70 */       addSubTag(tag);
/*    */     }
/*    */ 
/* 73 */     return super.parse(etf);
/*    */   }
/*    */ }