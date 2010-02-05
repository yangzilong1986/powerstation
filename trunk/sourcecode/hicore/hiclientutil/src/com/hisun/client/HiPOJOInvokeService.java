package com.hisun.client;

import com.hisun.dispatcher.HiRouterOut;
import com.hisun.exception.HiException;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.register.HiRegisterService;
import com.hisun.register.HiServiceObject;

public class HiPOJOInvokeService extends HiInvokeService {
    public HiPOJOInvokeService() {
    }

    public HiPOJOInvokeService(String code) {
        super(code);
    }

    protected HiETF doInvoke(String code, HiETF root) throws Exception {
        if (this.logger.isInfoEnabled()) {
            this.logger.info("request data:[" + code + ":" + this.etf + "]");
        }
        String serverName = info.getServerName();
        try {
            HiServiceObject serviceObject = HiRegisterService.getService(code);

            serverName = serviceObject.getServerName();
        } catch (HiException e) {
        }
        HiMessage msg = new HiMessage(serverName, info.getMsgType());
        msg.setBody(this.etf);
        msg.setHeadItem("STC", code);
        msg.setHeadItem("SCH", "rq");
        msg.setHeadItem("ECT", "text/etf");
        long curtime = System.currentTimeMillis();
        msg.setHeadItem("STM", new Long(curtime));

        HiMessageContext ctx = new HiMessageContext();
        ctx.setCurrentMsg(msg);
        HiMessageContext.setCurrentMessageContext(ctx);

        msg = HiRouterOut.syncProcess(msg);
        this.etf = msg.getETFBody();
        if (this.logger.isInfoEnabled()) {
            this.logger.info("response data:[" + code + ":" + this.etf + "]");
        }
        return this.etf;
    }
}