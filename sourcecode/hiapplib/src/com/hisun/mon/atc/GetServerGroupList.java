/*    */ package com.hisun.mon.atc;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.mon.HiServiceRequest;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.DocumentHelper;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class GetServerGroupList
/*    */ {
/*    */   private static final String S_G = "s_g";
/*    */   private static final String ATTR_G_NM = "g_name";
/*    */   private static final String ATTR_G_DESC = "g_desc";
/*    */ 
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 37 */     String host = HiArgUtils.getStringNotNull(args, "host");
/* 38 */     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
/*    */ 
/* 40 */     String cmd = "<ROOT><CODE>090809</CODE></ROOT>";
/* 41 */     byte[] result = (byte[])null;
/* 42 */     Element root = null;
/* 43 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*    */     try {
/* 45 */       result = HiServiceRequest.callService(host, port, 8, cmd.getBytes());
/*    */ 
/* 47 */       root = DocumentHelper.parseText("<?xml version='1.0' encoding='gb2312' ?>" + new String(result)).getRootElement();
/*    */     } catch (IOException e) {
/* 49 */       HiException.makeException(e);
/*    */     } catch (DocumentException e) {
/* 51 */       HiException.makeException(e);
/*    */     }
/*    */ 
/* 54 */     HiETF body = ctx.getCurrentMsg().getETFBody();
/* 55 */     Iterator it = root.elementIterator("s_g");
/* 56 */     Element serverNode = null;
/* 57 */     Element node = null;
/* 58 */     int idx = 1;
/*    */ 
/* 62 */     List groupList = new ArrayList();
/*    */ 
/* 64 */     while (it.hasNext()) {
/* 65 */       serverNode = (Element)it.next();
/* 66 */       String cur_grp_nm = serverNode.attributeValue("g_name");
/*    */ 
/* 68 */       if (!(groupList.contains(cur_grp_nm))) {
/* 69 */         groupList.add(cur_grp_nm);
/* 70 */         body.setGrandChildNode("GRP_" + idx + ".GRP_NM", cur_grp_nm);
/* 71 */         body.setGrandChildNode("GRP_" + idx + ".GRP_DESC", serverNode.attributeValue("g_desc"));
/*    */       }
/*    */ 
/* 74 */       ++idx;
/*    */     }
/* 76 */     return 0;
/*    */   }
/*    */ }