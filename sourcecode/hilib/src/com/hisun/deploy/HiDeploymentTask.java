/*    */ package com.hisun.deploy;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import java.io.PrintStream;
/*    */ import org.apache.tools.ant.Task;
/*    */ 
/*    */ public class HiDeploymentTask extends Task
/*    */ {
/*    */   String type;
/*    */   String serviceName;
/*    */   String destPath;
/*    */   boolean reload;
/*    */ 
/*    */   public HiDeploymentTask()
/*    */   {
/* 16 */     this.destPath = "./";
/*    */ 
/* 19 */     this.reload = false;
/*    */   }
/*    */ 
/*    */   public void setType(String type) {
/* 23 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public void setServiceName(String serviceName)
/*    */   {
/* 28 */     this.serviceName = serviceName;
/*    */   }
/*    */ 
/*    */   public void setDestPath(String destPath)
/*    */   {
/* 33 */     this.destPath = destPath;
/* 34 */     if ((this.destPath == null) || (this.destPath.endsWith("/")))
/*    */       return;
/* 36 */     this.destPath += "/";
/*    */   }
/*    */ 
/*    */   public void setReload(String reload)
/*    */   {
/* 42 */     if (reload != "true")
/*    */       return;
/* 44 */     this.reload = true;
/*    */   }
/*    */ 
/*    */   public void execute()
/*    */   {
/* 53 */     if ((this.serviceName == null) || (this.serviceName == ""))
/*    */     {
/* 55 */       System.out.println("HiDeploymentTask: seriveName is null, failure!");
/* 56 */       return;
/*    */     }
/*    */ 
/*    */     try
/*    */     {
/* 61 */       HiDeploymentHelper.init(this.type, this.reload);
/* 62 */       HiDeploymentHelper.createDescriptor(this.serviceName, this.destPath);
/*    */     }
/*    */     catch (HiException e)
/*    */     {
/* 66 */       e.printStackTrace();
/*    */     }
/*    */     catch (Exception t)
/*    */     {
/* 70 */       System.out.println("error ...");
/* 71 */       t.printStackTrace();
/*    */     }
/*    */   }
/*    */ }