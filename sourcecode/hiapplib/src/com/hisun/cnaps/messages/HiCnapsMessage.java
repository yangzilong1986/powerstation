 package com.hisun.cnaps.messages;
 
 import com.hisun.cnaps.HiCnapsCodeTable;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import java.io.PrintStream;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.List;
 
 public class HiCnapsMessage
 {
   HiCnapsCodeTable codes;
   private List buses;
 
   public HiCnapsMessage(HiCnapsCodeTable codeTable)
   {
     this.codes = null;
     this.codes = codeTable;
     this.buses = new ArrayList();
   }
 
   public HiCnapsMessageArea getMessageBusArea(int i) {
     if (i < 0)
       throw new IllegalArgumentException(String.valueOf(i));
     return ((HiCnapsMessageArea)this.buses.get(--i));
   }
 
   public void unpack(String cnapsBuffer) throws HiException {
     int index = 0;
     while ((index = cnapsBuffer.indexOf(123, index)) != -1) {
       String mark = cnapsBuffer.substring(index, index + 3);
       HiCnapsMessageArea area = HiCnapsMessageAreaFactory.createCnapsMessageArea(mark);
 
       if (area == null) {
         ++index;
       }
 
       area.setCodeTable(this.codes);
       area.unpack(cnapsBuffer.substring(index));
       int length = area.getLength();
       index += length;
       this.buses.add(area);
     }
   }
 
   public HiCnapsMessageArea createMessageBodyArea(String[] fileds, String[] optfileds, HiETF etf)
     throws HiException
   {
     HiCnapsMessageArea bodyArea = HiCnapsMessageAreaFactory.createCnapsMessageArea(2);
 
     bodyArea.setCodeTable(this.codes);
     bodyArea.packFromETF(fileds, optfileds, etf);
 
     this.buses.add(bodyArea);
     return bodyArea;
   }
 
   public int getBatchNo() {
     return this.buses.size();
   }
 
   public static void main(String[] atr) throws Exception {
     String bacth = "{3::30A:20020618:32A:RMB000000077889900:011:106290000010:52A:310001003011:CC4:310001003011:50C:11000067267001                  :50A:毅龙公司                                                    :50B:北京                                                        :012:308521021016:58A:308521021016:CC5:000000000011:59C:11                              :59A:中华                                                        :59B:发达                                                        :CEF:11:0BC:00000184:010:1000:0B9:5210:72A:                                                            }{3::30A:20020618:32A:RMB000000077889900:011:106290000010:52A:310001003011:CC4:310001003011:50C:11000067267001                  :50A:毅龙公司                                                    :50B:北京                                                        :012:308521021016:58A:308521021016:CC5:000000000011:59C:11                              :59A:中华                                                        :59B:发达                                                        :CEF:11:0BC:00000184:010:1000:0B9:5210:72A:                                                            }";
     HiCnapsCodeTable table = HiCnapsCodeTable.load(new URL("file:E:/Temps/CNAPS_CFG.XML"));
     HiCnapsMessage cm = new HiCnapsMessage(table);
     cm.unpack(bacth);
     System.out.println(cm.getBatchNo());
   }
 }