 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.HiITFEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiSysException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.message.HiXmlETF;
 import com.hisun.util.HiStringManager;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 import org.dom4j.Node;
 
 public class HiXMLItem extends HiITFEngineModel
 {
   private String _name;
   private String _xpath;
 
   public String getNodeName()
   {
     return "XMLItem";
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     try {
       HiMessage msg = ctx.getCurrentMsg();
       Logger log = HiLog.getLogger(msg);
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiItem.process00", HiEngineUtilities.getCurFlowStep(), this._name));
       }
 
       doProcess(ctx);
     } catch (HiException e) {
       throw e;
     } catch (Throwable te) {
       throw new HiSysException("213135", this._name, te);
     }
   }
 
   public void doProcess(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     String ect = msg.getHeadItem("ECT");
     if (StringUtils.equals(ect, "text/etf"))
     {
       doPackProcess(ctx);
     } else if (StringUtils.equals(ect, "text/plain"))
     {
       doUnpackProcess(ctx);
     }
     else throw new HiException("213120", ect);
   }
 
   public void doUnpackProcess(HiMessageContext ctx) throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiXmlETF etf = (HiXmlETF)HiItemHelper.getCurXmlRoot(msg);
     Node node = etf.getNode().selectSingleNode(this._xpath);
     node.getText();
   }
 
   public void doPackProcess(HiMessageContext ctx)
     throws HiException
   {
   }
 }