package com.hisun.component.web;

import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;

public class SetRetPage {
    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();

        msg.setHeadItem("SEND_OUTPUT", args.get("PAGE"));
        return 0;
    }
}