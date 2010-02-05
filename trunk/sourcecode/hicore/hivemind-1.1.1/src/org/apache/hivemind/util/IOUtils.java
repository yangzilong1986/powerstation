 package org.apache.hivemind.util;
 
 import java.io.IOException;
 import java.io.InputStream;
 
 public class IOUtils
 {
   public static void close(InputStream stream)
   {
     if (stream == null)
       return;
     try {
       stream.close();
     }
     catch (IOException ex)
     {
     }
   }
 }