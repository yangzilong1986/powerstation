 package com.hisun.cnaps.tags;
 
 import com.hisun.cnaps.common.HiCnapsDataTypeHelper;
 import com.hisun.cnaps.common.HiRepeatTagManager;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 
 public class HiCnapsTagImpl
   implements HiCnapsTag
 {
   protected String name;
   protected String etfName;
   protected int dataType;
   protected int length;
   protected String repeatName;
   protected String value;
   private HiRepeatTagManager manager;
 
   protected HiRepeatTagManager getRepeatManager()
   {
     return this.manager;
   }
 
   public HiCnapsTagImpl(Element element)
   {
     this.name = element.attributeValue("mark");
     String _dataType = element.attributeValue("etf_name");
     if (!(StringUtils.isBlank(_dataType)))
       this.dataType = (_dataType.charAt(0) & 0xFF);
     else
       this.dataType = 110;
     String _length = element.attributeValue("length");
 
     if (!(StringUtils.isBlank(_length)))
       try
       {
         this.length = Integer.parseInt(_length);
       }
       catch (Exception ex)
       {
         this.length = -1;
       }
     this.repeatName = element.attributeValue("sub_repeat");
     this.etfName = element.attributeValue("etf_name");
   }
 
   public int getLength()
   {
     return this.length;
   }
 
   public void setEtfName(String etfName)
   {
     this.etfName = etfName;
   }
 
   public void setDataType(int dataType)
   {
     this.dataType = dataType;
   }
 
   public void setLength(int length)
   {
     this.length = length;
   }
 
   public void setRepeatName(String repeatName)
   {
     this.repeatName = repeatName;
   }
 
   public void setValue(String value)
   {
     this.value = value;
   }
 
   public String getEtfName()
   {
     return this.etfName;
   }
 
   public int getDataType()
   {
     return this.dataType;
   }
 
   public String getRepeatName()
   {
     return this.repeatName;
   }
 
   public String getValue()
   {
     return this.value;
   }
 
   public String getMarkName()
   {
     return this.name;
   }
 
   public void setEtfname(String etfName)
   {
     this.etfName = etfName;
   }
 
   public void parse(String buffer)
     throws HiException
   {
     this.value = buffer;
   }
 
   public void unpack2Etf(HiETF etf)
     throws HiException
   {
     if (StringUtils.isNotBlank(this.repeatName))
     {
       String count = this.manager.nextTagEtfName(this.etfName);
       this.etfName = this.etfName.concat("_").concat(count);
       etf.setChildValue(this.repeatName, count);
     }
     etf.addNode(this.etfName, this.value);
   }
 
   public String parse(HiETF etf)
     throws HiException
   {
     if (StringUtils.isNotBlank(this.repeatName))
       this.etfName = this.etfName.concat("_").concat(this.manager.nextTagEtfName(this.etfName));
     String aStr = etf.getGrandChildValue(this.etfName);
     this.value = HiCnapsDataTypeHelper.fullIfNeed(getDataType(), getLength(), (aStr != null) ? aStr : "");
 
     return this.value;
   }
 
   public void setRepeatManager(HiRepeatTagManager manager)
   {
     this.manager = manager;
   }
 }