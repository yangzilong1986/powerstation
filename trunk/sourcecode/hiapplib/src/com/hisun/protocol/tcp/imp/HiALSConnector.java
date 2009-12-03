/*    */ package com.hisun.protocol.tcp.imp;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.IServerStartListener;
/*    */ import com.hisun.framework.event.IServerStopListener;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.protocol.tcp.HiConnection;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import java.io.IOException;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class HiALSConnector extends HiPoolTcpConnector
/*    */   implements IServerStartListener, IServerStopListener, IHandler
/*    */ {
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/*    */     try
/*    */     {
/* 24 */       send(ctx.getCurrentMsg());
/*    */     } catch (HiException e) {
/* 26 */       this.log.error(e);
/* 27 */       throw e;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void initConnection() throws IOException, InterruptedException
/*    */   {
/* 33 */     setExpirationTimeout(2147483647);
/*    */ 
/* 37 */     HiConnection[] conns = new HiConnection[getCapacity()];
/* 38 */     for (int i = 0; i < getCapacity(); ++i) {
/* 39 */       conns[i] = getConnection();
/*    */     }
/*    */ 
/* 42 */     for (i = 0; i < getCapacity(); ++i)
/* 43 */       conns[i].returnToPool();
/*    */   }
/*    */ 
/*    */   public void closeConnection()
/*    */   {
/* 48 */     for (Iterator iter = this.connections.iterator(); iter.hasNext(); ) {
/* 49 */       HiConnection conn = (HiConnection)iter.next();
/* 50 */       conn.close();
/*    */     }
/*    */ 
/* 53 */     this.connections.clear();
/*    */   }
/*    */ 
/*    */   public void serverStart(ServerEvent event) throws HiException {
/*    */     try {
/* 58 */       initConnection();
/*    */     } catch (Exception e) {
/* 60 */       throw new HiException(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void serverStop(ServerEvent event) throws HiException
/*    */   {
/* 66 */     closeConnection();
/*    */   }
/*    */ }