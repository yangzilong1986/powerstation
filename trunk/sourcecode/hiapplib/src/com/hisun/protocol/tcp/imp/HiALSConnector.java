 package com.hisun.protocol.tcp.imp;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerStartListener;
 import com.hisun.framework.event.IServerStopListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.HiConnection;
 import com.hisun.pubinterface.IHandler;
 import java.io.IOException;
 import java.util.HashSet;
 import java.util.Iterator;
 
 public class HiALSConnector extends HiPoolTcpConnector
   implements IServerStartListener, IServerStopListener, IHandler
 {
   public void process(HiMessageContext ctx)
     throws HiException
   {
     try
     {
       send(ctx.getCurrentMsg());
     } catch (HiException e) {
       this.log.error(e);
       throw e;
     }
   }
 
   public void initConnection() throws IOException, InterruptedException
   {
     setExpirationTimeout(2147483647);
 
     HiConnection[] conns = new HiConnection[getCapacity()];
     for (int i = 0; i < getCapacity(); ++i) {
       conns[i] = getConnection();
     }
 
     for (i = 0; i < getCapacity(); ++i)
       conns[i].returnToPool();
   }
 
   public void closeConnection()
   {
     for (Iterator iter = this.connections.iterator(); iter.hasNext(); ) {
       HiConnection conn = (HiConnection)iter.next();
       conn.close();
     }
 
     this.connections.clear();
   }
 
   public void serverStart(ServerEvent event) throws HiException {
     try {
       initConnection();
     } catch (Exception e) {
       throw new HiException(e);
     }
   }
 
   public void serverStop(ServerEvent event) throws HiException
   {
     closeConnection();
   }
 }