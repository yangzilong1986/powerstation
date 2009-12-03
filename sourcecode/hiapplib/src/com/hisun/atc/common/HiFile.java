/*     */ package com.hisun.atc.common;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.pubinterface.HiCloseable;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiFile
/*     */   implements HiCloseable
/*     */ {
/*     */   private BufferedWriter _bw;
/*     */   private BufferedReader _br;
/*     */   private FileInputStream _fis;
/*     */   private String _name;
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  29 */     this._name = name;
/*     */   }
/*     */ 
/*     */   private String read(int offset, int len) throws HiException {
/*  33 */     char[] buffer = new char[len];
/*     */     try {
/*  35 */       this._br.read(buffer, offset, len);
/*     */     } catch (IOException e) {
/*  37 */       throw new HiException("220079", this._name, e);
/*     */     }
/*  39 */     return new String(buffer);
/*     */   }
/*     */ 
/*     */   public String read(int len) throws HiException {
/*  43 */     return read(0, len);
/*     */   }
/*     */ 
/*     */   public int readBytes(StringBuffer buffer, int len) throws HiException {
/*  47 */     byte[] charBuffer = new byte[len];
/*  48 */     int ret = 0;
/*     */     try {
/*  50 */       ret = this._fis.read(charBuffer, 0, len);
/*     */     } catch (IOException e) {
/*  52 */       throw new HiException("220079", this._name, e);
/*     */     }
/*  54 */     buffer.append(new String(charBuffer, 0, ret));
/*  55 */     return ret; }
/*     */ 
/*     */   public int readBytes(ByteArrayOutputStream buffer, int offset, int len) throws HiException {
/*  58 */     byte[] charBuffer = new byte[len];
/*  59 */     int ret = 0;
/*     */     try {
/*  61 */       this._fis.skip(offset);
/*  62 */       ret = this._fis.read(charBuffer, 0, len);
/*     */ 
/*  64 */       buffer.write(charBuffer);
/*     */     } catch (IOException e) {
/*  66 */       throw new HiException("220079", this._name, e);
/*     */     }
/*     */ 
/*  69 */     return ret;
/*     */   }
/*     */ 
/*     */   public int available() throws HiException {
/*     */     try {
/*  74 */       if (this._fis != null)
/*  75 */         return this._fis.available();
/*     */     }
/*     */     catch (IOException e) {
/*  78 */       throw new HiException(e);
/*     */     }
/*  80 */     return 0; }
/*     */ 
/*     */   public int read(StringBuffer buffer, int len) throws HiException {
/*  83 */     char[] charBuffer = new char[len];
/*  84 */     int ret = 0;
/*     */     try {
/*  86 */       ret = this._br.read(charBuffer, 0, len);
/*     */     } catch (IOException e) {
/*  88 */       throw new HiException("220079", this._name, e);
/*     */     }
/*  90 */     buffer.append(new String(charBuffer, 0, ret));
/*  91 */     return ret;
/*     */   }
/*     */ 
/*     */   public String readLine() throws HiException {
/*     */     String buffer;
/*     */     try {
/*  97 */       buffer = this._br.readLine();
/*     */     } catch (IOException e) {
/*  99 */       throw new HiException("220079", this._name, e);
/*     */     }
/* 101 */     return buffer;
/*     */   }
/*     */ 
/*     */   public void open(String fileName, String mode) throws HiException {
/* 105 */     this._name = fileName;
/*     */     try {
/* 107 */       if (StringUtils.equalsIgnoreCase(mode, "r")) {
/* 108 */         this._br = new BufferedReader(new FileReader(this._name));
/* 109 */         this._fis = new FileInputStream(this._name);
/* 110 */       } else if (StringUtils.equalsIgnoreCase(mode, "w")) {
/* 111 */         createFile(fileName);
/* 112 */         this._bw = new BufferedWriter(new FileWriter(this._name, false));
/*     */       }
/* 114 */       else if (StringUtils.equalsIgnoreCase(mode, "a")) {
/* 115 */         createFile(fileName);
/* 116 */         this._bw = new BufferedWriter(new FileWriter(this._name, true));
/*     */       }
/*     */       else
/*     */       {
/* 120 */         throw new HiException("220079", this._name + "[" + mode + "]");
/*     */       }
/*     */     } catch (IOException e) {
/* 123 */       throw new HiException("220079", this._name, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void createFile(String fileName) throws HiException {
/*     */     try {
/* 129 */       File f = new File(fileName);
/* 130 */       if (!(f.exists()))
/* 131 */         f.createNewFile();
/*     */     } catch (IOException e) {
/* 133 */       throw new HiException("215117", this._name, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(String value) throws HiException {
/*     */     try {
/* 139 */       this._bw.write(value);
/* 140 */       this._bw.flush();
/*     */     } catch (IOException e) {
/* 142 */       throw new HiException("220079", this._name, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close() throws HiException {
/*     */     try {
/* 148 */       if (this._br != null)
/* 149 */         this._br.close();
/* 150 */       if (this._bw != null)
/* 151 */         this._bw.close();
/* 152 */       if (this._fis != null)
/* 153 */         this._fis.close();
/*     */     } catch (IOException e) {
/* 155 */       throw new HiException("220079", this._name, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void copy(String srcFile, String destFile)
/*     */     throws HiException
/*     */   {
/* 171 */     copy(srcFile, destFile, false);
/*     */   }
/*     */ 
/*     */   public static void copy(String srcFile, String destFile, boolean append)
/*     */     throws HiException
/*     */   {
/* 189 */     InputStream in = null;
/* 190 */     OutputStream out = null;
/*     */     try {
/* 192 */       File file = new File(destFile);
/* 193 */       if (!(file.getParentFile().exists()))
/*     */       {
/* 195 */         file.getParentFile().mkdirs();
/*     */       }
/*     */ 
/* 198 */       in = new FileInputStream(srcFile);
/* 199 */       out = new FileOutputStream(destFile, append);
/* 200 */       byte[] buf = new byte[1024];
/*     */ 
/* 202 */       while ((len = in.read(buf)) > 0)
/*     */       {
/*     */         int len;
/* 203 */         out.write(buf, 0, len);
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */       String[] files;
/* 207 */       throw new HiException("220030", files, e);
/*     */     } finally {
/*     */       try {
/* 210 */         if (in != null)
/* 211 */           in.close();
/* 212 */         if (out != null)
/* 213 */           out.close();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ }