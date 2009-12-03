/*     */ package com.hisun.mon.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.mon.HiServiceRequest;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class GetAppTransList
/*     */ {
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     HiETF grpNode;
/*  37 */     String host = HiArgUtils.getStringNotNull(args, "host");
/*  38 */     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
/*     */ 
/*  40 */     String appId = HiArgUtils.getStringNotNull(args, "app");
/*  41 */     String serverList = args.get("servers");
/*  42 */     String brno = args.get("brno");
/*  43 */     HiETF body = ctx.getCurrentMsg().getETFBody();
/*  44 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/*  46 */     String cmd = null;
/*  47 */     if (brno != null)
/*  48 */       cmd = "<ROOT><CODE>090806</CODE><AppID>" + appId + "</AppID><bk_no>" + brno + "</bk_no></ROOT>";
/*     */     else {
/*  50 */       cmd = "<ROOT><CODE>090806</CODE><AppID>" + appId + "</AppID></ROOT>";
/*     */     }
/*  52 */     byte[] result = (byte[])null;
/*  53 */     Element root = null;
/*     */     try {
/*  55 */       result = HiServiceRequest.callService(host, port, 8, cmd.getBytes());
/*  56 */       root = DocumentHelper.parseText(new String(result)).getRootElement();
/*     */     } catch (IOException e) {
/*  58 */       HiException.makeException(e);
/*     */     } catch (DocumentException e) {
/*  60 */       HiException.makeException(e);
/*     */     }
/*     */ 
/*  63 */     int num = Integer.parseInt(root.elementText("codnum"));
/*     */ 
/*  66 */     Map map = new HashMap();
/*  67 */     String cod = null;
/*  68 */     for (int i = 1; i <= num; ++i) {
/*  69 */       Element tranNode = root.element("cod_" + i);
/*  70 */       if (tranNode == null) {
/*     */         continue;
/*     */       }
/*  73 */       grpNode = body.addNode("GRP_" + i);
/*     */ 
/*  75 */       cod = tranNode.elementText("TxnCod");
/*  76 */       grpNode.setChildValue("TXN_CD", cod);
/*  77 */       grpNode.setChildValue("TXN_NM", tranNode.elementText("TxnNam"));
/*     */ 
/*  79 */       map.put(cod, grpNode);
/*     */     }
/*     */ 
/*  82 */     if (serverList == null) {
/*  83 */       return 0;
/*     */     }
/*     */ 
/*  86 */     String[] serverArr = StringUtils.split(serverList, '|');
/*     */ 
/*  89 */     String transCmdPre = null;
/*     */ 
/*  92 */     for (int i = 0; i < serverArr.length; ++i) {
/*  93 */       cmd = "<ROOT><CODE>090819</CODE><server>" + serverArr[i] + "</server></ROOT>";
/*     */       try
/*     */       {
/*  96 */         result = HiServiceRequest.callService(host, port, 8, cmd.getBytes());
/*  97 */         root = DocumentHelper.parseText(new String(result)).getRootElement();
/*     */       } catch (IOException e) {
/*  99 */         HiException.makeException(e);
/*     */       } catch (DocumentException e) {
/* 101 */         HiException.makeException(e);
/*     */       }
/*     */ 
/* 104 */       if (!("000000".equals(root.elementText("RspCod")))) {
/*     */         continue;
/*     */       }
/*     */ 
/* 108 */       Iterator it = root.elementIterator("c_g");
/* 109 */       while (it.hasNext()) {
/* 110 */         Element transNode2 = (Element)it.next();
/* 111 */         cod = transNode2.attributeValue("t_code");
/*     */ 
/* 113 */         grpNode = (HiETF)map.get(cod);
/* 114 */         if (grpNode == null)
/*     */           continue;
/* 116 */         grpNode.setChildValue("SVR_DESC", serverArr[i]);
/* 117 */         grpNode.setChildValue("RUN_FLG", transNode2.attributeValue("r_flag"));
/* 118 */         grpNode.setChildValue("LOG_LVL", transNode2.attributeValue("t_flag"));
/* 119 */         grpNode.setChildValue("MON_SW", transNode2.attributeValue("m_flag"));
/* 120 */         log.info(grpNode.toString());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 125 */     map.clear();
/* 126 */     return 0;
/*     */   }
/*     */ }