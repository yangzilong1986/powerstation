 package com.hisun.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.HiDefaultServer;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.IServerStartListener;
 import com.hisun.framework.event.IServerStopListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.util.ArrayList;
 
 public class HiAutoSendTransReqHandler
   implements IServerInitListener, IServerStartListener, IServerStopListener
 {
   private HiStringManager sm;
   private ArrayList codeParams;
   private String msgType;
   private Logger log;
   private HiDefaultServer server;
   private Thread thd;
 
   public HiAutoSendTransReqHandler()
   {
     this.sm = HiStringManager.getManager();
 
     this.codeParams = new ArrayList();
 
     this.msgType = "PLTIN0";
   }
 
   public void serverInit(ServerEvent arg0)
     throws HiException
   {
     this.log = arg0.getLog();
     this.server = ((HiDefaultServer)arg0.getServer());
   }
 
   public void setCodeList(String codeList) {
     parseCodeParam(codeList);
   }
 
   public String getCodeList() {
     return "";
   }
 
   public void serverStart(ServerEvent arg0) throws HiException {
     this.thd = new Thread(new HiAutoTriggerTrans());
     this.thd.start();
   }
 
   public String getMsgType() {
     return this.msgType;
   }
 
   public void setMsgType(String msgType) {
     this.msgType = msgType;
   }
 
   public void parseCodeParam(String codeParam) {
     String[] tmps1 = codeParam.split("\\|");
     for (int i = 0; i < tmps1.length; ++i) {
       String tmp = tmps1[i];
       int idx1 = tmp.indexOf(63);
       String code = tmp;
       HiETF root = HiETFFactory.createETF();
       if (idx1 != -1) {
         code = tmp.substring(0, idx1);
         String[] tmps2 = tmp.substring(idx1 + 1).split("&");
         for (int j = 0; j < tmps2.length; ++j) {
           idx1 = tmps2[j].indexOf(61);
           root.setChildValue(tmps2[j].substring(0, idx1), tmps2[j].substring(idx1 + 1));
         }
       }
 
       this.codeParams.add(new HiCodeParam(code, root));
     }
   }
 
   public void serverStop(ServerEvent arg0)
     throws HiException
   {
     if ((this.thd != null) && (this.thd.isAlive()))
       this.thd.interrupt();
   }
 
   class HiCodeParam
   {
     String code;
     HiETF param;
 
     public HiCodeParam(String paramString, HiETF paramHiETF)
     {
       this.code = paramString;
       this.param = param;
     }
   }
 
   class HiAutoTriggerTrans
     implements Runnable
   {
     public void run()
     {
       while (!(HiAutoSendTransReqHandler.this.server.isRunning())) {
         try {
           Thread.sleep(1000L);
         } catch (InterruptedException e) {
           e.printStackTrace();
           return;
         }
       }
 
       HiMessageContext ctx = new HiMessageContext();
       HiMessageContext.setCurrentMessageContext(ctx);
       for (int i = 0; i < HiAutoSendTransReqHandler.this.codeParams.size(); ++i) {
         if (Thread.currentThread().isInterrupted()) {
           return;
         }
         HiAutoSendTransReqHandler.HiCodeParam codeParam = (HiAutoSendTransReqHandler.HiCodeParam)HiAutoSendTransReqHandler.this.codeParams.get(i);
         HiMessage msg = createReqMsg(codeParam.code);
         ctx.setCurrentMsg(msg);
 
         msg.setBody(codeParam.param);
         if (HiAutoSendTransReqHandler.this.log.isInfoEnabled())
           HiAutoSendTransReqHandler.this.log.info(HiAutoSendTransReqHandler.this.sm.getString("HiAutoSendTransReqHandler.autotrigger", codeParam.code));
         try
         {
           HiAutoSendTransReqHandler.this.server.process(ctx);
         } catch (Throwable t) {
           HiAutoSendTransReqHandler.this.log.error(HiAutoSendTransReqHandler.this.sm.getString("HiAutoSendTransReqHandler.autotrigger.failure", codeParam.code), t);
         }
       }
     }
 
     public HiMessage createReqMsg(String txnCod)
     {
       HiMessage msg = new HiMessage(HiAutoSendTransReqHandler.this.server.getName(), HiAutoSendTransReqHandler.this.msgType);
       msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
 
       msg.setHeadItem("STC", txnCod);
       msg.setHeadItem("SCH", "rq");
       msg.setHeadItem("ECT", "text/etf");
       return msg;
     }
   }
 }