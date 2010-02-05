package com.hisun.component.web;

import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;

public class GetAllLoginUser {
    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        Logger log = HiLog.getLogger(msg);
        ServletContext webContext = (ServletContext) msg.getObjectHeadItem("_WEB_APPLICATION");
        ArrayList list = (ArrayList) webContext.getAttribute("_SESSION_LIST");
        HiETF root = ctx.getCurrentMsg().getETFBody();
        HiETF group = null;
        String groupName = args.get("GROUP");
        if (StringUtils.isEmpty(groupName)) {
            groupName = "GROUP";
        }
        log.info("[" + list.size() + "][" + groupName + "]");
        int j = 1;
        for (int i = 0; i < list.size(); ++i) {
            HttpSession session = (HttpSession) list.get(i);
            Enumeration en = session.getAttributeNames();
            if (!(en.hasMoreElements())) {
                log.info("[" + session.getId() + "][not elements]");
            } else {
                group = root.addNode(groupName + "_" + j);
                ++j;
                while (en.hasMoreElements()) {
                    String name = (String) en.nextElement();
                    String value = (String) session.getAttribute(name);
                    log.info("[" + name + "][" + value + "]");
                    if (value == null) {
                        continue;
                    }
                    group.setChildValue(name, value);
                }
            }
        }
        return 0;
    }
}