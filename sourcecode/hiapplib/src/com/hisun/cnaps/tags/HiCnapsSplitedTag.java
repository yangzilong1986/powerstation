 package com.hisun.cnaps.tags;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 
 public abstract class HiCnapsSplitedTag extends HiCnapsTagImpl
   implements HiCnapsSplitableTag
 {
   protected List subTags;
   private String subRepeatName;
   private Element rootElement;
 
   public HiCnapsSplitedTag(Element element)
   {
     super(element);
     this.rootElement = element;
     this.subTags = new ArrayList(3);
     this.subRepeatName = element.attributeValue("sub_repeat");
   }
 
   public int getSubTagCount()
   {
     return this.subTags.size();
   }
 
   protected Element getRootElement()
   {
     return this.rootElement;
   }
 
   protected void setSubTagRepeatName(String subTag)
   {
     this.subRepeatName = subTag;
   }
 
   protected void addSubTag(HiCnapsTag tag)
   {
     this.subTags.add(tag);
   }
 
   public String getSubTagRepeatName()
   {
     return this.subRepeatName;
   }
 
   public HiCnapsTag getSubTagbyIndex(int index)
   {
     if (this.subTags.isEmpty())
       return null;
     if (index > this.subTags.size()) {
       return null;
     }
     return ((HiCnapsTag)this.subTags.get(index));
   }
 
   public void unpack2Etf(HiETF etf)
     throws HiException
   {
     int subCnt = getSubTagCount();
     for (int i = 0; i < subCnt; ++i)
     {
       HiCnapsTag tag = getSubTagbyIndex(i);
       tag.setRepeatManager(getRepeatManager());
       tag.unpack2Etf(etf);
     }
 
     if (!(StringUtils.isBlank(getSubTagRepeatName())))
       etf.addNode(getSubTagRepeatName(), String.valueOf(subCnt));
   }
 
   public String parse(HiETF etf)
     throws HiException
   {
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < this.subTags.size(); ++i)
     {
       HiCnapsTag tag = (HiCnapsTag)this.subTags.get(i);
       tag.parse(etf);
       sb.append(tag.getValue());
     }
 
     this.value = sb.toString();
     return this.value;
   }
 }