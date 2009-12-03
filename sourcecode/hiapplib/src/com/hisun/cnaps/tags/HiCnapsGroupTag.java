/*    */ package com.hisun.cnaps.tags;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class HiCnapsGroupTag extends HiCnapsSplitedTag
/*    */ {
/*    */   public HiCnapsGroupTag(Element element)
/*    */   {
/* 17 */     super(element);
/*    */   }
/*    */ 
/*    */   public void parse(String value)
/*    */     throws HiException
/*    */   {
/*    */     int endPos;
/* 23 */     int groupLength = value.length();
/* 24 */     if (groupLength % getLength() != 0)
/* 25 */       throw new HiException("241101", this.name);
/* 26 */     int loop = groupLength / getLength();
/*    */ 
/* 28 */     int pos = endPos = 0;
/* 29 */     for (int i = 0; i < loop; ++i)
/*    */     {
/* 31 */       HiCnapsFixTag tag = new HiCnapsFixTag(getRootElement());
/* 32 */       tag.setSubTagRepeatName(null);
/* 33 */       tag.setEtfname(this.etfName.concat("_").concat(String.valueOf(i + 1)).toUpperCase());
/* 34 */       endPos = pos + getLength();
/* 35 */       String subBuffer = value.substring(pos, endPos);
/* 36 */       tag.parse(subBuffer);
/* 37 */       pos = endPos;
/* 38 */       tag.setRepeatName(getRepeatName());
/* 39 */       tag.setRepeatManager(getRepeatManager());
/* 40 */       addSubTag(tag);
/*    */     }
/* 42 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public int getSplitedMode()
/*    */   {
/* 48 */     return 2;
/*    */   }
/*    */ 
/*    */   public String parse(HiETF etf)
/*    */     throws HiException
/*    */   {
/* 54 */     String subRepeat = getSubTagRepeatName();
/* 55 */     HiETF subETF = null;
/* 56 */     int i = 0;
/* 57 */     String subEtfName = "";
/* 58 */     if (StringUtils.isBlank(subRepeat))
/*    */     {
/*    */       while (true)
/*    */       {
/* 62 */         subEtfName = getEtfName().concat("_").concat(String.valueOf(++i));
/* 63 */         if ((subETF = etf.getChildNode(subEtfName)) == null)
/*    */           break label219;
/* 65 */         HiCnapsFixTag tag = new HiCnapsFixTag(getRootElement());
/* 66 */         tag.setEtfname(subEtfName);
/* 67 */         tag.setRepeatName(getRepeatName());
/* 68 */         tag.setRepeatManager(getRepeatManager());
/* 69 */         addSubTag(tag);
/*    */       }
/*    */     }
/*    */ 
/* 73 */     int subCnt = 0;
/*    */     try
/*    */     {
/* 76 */       subCnt = Integer.parseInt(etf.getChildValue(subRepeat));
/*    */     }
/*    */     catch (Exception E)
/*    */     {
/* 80 */       throw new HiException(E);
/*    */     }
/* 82 */     while (++i <= subCnt)
/*    */     {
/* 84 */       subEtfName = getEtfName().concat("_").concat(String.valueOf(i));
/* 85 */       subETF = etf.getChildNode(subEtfName);
/* 86 */       if (subETF == null)
/* 87 */         throw new HiException("740005", subEtfName);
/* 88 */       HiCnapsFixTag tag = new HiCnapsFixTag(getRootElement());
/* 89 */       tag.setEtfname(subEtfName);
/* 90 */       addSubTag(tag);
/*    */     }
/*    */ 
/* 93 */     label219: return super.parse(etf);
/*    */   }
/*    */ }