/*     */ package com.hisun.atmp.crypt;
/*     */ 
/*     */ public class Convertors
/*     */ {
/*   4 */   public static final char[] mask = { '1', '9', 'F', '2', 'C', '8', '2', '7', '6', 'A', 'D', '0', '8', '3', '9', 'B' };
/*     */ 
/*   6 */   public static final char[] step = { '3', '5', '8', '2', '7', '6', '2', '9' };
/*   7 */   public static final char[] keymask = { 'B', 'A', '9', '8', '7', 'D', '4', '0' };
/*     */   public static final int TRANKEY_LEN = 8;
/*     */ 
/*     */   static Date charDate(char[] data)
/*     */   {
/* 112 */     return new charDate(data);
/*     */   }
/*     */ 
/*     */   static Convertor map2(Date m, Map2 map)
/*     */   {
/* 134 */     return new Convertor(map, m) { private final Convertors.Map2 val$map;
/*     */       private final Convertors.Date val$m;
/*     */ 
/*     */       public Convertors.Date convert(Convertors.Date v) { return new Convertors.Date(v) { private final Convertors.Date val$v;
/*     */ 
/*     */           public int get(int i) { return Convertors.1.this.val$map.c(this.val$v.get(i), Convertors.1.this.val$m.get(i));
/*     */           }
/*     */ 
/*     */           public int len() {
/* 142 */             return this.val$v.len();
/*     */           }
/*     */         };
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   static Convertor xor(Date m) {
/* 150 */     return map2(m, Maps.mask());
/*     */   }
/*     */ 
/*     */   static Convertor leftpad(int c, int len)
/*     */   {
/* 155 */     return new Convertor(len, c) { private final int val$len;
/*     */       private final int val$c;
/*     */ 
/*     */       public Convertors.Date convert(Convertors.Date v) { return v.leftpad(this.val$len, this.val$c);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   static Convertor rightpad(int c, int len)
/*     */   {
/* 164 */     return new Convertor(len, c) { private final int val$len;
/*     */       private final int val$c;
/*     */ 
/*     */       public Convertors.Date convert(Convertors.Date v) { return v.pad(0, this.val$len, this.val$c);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   static Convertor leftpad() {
/* 172 */     return leftpad(0, 16);
/*     */   }
/*     */ 
/*     */   static Convertor right(int len)
/*     */   {
/* 177 */     return new Convertor(len) { private final int val$len;
/*     */ 
/*     */       public Convertors.Date convert(Convertors.Date v) { return new Convertors.Date(v) { private int pos = this.val$v.len() - Convertors.4.this.val$len;
/*     */           private final Convertors.Date val$v;
/*     */ 
/*     */           public int get(int i) {
/* 183 */             return this.val$v.get(i + this.pos);
/*     */           }
/*     */ 
/*     */           public int len() {
/* 187 */             return Convertors.4.this.val$len;
/*     */           }
/*     */         };
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   static Convertor pack()
/*     */   {
/* 196 */     return new Convertor() {
/*     */       public Convertors.Date convert(Convertors.Date v) {
/* 198 */         return new Convertors.Date(v) { private final Convertors.Date val$v;
/*     */ 
/*     */           public int get(int i) { return (this.val$v.get(2 * i) * 16 + this.val$v.get(2 * i + 1));
/*     */           }
/*     */ 
/*     */           public int len() {
/* 204 */             return (this.val$v.len() / 2);
/*     */           }
/*     */         };
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   static Convertor unpack()
/*     */   {
/* 213 */     return new Convertor() {
/*     */       public Convertors.Date convert(Convertors.Date v) {
/* 215 */         return new Convertors.Date(v) { private final Convertors.Date val$v;
/*     */ 
/*     */           public int get(int i) { int n = this.val$v.get(i / 2);
/* 218 */             if (i % 2 == 0) {
/* 219 */               return (n / 16);
/*     */             }
/* 221 */             return (n % 16);
/*     */           }
/*     */ 
/*     */           public int len() {
/* 225 */             return (this.val$v.len() * 2);
/*     */           }
/*     */         };
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   static Convertor incadd(Date key)
/*     */   {
/* 234 */     return new Convertor(key) { private final Convertors.Date val$key;
/*     */ 
/*     */       public Convertors.Date convert(Convertors.Date v) { Convertors.charDate data = new Convertors.charDate(new char[v.len()]);
/*     */ 
/* 238 */         int a = 0;
/* 239 */         for (int i = v.len() - 1; i >= 0; --i) {
/* 240 */           int n = v.get(i) + this.val$key.get(i) + a;
/* 241 */           data.set(i, n % 10);
/* 242 */           a = n / 10;
/*     */         }
/* 244 */         return data;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   static Convertor incsub(Date key)
/*     */   {
/* 251 */     return new Convertor(key) { private final Convertors.Date val$key;
/*     */ 
/*     */       public Convertors.Date convert(Convertors.Date v) { Convertors.charDate data = new Convertors.charDate(new char[v.len()]);
/*     */ 
/* 255 */         int a = 0;
/* 256 */         for (int i = v.len() - 1; i >= 0; --i) {
/* 257 */           int n = v.get(i) - this.val$key.get(i) - a;
/* 258 */           if (n < 0) {
/* 259 */             n += 10;
/* 260 */             a = 1;
/*     */           } else {
/* 262 */             a = 0; }
/* 263 */           data.set(i, n % 10);
/*     */         }
/* 265 */         return data;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   static Convertor bind(Convertor c1, Convertor c2)
/*     */   {
/* 272 */     return new Convertor(c2, c1) { private final Convertor val$c2;
/*     */       private final Convertor val$c1;
/*     */ 
/*     */       public Convertors.Date convert(Convertors.Date v) { return this.val$c2.convert(this.val$c1.convert(v));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   static Convertor pinblockkey(Date key) {
/* 280 */     return new Convertor(key) { private final Convertors.Date val$key;
/*     */ 
/*     */       public Convertors.Date convert(Convertors.Date v) { return new Convertors.Date(v) { private final Convertors.Date val$v;
/*     */ 
/*     */           public int get(int i) { int n = this.val$v.get(i);
/* 285 */             if (n > 9)
/* 286 */               n = 0;
/* 287 */             return Convertors.10.this.val$key.get(n);
/*     */           }
/*     */ 
/*     */           public int len() {
/* 291 */             return this.val$v.len();
/*     */           }
/*     */         };
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   static Convertor pinblockkey() {
/* 299 */     return rightpad(0, 8).pinblockkey(charDate("1122447788".toCharArray()));
/*     */   }
/*     */ 
/*     */   static Convertor des(char[] encryptKey, int flag)
/*     */   {
/* 306 */     return null;
/*     */   }
/*     */ 
/*     */   static Convertor pinblock(char[] trm_key) {
/* 310 */     char[] k = pinblockkey().convert(trm_key);
/* 311 */     return pack().des(k, 1).unpack();
/*     */   }
/*     */ 
/*     */   static Convertor shuffle(char[] trm_key, char[] mask) {
/* 315 */     return leftpad(0, 16).incadd(charDate(trm_key).leftpad(16, 0)).xor(charDate(mask));
/*     */   }
/*     */ 
/*     */   static Convertor unshuffle(char[] trm_key, char[] mask)
/*     */   {
/* 320 */     return leftpad(0, 16).xor(charDate(mask)).incsub(charDate(trm_key).leftpad(16, 0));
/*     */   }
/*     */ 
/*     */   public static char[] shuffle2(char[] in, char[] trm_key, char[] mask)
/*     */   {
/* 326 */     return shuffle(trm_key, mask).right(in.length).convert(in);
/*     */   }
/*     */ 
/*     */   public static char[] unshuffle2(char[] in, char[] trm_key, char[] mask)
/*     */   {
/* 331 */     return unshuffle(trm_key, mask).right(in.length).convert(in);
/*     */   }
/*     */ 
/*     */   static class Maps
/*     */   {
/* 120 */     private static Convertors.Map2 _mask = new Convertors.Map2()
/*     */     {
/*     */       public int c(int v1, int v2) {
/* 123 */         return (v1 ^ v2);
/*     */       }
/* 120 */     };
/*     */ 
/*     */     static Convertors.Map2 mask()
/*     */     {
/* 128 */       return _mask;
/*     */     }
/*     */   }
/*     */ 
/*     */   static abstract interface Map2
/*     */   {
/*     */     public abstract int c(int paramInt1, int paramInt2);
/*     */   }
/*     */ 
/*     */   static class charDate extends Convertors.Date
/*     */   {
/*     */     final char[] data;
/*     */ 
/*     */     public charDate(char[] d)
/*     */     {
/*  91 */       this.data = d;
/*     */     }
/*     */ 
/*     */     public charDate(String s) {
/*  95 */       this.data = s.toCharArray();
/*     */     }
/*     */ 
/*     */     public int get(int i) {
/*  99 */       return toint(this.data[i]);
/*     */     }
/*     */ 
/*     */     public int len() {
/* 103 */       return this.data.length;
/*     */     }
/*     */ 
/*     */     public void set(int i, int v) {
/* 107 */       this.data[i] = tochar(v);
/*     */     }
/*     */   }
/*     */ 
/*     */   static abstract class Date
/*     */   {
/*     */     public abstract int len();
/*     */ 
/*     */     public abstract int get(int paramInt);
/*     */ 
/*     */     public char[] toChars()
/*     */     {
/*  19 */       char[] data = new char[len()];
/*  20 */       for (int i = 0; i < data.length; ++i)
/*  21 */         data[i] = tochar(get(i));
/*  22 */       return data;
/*     */     }
/*     */ 
/*     */     public int toint(char c) {
/*  26 */       return ((c >= 'A') ? c - 'A' + 10 : c - '0');
/*     */     }
/*     */ 
/*     */     public char tochar(int v) {
/*  30 */       return (char)((v < 10) ? v + 48 : v % 10 + 65);
/*     */     }
/*     */ 
/*     */     public Date reverse() {
/*  34 */       Date data = this;
/*  35 */       return new Date(data) { private final Convertors.Date val$data;
/*     */ 
/*     */         public int get(int i) { return this.val$data.get(this.val$data.len() - i - 1);
/*     */         }
/*     */ 
/*     */         public int len() {
/*  41 */           return this.val$data.len();
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     private Date pad(int pos, int length, int c) {
/*  47 */       Date data = this;
/*  48 */       return new Date(pos, data, c, length) { private final int val$pos;
/*     */         private final Convertors.Date val$data;
/*     */         private final int val$c;
/*     */         private final int val$length;
/*     */ 
/*     */         public int get(int i) { if ((i >= this.val$pos) && (i < this.val$pos + this.val$data.len()))
/*  51 */             return this.val$data.get(i - this.val$pos);
/*  52 */           return this.val$c;
/*     */         }
/*     */ 
/*     */         public int len() {
/*  56 */           return this.val$length;
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     public Date leftpad(int len, int c) {
/*  62 */       return pad(len - len(), len, c);
/*     */     }
/*     */ 
/*     */     public Date rightpad(int len, int c) {
/*  66 */       return pad(0, len, c);
/*     */     }
/*     */ 
/*     */     public Date append(Date d) {
/*  70 */       Date d1 = this;
/*  71 */       return new Date(d1, d) { final int len = this.val$d1.len() + this.val$d.len();
/*     */         private final Convertors.Date val$d1;
/*     */         private final Convertors.Date val$d;
/*     */ 
/*     */         public int get(int i) { if (i < this.val$d1.len())
/*  76 */             return this.val$d1.get(i);
/*  77 */           return this.val$d.get(i - this.val$d1.len());
/*     */         }
/*     */ 
/*     */         public int len() {
/*  81 */           return this.len;
/*     */         }
/*     */       };
/*     */     }
/*     */   }
/*     */ }