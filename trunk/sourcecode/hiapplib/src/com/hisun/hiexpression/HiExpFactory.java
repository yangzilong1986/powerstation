/*     */ package com.hisun.hiexpression;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiExpFactory
/*     */ {
/*   9 */   private static Logger log = HiLog.getLogger("expression.trc");
/*     */ 
/*     */   public static HiExpression createExp(String exp)
/*     */   {
/*  13 */     return new HiExpression(preLex(exp));
/*     */   }
/*     */ 
/*     */   public static HiExpression createIfExp(String exp) {
/*  17 */     return new HiExpression(preIfLex1(exp));
/*     */   }
/*     */ 
/*     */   public static String preprocess1(String exp)
/*     */   {
/*  22 */     return null;
/*     */   }
/*     */ 
/*     */   public static String preLex2(String exp) {
/*  26 */     int j = 0; int k = 0;
/*  27 */     boolean function_flag = false;
/*  28 */     int idx1 = 0; int idx2 = 0;
/*  29 */     int pos1 = -1; int pos2 = 0; int pos3 = 0;
/*     */ 
/*  31 */     while ((pos1 = exp.indexOf(41, pos1 + 1)) != -1)
/*     */     {
/*  34 */       if ((pos2 = exp.lastIndexOf(40, pos1)) == -1) {
/*     */         break;
/*     */       }
/*  37 */       if ((pos3 = exp.lastIndexOf(40, pos2 - 1)) == -1);
/*  38 */       pos3 = 0;
/*     */     }
/*     */ 
/*  41 */     return null;
/*     */   }
/*     */ 
/*     */   public static String preLex0(String exp)
/*     */   {
/*  46 */     return null;
/*     */   }
/*     */ 
/*     */   public static String preIfLex1(String exp)
/*     */   {
/*     */     int j;
/*     */     int k;
/*     */     boolean function_flag;
/*     */     int i;
/*     */     String s;
/*  50 */     StringBuffer result = new StringBuffer();
/*  51 */     if (log.isDebugEnabled()) {
/*  52 */       log.debug("prelex:[" + exp + "]");
/*     */     }
/*  54 */     int pos = exp.indexOf("(");
/*  55 */     String value = null;
/*  56 */     if (pos != -1) {
/*  57 */       value = exp.substring(0, pos);
/*     */     }
/*  59 */     if (StringUtils.equalsIgnoreCase(value, "and")) {
/*  60 */       j = 0; k = 0;
/*  61 */       k = pos + 1;
/*  62 */       function_flag = false;
/*  63 */       result.append(exp.substring(0, pos + 1));
/*  64 */       for (i = pos + 1; i < exp.length(); ++i)
/*     */       {
/*  66 */         if (exp.charAt(i) == '(') {
/*  67 */           function_flag = true;
/*  68 */           ++j;
/*     */         }
/*     */ 
/*  71 */         if (exp.charAt(i) == ')') {
/*  72 */           --j;
/*     */         }
/*     */ 
/*  75 */         if ((j == 0) && (function_flag)) {
/*  76 */           function_flag = false;
/*  77 */           s = preIfLex1(exp.substring(k, i + 1));
/*  78 */           result.append(s);
/*  79 */           for (; i < exp.length(); ++i) {
/*  80 */             if (exp.charAt(i) == ',') {
/*     */               break;
/*     */             }
/*     */           }
/*  84 */           k = i + 1;
/*  85 */           if (i < exp.length())
/*  86 */             result.append(",");
/*     */         }
/*     */       }
/*  89 */       result.append(")");
/*  90 */     } else if (StringUtils.equalsIgnoreCase(value, "or")) {
/*  91 */       j = 0; k = 0;
/*  92 */       k = pos + 1;
/*  93 */       function_flag = false;
/*  94 */       result.append(exp.substring(0, pos + 1));
/*  95 */       for (i = pos + 1; i < exp.length(); ++i) {
/*  96 */         if (exp.charAt(i) == '(') {
/*  97 */           function_flag = true;
/*  98 */           ++j;
/*     */         }
/*     */ 
/* 101 */         if (exp.charAt(i) == ')') {
/* 102 */           --j;
/*     */         }
/*     */ 
/* 105 */         if ((j == 0) && (function_flag)) {
/* 106 */           function_flag = false;
/* 107 */           s = preIfLex1(exp.substring(k, i + 1));
/* 108 */           for (; i < exp.length(); ++i) {
/* 109 */             if (exp.charAt(i) == ',')
/*     */               break;
/*     */           }
/* 112 */           k = i + 1;
/* 113 */           result.append(s);
/* 114 */           if (i < exp.length())
/* 115 */             result.append(",");
/*     */         }
/*     */       }
/* 118 */       result.append(")");
/*     */     } else {
/* 120 */       result.append(preIfLex(exp));
/*     */     }
/* 122 */     if (log.isDebugEnabled()) {
/* 123 */       log.debug("prelex:[" + exp + "]:[" + result + "]");
/*     */     }
/* 125 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public static String preIfLex(String exp)
/*     */   {
/*     */     String tmp;
/*     */     int idx;
/* 134 */     StringBuffer result = new StringBuffer(exp.length());
/*     */ 
/* 138 */     if ((idx = exp.indexOf("!=")) != -1) {
/* 139 */       tmp = preLex(exp.substring(0, idx).trim());
/* 140 */       result.append(tmp);
/* 141 */       result.append('!');
/* 142 */       result.append('=');
/* 143 */       tmp = preLex(exp.substring(idx + 2).trim());
/* 144 */       result.append(tmp);
/* 145 */     } else if ((idx = exp.indexOf("<=")) != -1)
/*     */     {
/* 147 */       tmp = preLex(exp.substring(0, idx).trim());
/* 148 */       result.append(tmp);
/* 149 */       result.append('<');
/* 150 */       result.append('=');
/* 151 */       tmp = preLex(exp.substring(idx + 2).trim());
/* 152 */       result.append(tmp);
/* 153 */     } else if ((idx = exp.indexOf(">=")) != -1)
/*     */     {
/* 155 */       tmp = preLex(exp.substring(0, idx).trim());
/* 156 */       result.append(tmp);
/* 157 */       result.append('>');
/* 158 */       result.append('=');
/* 159 */       tmp = preLex(exp.substring(idx + 2).trim());
/* 160 */       result.append(tmp);
/* 161 */     } else if ((idx = exp.indexOf(61)) != -1) {
/* 162 */       tmp = preLex(exp.substring(0, idx).trim());
/* 163 */       result.append(tmp);
/* 164 */       result.append('=');
/* 165 */       result.append('=');
/* 166 */       tmp = preLex(exp.substring(idx + 1).trim());
/* 167 */       result.append(tmp);
/* 168 */     } else if ((idx = exp.indexOf("<")) != -1)
/*     */     {
/* 170 */       tmp = preLex(exp.substring(0, idx).trim());
/* 171 */       result.append(tmp);
/* 172 */       result.append('<');
/* 173 */       tmp = preLex(exp.substring(idx + 1).trim());
/* 174 */       result.append(tmp);
/* 175 */     } else if ((idx = exp.indexOf(">")) != -1)
/*     */     {
/* 177 */       tmp = preLex(exp.substring(0, idx).trim());
/* 178 */       result.append(tmp);
/* 179 */       result.append('>');
/* 180 */       tmp = preLex(exp.substring(idx + 1).trim());
/* 181 */       result.append(tmp);
/*     */     } else {
/* 183 */       tmp = preLex(exp);
/* 184 */       result.append(tmp);
/*     */     }
/* 186 */     if (log.isDebugEnabled())
/* 187 */       log.debug("prelex:[" + exp + "]:[" + result + "]");
/* 188 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public static String preLex(String exp)
/*     */   {
/* 199 */     int state = 0;
/*     */ 
/* 201 */     int lastIndex = 0;
/* 202 */     int count = 0;
/* 203 */     StringBuffer result = new StringBuffer(exp.length());
/* 204 */     boolean isFunction = false;
/*     */ 
/* 207 */     int i = 0; if (i < exp.length()) {
/* 208 */       if (Character.isWhitespace(exp.charAt(i)))
/*     */       {
/* 211 */         switch (exp.charAt(i))
/*     */         {
/*     */         case '!':
/*     */         case '<':
/*     */         case '=':
/*     */         case '>':
/* 216 */           result.append("\"");
/* 217 */           result.append(exp);
/* 218 */           result.append("\"");
/* 219 */           return result.toString();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 227 */     for (int index = 0; index < exp.length(); ++index)
/*     */     {
/*     */       int j;
/* 228 */       switch (state)
/*     */       {
/*     */       case 0:
/* 231 */         if (isSpecialChar(exp, index, '"')) {
/* 232 */           result.append(exp.charAt(index));
/* 233 */           state = 5;
/*     */         }
/* 238 */         else if (isVar(exp, index)) {
/* 239 */           --index;
/* 240 */           state = 1;
/*     */         }
/* 245 */         else if (isSpecialChar(exp, index, '(')) {
/* 246 */           isFunction = true;
/* 247 */           state = 2;
/* 248 */           --index;
/* 249 */           ++count;
/*     */         }
/* 253 */         else if (isSeperater(exp, index, isFunction)) {
/* 254 */           if (isSpecialChar(exp, index, ')')) {
/* 255 */             --count;
/*     */           }
/* 257 */           --index;
/* 258 */           state = 3;
/*     */         }
/* 262 */         else if (index == exp.length() - 1) {
/* 263 */           --index;
/* 264 */           state = 4; }
/* 265 */         break;
/*     */       case 1:
/* 270 */         if (isSeperater(exp, index, isFunction)) {
/* 271 */           --index;
/* 272 */           state = 0;
/*     */         }
/*     */         else {
/* 275 */           result.append(exp.charAt(index));
/* 276 */           lastIndex = index + 1; }
/* 277 */         break;
/*     */       case 2:
/* 280 */         result.append(exp.substring(lastIndex, index + 1));
/* 281 */         lastIndex = index + 1;
/* 282 */         state = 0;
/* 283 */         break;
/*     */       case 3:
/* 286 */         if (lastIndex != index) {
/* 287 */           result.append('"');
/* 288 */           for (j = lastIndex; j < index; ++j)
/*     */           {
/* 291 */             if ((exp.charAt(j) == '\'') || (exp.charAt(j) == '"'))
/* 292 */               result.append('\\');
/* 293 */             result.append(exp.charAt(j));
/*     */           }
/* 295 */           result.append('"');
/*     */         }
/* 297 */         result.append(exp.charAt(index));
/* 298 */         lastIndex = index + 1;
/* 299 */         state = 0;
/* 300 */         break;
/*     */       case 4:
/* 303 */         if ((isFunction) && (count == 0))
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/* 309 */         if ((lastIndex != index) || (index == 0)) {
/* 310 */           result.append('"');
/* 311 */           for (j = lastIndex; j < index + 1; ++j) {
/* 312 */             if (exp.charAt(j) == '\\')
/*     */               continue;
/* 314 */             if ((exp.charAt(j) == '\'') || (exp.charAt(j) == '"'))
/* 315 */               result.append('\\');
/* 316 */             result.append(exp.charAt(j));
/*     */           }
/* 318 */           result.append('"');
/* 319 */           lastIndex = index + 1; }
/* 320 */         break;
/*     */       case 5:
/* 323 */         result.append(exp.charAt(index));
/* 324 */         if (isSpecialChar(exp, index, '"')) {
/* 325 */           state = 0;
/*     */         }
/* 327 */         lastIndex = index + 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 333 */     if (log.isDebugEnabled())
/* 334 */       log.debug("prelex:[" + exp + "]:[" + result + "]");
/* 335 */     return result.toString();
/*     */   }
/*     */ 
/*     */   private static boolean isVar(String exp, int idx) {
/* 339 */     boolean vared = false;
/*     */ 
/* 341 */     switch (exp.charAt(idx))
/*     */     {
/*     */     case '#':
/*     */     case '$':
/*     */     case '%':
/*     */     case '@':
/*     */     case '~':
/* 347 */       vared = true;
/*     */     }
/*     */ 
/* 351 */     if (vared)
/*     */     {
/* 353 */       return ((idx != 0) && (exp.charAt(idx - 1) == '\\'));
/*     */     }
/*     */ 
/* 357 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean isSeperater(String exp, int idx, boolean isFunction)
/*     */   {
/* 362 */     boolean isSeperatered = false;
/*     */ 
/* 364 */     switch (exp.charAt(idx))
/*     */     {
/*     */     case ',':
/* 366 */       if (isFunction)
/* 367 */         isSeperatered = true; break;
/*     */     case ')':
/* 370 */       isSeperatered = true;
/*     */     }
/*     */ 
/* 376 */     if (isSeperatered) {
/* 377 */       if ((idx == 0) || (exp.charAt(idx - 1) != '\\')) {
/* 378 */         return isSeperatered;
/*     */       }
/* 380 */       return false;
/*     */     }
/*     */ 
/* 384 */     return isSeperatered;
/*     */   }
/*     */ 
/*     */   private static boolean isSpecialChar(String exp, int idx, char c) {
/* 388 */     if (exp.charAt(idx) == c)
/*     */     {
/* 390 */       return ((idx != 0) && (exp.charAt(idx - 1) == '\\'));
/*     */     }
/*     */ 
/* 394 */     return false;
/*     */   }
/*     */ }