 package org.apache.hivemind.service;
 
 import java.text.MessageFormat;
 
 public class BodyBuilder
 {
   private static final int DEFAULT_LENGTH = 200;
   private static final char QUOTE = 34;
   private StringBuffer _buffer;
   private static final String INDENT = "  ";
   private int _nestingDepth;
   private boolean _atNewLine;
 
   public BodyBuilder()
   {
     this._buffer = new StringBuffer(200);
 
     this._nestingDepth = 0;
 
     this._atNewLine = true;
   }
 
   public void clear()
   {
     this._nestingDepth = 0;
     this._atNewLine = true;
     this._buffer.setLength(0);
   }
 
   public void add(String text)
   {
     indent();
 
     this._buffer.append(text);
   }
 
   public void add(String pattern, Object[] arguments)
   {
     add(MessageFormat.format(pattern, arguments));
   }
 
   public void add(String pattern, Object arg0)
   {
     add(pattern, new Object[] { arg0 });
   }
 
   public void add(String pattern, Object arg0, Object arg1)
   {
     add(pattern, new Object[] { arg0, arg1 });
   }
 
   public void add(String pattern, Object arg0, Object arg1, Object arg2)
   {
     add(pattern, new Object[] { arg0, arg1, arg2 });
   }
 
   public void addln(String pattern, Object[] arguments)
   {
     addln(MessageFormat.format(pattern, arguments));
   }
 
   public void addln(String pattern, Object arg0)
   {
     addln(pattern, new Object[] { arg0 });
   }
 
   public void addln(String pattern, Object arg0, Object arg1)
   {
     addln(pattern, new Object[] { arg0, arg1 });
   }
 
   public void addln(String pattern, Object arg0, Object arg1, Object arg2)
   {
     addln(pattern, new Object[] { arg0, arg1, arg2 });
   }
 
   public void addQuoted(String text)
   {
     indent();
     this._buffer.append('"');
     this._buffer.append(text);
     this._buffer.append('"');
   }
 
   public void addln(String text)
   {
     add(text);
 
     newline();
   }
 
   private void newline()
   {
     this._buffer.append("\n");
     this._atNewLine = true;
   }
 
   public void begin()
   {
     if (!(this._atNewLine)) {
       newline();
     }
     indent();
     this._buffer.append("{");
     newline();
 
     this._nestingDepth += 1;
   }
 
   public void end()
   {
     if (!(this._atNewLine)) {
       newline();
     }
     this._nestingDepth -= 1;
 
     indent();
     this._buffer.append("}");
 
     newline();
   }
 
   private void indent()
   {
     if (!(this._atNewLine))
       return;
     for (int i = 0; i < this._nestingDepth; ++i) {
       this._buffer.append("  ");
     }
     this._atNewLine = false;
   }
 
   public String toString()
   {
     return this._buffer.toString();
   }
 }