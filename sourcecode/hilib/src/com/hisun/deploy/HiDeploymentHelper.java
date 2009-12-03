/*     */ package com.hisun.deploy;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiDeploymentHelper
/*     */ {
/*     */   private static String _type;
/*  20 */   private static HiDeploymentDescriptor descriptor = null;
/*     */ 
/*     */   public static void init(String type, boolean reload)
/*     */     throws HiException
/*     */   {
/*  35 */     if ((!(reload)) && (_type == type) && (descriptor != null))
/*     */     {
/*  37 */       return;
/*     */     }
/*  39 */     descriptor = null;
/*  40 */     _type = type;
/*     */ 
/*  42 */     if (StringUtils.equalsIgnoreCase(type, "websphere"))
/*     */     {
/*  44 */       descriptor = new HiWSDescriptor();
/*     */     }
/*     */     else
/*     */     {
/*  49 */       descriptor = new HiWLDescriptor();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void createDescriptor(String serviceName, String destPath)
/*     */     throws HiException
/*     */   {
/*  66 */     String fullPath = destPath + "/META-INF/";
/*  67 */     File path = new File(fullPath);
/*  68 */     if (!(path.exists())) {
/*  69 */       path.mkdirs();
/*     */     }
/*     */ 
/*  72 */     if (descriptor == null)
/*     */       return;
/*  74 */     descriptor.createDescriptor(serviceName, fullPath);
/*     */   }
/*     */ 
/*     */   public static void deployManage(String mngType, String serviceName)
/*     */     throws HiException
/*     */   {
/*  89 */     if ((!(StringUtils.equalsIgnoreCase(mngType, "install"))) && (!(StringUtils.equalsIgnoreCase(mngType, "uninstall"))))
/*     */     {
/*  91 */       throw new HiException("", "not valid deploy type");
/*     */     }
/*     */ 
/*  94 */     URL fileUrl = HiResource.getResource("admin/ant.sh");
/*  95 */     if (fileUrl == null)
/*     */     {
/*  97 */       throw new HiException("", "file [admin/ant.sh] can't find");
/*     */     }
/*  99 */     String antSh = fileUrl.getFile();
/*     */ 
/* 101 */     fileUrl = HiResource.getResource("admin/build.xml");
/* 102 */     if (fileUrl == null)
/*     */     {
/* 104 */       throw new HiException("", "file [admin/build.xml] can't find");
/*     */     }
/* 106 */     String buildFile = fileUrl.getFile();
/*     */ 
/* 108 */     String antCmd = antSh + " " + buildFile + " " + serviceName + " " + mngType;
/*     */ 
/* 110 */     System.out.println("AntCmd:[" + antCmd + "]");
/*     */ 
/* 113 */     Process p = null;
/*     */     try
/*     */     {
/* 116 */       p = Runtime.getRuntime().exec(antCmd);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 120 */       e.printStackTrace();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 125 */       if (p != null)
/*     */       {
/* 127 */         p.waitFor();
/*     */ 
/* 129 */         if (p.exitValue() != 0)
/*     */         {
/* 131 */           System.out.println("exit ret:" + p.exitValue());
/*     */ 
/* 133 */           byte[] bb = new byte[1024];
/* 134 */           InputStream in = p.getErrorStream();
/*     */           try
/*     */           {
/* 137 */             in.read(bb);
/* 138 */             System.out.println("error[" + new String(bb) + "]");
/*     */           }
/*     */           catch (IOException e)
/*     */           {
/* 142 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 148 */         System.out.println("Failure: get process is null.");
/*     */       }
/*     */     }
/*     */     catch (InterruptedException ie) {
/* 152 */       System.out.println(ie);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Element loadTemplate(SAXReader saxReader, String file)
/*     */     throws HiException
/*     */   {
/* 165 */     URL fileUrl = HiResource.getResource(file);
/*     */     try
/*     */     {
/* 169 */       return saxReader.read(fileUrl).getRootElement();
/*     */     }
/*     */     catch (DocumentException e) {
/* 172 */       throw new HiException("", fileUrl.getFile(), e);
/*     */     }
/*     */   }
/*     */ }