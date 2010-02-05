package com.hisun.component.web;

import com.hisun.atc.common.HiArgUtils;
import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpSession;

public class ValImgCaptcha {
    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        HttpSession session = (HttpSession) msg.getObjectHeadItem("_WEB_SESSION");
        String chkCod = HiArgUtils.getStringNotNull(args, "ChkCod");
        String value = (String) session.getAttribute("__IMAGE_CAPTCHA");
        session.removeAttribute("__IMAGE_CAPTCHA");

        Logger log = HiLog.getLogger(msg);
        log.info("[" + value + "]:[" + chkCod + "]");
        if (!(StringUtils.equalsIgnoreCase(value, chkCod))) {
            return 1;
        }
        return 0;
    }
}