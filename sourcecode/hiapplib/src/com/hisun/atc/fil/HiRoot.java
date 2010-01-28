 package com.hisun.atc.fil;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiITFEngineModel;
 import com.hisun.engine.invoke.HiIEngineModel;
 import com.hisun.engine.invoke.impl.HiProcess;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessageContext;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 
 public class HiRoot extends HiITFEngineModel
 {
   String debug_file;
   String err_file;
   private HiInDesc inNode;
   private HiOutDesc outNode;
 
   public HiRoot()
   {
     this.inNode = null;
 
     this.outNode = null;
   }
 
   public String getNodeName()
   {
     return "root";
   }
 
   public void setDebug_file(String debug_file)
   {
     this.debug_file = debug_file;
   }
 
   public void setErr_file(String err_file)
   {
     this.err_file = err_file;
   }
 
   public void addChilds(HiIEngineModel child)
     throws HiException
   {
     if (child instanceof HiInDesc)
       this.inNode = ((HiInDesc)child);
     if (child instanceof HiOutDesc)
       this.outNode = ((HiOutDesc)child);
     super.addChilds(child);
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     processInNode(ctx);
     processOutNode(ctx);
   }
 
   public void processInNode(HiMessageContext ctx) throws HiException {
     if (this.inNode != null)
       HiProcess.process(this.inNode, ctx);
   }
 
   public void processOutNode(HiMessageContext ctx) throws HiException {
     if (this.outNode != null)
       HiProcess.process(this.outNode, ctx);
   }
 
   public HiEngineModel getInNode() {
     return this.inNode;
   }
 
   public HiEngineModel getOutNode() {
     return this.outNode;
   }
 
   public HiEngineModel getNode(String nodeName, String attrName, String attrValue)
     throws HiException
   {
     List childs = super.getChilds();
     for (int i = 0; i < childs.size(); ++i) {
       HiEngineModel model = (HiEngineModel)childs.get(i);
       if (StringUtils.equalsIgnoreCase(model.getNodeName(), nodeName)) {
         return model;
       }
     }
     return null;
   }
 }