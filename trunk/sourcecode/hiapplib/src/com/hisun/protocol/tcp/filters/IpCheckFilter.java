 package com.hisun.protocol.tcp.filters;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.ISocketHandler;
 import com.hisun.protocol.tcp.ISocketHandlerFilter;
 import java.net.InetAddress;
 import java.net.Socket;
 import java.util.Set;
 
 public class IpCheckFilter
   implements ISocketHandlerFilter
 {
   private final Logger log;
   private final Set set;
 
   public IpCheckFilter(Set set, Logger log)
   {
     this.set = set;
     this.log = log;
   }
 
   boolean check(String clientip) {
     return this.set.contains(clientip);
   }
 
   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException
   {
     String clientip = socket.getInetAddress().getHostAddress();
     boolean canAccess = check(clientip);
     if (!(canAccess)) {
       this.log.error("illegal client connect:" + clientip);
 
       throw new HiException("illegal ip:" + clientip);
     }
 
     handler.process(socket, ctx);
   }
 }