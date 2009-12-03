/*    */ package com.hisun.protocol.tcp.filters;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.protocol.tcp.ISocketHandler;
/*    */ import com.hisun.protocol.tcp.ISocketHandlerFilter;
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ public class CloseSocketFilter
/*    */   implements ISocketHandlerFilter
/*    */ {
/* 28 */   static HiStringManager sm = HiStringManager.getManager();
/*    */   private Logger log;
/*    */ 
/*    */   public CloseSocketFilter(Logger log)
/*    */   {
/* 34 */     this.log = log;
/*    */   }
/*    */ 
/*    */   protected void closeSocketQuietly(Socket socket) {
/*    */     try {
/* 39 */       socket.close();
/*    */     } catch (IOException e) {
/*    */     }
/*    */   }
/*    */ 
/*    */   private void debugInfo(HiMessageContext ctx) {
/* 45 */     if (StringUtils.equalsIgnoreCase(System.getProperty("ics.env"), "test")) {
/* 46 */       Long tm = (Long)ctx.getCurrentMsg().getObjectHeadItem("STM");
/*    */ 
/* 48 */       int tmOut = NumberUtils.toInt(System.getProperty("TMOUT"));
/* 49 */       if (tmOut <= 0) {
/* 50 */         tmOut = 5000;
/*    */       }
/* 52 */       if (System.currentTimeMillis() - tm.longValue() > tmOut) {
/* 53 */         if (this.log.isInfoEnabled())
/* 54 */           this.log.info(ctx.getCurrentMsg().getRequestId() + "交易时间:[" + (System.currentTimeMillis() - tm.longValue()) + "]");
/*    */       }
/*    */       else
/*    */       {
/* 58 */         File f = new File(HiICSProperty.getTrcDir() + ctx.getCurrentMsg().getRequestId() + ".trc");
/*    */ 
/* 60 */         f.delete();
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException
/*    */   {
/*    */     try {
/* 68 */       handler.process(socket, ctx);
/*    */     } finally {
/* 70 */       closeSocketQuietly(socket);
/* 71 */       if (this.log.isInfoEnabled())
/* 72 */         this.log.info(sm.getString("tcplistener.info.clientclose", socket.toString()));
/*    */     }
/*    */   }
/*    */ }