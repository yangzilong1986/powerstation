package com.hisun.handler;

import com.hisun.exception.HiException;
import com.hisun.framework.event.IServerDestroyListener;
import com.hisun.framework.event.IServerInitListener;
import com.hisun.framework.event.ServerEvent;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.register.HiRegisterService;
import com.hisun.register.HiServiceObject;
import com.hisun.util.HiJMSHelper;
import org.apache.commons.lang.StringUtils;

public class HiMonHandler implements IHandler, IServerInitListener, IServerDestroyListener {
    private HiJMSHelper jmsHelper;
    private String factoryName;
    private String queueName;

    public HiMonHandler() {
        this.jmsHelper = new HiJMSHelper();
    }

    public void setServiceInfo(HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        if (!(msg.hasHeadItem("STC"))) return;
        HiServiceObject service = HiRegisterService.getService(msg.getHeadItem("STC"));
        if (service.isMonSwitch()) {
            msg.setHeadItem("MON", "1");
        }

        if ((!(msg.hasHeadItem("STF"))) && (service.getLogLevel() != null))
            msg.setHeadItem("STF", service.getLogLevel());
    }

    public void dump(HiMessageContext ctx) throws HiException {
        Logger log = HiLog.getLogger(ctx.getCurrentMsg());
        if (log.isInfoEnabled()) {
            log.info("=========DUMP MSG begin =====");
            log.info(ctx.getCurrentMsg());
            log.info("=========DUMP MSG end =====");
        }
    }

    public void process(HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();

        if (!(StringUtils.equals(msg.getHeadItem("MON"), "1"))) {
            return;
        }

        if (msg.hasHeadItem("STC")) {
            msg.getETFBody().setChildValue("MonCod", msg.getHeadItem("STC"));
        }

        this.jmsHelper.sendMessage(msg);
        Logger log = HiLog.getLogger(msg);
        if (log.isInfoEnabled()) log.info("[" + msg.getHeadItem("STC") + "]发送异步消息成功");
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public void serverInit(ServerEvent arg0) throws HiException {
    }

    public void serverDestroy(ServerEvent arg0) throws HiException {
        this.jmsHelper.destory();
    }
}