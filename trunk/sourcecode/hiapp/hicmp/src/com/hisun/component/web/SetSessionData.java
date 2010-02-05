package com.hisun.component.web;

import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

public class SetSessionData {
    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        HttpSession session = (HttpSession) msg.getObjectHeadItem("_WEB_SESSION");

        HiETF root = msg.getETFBody();
        String objNam = args.get("ObjNam");

        for (int i = 0; i < args.size(); ++i) {
            String name = args.getName(i).toUpperCase();
            if ("ObjNam".equalsIgnoreCase(name)) {
                continue;
            }

            String value = args.getValue(i);
            if (StringUtils.isBlank(value)) {
                continue;
            }
            if (StringUtils.isNotBlank(objNam)) {
                HashMap map = (HashMap) session.getAttribute(objNam.toUpperCase());

                if (map == null) {
                    map = new HashMap();
                    session.setAttribute(objNam.toUpperCase(), map);
                }
                map.put(name, value);

                HiETF group = root.getChildNode(objNam);
                if (group == null) {
                    group = root.addNode(objNam);
                }
                group.setChildValue(name, value);
            } else {
                session.setAttribute(name, value);

                HiETF sessionRoot = root.getChildNode("SESSION");
                if (sessionRoot == null) {
                    sessionRoot = root.addNode("SESSION");
                }
                sessionRoot.setChildValue(name, value);
            }
        }
        Logger log = HiLog.getLogger(msg);
        Enumeration en = session.getAttributeNames();
        while (en.hasMoreElements()) {
            String name = (String) en.nextElement();
            log.info("[" + name + "][" + session.getAttribute(name) + "]");
        }

        return 0;
    }
}