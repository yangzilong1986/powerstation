/*     */ package com.hisun.engine.invoke;
/*     */ 
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiXmlETF;
/*     */ import java.util.Stack;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ class HiETFParse
/*     */ {
/*     */   public static HiETF parseTextETF(String data)
/*     */   {
/*  14 */     return new HiXmlETF(parseText(data));
/*     */   }
/*     */ 
/*     */   public static Element parseText(String data) {
/*  18 */     Document doc = DocumentHelper.createDocument();
/*  19 */     Element root = parse(data);
/*  20 */     doc.setRootElement(root);
/*  21 */     return root;
/*     */   }
/*     */ 
/*     */   private static Element parse(String data) {
/*  25 */     int state = 0;
/*  26 */     StringBuffer tmp = new StringBuffer();
/*  27 */     Stack nodeStack = new Stack();
/*  28 */     Element root = null;
/*     */ 
/*  30 */     for (int i = 0; i < data.length(); )
/*     */     {
/*     */       Element tmpNod;
/*  31 */       if (state == 0) {
/*  32 */         if (Character.isWhitespace(data.charAt(i))) {
/*  33 */           ++i;
/*     */         }
/*     */ 
/*  37 */         state = 1; }
/*  38 */       if (state == 1)
/*     */       {
/*  40 */         if (data.charAt(i) == '<') {
/*  41 */           if (data.charAt(i + 1) == '/')
/*     */           {
/*  43 */             state = 3;
/*     */           }
/*  45 */           ++i;
/*     */         }
/*     */ 
/*  49 */         if ((data.charAt(i) == '/') && 
/*  50 */           (data.charAt(i + 1) == '>'))
/*     */         {
/*  52 */           tmpNod = DocumentHelper.createElement(tmp.toString());
/*  53 */           tmp.setLength(0);
/*     */ 
/*  55 */           if (nodeStack.isEmpty()) {
/*  56 */             root = tmpNod;
/*     */           }
/*  58 */           else if (!(nodeStack.isEmpty())) {
/*  59 */             ((Element)nodeStack.peek()).add(tmpNod);
/*     */           }
/*     */ 
/*  62 */           state = 0;
/*  63 */           i += 2;
/*     */         }
/*     */ 
/*  68 */         if (data.charAt(i) == '>')
/*     */         {
/*  70 */           tmpNod = DocumentHelper.createElement(tmp.toString());
/*  71 */           if (nodeStack.isEmpty()) {
/*  72 */             root = tmpNod;
/*     */           }
/*  74 */           nodeStack.push(tmpNod);
/*  75 */           state = 2;
/*  76 */           tmp.setLength(0);
/*  77 */           ++i;
/*     */         }
/*     */ 
/*  80 */         tmp.append(data.charAt(i));
/*  81 */         ++i; }
/*  82 */       if (state == 2)
/*     */       {
/*  84 */         if (data.charAt(i) == '<') {
/*  85 */           if (data.charAt(i + 1) == '/')
/*     */           {
/*  87 */             if (nodeStack.isEmpty()) {
/*  88 */               throw new RuntimeException("invalid:{" + data + "}");
/*     */             }
/*  90 */             Element node = (Element)nodeStack.peek();
/*  91 */             node.setText(tmp.toString());
/*  92 */             tmp.setLength(0);
/*  93 */             state = 3;
/*     */           }
/*     */ 
/*  96 */           state = 0;
/*     */         }
/*     */ 
/* 100 */         tmp.append(data.charAt(i));
/* 101 */         ++i; }
/* 102 */       if (state != 3)
/*     */         continue;
/* 104 */       if (data.charAt(i) == '>') {
/* 105 */         if (nodeStack.isEmpty()) {
/* 106 */           throw new RuntimeException("invalid:{" + data + "}");
/*     */         }
/* 108 */         tmpNod = (Element)nodeStack.pop();
/* 109 */         if (!(nodeStack.isEmpty())) {
/* 110 */           ((Element)nodeStack.peek()).add(tmpNod);
/*     */         }
/* 112 */         state = 0;
/*     */       }
/* 114 */       ++i;
/*     */     }
/*     */ 
/* 117 */     return root;
/*     */   }
/*     */ }