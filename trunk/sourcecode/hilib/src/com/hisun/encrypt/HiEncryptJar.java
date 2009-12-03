/*     */ package com.hisun.encrypt;
/*     */ 
/*     */ import com.hisun.crypt.Decryptor;
/*     */ import com.hisun.crypt.Encryptor;
/*     */ import com.hisun.crypt.des.DESCryptorFactory;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.jar.JarInputStream;
/*     */ import java.util.jar.JarOutputStream;
/*     */ import java.util.zip.ZipEntry;
/*     */ 
/*     */ public class HiEncryptJar
/*     */ {
/*  21 */   private DES des = null;
/*     */ 
/*  23 */   private String key = "hisun";
/*     */ 
/*  25 */   private Decryptor decryptor = null;
/*     */ 
/*  27 */   private Encryptor encryptor = null;
/*     */ 
/*     */   public HiEncryptJar() throws Exception {
/*  30 */     DESCryptorFactory factory = new DESCryptorFactory();
/*  31 */     this.decryptor = factory.getDecryptor();
/*  32 */     this.decryptor.setKey(factory.getDefaultDecryptKey());
/*  33 */     this.encryptor = factory.getEncryptor();
/*  34 */     this.encryptor.setKey(factory.getDefaultEncryptKey());
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/*  38 */     HiEncryptJar encryptJar = new HiEncryptJar();
/*  39 */     boolean isEncrypt = true;
/*  40 */     if (args.length != 2) {
/*  41 */       throw new Exception("参数错误!!参数： jar文件名 [0-加密|1－解密]");
/*     */     }
/*     */ 
/*  44 */     if (args[1].equals("1")) {
/*  45 */       isEncrypt = false;
/*     */     }
/*  47 */     if (isEncrypt)
/*  48 */       encryptJar.encrypt(args[0]);
/*     */     else
/*  50 */       encryptJar.decrypt(args[0]);
/*     */   }
/*     */ 
/*     */   public void encrypt(String file)
/*     */     throws Exception
/*     */   {
/*     */     String bakFile;
/*  54 */     File f1 = new File(file);
/*     */ 
/*  56 */     if (f1.getParent() == null)
/*  57 */       bakFile = "." + File.separator + "encrypt-temp.jar";
/*     */     else {
/*  59 */       bakFile = f1.getParent() + File.separator + "encrypt-temp.jar";
/*     */     }
/*  61 */     OutputStream os = new FileOutputStream(bakFile);
/*  62 */     InputStream is = new FileInputStream(file);
/*  63 */     encrypt(is, os);
/*  64 */     is.close();
/*  65 */     os.close();
/*  66 */     File f2 = new File(bakFile);
/*  67 */     HiDirectoryUtil.copyFile(f2, f1);
/*  68 */     f2.delete();
/*     */   }
/*     */ 
/*     */   public void encrypt(InputStream in, OutputStream out) throws Exception {
/*  72 */     JarInputStream jin = new JarInputStream(in);
/*  73 */     JarOutputStream jout = new JarOutputStream(out, jin.getManifest());
/*  74 */     byte[] buffer = new byte[5];
/*  75 */     int len = 0;
/*  76 */     ZipEntry entry = jin.getNextEntry();
/*  77 */     for (; entry != null; entry = jin.getNextEntry())
/*  78 */       if (entry.isDirectory()) {
/*  79 */         jout.putNextEntry(entry);
/*  80 */         jout.closeEntry();
/*  81 */         jin.closeEntry();
/*     */       }
/*     */       else
/*     */       {
/*     */         int b;
/*  84 */         ZipEntry entry1 = new ZipEntry(entry.getName());
/*  85 */         jout.putNextEntry(entry1);
/*     */ 
/*  89 */         if (entry.getName().endsWith(".class")) {
/*  90 */           int j = 0;
/*  91 */           jout.write(this.key.getBytes());
/*  92 */           jin.read(buffer, 0, 5);
/*  93 */           if (this.key.equalsIgnoreCase(new String(buffer, 0, 5))) {
/*  94 */             throw new IOException("Already encrypt");
/*     */           }
/*  96 */           for (int i = 0; i < 5; ++i) {
/*  97 */             if (j == this.key.length())
/*  98 */               j = 0;
/*  99 */             jout.write(buffer[i] ^ this.key.charAt(j));
/* 100 */             ++j;
/*     */           }
/* 102 */           while ((b = jin.read()) != -1) {
/* 103 */             if (j == this.key.length())
/* 104 */               j = 0;
/* 105 */             jout.write(b ^ this.key.charAt(j));
/* 106 */             ++j;
/*     */           }
/*     */         } else {
/* 109 */           while ((b = jin.read()) != -1) {
/* 110 */             jout.write(b);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 122 */         jout.flush();
/* 123 */         jout.closeEntry();
/* 124 */         jin.closeEntry();
/*     */       }
/* 126 */     jin.close();
/* 127 */     jout.close();
/*     */   }
/*     */ 
/*     */   public void decrypt(String file) throws Exception {
/* 131 */     File f1 = new File(file);
/* 132 */     String bakFile = null;
/* 133 */     if (f1.getParent() == null)
/* 134 */       bakFile = "." + File.separator + "decrypt-temp.jar";
/*     */     else {
/* 136 */       bakFile = f1.getParent() + File.separator + "decrypt-temp.jar";
/*     */     }
/* 138 */     OutputStream os = new FileOutputStream(bakFile);
/* 139 */     InputStream is = new FileInputStream(file);
/* 140 */     decrypt(is, os);
/* 141 */     is.close();
/* 142 */     os.close();
/*     */ 
/* 144 */     File f2 = new File(bakFile);
/* 145 */     HiDirectoryUtil.copyFile(f2, f1);
/* 146 */     f2.delete();
/*     */   }
/*     */ 
/*     */   public void decrypt(InputStream in, OutputStream out) throws Exception {
/* 150 */     JarInputStream jin = new JarInputStream(in);
/* 151 */     JarOutputStream jout = new JarOutputStream(out, jin.getManifest());
/* 152 */     byte[] buffer = new byte[1024];
/* 153 */     int len = 0;
/* 154 */     ZipEntry entry = jin.getNextEntry();
/*     */ 
/* 156 */     for (; entry != null; entry = jin.getNextEntry())
/* 157 */       if (entry.isDirectory()) {
/* 158 */         jout.putNextEntry(entry);
/* 159 */         jout.closeEntry();
/* 160 */         jin.closeEntry();
/*     */       }
/*     */       else {
/* 163 */         ZipEntry entry1 = new ZipEntry(entry.getName());
/* 164 */         jout.putNextEntry(entry1);
/*     */ 
/* 166 */         ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
/* 167 */         while ((len = jin.read(buffer, 0, buffer.length)) != -1) {
/* 168 */           byteBuffer.write(buffer, 0, len);
/*     */         }
/* 170 */         buffer = byteBuffer.toByteArray();
/* 171 */         if (entry.getName().endsWith(".class"))
/* 172 */           jout.write(this.decryptor.decrypt(buffer, 6, buffer.length));
/*     */         else {
/* 174 */           jout.write(buffer);
/*     */         }
/*     */ 
/* 177 */         jout.flush();
/* 178 */         jout.closeEntry();
/* 179 */         jin.closeEntry();
/*     */       }
/* 181 */     jin.close();
/* 182 */     jout.close();
/*     */   }
/*     */ }