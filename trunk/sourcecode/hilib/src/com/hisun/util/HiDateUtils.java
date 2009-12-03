/*     */ package com.hisun.util;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.lang.time.DateUtils;
/*     */ 
/*     */ public class HiDateUtils
/*     */ {
/*     */   public static boolean chkDateFormat(String date)
/*     */   {
/*     */     try
/*     */     {
/*  29 */       if ((null == date) || ("".equals(date)) || (!(date.matches("[0-9]{8}")))) {
/*  30 */         return false;
/*     */       }
/*  32 */       int year = Integer.parseInt(date.substring(0, 4));
/*  33 */       int month = Integer.parseInt(date.substring(4, 6)) - 1;
/*  34 */       int day = Integer.parseInt(date.substring(6));
/*  35 */       Calendar calendar = GregorianCalendar.getInstance();
/*     */ 
/*  37 */       calendar.setLenient(false);
/*  38 */       calendar.set(1, year);
/*  39 */       calendar.set(2, month);
/*  40 */       calendar.set(5, day);
/*     */ 
/*  42 */       calendar.get(1);
/*     */     } catch (IllegalArgumentException e) {
/*  44 */       return false;
/*     */     }
/*  46 */     return true;
/*     */   }
/*     */ 
/*     */   public static String getMonthBegin(String strdate)
/*     */     throws HiException
/*     */   {
/*  57 */     java.util.Date date = parseDate(strdate);
/*     */ 
/*  59 */     return formatDateByFormat(date, "yyyy-MM") + "-01";
/*     */   }
/*     */ 
/*     */   public static String getMonthEnd(String strdate)
/*     */     throws HiException
/*     */   {
/*  71 */     java.util.Date date = parseDate(getMonthBegin(strdate));
/*  72 */     Calendar calendar = Calendar.getInstance();
/*  73 */     calendar.setTime(date);
/*  74 */     calendar.add(2, 1);
/*  75 */     calendar.add(6, -1);
/*  76 */     return formatDate(calendar.getTime());
/*     */   }
/*     */ 
/*     */   public static String formatDate(java.util.Date date)
/*     */     throws HiException
/*     */   {
/*  87 */     return formatDateByFormat(date, "yyyy-MM-dd");
/*     */   }
/*     */ 
/*     */   public static String formatDateByFormat(java.util.Date date, String format)
/*     */     throws HiException
/*     */   {
/*  99 */     String result = "";
/* 100 */     if (date != null)
/*     */     {
/*     */       try
/*     */       {
/* 104 */         SimpleDateFormat sdf = new SimpleDateFormat(format);
/* 105 */         result = sdf.format(date);
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 109 */         throw new HiException(ex);
/*     */       }
/*     */     }
/* 112 */     return result;
/*     */   }
/*     */ 
/*     */   public static java.util.Date parseDate(String dateStr, String format)
/*     */     throws HiException
/*     */   {
/*     */     java.util.Date date;
/*     */     try
/*     */     {
/* 129 */       date = DateUtils.parseDate(dateStr, new String[] { format });
/*     */     }
/*     */     catch (ParseException e) {
/* 132 */       throw new HiException(e);
/*     */     }
/* 134 */     return date;
/*     */   }
/*     */ 
/*     */   public static java.util.Date parseDate(String dateStr)
/*     */     throws HiException
/*     */   {
/* 144 */     return parseDate(dateStr, "yyyyMMdd");
/*     */   }
/*     */ 
/*     */   public static java.util.Date parseDate(java.sql.Date date) {
/* 148 */     return date;
/*     */   }
/*     */ 
/*     */   public static java.sql.Date parseSqlDate(java.util.Date date) {
/* 152 */     if (date != null) {
/* 153 */       return new java.sql.Date(date.getTime());
/*     */     }
/* 155 */     return null;
/*     */   }
/*     */ 
/*     */   public static java.sql.Date parseSqlDate(String dateStr, String format) throws HiException {
/* 159 */     java.util.Date date = parseDate(dateStr, format);
/* 160 */     return parseSqlDate(date);
/*     */   }
/*     */ 
/*     */   public static java.sql.Date parseSqlDate(String dateStr) throws HiException {
/* 164 */     return parseSqlDate(dateStr, "yyyy/MM/dd");
/*     */   }
/*     */ 
/*     */   public static Timestamp parseTimestamp(String dateStr, String format)
/*     */     throws HiException
/*     */   {
/* 170 */     java.util.Date date = parseDate(dateStr, format);
/* 171 */     if (date != null) {
/* 172 */       long t = date.getTime();
/* 173 */       return new Timestamp(t);
/*     */     }
/* 175 */     return null;
/*     */   }
/*     */ 
/*     */   public static Timestamp parseTimestamp(String dateStr) throws HiException {
/* 179 */     return parseTimestamp(dateStr, "yyyy/MM/dd HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static String format(java.util.Date date, String format)
/*     */   {
/* 192 */     String result = "";
/*     */     try {
/* 194 */       if (date != null) {
/* 195 */         DateFormat df = new SimpleDateFormat(format);
/* 196 */         result = df.format(date);
/*     */       }
/*     */     } catch (Exception e) {
/*     */     }
/* 200 */     return result;
/*     */   }
/*     */ 
/*     */   public static String format(java.util.Date date) {
/* 204 */     return format(date, "yyyy/MM/dd");
/*     */   }
/*     */ 
/*     */   public static int getYear(java.util.Date date)
/*     */   {
/* 215 */     Calendar c = Calendar.getInstance();
/* 216 */     c.setTime(date);
/* 217 */     return c.get(1);
/*     */   }
/*     */ 
/*     */   public static int getMonth(java.util.Date date)
/*     */   {
/* 228 */     Calendar c = Calendar.getInstance();
/* 229 */     c.setTime(date);
/* 230 */     return (c.get(2) + 1);
/*     */   }
/*     */ 
/*     */   public static int getDay(java.util.Date date)
/*     */   {
/* 241 */     Calendar c = Calendar.getInstance();
/* 242 */     c.setTime(date);
/* 243 */     return c.get(5);
/*     */   }
/*     */ 
/*     */   public static int getHour(java.util.Date date)
/*     */   {
/* 254 */     Calendar c = Calendar.getInstance();
/* 255 */     c.setTime(date);
/* 256 */     return c.get(11);
/*     */   }
/*     */ 
/*     */   public static int getMinute(java.util.Date date)
/*     */   {
/* 267 */     Calendar c = Calendar.getInstance();
/* 268 */     c.setTime(date);
/* 269 */     return c.get(12);
/*     */   }
/*     */ 
/*     */   public static int getSecond(java.util.Date date)
/*     */   {
/* 280 */     Calendar c = Calendar.getInstance();
/* 281 */     c.setTime(date);
/* 282 */     return c.get(13);
/*     */   }
/*     */ 
/*     */   public static long getMillis(java.util.Date date)
/*     */   {
/* 293 */     Calendar c = Calendar.getInstance();
/* 294 */     c.setTime(date);
/* 295 */     return c.getTimeInMillis();
/*     */   }
/*     */ 
/*     */   public static String getDate(java.util.Date date)
/*     */   {
/* 306 */     return format(date, "yyyy/MM/dd");
/*     */   }
/*     */ 
/*     */   public static String getTime(java.util.Date date)
/*     */   {
/* 317 */     return format(date, "HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static String getDateTime(java.util.Date date)
/*     */   {
/* 328 */     return format(date, "yyyy/MM/dd HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static java.util.Date addDate(java.util.Date date, int day)
/*     */   {
/* 341 */     Calendar c = Calendar.getInstance();
/* 342 */     c.setTimeInMillis(getMillis(date) + day * 24L * 3600L * 1000L);
/* 343 */     return c.getTime();
/*     */   }
/*     */ 
/*     */   public static int diffDate(java.util.Date date, java.util.Date date1)
/*     */   {
/* 356 */     return (int)((getMillis(date) - getMillis(date1)) / 86400000L);
/*     */   }
/*     */ 
/*     */   public static int diffDate(String dateStr1, String dateStr2)
/*     */     throws HiException
/*     */   {
/* 370 */     java.util.Date date1 = parseDate(dateStr1);
/* 371 */     java.util.Date date2 = parseDate(dateStr2);
/* 372 */     return (int)((getMillis(date1) - getMillis(date2)) / 86400000L);
/*     */   }
/*     */ 
/*     */   public static boolean isSameWeekDates(java.util.Date date1, java.util.Date date2)
/*     */   {
/* 377 */     Calendar cal1 = Calendar.getInstance();
/* 378 */     Calendar cal2 = Calendar.getInstance();
/* 379 */     cal1.setTime(date1);
/* 380 */     cal2.setTime(date2);
/* 381 */     int subYear = cal1.get(1) - cal2.get(1);
/* 382 */     if (0 == subYear) {
/* 383 */       if (cal1.get(3) != cal2.get(3)) break label114;
/* 384 */       return true;
/*     */     }
/* 386 */     if ((1 == subYear) && (11 == cal2.get(2)))
/*     */     {
/* 388 */       if (cal1.get(3) != cal2.get(3)) break label114;
/* 389 */       return true;
/*     */     }
/*     */ 
/* 393 */     label114: return ((-1 != subYear) || (11 != cal1.get(2)) || 
/* 392 */       (cal1.get(3) != cal2.get(3)));
/*     */   }
/*     */ 
/*     */   public static String getSeqWeek()
/*     */   {
/* 401 */     Calendar c = Calendar.getInstance(Locale.CHINA);
/* 402 */     String week = Integer.toString(c.get(3));
/* 403 */     if (week.length() == 1) week = "0" + week;
/* 404 */     String year = Integer.toString(c.get(1));
/* 405 */     return year + week;
/*     */   }
/*     */ 
/*     */   public static String getMonday(java.util.Date date)
/*     */   {
/* 411 */     Calendar c = Calendar.getInstance();
/* 412 */     c.setTime(date);
/* 413 */     c.set(7, 2);
/* 414 */     return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
/*     */   }
/*     */ 
/*     */   public static String getFriday(java.util.Date date)
/*     */   {
/* 420 */     Calendar c = Calendar.getInstance();
/* 421 */     c.setTime(date);
/* 422 */     c.set(7, 6);
/* 423 */     return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
/*     */   }
/*     */ }