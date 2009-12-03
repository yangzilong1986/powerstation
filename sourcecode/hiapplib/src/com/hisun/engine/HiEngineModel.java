/*     */ package com.hisun.engine;
/*     */ 
/*     */ import com.hisun.engine.invoke.HiIAction;
/*     */ import com.hisun.engine.invoke.HiIEngineModel;
/*     */ import com.hisun.engine.invoke.impl.HiElseIfProcess;
/*     */ import com.hisun.engine.invoke.impl.HiElseProcess;
/*     */ import com.hisun.engine.invoke.impl.HiIfProcess;
/*     */ import com.hisun.engine.invoke.impl.HiProcess;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.beanutils.MethodUtils;
/*     */ 
/*     */ public abstract class HiEngineModel
/*     */   implements HiIEngineModel, HiIAction
/*     */ {
/*  39 */   protected HiContext context = null;
/*     */ 
/*  41 */   protected static final HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*  47 */   protected List childs = null;
/*     */ 
/*  52 */   private ArrayList commonList = null;
/*     */   private HiIEngineModel parent;
/*  59 */   protected HashMap preMaps = null;
/*     */ 
/*  68 */   protected HashMap speMaps = null;
/*     */ 
/* 280 */   private ArrayList initList = new ArrayList();
/*     */ 
/*     */   public void addChilds(HiIEngineModel child)
/*     */     throws HiException
/*     */   {
/*  71 */     if (this.childs == null) {
/*  72 */       this.childs = new ArrayList();
/*     */     }
/*  74 */     if ((child instanceof HiElseIfProcess) || (child instanceof HiElseProcess)) {
/*  75 */       int nSize = this.childs.size();
/*  76 */       if (nSize <= 0) {
/*  77 */         throw new HiException("213304");
/*     */       }
/*  79 */       HiIfProcess ifChild = (HiIfProcess)this.childs.get(nSize - 1);
/*  80 */       ifChild.addControlNodes(child);
/*     */     } else {
/*  82 */       this.childs.add(child);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void popOwnerContext()
/*     */   {
/*  90 */     HiContext.popCurrentContext();
/*     */   }
/*     */ 
/*     */   public void afterProcess(HiMessageContext messContext) throws HiException {
/*  94 */     if (this.context != null) {
/*  95 */       messContext.popParent();
/*     */     }
/*  97 */     if ((this.speMaps == null) || (this.speMaps.isEmpty()))
/*  98 */       return;
/*  99 */     HiMessage mess = messContext.getCurrentMsg();
/* 100 */     Logger log = HiLog.getLogger(mess);
/* 101 */     Set set = this.speMaps.entrySet();
/* 102 */     Iterator iter = set.iterator();
/* 103 */     while (iter.hasNext()) {
/* 104 */       Map.Entry en = (Map.Entry)iter.next();
/* 105 */       String strClassName = (String)en.getKey();
/* 106 */       String strMethodName = (String)en.getValue();
/*     */       try
/*     */       {
/* 110 */         if (log.isInfoEnabled()) {
/* 111 */           log.info("afterProcess[" + strClassName + "][" + strMethodName + "]");
/*     */         }
/* 113 */         Class cl = HiResource.loadClassPrefix(strClassName);
/*     */ 
/* 115 */         Method method = MethodUtils.getAccessibleMethod(cl, strMethodName, new Class[] { HiMessageContext.class });
/*     */ 
/* 117 */         method.invoke(cl.newInstance(), new Object[] { messContext });
/*     */       } catch (Throwable t) {
/* 119 */         throw HiException.makeException("213306", strMethodName, t);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void beforeProcess(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 130 */     if (Thread.currentThread().isInterrupted()) {
/* 131 */       throw new HiException("241149", Thread.currentThread().getName());
/*     */     }
/* 133 */     HiEngineUtilities.timeoutCheck(ctx.getCurrentMsg());
/*     */ 
/* 135 */     HiMessage msg = ctx.getCurrentMsg();
/* 136 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 138 */     if (this.context != null) {
/* 139 */       ctx.pushParent(this.context);
/*     */     }
/* 141 */     if ((this.preMaps == null) || (this.preMaps.isEmpty()))
/* 142 */       return;
/* 143 */     if (log.isDebugEnabled())
/* 144 */       log.debug("before Process start....");
/* 145 */     Set set = this.preMaps.entrySet();
/* 146 */     Iterator iter = set.iterator();
/* 147 */     while (iter.hasNext()) {
/* 148 */       Map.Entry en = (Map.Entry)iter.next();
/* 149 */       String strClassName = (String)en.getKey();
/* 150 */       String strMethodName = (String)en.getValue();
/*     */       try
/*     */       {
/* 153 */         if (log.isInfoEnabled()) {
/* 154 */           log.info("beforeProcess[" + strClassName + "][" + strMethodName + "]");
/*     */         }
/*     */ 
/* 157 */         Class cl = HiResource.loadClassPrefix(strClassName);
/*     */ 
/* 159 */         Method method = MethodUtils.getAccessibleMethod(cl, strMethodName, new Class[] { HiMessageContext.class });
/*     */ 
/* 161 */         method.invoke(cl.newInstance(), new Object[] { ctx });
/*     */       } catch (Throwable t) {
/* 163 */         throw HiException.makeException("213306", strMethodName, t);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public List getChilds()
/*     */   {
/* 170 */     return this.childs;
/*     */   }
/*     */ 
/*     */   public ArrayList getCommonds() {
/* 174 */     return this.commonList;
/*     */   }
/*     */ 
/*     */   public HiIEngineModel getParent() throws HiException {
/* 178 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public HashMap getPreMap() {
/* 182 */     return this.preMaps;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 191 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 192 */     if (log.isDebugEnabled()) {
/* 193 */       log.debug(sm.getString("HiEngineModel.process00", HiEngineUtilities.getCurFlowStep(), getNodeName(), toString()));
/*     */     }
/*     */ 
/* 197 */     if (this.childs == null) {
/* 198 */       return;
/*     */     }
/* 200 */     for (int i = 0; i < this.childs.size(); ++i) {
/* 201 */       HiIAction child = (HiIAction)this.childs.get(i);
/* 202 */       HiEngineUtilities.setCurFlowStep(i);
/* 203 */       HiProcess.process(child, ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAfterdll(String strClassName, String strMethodName)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 218 */       if ((strClassName == null) || (strClassName.equals(""))) {
/* 219 */         return;
/*     */       }
/* 221 */       Class cl = HiResource.loadClassPrefix(strClassName);
/* 222 */       MethodUtils.getAccessibleMethod(cl, strMethodName, HiMessageContext.class);
/*     */     }
/*     */     catch (Throwable t) {
/* 225 */       throw HiException.makeException("213334", strClassName, strMethodName, t);
/*     */     }
/*     */ 
/* 228 */     if (this.speMaps == null)
/* 229 */       this.speMaps = new HashMap();
/* 230 */     this.speMaps.put(strClassName, strMethodName);
/*     */   }
/*     */ 
/*     */   public void setBeforedll(String strClassName, String strMethodName)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 243 */       if ((strClassName == null) || (strClassName.equals("")))
/* 244 */         return;
/* 245 */       Class cl = HiResource.loadClassPrefix(strClassName);
/*     */ 
/* 247 */       MethodUtils.getAccessibleMethod(cl, strMethodName, HiMessageContext.class);
/*     */     }
/*     */     catch (Throwable t) {
/* 250 */       throw HiException.makeException("213334", strClassName, strMethodName, t);
/*     */     }
/*     */ 
/* 253 */     if (this.preMaps == null)
/* 254 */       this.preMaps = new HashMap();
/* 255 */     this.preMaps.put(strClassName, strMethodName);
/*     */   }
/*     */ 
/*     */   public void setCommonds(Object common)
/*     */   {
/* 266 */     if (this.commonList == null)
/* 267 */       this.commonList = new ArrayList();
/* 268 */     this.commonList.add(common);
/*     */   }
/*     */ 
/*     */   public void setParent(HiIEngineModel parent)
/*     */     throws HiException
/*     */   {
/* 277 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   public ArrayList getInitList()
/*     */   {
/* 283 */     return this.initList;
/*     */   }
/*     */ 
/*     */   public void setInitList(HiIAction action)
/*     */   {
/* 292 */     this.initList.add(action);
/*     */   }
/*     */ 
/*     */   public void loadAfter() throws HiException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 300 */     return getNodeName();
/*     */   }
/*     */ }