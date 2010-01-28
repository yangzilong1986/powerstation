 package com.hisun.hiexpression.imp;
 
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.Reader;
 import java.io.UnsupportedEncodingException;
 
 public class JavaCharStream
 {
   public static final boolean staticFlag = 0;
   private static final int BUFFER_SIZE = 30;
   public int bufpos;
   int bufsize;
   int available;
   int tokenBegin;
   protected int[] bufline;
   protected int[] bufcolumn;
   protected int column;
   protected int line;
   protected boolean prevCharIsCR;
   protected boolean prevCharIsLF;
   protected Reader inputStream;
   protected char[] nextCharBuf;
   protected char[] buffer;
   protected int maxNextCharInd;
   protected int nextCharInd;
   protected int inBuf;
   protected int tabSize;
 
   static final int hexval(char c)
     throws IOException
   {
     switch (c) { case '0':
       return 0;
     case '1':
       return 1;
     case '2':
       return 2;
     case '3':
       return 3;
     case '4':
       return 4;
     case '5':
       return 5;
     case '6':
       return 6;
     case '7':
       return 7;
     case '8':
       return 8;
     case '9':
       return 9;
     case 'A':
     case 'a':
       return 10;
     case 'B':
     case 'b':
       return 11;
     case 'C':
     case 'c':
       return 12;
     case 'D':
     case 'd':
       return 13;
     case 'E':
     case 'e':
       return 14;
     case 'F':
     case 'f':
       return 15;
     case ':':
     case ';':
     case '<':
     case '=':
     case '>':
     case '?':
     case '@':
     case 'G':
     case 'H':
     case 'I':
     case 'J':
     case 'K':
     case 'L':
     case 'M':
     case 'N':
     case 'O':
     case 'P':
     case 'Q':
     case 'R':
     case 'S':
     case 'T':
     case 'U':
     case 'V':
     case 'W':
     case 'X':
     case 'Y':
     case 'Z':
     case '[':
     case '\\':
     case ']':
     case '^':
     case '_':
     case '`': } throw new IOException();
   }
 
   protected void setTabSize(int i)
   {
     this.tabSize = i;
   }
 
   protected int getTabSize(int i) {
     return this.tabSize;
   }
 
   protected void ExpandBuff(boolean wrapAround) {
     char[] newbuffer = new char[this.bufsize + 15];
     int[] newbufline = new int[this.bufsize + 15];
     int[] newbufcolumn = new int[this.bufsize + 15];
     try
     {
       if (wrapAround) {
         System.arraycopy(this.buffer, this.tokenBegin, newbuffer, 0, this.bufsize - this.tokenBegin);
 
         System.arraycopy(this.buffer, 0, newbuffer, this.bufsize - this.tokenBegin, this.bufpos);
 
         this.buffer = newbuffer;
 
         System.arraycopy(this.bufline, this.tokenBegin, newbufline, 0, this.bufsize - this.tokenBegin);
 
         System.arraycopy(this.bufline, 0, newbufline, this.bufsize - this.tokenBegin, this.bufpos);
 
         this.bufline = newbufline;
 
         System.arraycopy(this.bufcolumn, this.tokenBegin, newbufcolumn, 0, this.bufsize - this.tokenBegin);
 
         System.arraycopy(this.bufcolumn, 0, newbufcolumn, this.bufsize - this.tokenBegin, this.bufpos);
 
         this.bufcolumn = newbufcolumn;
 
         this.bufpos += this.bufsize - this.tokenBegin;
       } else {
         System.arraycopy(this.buffer, this.tokenBegin, newbuffer, 0, this.bufsize - this.tokenBegin);
 
         this.buffer = newbuffer;
 
         System.arraycopy(this.bufline, this.tokenBegin, newbufline, 0, this.bufsize - this.tokenBegin);
 
         this.bufline = newbufline;
 
         System.arraycopy(this.bufcolumn, this.tokenBegin, newbufcolumn, 0, this.bufsize - this.tokenBegin);
 
         this.bufcolumn = newbufcolumn;
 
         this.bufpos -= this.tokenBegin;
       }
     } catch (Throwable t) {
       throw new Error(t.getMessage());
     }
 
     this.available = (this.bufsize += 15);
     this.tokenBegin = 0;
   }
 
   protected void FillBuff() throws IOException
   {
     if (this.maxNextCharInd == 30)
       this.maxNextCharInd = (this.nextCharInd = 0);
     try
     {
       int i;
       if ((i = this.inputStream.read(this.nextCharBuf, this.maxNextCharInd, 30 - this.maxNextCharInd)) == -1)
       {
         this.inputStream.close();
         throw new IOException();
       }
       this.maxNextCharInd += i;
       return;
     } catch (IOException e) {
       if (this.bufpos != 0) {
         this.bufpos -= 1;
         backup(0);
       } else {
         this.bufline[this.bufpos] = this.line;
         this.bufcolumn[this.bufpos] = this.column;
       }
       throw e;
     }
   }
 
   protected char ReadByte() throws IOException {
     if (++this.nextCharInd >= this.maxNextCharInd) {
       FillBuff();
     }
     return this.nextCharBuf[this.nextCharInd];
   }
 
   public char BeginToken() throws IOException {
     if (this.inBuf > 0) {
       this.inBuf -= 1;
 
       if (++this.bufpos == this.bufsize) {
         this.bufpos = 0;
       }
       this.tokenBegin = this.bufpos;
       return this.buffer[this.bufpos];
     }
 
     this.tokenBegin = 0;
     this.bufpos = -1;
 
     return readChar();
   }
 
   protected void AdjustBuffSize() {
     if (this.available == this.bufsize)
       if (this.tokenBegin > 15) {
         this.bufpos = 0;
         this.available = this.tokenBegin;
       } else {
         ExpandBuff(false); }
     else if (this.available > this.tokenBegin)
       this.available = this.bufsize;
     else if (this.tokenBegin - this.available < 15)
       ExpandBuff(true);
     else
       this.available = this.tokenBegin;
   }
 
   protected void UpdateLineColumn(char c) {
     this.column += 1;
 
     if (this.prevCharIsLF) {
       this.prevCharIsLF = false;
       this.line += (this.column = 1);
     } else if (this.prevCharIsCR) {
       this.prevCharIsCR = false;
       if (c == '\n')
         this.prevCharIsLF = true;
       else {
         this.line += (this.column = 1);
       }
     }
     switch (c)
     {
     case '\r':
       this.prevCharIsCR = true;
       break;
     case '\n':
       this.prevCharIsLF = true;
       break;
     case '\t':
       this.column -= 1;
       this.column += this.tabSize - (this.column % this.tabSize);
     case '\11':
     case '\f':
     }
 
     this.bufline[this.bufpos] = this.line;
     this.bufcolumn[this.bufpos] = this.column;
   }
 
   public char readChar()
     throws IOException
   {
     char c;
     if (this.inBuf > 0) {
       this.inBuf -= 1;
 
       if (++this.bufpos == this.bufsize) {
         this.bufpos = 0;
       }
       return this.buffer[this.bufpos];
     }
 
     if (++this.bufpos == this.available) {
       AdjustBuffSize();
     }
     if ((this.buffer[this.bufpos] = c = ReadByte()) == '\\') {
       UpdateLineColumn(c);
 
       int backSlashCnt = 1;
       while (true)
       {
         if (++this.bufpos == this.available)
           AdjustBuffSize();
         try
         {
           if ((this.buffer[this.bufpos] = c = ReadByte()) != '\\') {
             UpdateLineColumn(c);
 
             if ((c == 'u') && ((backSlashCnt & 0x1) == 1)) {
               if (--this.bufpos < 0) {
                 this.bufpos = (this.bufsize - 1);
               }
               break label224:
             }
 
             backup(backSlashCnt);
             return '\\';
           }
         } catch (IOException e) {
           if (backSlashCnt > 1) {
             backup(backSlashCnt);
           }
           return '\\';
         }
 
         UpdateLineColumn(c);
         ++backSlashCnt;
       }
 
       try
       {
         while ((c = ReadByte()) == 'u') {
           label224: this.column += 1;
         }
         this.buffer[this.bufpos] = (c = (char)(hexval(c) << 12 | hexval(ReadByte()) << 8 | hexval(ReadByte()) << 4 | hexval(ReadByte())));
 
         this.column += 4;
       } catch (IOException e) {
         throw new Error("Invalid escape character at line " + this.line + " column " + this.column + ".");
       }
 
       if (backSlashCnt == 1) {
         return c;
       }
       backup(backSlashCnt - 1);
       return '\\';
     }
 
     UpdateLineColumn(c);
     return c;
   }
 
   /** @deprecated */
   public int getColumn()
   {
     return this.bufcolumn[this.bufpos];
   }
 
   /** @deprecated */
   public int getLine()
   {
     return this.bufline[this.bufpos];
   }
 
   public int getEndColumn() {
     return this.bufcolumn[this.bufpos];
   }
 
   public int getEndLine() {
     return this.bufline[this.bufpos];
   }
 
   public int getBeginColumn() {
     return this.bufcolumn[this.tokenBegin];
   }
 
   public int getBeginLine() {
     return this.bufline[this.tokenBegin];
   }
 
   public void backup(int amount)
   {
     this.inBuf += amount;
     if (this.bufpos -= amount < 0)
       this.bufpos += this.bufsize;
   }
 
   public JavaCharStream(Reader dstream, int startline, int startcolumn, int buffersize)
   {
     this.bufpos = -1;
 
     this.column = 0;
 
     this.line = 1;
 
     this.prevCharIsCR = false;
 
     this.prevCharIsLF = false;
 
     this.maxNextCharInd = 0;
 
     this.nextCharInd = -1;
 
     this.inBuf = 0;
 
     this.tabSize = 8;
 
     this.inputStream = dstream;
     this.line = startline;
     this.column = (startcolumn - 1);
 
     this.available = (this.bufsize = buffersize);
     this.buffer = new char[buffersize];
     this.bufline = new int[buffersize];
     this.bufcolumn = new int[buffersize];
     this.nextCharBuf = new char[30];
   }
 
   public JavaCharStream(Reader dstream, int startline, int startcolumn)
   {
     this(dstream, startline, startcolumn, 30);
   }
 
   public JavaCharStream(Reader dstream)
   {
     this(dstream, 1, 1, 30);
   }
 
   public void ReInit(Reader dstream, int startline, int startcolumn, int buffersize)
   {
     this.inputStream = dstream;
     this.line = startline;
     this.column = (startcolumn - 1);
 
     if ((this.buffer == null) || (buffersize != this.buffer.length)) {
       this.available = (this.bufsize = buffersize);
       this.buffer = new char[buffersize];
       this.bufline = new int[buffersize];
       this.bufcolumn = new int[buffersize];
       this.nextCharBuf = new char[30];
     }
     this.prevCharIsLF = (this.prevCharIsCR = 0);
     this.tokenBegin = (this.inBuf = this.maxNextCharInd = 0);
     this.nextCharInd = (this.bufpos = -1);
   }
 
   public void ReInit(Reader dstream, int startline, int startcolumn) {
     ReInit(dstream, startline, startcolumn, 30);
   }
 
   public void ReInit(Reader dstream) {
     ReInit(dstream, 1, 1, 30);
   }
 
   public JavaCharStream(InputStream dstream, String encoding, int startline, int startcolumn, int buffersize)
     throws UnsupportedEncodingException
   {
     this(new InputStreamReader(dstream, encoding), startline, startcolumn, buffersize);
   }
 
   public JavaCharStream(InputStream dstream, int startline, int startcolumn, int buffersize)
   {
     this(new InputStreamReader(dstream), startline, startcolumn, 30);
   }
 
   public JavaCharStream(InputStream dstream, String encoding, int startline, int startcolumn)
     throws UnsupportedEncodingException
   {
     this(dstream, encoding, startline, startcolumn, 30);
   }
 
   public JavaCharStream(InputStream dstream, int startline, int startcolumn)
   {
     this(dstream, startline, startcolumn, 30);
   }
 
   public JavaCharStream(InputStream dstream, String encoding) throws UnsupportedEncodingException
   {
     this(dstream, encoding, 1, 1, 30);
   }
 
   public JavaCharStream(InputStream dstream) {
     this(dstream, 1, 1, 30);
   }
 
   public void ReInit(InputStream dstream, String encoding, int startline, int startcolumn, int buffersize)
     throws UnsupportedEncodingException
   {
     ReInit(new InputStreamReader(dstream, encoding), startline, startcolumn, buffersize);
   }
 
   public void ReInit(InputStream dstream, int startline, int startcolumn, int buffersize)
   {
     ReInit(new InputStreamReader(dstream), startline, startcolumn, buffersize);
   }
 
   public void ReInit(InputStream dstream, String encoding, int startline, int startcolumn)
     throws UnsupportedEncodingException
   {
     ReInit(dstream, encoding, startline, startcolumn, 30);
   }
 
   public void ReInit(InputStream dstream, int startline, int startcolumn)
   {
     ReInit(dstream, startline, startcolumn, 30);
   }
 
   public void ReInit(InputStream dstream, String encoding) throws UnsupportedEncodingException
   {
     ReInit(dstream, encoding, 1, 1, 30);
   }
 
   public void ReInit(InputStream dstream) {
     ReInit(dstream, 1, 1, 30);
   }
 
   public String GetImage() {
     if (this.bufpos >= this.tokenBegin) {
       return new String(this.buffer, this.tokenBegin, this.bufpos - this.tokenBegin + 1);
     }
     return new String(this.buffer, this.tokenBegin, this.bufsize - this.tokenBegin) + new String(this.buffer, 0, this.bufpos + 1);
   }
 
   public char[] GetSuffix(int len)
   {
     char[] ret = new char[len];
 
     if (this.bufpos + 1 >= len) {
       System.arraycopy(this.buffer, this.bufpos - len + 1, ret, 0, len);
     } else {
       System.arraycopy(this.buffer, this.bufsize - (len - this.bufpos - 1), ret, 0, len - this.bufpos - 1);
 
       System.arraycopy(this.buffer, 0, ret, len - this.bufpos - 1, this.bufpos + 1);
     }
 
     return ret;
   }
 
   public void Done() {
     this.nextCharBuf = null;
     this.buffer = null;
     this.bufline = null;
     this.bufcolumn = null;
   }
 
   public void adjustBeginLineColumn(int newLine, int newCol)
   {
     int len;
     int start = this.tokenBegin;
 
     if (this.bufpos >= this.tokenBegin)
       len = this.bufpos - this.tokenBegin + this.inBuf + 1;
     else {
       len = this.bufsize - this.tokenBegin + this.bufpos + 1 + this.inBuf;
     }
 
     int i = 0; int j = 0; int k = 0;
     int nextColDiff = 0; int columnDiff = 0;
 
     while (i < len) { j = start % this.bufsize; if (this.bufline[j] != this.bufline[(k = ++start % this.bufsize)])
         break;
       this.bufline[j] = newLine;
       nextColDiff = columnDiff + this.bufcolumn[k] - this.bufcolumn[j];
       this.bufcolumn[j] = (newCol + columnDiff);
       columnDiff = nextColDiff;
       ++i;
     }
 
     if (i < len) {
       this.bufline[j] = (newLine++);
       this.bufcolumn[j] = (newCol + columnDiff);
 
       while (i++ < len) {
         if (this.bufline[(j = start % this.bufsize)] != this.bufline[(++start % this.bufsize)]) {
           this.bufline[j] = (newLine++);
         }
         this.bufline[j] = newLine;
       }
     }
 
     this.line = this.bufline[j];
     this.column = this.bufcolumn[j];
   }
 }