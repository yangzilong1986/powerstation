package com.hisun.framework.imp;

import com.hisun.exception.HiException;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiStringManager;
import org.apache.commons.beanutils.MethodUtils;

import java.lang.reflect.Method;

public class HiHandlerWapper implements IHandler {
    protected final HiStringManager sm = HiStringManager.getManager();
    protected final String name;
    protected Object handler;
    protected final String method;
    private final Logger log;

    public HiHandlerWapper(String name, Object handler, String method, Logger log) throws HiException {
        this.name = name;
        this.handler = handler;
        this.method = method;
        this.log = log;

        Method m = MethodUtils.getAccessibleMethod(handler.getClass(), method, HiMessageContext.class);

        if (m == null) throw new HiException("211002", "handler has no method '" + method + "' :" + name);
    }

    public void process(HiMessageContext ctx) throws HiException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Handler " + this.name + " " + this.method + "() - start");
        }
        try {
            MethodUtils.invokeMethod(this.handler, this.method, ctx);
        } catch (Throwable t) {
            throw HiException.makeException("211005", this.method, t);
        }

        if (this.log.isDebugEnabled()) this.log.debug("Handler " + this.name + " " + this.method + "() - end");
    }
}