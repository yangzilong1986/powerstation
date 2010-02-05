 package com.hisun.engine;
 
 import com.hisun.engine.invoke.HiIAction;
 import com.hisun.engine.invoke.HiIEngineModel;
 import com.hisun.engine.invoke.impl.HiElseIfProcess;
 import com.hisun.engine.invoke.impl.HiElseProcess;
 import com.hisun.engine.invoke.impl.HiIfProcess;
 import com.hisun.engine.invoke.impl.HiProcess;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiResource;
 import com.hisun.util.HiStringManager;
 import java.lang.reflect.Method;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.commons.beanutils.MethodUtils;
 
 public abstract class HiEngineModel
   implements HiIEngineModel, HiIAction
 {
   protected HiContext context = null;
 
   protected static final HiStringManager sm = HiStringManager.getManager();
 
   protected List childs = null;
 
   private ArrayList commonList = null;
   private HiIEngineModel parent;
   protected HashMap preMaps = null;
 
   protected HashMap speMaps = null;
 
   private ArrayList initList = new ArrayList();
 
   public void addChilds(HiIEngineModel child)
     throws HiException
   {
     if (this.childs == null) {
       this.childs = new ArrayList();
     }
     if ((child instanceof HiElseIfProcess) || (child instanceof HiElseProcess)) {
       int nSize = this.childs.size();
       if (nSize <= 0) {
         throw new HiException("213304");
       }
       HiIfProcess ifChild = (HiIfProcess)this.childs.get(nSize - 1);
       ifChild.addControlNodes(child);
     } else {
       this.childs.add(child);
     }
   }
 
   public void popOwnerContext()
   {
     HiContext.popCurrentContext();
   }
 
   public void afterProcess(HiMessageContext messContext) throws HiException {
     if (this.context != null) {
       messContext.popParent();
     }
     if ((this.speMaps == null) || (this.speMaps.isEmpty()))
       return;
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     Set set = this.speMaps.entrySet();
     Iterator iter = set.iterator();
     while (iter.hasNext()) {
       Map.Entry en = (Map.Entry)iter.next();
       String strClassName = (String)en.getKey();
       String strMethodName = (String)en.getValue();
       try
       {
         if (log.isInfoEnabled()) {
           log.info("afterProcess[" + strClassName + "][" + strMethodName + "]");
         }
         Class cl = HiResource.loadClassPrefix(strClassName);
 
         Method method = MethodUtils.getAccessibleMethod(cl, strMethodName, new Class[] { HiMessageContext.class });
 
         method.invoke(cl.newInstance(), new Object[] { messContext });
       } catch (Throwable t) {
         throw HiException.makeException("213306", strMethodName, t);
       }
     }
   }
 
   public void beforeProcess(HiMessageContext ctx)
     throws HiException
   {
     if (Thread.currentThread().isInterrupted()) {
       throw new HiException("241149", Thread.currentThread().getName());
     }
     HiEngineUtilities.timeoutCheck(ctx.getCurrentMsg());
 
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
 
     if (this.context != null) {
       ctx.pushParent(this.context);
     }
     if ((this.preMaps == null) || (this.preMaps.isEmpty()))
       return;
     if (log.isDebugEnabled())
       log.debug("before Process start....");
     Set set = this.preMaps.entrySet();
     Iterator iter = set.iterator();
     while (iter.hasNext()) {
       Map.Entry en = (Map.Entry)iter.next();
       String strClassName = (String)en.getKey();
       String strMethodName = (String)en.getValue();
       try
       {
         if (log.isInfoEnabled()) {
           log.info("beforeProcess[" + strClassName + "][" + strMethodName + "]");
         }
 
         Class cl = HiResource.loadClassPrefix(strClassName);
 
         Method method = MethodUtils.getAccessibleMethod(cl, strMethodName, new Class[] { HiMessageContext.class });
 
         method.invoke(cl.newInstance(), new Object[] { ctx });
       } catch (Throwable t) {
         throw HiException.makeException("213306", strMethodName, t);
       }
     }
   }
 
   public List getChilds()
   {
     return this.childs;
   }
 
   public ArrayList getCommonds() {
     return this.commonList;
   }
 
   public HiIEngineModel getParent() throws HiException {
     return this.parent;
   }
 
   public HashMap getPreMap() {
     return this.preMaps;
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiEngineModel.process00", HiEngineUtilities.getCurFlowStep(), getNodeName(), toString()));
     }
 
     if (this.childs == null) {
       return;
     }
     for (int i = 0; i < this.childs.size(); ++i) {
       HiIAction child = (HiIAction)this.childs.get(i);
       HiEngineUtilities.setCurFlowStep(i);
       HiProcess.process(child, ctx);
     }
   }
 
   public void setAfterdll(String strClassName, String strMethodName)
     throws HiException
   {
     try
     {
       if ((strClassName == null) || (strClassName.equals(""))) {
         return;
       }
       Class cl = HiResource.loadClassPrefix(strClassName);
       MethodUtils.getAccessibleMethod(cl, strMethodName, HiMessageContext.class);
     }
     catch (Throwable t) {
       throw HiException.makeException("213334", strClassName, strMethodName, t);
     }
 
     if (this.speMaps == null)
       this.speMaps = new HashMap();
     this.speMaps.put(strClassName, strMethodName);
   }
 
   public void setBeforedll(String strClassName, String strMethodName)
     throws HiException
   {
     try
     {
       if ((strClassName == null) || (strClassName.equals("")))
         return;
       Class cl = HiResource.loadClassPrefix(strClassName);
 
       MethodUtils.getAccessibleMethod(cl, strMethodName, HiMessageContext.class);
     }
     catch (Throwable t) {
       throw HiException.makeException("213334", strClassName, strMethodName, t);
     }
 
     if (this.preMaps == null)
       this.preMaps = new HashMap();
     this.preMaps.put(strClassName, strMethodName);
   }
 
   public void setCommonds(Object common)
   {
     if (this.commonList == null)
       this.commonList = new ArrayList();
     this.commonList.add(common);
   }
 
   public void setParent(HiIEngineModel parent)
     throws HiException
   {
     this.parent = parent;
   }
 
   public ArrayList getInitList()
   {
     return this.initList;
   }
 
   public void setInitList(HiIAction action)
   {
     this.initList.add(action);
   }
 
   public void loadAfter() throws HiException
   {
   }
 
   public String toString() {
     return getNodeName();
   }
 }