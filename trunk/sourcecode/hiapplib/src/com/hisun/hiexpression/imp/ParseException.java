/*     */ package com.hisun.hiexpression.imp;
/*     */ 
/*     */ public class ParseException extends Exception
/*     */ {
/*     */   protected boolean specialConstructor;
/*     */   public Token currentToken;
/*     */   public int[][] expectedTokenSequences;
/*     */   public String[] tokenImage;
/* 140 */   protected String eol = System.getProperty("line.separator", "\n");
/*     */ 
/*     */   public ParseException(Token currentTokenVal, int[][] expectedTokenSequencesVal, String[] tokenImageVal)
/*     */   {
/*  32 */     super("");
/*  33 */     this.specialConstructor = true;
/*  34 */     this.currentToken = currentTokenVal;
/*  35 */     this.expectedTokenSequences = expectedTokenSequencesVal;
/*  36 */     this.tokenImage = tokenImageVal;
/*     */   }
/*     */ 
/*     */   public ParseException()
/*     */   {
/*  51 */     this.specialConstructor = false;
/*     */   }
/*     */ 
/*     */   public ParseException(String message) {
/*  55 */     super(message);
/*  56 */     this.specialConstructor = false;
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/*  98 */     if (!(this.specialConstructor)) {
/*  99 */       return super.getMessage();
/*     */     }
/* 101 */     StringBuffer expected = new StringBuffer();
/* 102 */     int maxSize = 0;
/* 103 */     for (int i = 0; i < this.expectedTokenSequences.length; ++i) {
/* 104 */       if (maxSize < this.expectedTokenSequences[i].length) {
/* 105 */         maxSize = this.expectedTokenSequences[i].length;
/*     */       }
/* 107 */       for (int j = 0; j < this.expectedTokenSequences[i].length; ++j) {
/* 108 */         expected.append(this.tokenImage[this.expectedTokenSequences[i][j]]).append(" ");
/*     */       }
/* 110 */       if (this.expectedTokenSequences[i][(this.expectedTokenSequences[i].length - 1)] != 0) {
/* 111 */         expected.append("...");
/*     */       }
/* 113 */       expected.append(this.eol).append("    ");
/*     */     }
/* 115 */     String retval = "Encountered \"";
/* 116 */     Token tok = this.currentToken.next;
/* 117 */     for (int i = 0; i < maxSize; ++i) {
/* 118 */       if (i != 0) retval = retval + " ";
/* 119 */       if (tok.kind == 0) {
/* 120 */         retval = retval + this.tokenImage[0];
/* 121 */         break;
/*     */       }
/* 123 */       retval = retval + add_escapes(tok.image);
/* 124 */       tok = tok.next;
/*     */     }
/* 126 */     retval = retval + "\" at line " + this.currentToken.next.beginLine + ", column " + this.currentToken.next.beginColumn;
/* 127 */     retval = retval + "." + this.eol;
/* 128 */     if (this.expectedTokenSequences.length == 1)
/* 129 */       retval = retval + "Was expecting:" + this.eol + "    ";
/*     */     else {
/* 131 */       retval = retval + "Was expecting one of:" + this.eol + "    ";
/*     */     }
/* 133 */     retval = retval + expected.toString();
/* 134 */     return retval;
/*     */   }
/*     */ 
/*     */   protected String add_escapes(String str)
/*     */   {
/* 148 */     StringBuffer retval = new StringBuffer();
/*     */ 
/* 150 */     for (int i = 0; i < str.length(); ++i) {
/* 151 */       switch (str.charAt(i))
/*     */       {
/*     */       case '\0':
/* 154 */         break;
/*     */       case '\b':
/* 156 */         retval.append("\\b");
/* 157 */         break;
/*     */       case '\t':
/* 159 */         retval.append("\\t");
/* 160 */         break;
/*     */       case '\n':
/* 162 */         retval.append("\\n");
/* 163 */         break;
/*     */       case '\f':
/* 165 */         retval.append("\\f");
/* 166 */         break;
/*     */       case '\r':
/* 168 */         retval.append("\\r");
/* 169 */         break;
/*     */       case '"':
/* 171 */         retval.append("\\\"");
/* 172 */         break;
/*     */       case '\'':
/* 174 */         retval.append("\\'");
/* 175 */         break;
/*     */       case '\\':
/* 177 */         retval.append("\\\\");
/* 178 */         break;
/*     */       default:
/*     */         char ch;
/* 180 */         if (((ch = str.charAt(i)) < ' ') || (ch > '~')) {
/* 181 */           String s = "0000" + Integer.toString(ch, 16);
/* 182 */           retval.append("\\u" + s.substring(s.length() - 4, s.length()));
/*     */         } else {
/* 184 */           retval.append(ch);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 189 */     return retval.toString();
/*     */   }
/*     */ }