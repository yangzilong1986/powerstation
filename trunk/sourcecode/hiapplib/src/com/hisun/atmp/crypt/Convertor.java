/*    */ package com.hisun.atmp.crypt;
/*    */ 
/*    */ public abstract class Convertor
/*    */ {
/*    */   public abstract Convertors.Date convert(Convertors.Date paramDate);
/*    */ 
/*    */   public Convertor bind(Convertor c)
/*    */   {
/* 12 */     return Convertors.bind(this, c);
/*    */   }
/*    */ 
/*    */   public char[] convert(char[] data) {
/* 16 */     return convert(Convertors.charDate(data)).toChars();
/*    */   }
/*    */ 
/*    */   public char[] convert(String data) {
/* 20 */     return convert(data.toCharArray());
/*    */   }
/*    */ 
/*    */   public Convertor xor(Convertors.Date m)
/*    */   {
/* 25 */     return bind(Convertors.xor(m));
/*    */   }
/*    */ 
/*    */   public Convertor pack() {
/* 29 */     return bind(Convertors.pack());
/*    */   }
/*    */ 
/*    */   public Convertor unpack() {
/* 33 */     return bind(Convertors.unpack());
/*    */   }
/*    */ 
/*    */   public Convertor leftpad(int c, int len) {
/* 37 */     return bind(Convertors.leftpad(c, len));
/*    */   }
/*    */ 
/*    */   public Convertor rightpad(int c, int len) {
/* 41 */     return bind(Convertors.rightpad(c, len));
/*    */   }
/*    */ 
/*    */   public Convertor right(int len) {
/* 45 */     return bind(Convertors.right(len));
/*    */   }
/*    */ 
/*    */   public Convertor incadd(Convertors.Date m) {
/* 49 */     return bind(Convertors.incadd(m));
/*    */   }
/*    */ 
/*    */   public Convertor incsub(Convertors.Date m) {
/* 53 */     return bind(Convertors.incsub(m));
/*    */   }
/*    */ 
/*    */   public Convertor pinblockkey(Convertors.Date m) {
/* 57 */     return bind(Convertors.pinblockkey(m));
/*    */   }
/*    */ 
/*    */   public Convertor des(char[] encryptKey, int flag) {
/* 61 */     return bind(Convertors.des(encryptKey, flag));
/*    */   }
/*    */ }