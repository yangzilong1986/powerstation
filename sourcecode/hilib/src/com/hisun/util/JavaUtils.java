/*     */ package com.hisun.util;
/*     */ 
/*     */ import java.beans.Introspector;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.Method;
/*     */ import java.text.Collator;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Locale;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ public class JavaUtils
/*     */ {
/*     */   public static final char NL = 10;
/*     */   public static final char CR = 13;
/*  46 */   public static final String LS = System.getProperty("line.separator", new Character('\n').toString());
/*     */ 
/* 163 */   static final String[] keywords = { "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while" };
/*     */ 
/* 179 */   static final Collator englishCollator = Collator.getInstance(Locale.ENGLISH);
/*     */   static final char keywordPrefix = 95;
/* 438 */   private static WeakHashMap enumMap = new WeakHashMap();
/*     */ 
/* 670 */   private static boolean checkForAttachmentSupport = true;
/* 671 */   private static boolean attachmentSupportEnabled = false;
/*     */ 
/*     */   public static Class getWrapperClass(Class primitive)
/*     */   {
/*  52 */     if (primitive == Integer.TYPE)
/*  53 */       return Integer.class;
/*  54 */     if (primitive == Short.TYPE)
/*  55 */       return Short.class;
/*  56 */     if (primitive == Boolean.TYPE)
/*  57 */       return Boolean.class;
/*  58 */     if (primitive == Byte.TYPE)
/*  59 */       return Byte.class;
/*  60 */     if (primitive == Long.TYPE)
/*  61 */       return Long.class;
/*  62 */     if (primitive == Double.TYPE)
/*  63 */       return Double.class;
/*  64 */     if (primitive == Float.TYPE)
/*  65 */       return Float.class;
/*  66 */     if (primitive == Character.TYPE) {
/*  67 */       return Character.class;
/*     */     }
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */   public static String getWrapper(String primitive)
/*     */   {
/*  74 */     if (primitive.equals("int"))
/*  75 */       return "Integer";
/*  76 */     if (primitive.equals("short"))
/*  77 */       return "Short";
/*  78 */     if (primitive.equals("boolean"))
/*  79 */       return "Boolean";
/*  80 */     if (primitive.equals("byte"))
/*  81 */       return "Byte";
/*  82 */     if (primitive.equals("long"))
/*  83 */       return "Long";
/*  84 */     if (primitive.equals("double"))
/*  85 */       return "Double";
/*  86 */     if (primitive.equals("float"))
/*  87 */       return "Float";
/*  88 */     if (primitive.equals("char")) {
/*  89 */       return "Character";
/*     */     }
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   public static Class getPrimitiveClass(Class wrapper)
/*     */   {
/*  96 */     if (wrapper == Integer.class)
/*  97 */       return Integer.TYPE;
/*  98 */     if (wrapper == Short.class)
/*  99 */       return Short.TYPE;
/* 100 */     if (wrapper == Boolean.class)
/* 101 */       return Boolean.TYPE;
/* 102 */     if (wrapper == Byte.class)
/* 103 */       return Byte.TYPE;
/* 104 */     if (wrapper == Long.class)
/* 105 */       return Long.TYPE;
/* 106 */     if (wrapper == Double.class)
/* 107 */       return Double.TYPE;
/* 108 */     if (wrapper == Float.class)
/* 109 */       return Float.TYPE;
/* 110 */     if (wrapper == Character.class) {
/* 111 */       return Character.TYPE;
/*     */     }
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   public static Class getPrimitiveClassFromName(String primitive) {
/* 117 */     if (primitive.equals("int"))
/* 118 */       return Integer.TYPE;
/* 119 */     if (primitive.equals("short"))
/* 120 */       return Short.TYPE;
/* 121 */     if (primitive.equals("boolean"))
/* 122 */       return Boolean.TYPE;
/* 123 */     if (primitive.equals("byte"))
/* 124 */       return Byte.TYPE;
/* 125 */     if (primitive.equals("long"))
/* 126 */       return Long.TYPE;
/* 127 */     if (primitive.equals("double"))
/* 128 */       return Double.TYPE;
/* 129 */     if (primitive.equals("float"))
/* 130 */       return Float.TYPE;
/* 131 */     if (primitive.equals("char")) {
/* 132 */       return Character.TYPE;
/*     */     }
/* 134 */     return null;
/*     */   }
/*     */ 
/*     */   public static boolean isJavaId(String id)
/*     */   {
/* 191 */     if ((id == null) || (id.equals("")) || (isJavaKeyword(id)))
/* 192 */       return false;
/* 193 */     if (!(Character.isJavaIdentifierStart(id.charAt(0))))
/* 194 */       return false;
/* 195 */     for (int i = 1; i < id.length(); ++i)
/* 196 */       if (!(Character.isJavaIdentifierPart(id.charAt(i))))
/* 197 */         return false;
/* 198 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean isJavaKeyword(String keyword)
/*     */   {
/* 206 */     return (Arrays.binarySearch(keywords, keyword, englishCollator) >= 0);
/*     */   }
/*     */ 
/*     */   public static String makeNonJavaKeyword(String keyword)
/*     */   {
/* 214 */     return '_' + keyword;
/*     */   }
/*     */ 
/*     */   public static String getLoadableClassName(String text)
/*     */   {
/* 222 */     if ((text == null) || (text.indexOf("[") < 0) || (text.charAt(0) == '['))
/*     */     {
/* 225 */       return text; }
/* 226 */     String className = text.substring(0, text.indexOf("["));
/* 227 */     if (className.equals("byte"))
/* 228 */       className = "B";
/* 229 */     else if (className.equals("char"))
/* 230 */       className = "C";
/* 231 */     else if (className.equals("double"))
/* 232 */       className = "D";
/* 233 */     else if (className.equals("float"))
/* 234 */       className = "F";
/* 235 */     else if (className.equals("int"))
/* 236 */       className = "I";
/* 237 */     else if (className.equals("long"))
/* 238 */       className = "J";
/* 239 */     else if (className.equals("short"))
/* 240 */       className = "S";
/* 241 */     else if (className.equals("boolean"))
/* 242 */       className = "Z";
/*     */     else
/* 244 */       className = "L" + className + ";";
/* 245 */     int i = text.indexOf("]");
/* 246 */     while (i > 0) {
/* 247 */       className = "[" + className;
/* 248 */       i = text.indexOf("]", i + 1);
/*     */     }
/* 250 */     return className;
/*     */   }
/*     */ 
/*     */   public static String getTextClassName(String text)
/*     */   {
/* 258 */     if ((text == null) || (text.indexOf("[") != 0))
/*     */     {
/* 260 */       return text; }
/* 261 */     String className = "";
/* 262 */     int index = 0;
/*     */ 
/* 264 */     while ((index < text.length()) && (text.charAt(index) == '[')) {
/* 265 */       ++index;
/* 266 */       className = className + "[]";
/*     */     }
/* 268 */     if (index < text.length()) {
/* 269 */       if (text.charAt(index) == 'B')
/* 270 */         className = "byte" + className;
/* 271 */       else if (text.charAt(index) == 'C')
/* 272 */         className = "char" + className;
/* 273 */       else if (text.charAt(index) == 'D')
/* 274 */         className = "double" + className;
/* 275 */       else if (text.charAt(index) == 'F')
/* 276 */         className = "float" + className;
/* 277 */       else if (text.charAt(index) == 'I')
/* 278 */         className = "int" + className;
/* 279 */       else if (text.charAt(index) == 'J')
/* 280 */         className = "long" + className;
/* 281 */       else if (text.charAt(index) == 'S')
/* 282 */         className = "short" + className;
/* 283 */       else if (text.charAt(index) == 'Z')
/* 284 */         className = "boolean" + className;
/*     */       else {
/* 286 */         className = text.substring(index + 1, text.indexOf(";")) + className;
/*     */       }
/*     */     }
/* 289 */     return className;
/*     */   }
/*     */ 
/*     */   public static String xmlNameToJava(String name)
/*     */   {
/* 303 */     if ((name == null) || (name.equals(""))) {
/* 304 */       return name;
/*     */     }
/* 306 */     char[] nameArray = name.toCharArray();
/* 307 */     int nameLen = name.length();
/* 308 */     StringBuffer result = new StringBuffer(nameLen);
/* 309 */     boolean wordStart = false;
/*     */ 
/* 312 */     int i = 0;
/*     */ 
/* 314 */     while ((i < nameLen) && (((isPunctuation(nameArray[i])) || (!(Character.isJavaIdentifierStart(nameArray[i]))))))
/*     */     {
/* 316 */       ++i;
/*     */     }
/* 318 */     if (i < nameLen)
/*     */     {
/* 322 */       result.append(nameArray[i]);
/*     */ 
/* 324 */       wordStart = (!(Character.isLetter(nameArray[i]))) && (nameArray[i] != "_".charAt(0));
/*     */     }
/* 329 */     else if (Character.isJavaIdentifierPart(nameArray[0])) {
/* 330 */       result.append("_" + nameArray[0]);
/*     */     }
/*     */     else
/*     */     {
/* 336 */       result.append("_" + nameArray.length);
/*     */     }
/*     */ 
/* 345 */     for (++i; i < nameLen; ++i) {
/* 346 */       char c = nameArray[i];
/*     */ 
/* 350 */       if ((isPunctuation(c)) || (!(Character.isJavaIdentifierPart(c)))) {
/* 351 */         wordStart = true;
/*     */       }
/*     */       else {
/* 354 */         if ((wordStart) && (Character.isLowerCase(c))) {
/* 355 */           result.append(Character.toUpperCase(c));
/*     */         }
/*     */         else {
/* 358 */           result.append(c);
/*     */         }
/*     */ 
/* 364 */         wordStart = (!(Character.isLetter(c))) && (c != "_".charAt(0));
/*     */       }
/*     */     }
/*     */ 
/* 368 */     String newName = result.toString();
/*     */ 
/* 372 */     if (Character.isUpperCase(newName.charAt(0))) {
/* 373 */       newName = Introspector.decapitalize(newName);
/*     */     }
/*     */ 
/* 376 */     if (isJavaKeyword(newName)) {
/* 377 */       newName = makeNonJavaKeyword(newName);
/*     */     }
/* 379 */     return newName;
/*     */   }
/*     */ 
/*     */   private static boolean isPunctuation(char c)
/*     */   {
/* 387 */     return (('-' == c) || ('.' == c) || (':' == c) || (183 == c) || (903 == c) || (1757 == c) || (1758 == c));
/*     */   }
/*     */ 
/*     */   public static final String replace(String name, String oldT, String newT)
/*     */   {
/* 409 */     if (name == null) return "";
/*     */ 
/* 413 */     StringBuffer sb = new StringBuffer(name.length() * 2);
/*     */ 
/* 415 */     int len = oldT.length();
/*     */     try {
/* 417 */       int start = 0;
/* 418 */       int i = name.indexOf(oldT, start);
/*     */ 
/* 420 */       while (i >= 0) {
/* 421 */         sb.append(name.substring(start, i));
/* 422 */         sb.append(newT);
/* 423 */         start = i + len;
/* 424 */         i = name.indexOf(oldT, start);
/*     */       }
/* 426 */       if (start < name.length())
/* 427 */         sb.append(name.substring(start));
/*     */     }
/*     */     catch (NullPointerException e) {
/*     */     }
/* 431 */     return new String(sb);
/*     */   }
/*     */ 
/*     */   public static boolean isEnumClass(Class cls)
/*     */   {
/* 448 */     Boolean b = (Boolean)enumMap.get(cls);
/* 449 */     if (b == null) {
/* 450 */       b = (isEnumClassSub(cls)) ? Boolean.TRUE : Boolean.FALSE;
/* 451 */       synchronized (enumMap) {
/* 452 */         enumMap.put(cls, b);
/*     */       }
/*     */     }
/* 455 */     return b.booleanValue();
/*     */   }
/*     */ 
/*     */   private static boolean isEnumClassSub(Class cls) {
/*     */     try {
/* 460 */       Method[] methods = cls.getMethods();
/* 461 */       Method getValueMethod = null;
/* 462 */       Method fromValueMethod = null;
/* 463 */       Method setValueMethod = null; Method fromStringMethod = null;
/*     */ 
/* 467 */       for (int i = 0; i < methods.length; ++i) {
/* 468 */         String name = methods[i].getName();
/*     */ 
/* 470 */         if ((name.equals("getValue")) && (methods[i].getParameterTypes().length == 0))
/*     */         {
/* 472 */           getValueMethod = methods[i];
/* 473 */         } else if (name.equals("fromString")) {
/* 474 */           Object[] params = methods[i].getParameterTypes();
/* 475 */           if ((params.length == 1) && (params[0] == String.class))
/*     */           {
/* 477 */             fromStringMethod = methods[i];
/*     */           }
/* 479 */         } else if ((name.equals("fromValue")) && (methods[i].getParameterTypes().length == 1))
/*     */         {
/* 481 */           fromValueMethod = methods[i]; } else {
/* 482 */           if ((!(name.equals("setValue"))) || (methods[i].getParameterTypes().length != 1))
/*     */             continue;
/* 484 */           setValueMethod = methods[i];
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 491 */       if ((null != getValueMethod) && (null != fromStringMethod))
/*     */       {
/* 496 */         return ((null != setValueMethod) && (setValueMethod.getParameterTypes().length == 1) && (getValueMethod.getReturnType() == setValueMethod.getParameterTypes()[0]));
/*     */       }
/*     */ 
/* 501 */       return false;
/*     */     } catch (SecurityException e) {
/*     */     }
/* 504 */     return false;
/*     */   }
/*     */ 
/*     */   public static String stackToString(Throwable e)
/*     */   {
/* 509 */     StringWriter sw = new StringWriter(1024);
/* 510 */     PrintWriter pw = new PrintWriter(sw);
/* 511 */     e.printStackTrace(pw);
/* 512 */     pw.close();
/* 513 */     return sw.toString();
/*     */   }
/*     */ 
/*     */   public static final boolean isTrue(String value)
/*     */   {
/* 524 */     return (!(isFalseExplicitly(value)));
/*     */   }
/*     */ 
/*     */   public static final boolean isTrueExplicitly(String value)
/*     */   {
/* 532 */     return ((value != null) && (((value.equalsIgnoreCase("true")) || (value.equals("1")) || (value.equalsIgnoreCase("yes")))));
/*     */   }
/*     */ 
/*     */   public static final boolean isTrueExplicitly(Object value, boolean defaultVal)
/*     */   {
/* 547 */     if (value == null) return defaultVal;
/* 548 */     if (value instanceof Boolean) {
/* 549 */       return ((Boolean)value).booleanValue();
/*     */     }
/* 551 */     if (value instanceof Integer) {
/* 552 */       return (((Integer)value).intValue() != 0);
/*     */     }
/* 554 */     if (value instanceof String) {
/* 555 */       return isTrueExplicitly((String)value);
/*     */     }
/* 557 */     return true;
/*     */   }
/*     */ 
/*     */   public static final boolean isTrueExplicitly(Object value) {
/* 561 */     return isTrueExplicitly(value, false);
/*     */   }
/*     */ 
/*     */   public static final boolean isTrue(Object value, boolean defaultVal)
/*     */   {
/* 573 */     return (!(isFalseExplicitly(value, !(defaultVal))));
/*     */   }
/*     */ 
/*     */   public static final boolean isTrue(Object value) {
/* 577 */     return isTrue(value, false);
/*     */   }
/*     */ 
/*     */   public static final boolean isFalse(String value)
/*     */   {
/* 588 */     return isFalseExplicitly(value);
/*     */   }
/*     */ 
/*     */   public static final boolean isFalseExplicitly(String value)
/*     */   {
/* 596 */     return ((value == null) || (value.equalsIgnoreCase("false")) || (value.equals("0")) || (value.equalsIgnoreCase("no")));
/*     */   }
/*     */ 
/*     */   public static final boolean isFalseExplicitly(Object value, boolean defaultVal)
/*     */   {
/* 611 */     if (value == null) return defaultVal;
/* 612 */     if (value instanceof Boolean) {
/* 613 */       return (!(((Boolean)value).booleanValue()));
/*     */     }
/* 615 */     if (value instanceof Integer) {
/* 616 */       return (((Integer)value).intValue() == 0);
/*     */     }
/* 618 */     if (value instanceof String) {
/* 619 */       return isFalseExplicitly((String)value);
/*     */     }
/* 621 */     return false;
/*     */   }
/*     */ 
/*     */   public static final boolean isFalseExplicitly(Object value) {
/* 625 */     return isFalseExplicitly(value, true);
/*     */   }
/*     */ 
/*     */   public static final boolean isFalse(Object value, boolean defaultVal)
/*     */   {
/* 637 */     return isFalseExplicitly(value, defaultVal);
/*     */   }
/*     */ 
/*     */   public static final boolean isFalse(Object value) {
/* 641 */     return isFalse(value, true);
/*     */   }
/*     */ 
/*     */   public static String mimeToJava(String mime)
/*     */   {
/* 648 */     if (("image/gif".equals(mime)) || ("image/jpeg".equals(mime))) {
/* 649 */       return "java.awt.Image";
/*     */     }
/* 651 */     if ("text/plain".equals(mime)) {
/* 652 */       return "java.lang.String";
/*     */     }
/* 654 */     if (("text/xml".equals(mime)) || ("application/xml".equals(mime))) {
/* 655 */       return "javax.xml.transform.Source";
/*     */     }
/* 657 */     if (("application/octet-stream".equals(mime)) || ("application/octetstream".equals(mime)))
/*     */     {
/* 659 */       return "org.apache.axis.attachments.OctetStream";
/*     */     }
/* 661 */     if ((mime != null) && (mime.startsWith("multipart/"))) {
/* 662 */       return "javax.mail.internet.MimeMultipart";
/*     */     }
/*     */ 
/* 665 */     return "javax.activation.DataHandler";
/*     */   }
/*     */ 
/*     */   public static String getUniqueValue(Collection values, String initValue)
/*     */   {
/*     */     int end;
/* 680 */     if (!(values.contains(initValue))) {
/* 681 */       return initValue;
/*     */     }
/*     */ 
/* 685 */     StringBuffer unqVal = new StringBuffer(initValue);
/* 686 */     int beg = unqVal.length();
/* 687 */     while (Character.isDigit(unqVal.charAt(beg - 1))) {
/* 688 */       --beg;
/*     */     }
/* 690 */     if (beg == unqVal.length()) {
/* 691 */       unqVal.append('1');
/*     */     }
/* 693 */     int cur = --unqVal.length();
/*     */     while (true) {
/* 695 */       if (!(values.contains(unqVal.toString())))
/*     */         break label193;
/* 697 */       if (unqVal.charAt(cur) >= '9') break;
/* 698 */       unqVal.setCharAt(cur, (char)(unqVal.charAt(cur) + '\1'));
/*     */     }
/*     */ 
/*     */     do
/*     */     {
/* 703 */       if (cur-- <= beg) break label151; 
/*     */     }
/* 704 */     while (unqVal.charAt(cur) >= '9');
/* 705 */     unqVal.setCharAt(cur, (char)(unqVal.charAt(cur) + '\1'));
/*     */ 
/* 712 */     if (cur < beg) {
/* 713 */       label151: unqVal.insert(++cur, '1'); ++end; }
/*     */     while (true) {
/* 715 */       if (cur < end);
/* 716 */       unqVal.setCharAt(++cur, '0');
/*     */     }
/*     */ 
/* 723 */     label193: return unqVal.toString();
/*     */   }
/*     */ 
/*     */   public static abstract interface ConvertCache
/*     */   {
/*     */     public abstract void setConvertedValue(Class paramClass, Object paramObject);
/*     */ 
/*     */     public abstract Object getConvertedValue(Class paramClass);
/*     */ 
/*     */     public abstract Class getDestClass();
/*     */   }
/*     */ }