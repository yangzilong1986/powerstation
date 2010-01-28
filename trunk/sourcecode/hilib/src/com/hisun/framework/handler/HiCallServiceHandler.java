package com.hisun.framework.handler;


import com.hisun.dispatcher.HiRouterOut;
import com.hisun.exception.HiException;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiStringManager;

public class HiCallServiceHandler implements IHandler {
    private String param;
    private String[][] headers;
    private Logger log;
    private static HiStringManager sm = HiStringManager.getManager();

    public void process(HiMessageContext ctx) throws HiException {

        HiMessage msg = ctx.getCurrentMsg();

        parseParam();

        msgsetHeader(msg);


        HiRouterOut.process(ctx);
    }

    private void parseParam() {

        if (this.param == null) return;

        if (this.headers != null) {

            return;
        }


        String[] pp = this.param.split(";");

        this.headers = new String[pp.length][2];

        for (int i = 0; i < pp.length; ++i) {

            String[] header = pp[i].split("=");

            this.headers[i][0] = header[0];

            this.headers[i][1] = header[1];
        }
    }

    private void msgsetHeader(HiMessage msg) {

        if (this.headers == null) {

            return;
        }

        for (int i = 0; i < this.headers.length; ++i) {

            msg.setHeadItem(this.headers[i][0], this.headers[i][1]);


            if (this.log.isDebugEnabled())
                this.log.debug(sm.getString("callService.debug.setheader", this.headers[i][0], this.headers[i][1]));
        }
    }

    public String getParam() {

        return this.param;
    }

    public void setParam(String param) {

        this.param = param;
    }

    public void setLog(Logger log) {

        this.log = log;
    }
}