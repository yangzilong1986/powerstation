/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.IServerInitListener;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import com.hisun.util.HiResource;
/*    */ import java.net.URL;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class HiCONSVRHandler
/*    */   implements IHandler, IServerInitListener
/*    */ {
/*    */   private String _regionRouter;
/*    */   private Logger _log;
/*    */   private HashMap _regionRouterMap;
/*    */ 
/*    */   public HiCONSVRHandler()
/*    */   {
/* 37 */     this._regionRouterMap = new HashMap();
/*    */   }
/*    */ 
/*    */   public void doRequest(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 44 */     HiMessage msg1 = ctx.getCurrentMsg();
/* 45 */     HiByteBuffer byteBuffer = new HiByteBuffer(200);
/* 46 */     byteBuffer.append(msg1.toString());
/* 47 */     msg1.setBody(byteBuffer);
/*    */ 
/* 49 */     String srn = msg1.getHeadItem("SRN");
/* 50 */     if (StringUtils.isNotBlank(srn)) {
/* 51 */       HiRegionRouterItem regionRouterItem = (HiRegionRouterItem)this._regionRouterMap.get(srn);
/* 52 */       if (regionRouterItem != null) {
/* 53 */         msg1.setHeadItem("OIP", regionRouterItem.ip);
/* 54 */         msg1.setHeadItem("OPT", regionRouterItem.port);
/*    */       }
/*    */     }
/* 57 */     msg1.delHeadItem("SRN");
/* 58 */     ctx.setCurrentMsg(msg1);
/*    */   }
/*    */ 
/*    */   public void doResponse(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 67 */     HiMessage msg1 = ctx.getCurrentMsg();
/* 68 */     HiByteBuffer byteBuffer = (HiByteBuffer)msg1.getBody();
/* 69 */     HiMessage msg2 = new HiMessage(byteBuffer.toString());
/* 70 */     ctx.setCurrentMsg(msg2);
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext arg0) throws HiException {
/*    */   }
/*    */ 
/*    */   public void serverInit(ServerEvent arg0) throws HiException {
/* 77 */     if (!(StringUtils.isNotBlank(this._regionRouter)))
/*    */       return;
/* 79 */     loadRegionRouter(this._regionRouter);
/*    */   }
/*    */ 
/*    */   public String getRegionRouter()
/*    */   {
/* 85 */     return this._regionRouter;
/*    */   }
/*    */ 
/*    */   public void setRegionRouter(String regionRouter) {
/* 89 */     this._regionRouter = regionRouter;
/*    */   }
/*    */ 
/*    */   private void loadRegionRouter(String regionRouter) throws HiException {
/* 93 */     SAXReader reader = new SAXReader();
/* 94 */     URL url = HiResource.getResource(regionRouter);
/* 95 */     if (url == null)
/* 96 */       throw new HiException("213302", regionRouter);
/*    */     try
/*    */     {
/* 99 */       Document doc = reader.read(url);
/* 100 */       Element root = doc.getRootElement();
/* 101 */       List list = root.elements("Item");
/* 102 */       for (int i = 0; i < list.size(); ++i) {
/* 103 */         Element element = (Element)list.get(i);
/* 104 */         HiRegionRouterItem regionRouterItem = new HiRegionRouterItem();
/* 105 */         regionRouterItem.name = element.attributeValue("name");
/* 106 */         regionRouterItem.ip = element.attributeValue("ip");
/* 107 */         regionRouterItem.port = element.attributeValue("port");
/* 108 */         if (NumberUtils.toInt(regionRouterItem.port) <= 0) {
/* 109 */           throw new HiException("215120", "port");
/*    */         }
/* 111 */         this._regionRouterMap.put(regionRouterItem.name, regionRouterItem);
/*    */       }
/*    */     } catch (DocumentException e) {
/* 114 */       throw new HiException("213319", regionRouter, e);
/*    */     }
/*    */   }
/*    */ 
/*    */   class HiRegionRouterItem
/*    */   {
/*    */     String name;
/*    */     String ip;
/*    */     String port;
/*    */   }
/*    */ }