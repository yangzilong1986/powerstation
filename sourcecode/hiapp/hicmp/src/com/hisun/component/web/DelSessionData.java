package com.hisun.component.web;

import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpSession;

public class DelSessionData {
    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        HttpSession session = (HttpSession) msg.getObjectHeadItem("_WEB_SESSION");

        HiETF root = msg.getETFBody();
        String objNam = args.get("ObjNam");
        if (StringUtils.isNotBlank(objNam)) {
            session.removeAttribute(objNam.toUpperCase());
            root.removeChildNode(objNam);
            return 0;
        }

        for (int i = 0; i < args.size(); ++i) {
            String name = args.getName(i).toUpperCase();

            String value = args.getValue(i);
            session.removeAttribute(name);
            HiETF sessionRoot = root.getChildNode("SESSION");
            if (sessionRoot != null) {
                sessionRoot.removeChildNode(name);
            }
        }
        return 0;
    }
}