/*    */ package com.hisun.validator;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.IServerDestroyListener;
/*    */ import com.hisun.framework.event.IServerInitListener;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiResource;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class HiIPValidator
/*    */   implements IHandler, IServerInitListener, IServerDestroyListener
/*    */ {
/*    */   private ArrayList _fileList;
/*    */   private ArrayList _nodeList;
/*    */ 
/*    */   public HiIPValidator()
/*    */   {
/* 30 */     this._fileList = new ArrayList();
/*    */ 
/* 32 */     this._nodeList = new ArrayList(); }
/*    */ 
/*    */   public void setFile(String file) {
/* 35 */     this._fileList.add(file);
/*    */   }
/*    */ 
/*    */   public boolean validate(HiMessageContext ctx) {
/* 39 */     Element node = null;
/* 40 */     String srcIp = ctx.getCurrentMsg().getHeadItem("SIP");
/* 41 */     if (StringUtils.isEmpty(srcIp)) {
/* 42 */       return true;
/*    */     }
/* 44 */     for (int i = 0; i < this._nodeList.size(); ++i) {
/* 45 */       node = (Element)this._nodeList.get(i);
/* 46 */       if (StringUtils.equals(node.attributeValue("ip"), srcIp)) {
/* 47 */         return true;
/*    */       }
/*    */     }
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   public void serverInit(ServerEvent arg0) throws HiException {
/* 54 */     SAXReader reader = new SAXReader();
/*    */ 
/* 56 */     for (int i = 0; i < this._fileList.size(); ++i) {
/* 57 */       String file = (String)this._fileList.get(i);
/*    */       try {
/* 59 */         InputStream is = HiResource.getResourceAsStream(file);
/* 60 */         if (is == null)
/* 61 */           throw new HiException("212004", file);
/* 62 */         Document doc = reader.read(is);
/* 63 */         this._nodeList.add(doc.getRootElement());
/*    */       } catch (DocumentException e) {
/* 65 */         throw new HiException("340001", file, e.getMessage());
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void serverDestroy(ServerEvent arg0) throws HiException
/*    */   {
/* 72 */     this._fileList.clear();
/* 73 */     this._fileList = null;
/* 74 */     this._nodeList.clear();
/* 75 */     this._nodeList = null;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext arg0)
/*    */     throws HiException
/*    */   {
/*    */   }
/*    */ }