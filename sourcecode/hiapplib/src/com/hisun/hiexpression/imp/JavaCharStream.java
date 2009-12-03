/*     */ package com.hisun.hiexpression.imp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ public class JavaCharStream
/*     */ {
/*     */   public static final boolean staticFlag = 0;
/*     */   private static final int BUFFER_SIZE = 30;
/*     */   public int bufpos;
/*     */   int bufsize;
/*     */   int available;
/*     */   int tokenBegin;
/*     */   protected int[] bufline;
/*     */   protected int[] bufcolumn;
/*     */   protected int column;
/*     */   protected int line;
/*     */   protected boolean prevCharIsCR;
/*     */   protected boolean prevCharIsLF;
/*     */   protected Reader inputStream;
/*     */   protected char[] nextCharBuf;
/*     */   protected char[] buffer;
/*     */   protected int maxNextCharInd;
/*     */   protected int nextCharInd;
/*     */   protected int inBuf;
/*     */   protected int tabSize;
/*     */ 
/*     */   static final int hexval(char c)
/*     */     throws IOException
/*     */   {
/*  14 */     switch (c) { case '0':
/*  16 */       return 0;
/*     */     case '1':
/*  18 */       return 1;
/*     */     case '2':
/*  20 */       return 2;
/*     */     case '3':
/*  22 */       return 3;
/*     */     case '4':
/*  24 */       return 4;
/*     */     case '5':
/*  26 */       return 5;
/*     */     case '6':
/*  28 */       return 6;
/*     */     case '7':
/*  30 */       return 7;
/*     */     case '8':
/*  32 */       return 8;
/*     */     case '9':
/*  34 */       return 9;
/*     */     case 'A':
/*     */     case 'a':
/*  38 */       return 10;
/*     */     case 'B':
/*     */     case 'b':
/*  41 */       return 11;
/*     */     case 'C':
/*     */     case 'c':
/*  44 */       return 12;
/*     */     case 'D':
/*     */     case 'd':
/*  47 */       return 13;
/*     */     case 'E':
/*     */     case 'e':
/*  50 */       return 14;
/*     */     case 'F':
/*     */     case 'f':
/*  53 */       return 15;
/*     */     case ':':
/*     */     case ';':
/*     */     case '<':
/*     */     case '=':
/*     */     case '>':
/*     */     case '?':
/*     */     case '@':
/*     */     case 'G':
/*     */     case 'H':
/*     */     case 'I':
/*     */     case 'J':
/*     */     case 'K':
/*     */     case 'L':
/*     */     case 'M':
/*     */     case 'N':
/*     */     case 'O':
/*     */     case 'P':
/*     */     case 'Q':
/*     */     case 'R':
/*     */     case 'S':
/*     */     case 'T':
/*     */     case 'U':
/*     */     case 'V':
/*     */     case 'W':
/*     */     case 'X':
/*     */     case 'Y':
/*     */     case 'Z':
/*     */     case '[':
/*     */     case '\\':
/*     */     case ']':
/*     */     case '^':
/*     */     case '_':
/*     */     case '`': } throw new IOException();
/*     */   }
/*     */ 
/*     */   protected void setTabSize(int i)
/*     */   {
/*  94 */     this.tabSize = i;
/*     */   }
/*     */ 
/*     */   protected int getTabSize(int i) {
/*  98 */     return this.tabSize;
/*     */   }
/*     */ 
/*     */   protected void ExpandBuff(boolean wrapAround) {
/* 102 */     char[] newbuffer = new char[this.bufsize + 15];
/* 103 */     int[] newbufline = new int[this.bufsize + 15];
/* 104 */     int[] newbufcolumn = new int[this.bufsize + 15];
/*     */     try
/*     */     {
/* 107 */       if (wrapAround) {
/* 108 */         System.arraycopy(this.buffer, this.tokenBegin, newbuffer, 0, this.bufsize - this.tokenBegin);
/*     */ 
/* 110 */         System.arraycopy(this.buffer, 0, newbuffer, this.bufsize - this.tokenBegin, this.bufpos);
/*     */ 
/* 112 */         this.buffer = newbuffer;
/*     */ 
/* 114 */         System.arraycopy(this.bufline, this.tokenBegin, newbufline, 0, this.bufsize - this.tokenBegin);
/*     */ 
/* 116 */         System.arraycopy(this.bufline, 0, newbufline, this.bufsize - this.tokenBegin, this.bufpos);
/*     */ 
/* 118 */         this.bufline = newbufline;
/*     */ 
/* 120 */         System.arraycopy(this.bufcolumn, this.tokenBegin, newbufcolumn, 0, this.bufsize - this.tokenBegin);
/*     */ 
/* 122 */         System.arraycopy(this.bufcolumn, 0, newbufcolumn, this.bufsize - this.tokenBegin, this.bufpos);
/*     */ 
/* 124 */         this.bufcolumn = newbufcolumn;
/*     */ 
/* 126 */         this.bufpos += this.bufsize - this.tokenBegin;
/*     */       } else {
/* 128 */         System.arraycopy(this.buffer, this.tokenBegin, newbuffer, 0, this.bufsize - this.tokenBegin);
/*     */ 
/* 130 */         this.buffer = newbuffer;
/*     */ 
/* 132 */         System.arraycopy(this.bufline, this.tokenBegin, newbufline, 0, this.bufsize - this.tokenBegin);
/*     */ 
/* 134 */         this.bufline = newbufline;
/*     */ 
/* 136 */         System.arraycopy(this.bufcolumn, this.tokenBegin, newbufcolumn, 0, this.bufsize - this.tokenBegin);
/*     */ 
/* 138 */         this.bufcolumn = newbufcolumn;
/*     */ 
/* 140 */         this.bufpos -= this.tokenBegin;
/*     */       }
/*     */     } catch (Throwable t) {
/* 143 */       throw new Error(t.getMessage());
/*     */     }
/*     */ 
/* 146 */     this.available = (this.bufsize += 15);
/* 147 */     this.tokenBegin = 0;
/*     */   }
/*     */ 
/*     */   protected void FillBuff() throws IOException
/*     */   {
/* 152 */     if (this.maxNextCharInd == 30)
/* 153 */       this.maxNextCharInd = (this.nextCharInd = 0);
/*     */     try
/*     */     {
/*     */       int i;
/* 156 */       if ((i = this.inputStream.read(this.nextCharBuf, this.maxNextCharInd, 30 - this.maxNextCharInd)) == -1)
/*     */       {
/* 158 */         this.inputStream.close();
/* 159 */         throw new IOException();
/*     */       }
/* 161 */       this.maxNextCharInd += i;
/* 162 */       return;
/*     */     } catch (IOException e) {
/* 164 */       if (this.bufpos != 0) {
/* 165 */         this.bufpos -= 1;
/* 166 */         backup(0);
/*     */       } else {
/* 168 */         this.bufline[this.bufpos] = this.line;
/* 169 */         this.bufcolumn[this.bufpos] = this.column;
/*     */       }
/* 171 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected char ReadByte() throws IOException {
/* 176 */     if (++this.nextCharInd >= this.maxNextCharInd) {
/* 177 */       FillBuff();
/*     */     }
/* 179 */     return this.nextCharBuf[this.nextCharInd];
/*     */   }
/*     */ 
/*     */   public char BeginToken() throws IOException {
/* 183 */     if (this.inBuf > 0) {
/* 184 */       this.inBuf -= 1;
/*     */ 
/* 186 */       if (++this.bufpos == this.bufsize) {
/* 187 */         this.bufpos = 0;
/*     */       }
/* 189 */       this.tokenBegin = this.bufpos;
/* 190 */       return this.buffer[this.bufpos];
/*     */     }
/*     */ 
/* 193 */     this.tokenBegin = 0;
/* 194 */     this.bufpos = -1;
/*     */ 
/* 196 */     return readChar();
/*     */   }
/*     */ 
/*     */   protected void AdjustBuffSize() {
/* 200 */     if (this.available == this.bufsize)
/* 201 */       if (this.tokenBegin > 15) {
/* 202 */         this.bufpos = 0;
/* 203 */         this.available = this.tokenBegin;
/*     */       } else {
/* 205 */         ExpandBuff(false); }
/* 206 */     else if (this.available > this.tokenBegin)
/* 207 */       this.available = this.bufsize;
/* 208 */     else if (this.tokenBegin - this.available < 15)
/* 209 */       ExpandBuff(true);
/*     */     else
/* 211 */       this.available = this.tokenBegin;
/*     */   }
/*     */ 
/*     */   protected void UpdateLineColumn(char c) {
/* 215 */     this.column += 1;
/*     */ 
/* 217 */     if (this.prevCharIsLF) {
/* 218 */       this.prevCharIsLF = false;
/* 219 */       this.line += (this.column = 1);
/* 220 */     } else if (this.prevCharIsCR) {
/* 221 */       this.prevCharIsCR = false;
/* 222 */       if (c == '\n')
/* 223 */         this.prevCharIsLF = true;
/*     */       else {
/* 225 */         this.line += (this.column = 1);
/*     */       }
/*     */     }
/* 228 */     switch (c)
/*     */     {
/*     */     case '\r':
/* 230 */       this.prevCharIsCR = true;
/* 231 */       break;
/*     */     case '\n':
/* 233 */       this.prevCharIsLF = true;
/* 234 */       break;
/*     */     case '\t':
/* 236 */       this.column -= 1;
/* 237 */       this.column += this.tabSize - (this.column % this.tabSize);
/*     */     case '\11':
/*     */     case '\f':
/*     */     }
/*     */ 
/* 243 */     this.bufline[this.bufpos] = this.line;
/* 244 */     this.bufcolumn[this.bufpos] = this.column;
/*     */   }
/*     */ 
/*     */   public char readChar()
/*     */     throws IOException
/*     */   {
/*     */     char c;
/* 248 */     if (this.inBuf > 0) {
/* 249 */       this.inBuf -= 1;
/*     */ 
/* 251 */       if (++this.bufpos == this.bufsize) {
/* 252 */         this.bufpos = 0;
/*     */       }
/* 254 */       return this.buffer[this.bufpos];
/*     */     }
/*     */ 
/* 259 */     if (++this.bufpos == this.available) {
/* 260 */       AdjustBuffSize();
/*     */     }
/* 262 */     if ((this.buffer[this.bufpos] = c = ReadByte()) == '\\') {
/* 263 */       UpdateLineColumn(c);
/*     */ 
/* 265 */       int backSlashCnt = 1;
/*     */       while (true)
/*     */       {
/* 269 */         if (++this.bufpos == this.available)
/* 270 */           AdjustBuffSize();
/*     */         try
/*     */         {
/* 273 */           if ((this.buffer[this.bufpos] = c = ReadByte()) != '\\') {
/* 274 */             UpdateLineColumn(c);
/*     */ 
/* 276 */             if ((c == 'u') && ((backSlashCnt & 0x1) == 1)) {
/* 277 */               if (--this.bufpos < 0) {
/* 278 */                 this.bufpos = (this.bufsize - 1);
/*     */               }
/* 280 */               break label224:
/*     */             }
/*     */ 
/* 283 */             backup(backSlashCnt);
/* 284 */             return '\\';
/*     */           }
/*     */         } catch (IOException e) {
/* 287 */           if (backSlashCnt > 1) {
/* 288 */             backup(backSlashCnt);
/*     */           }
/* 290 */           return '\\';
/*     */         }
/*     */ 
/* 293 */         UpdateLineColumn(c);
/* 294 */         ++backSlashCnt;
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 299 */         while ((c = ReadByte()) == 'u') {
/* 300 */           label224: this.column += 1;
/*     */         }
/* 302 */         this.buffer[this.bufpos] = (c = (char)(hexval(c) << 12 | hexval(ReadByte()) << 8 | hexval(ReadByte()) << 4 | hexval(ReadByte())));
/*     */ 
/* 305 */         this.column += 4;
/*     */       } catch (IOException e) {
/* 307 */         throw new Error("Invalid escape character at line " + this.line + " column " + this.column + ".");
/*     */       }
/*     */ 
/* 311 */       if (backSlashCnt == 1) {
/* 312 */         return c;
/*     */       }
/* 314 */       backup(backSlashCnt - 1);
/* 315 */       return '\\';
/*     */     }
/*     */ 
/* 318 */     UpdateLineColumn(c);
/* 319 */     return c;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public int getColumn()
/*     */   {
/* 329 */     return this.bufcolumn[this.bufpos];
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public int getLine()
/*     */   {
/* 338 */     return this.bufline[this.bufpos];
/*     */   }
/*     */ 
/*     */   public int getEndColumn() {
/* 342 */     return this.bufcolumn[this.bufpos];
/*     */   }
/*     */ 
/*     */   public int getEndLine() {
/* 346 */     return this.bufline[this.bufpos];
/*     */   }
/*     */ 
/*     */   public int getBeginColumn() {
/* 350 */     return this.bufcolumn[this.tokenBegin];
/*     */   }
/*     */ 
/*     */   public int getBeginLine() {
/* 354 */     return this.bufline[this.tokenBegin];
/*     */   }
/*     */ 
/*     */   public void backup(int amount)
/*     */   {
/* 359 */     this.inBuf += amount;
/* 360 */     if (this.bufpos -= amount < 0)
/* 361 */       this.bufpos += this.bufsize;
/*     */   }
/*     */ 
/*     */   public JavaCharStream(Reader dstream, int startline, int startcolumn, int buffersize)
/*     */   {
/*  59 */     this.bufpos = -1;
/*     */ 
/*  71 */     this.column = 0;
/*     */ 
/*  73 */     this.line = 1;
/*     */ 
/*  75 */     this.prevCharIsCR = false;
/*     */ 
/*  77 */     this.prevCharIsLF = false;
/*     */ 
/*  85 */     this.maxNextCharInd = 0;
/*     */ 
/*  87 */     this.nextCharInd = -1;
/*     */ 
/*  89 */     this.inBuf = 0;
/*     */ 
/*  91 */     this.tabSize = 8;
/*     */ 
/* 366 */     this.inputStream = dstream;
/* 367 */     this.line = startline;
/* 368 */     this.column = (startcolumn - 1);
/*     */ 
/* 370 */     this.available = (this.bufsize = buffersize);
/* 371 */     this.buffer = new char[buffersize];
/* 372 */     this.bufline = new int[buffersize];
/* 373 */     this.bufcolumn = new int[buffersize];
/* 374 */     this.nextCharBuf = new char[30];
/*     */   }
/*     */ 
/*     */   public JavaCharStream(Reader dstream, int startline, int startcolumn)
/*     */   {
/* 379 */     this(dstream, startline, startcolumn, 30);
/*     */   }
/*     */ 
/*     */   public JavaCharStream(Reader dstream)
/*     */   {
/* 384 */     this(dstream, 1, 1, 30);
/*     */   }
/*     */ 
/*     */   public void ReInit(Reader dstream, int startline, int startcolumn, int buffersize)
/*     */   {
/* 389 */     this.inputStream = dstream;
/* 390 */     this.line = startline;
/* 391 */     this.column = (startcolumn - 1);
/*     */ 
/* 393 */     if ((this.buffer == null) || (buffersize != this.buffer.length)) {
/* 394 */       this.available = (this.bufsize = buffersize);
/* 395 */       this.buffer = new char[buffersize];
/* 396 */       this.bufline = new int[buffersize];
/* 397 */       this.bufcolumn = new int[buffersize];
/* 398 */       this.nextCharBuf = new char[30];
/*     */     }
/* 400 */     this.prevCharIsLF = (this.prevCharIsCR = 0);
/* 401 */     this.tokenBegin = (this.inBuf = this.maxNextCharInd = 0);
/* 402 */     this.nextCharInd = (this.bufpos = -1);
/*     */   }
/*     */ 
/*     */   public void ReInit(Reader dstream, int startline, int startcolumn) {
/* 406 */     ReInit(dstream, startline, startcolumn, 30);
/*     */   }
/*     */ 
/*     */   public void ReInit(Reader dstream) {
/* 410 */     ReInit(dstream, 1, 1, 30);
/*     */   }
/*     */ 
/*     */   public JavaCharStream(InputStream dstream, String encoding, int startline, int startcolumn, int buffersize)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 416 */     this(new InputStreamReader(dstream, encoding), startline, startcolumn, buffersize);
/*     */   }
/*     */ 
/*     */   public JavaCharStream(InputStream dstream, int startline, int startcolumn, int buffersize)
/*     */   {
/* 423 */     this(new InputStreamReader(dstream), startline, startcolumn, 30);
/*     */   }
/*     */ 
/*     */   public JavaCharStream(InputStream dstream, String encoding, int startline, int startcolumn)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 430 */     this(dstream, encoding, startline, startcolumn, 30);
/*     */   }
/*     */ 
/*     */   public JavaCharStream(InputStream dstream, int startline, int startcolumn)
/*     */   {
/* 435 */     this(dstream, startline, startcolumn, 30);
/*     */   }
/*     */ 
/*     */   public JavaCharStream(InputStream dstream, String encoding) throws UnsupportedEncodingException
/*     */   {
/* 440 */     this(dstream, encoding, 1, 1, 30);
/*     */   }
/*     */ 
/*     */   public JavaCharStream(InputStream dstream) {
/* 444 */     this(dstream, 1, 1, 30);
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream dstream, String encoding, int startline, int startcolumn, int buffersize)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 450 */     ReInit(new InputStreamReader(dstream, encoding), startline, startcolumn, buffersize);
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream dstream, int startline, int startcolumn, int buffersize)
/*     */   {
/* 457 */     ReInit(new InputStreamReader(dstream), startline, startcolumn, buffersize);
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream dstream, String encoding, int startline, int startcolumn)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 464 */     ReInit(dstream, encoding, startline, startcolumn, 30);
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream dstream, int startline, int startcolumn)
/*     */   {
/* 469 */     ReInit(dstream, startline, startcolumn, 30);
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream dstream, String encoding) throws UnsupportedEncodingException
/*     */   {
/* 474 */     ReInit(dstream, encoding, 1, 1, 30);
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream dstream) {
/* 478 */     ReInit(dstream, 1, 1, 30);
/*     */   }
/*     */ 
/*     */   public String GetImage() {
/* 482 */     if (this.bufpos >= this.tokenBegin) {
/* 483 */       return new String(this.buffer, this.tokenBegin, this.bufpos - this.tokenBegin + 1);
/*     */     }
/* 485 */     return new String(this.buffer, this.tokenBegin, this.bufsize - this.tokenBegin) + new String(this.buffer, 0, this.bufpos + 1);
/*     */   }
/*     */ 
/*     */   public char[] GetSuffix(int len)
/*     */   {
/* 490 */     char[] ret = new char[len];
/*     */ 
/* 492 */     if (this.bufpos + 1 >= len) {
/* 493 */       System.arraycopy(this.buffer, this.bufpos - len + 1, ret, 0, len);
/*     */     } else {
/* 495 */       System.arraycopy(this.buffer, this.bufsize - (len - this.bufpos - 1), ret, 0, len - this.bufpos - 1);
/*     */ 
/* 497 */       System.arraycopy(this.buffer, 0, ret, len - this.bufpos - 1, this.bufpos + 1);
/*     */     }
/*     */ 
/* 500 */     return ret;
/*     */   }
/*     */ 
/*     */   public void Done() {
/* 504 */     this.nextCharBuf = null;
/* 505 */     this.buffer = null;
/* 506 */     this.bufline = null;
/* 507 */     this.bufcolumn = null;
/*     */   }
/*     */ 
/*     */   public void adjustBeginLineColumn(int newLine, int newCol)
/*     */   {
/*     */     int len;
/* 514 */     int start = this.tokenBegin;
/*     */ 
/* 517 */     if (this.bufpos >= this.tokenBegin)
/* 518 */       len = this.bufpos - this.tokenBegin + this.inBuf + 1;
/*     */     else {
/* 520 */       len = this.bufsize - this.tokenBegin + this.bufpos + 1 + this.inBuf;
/*     */     }
/*     */ 
/* 523 */     int i = 0; int j = 0; int k = 0;
/* 524 */     int nextColDiff = 0; int columnDiff = 0;
/*     */ 
/* 527 */     while (i < len) { j = start % this.bufsize; if (this.bufline[j] != this.bufline[(k = ++start % this.bufsize)])
/*     */         break;
/* 529 */       this.bufline[j] = newLine;
/* 530 */       nextColDiff = columnDiff + this.bufcolumn[k] - this.bufcolumn[j];
/* 531 */       this.bufcolumn[j] = (newCol + columnDiff);
/* 532 */       columnDiff = nextColDiff;
/* 533 */       ++i;
/*     */     }
/*     */ 
/* 536 */     if (i < len) {
/* 537 */       this.bufline[j] = (newLine++);
/* 538 */       this.bufcolumn[j] = (newCol + columnDiff);
/*     */ 
/* 540 */       while (i++ < len) {
/* 541 */         if (this.bufline[(j = start % this.bufsize)] != this.bufline[(++start % this.bufsize)]) {
/* 542 */           this.bufline[j] = (newLine++);
/*     */         }
/* 544 */         this.bufline[j] = newLine;
/*     */       }
/*     */     }
/*     */ 
/* 548 */     this.line = this.bufline[j];
/* 549 */     this.column = this.bufcolumn[j];
/*     */   }
/*     */ }