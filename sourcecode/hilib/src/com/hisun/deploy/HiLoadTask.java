/*    */ package com.hisun.deploy;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import java.io.PrintStream;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.tools.ant.Task;
/*    */ 
/*    */ public class HiLoadTask extends Task
/*    */ {
/*    */   private String name;
/*    */   private String mngType;
/*    */   private String type;
/*    */   private String url;
/*    */ 
/*    */   public void setUrl(String url)
/*    */   {
/* 24 */     this.url = url;
/*    */   }
/*    */ 
/*    */   public void setMngType(String mngType)
/*    */   {
/* 29 */     this.mngType = mngType;
/*    */   }
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 34 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public void setType(String type)
/*    */   {
/* 39 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public void execute()
/*    */   {
/* 47 */     if ((StringUtils.isEmpty(this.url)) || (StringUtils.isEmpty(this.name)) || (StringUtils.isEmpty(this.type)) || (StringUtils.isEmpty(this.mngType)))
/*    */     {
/* 50 */       System.out.println("url, name, type, mngType can't be null");
/* 51 */       return;
/*    */     }
/*    */ 
/* 54 */     String requestUrl = this.url + "?type=" + this.type + "&name=" + this.name + "&method=" + this.mngType;
/*    */     try
/*    */     {
/* 57 */       HiLoadHelper.execute(requestUrl);
/*    */     }
/*    */     catch (HiException he)
/*    */     {
/* 61 */       System.out.println("Operation [" + this.mngType + "] failure.");
/* 62 */       he.printStackTrace();
/*    */     }
/*    */   }
/*    */ }