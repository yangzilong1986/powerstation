/*    */ package com.hisun.version;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FilenameFilter;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.jar.Attributes;
/*    */ import java.util.jar.JarFile;
/*    */ import java.util.jar.Manifest;
/*    */ 
/*    */ public class HiVersion
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws IOException
/*    */   {
/* 17 */     if (args.length != 2) {
/* 18 */       System.out.println("hiversion [-d|-f] jarfile");
/* 19 */       System.exit(-1);
/*    */     }
/* 21 */     ArrayList versionFiles = new ArrayList();
/*    */ 
/* 23 */     if ("-d".equals(args[0]))
/*    */     {
/* 25 */       dumpDirVersion(args[1], versionFiles);
/* 26 */     } else if ("-f".equals(args[0]))
/*    */     {
/* 28 */       dumpFileVersion(args[1], versionFiles);
/*    */     } else {
/* 30 */       System.out.println(args[0] + " invalid; hiversion [-d|-f] jarfile");
/* 31 */       System.exit(-1);
/*    */     }
/*    */ 
/* 34 */     StringBuffer buf = new StringBuffer();
/* 35 */     for (int i = 0; i < versionFiles.size(); ++i) {
/* 36 */       buf.setLength(0);
/* 37 */       HiFileVersionInfo info = (HiFileVersionInfo)versionFiles.get(i);
/* 38 */       buf.append(info.getFile());
/* 39 */       for (int j = info.getFile().length(); j < 40; ++j) {
/* 40 */         buf.append(" ");
/*    */       }
/* 42 */       buf.append(" version:[" + info.getVersion() + "]\t compile time:[" + info.getCompileTm() + "]");
/* 43 */       System.out.println(buf);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void dumpFileVersion(String file, ArrayList list) throws IOException {
/* 48 */     JarFile jf = new JarFile(file, true);
/* 49 */     Manifest mf = jf.getManifest();
/*    */ 
/* 51 */     Attributes attrs = mf.getMainAttributes();
/* 52 */     String version = attrs.getValue("IBS-Integrator-Module-Version");
/* 53 */     String compileTm = attrs.getValue("IBS-Integrator-Module-Compile-Time");
/* 54 */     if ((version == null) || (compileTm == null)) {
/* 55 */       return;
/*    */     }
/* 57 */     list.add(new HiFileVersionInfo(file, version, compileTm));
/*    */   }
/*    */ 
/*    */   public static void dumpDirVersion(String dir, ArrayList versionFiles) throws IOException {
/* 61 */     File f = new File(dir);
/* 62 */     String[] flist = f.list(new FilenameFilter() {
/*    */       public boolean accept(File dir, String name) {
/* 64 */         int idx = name.indexOf(46);
/* 65 */         if (idx == -1) {
/* 66 */           return false;
/*    */         }
/* 68 */         return "jar".equals(name.substring(idx + 1));
/*    */       }
/*    */     });
/* 71 */     for (int i = 0; i < flist.length; ++i)
/* 72 */       dumpFileVersion(dir + "/" + flist[i], versionFiles);
/*    */   }
/*    */ }