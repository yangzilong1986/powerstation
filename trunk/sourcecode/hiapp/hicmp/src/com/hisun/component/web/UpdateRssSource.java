package com.hisun.component.web;

import com.hisun.atc.common.HiArgUtils;
import com.hisun.atc.common.HiDbtSqlHelper;
import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UpdateRssSource {
    private Logger log;
    private static Map RssItemSpec = new HashMap();

    public UpdateRssSource() {
        this.log = HiLog.getLogger("updatersssource.trc");
    }

    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        HiETF etfBody = msg.getETFBody();
        String rssSql = HiArgUtils.getStringNotNull(args, "rssSql");
        String rssFile = HiArgUtils.getStringNotNull(args, "rssFile");
        String rssTit = args.get("title");
        String rssDesc = args.get("description");
        if (rssTit == null) {
            rssTit = "RSS 订阅";
        }
        if (rssDesc == null) {
            rssDesc = "RSS 订阅";
        }

        String sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, rssSql, etfBody);
        if (this.log.isDebugEnabled()) {
            this.log.debug("UpdateRssSource: [" + sqlSentence + "]");
        }

        List articleList = ctx.getDataBaseUtil().execQuery(sqlSentence);

        StringBuffer sb = new StringBuffer(1024);
        sb.append("<?xml version='1.0' encoding='GBK'?>");
        sb.append("\n");
        sb.append("<rss version='2.0'>");
        sb.append("<channel>");
        sb.append("<title>");
        sb.append(rssTit);
        sb.append("</title>");
        sb.append("<description>");
        sb.append(rssDesc);
        sb.append("</description>");

        if ((articleList != null) && (articleList.size() != 0)) {
            Map queryRec = null;
            Map.Entry recEntry = null;
            Iterator recIt = null;

            int i = 0;

            for (i = 0; i < articleList.size(); ++i) {
                queryRec = (Map) articleList.get(i);
                recIt = queryRec.entrySet().iterator();
                sb.append("\n");
                sb.append("<item>");

                while (recIt.hasNext()) {
                    recEntry = (Map.Entry) recIt.next();
                    String col = getSpecName((String) recEntry.getKey());
                    if (col == null) continue;
                    sb.append("\n");
                    sb.append("<");
                    sb.append(col);
                    sb.append(">");

                    sb.append(recEntry.getValue());

                    sb.append("</");
                    sb.append(col);
                    sb.append(">");
                }

                sb.append("</item>");
            }
        }
        sb.append("\n");
        sb.append("</channel>");
        sb.append("</rss>");
        try {
            DataOutputStream dout = new DataOutputStream(new FileOutputStream(rssFile));
            dout.write(sb.toString().getBytes());
        } catch (Exception e) {
            throw new HiException(e);
        }
        return 0;
    }

    private String getSpecName(String key) {
        return ((String) RssItemSpec.get(key));
    }

    static {
        RssItemSpec.put("TITLE", "title");
        RssItemSpec.put("DESCRIPTION", "description");
        RssItemSpec.put("PUBDATE", "pubDate");
        RssItemSpec.put("AUTHOR", "author");
        RssItemSpec.put("CATEGORY", "category");
        RssItemSpec.put("LINK", "link");
    }
}