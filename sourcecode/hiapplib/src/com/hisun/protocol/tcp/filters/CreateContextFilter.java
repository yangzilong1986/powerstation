/*    */ package com.hisun.protocol.tcp.filters;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.protocol.tcp.ISocketHandler;
/*    */ import com.hisun.protocol.tcp.ISocketHandlerFilter;
/*    */ import java.net.Socket;
/*    */ 
/*    */ public class CreateContextFilter
/*    */   implements ISocketHandlerFilter
/*    */ {
/*    */   private final String _name;
/*    */   private final String _type;
/*    */ 
/*    */   public CreateContextFilter(String name, String type)
/*    */   {
/* 22 */     this._name = name;
/* 23 */     this._type = type;
/*    */   }
/*    */ 
/*    */   public HiMessage getHiMessage() {
/* 27 */     HiMessage msg = new HiMessage(this._name, this._type);
/*    */ 
/* 30 */     msg.setHeadItem("ECT", "text/plain");
/* 31 */     msg.setHeadItem("SCH", "rq");
/* 32 */     return msg;
/*    */   }
/*    */ 
/*    */   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException
/*    */   {
/* 37 */     ctx = new HiMessageContext();
/* 38 */     ctx.setCurrentMsg(getHiMessage());
/* 39 */     handler.process(socket, ctx);
/*    */   }
/*    */ }