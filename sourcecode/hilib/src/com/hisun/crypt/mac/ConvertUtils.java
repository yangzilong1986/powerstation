/*     */ package com.hisun.crypt.mac;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public abstract class ConvertUtils
/*     */ {
/*  17 */   private static final DecimalFormat simpleFormat = new DecimalFormat("####");
/*     */ 
/*     */   public static final boolean objectToBoolean(Object o) {
/*  20 */     return ((o != null) ? Boolean.valueOf(o.toString()).booleanValue() : false);
/*     */   }
/*     */ 
/*     */   public static final int objectToInt(Object o) {
/*  24 */     if (o instanceof Number)
/*  25 */       return ((Number)o).intValue();
/*     */     try {
/*  27 */       if (o == null) {
/*  28 */         return -1;
/*     */       }
/*  30 */       return Integer.parseInt(o.toString()); } catch (NumberFormatException e) {
/*     */     }
/*  32 */     return -1;
/*     */   }
/*     */ 
/*     */   public static final short objectToShort(Object o)
/*     */   {
/*  37 */     if (o instanceof Number)
/*  38 */       return ((Number)o).shortValue();
/*     */     try {
/*  40 */       if (o == null) {
/*  41 */         return -1;
/*     */       }
/*  43 */       return Short.parseShort(o.toString()); } catch (NumberFormatException e) {
/*     */     }
/*  45 */     return -1;
/*     */   }
/*     */ 
/*     */   public static final double objectToDouble(Object o)
/*     */   {
/*  50 */     if (o instanceof Number)
/*  51 */       return ((Number)o).doubleValue();
/*     */     try {
/*  53 */       if (o == null) {
/*  54 */         return -1.0D;
/*     */       }
/*  56 */       return Double.parseDouble(o.toString()); } catch (NumberFormatException e) {
/*     */     }
/*  58 */     return -1.0D;
/*     */   }
/*     */ 
/*     */   public static final long objectToLong(Object o)
/*     */   {
/*  64 */     if (o instanceof Number)
/*  65 */       return ((Number)o).longValue();
/*     */     try {
/*  67 */       if (o == null) {
/*  68 */         return -1L;
/*     */       }
/*  70 */       return Long.parseLong(o.toString()); } catch (NumberFormatException e) {
/*     */     }
/*  72 */     return -1L;
/*     */   }
/*     */ 
/*     */   public static final String objectToString(Object obj, DecimalFormat fmt)
/*     */   {
/*  78 */     fmt.setDecimalSeparatorAlwaysShown(false);
/*  79 */     if (obj instanceof Double)
/*  80 */       return fmt.format(((Double)obj).doubleValue());
/*  81 */     if (obj instanceof Long) {
/*  82 */       return fmt.format(((Long)obj).longValue());
/*     */     }
/*  84 */     return obj.toString();
/*     */   }
/*     */ 
/*     */   public static final Object getObjectValue(String value)
/*     */   {
/*     */     try {
/*  90 */       return Long.valueOf(value);
/*     */     }
/*     */     catch (NumberFormatException e) {
/*     */       try {
/*  94 */         return Double.valueOf(value); } catch (NumberFormatException e) { }
/*     */     }
/*  96 */     return value;
/*     */   }
/*     */ 
/*     */   public static String longToSimpleString(long value)
/*     */   {
/* 101 */     return simpleFormat.format(value);
/*     */   }
/*     */ 
/*     */   public static String asHex(byte[] hash) {
/* 105 */     return toHex(hash);
/*     */   }
/*     */ 
/*     */   public static String toHex(byte[] input) {
/* 109 */     if (input == null)
/* 110 */       return null;
/* 111 */     StringBuffer output = new StringBuffer(input.length * 2);
/* 112 */     for (int i = 0; i < input.length; ++i) {
/* 113 */       int current = input[i] & 0xFF;
/* 114 */       if (current < 16)
/* 115 */         output.append("0");
/* 116 */       output.append(Integer.toString(current, 16));
/*     */     }
/*     */ 
/* 119 */     return output.toString();
/*     */   }
/*     */ 
/*     */   public static byte[] fromHex(String input) {
/* 123 */     if (input == null)
/* 124 */       return null;
/* 125 */     byte[] output = new byte[input.length() / 2];
/* 126 */     for (int i = 0; i < output.length; ++i) {
/* 127 */       output[i] = (byte)Integer.parseInt(input.substring(i * 2, (i + 1) * 2), 16);
/*     */     }
/* 129 */     return output;
/*     */   }
/*     */ 
/*     */   public static String stringToHexString(String input, String encoding) throws UnsupportedEncodingException
/*     */   {
/* 134 */     return ((input != null) ? toHex(input.getBytes(encoding)) : null);
/*     */   }
/*     */ 
/*     */   public static String stringToHexString(String input) {
/*     */     try {
/* 139 */       return stringToHexString(input, "UTF-8");
/*     */     } catch (UnsupportedEncodingException e) {
/* 141 */       throw new IllegalStateException("UTF-8 encoding is not supported by JVM");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String hexStringToString(String input, String encoding) throws UnsupportedEncodingException
/*     */   {
/* 147 */     return ((input != null) ? new String(fromHex(input), encoding) : null);
/*     */   }
/*     */ 
/*     */   public static String hexStringToString(String input) {
/*     */     try {
/* 152 */       return hexStringToString(input, "UTF-8");
/*     */     } catch (UnsupportedEncodingException e) {
/* 154 */       throw new IllegalStateException("UTF-8 encoding is not supported by JVM");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String timeZoneToCode(TimeZone tz)
/*     */   {
/* 160 */     return timeZoneToString(tz);
/*     */   }
/*     */ 
/*     */   public static TimeZone codeToTimeZone(String tzString)
/*     */   {
/* 165 */     return stringToTimeZone(tzString);
/*     */   }
/*     */ 
/*     */   public static String timeZoneToString(TimeZone tz)
/*     */   {
/* 170 */     return ((tz != null) ? tz.getID() : "");
/*     */   }
/*     */ 
/*     */   public static TimeZone stringToTimeZone(String tzString)
/*     */   {
/* 175 */     return TimeZone.getTimeZone((tzString != null) ? tzString : "");
/*     */   }
/*     */ 
/*     */   public static String localeToCode(Locale aLocale)
/*     */   {
/* 180 */     return localeToString(aLocale);
/*     */   }
/*     */ 
/*     */   public static Locale codeToLocale(String locString)
/*     */   {
/* 185 */     return stringToLocale(locString);
/*     */   }
/*     */ 
/*     */   public static String localeToString(Locale loc)
/*     */   {
/* 190 */     return ((loc != null) ? loc.toString() : "");
/*     */   }
/*     */ 
/*     */   public static Locale stringToLocale(String locString)
/*     */   {
/* 195 */     locString = (locString != null) ? locString.trim() : "";
/* 196 */     if (locString.equals(""))
/* 197 */       return new Locale("", "", "");
/* 198 */     int pos = locString.indexOf(95);
/* 199 */     if (pos == -1)
/* 200 */       return new Locale(locString, "", "");
/* 201 */     String language = locString.substring(0, pos);
/* 202 */     locString = locString.substring(pos + 1);
/* 203 */     pos = locString.indexOf(95);
/* 204 */     if (pos == -1) {
/* 205 */       return new Locale(language, locString, "");
/*     */     }
/* 207 */     String country = locString.substring(0, pos);
/* 208 */     locString = locString.substring(pos + 1);
/* 209 */     return new Locale(language, country, locString);
/*     */   }
/*     */ 
/*     */   public static java.sql.Date dateToSQLDate(java.util.Date d)
/*     */   {
/* 215 */     return ((d != null) ? new java.sql.Date(d.getTime()) : null);
/*     */   }
/*     */ 
/*     */   public static Time dateToSQLTime(java.util.Date d)
/*     */   {
/* 220 */     return ((d != null) ? new Time(d.getTime()) : null);
/*     */   }
/*     */ 
/*     */   public static Timestamp dateToSQLTimestamp(java.util.Date d)
/*     */   {
/* 225 */     return ((d != null) ? new Timestamp(d.getTime()) : null);
/*     */   }
/*     */ 
/*     */   public static java.util.Date sqlTimestampToDate(Timestamp t)
/*     */   {
/* 230 */     return ((t != null) ? new java.util.Date(Math.round(t.getTime() + t.getNanos() / 1000000.0D)) : null);
/*     */   }
/*     */ 
/*     */   public static Timestamp getCurrentDate()
/*     */   {
/* 235 */     Calendar c = Calendar.getInstance();
/* 236 */     c.set(c.get(1), c.get(2), c.get(5), 0, 0, 0);
/* 237 */     Timestamp t = new Timestamp(c.getTime().getTime());
/* 238 */     t.setNanos(0);
/* 239 */     return t;
/*     */   }
/*     */ 
/*     */   public static java.util.Date getDate(int y, int m, int d, boolean inclusive)
/*     */   {
/* 244 */     java.util.Date dt = null;
/* 245 */     Calendar c = Calendar.getInstance();
/* 246 */     c.clear();
/* 247 */     if ((c.getActualMinimum(1) <= y) && (y <= c.getActualMaximum(1)))
/*     */     {
/* 249 */       c.set(1, y);
/* 250 */       if ((c.getActualMinimum(2) <= m) && (m <= c.getActualMaximum(2)))
/*     */       {
/* 252 */         c.set(2, m);
/* 253 */         if ((c.getActualMinimum(5) <= d) && (d <= c.getActualMaximum(5)))
/* 254 */           c.set(5, d);
/*     */       }
/* 256 */       if (inclusive)
/*     */       {
/* 258 */         c.add(5, 1);
/* 259 */         c.add(14, -1);
/*     */       }
/* 261 */       dt = c.getTime();
/*     */     }
/* 263 */     return dt;
/*     */   }
/*     */ 
/*     */   public static java.util.Date getDateStart(java.util.Date d)
/*     */   {
/* 269 */     Calendar c = new GregorianCalendar();
/* 270 */     c.clear();
/* 271 */     Calendar co = new GregorianCalendar();
/* 272 */     co.setTime(d);
/* 273 */     c.set(5, co.get(5));
/* 274 */     c.set(2, co.get(2));
/* 275 */     c.set(1, co.get(1));
/*     */ 
/* 278 */     return c.getTime();
/*     */   }
/*     */ 
/*     */   public static java.util.Date getDateEnd(java.util.Date d)
/*     */   {
/* 283 */     Calendar c = Calendar.getInstance();
/* 284 */     c.clear();
/* 285 */     Calendar co = Calendar.getInstance();
/* 286 */     co.setTime(d);
/* 287 */     c.set(5, co.get(5));
/* 288 */     c.set(2, co.get(2));
/* 289 */     c.set(1, co.get(1));
/* 290 */     c.add(5, 1);
/* 291 */     c.add(14, -1);
/* 292 */     return c.getTime();
/*     */   }
/*     */ 
/*     */   public static double roundNumber(double rowNumber, int roundingPoint)
/*     */   {
/* 297 */     double base = Math.pow(10.0D, roundingPoint);
/* 298 */     return (Math.round(rowNumber * base) / base);
/*     */   }
/*     */ 
/*     */   public static Object getObject(String type, String value) throws Exception {
/* 302 */     type = type.toLowerCase();
/* 303 */     if ("boolean".equals(type))
/* 304 */       return Boolean.valueOf(value);
/* 305 */     if ("byte".equals(type))
/* 306 */       return Byte.valueOf(value);
/* 307 */     if ("short".equals(type))
/* 308 */       return Short.valueOf(value);
/* 309 */     if ("char".equals(type)) {
/* 310 */       if (value.length() != 1) {
/* 311 */         throw new NumberFormatException("Argument is not a character!");
/*     */       }
/* 313 */       return Character.valueOf(value.toCharArray()[0]); }
/* 314 */     if ("int".equals(type))
/* 315 */       return Integer.valueOf(value);
/* 316 */     if ("long".equals(type))
/* 317 */       return Long.valueOf(value);
/* 318 */     if ("float".equals(type))
/* 319 */       return Float.valueOf(value);
/* 320 */     if ("double".equals(type))
/* 321 */       return Double.valueOf(value);
/* 322 */     if ("string".equals(type)) {
/* 323 */       return value;
/*     */     }
/* 325 */     Object[] objs = { value };
/* 326 */     return Class.forName(type).getConstructor(new Class[] { String.class }).newInstance(objs);
/*     */   }
/*     */ }