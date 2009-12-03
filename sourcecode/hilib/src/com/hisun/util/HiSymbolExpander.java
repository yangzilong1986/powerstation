/*     */ package com.hisun.util;
/*     */ 
/*     */ import com.hisun.message.HiContext;
/*     */ 
/*     */ public class HiSymbolExpander
/*     */ {
/*     */   private HiContext _source;
/*     */   private String _namespace;
/*     */   private static final int STATE_START = 0;
/*     */   private static final int STATE_DOLLAR = 1;
/*     */   private static final int STATE_COLLECT_SYMBOL_NAME = 2;
/*     */ 
/*     */   public HiSymbolExpander(HiContext source, String namespace)
/*     */   {
/*  33 */     this._source = source;
/*  34 */     this._namespace = namespace;
/*     */   }
/*     */ 
/*     */   public String expandSymbols(String text)
/*     */   {
/*  55 */     StringBuffer result = new StringBuffer(text.length());
/*  56 */     char[] buffer = text.toCharArray();
/*  57 */     int state = 0;
/*  58 */     int blockStart = 0;
/*  59 */     int blockLength = 0;
/*  60 */     int symbolStart = -1;
/*  61 */     int symbolLength = 0;
/*  62 */     int i = 0;
/*  63 */     int braceDepth = 0;
/*  64 */     boolean anySymbols = false;
/*     */ 
/*  66 */     while (i < buffer.length)
/*     */     {
/*  68 */       char ch = buffer[i];
/*     */ 
/*  70 */       switch (state)
/*     */       {
/*     */       case 0:
/*  74 */         if (ch == '$')
/*     */         {
/*  76 */           state = 1;
/*  77 */           ++i;
/*     */         }
/*     */ 
/*  81 */         ++blockLength;
/*  82 */         ++i;
/*  83 */         break;
/*     */       case 1:
/*  87 */         if (ch == '{')
/*     */         {
/*  89 */           state = 2;
/*  90 */           ++i;
/*     */ 
/*  92 */           symbolStart = i;
/*  93 */           symbolLength = 0;
/*  94 */           braceDepth = 1;
/*     */         }
/*     */ 
/* 103 */         if (ch == '$')
/*     */         {
/* 108 */           anySymbols = true;
/*     */ 
/* 110 */           if (blockLength > 0) {
/* 111 */             result.append(buffer, blockStart, blockLength);
/*     */           }
/* 113 */           result.append(ch);
/*     */ 
/* 115 */           ++i;
/* 116 */           blockStart = i;
/* 117 */           blockLength = 0;
/* 118 */           state = 0;
/*     */         }
/*     */ 
/* 126 */         ++blockLength;
/*     */ 
/* 128 */         state = 0;
/* 129 */         break;
/*     */       case 2:
/* 133 */         if (ch != '}')
/*     */         {
/* 135 */           if (ch == '{') {
/* 136 */             ++braceDepth;
/*     */           }
/* 138 */           ++i;
/* 139 */           ++symbolLength;
/*     */         }
/*     */ 
/* 143 */         --braceDepth;
/*     */ 
/* 145 */         if (braceDepth > 0)
/*     */         {
/* 147 */           ++i;
/* 148 */           ++symbolLength;
/*     */         }
/*     */ 
/* 156 */         if (symbolLength == 0) {
/* 157 */           blockLength += 3;
/*     */         }
/*     */ 
/* 162 */         if (blockLength > 0) {
/* 163 */           result.append(buffer, blockStart, blockLength);
/*     */         }
/* 165 */         if (symbolLength > 0)
/*     */         {
/* 167 */           String variableName = text.substring(symbolStart, symbolStart + symbolLength);
/*     */ 
/* 170 */           result.append(expandSymbol(variableName));
/*     */ 
/* 172 */           anySymbols = true;
/*     */         }
/*     */ 
/* 175 */         ++i;
/* 176 */         blockStart = i;
/* 177 */         blockLength = 0;
/*     */ 
/* 181 */         state = 0;
/*     */ 
/* 183 */         continue;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 191 */     if (!(anySymbols)) {
/* 192 */       return text;
/*     */     }
/*     */ 
/* 197 */     if (state == 1) {
/* 198 */       ++blockLength;
/*     */     }
/* 200 */     if (state == 2) {
/* 201 */       blockLength += symbolLength + 2;
/*     */     }
/* 203 */     if (blockLength > 0) {
/* 204 */       result.append(buffer, blockStart, blockLength);
/*     */     }
/* 206 */     return result.toString();
/*     */   }
/*     */ 
/*     */   private String expandSymbol(String name)
/*     */   {
/* 211 */     String value = null;
/* 212 */     if ((name.indexOf(64) == 0) && (name.indexOf(46) != -1))
/* 213 */       value = this._source.getStrProp(name);
/*     */     else
/* 215 */       value = this._source.getStrProp(this._namespace, name);
/* 216 */     if (value != null) {
/* 217 */       return value;
/*     */     }
/* 219 */     return "${" + name + "}";
/*     */   }
/*     */ }