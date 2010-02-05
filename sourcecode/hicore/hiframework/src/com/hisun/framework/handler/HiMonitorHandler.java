package com.hisun.framework.handler;

import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiJMSHelper;
import org.apache.commons.lang.StringUtils;

public class HiMonitorHandler implements IHandler {
    private String factory;
    private String queue;
    private HiJMSHelper jsm;

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public void init() throws HiException {
        this.jsm = new HiJMSHelper();
        this.jsm.initialize(this.factory, this.queue);
    }

    public void destory() {
        if (this.jsm != null) this.jsm.destory();
    }

    public void process(HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();

        if (StringUtils.equals("1", msg.getHeadItem("MON"))) {
            Logger log = HiLog.getLogger(msg);
            if (log.isDebugEnabled()) {
                log.debug("send message to JMS queue!");
            }
            this.jsm.sendMessage(ctx);
        }
    }
}