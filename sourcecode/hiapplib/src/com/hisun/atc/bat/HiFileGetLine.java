 package com.hisun.atc.bat;
 
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileReader;
 
 public class HiFileGetLine
 {
   int record;
   File file = null;
 
   FileReader fr = null;
 
   BufferedReader reader = null;
 
   private String strLine = "";
 
   private String strBackLine = null;
 
   HiFileGetLine(File file, int record)
   {
     this.file = file;
     this.record = record;
     init();
   }
 
   void init()
   {
     try
     {
       this.fr = new FileReader(this.file);
       this.reader = new BufferedReader(this.fr);
     }
     catch (Exception e)
     {
     }
   }
 
   void close()
   {
     try
     {
       this.reader.close();
       this.fr.close();
     }
     catch (Exception e)
     {
     }
   }
 
   public String readLine()
   {
     try
     {
       if (this.strBackLine == null)
       {
         if (this.strLine == null)
           break label231;
         this.strLine = this.reader.readLine();
         if (this.strLine == null) {
           return null;
         }
         b = this.strLine.getBytes();
         lineLength = b.length;
         if (this.record >= lineLength)
         {
           return this.strLine;
         }
 
         newby = new byte[this.record];
         System.arraycopy(b, 0, newby, 0, this.record);
         strNewLine = new String(newby);
 
         backby = new byte[lineLength - this.record];
         System.arraycopy(b, this.record, backby, 0, lineLength - this.record);
         this.strBackLine = new String(backby);
 
         return strNewLine;
       }
 
       byte[] b = this.strBackLine.getBytes();
       int lineLength = b.length;
       if (this.record >= lineLength)
       {
         String strLine = this.strBackLine;
         this.strBackLine = null;
         return strLine;
       }
 
       byte[] newby = new byte[this.record];
       System.arraycopy(b, 0, newby, 0, this.record);
       String strNewLine = new String(newby);
 
       byte[] backby = new byte[lineLength - this.record];
       System.arraycopy(b, this.record, backby, 0, lineLength - this.record);
       this.strBackLine = new String(backby);
 
       label231: return strNewLine;
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return null;
   }
 }