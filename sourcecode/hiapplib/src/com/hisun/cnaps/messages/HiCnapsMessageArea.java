 package com.hisun.cnaps.messages;
 
 import com.hisun.cnaps.CnapsMessageArea;
 import com.hisun.cnaps.CnapsTag;
 import com.hisun.cnaps.HiCnapsCodeTable;
 import com.hisun.cnaps.tags.HiCnapsTag;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 
 public abstract class HiCnapsMessageArea
   implements CnapsMessageArea
 {
   private HiCnapsCodeTable codes;
   private List tags;
 
   public HiCnapsCodeTable getCnapsCodeTable()
   {
     return this.codes;
   }
 
   protected void addTag(HiCnapsTag tag) {
     this.tags.add(tag);
   }
 
   public HiCnapsMessageArea()
   {
     this.tags = new ArrayList();
   }
 
   public void setCodeTable(HiCnapsCodeTable codes) {
     this.codes = codes;
   }
 
   public int getTagCount() {
     return this.tags.size();
   }
 
   public CnapsTag getTagByIndex(int index) {
     return ((CnapsTag)this.tags.get(index));
   }
 
   public int getLength() {
     return getString().length();
   }
 
   public abstract void unpack(String paramString) throws HiException;
 
   public abstract void packFromETF(String[] paramArrayOfString1, String[] paramArrayOfString2, HiETF paramHiETF) throws HiException;
 
   public abstract String getString();
 
   public void unpack2Etf(HiETF etf) throws HiException
   {
     for (int i = 0; i < this.tags.size(); ++i) {
       HiCnapsTag tag = (HiCnapsTag)this.tags.get(i);
       tag.unpack2Etf(etf);
     }
   }
 
   public void unpack2Etf(String[] fileds, HiETF etf, boolean auth) throws HiException
   {
     int count = getTagCount();
 
     for (int i = 0; i < fileds.length; ++i) {
       boolean found = false;
       String filed = fileds[i].toUpperCase();
       for (int p = 0; p < count; ++p) {
         HiCnapsTag tag = (HiCnapsTag)getTagByIndex(p);
         if (tag.getMarkName().equals(filed)) {
           tag.unpack2Etf(etf);
           found = true;
           if (StringUtils.isBlank(tag.getRepeatName())) {
             break;
           }
         }
       }
 
       if ((!(found)) && (auth))
         throw new HiException("241104");
     }
   }
 
   public static void main(String[] rags) throws Exception
   {
     String s = ":30A:20020618:32A:RMB000000077889900:011:106290000010:52A:310001003011:CC4:310001003011:50C:11000067267001                  :50A:毅龙公司                                                    :50B:北京                                                        :012:01003011:58A:308521021016:CC5:000000000011:59C:11                              :59A:中华                                                        :59B:发达                                                        :CEF:11:0BC:00000184:010:1000:0B9:5210:72A:                                                            ";
     s.indexOf(":", 253);
     new HiCnapsMessage(null).unpack(s);
     System.out.println(s.indexOf(58, 253));
     System.out.println(s.substring(253, 266));
   }
 }