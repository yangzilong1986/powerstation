package com.hisun.component.web;

import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class GetOnLineFriends {
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
        ArrayList usrList = new ArrayList();
        for (int i = 0; i < list.size(); ++i) {
            HttpSession session = (HttpSession) list.get(i);
            Enumeration en = null;
            try {
                en = session.getAttributeNames();
            } catch (IllegalStateException e) {
                break label248:
            }
            if (!(en.hasMoreElements())) {
                log.info("[" + session.getId() + "][not elements]");
            } else {
                HashMap usrInf = (HashMap) session.getAttribute("USR_INF");
                if (usrInf == null) {
                    continue;
                }
                label248:
                usrList.add(usrInf.get("USR_ID"));
            }
        }
        int num = NumberUtils.toInt(root.getChildValue("REC_NUM"));

        for (int i = 0; i < num; ++i) {
            group = root.getChildNode(groupName + "_" + (i + 1));
            String usrId = group.getChildValue("FRD_ID");
            if (usrList.contains(usrId)) group.setChildValue("FLG", "1");
            else {
                group.setChildValue("FLG", "0");
            }
        }
        return 0;
    }
}