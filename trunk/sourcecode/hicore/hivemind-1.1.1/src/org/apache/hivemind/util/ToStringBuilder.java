 package org.apache.hivemind.util;
 
 public class ToStringBuilder
 {
   private StringBuffer _buffer;
   private int _mode;
   private int _attributeCount;
   private static int _defaultMode;
   public static final int INCLUDE_PACKAGE_PREFIX = 1;
   public static final int INCLUDE_HASHCODE = 2;
 
   public ToStringBuilder(Object target)
   {
     this(target, _defaultMode);
   }
 
   public ToStringBuilder(Object target, int mode)
   {
     this._buffer = new StringBuffer();
 
     this._mode = mode;
 
     appendClassName(target);
     appendHashCode(target);
   }
 
   private void appendHashCode(Object target)
   {
     if ((this._mode & 0x2) == 0) {
       return;
     }
     this._buffer.append('@');
     this._buffer.append(Integer.toHexString(target.hashCode()));
   }
 
   private void appendClassName(Object target)
   {
     String className = target.getClass().getName();
 
     if ((this._mode & 0x1) != 0)
     {
       this._buffer.append(className);
       return;
     }
 
     int lastdotx = className.lastIndexOf(46);
 
     this._buffer.append(className.substring(lastdotx + 1));
   }
 
   public static int getDefaultMode()
   {
     return _defaultMode;
   }
 
   public static void setDefaultMode(int i)
   {
     _defaultMode = i;
   }
 
   public String toString()
   {
     if (this._attributeCount > 0) {
       this._buffer.append(']');
     }
     String result = this._buffer.toString();
 
     this._buffer = null;
 
     return result;
   }
 
   public void append(String attributeName, boolean value)
   {
     append(attributeName, String.valueOf(value));
   }
 
   public void append(String attributeName, byte value)
   {
     append(attributeName, String.valueOf(value));
   }
 
   public void append(String attributeName, short value)
   {
     append(attributeName, String.valueOf(value));
   }
 
   public void append(String attributeName, int value)
   {
     append(attributeName, String.valueOf(value));
   }
 
   public void append(String attributeName, Object value)
   {
     append(attributeName, String.valueOf(value));
   }
 
   public void append(String attributeName, String value)
   {
     if (this._attributeCount++ == 0) {
       this._buffer.append('[');
     }
     else {
       this._buffer.append(' ');
     }
     this._buffer.append(attributeName);
 
     this._buffer.append('=');
 
     this._buffer.append(value);
   }
 }