 package com.hisun.hiexpression;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiDateUtils;
 import com.hisun.util.HiICSProperty;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileReader;
 import java.io.IOException;
 import java.io.UnsupportedEncodingException;
 import java.math.BigDecimal;
 import java.net.URLDecoder;
 import java.net.URLEncoder;
 import java.text.DecimalFormat;
 import java.text.ParseException;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.concurrent.ConcurrentHashMap;
 import java.util.jar.JarFile;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import org.apache.commons.codec.DecoderException;
 import org.apache.commons.codec.binary.Hex;
 import org.apache.commons.lang.CharUtils;
 import org.apache.commons.lang.RandomStringUtils;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.SystemUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.apache.commons.lang.time.DateFormatUtils;
 import org.apache.commons.lang.time.DateUtils;
 
 public class HiExpBasicFunctions
 {
   private static String DIGIT_UPPER = "零壹贰叁肆伍陆柒捌玖";
 
   private static String AMT_UPPER = "分角元拾佰仟万拾佰仟亿拾佰仟万拾佰仟亿";
 
   private static int[] ULEAD_MONTH_DAYS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
 
   private static int[] LEAD_MONTH_DAYS = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
 
   private static String EBCD_POSITIVE = "{ABCDEFGHI";
 
   private static String EBCD_NEGATIVE = "}JKLMNOPQR";
   private static final String FALSE = "0";
   private static final String TRUE = "1";
   private static ConcurrentHashMap memoryTD = new ConcurrentHashMap();
 
   public static String STRCAT(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 2)
       throw new HiException("215110", "STRCAT");
     StringBuffer result = new StringBuffer();
     for (int i = 0; i < args.length; ++i) {
       result.append(args[i]);
     }
     return result.toString();
   }
 
   public static String STRNCAT(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 3)
       throw new HiException("215110", "STRNCAT");
     return args[0] + StringUtils.substring(args[1], 0, NumberUtils.toInt(StringUtils.trim(args[2])));
   }
 
   public static String STRCMP(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "STRCMP");
     }
     return String.valueOf(args[0].compareTo(args[1]));
   }
 
   public static String SUBCMP(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 4)
       throw new HiException("215110", "SUBCMP");
     int offset = NumberUtils.toInt(StringUtils.trim(args[1])) - 1;
     if (offset < 0)
       offset = 0;
     if (offset > args[0].length()) {
       offset = args[0].length();
     }
     int length = NumberUtils.toInt(StringUtils.trim(args[2]));
     if (length < 0)
       length = 0;
     if (offset + length > args[0].length()) {
       length = args[0].length() - offset;
     }
     int ret = StringUtils.substring(args[0], offset, length).compareTo(args[3]);
 
     return String.valueOf(ret);
   }
 
   public static String LONGPOWER(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 2)
       throw new HiException("215110", "LONGPOWER");
     long result = NumberUtils.toLong(StringUtils.trim(args[0]));
     for (int i = 1; i < args.length; ++i) {
       result = ()Math.pow(result, NumberUtils.toInt(StringUtils.trim(args[i])));
     }
 
     return String.valueOf(result);
   }
 
   public static String SHORTPOWER(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 2)
       throw new HiException("215110", "SHORTPOWER");
     long result = NumberUtils.toLong(StringUtils.trim(args[0]));
     for (int i = 1; i < args.length; ++i) {
       result = ()Math.pow(result, NumberUtils.toInt(StringUtils.trim(args[i])));
     }
 
     return String.valueOf(result);
   }
 
   public static String INTCMP(Object ctx, String[] args)
     throws HiException
   {
     int i;
     if (args.length < 3) {
       throw new HiException("215110", "INTCMP");
     }
     long result = NumberUtils.toLong(StringUtils.trim(args[0]));
     int op = HiCmpMap.convert(StringUtils.trim(args[1]));
     switch (op)
     {
     case 1:
       for (i = 2; i < args.length; ++i) {
         if (result >= NumberUtils.toLong(StringUtils.trim(args[i])))
           return "0";
       }
       return "1";
     case 2:
       for (i = 2; i < args.length; ++i) {
         if (result > NumberUtils.toLong(StringUtils.trim(args[i])))
           return "0";
       }
       return "1";
     case 3:
       for (i = 2; i < args.length; ++i) {
         if (result != NumberUtils.toLong(StringUtils.trim(args[i])))
           return "0";
       }
       return "1";
     case 4:
       for (i = 2; i < args.length; ++i) {
         if (result == NumberUtils.toLong(StringUtils.trim(args[i])))
           return "0";
       }
       return "1";
     case 5:
       for (i = 2; i < args.length; ++i) {
         if (result <= NumberUtils.toLong(StringUtils.trim(args[i])))
           return "0";
       }
       return "1";
     case 6:
       for (i = 2; i < args.length; ++i) {
         if (result < NumberUtils.toLong(StringUtils.trim(args[i]))) {
           return "0";
         }
       }
       return "1";
     }
     throw new HiException("215111", "INTCMP", "op");
   }
 
   public static String DOUBLECMP(Object ctx, String[] args)
     throws HiException
   {
     int ret;
     int i;
     if (args.length < 3) {
       throw new HiException("215110", "DOUBLECMP");
     }
     double result = NumberUtils.toDouble(StringUtils.trim(args[0]));
     int op = HiCmpMap.convert(StringUtils.trim(args[1]));
 
     switch (op)
     {
     case 1:
       for (i = 2; i < args.length; ++i) {
         ret = NumberUtils.compare(result, NumberUtils.toDouble(StringUtils.trim(args[i])));
 
         if ((ret == 0) || (ret == 1))
           return "0";
       }
       return "1";
     case 2:
       for (i = 2; i < args.length; ++i) {
         ret = NumberUtils.compare(result, NumberUtils.toDouble(StringUtils.trim(args[i])));
 
         if (ret == 1)
           return "0";
       }
       return "1";
     case 3:
       for (i = 2; i < args.length; ++i) {
         ret = NumberUtils.compare(result, NumberUtils.toDouble(StringUtils.trim(args[i])));
 
         if (ret != 0)
           return "0";
       }
       return "1";
     case 4:
       for (i = 2; i < args.length; ++i) {
         ret = NumberUtils.compare(result, NumberUtils.toDouble(StringUtils.trim(args[i])));
 
         if (ret == 0)
           return "0";
       }
       return "1";
     case 5:
       for (i = 2; i < args.length; ++i) {
         ret = NumberUtils.compare(result, NumberUtils.toDouble(StringUtils.trim(args[i])));
 
         if ((ret == -1) || (ret == 0))
           return "0";
       }
       return "1";
     case 6:
       for (i = 2; i < args.length; ++i) {
         ret = NumberUtils.compare(result, NumberUtils.toDouble(StringUtils.trim(args[i])));
 
         if (ret == -1) {
           return "0";
         }
       }
       return "1";
     }
     throw new HiException("215111", "DOUBLECMP", "op");
   }
 
   public static String IS_NOEQUAL_STRING(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 2) {
       throw new HiException("215110", "IS_NOEQUAL_STRING");
     }
 
     for (int i = 0; i < args.length - 1; ++i) {
       if (StringUtils.equals(args[0], args[(i + 1)])) {
         return "0";
       }
     }
     return "1";
   }
 
   public static String IS_EQUAL_INT(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "IS_EQUAL_INT");
     }
 
     BigDecimal l1 = new BigDecimal(StringUtils.trim(args[0]));
     BigDecimal l2 = new BigDecimal(StringUtils.trim(args[1]));
     return ((l1.compareTo(l2) == 0) ? "1" : "0");
   }
 
   public static String IS_EQUAL_DOUBLE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "IS_EQUAL_DOUBLE");
     }
 
     BigDecimal l1 = new BigDecimal(StringUtils.trim(args[0]));
     BigDecimal l2 = new BigDecimal(StringUtils.trim(args[1]));
     return ((l1.compareTo(l2) == 0) ? "1" : "0");
   }
 
   public static String IS_EQUAL_STRING(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 2) {
       throw new HiException("215110", "IS_EQUAL_STRING");
     }
 
     for (int i = 0; i < args.length - 1; ++i) {
       if (StringUtils.equals(args[0], args[(i + 1)])) {
         return "1";
       }
     }
     return "0";
   }
 
   public static String ISNULL(Object ctx, String[] args)
     throws HiException
   {
     if (args.length == 0) {
       throw new HiException("215110", "ISNULL");
     }
     for (int i = 0; i < args.length; ++i) {
       if (!(StringUtils.isEmpty(args[i])))
         return "0";
     }
     return "1";
   }
 
   public static String ISCHIN(Object ctx, String[] args)
     throws HiException
   {
     if (args.length == 0) {
       throw new HiException("215110", "ISCHIN");
     }
 
     char[] buf = args[0].toCharArray();
     int sz = args[0].length();
     for (int i = 0; i < sz; ++i) {
       if (CharUtils.isAscii(buf[i])) {
         return "0";
       }
     }
     return "1";
   }
 
   public static String STRLEN(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "STRLEN");
     return String.valueOf(args[0].getBytes().length);
   }
 
   public static String SUBSTR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 3) {
       throw new HiException("215110", "SUBSTR");
     }
     int beginIndex = NumberUtils.toInt(StringUtils.trim(args[1])) - 1;
     byte[] bytes = args[0].getBytes();
     if (beginIndex < 0) {
       beginIndex = 0;
     }
     if (beginIndex > bytes.length) {
       beginIndex = bytes.length;
     }
     int length = NumberUtils.toInt(StringUtils.trim(args[2]));
     if (length < 0) {
       length = 0;
     }
     if (beginIndex + length > bytes.length) {
       length = bytes.length - beginIndex;
     }
     return new String(bytes, beginIndex, length);
   }
 
   public static String SUBRIGHT(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2)
       throw new HiException("215110", "SUBRIGHT");
     int length = NumberUtils.toInt(StringUtils.trim(args[1]));
     if (length < 0)
       length = 0;
     byte[] bytes = args[0].getBytes();
     if (length > bytes.length)
       length = bytes.length;
     return new String(bytes, bytes.length - length, length);
   }
 
   public static String ISNUMBER(Object ctx, String[] args)
     throws HiException
   {
     if (args.length == 0) {
       throw new HiException("215110", "ISNUMBER");
     }
     for (int i = 0; i < args.length; ++i) {
       if (!(StringUtils.isNumeric(args[i])))
         return "0";
     }
     return "1";
   }
 
   public static String GETCHARPOS(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 3)
       throw new HiException("215110", "GETCHARPOS");
     if ((args[1] == null) || (args[1] == null) || (args[2] == null)) {
       return "-1";
     }
     int len = NumberUtils.toInt(StringUtils.trim(args[1]));
     if (len == 0) {
       return "0";
     }
     for (int i = 0; (i < len) && (i < args[0].length()); ++i) {
       if (args[0].charAt(i) == args[2].charAt(0))
         return String.valueOf(i);
     }
     return "-1";
   }
 
   public static String GETCHARPOSFROM(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 3) {
       throw new HiException("215110", "GETCHARPOSFROM");
     }
     if ((args[0] == null) || (args[1] == null) || (args[2] == null)) {
       return "-1";
     }
     int ret = StringUtils.indexOf(args[0], args[2], NumberUtils.toInt(StringUtils.trim(args[1])));
 
     if (ret != -1) {
       ++ret;
     }
     return String.valueOf(ret);
   }
 
   public static String GETSTRPOS(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2)
       throw new HiException("215110", "GETSTRPOS");
     int idx = StringUtils.indexOf(args[0], args[1]);
     if (idx == -1) {
       return "-1";
     }
     return String.valueOf(idx + 1);
   }
 
   public static String DELSPACE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "DELSPACE");
     }
     if (StringUtils.equalsIgnoreCase("left", args[1]))
       return StringUtils.stripStart(args[0], " ");
     if (StringUtils.equalsIgnoreCase("right", args[1]))
       return StringUtils.stripEnd(args[0], " ");
     if (StringUtils.equalsIgnoreCase("both", args[1]))
       return StringUtils.strip(args[0]);
     if (StringUtils.equalsIgnoreCase("all", args[1])) {
       return StringUtils.replaceChars(args[0], " \t\f\r\n　", "");
     }
     return args[0];
   }
 
   public static String DELBOTHSPACE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "DELBOTHSPACE");
     }
     return StringUtils.strip(args[0]);
   }
 
   public static String DELRIGHTSPACE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "DELRIGHTSPACE");
     }
 
     return StringUtils.stripEnd(args[0], " ");
   }
 
   public static String SPACE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2)
       throw new HiException("215110", "SPACE");
     switch (NumberUtils.toInt(StringUtils.trim(args[1])))
     {
     case 0:
       return StringUtils.repeat(" ", NumberUtils.toInt(StringUtils.trim(args[0])));
     case 1:
       return StringUtils.repeat("　", NumberUtils.toInt(StringUtils.trim(args[0])));
     }
 
     throw new HiException("215111", "SPACE", "flag");
   }
 
   public static String INTTOSTR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2)
       throw new HiException("215110", "INTTOSTR");
     int length = NumberUtils.toInt(StringUtils.trim(args[1]));
 
     return StringUtils.leftPad(StringUtils.trim(args[0]), length, '0');
   }
 
   public static String ADDCHAR(Object ctx, String[] args)
     throws HiException
   {
     byte[] tmps;
     int i;
     if (args.length != 4)
       throw new HiException("215110", "ADDCHAR");
     if (isByteEnv());
     int length = NumberUtils.toInt(StringUtils.trim(args[1]));
     HiByteBuffer buf = new HiByteBuffer(10);
 
     switch (NumberUtils.toInt(StringUtils.trim(args[3])))
     {
     case 0:
       tmps = args[0].getBytes();
       for (i = 0; (i < tmps.length) && (i < length); ++i) {
         buf.append((char)tmps[i]);
       }
       for (; i < length; ++i) {
         buf.append(args[2]);
       }
       return buf.toString();
     case 1:
       tmps = args[0].getBytes();
       for (i = 0; i < length - tmps.length; ++i) {
         buf.append(args[2]);
       }
       for (int j = 0; (j < tmps.length) && (i < length); ++j) {
         buf.append((char)tmps[j]);
 
         ++i;
       }
 
       return buf.toString();
     }
 
     throw new HiException("215111", "ADDCHAR", "direction");
   }
 
   public static String CUTDATALENGTH(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "CUTDATALENGTH");
     }
     return StringUtils.substring(StringUtils.trim(args[0]), StringUtils.trim(args[0]).length() - NumberUtils.toInt(StringUtils.trim(args[1])));
   }
 
   public static String TOUPPER(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "TOUPPER");
     }
     return StringUtils.upperCase(args[0]);
   }
 
   public static String TOLOWER(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "TOLOWER");
     }
     return StringUtils.lowerCase(args[0]);
   }
 
   public static String REPSTR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 3) {
       throw new HiException("215110", "REPSTR");
     }
     return StringUtils.replaceOnce(args[0].trim(), args[1].trim(), args[2].trim());
   }
 
   public static String REPALLSTR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 3) {
       throw new HiException("215110", "REPALLSTR");
     }
     return StringUtils.replace(args[0], args[1], args[2]);
   }
 
   public static String DELCTRL(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "DELCTRL");
     }
     String str = args[0].trim();
     if (StringUtils.isEmpty(str))
       throw new HiException("215111", "DELCTRL", "str");
     char[] buf = str.toCharArray();
     for (int i = 0; i < buf.length; ++i) {
       if (CharUtils.isAsciiControl(buf[i])) {
         str = StringUtils.replace(str, Character.toString(buf[i]), "");
       }
     }
     return str;
   }
 
   public static String REPCTRL(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "REPCTRL");
     }
     String str = args[0];
     String ch = args[1];
     char[] buf = str.toCharArray();
     for (int i = 0; i < buf.length; ++i) {
       if (!(CharUtils.isAsciiControl(buf[i]))) {
         continue;
       }
       buf[i] = ch.charAt(0);
     }
 
     return new String(buf);
   }
 
   public static String REVERSAL(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "REVERSAL");
     StringBuffer result = new StringBuffer();
     for (int i = 0; i < args[0].length(); ++i) {
       switch (args[0].charAt(i))
       {
       case '0':
         result.append('1');
         break;
       case '1':
         result.append('0');
         break;
       default:
         result.append(args[0].charAt(i));
       }
     }
 
     return result.toString();
   }
 
   public static String LTRIM(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "LTRIM");
     }
     if ((args[0] == null) || (args[1] == null) || (args[0].length() == 0) || (args[1].length() == 0))
     {
       return args[0]; }
     for (int i = 0; i < args[0].length(); ++i) {
       if (args[0].charAt(i) != args[1].charAt(0)) {
         break;
       }
     }
 
     return args[0].substring(i);
   }
 
   public static String RTRIM(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "LTRIM");
     }
 
     if ((args[0] == null) || (args[1] == null) || (args[0].length() == 0) || (args[1].length() == 0))
     {
       return args[0];
     }
     for (int i = args[0].length() - 1; i > -1; --i) {
       if (args[0].charAt(i) != args[1].charAt(0)) {
         break;
       }
     }
 
     return args[0].substring(0, i + 1);
   }
 
   public static String TRIM(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "TRIM");
     }
     if ((args[0] == null) || (args[1] == null) || (args[0].length() == 0) || (args[1].length() == 0))
     {
       return args[0];
     }
     if (StringUtils.equals(args[1].trim(), "both"))
       return StringUtils.trim(args[0]);
     if (StringUtils.equals(args[1].trim(), "right"))
       return StringUtils.stripEnd(args[0], "");
     if (StringUtils.equals(args[1].trim(), "left"))
       return StringUtils.stripStart(args[0], "");
     if (StringUtils.equals(args[1].trim(), "all")) {
       String[] buf3 = args[0].split(" |　|\t|\n|\f");
       String ret = "";
       for (int j = 0; j < buf3.length; ++j) {
         ret = ret + buf3[j];
       }
       return ret;
     }
 
     for (int i = 0; i < args[0].length(); ++i) {
       if (args[0].charAt(i) != args[1].charAt(0))
         break;
     }
     for (int j = args[0].length() - 1; j > -1; --j) {
       if (args[0].charAt(j) != args[1].charAt(0))
         break;
     }
     return args[0].substring(i, j + 1);
   }
 
   public static String NullToEmpty(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "NullToEmpty");
     }
     if (args[0] == null) {
       return "";
     }
     return args[0];
   }
 
   public static String INSERTCHAR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 2) {
       throw new HiException("215110", "INSERTCHAR");
     }
 
     if (args[0] == null) {
       return "";
     }
 
     int iTimes = 1;
 
     int iStep = 0;
 
     if (args.length > 2) {
       iTimes = NumberUtils.toInt(StringUtils.trim(args[2]));
 
       if (args.length > 3) {
         iStep = NumberUtils.toInt(StringUtils.trim(args[3]));
       }
     }
 
     StringBuffer result = new StringBuffer(args[0].length());
 
     int interval = args[0].length();
     for (int i = 0; i < interval; ++i) {
       result.append(args[0].charAt(i));
       for (int j = 0; j < iTimes; ++j) {
         result.append(args[1]);
       }
       iTimes += iStep;
     }
 
     return result.toString();
   }
 
   public static String GETWORDDELIMITER(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 3) {
       throw new HiException("215110", "GETWORDDELIMITER");
     }
 
     String[] temp = StringUtils.splitByWholeSeparator(args[0], args[1]);
     int seq = NumberUtils.toInt(StringUtils.trim(args[2]));
     if (seq > temp.length)
       return "";
     return temp[(seq - 1)];
   }
 
   public static String LEFTSTR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2)
       throw new HiException("215110", "LEFTSTR");
     return StringUtils.left(args[0].trim(), NumberUtils.toInt(StringUtils.trim(args[1])));
   }
 
   public static String RIGHTSTR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2)
       throw new HiException("215110", "RIGHTSTR");
     return StringUtils.right(args[0], NumberUtils.toInt(StringUtils.trim(args[1])));
   }
 
   public static String STRGETCOUNT(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "STRGETCOUNT");
     }
     return String.valueOf(StringUtils.countMatches(args[0], args[1]));
   }
 
   public static String FABSAMT(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "FABSAMT");
     }
     String str = args[0].trim();
     String len = args[1].trim();
 
     if ((!(StringUtils.containsNone(str, ",."))) || (!(NumberUtils.isNumber(str)))) {
       throw new HiException("215111", "FABSAMT", "str");
     }
     DecimalFormat ft = new DecimalFormat("#");
 
     double dNum = NumberUtils.toDouble(StringUtils.trim(str));
     String ret = ft.format(Math.abs(dNum));
 
     if (ret.length() < Integer.parseInt(len.trim())) {
       StringBuffer buf = new StringBuffer(ret);
       for (int i = 0; i < Integer.parseInt(len.trim()) - ret.length(); ++i) {
         buf.insert(0, '0');
       }
       ret = buf.toString();
     }
     return ret;
   }
 
   public static String ISLEAPYEAR(Object ctx, String[] args)
     throws HiException
   {
     return ISLEAPYEAR(args);
   }
 
   public static String ISLEAPYEAR(String[] args) throws HiException {
     if (args.length != 1)
       throw new HiException("215110", "ISLEAPYEAR");
     String year = args[0].trim();
     if ((StringUtils.isEmpty(year)) || (!(StringUtils.isNumeric(year.trim())))) {
       throw new HiException("215111", "ISLEAPYEAR", "year");
     }
 
     int iYear = Integer.parseInt(year);
     if (iYear < 1900) {
       return "-2";
     }
     if (iYear % 4 == 0) {
       if (iYear % 100 == 0) {
         if (iYear % 400 == 0) {
           return "0";
         }
         return "-1";
       }
 
       return "0";
     }
 
     return "-1";
   }
 
   public static String FMTDATE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 3)
       throw new HiException("215110", "FMTDATE");
     String str = args[0].trim();
     int type1 = Integer.parseInt(args[1].trim());
     int type2 = Integer.parseInt(args[2].trim());
 
     if ((StringUtils.isEmpty(str)) || (type1 > 5) || (type1 < 0) || (type2 > 5) || (type2 < 0))
     {
       throw new HiException("215111", "FMTDATE", "date|type1{0~5}|type2{0~5}");
     }
 
     String pattern1 = ""; String pattern2 = "";
     String[] buf1 = { "yyyyMMdd", "yyyy/MM/dd", "MM/dd/yyyy", "yyyy.MM.dd", "yyyy-MM-dd", "yyyy年MM月dd天" };
 
     String[] buf2 = { "4Y2M2D", "4Y/2M/2D", "2M/2D/4Y", "4Y.2M.2D", "4Y-2M-2D", "4Y年2M月2D天" };
 
     str = str.trim();
 
     pattern1 = buf1[type1];
     pattern2 = buf1[type2];
 
     if ((StringUtils.isEmpty(pattern1)) || (StringUtils.isEmpty(pattern2))) {
       throw new HiException("215111", "FMTDATE", "type1|type2");
     }
 
     String[] pattern = { pattern1 };
     try
     {
       Date date = DateUtils.parseDate(str, pattern);
       return DateFormatUtils.format(date, pattern2);
     } catch (ParseException e) {
       throw new HiException("215112", "FMTDATE", e);
     }
   }
 
   public static String FMTSTR(Object ctx, String[] args) throws HiException {
     if (args.length != 2)
       throw new HiException("215110", "FMTSTR");
     String value = StringUtils.trim(args[0]);
     String fmt = StringUtils.trim(args[1]);
     StringBuffer buf = new StringBuffer();
     int j = 0;
     for (int i = 0; i < fmt.length(); ++i) {
       if (fmt.charAt(i) == 'n') {
         if (j >= value.length()) {
           continue;
         }
         buf.append(value.charAt(j));
         ++j;
       } else {
         buf.append(fmt.charAt(i));
       }
     }
     return buf.toString();
   }
 
   public static String CHECKDATE(Object ctx, String[] args)
     throws HiException
   {
     return CHECKDATE(args);
   }
 
   public static String CHECKDATE(String[] args) throws HiException {
     if (args.length != 1) {
       throw new HiException("215110", "CHECKDATE");
     }
     String str = args[0].trim();
     if (StringUtils.isEmpty(str)) {
       throw new HiException("215111", "CHECKDATE", "str");
     }
 
     try
     {
       str = str.trim();
       if (str.length() != 8) {
         return "-5";
       }
       int iYear = Integer.parseInt(str.substring(0, 4));
       int iMonth = Integer.parseInt(str.substring(4, 6));
       int iDay = Integer.parseInt(str.substring(6, 8));
 
       if (iYear <= 1900)
         return "-1";
       if ((iMonth < 1) || (iMonth > 12)) {
         return "-2";
       }
       if (ISLEAPYEAR(new String[] { Integer.toString(iYear) }).equals("0"))
       {
         if ((iDay > 0) && (iDay <= LEAD_MONTH_DAYS[(iMonth - 1)])) {
           return "0";
         }
         return "-3";
       }
 
       if ((iDay > 0) && (iDay <= ULEAD_MONTH_DAYS[(iMonth - 1)])) {
         return "0";
       }
       return "-3";
     }
     catch (NumberFormatException e)
     {
       throw new HiException("215112", "CHECKDATE", e);
     }
   }
 
   public static String GETDATETIME(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "GETDATETIME");
     }
     String[] buf1 = { "YYYY", "YY", "MM", "DD", "HH", "MI", "SS" };
     String[] buf2 = { "yyyy", "yy", "MM", "dd", "HH", "mm", "ss" };
     String str = args[0].trim();
     for (int i = 0; i < buf1.length; ++i) {
       str = StringUtils.replace(str, buf1[i], buf2[i]);
     }
 
     Calendar calendar = Calendar.getInstance();
     return DateFormatUtils.format(calendar.getTime(), str);
   }
 
   public static String GETDATETIME(Object ctx)
     throws HiException
   {
     return GETDATETIME(ctx, new String[] { "yyyyMMDDHHmmss" });
   }
 
   public static String GETDATE(Object ctx)
     throws HiException
   {
     return GETDATETIME(ctx, new String[] { "yyyyMMdd" });
   }
 
   public static String GETSECOND(Object ctx)
   {
     Date dt = new Date();
     long curSec = dt.getTime();
     curSec /= 1000L;
     return Long.toString(curSec);
   }
 
   public static String GETSECOND(Object ctx, String[] args)
     throws HiException
   {
     long curSec;
     if ((args == null) || (args.length == 0)) {
       Date dt = new Date();
       curSec = dt.getTime();
       curSec /= 1000L;
     } else {
       String format = "yyyyMMddHHmmss";
       if (args.length == 2) {
         format = args[1];
       }
       Date curDate = HiDateUtils.parseDate(args[0], format);
       curSec = curDate.getTime() / 1000L;
     }
     return Long.toString(curSec);
   }
 
   public static String CALCTIME(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 4)
       throw new HiException("215110", "CALCTIME");
     String datefmt = "MMddHHmmss";
     try
     {
       Date dt;
       if (args.length == 5) {
         datefmt = args[4];
         dt = DateUtils.parseDate(args[0], new String[] { args[4] });
       } else {
         dt = DateUtils.parseDate(args[0], new String[] { datefmt });
       }
       int num = NumberUtils.toInt(args[3]);
       if (num == 0) {
         return args[0];
       }
       int sign = (args[1].equals("+")) ? 1 : -1;
       num *= sign;
       if (args[2].equals("y"))
         dt = DateUtils.addYears(dt, num);
       else if (args[2].equals("M"))
         dt = DateUtils.addMonths(dt, num);
       else if (args[2].equals("d"))
         dt = DateUtils.addDays(dt, num);
       else if (args[2].equals("h"))
         dt = DateUtils.addHours(dt, num);
       else if (args[2].equals("m"))
         dt = DateUtils.addMinutes(dt, num);
       else if (args[2].equals("s"))
         dt = DateUtils.addSeconds(dt, num);
       else {
         throw new HiException("220026", args[2]);
       }
       return DateFormatUtils.format(dt, datefmt);
     } catch (ParseException e) {
       throw new HiException("220026", args[0]);
     }
   }
 
   public static String CALCDATE(Object ctx, String[] args)
     throws HiException
   {
     int[] buf3 = { 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 3, 2, 1 };
 
     if (args.length != 4) {
       throw new HiException("215110", "CALCDATE");
     }
     String str = args[0].trim();
     String op = args[1].trim();
     String obj = args[2].trim();
     String num = args[3].trim();
 
     if ((StringUtils.isEmpty(str)) || (str.length() != 8))
       throw new HiException("215111", "CALCDATE", "str");
     try {
       String[] pattern = { "yyyyMMdd" };
       Date dt = DateUtils.parseDate(str, pattern);
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(dt);
       int iYear = calendar.get(1);
       int iMonth = calendar.get(2) + 1;
       int iDay = calendar.get(5);
       int iNum = NumberUtils.toInt(num);
       if (obj.equals("y")) {
         if (op.equals("+"))
           iYear += iNum;
         else if (op.equals("-")) {
           iYear -= iNum;
         }
         if (iMonth == 2)
           if (ISMONTHEND(new String[] { str }).equals("1"))
             if (ISLEAPYEAR(new String[] { Integer.toString(iYear) }).equals("0"))
             {
               iDay = 29;
             }
             else iDay = 28;
       }
       else
       {
         if (obj.equals("m")) {
           if (op.equals("+"))
             iMonth += iNum;
           else if (op.equals("-")) {
             iMonth -= iNum;
           }
           calendar.set(iYear, iMonth - 1, iDay);
           iYear = calendar.get(1);
           if (iYear < 1900) {
             throw new HiException("215111", "CALCDATE", "year");
           }
 
           int index = iMonth;
           if (index <= 0) {
             index = buf3[(Math.abs(iMonth) % 12)];
           }
           else if (index % 12 == 0)
             index = 12;
           else {
             index %= 12;
           }
 
           if (ISLEAPYEAR(new String[] { Integer.toString(iYear) }).equals("0"))
           {
             if (iDay > LEAD_MONTH_DAYS[(index - 1)]) {
               iDay = LEAD_MONTH_DAYS[(index - 1)];
             }
           }
           else if (iDay > ULEAD_MONTH_DAYS[(index - 1)]) {
             iDay = ULEAD_MONTH_DAYS[(index - 1)];
           }
 
           calendar.set(iYear, index - 1, iDay);
           return DateFormatUtils.format(calendar.getTime(), "yyyyMMdd"); }
         if (obj.equals("d")) {
           if (op.equals("+"))
             iDay += iNum;
           else if (op.equals("-")) {
             iDay -= iNum;
           }
         }
       }
       if (iYear < 1900) {
         throw new HiException("215111", "CALCDATE", "year");
       }
       calendar.set(iYear, iMonth - 1, iDay);
       return DateFormatUtils.format(calendar.getTime(), "yyyyMMdd");
     } catch (ParseException e) {
       throw new HiException("215112", "CALCDATE", e);
     }
   }
 
   public static String ISYEAREND(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "ISYEAREND");
     }
     String str = args[0].trim();
     if (!(StringUtils.isEmpty(str))) if (CHECKDATE(new String[] { str }).equals("0"))
         break label54;
     return "0";
     try {
       label54: String[] pt = { "yyyyMMdd" };
       Date dt = DateUtils.parseDate(str, pt);
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(dt);
       int iMonth = calendar.get(2) + 1;
       int iDay = calendar.get(5);
       if ((iMonth == 12) && (iDay == 31)) {
         return "1";
       }
       return "0";
     }
     catch (ParseException e) {
       throw new HiException("215112", "ISYEAREND", e);
     }
   }
 
   public static String ISQUARTEREND(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "ISQUATEREND");
     }
 
     String str = args[0].trim();
     if (!(StringUtils.isEmpty(str))) if (CHECKDATE(new String[] { str }).equals("0"))
         break label54;
     return "0";
     try {
       label54: String[] pt = { "yyyyMMdd" };
       Date dt = DateUtils.parseDate(str, pt);
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(dt);
       int iMonth = calendar.get(2) + 1;
       int iDay = calendar.get(5);
       if ((iMonth == 3) && (iDay == 31))
         return "1";
       if ((iMonth == 6) && (iDay == 30))
         return "1";
       if ((iMonth == 9) && (iDay == 30))
         return "1";
       if ((iMonth == 12) && (iDay == 31)) {
         return "1";
       }
       return "0";
     } catch (ParseException e) {
       throw new HiException("215112", "ISQUATEREND", e);
     }
   }
 
   public static String ISMONTHEND(Object ctx, String[] args)
     throws HiException
   {
     return ISMONTHEND(args);
   }
 
   public static String ISMONTHEND(String[] args) throws HiException {
     if (args.length != 1) {
       throw new HiException("215110", "ISMONTHEND");
     }
     String str = args[0].trim();
     if ((StringUtils.isEmpty(str)) || (!(CHECKDATE(args).equals("0"))))
       return "0";
     str = str.trim();
     try {
       String[] pt = { "yyyyMMdd" };
       Date dt = DateUtils.parseDate(str, pt);
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(dt);
       int iYear = calendar.get(1);
       int iMonth = calendar.get(2) + 1;
       int iDay = calendar.get(5);
 
       if (ISLEAPYEAR(new String[] { Integer.toString(iYear) }).equals("0"))
       {
         if (LEAD_MONTH_DAYS[(iMonth - 1)] == iDay) {
           return "1";
         }
         return "0";
       }
       if (ULEAD_MONTH_DAYS[(iMonth - 1)] == iDay) {
         return "1";
       }
       return "0";
     }
     catch (ParseException e) {
       throw new HiException("215112", "ISMONTHEND", e);
     }
   }
 
   public static String ISWEEKEND(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "ISWEEKEND");
     }
     String str = args[0].trim();
     if ((StringUtils.isEmpty(str)) || (!(CHECKDATE(args).equals("0"))))
       return "0";
     try
     {
       Date dt = DateUtils.parseDate(str, new String[] { "yyyyMMdd" });
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(dt);
       int week = calendar.get(7);
       if ((week == 7) || (week == 1)) {
         return "1";
       }
       return "0";
     }
     catch (ParseException e) {
       throw new HiException("215112", "ISMONTHEND", e);
     }
   }
 
   public static String DIFFDATE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "DIFFDATE");
     }
     String date1 = args[0].trim();
     String date2 = args[1].trim();
     if ((StringUtils.isEmpty(date1)) || (StringUtils.isEmpty(date2))) {
       throw new HiException("215111", "DIFFDATE", "date1|date2");
     }
 
     if (CHECKDATE(new String[] { date1 }).equals("0")) if (CHECKDATE(new String[] { date2 }).equals("0"))
         break label112;
     throw new HiException("215111", "DIFFDATE", "date1|date2");
 
     label112: String[] pt = { "yyyyMMdd" };
     try {
       Date dt1 = DateUtils.parseDate(date1, pt);
       Date dt2 = DateUtils.parseDate(date2, pt);
       long diff = Math.abs(dt2.getTime() - dt1.getTime());
       return Long.toString(diff / 86400000L);
     } catch (ParseException e) {
       throw new HiException("215112", "ISMONTHEND", e);
     }
   }
 
   public static String CALCMONTH(Object ctx, String[] args)
     throws HiException
   {
     int diff = 0;
 
     if ((args.length < 2) || (args.length > 3)) {
       throw new HiException("215110", "CALCMONTH");
     }
     String date1 = args[0].trim();
     String date2 = args[1].trim();
     String flag = null;
     if (args.length == 3)
       flag = args[2].trim();
     else {
       flag = "0";
     }
 
     if ((StringUtils.isEmpty(date1)) || (StringUtils.isEmpty(date2)) || (StringUtils.isEmpty(flag)))
     {
       throw new HiException("215111", "CALCMONTH", "date1|date2");
     }
 
     if (CHECKDATE(new String[] { date1 }).equals("0")) if (CHECKDATE(new String[] { date2 }).equals("0"))
         break label159;
     throw new HiException("215111", "CALCMONTH", "date1|date2");
     try
     {
       label159: String[] pt = { "yyyyMMdd" };
       Date dt1 = DateUtils.parseDate(date1, pt);
       Date dt2 = DateUtils.parseDate(date2, pt);
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(dt1);
       int iYear1 = calendar.get(1);
       int iMonth1 = calendar.get(2) + 1;
       int iDay1 = calendar.get(5);
       calendar.setTime(dt2);
       int iYear2 = calendar.get(1);
       int iMonth2 = calendar.get(2) + 1;
       int iDay2 = calendar.get(5);
 
       if ("0".equals(flag.trim())) {
         diff = Math.abs(iYear2 - iYear1);
         if (dt2.getTime() >= dt1.getTime()) {
           diff = diff * 12 + iMonth2 - iMonth1;
           if (iDay2 < iDay1)
             --diff;
         }
         else {
           diff = diff * 12 + iMonth1 - iMonth2;
           if (iDay1 < iDay2)
             --diff;
         }
       }
       else if ("1".equals(flag.trim())) {
         diff = Math.abs(iYear2 - iYear1);
         if (dt2.getTime() >= dt1.getTime())
           diff = diff * 12 + iMonth2 - iMonth1;
         else
           diff = diff * 12 + iMonth1 - iMonth2;
       }
       else {
         throw new HiException("215111", "CALCMONTH", "flag");
       }
 
       return Integer.toString(diff);
     } catch (ParseException e) {
       throw new HiException("215112", "CALCMONTH", e);
     }
   }
 
   public static String CALCYEAR(Object ctx, String[] args)
     throws HiException
   {
     int diff = 0;
 
     if ((args.length < 2) || (args.length > 3)) {
       throw new HiException("215110", "CALCYEAR");
     }
     String date1 = args[0].trim();
     String date2 = args[1].trim();
     String flag = null;
     if (args.length == 3)
       flag = args[2].trim();
     else {
       flag = "0";
     }
 
     if ((StringUtils.isEmpty(date1)) || (StringUtils.isEmpty(date2)) || (StringUtils.isEmpty(flag)))
     {
       throw new HiException("215111", "CALCYEAR", "date1|date2|flag");
     }
 
     if (CHECKDATE(new String[] { date1 }).equals("0")) if (CHECKDATE(new String[] { date2 }).equals("0"))
         break label159;
     throw new HiException("215111", "CALCYEAR", "date1|date2");
     try
     {
       label159: String[] pt = { "yyyyMMdd" };
       Date dt1 = DateUtils.parseDate(date1, pt);
       Date dt2 = DateUtils.parseDate(date2, pt);
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(dt1);
       int iYear1 = calendar.get(1);
       int iMonth1 = calendar.get(2) + 1;
       int iDay1 = calendar.get(5);
       calendar.setTime(dt2);
       int iYear2 = calendar.get(1);
       int iMonth2 = calendar.get(2) + 1;
       int iDay2 = calendar.get(5);
 
       if (flag.equals("0")) {
         diff = Math.abs(iYear2 - iYear1);
         if (dt2.getTime() >= dt1.getTime()) {
           if ((iMonth2 < iMonth1) || (iDay2 < iDay1)) {
             --diff;
           }
         }
         else if ((iMonth1 < iMonth2) || (iDay1 < iDay2)) {
           --diff;
         }
       }
       else if (flag.equals("1")) {
         diff = Math.abs(iYear2 - iYear1);
       }
       return Integer.toString(diff);
     }
     catch (ParseException e) {
       throw new HiException("215112", "CALCYEAR", e);
     }
   }
 
   public static String NORMAL_TO_COBOL(Object ctx, String[] args)
     throws HiException
   {
     int i;
     String ret = "";
     if (args.length != 2) {
       throw new HiException("215110", "NORMAL_TO_COBOL");
     }
 
     String amt = args[0].trim();
     String len = args[1].trim();
 
     if (!(StringUtils.isNumeric(len.trim()))) {
       throw new HiException("215111", "NORMAL_TO_COBOL", "len");
     }
 
     if (StringUtils.isEmpty(amt.trim())) {
       return StringUtils.leftPad("", NumberUtils.toInt(len), '0');
     }
 
     if ((StringUtils.contains(amt, ',')) || (!(NumberUtils.isNumber(StringUtils.trim(amt)))))
     {
       throw new HiException("215111", "NORMAL_TO_COBOL", "amt");
     }
 
     int iLen = NumberUtils.toInt(len);
     if (iLen <= 0) {
       throw new HiException("215111", "NORMAL_TO_COBOL", "len");
     }
 
     double fAmt = NumberUtils.toDouble(amt);
     int flag = 0;
 
     if (amt.indexOf(45) != -1) {
       flag = -1;
     }
 
     DecimalFormat df = new DecimalFormat("#");
     ret = df.format(Math.abs(fAmt));
 
     if (ret.length() < iLen) {
       StringBuffer buf = new StringBuffer(ret);
       for (i = 0; i < iLen - ret.length(); ++i) {
         buf.insert(0, "0");
       }
       ret = buf.toString();
     }
     if (flag == -1) {
       char[] buf1 = ret.toCharArray();
       i = 48;
       int j = 112;
       int k = buf1[(buf1.length - 1)];
       buf1[(buf1.length - 1)] = (char)(k - i + j);
 
       ret = new String(buf1);
     }
 
     return ret;
   }
 
   public static String COBOL_TO_NORMAL(Object ctx, String[] args)
     throws HiException
   {
     int flag = 1; int iLen = 0;
     String ret = "";
 
     if (args.length != 2) {
       throw new HiException("215110", "COBOL_TO_NORMAL");
     }
 
     String amt = StringUtils.trim(args[0]);
     int len = NumberUtils.toInt(StringUtils.trim(args[1]));
 
     if (amt == null) {
       return StringUtils.leftPad("", len, '0');
     }
 
     if ((amt.charAt(amt.length() - 1) >= 'p') && (amt.charAt(amt.length() - 1) <= 'y'))
     {
       char[] buf = amt.toCharArray();
       flag = -1;
       buf[(amt.length() - 1)] = (char)(buf[(amt.length() - 1)] - 'p' + 48);
 
       amt = new String(buf);
     }
 
     double dAmt = NumberUtils.toDouble(amt);
     DecimalFormat ft = new DecimalFormat("#");
     ret = ft.format(dAmt);
     StringBuffer buf1 = new StringBuffer(ret);
     if (ret.length() < len - 1) {
       for (int i = 0; i < len - 1 - ret.length(); ++i) {
         buf1.insert(0, "0");
       }
       ret = buf1.toString();
     }
     if (flag == -1)
       buf1.insert(0, '-');
     else if (buf1.length() < len) {
       buf1.insert(0, '0');
     }
 
     return buf1.toString();
   }
 
   public static String NORMAL_TO_EBCD(Object ctx, String[] args)
     throws HiException
   {
     int flag = 1; int iLen = 0;
     String ret = "";
 
     if (args.length != 2) {
       throw new HiException("215110", "NORMAL_TO_EBCD");
     }
 
     String amt = args[0].trim();
     String len = args[1].trim();
 
     if ((!(NumberUtils.isNumber(amt))) || (!(NumberUtils.isNumber(len)))) {
       throw new HiException("215111", "NORMAL_TO_EBCD", "amt|len");
     }
 
     if (StringUtils.contains(amt, ',')) {
       throw new HiException("215111", "NORMAL_TO_EBCD", "amt");
     }
 
     double dNum = NumberUtils.toDouble(amt);
     iLen = NumberUtils.toInt(len);
     if (dNum < 0.0D)
       flag = -1;
     DecimalFormat ft = new DecimalFormat("#");
     ret = ft.format(Math.abs(dNum));
     char[] buf = ret.toCharArray();
     if (flag == -1) {
       if (buf[(buf.length - 1)] == '0')
         buf[(buf.length - 1)] = '}';
       else {
         buf[(buf.length - 1)] = (char)(buf[(buf.length - 1)] - '1' + 74);
       }
 
     }
     else if (buf[(buf.length - 1)] == '0')
       buf[(buf.length - 1)] = '{';
     else {
       buf[(buf.length - 1)] = (char)(buf[(buf.length - 1)] - '1' + 65);
     }
 
     ret = new String(buf);
 
     if (ret.length() < iLen) {
       StringBuffer retBuf = new StringBuffer(ret);
       for (int i = 0; i < iLen - ret.length(); ++i) {
         retBuf.insert(0, '0');
       }
       ret = retBuf.toString();
     }
 
     return ret;
   }
 
   public static String AMTADDDOT(Object ctx, String[] args)
     throws HiException
   {
     return AMTADDDOT(args);
   }
 
   public static String AMTADDDOT(String[] args) throws HiException {
     if (args.length != 1) {
       throw new HiException("215110", "AMTADDDOT");
     }
     String str = args[0].trim();
     if (!(NumberUtils.isNumber(str))) {
       throw new HiException("215111", "AMTADDDOT", "str");
     }
 
     StringBuffer buf = new StringBuffer(str);
     int flag = 1;
     if (buf.charAt(0) == '-') {
       flag = -1;
       buf = buf.delete(0, 1);
     }
     if (buf.length() >= 3)
       buf.insert(buf.length() - 2, '.');
     else if (buf.length() == 2)
       buf.insert(0, "0.");
     else if (buf.length() == 1)
       buf.insert(0, "0.0");
     else if (buf.length() == 0) {
       buf.insert(0, '0');
     }
     if (flag == -1) {
       buf.insert(0, '-');
     }
     return buf.toString();
   }
 
   public static String AMTTOCAP(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "AMTTOCAP");
     }
     String amt = args[0].trim();
     if (!(StringUtils.isNumeric(amt))) {
       throw new HiException("215111", "AMTTOCAP", "amt");
     }
     if (amt.length() > 15) {
       throw new HiException("215111", "AMTTOCAP", "amt-15");
     }
 
     char[] Amount = amt.toCharArray();
     StringBuffer ChAmount = new StringBuffer();
     int cunt = 1000; int flag = 0;
     int endzero = 0;
 
     int len = amt.length();
 
     for (int i = 0; i < len; ++i) {
       if ((Amount[i] < '0') || (Amount[i] > '9')) {
         throw new HiException("215111", "AMTTOCAP", "amt");
       }
 
       if ((cunt == 1000) && (Amount[i] != '0')) {
         cunt = i;
       }
       if ((Amount[i] != '0') && (i < len - 2)) {
         endzero = i;
       }
 
     }
 
     int count_zero = 0;
     for (i = cunt; i < len; ++i)
     {
       char tmp;
       flag = Amount[i] - '0';
       if (flag == 0) {
         ++count_zero;
         if ((len - i != 3) && (len - i != 7) && (len - i != 11) && (len - i != 15))
         {
           if ((i < len - 1) && (Amount[(i + 1)] != '0') && (i <= endzero)) {
             tmp = DIGIT_UPPER.charAt(flag);
             ChAmount.append(tmp);
             count_zero = 0; } else {
             if ((i != len - 2) || (Amount[(len - 1)] == '0'))
               continue;
             ChAmount.append("零");
             count_zero = 0;
           }
         } else if ((count_zero == 4) && (len - i == 7))
         {
           count_zero = 0;
         }
         else
         {
           tmp = AMT_UPPER.charAt(len - i - 1);
           ChAmount.append(tmp);
           count_zero = 0;
         }
       } else {
         count_zero = 0;
         tmp = DIGIT_UPPER.charAt(flag);
         ChAmount.append(tmp);
         tmp = AMT_UPPER.charAt(len - i - 1);
         ChAmount.append(tmp);
       }
     }
 
     if (flag == 0)
     {
       ChAmount.append("整");
     }
 
     return ChAmount.toString();
   }
 
   public static String AMTDELZERO(Object ctx, String[] args)
     throws HiException
   {
     return AMTDELZERO(args);
   }
 
   public static String AMTDELZERO(String[] args) throws HiException {
     if (args.length != 1) {
       throw new HiException("215110", "AMTDELZERO");
     }
     String amt = args[0].trim();
     if (!(NumberUtils.isNumber(amt))) {
       throw new HiException("215111", "AMTDELZERO", "amt");
     }
 
     double dNum = NumberUtils.toDouble(amt);
     DecimalFormat ft = new DecimalFormat("#");
     return ft.format(dNum);
   }
 
   public static String AMTFMT(Object ctx, String[] args)
     throws HiException
   {
     int j;
     if (args.length != 1) {
       throw new HiException("215110", "AMTFMT");
     }
     String amt = args[0].trim();
     if (!(NumberUtils.isNumber(amt))) {
       throw new HiException("215111", "AMTFMT", "amt");
     }
     int flag = 1;
 
     amt = AMTDELZERO(new String[] { amt });
     if (amt.charAt(0) == '-') {
       flag = -1;
       amt = amt.substring(1);
     }
 
     StringBuffer buf = new StringBuffer(amt);
     if (amt.length() < 3) {
       amt = AMTADDDOT(new String[] { amt });
       buf.replace(0, buf.length(), amt);
       if (flag == -1) {
         buf.insert(0, '-');
       }
       return buf.toString();
     }
 
     int len = amt.length();
     if ((len - 2) % 3 == 0)
       j = (len - 2) / 3 - 1;
     else {
       j = (len - 2) / 3;
     }
 
     for (int i = 0; i < j; ++i) {
       buf.insert(len - 2 - ((i + 1) * 3), ',');
     }
     buf.insert(buf.length() - 2, '.');
     if (flag == -1) {
       buf.insert(0, '-');
     }
     return buf.toString();
   }
 
   public static String AMTSIMPLEFMT(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "AMTSIMPLEFMT");
     }
 
     String amt = args[0].trim();
     if (!(NumberUtils.isNumber(amt))) {
       throw new HiException("215110", "AMTSIMPLEFMT", "amt");
     }
     if ((StringUtils.contains(amt, '.')) || (StringUtils.contains(amt, ','))) {
       throw new HiException("215110", "AMTSIMPLEFMT", "amt");
     }
 
     int flag = 1;
 
     amt = AMTDELZERO(new String[] { amt });
     if (amt.charAt(0) == '-') {
       flag = -1;
       amt = amt.substring(1);
     }
     StringBuffer buf = new StringBuffer(amt);
     amt = AMTADDDOT(new String[] { amt });
     buf = new StringBuffer(amt);
     if (flag == -1) {
       buf.insert(0, '-');
     }
     return buf.toString();
   }
 
   public static String CYCAMTFALSE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "CYCAMTFALSE");
     }
 
     String amt = args[1].trim();
     String cyccod = args[0].trim();
     if ((!(NumberUtils.isNumber(amt))) || (StringUtils.isEmpty(cyccod))) {
       throw new HiException("215110", "CYCAMTFALSE", "amt|cyccod");
     }
 
     int len = 0;
     String ret = "0";
     for (int i = 0; i < 2; ++i) {
       if (amt.charAt(amt.length() - i - 1) != '0') {
         ++len;
       }
     }
 
     if ((((StringUtils.equals(cyccod, "ITL")) || (StringUtils.equals(cyccod, "JPY")))) && 
       (len != 0)) {
       ret = "1";
     }
 
     return ret;
   }
 
   public static String CSUBSTR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 3) {
       throw new HiException("215110", "CSUBSTR");
     }
     if (StringUtils.isEmpty(args[0])) {
       return "";
     }
 
     int idx1 = NumberUtils.toInt(args[1].trim());
     int idx2 = NumberUtils.toInt(args[2].trim());
 
     HiByteBuffer bb = new HiByteBuffer(args[0].getBytes());
 
     byte[] subBytes = bb.subbyte(idx1, idx2);
 
     return new String(subBytes);
   }
 
   public static String AMTPOWER(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "AMTPOWER");
     }
     BigDecimal d = new BigDecimal(args[0].trim());
     int i = NumberUtils.toInt(args[1].trim());
 
     return String.valueOf(d.movePointRight(i).longValue());
   }
 
   public static String GETENV(Object ctx, String[] args)
     throws HiException
   {
     return GETENV(args);
   }
 
   public static String GETENV(String[] args) throws HiException {
     if (args.length != 1)
       throw new HiException("215110", "GETENV");
     try
     {
       String value = System.getenv(args[0].trim());
       if (StringUtils.isEmpty(value)) {
         value = System.getProperty(args[0].trim());
       }
       return value; } catch (Throwable t) {
     }
     return System.getProperty(args[0].trim());
   }
 
   public static String ADDAMT(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "ADDAMT");
     int value = 0;
     for (int i = 0; i < args.length; ++i) {
       if (args[i] == null)
         continue;
       value += NumberUtils.toInt(args[i].trim());
     }
     return String.valueOf(value);
   }
 
   public static String DELCHAR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "DELCHAR");
     }
     String value = args[0];
 
     String delStr = new String(new char[] { (char)NumberUtils.toInt(args[1]) });
 
     value = StringUtils.replace(value, delStr, "");
     return value;
   }
 
   public static String DELBOTHCHAR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "DELBOTHCHAR");
     }
 
     String value = args[0];
 
     String delStr = new String(new char[] { (char)NumberUtils.toInt(args[1]) });
 
     value = StringUtils.removeStart(value, delStr);
     value = StringUtils.removeEnd(value, delStr);
     return value;
   }
 
   public static String SUBAMT(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "SUBAMT");
     }
 
     BigDecimal atm1 = new BigDecimal(args[0].trim());
     BigDecimal atm2 = new BigDecimal(args[1].trim());
     BigDecimal atm3 = atm1.subtract(atm2);
     return atm3.toString();
   }
 
   public static String HEX2STR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "HEX2STR");
     try {
       return new String(Hex.decodeHex(args[0].toCharArray()));
     } catch (DecoderException e) {
       throw new HiException("215110", e.getMessage());
     }
   }
 
   public static String STR2HEX(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "HEX2STR");
     return new String(Hex.encodeHex(args[0].getBytes()));
   }
 
   public static String REPCHAR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 3)
       throw new HiException("215110", "REPCHAR");
     return StringUtils.replace(args[0], args[1], args[2]);
   }
 
   public static String EBCD_TO_NORMAL(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "EBCD_TO_NORMAL");
     }
 
     int outPutLen = Integer.parseInt(args[1]);
     if (StringUtils.isBlank(args[0])) {
       return StringUtils.leftPad("", outPutLen, "0");
     }
 
     String ebcd = args[0];
 
     char lab = ebcd.charAt(ebcd.length() - 1);
     String normal = ebcd.substring(0, ebcd.length() - 1);
 
     int index = EBCD_POSITIVE.indexOf(lab);
     if (index >= 0) {
       normal = normal + index;
       normal = StringUtils.leftPad(normal, outPutLen, '0');
       return normal;
     }
 
     index = EBCD_NEGATIVE.indexOf(lab);
     if (index >= 0) {
       normal = normal + index;
 
       normal = "-" + StringUtils.leftPad(normal, outPutLen - 1, '0');
       return normal;
     }
 
     return args[0];
   }
 
   public static String BIN2HEX(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "BIN2HEX");
     try {
       return Integer.toHexString(Integer.valueOf(args[0].trim(), 2).intValue());
     }
     catch (NumberFormatException e) {
       throw new HiException("", "含有非二进制的数字");
     }
   }
 
   public static String HEX2BIN(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "HEX2BIN");
     String binStr = "";
     try {
       binStr = Integer.toBinaryString(Integer.valueOf(args[0].trim(), 16).intValue());
     }
     catch (NumberFormatException e) {
       throw new HiException("", "含有非十六进制的数字");
     }
 
     return binStr;
   }
 
   public static String FADD(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 3) {
       throw new HiException("215110", "FADD");
     }
     int argNum = args.length;
 
     int precision = Integer.parseInt(args[(argNum - 1)].trim());
     double total = 0.0D;
     --argNum;
     for (int i = 0; i < argNum; ++i) {
       total += Double.parseDouble(args[i].trim());
     }
     BigDecimal tmp = BigDecimal.valueOf(total);
     tmp = tmp.setScale(precision, 4);
     return tmp.toString();
   }
 
   public static String FSUB(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 3) {
       throw new HiException("215110", "FSUB");
     }
     int argNum = args.length;
 
     int precision = Integer.parseInt(args[(argNum - 1)]);
     double total = NumberUtils.toDouble(args[0]);
 
     --argNum;
     for (int i = 1; i < argNum; ++i) {
       total -= NumberUtils.toDouble(args[i]);
     }
 
     BigDecimal tmp = BigDecimal.valueOf(total);
     tmp = tmp.setScale(precision, 4);
     return tmp.toString();
   }
 
   public static String FMUL(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 3) {
       throw new HiException("215110", "FMUL");
     }
     int argNum = args.length;
 
     int precision = NumberUtils.toInt(args[(argNum - 1)]);
     double total = NumberUtils.toDouble(args[0]);
 
     --argNum;
     for (int i = 1; i < argNum; ++i)
     {
       total *= NumberUtils.toDouble(args[i]);
     }
     BigDecimal tmp = BigDecimal.valueOf(total);
     tmp = tmp.setScale(precision, 4);
     return tmp.toString();
   }
 
   public static String FDIV(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 3) {
       throw new HiException("215110", "FDIV");
     }
     int argNum = args.length;
 
     int precision = NumberUtils.toInt(args[(argNum - 1)]);
     double total = NumberUtils.toDouble(args[0]);
 
     --argNum;
     for (int i = 1; i < argNum; ++i)
     {
       total /= NumberUtils.toDouble(args[i]);
     }
 
     BigDecimal tmp = BigDecimal.valueOf(total);
     tmp = tmp.setScale(precision, 4);
     return tmp.toString();
   }
 
   public static String FPOW(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 3) {
       throw new HiException("215110", "FPOW");
     }
 
     int precision = NumberUtils.toInt(args[2]);
 
     int times = NumberUtils.toInt(args[1]);
     double total = NumberUtils.toDouble(args[0]);
 
     for (int i = 1; i < times; ++i) {
       total *= total;
     }
     BigDecimal tmp = BigDecimal.valueOf(total);
     tmp = tmp.setScale(precision, 4);
     return tmp.toString();
   }
 
   public static String DATETOCAP(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1) {
       throw new HiException("215110", "DATETOCAP");
     }
     if (args[0].trim().length() != 8) {
       throw new HiException("215115", args[0].trim());
     }
 
     char[] dateArray = args[0].trim().toCharArray();
     int flag = 0;
     String year = ""; String month = ""; String day = "";
     for (int i = 0; i < 4; ++i) {
       if ((dateArray[i] < '0') || (dateArray[i] > '9')) {
         throw new HiException("215115", args[0].trim());
       }
 
       flag = dateArray[i] - '0';
       year = year + DIGIT_UPPER.charAt(flag);
     }
     year = year + "年";
 
     for (i = 4; i < 6; ++i) {
       if ((dateArray[i] < '0') || (dateArray[i] > '9')) {
         throw new HiException("215115", args[0].trim());
       }
 
       flag = dateArray[i] - '0';
       month = month + DIGIT_UPPER.charAt(flag);
     }
     month = month + "月";
 
     for (i = 6; i < 8; ++i) {
       if ((dateArray[i] < '0') || (dateArray[i] > '9')) {
         throw new HiException("215115", args[0].trim());
       }
 
       flag = dateArray[i] - '0';
       day = day + DIGIT_UPPER.charAt(flag);
     }
     day = day + "日";
     return year + month + day;
   }
 
   public static String GETFILELINES(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1) {
       throw new HiException("215110", "GETFILELINES");
     }
     String file = args[0].trim();
     File filepath = new File(file);
 
     if (!(filepath.isAbsolute())) {
       String[] homeArgs = { "HWORKDIR" };
       String root = GETENV(homeArgs);
       if (root != null) {
         if (root.endsWith("/"))
           root = root + file;
         else {
           root = root + file + "/";
         }
         file = root;
       } else {
         throw new HiException("215118", "HOME", "GETFILELINES");
       }
 
     }
 
     int total = 0;
     BufferedReader br = null;
     try {
       br = new BufferedReader(new FileReader(file));
       while (br.ready()) {
         br.readLine();
         ++total;
       }
     } catch (FileNotFoundException e) {
     }
     catch (IOException e) {
     }
     finally {
       try {
         if (br != null)
           br.close();
       } catch (IOException e) {
       }
     }
     return String.valueOf(total);
   }
 
   public static String GETFILESIZE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1) {
       throw new HiException("215110", "GETFILESIZE");
     }
     String file = args[0].trim();
     File filepath = new File(file);
 
     if (!(filepath.isAbsolute())) {
       String[] homeArgs = { "HWORKDIR" };
       String root = GETENV(homeArgs);
       if (root != null)
         filepath = new File(root, file);
       else {
         throw new HiException("215118", "HOME", "GETFILESIZE");
       }
 
     }
 
     long total = 0L;
 
     total = filepath.length();
 
     return String.valueOf(total);
   }
 
   public static String TODBC(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "TODBC");
     if (args[0] == null) {
       return null;
     }
     return HiExpBasicHelper.todbc(args[0]);
   }
 
   public static String TOSBC(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "TOSBC");
     if (args[0] == null) {
       return null;
     }
     return HiExpBasicHelper.tosbc(args[0]);
   }
 
   public static String CHAOSCODE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "CHAOSCODE");
     if (args[0] == null) {
       return null;
     }
     String str = args[0];
 
     StringBuffer outStr = new StringBuffer();
 
     int len = str.length();
 
     for (int i = 0; i < len; ++i) {
       String tStr = str.substring(i, i + 1);
       try {
         byte[] b = tStr.getBytes("GBK");
         if ((b[0] > -1) || (b[1] > -1)) {
           tStr = "　";
           outStr.append(tStr);
         } else {
           outStr.append(tStr);
         }
       } catch (UnsupportedEncodingException e) {
         throw new HiException(e);
       }
     }
     return outStr.toString();
   }
 
   public static String CHNCONVERT(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1) {
       throw new HiException("215110", "CHNCONVERT");
     }
     if (args[0] == null) {
       return null;
     }
     String str = args[0].trim();
     byte[] b = null;
     try {
       b = str.getBytes("GB2312");
     } catch (UnsupportedEncodingException e) {
       return null;
     }
     if (b == null) {
       return null;
     }
 
     HiByteBuffer bb = new HiByteBuffer(64);
     for (int i = 0; i < b.length; ++i) {
       bb.append(b[i]);
 
       if (b[i] <= -1) {
         bb.append(32);
         bb.append(32);
         bb.append(2);
       }
     }
     return bb.toString("GB2312");
   }
 
   public static String IS_MATCH(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 2)
       throw new HiException("215110", "IS_MATCH");
     boolean ignoreCase = true;
     if ((args.length == 3) && 
       ("1".equals(args[2]))) {
       ignoreCase = false;
     }
 
     Pattern p = null;
 
     if (ignoreCase)
       p = Pattern.compile(args[1]);
     else {
       p = Pattern.compile(args[1], 2);
     }
     Matcher m = p.matcher(args[0]);
 
     if (m.matches()) {
       return "1";
     }
     return "0";
   }
 
   public static String CONDITION3(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 3) {
       throw new HiException("215110", "CONDITION3");
     }
 
     boolean r = args[0].equals("1");
     return ((r) ? args[1] : args[2]);
   }
 
   public static String IsExistNode(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1) {
       throw new HiException("215110", "IsExistNode");
     }
     for (int i = 0; i < args.length; ++i) {
       if (args[i] == null)
         return "0";
     }
     return "1";
   }
 
   public static String RANDOM(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 2) {
       throw new HiException("215110", "RANDOM");
     }
     int len = NumberUtils.toInt(StringUtils.trim(args[0]));
 
     args[1] = StringUtils.trim(args[1]);
 
     if (StringUtils.equals(args[1], "0"))
       return RandomStringUtils.randomAlphanumeric(len);
     if (StringUtils.equals(args[1], "1")) {
       return RandomStringUtils.randomAlphabetic(len);
     }
     return RandomStringUtils.randomNumeric(len);
   }
 
   public static String ETF(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "ETF");
     return ((HiMessageContext)ctx).getCurrentMsg().getETFBody().getGrandChildValue(args[0]);
   }
 
   public static String BLANK(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "BLANK");
     int len = NumberUtils.toInt(args[0]);
     return StringUtils.leftPad("", len);
   }
 
   public static String DATEPROC(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "DATEPROC");
     return args[0].replaceAll("-", "");
   }
 
   public static String AMTPROC(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "AMTPROC");
     return args[0].replaceAll("[,.]", "");
   }
 
   public static String DECODE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "ENCODE");
     try {
       return URLDecoder.decode(args[0], "UTF-8");
     } catch (UnsupportedEncodingException e) {
       throw new HiException(e);
     }
   }
 
   public static String ENCODE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "ENCODE");
     try {
       return URLEncoder.encode(args[0], "UTF-8");
     } catch (UnsupportedEncodingException e) {
       throw new HiException(e);
     }
   }
 
   public static String XOREncrypt(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "XOREncrypt");
     }
     String data = args[0];
     StringBuffer result = new StringBuffer();
     String key = "hisun";
     int j = 0;
     for (int i = 0; i < data.length(); ++i) {
       if (j == key.length())
         j = 0;
       result.append(StringUtils.leftPad(Integer.toHexString(data.charAt(i) ^ key.charAt(j)), 2, '0'));
 
       ++j;
     }
     return result.toString();
   }
 
   public static String XORDecrypt(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "XORDecrypt");
     }
     String data = args[0];
     StringBuffer result = new StringBuffer();
     String key = "hisun";
     int j = 0;
     for (int i = 0; i < data.length(); i += 2) {
       if (j == key.length())
         j = 0;
       int c = Integer.parseInt(data.substring(i, i + 2), 16);
       result.append((char)(c ^ key.charAt(j)));
       ++j;
     }
     return result.toString();
   }
 
   public static String HEX2DEC(Object object, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "HEX2DEC");
     }
     long l = Long.parseLong(args[0], 16);
 
     return String.valueOf(l);
   }
 
   public static String DEC2HEX(Object object, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "DEC2HEX");
     }
     return Long.toString(Long.parseLong(args[0]), 16);
   }
 
   public static String ISALPHANUMERIC(Object object, String[] args)
     throws HiException
   {
     if (args.length == 0) {
       throw new HiException("215110", "ISALPHANUMERIC");
     }
 
     char[] chs = null;
     for (int i = 0; i < args.length; ++i) {
       chs = args[i].toCharArray();
       for (int j = 0; j < chs.length; ++j) {
         if (!(CharUtils.isAsciiAlphanumeric(chs[j]))) {
           return "0";
         }
       }
     }
     return "1";
   }
 
   public static String FORMATTIME(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "FORMATTIME");
     String pattern = "yyyy/MM/dd HH:mm:ss";
     if (args.length > 1) {
       pattern = args[1];
     }
     return DateFormatUtils.format(Long.parseLong(args[0]), pattern);
   }
 
   public static String ISFILE(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "ISFILE");
     for (int i = 0; i < args.length; ++i) {
       File f = null;
       if (!(args[i].startsWith(SystemUtils.FILE_SEPARATOR)))
         f = new File(HiICSProperty.getWorkDir() + "/" + args[i]);
       else {
         f = new File(args[i]);
       }
       if (!(f.isFile())) {
         return "0";
       }
     }
     return "1";
   }
 
   public static String ISDIR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "ISDIR");
     for (int i = 0; i < args.length; ++i) {
       File f = null;
       if (!(args[i].startsWith(SystemUtils.FILE_SEPARATOR)))
         f = new File(HiICSProperty.getWorkDir() + "/" + args[i]);
       else {
         f = new File(args[i]);
       }
       if (!(f.isDirectory())) {
         return "0";
       }
     }
     return "1";
   }
 
   public static String ISJAR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "ISJAR");
     for (int i = 0; i < args.length; ++i) {
       String name = args[i];
       if (!(name.startsWith(SystemUtils.FILE_SEPARATOR)))
         name = HiICSProperty.getWorkDir() + "/" + name;
       try
       {
         JarFile f = new JarFile(name);
         f.close();
       } catch (IOException e) {
         return "0";
       }
     }
     return "1";
   }
 
   public static String SaveToTDS(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 2)
       throw new HiException("215110", "SaveToTDS");
     boolean overlay = false;
     if ((args.length >= 3) && 
       ("1".equals(args[2]))) {
       overlay = true;
     }
 
     if ((memoryTD.containsKey(args[0])) && (!(overlay))) {
       return "0";
     }
 
     memoryTD.put(args[0], args[1]);
     return "1";
   }
 
   public static String DeleteTDS(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1) {
       throw new HiException("215110", "LoadFromTDS");
     }
     if (!(memoryTD.containsKey(args[0]))) {
       return "1";
     }
     memoryTD.remove(args[0]);
     return "1";
   }
 
   public static String LoadFromTDS(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1) {
       throw new HiException("215110", "LoadFromTDS");
     }
     if (!(memoryTD.containsKey(args[0]))) {
       return "2";
     }
     String value = (String)memoryTD.get(args[0]);
     if ((args.length == 2) && 
       ("1".equals(args[1]))) {
       memoryTD.remove(args[0]);
     }
 
     return value;
   }
 
   public static String GetETFValue(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1) {
       throw new HiException("215110", "GetETFValue");
     }
     HiMessageContext msgCtx = (HiMessageContext)ctx;
     HiMessage msg = msgCtx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     String value = root.getGrandChildValue(args[0]);
     if (value == null) {
       return "";
     }
     return value;
   }
 
   public static String SetETFValue(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 2) {
       throw new HiException("215110", "setETFValue");
     }
     HiMessageContext msgCtx = (HiMessageContext)ctx;
     HiMessage msg = msgCtx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     HiETF node = root.getGrandChildNode(args[0]);
     if (node == null) {
       return "0";
     }
     node.setValue(args[1]);
     return "1";
   }
 
   public static String GetETFName(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "getETFName");
     HiMessageContext msgCtx = (HiMessageContext)ctx;
     HiMessage msg = msgCtx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     HiETF node = root.getGrandChildNode(args[0]);
     if (node == null) {
       return "";
     }
     return node.getName();
   }
 
   public static String SetETFName(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 2)
       throw new HiException("215110", "setETFName");
     HiMessageContext msgCtx = (HiMessageContext)ctx;
     HiMessage msg = msgCtx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     HiETF node = root.getGrandChildNode(args[0]);
     if (node == null) {
       return "0";
     }
     node.setName(args[1]);
     return "1";
   }
 
   public static String HZ2PY(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "HZ2PY");
     return args[0];
   }
 
   public static String UCOMP3(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 3)
       throw new HiException("215110", "UCOMP3");
     return args[0];
   }
 
   public static String COMP3(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 3)
       throw new HiException("215110", "COMP3");
     return args[0];
   }
 
   private static boolean isByteEnv() {
     return "byte".equals(HiICSProperty.getProperty("IBS_CHARACTER"));
   }
 
   public static String Encrypt(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "XOREncrypt");
     return args[0];
   }
 
   public static String Decrypt(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "XOREncrypt");
     return args[0];
   }
 
   public static String PAD(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 3)
       throw new HiException("215110", "PAD");
     if ("0".equals(args[1])) {
       if (args.length < 4) {
         return StringUtils.rightPad(args[0], NumberUtils.toInt(args[2]));
       }
       return StringUtils.rightPad(args[0], NumberUtils.toInt(args[2]), args[4]);
     }
 
     if (args.length < 4) {
       return StringUtils.leftPad(args[0], NumberUtils.toInt(args[2]));
     }
     return StringUtils.leftPad(args[0], NumberUtils.toInt(args[2]), args[4]);
   }
 
   private static boolean isHaveInCharSet(String str, String char_set)
   {
     byte[] bytes1 = str.getBytes();
     byte[] bytes2 = char_set.getBytes();
     for (int i = 0; i < bytes1.length; ++i)
     {
       for (int j = 0; j < bytes2.length; ++j)
       {
         if (bytes1[i] == bytes2[j])
         {
           return true;
         }
       }
     }
     return false;
   }
 
   private static boolean isSeriesSameChar(String str, int num)
   {
     byte[] bytes = str.getBytes();
     byte c = bytes[0];
 
     if (num < 2)
     {
       return false;
     }
 
     int i = 1; for (int j = 1; i < bytes.length; ++i)
     {
       if (bytes[i] == c)
       {
         ++j;
         if (j < num)
           continue;
         return true;
       }
 
       j = 1;
       c = bytes[i];
     }
 
     return false;
   }
 
   public static String BINNOT(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1)
       throw new HiException("215110", "BINNOT");
     byte[] bytes = args[0].getBytes();
     StringBuffer result = new StringBuffer();
     for (int i = 0; i < bytes.length; ++i)
     {
       if (bytes[i] == 48)
         result.append('1');
       else {
         result.append('0');
       }
     }
     return result.toString();
   }
 
   public static String BINAND(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "BINAND");
     }
     byte[] bytes1 = args[0].getBytes();
     byte[] bytes2 = args[1].getBytes();
     int length = Math.min(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
 
     StringBuffer result = new StringBuffer();
     for (int i = 0; i < length; ++i)
     {
       if ((bytes1[i] == 48) && (bytes2[i] == 48))
         result.append('0');
       else {
         result.append('1');
       }
     }
     return result.toString();
   }
 
   public static String CPMAND(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 2) {
       throw new HiException("215110", "BINAND");
     }
     byte[] bytes1 = args[0].getBytes();
     byte[] bytes2 = args[1].getBytes();
     int length = Math.min(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
 
     for (int i = 0; i < length; ++i)
     {
       if ((bytes1[i] != 48) && (bytes2[i] != 48)) {
         return new String("1");
       }
     }
     return new String("0");
   }
 
   public static String ISSIMPLEPASSWD(Object ctx, String[] args)
     throws HiException
   {
     if (args.length != 1) {
       throw new HiException("215110", "ISSIMPLEPASSWD");
     }
 
     if (args[0].length() != 6)
     {
       return new String("1");
     }
 
     if (!(isHaveInCharSet(args[0], "0123456789")))
     {
       return new String("2");
     }
 
     if (!(isHaveInCharSet(args[0], "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")))
     {
       return new String("3");
     }
 
     if (!(isHaveInCharSet(args[0], "!@#$%^&*()")))
     {
       return new String("4");
     }
 
     if (isSeriesSameChar(args[0], 3))
     {
       return new String("5");
     }
 
     return new String("0");
   }
 
   public static String GETRANDOM(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1) {
       throw new HiException("215110", "GETRANDOM");
     }
     int len = NumberUtils.toInt(StringUtils.trim(args[0]));
 
     return RandomStringUtils.randomNumeric(len);
   }
 
   public static String CHAR(Object ctx, String[] args)
     throws HiException
   {
     if (args.length < 1)
       throw new HiException("215110", "CHAR");
     char c = (char)NumberUtils.toInt(StringUtils.trim(args[0]));
     return new String(new char[] { c });
   }
 }