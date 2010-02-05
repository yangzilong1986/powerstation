package com.hisun.component.web;

import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.visitors.TagFindingVisitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GoogleSearch {
    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        HiETF root = msg.getETFBody();
        Logger log = HiLog.getLogger(msg);
        String searchContent = args.get("SeaCnt");
        String start = args.get("start");
        try {
            String searchContent3 = URLEncoder.encode(searchContent, "GBK");
            String url = null;
            if (StringUtils.isBlank(start)) {
                url = "http://www.google.cn/search?hl=zh-CN&q=" + searchContent + "&meta=&aq=f&oq=";
            } else {
                url = "http://www.google.cn/search?hl=zh-CN&newwindow=1&sa=N&q=" + searchContent + "&start=" + start;
            }
            if (log.isInfoEnabled()) {
                log.info("url:[" + url + "]");
            }
            Parser parser = new Parser(url);

            TagFindingVisitor visitor = new TagFindingVisitor(new String[]{"li", "table"}, root, searchContent, searchContent3) {
                private int liCnt = 0;

                public void visitTag(Tag tag) {
                    if ("li".equalsIgnoreCase(tag.getTagName())) {
                        HiETF grp = this.val$root.addNode("REC_" + (this.liCnt + 1));
                        String s = tag.toHtml();
                        s = StringUtils.replace(s, "\"/url?", "\"http://www.google.cn/url?");
                        s = StringUtils.replace(s, "\"/search?", "\"http://www.google.cn/search?");
                        s = StringUtils.replace(s, "网页快照", "");
                        s = StringUtils.replace(s, "类似网页", "");
                        grp.setChildValue("li", s);
                        this.liCnt += 1;
                    }
                    if ((!("table".equalsIgnoreCase(tag.getTagName()))) || (!("nav".equalsIgnoreCase(tag.getAttribute("id")))))
                        return;
                    String s = tag.toHtml();
                    s = StringUtils.replace(s, "/search", "search.dow");
                    String searchContent2 = "";
                    try {
                        searchContent2 = URLEncoder.encode(this.val$searchContent, "UTF8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    s = StringUtils.replace(s, searchContent2, this.val$searchContent3);
                    this.val$root.setChildValue("nav", s);
                }
            };
            parser.visitAllNodesWith(visitor);
        } catch (Exception e) {
            throw new HiException(e);
        }
        return 0;
    }

    public int execute01(HiATLParam args, HiMessageContext ctx) throws HiException {
        String url;
        HiMessage msg = ctx.getCurrentMsg();
        Logger log = HiLog.getLogger(msg);
        String searchContent = args.get("SeaCnt");

        HttpServletResponse response = (HttpServletResponse) msg.getObjectHeadItem("_WEB_RESPONSE");

        HttpServletRequest request = (HttpServletRequest) msg.getObjectHeadItem("_WEB_REQUEST");

        if (StringUtils.isNotBlank(searchContent)) {
            searchContent = processSearchContent(searchContent);
            searchContent = URLEncoder.encode(searchContent);
            url = "http://203.208.37.104/search?hl=zh-CN&q=" + searchContent + "&meta=&aq=f&oq=";
        } else {
            String queryString = request.getQueryString();
            url = "http://203.208.37.104/search?" + queryString;
        }
        log.info("URL:[" + url + "]");

        response.setContentType("text/html;charset=GBK");
        String result = getRequestContent(url);
        result = parserSearchResult(result);
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            throw new HiException(e);
        }

        return 0;
    }

    private String getRequestContent(String url) throws HiException {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = null;
        try {
            getMethod = new GetMethod(url);
            int statusCode = httpClient.executeMethod(getMethod);
            byte[] responseBody = getMethod.getResponseBody();
            String str = new String(responseBody, 0, responseBody.length);

            return str;
        } catch (Exception e) {
        } finally {
            getMethod.releaseConnection();
        }
    }

    private String parserSearchResult(String result) {
        return result;
    }

    private String processSearchContent(String content) {
        return content.replace(' ', '+');
    }
}