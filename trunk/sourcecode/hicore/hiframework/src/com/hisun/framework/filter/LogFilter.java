package com.hisun.framework.filter;

import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.pubinterface.IHandlerFilter;
import com.hisun.stat.util.IStat;

public class LogFilter implements IHandlerFilter {
    private final Logger _log;
    private final String _desc;
    private IStat _stat;

    public LogFilter(String desc, Logger log) {
        this._desc = desc;
        this._log = log;
    }

    public LogFilter(String desc, Logger log, IStat stat) {
        this._desc = desc;
        this._log = log;
        this._stat = stat;
    }

    public void process(HiMessageContext ctx, IHandler handler) throws HiException {
        if (this._log.isDebugEnabled()) {
            this._log.debug(this._desc + " - start");
        }
        long time = System.currentTimeMillis();
        long size = Runtime.getRuntime().freeMemory();
        try {
            handler.process(ctx);
        } finally {
            HiLog.close(ctx.getCurrentMsg());
        }
        time = System.currentTimeMillis() - time;
        size -= Runtime.getRuntime().freeMemory();
        if (this._stat != null) this._stat.once(time, size);
        if (this._log.isDebugEnabled()) this._log.debug(this._desc + " - end, exec time(ms) :" + time);
    }
}