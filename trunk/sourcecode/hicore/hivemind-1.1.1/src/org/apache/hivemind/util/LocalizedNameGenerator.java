 package org.apache.hivemind.util;
 
 import java.util.Locale;
 import java.util.NoSuchElementException;
 import org.apache.hivemind.HiveMind;
 
 public class LocalizedNameGenerator
 {
   private int _baseNameLength;
   private String _suffix;
   private StringBuffer _buffer;
   private String _language;
   private String _country;
   private String _variant;
   private int _state;
   private int _prevState;
   private static final int INITIAL = 0;
   private static final int LCV = 1;
   private static final int LC = 2;
   private static final int LV = 3;
   private static final int L = 4;
   private static final int BARE = 5;
   private static final int EXHAUSTED = 6;
 
   public LocalizedNameGenerator(String baseName, Locale locale, String suffix)
   {
     this._baseNameLength = baseName.length();
 
     if (locale != null)
     {
       this._language = locale.getLanguage();
       this._country = locale.getCountry();
       this._variant = locale.getVariant();
     }
 
     this._state = 0;
     this._prevState = 0;
 
     this._suffix = suffix;
 
     this._buffer = new StringBuffer(baseName);
 
     advance();
   }
 
   private void advance()
   {
     this._prevState = this._state;
     while (true) {
       if (this._state == 6)
         return;
       this._state += 1;
 
       switch (this._state)
       {
       case 1:
         if (!(HiveMind.isBlank(this._variant)));
         return;
       case 2:
         if (!(HiveMind.isBlank(this._country)));
         return;
       case 3:
         if (HiveMind.isBlank(this._variant)) continue; if (!(HiveMind.isBlank(this._country)));
         return;
       case 4:
         if (!(HiveMind.isBlank(this._language)))
         {
           return; }
       case 5:
       }
     }
     return;
   }
 
   public boolean more()
   {
     return (this._state != 6);
   }
 
   public String next()
   {
     if (this._state == 6) {
       throw new NoSuchElementException();
     }
     String result = build();
 
     advance();
 
     return result;
   }
 
   private String build()
   {
     this._buffer.setLength(this._baseNameLength);
 
     if ((this._state == 2) || (this._state == 1) || (this._state == 4))
     {
       this._buffer.append('_');
       this._buffer.append(this._language);
     }
 
     if ((this._state == 2) || (this._state == 1) || (this._state == 3))
     {
       this._buffer.append('_');
 
       if (this._state != 3) {
         this._buffer.append(this._country);
       }
     }
     if ((this._state == 3) || (this._state == 1))
     {
       this._buffer.append('_');
       this._buffer.append(this._variant);
     }
 
     if (this._suffix != null) {
       this._buffer.append(this._suffix);
     }
     return this._buffer.toString();
   }
 
   public Locale getCurrentLocale()
   {
     switch (this._prevState)
     {
     case 1:
       return new Locale(this._language, this._country, this._variant);
     case 2:
       return new Locale(this._language, this._country, "");
     case 3:
       return new Locale(this._language, "", this._variant);
     case 4:
       return new Locale(this._language, "", "");
     }
 
     return null;
   }
 }