 package com.hisun.engine;
 
 import com.hisun.engine.invoke.impl.HiAbstractApplication;
 import com.hisun.engine.invoke.impl.HiProcess;
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerDestroyListener;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.parse.HiCfgFile;
 import com.hisun.parse.HiResourceRule;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.register.HiRegisterService;
 import com.hisun.util.HiResource;
 import com.hisun.util.HiStringManager;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.LinkedHashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 
 public class HiEngine
   implements IServerInitListener, IServerDestroyListener, IHandler
 {
   private List appList = new ArrayList();
 
   private Map fileList = new LinkedHashMap();
 
   private HiAbstractApplication firstApp = null;
 
   private String strFirstFileName = null;
 
   private HashMap tranMaps = new HashMap();
 
   public final Logger log = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
 
   public void destroy()
     throws HiException
   {
     for (int i = 0; i < this.appList.size(); ++i) {
       HiAbstractApplication app = (HiAbstractApplication)this.appList.get(i);
       HiRegisterService.unregister(app.context);
     }
   }
 
   public void destroy(int second)
   {
   }
 
   public void errProcess(HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     String strCode = mess.getHeadItemRoot("ECO");
     if (log.isInfoEnabled()) {
       log.info(HiStringManager.getManager().getString("HiEngine.errProcess1", strCode));
     }
 
     mess.setHeadItem("SCH", "rp");
     mess.setHeadItem("STC", strCode);
 
     HiAbstractApplication app = (HiAbstractApplication)this.tranMaps.get(strCode);
 
     HiProcess.process(app, ctx);
   }
 
   public String getInfo() {
     return null;
   }
 
   public String getName() {
     return null;
   }
 
   public void init()
     throws HiException
   {
     Set set = this.fileList.entrySet();
     Iterator iter = set.iterator();
 
     while (iter.hasNext()) {
       Map.Entry en = (Map.Entry)iter.next();
       String strFileName = (String)en.getKey();
       String strType = (String)en.getValue();
 
       if (this.log.isInfoEnabled()) {
         this.log.info(HiStringManager.getManager().getString("HiEngine.init", strFileName));
       }
 
       URL fileUrl = HiResource.getResource(strFileName);
 
       if (fileUrl == null) {
         throw new HiException("213302", strFileName);
       }
 
       if (this.log.isInfoEnabled()) {
         this.log.info(HiStringManager.getManager().getString("HiEngine.init1", fileUrl));
       }
 
       HiAbstractApplication app = null;
       if ("CTL".equalsIgnoreCase(strType))
       {
         app = (HiAbstractApplication)HiResourceRule.getCTLCfgFile(fileUrl).getRootInstance();
       }
       else {
         app = (HiAbstractApplication)HiResourceRule.getITFCfgFile(fileUrl).getRootInstance();
       }
 
       app.setType(strType);
       app.setFileName(strFileName);
 
       this.appList.add(app);
 
       if (strFileName.equals(this.strFirstFileName)) {
         this.firstApp = app;
       }
 
       HiRegisterService.register(app.context);
 
       Set tranSet = app.getTranMap().entrySet();
       Iterator tranIter = tranSet.iterator();
       while (tranIter.hasNext()) {
         Map.Entry tranEn = (Map.Entry)tranIter.next();
         String strCode = (String)tranEn.getKey();
         this.tranMaps.put(strCode, app);
         if (this.log.isInfoEnabled())
           this.log.info("code=[" + strCode + "]");
       }
     }
   }
 
   public boolean isRunning()
   {
     return false;
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     String strCode = null;
     HiAbstractApplication app = null;
 
     strCode = mess.getHeadItem("STC");
     boolean isExistCode = true;
     if (StringUtils.isEmpty(strCode)) {
       this.firstApp.beforeProcess(ctx);
       isExistCode = false;
     }
     strCode = mess.getHeadItem("STC");
     if (StringUtils.isEmpty(strCode)) {
       throw new HiException("213303", strCode);
     }
 
     app = (HiAbstractApplication)this.tranMaps.get(strCode);
 
     if (app == null) {
       Iterator iter = this.tranMaps.entrySet().iterator();
       String strCodes = "";
       while (iter.hasNext()) {
         Map.Entry en = (Map.Entry)iter.next();
         strCodes = strCodes + "[" + ((String)en.getKey()) + "]";
       }
       throw new HiException("213303", strCode + ":" + strCodes);
     }
 
     if (isExistCode) {
       HiProcess.process(app, ctx);
     } else {
       app.process(ctx);
       app.afterProcess(ctx);
     }
   }
 
   public void setCTL(String strFileName)
   {
     setFileName(strFileName, "CTL");
   }
 
   public void setFileName(String strFileName, String strType) {
     if ((StringUtils.isEmpty(this.strFirstFileName)) && ("ITF".equals(strType)))
       this.strFirstFileName = strFileName;
     this.fileList.put(strFileName, strType);
   }
 
   public void setITF(String strFileName) {
     setFileName(strFileName, "ITF");
   }
 
   public void start()
   {
   }
 
   public void stop()
   {
   }
 
   public void serverInit(ServerEvent event)
     throws HiException
   {
     init();
   }
 
   public void serverDestroy(ServerEvent event) throws HiException {
     destroy();
   }
 
   public void manage(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     String fileNam = root.getChildValue("FIL_NAM");
     String cmdId = root.getChildValue("CMD_ID");
     if (!("reload".equalsIgnoreCase(cmdId))) return;
     try {
       reload(fileNam);
       root.setChildValue("RSP_CD", "000000");
     } catch (HiException e) {
       root.setChildValue("RSP_CD", e.getCode());
       root.setChildValue("RSP_MSG", e.getMessage());
       this.log.error(e, e);
     }
   }
 
   public void reload(String strFileName)
     throws HiException
   {
     Iterator iter = this.appList.iterator();
     ArrayList tmpAppList = new ArrayList();
     while (iter.hasNext())
     {
       Map.Entry tranEn;
       String strCode;
       HiAbstractApplication oldApp = (HiAbstractApplication)iter.next();
       if ((strFileName != null) && (!(StringUtils.equalsIgnoreCase(oldApp.getFileName(), strFileName)))) {
         continue;
       }
       HiAbstractApplication newApp = load(oldApp.getFileName(), oldApp.getType());
 
       iter.remove();
       HiRegisterService.unregister(oldApp.context);
 
       Set tranSet = oldApp.getTranMap().entrySet();
       Iterator tranIter = tranSet.iterator();
       while (tranIter.hasNext()) {
         tranEn = (Map.Entry)tranIter.next();
         strCode = (String)tranEn.getKey();
         if (this.tranMaps.containsKey(strCode)) {
           tranIter.remove();
         }
       }
 
       HiRegisterService.register(newApp.context);
       tranSet = newApp.getTranMap().entrySet();
       tranIter = tranSet.iterator();
       while (tranIter.hasNext()) {
         tranEn = (Map.Entry)tranIter.next();
         strCode = (String)tranEn.getKey();
         this.tranMaps.put(strCode, newApp);
         if (this.log.isInfoEnabled()) {
           this.log.info("code=[" + strCode + "]");
         }
       }
       if (StringUtils.equalsIgnoreCase(newApp.getFileName(), this.strFirstFileName)) {
         this.firstApp = newApp;
       }
       tmpAppList.add(newApp);
     }
     if (!(tmpAppList.isEmpty()))
       this.appList.addAll(tmpAppList);
   }
 
   private HiAbstractApplication load(String strFileName, String strType) throws HiException
   {
     if (this.log.isInfoEnabled()) {
       this.log.info(HiStringManager.getManager().getString("HiEngine.init", strFileName));
     }
 
     URL fileUrl = HiResource.getResource(strFileName);
 
     if (fileUrl == null) {
       throw new HiException("213302", strFileName);
     }
     if (this.log.isInfoEnabled()) {
       this.log.info(HiStringManager.getManager().getString("HiEngine.init1", fileUrl));
     }
 
     HiAbstractApplication app = null;
     if ("CTL".equalsIgnoreCase(strType)) {
       app = (HiAbstractApplication)HiResourceRule.getCTLCfgFile(fileUrl).getRootInstance();
     }
     else {
       app = (HiAbstractApplication)HiResourceRule.getITFCfgFile(fileUrl).getRootInstance();
     }
 
     app.setType(strType);
     app.setFileName(strFileName);
     return app;
   }
 }