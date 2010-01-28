 package com.hisun.cnaps.tags;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 
 public class HiCnapsGroupTag extends HiCnapsSplitedTag
 {
   public HiCnapsGroupTag(Element element)
   {
     super(element);
   }
 
   public void parse(String value)
     throws HiException
   {
     int endPos;
     int groupLength = value.length();
     if (groupLength % getLength() != 0)
       throw new HiException("241101", this.name);
     int loop = groupLength / getLength();
 
     int pos = endPos = 0;
     for (int i = 0; i < loop; ++i)
     {
       HiCnapsFixTag tag = new HiCnapsFixTag(getRootElement());
       tag.setSubTagRepeatName(null);
       tag.setEtfname(this.etfName.concat("_").concat(String.valueOf(i + 1)).toUpperCase());
       endPos = pos + getLength();
       String subBuffer = value.substring(pos, endPos);
       tag.parse(subBuffer);
       pos = endPos;
       tag.setRepeatName(getRepeatName());
       tag.setRepeatManager(getRepeatManager());
       addSubTag(tag);
     }
     this.value = value;
   }
 
   public int getSplitedMode()
   {
     return 2;
   }
 
   public String parse(HiETF etf)
     throws HiException
   {
     String subRepeat = getSubTagRepeatName();
     HiETF subETF = null;
     int i = 0;
     String subEtfName = "";
     if (StringUtils.isBlank(subRepeat))
     {
       while (true)
       {
         subEtfName = getEtfName().concat("_").concat(String.valueOf(++i));
         if ((subETF = etf.getChildNode(subEtfName)) == null)
           break label219;
         HiCnapsFixTag tag = new HiCnapsFixTag(getRootElement());
         tag.setEtfname(subEtfName);
         tag.setRepeatName(getRepeatName());
         tag.setRepeatManager(getRepeatManager());
         addSubTag(tag);
       }
     }
 
     int subCnt = 0;
     try
     {
       subCnt = Integer.parseInt(etf.getChildValue(subRepeat));
     }
     catch (Exception E)
     {
       throw new HiException(E);
     }
     while (++i <= subCnt)
     {
       subEtfName = getEtfName().concat("_").concat(String.valueOf(i));
       subETF = etf.getChildNode(subEtfName);
       if (subETF == null)
         throw new HiException("740005", subEtfName);
       HiCnapsFixTag tag = new HiCnapsFixTag(getRootElement());
       tag.setEtfname(subEtfName);
       addSubTag(tag);
     }
 
     label219: return super.parse(etf);
   }
 }