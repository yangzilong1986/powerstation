/*    */ package com.hisun.component.web;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import javax.servlet.RequestDispatcher;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class FileDownload
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 26 */     HiMessage msg = ctx.getCurrentMsg();
/* 27 */     Logger log = HiLog.getLogger(msg);
/* 28 */     String fileDisplayName = args.get("DspNam");
/* 29 */     String fileDownloadName = HiArgUtils.getStringNotNull(args, "FilNam");
/* 30 */     if (fileDownloadName.indexOf(47) == -1) {
/* 31 */       fileDownloadName = "download/" + fileDownloadName;
/*    */     }
/*    */ 
/* 34 */     HttpServletResponse response = (HttpServletResponse)msg.getObjectHeadItem("_WEB_RESPONSE");
/*    */     try
/*    */     {
/* 38 */       response.reset();
/* 39 */       fileDisplayName = new String(fileDisplayName.getBytes("gb2312"), "iso8859-1");
/* 40 */       response.setContentType("application/x-download");
/* 41 */       response.addHeader("Content-Disposition", "attachment;filename=" + fileDisplayName);
/*    */ 
/* 43 */       HttpServletRequest request = (HttpServletRequest)msg.getObjectHeadItem("_WEB_REQUEST");
/*    */ 
/* 46 */       request.getRequestDispatcher(fileDownloadName).forward(request, response);
/*    */ 
/* 48 */       response.flushBuffer();
/*    */     } catch (Exception e) {
/* 50 */       throw new HiException(e);
/*    */     }
/* 52 */     return 0;
/*    */   }
/*    */ }