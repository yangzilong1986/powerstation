/*      */ package com.hisun.hiexpression;
/*      */ 
/*      */ import com.hisun.exception.HiException;
/*      */ import com.hisun.message.HiETF;
/*      */ import com.hisun.message.HiMessage;
/*      */ import com.hisun.message.HiMessageContext;
/*      */ import com.hisun.util.HiByteBuffer;
/*      */ import com.hisun.util.HiDateUtils;
/*      */ import com.hisun.util.HiICSProperty;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileReader;
/*      */ import java.io.IOException;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.math.BigDecimal;
/*      */ import java.net.URLDecoder;
/*      */ import java.net.URLEncoder;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.ParseException;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.jar.JarFile;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.commons.codec.DecoderException;
/*      */ import org.apache.commons.codec.binary.Hex;
/*      */ import org.apache.commons.lang.CharUtils;
/*      */ import org.apache.commons.lang.RandomStringUtils;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.lang.SystemUtils;
/*      */ import org.apache.commons.lang.math.NumberUtils;
/*      */ import org.apache.commons.lang.time.DateFormatUtils;
/*      */ import org.apache.commons.lang.time.DateUtils;
/*      */ 
/*      */ public class HiExpBasicFunctions
/*      */ {
/*   45 */   private static String DIGIT_UPPER = "零壹贰叁肆伍陆柒捌玖";
/*      */ 
/*   47 */   private static String AMT_UPPER = "分角元拾佰仟万拾佰仟亿拾佰仟万拾佰仟亿";
/*      */ 
/*   49 */   private static int[] ULEAD_MONTH_DAYS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
/*      */ 
/*   52 */   private static int[] LEAD_MONTH_DAYS = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
/*      */ 
/*   55 */   private static String EBCD_POSITIVE = "{ABCDEFGHI";
/*      */ 
/*   57 */   private static String EBCD_NEGATIVE = "}JKLMNOPQR";
/*      */   private static final String FALSE = "0";
/*      */   private static final String TRUE = "1";
/*   63 */   private static ConcurrentHashMap memoryTD = new ConcurrentHashMap();
/*      */ 
/*      */   public static String STRCAT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*   77 */     if (args.length < 2)
/*   78 */       throw new HiException("215110", "STRCAT");
/*   79 */     StringBuffer result = new StringBuffer();
/*   80 */     for (int i = 0; i < args.length; ++i) {
/*   81 */       result.append(args[i]);
/*      */     }
/*   83 */     return result.toString();
/*      */   }
/*      */ 
/*      */   public static String STRNCAT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  100 */     if (args.length != 3)
/*  101 */       throw new HiException("215110", "STRNCAT");
/*  102 */     return args[0] + StringUtils.substring(args[1], 0, NumberUtils.toInt(StringUtils.trim(args[2])));
/*      */   }
/*      */ 
/*      */   public static String STRCMP(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  123 */     if (args.length != 2) {
/*  124 */       throw new HiException("215110", "STRCMP");
/*      */     }
/*  126 */     return String.valueOf(args[0].compareTo(args[1]));
/*      */   }
/*      */ 
/*      */   public static String SUBCMP(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  151 */     if (args.length != 4)
/*  152 */       throw new HiException("215110", "SUBCMP");
/*  153 */     int offset = NumberUtils.toInt(StringUtils.trim(args[1])) - 1;
/*  154 */     if (offset < 0)
/*  155 */       offset = 0;
/*  156 */     if (offset > args[0].length()) {
/*  157 */       offset = args[0].length();
/*      */     }
/*  159 */     int length = NumberUtils.toInt(StringUtils.trim(args[2]));
/*  160 */     if (length < 0)
/*  161 */       length = 0;
/*  162 */     if (offset + length > args[0].length()) {
/*  163 */       length = args[0].length() - offset;
/*      */     }
/*  165 */     int ret = StringUtils.substring(args[0], offset, length).compareTo(args[3]);
/*      */ 
/*  167 */     return String.valueOf(ret);
/*      */   }
/*      */ 
/*      */   public static String LONGPOWER(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  183 */     if (args.length < 2)
/*  184 */       throw new HiException("215110", "LONGPOWER");
/*  185 */     long result = NumberUtils.toLong(StringUtils.trim(args[0]));
/*  186 */     for (int i = 1; i < args.length; ++i) {
/*  187 */       result = ()Math.pow(result, NumberUtils.toInt(StringUtils.trim(args[i])));
/*      */     }
/*      */ 
/*  190 */     return String.valueOf(result);
/*      */   }
/*      */ 
/*      */   public static String SHORTPOWER(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  206 */     if (args.length < 2)
/*  207 */       throw new HiException("215110", "SHORTPOWER");
/*  208 */     long result = NumberUtils.toLong(StringUtils.trim(args[0]));
/*  209 */     for (int i = 1; i < args.length; ++i) {
/*  210 */       result = ()Math.pow(result, NumberUtils.toInt(StringUtils.trim(args[i])));
/*      */     }
/*      */ 
/*  213 */     return String.valueOf(result);
/*      */   }
/*      */ 
/*      */   public static String INTCMP(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*      */     int i;
/*  240 */     if (args.length < 3) {
/*  241 */       throw new HiException("215110", "INTCMP");
/*      */     }
/*  243 */     long result = NumberUtils.toLong(StringUtils.trim(args[0]));
/*  244 */     int op = HiCmpMap.convert(StringUtils.trim(args[1]));
/*  245 */     switch (op)
/*      */     {
/*      */     case 1:
/*  247 */       for (i = 2; i < args.length; ++i) {
/*  248 */         if (result >= NumberUtils.toLong(StringUtils.trim(args[i])))
/*  249 */           return "0";
/*      */       }
/*  251 */       return "1";
/*      */     case 2:
/*  253 */       for (i = 2; i < args.length; ++i) {
/*  254 */         if (result > NumberUtils.toLong(StringUtils.trim(args[i])))
/*  255 */           return "0";
/*      */       }
/*  257 */       return "1";
/*      */     case 3:
/*  259 */       for (i = 2; i < args.length; ++i) {
/*  260 */         if (result != NumberUtils.toLong(StringUtils.trim(args[i])))
/*  261 */           return "0";
/*      */       }
/*  263 */       return "1";
/*      */     case 4:
/*  265 */       for (i = 2; i < args.length; ++i) {
/*  266 */         if (result == NumberUtils.toLong(StringUtils.trim(args[i])))
/*  267 */           return "0";
/*      */       }
/*  269 */       return "1";
/*      */     case 5:
/*  271 */       for (i = 2; i < args.length; ++i) {
/*  272 */         if (result <= NumberUtils.toLong(StringUtils.trim(args[i])))
/*  273 */           return "0";
/*      */       }
/*  275 */       return "1";
/*      */     case 6:
/*  277 */       for (i = 2; i < args.length; ++i) {
/*  278 */         if (result < NumberUtils.toLong(StringUtils.trim(args[i]))) {
/*  279 */           return "0";
/*      */         }
/*      */       }
/*  282 */       return "1";
/*      */     }
/*  284 */     throw new HiException("215111", "INTCMP", "op");
/*      */   }
/*      */ 
/*      */   public static String DOUBLECMP(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*      */     int ret;
/*      */     int i;
/*  313 */     if (args.length < 3) {
/*  314 */       throw new HiException("215110", "DOUBLECMP");
/*      */     }
/*  316 */     double result = NumberUtils.toDouble(StringUtils.trim(args[0]));
/*  317 */     int op = HiCmpMap.convert(StringUtils.trim(args[1]));
/*      */ 
/*  320 */     switch (op)
/*      */     {
/*      */     case 1:
/*  322 */       for (i = 2; i < args.length; ++i) {
/*  323 */         ret = NumberUtils.compare(result, NumberUtils.toDouble(StringUtils.trim(args[i])));
/*      */ 
/*  325 */         if ((ret == 0) || (ret == 1))
/*  326 */           return "0";
/*      */       }
/*  328 */       return "1";
/*      */     case 2:
/*  330 */       for (i = 2; i < args.length; ++i) {
/*  331 */         ret = NumberUtils.compare(result, NumberUtils.toDouble(StringUtils.trim(args[i])));
/*      */ 
/*  333 */         if (ret == 1)
/*  334 */           return "0";
/*      */       }
/*  336 */       return "1";
/*      */     case 3:
/*  338 */       for (i = 2; i < args.length; ++i) {
/*  339 */         ret = NumberUtils.compare(result, NumberUtils.toDouble(StringUtils.trim(args[i])));
/*      */ 
/*  341 */         if (ret != 0)
/*  342 */           return "0";
/*      */       }
/*  344 */       return "1";
/*      */     case 4:
/*  346 */       for (i = 2; i < args.length; ++i) {
/*  347 */         ret = NumberUtils.compare(result, NumberUtils.toDouble(StringUtils.trim(args[i])));
/*      */ 
/*  349 */         if (ret == 0)
/*  350 */           return "0";
/*      */       }
/*  352 */       return "1";
/*      */     case 5:
/*  354 */       for (i = 2; i < args.length; ++i) {
/*  355 */         ret = NumberUtils.compare(result, NumberUtils.toDouble(StringUtils.trim(args[i])));
/*      */ 
/*  357 */         if ((ret == -1) || (ret == 0))
/*  358 */           return "0";
/*      */       }
/*  360 */       return "1";
/*      */     case 6:
/*  362 */       for (i = 2; i < args.length; ++i) {
/*  363 */         ret = NumberUtils.compare(result, NumberUtils.toDouble(StringUtils.trim(args[i])));
/*      */ 
/*  365 */         if (ret == -1) {
/*  366 */           return "0";
/*      */         }
/*      */       }
/*  369 */       return "1";
/*      */     }
/*  371 */     throw new HiException("215111", "DOUBLECMP", "op");
/*      */   }
/*      */ 
/*      */   public static String IS_NOEQUAL_STRING(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  384 */     if (args.length < 2) {
/*  385 */       throw new HiException("215110", "IS_NOEQUAL_STRING");
/*      */     }
/*      */ 
/*  388 */     for (int i = 0; i < args.length - 1; ++i) {
/*  389 */       if (StringUtils.equals(args[0], args[(i + 1)])) {
/*  390 */         return "0";
/*      */       }
/*      */     }
/*  393 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String IS_EQUAL_INT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  405 */     if (args.length != 2) {
/*  406 */       throw new HiException("215110", "IS_EQUAL_INT");
/*      */     }
/*      */ 
/*  409 */     BigDecimal l1 = new BigDecimal(StringUtils.trim(args[0]));
/*  410 */     BigDecimal l2 = new BigDecimal(StringUtils.trim(args[1]));
/*  411 */     return ((l1.compareTo(l2) == 0) ? "1" : "0");
/*      */   }
/*      */ 
/*      */   public static String IS_EQUAL_DOUBLE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  423 */     if (args.length != 2) {
/*  424 */       throw new HiException("215110", "IS_EQUAL_DOUBLE");
/*      */     }
/*      */ 
/*  427 */     BigDecimal l1 = new BigDecimal(StringUtils.trim(args[0]));
/*  428 */     BigDecimal l2 = new BigDecimal(StringUtils.trim(args[1]));
/*  429 */     return ((l1.compareTo(l2) == 0) ? "1" : "0");
/*      */   }
/*      */ 
/*      */   public static String IS_EQUAL_STRING(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  441 */     if (args.length < 2) {
/*  442 */       throw new HiException("215110", "IS_EQUAL_STRING");
/*      */     }
/*      */ 
/*  445 */     for (int i = 0; i < args.length - 1; ++i) {
/*  446 */       if (StringUtils.equals(args[0], args[(i + 1)])) {
/*  447 */         return "1";
/*      */       }
/*      */     }
/*  450 */     return "0";
/*      */   }
/*      */ 
/*      */   public static String ISNULL(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  463 */     if (args.length == 0) {
/*  464 */       throw new HiException("215110", "ISNULL");
/*      */     }
/*  466 */     for (int i = 0; i < args.length; ++i) {
/*  467 */       if (!(StringUtils.isEmpty(args[i])))
/*  468 */         return "0";
/*      */     }
/*  470 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String ISCHIN(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  485 */     if (args.length == 0) {
/*  486 */       throw new HiException("215110", "ISCHIN");
/*      */     }
/*      */ 
/*  489 */     char[] buf = args[0].toCharArray();
/*  490 */     int sz = args[0].length();
/*  491 */     for (int i = 0; i < sz; ++i) {
/*  492 */       if (CharUtils.isAscii(buf[i])) {
/*  493 */         return "0";
/*      */       }
/*      */     }
/*  496 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String STRLEN(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  509 */     if (args.length != 1)
/*  510 */       throw new HiException("215110", "STRLEN");
/*  511 */     return String.valueOf(args[0].getBytes().length);
/*      */   }
/*      */ 
/*      */   public static String SUBSTR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  531 */     if (args.length != 3) {
/*  532 */       throw new HiException("215110", "SUBSTR");
/*      */     }
/*  534 */     int beginIndex = NumberUtils.toInt(StringUtils.trim(args[1])) - 1;
/*  535 */     byte[] bytes = args[0].getBytes();
/*  536 */     if (beginIndex < 0) {
/*  537 */       beginIndex = 0;
/*      */     }
/*  539 */     if (beginIndex > bytes.length) {
/*  540 */       beginIndex = bytes.length;
/*      */     }
/*  542 */     int length = NumberUtils.toInt(StringUtils.trim(args[2]));
/*  543 */     if (length < 0) {
/*  544 */       length = 0;
/*      */     }
/*  546 */     if (beginIndex + length > bytes.length) {
/*  547 */       length = bytes.length - beginIndex;
/*      */     }
/*  549 */     return new String(bytes, beginIndex, length);
/*      */   }
/*      */ 
/*      */   public static String SUBRIGHT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  564 */     if (args.length != 2)
/*  565 */       throw new HiException("215110", "SUBRIGHT");
/*  566 */     int length = NumberUtils.toInt(StringUtils.trim(args[1]));
/*  567 */     if (length < 0)
/*  568 */       length = 0;
/*  569 */     byte[] bytes = args[0].getBytes();
/*  570 */     if (length > bytes.length)
/*  571 */       length = bytes.length;
/*  572 */     return new String(bytes, bytes.length - length, length);
/*      */   }
/*      */ 
/*      */   public static String ISNUMBER(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  585 */     if (args.length == 0) {
/*  586 */       throw new HiException("215110", "ISNUMBER");
/*      */     }
/*  588 */     for (int i = 0; i < args.length; ++i) {
/*  589 */       if (!(StringUtils.isNumeric(args[i])))
/*  590 */         return "0";
/*      */     }
/*  592 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String GETCHARPOS(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  610 */     if (args.length != 3)
/*  611 */       throw new HiException("215110", "GETCHARPOS");
/*  612 */     if ((args[1] == null) || (args[1] == null) || (args[2] == null)) {
/*  613 */       return "-1";
/*      */     }
/*  615 */     int len = NumberUtils.toInt(StringUtils.trim(args[1]));
/*  616 */     if (len == 0) {
/*  617 */       return "0";
/*      */     }
/*  619 */     for (int i = 0; (i < len) && (i < args[0].length()); ++i) {
/*  620 */       if (args[0].charAt(i) == args[2].charAt(0))
/*  621 */         return String.valueOf(i);
/*      */     }
/*  623 */     return "-1";
/*      */   }
/*      */ 
/*      */   public static String GETCHARPOSFROM(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  653 */     if (args.length != 3) {
/*  654 */       throw new HiException("215110", "GETCHARPOSFROM");
/*      */     }
/*  656 */     if ((args[0] == null) || (args[1] == null) || (args[2] == null)) {
/*  657 */       return "-1";
/*      */     }
/*  659 */     int ret = StringUtils.indexOf(args[0], args[2], NumberUtils.toInt(StringUtils.trim(args[1])));
/*      */ 
/*  661 */     if (ret != -1) {
/*  662 */       ++ret;
/*      */     }
/*  664 */     return String.valueOf(ret);
/*      */   }
/*      */ 
/*      */   public static String GETSTRPOS(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  681 */     if (args.length != 2)
/*  682 */       throw new HiException("215110", "GETSTRPOS");
/*  683 */     int idx = StringUtils.indexOf(args[0], args[1]);
/*  684 */     if (idx == -1) {
/*  685 */       return "-1";
/*      */     }
/*  687 */     return String.valueOf(idx + 1);
/*      */   }
/*      */ 
/*      */   public static String DELSPACE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  705 */     if (args.length != 2) {
/*  706 */       throw new HiException("215110", "DELSPACE");
/*      */     }
/*  708 */     if (StringUtils.equalsIgnoreCase("left", args[1]))
/*  709 */       return StringUtils.stripStart(args[0], " ");
/*  710 */     if (StringUtils.equalsIgnoreCase("right", args[1]))
/*  711 */       return StringUtils.stripEnd(args[0], " ");
/*  712 */     if (StringUtils.equalsIgnoreCase("both", args[1]))
/*  713 */       return StringUtils.strip(args[0]);
/*  714 */     if (StringUtils.equalsIgnoreCase("all", args[1])) {
/*  715 */       return StringUtils.replaceChars(args[0], " \t\f\r\n　", "");
/*      */     }
/*  717 */     return args[0];
/*      */   }
/*      */ 
/*      */   public static String DELBOTHSPACE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  732 */     if (args.length != 1) {
/*  733 */       throw new HiException("215110", "DELBOTHSPACE");
/*      */     }
/*  735 */     return StringUtils.strip(args[0]);
/*      */   }
/*      */ 
/*      */   public static String DELRIGHTSPACE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  749 */     if (args.length != 1) {
/*  750 */       throw new HiException("215110", "DELRIGHTSPACE");
/*      */     }
/*      */ 
/*  753 */     return StringUtils.stripEnd(args[0], " ");
/*      */   }
/*      */ 
/*      */   public static String SPACE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  772 */     if (args.length != 2)
/*  773 */       throw new HiException("215110", "SPACE");
/*  774 */     switch (NumberUtils.toInt(StringUtils.trim(args[1])))
/*      */     {
/*      */     case 0:
/*  776 */       return StringUtils.repeat(" ", NumberUtils.toInt(StringUtils.trim(args[0])));
/*      */     case 1:
/*  779 */       return StringUtils.repeat("　", NumberUtils.toInt(StringUtils.trim(args[0])));
/*      */     }
/*      */ 
/*  784 */     throw new HiException("215111", "SPACE", "flag");
/*      */   }
/*      */ 
/*      */   public static String INTTOSTR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  800 */     if (args.length != 2)
/*  801 */       throw new HiException("215110", "INTTOSTR");
/*  802 */     int length = NumberUtils.toInt(StringUtils.trim(args[1]));
/*      */ 
/*  804 */     return StringUtils.leftPad(StringUtils.trim(args[0]), length, '0');
/*      */   }
/*      */ 
/*      */   public static String ADDCHAR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*      */     byte[] tmps;
/*      */     int i;
/*  821 */     if (args.length != 4)
/*  822 */       throw new HiException("215110", "ADDCHAR");
/*  823 */     if (isByteEnv());
/*  828 */     int length = NumberUtils.toInt(StringUtils.trim(args[1]));
/*  829 */     HiByteBuffer buf = new HiByteBuffer(10);
/*      */ 
/*  832 */     switch (NumberUtils.toInt(StringUtils.trim(args[3])))
/*      */     {
/*      */     case 0:
/*  834 */       tmps = args[0].getBytes();
/*  835 */       for (i = 0; (i < tmps.length) && (i < length); ++i) {
/*  836 */         buf.append((char)tmps[i]);
/*      */       }
/*  838 */       for (; i < length; ++i) {
/*  839 */         buf.append(args[2]);
/*      */       }
/*  841 */       return buf.toString();
/*      */     case 1:
/*  843 */       tmps = args[0].getBytes();
/*  844 */       for (i = 0; i < length - tmps.length; ++i) {
/*  845 */         buf.append(args[2]);
/*      */       }
/*  847 */       for (int j = 0; (j < tmps.length) && (i < length); ++j) {
/*  848 */         buf.append((char)tmps[j]);
/*      */ 
/*  847 */         ++i;
/*      */       }
/*      */ 
/*  850 */       return buf.toString();
/*      */     }
/*      */ 
/*  854 */     throw new HiException("215111", "ADDCHAR", "direction");
/*      */   }
/*      */ 
/*      */   public static String CUTDATALENGTH(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  871 */     if (args.length != 2) {
/*  872 */       throw new HiException("215110", "CUTDATALENGTH");
/*      */     }
/*  874 */     return StringUtils.substring(StringUtils.trim(args[0]), StringUtils.trim(args[0]).length() - NumberUtils.toInt(StringUtils.trim(args[1])));
/*      */   }
/*      */ 
/*      */   public static String TOUPPER(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  889 */     if (args.length != 1) {
/*  890 */       throw new HiException("215110", "TOUPPER");
/*      */     }
/*  892 */     return StringUtils.upperCase(args[0]);
/*      */   }
/*      */ 
/*      */   public static String TOLOWER(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  905 */     if (args.length != 1) {
/*  906 */       throw new HiException("215110", "TOLOWER");
/*      */     }
/*  908 */     return StringUtils.lowerCase(args[0]);
/*      */   }
/*      */ 
/*      */   public static String REPSTR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  925 */     if (args.length != 3) {
/*  926 */       throw new HiException("215110", "REPSTR");
/*      */     }
/*  928 */     return StringUtils.replaceOnce(args[0].trim(), args[1].trim(), args[2].trim());
/*      */   }
/*      */ 
/*      */   public static String REPALLSTR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  947 */     if (args.length != 3) {
/*  948 */       throw new HiException("215110", "REPALLSTR");
/*      */     }
/*  950 */     return StringUtils.replace(args[0], args[1], args[2]);
/*      */   }
/*      */ 
/*      */   public static String DELCTRL(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  963 */     if (args.length != 1) {
/*  964 */       throw new HiException("215110", "DELCTRL");
/*      */     }
/*  966 */     String str = args[0].trim();
/*  967 */     if (StringUtils.isEmpty(str))
/*  968 */       throw new HiException("215111", "DELCTRL", "str");
/*  969 */     char[] buf = str.toCharArray();
/*  970 */     for (int i = 0; i < buf.length; ++i) {
/*  971 */       if (CharUtils.isAsciiControl(buf[i])) {
/*  972 */         str = StringUtils.replace(str, Character.toString(buf[i]), "");
/*      */       }
/*      */     }
/*  975 */     return str;
/*      */   }
/*      */ 
/*      */   public static String REPCTRL(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*  991 */     if (args.length != 2) {
/*  992 */       throw new HiException("215110", "REPCTRL");
/*      */     }
/*  994 */     String str = args[0];
/*  995 */     String ch = args[1];
/*  996 */     char[] buf = str.toCharArray();
/*  997 */     for (int i = 0; i < buf.length; ++i) {
/*  998 */       if (!(CharUtils.isAsciiControl(buf[i]))) {
/*      */         continue;
/*      */       }
/* 1001 */       buf[i] = ch.charAt(0);
/*      */     }
/*      */ 
/* 1004 */     return new String(buf);
/*      */   }
/*      */ 
/*      */   public static String REVERSAL(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1025 */     if (args.length != 1)
/* 1026 */       throw new HiException("215110", "REVERSAL");
/* 1027 */     StringBuffer result = new StringBuffer();
/* 1028 */     for (int i = 0; i < args[0].length(); ++i) {
/* 1029 */       switch (args[0].charAt(i))
/*      */       {
/*      */       case '0':
/* 1031 */         result.append('1');
/* 1032 */         break;
/*      */       case '1':
/* 1034 */         result.append('0');
/* 1035 */         break;
/*      */       default:
/* 1037 */         result.append(args[0].charAt(i));
/*      */       }
/*      */     }
/*      */ 
/* 1041 */     return result.toString();
/*      */   }
/*      */ 
/*      */   public static String LTRIM(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1057 */     if (args.length != 2) {
/* 1058 */       throw new HiException("215110", "LTRIM");
/*      */     }
/* 1060 */     if ((args[0] == null) || (args[1] == null) || (args[0].length() == 0) || (args[1].length() == 0))
/*      */     {
/* 1062 */       return args[0]; }
/* 1063 */     for (int i = 0; i < args[0].length(); ++i) {
/* 1064 */       if (args[0].charAt(i) != args[1].charAt(0)) {
/*      */         break;
/*      */       }
/*      */     }
/*      */ 
/* 1069 */     return args[0].substring(i);
/*      */   }
/*      */ 
/*      */   public static String RTRIM(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1085 */     if (args.length != 2) {
/* 1086 */       throw new HiException("215110", "LTRIM");
/*      */     }
/*      */ 
/* 1089 */     if ((args[0] == null) || (args[1] == null) || (args[0].length() == 0) || (args[1].length() == 0))
/*      */     {
/* 1091 */       return args[0];
/*      */     }
/* 1093 */     for (int i = args[0].length() - 1; i > -1; --i) {
/* 1094 */       if (args[0].charAt(i) != args[1].charAt(0)) {
/*      */         break;
/*      */       }
/*      */     }
/*      */ 
/* 1099 */     return args[0].substring(0, i + 1);
/*      */   }
/*      */ 
/*      */   public static String TRIM(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1114 */     if (args.length != 2) {
/* 1115 */       throw new HiException("215110", "TRIM");
/*      */     }
/* 1117 */     if ((args[0] == null) || (args[1] == null) || (args[0].length() == 0) || (args[1].length() == 0))
/*      */     {
/* 1119 */       return args[0];
/*      */     }
/* 1121 */     if (StringUtils.equals(args[1].trim(), "both"))
/* 1122 */       return StringUtils.trim(args[0]);
/* 1123 */     if (StringUtils.equals(args[1].trim(), "right"))
/* 1124 */       return StringUtils.stripEnd(args[0], "");
/* 1125 */     if (StringUtils.equals(args[1].trim(), "left"))
/* 1126 */       return StringUtils.stripStart(args[0], "");
/* 1127 */     if (StringUtils.equals(args[1].trim(), "all")) {
/* 1128 */       String[] buf3 = args[0].split(" |　|\t|\n|\f");
/* 1129 */       String ret = "";
/* 1130 */       for (int j = 0; j < buf3.length; ++j) {
/* 1131 */         ret = ret + buf3[j];
/*      */       }
/* 1133 */       return ret;
/*      */     }
/*      */ 
/* 1137 */     for (int i = 0; i < args[0].length(); ++i) {
/* 1138 */       if (args[0].charAt(i) != args[1].charAt(0))
/*      */         break;
/*      */     }
/* 1141 */     for (int j = args[0].length() - 1; j > -1; --j) {
/* 1142 */       if (args[0].charAt(j) != args[1].charAt(0))
/*      */         break;
/*      */     }
/* 1145 */     return args[0].substring(i, j + 1);
/*      */   }
/*      */ 
/*      */   public static String NullToEmpty(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1162 */     if (args.length != 1) {
/* 1163 */       throw new HiException("215110", "NullToEmpty");
/*      */     }
/* 1165 */     if (args[0] == null) {
/* 1166 */       return "";
/*      */     }
/* 1168 */     return args[0];
/*      */   }
/*      */ 
/*      */   public static String INSERTCHAR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1193 */     if (args.length < 2) {
/* 1194 */       throw new HiException("215110", "INSERTCHAR");
/*      */     }
/*      */ 
/* 1197 */     if (args[0] == null) {
/* 1198 */       return "";
/*      */     }
/*      */ 
/* 1201 */     int iTimes = 1;
/*      */ 
/* 1203 */     int iStep = 0;
/*      */ 
/* 1205 */     if (args.length > 2) {
/* 1206 */       iTimes = NumberUtils.toInt(StringUtils.trim(args[2]));
/*      */ 
/* 1208 */       if (args.length > 3) {
/* 1209 */         iStep = NumberUtils.toInt(StringUtils.trim(args[3]));
/*      */       }
/*      */     }
/*      */ 
/* 1213 */     StringBuffer result = new StringBuffer(args[0].length());
/*      */ 
/* 1215 */     int interval = args[0].length();
/* 1216 */     for (int i = 0; i < interval; ++i) {
/* 1217 */       result.append(args[0].charAt(i));
/* 1218 */       for (int j = 0; j < iTimes; ++j) {
/* 1219 */         result.append(args[1]);
/*      */       }
/* 1221 */       iTimes += iStep;
/*      */     }
/*      */ 
/* 1225 */     return result.toString();
/*      */   }
/*      */ 
/*      */   public static String GETWORDDELIMITER(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1256 */     if (args.length != 3) {
/* 1257 */       throw new HiException("215110", "GETWORDDELIMITER");
/*      */     }
/*      */ 
/* 1260 */     String[] temp = StringUtils.splitByWholeSeparator(args[0], args[1]);
/* 1261 */     int seq = NumberUtils.toInt(StringUtils.trim(args[2]));
/* 1262 */     if (seq > temp.length)
/* 1263 */       return "";
/* 1264 */     return temp[(seq - 1)];
/*      */   }
/*      */ 
/*      */   public static String LEFTSTR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1286 */     if (args.length != 2)
/* 1287 */       throw new HiException("215110", "LEFTSTR");
/* 1288 */     return StringUtils.left(args[0].trim(), NumberUtils.toInt(StringUtils.trim(args[1])));
/*      */   }
/*      */ 
/*      */   public static String RIGHTSTR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1311 */     if (args.length != 2)
/* 1312 */       throw new HiException("215110", "RIGHTSTR");
/* 1313 */     return StringUtils.right(args[0], NumberUtils.toInt(StringUtils.trim(args[1])));
/*      */   }
/*      */ 
/*      */   public static String STRGETCOUNT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1336 */     if (args.length != 2) {
/* 1337 */       throw new HiException("215110", "STRGETCOUNT");
/*      */     }
/* 1339 */     return String.valueOf(StringUtils.countMatches(args[0], args[1]));
/*      */   }
/*      */ 
/*      */   public static String FABSAMT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1356 */     if (args.length != 2) {
/* 1357 */       throw new HiException("215110", "FABSAMT");
/*      */     }
/* 1359 */     String str = args[0].trim();
/* 1360 */     String len = args[1].trim();
/*      */ 
/* 1363 */     if ((!(StringUtils.containsNone(str, ",."))) || (!(NumberUtils.isNumber(str)))) {
/* 1364 */       throw new HiException("215111", "FABSAMT", "str");
/*      */     }
/* 1366 */     DecimalFormat ft = new DecimalFormat("#");
/*      */ 
/* 1368 */     double dNum = NumberUtils.toDouble(StringUtils.trim(str));
/* 1369 */     String ret = ft.format(Math.abs(dNum));
/*      */ 
/* 1371 */     if (ret.length() < Integer.parseInt(len.trim())) {
/* 1372 */       StringBuffer buf = new StringBuffer(ret);
/* 1373 */       for (int i = 0; i < Integer.parseInt(len.trim()) - ret.length(); ++i) {
/* 1374 */         buf.insert(0, '0');
/*      */       }
/* 1376 */       ret = buf.toString();
/*      */     }
/* 1378 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String ISLEAPYEAR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1392 */     return ISLEAPYEAR(args);
/*      */   }
/*      */ 
/*      */   public static String ISLEAPYEAR(String[] args) throws HiException {
/* 1396 */     if (args.length != 1)
/* 1397 */       throw new HiException("215110", "ISLEAPYEAR");
/* 1398 */     String year = args[0].trim();
/* 1399 */     if ((StringUtils.isEmpty(year)) || (!(StringUtils.isNumeric(year.trim())))) {
/* 1400 */       throw new HiException("215111", "ISLEAPYEAR", "year");
/*      */     }
/*      */ 
/* 1404 */     int iYear = Integer.parseInt(year);
/* 1405 */     if (iYear < 1900) {
/* 1406 */       return "-2";
/*      */     }
/* 1408 */     if (iYear % 4 == 0) {
/* 1409 */       if (iYear % 100 == 0) {
/* 1410 */         if (iYear % 400 == 0) {
/* 1411 */           return "0";
/*      */         }
/* 1413 */         return "-1";
/*      */       }
/*      */ 
/* 1416 */       return "0";
/*      */     }
/*      */ 
/* 1419 */     return "-1";
/*      */   }
/*      */ 
/*      */   public static String FMTDATE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1449 */     if (args.length != 3)
/* 1450 */       throw new HiException("215110", "FMTDATE");
/* 1451 */     String str = args[0].trim();
/* 1452 */     int type1 = Integer.parseInt(args[1].trim());
/* 1453 */     int type2 = Integer.parseInt(args[2].trim());
/*      */ 
/* 1455 */     if ((StringUtils.isEmpty(str)) || (type1 > 5) || (type1 < 0) || (type2 > 5) || (type2 < 0))
/*      */     {
/* 1457 */       throw new HiException("215111", "FMTDATE", "date|type1{0~5}|type2{0~5}");
/*      */     }
/*      */ 
/* 1461 */     String pattern1 = ""; String pattern2 = "";
/* 1462 */     String[] buf1 = { "yyyyMMdd", "yyyy/MM/dd", "MM/dd/yyyy", "yyyy.MM.dd", "yyyy-MM-dd", "yyyy年MM月dd天" };
/*      */ 
/* 1464 */     String[] buf2 = { "4Y2M2D", "4Y/2M/2D", "2M/2D/4Y", "4Y.2M.2D", "4Y-2M-2D", "4Y年2M月2D天" };
/*      */ 
/* 1466 */     str = str.trim();
/*      */ 
/* 1478 */     pattern1 = buf1[type1];
/* 1479 */     pattern2 = buf1[type2];
/*      */ 
/* 1481 */     if ((StringUtils.isEmpty(pattern1)) || (StringUtils.isEmpty(pattern2))) {
/* 1482 */       throw new HiException("215111", "FMTDATE", "type1|type2");
/*      */     }
/*      */ 
/* 1486 */     String[] pattern = { pattern1 };
/*      */     try
/*      */     {
/* 1489 */       Date date = DateUtils.parseDate(str, pattern);
/* 1490 */       return DateFormatUtils.format(date, pattern2);
/*      */     } catch (ParseException e) {
/* 1492 */       throw new HiException("215112", "FMTDATE", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String FMTSTR(Object ctx, String[] args) throws HiException {
/* 1497 */     if (args.length != 2)
/* 1498 */       throw new HiException("215110", "FMTSTR");
/* 1499 */     String value = StringUtils.trim(args[0]);
/* 1500 */     String fmt = StringUtils.trim(args[1]);
/* 1501 */     StringBuffer buf = new StringBuffer();
/* 1502 */     int j = 0;
/* 1503 */     for (int i = 0; i < fmt.length(); ++i) {
/* 1504 */       if (fmt.charAt(i) == 'n') {
/* 1505 */         if (j >= value.length()) {
/*      */           continue;
/*      */         }
/* 1508 */         buf.append(value.charAt(j));
/* 1509 */         ++j;
/*      */       } else {
/* 1511 */         buf.append(fmt.charAt(i));
/*      */       }
/*      */     }
/* 1514 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public static String CHECKDATE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1528 */     return CHECKDATE(args);
/*      */   }
/*      */ 
/*      */   public static String CHECKDATE(String[] args) throws HiException {
/* 1532 */     if (args.length != 1) {
/* 1533 */       throw new HiException("215110", "CHECKDATE");
/*      */     }
/* 1535 */     String str = args[0].trim();
/* 1536 */     if (StringUtils.isEmpty(str)) {
/* 1537 */       throw new HiException("215111", "CHECKDATE", "str");
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1543 */       str = str.trim();
/* 1544 */       if (str.length() != 8) {
/* 1545 */         return "-5";
/*      */       }
/* 1547 */       int iYear = Integer.parseInt(str.substring(0, 4));
/* 1548 */       int iMonth = Integer.parseInt(str.substring(4, 6));
/* 1549 */       int iDay = Integer.parseInt(str.substring(6, 8));
/*      */ 
/* 1551 */       if (iYear <= 1900)
/* 1552 */         return "-1";
/* 1553 */       if ((iMonth < 1) || (iMonth > 12)) {
/* 1554 */         return "-2";
/*      */       }
/* 1556 */       if (ISLEAPYEAR(new String[] { Integer.toString(iYear) }).equals("0"))
/*      */       {
/* 1559 */         if ((iDay > 0) && (iDay <= LEAD_MONTH_DAYS[(iMonth - 1)])) {
/* 1560 */           return "0";
/*      */         }
/* 1562 */         return "-3";
/*      */       }
/*      */ 
/* 1565 */       if ((iDay > 0) && (iDay <= ULEAD_MONTH_DAYS[(iMonth - 1)])) {
/* 1566 */         return "0";
/*      */       }
/* 1568 */       return "-3";
/*      */     }
/*      */     catch (NumberFormatException e)
/*      */     {
/* 1572 */       throw new HiException("215112", "CHECKDATE", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String GETDATETIME(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1594 */     if (args.length != 1) {
/* 1595 */       throw new HiException("215110", "GETDATETIME");
/*      */     }
/* 1597 */     String[] buf1 = { "YYYY", "YY", "MM", "DD", "HH", "MI", "SS" };
/* 1598 */     String[] buf2 = { "yyyy", "yy", "MM", "dd", "HH", "mm", "ss" };
/* 1599 */     String str = args[0].trim();
/* 1600 */     for (int i = 0; i < buf1.length; ++i) {
/* 1601 */       str = StringUtils.replace(str, buf1[i], buf2[i]);
/*      */     }
/*      */ 
/* 1604 */     Calendar calendar = Calendar.getInstance();
/* 1605 */     return DateFormatUtils.format(calendar.getTime(), str);
/*      */   }
/*      */ 
/*      */   public static String GETDATETIME(Object ctx)
/*      */     throws HiException
/*      */   {
/* 1621 */     return GETDATETIME(ctx, new String[] { "yyyyMMDDHHmmss" });
/*      */   }
/*      */ 
/*      */   public static String GETDATE(Object ctx)
/*      */     throws HiException
/*      */   {
/* 1632 */     return GETDATETIME(ctx, new String[] { "yyyyMMdd" });
/*      */   }
/*      */ 
/*      */   public static String GETSECOND(Object ctx)
/*      */   {
/* 1643 */     Date dt = new Date();
/* 1644 */     long curSec = dt.getTime();
/* 1645 */     curSec /= 1000L;
/* 1646 */     return Long.toString(curSec);
/*      */   }
/*      */ 
/*      */   public static String GETSECOND(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*      */     long curSec;
/* 1660 */     if ((args == null) || (args.length == 0)) {
/* 1661 */       Date dt = new Date();
/* 1662 */       curSec = dt.getTime();
/* 1663 */       curSec /= 1000L;
/*      */     } else {
/* 1665 */       String format = "yyyyMMddHHmmss";
/* 1666 */       if (args.length == 2) {
/* 1667 */         format = args[1];
/*      */       }
/* 1669 */       Date curDate = HiDateUtils.parseDate(args[0], format);
/* 1670 */       curSec = curDate.getTime() / 1000L;
/*      */     }
/* 1672 */     return Long.toString(curSec);
/*      */   }
/*      */ 
/*      */   public static String CALCTIME(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1700 */     if (args.length < 4)
/* 1701 */       throw new HiException("215110", "CALCTIME");
/* 1702 */     String datefmt = "MMddHHmmss";
/*      */     try
/*      */     {
/*      */       Date dt;
/* 1705 */       if (args.length == 5) {
/* 1706 */         datefmt = args[4];
/* 1707 */         dt = DateUtils.parseDate(args[0], new String[] { args[4] });
/*      */       } else {
/* 1709 */         dt = DateUtils.parseDate(args[0], new String[] { datefmt });
/*      */       }
/* 1711 */       int num = NumberUtils.toInt(args[3]);
/* 1712 */       if (num == 0) {
/* 1713 */         return args[0];
/*      */       }
/* 1715 */       int sign = (args[1].equals("+")) ? 1 : -1;
/* 1716 */       num *= sign;
/* 1717 */       if (args[2].equals("y"))
/* 1718 */         dt = DateUtils.addYears(dt, num);
/* 1719 */       else if (args[2].equals("M"))
/* 1720 */         dt = DateUtils.addMonths(dt, num);
/* 1721 */       else if (args[2].equals("d"))
/* 1722 */         dt = DateUtils.addDays(dt, num);
/* 1723 */       else if (args[2].equals("h"))
/* 1724 */         dt = DateUtils.addHours(dt, num);
/* 1725 */       else if (args[2].equals("m"))
/* 1726 */         dt = DateUtils.addMinutes(dt, num);
/* 1727 */       else if (args[2].equals("s"))
/* 1728 */         dt = DateUtils.addSeconds(dt, num);
/*      */       else {
/* 1730 */         throw new HiException("220026", args[2]);
/*      */       }
/* 1732 */       return DateFormatUtils.format(dt, datefmt);
/*      */     } catch (ParseException e) {
/* 1734 */       throw new HiException("220026", args[0]);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String CALCDATE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1763 */     int[] buf3 = { 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 3, 2, 1 };
/*      */ 
/* 1765 */     if (args.length != 4) {
/* 1766 */       throw new HiException("215110", "CALCDATE");
/*      */     }
/* 1768 */     String str = args[0].trim();
/* 1769 */     String op = args[1].trim();
/* 1770 */     String obj = args[2].trim();
/* 1771 */     String num = args[3].trim();
/*      */ 
/* 1773 */     if ((StringUtils.isEmpty(str)) || (str.length() != 8))
/* 1774 */       throw new HiException("215111", "CALCDATE", "str");
/*      */     try {
/* 1776 */       String[] pattern = { "yyyyMMdd" };
/* 1777 */       Date dt = DateUtils.parseDate(str, pattern);
/* 1778 */       Calendar calendar = Calendar.getInstance();
/* 1779 */       calendar.setTime(dt);
/* 1780 */       int iYear = calendar.get(1);
/* 1781 */       int iMonth = calendar.get(2) + 1;
/* 1782 */       int iDay = calendar.get(5);
/* 1783 */       int iNum = NumberUtils.toInt(num);
/* 1784 */       if (obj.equals("y")) {
/* 1785 */         if (op.equals("+"))
/* 1786 */           iYear += iNum;
/* 1787 */         else if (op.equals("-")) {
/* 1788 */           iYear -= iNum;
/*      */         }
/* 1790 */         if (iMonth == 2)
/* 1791 */           if (ISMONTHEND(new String[] { str }).equals("1"))
/* 1792 */             if (ISLEAPYEAR(new String[] { Integer.toString(iYear) }).equals("0"))
/*      */             {
/* 1794 */               iDay = 29;
/*      */             }
/*      */             else iDay = 28;
/*      */       }
/*      */       else
/*      */       {
/* 1800 */         if (obj.equals("m")) {
/* 1801 */           if (op.equals("+"))
/* 1802 */             iMonth += iNum;
/* 1803 */           else if (op.equals("-")) {
/* 1804 */             iMonth -= iNum;
/*      */           }
/* 1806 */           calendar.set(iYear, iMonth - 1, iDay);
/* 1807 */           iYear = calendar.get(1);
/* 1808 */           if (iYear < 1900) {
/* 1809 */             throw new HiException("215111", "CALCDATE", "year");
/*      */           }
/*      */ 
/* 1815 */           int index = iMonth;
/* 1816 */           if (index <= 0) {
/* 1817 */             index = buf3[(Math.abs(iMonth) % 12)];
/*      */           }
/* 1819 */           else if (index % 12 == 0)
/* 1820 */             index = 12;
/*      */           else {
/* 1822 */             index %= 12;
/*      */           }
/*      */ 
/* 1826 */           if (ISLEAPYEAR(new String[] { Integer.toString(iYear) }).equals("0"))
/*      */           {
/* 1828 */             if (iDay > LEAD_MONTH_DAYS[(index - 1)]) {
/* 1829 */               iDay = LEAD_MONTH_DAYS[(index - 1)];
/*      */             }
/*      */           }
/* 1832 */           else if (iDay > ULEAD_MONTH_DAYS[(index - 1)]) {
/* 1833 */             iDay = ULEAD_MONTH_DAYS[(index - 1)];
/*      */           }
/*      */ 
/* 1837 */           calendar.set(iYear, index - 1, iDay);
/* 1838 */           return DateFormatUtils.format(calendar.getTime(), "yyyyMMdd"); }
/* 1839 */         if (obj.equals("d")) {
/* 1840 */           if (op.equals("+"))
/* 1841 */             iDay += iNum;
/* 1842 */           else if (op.equals("-")) {
/* 1843 */             iDay -= iNum;
/*      */           }
/*      */         }
/*      */       }
/* 1847 */       if (iYear < 1900) {
/* 1848 */         throw new HiException("215111", "CALCDATE", "year");
/*      */       }
/* 1850 */       calendar.set(iYear, iMonth - 1, iDay);
/* 1851 */       return DateFormatUtils.format(calendar.getTime(), "yyyyMMdd");
/*      */     } catch (ParseException e) {
/* 1853 */       throw new HiException("215112", "CALCDATE", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String ISYEAREND(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1868 */     if (args.length != 1) {
/* 1869 */       throw new HiException("215110", "ISYEAREND");
/*      */     }
/* 1871 */     String str = args[0].trim();
/* 1872 */     if (!(StringUtils.isEmpty(str))) if (CHECKDATE(new String[] { str }).equals("0"))
/*      */         break label54;
/* 1874 */     return "0";
/*      */     try {
/* 1876 */       label54: String[] pt = { "yyyyMMdd" };
/* 1877 */       Date dt = DateUtils.parseDate(str, pt);
/* 1878 */       Calendar calendar = Calendar.getInstance();
/* 1879 */       calendar.setTime(dt);
/* 1880 */       int iMonth = calendar.get(2) + 1;
/* 1881 */       int iDay = calendar.get(5);
/* 1882 */       if ((iMonth == 12) && (iDay == 31)) {
/* 1883 */         return "1";
/*      */       }
/* 1885 */       return "0";
/*      */     }
/*      */     catch (ParseException e) {
/* 1888 */       throw new HiException("215112", "ISYEAREND", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String ISQUARTEREND(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1903 */     if (args.length != 1) {
/* 1904 */       throw new HiException("215110", "ISQUATEREND");
/*      */     }
/*      */ 
/* 1907 */     String str = args[0].trim();
/* 1908 */     if (!(StringUtils.isEmpty(str))) if (CHECKDATE(new String[] { str }).equals("0"))
/*      */         break label54;
/* 1910 */     return "0";
/*      */     try {
/* 1912 */       label54: String[] pt = { "yyyyMMdd" };
/* 1913 */       Date dt = DateUtils.parseDate(str, pt);
/* 1914 */       Calendar calendar = Calendar.getInstance();
/* 1915 */       calendar.setTime(dt);
/* 1916 */       int iMonth = calendar.get(2) + 1;
/* 1917 */       int iDay = calendar.get(5);
/* 1918 */       if ((iMonth == 3) && (iDay == 31))
/* 1919 */         return "1";
/* 1920 */       if ((iMonth == 6) && (iDay == 30))
/* 1921 */         return "1";
/* 1922 */       if ((iMonth == 9) && (iDay == 30))
/* 1923 */         return "1";
/* 1924 */       if ((iMonth == 12) && (iDay == 31)) {
/* 1925 */         return "1";
/*      */       }
/* 1927 */       return "0";
/*      */     } catch (ParseException e) {
/* 1929 */       throw new HiException("215112", "ISQUATEREND", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String ISMONTHEND(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1945 */     return ISMONTHEND(args);
/*      */   }
/*      */ 
/*      */   public static String ISMONTHEND(String[] args) throws HiException {
/* 1949 */     if (args.length != 1) {
/* 1950 */       throw new HiException("215110", "ISMONTHEND");
/*      */     }
/* 1952 */     String str = args[0].trim();
/* 1953 */     if ((StringUtils.isEmpty(str)) || (!(CHECKDATE(args).equals("0"))))
/* 1954 */       return "0";
/* 1955 */     str = str.trim();
/*      */     try {
/* 1957 */       String[] pt = { "yyyyMMdd" };
/* 1958 */       Date dt = DateUtils.parseDate(str, pt);
/* 1959 */       Calendar calendar = Calendar.getInstance();
/* 1960 */       calendar.setTime(dt);
/* 1961 */       int iYear = calendar.get(1);
/* 1962 */       int iMonth = calendar.get(2) + 1;
/* 1963 */       int iDay = calendar.get(5);
/*      */ 
/* 1967 */       if (ISLEAPYEAR(new String[] { Integer.toString(iYear) }).equals("0"))
/*      */       {
/* 1969 */         if (LEAD_MONTH_DAYS[(iMonth - 1)] == iDay) {
/* 1970 */           return "1";
/*      */         }
/* 1972 */         return "0";
/*      */       }
/* 1974 */       if (ULEAD_MONTH_DAYS[(iMonth - 1)] == iDay) {
/* 1975 */         return "1";
/*      */       }
/* 1977 */       return "0";
/*      */     }
/*      */     catch (ParseException e) {
/* 1980 */       throw new HiException("215112", "ISMONTHEND", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String ISWEEKEND(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 1995 */     if (args.length != 1) {
/* 1996 */       throw new HiException("215110", "ISWEEKEND");
/*      */     }
/* 1998 */     String str = args[0].trim();
/* 1999 */     if ((StringUtils.isEmpty(str)) || (!(CHECKDATE(args).equals("0"))))
/* 2000 */       return "0";
/*      */     try
/*      */     {
/* 2003 */       Date dt = DateUtils.parseDate(str, new String[] { "yyyyMMdd" });
/* 2004 */       Calendar calendar = Calendar.getInstance();
/* 2005 */       calendar.setTime(dt);
/* 2006 */       int week = calendar.get(7);
/* 2007 */       if ((week == 7) || (week == 1)) {
/* 2008 */         return "1";
/*      */       }
/* 2010 */       return "0";
/*      */     }
/*      */     catch (ParseException e) {
/* 2013 */       throw new HiException("215112", "ISMONTHEND", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String DIFFDATE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2034 */     if (args.length != 2) {
/* 2035 */       throw new HiException("215110", "DIFFDATE");
/*      */     }
/* 2037 */     String date1 = args[0].trim();
/* 2038 */     String date2 = args[1].trim();
/* 2039 */     if ((StringUtils.isEmpty(date1)) || (StringUtils.isEmpty(date2))) {
/* 2040 */       throw new HiException("215111", "DIFFDATE", "date1|date2");
/*      */     }
/*      */ 
/* 2043 */     if (CHECKDATE(new String[] { date1 }).equals("0")) if (CHECKDATE(new String[] { date2 }).equals("0"))
/*      */         break label112;
/* 2045 */     throw new HiException("215111", "DIFFDATE", "date1|date2");
/*      */ 
/* 2048 */     label112: String[] pt = { "yyyyMMdd" };
/*      */     try {
/* 2050 */       Date dt1 = DateUtils.parseDate(date1, pt);
/* 2051 */       Date dt2 = DateUtils.parseDate(date2, pt);
/* 2052 */       long diff = Math.abs(dt2.getTime() - dt1.getTime());
/* 2053 */       return Long.toString(diff / 86400000L);
/*      */     } catch (ParseException e) {
/* 2055 */       throw new HiException("215112", "ISMONTHEND", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String CALCMONTH(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2083 */     int diff = 0;
/*      */ 
/* 2085 */     if ((args.length < 2) || (args.length > 3)) {
/* 2086 */       throw new HiException("215110", "CALCMONTH");
/*      */     }
/* 2088 */     String date1 = args[0].trim();
/* 2089 */     String date2 = args[1].trim();
/* 2090 */     String flag = null;
/* 2091 */     if (args.length == 3)
/* 2092 */       flag = args[2].trim();
/*      */     else {
/* 2094 */       flag = "0";
/*      */     }
/*      */ 
/* 2097 */     if ((StringUtils.isEmpty(date1)) || (StringUtils.isEmpty(date2)) || (StringUtils.isEmpty(flag)))
/*      */     {
/* 2099 */       throw new HiException("215111", "CALCMONTH", "date1|date2");
/*      */     }
/*      */ 
/* 2103 */     if (CHECKDATE(new String[] { date1 }).equals("0")) if (CHECKDATE(new String[] { date2 }).equals("0"))
/*      */         break label159;
/* 2105 */     throw new HiException("215111", "CALCMONTH", "date1|date2");
/*      */     try
/*      */     {
/* 2110 */       label159: String[] pt = { "yyyyMMdd" };
/* 2111 */       Date dt1 = DateUtils.parseDate(date1, pt);
/* 2112 */       Date dt2 = DateUtils.parseDate(date2, pt);
/* 2113 */       Calendar calendar = Calendar.getInstance();
/* 2114 */       calendar.setTime(dt1);
/* 2115 */       int iYear1 = calendar.get(1);
/* 2116 */       int iMonth1 = calendar.get(2) + 1;
/* 2117 */       int iDay1 = calendar.get(5);
/* 2118 */       calendar.setTime(dt2);
/* 2119 */       int iYear2 = calendar.get(1);
/* 2120 */       int iMonth2 = calendar.get(2) + 1;
/* 2121 */       int iDay2 = calendar.get(5);
/*      */ 
/* 2123 */       if ("0".equals(flag.trim())) {
/* 2124 */         diff = Math.abs(iYear2 - iYear1);
/* 2125 */         if (dt2.getTime() >= dt1.getTime()) {
/* 2126 */           diff = diff * 12 + iMonth2 - iMonth1;
/* 2127 */           if (iDay2 < iDay1)
/* 2128 */             --diff;
/*      */         }
/*      */         else {
/* 2131 */           diff = diff * 12 + iMonth1 - iMonth2;
/* 2132 */           if (iDay1 < iDay2)
/* 2133 */             --diff;
/*      */         }
/*      */       }
/* 2136 */       else if ("1".equals(flag.trim())) {
/* 2137 */         diff = Math.abs(iYear2 - iYear1);
/* 2138 */         if (dt2.getTime() >= dt1.getTime())
/* 2139 */           diff = diff * 12 + iMonth2 - iMonth1;
/*      */         else
/* 2141 */           diff = diff * 12 + iMonth1 - iMonth2;
/*      */       }
/*      */       else {
/* 2144 */         throw new HiException("215111", "CALCMONTH", "flag");
/*      */       }
/*      */ 
/* 2147 */       return Integer.toString(diff);
/*      */     } catch (ParseException e) {
/* 2149 */       throw new HiException("215112", "CALCMONTH", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String CALCYEAR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2201 */     int diff = 0;
/*      */ 
/* 2203 */     if ((args.length < 2) || (args.length > 3)) {
/* 2204 */       throw new HiException("215110", "CALCYEAR");
/*      */     }
/* 2206 */     String date1 = args[0].trim();
/* 2207 */     String date2 = args[1].trim();
/* 2208 */     String flag = null;
/* 2209 */     if (args.length == 3)
/* 2210 */       flag = args[2].trim();
/*      */     else {
/* 2212 */       flag = "0";
/*      */     }
/*      */ 
/* 2215 */     if ((StringUtils.isEmpty(date1)) || (StringUtils.isEmpty(date2)) || (StringUtils.isEmpty(flag)))
/*      */     {
/* 2217 */       throw new HiException("215111", "CALCYEAR", "date1|date2|flag");
/*      */     }
/*      */ 
/* 2221 */     if (CHECKDATE(new String[] { date1 }).equals("0")) if (CHECKDATE(new String[] { date2 }).equals("0"))
/*      */         break label159;
/* 2223 */     throw new HiException("215111", "CALCYEAR", "date1|date2");
/*      */     try
/*      */     {
/* 2228 */       label159: String[] pt = { "yyyyMMdd" };
/* 2229 */       Date dt1 = DateUtils.parseDate(date1, pt);
/* 2230 */       Date dt2 = DateUtils.parseDate(date2, pt);
/* 2231 */       Calendar calendar = Calendar.getInstance();
/* 2232 */       calendar.setTime(dt1);
/* 2233 */       int iYear1 = calendar.get(1);
/* 2234 */       int iMonth1 = calendar.get(2) + 1;
/* 2235 */       int iDay1 = calendar.get(5);
/* 2236 */       calendar.setTime(dt2);
/* 2237 */       int iYear2 = calendar.get(1);
/* 2238 */       int iMonth2 = calendar.get(2) + 1;
/* 2239 */       int iDay2 = calendar.get(5);
/*      */ 
/* 2241 */       if (flag.equals("0")) {
/* 2242 */         diff = Math.abs(iYear2 - iYear1);
/* 2243 */         if (dt2.getTime() >= dt1.getTime()) {
/* 2244 */           if ((iMonth2 < iMonth1) || (iDay2 < iDay1)) {
/* 2245 */             --diff;
/*      */           }
/*      */         }
/* 2248 */         else if ((iMonth1 < iMonth2) || (iDay1 < iDay2)) {
/* 2249 */           --diff;
/*      */         }
/*      */       }
/* 2252 */       else if (flag.equals("1")) {
/* 2253 */         diff = Math.abs(iYear2 - iYear1);
/*      */       }
/* 2255 */       return Integer.toString(diff);
/*      */     }
/*      */     catch (ParseException e) {
/* 2258 */       throw new HiException("215112", "CALCYEAR", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String NORMAL_TO_COBOL(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*      */     int i;
/* 2298 */     String ret = "";
/* 2299 */     if (args.length != 2) {
/* 2300 */       throw new HiException("215110", "NORMAL_TO_COBOL");
/*      */     }
/*      */ 
/* 2303 */     String amt = args[0].trim();
/* 2304 */     String len = args[1].trim();
/*      */ 
/* 2306 */     if (!(StringUtils.isNumeric(len.trim()))) {
/* 2307 */       throw new HiException("215111", "NORMAL_TO_COBOL", "len");
/*      */     }
/*      */ 
/* 2311 */     if (StringUtils.isEmpty(amt.trim())) {
/* 2312 */       return StringUtils.leftPad("", NumberUtils.toInt(len), '0');
/*      */     }
/*      */ 
/* 2317 */     if ((StringUtils.contains(amt, ',')) || (!(NumberUtils.isNumber(StringUtils.trim(amt)))))
/*      */     {
/* 2319 */       throw new HiException("215111", "NORMAL_TO_COBOL", "amt");
/*      */     }
/*      */ 
/* 2323 */     int iLen = NumberUtils.toInt(len);
/* 2324 */     if (iLen <= 0) {
/* 2325 */       throw new HiException("215111", "NORMAL_TO_COBOL", "len");
/*      */     }
/*      */ 
/* 2328 */     double fAmt = NumberUtils.toDouble(amt);
/* 2329 */     int flag = 0;
/*      */ 
/* 2331 */     if (amt.indexOf(45) != -1) {
/* 2332 */       flag = -1;
/*      */     }
/*      */ 
/* 2335 */     DecimalFormat df = new DecimalFormat("#");
/* 2336 */     ret = df.format(Math.abs(fAmt));
/*      */ 
/* 2338 */     if (ret.length() < iLen) {
/* 2339 */       StringBuffer buf = new StringBuffer(ret);
/* 2340 */       for (i = 0; i < iLen - ret.length(); ++i) {
/* 2341 */         buf.insert(0, "0");
/*      */       }
/* 2343 */       ret = buf.toString();
/*      */     }
/* 2345 */     if (flag == -1) {
/* 2346 */       char[] buf1 = ret.toCharArray();
/* 2347 */       i = 48;
/* 2348 */       int j = 112;
/* 2349 */       int k = buf1[(buf1.length - 1)];
/* 2350 */       buf1[(buf1.length - 1)] = (char)(k - i + j);
/*      */ 
/* 2352 */       ret = new String(buf1);
/*      */     }
/*      */ 
/* 2355 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String COBOL_TO_NORMAL(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2372 */     int flag = 1; int iLen = 0;
/* 2373 */     String ret = "";
/*      */ 
/* 2375 */     if (args.length != 2) {
/* 2376 */       throw new HiException("215110", "COBOL_TO_NORMAL");
/*      */     }
/*      */ 
/* 2379 */     String amt = StringUtils.trim(args[0]);
/* 2380 */     int len = NumberUtils.toInt(StringUtils.trim(args[1]));
/*      */ 
/* 2382 */     if (amt == null) {
/* 2383 */       return StringUtils.leftPad("", len, '0');
/*      */     }
/*      */ 
/* 2391 */     if ((amt.charAt(amt.length() - 1) >= 'p') && (amt.charAt(amt.length() - 1) <= 'y'))
/*      */     {
/* 2393 */       char[] buf = amt.toCharArray();
/* 2394 */       flag = -1;
/* 2395 */       buf[(amt.length() - 1)] = (char)(buf[(amt.length() - 1)] - 'p' + 48);
/*      */ 
/* 2397 */       amt = new String(buf);
/*      */     }
/*      */ 
/* 2405 */     double dAmt = NumberUtils.toDouble(amt);
/* 2406 */     DecimalFormat ft = new DecimalFormat("#");
/* 2407 */     ret = ft.format(dAmt);
/* 2408 */     StringBuffer buf1 = new StringBuffer(ret);
/* 2409 */     if (ret.length() < len - 1) {
/* 2410 */       for (int i = 0; i < len - 1 - ret.length(); ++i) {
/* 2411 */         buf1.insert(0, "0");
/*      */       }
/* 2413 */       ret = buf1.toString();
/*      */     }
/* 2415 */     if (flag == -1)
/* 2416 */       buf1.insert(0, '-');
/* 2417 */     else if (buf1.length() < len) {
/* 2418 */       buf1.insert(0, '0');
/*      */     }
/*      */ 
/* 2421 */     return buf1.toString();
/*      */   }
/*      */ 
/*      */   public static String NORMAL_TO_EBCD(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2445 */     int flag = 1; int iLen = 0;
/* 2446 */     String ret = "";
/*      */ 
/* 2448 */     if (args.length != 2) {
/* 2449 */       throw new HiException("215110", "NORMAL_TO_EBCD");
/*      */     }
/*      */ 
/* 2452 */     String amt = args[0].trim();
/* 2453 */     String len = args[1].trim();
/*      */ 
/* 2455 */     if ((!(NumberUtils.isNumber(amt))) || (!(NumberUtils.isNumber(len)))) {
/* 2456 */       throw new HiException("215111", "NORMAL_TO_EBCD", "amt|len");
/*      */     }
/*      */ 
/* 2460 */     if (StringUtils.contains(amt, ',')) {
/* 2461 */       throw new HiException("215111", "NORMAL_TO_EBCD", "amt");
/*      */     }
/*      */ 
/* 2465 */     double dNum = NumberUtils.toDouble(amt);
/* 2466 */     iLen = NumberUtils.toInt(len);
/* 2467 */     if (dNum < 0.0D)
/* 2468 */       flag = -1;
/* 2469 */     DecimalFormat ft = new DecimalFormat("#");
/* 2470 */     ret = ft.format(Math.abs(dNum));
/* 2471 */     char[] buf = ret.toCharArray();
/* 2472 */     if (flag == -1) {
/* 2473 */       if (buf[(buf.length - 1)] == '0')
/* 2474 */         buf[(buf.length - 1)] = '}';
/*      */       else {
/* 2476 */         buf[(buf.length - 1)] = (char)(buf[(buf.length - 1)] - '1' + 74);
/*      */       }
/*      */ 
/*      */     }
/* 2480 */     else if (buf[(buf.length - 1)] == '0')
/* 2481 */       buf[(buf.length - 1)] = '{';
/*      */     else {
/* 2483 */       buf[(buf.length - 1)] = (char)(buf[(buf.length - 1)] - '1' + 65);
/*      */     }
/*      */ 
/* 2487 */     ret = new String(buf);
/*      */ 
/* 2489 */     if (ret.length() < iLen) {
/* 2490 */       StringBuffer retBuf = new StringBuffer(ret);
/* 2491 */       for (int i = 0; i < iLen - ret.length(); ++i) {
/* 2492 */         retBuf.insert(0, '0');
/*      */       }
/* 2494 */       ret = retBuf.toString();
/*      */     }
/*      */ 
/* 2497 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String AMTADDDOT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2511 */     return AMTADDDOT(args);
/*      */   }
/*      */ 
/*      */   public static String AMTADDDOT(String[] args) throws HiException {
/* 2515 */     if (args.length != 1) {
/* 2516 */       throw new HiException("215110", "AMTADDDOT");
/*      */     }
/* 2518 */     String str = args[0].trim();
/* 2519 */     if (!(NumberUtils.isNumber(str))) {
/* 2520 */       throw new HiException("215111", "AMTADDDOT", "str");
/*      */     }
/*      */ 
/* 2523 */     StringBuffer buf = new StringBuffer(str);
/* 2524 */     int flag = 1;
/* 2525 */     if (buf.charAt(0) == '-') {
/* 2526 */       flag = -1;
/* 2527 */       buf = buf.delete(0, 1);
/*      */     }
/* 2529 */     if (buf.length() >= 3)
/* 2530 */       buf.insert(buf.length() - 2, '.');
/* 2531 */     else if (buf.length() == 2)
/* 2532 */       buf.insert(0, "0.");
/* 2533 */     else if (buf.length() == 1)
/* 2534 */       buf.insert(0, "0.0");
/* 2535 */     else if (buf.length() == 0) {
/* 2536 */       buf.insert(0, '0');
/*      */     }
/* 2538 */     if (flag == -1) {
/* 2539 */       buf.insert(0, '-');
/*      */     }
/* 2541 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public static String AMTTOCAP(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2555 */     if (args.length != 1) {
/* 2556 */       throw new HiException("215110", "AMTTOCAP");
/*      */     }
/* 2558 */     String amt = args[0].trim();
/* 2559 */     if (!(StringUtils.isNumeric(amt))) {
/* 2560 */       throw new HiException("215111", "AMTTOCAP", "amt");
/*      */     }
/* 2562 */     if (amt.length() > 15) {
/* 2563 */       throw new HiException("215111", "AMTTOCAP", "amt-15");
/*      */     }
/*      */ 
/* 2566 */     char[] Amount = amt.toCharArray();
/* 2567 */     StringBuffer ChAmount = new StringBuffer();
/* 2568 */     int cunt = 1000; int flag = 0;
/* 2569 */     int endzero = 0;
/*      */ 
/* 2572 */     int len = amt.length();
/*      */ 
/* 2574 */     for (int i = 0; i < len; ++i) {
/* 2575 */       if ((Amount[i] < '0') || (Amount[i] > '9')) {
/* 2576 */         throw new HiException("215111", "AMTTOCAP", "amt");
/*      */       }
/*      */ 
/* 2579 */       if ((cunt == 1000) && (Amount[i] != '0')) {
/* 2580 */         cunt = i;
/*      */       }
/* 2582 */       if ((Amount[i] != '0') && (i < len - 2)) {
/* 2583 */         endzero = i;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2588 */     int count_zero = 0;
/* 2589 */     for (i = cunt; i < len; ++i)
/*      */     {
/*      */       char tmp;
/* 2590 */       flag = Amount[i] - '0';
/* 2591 */       if (flag == 0) {
/* 2592 */         ++count_zero;
/* 2593 */         if ((len - i != 3) && (len - i != 7) && (len - i != 11) && (len - i != 15))
/*      */         {
/* 2598 */           if ((i < len - 1) && (Amount[(i + 1)] != '0') && (i <= endzero)) {
/* 2599 */             tmp = DIGIT_UPPER.charAt(flag);
/* 2600 */             ChAmount.append(tmp);
/* 2601 */             count_zero = 0; } else {
/* 2602 */             if ((i != len - 2) || (Amount[(len - 1)] == '0'))
/*      */               continue;
/* 2604 */             ChAmount.append("零");
/* 2605 */             count_zero = 0;
/*      */           }
/* 2607 */         } else if ((count_zero == 4) && (len - i == 7))
/*      */         {
/* 2609 */           count_zero = 0;
/*      */         }
/*      */         else
/*      */         {
/* 2614 */           tmp = AMT_UPPER.charAt(len - i - 1);
/* 2615 */           ChAmount.append(tmp);
/* 2616 */           count_zero = 0;
/*      */         }
/*      */       } else {
/* 2619 */         count_zero = 0;
/* 2620 */         tmp = DIGIT_UPPER.charAt(flag);
/* 2621 */         ChAmount.append(tmp);
/* 2622 */         tmp = AMT_UPPER.charAt(len - i - 1);
/* 2623 */         ChAmount.append(tmp);
/*      */       }
/*      */     }
/*      */ 
/* 2627 */     if (flag == 0)
/*      */     {
/* 2629 */       ChAmount.append("整");
/*      */     }
/*      */ 
/* 2632 */     return ChAmount.toString();
/*      */   }
/*      */ 
/*      */   public static String AMTDELZERO(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2647 */     return AMTDELZERO(args);
/*      */   }
/*      */ 
/*      */   public static String AMTDELZERO(String[] args) throws HiException {
/* 2651 */     if (args.length != 1) {
/* 2652 */       throw new HiException("215110", "AMTDELZERO");
/*      */     }
/* 2654 */     String amt = args[0].trim();
/* 2655 */     if (!(NumberUtils.isNumber(amt))) {
/* 2656 */       throw new HiException("215111", "AMTDELZERO", "amt");
/*      */     }
/*      */ 
/* 2659 */     double dNum = NumberUtils.toDouble(amt);
/* 2660 */     DecimalFormat ft = new DecimalFormat("#");
/* 2661 */     return ft.format(dNum);
/*      */   }
/*      */ 
/*      */   public static String AMTFMT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/*      */     int j;
/* 2675 */     if (args.length != 1) {
/* 2676 */       throw new HiException("215110", "AMTFMT");
/*      */     }
/* 2678 */     String amt = args[0].trim();
/* 2679 */     if (!(NumberUtils.isNumber(amt))) {
/* 2680 */       throw new HiException("215111", "AMTFMT", "amt");
/*      */     }
/* 2682 */     int flag = 1;
/*      */ 
/* 2685 */     amt = AMTDELZERO(new String[] { amt });
/* 2686 */     if (amt.charAt(0) == '-') {
/* 2687 */       flag = -1;
/* 2688 */       amt = amt.substring(1);
/*      */     }
/*      */ 
/* 2691 */     StringBuffer buf = new StringBuffer(amt);
/* 2692 */     if (amt.length() < 3) {
/* 2693 */       amt = AMTADDDOT(new String[] { amt });
/* 2694 */       buf.replace(0, buf.length(), amt);
/* 2695 */       if (flag == -1) {
/* 2696 */         buf.insert(0, '-');
/*      */       }
/* 2698 */       return buf.toString();
/*      */     }
/*      */ 
/* 2701 */     int len = amt.length();
/* 2702 */     if ((len - 2) % 3 == 0)
/* 2703 */       j = (len - 2) / 3 - 1;
/*      */     else {
/* 2705 */       j = (len - 2) / 3;
/*      */     }
/*      */ 
/* 2708 */     for (int i = 0; i < j; ++i) {
/* 2709 */       buf.insert(len - 2 - ((i + 1) * 3), ',');
/*      */     }
/* 2711 */     buf.insert(buf.length() - 2, '.');
/* 2712 */     if (flag == -1) {
/* 2713 */       buf.insert(0, '-');
/*      */     }
/* 2715 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public static String AMTSIMPLEFMT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2729 */     if (args.length != 1) {
/* 2730 */       throw new HiException("215110", "AMTSIMPLEFMT");
/*      */     }
/*      */ 
/* 2733 */     String amt = args[0].trim();
/* 2734 */     if (!(NumberUtils.isNumber(amt))) {
/* 2735 */       throw new HiException("215110", "AMTSIMPLEFMT", "amt");
/*      */     }
/* 2737 */     if ((StringUtils.contains(amt, '.')) || (StringUtils.contains(amt, ','))) {
/* 2738 */       throw new HiException("215110", "AMTSIMPLEFMT", "amt");
/*      */     }
/*      */ 
/* 2742 */     int flag = 1;
/*      */ 
/* 2744 */     amt = AMTDELZERO(new String[] { amt });
/* 2745 */     if (amt.charAt(0) == '-') {
/* 2746 */       flag = -1;
/* 2747 */       amt = amt.substring(1);
/*      */     }
/* 2749 */     StringBuffer buf = new StringBuffer(amt);
/* 2750 */     amt = AMTADDDOT(new String[] { amt });
/* 2751 */     buf = new StringBuffer(amt);
/* 2752 */     if (flag == -1) {
/* 2753 */       buf.insert(0, '-');
/*      */     }
/* 2755 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public static String CYCAMTFALSE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2777 */     if (args.length != 2) {
/* 2778 */       throw new HiException("215110", "CYCAMTFALSE");
/*      */     }
/*      */ 
/* 2781 */     String amt = args[1].trim();
/* 2782 */     String cyccod = args[0].trim();
/* 2783 */     if ((!(NumberUtils.isNumber(amt))) || (StringUtils.isEmpty(cyccod))) {
/* 2784 */       throw new HiException("215110", "CYCAMTFALSE", "amt|cyccod");
/*      */     }
/*      */ 
/* 2787 */     int len = 0;
/* 2788 */     String ret = "0";
/* 2789 */     for (int i = 0; i < 2; ++i) {
/* 2790 */       if (amt.charAt(amt.length() - i - 1) != '0') {
/* 2791 */         ++len;
/*      */       }
/*      */     }
/*      */ 
/* 2795 */     if ((((StringUtils.equals(cyccod, "ITL")) || (StringUtils.equals(cyccod, "JPY")))) && 
/* 2797 */       (len != 0)) {
/* 2798 */       ret = "1";
/*      */     }
/*      */ 
/* 2801 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String CSUBSTR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2821 */     if (args.length != 3) {
/* 2822 */       throw new HiException("215110", "CSUBSTR");
/*      */     }
/* 2824 */     if (StringUtils.isEmpty(args[0])) {
/* 2825 */       return "";
/*      */     }
/*      */ 
/* 2828 */     int idx1 = NumberUtils.toInt(args[1].trim());
/* 2829 */     int idx2 = NumberUtils.toInt(args[2].trim());
/*      */ 
/* 2831 */     HiByteBuffer bb = new HiByteBuffer(args[0].getBytes());
/*      */ 
/* 2833 */     byte[] subBytes = bb.subbyte(idx1, idx2);
/*      */ 
/* 2846 */     return new String(subBytes);
/*      */   }
/*      */ 
/*      */   public static String AMTPOWER(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2862 */     if (args.length != 2) {
/* 2863 */       throw new HiException("215110", "AMTPOWER");
/*      */     }
/* 2865 */     BigDecimal d = new BigDecimal(args[0].trim());
/* 2866 */     int i = NumberUtils.toInt(args[1].trim());
/*      */ 
/* 2868 */     return String.valueOf(d.movePointRight(i).longValue());
/*      */   }
/*      */ 
/*      */   public static String GETENV(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2884 */     return GETENV(args);
/*      */   }
/*      */ 
/*      */   public static String GETENV(String[] args) throws HiException {
/* 2888 */     if (args.length != 1)
/* 2889 */       throw new HiException("215110", "GETENV");
/*      */     try
/*      */     {
/* 2892 */       String value = System.getenv(args[0].trim());
/* 2893 */       if (StringUtils.isEmpty(value)) {
/* 2894 */         value = System.getProperty(args[0].trim());
/*      */       }
/* 2896 */       return value; } catch (Throwable t) {
/*      */     }
/* 2898 */     return System.getProperty(args[0].trim());
/*      */   }
/*      */ 
/*      */   public static String ADDAMT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2916 */     if (args.length < 1)
/* 2917 */       throw new HiException("215110", "ADDAMT");
/* 2918 */     int value = 0;
/* 2919 */     for (int i = 0; i < args.length; ++i) {
/* 2920 */       if (args[i] == null)
/*      */         continue;
/* 2922 */       value += NumberUtils.toInt(args[i].trim());
/*      */     }
/* 2924 */     return String.valueOf(value);
/*      */   }
/*      */ 
/*      */   public static String DELCHAR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2940 */     if (args.length != 2) {
/* 2941 */       throw new HiException("215110", "DELCHAR");
/*      */     }
/* 2943 */     String value = args[0];
/*      */ 
/* 2945 */     String delStr = new String(new char[] { (char)NumberUtils.toInt(args[1]) });
/*      */ 
/* 2947 */     value = StringUtils.replace(value, delStr, "");
/* 2948 */     return value;
/*      */   }
/*      */ 
/*      */   public static String DELBOTHCHAR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2965 */     if (args.length != 2) {
/* 2966 */       throw new HiException("215110", "DELBOTHCHAR");
/*      */     }
/*      */ 
/* 2969 */     String value = args[0];
/*      */ 
/* 2971 */     String delStr = new String(new char[] { (char)NumberUtils.toInt(args[1]) });
/*      */ 
/* 2973 */     value = StringUtils.removeStart(value, delStr);
/* 2974 */     value = StringUtils.removeEnd(value, delStr);
/* 2975 */     return value;
/*      */   }
/*      */ 
/*      */   public static String SUBAMT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 2991 */     if (args.length != 2) {
/* 2992 */       throw new HiException("215110", "SUBAMT");
/*      */     }
/*      */ 
/* 2995 */     BigDecimal atm1 = new BigDecimal(args[0].trim());
/* 2996 */     BigDecimal atm2 = new BigDecimal(args[1].trim());
/* 2997 */     BigDecimal atm3 = atm1.subtract(atm2);
/* 2998 */     return atm3.toString();
/*      */   }
/*      */ 
/*      */   public static String HEX2STR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3022 */     if (args.length != 1)
/* 3023 */       throw new HiException("215110", "HEX2STR");
/*      */     try {
/* 3025 */       return new String(Hex.decodeHex(args[0].toCharArray()));
/*      */     } catch (DecoderException e) {
/* 3027 */       throw new HiException("215110", e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String STR2HEX(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3044 */     if (args.length != 1)
/* 3045 */       throw new HiException("215110", "HEX2STR");
/* 3046 */     return new String(Hex.encodeHex(args[0].getBytes()));
/*      */   }
/*      */ 
/*      */   public static String REPCHAR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3064 */     if (args.length != 3)
/* 3065 */       throw new HiException("215110", "REPCHAR");
/* 3066 */     return StringUtils.replace(args[0], args[1], args[2]);
/*      */   }
/*      */ 
/*      */   public static String EBCD_TO_NORMAL(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3081 */     if (args.length != 2) {
/* 3082 */       throw new HiException("215110", "EBCD_TO_NORMAL");
/*      */     }
/*      */ 
/* 3087 */     int outPutLen = Integer.parseInt(args[1]);
/* 3088 */     if (StringUtils.isBlank(args[0])) {
/* 3089 */       return StringUtils.leftPad("", outPutLen, "0");
/*      */     }
/*      */ 
/* 3092 */     String ebcd = args[0];
/*      */ 
/* 3094 */     char lab = ebcd.charAt(ebcd.length() - 1);
/* 3095 */     String normal = ebcd.substring(0, ebcd.length() - 1);
/*      */ 
/* 3097 */     int index = EBCD_POSITIVE.indexOf(lab);
/* 3098 */     if (index >= 0) {
/* 3099 */       normal = normal + index;
/* 3100 */       normal = StringUtils.leftPad(normal, outPutLen, '0');
/* 3101 */       return normal;
/*      */     }
/*      */ 
/* 3104 */     index = EBCD_NEGATIVE.indexOf(lab);
/* 3105 */     if (index >= 0) {
/* 3106 */       normal = normal + index;
/*      */ 
/* 3108 */       normal = "-" + StringUtils.leftPad(normal, outPutLen - 1, '0');
/* 3109 */       return normal;
/*      */     }
/*      */ 
/* 3112 */     return args[0];
/*      */   }
/*      */ 
/*      */   public static String BIN2HEX(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3125 */     if (args.length < 1)
/* 3126 */       throw new HiException("215110", "BIN2HEX");
/*      */     try {
/* 3128 */       return Integer.toHexString(Integer.valueOf(args[0].trim(), 2).intValue());
/*      */     }
/*      */     catch (NumberFormatException e) {
/* 3131 */       throw new HiException("", "含有非二进制的数字");
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String HEX2BIN(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3145 */     if (args.length < 1)
/* 3146 */       throw new HiException("215110", "HEX2BIN");
/* 3147 */     String binStr = "";
/*      */     try {
/* 3149 */       binStr = Integer.toBinaryString(Integer.valueOf(args[0].trim(), 16).intValue());
/*      */     }
/*      */     catch (NumberFormatException e) {
/* 3152 */       throw new HiException("", "含有非十六进制的数字");
/*      */     }
/*      */ 
/* 3160 */     return binStr;
/*      */   }
/*      */ 
/*      */   public static String FADD(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3174 */     if (args.length < 3) {
/* 3175 */       throw new HiException("215110", "FADD");
/*      */     }
/* 3177 */     int argNum = args.length;
/*      */ 
/* 3179 */     int precision = Integer.parseInt(args[(argNum - 1)].trim());
/* 3180 */     double total = 0.0D;
/* 3181 */     --argNum;
/* 3182 */     for (int i = 0; i < argNum; ++i) {
/* 3183 */       total += Double.parseDouble(args[i].trim());
/*      */     }
/* 3185 */     BigDecimal tmp = BigDecimal.valueOf(total);
/* 3186 */     tmp = tmp.setScale(precision, 4);
/* 3187 */     return tmp.toString();
/*      */   }
/*      */ 
/*      */   public static String FSUB(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3202 */     if (args.length < 3) {
/* 3203 */       throw new HiException("215110", "FSUB");
/*      */     }
/* 3205 */     int argNum = args.length;
/*      */ 
/* 3207 */     int precision = Integer.parseInt(args[(argNum - 1)]);
/* 3208 */     double total = NumberUtils.toDouble(args[0]);
/*      */ 
/* 3210 */     --argNum;
/* 3211 */     for (int i = 1; i < argNum; ++i) {
/* 3212 */       total -= NumberUtils.toDouble(args[i]);
/*      */     }
/*      */ 
/* 3215 */     BigDecimal tmp = BigDecimal.valueOf(total);
/* 3216 */     tmp = tmp.setScale(precision, 4);
/* 3217 */     return tmp.toString();
/*      */   }
/*      */ 
/*      */   public static String FMUL(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3232 */     if (args.length < 3) {
/* 3233 */       throw new HiException("215110", "FMUL");
/*      */     }
/* 3235 */     int argNum = args.length;
/*      */ 
/* 3239 */     int precision = NumberUtils.toInt(args[(argNum - 1)]);
/* 3240 */     double total = NumberUtils.toDouble(args[0]);
/*      */ 
/* 3242 */     --argNum;
/* 3243 */     for (int i = 1; i < argNum; ++i)
/*      */     {
/* 3245 */       total *= NumberUtils.toDouble(args[i]);
/*      */     }
/* 3247 */     BigDecimal tmp = BigDecimal.valueOf(total);
/* 3248 */     tmp = tmp.setScale(precision, 4);
/* 3249 */     return tmp.toString();
/*      */   }
/*      */ 
/*      */   public static String FDIV(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3265 */     if (args.length < 3) {
/* 3266 */       throw new HiException("215110", "FDIV");
/*      */     }
/* 3268 */     int argNum = args.length;
/*      */ 
/* 3272 */     int precision = NumberUtils.toInt(args[(argNum - 1)]);
/* 3273 */     double total = NumberUtils.toDouble(args[0]);
/*      */ 
/* 3275 */     --argNum;
/* 3276 */     for (int i = 1; i < argNum; ++i)
/*      */     {
/* 3278 */       total /= NumberUtils.toDouble(args[i]);
/*      */     }
/*      */ 
/* 3281 */     BigDecimal tmp = BigDecimal.valueOf(total);
/* 3282 */     tmp = tmp.setScale(precision, 4);
/* 3283 */     return tmp.toString();
/*      */   }
/*      */ 
/*      */   public static String FPOW(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3298 */     if (args.length < 3) {
/* 3299 */       throw new HiException("215110", "FPOW");
/*      */     }
/*      */ 
/* 3307 */     int precision = NumberUtils.toInt(args[2]);
/*      */ 
/* 3309 */     int times = NumberUtils.toInt(args[1]);
/* 3310 */     double total = NumberUtils.toDouble(args[0]);
/*      */ 
/* 3312 */     for (int i = 1; i < times; ++i) {
/* 3313 */       total *= total;
/*      */     }
/* 3315 */     BigDecimal tmp = BigDecimal.valueOf(total);
/* 3316 */     tmp = tmp.setScale(precision, 4);
/* 3317 */     return tmp.toString();
/*      */   }
/*      */ 
/*      */   public static String DATETOCAP(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3333 */     if (args.length < 1) {
/* 3334 */       throw new HiException("215110", "DATETOCAP");
/*      */     }
/* 3336 */     if (args[0].trim().length() != 8) {
/* 3337 */       throw new HiException("215115", args[0].trim());
/*      */     }
/*      */ 
/* 3340 */     char[] dateArray = args[0].trim().toCharArray();
/* 3341 */     int flag = 0;
/* 3342 */     String year = ""; String month = ""; String day = "";
/* 3343 */     for (int i = 0; i < 4; ++i) {
/* 3344 */       if ((dateArray[i] < '0') || (dateArray[i] > '9')) {
/* 3345 */         throw new HiException("215115", args[0].trim());
/*      */       }
/*      */ 
/* 3348 */       flag = dateArray[i] - '0';
/* 3349 */       year = year + DIGIT_UPPER.charAt(flag);
/*      */     }
/* 3351 */     year = year + "年";
/*      */ 
/* 3353 */     for (i = 4; i < 6; ++i) {
/* 3354 */       if ((dateArray[i] < '0') || (dateArray[i] > '9')) {
/* 3355 */         throw new HiException("215115", args[0].trim());
/*      */       }
/*      */ 
/* 3358 */       flag = dateArray[i] - '0';
/* 3359 */       month = month + DIGIT_UPPER.charAt(flag);
/*      */     }
/* 3361 */     month = month + "月";
/*      */ 
/* 3363 */     for (i = 6; i < 8; ++i) {
/* 3364 */       if ((dateArray[i] < '0') || (dateArray[i] > '9')) {
/* 3365 */         throw new HiException("215115", args[0].trim());
/*      */       }
/*      */ 
/* 3368 */       flag = dateArray[i] - '0';
/* 3369 */       day = day + DIGIT_UPPER.charAt(flag);
/*      */     }
/* 3371 */     day = day + "日";
/* 3372 */     return year + month + day;
/*      */   }
/*      */ 
/*      */   public static String GETFILELINES(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3386 */     if (args.length < 1) {
/* 3387 */       throw new HiException("215110", "GETFILELINES");
/*      */     }
/* 3389 */     String file = args[0].trim();
/* 3390 */     File filepath = new File(file);
/*      */ 
/* 3392 */     if (!(filepath.isAbsolute())) {
/* 3393 */       String[] homeArgs = { "HWORKDIR" };
/* 3394 */       String root = GETENV(homeArgs);
/* 3395 */       if (root != null) {
/* 3396 */         if (root.endsWith("/"))
/* 3397 */           root = root + file;
/*      */         else {
/* 3399 */           root = root + file + "/";
/*      */         }
/* 3401 */         file = root;
/*      */       } else {
/* 3403 */         throw new HiException("215118", "HOME", "GETFILELINES");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3408 */     int total = 0;
/* 3409 */     BufferedReader br = null;
/*      */     try {
/* 3411 */       br = new BufferedReader(new FileReader(file));
/* 3412 */       while (br.ready()) {
/* 3413 */         br.readLine();
/* 3414 */         ++total;
/*      */       }
/*      */     } catch (FileNotFoundException e) {
/*      */     }
/*      */     catch (IOException e) {
/*      */     }
/*      */     finally {
/*      */       try {
/* 3422 */         if (br != null)
/* 3423 */           br.close();
/*      */       } catch (IOException e) {
/*      */       }
/*      */     }
/* 3427 */     return String.valueOf(total);
/*      */   }
/*      */ 
/*      */   public static String GETFILESIZE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3441 */     if (args.length < 1) {
/* 3442 */       throw new HiException("215110", "GETFILESIZE");
/*      */     }
/* 3444 */     String file = args[0].trim();
/* 3445 */     File filepath = new File(file);
/*      */ 
/* 3447 */     if (!(filepath.isAbsolute())) {
/* 3448 */       String[] homeArgs = { "HWORKDIR" };
/* 3449 */       String root = GETENV(homeArgs);
/* 3450 */       if (root != null)
/* 3451 */         filepath = new File(root, file);
/*      */       else {
/* 3453 */         throw new HiException("215118", "HOME", "GETFILESIZE");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3458 */     long total = 0L;
/*      */ 
/* 3460 */     total = filepath.length();
/*      */ 
/* 3462 */     return String.valueOf(total);
/*      */   }
/*      */ 
/*      */   public static String TODBC(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3474 */     if (args.length < 1)
/* 3475 */       throw new HiException("215110", "TODBC");
/* 3476 */     if (args[0] == null) {
/* 3477 */       return null;
/*      */     }
/* 3479 */     return HiExpBasicHelper.todbc(args[0]);
/*      */   }
/*      */ 
/*      */   public static String TOSBC(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3491 */     if (args.length < 1)
/* 3492 */       throw new HiException("215110", "TOSBC");
/* 3493 */     if (args[0] == null) {
/* 3494 */       return null;
/*      */     }
/* 3496 */     return HiExpBasicHelper.tosbc(args[0]);
/*      */   }
/*      */ 
/*      */   public static String CHAOSCODE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3510 */     if (args.length < 1)
/* 3511 */       throw new HiException("215110", "CHAOSCODE");
/* 3512 */     if (args[0] == null) {
/* 3513 */       return null;
/*      */     }
/* 3515 */     String str = args[0];
/*      */ 
/* 3517 */     StringBuffer outStr = new StringBuffer();
/*      */ 
/* 3519 */     int len = str.length();
/*      */ 
/* 3521 */     for (int i = 0; i < len; ++i) {
/* 3522 */       String tStr = str.substring(i, i + 1);
/*      */       try {
/* 3524 */         byte[] b = tStr.getBytes("GBK");
/* 3525 */         if ((b[0] > -1) || (b[1] > -1)) {
/* 3526 */           tStr = "　";
/* 3527 */           outStr.append(tStr);
/*      */         } else {
/* 3529 */           outStr.append(tStr);
/*      */         }
/*      */       } catch (UnsupportedEncodingException e) {
/* 3532 */         throw new HiException(e);
/*      */       }
/*      */     }
/* 3535 */     return outStr.toString();
/*      */   }
/*      */ 
/*      */   public static String CHNCONVERT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3548 */     if (args.length < 1) {
/* 3549 */       throw new HiException("215110", "CHNCONVERT");
/*      */     }
/* 3551 */     if (args[0] == null) {
/* 3552 */       return null;
/*      */     }
/* 3554 */     String str = args[0].trim();
/* 3555 */     byte[] b = null;
/*      */     try {
/* 3557 */       b = str.getBytes("GB2312");
/*      */     } catch (UnsupportedEncodingException e) {
/* 3559 */       return null;
/*      */     }
/* 3561 */     if (b == null) {
/* 3562 */       return null;
/*      */     }
/*      */ 
/* 3565 */     HiByteBuffer bb = new HiByteBuffer(64);
/* 3566 */     for (int i = 0; i < b.length; ++i) {
/* 3567 */       bb.append(b[i]);
/*      */ 
/* 3569 */       if (b[i] <= -1) {
/* 3570 */         bb.append(32);
/* 3571 */         bb.append(32);
/* 3572 */         bb.append(2);
/*      */       }
/*      */     }
/* 3575 */     return bb.toString("GB2312");
/*      */   }
/*      */ 
/*      */   public static String IS_MATCH(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3593 */     if (args.length < 2)
/* 3594 */       throw new HiException("215110", "IS_MATCH");
/* 3595 */     boolean ignoreCase = true;
/* 3596 */     if ((args.length == 3) && 
/* 3597 */       ("1".equals(args[2]))) {
/* 3598 */       ignoreCase = false;
/*      */     }
/*      */ 
/* 3601 */     Pattern p = null;
/*      */ 
/* 3603 */     if (ignoreCase)
/* 3604 */       p = Pattern.compile(args[1]);
/*      */     else {
/* 3606 */       p = Pattern.compile(args[1], 2);
/*      */     }
/* 3608 */     Matcher m = p.matcher(args[0]);
/*      */ 
/* 3610 */     if (m.matches()) {
/* 3611 */       return "1";
/*      */     }
/* 3613 */     return "0";
/*      */   }
/*      */ 
/*      */   public static String CONDITION3(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3632 */     if (args.length < 3) {
/* 3633 */       throw new HiException("215110", "CONDITION3");
/*      */     }
/*      */ 
/* 3636 */     boolean r = args[0].equals("1");
/* 3637 */     return ((r) ? args[1] : args[2]);
/*      */   }
/*      */ 
/*      */   public static String IsExistNode(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3652 */     if (args.length < 1) {
/* 3653 */       throw new HiException("215110", "IsExistNode");
/*      */     }
/* 3655 */     for (int i = 0; i < args.length; ++i) {
/* 3656 */       if (args[i] == null)
/* 3657 */         return "0";
/*      */     }
/* 3659 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String RANDOM(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3673 */     if (args.length < 2) {
/* 3674 */       throw new HiException("215110", "RANDOM");
/*      */     }
/* 3676 */     int len = NumberUtils.toInt(StringUtils.trim(args[0]));
/*      */ 
/* 3678 */     args[1] = StringUtils.trim(args[1]);
/*      */ 
/* 3680 */     if (StringUtils.equals(args[1], "0"))
/* 3681 */       return RandomStringUtils.randomAlphanumeric(len);
/* 3682 */     if (StringUtils.equals(args[1], "1")) {
/* 3683 */       return RandomStringUtils.randomAlphabetic(len);
/*      */     }
/* 3685 */     return RandomStringUtils.randomNumeric(len);
/*      */   }
/*      */ 
/*      */   public static String ETF(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3699 */     if (args.length != 1)
/* 3700 */       throw new HiException("215110", "ETF");
/* 3701 */     return ((HiMessageContext)ctx).getCurrentMsg().getETFBody().getGrandChildValue(args[0]);
/*      */   }
/*      */ 
/*      */   public static String BLANK(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3712 */     if (args.length != 1)
/* 3713 */       throw new HiException("215110", "BLANK");
/* 3714 */     int len = NumberUtils.toInt(args[0]);
/* 3715 */     return StringUtils.leftPad("", len);
/*      */   }
/*      */ 
/*      */   public static String DATEPROC(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3725 */     if (args.length != 1)
/* 3726 */       throw new HiException("215110", "DATEPROC");
/* 3727 */     return args[0].replaceAll("-", "");
/*      */   }
/*      */ 
/*      */   public static String AMTPROC(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3737 */     if (args.length != 1)
/* 3738 */       throw new HiException("215110", "AMTPROC");
/* 3739 */     return args[0].replaceAll("[,.]", "");
/*      */   }
/*      */ 
/*      */   public static String DECODE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3749 */     if (args.length != 1)
/* 3750 */       throw new HiException("215110", "ENCODE");
/*      */     try {
/* 3752 */       return URLDecoder.decode(args[0], "UTF-8");
/*      */     } catch (UnsupportedEncodingException e) {
/* 3754 */       throw new HiException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String ENCODE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3765 */     if (args.length != 1)
/* 3766 */       throw new HiException("215110", "ENCODE");
/*      */     try {
/* 3768 */       return URLEncoder.encode(args[0], "UTF-8");
/*      */     } catch (UnsupportedEncodingException e) {
/* 3770 */       throw new HiException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String XOREncrypt(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3785 */     if (args.length != 1) {
/* 3786 */       throw new HiException("215110", "XOREncrypt");
/*      */     }
/* 3788 */     String data = args[0];
/* 3789 */     StringBuffer result = new StringBuffer();
/* 3790 */     String key = "hisun";
/* 3791 */     int j = 0;
/* 3792 */     for (int i = 0; i < data.length(); ++i) {
/* 3793 */       if (j == key.length())
/* 3794 */         j = 0;
/* 3795 */       result.append(StringUtils.leftPad(Integer.toHexString(data.charAt(i) ^ key.charAt(j)), 2, '0'));
/*      */ 
/* 3798 */       ++j;
/*      */     }
/* 3800 */     return result.toString();
/*      */   }
/*      */ 
/*      */   public static String XORDecrypt(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3808 */     if (args.length != 1) {
/* 3809 */       throw new HiException("215110", "XORDecrypt");
/*      */     }
/* 3811 */     String data = args[0];
/* 3812 */     StringBuffer result = new StringBuffer();
/* 3813 */     String key = "hisun";
/* 3814 */     int j = 0;
/* 3815 */     for (int i = 0; i < data.length(); i += 2) {
/* 3816 */       if (j == key.length())
/* 3817 */         j = 0;
/* 3818 */       int c = Integer.parseInt(data.substring(i, i + 2), 16);
/* 3819 */       result.append((char)(c ^ key.charAt(j)));
/* 3820 */       ++j;
/*      */     }
/* 3822 */     return result.toString();
/*      */   }
/*      */ 
/*      */   public static String HEX2DEC(Object object, String[] args)
/*      */     throws HiException
/*      */   {
/* 3834 */     if (args.length != 1) {
/* 3835 */       throw new HiException("215110", "HEX2DEC");
/*      */     }
/* 3837 */     long l = Long.parseLong(args[0], 16);
/*      */ 
/* 3839 */     return String.valueOf(l);
/*      */   }
/*      */ 
/*      */   public static String DEC2HEX(Object object, String[] args)
/*      */     throws HiException
/*      */   {
/* 3851 */     if (args.length != 1) {
/* 3852 */       throw new HiException("215110", "DEC2HEX");
/*      */     }
/* 3854 */     return Long.toString(Long.parseLong(args[0]), 16);
/*      */   }
/*      */ 
/*      */   public static String ISALPHANUMERIC(Object object, String[] args)
/*      */     throws HiException
/*      */   {
/* 3877 */     if (args.length == 0) {
/* 3878 */       throw new HiException("215110", "ISALPHANUMERIC");
/*      */     }
/*      */ 
/* 3881 */     char[] chs = null;
/* 3882 */     for (int i = 0; i < args.length; ++i) {
/* 3883 */       chs = args[i].toCharArray();
/* 3884 */       for (int j = 0; j < chs.length; ++j) {
/* 3885 */         if (!(CharUtils.isAsciiAlphanumeric(chs[j]))) {
/* 3886 */           return "0";
/*      */         }
/*      */       }
/*      */     }
/* 3890 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String FORMATTIME(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3902 */     if (args.length < 1)
/* 3903 */       throw new HiException("215110", "FORMATTIME");
/* 3904 */     String pattern = "yyyy/MM/dd HH:mm:ss";
/* 3905 */     if (args.length > 1) {
/* 3906 */       pattern = args[1];
/*      */     }
/* 3908 */     return DateFormatUtils.format(Long.parseLong(args[0]), pattern);
/*      */   }
/*      */ 
/*      */   public static String ISFILE(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3918 */     if (args.length < 1)
/* 3919 */       throw new HiException("215110", "ISFILE");
/* 3920 */     for (int i = 0; i < args.length; ++i) {
/* 3921 */       File f = null;
/* 3922 */       if (!(args[i].startsWith(SystemUtils.FILE_SEPARATOR)))
/* 3923 */         f = new File(HiICSProperty.getWorkDir() + "/" + args[i]);
/*      */       else {
/* 3925 */         f = new File(args[i]);
/*      */       }
/* 3927 */       if (!(f.isFile())) {
/* 3928 */         return "0";
/*      */       }
/*      */     }
/* 3931 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String ISDIR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3941 */     if (args.length < 1)
/* 3942 */       throw new HiException("215110", "ISDIR");
/* 3943 */     for (int i = 0; i < args.length; ++i) {
/* 3944 */       File f = null;
/* 3945 */       if (!(args[i].startsWith(SystemUtils.FILE_SEPARATOR)))
/* 3946 */         f = new File(HiICSProperty.getWorkDir() + "/" + args[i]);
/*      */       else {
/* 3948 */         f = new File(args[i]);
/*      */       }
/* 3950 */       if (!(f.isDirectory())) {
/* 3951 */         return "0";
/*      */       }
/*      */     }
/* 3954 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String ISJAR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3964 */     if (args.length < 1)
/* 3965 */       throw new HiException("215110", "ISJAR");
/* 3966 */     for (int i = 0; i < args.length; ++i) {
/* 3967 */       String name = args[i];
/* 3968 */       if (!(name.startsWith(SystemUtils.FILE_SEPARATOR)))
/* 3969 */         name = HiICSProperty.getWorkDir() + "/" + name;
/*      */       try
/*      */       {
/* 3972 */         JarFile f = new JarFile(name);
/* 3973 */         f.close();
/*      */       } catch (IOException e) {
/* 3975 */         return "0";
/*      */       }
/*      */     }
/* 3978 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String SaveToTDS(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 3992 */     if (args.length < 2)
/* 3993 */       throw new HiException("215110", "SaveToTDS");
/* 3994 */     boolean overlay = false;
/* 3995 */     if ((args.length >= 3) && 
/* 3996 */       ("1".equals(args[2]))) {
/* 3997 */       overlay = true;
/*      */     }
/*      */ 
/* 4000 */     if ((memoryTD.containsKey(args[0])) && (!(overlay))) {
/* 4001 */       return "0";
/*      */     }
/*      */ 
/* 4004 */     memoryTD.put(args[0], args[1]);
/* 4005 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String DeleteTDS(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4019 */     if (args.length < 1) {
/* 4020 */       throw new HiException("215110", "LoadFromTDS");
/*      */     }
/* 4022 */     if (!(memoryTD.containsKey(args[0]))) {
/* 4023 */       return "1";
/*      */     }
/* 4025 */     memoryTD.remove(args[0]);
/* 4026 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String LoadFromTDS(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4040 */     if (args.length < 1) {
/* 4041 */       throw new HiException("215110", "LoadFromTDS");
/*      */     }
/* 4043 */     if (!(memoryTD.containsKey(args[0]))) {
/* 4044 */       return "2";
/*      */     }
/* 4046 */     String value = (String)memoryTD.get(args[0]);
/* 4047 */     if ((args.length == 2) && 
/* 4048 */       ("1".equals(args[1]))) {
/* 4049 */       memoryTD.remove(args[0]);
/*      */     }
/*      */ 
/* 4052 */     return value;
/*      */   }
/*      */ 
/*      */   public static String GetETFValue(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4066 */     if (args.length < 1) {
/* 4067 */       throw new HiException("215110", "GetETFValue");
/*      */     }
/* 4069 */     HiMessageContext msgCtx = (HiMessageContext)ctx;
/* 4070 */     HiMessage msg = msgCtx.getCurrentMsg();
/* 4071 */     HiETF root = msg.getETFBody();
/* 4072 */     String value = root.getGrandChildValue(args[0]);
/* 4073 */     if (value == null) {
/* 4074 */       return "";
/*      */     }
/* 4076 */     return value;
/*      */   }
/*      */ 
/*      */   public static String SetETFValue(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4092 */     if (args.length < 2) {
/* 4093 */       throw new HiException("215110", "setETFValue");
/*      */     }
/* 4095 */     HiMessageContext msgCtx = (HiMessageContext)ctx;
/* 4096 */     HiMessage msg = msgCtx.getCurrentMsg();
/* 4097 */     HiETF root = msg.getETFBody();
/* 4098 */     HiETF node = root.getGrandChildNode(args[0]);
/* 4099 */     if (node == null) {
/* 4100 */       return "0";
/*      */     }
/* 4102 */     node.setValue(args[1]);
/* 4103 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String GetETFName(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4118 */     if (args.length < 1)
/* 4119 */       throw new HiException("215110", "getETFName");
/* 4120 */     HiMessageContext msgCtx = (HiMessageContext)ctx;
/* 4121 */     HiMessage msg = msgCtx.getCurrentMsg();
/* 4122 */     HiETF root = msg.getETFBody();
/* 4123 */     HiETF node = root.getGrandChildNode(args[0]);
/* 4124 */     if (node == null) {
/* 4125 */       return "";
/*      */     }
/* 4127 */     return node.getName();
/*      */   }
/*      */ 
/*      */   public static String SetETFName(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4142 */     if (args.length < 2)
/* 4143 */       throw new HiException("215110", "setETFName");
/* 4144 */     HiMessageContext msgCtx = (HiMessageContext)ctx;
/* 4145 */     HiMessage msg = msgCtx.getCurrentMsg();
/* 4146 */     HiETF root = msg.getETFBody();
/* 4147 */     HiETF node = root.getGrandChildNode(args[0]);
/* 4148 */     if (node == null) {
/* 4149 */       return "0";
/*      */     }
/* 4151 */     node.setName(args[1]);
/* 4152 */     return "1";
/*      */   }
/*      */ 
/*      */   public static String HZ2PY(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4163 */     if (args.length < 1)
/* 4164 */       throw new HiException("215110", "HZ2PY");
/* 4165 */     return args[0];
/*      */   }
/*      */ 
/*      */   public static String UCOMP3(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4178 */     if (args.length < 3)
/* 4179 */       throw new HiException("215110", "UCOMP3");
/* 4180 */     return args[0];
/*      */   }
/*      */ 
/*      */   public static String COMP3(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4193 */     if (args.length < 3)
/* 4194 */       throw new HiException("215110", "COMP3");
/* 4195 */     return args[0];
/*      */   }
/*      */ 
/*      */   private static boolean isByteEnv() {
/* 4199 */     return "byte".equals(HiICSProperty.getProperty("IBS_CHARACTER"));
/*      */   }
/*      */ 
/*      */   public static String Encrypt(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4212 */     if (args.length != 1)
/* 4213 */       throw new HiException("215110", "XOREncrypt");
/* 4214 */     return args[0];
/*      */   }
/*      */ 
/*      */   public static String Decrypt(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4228 */     if (args.length != 1)
/* 4229 */       throw new HiException("215110", "XOREncrypt");
/* 4230 */     return args[0];
/*      */   }
/*      */ 
/*      */   public static String PAD(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4245 */     if (args.length < 3)
/* 4246 */       throw new HiException("215110", "PAD");
/* 4247 */     if ("0".equals(args[1])) {
/* 4248 */       if (args.length < 4) {
/* 4249 */         return StringUtils.rightPad(args[0], NumberUtils.toInt(args[2]));
/*      */       }
/* 4251 */       return StringUtils.rightPad(args[0], NumberUtils.toInt(args[2]), args[4]);
/*      */     }
/*      */ 
/* 4254 */     if (args.length < 4) {
/* 4255 */       return StringUtils.leftPad(args[0], NumberUtils.toInt(args[2]));
/*      */     }
/* 4257 */     return StringUtils.leftPad(args[0], NumberUtils.toInt(args[2]), args[4]);
/*      */   }
/*      */ 
/*      */   private static boolean isHaveInCharSet(String str, String char_set)
/*      */   {
/* 4269 */     byte[] bytes1 = str.getBytes();
/* 4270 */     byte[] bytes2 = char_set.getBytes();
/* 4271 */     for (int i = 0; i < bytes1.length; ++i)
/*      */     {
/* 4273 */       for (int j = 0; j < bytes2.length; ++j)
/*      */       {
/* 4275 */         if (bytes1[i] == bytes2[j])
/*      */         {
/* 4277 */           return true;
/*      */         }
/*      */       }
/*      */     }
/* 4281 */     return false;
/*      */   }
/*      */ 
/*      */   private static boolean isSeriesSameChar(String str, int num)
/*      */   {
/* 4291 */     byte[] bytes = str.getBytes();
/* 4292 */     byte c = bytes[0];
/*      */ 
/* 4294 */     if (num < 2)
/*      */     {
/* 4296 */       return false;
/*      */     }
/*      */ 
/* 4299 */     int i = 1; for (int j = 1; i < bytes.length; ++i)
/*      */     {
/* 4301 */       if (bytes[i] == c)
/*      */       {
/* 4303 */         ++j;
/* 4304 */         if (j < num)
/*      */           continue;
/* 4306 */         return true;
/*      */       }
/*      */ 
/* 4311 */       j = 1;
/* 4312 */       c = bytes[i];
/*      */     }
/*      */ 
/* 4315 */     return false;
/*      */   }
/*      */ 
/*      */   public static String BINNOT(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4328 */     if (args.length != 1)
/* 4329 */       throw new HiException("215110", "BINNOT");
/* 4330 */     byte[] bytes = args[0].getBytes();
/* 4331 */     StringBuffer result = new StringBuffer();
/* 4332 */     for (int i = 0; i < bytes.length; ++i)
/*      */     {
/* 4334 */       if (bytes[i] == 48)
/* 4335 */         result.append('1');
/*      */       else {
/* 4337 */         result.append('0');
/*      */       }
/*      */     }
/* 4340 */     return result.toString();
/*      */   }
/*      */ 
/*      */   public static String BINAND(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4355 */     if (args.length != 2) {
/* 4356 */       throw new HiException("215110", "BINAND");
/*      */     }
/* 4358 */     byte[] bytes1 = args[0].getBytes();
/* 4359 */     byte[] bytes2 = args[1].getBytes();
/* 4360 */     int length = Math.min(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
/*      */ 
/* 4362 */     StringBuffer result = new StringBuffer();
/* 4363 */     for (int i = 0; i < length; ++i)
/*      */     {
/* 4365 */       if ((bytes1[i] == 48) && (bytes2[i] == 48))
/* 4366 */         result.append('0');
/*      */       else {
/* 4368 */         result.append('1');
/*      */       }
/*      */     }
/* 4371 */     return result.toString();
/*      */   }
/*      */ 
/*      */   public static String CPMAND(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4386 */     if (args.length != 2) {
/* 4387 */       throw new HiException("215110", "BINAND");
/*      */     }
/* 4389 */     byte[] bytes1 = args[0].getBytes();
/* 4390 */     byte[] bytes2 = args[1].getBytes();
/* 4391 */     int length = Math.min(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
/*      */ 
/* 4393 */     for (int i = 0; i < length; ++i)
/*      */     {
/* 4395 */       if ((bytes1[i] != 48) && (bytes2[i] != 48)) {
/* 4396 */         return new String("1");
/*      */       }
/*      */     }
/* 4399 */     return new String("0");
/*      */   }
/*      */ 
/*      */   public static String ISSIMPLEPASSWD(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4419 */     if (args.length != 1) {
/* 4420 */       throw new HiException("215110", "ISSIMPLEPASSWD");
/*      */     }
/*      */ 
/* 4423 */     if (args[0].length() != 6)
/*      */     {
/* 4425 */       return new String("1");
/*      */     }
/*      */ 
/* 4429 */     if (!(isHaveInCharSet(args[0], "0123456789")))
/*      */     {
/* 4431 */       return new String("2");
/*      */     }
/*      */ 
/* 4435 */     if (!(isHaveInCharSet(args[0], "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")))
/*      */     {
/* 4437 */       return new String("3");
/*      */     }
/*      */ 
/* 4441 */     if (!(isHaveInCharSet(args[0], "!@#$%^&*()")))
/*      */     {
/* 4443 */       return new String("4");
/*      */     }
/*      */ 
/* 4446 */     if (isSeriesSameChar(args[0], 3))
/*      */     {
/* 4448 */       return new String("5");
/*      */     }
/*      */ 
/* 4451 */     return new String("0");
/*      */   }
/*      */ 
/*      */   public static String GETRANDOM(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4464 */     if (args.length < 1) {
/* 4465 */       throw new HiException("215110", "GETRANDOM");
/*      */     }
/* 4467 */     int len = NumberUtils.toInt(StringUtils.trim(args[0]));
/*      */ 
/* 4469 */     return RandomStringUtils.randomNumeric(len);
/*      */   }
/*      */ 
/*      */   public static String CHAR(Object ctx, String[] args)
/*      */     throws HiException
/*      */   {
/* 4482 */     if (args.length < 1)
/* 4483 */       throw new HiException("215110", "CHAR");
/* 4484 */     char c = (char)NumberUtils.toInt(StringUtils.trim(args[0]));
/* 4485 */     return new String(new char[] { c });
/*      */   }
/*      */ }