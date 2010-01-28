 package com.hisun.eci;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiStringManager;
 import org.apache.commons.lang.StringUtils;
 
 public class HiCicsCliSyncHandler
   implements IHandler, IServerInitListener
 {
   private static HiStringManager _sm = HiStringManager.getManager();
   private String systemName;
   private String userName;
   private String password;
   private int preLen;
   private int timeOut;
   private String progId;
   private int bufferLen;
   CicsCliSyncJni cicsCli;
   final Logger log;
   final HiStringManager sm;
 
   public HiCicsCliSyncHandler()
   {
     this.systemName = "";
     this.userName = "";
     this.password = "";
     this.preLen = 4;
 
     this.timeOut = 30;
     this.progId = "";
 
     this.bufferLen = 10241;
 
     this.cicsCli = new CicsCliSyncJni();
 
     this.log = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
 
     this.sm = HiStringManager.getManager(); }
 
   public String getSystemName() {
     return this.systemName;
   }
 
   public void setSystemName(String systemName) {
     this.systemName = systemName;
   }
 
   public String getUserName() {
     return this.userName;
   }
 
   public void setUserName(String userName) {
     this.userName = userName;
   }
 
   public String getPassword() {
     return this.password;
   }
 
   public void setPassword(String password) {
     this.password = password;
   }
 
   public int getPreLen() {
     return this.preLen;
   }
 
   public void setPreLen(String preLen) {
     this.preLen = Integer.parseInt(preLen);
   }
 
   public void setBufferLen(String bufferLen) {
     this.preLen = Integer.parseInt(bufferLen);
   }
 
   public String getProgId() {
     return this.progId;
   }
 
   public void setProgId(String progId) {
     this.progId = progId;
   }
 
   public int getTimeOut() {
     return this.timeOut;
   }
 
   public void setTimeOut(String timeOut) {
     this.timeOut = Integer.parseInt(timeOut);
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     if (this.log.isDebugEnabled()) {
       this.log.debug("HiCicsCliSyncHandler process:start");
     }
 
     HiMessage msg = ctx.getCurrentMsg();
     HiByteBuffer plainBytes = (HiByteBuffer)msg.getBody();
 
     String pid = msg.getHeadItemRoot("PID");
     if (StringUtils.isNotBlank(pid))
     {
       this.cicsCli.setProgId(pid);
     }
     if (this.log.isInfoEnabled())
     {
       this.log.info(_sm.getString("ibs.send", msg.getRequestId(), this.cicsCli.getProgId(), String.valueOf(plainBytes.length()), plainBytes.toString()));
     }
     byte[] recvBuffer = new byte[this.bufferLen];
     int ret = this.cicsCli.clientSyncOut(plainBytes.getBytes(), recvBuffer);
 
     switch (ret)
     {
     case -1:
       throw new HiException("231207", "ECI");
     case -2:
       throw new HiException("231205", "ECI");
     }
 
     if (ret > 0)
     {
       msg.setHeadItem("SSC", "000000");
       HiByteBuffer result = new HiByteBuffer(ret);
       result.append(recvBuffer, 0, ret);
       msg.setHeadItem("SCH", "rp");
       msg.setBody(result);
 
       if (this.log.isInfoEnabled()) {
         this.log.info(_sm.getString("ibs.recv", msg.getRequestId(), this.cicsCli.getProgId(), String.valueOf(ret), result.toString()));
       }
 
     }
 
     if (this.log.isDebugEnabled())
       this.log.debug("HiCicsCliSyncHandler process:end");
   }
 
   public void serverInit(ServerEvent arg0)
     throws HiException
   {
     if (this.log.isDebugEnabled()) {
       this.log.debug("HiCicsCliSyncHandler serverInit:start");
     }
 
     if (this.log.isInfoEnabled())
     {
       this.log.info("SystemName[" + this.systemName + "] userName[" + this.userName + "],password[" + this.password + "]");
     }
     this.cicsCli.init(this.systemName, this.userName, this.password, this.progId, this.timeOut);
 
     if (this.log.isDebugEnabled())
       this.log.debug("HiCicsCliSyncHandler serverInit:end");
   }
 }