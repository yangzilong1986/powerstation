 package com.hisun.hiexpression;
 
 import org.apache.commons.lang.StringUtils;
 
 class HiCmpMap
 {
   static final int LT = 1;
   static final int LE = 2;
   static final int EQ = 3;
   static final int NE = 4;
   static final int GT = 5;
   static final int GE = 6;
   static final int ER = -1;
 
   static int convert(String op)
   {
     if ((StringUtils.equals(op, "1")) || (StringUtils.equalsIgnoreCase(op, "lt")))
     {
       return 1; }
     if ((StringUtils.equals(op, "2")) || (StringUtils.equalsIgnoreCase(op, "le")))
     {
       return 2; }
     if ((StringUtils.equals(op, "3")) || (StringUtils.equalsIgnoreCase(op, "eq")))
     {
       return 3; }
     if ((StringUtils.equals(op, "4")) || (StringUtils.equalsIgnoreCase(op, "ne")))
     {
       return 4; }
     if ((StringUtils.equals(op, "5")) || (StringUtils.equalsIgnoreCase(op, "ge")))
     {
       return 6; }
     if ((StringUtils.equals(op, "6")) || (StringUtils.equalsIgnoreCase(op, "gt")))
     {
       return 5;
     }
     return -1;
   }
 }