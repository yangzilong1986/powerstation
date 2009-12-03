/*     */ package com.hisun.encrypt;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.JarURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.JarInputStream;
/*     */ import java.util.jar.JarOutputStream;
/*     */ import java.util.jar.Manifest;
/*     */ import java.util.zip.ZipEntry;
/*     */ 
/*     */ public final class HiJarUtils
/*     */ {
/*     */   public static void jar(OutputStream out, File src)
/*     */     throws IOException
/*     */   {
/*  55 */     jar(out, new File[] { src }, null, null, null);
/*     */   }
/*     */ 
/*     */   public static void jar(OutputStream out, File[] src)
/*     */     throws IOException
/*     */   {
/*  78 */     jar(out, src, null, null, null);
/*     */   }
/*     */ 
/*     */   public static void jar(OutputStream out, File[] src, FileFilter filter)
/*     */     throws IOException
/*     */   {
/* 106 */     jar(out, src, filter, null, null);
/*     */   }
/*     */ 
/*     */   public static void jar(OutputStream out, File[] src, FileFilter filter, String prefix, Manifest man)
/*     */     throws IOException
/*     */   {
/*     */     JarOutputStream jout;
/* 137 */     for (int i = 0; i < src.length; ++i) {
/* 138 */       if (!(src[i].exists())) {
/* 139 */         throw new FileNotFoundException(src.toString());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 144 */     if (man == null)
/* 145 */       jout = new JarOutputStream(out);
/*     */     else {
/* 147 */       jout = new JarOutputStream(out, man);
/*     */     }
/* 149 */     if ((prefix != null) && (prefix.length() > 0) && (!(prefix.equals("/"))))
/*     */     {
/* 151 */       if (prefix.charAt(0) == '/') {
/* 152 */         prefix = prefix.substring(1);
/*     */       }
/*     */ 
/* 155 */       if (prefix.charAt(prefix.length() - 1) != '/')
/* 156 */         prefix = prefix + "/";
/*     */     }
/*     */     else {
/* 159 */       prefix = "";
/*     */     }
/* 161 */     JarInfo info = new JarInfo(jout, filter);
/* 162 */     for (int i = 0; i < src.length; ++i) {
/* 163 */       jar(src[i], prefix, info);
/*     */     }
/* 165 */     jout.close();
/*     */   }
/*     */ 
/*     */   private static void jar(File src, String prefix, JarInfo info)
/*     */     throws IOException
/*     */   {
/* 194 */     JarOutputStream jout = info.out;
/* 195 */     if (src.isDirectory())
/*     */     {
/* 197 */       prefix = prefix + src.getName() + "/";
/* 198 */       ZipEntry entry = new ZipEntry(prefix);
/* 199 */       entry.setTime(src.lastModified());
/* 200 */       entry.setMethod(0);
/* 201 */       entry.setSize(0L);
/* 202 */       entry.setCrc(0L);
/* 203 */       jout.putNextEntry(entry);
/* 204 */       jout.closeEntry();
/*     */ 
/* 207 */       File[] files = src.listFiles(info.filter);
/* 208 */       for (int i = 0; i < files.length; ++i)
/* 209 */         jar(files[i], prefix, info);
/*     */     } else {
/* 211 */       if (!(src.isFile()))
/*     */         return;
/* 213 */       byte[] buffer = info.buffer;
/*     */ 
/* 216 */       ZipEntry entry = new ZipEntry(prefix + src.getName());
/* 217 */       entry.setTime(src.lastModified());
/* 218 */       jout.putNextEntry(entry);
/*     */ 
/* 221 */       FileInputStream in = new FileInputStream(src);
/*     */ 
/* 223 */       while ((len = in.read(buffer, 0, buffer.length)) != -1)
/*     */       {
/*     */         int len;
/* 224 */         jout.write(buffer, 0, len);
/*     */       }
/* 226 */       in.close();
/* 227 */       jout.closeEntry();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void unjar(String jarFile, String destDir) throws IOException {
/* 232 */     BufferedInputStream in = new BufferedInputStream(new FileInputStream(jarFile));
/*     */ 
/* 234 */     File dest = new File(destDir);
/* 235 */     unjar(in, dest);
/*     */   }
/*     */ 
/*     */   public static void unjar(InputStream in, File dest)
/*     */     throws IOException
/*     */   {
/*     */     File file;
/*     */     File parent;
/*     */     OutputStream out;
/* 241 */     if (!(dest.exists())) {
/* 242 */       dest.mkdirs();
/*     */     }
/* 244 */     if (!(dest.isDirectory())) {
/* 245 */       throw new IOException("Destination must be a directory.");
/*     */     }
/* 247 */     JarInputStream jin = new JarInputStream(in);
/* 248 */     byte[] buffer = new byte[1024];
/*     */ 
/* 250 */     ZipEntry entry = jin.getNextEntry();
/* 251 */     while (entry != null) {
/* 252 */       String fileName = entry.getName();
/* 253 */       if (fileName.charAt(fileName.length() - 1) == '/') {
/* 254 */         fileName = fileName.substring(0, fileName.length() - 1);
/*     */       }
/* 256 */       if (fileName.charAt(0) == '/') {
/* 257 */         fileName = fileName.substring(1);
/*     */       }
/* 259 */       if (File.separatorChar != '/') {
/* 260 */         fileName = fileName.replace('/', File.separatorChar);
/*     */       }
/* 262 */       file = new File(dest, fileName);
/* 263 */       if (entry.isDirectory())
/*     */       {
/* 265 */         file.mkdirs();
/* 266 */         jin.closeEntry();
/*     */       }
/*     */       else {
/* 269 */         parent = file.getParentFile();
/* 270 */         if ((parent != null) && (!(parent.exists()))) {
/* 271 */           parent.mkdirs();
/*     */         }
/*     */ 
/* 275 */         out = new FileOutputStream(file);
/* 276 */         int len = 0;
/* 277 */         while ((len = jin.read(buffer, 0, buffer.length)) != -1) {
/* 278 */           out.write(buffer, 0, len);
/*     */         }
/* 280 */         out.flush();
/* 281 */         out.close();
/* 282 */         jin.closeEntry();
/* 283 */         file.setLastModified(entry.getTime());
/*     */       }
/* 285 */       entry = jin.getNextEntry();
/*     */     }
/*     */ 
/* 291 */     Manifest mf = jin.getManifest();
/* 292 */     if (mf != null) {
/* 293 */       file = new File(dest, "META-INF/MANIFEST.MF");
/* 294 */       parent = file.getParentFile();
/* 295 */       if (!(parent.exists())) {
/* 296 */         parent.mkdirs();
/*     */       }
/* 298 */       out = new FileOutputStream(file);
/* 299 */       mf.write(out);
/* 300 */       out.flush();
/* 301 */       out.close();
/*     */     }
/* 303 */     jin.close();
/*     */   }
/*     */ 
/*     */   public static URL extractNestedJar(URL jarURL, File dest)
/*     */     throws IOException
/*     */   {
/* 322 */     if (!(jarURL.getProtocol().equals("jar"))) {
/* 323 */       return jarURL;
/*     */     }
/* 325 */     String destPath = dest.getAbsolutePath();
/* 326 */     URLConnection urlConn = jarURL.openConnection();
/* 327 */     JarURLConnection jarConn = (JarURLConnection)urlConn;
/*     */ 
/* 329 */     String parentArchiveName = jarConn.getJarFile().getName();
/*     */ 
/* 331 */     int length = Math.min(destPath.length(), parentArchiveName.length());
/* 332 */     int n = 0;
/* 333 */     while (n < length) {
/* 334 */       char a = destPath.charAt(n);
/* 335 */       char b = parentArchiveName.charAt(n);
/* 336 */       if (a != b)
/*     */         break;
/* 338 */       ++n;
/*     */     }
/*     */ 
/* 341 */     parentArchiveName = parentArchiveName.substring(n);
/*     */ 
/* 343 */     File archiveDir = new File(dest, parentArchiveName + "-contents");
/* 344 */     if ((!(archiveDir.exists())) && (!(archiveDir.mkdirs()))) {
/* 345 */       throw new IOException("Failed to create contents directory for archive, path=" + archiveDir.getAbsolutePath());
/*     */     }
/*     */ 
/* 348 */     String archiveName = jarConn.getEntryName();
/* 349 */     File archiveFile = new File(archiveDir, archiveName);
/* 350 */     File archiveParentDir = archiveFile.getParentFile();
/* 351 */     if ((!(archiveParentDir.exists())) && (!(archiveParentDir.mkdirs())))
/*     */     {
/* 353 */       throw new IOException("Failed to create parent directory for archive, path=" + archiveParentDir.getAbsolutePath());
/*     */     }
/*     */ 
/* 356 */     InputStream archiveIS = jarConn.getInputStream();
/* 357 */     FileOutputStream fos = new FileOutputStream(archiveFile);
/* 358 */     BufferedOutputStream bos = new BufferedOutputStream(fos);
/* 359 */     byte[] buffer = new byte[4096];
/*     */ 
/* 361 */     while ((read = archiveIS.read(buffer)) > 0)
/*     */     {
/*     */       int read;
/* 362 */       bos.write(buffer, 0, read);
/*     */     }
/* 364 */     archiveIS.close();
/* 365 */     bos.close();
/*     */ 
/* 368 */     return archiveFile.toURL();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/* 376 */     args = new String[3];
/* 377 */     args[1] = "d:/project/eclipse/publibs/lib/hicommon.jar";
/* 378 */     args[2] = "d:/project/eclipse/tmp-extract/";
/*     */ 
/* 380 */     BufferedInputStream in = new BufferedInputStream(new FileInputStream(args[1]));
/*     */ 
/* 382 */     File dest = new File(args[2]);
/* 383 */     unjar(in, dest);
/*     */ 
/* 385 */     BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(args[1]));
/*     */ 
/* 387 */     File[] src = new File[args.length - 2];
/* 388 */     for (int i = 0; i < src.length; ++i) {
/* 389 */       src[i] = new File(args[(2 + i)]);
/*     */     }
/* 391 */     jar(out, src);
/*     */ 
/* 393 */     HiDirectoryUtil.deleteDirectory(args[1]);
/*     */   }
/*     */ 
/*     */   private static class JarInfo
/*     */   {
/*     */     public JarOutputStream out;
/*     */     public FileFilter filter;
/*     */     public byte[] buffer;
/*     */ 
/*     */     public JarInfo(JarOutputStream out, FileFilter filter)
/*     */     {
/* 181 */       this.out = out;
/* 182 */       this.filter = filter;
/* 183 */       this.buffer = new byte[1024];
/*     */     }
/*     */   }
/*     */ }