/*    */ package com.hisun.cnaps.messages;
/*    */ 
/*    */ import com.hisun.cnaps.CnapsMessageArea;
/*    */ import com.hisun.cnaps.CnapsTag;
/*    */ import com.hisun.cnaps.HiCnapsCodeTable;
/*    */ import com.hisun.cnaps.tags.HiCnapsTag;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public abstract class HiCnapsMessageArea
/*    */   implements CnapsMessageArea
/*    */ {
/*    */   private HiCnapsCodeTable codes;
/*    */   private List tags;
/*    */ 
/*    */   public HiCnapsCodeTable getCnapsCodeTable()
/*    */   {
/* 22 */     return this.codes;
/*    */   }
/*    */ 
/*    */   protected void addTag(HiCnapsTag tag) {
/* 26 */     this.tags.add(tag);
/*    */   }
/*    */ 
/*    */   public HiCnapsMessageArea()
/*    */   {
/* 31 */     this.tags = new ArrayList();
/*    */   }
/*    */ 
/*    */   public void setCodeTable(HiCnapsCodeTable codes) {
/* 35 */     this.codes = codes;
/*    */   }
/*    */ 
/*    */   public int getTagCount() {
/* 39 */     return this.tags.size();
/*    */   }
/*    */ 
/*    */   public CnapsTag getTagByIndex(int index) {
/* 43 */     return ((CnapsTag)this.tags.get(index));
/*    */   }
/*    */ 
/*    */   public int getLength() {
/* 47 */     return getString().length();
/*    */   }
/*    */ 
/*    */   public abstract void unpack(String paramString) throws HiException;
/*    */ 
/*    */   public abstract void packFromETF(String[] paramArrayOfString1, String[] paramArrayOfString2, HiETF paramHiETF) throws HiException;
/*    */ 
/*    */   public abstract String getString();
/*    */ 
/*    */   public void unpack2Etf(HiETF etf) throws HiException
/*    */   {
/* 58 */     for (int i = 0; i < this.tags.size(); ++i) {
/* 59 */       HiCnapsTag tag = (HiCnapsTag)this.tags.get(i);
/* 60 */       tag.unpack2Etf(etf);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void unpack2Etf(String[] fileds, HiETF etf, boolean auth) throws HiException
/*    */   {
/* 66 */     int count = getTagCount();
/*    */ 
/* 68 */     for (int i = 0; i < fileds.length; ++i) {
/* 69 */       boolean found = false;
/* 70 */       String filed = fileds[i].toUpperCase();
/* 71 */       for (int p = 0; p < count; ++p) {
/* 72 */         HiCnapsTag tag = (HiCnapsTag)getTagByIndex(p);
/* 73 */         if (tag.getMarkName().equals(filed)) {
/* 74 */           tag.unpack2Etf(etf);
/* 75 */           found = true;
/* 76 */           if (StringUtils.isBlank(tag.getRepeatName())) {
/*    */             break;
/*    */           }
/*    */         }
/*    */       }
/*    */ 
/* 82 */       if ((!(found)) && (auth))
/* 83 */         throw new HiException("241104");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main(String[] rags) throws Exception
/*    */   {
/* 89 */     String s = ":30A:20020618:32A:RMB000000077889900:011:106290000010:52A:310001003011:CC4:310001003011:50C:11000067267001                  :50A:毅龙公司                                                    :50B:北京                                                        :012:01003011:58A:308521021016:CC5:000000000011:59C:11                              :59A:中华                                                        :59B:发达                                                        :CEF:11:0BC:00000184:010:1000:0B9:5210:72A:                                                            ";
/* 90 */     s.indexOf(":", 253);
/* 91 */     new HiCnapsMessage(null).unpack(s);
/* 92 */     System.out.println(s.indexOf(58, 253));
/* 93 */     System.out.println(s.substring(253, 266));
/*    */   }
/*    */ }