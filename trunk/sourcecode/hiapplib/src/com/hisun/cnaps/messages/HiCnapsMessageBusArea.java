/*    */ package com.hisun.cnaps.messages;
/*    */ 
/*    */ import com.hisun.cnaps.CnapsTag;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ 
/*    */ public class HiCnapsMessageBusArea extends HiCnapsMessageArea
/*    */ {
/*    */   public HiCnapsMessageArea bodyarea;
/*    */   private int length;
/*    */ 
/*    */   public HiCnapsMessageBusArea()
/*    */   {
/* 15 */     this.bodyarea = null;
/* 16 */     this.length = 0;
/*    */   }
/*    */ 
/*    */   public String getString()
/*    */   {
/* 12 */     return this.bodyarea.getString();
/*    */   }
/*    */ 
/*    */   public void packFromETF(String[] as, String[] optFields, HiETF hietf)
/*    */     throws HiException
/*    */   {
/* 19 */     this.bodyarea = HiCnapsMessageAreaFactory.createCnapsMessageArea(3);
/* 20 */     this.bodyarea.setCodeTable(getCnapsCodeTable());
/* 21 */     this.bodyarea.packFromETF(as, optFields, hietf);
/*    */   }
/*    */ 
/*    */   public void unpack(String s)
/*    */     throws HiException
/*    */   {
/* 27 */     int index = s.indexOf("{3:");
/* 28 */     if (index == -1) {
/* 29 */       throw new HiException("241098", s);
/*    */     }
/* 31 */     String mark = s.substring(index, index + 3);
/* 32 */     this.length += index;
/* 33 */     this.bodyarea = HiCnapsMessageAreaFactory.createCnapsMessageArea(mark);
/* 34 */     this.bodyarea.setCodeTable(getCnapsCodeTable());
/* 35 */     if (this.bodyarea == null) {
/* 36 */       throw new HiException("241098", s);
/*    */     }
/* 38 */     this.bodyarea.unpack(s.substring(index));
/* 39 */     this.length += this.bodyarea.getLength();
/*    */   }
/*    */ 
/*    */   public CnapsTag getTagByIndex(int index) {
/* 43 */     return this.bodyarea.getTagByIndex(index);
/*    */   }
/*    */ 
/*    */   public int getTagCount() {
/* 47 */     return this.bodyarea.getTagCount();
/*    */   }
/*    */ 
/*    */   public int getLength() {
/* 51 */     return this.length;
/*    */   }
/*    */ }