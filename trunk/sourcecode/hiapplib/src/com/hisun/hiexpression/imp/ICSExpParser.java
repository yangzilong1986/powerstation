/*     */ package com.hisun.hiexpression.imp;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class ICSExpParser
/*     */   implements ICSExpParserTreeConstants, ICSExpParserConstants
/*     */ {
/*     */   protected JJTICSExpParserState jjtree;
/*     */   public ICSExpParserTokenManager token_source;
/*     */   JavaCharStream jj_input_stream;
/*     */   public Token token;
/*     */   public Token jj_nt;
/*     */   private int jj_ntk;
/*     */   private Token jj_scanpos;
/*     */   private Token jj_lastpos;
/*     */   private int jj_la;
/*     */   public boolean lookingAhead;
/*     */   private boolean jj_semLA;
/*     */   private int jj_gen;
/*     */   private final int[] jj_la1;
/*     */   private static int[] jj_la1_0;
/*     */   private static int[] jj_la1_1;
/*     */   private final JJCalls[] jj_2_rtns;
/*     */   private boolean jj_rescan;
/*     */   private int jj_gc;
/*     */   private final LookaheadSuccess jj_ls;
/*     */   private Vector jj_expentries;
/*     */   private int[] jj_expentry;
/*     */   private int jj_kind;
/*     */   private int[] jj_lasttokens;
/*     */   private int jj_endpos;
/*     */ 
/*     */   public final Node topLevelExpression()
/*     */     throws ParseException
/*     */   {
/*  18 */     equalityExpression();
/*  19 */     jj_consume_token(0);
/*  20 */     return this.jjtree.rootNode();
/*     */   }
/*     */ 
/*     */   public final void equalityExpression()
/*     */     throws ParseException
/*     */   {
/*  26 */     relationalExpression();
/*     */     while (true)
/*     */     {
/*  29 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
/*     */       {
/*     */       case 1:
/*     */       case 2:
/*  33 */         break;
/*     */       default:
/*  35 */         this.jj_la1[0] = this.jj_gen;
/*  36 */         break;
/*     */       }
/*  38 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
/*     */       {
/*     */       case 1:
/*  40 */         jj_consume_token(1);
/*  41 */         ASTEq jjtn001 = new ASTEq(1);
/*  42 */         boolean jjtc001 = true;
/*  43 */         this.jjtree.openNodeScope(jjtn001);
/*     */         try {
/*  45 */           relationalExpression();
/*     */         } catch (Throwable jjte001) {
/*  47 */           if (jjtc001) {
/*  48 */             this.jjtree.clearNodeScope(jjtn001);
/*  49 */             jjtc001 = false;
/*     */           } else {
/*  51 */             this.jjtree.popNode();
/*     */           }
/*  53 */           if (jjte001 instanceof RuntimeException) {
/*  54 */             throw ((RuntimeException)jjte001);
/*     */           }
/*     */ 
/*  59 */           throw ((Error)jjte001);
/*     */         } finally {
/*  61 */           if (jjtc001) {
/*  62 */             this.jjtree.closeNodeScope(jjtn001, 2);
/*     */           }
/*     */         }
/*  65 */         break;
/*     */       case 2:
/*     */         int i;
/*  67 */         jj_consume_token(2);
/*  68 */         ASTNotEq jjtn002 = new ASTNotEq(2);
/*  69 */         int i = 1;
/*  70 */         this.jjtree.openNodeScope(jjtn002);
/*     */         try {
/*  72 */           relationalExpression();
/*     */         } catch (Throwable jjte002) {
/*  74 */           if (i != 0) {
/*  75 */             this.jjtree.clearNodeScope(jjtn002);
/*  76 */             i = 0;
/*     */           } else {
/*  78 */             this.jjtree.popNode();
/*     */           }
/*  80 */           if (jjte002 instanceof RuntimeException) {
/*  81 */             throw ((RuntimeException)jjte002);
/*     */           }
/*     */ 
/*  86 */           throw ((Error)jjte002);
/*     */         } finally {
/*  88 */           if (i != 0) {
/*  89 */             this.jjtree.closeNodeScope(jjtn002, 2);
/*     */           }
/*     */         }
/*  92 */         break;
/*     */       default:
/*  94 */         this.jj_la1[1] = this.jj_gen;
/*  95 */         jj_consume_token(-1);
/*  96 */         throw new ParseException();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void relationalExpression() throws ParseException
/*     */   {
/* 103 */     primaryExpression();
/*     */     while (true)
/*     */     {
/* 106 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
/*     */       {
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/* 112 */         break;
/*     */       default:
/* 114 */         this.jj_la1[2] = this.jj_gen;
/* 115 */         break;
/*     */       }
/* 117 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
/*     */       {
/*     */       case 3:
/* 119 */         jj_consume_token(3);
/* 120 */         ASTLess jjtn001 = new ASTLess(3);
/* 121 */         boolean jjtc001 = true;
/* 122 */         this.jjtree.openNodeScope(jjtn001);
/*     */         try {
/* 124 */           primaryExpression();
/*     */         } catch (Throwable jjte001) {
/* 126 */           if (jjtc001) {
/* 127 */             this.jjtree.clearNodeScope(jjtn001);
/* 128 */             jjtc001 = false;
/*     */           } else {
/* 130 */             this.jjtree.popNode();
/*     */           }
/* 132 */           if (jjte001 instanceof RuntimeException) {
/* 133 */             throw ((RuntimeException)jjte001);
/*     */           }
/*     */ 
/* 138 */           throw ((Error)jjte001);
/*     */         } finally {
/* 140 */           if (jjtc001) {
/* 141 */             this.jjtree.closeNodeScope(jjtn001, 2);
/*     */           }
/*     */         }
/* 144 */         break;
/*     */       case 4:
/*     */         int i;
/* 146 */         jj_consume_token(4);
/* 147 */         ASTGreater jjtn002 = new ASTGreater(4);
/* 148 */         int i = 1;
/* 149 */         this.jjtree.openNodeScope(jjtn002);
/*     */         try {
/* 151 */           primaryExpression();
/*     */         } catch (Throwable jjte002) {
/* 153 */           if (i != 0) {
/* 154 */             this.jjtree.clearNodeScope(jjtn002);
/* 155 */             i = 0;
/*     */           } else {
/* 157 */             this.jjtree.popNode();
/*     */           }
/* 159 */           if (jjte002 instanceof RuntimeException) {
/* 160 */             throw ((RuntimeException)jjte002);
/*     */           }
/*     */ 
/* 165 */           throw ((Error)jjte002);
/*     */         } finally {
/* 167 */           if (i != 0) {
/* 168 */             this.jjtree.closeNodeScope(jjtn002, 2);
/*     */           }
/*     */         }
/* 171 */         break;
/*     */       case 5:
/*     */         int j;
/* 173 */         jj_consume_token(5);
/* 174 */         ASTLessEq jjtn003 = new ASTLessEq(5);
/* 175 */         int j = 1;
/* 176 */         this.jjtree.openNodeScope(jjtn003);
/*     */         try {
/* 178 */           primaryExpression();
/*     */         } catch (Throwable jjte003) {
/* 180 */           if (j != 0) {
/* 181 */             this.jjtree.clearNodeScope(jjtn003);
/* 182 */             j = 0;
/*     */           } else {
/* 184 */             this.jjtree.popNode();
/*     */           }
/* 186 */           if (jjte003 instanceof RuntimeException) {
/* 187 */             throw ((RuntimeException)jjte003);
/*     */           }
/*     */ 
/* 192 */           throw ((Error)jjte003);
/*     */         } finally {
/* 194 */           if (j != 0) {
/* 195 */             this.jjtree.closeNodeScope(jjtn003, 2);
/*     */           }
/*     */         }
/* 198 */         break;
/*     */       case 6:
/*     */         int k;
/* 200 */         jj_consume_token(6);
/* 201 */         ASTGreaterEq jjtn004 = new ASTGreaterEq(6);
/* 202 */         int k = 1;
/* 203 */         this.jjtree.openNodeScope(jjtn004);
/*     */         try {
/* 205 */           primaryExpression();
/*     */         } catch (Throwable jjte004) {
/* 207 */           if (k != 0) {
/* 208 */             this.jjtree.clearNodeScope(jjtn004);
/* 209 */             k = 0;
/*     */           } else {
/* 211 */             this.jjtree.popNode();
/*     */           }
/* 213 */           if (jjte004 instanceof RuntimeException) {
/* 214 */             throw ((RuntimeException)jjte004);
/*     */           }
/*     */ 
/* 219 */           throw ((Error)jjte004);
/*     */         } finally {
/* 221 */           if (k != 0) {
/* 222 */             this.jjtree.closeNodeScope(jjtn004, 2);
/*     */           }
/*     */         }
/* 225 */         break;
/*     */       default:
/* 227 */         this.jj_la1[3] = this.jj_gen;
/* 228 */         jj_consume_token(-1);
/* 229 */         throw new ParseException();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void icsValueRef()
/*     */     throws ParseException
/*     */   {
/*     */     Token t;
/* 237 */     switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
/*     */     {
/*     */     case 7:
/* 239 */       jj_consume_token(7);
/* 240 */       t = jj_consume_token(29);
/* 241 */       ASTIcsVarRef jjtn001 = new ASTIcsVarRef(7);
/* 242 */       boolean jjtc001 = true;
/* 243 */       this.jjtree.openNodeScope(jjtn001);
/*     */       try {
/* 245 */         this.jjtree.closeNodeScope(jjtn001, 0);
/* 246 */         jjtc001 = false;
/* 247 */         jjtn001.setItemName(0, t.image);
/*     */       } finally {
/* 249 */         if (jjtc001) {
/* 250 */           this.jjtree.closeNodeScope(jjtn001, 0);
/*     */         }
/*     */       }
/* 253 */       break;
/*     */     case 8:
/* 255 */       jj_consume_token(8);
/* 256 */       t = jj_consume_token(29);
/* 257 */       ASTIcsVarRef jjtn002 = new ASTIcsVarRef(7);
/* 258 */       boolean jjtc002 = true;
/* 259 */       this.jjtree.openNodeScope(jjtn002);
/*     */       try {
/* 261 */         this.jjtree.closeNodeScope(jjtn002, 0);
/* 262 */         jjtc002 = false;
/* 263 */         jjtn002.setItemName(1, t.image);
/*     */       } finally {
/* 265 */         if (jjtc002) {
/* 266 */           this.jjtree.closeNodeScope(jjtn002, 0);
/*     */         }
/*     */       }
/* 269 */       break;
/*     */     case 9:
/* 271 */       jj_consume_token(9);
/* 272 */       t = jj_consume_token(29);
/* 273 */       ASTIcsVarRef jjtn003 = new ASTIcsVarRef(7);
/* 274 */       boolean jjtc003 = true;
/* 275 */       this.jjtree.openNodeScope(jjtn003);
/*     */       try {
/* 277 */         this.jjtree.closeNodeScope(jjtn003, 0);
/* 278 */         jjtc003 = false;
/* 279 */         jjtn003.setItemName(2, t.image);
/*     */       } finally {
/* 281 */         if (jjtc003) {
/* 282 */           this.jjtree.closeNodeScope(jjtn003, 0);
/*     */         }
/*     */       }
/* 285 */       break;
/*     */     case 10:
/* 287 */       jj_consume_token(10);
/* 288 */       t = jj_consume_token(29);
/* 289 */       ASTIcsVarRef jjtn004 = new ASTIcsVarRef(7);
/* 290 */       boolean jjtc004 = true;
/* 291 */       this.jjtree.openNodeScope(jjtn004);
/*     */       try {
/* 293 */         this.jjtree.closeNodeScope(jjtn004, 0);
/* 294 */         jjtc004 = false;
/* 295 */         jjtn004.setItemName(3, t.image);
/*     */       } finally {
/* 297 */         if (jjtc004) {
/* 298 */           this.jjtree.closeNodeScope(jjtn004, 0);
/*     */         }
/*     */       }
/* 301 */       break;
/*     */     case 11:
/* 303 */       jj_consume_token(11);
/* 304 */       t = jj_consume_token(29);
/* 305 */       ASTIcsVarRef jjtn005 = new ASTIcsVarRef(7);
/* 306 */       boolean jjtc005 = true;
/* 307 */       this.jjtree.openNodeScope(jjtn005);
/*     */       try {
/* 309 */         this.jjtree.closeNodeScope(jjtn005, 0);
/* 310 */         jjtc005 = false;
/* 311 */         jjtn005.setItemName(4, t.image);
/*     */       } finally {
/* 313 */         if (jjtc005) {
/* 314 */           this.jjtree.closeNodeScope(jjtn005, 0);
/*     */         }
/*     */       }
/* 317 */       break;
/*     */     case 12:
/* 319 */       jj_consume_token(12);
/* 320 */       t = jj_consume_token(29);
/* 321 */       ASTIcsVarRef jjtn006 = new ASTIcsVarRef(7);
/* 322 */       boolean jjtc006 = true;
/* 323 */       this.jjtree.openNodeScope(jjtn006);
/*     */       try {
/* 325 */         this.jjtree.closeNodeScope(jjtn006, 0);
/* 326 */         jjtc006 = false;
/* 327 */         jjtn006.setItemName(6, t.image);
/*     */       } finally {
/* 329 */         if (jjtc006) {
/* 330 */           this.jjtree.closeNodeScope(jjtn006, 0);
/*     */         }
/*     */       }
/* 333 */       break;
/*     */     case 13:
/* 335 */       jj_consume_token(13);
/* 336 */       t = jj_consume_token(29);
/* 337 */       ASTIcsVarRef jjtn007 = new ASTIcsVarRef(7);
/* 338 */       boolean jjtc007 = true;
/* 339 */       this.jjtree.openNodeScope(jjtn007);
/*     */       try {
/* 341 */         this.jjtree.closeNodeScope(jjtn007, 0);
/* 342 */         jjtc007 = false;
/* 343 */         jjtn007.setItemName(3, t.image);
/*     */       } finally {
/* 345 */         if (jjtc007) {
/* 346 */           this.jjtree.closeNodeScope(jjtn007, 0);
/*     */         }
/*     */       }
/* 349 */       break;
/*     */     case 14:
/* 351 */       jj_consume_token(14);
/* 352 */       t = jj_consume_token(29);
/* 353 */       ASTIcsVarRef jjtn008 = new ASTIcsVarRef(7);
/* 354 */       boolean jjtc008 = true;
/* 355 */       this.jjtree.openNodeScope(jjtn008);
/*     */       try {
/* 357 */         this.jjtree.closeNodeScope(jjtn008, 0);
/* 358 */         jjtc008 = false;
/* 359 */         jjtn008.setItemName(0, t.image);
/*     */       } finally {
/* 361 */         if (jjtc008) {
/* 362 */           this.jjtree.closeNodeScope(jjtn008, 0);
/*     */         }
/*     */       }
/* 365 */       break;
/*     */     case 15:
/* 367 */       jj_consume_token(15);
/* 368 */       t = jj_consume_token(29);
/* 369 */       ASTIcsVarRef jjtn009 = new ASTIcsVarRef(7);
/* 370 */       boolean jjtc009 = true;
/* 371 */       this.jjtree.openNodeScope(jjtn009);
/*     */       try {
/* 373 */         this.jjtree.closeNodeScope(jjtn009, 0);
/* 374 */         jjtc009 = false;
/* 375 */         jjtn009.setItemName(2, t.image);
/*     */       } finally {
/* 377 */         if (jjtc009) {
/* 378 */           this.jjtree.closeNodeScope(jjtn009, 0);
/*     */         }
/*     */       }
/* 381 */       break;
/*     */     case 16:
/* 383 */       jj_consume_token(16);
/* 384 */       t = jj_consume_token(29);
/* 385 */       ASTIcsVarRef jjtn010 = new ASTIcsVarRef(7);
/* 386 */       boolean jjtc010 = true;
/* 387 */       this.jjtree.openNodeScope(jjtn010);
/*     */       try {
/* 389 */         this.jjtree.closeNodeScope(jjtn010, 0);
/* 390 */         jjtc010 = false;
/* 391 */         jjtn010.setItemName(4, t.image);
/*     */       } finally {
/* 393 */         if (jjtc010) {
/* 394 */           this.jjtree.closeNodeScope(jjtn010, 0);
/*     */         }
/*     */       }
/* 397 */       break;
/*     */     case 17:
/* 399 */       jj_consume_token(17);
/* 400 */       t = jj_consume_token(29);
/* 401 */       ASTIcsVarRef jjtn011 = new ASTIcsVarRef(7);
/* 402 */       boolean jjtc011 = true;
/* 403 */       this.jjtree.openNodeScope(jjtn011);
/*     */       try {
/* 405 */         this.jjtree.closeNodeScope(jjtn011, 0);
/* 406 */         jjtc011 = false;
/* 407 */         jjtn011.setItemName(5, t.image);
/*     */       } finally {
/* 409 */         if (jjtc011) {
/* 410 */           this.jjtree.closeNodeScope(jjtn011, 0);
/*     */         }
/*     */       }
/* 413 */       break;
/*     */     default:
/* 415 */       this.jj_la1[4] = this.jj_gen;
/* 416 */       jj_consume_token(-1);
/* 417 */       throw new ParseException();
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void primaryExpression() throws ParseException
/*     */   {
/* 423 */     String className = null;
/* 424 */     switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
/*     */     {
/*     */     case 18:
/* 426 */       jj_consume_token(18);
/* 427 */       ASTConst jjtn001 = new ASTConst(8);
/* 428 */       boolean jjtc001 = true;
/* 429 */       this.jjtree.openNodeScope(jjtn001);
/*     */       try {
/* 431 */         this.jjtree.closeNodeScope(jjtn001, 0);
/* 432 */         jjtc001 = false;
/* 433 */         jjtn001.setValue(Boolean.TRUE);
/*     */       } finally {
/* 435 */         if (jjtc001) {
/* 436 */           this.jjtree.closeNodeScope(jjtn001, 0);
/*     */         }
/*     */       }
/* 439 */       break;
/*     */     case 19:
/* 441 */       jj_consume_token(19);
/* 442 */       ASTConst jjtn002 = new ASTConst(8);
/* 443 */       boolean jjtc002 = true;
/* 444 */       this.jjtree.openNodeScope(jjtn002);
/*     */       try {
/* 446 */         this.jjtree.closeNodeScope(jjtn002, 0);
/* 447 */         jjtc002 = false;
/* 448 */         jjtn002.setValue(Boolean.FALSE);
/*     */       } finally {
/* 450 */         if (jjtc002) {
/* 451 */           this.jjtree.closeNodeScope(jjtn002, 0);
/*     */         }
/*     */       }
/* 454 */       break;
/*     */     case 20:
/* 456 */       ASTConst jjtn003 = new ASTConst(8);
/* 457 */       boolean jjtc003 = true;
/* 458 */       this.jjtree.openNodeScope(jjtn003);
/*     */       try {
/* 460 */         jj_consume_token(20);
/*     */       } finally {
/* 462 */         if (jjtc003) {
/* 463 */           this.jjtree.closeNodeScope(jjtn003, 0);
/*     */         }
/*     */       }
/* 466 */       break;
/*     */     default:
/* 468 */       this.jj_la1[6] = this.jj_gen;
/* 469 */       if (jj_2_1(2)) {
/* 470 */         staticMethodCall();
/* 471 */         return; }
/* 472 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
/*     */       {
/*     */       case 37:
/*     */       case 40:
/*     */       case 43:
/* 476 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
/*     */         {
/*     */         case 37:
/* 478 */           jj_consume_token(37);
/* 479 */           break;
/*     */         case 40:
/* 481 */           jj_consume_token(40);
/* 482 */           break;
/*     */         case 43:
/* 484 */           jj_consume_token(43);
/* 485 */           break;
/*     */         default:
/* 487 */           this.jj_la1[5] = this.jj_gen;
/* 488 */           jj_consume_token(-1);
/* 489 */           throw new ParseException();
/*     */         }
/* 491 */         ASTConst jjtn004 = new ASTConst(8);
/* 492 */         boolean jjtc004 = true;
/* 493 */         this.jjtree.openNodeScope(jjtn004);
/*     */         try {
/* 495 */           this.jjtree.closeNodeScope(jjtn004, 0);
/* 496 */           jjtc004 = false;
/* 497 */           jjtn004.setValue(this.token_source.literalValue);
/*     */         } finally {
/* 499 */           if (jjtc004) {
/* 500 */             this.jjtree.closeNodeScope(jjtn004, 0);
/*     */           }
/*     */         }
/* 503 */         break;
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/*     */       case 14:
/*     */       case 15:
/*     */       case 16:
/*     */       case 17:
/* 515 */         icsValueRef();
/* 516 */         break;
/*     */       case 21:
/* 518 */         jj_consume_token(21);
/* 519 */         equalityExpression();
/* 520 */         jj_consume_token(22);
/* 521 */         break;
/*     */       case 18:
/*     */       case 19:
/*     */       case 20:
/*     */       case 22:
/*     */       case 23:
/*     */       case 24:
/*     */       case 25:
/*     */       case 26:
/*     */       case 27:
/*     */       case 28:
/*     */       case 29:
/*     */       case 30:
/*     */       case 31:
/*     */       case 32:
/*     */       case 33:
/*     */       case 34:
/*     */       case 35:
/*     */       case 36:
/*     */       case 38:
/*     */       case 39:
/*     */       case 41:
/*     */       case 42:
/*     */       default:
/* 523 */         this.jj_la1[7] = this.jj_gen;
/* 524 */         jj_consume_token(-1);
/* 525 */         throw new ParseException();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void staticMethodCall()
/*     */     throws ParseException
/*     */   {
/* 533 */     ASTStaticMethod jjtn000 = new ASTStaticMethod(9);
/* 534 */     boolean jjtc000 = true;
/* 535 */     this.jjtree.openNodeScope(jjtn000);
/* 536 */     String className = "com.hisun.hiexpression.HiExpBasicFunctions";
/*     */     try {
/* 538 */       Token t = jj_consume_token(29);
/* 539 */       jj_consume_token(21);
/* 540 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
/*     */       {
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/*     */       case 14:
/*     */       case 15:
/*     */       case 16:
/*     */       case 17:
/*     */       case 18:
/*     */       case 19:
/*     */       case 20:
/*     */       case 21:
/*     */       case 29:
/*     */       case 37:
/*     */       case 40:
/*     */       case 43:
/* 560 */         primaryExpression();
/*     */         while (true)
/*     */         {
/* 563 */           switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk)
/*     */           {
/*     */           case 23:
/* 566 */             break;
/*     */           default:
/* 568 */             this.jj_la1[8] = this.jj_gen;
/* 569 */             break;
/*     */           }
/* 571 */           jj_consume_token(23);
/* 572 */           primaryExpression(); } case 22:
/*     */       case 23:
/*     */       case 24:
/*     */       case 25:
/*     */       case 26:
/*     */       case 27:
/*     */       case 28:
/*     */       case 30:
/*     */       case 31:
/*     */       case 32:
/*     */       case 33:
/*     */       case 34:
/*     */       case 35:
/*     */       case 36:
/*     */       case 38:
/*     */       case 39:
/*     */       case 41:
/*     */       case 42: } this.jj_la1[9] = this.jj_gen;
/*     */ 
/* 579 */       jj_consume_token(22);
/* 580 */       this.jjtree.closeNodeScope(jjtn000, true);
/* 581 */       jjtc000 = false;
/* 582 */       jjtn000.init(className, t.image);
/*     */     } catch (Throwable jjte000) {
/* 584 */       if (jjtc000) {
/* 585 */         this.jjtree.clearNodeScope(jjtn000);
/* 586 */         jjtc000 = false;
/*     */       } else {
/* 588 */         this.jjtree.popNode();
/*     */       }
/* 590 */       if (jjte000 instanceof RuntimeException) {
/* 591 */         throw ((RuntimeException)jjte000);
/*     */       }
/*     */ 
/* 596 */       throw ((Error)jjte000);
/*     */     } finally {
/* 598 */       if (jjtc000)
/* 599 */         this.jjtree.closeNodeScope(jjtn000, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   private final boolean jj_2_1(int xla)
/*     */   {
/* 605 */     this.jj_la = xla; this.jj_lastpos = (this.jj_scanpos = this.token);
/*     */     try { int i = (!(jj_3_1())) ? 1 : 0;
/*     */ 
/* 608 */       return i;
/*     */     }
/*     */     catch (LookaheadSuccess ls)
/*     */     {
/* 607 */       int j = 1;
/* 608 */       return j; } finally { jj_save(0, xla); }
/*     */   }
/*     */ 
/*     */   private final boolean jj_3R_4() {
/* 612 */     if (jj_scan_token(29)) return true;
/* 613 */     return (!(jj_scan_token(21)));
/*     */   }
/*     */ 
/*     */   private final boolean jj_3_1()
/*     */   {
/* 618 */     return (!(jj_3R_4()));
/*     */   }
/*     */ 
/*     */   private static void jj_la1_0()
/*     */   {
/* 639 */     jj_la1_0 = new int[] { 6, 6, 120, 120, 262016, 0, 1835008, 2359168, 8388608, 541065088 }; }
/*     */ 
/*     */   private static void jj_la1_1() {
/* 642 */     jj_la1_1 = new int[] { 0, 0, 0, 0, 0, 2336, 0, 2336, 0, 2336 };
/*     */   }
/*     */ 
/*     */   public ICSExpParser(InputStream stream)
/*     */   {
/* 649 */     this(stream, null);
/*     */   }
/*     */ 
/*     */   public ICSExpParser(InputStream stream, String encoding)
/*     */   {
/*  12 */     this.jjtree = new JJTICSExpParserState();
/*     */ 
/* 628 */     this.lookingAhead = false;
/*     */ 
/* 631 */     this.jj_la1 = new int[10];
/*     */ 
/* 644 */     this.jj_2_rtns = new JJCalls[1];
/* 645 */     this.jj_rescan = false;
/* 646 */     this.jj_gc = 0;
/*     */ 
/* 740 */     this.jj_ls = new LookaheadSuccess(null);
/*     */ 
/* 786 */     this.jj_expentries = new Vector();
/*     */ 
/* 788 */     this.jj_kind = -1;
/* 789 */     this.jj_lasttokens = new int[100];
/*     */     try
/*     */     {
/* 652 */       this.jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }
/* 653 */     this.token_source = new ICSExpParserTokenManager(this.jj_input_stream);
/* 654 */     this.token = new Token();
/* 655 */     this.jj_ntk = -1;
/* 656 */     this.jj_gen = 0;
/* 657 */     for (int i = 0; i < 10; ++i) this.jj_la1[i] = -1;
/* 658 */     for (i = 0; i < this.jj_2_rtns.length; ++i) this.jj_2_rtns[i] = new JJCalls();
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream stream) {
/* 662 */     ReInit(stream, null); }
/*     */ 
/*     */   public void ReInit(InputStream stream, String encoding) {
/*     */     try { this.jj_input_stream.ReInit(stream, encoding, 1, 1); } catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }
/* 666 */     this.token_source.ReInit(this.jj_input_stream);
/* 667 */     this.token = new Token();
/* 668 */     this.jj_ntk = -1;
/* 669 */     this.jjtree.reset();
/* 670 */     this.jj_gen = 0;
/* 671 */     for (int i = 0; i < 10; ++i) this.jj_la1[i] = -1;
/* 672 */     for (i = 0; i < this.jj_2_rtns.length; ++i) this.jj_2_rtns[i] = new JJCalls();
/*     */   }
/*     */ 
/*     */   public ICSExpParser(Reader stream)
/*     */   {
/*  12 */     this.jjtree = new JJTICSExpParserState();
/*     */ 
/* 628 */     this.lookingAhead = false;
/*     */ 
/* 631 */     this.jj_la1 = new int[10];
/*     */ 
/* 644 */     this.jj_2_rtns = new JJCalls[1];
/* 645 */     this.jj_rescan = false;
/* 646 */     this.jj_gc = 0;
/*     */ 
/* 740 */     this.jj_ls = new LookaheadSuccess(null);
/*     */ 
/* 786 */     this.jj_expentries = new Vector();
/*     */ 
/* 788 */     this.jj_kind = -1;
/* 789 */     this.jj_lasttokens = new int[100];
/*     */ 
/* 676 */     this.jj_input_stream = new JavaCharStream(stream, 1, 1);
/* 677 */     this.token_source = new ICSExpParserTokenManager(this.jj_input_stream);
/* 678 */     this.token = new Token();
/* 679 */     this.jj_ntk = -1;
/* 680 */     this.jj_gen = 0;
/* 681 */     for (int i = 0; i < 10; ++i) this.jj_la1[i] = -1;
/* 682 */     for (i = 0; i < this.jj_2_rtns.length; ++i) this.jj_2_rtns[i] = new JJCalls();
/*     */   }
/*     */ 
/*     */   public void ReInit(Reader stream) {
/* 686 */     this.jj_input_stream.ReInit(stream, 1, 1);
/* 687 */     this.token_source.ReInit(this.jj_input_stream);
/* 688 */     this.token = new Token();
/* 689 */     this.jj_ntk = -1;
/* 690 */     this.jjtree.reset();
/* 691 */     this.jj_gen = 0;
/* 692 */     for (int i = 0; i < 10; ++i) this.jj_la1[i] = -1;
/* 693 */     for (i = 0; i < this.jj_2_rtns.length; ++i) this.jj_2_rtns[i] = new JJCalls();
/*     */   }
/*     */ 
/*     */   public ICSExpParser(ICSExpParserTokenManager tm)
/*     */   {
/*  12 */     this.jjtree = new JJTICSExpParserState();
/*     */ 
/* 628 */     this.lookingAhead = false;
/*     */ 
/* 631 */     this.jj_la1 = new int[10];
/*     */ 
/* 644 */     this.jj_2_rtns = new JJCalls[1];
/* 645 */     this.jj_rescan = false;
/* 646 */     this.jj_gc = 0;
/*     */ 
/* 740 */     this.jj_ls = new LookaheadSuccess(null);
/*     */ 
/* 786 */     this.jj_expentries = new Vector();
/*     */ 
/* 788 */     this.jj_kind = -1;
/* 789 */     this.jj_lasttokens = new int[100];
/*     */ 
/* 697 */     this.token_source = tm;
/* 698 */     this.token = new Token();
/* 699 */     this.jj_ntk = -1;
/* 700 */     this.jj_gen = 0;
/* 701 */     for (int i = 0; i < 10; ++i) this.jj_la1[i] = -1;
/* 702 */     for (i = 0; i < this.jj_2_rtns.length; ++i) this.jj_2_rtns[i] = new JJCalls();
/*     */   }
/*     */ 
/*     */   public void ReInit(ICSExpParserTokenManager tm) {
/* 706 */     this.token_source = tm;
/* 707 */     this.token = new Token();
/* 708 */     this.jj_ntk = -1;
/* 709 */     this.jjtree.reset();
/* 710 */     this.jj_gen = 0;
/* 711 */     for (int i = 0; i < 10; ++i) this.jj_la1[i] = -1;
/* 712 */     for (i = 0; i < this.jj_2_rtns.length; ++i) this.jj_2_rtns[i] = new JJCalls();
/*     */   }
/*     */ 
/*     */   private final Token jj_consume_token(int kind)
/*     */     throws ParseException
/*     */   {
/*     */     Token oldToken;
/* 717 */     if ((oldToken = this.token).next != null) this.token = this.token.next;
/*     */     else this.token = (this.token.next = this.token_source.getNextToken());
/* 719 */     this.jj_ntk = -1;
/* 720 */     if (this.token.kind == kind) {
/* 721 */       this.jj_gen += 1;
/* 722 */       if (++this.jj_gc > 100) {
/* 723 */         this.jj_gc = 0;
/* 724 */         for (int i = 0; i < this.jj_2_rtns.length; ++i) {
/* 725 */           JJCalls c = this.jj_2_rtns[i];
/* 726 */           while (c != null) {
/* 727 */             if (c.gen < this.jj_gen) c.first = null;
/* 728 */             c = c.next;
/*     */           }
/*     */         }
/*     */       }
/* 732 */       return this.token;
/*     */     }
/* 734 */     this.token = oldToken;
/* 735 */     this.jj_kind = kind;
/* 736 */     throw generateParseException();
/*     */   }
/*     */ 
/*     */   private final boolean jj_scan_token(int kind)
/*     */   {
/* 742 */     if (this.jj_scanpos == this.jj_lastpos) {
/* 743 */       this.jj_la -= 1;
/* 744 */       if (this.jj_scanpos.next == null)
/* 745 */         this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken());
/*     */       else
/* 747 */         this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next);
/*     */     }
/*     */     else {
/* 750 */       this.jj_scanpos = this.jj_scanpos.next;
/*     */     }
/* 752 */     if (this.jj_rescan) {
/* 753 */       int i = 0; Token tok = this.token;
/* 754 */       for (; (tok != null) && (tok != this.jj_scanpos); tok = tok.next) ++i;
/* 755 */       if (tok != null) jj_add_error_token(kind, i);
/*     */     }
/* 757 */     if (this.jj_scanpos.kind != kind) return true;
/* 758 */     if ((this.jj_la == 0) && (this.jj_scanpos == this.jj_lastpos)) throw this.jj_ls;
/* 759 */     return false;
/*     */   }
/*     */ 
/*     */   public final Token getNextToken() {
/* 763 */     if (this.token.next != null) this.token = this.token.next;
/*     */     else this.token = (this.token.next = this.token_source.getNextToken());
/* 765 */     this.jj_ntk = -1;
/* 766 */     this.jj_gen += 1;
/* 767 */     return this.token;
/*     */   }
/*     */ 
/*     */   public final Token getToken(int index) {
/* 771 */     Token t = (this.lookingAhead) ? this.jj_scanpos : this.token;
/* 772 */     for (int i = 0; i < index; ++i) {
/* 773 */       if (t.next != null) t = t.next;
/*     */       else t = t.next = this.token_source.getNextToken();
/*     */     }
/* 776 */     return t;
/*     */   }
/*     */ 
/*     */   private final int jj_ntk() {
/* 780 */     if ((this.jj_nt = this.token.next) == null) {
/* 781 */       return (this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind);
/*     */     }
/* 783 */     return (this.jj_ntk = this.jj_nt.kind);
/*     */   }
/*     */ 
/*     */   private void jj_add_error_token(int kind, int pos)
/*     */   {
/* 793 */     if (pos >= 100) return;
/* 794 */     if (pos == this.jj_endpos + 1) {
/* 795 */       this.jj_lasttokens[(this.jj_endpos++)] = kind;
/* 796 */     } else if (this.jj_endpos != 0) {
/* 797 */       this.jj_expentry = new int[this.jj_endpos];
/* 798 */       for (int i = 0; i < this.jj_endpos; ++i) {
/* 799 */         this.jj_expentry[i] = this.jj_lasttokens[i];
/*     */       }
/* 801 */       boolean exists = false;
/* 802 */       for (Enumeration e = this.jj_expentries.elements(); e.hasMoreElements(); ) {
/* 803 */         int[] oldentry = (int[])(int[])e.nextElement();
/* 804 */         if (oldentry.length == this.jj_expentry.length) {
/* 805 */           exists = true;
/* 806 */           for (int i = 0; i < this.jj_expentry.length; ++i) {
/* 807 */             if (oldentry[i] != this.jj_expentry[i]) {
/* 808 */               exists = false;
/* 809 */               break;
/*     */             }
/*     */           }
/* 812 */           if (exists) break;
/*     */         }
/*     */       }
/* 815 */       if (!(exists)) this.jj_expentries.addElement(this.jj_expentry);
/* 816 */       if (pos == 0) return; this.jj_lasttokens[((this.jj_endpos = pos) - 1)] = kind;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ParseException generateParseException() {
/* 821 */     this.jj_expentries.removeAllElements();
/* 822 */     boolean[] la1tokens = new boolean[50];
/* 823 */     if (this.jj_kind >= 0) {
/* 824 */       la1tokens[this.jj_kind] = true;
/* 825 */       this.jj_kind = -1;
/*     */     }
/* 827 */     for (int i = 0; i < 10; ++i) {
/* 828 */       if (this.jj_la1[i] == this.jj_gen) {
/* 829 */         for (int j = 0; j < 32; ++j) {
/* 830 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/* 831 */             la1tokens[j] = true;
/*     */           }
/* 833 */           if ((jj_la1_1[i] & 1 << j) != 0) {
/* 834 */             la1tokens[(32 + j)] = true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 839 */     for (i = 0; i < 50; ++i) {
/* 840 */       if (la1tokens[i] != 0) {
/* 841 */         this.jj_expentry = new int[1];
/* 842 */         this.jj_expentry[0] = i;
/* 843 */         this.jj_expentries.addElement(this.jj_expentry);
/*     */       }
/*     */     }
/* 846 */     this.jj_endpos = 0;
/* 847 */     jj_rescan_token();
/* 848 */     jj_add_error_token(0, 0);
/* 849 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* 850 */     for (int i = 0; i < this.jj_expentries.size(); ++i) {
/* 851 */       exptokseq[i] = ((int[])(int[])this.jj_expentries.elementAt(i));
/*     */     }
/* 853 */     return new ParseException(this.token, exptokseq, tokenImage);
/*     */   }
/*     */ 
/*     */   public final void enable_tracing() {
/*     */   }
/*     */ 
/*     */   public final void disable_tracing() {
/*     */   }
/*     */ 
/*     */   private final void jj_rescan_token() {
/* 863 */     this.jj_rescan = true;
/* 864 */     for (int i = 0; i < 1; ++i)
/*     */       try {
/* 866 */         JJCalls p = this.jj_2_rtns[i];
/*     */         do {
/* 868 */           if (p.gen > this.jj_gen) {
/* 869 */             this.jj_la = p.arg; this.jj_lastpos = (this.jj_scanpos = p.first);
/* 870 */             switch (i)
/*     */             {
/*     */             case 0:
/* 871 */               jj_3_1();
/*     */             }
/*     */           }
/* 874 */           p = p.next; }
/* 875 */         while (p != null);
/*     */       } catch (LookaheadSuccess ls) {
/*     */       }
/* 878 */     this.jj_rescan = false;
/*     */   }
/*     */ 
/*     */   private final void jj_save(int index, int xla) {
/* 882 */     JJCalls p = this.jj_2_rtns[index];
/* 883 */     while (p.gen > this.jj_gen) {
/* 884 */       if (p.next == null) { p = p.next = new JJCalls(); break; }
/* 885 */       p = p.next;
/*     */     }
/* 887 */     p.gen = (this.jj_gen + xla - this.jj_la); p.first = this.token; p.arg = xla;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 635 */     jj_la1_0();
/* 636 */     jj_la1_1();
/*     */   }
/*     */ 
/*     */   static final class JJCalls
/*     */   {
/*     */     int gen;
/*     */     Token first;
/*     */     int arg;
/*     */     JJCalls next;
/*     */   }
/*     */ 
/*     */   private static final class LookaheadSuccess extends Error
/*     */   {
/*     */     private LookaheadSuccess()
/*     */     {
/*     */     }
/*     */ 
/*     */     LookaheadSuccess(ICSExpParser.1 x0)
/*     */     {
/*     */     }
/*     */   }
/*     */ }