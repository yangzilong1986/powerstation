 package com.hisun.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiResource;
 import java.net.URL;
 import java.util.HashMap;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class HiCONSVRHandler
   implements IHandler, IServerInitListener
 {
   private String _regionRouter;
   private Logger _log;
   private HashMap _regionRouterMap;
 
   public HiCONSVRHandler()
   {
     this._regionRouterMap = new HashMap();
   }
 
   public void doRequest(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg1 = ctx.getCurrentMsg();
     HiByteBuffer byteBuffer = new HiByteBuffer(200);
     byteBuffer.append(msg1.toString());
     msg1.setBody(byteBuffer);
 
     String srn = msg1.getHeadItem("SRN");
     if (StringUtils.isNotBlank(srn)) {
       HiRegionRouterItem regionRouterItem = (HiRegionRouterItem)this._regionRouterMap.get(srn);
       if (regionRouterItem != null) {
         msg1.setHeadItem("OIP", regionRouterItem.ip);
         msg1.setHeadItem("OPT", regionRouterItem.port);
       }
     }
     msg1.delHeadItem("SRN");
     ctx.setCurrentMsg(msg1);
   }
 
   public void doResponse(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg1 = ctx.getCurrentMsg();
     HiByteBuffer byteBuffer = (HiByteBuffer)msg1.getBody();
     HiMessage msg2 = new HiMessage(byteBuffer.toString());
     ctx.setCurrentMsg(msg2);
   }
 
   public void process(HiMessageContext arg0) throws HiException {
   }
 
   public void serverInit(ServerEvent arg0) throws HiException {
     if (!(StringUtils.isNotBlank(this._regionRouter)))
       return;
     loadRegionRouter(this._regionRouter);
   }
 
   public String getRegionRouter()
   {
     return this._regionRouter;
   }
 
   public void setRegionRouter(String regionRouter) {
     this._regionRouter = regionRouter;
   }
 
   private void loadRegionRouter(String regionRouter) throws HiException {
     SAXReader reader = new SAXReader();
     URL url = HiResource.getResource(regionRouter);
     if (url == null)
       throw new HiException("213302", regionRouter);
     try
     {
       Document doc = reader.read(url);
       Element root = doc.getRootElement();
       List list = root.elements("Item");
       for (int i = 0; i < list.size(); ++i) {
         Element element = (Element)list.get(i);
         HiRegionRouterItem regionRouterItem = new HiRegionRouterItem();
         regionRouterItem.name = element.attributeValue("name");
         regionRouterItem.ip = element.attributeValue("ip");
         regionRouterItem.port = element.attributeValue("port");
         if (NumberUtils.toInt(regionRouterItem.port) <= 0) {
           throw new HiException("215120", "port");
         }
         this._regionRouterMap.put(regionRouterItem.name, regionRouterItem);
       }
     } catch (DocumentException e) {
       throw new HiException("213319", regionRouter, e);
     }
   }
 
   class HiRegionRouterItem
   {
     String name;
     String ip;
     String port;
   }
 }