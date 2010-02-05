package com.hisun.handler;

import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;

public class HiTimeOutFilter implements IHandler {
    private int _timeOut;
    Logger log;

    public HiTimeOutFilter() {
        this._timeOut = 1000;

        this.log = HiLog.getLogger("timeout.trc");
    }

    public void setTimeOut(int timeOut) {
        this._timeOut = timeOut;
    }

    public void process(HiMessageContext ctx) throws HiException {
        HiMessage msg1 = ctx.getCurrentMsg();
        Long tm = (Long) msg1.getObjectHeadItem("STM");
        if (tm == null) {
            return;
        }
        if ((System.currentTimeMillis() - tm.longValue() <= this._timeOut) || (!(this.log.isDebugEnabled()))) return;
        this.log.debug(msg1.getRequestId() + ":超过指定的超时时间:[" + this._timeOut + "ms][" + (System.currentTimeMillis() - tm.longValue()) + "ms]");
    }
}