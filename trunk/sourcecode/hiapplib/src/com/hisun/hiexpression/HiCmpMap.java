/*      */ package com.hisun.hiexpression;
/*      */ 
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ 
/*      */ class HiCmpMap
/*      */ {
/*      */   static final int LT = 1;
/*      */   static final int LE = 2;
/*      */   static final int EQ = 3;
/*      */   static final int NE = 4;
/*      */   static final int GT = 5;
/*      */   static final int GE = 6;
/*      */   static final int ER = -1;
/*      */ 
/*      */   static int convert(String op)
/*      */   {
/* 4499 */     if ((StringUtils.equals(op, "1")) || (StringUtils.equalsIgnoreCase(op, "lt")))
/*      */     {
/* 4501 */       return 1; }
/* 4502 */     if ((StringUtils.equals(op, "2")) || (StringUtils.equalsIgnoreCase(op, "le")))
/*      */     {
/* 4504 */       return 2; }
/* 4505 */     if ((StringUtils.equals(op, "3")) || (StringUtils.equalsIgnoreCase(op, "eq")))
/*      */     {
/* 4507 */       return 3; }
/* 4508 */     if ((StringUtils.equals(op, "4")) || (StringUtils.equalsIgnoreCase(op, "ne")))
/*      */     {
/* 4510 */       return 4; }
/* 4511 */     if ((StringUtils.equals(op, "5")) || (StringUtils.equalsIgnoreCase(op, "ge")))
/*      */     {
/* 4513 */       return 6; }
/* 4514 */     if ((StringUtils.equals(op, "6")) || (StringUtils.equalsIgnoreCase(op, "gt")))
/*      */     {
/* 4516 */       return 5;
/*      */     }
/* 4518 */     return -1;
/*      */   }
/*      */ }