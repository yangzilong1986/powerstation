/*      */ package com.hisun.hiexpression.imp;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ 
/*      */ public class ICSExpParserTokenManager
/*      */   implements ICSExpParserConstants
/*      */ {
/*      */   Object literalValue;
/*      */   private char charValue;
/*      */   private char charLiteralStartQuote;
/*      */   private StringBuffer stringBuffer;
/*      */   public PrintStream debugStream;
/*  384 */   static final long[] jjbitVec0 = { 2301339413881290750L, -16384L, 4294967295L, 432345564227567616L };
/*      */ 
/*  387 */   static final long[] jjbitVec2 = { 0L, 0L, 0L, -36028797027352577L };
/*      */ 
/*  390 */   static final long[] jjbitVec3 = { 0L, -1L, -1L, -1L };
/*      */ 
/*  393 */   static final long[] jjbitVec4 = { -1L, -1L, 65535L, 0L };
/*      */ 
/*  396 */   static final long[] jjbitVec5 = { -1L, -1L, 0L, 0L };
/*      */ 
/*  399 */   static final long[] jjbitVec6 = { 70368744177663L, 0L, 0L, 0L };
/*      */ 
/*  711 */   static final long[] jjbitVec7 = { -2L, -1L, -1L, -1L };
/*      */ 
/*  714 */   static final long[] jjbitVec8 = { 0L, 0L, -1L, -1L };
/*      */ 
/* 1095 */   static final int[] jjnextStates = { 15, 16, 18, 19, 22, 13, 24, 25, 7, 9, 10, 13, 17, 10, 13, 11, 12, 20, 21, 1, 2, 3 };
/*      */ 
/* 1131 */   public static final String[] jjstrLiteralImages = { "", "==", "!=", "<", ">", "<=", ">=", "@BAS.", "@BCFG.", "@MSG.", "@ETF.", "@PARA.", "@SYS.", "$", "~", "%", "#", "@", "true", "false", "null", "(", ")", ",", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
/*      */ 
/* 1138 */   public static final String[] lexStateNames = { "DEFAULT", "WithinCharLiteral", "WithinBackCharLiteral", "WithinStringLiteral" };
/*      */ 
/* 1144 */   public static final int[] jjnewLexState = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2, 1, 3, -1, -1, 0, -1, -1, 0, -1, -1, 0, -1, -1, -1, -1, -1, -1 };
/*      */ 
/* 1148 */   static final long[] jjtoToken = { 62810155384831L };
/*      */ 
/* 1151 */   static final long[] jjtoSkip = { 520093696L };
/*      */ 
/* 1154 */   static final long[] jjtoMore = { 7554847473664L };
/*      */   protected JavaCharStream input_stream;
/*      */   private final int[] jjrounds;
/*      */   private final int[] jjstateSet;
/*      */   StringBuffer image;
/*      */   int jjimageLen;
/*      */   int lengthOfMatch;
/*      */   protected char curChar;
/*      */   int curLexState;
/*      */   int defaultLexState;
/*      */   int jjnewStateCnt;
/*      */   int jjround;
/*      */   int jjmatchedPos;
/*      */   int jjmatchedKind;
/*      */ 
/*      */   private char escapeChar()
/*      */   {
/*   20 */     int ofs = this.image.length() - 1;
/*   21 */     switch (this.image.charAt(ofs))
/*      */     {
/*      */     case 'n':
/*   22 */       return '\n';
/*      */     case 'r':
/*   23 */       return '\r';
/*      */     case 't':
/*   24 */       return '\t';
/*      */     case 'b':
/*   25 */       return '\b';
/*      */     case 'f':
/*   26 */       return '\f';
/*      */     case '\\':
/*   27 */       return '\\';
/*      */     case '\'':
/*   28 */       return '\'';
/*      */     case '"':
/*   29 */       return '"';
/*      */     }
/*      */ 
/*   33 */     while (this.image.charAt(--ofs) != '\\');
/*   35 */     int value = 0;
/*   36 */     while (++ofs < this.image.length())
/*   37 */       value = value << 3 | this.image.charAt(ofs) - '0';
/*   38 */     return (char)value;
/*      */   }
/*      */ 
/*      */   private Object makeInt()
/*      */   {
/*      */     Object result;
/*   44 */     String s = this.image.toString();
/*   45 */     int base = 10;
/*      */ 
/*   47 */     if (s.charAt(0) == '0')
/*   48 */       base = ((s.length() > 1) && (((s.charAt(1) == 'x') || (s.charAt(1) == 'X')))) ? 16 : 8;
/*   49 */     if (base == 16)
/*   50 */       s = s.substring(2);
/*   51 */     switch (s.charAt(s.length() - 1))
/*      */     {
/*      */     case 'L':
/*      */     case 'l':
/*   53 */       result = Long.valueOf(s.substring(0, s.length() - 1), base);
/*   54 */       break;
/*      */     case 'H':
/*      */     case 'h':
/*   57 */       result = new BigInteger(s.substring(0, s.length() - 1), base);
/*   58 */       break;
/*      */     default:
/*   61 */       result = Integer.valueOf(s, base);
/*      */     }
/*      */ 
/*   64 */     return result;
/*      */   }
/*      */ 
/*      */   private Object makeFloat()
/*      */   {
/*   69 */     String s = this.image.toString();
/*   70 */     switch (s.charAt(s.length() - 1)) {
/*      */     case 'F':
/*      */     case 'f':
/*   72 */       return Float.valueOf(s);
/*      */     case 'B':
/*      */     case 'b':
/*   75 */       return new BigDecimal(s.substring(0, s.length() - 1));
/*      */     case 'D':
/*      */     case 'd': }
/*   79 */     return Double.valueOf(s);
/*      */   }
/*      */ 
/*      */   public void setDebugStream(PrintStream ds) {
/*   83 */     this.debugStream = ds; }
/*      */ 
/*      */   private final int jjStopStringLiteralDfa_0(int pos, long active0) {
/*   86 */     switch (pos)
/*      */     {
/*      */     case 0:
/*   89 */       if ((active0 & 0x1C0000) != 0L)
/*      */       {
/*   91 */         this.jjmatchedKind = 29;
/*   92 */         return 27;
/*      */       }
/*   94 */       return -1;
/*      */     case 1:
/*   96 */       if ((active0 & 0x1C0000) != 0L)
/*      */       {
/*   98 */         this.jjmatchedKind = 29;
/*   99 */         this.jjmatchedPos = 1;
/*  100 */         return 27;
/*      */       }
/*  102 */       return -1;
/*      */     case 2:
/*  104 */       if ((active0 & 0x1C0000) != 0L)
/*      */       {
/*  106 */         this.jjmatchedKind = 29;
/*  107 */         this.jjmatchedPos = 2;
/*  108 */         return 27;
/*      */       }
/*  110 */       return -1;
/*      */     case 3:
/*  112 */       if ((active0 & 0x80000) != 0L)
/*      */       {
/*  114 */         this.jjmatchedKind = 29;
/*  115 */         this.jjmatchedPos = 3;
/*  116 */         return 27;
/*      */       }
/*  118 */       if ((active0 & 0x140000) != 0L)
/*  119 */         return 27;
/*  120 */       return -1;
/*      */     case 4:
/*  122 */       if ((active0 & 0x80000) != 0L)
/*  123 */         return 27;
/*  124 */       return -1;
/*      */     }
/*  126 */     return -1;
/*      */   }
/*      */ 
/*      */   private final int jjStartNfa_0(int pos, long active0)
/*      */   {
/*  131 */     return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
/*      */   }
/*      */ 
/*      */   private final int jjStopAtPos(int pos, int kind) {
/*  135 */     this.jjmatchedKind = kind;
/*  136 */     this.jjmatchedPos = pos;
/*  137 */     return (pos + 1);
/*      */   }
/*      */ 
/*      */   private final int jjStartNfaWithStates_0(int pos, int kind, int state) {
/*  141 */     this.jjmatchedKind = kind;
/*  142 */     this.jjmatchedPos = pos;
/*      */     try { this.curChar = this.input_stream.readChar(); } catch (IOException e) {
/*  144 */       return (pos + 1); }
/*  145 */     return jjMoveNfa_0(state, pos + 1);
/*      */   }
/*      */ 
/*      */   private final int jjMoveStringLiteralDfa0_0() {
/*  149 */     switch (this.curChar)
/*      */     {
/*      */     case '!':
/*  152 */       return jjMoveStringLiteralDfa1_0(4L);
/*      */     case '"':
/*  154 */       return jjStopAtPos(0, 34);
/*      */     case '#':
/*  156 */       return jjStopAtPos(0, 16);
/*      */     case '$':
/*  158 */       return jjStopAtPos(0, 13);
/*      */     case '%':
/*  160 */       return jjStopAtPos(0, 15);
/*      */     case '\'':
/*  162 */       return jjStopAtPos(0, 33);
/*      */     case '(':
/*  164 */       return jjStopAtPos(0, 21);
/*      */     case ')':
/*  166 */       return jjStopAtPos(0, 22);
/*      */     case ',':
/*  168 */       return jjStopAtPos(0, 23);
/*      */     case '<':
/*  170 */       this.jjmatchedKind = 3;
/*  171 */       return jjMoveStringLiteralDfa1_0(32L);
/*      */     case '=':
/*  173 */       return jjMoveStringLiteralDfa1_0(2L);
/*      */     case '>':
/*  175 */       this.jjmatchedKind = 4;
/*  176 */       return jjMoveStringLiteralDfa1_0(64L);
/*      */     case '@':
/*  178 */       this.jjmatchedKind = 17;
/*  179 */       return jjMoveStringLiteralDfa1_0(8064L);
/*      */     case '`':
/*  181 */       return jjStopAtPos(0, 32);
/*      */     case 'f':
/*  183 */       return jjMoveStringLiteralDfa1_0(524288L);
/*      */     case 'n':
/*  185 */       return jjMoveStringLiteralDfa1_0(1048576L);
/*      */     case 't':
/*  187 */       return jjMoveStringLiteralDfa1_0(262144L);
/*      */     case '~':
/*  189 */       return jjStopAtPos(0, 14);
/*      */     }
/*  191 */     return jjMoveNfa_0(0, 0);
/*      */   }
/*      */ 
/*      */   private final int jjMoveStringLiteralDfa1_0(long active0) {
/*      */     try {
/*  196 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  198 */       jjStopStringLiteralDfa_0(0, active0);
/*  199 */       return 1;
/*      */     }
/*  201 */     switch (this.curChar)
/*      */     {
/*      */     case '=':
/*  204 */       if ((active0 & 0x2) != 0L)
/*  205 */         return jjStopAtPos(1, 1);
/*  206 */       if ((active0 & 0x4) != 0L)
/*  207 */         return jjStopAtPos(1, 2);
/*  208 */       if ((active0 & 0x20) != 0L)
/*  209 */         return jjStopAtPos(1, 5);
/*  210 */       if ((active0 & 0x40) == 0L) break label253;
/*  211 */       return jjStopAtPos(1, 6);
/*      */     case 'B':
/*  214 */       return jjMoveStringLiteralDfa2_0(active0, 384L);
/*      */     case 'E':
/*  216 */       return jjMoveStringLiteralDfa2_0(active0, 1024L);
/*      */     case 'M':
/*  218 */       return jjMoveStringLiteralDfa2_0(active0, 512L);
/*      */     case 'P':
/*  220 */       return jjMoveStringLiteralDfa2_0(active0, 2048L);
/*      */     case 'S':
/*  222 */       return jjMoveStringLiteralDfa2_0(active0, 4096L);
/*      */     case 'a':
/*  224 */       return jjMoveStringLiteralDfa2_0(active0, 524288L);
/*      */     case 'r':
/*  226 */       return jjMoveStringLiteralDfa2_0(active0, 262144L);
/*      */     case 'u':
/*  228 */       return jjMoveStringLiteralDfa2_0(active0, 1048576L);
/*      */     }
/*      */ 
/*  232 */     label253: return jjStartNfa_0(0, active0);
/*      */   }
/*      */ 
/*      */   private final int jjMoveStringLiteralDfa2_0(long old0, long active0) {
/*  236 */     if ((active0 &= old0) == 0L)
/*  237 */       return jjStartNfa_0(0, old0); try {
/*  238 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  240 */       jjStopStringLiteralDfa_0(1, active0);
/*  241 */       return 2;
/*      */     }
/*  243 */     switch (this.curChar)
/*      */     {
/*      */     case 'A':
/*  246 */       return jjMoveStringLiteralDfa3_0(active0, 2176L);
/*      */     case 'C':
/*  248 */       return jjMoveStringLiteralDfa3_0(active0, 256L);
/*      */     case 'S':
/*  250 */       return jjMoveStringLiteralDfa3_0(active0, 512L);
/*      */     case 'T':
/*  252 */       return jjMoveStringLiteralDfa3_0(active0, 1024L);
/*      */     case 'Y':
/*  254 */       return jjMoveStringLiteralDfa3_0(active0, 4096L);
/*      */     case 'l':
/*  256 */       return jjMoveStringLiteralDfa3_0(active0, 1572864L);
/*      */     case 'u':
/*  258 */       return jjMoveStringLiteralDfa3_0(active0, 262144L);
/*      */     }
/*      */ 
/*  262 */     return jjStartNfa_0(1, active0);
/*      */   }
/*      */ 
/*      */   private final int jjMoveStringLiteralDfa3_0(long old0, long active0) {
/*  266 */     if ((active0 &= old0) == 0L)
/*  267 */       return jjStartNfa_0(1, old0); try {
/*  268 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  270 */       jjStopStringLiteralDfa_0(2, active0);
/*  271 */       return 3;
/*      */     }
/*  273 */     switch (this.curChar)
/*      */     {
/*      */     case 'F':
/*  276 */       return jjMoveStringLiteralDfa4_0(active0, 1280L);
/*      */     case 'G':
/*  278 */       return jjMoveStringLiteralDfa4_0(active0, 512L);
/*      */     case 'R':
/*  280 */       return jjMoveStringLiteralDfa4_0(active0, 2048L);
/*      */     case 'S':
/*  282 */       return jjMoveStringLiteralDfa4_0(active0, 4224L);
/*      */     case 'e':
/*  284 */       if ((active0 & 0x40000) == 0L) break label197;
/*  285 */       return jjStartNfaWithStates_0(3, 18, 27);
/*      */     case 'l':
/*  288 */       if ((active0 & 0x100000) == 0L) break label197;
/*  289 */       return jjStartNfaWithStates_0(3, 20, 27);
/*      */     case 's':
/*  292 */       return jjMoveStringLiteralDfa4_0(active0, 524288L);
/*      */     }
/*      */ 
/*  296 */     label197: return jjStartNfa_0(2, active0);
/*      */   }
/*      */ 
/*      */   private final int jjMoveStringLiteralDfa4_0(long old0, long active0) {
/*  300 */     if ((active0 &= old0) == 0L)
/*  301 */       return jjStartNfa_0(2, old0); try {
/*  302 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  304 */       jjStopStringLiteralDfa_0(3, active0);
/*  305 */       return 4;
/*      */     }
/*  307 */     switch (this.curChar)
/*      */     {
/*      */     case '.':
/*  310 */       if ((active0 & 0x80) != 0L)
/*  311 */         return jjStopAtPos(4, 7);
/*  312 */       if ((active0 & 0x200) != 0L)
/*  313 */         return jjStopAtPos(4, 9);
/*  314 */       if ((active0 & 0x400) != 0L)
/*  315 */         return jjStopAtPos(4, 10);
/*  316 */       if ((active0 & 0x1000) == 0L) break label198;
/*  317 */       return jjStopAtPos(4, 12);
/*      */     case 'A':
/*  320 */       return jjMoveStringLiteralDfa5_0(active0, 2048L);
/*      */     case 'G':
/*  322 */       return jjMoveStringLiteralDfa5_0(active0, 256L);
/*      */     case 'e':
/*  324 */       if ((active0 & 0x80000) == 0L) break label198;
/*  325 */       return jjStartNfaWithStates_0(4, 19, 27);
/*      */     }
/*      */ 
/*  330 */     label198: return jjStartNfa_0(3, active0);
/*      */   }
/*      */ 
/*      */   private final int jjMoveStringLiteralDfa5_0(long old0, long active0) {
/*  334 */     if ((active0 &= old0) == 0L)
/*  335 */       return jjStartNfa_0(3, old0); try {
/*  336 */       this.curChar = this.input_stream.readChar();
/*      */     } catch (IOException e) {
/*  338 */       jjStopStringLiteralDfa_0(4, active0);
/*  339 */       return 5;
/*      */     }
/*  341 */     switch (this.curChar)
/*      */     {
/*      */     case '.':
/*  344 */       if ((active0 & 0x100) != 0L)
/*  345 */         return jjStopAtPos(5, 8);
/*  346 */       if ((active0 & 0x800) == 0L) break label100;
/*  347 */       return jjStopAtPos(5, 11);
/*      */     }
/*      */ 
/*  352 */     label100: return jjStartNfa_0(4, active0);
/*      */   }
/*      */ 
/*      */   private final void jjCheckNAdd(int state) {
/*  356 */     if (this.jjrounds[state] == this.jjround)
/*      */       return;
/*  358 */     this.jjstateSet[(this.jjnewStateCnt++)] = state;
/*  359 */     this.jjrounds[state] = this.jjround;
/*      */   }
/*      */ 
/*      */   private final void jjAddStates(int start, int end)
/*      */   {
/*      */     do
/*  365 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/*  366 */     while (start++ != end);
/*      */   }
/*      */ 
/*      */   private final void jjCheckNAddTwoStates(int state1, int state2) {
/*  370 */     jjCheckNAdd(state1);
/*  371 */     jjCheckNAdd(state2);
/*      */   }
/*      */ 
/*      */   private final void jjCheckNAddStates(int start, int end) {
/*      */     do
/*  376 */       jjCheckNAdd(jjnextStates[start]);
/*  377 */     while (start++ != end);
/*      */   }
/*      */ 
/*      */   private final void jjCheckNAddStates(int start) {
/*  381 */     jjCheckNAdd(jjnextStates[start]);
/*  382 */     jjCheckNAdd(jjnextStates[(start + 1)]);
/*      */   }
/*      */ 
/*      */   private final int jjMoveNfa_0(int startState, int curPos)
/*      */   {
/*  405 */     int startsAt = 0;
/*  406 */     this.jjnewStateCnt = 27;
/*  407 */     int i = 1;
/*  408 */     this.jjstateSet[0] = startState;
/*  409 */     int kind = 2147483647;
/*      */     while (true)
/*      */     {
/*      */       long l;
/*  412 */       if (++this.jjround == 2147483647)
/*  413 */         ReInitRounds();
/*  414 */       if (this.curChar < '@')
/*      */       {
/*  416 */         l = 1L << this.curChar;
/*      */         do
/*      */         {
/*  419 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  422 */             if ((0x0 & l) != 0L)
/*  423 */               jjCheckNAddStates(0, 5);
/*  424 */             else if (this.curChar == '.')
/*  425 */               jjCheckNAdd(9);
/*  426 */             if ((0x0 & l) != 0L)
/*      */             {
/*  428 */               if (kind > 44)
/*  429 */                 kind = 44;
/*  430 */               jjCheckNAddTwoStates(6, 7);
/*      */             } else {
/*  432 */               if (this.curChar != '0')
/*      */                 continue;
/*  434 */               if (kind > 44)
/*  435 */                 kind = 44;
/*  436 */               jjCheckNAddStates(6, 8); }
/*  437 */             break;
/*      */           case 27:
/*  440 */             if ((0x0 & l) != 0L)
/*      */             {
/*  442 */               if (kind > 29)
/*  443 */                 kind = 29;
/*  444 */               jjCheckNAddTwoStates(1, 2);
/*      */             }
/*  446 */             else if (this.curChar == '.') {
/*  447 */               this.jjstateSet[(this.jjnewStateCnt++)] = 3; } break;
/*      */           case 1:
/*  450 */             if ((0x0 & l) == 0L)
/*      */               continue;
/*  452 */             if (kind > 29)
/*  453 */               kind = 29;
/*  454 */             jjCheckNAddTwoStates(1, 2);
/*  455 */             break;
/*      */           case 2:
/*  457 */             if (this.curChar == '.')
/*  458 */               this.jjstateSet[(this.jjnewStateCnt++)] = 3; break;
/*      */           case 4:
/*  461 */             if ((0x0 & l) == 0L)
/*      */               continue;
/*  463 */             if (kind > 29)
/*  464 */               kind = 29;
/*  465 */             jjCheckNAddTwoStates(2, 4);
/*  466 */             break;
/*      */           case 5:
/*  468 */             if ((0x0 & l) == 0L)
/*      */               continue;
/*  470 */             if (kind > 44)
/*  471 */               kind = 44;
/*  472 */             jjCheckNAddTwoStates(6, 7);
/*  473 */             break;
/*      */           case 6:
/*  475 */             if ((0x0 & l) == 0L)
/*      */               continue;
/*  477 */             if (kind > 44)
/*  478 */               kind = 44;
/*  479 */             jjCheckNAddTwoStates(6, 7);
/*  480 */             break;
/*      */           case 8:
/*  482 */             if (this.curChar == '.')
/*  483 */               jjCheckNAdd(9); break;
/*      */           case 9:
/*  486 */             if ((0x0 & l) == 0L)
/*      */               continue;
/*  488 */             if (kind > 45)
/*  489 */               kind = 45;
/*  490 */             jjCheckNAddStates(9, 11);
/*  491 */             break;
/*      */           case 11:
/*  493 */             if ((0x0 & l) != 0L)
/*  494 */               jjCheckNAdd(12); break;
/*      */           case 12:
/*  497 */             if ((0x0 & l) == 0L)
/*      */               continue;
/*  499 */             if (kind > 45)
/*  500 */               kind = 45;
/*  501 */             jjCheckNAddTwoStates(12, 13);
/*  502 */             break;
/*      */           case 14:
/*  504 */             if ((0x0 & l) != 0L)
/*  505 */               jjCheckNAddStates(0, 5); break;
/*      */           case 15:
/*  508 */             if ((0x0 & l) != 0L)
/*  509 */               jjCheckNAddTwoStates(15, 16); break;
/*      */           case 16:
/*  512 */             if (this.curChar != '.')
/*      */               continue;
/*  514 */             if (kind > 45)
/*  515 */               kind = 45;
/*  516 */             jjCheckNAddStates(12, 14);
/*  517 */             break;
/*      */           case 17:
/*  519 */             if ((0x0 & l) == 0L)
/*      */               continue;
/*  521 */             if (kind > 45)
/*  522 */               kind = 45;
/*  523 */             jjCheckNAddStates(12, 14);
/*  524 */             break;
/*      */           case 18:
/*  526 */             if ((0x0 & l) != 0L)
/*  527 */               jjCheckNAddTwoStates(18, 19); break;
/*      */           case 20:
/*  530 */             if ((0x0 & l) != 0L)
/*  531 */               jjCheckNAdd(21); break;
/*      */           case 21:
/*  534 */             if ((0x0 & l) == 0L)
/*      */               continue;
/*  536 */             if (kind > 45)
/*  537 */               kind = 45;
/*  538 */             jjCheckNAddTwoStates(21, 13);
/*  539 */             break;
/*      */           case 22:
/*  541 */             if ((0x0 & l) != 0L)
/*  542 */               jjCheckNAddTwoStates(22, 13); break;
/*      */           case 23:
/*  545 */             if (this.curChar != '0')
/*      */               continue;
/*  547 */             if (kind > 44)
/*  548 */               kind = 44;
/*  549 */             jjCheckNAddStates(6, 8);
/*  550 */             break;
/*      */           case 24:
/*  552 */             if ((0x0 & l) == 0L)
/*      */               continue;
/*  554 */             if (kind > 44)
/*  555 */               kind = 44;
/*  556 */             jjCheckNAddTwoStates(24, 7);
/*  557 */             break;
/*      */           case 26:
/*  559 */             if ((0x0 & l) == 0L)
/*      */               continue;
/*  561 */             if (kind > 44)
/*  562 */               kind = 44; jjCheckNAddTwoStates(26, 7);
/*      */           case 3:
/*      */           case 7:
/*      */           case 10:
/*      */           case 13:
/*      */           case 19:
/*      */           case 25: }  } while (i != startsAt);
/*      */       }
/*  569 */       else if (this.curChar < 128)
/*      */       {
/*  571 */         l = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/*  574 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  577 */             if ((0x87FFFFFE & l) == 0L)
/*      */               continue;
/*  579 */             if (kind > 29)
/*  580 */               kind = 29;
/*  581 */             jjCheckNAddTwoStates(1, 2);
/*  582 */             break;
/*      */           case 1:
/*      */           case 27:
/*  585 */             if ((0x87FFFFFE & l) == 0L)
/*      */               continue;
/*  587 */             if (kind > 29)
/*  588 */               kind = 29;
/*  589 */             jjCheckNAddTwoStates(1, 2);
/*  590 */             break;
/*      */           case 3:
/*      */           case 4:
/*  593 */             if ((0x87FFFFFE & l) == 0L)
/*      */               continue;
/*  595 */             if (kind > 29)
/*  596 */               kind = 29;
/*  597 */             jjCheckNAddTwoStates(2, 4);
/*  598 */             break;
/*      */           case 7:
/*  600 */             if (((0x1100 & l) != 0L) && (kind > 44))
/*  601 */               kind = 44; break;
/*      */           case 10:
/*  604 */             if ((0x20 & l) != 0L)
/*  605 */               jjAddStates(15, 16); break;
/*      */           case 13:
/*  608 */             if (((0x54 & l) != 0L) && (kind > 45))
/*  609 */               kind = 45; break;
/*      */           case 19:
/*  612 */             if ((0x20 & l) != 0L)
/*  613 */               jjAddStates(17, 18); break;
/*      */           case 25:
/*  616 */             if ((0x1000000 & l) != 0L)
/*  617 */               jjCheckNAdd(26); break;
/*      */           case 26:
/*  620 */             if ((0x7E & l) == 0L)
/*      */               continue;
/*  622 */             if (kind > 44)
/*  623 */               kind = 44; jjCheckNAddTwoStates(26, 7);
/*      */           case 2:
/*      */           case 5:
/*      */           case 6:
/*      */           case 8:
/*      */           case 9:
/*      */           case 11:
/*      */           case 12:
/*      */           case 14:
/*      */           case 15:
/*      */           case 16:
/*      */           case 17:
/*      */           case 18:
/*      */           case 20:
/*      */           case 21:
/*      */           case 22:
/*      */           case 23:
/*      */           case 24: }  } while (i != startsAt);
/*      */       }
/*      */       else
/*      */       {
/*  632 */         int hiByte = this.curChar >> '\b';
/*  633 */         int i1 = hiByte >> 6;
/*  634 */         long l1 = 1L << (hiByte & 0x3F);
/*  635 */         int i2 = (this.curChar & 0xFF) >> '\6';
/*  636 */         long l2 = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/*  639 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  642 */             if (!(jjCanMove_0(hiByte, i1, i2, l1, l2)))
/*      */               continue;
/*  644 */             if (kind > 29)
/*  645 */               kind = 29;
/*  646 */             jjCheckNAddTwoStates(1, 2);
/*  647 */             break;
/*      */           case 1:
/*      */           case 27:
/*  650 */             if (!(jjCanMove_0(hiByte, i1, i2, l1, l2)))
/*      */               continue;
/*  652 */             if (kind > 29)
/*  653 */               kind = 29;
/*  654 */             jjCheckNAddTwoStates(1, 2);
/*  655 */             break;
/*      */           case 3:
/*      */           case 4:
/*  658 */             if (!(jjCanMove_0(hiByte, i1, i2, l1, l2)))
/*      */               continue;
/*  660 */             if (kind > 29)
/*  661 */               kind = 29;
/*  662 */             jjCheckNAddTwoStates(2, 4);
/*      */           }
/*      */         }
/*      */ 
/*  666 */         while (i != startsAt);
/*      */       }
/*  668 */       if (kind != 2147483647)
/*      */       {
/*  670 */         this.jjmatchedKind = kind;
/*  671 */         this.jjmatchedPos = curPos;
/*  672 */         kind = 2147483647;
/*      */       }
/*  674 */       ++curPos;
/*  675 */       i = this.jjnewStateCnt; if (i == (startsAt = 27 - (this.jjnewStateCnt = startsAt)))
/*  676 */         return curPos; try {
/*  677 */         this.curChar = this.input_stream.readChar(); } catch (IOException e) { } }
/*  678 */     return curPos;
/*      */   }
/*      */ 
/*      */   private final int jjStopStringLiteralDfa_2(int pos, long active0)
/*      */   {
/*  683 */     switch (pos)
/*      */     {
/*      */     }
/*  686 */     return -1;
/*      */   }
/*      */ 
/*      */   private final int jjStartNfa_2(int pos, long active0)
/*      */   {
/*  691 */     return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
/*      */   }
/*      */ 
/*      */   private final int jjStartNfaWithStates_2(int pos, int kind, int state) {
/*  695 */     this.jjmatchedKind = kind;
/*  696 */     this.jjmatchedPos = pos;
/*      */     try { this.curChar = this.input_stream.readChar(); } catch (IOException e) {
/*  698 */       return (pos + 1); }
/*  699 */     return jjMoveNfa_2(state, pos + 1);
/*      */   }
/*      */ 
/*      */   private final int jjMoveStringLiteralDfa0_2() {
/*  703 */     switch (this.curChar)
/*      */     {
/*      */     case '`':
/*  706 */       return jjStopAtPos(0, 40);
/*      */     }
/*  708 */     return jjMoveNfa_2(0, 0);
/*      */   }
/*      */ 
/*      */   private final int jjMoveNfa_2(int startState, int curPos)
/*      */   {
/*  720 */     int startsAt = 0;
/*  721 */     this.jjnewStateCnt = 6;
/*  722 */     int i = 1;
/*  723 */     this.jjstateSet[0] = startState;
/*  724 */     int kind = 2147483647;
/*      */     while (true)
/*      */     {
/*      */       long l;
/*  727 */       if (++this.jjround == 2147483647)
/*  728 */         ReInitRounds();
/*  729 */       if (this.curChar < '@')
/*      */       {
/*  731 */         l = 1L << this.curChar;
/*      */         do
/*      */         {
/*  734 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  737 */             if (kind > 39)
/*  738 */               kind = 39; break;
/*      */           case 1:
/*  741 */             if (((0x0 & l) != 0L) && (kind > 38))
/*  742 */               kind = 38; break;
/*      */           case 2:
/*  745 */             if ((0x0 & l) != 0L)
/*  746 */               this.jjstateSet[(this.jjnewStateCnt++)] = 3; break;
/*      */           case 3:
/*  749 */             if ((0x0 & l) == 0L)
/*      */               continue;
/*  751 */             if (kind > 38)
/*  752 */               kind = 38;
/*  753 */             this.jjstateSet[(this.jjnewStateCnt++)] = 4;
/*  754 */             break;
/*      */           case 4:
/*  756 */             if (((0x0 & l) != 0L) && (kind > 38)) {
/*  757 */               kind = 38;
/*      */             }
/*      */           }
/*      */         }
/*  761 */         while (i != startsAt);
/*      */       }
/*  763 */       else if (this.curChar < 128)
/*      */       {
/*  765 */         l = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/*  768 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  771 */             if ((0xEFFFFFFF & l) != 0L)
/*      */             {
/*  773 */               if (kind > 39)
/*  774 */                 kind = 39;
/*      */             }
/*  776 */             else if (this.curChar == '\\')
/*  777 */               jjAddStates(19, 21); break;
/*      */           case 1:
/*  780 */             if (((0x10000000 & l) != 0L) && (kind > 38))
/*  781 */               kind = 38; break;
/*      */           case 5:
/*  784 */             if (((0xEFFFFFFF & l) != 0L) && (kind > 39)) {
/*  785 */               kind = 39;
/*      */             }
/*      */           }
/*      */         }
/*  789 */         while (i != startsAt);
/*      */       }
/*      */       else
/*      */       {
/*  793 */         int hiByte = this.curChar >> '\b';
/*  794 */         int i1 = hiByte >> 6;
/*  795 */         long l1 = 1L << (hiByte & 0x3F);
/*  796 */         int i2 = (this.curChar & 0xFF) >> '\6';
/*  797 */         long l2 = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/*  800 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  803 */             if ((jjCanMove_1(hiByte, i1, i2, l1, l2)) && (kind > 39)) {
/*  804 */               kind = 39;
/*      */             }
/*      */           }
/*      */         }
/*  808 */         while (i != startsAt);
/*      */       }
/*  810 */       if (kind != 2147483647)
/*      */       {
/*  812 */         this.jjmatchedKind = kind;
/*  813 */         this.jjmatchedPos = curPos;
/*  814 */         kind = 2147483647;
/*      */       }
/*  816 */       ++curPos;
/*  817 */       i = this.jjnewStateCnt; if (i == (startsAt = 6 - (this.jjnewStateCnt = startsAt)))
/*  818 */         return curPos; try {
/*  819 */         this.curChar = this.input_stream.readChar(); } catch (IOException e) { } }
/*  820 */     return curPos;
/*      */   }
/*      */ 
/*      */   private final int jjStopStringLiteralDfa_1(int pos, long active0)
/*      */   {
/*  825 */     switch (pos)
/*      */     {
/*      */     }
/*  828 */     return -1;
/*      */   }
/*      */ 
/*      */   private final int jjStartNfa_1(int pos, long active0)
/*      */   {
/*  833 */     return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
/*      */   }
/*      */ 
/*      */   private final int jjStartNfaWithStates_1(int pos, int kind, int state) {
/*  837 */     this.jjmatchedKind = kind;
/*  838 */     this.jjmatchedPos = pos;
/*      */     try { this.curChar = this.input_stream.readChar(); } catch (IOException e) {
/*  840 */       return (pos + 1); }
/*  841 */     return jjMoveNfa_1(state, pos + 1);
/*      */   }
/*      */ 
/*      */   private final int jjMoveStringLiteralDfa0_1() {
/*  845 */     switch (this.curChar)
/*      */     {
/*      */     case '\'':
/*  848 */       return jjStopAtPos(0, 37);
/*      */     }
/*  850 */     return jjMoveNfa_1(0, 0);
/*      */   }
/*      */ 
/*      */   private final int jjMoveNfa_1(int startState, int curPos)
/*      */   {
/*  856 */     int startsAt = 0;
/*  857 */     this.jjnewStateCnt = 6;
/*  858 */     int i = 1;
/*  859 */     this.jjstateSet[0] = startState;
/*  860 */     int kind = 2147483647;
/*      */     while (true)
/*      */     {
/*      */       long l;
/*  863 */       if (++this.jjround == 2147483647)
/*  864 */         ReInitRounds();
/*  865 */       if (this.curChar < '@')
/*      */       {
/*  867 */         l = 1L << this.curChar;
/*      */         do
/*      */         {
/*  870 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  873 */             if (((0xFFFFFFFF & l) != 0L) && (kind > 36))
/*  874 */               kind = 36; break;
/*      */           case 1:
/*  877 */             if (((0x0 & l) != 0L) && (kind > 35))
/*  878 */               kind = 35; break;
/*      */           case 2:
/*  881 */             if ((0x0 & l) != 0L)
/*  882 */               this.jjstateSet[(this.jjnewStateCnt++)] = 3; break;
/*      */           case 3:
/*  885 */             if ((0x0 & l) == 0L)
/*      */               continue;
/*  887 */             if (kind > 35)
/*  888 */               kind = 35;
/*  889 */             this.jjstateSet[(this.jjnewStateCnt++)] = 4;
/*  890 */             break;
/*      */           case 4:
/*  892 */             if (((0x0 & l) != 0L) && (kind > 35)) {
/*  893 */               kind = 35;
/*      */             }
/*      */           }
/*      */         }
/*  897 */         while (i != startsAt);
/*      */       }
/*  899 */       else if (this.curChar < 128)
/*      */       {
/*  901 */         l = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/*  904 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  907 */             if ((0xEFFFFFFF & l) != 0L)
/*      */             {
/*  909 */               if (kind > 36)
/*  910 */                 kind = 36;
/*      */             }
/*  912 */             else if (this.curChar == '\\')
/*  913 */               jjAddStates(19, 21); break;
/*      */           case 1:
/*  916 */             if (((0x10000000 & l) != 0L) && (kind > 35))
/*  917 */               kind = 35; break;
/*      */           case 5:
/*  920 */             if (((0xEFFFFFFF & l) != 0L) && (kind > 36)) {
/*  921 */               kind = 36;
/*      */             }
/*      */           }
/*      */         }
/*  925 */         while (i != startsAt);
/*      */       }
/*      */       else
/*      */       {
/*  929 */         int hiByte = this.curChar >> '\b';
/*  930 */         int i1 = hiByte >> 6;
/*  931 */         long l1 = 1L << (hiByte & 0x3F);
/*  932 */         int i2 = (this.curChar & 0xFF) >> '\6';
/*  933 */         long l2 = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/*  936 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/*  939 */             if ((jjCanMove_1(hiByte, i1, i2, l1, l2)) && (kind > 36)) {
/*  940 */               kind = 36;
/*      */             }
/*      */           }
/*      */         }
/*  944 */         while (i != startsAt);
/*      */       }
/*  946 */       if (kind != 2147483647)
/*      */       {
/*  948 */         this.jjmatchedKind = kind;
/*  949 */         this.jjmatchedPos = curPos;
/*  950 */         kind = 2147483647;
/*      */       }
/*  952 */       ++curPos;
/*  953 */       i = this.jjnewStateCnt; if (i == (startsAt = 6 - (this.jjnewStateCnt = startsAt)))
/*  954 */         return curPos; try {
/*  955 */         this.curChar = this.input_stream.readChar(); } catch (IOException e) { } }
/*  956 */     return curPos;
/*      */   }
/*      */ 
/*      */   private final int jjStopStringLiteralDfa_3(int pos, long active0)
/*      */   {
/*  961 */     switch (pos)
/*      */     {
/*      */     }
/*  964 */     return -1;
/*      */   }
/*      */ 
/*      */   private final int jjStartNfa_3(int pos, long active0)
/*      */   {
/*  969 */     return jjMoveNfa_3(jjStopStringLiteralDfa_3(pos, active0), pos + 1);
/*      */   }
/*      */ 
/*      */   private final int jjStartNfaWithStates_3(int pos, int kind, int state) {
/*  973 */     this.jjmatchedKind = kind;
/*  974 */     this.jjmatchedPos = pos;
/*      */     try { this.curChar = this.input_stream.readChar(); } catch (IOException e) {
/*  976 */       return (pos + 1); }
/*  977 */     return jjMoveNfa_3(state, pos + 1);
/*      */   }
/*      */ 
/*      */   private final int jjMoveStringLiteralDfa0_3() {
/*  981 */     switch (this.curChar)
/*      */     {
/*      */     case '"':
/*  984 */       return jjStopAtPos(0, 43);
/*      */     }
/*  986 */     return jjMoveNfa_3(0, 0);
/*      */   }
/*      */ 
/*      */   private final int jjMoveNfa_3(int startState, int curPos)
/*      */   {
/*  992 */     int startsAt = 0;
/*  993 */     this.jjnewStateCnt = 6;
/*  994 */     int i = 1;
/*  995 */     this.jjstateSet[0] = startState;
/*  996 */     int kind = 2147483647;
/*      */     while (true)
/*      */     {
/*      */       long l;
/*  999 */       if (++this.jjround == 2147483647)
/* 1000 */         ReInitRounds();
/* 1001 */       if (this.curChar < '@')
/*      */       {
/* 1003 */         l = 1L << this.curChar;
/*      */         do
/*      */         {
/* 1006 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/* 1009 */             if (((0xFFFFFFFF & l) != 0L) && (kind > 42))
/* 1010 */               kind = 42; break;
/*      */           case 1:
/* 1013 */             if (((0x0 & l) != 0L) && (kind > 41))
/* 1014 */               kind = 41; break;
/*      */           case 2:
/* 1017 */             if ((0x0 & l) != 0L)
/* 1018 */               this.jjstateSet[(this.jjnewStateCnt++)] = 3; break;
/*      */           case 3:
/* 1021 */             if ((0x0 & l) == 0L)
/*      */               continue;
/* 1023 */             if (kind > 41)
/* 1024 */               kind = 41;
/* 1025 */             this.jjstateSet[(this.jjnewStateCnt++)] = 4;
/* 1026 */             break;
/*      */           case 4:
/* 1028 */             if (((0x0 & l) != 0L) && (kind > 41)) {
/* 1029 */               kind = 41;
/*      */             }
/*      */           }
/*      */         }
/* 1033 */         while (i != startsAt);
/*      */       }
/* 1035 */       else if (this.curChar < 128)
/*      */       {
/* 1037 */         l = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/* 1040 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/* 1043 */             if ((0xEFFFFFFF & l) != 0L)
/*      */             {
/* 1045 */               if (kind > 42)
/* 1046 */                 kind = 42;
/*      */             }
/* 1048 */             else if (this.curChar == '\\')
/* 1049 */               jjAddStates(19, 21); break;
/*      */           case 1:
/* 1052 */             if (((0x10000000 & l) != 0L) && (kind > 41))
/* 1053 */               kind = 41; break;
/*      */           case 5:
/* 1056 */             if (((0xEFFFFFFF & l) != 0L) && (kind > 42)) {
/* 1057 */               kind = 42;
/*      */             }
/*      */           }
/*      */         }
/* 1061 */         while (i != startsAt);
/*      */       }
/*      */       else
/*      */       {
/* 1065 */         int hiByte = this.curChar >> '\b';
/* 1066 */         int i1 = hiByte >> 6;
/* 1067 */         long l1 = 1L << (hiByte & 0x3F);
/* 1068 */         int i2 = (this.curChar & 0xFF) >> '\6';
/* 1069 */         long l2 = 1L << (this.curChar & 0x3F);
/*      */         do
/*      */         {
/* 1072 */           switch (this.jjstateSet[(--i)])
/*      */           {
/*      */           case 0:
/* 1075 */             if ((jjCanMove_1(hiByte, i1, i2, l1, l2)) && (kind > 42)) {
/* 1076 */               kind = 42;
/*      */             }
/*      */           }
/*      */         }
/* 1080 */         while (i != startsAt);
/*      */       }
/* 1082 */       if (kind != 2147483647)
/*      */       {
/* 1084 */         this.jjmatchedKind = kind;
/* 1085 */         this.jjmatchedPos = curPos;
/* 1086 */         kind = 2147483647;
/*      */       }
/* 1088 */       ++curPos;
/* 1089 */       i = this.jjnewStateCnt; if (i == (startsAt = 6 - (this.jjnewStateCnt = startsAt)))
/* 1090 */         return curPos; try {
/* 1091 */         this.curChar = this.input_stream.readChar(); } catch (IOException e) { } }
/* 1092 */     return curPos;
/*      */   }
/*      */ 
/*      */   private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
/*      */   {
/* 1101 */     switch (hiByte)
/*      */     {
/*      */     case 0:
/* 1104 */       return ((jjbitVec2[i2] & l2) != 0L);
/*      */     case 48:
/* 1106 */       return ((jjbitVec3[i2] & l2) != 0L);
/*      */     case 49:
/* 1108 */       return ((jjbitVec4[i2] & l2) != 0L);
/*      */     case 51:
/* 1110 */       return ((jjbitVec5[i2] & l2) != 0L);
/*      */     case 61:
/* 1112 */       return ((jjbitVec6[i2] & l2) != 0L);
/*      */     }
/*      */ 
/* 1115 */     return ((jjbitVec0[i1] & l1) == 0L);
/*      */   }
/*      */ 
/*      */   private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2)
/*      */   {
/* 1121 */     switch (hiByte)
/*      */     {
/*      */     case 0:
/* 1124 */       return ((jjbitVec8[i2] & l2) != 0L);
/*      */     }
/*      */ 
/* 1127 */     return ((jjbitVec7[i1] & l1) == 0L);
/*      */   }
/*      */ 
/*      */   public ICSExpParserTokenManager(JavaCharStream stream)
/*      */   {
/*   82 */     this.debugStream = System.out;
/*      */ 
/* 1158 */     this.jjrounds = new int[27];
/* 1159 */     this.jjstateSet = new int[54];
/*      */ 
/* 1213 */     this.curLexState = 0;
/* 1214 */     this.defaultLexState = 0;
/*      */ 
/* 1167 */     this.input_stream = stream; }
/*      */ 
/*      */   public ICSExpParserTokenManager(JavaCharStream stream, int lexState) {
/* 1170 */     this(stream);
/* 1171 */     SwitchTo(lexState);
/*      */   }
/*      */ 
/*      */   public void ReInit(JavaCharStream stream) {
/* 1175 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 1176 */     this.curLexState = this.defaultLexState;
/* 1177 */     this.input_stream = stream;
/* 1178 */     ReInitRounds();
/*      */   }
/*      */ 
/*      */   private final void ReInitRounds()
/*      */   {
/* 1183 */     this.jjround = -2147483647;
/* 1184 */     for (int i = 27; i-- > 0; )
/* 1185 */       this.jjrounds[i] = -2147483648;
/*      */   }
/*      */ 
/*      */   public void ReInit(JavaCharStream stream, int lexState) {
/* 1189 */     ReInit(stream);
/* 1190 */     SwitchTo(lexState);
/*      */   }
/*      */ 
/*      */   public void SwitchTo(int lexState) {
/* 1194 */     if ((lexState >= 4) || (lexState < 0)) {
/* 1195 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/*      */     }
/* 1197 */     this.curLexState = lexState;
/*      */   }
/*      */ 
/*      */   protected Token jjFillToken()
/*      */   {
/* 1202 */     Token t = Token.newToken(this.jjmatchedKind);
/* 1203 */     t.kind = this.jjmatchedKind;
/* 1204 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/* 1205 */     t.image = ((im == null) ? this.input_stream.GetImage() : im);
/* 1206 */     t.beginLine = this.input_stream.getBeginLine();
/* 1207 */     t.beginColumn = this.input_stream.getBeginColumn();
/* 1208 */     t.endLine = this.input_stream.getEndLine();
/* 1209 */     t.endColumn = this.input_stream.getEndColumn();
/* 1210 */     return t;
/*      */   }
/*      */ 
/*      */   public Token getNextToken()
/*      */   {
/*      */     label5: Token matchedToken;
/* 1223 */     Token specialToken = null;
/*      */ 
/* 1225 */     int curPos = 0;
/*      */     try
/*      */     {
/* 1232 */       this.curChar = this.input_stream.BeginToken();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1236 */       this.jjmatchedKind = 0;
/* 1237 */       matchedToken = jjFillToken();
/* 1238 */       return matchedToken;
/*      */     }
/* 1240 */     this.image = null;
/* 1241 */     this.jjimageLen = 0;
/*      */     while (true) {
/*      */       while (true) {
/*      */         do {
/*      */           while (true) switch (this.curLexState) {
/*      */             case 0:
/*      */               try {
/* 1248 */                 this.input_stream.backup(0);
/* 1249 */                 while ((this.curChar <= ' ') && ((0x3600 & 1L << this.curChar) != 0L))
/* 1250 */                   this.curChar = this.input_stream.BeginToken();
/*      */               } catch (IOException e1) {
/* 1252 */                 break label5: } case 1:
/*      */             case 2:
/*      */             case 3: }  this.jjmatchedKind = 2147483647;
/* 1254 */           this.jjmatchedPos = 0;
/* 1255 */           curPos = jjMoveStringLiteralDfa0_0();
/* 1256 */           break label207:
/*      */ 
/* 1258 */           this.jjmatchedKind = 2147483647;
/* 1259 */           this.jjmatchedPos = 0;
/* 1260 */           curPos = jjMoveStringLiteralDfa0_1();
/* 1261 */           break label207:
/*      */ 
/* 1263 */           this.jjmatchedKind = 2147483647;
/* 1264 */           this.jjmatchedPos = 0;
/* 1265 */           curPos = jjMoveStringLiteralDfa0_2();
/* 1266 */           break label207:
/*      */ 
/* 1268 */           this.jjmatchedKind = 2147483647;
/* 1269 */           this.jjmatchedPos = 0;
/* 1270 */           curPos = jjMoveStringLiteralDfa0_3();
/*      */ 
/* 1273 */           label207: if (this.jjmatchedKind == 2147483647)
/*      */             break label411;
/* 1275 */           if (this.jjmatchedPos + 1 < curPos)
/* 1276 */             this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 1277 */           if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/*      */           {
/* 1279 */             matchedToken = jjFillToken();
/* 1280 */             TokenLexicalActions(matchedToken);
/* 1281 */             if (jjnewLexState[this.jjmatchedKind] != -1)
/* 1282 */               this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 1283 */             return matchedToken;
/*      */           }
/* 1285 */           if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) == 0L) break label358;
/*      */         }
/* 1287 */         while (jjnewLexState[this.jjmatchedKind] == -1);
/* 1288 */         this.curLexState = jjnewLexState[this.jjmatchedKind];
/*      */       }
/*      */ 
/* 1291 */       label358: MoreLexicalActions();
/* 1292 */       if (jjnewLexState[this.jjmatchedKind] != -1)
/* 1293 */         this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 1294 */       curPos = 0;
/* 1295 */       this.jjmatchedKind = 2147483647;
/*      */       try {
/* 1297 */         this.curChar = this.input_stream.readChar();
/*      */       }
/*      */       catch (IOException error_line)
/*      */       {
/* 1302 */         label411: int error_line = this.input_stream.getEndLine();
/* 1303 */         int error_column = this.input_stream.getEndColumn();
/* 1304 */         String error_after = null;
/* 1305 */         boolean EOFSeen = false;
/*      */         try { this.input_stream.readChar(); this.input_stream.backup(1);
/*      */         } catch (IOException e1) {
/* 1308 */           EOFSeen = true;
/* 1309 */           error_after = (curPos <= 1) ? "" : this.input_stream.GetImage();
/* 1310 */           if ((this.curChar != '\n') && (this.curChar != '\r')) break label506;
/* 1311 */           ++error_line;
/* 1312 */           error_column = 0; }
/* 1313 */         break label509:
/*      */ 
/* 1315 */         label506: ++error_column;
/*      */ 
/* 1317 */         if (!(EOFSeen)) {
/* 1318 */           label509: this.input_stream.backup(1);
/* 1319 */           error_after = (curPos <= 1) ? "" : this.input_stream.GetImage();
/*      */         }
/* 1321 */         throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void MoreLexicalActions()
/*      */   {
/* 1328 */     this.jjimageLen += ++this.jjmatchedPos;
/* 1329 */     switch (this.jjmatchedKind)
/*      */     {
/*      */     case 33:
/* 1332 */       if (this.image == null)
/* 1333 */         this.image = new StringBuffer();
/* 1334 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1335 */       this.jjimageLen = 0;
/* 1336 */       this.stringBuffer = new StringBuffer();
/* 1337 */       break;
/*      */     case 34:
/* 1339 */       if (this.image == null)
/* 1340 */         this.image = new StringBuffer();
/* 1341 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1342 */       this.jjimageLen = 0;
/* 1343 */       this.stringBuffer = new StringBuffer();
/* 1344 */       break;
/*      */     case 35:
/* 1346 */       if (this.image == null)
/* 1347 */         this.image = new StringBuffer();
/* 1348 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1349 */       this.jjimageLen = 0;
/* 1350 */       this.charValue = escapeChar(); this.stringBuffer.append(this.charValue);
/* 1351 */       break;
/*      */     case 36:
/* 1353 */       if (this.image == null)
/* 1354 */         this.image = new StringBuffer();
/* 1355 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1356 */       this.jjimageLen = 0;
/* 1357 */       this.charValue = this.image.charAt(this.image.length() - 1); this.stringBuffer.append(this.charValue);
/* 1358 */       break;
/*      */     case 38:
/* 1360 */       if (this.image == null)
/* 1361 */         this.image = new StringBuffer();
/* 1362 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1363 */       this.jjimageLen = 0;
/* 1364 */       this.charValue = escapeChar();
/* 1365 */       break;
/*      */     case 39:
/* 1367 */       if (this.image == null)
/* 1368 */         this.image = new StringBuffer();
/* 1369 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1370 */       this.jjimageLen = 0;
/* 1371 */       this.charValue = this.image.charAt(this.image.length() - 1);
/* 1372 */       break;
/*      */     case 41:
/* 1374 */       if (this.image == null)
/* 1375 */         this.image = new StringBuffer();
/* 1376 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1377 */       this.jjimageLen = 0;
/* 1378 */       this.stringBuffer.append(escapeChar());
/* 1379 */       break;
/*      */     case 42:
/* 1381 */       if (this.image == null)
/* 1382 */         this.image = new StringBuffer();
/* 1383 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1384 */       this.jjimageLen = 0;
/* 1385 */       this.stringBuffer.append(this.image.charAt(this.image.length() - 1));
/*      */     case 37:
/*      */     case 40:
/*      */     }
/*      */   }
/*      */ 
/*      */   void TokenLexicalActions(Token matchedToken)
/*      */   {
/* 1393 */     switch (this.jjmatchedKind)
/*      */     {
/*      */     case 37:
/* 1396 */       if (this.image == null)
/* 1397 */         this.image = new StringBuffer();
/* 1398 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + ++this.jjmatchedPos));
/* 1399 */       if (this.stringBuffer.length() == 1)
/*      */       {
/* 1401 */         this.literalValue = new String("" + this.charValue);
/* 1402 */         return; }
/* 1403 */       this.literalValue = new String(this.stringBuffer);
/*      */ 
/* 1405 */       break;
/*      */     case 40:
/* 1407 */       if (this.image == null)
/* 1408 */         this.image = new StringBuffer();
/* 1409 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + ++this.jjmatchedPos));
/* 1410 */       this.literalValue = new Character(this.charValue);
/* 1411 */       break;
/*      */     case 43:
/* 1413 */       if (this.image == null)
/* 1414 */         this.image = new StringBuffer();
/* 1415 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + ++this.jjmatchedPos));
/* 1416 */       this.literalValue = new String(this.stringBuffer);
/* 1417 */       break;
/*      */     case 44:
/* 1419 */       if (this.image == null)
/* 1420 */         this.image = new StringBuffer();
/* 1421 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + ++this.jjmatchedPos));
/* 1422 */       this.literalValue = makeInt();
/*      */ 
/* 1424 */       break;
/*      */     case 45:
/* 1426 */       if (this.image == null)
/* 1427 */         this.image = new StringBuffer();
/* 1428 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + ++this.jjmatchedPos));
/* 1429 */       this.literalValue = makeFloat();
/*      */     case 38:
/*      */     case 39:
/*      */     case 41:
/*      */     case 42:
/*      */     }
/*      */   }
/*      */ }