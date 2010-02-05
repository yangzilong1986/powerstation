 package org.apache.hivemind.util;
 
 import java.util.ArrayList;
 import java.util.List;
 
 public class StringUtils
 {
   public static String[] split(String input)
   {
     if (input == null) {
       return new String[0];
     }
     List strings = new ArrayList();
 
     int startx = 0;
     int cursor = 0;
     int length = input.length();
 
     while (cursor < length)
     {
       if (input.charAt(cursor) == ',')
       {
         String item = input.substring(startx, cursor);
         strings.add(item);
         startx = cursor + 1;
       }
 
       ++cursor;
     }
 
     if (startx < length) {
       strings.add(input.substring(startx));
     }
     return ((String[])(String[])strings.toArray(new String[strings.size()]));
   }
 
   public static String capitalize(String input)
   {
     if (input.length() == 0) {
       return input;
     }
     char ch = input.charAt(0);
 
     if (Character.isUpperCase(ch)) {
       return input;
     }
     return String.valueOf(Character.toUpperCase(ch)) + input.substring(1);
   }
 
   public static String join(String[] input, char separator)
   {
     if ((input == null) || (input.length == 0)) {
       return null;
     }
     StringBuffer buffer = new StringBuffer();
 
     for (int i = 0; i < input.length; ++i)
     {
       if (i > 0) {
         buffer.append(separator);
       }
       buffer.append(input[i]);
     }
 
     return buffer.toString();
   }
 
   public static String replace(String string, String pattern, String replacement)
   {
     StringBuffer sbuf = new StringBuffer();
     int index = string.indexOf(pattern);
     int pos = 0;
     int patternLength = pattern.length();
     for (; index >= 0; index = string.indexOf(pattern, pos))
     {
       sbuf.append(string.substring(pos, index));
       sbuf.append(replacement);
       pos = index + patternLength;
     }
     sbuf.append(string.substring(pos));
 
     return sbuf.toString();
   }
 }