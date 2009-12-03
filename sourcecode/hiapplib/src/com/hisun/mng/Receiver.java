/*     */ package com.hisun.mng;
/*     */ 
/*     */ import com.hisun.message.HiETF;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ 
/*     */ class Receiver
/*     */   implements Runnable
/*     */ {
/*     */   private final InputStream is;
/*     */   private HiETF root;
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/* 508 */       BufferedReader br = new BufferedReader(new InputStreamReader(this.is));
/*     */ 
/* 511 */       int k = 1;
/* 512 */       while ((line = br.readLine()) != null)
/*     */       {
/*     */         String line;
/* 513 */         HiETF grp = this.root.addNode("GRP_" + (k++));
/* 514 */         grp.setChildValue("OUT_MSG", line);
/*     */       }
/* 516 */       br.close();
/*     */     } catch (IOException e) {
/* 518 */       throw new IllegalArgumentException("IOException receiving data from child process.");
/*     */     }
/*     */   }
/*     */ 
/*     */   Receiver(HiETF root, InputStream is)
/*     */   {
/* 532 */     this.is = is;
/* 533 */     this.root = root;
/*     */   }
/*     */ }