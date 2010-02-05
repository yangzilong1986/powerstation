 package com.hisun.protocol.tcp.filters;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.ISocketHandler;
 import com.hisun.protocol.tcp.ISocketHandlerFilter;
 import java.net.Socket;
 
 public class CreateContextFilter
   implements ISocketHandlerFilter
 {
   private final String _name;
   private final String _type;
 
   public CreateContextFilter(String name, String type)
   {
     this._name = name;
     this._type = type;
   }
 
   public HiMessage getHiMessage() {
     HiMessage msg = new HiMessage(this._name, this._type);
 
     msg.setHeadItem("ECT", "text/plain");
     msg.setHeadItem("SCH", "rq");
     return msg;
   }
 
   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException
   {
     ctx = new HiMessageContext();
     ctx.setCurrentMsg(getHiMessage());
     handler.process(socket, ctx);
   }
 }