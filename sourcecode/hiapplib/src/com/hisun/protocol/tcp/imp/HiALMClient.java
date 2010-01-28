 package com.hisun.protocol.tcp.imp;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.protocol.tcp.HiAbstractALMClient;
 import com.hisun.protocol.tcp.HiMessageInOut;
 import org.apache.commons.lang.StringUtils;
 
 public class HiALMClient extends HiAbstractALMClient
 {
   private String _checkData;
   private HiMessageInOut messageInOut = new HiMessageInOut();
 
   public HiALMClient() {
     setMsginout(this.messageInOut); }
 
   public void setCheckData(String checkData) {
     this._checkData = checkData;
   }
 
   public String getCheckData() {
     return this._checkData;
   }
 
   public void setPreLenType(String preLenType) {
     this.messageInOut.setPreLenType(preLenType);
   }
 
   public String getPreLenType() {
     return this.messageInOut.getPreLenType(); }
 
   public void setPreLen(int preLen) {
     this.messageInOut.setPreLen(preLen);
   }
 
   public int getPreLen() {
     return this.messageInOut.getPreLen(); }
 
   public void serverInit(ServerEvent arg0) throws HiException {
     HiCheckConnImpl checkConn = new HiCheckConnImpl();
     if (StringUtils.isEmpty(this._checkData)) {
       int len = getMsginout().getPreLen();
       checkConn.setCheckData(StringUtils.repeat("0", len));
     } else {
       checkConn.setCheckData(this._checkData);
     }
     setCheckConn(checkConn);
     super.serverInit(arg0);
   }
 }