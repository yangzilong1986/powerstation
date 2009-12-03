/*     */ package com.hisun.component.web;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.httpclient.HttpClient;
/*     */ import org.apache.commons.httpclient.methods.GetMethod;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.htmlparser.Parser;
/*     */ import org.htmlparser.Tag;
/*     */ import org.htmlparser.visitors.TagFindingVisitor;
/*     */ 
/*     */ public class GoogleSearch
/*     */ {
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  45 */     HiMessage msg = ctx.getCurrentMsg();
/*  46 */     HiETF root = msg.getETFBody();
/*  47 */     Logger log = HiLog.getLogger(msg);
/*  48 */     String searchContent = args.get("SeaCnt");
/*  49 */     String start = args.get("start");
/*     */     try
/*     */     {
/*  54 */       String searchContent3 = URLEncoder.encode(searchContent, "GBK");
/*  55 */       String url = null;
/*  56 */       if (StringUtils.isBlank(start)) {
/*  57 */         url = "http://www.google.cn/search?hl=zh-CN&q=" + searchContent + "&meta=&aq=f&oq=";
/*     */       }
/*     */       else {
/*  60 */         url = "http://www.google.cn/search?hl=zh-CN&newwindow=1&sa=N&q=" + searchContent + "&start=" + start;
/*     */       }
/*  62 */       if (log.isInfoEnabled()) {
/*  63 */         log.info("url:[" + url + "]");
/*     */       }
/*  65 */       Parser parser = new Parser(url);
/*     */ 
/*  83 */       TagFindingVisitor visitor = new TagFindingVisitor(new String[] { "li", "table" }, root, searchContent, searchContent3)
/*     */       {
/*  85 */         private int liCnt = 0;
/*     */ 
/*     */         public void visitTag(Tag tag) {
/*  88 */           if ("li".equalsIgnoreCase(tag.getTagName())) {
/*  89 */             HiETF grp = this.val$root.addNode("REC_" + (this.liCnt + 1));
/*  90 */             String s = tag.toHtml();
/*  91 */             s = StringUtils.replace(s, "\"/url?", "\"http://www.google.cn/url?");
/*  92 */             s = StringUtils.replace(s, "\"/search?", "\"http://www.google.cn/search?");
/*  93 */             s = StringUtils.replace(s, "网页快照", "");
/*  94 */             s = StringUtils.replace(s, "类似网页", "");
/*  95 */             grp.setChildValue("li", s);
/*  96 */             this.liCnt += 1;
/*     */           }
/*  98 */           if ((!("table".equalsIgnoreCase(tag.getTagName()))) || (!("nav".equalsIgnoreCase(tag.getAttribute("id")))))
/*     */             return;
/* 100 */           String s = tag.toHtml();
/* 101 */           s = StringUtils.replace(s, "/search", "search.dow");
/* 102 */           String searchContent2 = "";
/*     */           try {
/* 104 */             searchContent2 = URLEncoder.encode(this.val$searchContent, "UTF8");
/*     */           } catch (UnsupportedEncodingException e) {
/* 106 */             e.printStackTrace();
/*     */           }
/* 108 */           s = StringUtils.replace(s, searchContent2, this.val$searchContent3);
/* 109 */           this.val$root.setChildValue("nav", s);
/*     */         }
/*     */       };
/* 113 */       parser.visitAllNodesWith(visitor);
/*     */     } catch (Exception e) {
/* 115 */       throw new HiException(e);
/*     */     }
/* 117 */     return 0;
/*     */   }
/*     */ 
/*     */   public int execute01(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     String url;
/* 122 */     HiMessage msg = ctx.getCurrentMsg();
/* 123 */     Logger log = HiLog.getLogger(msg);
/* 124 */     String searchContent = args.get("SeaCnt");
/*     */ 
/* 127 */     HttpServletResponse response = (HttpServletResponse)msg.getObjectHeadItem("_WEB_RESPONSE");
/*     */ 
/* 129 */     HttpServletRequest request = (HttpServletRequest)msg.getObjectHeadItem("_WEB_REQUEST");
/*     */ 
/* 132 */     if (StringUtils.isNotBlank(searchContent)) {
/* 133 */       searchContent = processSearchContent(searchContent);
/* 134 */       searchContent = URLEncoder.encode(searchContent);
/* 135 */       url = "http://203.208.37.104/search?hl=zh-CN&q=" + searchContent + "&meta=&aq=f&oq=";
/*     */     }
/*     */     else {
/* 138 */       String queryString = request.getQueryString();
/* 139 */       url = "http://203.208.37.104/search?" + queryString;
/*     */     }
/* 141 */     log.info("URL:[" + url + "]");
/*     */ 
/* 143 */     response.setContentType("text/html;charset=GBK");
/* 144 */     String result = getRequestContent(url);
/* 145 */     result = parserSearchResult(result);
/*     */     try {
/* 147 */       response.getWriter().write(result);
/*     */     } catch (IOException e) {
/* 149 */       throw new HiException(e);
/*     */     }
/*     */ 
/* 152 */     return 0;
/*     */   }
/*     */ 
/*     */   private String getRequestContent(String url) throws HiException {
/* 156 */     HttpClient httpClient = new HttpClient();
/* 157 */     GetMethod getMethod = null;
/*     */     try {
/* 159 */       getMethod = new GetMethod(url);
/* 160 */       int statusCode = httpClient.executeMethod(getMethod);
/* 161 */       byte[] responseBody = getMethod.getResponseBody();
/* 162 */       String str = new String(responseBody, 0, responseBody.length);
/*     */ 
/* 167 */       return str;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/* 166 */       getMethod.releaseConnection();
/*     */     }
/*     */   }
/*     */ 
/*     */   private String parserSearchResult(String result) {
/* 171 */     return result;
/*     */   }
/*     */ 
/*     */   private String processSearchContent(String content) {
/* 175 */     return content.replace(' ', '+');
/*     */   }
/*     */ }