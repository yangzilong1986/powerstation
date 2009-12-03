/*     */ package com.hisun.engine;
/*     */ 
/*     */ import com.hisun.engine.invoke.impl.HiAbstractApplication;
/*     */ import com.hisun.engine.invoke.impl.HiProcess;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerDestroyListener;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.parse.HiCfgFile;
/*     */ import com.hisun.parse.HiResourceRule;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiEngine
/*     */   implements IServerInitListener, IServerDestroyListener, IHandler
/*     */ {
/*  48 */   private List appList = new ArrayList();
/*     */ 
/*  50 */   private Map fileList = new LinkedHashMap();
/*     */ 
/*  54 */   private HiAbstractApplication firstApp = null;
/*     */ 
/*  56 */   private String strFirstFileName = null;
/*     */ 
/*  59 */   private HashMap tranMaps = new HashMap();
/*     */ 
/* 116 */   public final Logger log = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */ 
/*     */   public void destroy()
/*     */     throws HiException
/*     */   {
/*  67 */     for (int i = 0; i < this.appList.size(); ++i) {
/*  68 */       HiAbstractApplication app = (HiAbstractApplication)this.appList.get(i);
/*  69 */       HiRegisterService.unregister(app.context);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void destroy(int second)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void errProcess(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  86 */     HiMessage mess = ctx.getCurrentMsg();
/*  87 */     Logger log = HiLog.getLogger(mess);
/*  88 */     String strCode = mess.getHeadItemRoot("ECO");
/*  89 */     if (log.isInfoEnabled()) {
/*  90 */       log.info(HiStringManager.getManager().getString("HiEngine.errProcess1", strCode));
/*     */     }
/*     */ 
/*  93 */     mess.setHeadItem("SCH", "rp");
/*  94 */     mess.setHeadItem("STC", strCode);
/*     */ 
/*  96 */     HiAbstractApplication app = (HiAbstractApplication)this.tranMaps.get(strCode);
/*     */ 
/* 105 */     HiProcess.process(app, ctx);
/*     */   }
/*     */ 
/*     */   public String getInfo() {
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   public void init()
/*     */     throws HiException
/*     */   {
/* 125 */     Set set = this.fileList.entrySet();
/* 126 */     Iterator iter = set.iterator();
/*     */ 
/* 128 */     while (iter.hasNext()) {
/* 129 */       Map.Entry en = (Map.Entry)iter.next();
/* 130 */       String strFileName = (String)en.getKey();
/* 131 */       String strType = (String)en.getValue();
/*     */ 
/* 133 */       if (this.log.isInfoEnabled()) {
/* 134 */         this.log.info(HiStringManager.getManager().getString("HiEngine.init", strFileName));
/*     */       }
/*     */ 
/* 137 */       URL fileUrl = HiResource.getResource(strFileName);
/*     */ 
/* 139 */       if (fileUrl == null) {
/* 140 */         throw new HiException("213302", strFileName);
/*     */       }
/*     */ 
/* 147 */       if (this.log.isInfoEnabled()) {
/* 148 */         this.log.info(HiStringManager.getManager().getString("HiEngine.init1", fileUrl));
/*     */       }
/*     */ 
/* 152 */       HiAbstractApplication app = null;
/* 153 */       if ("CTL".equalsIgnoreCase(strType))
/*     */       {
/* 157 */         app = (HiAbstractApplication)HiResourceRule.getCTLCfgFile(fileUrl).getRootInstance();
/*     */       }
/*     */       else {
/* 160 */         app = (HiAbstractApplication)HiResourceRule.getITFCfgFile(fileUrl).getRootInstance();
/*     */       }
/*     */ 
/* 163 */       app.setType(strType);
/* 164 */       app.setFileName(strFileName);
/*     */ 
/* 167 */       this.appList.add(app);
/*     */ 
/* 169 */       if (strFileName.equals(this.strFirstFileName)) {
/* 170 */         this.firstApp = app;
/*     */       }
/*     */ 
/* 173 */       HiRegisterService.register(app.context);
/*     */ 
/* 176 */       Set tranSet = app.getTranMap().entrySet();
/* 177 */       Iterator tranIter = tranSet.iterator();
/* 178 */       while (tranIter.hasNext()) {
/* 179 */         Map.Entry tranEn = (Map.Entry)tranIter.next();
/* 180 */         String strCode = (String)tranEn.getKey();
/* 181 */         this.tranMaps.put(strCode, app);
/* 182 */         if (this.log.isInfoEnabled())
/* 183 */           this.log.info("code=[" + strCode + "]");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isRunning()
/*     */   {
/* 191 */     return false;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 198 */     HiMessage mess = ctx.getCurrentMsg();
/* 199 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/* 201 */     String strCode = null;
/* 202 */     HiAbstractApplication app = null;
/*     */ 
/* 204 */     strCode = mess.getHeadItem("STC");
/* 205 */     boolean isExistCode = true;
/* 206 */     if (StringUtils.isEmpty(strCode)) {
/* 207 */       this.firstApp.beforeProcess(ctx);
/* 208 */       isExistCode = false;
/*     */     }
/* 210 */     strCode = mess.getHeadItem("STC");
/* 211 */     if (StringUtils.isEmpty(strCode)) {
/* 212 */       throw new HiException("213303", strCode);
/*     */     }
/*     */ 
/* 215 */     app = (HiAbstractApplication)this.tranMaps.get(strCode);
/*     */ 
/* 217 */     if (app == null) {
/* 218 */       Iterator iter = this.tranMaps.entrySet().iterator();
/* 219 */       String strCodes = "";
/* 220 */       while (iter.hasNext()) {
/* 221 */         Map.Entry en = (Map.Entry)iter.next();
/* 222 */         strCodes = strCodes + "[" + ((String)en.getKey()) + "]";
/*     */       }
/* 224 */       throw new HiException("213303", strCode + ":" + strCodes);
/*     */     }
/*     */ 
/* 227 */     if (isExistCode) {
/* 228 */       HiProcess.process(app, ctx);
/*     */     } else {
/* 230 */       app.process(ctx);
/* 231 */       app.afterProcess(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setCTL(String strFileName)
/*     */   {
/* 252 */     setFileName(strFileName, "CTL");
/*     */   }
/*     */ 
/*     */   public void setFileName(String strFileName, String strType) {
/* 256 */     if ((StringUtils.isEmpty(this.strFirstFileName)) && ("ITF".equals(strType)))
/* 257 */       this.strFirstFileName = strFileName;
/* 258 */     this.fileList.put(strFileName, strType);
/*     */   }
/*     */ 
/*     */   public void setITF(String strFileName) {
/* 262 */     setFileName(strFileName, "ITF");
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent event)
/*     */     throws HiException
/*     */   {
/* 276 */     init();
/*     */   }
/*     */ 
/*     */   public void serverDestroy(ServerEvent event) throws HiException {
/* 280 */     destroy();
/*     */   }
/*     */ 
/*     */   public void manage(HiMessageContext ctx) throws HiException {
/* 284 */     HiMessage msg = ctx.getCurrentMsg();
/* 285 */     HiETF root = msg.getETFBody();
/* 286 */     String fileNam = root.getChildValue("FIL_NAM");
/* 287 */     String cmdId = root.getChildValue("CMD_ID");
/* 288 */     if (!("reload".equalsIgnoreCase(cmdId))) return;
/*     */     try {
/* 290 */       reload(fileNam);
/* 291 */       root.setChildValue("RSP_CD", "000000");
/*     */     } catch (HiException e) {
/* 293 */       root.setChildValue("RSP_CD", e.getCode());
/* 294 */       root.setChildValue("RSP_MSG", e.getMessage());
/* 295 */       this.log.error(e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reload(String strFileName)
/*     */     throws HiException
/*     */   {
/* 306 */     Iterator iter = this.appList.iterator();
/* 307 */     ArrayList tmpAppList = new ArrayList();
/* 308 */     while (iter.hasNext())
/*     */     {
/*     */       Map.Entry tranEn;
/*     */       String strCode;
/* 309 */       HiAbstractApplication oldApp = (HiAbstractApplication)iter.next();
/* 310 */       if ((strFileName != null) && (!(StringUtils.equalsIgnoreCase(oldApp.getFileName(), strFileName)))) {
/*     */         continue;
/*     */       }
/* 313 */       HiAbstractApplication newApp = load(oldApp.getFileName(), oldApp.getType());
/*     */ 
/* 316 */       iter.remove();
/* 317 */       HiRegisterService.unregister(oldApp.context);
/*     */ 
/* 319 */       Set tranSet = oldApp.getTranMap().entrySet();
/* 320 */       Iterator tranIter = tranSet.iterator();
/* 321 */       while (tranIter.hasNext()) {
/* 322 */         tranEn = (Map.Entry)tranIter.next();
/* 323 */         strCode = (String)tranEn.getKey();
/* 324 */         if (this.tranMaps.containsKey(strCode)) {
/* 325 */           tranIter.remove();
/*     */         }
/*     */       }
/*     */ 
/* 329 */       HiRegisterService.register(newApp.context);
/* 330 */       tranSet = newApp.getTranMap().entrySet();
/* 331 */       tranIter = tranSet.iterator();
/* 332 */       while (tranIter.hasNext()) {
/* 333 */         tranEn = (Map.Entry)tranIter.next();
/* 334 */         strCode = (String)tranEn.getKey();
/* 335 */         this.tranMaps.put(strCode, newApp);
/* 336 */         if (this.log.isInfoEnabled()) {
/* 337 */           this.log.info("code=[" + strCode + "]");
/*     */         }
/*     */       }
/* 340 */       if (StringUtils.equalsIgnoreCase(newApp.getFileName(), this.strFirstFileName)) {
/* 341 */         this.firstApp = newApp;
/*     */       }
/* 343 */       tmpAppList.add(newApp);
/*     */     }
/* 345 */     if (!(tmpAppList.isEmpty()))
/* 346 */       this.appList.addAll(tmpAppList);
/*     */   }
/*     */ 
/*     */   private HiAbstractApplication load(String strFileName, String strType) throws HiException
/*     */   {
/* 351 */     if (this.log.isInfoEnabled()) {
/* 352 */       this.log.info(HiStringManager.getManager().getString("HiEngine.init", strFileName));
/*     */     }
/*     */ 
/* 355 */     URL fileUrl = HiResource.getResource(strFileName);
/*     */ 
/* 357 */     if (fileUrl == null) {
/* 358 */       throw new HiException("213302", strFileName);
/*     */     }
/* 360 */     if (this.log.isInfoEnabled()) {
/* 361 */       this.log.info(HiStringManager.getManager().getString("HiEngine.init1", fileUrl));
/*     */     }
/*     */ 
/* 365 */     HiAbstractApplication app = null;
/* 366 */     if ("CTL".equalsIgnoreCase(strType)) {
/* 367 */       app = (HiAbstractApplication)HiResourceRule.getCTLCfgFile(fileUrl).getRootInstance();
/*     */     }
/*     */     else {
/* 370 */       app = (HiAbstractApplication)HiResourceRule.getITFCfgFile(fileUrl).getRootInstance();
/*     */     }
/*     */ 
/* 373 */     app.setType(strType);
/* 374 */     app.setFileName(strFileName);
/* 375 */     return app;
/*     */   }
/*     */ }