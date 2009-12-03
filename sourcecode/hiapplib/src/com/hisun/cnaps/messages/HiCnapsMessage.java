/*    */ package com.hisun.cnaps.messages;
/*    */ 
/*    */ import com.hisun.cnaps.HiCnapsCodeTable;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import java.io.PrintStream;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HiCnapsMessage
/*    */ {
/*    */   HiCnapsCodeTable codes;
/*    */   private List buses;
/*    */ 
/*    */   public HiCnapsMessage(HiCnapsCodeTable codeTable)
/*    */   {
/* 20 */     this.codes = null;
/* 21 */     this.codes = codeTable;
/* 22 */     this.buses = new ArrayList();
/*    */   }
/*    */ 
/*    */   public HiCnapsMessageArea getMessageBusArea(int i) {
/* 26 */     if (i < 0)
/* 27 */       throw new IllegalArgumentException(String.valueOf(i));
/* 28 */     return ((HiCnapsMessageArea)this.buses.get(--i));
/*    */   }
/*    */ 
/*    */   public void unpack(String cnapsBuffer) throws HiException {
/* 32 */     int index = 0;
/* 33 */     while ((index = cnapsBuffer.indexOf(123, index)) != -1) {
/* 34 */       String mark = cnapsBuffer.substring(index, index + 3);
/* 35 */       HiCnapsMessageArea area = HiCnapsMessageAreaFactory.createCnapsMessageArea(mark);
/*    */ 
/* 37 */       if (area == null) {
/* 38 */         ++index;
/*    */       }
/*    */ 
/* 41 */       area.setCodeTable(this.codes);
/* 42 */       area.unpack(cnapsBuffer.substring(index));
/* 43 */       int length = area.getLength();
/* 44 */       index += length;
/* 45 */       this.buses.add(area);
/*    */     }
/*    */   }
/*    */ 
/*    */   public HiCnapsMessageArea createMessageBodyArea(String[] fileds, String[] optfileds, HiETF etf)
/*    */     throws HiException
/*    */   {
/* 52 */     HiCnapsMessageArea bodyArea = HiCnapsMessageAreaFactory.createCnapsMessageArea(2);
/*    */ 
/* 54 */     bodyArea.setCodeTable(this.codes);
/* 55 */     bodyArea.packFromETF(fileds, optfileds, etf);
/*    */ 
/* 57 */     this.buses.add(bodyArea);
/* 58 */     return bodyArea;
/*    */   }
/*    */ 
/*    */   public int getBatchNo() {
/* 62 */     return this.buses.size();
/*    */   }
/*    */ 
/*    */   public static void main(String[] atr) throws Exception {
/* 66 */     String bacth = "{3::30A:20020618:32A:RMB000000077889900:011:106290000010:52A:310001003011:CC4:310001003011:50C:11000067267001                  :50A:毅龙公司                                                    :50B:北京                                                        :012:308521021016:58A:308521021016:CC5:000000000011:59C:11                              :59A:中华                                                        :59B:发达                                                        :CEF:11:0BC:00000184:010:1000:0B9:5210:72A:                                                            }{3::30A:20020618:32A:RMB000000077889900:011:106290000010:52A:310001003011:CC4:310001003011:50C:11000067267001                  :50A:毅龙公司                                                    :50B:北京                                                        :012:308521021016:58A:308521021016:CC5:000000000011:59C:11                              :59A:中华                                                        :59B:发达                                                        :CEF:11:0BC:00000184:010:1000:0B9:5210:72A:                                                            }";
/* 67 */     HiCnapsCodeTable table = HiCnapsCodeTable.load(new URL("file:E:/Temps/CNAPS_CFG.XML"));
/* 68 */     HiCnapsMessage cm = new HiCnapsMessage(table);
/* 69 */     cm.unpack(bacth);
/* 70 */     System.out.println(cm.getBatchNo());
/*    */   }
/*    */ }