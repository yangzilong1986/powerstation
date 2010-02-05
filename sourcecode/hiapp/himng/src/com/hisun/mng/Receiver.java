 package com.hisun.mng;
 
 import com.hisun.message.HiETF;
 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 
 class Receiver
   implements Runnable
 {
   private final InputStream is;
   private HiETF root;
 
   public void run()
   {
     try
     {
       BufferedReader br = new BufferedReader(new InputStreamReader(this.is));
 
       int k = 1;
       while ((line = br.readLine()) != null)
       {
         String line;
         HiETF grp = this.root.addNode("GRP_" + (k++));
         grp.setChildValue("OUT_MSG", line);
       }
       br.close();
     } catch (IOException e) {
       throw new IllegalArgumentException("IOException receiving data from child process.");
     }
   }
 
   Receiver(HiETF root, InputStream is)
   {
     this.is = is;
     this.root = root;
   }
 }