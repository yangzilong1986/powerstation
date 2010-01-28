 package com.hisun.atmp.crypt;
 
 public abstract class Convertor
 {
   public abstract Convertors.Date convert(Convertors.Date paramDate);
 
   public Convertor bind(Convertor c)
   {
     return Convertors.bind(this, c);
   }
 
   public char[] convert(char[] data) {
     return convert(Convertors.charDate(data)).toChars();
   }
 
   public char[] convert(String data) {
     return convert(data.toCharArray());
   }
 
   public Convertor xor(Convertors.Date m)
   {
     return bind(Convertors.xor(m));
   }
 
   public Convertor pack() {
     return bind(Convertors.pack());
   }
 
   public Convertor unpack() {
     return bind(Convertors.unpack());
   }
 
   public Convertor leftpad(int c, int len) {
     return bind(Convertors.leftpad(c, len));
   }
 
   public Convertor rightpad(int c, int len) {
     return bind(Convertors.rightpad(c, len));
   }
 
   public Convertor right(int len) {
     return bind(Convertors.right(len));
   }
 
   public Convertor incadd(Convertors.Date m) {
     return bind(Convertors.incadd(m));
   }
 
   public Convertor incsub(Convertors.Date m) {
     return bind(Convertors.incsub(m));
   }
 
   public Convertor pinblockkey(Convertors.Date m) {
     return bind(Convertors.pinblockkey(m));
   }
 
   public Convertor des(char[] encryptKey, int flag) {
     return bind(Convertors.des(encryptKey, flag));
   }
 }