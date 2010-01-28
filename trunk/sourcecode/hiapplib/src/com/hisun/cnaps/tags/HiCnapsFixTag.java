 package com.hisun.cnaps.tags;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 
 public class HiCnapsFixTag extends HiCnapsSplitedTag
 {
   public HiCnapsFixTag(Element element)
   {
     super(element);
   }
 
   public void parse(String value)
     throws HiException
   {
     List subLs = getRootElement().selectNodes("SubTag");
     int pos = 0;
     int endPos = 0;
     int totLength = value.length();
     for (int i = 0; i < subLs.size(); ++i)
     {
       Element subElement = (Element)subLs.get(i);
       HiCnapsTagImpl tag = new HiCnapsTagImpl(subElement);
       if (StringUtils.isNotBlank(this.etfName))
         tag.setEtfname(this.etfName.concat(".").concat(tag.getEtfName()).toUpperCase());
       tag.setRepeatName(getRepeatName());
       tag.setRepeatManager(getRepeatManager());
       long length = tag.getLength();
       if (length == -1L)
         throw new HiException("241100", String.valueOf(length));
       if (totLength - pos < length)
         throw new HiException("241100", String.valueOf(length));
       endPos = pos + tag.getLength();
       String subValue = value.substring(pos, endPos);
       tag.parse(subValue);
       pos = endPos;
       addSubTag(tag);
     }
     this.value = value;
   }
 
   public int getSplitedMode()
   {
     return 1;
   }
 
   public String parse(HiETF etf)
     throws HiException
   {
     List subLs = getRootElement().selectNodes("SubTag");
     for (int i = 0; i < subLs.size(); ++i)
     {
       Element subElement = (Element)subLs.get(i);
       HiCnapsTagImpl tag = new HiCnapsTagImpl(subElement);
       if (StringUtils.isNotBlank(this.etfName))
         tag.setEtfname(this.etfName.concat(".").concat(tag.getEtfName()).toUpperCase());
       tag.setRepeatName(getRepeatName());
       tag.setRepeatManager(getRepeatManager());
       addSubTag(tag);
     }
 
     return super.parse(etf);
   }
 }