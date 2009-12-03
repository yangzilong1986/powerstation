/*    */ package com.hisun.cnaps.handler;
/*    */ 
/*    */ import com.hisun.cnaps.HiCnapsCodeTable;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.IServerInitListener;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiResource;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import java.io.File;
/*    */ import java.net.URL;
/*    */ import org.dom4j.DocumentException;
/*    */ 
/*    */ public class HiCnapsParseHandler
/*    */   implements IHandler, IServerInitListener
/*    */ {
/* 30 */   final Logger log = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*    */   HiCnapsCodeTable codeTable;
/*    */   private String cfgFile;
/* 33 */   final HiStringManager sm = HiStringManager.getManager();
/*    */ 
/*    */   public HiCnapsParseHandler()
/*    */   {
/* 37 */     this.cfgFile = null;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext context)
/*    */     throws HiException
/*    */   {
/* 43 */     HiMessage msg = context.getCurrentMsg();
/* 44 */     this.log.info(this.sm.getString("HiCnapsHandler.process"));
/*    */   }
/*    */ 
/*    */   public void setCFG(String cfgFile)
/*    */   {
/* 49 */     if (this.log.isInfoEnabled())
/* 50 */       this.log.info(this.sm.getString("HiCnapsHandler.cfgFile"), cfgFile);
/* 51 */     this.cfgFile = cfgFile;
/*    */   }
/*    */ 
/*    */   public void serverInit(ServerEvent arg0)
/*    */     throws HiException
/*    */   {
/* 57 */     if (this.log.isInfoEnabled())
/* 58 */       this.log.info("HiCnapsHandler.init");
/* 59 */     URL fileUrl = HiResource.getResource(this.cfgFile);
/* 60 */     if (this.log.isInfoEnabled())
/*    */     {
/* 62 */       this.log.info(this.sm.getString("HiCnapsHandler.cfgFile"), fileUrl.toString());
/* 63 */       this.log.info("HiCnapsHandler.init.after");
/*    */     }
/* 65 */     if (fileUrl != null) {
/*    */       try {
/* 67 */         this.codeTable = HiCnapsCodeTable.load(fileUrl);
/*    */       } catch (DocumentException e) {
/* 69 */         throw new HiException("213319", new File(this.cfgFile).getAbsolutePath(), e);
/*    */       }
/*    */     }
/*    */ 
/* 73 */     throw new HiException("213302", new File(this.cfgFile).getAbsolutePath());
/*    */ 
/* 76 */     arg0.getServerContext().setProperty("cnaps_code", this.codeTable);
/*    */   }
/*    */ 
/*    */   public HiCnapsCodeTable getCodeTable()
/*    */   {
/* 81 */     return this.codeTable;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */     throws HiException
/*    */   {
/*    */   }
/*    */ }