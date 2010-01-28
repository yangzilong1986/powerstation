 package com.hisun.hiexpression.imp;
 
 import java.io.IOException;
 import java.io.PrintStream;
 import java.math.BigDecimal;
 import java.math.BigInteger;
 
 public class ICSExpParserTokenManager
   implements ICSExpParserConstants
 {
   Object literalValue;
   private char charValue;
   private char charLiteralStartQuote;
   private StringBuffer stringBuffer;
   public PrintStream debugStream;
   static final long[] jjbitVec0 = { 2301339413881290750L, -16384L, 4294967295L, 432345564227567616L };
 
   static final long[] jjbitVec2 = { 0L, 0L, 0L, -36028797027352577L };
 
   static final long[] jjbitVec3 = { 0L, -1L, -1L, -1L };
 
   static final long[] jjbitVec4 = { -1L, -1L, 65535L, 0L };
 
   static final long[] jjbitVec5 = { -1L, -1L, 0L, 0L };
 
   static final long[] jjbitVec6 = { 70368744177663L, 0L, 0L, 0L };
 
   static final long[] jjbitVec7 = { -2L, -1L, -1L, -1L };
 
   static final long[] jjbitVec8 = { 0L, 0L, -1L, -1L };
 
   static final int[] jjnextStates = { 15, 16, 18, 19, 22, 13, 24, 25, 7, 9, 10, 13, 17, 10, 13, 11, 12, 20, 21, 1, 2, 3 };
 
   public static final String[] jjstrLiteralImages = { "", "==", "!=", "<", ">", "<=", ">=", "@BAS.", "@BCFG.", "@MSG.", "@ETF.", "@PARA.", "@SYS.", "$", "~", "%", "#", "@", "true", "false", "null", "(", ")", ",", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
 
   public static final String[] lexStateNames = { "DEFAULT", "WithinCharLiteral", "WithinBackCharLiteral", "WithinStringLiteral" };
 
   public static final int[] jjnewLexState = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2, 1, 3, -1, -1, 0, -1, -1, 0, -1, -1, 0, -1, -1, -1, -1, -1, -1 };
 
   static final long[] jjtoToken = { 62810155384831L };
 
   static final long[] jjtoSkip = { 520093696L };
 
   static final long[] jjtoMore = { 7554847473664L };
   protected JavaCharStream input_stream;
   private final int[] jjrounds;
   private final int[] jjstateSet;
   StringBuffer image;
   int jjimageLen;
   int lengthOfMatch;
   protected char curChar;
   int curLexState;
   int defaultLexState;
   int jjnewStateCnt;
   int jjround;
   int jjmatchedPos;
   int jjmatchedKind;
 
   private char escapeChar()
   {
     int ofs = this.image.length() - 1;
     switch (this.image.charAt(ofs))
     {
     case 'n':
       return '\n';
     case 'r':
       return '\r';
     case 't':
       return '\t';
     case 'b':
       return '\b';
     case 'f':
       return '\f';
     case '\\':
       return '\\';
     case '\'':
       return '\'';
     case '"':
       return '"';
     }
 
     while (this.image.charAt(--ofs) != '\\');
     int value = 0;
     while (++ofs < this.image.length())
       value = value << 3 | this.image.charAt(ofs) - '0';
     return (char)value;
   }
 
   private Object makeInt()
   {
     Object result;
     String s = this.image.toString();
     int base = 10;
 
     if (s.charAt(0) == '0')
       base = ((s.length() > 1) && (((s.charAt(1) == 'x') || (s.charAt(1) == 'X')))) ? 16 : 8;
     if (base == 16)
       s = s.substring(2);
     switch (s.charAt(s.length() - 1))
     {
     case 'L':
     case 'l':
       result = Long.valueOf(s.substring(0, s.length() - 1), base);
       break;
     case 'H':
     case 'h':
       result = new BigInteger(s.substring(0, s.length() - 1), base);
       break;
     default:
       result = Integer.valueOf(s, base);
     }
 
     return result;
   }
 
   private Object makeFloat()
   {
     String s = this.image.toString();
     switch (s.charAt(s.length() - 1)) {
     case 'F':
     case 'f':
       return Float.valueOf(s);
     case 'B':
     case 'b':
       return new BigDecimal(s.substring(0, s.length() - 1));
     case 'D':
     case 'd': }
     return Double.valueOf(s);
   }
 
   public void setDebugStream(PrintStream ds) {
     this.debugStream = ds; }
 
   private final int jjStopStringLiteralDfa_0(int pos, long active0) {
     switch (pos)
     {
     case 0:
       if ((active0 & 0x1C0000) != 0L)
       {
         this.jjmatchedKind = 29;
         return 27;
       }
       return -1;
     case 1:
       if ((active0 & 0x1C0000) != 0L)
       {
         this.jjmatchedKind = 29;
         this.jjmatchedPos = 1;
         return 27;
       }
       return -1;
     case 2:
       if ((active0 & 0x1C0000) != 0L)
       {
         this.jjmatchedKind = 29;
         this.jjmatchedPos = 2;
         return 27;
       }
       return -1;
     case 3:
       if ((active0 & 0x80000) != 0L)
       {
         this.jjmatchedKind = 29;
         this.jjmatchedPos = 3;
         return 27;
       }
       if ((active0 & 0x140000) != 0L)
         return 27;
       return -1;
     case 4:
       if ((active0 & 0x80000) != 0L)
         return 27;
       return -1;
     }
     return -1;
   }
 
   private final int jjStartNfa_0(int pos, long active0)
   {
     return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
   }
 
   private final int jjStopAtPos(int pos, int kind) {
     this.jjmatchedKind = kind;
     this.jjmatchedPos = pos;
     return (pos + 1);
   }
 
   private final int jjStartNfaWithStates_0(int pos, int kind, int state) {
     this.jjmatchedKind = kind;
     this.jjmatchedPos = pos;
     try { this.curChar = this.input_stream.readChar(); } catch (IOException e) {
       return (pos + 1); }
     return jjMoveNfa_0(state, pos + 1);
   }
 
   private final int jjMoveStringLiteralDfa0_0() {
     switch (this.curChar)
     {
     case '!':
       return jjMoveStringLiteralDfa1_0(4L);
     case '"':
       return jjStopAtPos(0, 34);
     case '#':
       return jjStopAtPos(0, 16);
     case '$':
       return jjStopAtPos(0, 13);
     case '%':
       return jjStopAtPos(0, 15);
     case '\'':
       return jjStopAtPos(0, 33);
     case '(':
       return jjStopAtPos(0, 21);
     case ')':
       return jjStopAtPos(0, 22);
     case ',':
       return jjStopAtPos(0, 23);
     case '<':
       this.jjmatchedKind = 3;
       return jjMoveStringLiteralDfa1_0(32L);
     case '=':
       return jjMoveStringLiteralDfa1_0(2L);
     case '>':
       this.jjmatchedKind = 4;
       return jjMoveStringLiteralDfa1_0(64L);
     case '@':
       this.jjmatchedKind = 17;
       return jjMoveStringLiteralDfa1_0(8064L);
     case '`':
       return jjStopAtPos(0, 32);
     case 'f':
       return jjMoveStringLiteralDfa1_0(524288L);
     case 'n':
       return jjMoveStringLiteralDfa1_0(1048576L);
     case 't':
       return jjMoveStringLiteralDfa1_0(262144L);
     case '~':
       return jjStopAtPos(0, 14);
     }
     return jjMoveNfa_0(0, 0);
   }
 
   private final int jjMoveStringLiteralDfa1_0(long active0) {
     try {
       this.curChar = this.input_stream.readChar();
     } catch (IOException e) {
       jjStopStringLiteralDfa_0(0, active0);
       return 1;
     }
     switch (this.curChar)
     {
     case '=':
       if ((active0 & 0x2) != 0L)
         return jjStopAtPos(1, 1);
       if ((active0 & 0x4) != 0L)
         return jjStopAtPos(1, 2);
       if ((active0 & 0x20) != 0L)
         return jjStopAtPos(1, 5);
       if ((active0 & 0x40) == 0L) break label253;
       return jjStopAtPos(1, 6);
     case 'B':
       return jjMoveStringLiteralDfa2_0(active0, 384L);
     case 'E':
       return jjMoveStringLiteralDfa2_0(active0, 1024L);
     case 'M':
       return jjMoveStringLiteralDfa2_0(active0, 512L);
     case 'P':
       return jjMoveStringLiteralDfa2_0(active0, 2048L);
     case 'S':
       return jjMoveStringLiteralDfa2_0(active0, 4096L);
     case 'a':
       return jjMoveStringLiteralDfa2_0(active0, 524288L);
     case 'r':
       return jjMoveStringLiteralDfa2_0(active0, 262144L);
     case 'u':
       return jjMoveStringLiteralDfa2_0(active0, 1048576L);
     }
 
     label253: return jjStartNfa_0(0, active0);
   }
 
   private final int jjMoveStringLiteralDfa2_0(long old0, long active0) {
     if ((active0 &= old0) == 0L)
       return jjStartNfa_0(0, old0); try {
       this.curChar = this.input_stream.readChar();
     } catch (IOException e) {
       jjStopStringLiteralDfa_0(1, active0);
       return 2;
     }
     switch (this.curChar)
     {
     case 'A':
       return jjMoveStringLiteralDfa3_0(active0, 2176L);
     case 'C':
       return jjMoveStringLiteralDfa3_0(active0, 256L);
     case 'S':
       return jjMoveStringLiteralDfa3_0(active0, 512L);
     case 'T':
       return jjMoveStringLiteralDfa3_0(active0, 1024L);
     case 'Y':
       return jjMoveStringLiteralDfa3_0(active0, 4096L);
     case 'l':
       return jjMoveStringLiteralDfa3_0(active0, 1572864L);
     case 'u':
       return jjMoveStringLiteralDfa3_0(active0, 262144L);
     }
 
     return jjStartNfa_0(1, active0);
   }
 
   private final int jjMoveStringLiteralDfa3_0(long old0, long active0) {
     if ((active0 &= old0) == 0L)
       return jjStartNfa_0(1, old0); try {
       this.curChar = this.input_stream.readChar();
     } catch (IOException e) {
       jjStopStringLiteralDfa_0(2, active0);
       return 3;
     }
     switch (this.curChar)
     {
     case 'F':
       return jjMoveStringLiteralDfa4_0(active0, 1280L);
     case 'G':
       return jjMoveStringLiteralDfa4_0(active0, 512L);
     case 'R':
       return jjMoveStringLiteralDfa4_0(active0, 2048L);
     case 'S':
       return jjMoveStringLiteralDfa4_0(active0, 4224L);
     case 'e':
       if ((active0 & 0x40000) == 0L) break label197;
       return jjStartNfaWithStates_0(3, 18, 27);
     case 'l':
       if ((active0 & 0x100000) == 0L) break label197;
       return jjStartNfaWithStates_0(3, 20, 27);
     case 's':
       return jjMoveStringLiteralDfa4_0(active0, 524288L);
     }
 
     label197: return jjStartNfa_0(2, active0);
   }
 
   private final int jjMoveStringLiteralDfa4_0(long old0, long active0) {
     if ((active0 &= old0) == 0L)
       return jjStartNfa_0(2, old0); try {
       this.curChar = this.input_stream.readChar();
     } catch (IOException e) {
       jjStopStringLiteralDfa_0(3, active0);
       return 4;
     }
     switch (this.curChar)
     {
     case '.':
       if ((active0 & 0x80) != 0L)
         return jjStopAtPos(4, 7);
       if ((active0 & 0x200) != 0L)
         return jjStopAtPos(4, 9);
       if ((active0 & 0x400) != 0L)
         return jjStopAtPos(4, 10);
       if ((active0 & 0x1000) == 0L) break label198;
       return jjStopAtPos(4, 12);
     case 'A':
       return jjMoveStringLiteralDfa5_0(active0, 2048L);
     case 'G':
       return jjMoveStringLiteralDfa5_0(active0, 256L);
     case 'e':
       if ((active0 & 0x80000) == 0L) break label198;
       return jjStartNfaWithStates_0(4, 19, 27);
     }
 
     label198: return jjStartNfa_0(3, active0);
   }
 
   private final int jjMoveStringLiteralDfa5_0(long old0, long active0) {
     if ((active0 &= old0) == 0L)
       return jjStartNfa_0(3, old0); try {
       this.curChar = this.input_stream.readChar();
     } catch (IOException e) {
       jjStopStringLiteralDfa_0(4, active0);
       return 5;
     }
     switch (this.curChar)
     {
     case '.':
       if ((active0 & 0x100) != 0L)
         return jjStopAtPos(5, 8);
       if ((active0 & 0x800) == 0L) break label100;
       return jjStopAtPos(5, 11);
     }
 
     label100: return jjStartNfa_0(4, active0);
   }
 
   private final void jjCheckNAdd(int state) {
     if (this.jjrounds[state] == this.jjround)
       return;
     this.jjstateSet[(this.jjnewStateCnt++)] = state;
     this.jjrounds[state] = this.jjround;
   }
 
   private final void jjAddStates(int start, int end)
   {
     do
       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
     while (start++ != end);
   }
 
   private final void jjCheckNAddTwoStates(int state1, int state2) {
     jjCheckNAdd(state1);
     jjCheckNAdd(state2);
   }
 
   private final void jjCheckNAddStates(int start, int end) {
     do
       jjCheckNAdd(jjnextStates[start]);
     while (start++ != end);
   }
 
   private final void jjCheckNAddStates(int start) {
     jjCheckNAdd(jjnextStates[start]);
     jjCheckNAdd(jjnextStates[(start + 1)]);
   }
 
   private final int jjMoveNfa_0(int startState, int curPos)
   {
     int startsAt = 0;
     this.jjnewStateCnt = 27;
     int i = 1;
     this.jjstateSet[0] = startState;
     int kind = 2147483647;
     while (true)
     {
       long l;
       if (++this.jjround == 2147483647)
         ReInitRounds();
       if (this.curChar < '@')
       {
         l = 1L << this.curChar;
         do
         {
           switch (this.jjstateSet[(--i)])
           {
           case 0:
             if ((0x0 & l) != 0L)
               jjCheckNAddStates(0, 5);
             else if (this.curChar == '.')
               jjCheckNAdd(9);
             if ((0x0 & l) != 0L)
             {
               if (kind > 44)
                 kind = 44;
               jjCheckNAddTwoStates(6, 7);
             } else {
               if (this.curChar != '0')
                 continue;
               if (kind > 44)
                 kind = 44;
               jjCheckNAddStates(6, 8); }
             break;
           case 27:
             if ((0x0 & l) != 0L)
             {
               if (kind > 29)
                 kind = 29;
               jjCheckNAddTwoStates(1, 2);
             }
             else if (this.curChar == '.') {
               this.jjstateSet[(this.jjnewStateCnt++)] = 3; } break;
           case 1:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 29)
               kind = 29;
             jjCheckNAddTwoStates(1, 2);
             break;
           case 2:
             if (this.curChar == '.')
               this.jjstateSet[(this.jjnewStateCnt++)] = 3; break;
           case 4:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 29)
               kind = 29;
             jjCheckNAddTwoStates(2, 4);
             break;
           case 5:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 44)
               kind = 44;
             jjCheckNAddTwoStates(6, 7);
             break;
           case 6:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 44)
               kind = 44;
             jjCheckNAddTwoStates(6, 7);
             break;
           case 8:
             if (this.curChar == '.')
               jjCheckNAdd(9); break;
           case 9:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 45)
               kind = 45;
             jjCheckNAddStates(9, 11);
             break;
           case 11:
             if ((0x0 & l) != 0L)
               jjCheckNAdd(12); break;
           case 12:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 45)
               kind = 45;
             jjCheckNAddTwoStates(12, 13);
             break;
           case 14:
             if ((0x0 & l) != 0L)
               jjCheckNAddStates(0, 5); break;
           case 15:
             if ((0x0 & l) != 0L)
               jjCheckNAddTwoStates(15, 16); break;
           case 16:
             if (this.curChar != '.')
               continue;
             if (kind > 45)
               kind = 45;
             jjCheckNAddStates(12, 14);
             break;
           case 17:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 45)
               kind = 45;
             jjCheckNAddStates(12, 14);
             break;
           case 18:
             if ((0x0 & l) != 0L)
               jjCheckNAddTwoStates(18, 19); break;
           case 20:
             if ((0x0 & l) != 0L)
               jjCheckNAdd(21); break;
           case 21:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 45)
               kind = 45;
             jjCheckNAddTwoStates(21, 13);
             break;
           case 22:
             if ((0x0 & l) != 0L)
               jjCheckNAddTwoStates(22, 13); break;
           case 23:
             if (this.curChar != '0')
               continue;
             if (kind > 44)
               kind = 44;
             jjCheckNAddStates(6, 8);
             break;
           case 24:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 44)
               kind = 44;
             jjCheckNAddTwoStates(24, 7);
             break;
           case 26:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 44)
               kind = 44; jjCheckNAddTwoStates(26, 7);
           case 3:
           case 7:
           case 10:
           case 13:
           case 19:
           case 25: }  } while (i != startsAt);
       }
       else if (this.curChar < 128)
       {
         l = 1L << (this.curChar & 0x3F);
         do
         {
           switch (this.jjstateSet[(--i)])
           {
           case 0:
             if ((0x87FFFFFE & l) == 0L)
               continue;
             if (kind > 29)
               kind = 29;
             jjCheckNAddTwoStates(1, 2);
             break;
           case 1:
           case 27:
             if ((0x87FFFFFE & l) == 0L)
               continue;
             if (kind > 29)
               kind = 29;
             jjCheckNAddTwoStates(1, 2);
             break;
           case 3:
           case 4:
             if ((0x87FFFFFE & l) == 0L)
               continue;
             if (kind > 29)
               kind = 29;
             jjCheckNAddTwoStates(2, 4);
             break;
           case 7:
             if (((0x1100 & l) != 0L) && (kind > 44))
               kind = 44; break;
           case 10:
             if ((0x20 & l) != 0L)
               jjAddStates(15, 16); break;
           case 13:
             if (((0x54 & l) != 0L) && (kind > 45))
               kind = 45; break;
           case 19:
             if ((0x20 & l) != 0L)
               jjAddStates(17, 18); break;
           case 25:
             if ((0x1000000 & l) != 0L)
               jjCheckNAdd(26); break;
           case 26:
             if ((0x7E & l) == 0L)
               continue;
             if (kind > 44)
               kind = 44; jjCheckNAddTwoStates(26, 7);
           case 2:
           case 5:
           case 6:
           case 8:
           case 9:
           case 11:
           case 12:
           case 14:
           case 15:
           case 16:
           case 17:
           case 18:
           case 20:
           case 21:
           case 22:
           case 23:
           case 24: }  } while (i != startsAt);
       }
       else
       {
         int hiByte = this.curChar >> '\b';
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 0x3F);
         int i2 = (this.curChar & 0xFF) >> '\6';
         long l2 = 1L << (this.curChar & 0x3F);
         do
         {
           switch (this.jjstateSet[(--i)])
           {
           case 0:
             if (!(jjCanMove_0(hiByte, i1, i2, l1, l2)))
               continue;
             if (kind > 29)
               kind = 29;
             jjCheckNAddTwoStates(1, 2);
             break;
           case 1:
           case 27:
             if (!(jjCanMove_0(hiByte, i1, i2, l1, l2)))
               continue;
             if (kind > 29)
               kind = 29;
             jjCheckNAddTwoStates(1, 2);
             break;
           case 3:
           case 4:
             if (!(jjCanMove_0(hiByte, i1, i2, l1, l2)))
               continue;
             if (kind > 29)
               kind = 29;
             jjCheckNAddTwoStates(2, 4);
           }
         }
 
         while (i != startsAt);
       }
       if (kind != 2147483647)
       {
         this.jjmatchedKind = kind;
         this.jjmatchedPos = curPos;
         kind = 2147483647;
       }
       ++curPos;
       i = this.jjnewStateCnt; if (i == (startsAt = 27 - (this.jjnewStateCnt = startsAt)))
         return curPos; try {
         this.curChar = this.input_stream.readChar(); } catch (IOException e) { } }
     return curPos;
   }
 
   private final int jjStopStringLiteralDfa_2(int pos, long active0)
   {
     switch (pos)
     {
     }
     return -1;
   }
 
   private final int jjStartNfa_2(int pos, long active0)
   {
     return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
   }
 
   private final int jjStartNfaWithStates_2(int pos, int kind, int state) {
     this.jjmatchedKind = kind;
     this.jjmatchedPos = pos;
     try { this.curChar = this.input_stream.readChar(); } catch (IOException e) {
       return (pos + 1); }
     return jjMoveNfa_2(state, pos + 1);
   }
 
   private final int jjMoveStringLiteralDfa0_2() {
     switch (this.curChar)
     {
     case '`':
       return jjStopAtPos(0, 40);
     }
     return jjMoveNfa_2(0, 0);
   }
 
   private final int jjMoveNfa_2(int startState, int curPos)
   {
     int startsAt = 0;
     this.jjnewStateCnt = 6;
     int i = 1;
     this.jjstateSet[0] = startState;
     int kind = 2147483647;
     while (true)
     {
       long l;
       if (++this.jjround == 2147483647)
         ReInitRounds();
       if (this.curChar < '@')
       {
         l = 1L << this.curChar;
         do
         {
           switch (this.jjstateSet[(--i)])
           {
           case 0:
             if (kind > 39)
               kind = 39; break;
           case 1:
             if (((0x0 & l) != 0L) && (kind > 38))
               kind = 38; break;
           case 2:
             if ((0x0 & l) != 0L)
               this.jjstateSet[(this.jjnewStateCnt++)] = 3; break;
           case 3:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 38)
               kind = 38;
             this.jjstateSet[(this.jjnewStateCnt++)] = 4;
             break;
           case 4:
             if (((0x0 & l) != 0L) && (kind > 38)) {
               kind = 38;
             }
           }
         }
         while (i != startsAt);
       }
       else if (this.curChar < 128)
       {
         l = 1L << (this.curChar & 0x3F);
         do
         {
           switch (this.jjstateSet[(--i)])
           {
           case 0:
             if ((0xEFFFFFFF & l) != 0L)
             {
               if (kind > 39)
                 kind = 39;
             }
             else if (this.curChar == '\\')
               jjAddStates(19, 21); break;
           case 1:
             if (((0x10000000 & l) != 0L) && (kind > 38))
               kind = 38; break;
           case 5:
             if (((0xEFFFFFFF & l) != 0L) && (kind > 39)) {
               kind = 39;
             }
           }
         }
         while (i != startsAt);
       }
       else
       {
         int hiByte = this.curChar >> '\b';
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 0x3F);
         int i2 = (this.curChar & 0xFF) >> '\6';
         long l2 = 1L << (this.curChar & 0x3F);
         do
         {
           switch (this.jjstateSet[(--i)])
           {
           case 0:
             if ((jjCanMove_1(hiByte, i1, i2, l1, l2)) && (kind > 39)) {
               kind = 39;
             }
           }
         }
         while (i != startsAt);
       }
       if (kind != 2147483647)
       {
         this.jjmatchedKind = kind;
         this.jjmatchedPos = curPos;
         kind = 2147483647;
       }
       ++curPos;
       i = this.jjnewStateCnt; if (i == (startsAt = 6 - (this.jjnewStateCnt = startsAt)))
         return curPos; try {
         this.curChar = this.input_stream.readChar(); } catch (IOException e) { } }
     return curPos;
   }
 
   private final int jjStopStringLiteralDfa_1(int pos, long active0)
   {
     switch (pos)
     {
     }
     return -1;
   }
 
   private final int jjStartNfa_1(int pos, long active0)
   {
     return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
   }
 
   private final int jjStartNfaWithStates_1(int pos, int kind, int state) {
     this.jjmatchedKind = kind;
     this.jjmatchedPos = pos;
     try { this.curChar = this.input_stream.readChar(); } catch (IOException e) {
       return (pos + 1); }
     return jjMoveNfa_1(state, pos + 1);
   }
 
   private final int jjMoveStringLiteralDfa0_1() {
     switch (this.curChar)
     {
     case '\'':
       return jjStopAtPos(0, 37);
     }
     return jjMoveNfa_1(0, 0);
   }
 
   private final int jjMoveNfa_1(int startState, int curPos)
   {
     int startsAt = 0;
     this.jjnewStateCnt = 6;
     int i = 1;
     this.jjstateSet[0] = startState;
     int kind = 2147483647;
     while (true)
     {
       long l;
       if (++this.jjround == 2147483647)
         ReInitRounds();
       if (this.curChar < '@')
       {
         l = 1L << this.curChar;
         do
         {
           switch (this.jjstateSet[(--i)])
           {
           case 0:
             if (((0xFFFFFFFF & l) != 0L) && (kind > 36))
               kind = 36; break;
           case 1:
             if (((0x0 & l) != 0L) && (kind > 35))
               kind = 35; break;
           case 2:
             if ((0x0 & l) != 0L)
               this.jjstateSet[(this.jjnewStateCnt++)] = 3; break;
           case 3:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 35)
               kind = 35;
             this.jjstateSet[(this.jjnewStateCnt++)] = 4;
             break;
           case 4:
             if (((0x0 & l) != 0L) && (kind > 35)) {
               kind = 35;
             }
           }
         }
         while (i != startsAt);
       }
       else if (this.curChar < 128)
       {
         l = 1L << (this.curChar & 0x3F);
         do
         {
           switch (this.jjstateSet[(--i)])
           {
           case 0:
             if ((0xEFFFFFFF & l) != 0L)
             {
               if (kind > 36)
                 kind = 36;
             }
             else if (this.curChar == '\\')
               jjAddStates(19, 21); break;
           case 1:
             if (((0x10000000 & l) != 0L) && (kind > 35))
               kind = 35; break;
           case 5:
             if (((0xEFFFFFFF & l) != 0L) && (kind > 36)) {
               kind = 36;
             }
           }
         }
         while (i != startsAt);
       }
       else
       {
         int hiByte = this.curChar >> '\b';
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 0x3F);
         int i2 = (this.curChar & 0xFF) >> '\6';
         long l2 = 1L << (this.curChar & 0x3F);
         do
         {
           switch (this.jjstateSet[(--i)])
           {
           case 0:
             if ((jjCanMove_1(hiByte, i1, i2, l1, l2)) && (kind > 36)) {
               kind = 36;
             }
           }
         }
         while (i != startsAt);
       }
       if (kind != 2147483647)
       {
         this.jjmatchedKind = kind;
         this.jjmatchedPos = curPos;
         kind = 2147483647;
       }
       ++curPos;
       i = this.jjnewStateCnt; if (i == (startsAt = 6 - (this.jjnewStateCnt = startsAt)))
         return curPos; try {
         this.curChar = this.input_stream.readChar(); } catch (IOException e) { } }
     return curPos;
   }
 
   private final int jjStopStringLiteralDfa_3(int pos, long active0)
   {
     switch (pos)
     {
     }
     return -1;
   }
 
   private final int jjStartNfa_3(int pos, long active0)
   {
     return jjMoveNfa_3(jjStopStringLiteralDfa_3(pos, active0), pos + 1);
   }
 
   private final int jjStartNfaWithStates_3(int pos, int kind, int state) {
     this.jjmatchedKind = kind;
     this.jjmatchedPos = pos;
     try { this.curChar = this.input_stream.readChar(); } catch (IOException e) {
       return (pos + 1); }
     return jjMoveNfa_3(state, pos + 1);
   }
 
   private final int jjMoveStringLiteralDfa0_3() {
     switch (this.curChar)
     {
     case '"':
       return jjStopAtPos(0, 43);
     }
     return jjMoveNfa_3(0, 0);
   }
 
   private final int jjMoveNfa_3(int startState, int curPos)
   {
     int startsAt = 0;
     this.jjnewStateCnt = 6;
     int i = 1;
     this.jjstateSet[0] = startState;
     int kind = 2147483647;
     while (true)
     {
       long l;
       if (++this.jjround == 2147483647)
         ReInitRounds();
       if (this.curChar < '@')
       {
         l = 1L << this.curChar;
         do
         {
           switch (this.jjstateSet[(--i)])
           {
           case 0:
             if (((0xFFFFFFFF & l) != 0L) && (kind > 42))
               kind = 42; break;
           case 1:
             if (((0x0 & l) != 0L) && (kind > 41))
               kind = 41; break;
           case 2:
             if ((0x0 & l) != 0L)
               this.jjstateSet[(this.jjnewStateCnt++)] = 3; break;
           case 3:
             if ((0x0 & l) == 0L)
               continue;
             if (kind > 41)
               kind = 41;
             this.jjstateSet[(this.jjnewStateCnt++)] = 4;
             break;
           case 4:
             if (((0x0 & l) != 0L) && (kind > 41)) {
               kind = 41;
             }
           }
         }
         while (i != startsAt);
       }
       else if (this.curChar < 128)
       {
         l = 1L << (this.curChar & 0x3F);
         do
         {
           switch (this.jjstateSet[(--i)])
           {
           case 0:
             if ((0xEFFFFFFF & l) != 0L)
             {
               if (kind > 42)
                 kind = 42;
             }
             else if (this.curChar == '\\')
               jjAddStates(19, 21); break;
           case 1:
             if (((0x10000000 & l) != 0L) && (kind > 41))
               kind = 41; break;
           case 5:
             if (((0xEFFFFFFF & l) != 0L) && (kind > 42)) {
               kind = 42;
             }
           }
         }
         while (i != startsAt);
       }
       else
       {
         int hiByte = this.curChar >> '\b';
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 0x3F);
         int i2 = (this.curChar & 0xFF) >> '\6';
         long l2 = 1L << (this.curChar & 0x3F);
         do
         {
           switch (this.jjstateSet[(--i)])
           {
           case 0:
             if ((jjCanMove_1(hiByte, i1, i2, l1, l2)) && (kind > 42)) {
               kind = 42;
             }
           }
         }
         while (i != startsAt);
       }
       if (kind != 2147483647)
       {
         this.jjmatchedKind = kind;
         this.jjmatchedPos = curPos;
         kind = 2147483647;
       }
       ++curPos;
       i = this.jjnewStateCnt; if (i == (startsAt = 6 - (this.jjnewStateCnt = startsAt)))
         return curPos; try {
         this.curChar = this.input_stream.readChar(); } catch (IOException e) { } }
     return curPos;
   }
 
   private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
   {
     switch (hiByte)
     {
     case 0:
       return ((jjbitVec2[i2] & l2) != 0L);
     case 48:
       return ((jjbitVec3[i2] & l2) != 0L);
     case 49:
       return ((jjbitVec4[i2] & l2) != 0L);
     case 51:
       return ((jjbitVec5[i2] & l2) != 0L);
     case 61:
       return ((jjbitVec6[i2] & l2) != 0L);
     }
 
     return ((jjbitVec0[i1] & l1) == 0L);
   }
 
   private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2)
   {
     switch (hiByte)
     {
     case 0:
       return ((jjbitVec8[i2] & l2) != 0L);
     }
 
     return ((jjbitVec7[i1] & l1) == 0L);
   }
 
   public ICSExpParserTokenManager(JavaCharStream stream)
   {
     this.debugStream = System.out;
 
     this.jjrounds = new int[27];
     this.jjstateSet = new int[54];
 
     this.curLexState = 0;
     this.defaultLexState = 0;
 
     this.input_stream = stream; }
 
   public ICSExpParserTokenManager(JavaCharStream stream, int lexState) {
     this(stream);
     SwitchTo(lexState);
   }
 
   public void ReInit(JavaCharStream stream) {
     this.jjmatchedPos = (this.jjnewStateCnt = 0);
     this.curLexState = this.defaultLexState;
     this.input_stream = stream;
     ReInitRounds();
   }
 
   private final void ReInitRounds()
   {
     this.jjround = -2147483647;
     for (int i = 27; i-- > 0; )
       this.jjrounds[i] = -2147483648;
   }
 
   public void ReInit(JavaCharStream stream, int lexState) {
     ReInit(stream);
     SwitchTo(lexState);
   }
 
   public void SwitchTo(int lexState) {
     if ((lexState >= 4) || (lexState < 0)) {
       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
     }
     this.curLexState = lexState;
   }
 
   protected Token jjFillToken()
   {
     Token t = Token.newToken(this.jjmatchedKind);
     t.kind = this.jjmatchedKind;
     String im = jjstrLiteralImages[this.jjmatchedKind];
     t.image = ((im == null) ? this.input_stream.GetImage() : im);
     t.beginLine = this.input_stream.getBeginLine();
     t.beginColumn = this.input_stream.getBeginColumn();
     t.endLine = this.input_stream.getEndLine();
     t.endColumn = this.input_stream.getEndColumn();
     return t;
   }
 
   public Token getNextToken()
   {
     label5: Token matchedToken;
     Token specialToken = null;
 
     int curPos = 0;
     try
     {
       this.curChar = this.input_stream.BeginToken();
     }
     catch (IOException e)
     {
       this.jjmatchedKind = 0;
       matchedToken = jjFillToken();
       return matchedToken;
     }
     this.image = null;
     this.jjimageLen = 0;
     while (true) {
       while (true) {
         do {
           while (true) switch (this.curLexState) {
             case 0:
               try {
                 this.input_stream.backup(0);
                 while ((this.curChar <= ' ') && ((0x3600 & 1L << this.curChar) != 0L))
                   this.curChar = this.input_stream.BeginToken();
               } catch (IOException e1) {
                 break label5: } case 1:
             case 2:
             case 3: }  this.jjmatchedKind = 2147483647;
           this.jjmatchedPos = 0;
           curPos = jjMoveStringLiteralDfa0_0();
           break label207:
 
           this.jjmatchedKind = 2147483647;
           this.jjmatchedPos = 0;
           curPos = jjMoveStringLiteralDfa0_1();
           break label207:
 
           this.jjmatchedKind = 2147483647;
           this.jjmatchedPos = 0;
           curPos = jjMoveStringLiteralDfa0_2();
           break label207:
 
           this.jjmatchedKind = 2147483647;
           this.jjmatchedPos = 0;
           curPos = jjMoveStringLiteralDfa0_3();
 
           label207: if (this.jjmatchedKind == 2147483647)
             break label411;
           if (this.jjmatchedPos + 1 < curPos)
             this.input_stream.backup(curPos - this.jjmatchedPos - 1);
           if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
           {
             matchedToken = jjFillToken();
             TokenLexicalActions(matchedToken);
             if (jjnewLexState[this.jjmatchedKind] != -1)
               this.curLexState = jjnewLexState[this.jjmatchedKind];
             return matchedToken;
           }
           if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) == 0L) break label358;
         }
         while (jjnewLexState[this.jjmatchedKind] == -1);
         this.curLexState = jjnewLexState[this.jjmatchedKind];
       }
 
       label358: MoreLexicalActions();
       if (jjnewLexState[this.jjmatchedKind] != -1)
         this.curLexState = jjnewLexState[this.jjmatchedKind];
       curPos = 0;
       this.jjmatchedKind = 2147483647;
       try {
         this.curChar = this.input_stream.readChar();
       }
       catch (IOException error_line)
       {
         label411: int error_line = this.input_stream.getEndLine();
         int error_column = this.input_stream.getEndColumn();
         String error_after = null;
         boolean EOFSeen = false;
         try { this.input_stream.readChar(); this.input_stream.backup(1);
         } catch (IOException e1) {
           EOFSeen = true;
           error_after = (curPos <= 1) ? "" : this.input_stream.GetImage();
           if ((this.curChar != '\n') && (this.curChar != '\r')) break label506;
           ++error_line;
           error_column = 0; }
         break label509:
 
         label506: ++error_column;
 
         if (!(EOFSeen)) {
           label509: this.input_stream.backup(1);
           error_after = (curPos <= 1) ? "" : this.input_stream.GetImage();
         }
         throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
       }
     }
   }
 
   void MoreLexicalActions()
   {
     this.jjimageLen += ++this.jjmatchedPos;
     switch (this.jjmatchedKind)
     {
     case 33:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
       this.jjimageLen = 0;
       this.stringBuffer = new StringBuffer();
       break;
     case 34:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
       this.jjimageLen = 0;
       this.stringBuffer = new StringBuffer();
       break;
     case 35:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
       this.jjimageLen = 0;
       this.charValue = escapeChar(); this.stringBuffer.append(this.charValue);
       break;
     case 36:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
       this.jjimageLen = 0;
       this.charValue = this.image.charAt(this.image.length() - 1); this.stringBuffer.append(this.charValue);
       break;
     case 38:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
       this.jjimageLen = 0;
       this.charValue = escapeChar();
       break;
     case 39:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
       this.jjimageLen = 0;
       this.charValue = this.image.charAt(this.image.length() - 1);
       break;
     case 41:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
       this.jjimageLen = 0;
       this.stringBuffer.append(escapeChar());
       break;
     case 42:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
       this.jjimageLen = 0;
       this.stringBuffer.append(this.image.charAt(this.image.length() - 1));
     case 37:
     case 40:
     }
   }
 
   void TokenLexicalActions(Token matchedToken)
   {
     switch (this.jjmatchedKind)
     {
     case 37:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + ++this.jjmatchedPos));
       if (this.stringBuffer.length() == 1)
       {
         this.literalValue = new String("" + this.charValue);
         return; }
       this.literalValue = new String(this.stringBuffer);
 
       break;
     case 40:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + ++this.jjmatchedPos));
       this.literalValue = new Character(this.charValue);
       break;
     case 43:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + ++this.jjmatchedPos));
       this.literalValue = new String(this.stringBuffer);
       break;
     case 44:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + ++this.jjmatchedPos));
       this.literalValue = makeInt();
 
       break;
     case 45:
       if (this.image == null)
         this.image = new StringBuffer();
       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + ++this.jjmatchedPos));
       this.literalValue = makeFloat();
     case 38:
     case 39:
     case 41:
     case 42:
     }
   }
 }